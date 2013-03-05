<?php

require 'Slim/Slim.php';
\Slim\Slim::registerAutoloader();
$api = new \Slim\Slim(array(
  'log.enabled' => true,
  'log.level'   => \Slim\Log::DEBUG
));

require 'db.php'; // defines getDB()

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
        ." registered users, and ".$numReds." users have 'red' as their favorite color.";
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

?>