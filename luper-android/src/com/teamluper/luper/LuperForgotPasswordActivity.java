package com.teamluper.luper;
import com.googlecode.androidannotations.annotations.EActivity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

@EActivity
public class LuperForgotPasswordActivity extends FragmentActivity {

	@Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);

	    //uses the activity_luper_register.xml to set the graphical layout
	    setContentView(R.layout.activity_luper_password);
	  }
	  @Override
	  public boolean onCreateOptionsMenu(Menu menu) {
	    super.onCreateOptionsMenu(menu);
	    getMenuInflater().inflate(R.menu.luper_register, menu);
	    return true;
	  }

	}