package org.jetbrains.kotlin.com.intellij.util;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes3.dex */
final class SameThreadExecutorService extends AbstractExecutorService {
    private volatile boolean isTerminated;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/util/SameThreadExecutorService";
        } else if (i != 2) {
            objArr[0] = "theUnit";
        } else {
            objArr[0] = "command";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/SameThreadExecutorService";
        } else {
            objArr[1] = "shutdownNow";
        }
        if (i != 1) {
            if (i != 2) {
                objArr[2] = "awaitTermination";
            } else {
                objArr[2] = "execute";
            }
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    SameThreadExecutorService() {
    }

    @Override // java.util.concurrent.ExecutorService
    public void shutdown() {
        this.isTerminated = true;
    }

    @Override // java.util.concurrent.ExecutorService
    public boolean isShutdown() {
        return this.isTerminated;
    }

    @Override // java.util.concurrent.ExecutorService
    public boolean isTerminated() {
        return this.isTerminated;
    }

    @Override // java.util.concurrent.ExecutorService
    public boolean awaitTermination(long j, TimeUnit timeUnit) {
        if (timeUnit == null) {
            $$$reportNull$$$0(0);
        }
        if (isShutdown()) {
            return true;
        }
        throw new IllegalStateException("Must call shutdown*() before awaitTermination()");
    }

    @Override // java.util.concurrent.ExecutorService
    public List<Runnable> shutdownNow() {
        shutdown();
        List<Runnable> listEmptyList = Collections.emptyList();
        if (listEmptyList == null) {
            $$$reportNull$$$0(1);
        }
        return listEmptyList;
    }

    @Override // java.util.concurrent.Executor
    public void execute(Runnable runnable) {
        if (runnable == null) {
            $$$reportNull$$$0(2);
        }
        if (isShutdown()) {
            throw new IllegalStateException("Must not call execute() after pool is shut down");
        }
        runnable.run();
    }
}
