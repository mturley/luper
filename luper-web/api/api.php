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
    $numReds = $db->query("SELECT COUNT(*) AS numReds FROM Users
                           WHERE favColor = 'red'")->fetchObject()->numReds;
    echo "Success!  Here's some data from the database: there are currently ".$numUsers
        ." registered users, and ".$numReds." of them have their favColor preference set to \"red\".";
  } catch(PDOException $e) {
    $api->halt(500,$e->getMessage());
  }
});

$api->get('/account/byEmail/:email', function() use ($api) {
  try {
    $db = getDB();
    // TODO
  } catch(PDOException $e) {
    $api->halt(500,$e->getMessage());
  }
});

$api->run();