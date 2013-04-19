package com.teamluper.luper;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.googlecode.androidannotations.annotations.EFragment;

@EFragment
public class TabLoginFragment extends Fragment {

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

  private SQLiteDataSource dataSource;

  @Override
  public View onCreateView(LayoutInflater infl, ViewGroup vg, Bundle state) {
    if(vg == null) return null;
    View v = infl.inflate(R.layout.tab_login_layout, vg, false);
    Activity a = getActivity();

    // set up the SQLite database access.
    dataSource = ((LuperLoginActivity)getActivity()).getDataSource();
    if(!dataSource.isOpen()) dataSource.open();

    // Set up the login form.
    mEmail = a.getIntent().getStringExtra(EXTRA_EMAIL);
    mEmailView = (EditText) v.findViewById(R.id.email);
    mEmailView.setText(mEmail);

    mPasswordView = (EditText) v.findViewById(R.id.password);
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

    mLoginFormView = v.findViewById(R.id.login_form);
    mLoginStatusView = v.findViewById(R.id.login_status);
    mLoginStatusMessageView = (TextView) v.findViewById(R.id.login_status_message);

    v.findViewById(R.id.login_button).setOnClickListener(
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
    return v;
  }

  /**
   * Attempts to sign in or register the account specified by the login form. If
   * there are form errors (invalid email, missing fields, etc.), the errors are
   * presented and no actual login attempt is made.
   */
  public void attemptLogin() {
    // TODO check if existing REST login call is still in progress, fail if so.

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
      mLoginStatusMessageView.setText(R.string.progress_logging_in);
      showProgress(true);
      DialogFactory.alert(getActivity(), "TODO","This spinner will spin forever.  Still need to implement login.");
      // TODO start a new login task (JUST CALL A @BACKGROUND REST thing)
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
}
