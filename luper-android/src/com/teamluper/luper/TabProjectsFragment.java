package com.teamluper.luper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import com.googlecode.androidannotations.annotations.EFragment;

import java.util.List;

@EFragment
public class TabProjectsFragment extends Fragment {
  @Override
  public View onCreateView(LayoutInflater infl, ViewGroup vg, Bundle state) {
    if(vg == null) return null;

    final LuperMainActivity main = (LuperMainActivity) getActivity();

    View view = infl.inflate(R.layout.tab_projects_layout, vg, false);

    List<Sequence> allSequences = main.dataSource.getAllSequences();
    ArrayAdapter<Sequence> adapter = new ArrayAdapter<Sequence>(
      (Context) main,
      android.R.layout.simple_list_item_1,
      allSequences);
    ListView projectsListView = (ListView) view.findViewById(R.id.projectsListView);
    projectsListView.setAdapter(adapter);
    projectsListView.setEmptyView(view.findViewById(R.id.projectsListEmptyText));
    projectsListView.setOnItemClickListener(
        new OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view,
              int position, long id) {
            Sequence s = (Sequence) parent.getItemAtPosition(position);
            main.launchProjectEditor(s.getId());
          }
        });
     projectsListView.setOnItemLongClickListener(new OnItemLongClickListener() {
    		 public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                     int pos, long id) {
                 showListDialog();
                 return true;
             }

        
    		 });
    return (RelativeLayout) view;
  }
  
  public void showListDialog(){
	    final CharSequence[] items = {"Open", "Delete", "Share", "Export as mp3", "Cancel"};
	    new AlertDialog.Builder(getActivity())
	        .setTitle("Project Options")
	        .setItems(items, new DialogInterface.OnClickListener() {
	          public void onClick(DialogInterface dialog, int item) {
	            if (items[item].equals("Open")) {
	              //do something
	            }
	            if (items[item].equals("Delete")) {
	            //do something
	            } 
	            if (items[item].equals("Share")) {
		            //do something
		            }
	            else if (items[item].equals("Export as mp3")) {
	            } 
	            else if (items[item].equals("Cancel")) {
	              // does nothing and will never do anything
	            }
	          }
	        }).show();
	  }
  
}