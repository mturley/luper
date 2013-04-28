package com.teamluper.luper;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.googlecode.androidannotations.annotations.EActivity;

@EActivity
public class LuperHelp extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout bok= new LinearLayout(this);

        TextView derp = new TextView(this);
        derp.setText("dis is gonna be the help page");

        bok.setGravity(Gravity.CENTER);
        derp.setPadding(0,10,0,0);
        bok.addView(derp);

        setContentView(bok);

    }

}
