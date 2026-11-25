package com.android.tools.r8;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class M {
    public static CompilationFailedException a() {
        return new CompilationFailedException("Compilation failed to complete", null, false);
    }

    public static CompilationFailedException a(Throwable th) {
        return new CompilationFailedException("Compilation failed to complete", th, false);
    }

    public static CompilationFailedException a(String str, Throwable th, boolean z) {
        return new CompilationFailedException(str, th, z);
    }

    public static CompilationFailedException a(String str) {
        return new CompilationFailedException(str, null, false);
    }
}
