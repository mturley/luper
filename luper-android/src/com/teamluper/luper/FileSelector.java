package com.teamluper.luper;
 
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
 
//import com.teamluper.LuperApp.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
 
/**
 * @author kantsh
 *      This code can be used for development purposes and is free to use.
 *         Completed on Tuesday,April 24,2012
 *         mailto "kant.shashi@hotmail.com"
 */
 
public class FileSelector extends Activity {
 ArrayList<String> str = new ArrayList<String>();
 
 // Check if the first level of the directory structure is the one showing
 private Boolean firstLvl = true;
 
 private static final String TAG = "F_PATH";
 
 private Item[] fileList;
 private File path = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/LuperApp/");
 private String chosenFile;
 private static final int DIALOG_LOAD_FILE = 1000;
 
 ListAdapter adapter;
 
 @Override
 protected void onCreate(Bundle savedInstanceState) {
  // TODO Auto-generated method stub
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_file_selector);
 
  loadFileList();
 
  showDialog(DIALOG_LOAD_FILE);
  Log.d(TAG, path.getAbsolutePath());
 
 }
 
 private void loadFileList() {
  try {
   path.mkdirs();
  } catch (SecurityException e) {
   Log.e(TAG, "unable to write on the sd card ");
  }
 
  // Checks whether path exists
  if (path.exists()) {
   // create a filter that helps to look for files directories and
   // hidden files
   FilenameFilter filter = new FilenameFilter() {
    @Override
    public boolean accept(File dir, String filename) {
     File sel = new File(dir, filename);
     // Filters based on whether the file is hidden or not
     return (sel.isFile() || sel.isDirectory())
       && !sel.isHidden();
    }
   };
 
   String[] fList = path.list(filter);
   fileList = new Item[fList.length];
   for (int i = 0; i < fList.length; i++) {
    fileList[i] = new Item(fList[i]); // by
                  // default
                  // icon
 
    // Convert into file path
    File sel = new File(path, fList[i]);
 
    // Set drawables
//    if (sel.isDirectory()) {
//     fileList[i].icon = R.drawable.directory_icon;
//     Log.d("DIRECTORY", fileList[i].file);
//    }
// 
//    // pdf format
//    else if (sel.getName().endsWith(".pdf")) {
//     fileList[i].icon = R.drawable.pdf_file;
//     Log.d("FILE", fileList[i].file);
//    }
// 
//    // image formats
//    else if (sel.getName().endsWith(".jpeg")
//      || sel.getName().endsWith(".jpg")) {
//     fileList[i].icon = R.drawable.jpg_file;
//     Log.d("FILE", fileList[i].file);
//    } else if (sel.getName().endsWith(".gif")) {
//     fileList[i].icon = R.drawable.gif;
//     Log.d("FILE", fileList[i].file);
//    }
// 
//    // audio and some common formats
//    else if (sel.getName().endsWith(".mp3")
//      || sel.getName().endsWith(".MP3")) {
//     fileList[i].icon = R.drawable.mp3_file;
//     Log.d("FILE", fileList[i].file);
//    } else if (sel.getName().endsWith(".mpg")
//      || sel.getName().endsWith(".mpeg")) {
//     fileList[i].icon = R.drawable.mpeg_file;
//     Log.d("FILE", fileList[i].file);
//    } else if (sel.getName().endsWith(".avi")) {
//     fileList[i].icon = R.drawable.avi_file;
//     Log.d("FILE", fileList[i].file);
//    }
//    // all other formats for video
//    else if (sel.getName().endsWith(".mov")
//      || sel.getName().endsWith(".asf")
//      || sel.getName().endsWith(".mp4")
//      || sel.getName().endsWith(".3gp")
//      || sel.getName().endsWith(".flv")
//      || sel.getName().endsWith(".rm")
//      || sel.getName().endsWith(".wmv")
//      || sel.getName().endsWith(".mkv")) {
//     fileList[i].icon = R.drawable.video_icon;
//     Log.d("FILE", fileList[i].file);
//    }
// 
//    // archives file formats
//    else if (sel.getName().endsWith(".zip")
//      || sel.getName().endsWith(".rar")
//      || sel.getName().endsWith(".jar")
//      || sel.getName().endsWith(".gzip")) {
//     fileList[i].icon = R.drawable.zip_icon;
//     Log.d("FILE", fileList[i].file);
//    }
// 
//    else {
//     Log.d("FILE", fileList[i].file);
//    }
   }
 
   // show the up symbol only when u r not at the top of the path
//   if (!firstLvl) {
//    Item temp[] = new Item[fileList.length + 1];
//    for (int i = 0; i &lt; fileList.length; i++) {
//     temp[i + 1] = fileList[i];
//    }
//    temp[0] = new Item("Up", R.drawable.directory_up);
//    fileList = temp;
//   }
  } else {
   Log.e(TAG, "path does not exist");
  }
 
  adapter = new ArrayAdapter<Item>(this,
    android.R.layout.select_dialog_item, android.R.id.text1,
    fileList) {
   @Override
   public View getView(int position, View convertView, ViewGroup parent) {
    // creates view
    View view = super.getView(position, convertView, parent);
    TextView textView = (TextView) view
      .findViewById(android.R.id.text1);
 
    // put the image on the text view
//    textView.setCompoundDrawablesWithIntrinsicBounds(
//      fileList[position].icon, 0, 0, 0);
 
    // add margin between image and text (support various screen
    // densities)
    int dp5 = (int) (5 * getResources().getDisplayMetrics().density + 0.5f);
    textView.setCompoundDrawablePadding(dp5);
 
    return view;
   }
  };
 
 }
 
 private class Item {
  public String file;
  //public int icon;
 
  public Item(String file) {
   this.file = file;
   //this.icon = icon;
  }
 
  @Override
  public String toString() {
   return file;
  }
 }
 
 @Override
 protected Dialog onCreateDialog(int id) {
  Dialog dialog = null;
  AlertDialog.Builder builder = new Builder(this);
 
  if (fileList == null) {
   Log.e(TAG, "No files loaded");
   dialog = builder.create();
   return dialog;
  }
 
  switch (id) {
  case DIALOG_LOAD_FILE:
   builder.setTitle("Browse your file");
   builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialog, int which) {
     chosenFile = fileList[which].file;
     File sel = new File(path + "/" + chosenFile);
     if (sel.isDirectory()) {
      firstLvl = false;
 
      // Adds chosen directory to list
      str.add(chosenFile);
      fileList = null;
      path = new File(sel + "");
 
      loadFileList();
      // refresh the list being shown
      removeDialog(DIALOG_LOAD_FILE);
      showDialog(DIALOG_LOAD_FILE);
 
      Log.d(TAG, path.getAbsolutePath());
     }
 
     // Checks if 'up' was clicked
     else if (chosenFile.equalsIgnoreCase("up") && !sel.exists()) {
 
      // present directory removed from list
      String s = str.remove(str.size() - 1);
 
      // path modified to exclude present directory
      path = new File(path.toString().substring(0,
        path.toString().lastIndexOf(s)));
      fileList = null;
 
      // if there are no more directories in the list, then
      // its the first level
      if (str.isEmpty()) {
       firstLvl = true;
      }
      loadFileList();
 
      removeDialog(DIALOG_LOAD_FILE);
      showDialog(DIALOG_LOAD_FILE);
      Log.d(TAG, path.getAbsolutePath());
 
     }
     // File picked.......supposed to return fileName chosen by
     // the User
     else {
      // Perform action with file picked
      Intent intent = getIntent();
      String returnVal = path.getPath() + "/" + chosenFile;
      intent.putExtra("fileChosen", returnVal);
      setResult(RESULT_OK, intent);
      // for purpose of testing
      // Toast.makeText(getBaseContext(),
      // returnVal,Toast.LENGTH_LONG).show();
      finish();
     }
 
    }
   });
   break;
  }
  dialog = builder.show();
  return dialog;
 }
}