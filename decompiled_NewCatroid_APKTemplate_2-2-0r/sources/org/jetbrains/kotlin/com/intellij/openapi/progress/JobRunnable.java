package org.jetbrains.kotlin.com.intellij.openapi.progress;

import java.util.concurrent.CancellationException;
import kotlinx.coroutines.CompletableJob;
import kotlinx.coroutines.JobKt;
import org.jetbrains.kotlin.com.intellij.openapi.application.AccessToken;

/* JADX INFO: loaded from: classes3.dex */
public final class JobRunnable implements Runnable {
    private final CompletableJob myJob;
    private final Runnable myRunnable;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 2 ? 3 : 2];
        if (i != 2) {
            objArr[0] = "runnable";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/progress/JobRunnable";
        }
        if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/progress/JobRunnable";
        } else {
            objArr[1] = "jobRunnable";
        }
        if (i == 1) {
            objArr[2] = "jobRunnable";
        } else if (i != 2) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 2) {
            throw new IllegalStateException(str2);
        }
    }

    private JobRunnable(Runnable runnable) {
        if (runnable == null) {
            $$$reportNull$$$0(0);
        }
        this.myJob = JobKt.Job(Cancellation.currentJob());
        this.myRunnable = runnable;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            AccessToken accessTokenWithJob = Cancellation.withJob(this.myJob);
            try {
                this.myRunnable.run();
                this.myJob.complete();
                if (accessTokenWithJob != null) {
                    accessTokenWithJob.close();
                }
            } catch (Throwable th) {
                if (accessTokenWithJob != null) {
                    try {
                        accessTokenWithJob.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (CancellationException e) {
            this.myJob.completeExceptionally(e);
        } catch (JobCanceledException e2) {
            if (!this.myJob.isCancelled()) {
                throw new IllegalStateException("JobCanceledException must be thrown by ProgressManager.checkCanceled()", e2);
            }
        } catch (Throwable th3) {
            this.myJob.completeExceptionally(th3);
            throw th3;
        }
    }

    public static Runnable jobRunnable(Runnable runnable) {
        if (runnable == null) {
            $$$reportNull$$$0(1);
        }
        if (!(runnable instanceof JobFutureTask)) {
            return new JobRunnable(runnable);
        }
        if (runnable == null) {
            $$$reportNull$$$0(2);
        }
        return runnable;
    }
}
