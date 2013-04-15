package com.teamluper.luper;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.actionbarsherlock.app.SherlockActivity;

public class PopupTestActivity extends SherlockActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button button = new Button(this);
        button.setText("Project_Name");
        button.setOnClickListener(listener);
        registerForContextMenu(button);

        setContentView(button);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        public void onClick(View v) {
            openContextMenu(v);
        }
    };

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.contextmenu, menu);
        menu.setHeaderTitle("Watcha want to do?");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()) {
        case R.id.menu_edit:
            return true;
        case R.id.menu_share:
            return true;
        case R.id.menu_delete:
            return true;
        }
        return super.onContextItemSelected(item);
    }

}
