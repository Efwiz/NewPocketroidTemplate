package org.jetbrains.kotlin.com.intellij.openapi.application;

import java.util.concurrent.Callable;
import org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable;
import org.jetbrains.kotlin.com.intellij.util.ThrowableRunnable;

/* JADX INFO: loaded from: classes3.dex */
public abstract class ReadAction<T> extends BaseActionRunnable<T> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2 || i == 3 || i == 4) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 1 || i == 2 || i == 3 || i == 4) ? 3 : 2];
        if (i == 1 || i == 2) {
            objArr[0] = "action";
        } else if (i == 3 || i == 4) {
            objArr[0] = "task";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/application/ReadAction";
        }
        if (i == 1 || i == 2 || i == 3 || i == 4) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/application/ReadAction";
        } else if (i != 5) {
            objArr[1] = "execute";
        } else {
            objArr[1] = "nonBlocking";
        }
        if (i == 1) {
            objArr[2] = "run";
        } else if (i == 2) {
            objArr[2] = "compute";
        } else if (i == 3 || i == 4) {
            objArr[2] = "nonBlocking";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2 && i != 3 && i != 4) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    public static <E extends Throwable> void run(final ThrowableRunnable<E> throwableRunnable) throws Throwable {
        if (throwableRunnable == null) {
            $$$reportNull$$$0(1);
        }
        compute(new ThrowableComputable() { // from class: org.jetbrains.kotlin.com.intellij.openapi.application.ReadAction$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable
            public final Object compute() {
                return ReadAction.lambda$run$1(throwableRunnable);
            }
        });
    }

    static /* synthetic */ Object lambda$run$1(ThrowableRunnable throwableRunnable) throws Throwable {
        throwableRunnable.run();
        return null;
    }

    public static <T, E extends Throwable> T compute(ThrowableComputable<T, E> throwableComputable) throws Throwable {
        if (throwableComputable == null) {
            $$$reportNull$$$0(2);
        }
        return (T) ApplicationManager.getApplication().runReadAction(throwableComputable);
    }

    public static <T> NonBlockingReadAction<T> nonBlocking(Callable<? extends T> callable) {
        if (callable == null) {
            $$$reportNull$$$0(4);
        }
        NonBlockingReadAction<T> nonBlockingReadActionBuildNonBlockingReadAction = AsyncExecutionService.getService().buildNonBlockingReadAction(callable);
        if (nonBlockingReadActionBuildNonBlockingReadAction == null) {
            $$$reportNull$$$0(5);
        }
        return nonBlockingReadActionBuildNonBlockingReadAction;
    }
}
