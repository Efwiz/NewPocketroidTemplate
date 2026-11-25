package org.jetbrains.kotlin.com.intellij.openapi.project;

import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Attachment;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.ExceptionWithAttachments;

/* JADX INFO: loaded from: classes3.dex */
public final class IndexNotReadyException extends RuntimeException implements ExceptionWithAttachments {
    private final Throwable myStartTrace;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/openapi/project/IndexNotReadyException", "getAttachments"));
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.diagnostic.ExceptionWithAttachments
    public Attachment[] getAttachments() {
        Attachment[] attachmentArr;
        if (this.myStartTrace == null) {
            attachmentArr = Attachment.EMPTY_ARRAY;
        } else {
            attachmentArr = new Attachment[]{new Attachment("indexingStart", this.myStartTrace)};
        }
        if (attachmentArr == null) {
            $$$reportNull$$$0(0);
        }
        return attachmentArr;
    }
}
