package com.example.asaf;

import android.content.Context;
import android.content.Intent;
import android.view.View;

public class FooterHandler implements View.OnClickListener {

    private Context context;

    public FooterHandler(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        int buttonId = v.getId();

        if (buttonId==R.id.btn_my_suggestions) {
            Intent chatsIntent = new Intent(context, MySuggestionsActivity.class);
            context.startActivity(chatsIntent);
        }
        if (buttonId==R.id.btn_my_rides) {
            Intent chatsIntent = new Intent(context, MyRidesActivity.class);
            context.startActivity(chatsIntent);
        }
        if (buttonId==R.id.btn_my_chats) {
            Intent chatsIntent = new Intent(context, MyChatsActivity.class);
            context.startActivity(chatsIntent);
        }
    }
}

