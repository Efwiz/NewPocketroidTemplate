package org.jetbrains.kotlin.org.jline.reader;

/* JADX INFO: loaded from: classes4.dex */
public interface Parser {

    public enum ParseContext {
        UNSPECIFIED,
        ACCEPT_LINE,
        COMPLETE,
        SECONDARY_PROMPT
    }

    ParsedLine parse(String str, int i, ParseContext parseContext) throws SyntaxError;
}
