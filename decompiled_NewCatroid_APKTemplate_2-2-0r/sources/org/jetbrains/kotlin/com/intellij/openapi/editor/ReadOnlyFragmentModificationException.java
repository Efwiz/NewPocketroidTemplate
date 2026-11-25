package org.jetbrains.kotlin.com.intellij.openapi.editor;

import org.jetbrains.kotlin.com.intellij.openapi.editor.event.DocumentEvent;

/* JADX INFO: loaded from: classes3.dex */
public class ReadOnlyFragmentModificationException extends RuntimeException {
    private final RangeMarker myGuardedBlock;
    private final DocumentEvent myIllegalAttemptEvent;

    public ReadOnlyFragmentModificationException(DocumentEvent documentEvent, RangeMarker rangeMarker) {
        super("Attempt to modify read-only fragment");
        this.myIllegalAttemptEvent = documentEvent;
        this.myGuardedBlock = rangeMarker;
    }
}
