package com.teamluper.luper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.facebook.Response;
import com.facebook.Session;

import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.Request;
import com.facebook.model.GraphUser;

import com.googlecode.androidannotations.annotations.Background;

import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.rest.RestService;
import com.teamluper.luper.rest.LuperRestClient;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
@EActivity
public class LuperLoginActivity extends SherlockFragmentActivity {
  ViewPager mViewPager;
  TabsAdapter mTabsAdapter;

  // Facebook Login Session
  private Session session;
  private String accessToken;

  protected void loadActiveSession() {
	  session = Session.getActiveSession();
	  accessToken = session.getAccessToken();
  }

  @RestService
  LuperRestClient restClient;

  private SQLiteDataSource dataSource;

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
	  super.onActivityResult(requestCode,resultCode,data);
	  Session.getActiveSession().onActivityResult(this,requestCode,resultCode,data);
  }

  // How Luper should behave when user logs into or out of facebook
  protected void onSessionStateChange(Session sesh, SessionState seshState, Exception e) {
	  if (seshState.isOpened()) {
		  /** usr logged in **/
		  // Create new request to facebook API
		  Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {

		    // callback after Graph API response with user object
		    @Override
		    public void onCompleted(GraphUser user, Response response) {
          String email = "";
		    	//if (user != null) {
		    	//	  TextView welcome = (TextView) findViewById(R.id.welcome);
		    	//	  welcome.setText("Hello " + user.getName() + "!");
		    	//	}
		    }

		  });
	  } else if (seshState.isClosed()) {
		  // usr logged out
	  } else {
		  // probably shouldn't be here
	  }
  }

  private Session.StatusCallback callback = new Session.StatusCallback() {
	  @Override
	  public void call(Session sesh, SessionState seshState, Exception e) {
		  onSessionStateChange(sesh,seshState,e);
	  }
  };

  private UiLifecycleHelper uiHelper;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    // enable tabs in the ActionBar
    final ActionBar bar = getSupportActionBar();
    bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
    bar.setTitle(R.string.login_title);
    bar.setDisplayHomeAsUpEnabled(false);

    // set up the ViewPager and Tabs
    mViewPager = new ViewPager(this);
    mViewPager.setId(R.id.tabcontentpager);
    setContentView(mViewPager);
    mTabsAdapter = new TabsAdapter(this, mViewPager);
    mTabsAdapter.addTab(bar.newTab().setText(""+"Log In"),
      TabLoginFragment_.class, null);
    mTabsAdapter.addTab(bar.newTab().setText(""+"Register"),
      TabRegisterFragment_.class, null);

    // connect to the database
    dataSource = new SQLiteDataSource(this);
    dataSource.open();

    // UI handler - Facebook login
    uiHelper = new UiLifecycleHelper(this,callback);
    uiHelper.onCreate(savedInstanceState);
  }

  @Override
  protected void onStop() {
    if(dataSource.isOpen()) dataSource.close();
    super.onStop();
  }

  @Override
  public void onDestroy() {
      super.onDestroy();
      uiHelper.onDestroy();
  }

  @Override
  protected void onResume() {
    if(!dataSource.isOpen()) dataSource.open();
    session = Session.getActiveSession();
    super.onResume();
    uiHelper.onResume();
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
      super.onSaveInstanceState(outState);
      uiHelper.onSaveInstanceState(outState);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    super.onCreateOptionsMenu(menu);
    getSupportMenuInflater().inflate(R.menu.luper_login, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if(item.getItemId() == R.id.action_forgot_password) {
      Intent intent = new Intent(this, LuperForgotPasswordActivity_.class);
      startActivity(intent);
    }
    return true;
  }

  public SQLiteDataSource getDataSource() {
    return dataSource;
  }

  @Background
  public void skipLogin(View v) {
    User dummyUser = dataSource.getUserById(1);
    dataSource.setActiveUser(dummyUser);
    startMainActivity();
  }

  @UiThread
  public void startMainActivity() {
    Intent intent = new Intent(this, LuperMainActivity_.class);
    startActivity(intent);
  }

  @UiThread
  public void prefillLoginForm(String email) {
    getSupportActionBar().setSelectedNavigationItem(0); // switch to login tab
    EditText emailField = (EditText) findViewById(R.id.login_email);
    EditText passwordField = (EditText) findViewById(R.id.login_password);
    emailField.setText(email);
    passwordField.setText("");
    passwordField.requestFocus();
  }

  public static String sha1(String input) throws NoSuchAlgorithmException {
    MessageDigest mDigest = MessageDigest.getInstance("SHA1");
    byte[] result = mDigest.digest(input.getBytes());
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < result.length; i++) {
      sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
    }
    return sb.toString();
  }
}
