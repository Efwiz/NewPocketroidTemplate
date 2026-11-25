package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;

/* JADX INFO: loaded from: classes3.dex */
public class PsiDiamondTypeElementImpl extends PsiTypeElementImpl {
    public PsiDiamondTypeElementImpl() {
        super(JavaElementType.DIAMOND_TYPE);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiTypeElementImpl, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiTypeElement:DIAMOND";
    }
}
