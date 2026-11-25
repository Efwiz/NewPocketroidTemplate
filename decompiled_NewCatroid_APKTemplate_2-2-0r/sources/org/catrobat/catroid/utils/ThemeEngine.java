package org.catrobat.catroid.utils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/ThemeEngine.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ThemeEngine {
    public static final String THEME_PREFS_NAME = "live_theme_settings";

    public static void applyTheme(Activity activity) {
        Log.d("ThemeEngine", "--- Применение темы для " + activity.getClass().getSimpleName() + " ---");
        ViewGroup viewGroup = (ViewGroup) activity.getWindow().getDecorView();
        if (viewGroup == null) {
            Log.d("ThemeEngine", "Root view не найден, выход.");
            return;
        }
        SharedPreferences sharedPreferences = activity.getSharedPreferences(THEME_PREFS_NAME, 0);
        Log.d("ThemeEngine", "Читаем из файла: live_theme_settings");
        themeView(viewGroup, sharedPreferences);
    }

    private static void themeView(View view, SharedPreferences themePrefs) {
        if (view.getTag() instanceof String) {
            String str = (String) view.getTag();
            if (str.endsWith("_background")) {
                Log.d("ThemeEngine", "Найден View с тегом: " + str);
                String string = themePrefs.getString(str, null);
                Log.d("ThemeEngine", "Для ключа '" + str + "' найдено значение (String): " + string);
                if (string != null) {
                    try {
                        int color = Color.parseColor(string);
                        Log.d("ThemeEngine", "Цвет успешно распарсен: " + color);
                        if (str.endsWith("_background")) {
                            view.setBackgroundColor(color);
                        } else if (str.endsWith("_text") && (view instanceof TextView)) {
                            ((TextView) view).setTextColor(color);
                        }
                    } catch (IllegalArgumentException unused) {
                        Log.e("ThemeEngine", "Неверный формат цвета в настройках: " + string);
                    }
                } else {
                    Log.d("ThemeEngine", "Значение не найдено или пустое, цвет не применяется.");
                }
            }
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                themeView(viewGroup.getChildAt(i), themePrefs);
            }
        }
    }
}
