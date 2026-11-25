package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
public interface StubbedSpine {
    int getStubCount();

    PsiElement getStubPsi(int i);

    IElementType getStubType(int i);
}
