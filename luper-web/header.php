<?php

function button($url, $label) {
  echo '<button type="button" ';
  if($_SERVER['PHP_SELF'] == $url) echo 'class="active"';
  echo '><a href="'.$url.'">'.$label.'</a></button>';
}

?>

<div id="header">
  <?php
    button("/index.php","Home (1.0 Release)");
    button("/beta.php","Archived Beta");
    button("/alpha.php","Archived Alpha");
    button("/download/Luper-1.0.apk","Download APK");
    button("/feedback.php","Feedback");
  ?>
</div>

<script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
<script>
  $(function() {
    // turn off the underlines in the links and make the entire button clickable instead
    $("#header").find("button").click(function() {
      document.location = $(this).find("a").attr("href");
    }).css("cursor","hand").find("a").css("text-decoration","none");
  });
</script>