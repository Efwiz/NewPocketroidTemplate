package org.jetbrains.kotlin.util;

import org.jetbrains.kotlin.com.intellij.openapi.application.Application;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.types.error.LazyWrappedTypeComputationException;

/* JADX INFO: loaded from: classes6.dex */
public class ReenteringLazyValueComputationException extends LazyWrappedTypeComputationException {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/util/ReenteringLazyValueComputationException", "fillInStackTrace"));
    }

    @Override // java.lang.Throwable
    public synchronized Throwable fillInStackTrace() {
        Application application = ApplicationManager.getApplication();
        if (application != null && !application.isInternal() && !application.isUnitTestMode()) {
            return this;
        }
        Throwable thFillInStackTrace = super.fillInStackTrace();
        if (thFillInStackTrace == null) {
            $$$reportNull$$$0(0);
        }
        return thFillInStackTrace;
    }
}
