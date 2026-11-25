package androidx.concurrent.futures;

import java.util.concurrent.Executor;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/concurrent-futures-1.1.0.jar:androidx/concurrent/futures/DirectExecutor.class
  classes.dex
 */
/* JADX INFO: loaded from: classes.dex */
public enum DirectExecutor implements Executor {
    INSTANCE;

    @Override // java.util.concurrent.Executor
    public void execute(Runnable runnable) {
        runnable.run();
    }

    @Override // java.lang.Enum
    public String toString() {
        return "DirectExecutor";
    }
}
