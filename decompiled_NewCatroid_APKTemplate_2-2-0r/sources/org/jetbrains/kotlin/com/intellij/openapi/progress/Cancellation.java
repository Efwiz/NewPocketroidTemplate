package org.jetbrains.kotlin.com.intellij.openapi.progress;

import kotlinx.coroutines.Job;
import org.jetbrains.kotlin.com.intellij.openapi.application.AccessToken;

/* JADX INFO: loaded from: classes3.dex */
public final class Cancellation {
    private static final ThreadLocal<Job> ourJob = new ThreadLocal<>();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 2 || i == 4) {
            objArr[0] = "action";
        } else {
            objArr[0] = "job";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/progress/Cancellation";
        objArr[2] = "withJob";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public static Job currentJob() {
        return ourJob.get();
    }

    public static boolean isCancelled() {
        Job job = ourJob.get();
        return job != null && job.isCancelled();
    }

    public static void checkCancelled() {
        if (isCancelled()) {
            throw new JobCanceledException();
        }
    }

    public static AccessToken withJob(final Job job) {
        if (job == null) {
            $$$reportNull$$$0(0);
        }
        ThreadLocal<Job> threadLocal = ourJob;
        final Job job2 = threadLocal.get();
        threadLocal.set(job);
        return new AccessToken() { // from class: org.jetbrains.kotlin.com.intellij.openapi.progress.Cancellation.1
            @Override // org.jetbrains.kotlin.com.intellij.openapi.application.AccessToken
            public void finish() {
                Job job3 = (Job) Cancellation.ourJob.get();
                Cancellation.ourJob.set(job2);
                if (job3 != job) {
                    throw new IllegalStateException("Job was not reset correctly");
                }
            }
        };
    }
}
