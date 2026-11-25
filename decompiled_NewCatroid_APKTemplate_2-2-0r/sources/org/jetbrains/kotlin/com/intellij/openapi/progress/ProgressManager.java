package org.jetbrains.kotlin.com.intellij.openapi.progress;

import java.util.HashSet;
import javax.swing.JComponent;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.application.CachedSingletonsRegistry;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Computable;
import org.jetbrains.kotlin.com.intellij.openapi.util.Ref;
import org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable;

/* JADX INFO: loaded from: classes3.dex */
public abstract class ProgressManager extends ProgressIndicatorProvider {
    static ProgressManager ourInstance = (ProgressManager) CachedSingletonsRegistry.markCachedField(ProgressManager.class);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
            default:
                str = "@NotNull method %s.%s must not return null";
                break;
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                i2 = 3;
                break;
            default:
                i2 = 2;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "process";
                break;
            case 2:
            case 3:
            case 4:
                objArr[0] = "text";
                break;
            case 5:
                objArr[0] = "task";
                break;
            case 6:
            case 7:
                objArr[0] = "indicator";
                break;
            case 8:
                objArr[0] = "original";
                break;
            default:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/progress/ProgressManager";
                break;
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/progress/ProgressManager";
                break;
            default:
                objArr[1] = "getInstance";
                break;
        }
        switch (i) {
            case 1:
                objArr[2] = "runProcess";
                break;
            case 2:
            case 4:
                objArr[2] = "progress";
                break;
            case 3:
                objArr[2] = "progress2";
                break;
            case 5:
                objArr[2] = "run";
                break;
            case 6:
                objArr[2] = "indicatorCanceled";
                break;
            case 7:
                objArr[2] = "canceled";
                break;
            case 8:
                objArr[2] = "assertNotCircular";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                throw new IllegalArgumentException(str2);
            default:
                throw new IllegalStateException(str2);
        }
    }

    public abstract <T, E extends Exception> T computeInNonCancelableSection(ThrowableComputable<T, E> throwableComputable) throws Exception;

    public abstract <T, E extends Throwable> T computePrioritized(ThrowableComputable<T, E> throwableComputable) throws Throwable;

    public abstract void executeNonCancelableSection(Runnable runnable);

    public abstract void executeProcessUnderProgress(Runnable runnable, ProgressIndicator progressIndicator) throws ProcessCanceledException;

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicatorProvider
    public abstract ProgressIndicator getProgressIndicator();

    public abstract boolean hasModalProgressIndicator();

    public abstract boolean hasProgressIndicator();

    public abstract boolean hasUnsafeProgressIndicator();

    protected void indicatorCanceled(ProgressIndicator progressIndicator) {
        if (progressIndicator == null) {
            $$$reportNull$$$0(6);
        }
    }

    public abstract boolean isInNonCancelableSection();

    public abstract void run(Task task);

    public abstract boolean runInReadActionWithWriteActionPriority(Runnable runnable, ProgressIndicator progressIndicator);

    public abstract void runProcess(Runnable runnable, ProgressIndicator progressIndicator) throws ProcessCanceledException;

    public abstract void runProcessWithProgressAsynchronously(Task.Backgroundable backgroundable, ProgressIndicator progressIndicator);

    @Deprecated
    public abstract void runProcessWithProgressAsynchronously(Project project, String str, Runnable runnable, Runnable runnable2, Runnable runnable3, PerformInBackgroundOption performInBackgroundOption);

    public abstract <T, E extends Exception> T runProcessWithProgressSynchronously(ThrowableComputable<T, E> throwableComputable, String str, boolean z, Project project) throws Exception;

    public abstract boolean runProcessWithProgressSynchronously(Runnable runnable, String str, boolean z, Project project);

    public abstract boolean runProcessWithProgressSynchronously(Runnable runnable, String str, boolean z, Project project, JComponent jComponent);

    public static ProgressManager getInstance() {
        ProgressManager progressManager = ourInstance;
        if (progressManager == null) {
            progressManager = (ProgressManager) ApplicationManager.getApplication().getService(ProgressManager.class);
            ourInstance = progressManager;
        }
        if (progressManager == null) {
            $$$reportNull$$$0(0);
        }
        return progressManager;
    }

    public final <T> T runProcess(final Computable<T> computable, ProgressIndicator progressIndicator) throws ProcessCanceledException {
        if (computable == null) {
            $$$reportNull$$$0(1);
        }
        final Ref ref = new Ref();
        runProcess(new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ref.set(computable.compute());
            }
        }, progressIndicator);
        return (T) ref.get();
    }

    public static void progress(String str) throws ProcessCanceledException {
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        progress(str, "");
    }

    public static void progress2(String str) throws ProcessCanceledException {
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        ProgressIndicator progressIndicator = getInstance().getProgressIndicator();
        if (progressIndicator != null) {
            progressIndicator.checkCanceled();
            progressIndicator.setText2(str);
        }
    }

    public static void progress(String str, String str2) throws ProcessCanceledException {
        if (str == null) {
            $$$reportNull$$$0(4);
        }
        ProgressIndicator progressIndicator = getInstance().getProgressIndicator();
        if (progressIndicator != null) {
            progressIndicator.checkCanceled();
            progressIndicator.setText(str);
            if (str2 == null) {
                str2 = "";
            }
            progressIndicator.setText2(str2);
        }
    }

    public <T, E extends Exception> T run(Task.WithResult<T, E> withResult) throws Exception {
        if (withResult == null) {
            $$$reportNull$$$0(5);
        }
        run((Task) withResult);
        return withResult.getResult();
    }

    public static void canceled(ProgressIndicator progressIndicator) {
        if (progressIndicator == null) {
            $$$reportNull$$$0(7);
        }
        getInstance().indicatorCanceled(progressIndicator);
    }

    public static void checkCanceled() throws ProcessCanceledException {
        ProgressManager progressManager = ourInstance;
        if (progressManager != null) {
            progressManager.doCheckCanceled();
        }
    }

    public static void assertNotCircular(ProgressIndicator progressIndicator) {
        if (progressIndicator == null) {
            $$$reportNull$$$0(8);
        }
        HashSet hashSet = null;
        while (progressIndicator instanceof WrappedProgressIndicator) {
            if (hashSet == null) {
                hashSet = new HashSet();
            }
            if (hashSet.add(progressIndicator)) {
                progressIndicator = ((WrappedProgressIndicator) progressIndicator).getOriginalProgressIndicator();
            } else {
                throw new IllegalArgumentException(progressIndicator + " wraps itself");
            }
        }
    }
}
