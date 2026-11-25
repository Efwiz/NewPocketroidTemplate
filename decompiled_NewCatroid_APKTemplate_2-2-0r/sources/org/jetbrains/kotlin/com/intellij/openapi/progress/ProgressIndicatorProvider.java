package org.jetbrains.kotlin.com.intellij.openapi.progress;

/* JADX INFO: loaded from: classes3.dex */
public abstract class ProgressIndicatorProvider {
    protected abstract void doCheckCanceled() throws ProcessCanceledException;

    public abstract ProgressIndicator getProgressIndicator();

    public static ProgressIndicatorProvider getInstance() {
        return ProgressManager.getInstance();
    }

    public static ProgressIndicator getGlobalProgressIndicator() {
        ProgressManager progressManager = ProgressManager.ourInstance;
        if (progressManager == null) {
            return null;
        }
        return progressManager.getProgressIndicator();
    }

    public static void checkCanceled() throws ProcessCanceledException {
        ProgressManager.checkCanceled();
    }
}
