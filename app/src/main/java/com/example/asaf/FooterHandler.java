package com.example.asaf;

import android.app.Activity;
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
            Intent suggestionsIntent = new Intent(context, MySuggestionsActivity.class);
            context.startActivity(suggestionsIntent);
            if (context instanceof MySuggestionsActivity) {
                ((Activity) context).finish();
            }
        }
        if (buttonId==R.id.btn_my_rides) {
            Intent ridesIntent = new Intent(context, MyRidesActivity.class);
            context.startActivity(ridesIntent);
            if (context instanceof MyRidesActivity) {
                ((Activity) context).finish();
            }
        }
        if (buttonId==R.id.btn_my_chats) {
            Intent chatsIntent = new Intent(context, MyChatsActivity.class);
            context.startActivity(chatsIntent);
            if (context instanceof MyChatsActivity) {
                ((Activity) context).finish();
            }
        }
    }
}

