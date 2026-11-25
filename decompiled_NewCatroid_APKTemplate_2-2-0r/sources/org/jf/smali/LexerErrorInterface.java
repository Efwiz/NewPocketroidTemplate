package org.jf.smali;

import org.antlr.runtime.CharStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.RecognizerSharedState;

/* JADX INFO: loaded from: classes6.dex */
public interface LexerErrorInterface {
    int getNumberOfSyntaxErrors();

    public static abstract class ANTLRLexerWithErrorInterface extends Lexer implements LexerErrorInterface {
        public ANTLRLexerWithErrorInterface() {
        }

        public ANTLRLexerWithErrorInterface(CharStream charStream, RecognizerSharedState recognizerSharedState) {
            super(charStream, recognizerSharedState);
        }
    }
}
