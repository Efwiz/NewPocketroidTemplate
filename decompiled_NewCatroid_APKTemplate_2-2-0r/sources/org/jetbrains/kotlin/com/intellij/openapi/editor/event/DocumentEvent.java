package org.jetbrains.kotlin.com.intellij.openapi.editor.event;

import java.util.EventObject;
import org.jetbrains.kotlin.com.intellij.openapi.editor.Document;

/* JADX INFO: loaded from: classes3.dex */
public abstract class DocumentEvent extends EventObject {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i != 1) {
            objArr[0] = "document";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/editor/event/DocumentEvent";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/editor/event/DocumentEvent";
        } else {
            objArr[1] = "getDocument";
        }
        if (i != 1) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    public abstract CharSequence getNewFragment();

    public abstract int getNewLength();

    public abstract int getOffset();

    public abstract CharSequence getOldFragment();

    public abstract int getOldLength();

    public abstract long getOldTimeStamp();

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    protected DocumentEvent(Document document) {
        super(document);
        if (document == null) {
            $$$reportNull$$$0(0);
        }
    }

    public Document getDocument() {
        Document document = (Document) getSource();
        if (document == null) {
            $$$reportNull$$$0(1);
        }
        return document;
    }

    public int getMoveOffset() {
        return getOffset();
    }

    public boolean isWholeTextReplaced() {
        return getOffset() == 0 && getNewLength() == getDocument().getTextLength();
    }
}
