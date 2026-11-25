package com.huawei.hms.mlsdk.t;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;

/* JADX INFO: compiled from: TaskExecutor.java */
/* JADX INFO: loaded from: classes10.dex */
public class j0 {
    private ExecutorService a = Executors.newCachedThreadPool(new b(null));

    /* JADX INFO: compiled from: TaskExecutor.java */
    private static final class b implements ThreadFactory {
        /* synthetic */ b(a aVar) {
        }

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable);
            thread.setName("ThreadFactory");
            thread.setUncaughtExceptionHandler(new c(null));
            return thread;
        }
    }

    /* JADX INFO: compiled from: TaskExecutor.java */
    private static final class c implements Thread.UncaughtExceptionHandler {
        /* synthetic */ c(a aVar) {
        }

        @Override // java.lang.Thread.UncaughtExceptionHandler
        public void uncaughtException(Thread thread, Throwable th) {
            i0.b("TaskExecutor", com.huawei.hms.mlsdk.t.a.a("UncaughtException Thread.name = ").append(thread.getName()).append(", e = ").append(th.getMessage()).toString());
        }
    }

    /* JADX INFO: compiled from: TaskExecutor.java */
    private static final class d {
        static j0 a = new j0(null);
    }

    /* synthetic */ j0(a aVar) {
    }

    public static j0 b() {
        return d.a;
    }

    public void a(Runnable runnable) {
        if (this.a.isShutdown()) {
            this.a = Executors.newCachedThreadPool(new b(null));
        }
        try {
            this.a.execute(runnable);
        } catch (RejectedExecutionException e) {
            i0.b("TaskExecutor", com.huawei.hms.mlsdk.t.a.a("ThreadPoolException:").append(e.getMessage()).toString());
        }
    }

    public void a() {
        this.a.shutdownNow();
    }
}
