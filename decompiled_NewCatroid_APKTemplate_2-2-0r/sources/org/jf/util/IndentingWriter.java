package org.jf.util;

import java.io.IOException;
import java.io.Writer;
import org.apache.commons.lang3.SystemProperties;

/* JADX INFO: loaded from: classes6.dex */
public class IndentingWriter extends Writer {
    private static final String newLine = System.getProperty(SystemProperties.LINE_SEPARATOR);
    protected final Writer writer;
    protected final char[] buffer = new char[24];
    protected int indentLevel = 0;
    private boolean beginningOfLine = true;
    private int lineNumber = 1;

    public IndentingWriter(Writer writer) {
        this.writer = writer;
    }

    protected void writeIndent() throws IOException {
        for (int i = 0; i < this.indentLevel; i++) {
            this.writer.write(32);
        }
    }

    public int getLineNumber() {
        return this.lineNumber;
    }

    @Override // java.io.Writer
    public void write(int i) throws IOException {
        if (i == 10) {
            this.writer.write(newLine);
            this.beginningOfLine = true;
            this.lineNumber++;
        } else {
            if (this.beginningOfLine) {
                writeIndent();
            }
            this.beginningOfLine = false;
            this.writer.write(i);
        }
    }

    private void writeLine(char[] cArr, int i, int i2) throws IOException {
        if (this.beginningOfLine && i2 > 0) {
            writeIndent();
            this.beginningOfLine = false;
        }
        this.writer.write(cArr, i, i2);
    }

    private void writeLine(String str, int i, int i2) throws IOException {
        if (this.beginningOfLine && i2 > 0) {
            writeIndent();
            this.beginningOfLine = false;
        }
        this.writer.write(str, i, i2);
    }

    @Override // java.io.Writer
    public void write(char[] cArr) throws IOException {
        write(cArr, 0, cArr.length);
    }

    @Override // java.io.Writer
    public void write(char[] cArr, int i, int i2) throws IOException {
        int i3 = i2 + i;
        while (true) {
            int i4 = i;
            while (true) {
                if (i < i3) {
                    if (cArr[i] == '\n') {
                        break;
                    } else {
                        i++;
                    }
                } else {
                    writeLine(cArr, i4, i - i4);
                    return;
                }
            }
            writeLine(cArr, i4, i - i4);
            this.writer.write(newLine);
            this.beginningOfLine = true;
            i++;
            this.lineNumber++;
        }
    }

    @Override // java.io.Writer
    public void write(String str) throws IOException {
        write(str, 0, str.length());
    }

    @Override // java.io.Writer
    public void write(String str, int i, int i2) throws IOException {
        int i3 = i2 + i;
        int i4 = i;
        while (i < i3) {
            i = str.indexOf(10, i4);
            if (i == -1 || i >= i3) {
                writeLine(str, i4, i3 - i4);
                return;
            }
            writeLine(str, i4, i - i4);
            this.writer.write(newLine);
            this.beginningOfLine = true;
            this.lineNumber++;
            i4 = i + 1;
        }
    }

    @Override // java.io.Writer, java.lang.Appendable
    public Writer append(CharSequence charSequence) throws IOException {
        write(charSequence.toString());
        return this;
    }

    @Override // java.io.Writer, java.lang.Appendable
    public Writer append(CharSequence charSequence, int i, int i2) throws IOException {
        write(charSequence.subSequence(i, i2).toString());
        return this;
    }

    @Override // java.io.Writer, java.lang.Appendable
    public Writer append(char c) throws IOException {
        write(c);
        return this;
    }

    @Override // java.io.Writer, java.io.Flushable
    public void flush() throws IOException {
        this.writer.flush();
    }

    @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.writer.close();
    }

    public void indent(int i) {
        int i2 = this.indentLevel + i;
        this.indentLevel = i2;
        if (i2 < 0) {
            this.indentLevel = 0;
        }
    }

    public void deindent(int i) {
        int i2 = this.indentLevel - i;
        this.indentLevel = i2;
        if (i2 < 0) {
            this.indentLevel = 0;
        }
    }
}
