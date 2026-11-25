package org.antlr.runtime;

import org.antlr.runtime.misc.LookaheadStream;

/* JADX INFO: loaded from: classes12.dex */
public class UnbufferedTokenStream extends LookaheadStream<Token> implements TokenStream {
    protected TokenSource tokenSource;
    protected int tokenIndex = 0;
    protected int channel = 0;

    @Override // org.antlr.runtime.misc.LookaheadStream, org.antlr.runtime.TokenStream
    public /* bridge */ /* synthetic */ Token LT(int i) {
        return (Token) super.LT(i);
    }

    public UnbufferedTokenStream(TokenSource tokenSource) {
        this.tokenSource = tokenSource;
    }

    @Override // org.antlr.runtime.misc.LookaheadStream
    public Token nextElement() {
        Token tokenNextToken = this.tokenSource.nextToken();
        int i = this.tokenIndex;
        this.tokenIndex = i + 1;
        tokenNextToken.setTokenIndex(i);
        return tokenNextToken;
    }

    @Override // org.antlr.runtime.misc.LookaheadStream
    public boolean isEOF(Token token) {
        return token.getType() == -1;
    }

    @Override // org.antlr.runtime.TokenStream
    public TokenSource getTokenSource() {
        return this.tokenSource;
    }

    @Override // org.antlr.runtime.TokenStream
    public String toString(int i, int i2) {
        return "n/a";
    }

    @Override // org.antlr.runtime.TokenStream
    public String toString(Token token, Token token2) {
        return "n/a";
    }

    @Override // org.antlr.runtime.IntStream
    public int LA(int i) {
        return ((Token) LT(i)).getType();
    }

    @Override // org.antlr.runtime.TokenStream
    public Token get(int i) {
        throw new UnsupportedOperationException("Absolute token indexes are meaningless in an unbuffered stream");
    }

    @Override // org.antlr.runtime.IntStream
    public String getSourceName() {
        return this.tokenSource.getSourceName();
    }
}
