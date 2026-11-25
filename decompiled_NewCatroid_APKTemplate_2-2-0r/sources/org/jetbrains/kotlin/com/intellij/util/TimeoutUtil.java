package org.jetbrains.kotlin.com.intellij.util;

/* JADX INFO: loaded from: classes3.dex */
public final class TimeoutUtil {
    public static void sleep(long j) {
        try {
            Thread.sleep(j);
        } catch (InterruptedException unused) {
        }
    }
}
