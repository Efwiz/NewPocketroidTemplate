package org.antlr.runtime;

/* JADX INFO: loaded from: classes12.dex */
public interface CharStream extends IntStream {
    public static final int EOF = -1;

    int LT(int i);

    int getCharPositionInLine();

    int getLine();

    void setCharPositionInLine(int i);

    void setLine(int i);

    String substring(int i, int i2);
}
