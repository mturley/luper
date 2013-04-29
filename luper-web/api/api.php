<?php

require 'Slim/Slim.php';
\Slim\Slim::registerAutoloader();
$api = new \Slim\Slim(array(
  'log.enabled' => true,
  'log.level'   => \Slim\Log::DEBUG
));

require_once 'db.php'; // defines getDB()
require_once 'auth.php'; // defines password hashing functions

function handlePDOException($api, $e) {
  // database errors will be delivered as an HTTP 500 Internal Server Error
  $response = new stdclass();
  $response->success = false;
  $response->message = "Database error!  Please contact support@teamluper.com with the error message: "+$e->getMessage();
  $api->halt(200,json_encode($response));
}

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

// registers a new user account
$api->post('/auth-register', function() use ($api) {
  // fetch and decode the request object
  $request = json_decode($api->request()->getBody());
  $email = $request->email;
  $passwordHash = $request->passwordHash;
  $username = $request->username;
  try {
    // check if the email is already registered
    $db = getDB();
    $stmt = $db->prepare("SELECT COUNT(*) AS userCount FROM Users WHERE email = :email");
    $stmt->bindParam("email",$email);
    $stmt->execute();
    $userCount = $stmt->fetchObject()->userCount;
    if($userCount != 0) {
      $response = new stdclass();
      $response->success = false;
      $response->message = "An account with that email address is already registered!";
      echo json_encode($response);
    } else {
      // here we put a singly-hashed password in the database.
      // later on we'll generate a challenge salt and hash
      // the password further during login verification.
      $stmt = $db->prepare(
        "INSERT INTO Users (username, email, passwordHash, isActiveUser, preferences)".
        "           VALUES (:username, :email, :passwordHash, 1, '{}');");
      $stmt->bindParam("username",$username);
      $stmt->bindParam("email",$email);
      $stmt->bindParam("passwordHash",$passwordHash);
      $stmt->execute();
      // build, encode and send the response object
      $response = new stdclass();
      $response->success = true;
      $response->insertId = $db->lastInsertId();
      echo json_encode($response);
    }
  } catch(PDOException $e) {
    handlePDOException($api, $e);
  }
});

// generates, stores and returns a new random salt for use in hashing.
$api->post('/auth-challenge', function() use ($api) {
  // fetch and decode the request object
  $request = json_decode($api->request()->getBody());
  $email = $request->email;
  try {
    $db = getDB();
    $stmt = $db->prepare("SELECT passwordHash FROM Users WHERE email = :email");
    $stmt->bindParam("email",$email);
    $stmt->execute();
    $obj = $stmt->fetchObject();
    $response = new stdclass();
    if(!$obj) {
      $response->success = false;
      $response->message = "No user found with that email address. Did you already register?";
    } else if($obj->passwordHash == null) {
      $response->success = false;
      $response->message = "User has no registered password; maybe you meant to log in via facebook?";
    } else {
      $salt = newRandomSalt($email);
      $stmt = $db->prepare("UPDATE Users SET challengeSalt = :salt WHERE email = :email");
      $stmt->bindParam("salt",$salt);
      $stmt->bindParam("email",$email);
      $stmt->execute();
      if($stmt->rowCount() != 1) {
        $response->success = false;
        $response->message = "Something is wrong with this user in the database. Please contact support@teamluper.com!";
      } else {
        $response->success = true;
        $response->salt = $salt;
      }
    }
    echo json_encode($response);
  } catch(PDOException $e) {
    handlePDOException($api, $e);
  }
});

// validates a login attempt against the database.
$api->post('/auth-login', function() use ($api) {
  // fetch and decode the request object
  $request = json_decode($api->request()->getBody());
  $email = $request->email;
  $attemptHash = $request->attemptHash;
  try {
    // validate the password against the database
    $db = getDB();
    $isLoginValid = validateLoginAttempt($db, $email, $attemptHash);
    // build, encode and send the response object
    $response = new stdclass();
    $response->success = true;
    $response->isLoginValid = $isLoginValid;
    echo json_encode($response);
  } catch(PDOException $e) {
    handlePDOException($api, $e);
  }
});

// resets and/or changes a password
$api->post('/auth-passwd', function() use ($api) {
  try {
    // fetch and decode the request object
    $request = json_decode($api->request()->getBody());
    $email = $request->email;
    $oldPasswordAttemptHash = $request->oldPasswordAttemptHash;
    $newPassword = $request->newPasswordHash;
    // validate the old password against the database
    $db = getDB();
    $oldPasswordIsValid = validateLoginAttempt($db, $email, $oldPasswordAttemptHash);
    if(!$oldPasswordIsValid) {
      $response = new stdclass();
      $response->success = false;
      $response->message = "Incorrect Old Password!";
      $api->halt(403,json_encode($response));
    } else {
      $newHash = sha256($newPassword);
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
        $response->message = "Password failed to update! Are you sure that account exists?";
      }
      echo json_encode($response);
    }
  } catch(PDOException $e) {
    handlePDOException($api, $e);
  }
});

$api->get("/fetch-user/:email", function($email) use ($api) {
  try {
    $db = getDB();
    $stmt = $db->prepare("SELECT * FROM Users WHERE email = :email");
    $stmt->bindParam("email", $email);
    $stmt->execute();
    if($stmt->rowCount() == 1) {
      echo json_encode($stmt->fetchObject());
    } else {
      echo "{}";
    }
  } catch(PDOException $e) {
    handlePDOException($api, $e);
  }
});

$api->run();