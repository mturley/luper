<?php

// connects to the MySQL database and returns a PDO handle for it.
function getDB() {
  $dbhost = "localhost";
  $dbname = "luper";
  $dbuser = "luper";
  $dbpass = "luper";
  $db = new PDO("mysql:host=$dbhost;dbname=$dbname", $dbuser, $dbpass);
  $db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
  return $db;
}