package org.catrobat.catroid.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Process;
import android.preference.PreferenceManager;
import android.util.Log;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.arguments.K2JsArgumentConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/BaseExceptionHandler.class
  classes14.dex
 */
/* JADX INFO: compiled from: BaseExceptionHandler.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\b\u0016\u0018\u0000 \u000e2\u00020\u0001:\u0001\u000eB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0004J\u0018\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/catrobat/catroid/ui/BaseExceptionHandler;", "Ljava/lang/Thread$UncaughtExceptionHandler;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "preferences", "Landroid/content/SharedPreferences;", "exit", "", "uncaughtException", "thread", "Ljava/lang/Thread;", K2JsArgumentConstants.RUNTIME_DIAGNOSTIC_EXCEPTION, "", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class BaseExceptionHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "BaseExceptionHandler";
    private final SharedPreferences preferences;

    public BaseExceptionHandler(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        Intrinsics.checkNotNullExpressionValue(defaultSharedPreferences, "getDefaultSharedPreferences(...)");
        this.preferences = defaultSharedPreferences;
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(Thread thread, Throwable exception) {
        Intrinsics.checkNotNullParameter(thread, "thread");
        Intrinsics.checkNotNullParameter(exception, "exception");
        Log.e(TAG, "uncaughtException: ", exception);
        this.preferences.edit().putBoolean(BaseActivityKt.RECOVERED_FROM_CRASH, true).apply();
        exit();
    }

    protected final void exit() {
        Process.killProcess(Process.myPid());
        System.exit(10);
        throw new RuntimeException("System.exit returned normally, while it was supposed to halt JVM.");
    }
}
