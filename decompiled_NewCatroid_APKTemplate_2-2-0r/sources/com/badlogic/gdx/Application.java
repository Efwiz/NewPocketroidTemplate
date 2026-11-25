package com.badlogic.gdx;

import com.badlogic.gdx.utils.Clipboard;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/Application.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public interface Application {
    public static final int LOG_DEBUG = 3;
    public static final int LOG_ERROR = 1;
    public static final int LOG_INFO = 2;
    public static final int LOG_NONE = 0;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/Application$ApplicationType.class
      classes8.dex
     */
    public enum ApplicationType {
        Android,
        Desktop,
        HeadlessDesktop,
        Applet,
        WebGL,
        iOS
    }

    void addLifecycleListener(LifecycleListener lifecycleListener);

    void debug(String str, String str2);

    void debug(String str, String str2, Throwable th);

    void error(String str, String str2);

    void error(String str, String str2, Throwable th);

    void exit();

    ApplicationListener getApplicationListener();

    ApplicationLogger getApplicationLogger();

    Audio getAudio();

    Clipboard getClipboard();

    Files getFiles();

    Graphics getGraphics();

    Input getInput();

    long getJavaHeap();

    int getLogLevel();

    long getNativeHeap();

    Net getNet();

    Preferences getPreferences(String str);

    ApplicationType getType();

    int getVersion();

    void log(String str, String str2);

    void log(String str, String str2, Throwable th);

    void postRunnable(Runnable runnable);

    void removeLifecycleListener(LifecycleListener lifecycleListener);

    void setApplicationLogger(ApplicationLogger applicationLogger);

    void setLogLevel(int i);
}
