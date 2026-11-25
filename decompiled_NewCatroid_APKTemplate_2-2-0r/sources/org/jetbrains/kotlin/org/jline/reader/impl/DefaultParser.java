package org.jetbrains.kotlin.org.jline.reader.impl;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import org.jetbrains.kotlin.org.jline.reader.EOFError;
import org.jetbrains.kotlin.org.jline.reader.ParsedLine;
import org.jetbrains.kotlin.org.jline.reader.Parser;

/* JADX INFO: loaded from: classes4.dex */
public class DefaultParser implements Parser {
    private boolean eofOnEscapedNewLine;
    private boolean eofOnUnclosedQuote;
    private char[] quoteChars = {'\'', '\"'};
    private char[] escapeChars = {'\\'};

    public boolean isQuoted(CharSequence charSequence, int i) {
        return false;
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.Parser
    public ParsedLine parse(String str, int i, Parser.ParseContext parseContext) {
        int size;
        LinkedList linkedList = new LinkedList();
        StringBuilder sb = new StringBuilder();
        int i2 = -1;
        int length = -1;
        int size2 = -1;
        for (int i3 = 0; str != null && i3 < str.length(); i3++) {
            if (i3 == i) {
                size2 = linkedList.size();
                length = sb.length();
            }
            if (i2 < 0 && isQuoteChar(str, i3)) {
                i2 = i3;
            } else if (i2 >= 0) {
                if (str.charAt(i2) == str.charAt(i3) && !isEscaped(str, i3)) {
                    linkedList.add(sb.toString());
                    sb.setLength(0);
                    i2 = -1;
                } else if (!isEscapeChar(str, i3)) {
                    sb.append(str.charAt(i3));
                }
            } else if (isDelimiter(str, i3)) {
                if (sb.length() > 0) {
                    linkedList.add(sb.toString());
                    sb.setLength(0);
                }
            } else if (!isEscapeChar(str, i3)) {
                sb.append(str.charAt(i3));
            }
        }
        if (sb.length() > 0 || i == str.length()) {
            linkedList.add(sb.toString());
        }
        if (i == str.length()) {
            size = linkedList.size() - 1;
            length = ((String) linkedList.get(linkedList.size() - 1)).length();
        } else {
            size = size2;
        }
        if (this.eofOnEscapedNewLine && isEscapeChar(str, str.length() - 1)) {
            throw new EOFError(-1, -1, "Escaped new line", "newline");
        }
        if (this.eofOnUnclosedQuote && i2 >= 0) {
            throw new EOFError(-1, -1, "Missing closing quote", str.charAt(i2) == '\'' ? "quote" : "dquote");
        }
        return new ArgumentList(str, linkedList, size, length, i);
    }

    public boolean isDelimiter(CharSequence charSequence, int i) {
        return (isQuoted(charSequence, i) || isEscaped(charSequence, i) || !isDelimiterChar(charSequence, i)) ? false : true;
    }

    public boolean isQuoteChar(CharSequence charSequence, int i) {
        if (i < 0) {
            return false;
        }
        int i2 = 0;
        while (true) {
            char[] cArr = this.quoteChars;
            if (cArr == null || i2 >= cArr.length) {
                break;
            }
            if (charSequence.charAt(i) == this.quoteChars[i2]) {
                return !isEscaped(charSequence, i);
            }
            i2++;
        }
        return false;
    }

    public boolean isEscapeChar(CharSequence charSequence, int i) {
        if (i < 0) {
            return false;
        }
        int i2 = 0;
        while (true) {
            char[] cArr = this.escapeChars;
            if (cArr == null || i2 >= cArr.length) {
                break;
            }
            if (charSequence.charAt(i) == this.escapeChars[i2]) {
                return !isEscaped(charSequence, i);
            }
            i2++;
        }
        return false;
    }

    public boolean isEscaped(CharSequence charSequence, int i) {
        if (i <= 0) {
            return false;
        }
        return isEscapeChar(charSequence, i - 1);
    }

    public boolean isDelimiterChar(CharSequence charSequence, int i) {
        return Character.isWhitespace(charSequence.charAt(i));
    }

    public static class ArgumentList implements ParsedLine {
        private final int cursor;
        private final String line;
        private final int wordCursor;
        private final int wordIndex;
        private final List<String> words;

        public ArgumentList(String str, List<String> list, int i, int i2, int i3) {
            this.line = str;
            this.words = Collections.unmodifiableList((List) Objects.requireNonNull(list));
            this.wordIndex = i;
            this.wordCursor = i2;
            this.cursor = i3;
        }

        @Override // org.jetbrains.kotlin.org.jline.reader.ParsedLine
        public String word() {
            int i = this.wordIndex;
            if (i < 0 || i >= this.words.size()) {
                return "";
            }
            return this.words.get(this.wordIndex);
        }

        @Override // org.jetbrains.kotlin.org.jline.reader.ParsedLine
        public int wordCursor() {
            return this.wordCursor;
        }
    }
}
