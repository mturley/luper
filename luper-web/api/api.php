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
    echo "it works!";
  } catch(PDOException $e) {
    $api->halt(500,$e->getMessage());
  }
});

$api->run();

?>