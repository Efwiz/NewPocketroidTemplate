package org.jetbrains.kotlin.com.intellij.openapi.progress;

import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.ControlFlowException;

/* JADX INFO: loaded from: classes3.dex */
public class ProcessCanceledException extends RuntimeException implements ControlFlowException {
    public ProcessCanceledException() {
    }

    public ProcessCanceledException(Throwable th) {
        super(th);
        if (th instanceof ProcessCanceledException) {
            throw new IllegalArgumentException("Must not self-wrap ProcessCanceledException: ", th);
        }
    }
}
