package org.jetbrains.kotlin.com.intellij.openapi.application;

/* JADX INFO: loaded from: classes3.dex */
public abstract class TransactionGuard {
    private static volatile TransactionGuard ourInstance = (TransactionGuard) CachedSingletonsRegistry.markCachedField(TransactionGuard.class);

    public abstract void assertWriteSafeContext(ModalityState modalityState);

    public abstract boolean isWriteSafeModality(ModalityState modalityState);

    public static TransactionGuard getInstance() {
        TransactionGuard transactionGuard = ourInstance;
        if (transactionGuard != null) {
            return transactionGuard;
        }
        TransactionGuard transactionGuard2 = (TransactionGuard) ApplicationManager.getApplication().getService(TransactionGuard.class);
        ourInstance = transactionGuard2;
        return transactionGuard2;
    }
}
