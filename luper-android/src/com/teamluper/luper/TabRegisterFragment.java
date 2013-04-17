package com.teamluper.luper;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.UiThread;

@EFragment
public class TabRegisterFragment extends Fragment {
  @Override
  public View onCreateView(LayoutInflater infl, ViewGroup vg, Bundle state) {
    if(vg == null) return null;
    return infl.inflate(R.layout.tab_register_layout, vg, false);
  }

  @Background
  public void processRegistration(View view) {
    alert("Registration","TODO: actually process it");
    // TODO stub here
  }

  @UiThread
  public void alert(String title, String message) {
    DialogFactory.alert(getActivity(), title, message);
  }

}
