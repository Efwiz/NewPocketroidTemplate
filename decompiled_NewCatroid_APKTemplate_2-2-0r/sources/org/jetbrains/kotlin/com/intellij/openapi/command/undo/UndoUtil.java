package org.jetbrains.kotlin.com.intellij.openapi.command.undo;

import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;

/* JADX INFO: loaded from: classes3.dex */
public final class UndoUtil {
    /* JADX WARN: Code duplicated, block: B:13:0x001b  */
    /* JADX WARN: Code duplicated, block: B:14:0x0020  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1 || i == 2) {
            objArr[0] = "document";
        } else if (i == 3) {
            objArr[0] = "runnable";
        } else if (i == 5 || i == 6) {
            objArr[0] = "document";
        } else if (i != 9) {
            objArr[0] = "file";
        } else {
            objArr[0] = "runnable";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/command/undo/UndoUtil";
        switch (i) {
            case 1:
            case 4:
                objArr[2] = "disableUndoFor";
                break;
            case 2:
            case 3:
                objArr[2] = "disableUndoIn";
                break;
            case 5:
                objArr[2] = "enableUndoFor";
                break;
            case 6:
            case 7:
                objArr[2] = "isUndoDisabledFor";
                break;
            case 8:
            case 9:
                objArr[2] = "forceUndoIn";
                break;
            case 10:
                objArr[2] = "setForceUndoFlag";
                break;
            case 11:
                objArr[2] = "isForceUndoFlagSet";
                break;
            default:
                objArr[2] = "markPsiFileForUndo";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public static void disableUndoFor(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(4);
        }
        virtualFile.putUserData(UndoConstants.DONT_RECORD_UNDO, Boolean.TRUE);
    }
}
