package org.jetbrains.kotlin.com.intellij.mock;

import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import java.awt.Component;
import java.lang.reflect.Modifier;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.application.AccessToken;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationListener;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.application.ModalityInvokator;
import org.jetbrains.kotlin.com.intellij.openapi.application.ModalityState;
import org.jetbrains.kotlin.com.intellij.openapi.application.ex.ApplicationEx;
import org.jetbrains.kotlin.com.intellij.openapi.application.impl.AnyModalityState;
import org.jetbrains.kotlin.com.intellij.openapi.components.Service;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.Extensions;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProcessCanceledException;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Computable;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable;
import org.jetbrains.kotlin.com.intellij.util.concurrency.AppExecutorUtil;

/* JADX INFO: loaded from: classes3.dex */
public class MockApplication extends MockComponentManager implements ApplicationEx {
    public static int INSTANCES_CREATED;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 7 || i == 9 || i == 29 || i == 22 || i == 23 || i == 41 || i == 42 || i == 44 || i == 45) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 7 || i == 9 || i == 29 || i == 22 || i == 23 || i == 41 || i == 42 || i == 44 || i == 45) ? 2 : 3];
        switch (i) {
            case 2:
            case 7:
            case 9:
            case 22:
            case 23:
            case 29:
            case 41:
            case 42:
            case 44:
            case 45:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/mock/MockApplication";
                break;
            case 3:
            case 4:
            case 5:
                objArr[0] = "serviceClass";
                break;
            case 6:
            case 8:
            case 10:
            case 11:
            case 13:
            case 16:
            case 19:
                objArr[0] = "action";
                break;
            case 12:
            case 14:
                objArr[0] = "modal";
                break;
            case 15:
            case 31:
            case 34:
                objArr[0] = "expired";
                break;
            case 17:
            case 18:
            case 20:
            case 21:
                objArr[0] = "computation";
                break;
            case 24:
                objArr[0] = "actionClass";
                break;
            case 25:
            case 26:
            case 28:
                objArr[0] = ServiceSpecificExtraArgs.CastExtraArgs.LISTENER;
                break;
            case 27:
                objArr[0] = "parent";
                break;
            case 30:
            case 32:
            case 35:
            case 36:
            case 38:
            case 40:
            case 48:
                objArr[0] = "runnable";
                break;
            case 33:
            case 37:
                objArr[0] = "state";
                break;
            case 39:
                objArr[0] = "modalityState";
                break;
            case 43:
                objArr[0] = "c";
                break;
            case 46:
                objArr[0] = "process";
                break;
            case 47:
                objArr[0] = "progressTitle";
                break;
            default:
                objArr[0] = "parentDisposable";
                break;
        }
        if (i == 2) {
            objArr[1] = "setUp";
        } else if (i == 7 || i == 9) {
            objArr[1] = "executeOnPooledThread";
        } else if (i == 29) {
            objArr[1] = "getNoneModalityState";
        } else if (i == 22) {
            objArr[1] = "acquireReadActionLock";
        } else if (i == 23) {
            objArr[1] = "acquireWriteActionLock";
        } else if (i == 41) {
            objArr[1] = "getCurrentModalityState";
        } else if (i == 42) {
            objArr[1] = "getAnyModalityState";
        } else if (i == 44) {
            objArr[1] = "getModalityStateForComponent";
        } else if (i != 45) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/mock/MockApplication";
        } else {
            objArr[1] = "getDefaultModalityState";
        }
        switch (i) {
            case 1:
                objArr[2] = "setUp";
                break;
            case 2:
            case 7:
            case 9:
            case 22:
            case 23:
            case 29:
            case 41:
            case 42:
            case 44:
            case 45:
                break;
            case 3:
                objArr[2] = "getServiceIfCreated";
                break;
            case 4:
                objArr[2] = "getService";
                break;
            case 5:
                objArr[2] = "doGetService";
                break;
            case 6:
            case 8:
                objArr[2] = "executeOnPooledThread";
                break;
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
                objArr[2] = "invokeLaterOnWriteThread";
                break;
            case 16:
            case 17:
            case 18:
                objArr[2] = "runReadAction";
                break;
            case 19:
            case 20:
            case 21:
                objArr[2] = "runWriteAction";
                break;
            case 24:
                objArr[2] = "hasWriteAction";
                break;
            case 25:
            case 26:
            case 27:
                objArr[2] = "addApplicationListener";
                break;
            case 28:
                objArr[2] = "removeApplicationListener";
                break;
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
                objArr[2] = "invokeLater";
                break;
            case 38:
            case 39:
            case 40:
                objArr[2] = "invokeAndWait";
                break;
            case 43:
                objArr[2] = "getModalityStateForComponent";
                break;
            case 46:
            case 47:
                objArr[2] = "runProcessWithProgressSynchronously";
                break;
            case 48:
                objArr[2] = "tryRunReadAction";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 7 && i != 9 && i != 29 && i != 22 && i != 23 && i != 41 && i != 42 && i != 44 && i != 45) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public void addApplicationListener(ApplicationListener applicationListener) {
        if (applicationListener == null) {
            $$$reportNull$$$0(25);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.application.Application
    public void addApplicationListener(ApplicationListener applicationListener, Disposable disposable) {
        if (applicationListener == null) {
            $$$reportNull$$$0(26);
        }
        if (disposable == null) {
            $$$reportNull$$$0(27);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.application.Application
    public void assertIsDispatchThread() {
    }

    public void assertIsDispatchThread(JComponent jComponent) {
    }

    public void assertIsNonDispatchThread() {
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.application.Application
    public void assertIsWriteThread() {
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.application.Application
    public void assertReadAccessAllowed() {
    }

    public void assertReadAccessNotAllowed() {
    }

    public void assertTimeConsuming() {
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.application.Application
    public void assertWriteAccessAllowed() {
    }

    public long getIdleTime() {
        return 0L;
    }

    public long getStartTime() {
        return 0L;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.application.Application
    public boolean hasWriteAction(Class<?> cls) {
        if (cls != null) {
            return false;
        }
        $$$reportNull$$$0(24);
        return false;
    }

    public boolean holdsReadLock() {
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.application.Application
    public void invokeLater(Runnable runnable) {
        if (runnable == null) {
            $$$reportNull$$$0(35);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.application.Application
    public void invokeLater(Runnable runnable, ModalityState modalityState) {
        if (runnable == null) {
            $$$reportNull$$$0(36);
        }
        if (modalityState == null) {
            $$$reportNull$$$0(37);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.application.Application
    public void invokeLater(Runnable runnable, ModalityState modalityState, Condition<?> condition) {
        if (runnable == null) {
            $$$reportNull$$$0(32);
        }
        if (modalityState == null) {
            $$$reportNull$$$0(33);
        }
        if (condition == null) {
            $$$reportNull$$$0(34);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.application.Application
    public void invokeLater(Runnable runnable, Condition<?> condition) {
        if (runnable == null) {
            $$$reportNull$$$0(30);
        }
        if (condition == null) {
            $$$reportNull$$$0(31);
        }
    }

    public boolean isActive() {
        return true;
    }

    public boolean isCommandLine() {
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.application.Application
    public boolean isEAP() {
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.application.Application
    public boolean isHeadlessEnvironment() {
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.application.Application
    public boolean isInternal() {
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.application.Application
    public boolean isReadAccessAllowed() {
        return true;
    }

    public boolean isRestartCapable() {
        return false;
    }

    public boolean isSaveAllowed() {
        return true;
    }

    public boolean isUnitTestMode() {
        return true;
    }

    public boolean isWriteAccessAllowed() {
        return true;
    }

    public boolean isWriteActionInProgress() {
        return false;
    }

    public boolean isWriteActionPending() {
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.application.Application
    public boolean isWriteThread() {
        return true;
    }

    public void load() {
    }

    public void removeApplicationListener(ApplicationListener applicationListener) {
        if (applicationListener == null) {
            $$$reportNull$$$0(28);
        }
    }

    public void restart(boolean z) {
    }

    public void restart(boolean z, boolean z2) {
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.application.ex.ApplicationEx
    public boolean runProcessWithProgressSynchronously(Runnable runnable, String str, boolean z, boolean z2, Project project, JComponent jComponent, String str2) {
        if (runnable == null) {
            $$$reportNull$$$0(46);
        }
        if (str != null) {
            return false;
        }
        $$$reportNull$$$0(47);
        return false;
    }

    public void saveAll() {
    }

    public void saveSettings() {
    }

    public void setSaveAllowed(boolean z) {
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MockApplication(Disposable disposable) {
        super(null, disposable);
        if (disposable == null) {
            $$$reportNull$$$0(0);
        }
        INSTANCES_CREATED++;
        Extensions.setRootArea(getExtensionArea(), disposable);
    }

    public static MockApplication setUp(Disposable disposable) {
        if (disposable == null) {
            $$$reportNull$$$0(1);
        }
        MockApplication mockApplication = new MockApplication(disposable);
        ApplicationManager.setApplication(mockApplication, disposable);
        return mockApplication;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.components.ComponentManager
    public final <T> T getServiceIfCreated(Class<T> cls) {
        if (cls == null) {
            $$$reportNull$$$0(3);
        }
        return (T) doGetService(cls, false);
    }

    @Override // org.jetbrains.kotlin.com.intellij.mock.MockComponentManager, org.jetbrains.kotlin.com.intellij.openapi.components.ComponentManager
    public final <T> T getService(Class<T> cls) {
        if (cls == null) {
            $$$reportNull$$$0(4);
        }
        return (T) doGetService(cls, true);
    }

    private <T> T doGetService(Class<T> cls, boolean z) {
        if (cls == null) {
            $$$reportNull$$$0(5);
        }
        T t = (T) super.getService(cls);
        if (t != null || !z || !Modifier.isFinal(cls.getModifiers()) || !cls.isAnnotationPresent(Service.class)) {
            return t;
        }
        synchronized (cls) {
            T t2 = (T) super.getService(cls);
            if (t2 != null) {
                return t2;
            }
            m6468getPicoContainer().registerComponentImplementation(cls.getName(), cls);
            return (T) super.getService(cls);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.application.Application
    public boolean isDispatchThread() {
        return SwingUtilities.isEventDispatchThread();
    }

    public Future<?> executeOnPooledThread(Runnable runnable) {
        if (runnable == null) {
            $$$reportNull$$$0(6);
        }
        Future<?> futureSubmit = AppExecutorUtil.getAppExecutorService().submit(runnable);
        if (futureSubmit == null) {
            $$$reportNull$$$0(7);
        }
        return futureSubmit;
    }

    public <T> Future<T> executeOnPooledThread(Callable<T> callable) {
        if (callable == null) {
            $$$reportNull$$$0(8);
        }
        Future<T> futureSubmit = AppExecutorUtil.getAppExecutorService().submit(callable);
        if (futureSubmit == null) {
            $$$reportNull$$$0(9);
        }
        return futureSubmit;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.application.Application
    public void invokeLaterOnWriteThread(Runnable runnable) {
        if (runnable == null) {
            $$$reportNull$$$0(10);
        }
        runnable.run();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.application.Application
    public void invokeLaterOnWriteThread(Runnable runnable, ModalityState modalityState) {
        if (runnable == null) {
            $$$reportNull$$$0(11);
        }
        if (modalityState == null) {
            $$$reportNull$$$0(12);
        }
        runnable.run();
    }

    public void invokeLaterOnWriteThread(Runnable runnable, ModalityState modalityState, Condition<?> condition) {
        if (runnable == null) {
            $$$reportNull$$$0(13);
        }
        if (modalityState == null) {
            $$$reportNull$$$0(14);
        }
        if (condition == null) {
            $$$reportNull$$$0(15);
        }
        runnable.run();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.application.Application
    public void runReadAction(Runnable runnable) {
        if (runnable == null) {
            $$$reportNull$$$0(16);
        }
        runnable.run();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.application.Application
    public <T> T runReadAction(Computable<T> computable) {
        if (computable == null) {
            $$$reportNull$$$0(17);
        }
        return computable.compute();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.application.Application
    public <T, E extends Throwable> T runReadAction(ThrowableComputable<T, E> throwableComputable) throws Throwable {
        if (throwableComputable == null) {
            $$$reportNull$$$0(18);
        }
        return throwableComputable.compute();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.application.Application
    public void runWriteAction(Runnable runnable) {
        if (runnable == null) {
            $$$reportNull$$$0(19);
        }
        runnable.run();
    }

    public <T> T runWriteAction(Computable<T> computable) {
        if (computable == null) {
            $$$reportNull$$$0(20);
        }
        return computable.compute();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.application.Application
    public <T, E extends Throwable> T runWriteAction(ThrowableComputable<T, E> throwableComputable) throws Throwable {
        if (throwableComputable == null) {
            $$$reportNull$$$0(21);
        }
        return throwableComputable.compute();
    }

    public AccessToken acquireReadActionLock() {
        AccessToken accessToken = AccessToken.EMPTY_ACCESS_TOKEN;
        if (accessToken == null) {
            $$$reportNull$$$0(22);
        }
        return accessToken;
    }

    public AccessToken acquireWriteActionLock(Class<?> cls) {
        AccessToken accessToken = AccessToken.EMPTY_ACCESS_TOKEN;
        if (accessToken == null) {
            $$$reportNull$$$0(23);
        }
        return accessToken;
    }

    public ModalityState getNoneModalityState() {
        ModalityState modalityState = ModalityState.NON_MODAL;
        if (modalityState == null) {
            $$$reportNull$$$0(29);
        }
        return modalityState;
    }

    public ModalityInvokator getInvokator() {
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.application.Application
    public void invokeAndWait(Runnable runnable, ModalityState modalityState) {
        if (runnable == null) {
            $$$reportNull$$$0(38);
        }
        if (modalityState == null) {
            $$$reportNull$$$0(39);
        }
        if (isDispatchThread()) {
            runnable.run();
            return;
        }
        try {
            SwingUtilities.invokeAndWait(runnable);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void invokeAndWait(Runnable runnable) throws ProcessCanceledException {
        if (runnable == null) {
            $$$reportNull$$$0(40);
        }
        invokeAndWait(runnable, getDefaultModalityState());
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.application.Application
    public ModalityState getCurrentModalityState() {
        ModalityState noneModalityState = getNoneModalityState();
        if (noneModalityState == null) {
            $$$reportNull$$$0(41);
        }
        return noneModalityState;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.application.Application
    public ModalityState getAnyModalityState() {
        AnyModalityState anyModalityState = AnyModalityState.ANY;
        if (anyModalityState == null) {
            $$$reportNull$$$0(42);
        }
        return anyModalityState;
    }

    public ModalityState getModalityStateForComponent(Component component) {
        if (component == null) {
            $$$reportNull$$$0(43);
        }
        ModalityState noneModalityState = getNoneModalityState();
        if (noneModalityState == null) {
            $$$reportNull$$$0(44);
        }
        return noneModalityState;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.application.Application
    public ModalityState getDefaultModalityState() {
        ModalityState noneModalityState = getNoneModalityState();
        if (noneModalityState == null) {
            $$$reportNull$$$0(45);
        }
        return noneModalityState;
    }

    public boolean tryRunReadAction(Runnable runnable) {
        if (runnable == null) {
            $$$reportNull$$$0(48);
        }
        runReadAction(runnable);
        return true;
    }
}
