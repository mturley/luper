package com.teamluper.luper;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.googlecode.androidannotations.annotations.EFragment;

@EFragment
public class TabHomeFragment extends Fragment {
  @Override
  public View onCreateView(LayoutInflater infl, ViewGroup vg, Bundle state) {
    if(vg == null) return null;
    return (RelativeLayout)infl.inflate(R.layout.tab_home_layout, vg, false);
  }
}
