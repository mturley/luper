<?php

require 'Slim/Slim.php';
\Slim\Slim::registerAutoloader();
$api = new \Slim\Slim(array(
  'log.enabled' => true,
  'log.level'   => \Slim\Log::DEBUG
));

require_once 'db.php'; // defines getDB()
require_once 'auth.php'; // defines password hashing functions

$api->get('/null', function() use ($api) {
  echo "";
});

$api->get('/test', function() use ($api) {
  try {
    $db = getDB();
    $numUsers = $db->query("SELECT COUNT(*) AS numUsers FROM Users")->fetchObject()->numUsers;
    echo "Success!  Here's some data from the database: there are currently ".$numUsers
        ." registered users.";
  } catch(PDOException $e) {
    $api->halt(500,$e->getMessage());
  }
});

// /auth/register/  registers a new user account
$api->post('/auth/register/:email/:plainTextPassword/:username',
  function($email, $plainTextPassword, $username) use ($api) {
  try {
    $input = $api->request()->getBody();
    // TODO fetch actual values from the body
    $db = getDB();
    $hash = newRandomSaltedHash($email, $plainTextPassword);
    $stmt = $db->prepare(
      "INSERT INTO Users (username, email, passwordHash, isActiveUser, preferences)".
      "           VALUES (:username, :email, :passwordHash, 1, '{}');");
    $stmt->bindParam("username",$username);
    $stmt->bindParam("email",$email);
    $stmt->bindParam("passwordHash",$hash);
    $stmt->execute();
    $response = new stdclass();
    $response->success = true;
    $response->insertId = $db->lastInsertId();
    echo json_encode($response);
  } catch(PDOException $e) {
    $api->halt(500,$e->getMessage());
  }
});

// /auth/passwd/  resets and/or changes a password
$api->post('/auth/passwd/:email/:newPlainTextPassword', function() use ($api) {
  try {
    // TODO update query via PDO, respond with success JSON
  } catch(PDOException $e) {
    $api->halt(500,$e->getMessage());
  }
});

// /auth/login/  validates a login attempt with the half-baked approach (see auth.php)
// better security with or without HTTPS, but slower than below.
$api->get('/auth/login/:email/:halfBakedPasswordHash', function() use ($api) {
  try {
    // TODO
  } catch(PDOException $e) {
    $api->halt(500,$e->getMessage());
  }
});

// /auth/login_insecure/  validates a login attempt with plain text submission.
// acceptable security if used exclusively over HTTPS.  Faster than above.
$api->get('/auth/login_insecure/:email/:plainTextPassword', function() use ($api) {
  try {
    // TODO
  } catch(PDOException $e) {
    $api->halt(500,$e->getMessage());
  }
});

// /auth/salt/  retrieves the salt value for a given user
// for use in performing a client-side partial hash ("half-baked hash")
$api->get('/auth/salt/:email', function($email) use ($api) {
  try {
    $hash = getKnownHashForUser($email);
    $salt = substr($hash, 0, 64);
    $response = new stdclass();
    $response->email = $email;
    $response->salt = $salt;
    echo json_encode($response);
  } catch(PDOException $e) {
    $api->halt(500,$e->getMessage());
  }
});

$api->run();