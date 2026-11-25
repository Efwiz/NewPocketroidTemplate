package org.jetbrains.kotlin.com.intellij.openapi.editor.event;

import org.jetbrains.kotlin.com.intellij.openapi.editor.Document;

/* JADX INFO: loaded from: classes3.dex */
public interface BulkAwareDocumentListener extends DocumentListener {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        objArr[0] = "event";
        objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/editor/event/BulkAwareDocumentListener";
        if (i == 1) {
            objArr[2] = "documentChanged";
        } else if (i == 2) {
            objArr[2] = "beforeDocumentChangeNonBulk";
        } else if (i != 3) {
            objArr[2] = "beforeDocumentChange";
        } else {
            objArr[2] = "documentChangedNonBulk";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    default void beforeDocumentChangeNonBulk(DocumentEvent documentEvent) {
        if (documentEvent == null) {
            $$$reportNull$$$0(2);
        }
    }

    default void documentChangedNonBulk(DocumentEvent documentEvent) {
        if (documentEvent == null) {
            $$$reportNull$$$0(3);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.event.DocumentListener
    default void beforeDocumentChange(DocumentEvent documentEvent) {
        if (documentEvent == null) {
            $$$reportNull$$$0(0);
        }
        if (documentEvent.getDocument().isInBulkUpdate()) {
            return;
        }
        beforeDocumentChangeNonBulk(documentEvent);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.event.DocumentListener
    default void documentChanged(DocumentEvent documentEvent) {
        if (documentEvent == null) {
            $$$reportNull$$$0(1);
        }
        if (documentEvent.getDocument().isInBulkUpdate()) {
            return;
        }
        documentChangedNonBulk(documentEvent);
    }

    public interface Simple extends BulkAwareDocumentListener {
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 2 || i == 3 || i == 4 || i == 5) {
                objArr[0] = "document";
            } else {
                objArr[0] = "event";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/editor/event/BulkAwareDocumentListener$Simple";
            if (i == 1) {
                objArr[2] = "documentChangedNonBulk";
            } else if (i == 2) {
                objArr[2] = "bulkUpdateStarting";
            } else if (i == 3) {
                objArr[2] = "bulkUpdateFinished";
            } else if (i == 4) {
                objArr[2] = "beforeDocumentChange";
            } else if (i != 5) {
                objArr[2] = "beforeDocumentChangeNonBulk";
            } else {
                objArr[2] = "afterDocumentChange";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        default void afterDocumentChange(Document document) {
            if (document == null) {
                $$$reportNull$$$0(5);
            }
        }

        default void beforeDocumentChange(Document document) {
            if (document == null) {
                $$$reportNull$$$0(4);
            }
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.event.BulkAwareDocumentListener
        default void beforeDocumentChangeNonBulk(DocumentEvent documentEvent) {
            if (documentEvent == null) {
                $$$reportNull$$$0(0);
            }
            beforeDocumentChange(documentEvent.getDocument());
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.event.BulkAwareDocumentListener
        default void documentChangedNonBulk(DocumentEvent documentEvent) {
            if (documentEvent == null) {
                $$$reportNull$$$0(1);
            }
            afterDocumentChange(documentEvent.getDocument());
        }
    }
}
