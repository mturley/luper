<?php
$useragent=$_SERVER['HTTP_USER_AGENT'];
if(preg_match('/(android|bb\d+|meego).+mobile|avantgo|bada\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\.(browser|link)|vodafone|wap|windows (ce|phone)|xda|xiino/i',$useragent)||preg_match('/1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\-(n|u)|c55\/|capi|ccwa|cdm\-|cell|chtm|cldc|cmd\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\-s|devi|dica|dmob|do(c|p)o|ds(12|\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\-|_)|g1 u|g560|gene|gf\-5|g\-mo|go(\.w|od)|gr(ad|un)|haie|hcit|hd\-(m|p|t)|hei\-|hi(pt|ta)|hp( i|ip)|hs\-c|ht(c(\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\-(20|go|ma)|i230|iac( |\-|\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\/)|klon|kpt |kwc\-|kyo(c|k)|le(no|xi)|lg( g|\/(k|l|u)|50|54|\-[a-w])|libw|lynx|m1\-w|m3ga|m50\/|ma(te|ui|xo)|mc(01|21|ca)|m\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\-2|po(ck|rt|se)|prox|psio|pt\-g|qa\-a|qc(07|12|21|32|60|\-[2-7]|i\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\-|oo|p\-)|sdk\/|se(c(\-|0|1)|47|mc|nd|ri)|sgh\-|shar|sie(\-|m)|sk\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\-|v\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\-|tdg\-|tel(i|m)|tim\-|t\-mo|to(pl|sh)|ts(70|m\-|m3|m5)|tx\-9|up(\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\-|your|zeto|zte\-/i',substr($useragent,0,4)))
header('Location: index-mobile.php');
?>

<html>
  <head>
    <title>Luper</title>
    <link rel='stylesheet' href='style.css' />
  </head>
  <body>

  <div id="container">
    <div id="header">
      <button type="button" class="active"><a href="/">Home (Beta Release)</a></button>
      <button type="button"><a href="alpha.html">Archived Alpha Release</a></button>
      <button type="button"><a href="download/LuperBeta.apk">Download Beta APK</a></button>
    </div>
  	<div align="center" id="content">
      <div class="halfcolumn">
        <img src="luper.png" />
        <h1>Beta Release (4/2/2013)</h1>
        <a id="downloadlink" href="download/LuperBeta.apk">
          <img src="ic_launcher.png" id="appicon"><br />
          <strong>Click to Download</strong><br />
          LuperBeta.apk
        </a>
        <br /><br /><br />
        <h2 class="darkred">NOTE: Your device must be set to allow installation of applications from "Unknown Sources" (found in Settings > Security)</h2>
      </div>
      <div class="halfcolumn">
        <br />
        <h1>Installation Instructions</h1>
        <ol class="leftalign">
          <li>Navigate to this page (TeamLuper.com) in your Android device's browser.</li>
          <li>Download LuperBeta.apk directly to your device by touching the icon at left.</li>
          <li>When the download is complete, pull down your notifications pane and touch the LuperBeta.apk Download Complete notification.  You will be prompted to install the application.</li>
        </ol>
        <h2>OR</h2>
        <ol class="leftalign">
          <li>Download LuperBeta.apk on your PC and copy it to your Android device's storage.</li>
          <li>Make sure you have a File Manager application on your device, such as <a href="https://play.google.com/store/apps/details?id=com.metago.astro&hl=en">ASTRO (available on Google Play)</a></li>
          <li>Using your File Manager, navigate to and open the LuperBeta.apk file.  You will be prompted to install the application.</li>
        </ol>
      </div>
      <div class="fullcolumn alignleft">
        <br />
        <div class="bigindent">
          <h1>Luper Project Documentation:</h1>
          <h2>
            <a href="https://docs.google.com/document/d/1_9iY-CbKjMrHwbM1HPnjRyZs-nU5rbPeuXvCtY9m0FI/edit">
            Click Here to read the Beta Release Notes
            </a>
            <br />
            <a href="https://docs.google.com/document/d/1zzYt0WOMM9ZO24DtscgJJ3vRmZ808cYjxcJRq_FCwLQ/edit">
            Click Here to read the Requirements Specification (updated 4/2)
            </a>
            <br />
            <a href="https://docs.google.com/document/d/1_pbSkRCXVBt0ycZDsRQcn6zgjMj9KoIOc83PRVLPSoc/edit">
            Click Here to read the Design Specification (updated 4/2)
            </a>
          </h2>
        </div>
      </div>
	  </div>
  </div>
  <div id="footer">
    <p><strong>Team Luper is:</strong> Ben Foster, Brad Bullman, Cameron Smith, Eric Smith, Mike Turley, Josh Chudy, Steve Donahue, Sofya Vorotnikova, Xian Chen</p>
  </div>
  </body>
</html>