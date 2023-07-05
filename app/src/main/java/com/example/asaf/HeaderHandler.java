package com.example.asaf;

import android.app.Activity;
import android.content.Context;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


public class HeaderHandler {

    private Context context;

    public HeaderHandler(Context context) {
        this.context = context;
    }



    public void openSideMenu() {
        DrawerLayout drawerLayout = ((Activity) context).findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END);
        } else {
            drawerLayout.openDrawer(GravityCompat.END);
        }
    }

}
