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
    $stmt = $db->prepare("SELECT COUNT(*) AS numUsers FROM Users");
    $stmt->execute();
    $numUsers = $stmt->fetchObject()->numUsers;
    echo "Success!  Here's some data from the database: there are currently ".$numUsers
        ." registered users.";
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