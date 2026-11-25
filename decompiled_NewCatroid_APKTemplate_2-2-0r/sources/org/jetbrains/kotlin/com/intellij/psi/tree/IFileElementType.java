package org.jetbrains.kotlin.com.intellij.psi.tree;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;

/* JADX INFO: loaded from: classes3.dex */
public class IFileElementType extends ILazyParseableElementType {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 2) {
            objArr[0] = "debugName";
        } else {
            objArr[0] = "chameleon";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/tree/IFileElementType";
        if (i != 2) {
            objArr[2] = "<init>";
        } else {
            objArr[2] = "parseContents";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public IFileElementType(Language language) {
        super("FILE", language);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IFileElementType(String str, Language language) {
        super(str, language);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IFileElementType(String str, Language language, boolean z) {
        super(str, language, z);
        if (str == null) {
            $$$reportNull$$$0(1);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.tree.ILazyParseableElementType, org.jetbrains.kotlin.com.intellij.psi.tree.ILazyParseableElementTypeBase
    public ASTNode parseContents(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(2);
        }
        PsiElement psi = aSTNode.getPsi();
        if (psi == null) {
            throw new AssertionError("Bad chameleon: " + aSTNode + " of type " + aSTNode.getElementType() + " in #" + aSTNode.getElementType().getLanguage());
        }
        return doParseContents(aSTNode, psi);
    }
}
