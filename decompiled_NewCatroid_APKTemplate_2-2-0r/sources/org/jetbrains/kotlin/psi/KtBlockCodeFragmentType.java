package org.jetbrains.kotlin.psi;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.PsiBuilderFactory;
import org.jetbrains.kotlin.com.intellij.lexer.Lexer;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ICodeFragmentElementType;
import org.jetbrains.kotlin.idea.KotlinLanguage;
import org.jetbrains.kotlin.parsing.KotlinParser;

/* JADX INFO: loaded from: classes6.dex */
public class KtBlockCodeFragmentType extends ICodeFragmentElementType {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "chameleon";
        } else {
            objArr[0] = "psi";
        }
        objArr[1] = "org/jetbrains/kotlin/psi/KtBlockCodeFragmentType";
        objArr[2] = "doParseContents";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public KtBlockCodeFragmentType() {
        super("kotlin.BLOCK_CODE_FRAGMENT", KotlinLanguage.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.tree.ILazyParseableElementType
    protected ASTNode doParseContents(ASTNode aSTNode, PsiElement psiElement) {
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(1);
        }
        return KotlinParser.parseBlockCodeFragment(PsiBuilderFactory.getInstance().createBuilder(psiElement.getProject(), aSTNode, (Lexer) null, getLanguageForParser(psiElement), aSTNode.getChars())).getFirstChildNode();
    }
}
