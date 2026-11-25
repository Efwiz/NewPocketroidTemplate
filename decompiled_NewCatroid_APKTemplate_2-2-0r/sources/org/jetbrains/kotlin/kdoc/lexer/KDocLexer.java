package org.jetbrains.kotlin.kdoc.lexer;

import org.jetbrains.kotlin.com.intellij.lexer.FlexAdapter;
import org.jetbrains.kotlin.com.intellij.lexer.MergingLexerAdapter;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;

/* JADX INFO: loaded from: classes5.dex */
public class KDocLexer extends MergingLexerAdapter {
    private static final TokenSet KDOC_TOKENS = TokenSet.create(KDocTokens.TEXT, KDocTokens.CODE_BLOCK_TEXT);

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KDocLexer() {
        super(new FlexAdapter(new _KDocLexer(null)), KDOC_TOKENS);
    }
}
