package org.jetbrains.kotlin.com.intellij.openapi.editor.event;

import java.util.EventListener;
import org.jetbrains.kotlin.com.intellij.util.ArrayFactory;

/* JADX INFO: loaded from: classes3.dex */
public interface DocumentListener extends EventListener {
    public static final DocumentListener[] EMPTY_ARRAY = new DocumentListener[0];
    public static final ArrayFactory<DocumentListener> ARRAY_FACTORY = new ArrayFactory() { // from class: org.jetbrains.kotlin.com.intellij.openapi.editor.event.DocumentListener$$ExternalSyntheticLambda0
        @Override // org.jetbrains.kotlin.com.intellij.util.ArrayFactory
        public final Object[] create(int i) {
            return DocumentListener.lambda$static$0(i);
        }
    };

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 2 || i == 3) {
            objArr[0] = "document";
        } else {
            objArr[0] = "event";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/editor/event/DocumentListener";
        if (i == 1) {
            objArr[2] = "documentChanged";
        } else if (i == 2) {
            objArr[2] = "bulkUpdateStarting";
        } else if (i != 3) {
            objArr[2] = "beforeDocumentChange";
        } else {
            objArr[2] = "bulkUpdateFinished";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    default void beforeDocumentChange(DocumentEvent documentEvent) {
        if (documentEvent == null) {
            $$$reportNull$$$0(0);
        }
    }

    default void documentChanged(DocumentEvent documentEvent) {
        if (documentEvent == null) {
            $$$reportNull$$$0(1);
        }
    }

    static /* synthetic */ DocumentListener[] lambda$static$0(int i) {
        return i == 0 ? EMPTY_ARRAY : new DocumentListener[i];
    }
}
