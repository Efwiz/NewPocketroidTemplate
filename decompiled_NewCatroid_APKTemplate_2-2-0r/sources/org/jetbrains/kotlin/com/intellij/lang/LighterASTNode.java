package org.jetbrains.kotlin.com.intellij.lang;

import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
public interface LighterASTNode {
    public static final LighterASTNode[] EMPTY_ARRAY = new LighterASTNode[0];

    int getEndOffset();

    int getStartOffset();

    IElementType getTokenType();

    default int getTextLength() {
        return getEndOffset() - getStartOffset();
    }
}
