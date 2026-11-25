package org.jetbrains.kotlin.com.intellij.lexer;

/* JADX INFO: loaded from: classes3.dex */
class LexerPositionImpl implements LexerPosition {
    private final int myOffset;
    private final int myState;

    LexerPositionImpl(int i, int i2) {
        this.myOffset = i;
        this.myState = i2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.LexerPosition
    public int getOffset() {
        return this.myOffset;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.LexerPosition
    public int getState() {
        return this.myState;
    }
}
