package com.teamluper.luper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.rest.RestService;
import com.teamluper.luper.rest.LuperRestClient;
import org.springframework.web.client.HttpClientErrorException;

@EActivity
public class LuperDevToolsActivity extends Activity {
  @RestService
  LuperRestClient rest;
  SQLiteDataSource dataSource ;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_luper_devtools);

    dataSource = new SQLiteDataSource(this);
    dataSource.open();
  }

  @Override
  protected void onStop() {
    if(dataSource.isOpen()) dataSource.close();
    super.onStop();
  }

  @Override
  protected void onResume() {
    if(!dataSource.isOpen()) dataSource.open();
    super.onResume();
  }

  // this will be removed, it's an example of how we'll access the EC2 server.
  @Background
  public void testRestAPI(View view) {
    if(!LuperMainActivity.deviceIsOnline(this)) {
      alert("Internet Connection Required",
        "That feature requires access to the internet, and your device is " +
          "offline!  Please connect to a Wifi network or a mobile data network " +
          "and try again.");
      return;
    }
    try {
      String t = rest.getTestString();
      alert("Database Connection Test PASS!",
        "Request: GET http://teamluper.com/api/test\n" +
          "Response: '" + t + "'");
    } catch(HttpClientErrorException e) {
      alert("Database Connection Test FAIL!", e.toString());
    }
  }

  //method to navigate to the audiorecorder activity
  public void startRecording(View view) {
    Intent intent = new Intent(this, AudioRecorderTestActivity_.class);
    startActivity(intent);
  }

  public void dropAllData(View view) {
    dataSource.dropAllData();
    DialogFactory.alert(this,"Done!");
  }

  @UiThread
  public void alert(String title, String message) {
    DialogFactory.alert(this, title, message);
  }
}