package org.jetbrains.kotlin.com.intellij.lexer;

/* JADX INFO: loaded from: classes3.dex */
public abstract class LexerBase extends Lexer {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "position", "org/jetbrains/kotlin/com/intellij/lexer/LexerBase", "restore"));
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.Lexer
    public LexerPosition getCurrentPosition() {
        return new LexerPositionImpl(getTokenStart(), getState());
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.Lexer
    public void restore(LexerPosition lexerPosition) {
        if (lexerPosition == null) {
            $$$reportNull$$$0(0);
        }
        start(getBufferSequence(), lexerPosition.getOffset(), getBufferEnd(), lexerPosition.getState());
    }
}
