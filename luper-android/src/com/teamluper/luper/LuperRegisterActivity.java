package com.teamluper.luper;

import com.googlecode.androidannotations.annotations.EActivity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

@EActivity
public class LuperRegisterActivity extends FragmentActivity {
	
	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);

	    setContentView(R.layout.activity_luper_register);
	  }
	  @Override
	  public boolean onCreateOptionsMenu(Menu menu) {
	    super.onCreateOptionsMenu(menu);
	    getMenuInflater().inflate(R.menu.luper_register, menu);
	    return true;
	  }

	}