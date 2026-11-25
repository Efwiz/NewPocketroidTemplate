package com.badlogic.gdx.utils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/GdxNativesLoader.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class GdxNativesLoader {
    public static boolean disableNativesLoading = false;
    private static boolean nativesLoaded;

    public static synchronized void load() {
        if (nativesLoaded) {
            return;
        }
        if (disableNativesLoading) {
            return;
        }
        new SharedLibraryLoader().load("gdx");
        nativesLoaded = true;
    }
}
