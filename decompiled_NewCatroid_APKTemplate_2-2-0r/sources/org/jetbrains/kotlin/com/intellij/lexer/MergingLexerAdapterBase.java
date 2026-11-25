package org.jetbrains.kotlin.com.intellij.lexer;

import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
public abstract class MergingLexerAdapterBase extends DelegateLexer {
    private int myState;
    private int myTokenStart;
    private IElementType myTokenType;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "buffer";
        } else {
            objArr[0] = "position";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/lexer/MergingLexerAdapterBase";
        if (i != 1) {
            objArr[2] = "start";
        } else {
            objArr[2] = "restore";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public abstract MergeFunction getMergeFunction();

    public MergingLexerAdapterBase(Lexer lexer) {
        super(lexer);
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.DelegateLexer, org.jetbrains.kotlin.com.intellij.lexer.Lexer
    public void start(CharSequence charSequence, int i, int i2, int i3) {
        if (charSequence == null) {
            $$$reportNull$$$0(0);
        }
        super.start(charSequence, i, i2, i3);
        this.myTokenType = null;
        this.myState = 0;
        this.myTokenStart = 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.DelegateLexer, org.jetbrains.kotlin.com.intellij.lexer.Lexer
    public int getState() {
        if (this.myTokenType == null) {
            locateToken();
        }
        return this.myState;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.DelegateLexer, org.jetbrains.kotlin.com.intellij.lexer.Lexer
    public IElementType getTokenType() {
        if (this.myTokenType == null) {
            locateToken();
        }
        return this.myTokenType;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.DelegateLexer, org.jetbrains.kotlin.com.intellij.lexer.Lexer
    public int getTokenStart() {
        if (this.myTokenType == null) {
            locateToken();
        }
        return this.myTokenStart;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.DelegateLexer, org.jetbrains.kotlin.com.intellij.lexer.Lexer
    public int getTokenEnd() {
        if (this.myTokenType == null) {
            locateToken();
        }
        return super.getTokenStart();
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.DelegateLexer, org.jetbrains.kotlin.com.intellij.lexer.Lexer
    public void advance() {
        this.myTokenType = null;
        this.myState = 0;
        this.myTokenStart = 0;
    }

    private void locateToken() {
        if (this.myTokenType == null) {
            Lexer delegate = getDelegate();
            this.myTokenType = delegate.getTokenType();
            this.myTokenStart = delegate.getTokenStart();
            this.myState = delegate.getState();
            if (this.myTokenType == null) {
                return;
            }
            delegate.advance();
            this.myTokenType = getMergeFunction().merge(this.myTokenType, delegate);
        }
    }

    public Lexer getOriginal() {
        return getDelegate();
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.LexerBase, org.jetbrains.kotlin.com.intellij.lexer.Lexer
    public void restore(LexerPosition lexerPosition) {
        if (lexerPosition == null) {
            $$$reportNull$$$0(1);
        }
        MyLexerPosition myLexerPosition = (MyLexerPosition) lexerPosition;
        getDelegate().restore(myLexerPosition.getOriginalPosition());
        this.myTokenType = myLexerPosition.getType();
        this.myTokenStart = myLexerPosition.getOffset();
        this.myState = myLexerPosition.getOldState();
    }

    public String toString() {
        return getClass().getName() + "[" + getDelegate() + "]";
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.LexerBase, org.jetbrains.kotlin.com.intellij.lexer.Lexer
    public LexerPosition getCurrentPosition() {
        return new MyLexerPosition(this.myTokenStart, this.myTokenType, getDelegate().getCurrentPosition(), this.myState);
    }

    private static class MyLexerPosition implements LexerPosition {
        private final int myOffset;
        private final int myOldState;
        private final LexerPosition myOriginalPosition;
        private final IElementType myTokenType;

        MyLexerPosition(int i, IElementType iElementType, LexerPosition lexerPosition, int i2) {
            this.myOffset = i;
            this.myTokenType = iElementType;
            this.myOriginalPosition = lexerPosition;
            this.myOldState = i2;
        }

        @Override // org.jetbrains.kotlin.com.intellij.lexer.LexerPosition
        public int getOffset() {
            return this.myOffset;
        }

        @Override // org.jetbrains.kotlin.com.intellij.lexer.LexerPosition
        public int getState() {
            return this.myOriginalPosition.getState();
        }

        public IElementType getType() {
            return this.myTokenType;
        }

        public LexerPosition getOriginalPosition() {
            return this.myOriginalPosition;
        }

        public int getOldState() {
            return this.myOldState;
        }
    }
}
