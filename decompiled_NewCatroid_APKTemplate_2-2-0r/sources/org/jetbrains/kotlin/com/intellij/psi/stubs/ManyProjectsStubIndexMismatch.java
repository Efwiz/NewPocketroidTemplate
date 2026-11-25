package org.jetbrains.kotlin.com.intellij.psi.stubs;

import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Attachment;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.RuntimeExceptionWithAttachments;

/* JADX INFO: compiled from: StubTreeLoader.java */
/* JADX INFO: loaded from: classes3.dex */
class ManyProjectsStubIndexMismatch extends RuntimeExceptionWithAttachments {
    ManyProjectsStubIndexMismatch(String str, Throwable th, Attachment... attachmentArr) {
        super(str, th, attachmentArr);
    }
}
