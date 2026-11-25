package org.antlr.stringtemplate;

import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.SystemProperties;

/* JADX INFO: loaded from: classes12.dex */
public class AutoIndentWriter implements StringTemplateWriter {
    protected int[] anchors;
    protected int anchors_sp;
    protected boolean atStartOfLine;
    protected int charPosition;
    protected int charPositionOfStartOfExpr;
    protected List indents;
    protected int lineWidth;
    protected String newline;
    protected Writer out;

    public AutoIndentWriter(Writer writer, String str) {
        ArrayList arrayList = new ArrayList();
        this.indents = arrayList;
        this.anchors = new int[10];
        this.anchors_sp = -1;
        this.atStartOfLine = true;
        this.charPosition = 0;
        this.lineWidth = -1;
        this.charPositionOfStartOfExpr = 0;
        this.out = writer;
        arrayList.add(null);
        this.newline = str;
    }

    public AutoIndentWriter(Writer writer) {
        this(writer, System.getProperty(SystemProperties.LINE_SEPARATOR));
    }

    @Override // org.antlr.stringtemplate.StringTemplateWriter
    public void setLineWidth(int i) {
        this.lineWidth = i;
    }

    @Override // org.antlr.stringtemplate.StringTemplateWriter
    public void pushIndentation(String str) {
        int i;
        int indentationWidth = getIndentationWidth();
        int i2 = this.anchors_sp;
        if (i2 >= 0 && (i = this.anchors[i2]) > indentationWidth) {
            StringBuffer indentString = getIndentString(i - indentationWidth);
            if (str != null) {
                indentString.append(str);
            }
            this.indents.add(indentString.toString());
            return;
        }
        this.indents.add(str);
    }

    @Override // org.antlr.stringtemplate.StringTemplateWriter
    public String popIndentation() {
        List list = this.indents;
        return (String) list.remove(list.size() - 1);
    }

    @Override // org.antlr.stringtemplate.StringTemplateWriter
    public void pushAnchorPoint() {
        int i = this.anchors_sp + 1;
        int[] iArr = this.anchors;
        if (i >= iArr.length) {
            int[] iArr2 = new int[iArr.length * 2];
            System.arraycopy(iArr, 0, iArr2, 0, iArr.length - 1);
            this.anchors = iArr2;
        }
        int i2 = this.anchors_sp + 1;
        this.anchors_sp = i2;
        this.anchors[i2] = this.charPosition;
    }

    @Override // org.antlr.stringtemplate.StringTemplateWriter
    public void popAnchorPoint() {
        this.anchors_sp--;
    }

    public int getIndentationWidth() {
        int length = 0;
        for (int i = 0; i < this.indents.size(); i++) {
            String str = (String) this.indents.get(i);
            if (str != null) {
                length += str.length();
            }
        }
        return length;
    }

    @Override // org.antlr.stringtemplate.StringTemplateWriter
    public int write(String str) throws IOException {
        int i;
        int i2 = 0;
        int length = 0;
        while (i2 < str.length()) {
            char cCharAt = str.charAt(i2);
            if (cCharAt == '\r' || cCharAt == '\n') {
                this.atStartOfLine = true;
                this.charPosition = -1;
                length += this.newline.length();
                this.out.write(this.newline);
                this.charPosition += length;
                if (cCharAt == '\r' && (i = i2 + 1) < str.length() && str.charAt(i) == '\n') {
                    i2 = i;
                }
            } else {
                if (this.atStartOfLine) {
                    length += indent();
                    this.atStartOfLine = false;
                }
                length++;
                this.out.write(cCharAt);
                this.charPosition++;
            }
            i2++;
        }
        return length;
    }

    @Override // org.antlr.stringtemplate.StringTemplateWriter
    public int writeSeparator(String str) throws IOException {
        return write(str);
    }

    @Override // org.antlr.stringtemplate.StringTemplateWriter
    public int write(String str, String str2) throws IOException {
        return writeWrapSeparator(str2) + write(str);
    }

    @Override // org.antlr.stringtemplate.StringTemplateWriter
    public int writeWrapSeparator(String str) throws IOException {
        int i = this.lineWidth;
        if (i == -1 || str == null || this.atStartOfLine || this.charPosition < i) {
            return 0;
        }
        int iIndent = 0;
        for (int i2 = 0; i2 < str.length(); i2++) {
            char cCharAt = str.charAt(i2);
            if (cCharAt == '\n') {
                this.out.write(cCharAt);
                this.charPosition = 0;
                iIndent = iIndent + 1 + indent();
            } else {
                iIndent++;
                this.out.write(cCharAt);
                this.charPosition++;
            }
        }
        return iIndent;
    }

    public int indent() throws IOException {
        int length = 0;
        for (int i = 0; i < this.indents.size(); i++) {
            String str = (String) this.indents.get(i);
            if (str != null) {
                length += str.length();
                this.out.write(str);
            }
        }
        this.charPosition += length;
        return length;
    }

    public int indent(int i) throws IOException {
        for (int i2 = 1; i2 <= i; i2++) {
            this.out.write(32);
        }
        this.charPosition += i;
        return i;
    }

    protected StringBuffer getIndentString(int i) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 1; i2 <= i; i2++) {
            stringBuffer.append(StringUtil.SPACE);
        }
        return stringBuffer;
    }
}
