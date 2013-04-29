package com.teamluper.luper;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EFragment;

@EFragment
public class TabHomeFragment extends Fragment {
  SQLiteDataSource dataSource = null;
  User activeUser = null;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    dataSource = ((LuperMainActivity) getActivity()).getDataSource();
    activeUser = dataSource.getActiveUser();
  }

  @Override
  public View onCreateView(LayoutInflater infl, ViewGroup vg, Bundle state) {
    if(vg == null) return null;
    View v = infl.inflate(R.layout.tab_home_layout, vg, false);
    if(activeUser != null) {
      ((TextView) v.findViewById(R.id.mainWelcome)).setText("Welcome to Lüper, "+activeUser.getUsername()+"!");
    }
    return v;
  }

}
