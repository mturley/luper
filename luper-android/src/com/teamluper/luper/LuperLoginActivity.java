package com.teamluper.luper;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.UiThread;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
//modified from Activity to FragmentActivity for Facebook
@EActivity
public class LuperLoginActivity extends SherlockFragmentActivity {
  ViewPager mViewPager;
  TabsAdapter mTabsAdapter;
  SQLiteDataSource dataSource;

  /**
   * A dummy authentication store containing known user names and passwords.
   * TODO: remove after connecting to a real authentication system.
   */
  private static final String[] DUMMY_CREDENTIALS = new String[] {
      "foo@example.com:hello", "bar@example.com:world" };

  /**
   * The default email to populate the email field with.
   */
  public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";

  /**
   * Keep track of the login task to ensure we can cancel it if requested.
   */
  private UserLoginTask mAuthTask = null;

  // Values for email and password at the time of the login attempt.
  private String mEmail;
  private String mPassword;

  // UI references.
  private EditText mEmailView;
  private EditText mPasswordView;
  private View mLoginFormView;
  private View mLoginStatusView;
  private TextView mLoginStatusMessageView;

  //Instance of FacebookLoginFragment
  //private FacebookLoginFragment facebookLoginFragment;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // enable tabs in the ActionBar
    final ActionBar bar = getSupportActionBar();
    bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

    // set up the ViewPager and Tabs
    mViewPager = new ViewPager(this);
    mViewPager.setId(R.id.tabcontentpager);
    setContentView(mViewPager);
    mTabsAdapter = new TabsAdapter(this, mViewPager);
    mTabsAdapter.addTab(bar.newTab().setText(""+"Log In"),
      TabLoginFragment_.class, null);
    mTabsAdapter.addTab(bar.newTab().setText(""+"Register"),
      TabRegisterFragment_.class, null);

    // set up the SQLite database access.
    dataSource = LuperMainActivity.getInstance().getDataSource();
    if(!dataSource.isOpen()) dataSource.open();

    // Set up the login form.
    mEmail = getIntent().getStringExtra(EXTRA_EMAIL);
    mEmailView = (EditText) findViewById(R.id.email);
    mEmailView.setText(mEmail);

    mPasswordView = (EditText) findViewById(R.id.password);
    mPasswordView
        .setOnEditorActionListener(new TextView.OnEditorActionListener() {
          @Override
          public boolean onEditorAction(TextView textView, int id,
              KeyEvent keyEvent) {
            if (id == R.id.login || id == EditorInfo.IME_NULL) {
              attemptLogin();
              return true;
            }
            return false;
          }
        });

    mLoginFormView = findViewById(R.id.login_form);
    mLoginStatusView = findViewById(R.id.login_status);
    mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

    findViewById(R.id.sign_in_button).setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            attemptLogin();
          }
        });


    //facebook button implementation
    /*
    if (savedInstanceState == null) {
        // Add the fragment on initial activity setup
        facebookLoginFragment = new FacebookLoginFragment();
        getSupportFragmentManager()
        .beginTransaction()
        .add(android.R.id.content, facebookLoginFragment)
        .commit();
    } else {
        // Or set the fragment from restored state info
        facebookLoginFragment = (FacebookLoginFragment) getSupportFragmentManager()
        .findFragmentById(android.R.id.content);
    }
    */
  }


  //Register Account Activity
  //Activity is created in Luper Manifest
  public void registerMe(View view) {
    	Intent intent = new Intent(this, LuperRegisterActivity_.class);
    	startActivity(intent);
    }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    super.onCreateOptionsMenu(menu);
    getSupportMenuInflater().inflate(R.menu.luper_login, menu);
    return true;
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

  /**
   * Attempts to sign in or register the account specified by the login form. If
   * there are form errors (invalid email, missing fields, etc.), the errors are
   * presented and no actual login attempt is made.
   */
  public void attemptLogin() {
    if (mAuthTask != null) {
      return;
    }

    // Reset errors.
    mEmailView.setError(null);
    mPasswordView.setError(null);

    // Store values at the time of the login attempt.
    mEmail = mEmailView.getText().toString();
    mPassword = mPasswordView.getText().toString();

    boolean cancel = false;
    View focusView = null;

    // Check for a valid password.
    if (TextUtils.isEmpty(mPassword)) {
      mPasswordView.setError(getString(R.string.error_field_required));
      focusView = mPasswordView;
      cancel = true;
    } else if (mPassword.length() < 4) {
      mPasswordView.setError(getString(R.string.error_invalid_password));
      focusView = mPasswordView;
      cancel = true;
    }

    // Check for a valid email address.
    if (TextUtils.isEmpty(mEmail)) {
      mEmailView.setError(getString(R.string.error_field_required));
      focusView = mEmailView;
      cancel = true;
    } else if (!mEmail.contains("@")) {
      mEmailView.setError(getString(R.string.error_invalid_email));
      focusView = mEmailView;
      cancel = true;
    }

    if (cancel) {
      // There was an error; don't attempt login and focus the first
      // form field with an error.
      focusView.requestFocus();
    } else {
      // Show a progress spinner, and kick off a background task to
      // perform the user login attempt.
      mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
      showProgress(true);
      mAuthTask = new UserLoginTask();
      mAuthTask.execute((Void) null);
    }
  }

  /**
   * Shows the progress UI and hides the login form.
   */
  @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
  private void showProgress(final boolean show) {
    // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
    // for very easy animations. If available, use these APIs to fade-in
    // the progress spinner.
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
      int shortAnimTime = getResources().getInteger(
          android.R.integer.config_shortAnimTime);

      mLoginStatusView.setVisibility(View.VISIBLE);
      mLoginStatusView.animate().setDuration(shortAnimTime).alpha(show ? 1 : 0)
          .setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
              mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
          });

      mLoginFormView.setVisibility(View.VISIBLE);
      mLoginFormView.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1)
          .setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
              mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
          });
    } else {
      // The ViewPropertyAnimator APIs are not available, so simply show
      // and hide the relevant UI components.
      mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
      mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
    }
  }

  /**
   * Represents an asynchronous login/registration task used to authenticate the
   * user.
   */
  // TODO refactor this all into just a simple @Background-annotated method.
  public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
    @Override
    protected Boolean doInBackground(Void... params) {
      // TODO: attempt authentication against a network service.

      try {
        // Simulate network access.
        Thread.sleep(2000);
      } catch (InterruptedException e) {
        return false;
      }

      for (String credential : DUMMY_CREDENTIALS) {
        String[] pieces = credential.split(":");
        if (pieces[0].equals(mEmail)) {
          // Account exists, return true if the password matches.
          return pieces[1].equals(mPassword);
        }
      }

      // TODO: register the new account here.
      return true;
    }

    @Override
    protected void onPostExecute(final Boolean success) {
      mAuthTask = null;
      showProgress(false);

      if (success) {
        finish();
      } else {
        mPasswordView.setError(getString(R.string.error_incorrect_password));
        mPasswordView.requestFocus();
      }
    }

    @Override
    protected void onCancelled() {
      mAuthTask = null;
      showProgress(false);
    }
  }
}
