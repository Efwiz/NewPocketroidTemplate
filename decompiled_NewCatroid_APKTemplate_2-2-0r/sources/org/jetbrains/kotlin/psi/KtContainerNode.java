package org.jetbrains.kotlin.psi;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes6.dex */
public class KtContainerNode extends KtElementImpl {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "node", "org/jetbrains/kotlin/psi/KtContainerNode", "<init>"));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtContainerNode(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase
    protected <T> T findChildByClass(Class<T> cls) {
        return (T) super.findChildByClass(cls);
    }

    @Override // org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement
    protected <T extends PsiElement> T findChildByType(IElementType iElementType) {
        return (T) super.findChildByType(iElementType);
    }
}
