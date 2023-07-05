package com.example.asaf;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;


public class HeaderHandler {

    private Context context;
    private FireBaseModel fireBaseModel;

    public HeaderHandler(Context context) {
        this.context = context;
        fireBaseModel = new FireBaseModel(context);
    }



    public void openSideMenu() {
        DrawerLayout drawerLayout = ((Activity) context).findViewById(R.id.drawer_layout);
        NavigationView navigationView = ((Activity) context).findViewById(R.id.nav_view);

        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END);
        } else {
            drawerLayout.openDrawer(GravityCompat.END);
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                // Handle the item click based on the item id
                int itemId = menuItem.getItemId();

                if (itemId == R.id.btn_home) {
                    Intent intent = new Intent(context, HomeActivity.class);
                    context.startActivity(intent);
                    if (context instanceof HomeActivity) {
                        ((Activity) context).finish();
                    }
                }

                else if (itemId == R.id.btn_ranks) {

                }

                else if (itemId == R.id.btn_payments) {

                }

                else if (itemId == R.id.btn_logout) {
                    fireBaseModel.logOut();
                    Intent intent = new Intent(context, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear activity stack
                    context.startActivity(intent);
                    ((Activity) context).finish();
                }
                return true;
            }
        });
    }


}
