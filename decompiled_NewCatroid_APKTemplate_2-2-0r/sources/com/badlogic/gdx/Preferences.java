package com.badlogic.gdx;

import java.util.Map;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/Preferences.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public interface Preferences {
    void clear();

    boolean contains(String str);

    void flush();

    Map<String, ?> get();

    boolean getBoolean(String str);

    boolean getBoolean(String str, boolean z);

    float getFloat(String str);

    float getFloat(String str, float f);

    int getInteger(String str);

    int getInteger(String str, int i);

    long getLong(String str);

    long getLong(String str, long j);

    String getString(String str);

    String getString(String str, String str2);

    Preferences put(Map<String, ?> map);

    Preferences putBoolean(String str, boolean z);

    Preferences putFloat(String str, float f);

    Preferences putInteger(String str, int i);

    Preferences putLong(String str, long j);

    Preferences putString(String str, String str2);

    void remove(String str);
}
