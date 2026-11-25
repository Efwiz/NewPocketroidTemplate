package org.antlr.runtime.debug;

import org.antlr.runtime.Token;
import org.antlr.runtime.TokenSource;
import org.antlr.runtime.TokenStream;

/* JADX INFO: loaded from: classes12.dex */
public class DebugTokenStream implements TokenStream {
    protected DebugEventListener dbg;
    protected boolean initialStreamState = true;
    public TokenStream input;
    protected int lastMarker;

    @Override // org.antlr.runtime.IntStream
    public void release(int i) {
    }

    public DebugTokenStream(TokenStream tokenStream, DebugEventListener debugEventListener) {
        this.input = tokenStream;
        setDebugListener(debugEventListener);
        tokenStream.LT(1);
    }

    public void setDebugListener(DebugEventListener debugEventListener) {
        this.dbg = debugEventListener;
    }

    @Override // org.antlr.runtime.IntStream
    public void consume() {
        if (this.initialStreamState) {
            consumeInitialHiddenTokens();
        }
        int iIndex = this.input.index();
        Token tokenLT = this.input.LT(1);
        this.input.consume();
        int iIndex2 = this.input.index();
        this.dbg.consumeToken(tokenLT);
        int i = iIndex + 1;
        if (iIndex2 > i) {
            while (i < iIndex2) {
                this.dbg.consumeHiddenToken(this.input.get(i));
                i++;
            }
        }
    }

    protected void consumeInitialHiddenTokens() {
        int iIndex = this.input.index();
        for (int i = 0; i < iIndex; i++) {
            this.dbg.consumeHiddenToken(this.input.get(i));
        }
        this.initialStreamState = false;
    }

    @Override // org.antlr.runtime.TokenStream
    public Token LT(int i) {
        if (this.initialStreamState) {
            consumeInitialHiddenTokens();
        }
        this.dbg.LT(i, this.input.LT(i));
        return this.input.LT(i);
    }

    @Override // org.antlr.runtime.IntStream
    public int LA(int i) {
        if (this.initialStreamState) {
            consumeInitialHiddenTokens();
        }
        this.dbg.LT(i, this.input.LT(i));
        return this.input.LA(i);
    }

    @Override // org.antlr.runtime.TokenStream
    public Token get(int i) {
        return this.input.get(i);
    }

    @Override // org.antlr.runtime.IntStream
    public int mark() {
        int iMark = this.input.mark();
        this.lastMarker = iMark;
        this.dbg.mark(iMark);
        return this.lastMarker;
    }

    @Override // org.antlr.runtime.IntStream
    public int index() {
        return this.input.index();
    }

    @Override // org.antlr.runtime.TokenStream
    public int range() {
        return this.input.range();
    }

    @Override // org.antlr.runtime.IntStream
    public void rewind(int i) {
        this.dbg.rewind(i);
        this.input.rewind(i);
    }

    @Override // org.antlr.runtime.IntStream
    public void rewind() {
        this.dbg.rewind();
        this.input.rewind(this.lastMarker);
    }

    @Override // org.antlr.runtime.IntStream
    public void seek(int i) {
        this.input.seek(i);
    }

    @Override // org.antlr.runtime.IntStream
    public int size() {
        return this.input.size();
    }

    @Override // org.antlr.runtime.TokenStream
    public TokenSource getTokenSource() {
        return this.input.getTokenSource();
    }

    @Override // org.antlr.runtime.IntStream
    public String getSourceName() {
        return getTokenSource().getSourceName();
    }

    public String toString() {
        return this.input.toString();
    }

    @Override // org.antlr.runtime.TokenStream
    public String toString(int i, int i2) {
        return this.input.toString(i, i2);
    }

    @Override // org.antlr.runtime.TokenStream
    public String toString(Token token, Token token2) {
        return this.input.toString(token, token2);
    }
}
