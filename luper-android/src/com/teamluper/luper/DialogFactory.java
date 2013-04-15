package com.teamluper.luper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.Toast;

public class DialogFactory {
  //toastMessage and alert are just helper methods to make it easier to
  // include a popup message in either dialog or toast form.
  // perhaps we'll need to move this to a common static class shared by all
  // our other classes?  if not, they'll need to be repeated in every file.
  public static void toastMessage(Context context, String message) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
  }

  public static void alert(Context context, String message) {
    alert(context, null, message);
  }
  
  public static void alert(Context context, String title, String message) {
    alert(context, title, message, null);
  }
  
  public static void alert(Context context, String title, String message,
      final Lambda.VoidCallback callback) {
    AlertDialog.Builder dialog = new AlertDialog.Builder(context)
    .setCancelable(false)
    .setMessage(message)
    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int which) {
        if(callback != null) callback.go();
      }
    });
    if(title != null) dialog.setTitle(title);
    dialog.show();
  }

  public static void prompt(Context context, String title, String message,
      final Lambda.StringCallback callback) {
    final EditText input = new EditText(context);
    new AlertDialog.Builder(context)
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
