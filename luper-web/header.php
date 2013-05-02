<?php

function button($url, $label, $icon = null) {
  echo '<button type="button" ';
  if($_SERVER['PHP_SELF'] == $url) echo 'class="active"';
  echo '>';
  if($icon != null) echo '<img src="'.$icon.'" />';
  echo '<a href="'.$url.'">'.$label.'</a></button>';
}

?>

<div id="header">
  <?php
    button("/index.php","Home (1.0 Release)","png/9_av_play.png");
    button("/beta.php","Archived Beta","png/9_av_rewind.png");
    button("/alpha.php","Archived Alpha","png/9_av_previous.png");
    button("/download/Luper-1.0.apk","Download APK","png/9_av_download.png");
    button("/feedback.php","Feedback","png/5_content_email.png");
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