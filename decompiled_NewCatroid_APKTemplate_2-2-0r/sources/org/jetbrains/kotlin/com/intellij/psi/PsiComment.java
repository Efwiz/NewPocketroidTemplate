package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.model.psi.UrlReferenceHost;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiComment extends UrlReferenceHost, PsiElement {
    IElementType getTokenType();
}
