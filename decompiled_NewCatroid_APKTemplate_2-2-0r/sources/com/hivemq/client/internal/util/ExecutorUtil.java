package com.hivemq.client.internal.util;

import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;

/* JADX INFO: loaded from: classes10.dex */
public final class ExecutorUtil {
    public static boolean execute(Executor executor, Runnable runnable) {
        try {
            executor.execute(runnable);
            return true;
        } catch (RejectedExecutionException unused) {
            return false;
        }
    }

    private ExecutorUtil() {
    }
}
