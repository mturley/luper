<?php

function button($url, $label) {
  echo '<button type="button" ';
  if($_SERVER['PHP_SELF'] == $url) echo 'class="active"';
  echo '><a href="'.$url.'">'.$label.'</a></button>';
}

?>

<div id="header">
  <?php
    button("index.php","Home (1.0 Release)");
    button("beta.php","Archived Beta");
    button("alpha.php","Archived Alpha");
    button("download/Luper-1.0.apk","Download APK");
    button("feedback.php","Feedback");
  ?>
</div>