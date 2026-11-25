package org.jetbrains.kotlin.com.intellij.openapi.util;

/* JADX INFO: loaded from: classes3.dex */
public final class EmptyRunnable implements Runnable {
    public static final Runnable INSTANCE = new EmptyRunnable();

    @Override // java.lang.Runnable
    public void run() {
    }

    public static Runnable getInstance() {
        return INSTANCE;
    }

    public String toString() {
        return "EmptyRunnable";
    }
}
