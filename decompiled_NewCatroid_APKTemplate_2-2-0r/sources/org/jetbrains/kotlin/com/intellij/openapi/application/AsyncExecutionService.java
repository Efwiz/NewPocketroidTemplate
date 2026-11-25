package org.jetbrains.kotlin.com.intellij.openapi.application;

import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes3.dex */
public abstract class AsyncExecutionService {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/openapi/application/AsyncExecutionService", "getService"));
    }

    protected abstract <T> NonBlockingReadAction<T> buildNonBlockingReadAction(Callable<? extends T> callable);

    protected abstract AppUIExecutor createWriteThreadExecutor(ModalityState modalityState);

    static AsyncExecutionService getService() {
        AsyncExecutionService asyncExecutionService = (AsyncExecutionService) ApplicationManager.getApplication().getService(AsyncExecutionService.class);
        if (asyncExecutionService == null) {
            $$$reportNull$$$0(0);
        }
        return asyncExecutionService;
    }
}
