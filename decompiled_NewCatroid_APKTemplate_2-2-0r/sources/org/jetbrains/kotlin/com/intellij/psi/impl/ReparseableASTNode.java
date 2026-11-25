package org.jetbrains.kotlin.com.intellij.psi.impl;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.FileASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;

/* JADX INFO: loaded from: classes3.dex */
public interface ReparseableASTNode {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "newChild";
        } else if (i == 2) {
            objArr[0] = "psiFile";
        } else if (i != 3) {
            objArr[0] = "oldChild";
        } else {
            objArr[0] = "newNode";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/ReparseableASTNode";
        if (i == 1) {
            objArr[2] = "applyInsertOnReparse";
        } else if (i == 2 || i == 3) {
            objArr[2] = "applyReplaceFileOnReparse";
        } else {
            objArr[2] = "applyDeleteOnReparse";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    void applyReplaceOnReparse(ASTNode aSTNode);

    default void applyDeleteOnReparse(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
        throw new UnsupportedOperationException();
    }

    default void applyInsertOnReparse(ASTNode aSTNode, ASTNode aSTNode2) {
        if (aSTNode == null) {
            $$$reportNull$$$0(1);
        }
        throw new UnsupportedOperationException();
    }

    default void applyReplaceFileOnReparse(PsiFile psiFile, FileASTNode fileASTNode) {
        if (psiFile == null) {
            $$$reportNull$$$0(2);
        }
        if (fileASTNode == null) {
            $$$reportNull$$$0(3);
        }
        throw new UnsupportedOperationException();
    }
}
