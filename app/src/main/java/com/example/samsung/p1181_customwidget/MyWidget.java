
package com.example.samsung.p1181_customwidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;

import java.util.Arrays;

/**
 * Created by samsung on 12.05.2017.
 */

public class MyWidget extends AppWidgetProvider {

    private String message;

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        message = context.getString(R.string.my_widget)
                + " " + context.getString(R.string.on_enabled);
        Messager.sendToAllRecipients(context, message);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        message = context.getString(R.string.my_widget)
                + " " + context.getString(R.string.on_update)
                + " " + Arrays.toString(appWidgetIds);
        Messager.sendToAllRecipients(context, message);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        message = context.getString(R.string.my_widget)
                + " " + context.getString(R.string.on_deleted)
                + " " + Arrays.toString(appWidgetIds);
        Messager.sendToAllRecipients(context, message);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        message = context.getString(R.string.my_widget)
                + " " + context.getString(R.string.on_disabled);
        Messager.sendToAllRecipients(context, message);
    }
}
