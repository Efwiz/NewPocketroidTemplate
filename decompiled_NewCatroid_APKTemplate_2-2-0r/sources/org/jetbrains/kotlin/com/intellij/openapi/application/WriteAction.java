package org.jetbrains.kotlin.com.intellij.openapi.application;

import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable;
import org.jetbrains.kotlin.com.intellij.util.ThrowableRunnable;

/* JADX INFO: loaded from: classes3.dex */
public abstract class WriteAction<T> extends BaseActionRunnable<T> {
    private static final Logger LOG = Logger.getInstance((Class<?>) WriteAction.class);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 4 || i == 5 || i == 6 || i == 7) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 3 || i == 4 || i == 5 || i == 6 || i == 7) ? 3 : 2];
        if (i == 3 || i == 4 || i == 5 || i == 6 || i == 7) {
            objArr[0] = "action";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/application/WriteAction";
        }
        switch (i) {
            case 2:
                objArr[1] = "start";
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/application/WriteAction";
                break;
            default:
                objArr[1] = "execute";
                break;
        }
        if (i == 3) {
            objArr[2] = "run";
        } else if (i == 4) {
            objArr[2] = "compute";
        } else if (i == 5) {
            objArr[2] = "runAndWait";
        } else if (i == 6 || i == 7) {
            objArr[2] = "computeAndWait";
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 4 && i != 5 && i != 6 && i != 7) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    @Deprecated
    public WriteAction() {
    }

    public static <E extends Throwable> void run(final ThrowableRunnable<E> throwableRunnable) throws Throwable {
        if (throwableRunnable == null) {
            $$$reportNull$$$0(3);
        }
        ApplicationManager.getApplication().runWriteAction(new ThrowableComputable() { // from class: org.jetbrains.kotlin.com.intellij.openapi.application.WriteAction$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable
            public final Object compute() {
                return WriteAction.lambda$run$1(throwableRunnable);
            }
        });
    }

    static /* synthetic */ Void lambda$run$1(ThrowableRunnable throwableRunnable) throws Throwable {
        throwableRunnable.run();
        return null;
    }
}
