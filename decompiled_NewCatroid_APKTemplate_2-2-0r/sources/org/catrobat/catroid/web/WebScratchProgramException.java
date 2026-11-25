package org.catrobat.catroid.web;

import android.util.Log;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/web/WebScratchProgramException.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class WebScratchProgramException extends Exception {
    public static final int ERROR_PROGRAM_NOT_ACCESSIBLE = 1001;
    private static final String TAG = "WebConnectionException";
    private static final long serialVersionUID = 1;
    private final String message;
    private final int statusCode;

    public WebScratchProgramException(int statusCode, String message) {
        super(message);
        message = message == null ? "Unknown Error, no exception message given." : message;
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        Log.d(TAG, "Error #" + this.statusCode + ": " + this.message);
        return this.message;
    }
}
