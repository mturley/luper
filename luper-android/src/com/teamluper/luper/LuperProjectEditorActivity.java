package com.teamluper.luper;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.UiThread;

import java.util.ArrayList;


@EActivity
public class LuperProjectEditorActivity extends SherlockActivity {
  LuperMainActivity main;
  SQLiteDataSource dataSource = null;
	Sequence sequence = null;

	// TODO these will be moved to within Sequence, and accessed with
	// sequence.getClips() and sequence.getTracks(), etc.
	ArrayList<Clip> clips = new ArrayList<Clip>();
	ArrayList<Track> tracks = new ArrayList<Track>();

	@Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        long ID = getIntent().getLongExtra("selectedProjectId", -1);
        if(ID == -1) {
          DialogFactory.alert(this,"ERROR","No project ID found!  Aborting.",
            new Lambda.VoidCallback() {
              public void go() {
                finish();
              }
            });
          return;
        }

        main = LuperMainActivity.getInstance();
        dataSource = main.getDataSource();
        sequence = dataSource.getSequenceById(ID);
        // TODO use the data within the sequence object to render the clips.
        DialogFactory.alert(this, "Successfully Loaded Project ID #"+ID+
          "!  The title is '"+sequence.getTitle()+"'");

        loadDataInBackground();
        loadAudioInBackground();

        final ActionBar bar = getSupportActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS); // Gives us Tabs!

        /* TESTS FUNCTIONALLITY OF THE TRACK VIEW RENDERING */
          Clip clip1 = new Clip(); clip1.begin = 0; clip1.end = 500; clip1.duration = 500;
          Clip clip2 = new Clip(); clip2.begin = 250; clip2.end = 1000; clip2.duration = 650;
          Clip clip3 = new Clip(); clip3.begin = 100; clip3.end = 600; clip3.duration = 500;

        LinearLayout base = new LinearLayout(this);
        base.setBackgroundColor(Color.parseColor("#e2dfd8"));

        base.setOrientation(LinearLayout.VERTICAL);

        TrackView track1 = new TrackView(this);

//        ImageButton addClipButton = new ImageButton(this);
//        addClipButton.setImageResource(R.drawable.add);
//
//        track1.addView(addClipButton);

//        RelativeLayout track1 = new RelativeLayout(this);
//        RelativeLayout track2 = new RelativeLayout(this);
//        RelativeLayout track3 = new RelativeLayout(this);
//
//        ColorChipButton chip1 = new ColorChipButton(this, clip1);
//        ColorChipButton chip2 = new ColorChipButton(this, clip2);
//        ColorChipButton chip3 = new ColorChipButton(this, clip3);
//
//        track1.addView(chip1);
//        track2.addView(chip2);
//        track3.addView(chip3);
//
//        track1.setPadding(0, 10, 0, 5);
//        track2.setPadding(0, 10, 0, 5);
//        track3.setPadding(0, 10, 0, 5);
//
//        chip1.setBackgroundColor(Color.GRAY);
//        chip2.setBackgroundColor(Color.RED);
//        chip3.setBackgroundColor(Color.WHITE);

        TextView timelinetxt = new TextView(this);
        timelinetxt.setText("_____|__________________|___________________|___________________|___________________|_________\n");

        base.addView(track1,
                new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
//        base.addView(track2,
//                new RelativeLayout.LayoutParams(
//                        ViewGroup.LayoutParams.WRAP_CONTENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT));
//        base.addView(track3,
//                new RelativeLayout.LayoutParams(
//                        ViewGroup.LayoutParams.WRAP_CONTENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT));

        setContentView(base);

    }

	// #Creates the Actionbar
	@Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inf = getSupportMenuInflater();
    inf.inflate(R.menu.editor_bar, menu);
    return super.onCreateOptionsMenu(menu);
  }
	@Override
  public boolean onOptionsItemSelected(MenuItem item) {
	  boolean incomplete = true;
    if(item.getItemId() == R.id.editor_play) {
      // TODO
    }
    if(item.getItemId() == R.id.editor_edit_clip) {
      // TODO
    }
    if(item.getItemId() == R.id.editor_add_clip) {
      // TODO
    }
    if(item.getItemId() == R.id.editor_delete_clip) {
      // TODO
    }
    if(item.getItemId() == R.id.editor_volume) {
      // TODO
    }
    if(item.getItemId() == R.id.editor_help) {
      // TODO
    }
    if(incomplete) DialogFactory.alert(this,"Incomplete Feature",
        "That button hasn't been hooked up to anything.");
    return true;
  }

  @Background
  public void loadDataInBackground() {
    if(sequence == null) return;
    sequence.loadAllTrackData();
    alert("All Track Data is Loaded!");
  }

  @Background
  public void loadAudioInBackground() {
    if(sequence == null) return;
    sequence.loadAllTrackAudio();
    alert("All Audio Data is Loaded!");
  }

  @UiThread
  public void alert(String message) {
    DialogFactory.alert(this, message);
  }
}