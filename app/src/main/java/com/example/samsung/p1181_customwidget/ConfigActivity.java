package com.example.samsung.p1181_customwidget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import static android.content.SharedPreferences.*;

public class ConfigActivity extends Activity {

    private int widgetID = AppWidgetManager.INVALID_APPWIDGET_ID, color;
    private Intent resultValue;
    private Bundle extras;
    private String message;
    private EditText etText;
    private SharedPreferences preferences;
    private Editor  editor;
    private AppWidgetManager manager;

    public final static String WIDGET_PREF = "widget_pref",
            WIDGET_TEXT = "widget_text_",
            WIDGET_COLOR = "widget_color_";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        message = getString(R.string.config_activity) + " " + getString(R.string.on_create);
        Messager.sendToAllRecipients(this, message);

        //Получение ID конфигурируемого виджета
        extras = getIntent().getExtras();
        if (extras != null) {
            widgetID = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        //Проверка корректности ID виджета
        if (widgetID == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }
        //Формирование Intent'а ответа
        resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetID);

        //Отрацательный ответ
        setResult(RESULT_CANCELED, resultValue);
        setContentView(R.layout.config);
    }

    public void onClickBtn(View view) {

        int selRbColor = ((RadioGroup) findViewById(R.id.rgColor)).getCheckedRadioButtonId();

        if (color == 0) {
            color = Color.RED;
        }

        switch (selRbColor) {

            case R.id.rbRad :
                color = Color.RED;
                break;
            case R.id.rbGreen :
                color = Color.GREEN;
                break;
            case R.id.rbBlue :
                color = Color.BLUE;
                break;
            default:
                break;
        }
        etText = (EditText) findViewById(R.id.etText);
        //Запись значений на экране в Preferences
        preferences = getSharedPreferences(WIDGET_PREF, MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(WIDGET_TEXT + widgetID, etText.getText().toString());
        editor.putInt(WIDGET_COLOR + widgetID, color);
        editor.commit();
        //Принудительный вызов обновления виджета
        manager = AppWidgetManager.getInstance(this);
        MyWidget.updateWidget(this, manager, preferences, widgetID);
        //Положительный ответ
        setResult(RESULT_OK, resultValue);

        message = getString(R.string.config_activity) + " " + getString(R.string.finish);
        Messager.sendToAllRecipients(this, message);

        finish();
    }
}
