package org.jetbrains.kotlin.com.intellij.openapi.editor.ex;

import org.jetbrains.kotlin.com.intellij.openapi.editor.Document;

/* JADX INFO: loaded from: classes3.dex */
public interface DocumentEx extends Document {

    /* JADX INFO: renamed from: org.jetbrains.kotlin.com.intellij.openapi.editor.ex.DocumentEx$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ boolean $assertionsDisabled = false;
    }

    default int getModificationSequence() {
        return 0;
    }

    default boolean isInEventsHandling() {
        return false;
    }

    boolean removeRangeMarker(RangeMarkerEx rangeMarkerEx);

    void setModificationStamp(long j);

    default void suppressGuardedExceptions() {
    }

    default void unSuppressGuardedExceptions() {
    }

    static {
        boolean z = AnonymousClass1.$assertionsDisabled;
    }
}
