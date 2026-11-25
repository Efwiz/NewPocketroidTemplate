package org.jetbrains.kotlin.com.intellij.openapi.application.ex;

import java.util.concurrent.atomic.AtomicReference;
import javax.swing.SwingUtilities;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.application.EdtReplacementThread;
import org.jetbrains.kotlin.com.intellij.openapi.application.ModalityState;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.util.ExceptionUtil;
import org.jetbrains.kotlin.com.intellij.util.concurrency.Semaphore;
import org.jetbrains.kotlin.com.intellij.util.ui.EdtInvocationManager;

/* JADX INFO: loaded from: classes3.dex */
public final class ApplicationUtil {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
                objArr[0] = "callable";
                break;
            case 2:
            case 4:
                objArr[0] = "indicator";
                break;
            case 3:
                objArr[0] = "future";
                break;
            case 5:
                objArr[0] = "runnable";
                break;
            case 6:
            case 9:
                objArr[0] = "thread";
                break;
            case 7:
            case 10:
                objArr[0] = "modalityState";
                break;
            case 8:
            case 11:
                objArr[0] = "r";
                break;
            default:
                objArr[0] = "computable";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/application/ex/ApplicationUtil";
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
                objArr[2] = "runWithCheckCanceled";
                break;
            case 5:
                objArr[2] = "showDialogAfterWriteAction";
                break;
            case 6:
            case 7:
            case 8:
                objArr[2] = "invokeLaterSomewhere";
                break;
            case 9:
            case 10:
            case 11:
                objArr[2] = "invokeAndWaitSomewhere";
                break;
            default:
                objArr[2] = "tryRunReadAction";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.com.intellij.openapi.application.ex.ApplicationUtil$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$intellij$openapi$application$EdtReplacementThread;

        static {
            int[] iArr = new int[EdtReplacementThread.values().length];
            $SwitchMap$com$intellij$openapi$application$EdtReplacementThread = iArr;
            try {
                iArr[EdtReplacementThread.EDT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$intellij$openapi$application$EdtReplacementThread[EdtReplacementThread.WT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$intellij$openapi$application$EdtReplacementThread[EdtReplacementThread.EDT_WITH_IW.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public static void invokeLaterSomewhere(EdtReplacementThread edtReplacementThread, ModalityState modalityState, Runnable runnable) {
        if (edtReplacementThread == null) {
            $$$reportNull$$$0(6);
        }
        if (modalityState == null) {
            $$$reportNull$$$0(7);
        }
        if (runnable == null) {
            $$$reportNull$$$0(8);
        }
        int i = AnonymousClass1.$SwitchMap$com$intellij$openapi$application$EdtReplacementThread[edtReplacementThread.ordinal()];
        if (i == 1) {
            SwingUtilities.invokeLater(runnable);
        } else if (i == 2) {
            ApplicationManager.getApplication().invokeLaterOnWriteThread(runnable, modalityState);
        } else {
            if (i != 3) {
                return;
            }
            ApplicationManager.getApplication().invokeLater(runnable, modalityState);
        }
    }

    public static void invokeAndWaitSomewhere(EdtReplacementThread edtReplacementThread, ModalityState modalityState, final Runnable runnable) {
        if (edtReplacementThread == null) {
            $$$reportNull$$$0(9);
        }
        if (modalityState == null) {
            $$$reportNull$$$0(10);
        }
        if (runnable == null) {
            $$$reportNull$$$0(11);
        }
        int i = AnonymousClass1.$SwitchMap$com$intellij$openapi$application$EdtReplacementThread[edtReplacementThread.ordinal()];
        if (i == 1) {
            if (!SwingUtilities.isEventDispatchThread() && ApplicationManager.getApplication().isWriteThread()) {
                Logger.getInstance((Class<?>) ApplicationUtil.class).error("Can't invokeAndWait from WT to EDT: probably leads to deadlock");
            }
            EdtInvocationManager.invokeAndWaitIfNeeded(runnable);
            return;
        }
        if (i != 2) {
            if (i != 3) {
                return;
            }
            if (!SwingUtilities.isEventDispatchThread() && ApplicationManager.getApplication().isWriteThread()) {
                Logger.getInstance((Class<?>) ApplicationUtil.class).error("Can't invokeAndWait from WT to EDT: probably leads to deadlock");
            }
            ApplicationManager.getApplication().invokeAndWait(runnable, modalityState);
            return;
        }
        if (ApplicationManager.getApplication().isWriteThread()) {
            runnable.run();
            return;
        }
        if (SwingUtilities.isEventDispatchThread()) {
            Logger.getInstance((Class<?>) ApplicationUtil.class).error("Can't invokeAndWait from EDT to WT");
            return;
        }
        final Semaphore semaphore = new Semaphore(1);
        final AtomicReference atomicReference = new AtomicReference();
        ApplicationManager.getApplication().invokeLaterOnWriteThread(new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.openapi.application.ex.ApplicationUtil$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ApplicationUtil.lambda$invokeAndWaitSomewhere$3(runnable, atomicReference, semaphore);
            }
        }, modalityState);
        semaphore.waitFor();
        if (atomicReference.get() != null) {
            ExceptionUtil.rethrow((Throwable) atomicReference.get());
        }
    }

    static /* synthetic */ void lambda$invokeAndWaitSomewhere$3(Runnable runnable, AtomicReference atomicReference, Semaphore semaphore) {
        try {
            runnable.run();
        } catch (Throwable th) {
            try {
                atomicReference.set(th);
            } finally {
                semaphore.up();
            }
        }
    }
}
