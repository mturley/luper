<?php

require_once 'db.php';

// this well-salted sha256 password hashing algorithm comes courtesy of:
// http://alias.io/2010/01/store-passwords-safely-with-php-and-mysql/
// with some modifications by me, mostly the addition of the "half-baked" approach.
// this is probably all overkill, but it's better to be safe than sorry.

define("HALF_TOTAL_NUM_HASHES",50000); // we will be hashing twice as many times as this number.
// this number must match the one on the client or the password will fail to validate.

// computes a new random salt.
function newRandomSalt($email) {
  // Create a 256 bit (64 characters) long random salt
  // Let's add 'something random' and the username
  // to the salt as well for added security
  $salt = hash('sha256', uniqid(mt_rand(), true) . 'something random' . strtolower($email));
  return $salt;
}

// computes a partial to full hash using the given salt and the given number of hash repeats.
// if you're passing a plain text password for $semiHashedPassword, you should pass true for $initialize.
function saltyHashBrowns($semiHashedPassword, $salt, $hashCount, $initialize) {
  if($initialize) {
    // Prefix the password with the salt
    $hash = $salt . $semiHashedPassword;
  }
  // Hashing our hashes times a hundred thousand, because security!
  for ( $i = 0; $i < $hashCount; $i++ ) {
    $hash = hash('sha256', $hash); // hashity hash
  }
  // Prefix the hash with the salt again so we can retrieve it later
  $hash = $salt . $hash;
  return $hash;
}

// computes a new random salt, then computes a full hash based on it.
// use for registering or updating password only. (basically anytime you need to store a new password hash)
function newRandomSaltedHash($email, $plainPassword) {
  $salt = newRandomSalt($email);
  $hash = saltyHashBrowns($plainPassword, $salt, HALF_TOTAL_NUM_HASHES*2, true); // full hash
  return $hash;
}

// a basic authentication from a password which was submitted over the internet in plain text.
// terrible for regular use on HTTP (vulnerable to very simple man-in-the-middle attacks)
// but just fine if the password is sent securely over SSL via HTTPS.
function validatePlainTextLoginAttempt($db, $email, $plainPassword) {
  $knownHash = getKnownHashForUser($db, $email);
  if(!$knownHash) return false;
  $salt = substr($knownHash, 0, 64);
  $attemptHash = saltyHashBrowns($plainPassword, $salt, HALF_TOTAL_NUM_HASHES*2, true); // full hash
  return $attemptHash == $knownHash;
}

// alternatively, you can authenticate by submitting a password that has already been hashed a bunch by the client.
// as long as the total number of hashes is the same, the result will still match if valid.
function validateHalfBakedLoginAttempt($db, $email, $halfBakedHash) {
  // remove the hash from its salty envelope, taking note of the salt.
  $salt = substr($halfBakedHash, 0, 64);
  $hash = substr($halfBakedHash, 64);
  $attemptHash = saltyHashBrowns($hash, $salt, HALF_TOTAL_NUM_HASHES, false);
  $knownHash = getKnownHashForUser($db, $email);
  if(!$knownHash) return false;
  return $attemptHash == $knownHash;
}

// actually fetches the passwordHash field given a user's email address.
function getKnownHashForUser($db, $email) {
  $query = $db->prepare("SELECT passwordHash FROM Users WHERE email = :email LIMIT 1");
  $query->bindParam("email", $email);
  $query->execute();
  $obj = $query->fetchObject();
  if(!$obj) return false;
  return $query->fetchObject()->passwordHash;
}