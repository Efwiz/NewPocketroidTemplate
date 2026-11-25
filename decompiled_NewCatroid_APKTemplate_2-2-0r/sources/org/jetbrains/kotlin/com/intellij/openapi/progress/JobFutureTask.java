package org.jetbrains.kotlin.com.intellij.openapi.progress;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;
import kotlinx.coroutines.CompletableDeferred;
import kotlinx.coroutines.CompletableDeferredKt;
import org.jetbrains.kotlin.com.intellij.openapi.application.AccessToken;

/* JADX INFO: loaded from: classes3.dex */
public final class JobFutureTask<V> extends FutureTask<V> {
    private final CompletableDeferred<V> myJob;

    /* JADX WARN: Code duplicated, block: B:19:0x002d  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 5 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 5 ? 3 : 2];
        if (i == 1 || i == 2) {
            objArr[0] = "callable";
        } else if (i == 3) {
            objArr[0] = "deferred";
        } else if (i == 4) {
            objArr[0] = "callable";
        } else if (i != 5) {
            objArr[0] = "job";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/progress/JobFutureTask";
        }
        if (i != 5) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/progress/JobFutureTask";
        } else {
            objArr[1] = "jobCallable";
        }
        if (i == 2) {
            objArr[2] = "jobRunnableFuture";
        } else if (i == 3 || i == 4) {
            objArr[2] = "jobCallable";
        } else if (i != 5) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 5) {
            throw new IllegalStateException(str2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private JobFutureTask(CompletableDeferred<V> completableDeferred, Callable<V> callable) {
        super(jobCallable(completableDeferred, callable));
        if (completableDeferred == null) {
            $$$reportNull$$$0(0);
        }
        if (callable == null) {
            $$$reportNull$$$0(1);
        }
        this.myJob = completableDeferred;
    }

    @Override // java.util.concurrent.FutureTask, java.util.concurrent.Future
    public boolean cancel(boolean z) {
        this.myJob.cancel((CancellationException) null);
        return super.cancel(z);
    }

    public static <V> RunnableFuture<V> jobRunnableFuture(Callable<V> callable) {
        if (callable == null) {
            $$$reportNull$$$0(2);
        }
        return new JobFutureTask(CompletableDeferredKt.CompletableDeferred(Cancellation.currentJob()), callable);
    }

    private static <V> Callable<V> jobCallable(final CompletableDeferred<V> completableDeferred, final Callable<? extends V> callable) {
        if (completableDeferred == null) {
            $$$reportNull$$$0(3);
        }
        if (callable == null) {
            $$$reportNull$$$0(4);
        }
        return new Callable() { // from class: org.jetbrains.kotlin.com.intellij.openapi.progress.JobFutureTask$$ExternalSyntheticLambda0
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return JobFutureTask.lambda$jobCallable$0(completableDeferred, callable);
            }
        };
    }

    static /* synthetic */ Object lambda$jobCallable$0(CompletableDeferred completableDeferred, Callable callable) throws Exception {
        try {
            AccessToken accessTokenWithJob = Cancellation.withJob(completableDeferred);
            try {
                Object objCall = callable.call();
                completableDeferred.complete(objCall);
                if (accessTokenWithJob != null) {
                    accessTokenWithJob.close();
                }
                return objCall;
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
        } catch (JobCanceledException unused) {
            throw completableDeferred.getCancellationException();
        } catch (Throwable th3) {
            completableDeferred.completeExceptionally(th3);
            throw th3;
        }
    }
}
