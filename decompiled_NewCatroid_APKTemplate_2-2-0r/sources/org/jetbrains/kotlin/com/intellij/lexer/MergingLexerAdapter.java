package org.jetbrains.kotlin.com.intellij.lexer;

import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;

/* JADX INFO: loaded from: classes3.dex */
public class MergingLexerAdapter extends MergingLexerAdapterBase {
    private final MergeFunction myMergeFunction;
    private final TokenSet myTokenSet;

    public MergingLexerAdapter(Lexer lexer, TokenSet tokenSet) {
        super(lexer);
        this.myMergeFunction = new MyMergeFunction();
        this.myTokenSet = tokenSet;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.MergingLexerAdapterBase
    public MergeFunction getMergeFunction() {
        return this.myMergeFunction;
    }

    private class MyMergeFunction implements MergeFunction {
        private MyMergeFunction() {
        }

        @Override // org.jetbrains.kotlin.com.intellij.lexer.MergeFunction
        public IElementType merge(IElementType iElementType, Lexer lexer) {
            if (!MergingLexerAdapter.this.myTokenSet.contains(iElementType)) {
                return iElementType;
            }
            while (lexer.getTokenType() == iElementType) {
                lexer.advance();
            }
            return iElementType;
        }
    }
}
