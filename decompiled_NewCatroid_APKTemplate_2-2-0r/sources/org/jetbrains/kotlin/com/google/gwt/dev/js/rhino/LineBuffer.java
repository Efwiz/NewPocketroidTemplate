package org.jetbrains.kotlin.com.google.gwt.dev.js.rhino;

import java.io.IOException;
import java.io.Reader;

/* JADX INFO: loaded from: classes3.dex */
final class LineBuffer {
    private int columnno;
    private Reader in;
    private int lineStart;
    private int lineno;
    private int otherEnd;
    private char[] otherBuffer = null;
    private char[] buffer = null;
    private int offset = 0;
    private int end = 0;
    private int prevColumnno = 0;
    private int otherStart = 0;
    private int prevStart = 0;
    private boolean lastWasCR = false;
    private boolean hitEOF = false;

    LineBuffer(Reader reader, CodePosition codePosition) {
        this.lineStart = 0;
        this.in = reader;
        this.lineno = codePosition.getLine();
        this.columnno = codePosition.getOffset();
        this.lineStart = -codePosition.getOffset();
    }

    int read() throws IOException {
        char c;
        do {
            if (this.end == this.offset && !fill()) {
                return -1;
            }
            char[] cArr = this.buffer;
            int i = this.offset;
            c = cArr[i];
            int i2 = i + 1;
            this.offset = i2;
            int i3 = this.columnno;
            int i4 = i3 + 1;
            this.columnno = i4;
            this.prevColumnno = i3;
            if ((57296 & c) == 0) {
                if (c != '\n') {
                    if (c != '\r') {
                        if (c == 8232 || c == 8233) {
                        }
                    } else if (i2 != this.end) {
                        if (cArr[i2] == '\n') {
                            this.offset = i + 2;
                        }
                    } else {
                        this.lastWasCR = true;
                    }
                }
                this.prevStart = this.lineStart;
                this.lineStart = this.offset;
                this.lineno++;
                this.prevColumnno = i4;
                this.columnno = 0;
                return 10;
            }
            if (c < 128) {
                break;
            }
        } while (formatChar(c));
        return c;
    }

    void unread() {
        if (this.offset == 0 && !this.hitEOF) {
            Context.codeBug();
        }
        int i = this.offset;
        if (i == 0) {
            return;
        }
        int i2 = i - 1;
        this.offset = i2;
        this.columnno--;
        char c = this.buffer[i2];
        if ((57296 & c) == 0 && Utils.isEndOfLine(c)) {
            this.lineStart = this.prevStart;
            this.lineno--;
            this.columnno = this.prevColumnno;
        }
    }

    private void skipFormatChar() {
        if (!formatChar(this.buffer[this.offset])) {
            Context.codeBug();
        }
        int i = this.offset;
        if (i != 0) {
            char[] cArr = this.buffer;
            char c = cArr[i];
            cArr[i] = cArr[i - 1];
            cArr[i - 1] = c;
        } else {
            int i2 = this.otherEnd;
            if (i2 != 0) {
                char[] cArr2 = this.buffer;
                char c2 = cArr2[i];
                char[] cArr3 = this.otherBuffer;
                cArr2[i] = cArr3[i2 - 1];
                cArr3[i2 - 1] = c2;
            }
        }
        this.offset = i + 1;
        int i3 = this.columnno;
        this.columnno = i3 + 1;
        this.prevColumnno = i3;
    }

    int peek() throws IOException {
        while (true) {
            if (this.end == this.offset && !fill()) {
                return -1;
            }
            char c = this.buffer[this.offset];
            if ((57296 & c) == 0 && Utils.isEndOfLine(c)) {
                return 10;
            }
            if (c < 128 || !formatChar(c)) {
                return c;
            }
            skipFormatChar();
        }
    }

    boolean match(int i) throws IOException {
        if ((57296 & i) == 0 && Utils.isEndOfLine(i)) {
            Context.codeBug();
        }
        if (i >= 128 && formatChar(i)) {
            Context.codeBug();
        }
        while (true) {
            if (this.end == this.offset && !fill()) {
                return false;
            }
            char[] cArr = this.buffer;
            int i2 = this.offset;
            char c = cArr[i2];
            if (i == c) {
                this.offset = i2 + 1;
                int i3 = this.columnno;
                this.columnno = i3 + 1;
                this.prevColumnno = i3;
                return true;
            }
            if (c < 128 || !formatChar(c)) {
                return false;
            }
            skipFormatChar();
        }
    }

    int getOffset() {
        return this.offset - this.lineStart;
    }

    private boolean fill() throws IOException {
        if (this.end != this.offset) {
            Context.codeBug();
        }
        char[] cArr = this.buffer;
        char[] cArr2 = this.otherBuffer;
        this.buffer = cArr2;
        this.otherBuffer = cArr;
        if (cArr2 == null) {
            this.buffer = new char[256];
        }
        int i = this.lineStart;
        if (i >= 0) {
            this.otherStart = i;
        } else {
            this.otherStart = 0;
        }
        int i2 = this.end;
        this.otherEnd = i2;
        int i3 = cArr == null ? 0 : i - i2;
        this.lineStart = i3;
        this.prevStart = i3;
        this.offset = 0;
        Reader reader = this.in;
        char[] cArr3 = this.buffer;
        int i4 = reader.read(cArr3, 0, cArr3.length);
        this.end = i4;
        if (i4 < 0) {
            this.end = 0;
            this.hitEOF = true;
            return false;
        }
        if (this.lastWasCR) {
            if (this.buffer[0] == '\n') {
                this.offset++;
                int i5 = this.columnno;
                this.columnno = i5 + 1;
                this.prevColumnno = i5;
                if (i4 == 1) {
                    return fill();
                }
            }
            this.lineStart = this.offset;
            this.lastWasCR = false;
        }
        return true;
    }

    int getLineno() {
        return this.lineno;
    }

    int getColumnno() {
        return this.columnno;
    }

    private static boolean formatChar(int i) {
        return Character.getType((char) i) == 16;
    }
}
