package org.jetbrains.kotlin.com.intellij.util.io;

/* JADX INFO: loaded from: classes3.dex */
final class FinalizationRequest {
    public final long finalizationId;
    public final Page page;

    FinalizationRequest(Page page, long j) {
        this.page = page;
        this.finalizationId = j;
    }

    public String toString() {
        return "FinalizationRequest[page = " + this.page + ", finalizationId = " + this.finalizationId + "]";
    }
}
