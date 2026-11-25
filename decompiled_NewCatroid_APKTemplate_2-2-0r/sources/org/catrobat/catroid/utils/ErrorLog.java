package org.catrobat.catroid.utils;

import android.os.Environment;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import org.catrobat.catroid.stage.StageActivity;
import org.jetbrains.kotlin.cli.common.arguments.K2JsArgumentConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/ErrorLog.class
  classes14.dex
 */
/* JADX INFO: compiled from: ErrorLog.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006¨\u0006\u0007"}, d2 = {"Lorg/catrobat/catroid/utils/ErrorLog;", "", "()V", K2JsArgumentConstants.RUNTIME_DIAGNOSTIC_LOG, "", "error1", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ErrorLog {
    public static final ErrorLog INSTANCE = new ErrorLog();

    private ErrorLog() {
    }

    public final void log(String error1) {
        if (error1 == null) {
            error1 = "**пустая ошибка (попробуйте еще раз)**";
        }
        StageActivity.messageHandler.obtainMessage(3, new ArrayList(CollectionsKt.listOf("Произошла ошибка. Лог сохранен в NewCatroidError.txt"))).sendToTarget();
        try {
            if (Intrinsics.areEqual(Environment.getExternalStorageState(), "mounted")) {
                File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                if (!externalStoragePublicDirectory.exists()) {
                    externalStoragePublicDirectory.mkdirs();
                }
                FileOutputStream fileOutputStream = new FileOutputStream(new File(externalStoragePublicDirectory, "NewCatroidError.txt"), false);
                try {
                    byte[] bytes = error1.getBytes(Charsets.UTF_8);
                    Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
                    fileOutputStream.write(bytes);
                    Unit unit = Unit.INSTANCE;
                    CloseableKt.closeFinally(fileOutputStream, null);
                } catch (Throwable th) {
                    try {
                        throw th;
                    } catch (Throwable th2) {
                        CloseableKt.closeFinally(fileOutputStream, th);
                        throw th2;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
}
