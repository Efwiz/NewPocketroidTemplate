package org.jetbrains.kotlin.com.intellij.lang;

import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ForeignLeafPsiElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.ILeafElementType;

/* JADX INFO: loaded from: classes3.dex */
public class ForeignLeafType extends TokenWrapper implements ILeafElementType {
    @Override // org.jetbrains.kotlin.com.intellij.psi.tree.ILeafElementType
    public ASTNode createLeafNode(CharSequence charSequence) {
        return new ForeignLeafPsiElement(this, getValue());
    }
}
