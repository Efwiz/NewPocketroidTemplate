package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;

/* JADX INFO: loaded from: classes3.dex */
public final class SourceTreeToPsiMap {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    /* JADX WARN: Code duplicated, block: B:18:0x0028  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 3) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 3) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/SourceTreeToPsiMap";
        } else if (i == 2) {
            objArr[0] = "psiElement";
        } else if (i != 3) {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/SourceTreeToPsiMap";
        }
        if (i == 1) {
            objArr[1] = "treeToPsiNotNull";
        } else if (i != 3) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/SourceTreeToPsiMap";
        } else {
            objArr[1] = "psiToTreeNotNull";
        }
        if (i != 1) {
            if (i == 2) {
                objArr[2] = "psiToTreeNotNull";
            } else if (i != 3) {
                objArr[2] = "treeToPsiNotNull";
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 3) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    private SourceTreeToPsiMap() {
    }

    public static PsiElement treeElementToPsi(ASTNode aSTNode) {
        if (aSTNode == null) {
            return null;
        }
        return aSTNode.getPsi();
    }

    public static <T extends PsiElement> T treeToPsiNotNull(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
        T t = (T) aSTNode.getPsi();
        if (t == null) {
            $$$reportNull$$$0(1);
        }
        return t;
    }

    public static ASTNode psiElementToTree(PsiElement psiElement) {
        if (psiElement == null) {
            return null;
        }
        return psiElement.getNode();
    }

    public static TreeElement psiToTreeNotNull(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(2);
        }
        TreeElement treeElement = (TreeElement) psiElement.getNode();
        if (treeElement == null) {
            $$$reportNull$$$0(3);
        }
        return treeElement;
    }

    public static boolean hasTreeElement(PsiElement psiElement) {
        return (psiElement instanceof TreeElement) || (psiElement instanceof ASTDelegatePsiElement) || (psiElement instanceof PsiFileImpl);
    }
}
