package org.jetbrains.kotlin.com.intellij.openapi.application.ex;

import javax.swing.JComponent;
import org.jetbrains.kotlin.com.intellij.openapi.application.Application;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable;

/* JADX INFO: loaded from: classes3.dex */
public interface ApplicationEx extends Application {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
                objArr[0] = "process";
                break;
            case 2:
                objArr[0] = "progressTitle";
                break;
            case 3:
                objArr[0] = "runnable";
                break;
            case 4:
            case 6:
                objArr[0] = "title";
                break;
            case 5:
            case 7:
            case 8:
            case 9:
                objArr[0] = "action";
                break;
            default:
                objArr[0] = "invokedClassFqn";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/application/ex/ApplicationEx";
        switch (i) {
            case 1:
            case 2:
                objArr[2] = "runProcessWithProgressSynchronously";
                break;
            case 3:
                objArr[2] = "executeByImpatientReader";
                break;
            case 4:
            case 5:
                objArr[2] = "runWriteActionWithCancellableProgressInDispatchThread";
                break;
            case 6:
            case 7:
                objArr[2] = "runWriteActionWithNonCancellableProgressInDispatchThread";
                break;
            case 8:
                objArr[2] = "runUnlockingIntendedWrite";
                break;
            case 9:
                objArr[2] = "runIntendedWriteActionOnCurrentThread";
                break;
            default:
                objArr[2] = "acquireWriteIntentLock";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    boolean runProcessWithProgressSynchronously(Runnable runnable, String str, boolean z, boolean z2, Project project, JComponent jComponent, String str2);

    default <T, E extends Throwable> T runUnlockingIntendedWrite(ThrowableComputable<T, E> throwableComputable) throws Throwable {
        if (throwableComputable == null) {
            $$$reportNull$$$0(8);
        }
        return throwableComputable.compute();
    }

    default void runIntendedWriteActionOnCurrentThread(Runnable runnable) {
        if (runnable == null) {
            $$$reportNull$$$0(9);
        }
        runnable.run();
    }
}
