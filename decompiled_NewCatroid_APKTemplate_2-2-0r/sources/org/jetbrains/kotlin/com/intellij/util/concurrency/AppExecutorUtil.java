package org.jetbrains.kotlin.com.intellij.util.concurrency;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.util.Disposer;

/* JADX INFO: loaded from: classes3.dex */
public final class AppExecutorUtil {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 11:
            case 12:
            case 13:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
            case 10:
            default:
                str = "@NotNull method %s.%s must not return null";
                break;
        }
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 11:
            case 12:
            case 13:
                i2 = 3;
                break;
            case 10:
            default:
                i2 = 2;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 7:
            case 11:
                objArr[0] = "name";
                break;
            case 6:
            case 8:
            case 12:
                objArr[0] = "backendExecutor";
                break;
            case 9:
                objArr[0] = "parentDisposable";
                break;
            case 10:
            default:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/concurrency/AppExecutorUtil";
                break;
            case 13:
                objArr[0] = "comparator";
                break;
        }
        switch (i) {
            case 1:
                objArr[1] = "getAppExecutorService";
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 11:
            case 12:
            case 13:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/util/concurrency/AppExecutorUtil";
                break;
            case 10:
                objArr[1] = "createBoundedApplicationPoolExecutor";
                break;
            default:
                objArr[1] = "getAppScheduledExecutorService";
                break;
        }
        switch (i) {
            case 2:
                objArr[2] = "createBoundedScheduledExecutorService";
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                objArr[2] = "createBoundedApplicationPoolExecutor";
                break;
            case 11:
            case 12:
            case 13:
                objArr[2] = "createCustomPriorityQueueBoundedApplicationPoolExecutor";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 11:
            case 12:
            case 13:
                throw new IllegalArgumentException(str2);
            case 10:
            default:
                throw new IllegalStateException(str2);
        }
    }

    public static ScheduledExecutorService getAppScheduledExecutorService() {
        ScheduledExecutorService appScheduledExecutorService = AppScheduledExecutorService.getInstance();
        if (appScheduledExecutorService == null) {
            $$$reportNull$$$0(0);
        }
        return appScheduledExecutorService;
    }

    public static ExecutorService getAppExecutorService() {
        ExecutorService executorService = ((AppScheduledExecutorService) getAppScheduledExecutorService()).backendExecutorService;
        if (executorService == null) {
            $$$reportNull$$$0(1);
        }
        return executorService;
    }

    public static ExecutorService createBoundedApplicationPoolExecutor(String str, int i) {
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        return createBoundedApplicationPoolExecutor(str, getAppExecutorService(), i);
    }

    public static ExecutorService createBoundedApplicationPoolExecutor(String str, Executor executor, int i) {
        if (str == null) {
            $$$reportNull$$$0(5);
        }
        if (executor == null) {
            $$$reportNull$$$0(6);
        }
        return new BoundedTaskExecutor(str, executor, i, true);
    }

    public static ExecutorService createBoundedApplicationPoolExecutor(String str, Executor executor, int i, Disposable disposable) {
        if (str == null) {
            $$$reportNull$$$0(7);
        }
        if (executor == null) {
            $$$reportNull$$$0(8);
        }
        if (disposable == null) {
            $$$reportNull$$$0(9);
        }
        final BoundedTaskExecutor boundedTaskExecutor = new BoundedTaskExecutor(str, executor, i, true);
        Disposer.register(disposable, new Disposable() { // from class: org.jetbrains.kotlin.com.intellij.util.concurrency.AppExecutorUtil$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.openapi.Disposable
            public final void dispose() {
                boundedTaskExecutor.shutdownNow();
            }
        });
        return boundedTaskExecutor;
    }
}
