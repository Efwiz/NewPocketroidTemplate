package org.jetbrains.kotlin.com.intellij.openapi.editor.impl;

import org.jetbrains.kotlin.com.intellij.openapi.editor.Document;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointName;

/* JADX INFO: loaded from: classes3.dex */
public abstract class DocumentWriteAccessGuard {
    public static final ExtensionPointName<DocumentWriteAccessGuard> EP_NAME = ExtensionPointName.create("org.jetbrains.kotlin.com.intellij.documentWriteAccessGuard");

    public abstract Result isWritable(Document document);

    public static class Result {
        private final String myFailureReason;
        private final boolean mySuccess;

        public boolean isSuccess() {
            return this.mySuccess;
        }

        public String getFailureReason() {
            return this.myFailureReason;
        }
    }
}
