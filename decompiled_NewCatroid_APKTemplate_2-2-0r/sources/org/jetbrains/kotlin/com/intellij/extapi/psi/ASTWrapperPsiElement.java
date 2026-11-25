package org.jetbrains.kotlin.com.intellij.extapi.psi;

import com.huawei.hms.ml.language.common.utils.Constant;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.SharedImplUtil;

/* JADX INFO: loaded from: classes3.dex */
public class ASTWrapperPsiElement extends ASTDelegatePsiElement {
    private final ASTNode myNode;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i != 1) {
            objArr[0] = "node";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/extapi/psi/ASTWrapperPsiElement";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/extapi/psi/ASTWrapperPsiElement";
        } else {
            objArr[1] = "getNode";
        }
        if (i != 1) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    public ASTWrapperPsiElement(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
        this.myNode = aSTNode;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getParent() {
        return SharedImplUtil.getParent(getNode());
    }

    @Override // org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public ASTNode getNode() {
        ASTNode aSTNode = this.myNode;
        if (aSTNode == null) {
            $$$reportNull$$$0(1);
        }
        return aSTNode;
    }

    @Override // java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return getClass().getSimpleName() + "(" + this.myNode.getElementType() + Constant.AFTER_QUTO;
    }
}
