package org.jetbrains.kotlin.com.intellij.openapi.application;

import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.components.ComponentManager;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProcessCanceledException;
import org.jetbrains.kotlin.com.intellij.openapi.util.Computable;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable;

/* JADX INFO: loaded from: classes3.dex */
public interface Application extends ComponentManager {
    void addApplicationListener(ApplicationListener applicationListener, Disposable disposable);

    void assertIsDispatchThread();

    void assertIsWriteThread();

    void assertReadAccessAllowed();

    void assertWriteAccessAllowed();

    ModalityState getAnyModalityState();

    ModalityState getCurrentModalityState();

    ModalityState getDefaultModalityState();

    boolean hasWriteAction(Class<?> cls);

    void invokeAndWait(Runnable runnable, ModalityState modalityState) throws ProcessCanceledException;

    void invokeLater(Runnable runnable);

    void invokeLater(Runnable runnable, ModalityState modalityState);

    void invokeLater(Runnable runnable, ModalityState modalityState, Condition<?> condition);

    void invokeLater(Runnable runnable, Condition<?> condition);

    void invokeLaterOnWriteThread(Runnable runnable);

    void invokeLaterOnWriteThread(Runnable runnable, ModalityState modalityState);

    boolean isDispatchThread();

    boolean isEAP();

    boolean isHeadlessEnvironment();

    boolean isInternal();

    boolean isReadAccessAllowed();

    boolean isUnitTestMode();

    boolean isWriteAccessAllowed();

    boolean isWriteThread();

    <T> T runReadAction(Computable<T> computable);

    <T, E extends Throwable> T runReadAction(ThrowableComputable<T, E> throwableComputable) throws Throwable;

    void runReadAction(Runnable runnable);

    <T, E extends Throwable> T runWriteAction(ThrowableComputable<T, E> throwableComputable) throws Throwable;

    void runWriteAction(Runnable runnable);
}
