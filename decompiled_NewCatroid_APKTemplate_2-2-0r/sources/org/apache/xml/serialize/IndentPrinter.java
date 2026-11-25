package org.apache.xml.serialize;

import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

/* JADX INFO: loaded from: classes13.dex */
public class IndentPrinter extends Printer {
    private StringBuffer _line;
    private int _nextIndent;
    private int _spaces;
    private StringBuffer _text;
    private int _thisIndent;

    public IndentPrinter(Writer writer, OutputFormat outputFormat) {
        super(writer, outputFormat);
        this._line = new StringBuffer(80);
        this._text = new StringBuffer(20);
        this._spaces = 0;
        this._nextIndent = 0;
        this._thisIndent = 0;
    }

    @Override // org.apache.xml.serialize.Printer
    public void breakLine() {
        breakLine(false);
    }

    @Override // org.apache.xml.serialize.Printer
    public void breakLine(boolean z) {
        if (this._text.length() > 0) {
            while (this._spaces > 0) {
                this._line.append(StringUtil.SPACE);
                this._spaces--;
            }
            this._line.append(this._text);
            this._text = new StringBuffer(20);
        }
        flushLine(z);
        try {
            this._writer.write(this._format.getLineSeparator());
        } catch (IOException e) {
            if (this._exception == null) {
                this._exception = e;
            }
        }
    }

    @Override // org.apache.xml.serialize.Printer
    public void enterDTD() {
        if (this._dtdWriter == null) {
            this._line.append(this._text);
            this._text = new StringBuffer(20);
            flushLine(false);
            this._dtdWriter = new StringWriter();
            this._docWriter = this._writer;
            this._writer = this._dtdWriter;
        }
    }

    @Override // org.apache.xml.serialize.Printer
    public void flush() {
        if (this._line.length() > 0 || this._text.length() > 0) {
            breakLine();
        }
        try {
            this._writer.flush();
        } catch (IOException e) {
            if (this._exception == null) {
                this._exception = e;
            }
        }
    }

    @Override // org.apache.xml.serialize.Printer
    public void flushLine(boolean z) {
        if (this._line.length() > 0) {
            try {
                if (this._format.getIndenting() && !z) {
                    int lineWidth = this._thisIndent;
                    if (lineWidth * 2 > this._format.getLineWidth() && this._format.getLineWidth() > 0) {
                        lineWidth = this._format.getLineWidth() / 2;
                    }
                    while (lineWidth > 0) {
                        this._writer.write(32);
                        lineWidth--;
                    }
                }
                this._thisIndent = this._nextIndent;
                this._spaces = 0;
                this._writer.write(this._line.toString());
                this._line = new StringBuffer(40);
            } catch (IOException e) {
                if (this._exception == null) {
                    this._exception = e;
                }
            }
        }
    }

    @Override // org.apache.xml.serialize.Printer
    public int getNextIndent() {
        return this._nextIndent;
    }

    @Override // org.apache.xml.serialize.Printer
    public void indent() {
        this._nextIndent += this._format.getIndent();
    }

    @Override // org.apache.xml.serialize.Printer
    public String leaveDTD() {
        if (this._writer != this._dtdWriter) {
            return null;
        }
        this._line.append(this._text);
        this._text = new StringBuffer(20);
        flushLine(false);
        this._writer = this._docWriter;
        return this._dtdWriter.toString();
    }

    @Override // org.apache.xml.serialize.Printer
    public void printSpace() {
        if (this._text.length() > 0) {
            if (this._format.getLineWidth() > 0 && this._thisIndent + this._line.length() + this._spaces + this._text.length() > this._format.getLineWidth()) {
                flushLine(false);
                try {
                    this._writer.write(this._format.getLineSeparator());
                } catch (IOException e) {
                    if (this._exception == null) {
                        this._exception = e;
                    }
                }
            }
            while (this._spaces > 0) {
                this._line.append(StringUtil.SPACE);
                this._spaces--;
            }
            this._line.append(this._text);
            this._text = new StringBuffer(20);
        }
        this._spaces++;
    }

    @Override // org.apache.xml.serialize.Printer
    public void printText(char c) {
        this._text.append(c);
    }

    @Override // org.apache.xml.serialize.Printer
    public void printText(String str) {
        this._text.append(str);
    }

    @Override // org.apache.xml.serialize.Printer
    public void printText(StringBuffer stringBuffer) {
        this._text.append(stringBuffer.toString());
    }

    @Override // org.apache.xml.serialize.Printer
    public void printText(char[] cArr, int i, int i2) {
        this._text.append(cArr, i, i2);
    }

    @Override // org.apache.xml.serialize.Printer
    public void setNextIndent(int i) {
        this._nextIndent = i;
    }

    @Override // org.apache.xml.serialize.Printer
    public void setThisIndent(int i) {
        this._thisIndent = i;
    }

    @Override // org.apache.xml.serialize.Printer
    public void unindent() {
        int indent = this._nextIndent - this._format.getIndent();
        this._nextIndent = indent;
        if (indent < 0) {
            this._nextIndent = 0;
        }
        if (this._line.length() + this._spaces + this._text.length() == 0) {
            this._thisIndent = this._nextIndent;
        }
    }
}
