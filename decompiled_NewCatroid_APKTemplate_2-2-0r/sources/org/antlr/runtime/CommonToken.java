package org.antlr.runtime;

import java.io.Serializable;

/* JADX INFO: loaded from: classes12.dex */
public class CommonToken implements Token, Serializable {
    protected int channel;
    protected int charPositionInLine;
    protected int index;
    protected transient CharStream input;
    protected int line;
    protected int start;
    protected int stop;
    protected String text;
    protected int type;

    public CommonToken(int i) {
        this.charPositionInLine = -1;
        this.channel = 0;
        this.index = -1;
        this.type = i;
    }

    public CommonToken(CharStream charStream, int i, int i2, int i3, int i4) {
        this.charPositionInLine = -1;
        this.index = -1;
        this.input = charStream;
        this.type = i;
        this.channel = i2;
        this.start = i3;
        this.stop = i4;
    }

    public CommonToken(int i, String str) {
        this.charPositionInLine = -1;
        this.index = -1;
        this.type = i;
        this.channel = 0;
        this.text = str;
    }

    public CommonToken(Token token) {
        this.charPositionInLine = -1;
        this.channel = 0;
        this.index = -1;
        this.text = token.getText();
        this.type = token.getType();
        this.line = token.getLine();
        this.index = token.getTokenIndex();
        this.charPositionInLine = token.getCharPositionInLine();
        this.channel = token.getChannel();
        this.input = token.getInputStream();
        if (token instanceof CommonToken) {
            CommonToken commonToken = (CommonToken) token;
            this.start = commonToken.start;
            this.stop = commonToken.stop;
        }
    }

    @Override // org.antlr.runtime.Token
    public int getType() {
        return this.type;
    }

    @Override // org.antlr.runtime.Token
    public void setLine(int i) {
        this.line = i;
    }

    @Override // org.antlr.runtime.Token
    public String getText() {
        int i;
        String str = this.text;
        if (str != null) {
            return str;
        }
        CharStream charStream = this.input;
        if (charStream == null) {
            return null;
        }
        int size = charStream.size();
        int i2 = this.start;
        if (i2 < size && (i = this.stop) < size) {
            return this.input.substring(i2, i);
        }
        return "<EOF>";
    }

    @Override // org.antlr.runtime.Token
    public void setText(String str) {
        this.text = str;
    }

    @Override // org.antlr.runtime.Token
    public int getLine() {
        return this.line;
    }

    @Override // org.antlr.runtime.Token
    public int getCharPositionInLine() {
        return this.charPositionInLine;
    }

    @Override // org.antlr.runtime.Token
    public void setCharPositionInLine(int i) {
        this.charPositionInLine = i;
    }

    @Override // org.antlr.runtime.Token
    public int getChannel() {
        return this.channel;
    }

    @Override // org.antlr.runtime.Token
    public void setChannel(int i) {
        this.channel = i;
    }

    @Override // org.antlr.runtime.Token
    public void setType(int i) {
        this.type = i;
    }

    public int getStartIndex() {
        return this.start;
    }

    public void setStartIndex(int i) {
        this.start = i;
    }

    public int getStopIndex() {
        return this.stop;
    }

    public void setStopIndex(int i) {
        this.stop = i;
    }

    @Override // org.antlr.runtime.Token
    public int getTokenIndex() {
        return this.index;
    }

    @Override // org.antlr.runtime.Token
    public void setTokenIndex(int i) {
        this.index = i;
    }

    @Override // org.antlr.runtime.Token
    public CharStream getInputStream() {
        return this.input;
    }

    @Override // org.antlr.runtime.Token
    public void setInputStream(CharStream charStream) {
        this.input = charStream;
    }

    public String toString() {
        String str;
        String strReplaceAll;
        if (this.channel <= 0) {
            str = "";
        } else {
            str = ",channel=" + this.channel;
        }
        String text = getText();
        if (text != null) {
            strReplaceAll = text.replaceAll("\n", "\\\\n").replaceAll("\r", "\\\\r").replaceAll("\t", "\\\\t");
        } else {
            strReplaceAll = "<no text>";
        }
        return "[@" + getTokenIndex() + "," + this.start + ":" + this.stop + "='" + strReplaceAll + "',<" + this.type + ">" + str + "," + this.line + ":" + getCharPositionInLine() + "]";
    }
}
