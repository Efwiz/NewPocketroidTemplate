package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiCodeBlock;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaToken;
import org.jetbrains.kotlin.com.intellij.psi.PsiSwitchBlock;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
public abstract class PsiSwitchBlockImpl extends CompositePsiElement implements PsiSwitchBlock {
    protected PsiSwitchBlockImpl(IElementType iElementType) {
        super(iElementType);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiSwitchBlock
    public PsiExpression getExpression() {
        return (PsiExpression) findPsiChildByType(ElementType.EXPRESSION_BIT_SET);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiSwitchBlock
    public PsiCodeBlock getBody() {
        return (PsiCodeBlock) findPsiChildByType(JavaElementType.CODE_BLOCK);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiSwitchBlock
    public PsiJavaToken getLParenth() {
        return (PsiJavaToken) findPsiChildByType(JavaTokenType.LPARENTH);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiSwitchBlock
    public PsiJavaToken getRParenth() {
        return (PsiJavaToken) findPsiChildByType(JavaTokenType.RPARENTH);
    }
}
