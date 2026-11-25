package io.github.rosemoe.sora.lang.completion.snippet.parser;

import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;

/* JADX INFO: loaded from: classes11.dex */
public enum TokenType {
    Dollar('$'),
    Colon(':'),
    Comma(','),
    CurlyOpen('{'),
    CurlyClose('}'),
    Backslash('\\'),
    Forwardslash('/'),
    Pipe('|'),
    Int,
    VariableName,
    Format,
    Plus('+'),
    Dash('-'),
    QuestionMark(MangleConstant.Q_MARK),
    Backtick('`'),
    EOF;

    private final char target;

    TokenType() {
        this((char) 0);
    }

    TokenType(char c) {
        this.target = c;
    }

    public char getTargetCharacter() {
        return this.target;
    }
}
