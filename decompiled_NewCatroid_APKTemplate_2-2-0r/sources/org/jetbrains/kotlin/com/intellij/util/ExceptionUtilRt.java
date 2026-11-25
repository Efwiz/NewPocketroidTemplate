package org.jetbrains.kotlin.com.intellij.util;

/* JADX INFO: loaded from: classes3.dex */
public class ExceptionUtilRt {
    public static void rethrowUnchecked(Throwable th) {
        if (th instanceof Error) {
            throw ((Error) th);
        }
        if (th instanceof RuntimeException) {
            throw ((RuntimeException) th);
        }
    }
}
