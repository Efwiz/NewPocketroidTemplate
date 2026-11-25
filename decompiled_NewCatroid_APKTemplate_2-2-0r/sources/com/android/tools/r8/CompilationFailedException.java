package com.android.tools.r8;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class CompilationFailedException extends Exception {
    private final boolean a;

    CompilationFailedException(String str, Throwable th, boolean z) {
        super(str, th);
        this.a = z;
    }

    public boolean wasCancelled() {
        return this.a;
    }

    public CompilationFailedException(String str) {
        super(str);
        this.a = false;
    }
}
