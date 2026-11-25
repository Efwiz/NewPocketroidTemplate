package org.jetbrains.kotlin.com.intellij.lexer;

import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProcessCanceledException;
import org.jetbrains.kotlin.com.intellij.psi.TokenType;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
public class FlexAdapter extends LexerBase {
    private static final Logger LOG = Logger.getInstance((Class<?>) FlexAdapter.class);
    private int myBufferEnd;
    private boolean myFailed;
    private final FlexLexer myFlex;
    private int myState;
    private CharSequence myText;
    private int myTokenEnd;
    private int myTokenStart;
    private IElementType myTokenType;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 2 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "buffer";
        } else if (i != 2) {
            objArr[0] = "flex";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/lexer/FlexAdapter";
        }
        if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/lexer/FlexAdapter";
        } else {
            objArr[1] = "getBufferSequence";
        }
        if (i == 1) {
            objArr[2] = "start";
        } else if (i != 2) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 2) {
            throw new IllegalStateException(str2);
        }
    }

    public FlexAdapter(FlexLexer flexLexer) {
        if (flexLexer == null) {
            $$$reportNull$$$0(0);
        }
        this.myFlex = flexLexer;
    }

    public FlexLexer getFlex() {
        return this.myFlex;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.Lexer
    public void start(CharSequence charSequence, int i, int i2, int i3) {
        if (charSequence == null) {
            $$$reportNull$$$0(1);
        }
        this.myText = charSequence;
        this.myTokenEnd = i;
        this.myTokenStart = i;
        this.myBufferEnd = i2;
        this.myFlex.reset(charSequence, i, i2, i3);
        this.myTokenType = null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.Lexer
    public int getState() {
        locateToken();
        return this.myState;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.Lexer
    public IElementType getTokenType() {
        locateToken();
        return this.myTokenType;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.Lexer
    public int getTokenStart() {
        locateToken();
        return this.myTokenStart;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.Lexer
    public int getTokenEnd() {
        locateToken();
        return this.myTokenEnd;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.Lexer
    public void advance() {
        locateToken();
        this.myTokenType = null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.Lexer
    public CharSequence getBufferSequence() {
        CharSequence charSequence = this.myText;
        if (charSequence == null) {
            $$$reportNull$$$0(2);
        }
        return charSequence;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.Lexer
    public int getBufferEnd() {
        return this.myBufferEnd;
    }

    protected void locateToken() {
        if (this.myTokenType != null) {
            return;
        }
        this.myTokenStart = this.myTokenEnd;
        if (this.myFailed) {
            return;
        }
        try {
            this.myState = this.myFlex.yystate();
            this.myTokenType = this.myFlex.advance();
            this.myTokenEnd = this.myFlex.getTokenEnd();
        } catch (ProcessCanceledException e) {
            throw e;
        } catch (Throwable th) {
            this.myFailed = true;
            this.myTokenType = TokenType.BAD_CHARACTER;
            this.myTokenEnd = this.myBufferEnd;
            LOG.warn(this.myFlex.getClass().getName(), th);
        }
    }

    public String toString() {
        return "FlexAdapter for " + this.myFlex.getClass().getName();
    }
}
