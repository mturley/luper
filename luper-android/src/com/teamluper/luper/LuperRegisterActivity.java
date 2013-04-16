package com.teamluper.luper;

import android.view.View;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.EActivity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import com.googlecode.androidannotations.annotations.UiThread;

@EActivity
public class LuperRegisterActivity extends FragmentActivity {

	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    //uses the activity_luper_register.xml to set the graphical layout
	    setContentView(R.layout.activity_luper_register);
	  }
	  @Override
	  public boolean onCreateOptionsMenu(Menu menu) {
	    super.onCreateOptionsMenu(menu);
	    getMenuInflater().inflate(R.menu.luper_register, menu);
	    return true;
	  }

    @Background
    public void processRegistration(View view) {
      alert("Registration","TODO: actually process it");
    }

    @UiThread
    public void alert(String title, String message) {
      DialogFactory.alert(this, title, message);
    }

	}