package org.jetbrains.kotlin.com.intellij.openapi.diagnostic;

/* JADX INFO: loaded from: classes3.dex */
public class RuntimeExceptionWithAttachments extends RuntimeException implements ExceptionWithAttachments {
    private final Attachment[] myAttachments;
    private final String myUserMessage;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/openapi/diagnostic/RuntimeExceptionWithAttachments", "getAttachments"));
    }

    public RuntimeExceptionWithAttachments(String str, Attachment... attachmentArr) {
        super(str);
        this.myUserMessage = null;
        this.myAttachments = attachmentArr;
    }

    public RuntimeExceptionWithAttachments(Throwable th, Attachment... attachmentArr) {
        super(th);
        this.myUserMessage = null;
        this.myAttachments = attachmentArr;
    }

    public RuntimeExceptionWithAttachments(String str, Throwable th, Attachment... attachmentArr) {
        super(str, th);
        this.myUserMessage = null;
        this.myAttachments = attachmentArr;
    }

    public RuntimeExceptionWithAttachments(String str, String str2, Attachment... attachmentArr) {
        super(str2);
        this.myUserMessage = str;
        this.myAttachments = attachmentArr;
    }

    public String getUserMessage() {
        return this.myUserMessage;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.diagnostic.ExceptionWithAttachments
    public Attachment[] getAttachments() {
        Attachment[] attachmentArr = this.myAttachments;
        if (attachmentArr == null) {
            $$$reportNull$$$0(0);
        }
        return attachmentArr;
    }
}
