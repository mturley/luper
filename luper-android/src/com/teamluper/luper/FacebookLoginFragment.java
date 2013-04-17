package com.teamluper.luper;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FacebookLoginFragment extends Fragment{
	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container ,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.facebook, container , false);
		
		return view;
	}

}
