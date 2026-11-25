package org.antlr.runtime;

/* JADX INFO: loaded from: classes12.dex */
public interface TokenStream extends IntStream {
    Token LT(int i);

    Token get(int i);

    TokenSource getTokenSource();

    int range();

    String toString(int i, int i2);

    String toString(Token token, Token token2);
}
