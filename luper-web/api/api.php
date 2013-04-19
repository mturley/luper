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

// /auth/register  registers a new user account
$api->post('/auth/register',
  function() use ($api) {
  try {
    // fetch and decode the request object
    $request = json_decode($api->request()->getBody());
    $email = $request->email;
    $password = $request->password;
    $username = $request->username;
    // put some data into the database
    $db = getDB();
    $hash = newRandomSaltedHash($email, $password);
    $stmt = $db->prepare(
      "INSERT INTO Users (username, email, passwordHash, isActiveUser, preferences)".
      "           VALUES (:username, :email, :passwordHash, 1, '{}');");
    $stmt->bindParam("username",$username);
    $stmt->bindParam("email",$email);
    $stmt->bindParam("passwordHash",$hash);
    $stmt->execute();
    // build, encode and send the response object
    $response = new stdclass();
    $response->success = true;
    $response->insertId = $db->lastInsertId();
    echo json_encode($response);
  } catch(PDOException $e) {
    // database errors will be delivered as an HTTP 500 Internal Server Error
    $api->halt(500,$e->getMessage());
  }
});

// /auth/challenge  retrieves the challenge salt value for a given user
$api->post('/auth/challenge', function($email) use ($api) {
  try {
    // TODO refactor this so every challenge salt is different... we'd need to store a different password hash too
    $db = getDB();
    $hash = getKnownHashForUser($db, $email);
    $salt = substr($hash, 0, 64);
    $response = new stdclass();
    $response->email = $email;
    $response->salt = $salt;
    echo json_encode($response);
  } catch(PDOException $e) {
    $api->halt(500,$e->getMessage());
  }
});

// /auth/login  validates a login attempt against the database.
$api->post('/auth/login', function() use ($api) {
  try {
    // fetch and decode the request object
    $request = json_decode($api->request()->getBody());
    $email = $request->email;
    $password = $request->password; // may be a hash or plain text depending on...
    $securityType = $request->securityType; // this.
    // validate the password against the database
    $db = getDB();
    $isLoginValid = false;
    if($securityType == "plaintext") {
      $isLoginValid = validatePlainTextLoginAttempt($db, $email, $password);
    } else if($securityType == "halfbaked") {
      $isLoginValid = validateHalfBakedLoginAttempt($db, $email, $password);
    }
    // build, encode and send the response object
    $response = new stdclass();
    $response->isLoginValid = $isLoginValid;
    echo json_encode($response);
  } catch(PDOException $e) {
    // database errors will be delivered as an HTTP 500 Internal Server Error
    $api->halt(500,$e->getMessage());
  }
});

// /auth/passwd  resets and/or changes a password
$api->post('/auth/passwd', function() use ($api) {
  try {
    // fetch and decode the request object
    $request = json_decode($api->request()->getBody());
    $email = $request->email;
    $oldPassword = $request->oldPassword; // depends on securityType
    $newPassword = $request->newPassword; // plain text
    $securityType = $request->securityType;
    // validate the old password against the database
    $db = getDB();
    $oldPasswordIsValid = false;
    if($securityType == "plaintext") {
      $oldPasswordIsValid = validatePlainTextLoginAttempt($db, $email, $oldPassword);
    } else if($securityType == "halfbaked") {
      $oldPasswordIsValid = validateHalfBakedLoginAttempt($db, $email, $oldPassword);
    }
    if(!$oldPasswordIsValid) {
      $api->halt(403,"FORBIDDEN: Incorrect Old Password.");
    } else {
      $newHash = newRandomSaltedHash($email, $newPassword);
      $stmt = $db->prepare("UPDATE Users SET passwordHash = :hash WHERE email = :email");
      $stmt->bindParam("hash",$newHash);
      $stmt->bindParam("email",$email);
      $stmt->execute();
      // build, encode and send the response object
      $response = new stdclass();
      if($stmt->rowCount() == 1) {
        $response->success = true;
      } else {
        $response->success = false;
      }
      echo json_encode($response);
    }
  } catch(PDOException $e) {
    // database errors will be delivered as an HTTP 500 Internal Server Error
    $api->halt(500,$e->getMessage());
  }
});

$api->run();