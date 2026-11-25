package org.jetbrains.kotlin.com.intellij.lang;

import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
public interface DefaultASTFactory {
    LeafElement createComment(IElementType iElementType, CharSequence charSequence);
}
