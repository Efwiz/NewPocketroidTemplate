package org.jetbrains.kotlin.com.intellij.util;

import org.jetbrains.kotlin.com.intellij.openapi.editor.event.DocumentEvent;

/* JADX INFO: loaded from: classes3.dex */
public final class DocumentEventUtil {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        objArr[0] = "e";
        objArr[1] = "org/jetbrains/kotlin/com/intellij/util/DocumentEventUtil";
        if (i == 1) {
            objArr[2] = "isMoveDeletion";
        } else if (i == 2) {
            objArr[2] = "getMoveOffsetBeforeInsertion";
        } else if (i != 3) {
            objArr[2] = "isMoveInsertion";
        } else {
            objArr[2] = "getMoveOffsetAfterDeletion";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public static boolean isMoveInsertion(DocumentEvent documentEvent) {
        if (documentEvent == null) {
            $$$reportNull$$$0(0);
        }
        return documentEvent.getOldLength() == 0 && documentEvent.getMoveOffset() != documentEvent.getOffset();
    }
}
