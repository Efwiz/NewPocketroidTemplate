package androidx.arch.core.executor;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core-runtime-2.2.0.aar:classes.jar:androidx/arch/core/executor/TaskExecutor.class
  classes.dex
 */
/* JADX INFO: loaded from: classes.dex */
public abstract class TaskExecutor {
    public abstract void executeOnDiskIO(Runnable runnable);

    public abstract boolean isMainThread();

    public abstract void postToMainThread(Runnable runnable);

    public void executeOnMainThread(Runnable runnable) {
        if (isMainThread()) {
            runnable.run();
        } else {
            postToMainThread(runnable);
        }
    }
}
