
package com.example.samsung.p1181_customwidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import java.util.Arrays;

/**
 * Created by samsung on 12.05.2017.
 */

public class MyWidget extends AppWidgetProvider {

    private String message;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public SharedPreferences getPreferences() {
        return this.preferences;
    }

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

        preferences = context.getSharedPreferences(
                ConfigActivity.WIDGET_PREF,
                Context.MODE_PRIVATE
        );
        for (int id : appWidgetIds) {
            updateWidget(context, appWidgetManager, preferences, id);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        message = context.getString(R.string.my_widget)
                + " " + context.getString(R.string.on_deleted)
                + " " + Arrays.toString(appWidgetIds);
        Messager.sendToAllRecipients(context, message);

        // Удаляем Preferences
        editor = context.getSharedPreferences(
                ConfigActivity.WIDGET_PREF,
                Context.MODE_PRIVATE).edit();
        for (int widgetID : appWidgetIds) {
            editor.remove(ConfigActivity.WIDGET_TEXT + widgetID);
            editor.remove(ConfigActivity.WIDGET_COLOR + widgetID);
        }
        editor.commit();    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        message = context.getString(R.string.my_widget)
                + " " + context.getString(R.string.on_disabled);
        Messager.sendToAllRecipients(context, message);
    }

    static void updateWidget(
            final Context context,
            final AppWidgetManager appWidgetManager,
            final SharedPreferences preferences,
            final int widgetID) {
        String message = context.getString(R.string.my_widget)
                + " " + context.getString(R.string.update_widget)
                + ": " + widgetID;
        Messager.sendToAllRecipients(context, message);
        //Чтение параметров Preferences
        String widgetText = preferences.getString(ConfigActivity.WIDGET_TEXT + widgetID, null);

        if (widgetText == null) {
            return;
        }

        int widgetColor = preferences.getInt(ConfigActivity.WIDGET_COLOR + widgetID, 0);
        //Настройка внешнего вида виджета
        RemoteViews widgeView = new RemoteViews(context.getPackageName(), R.layout.widget);
        widgeView.setTextViewText(R.id.tv, widgetText);
        widgeView.setInt(R.id.tv, "setBackgroundColor", widgetColor);
        //Обновление виджета
        appWidgetManager.updateAppWidget(widgetID, widgeView);
    }
}
