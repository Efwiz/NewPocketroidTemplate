package org.jetbrains.kotlin.com.intellij.lexer;

import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
public class DummyLexer extends LexerBase {
    private CharSequence myBuffer;
    private int myEndOffset;
    private int myStartOffset;
    private final IElementType myTokenType;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/lexer/DummyLexer";
        } else if (i != 2) {
            objArr[0] = "buffer";
        } else {
            objArr[0] = "position";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/lexer/DummyLexer";
        } else {
            objArr[1] = "getBufferSequence";
        }
        if (i != 1) {
            if (i != 2) {
                objArr[2] = "start";
            } else {
                objArr[2] = "restore";
            }
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.Lexer
    public int getState() {
        return 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.LexerBase, org.jetbrains.kotlin.com.intellij.lexer.Lexer
    public void restore(LexerPosition lexerPosition) {
        if (lexerPosition == null) {
            $$$reportNull$$$0(2);
        }
    }

    public DummyLexer(IElementType iElementType) {
        this.myTokenType = iElementType;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.Lexer
    public void start(CharSequence charSequence, int i, int i2, int i3) {
        if (charSequence == null) {
            $$$reportNull$$$0(0);
        }
        this.myBuffer = charSequence;
        this.myStartOffset = i;
        this.myEndOffset = i2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.Lexer
    public CharSequence getBufferSequence() {
        CharSequence charSequence = this.myBuffer;
        if (charSequence == null) {
            $$$reportNull$$$0(1);
        }
        return charSequence;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.Lexer
    public IElementType getTokenType() {
        if (this.myStartOffset < this.myEndOffset) {
            return this.myTokenType;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.Lexer
    public int getTokenStart() {
        return this.myStartOffset;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.Lexer
    public int getTokenEnd() {
        return this.myEndOffset;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.Lexer
    public void advance() {
        this.myStartOffset = this.myEndOffset;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.LexerBase, org.jetbrains.kotlin.com.intellij.lexer.Lexer
    public LexerPosition getCurrentPosition() {
        return new LexerPositionImpl(0, getState());
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.Lexer
    public int getBufferEnd() {
        return this.myEndOffset;
    }
}
