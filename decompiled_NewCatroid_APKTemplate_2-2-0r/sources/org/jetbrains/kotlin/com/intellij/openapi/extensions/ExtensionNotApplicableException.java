package org.jetbrains.kotlin.com.intellij.openapi.extensions;

import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.ControlFlowException;

/* JADX INFO: loaded from: classes3.dex */
public final class ExtensionNotApplicableException extends RuntimeException implements ControlFlowException {
    public static final ExtensionNotApplicableException INSTANCE = new ExtensionNotApplicableException();

    private ExtensionNotApplicableException() {
    }

    @Override // java.lang.Throwable
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
