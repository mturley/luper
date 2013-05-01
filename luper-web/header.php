<?php

function active($page) {
  if($activePage == $page) {
    echo 'c'
  }
}

<div id="header">
  <button type="button" class="active"><a href="/">Home (1.0 Release)</a></button>
  <button type="button"><a href="beta.php">Archived Beta Release</a></button>
  <button type="button"><a href="alpha.html">Archived Alpha Release</a></button>
  <button type="button"><a href="download/LuperBeta.apk">Download Latest APK</a></button>
  <button type="button"><a href="feedback.php">Feedback</a></button>
</div>