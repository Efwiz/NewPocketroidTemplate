package org.jetbrains.kotlin.com.intellij.openapi.editor.impl;

import org.jetbrains.kotlin.com.intellij.openapi.editor.event.DocumentEvent;

/* JADX INFO: loaded from: classes3.dex */
final class PersistentRangeMarkerUtil {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "e", "org/jetbrains/kotlin/com/intellij/openapi/editor/impl/PersistentRangeMarkerUtil", "shouldTranslateViaDiff"));
    }

    static boolean shouldTranslateViaDiff(DocumentEvent documentEvent, int i, int i2) {
        if (documentEvent == null) {
            $$$reportNull$$$0(0);
        }
        if (documentEvent.isWholeTextReplaced()) {
            return true;
        }
        return documentEvent.getOffset() < i2 && documentEvent.getOffset() + documentEvent.getOldLength() > i && Math.max(documentEvent.getNewLength(), documentEvent.getOldLength()) * 5 >= documentEvent.getDocument().getTextLength() * 4;
    }
}
