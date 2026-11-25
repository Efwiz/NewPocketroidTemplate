package org.jetbrains.kotlin.com.intellij.psi.tree;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;

/* JADX INFO: loaded from: classes3.dex */
@FunctionalInterface
public interface ILazyParseableElementTypeBase {
    ASTNode parseContents(ASTNode aSTNode);

    default boolean reuseCollapsedTokens() {
        return false;
    }
}
