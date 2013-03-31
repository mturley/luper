// LuperApp.java
// -------------
 
// NOTE: ActionBarSherlock is a polyfill providing the new Android 4.2 ActionBar
//       functionality to all versions of android.  To compile this project
//       properly, the actionbarsherlock project must also be in your
//       workspace and/or build path in addition to the Luper project.
//       See README.md on github for more details.

package com.teamluper.luper;

// imports from the core android API
import java.io.File;
import java.util.List;

import android.os.Environment;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

// imports for ActionBarSherlock dependency
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

// imports for AndroidAnnotations dependency
import com.googlecode.androidannotations.annotations.AfterViews;
// @AfterViews is never used, but don't remove as we may want to use it eventually
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.ViewById;
import com.googlecode.androidannotations.annotations.rest.RestService;

// imports for the SpringFramework REST dependency
import org.springframework.web.client.HttpClientErrorException;
import com.teamluper.luper.rest.LuperRestClient;

// @EActivity = "Enhanced Activity", which turns on AndroidAnnotations features
@EActivity
public class LuperApp extends SherlockFragmentActivity {

  @RestService
  LuperRestClient rest;
  
  ViewPager mViewPager;
  TabsAdapter mTabsAdapter;
  
  // Additional local variables
  AccountManager am;
  LuperDataSource dataSource;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    
    // set up the ViewPager, which we will use in conjunction with tabs.
    // this makes it possible to swipe left and right between the tabs.
    mViewPager = new ViewPager(this);
    mViewPager.setId(R.id.tabcontentpager);
    setContentView(mViewPager);
    
    dataSource = new LuperDataSource(this);
    dataSource.open();
    
    
    final ActionBar bar = getSupportActionBar();
    bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS); // Gives us Tabs!
    
    // FIXME this is slowing down the app launch dramatically.  Perhaps do it in background?
    //Creates a folder for Luper and associated clips and projects
    File nfile=new File(Environment.getExternalStorageDirectory()+"/LuperApp/Clips");
    File mfile=new File(Environment.getExternalStorageDirectory()+"/LuperApp/Projects");
    nfile.mkdir();
    mfile.mkdir();
    
    
    // now we set up the TabsAdapter, which is a special class borrowed from Google.
    // TabsAdapter.java takes care of all the guts of the Tab interactions, and
    // links it with our ViewPager for us.  The code below is all we need to
    // add some fragment content as tabs in the ActionBar!
    mTabsAdapter = new TabsAdapter(this, mViewPager);
    mTabsAdapter.addTab(bar.newTab().setText(""+"Home"),
        TabHomeFragment_.class, null);
    mTabsAdapter.addTab(bar.newTab().setText(""+"Projects"),
        TabProjectsFragment_.class, null);
    mTabsAdapter.addTab(bar.newTab().setText(""+"Friends"),
        TabFriendsFragment_.class, null);
    
    //create a directory to save in
    File testdir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/LuperApp/");
    testdir.mkdirs();
  }
  
  @Override
  protected void onResume() {
    super.onResume();
    testAccounts();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inf = getSupportMenuInflater();
    inf.inflate(R.menu.activity_main, menu);
    
    // because one of the action items is a custom view,
    // we need the next few lines to force it to use onOptionsItemSelected
    // when it's clicked.
    final MenuItem item = menu.findItem(R.id.menu_new_project);
    item.getActionView().setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        onOptionsItemSelected(item);
      }
    });
    
    return super.onCreateOptionsMenu(menu);
  }
  
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if(item.getItemId() == R.id.menu_new_project) {
      promptDialog("New Project",
        "Please type a name for your project.  You can change it later.",
        new StringCallback() {
          public void go(String value) {
            newProject(value);
          }
        }
      );
    }
    if(item.getItemId() == R.id.menu_settings) {
      Intent intent = new Intent(this, LuperSettings_.class);
      startActivity(intent);
    }
    return true;
  }
  
  //method to navigate to the audiorecorder activity
  public void startRecording(View view) {
  	Intent intent = new Intent(this, AudioRecorderTest_.class);
  	startActivity(intent);
  }
  
  // this will be removed, it's an example of how we'll access the EC2 server.
  @Background
  public void testRestAPI(View view) {
    try {
      String t = rest.getTestString();
      alertDialog("Database Connection Test PASS!\n" +
          "Request: GET http://teamluper.com/api/test\n" +
          "Response: '"+t+"'");
    } catch(HttpClientErrorException e) {
      alertDialog("Database Connection Test FAIL!\n" + e.toString());
    }
  }
  
  public void dropAllData(View view) {
    dataSource.dropAllData();
    alertDialog("Done!");
  }
  
  public void newProject(String title) {
    Sequence newSequence = dataSource.createSequence(title);
    ListView lv = (ListView) findViewById(R.id.projectsListView);
    ArrayAdapter a = (ArrayAdapter) lv.getAdapter();
    a.add(newSequence);
  }
  
  // Just here until it gets moved to Project Tab
  @Background
  public void exampleProject(View view) {
		  Intent intent = new Intent(this, ExampleProject.class);
		  startActivity(intent);
  }
  
  
  @Background
  public void start_testmenu(View view) {
		  Intent intent = new Intent(this, PopupTest.class);
		  startActivity(intent);
  }
  
  // code: http://www.java2s.com/Code/Android/2D-Graphics/DrawwithCanvas.htm
  @Background
  public void start_testcanvas(View view) {
		  Intent intent = new Intent(this, CanvasTest.class);
		  startActivity(intent);
  }
  
  
  // this will be removed too, it's checking the google account that the
  // device's user is already logged in with.  We'll likely ditch this in favor
  // of a Facebook-based login solution.
  @Background
  void testAccounts() {
    if(am == null) am = AccountManager.get(this);
    Account[] accounts = am.getAccountsByType("com.google");
    System.out.println("== LUPER ACCOUNTS TESTING ==  found "+accounts.length+" accounts");
    for(int i=0; i<accounts.length; i++) {
      System.out.println(accounts[i].toString());
    }
  }
  
  // toastMessage and alertDialog are just helper methods to make it easier to
  // include a popup message in either dialog or toast form.
  // perhaps we'll need to move this to a common static class shared by all
  // our other classes?  if not, they'll need to be repeated in every file.
  @UiThread
  void toastMessage(String message) {
    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
  }
  
  @UiThread
  void alertDialog(String message) {
    new AlertDialog.Builder(this)
    .setCancelable(false)
    .setMessage(message)
    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int which) {
        // do nothing
      }
    })
    .show();
  }
  
  @UiThread
  void promptDialog(String title, String message, final StringCallback callback) {
    final EditText input = new EditText(this);
    new AlertDialog.Builder(this)
      .setTitle(title)
      .setMessage(message)
      .setView(input)
      .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int whichButton) {
              String value = input.getText().toString();
              callback.go(value);
          }
      }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int whichButton) {
              // Do nothing.
          }
      }).show();
  }
}
