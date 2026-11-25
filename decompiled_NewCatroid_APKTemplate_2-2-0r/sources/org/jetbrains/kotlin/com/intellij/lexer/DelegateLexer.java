package org.jetbrains.kotlin.com.intellij.lexer;

import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
public class DelegateLexer extends LexerBase {
    protected final Lexer myDelegate;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 2 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "buffer";
        } else if (i != 2) {
            objArr[0] = MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX;
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/lexer/DelegateLexer";
        }
        if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/lexer/DelegateLexer";
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

    public DelegateLexer(Lexer lexer) {
        if (lexer == null) {
            $$$reportNull$$$0(0);
        }
        this.myDelegate = lexer;
    }

    public final Lexer getDelegate() {
        return this.myDelegate;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.Lexer
    public void start(CharSequence charSequence, int i, int i2, int i3) {
        if (charSequence == null) {
            $$$reportNull$$$0(1);
        }
        this.myDelegate.start(charSequence, i, i2, i3);
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.Lexer
    public int getState() {
        return this.myDelegate.getState();
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.Lexer
    public IElementType getTokenType() {
        return this.myDelegate.getTokenType();
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.Lexer
    public int getTokenStart() {
        return this.myDelegate.getTokenStart();
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.Lexer
    public int getTokenEnd() {
        return this.myDelegate.getTokenEnd();
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.Lexer
    public void advance() {
        this.myDelegate.advance();
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.Lexer
    public final CharSequence getBufferSequence() {
        CharSequence bufferSequence = this.myDelegate.getBufferSequence();
        if (bufferSequence == null) {
            $$$reportNull$$$0(2);
        }
        return bufferSequence;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.Lexer
    public int getBufferEnd() {
        return this.myDelegate.getBufferEnd();
    }
}
