package org.jetbrains.kotlin.com.intellij.lexer;

import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
public interface TokenList {
    int getTokenCount();

    int getTokenEnd(int i);

    int getTokenStart(int i);

    IElementType getTokenType(int i);

    CharSequence getTokenizedText();

    default Lexer asLexer() {
        return new WrappingLexer(this);
    }

    public static class WrappingLexer extends LexerBase {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private int myIndex;
        private final TokenList myTokens;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 1 ? 3 : 2];
            if (i != 1) {
                objArr[0] = "buffer";
            } else {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/lexer/TokenList$WrappingLexer";
            }
            if (i != 1) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/lexer/TokenList$WrappingLexer";
            } else {
                objArr[1] = "getBufferSequence";
            }
            if (i != 1) {
                objArr[2] = "start";
            }
            String str2 = String.format(str, objArr);
            if (i == 1) {
                throw new IllegalStateException(str2);
            }
        }

        WrappingLexer(TokenList tokenList) {
            this.myTokens = tokenList;
        }

        public TokenList getTokens() {
            return this.myTokens;
        }

        @Override // org.jetbrains.kotlin.com.intellij.lexer.Lexer
        public void start(CharSequence charSequence, int i, int i2, int i3) {
            if (charSequence == null) {
                $$$reportNull$$$0(0);
            }
            this.myIndex = 0;
        }

        @Override // org.jetbrains.kotlin.com.intellij.lexer.Lexer
        public int getState() {
            return this.myIndex;
        }

        @Override // org.jetbrains.kotlin.com.intellij.lexer.Lexer
        public IElementType getTokenType() {
            return this.myTokens.getTokenType(this.myIndex);
        }

        @Override // org.jetbrains.kotlin.com.intellij.lexer.Lexer
        public int getTokenStart() {
            return this.myTokens.getTokenStart(this.myIndex);
        }

        @Override // org.jetbrains.kotlin.com.intellij.lexer.Lexer
        public int getTokenEnd() {
            return this.myTokens.getTokenEnd(this.myIndex);
        }

        @Override // org.jetbrains.kotlin.com.intellij.lexer.Lexer
        public void advance() {
            this.myIndex++;
        }

        @Override // org.jetbrains.kotlin.com.intellij.lexer.Lexer
        public CharSequence getBufferSequence() {
            CharSequence tokenizedText = this.myTokens.getTokenizedText();
            if (tokenizedText == null) {
                $$$reportNull$$$0(1);
            }
            return tokenizedText;
        }

        @Override // org.jetbrains.kotlin.com.intellij.lexer.Lexer
        public int getBufferEnd() {
            return this.myTokens.getTokenizedText().length();
        }
    }
}
