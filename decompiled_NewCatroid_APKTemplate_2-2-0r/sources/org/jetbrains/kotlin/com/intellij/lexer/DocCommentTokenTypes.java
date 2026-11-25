package org.jetbrains.kotlin.com.intellij.lexer;

import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;

/* JADX INFO: loaded from: classes3.dex */
public interface DocCommentTokenTypes {
    IElementType badCharacter();

    IElementType commentData();

    IElementType commentEnd();

    IElementType commentLeadingAsterisks();

    IElementType commentStart();

    IElementType inlineTagEnd();

    IElementType inlineTagStart();

    IElementType space();

    TokenSet spaceCommentsTokenSet();

    IElementType tagName();

    IElementType tagValueComma();

    IElementType tagValueGT();

    IElementType tagValueLParen();

    IElementType tagValueLT();

    IElementType tagValueRParen();

    IElementType tagValueSharp();

    IElementType tagValueToken();
}
