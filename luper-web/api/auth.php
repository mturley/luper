<?php

require_once 'db.php';

// this well-salted sha256 password hashing algorithm comes courtesy of:
// http://alias.io/2010/01/store-passwords-safely-with-php-and-mysql/
// with some modifications by me, mainly with challenge salts, etc.

define("HASH_COUNT", 10000);
// HASH_COUNT must match the corresponding setting on the client,
// or all login attempts will fail as "invalid password".

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
function saltyHashBrowns($simpleHashedPassword, $salt, $hashCount) {
  // Prefix the password with the salt
  $hash = $salt . $simpleHashedPassword;
  // Hashing our hashes times a hundred thousand, because security!
  for ( $i = 0; $i < $hashCount; $i++ ) {
    $hash = sha256($hash); // hashity hash
  }
  return $hash;
}

// computes a simple hash with no salt, for initial password storage.
function sha256($password) {
  return hash('sha256', $password);
}

// validates an attempted login hash that was made using the latest challenge salt.
function validateLoginAttempt($db, $email, $attemptHash) {
  $knownSimpleHash = getKnownSimpleHashForUser($db, $email);
  if(!$knownSimpleHash) return false;
  // fetch the last challenge salt, the same one that was used on the client.
  $stmt = $db->prepare("SELECT challengeSalt FROM Users WHERE email = :email");
  $stmt->bindParam("email", $email);
  $stmt->execute();
  $saltObj = $stmt->fetchObject();
  if(!$saltObj) return false;
  $salt = $saltObj->challengeSalt;
  // the db's challenge salt has been used, so expire it by setting it null.
  // while we're at it, update lastLoginTime to NOW().
  $stmt = $db->prepare("UPDATE Users SET challengeSalt = NULL, lastLoginTime = NOW() WHERE email = :email");
  $stmt->bindParam("email", $email);
  $stmt->execute();
  $knownCompleteHash = saltyHashBrowns($knownSimpleHash, $salt, HASH_COUNT);
  return $attemptHash == $knownCompleteHash;
}

// actually fetches the passwordHash field given a user's email address.
function getKnownSimpleHashForUser($db, $email) {
  $query = $db->prepare("SELECT passwordHash FROM Users WHERE email = :email LIMIT 1");
  $query->bindParam("email", $email);
  $query->execute();
  $obj = $query->fetchObject();
  if(!$obj) return false;
  return $obj->passwordHash;
}