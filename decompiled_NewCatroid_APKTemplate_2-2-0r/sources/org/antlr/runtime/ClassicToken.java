package org.antlr.runtime;

/* JADX INFO: loaded from: classes12.dex */
public class ClassicToken implements Token {
    protected int channel;
    protected int charPositionInLine;
    protected int index;
    protected int line;
    protected String text;
    protected int type;

    @Override // org.antlr.runtime.Token
    public CharStream getInputStream() {
        return null;
    }

    @Override // org.antlr.runtime.Token
    public void setInputStream(CharStream charStream) {
    }

    public ClassicToken(int i) {
        this.channel = 0;
        this.type = i;
    }

    public ClassicToken(Token token) {
        this.channel = 0;
        this.text = token.getText();
        this.type = token.getType();
        this.line = token.getLine();
        this.charPositionInLine = token.getCharPositionInLine();
        this.channel = token.getChannel();
    }

    public ClassicToken(int i, String str) {
        this.channel = 0;
        this.type = i;
        this.text = str;
    }

    public ClassicToken(int i, String str, int i2) {
        this.type = i;
        this.text = str;
        this.channel = i2;
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
        return this.text;
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

    @Override // org.antlr.runtime.Token
    public int getTokenIndex() {
        return this.index;
    }

    @Override // org.antlr.runtime.Token
    public void setTokenIndex(int i) {
        this.index = i;
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
        return "[@" + getTokenIndex() + ",'" + strReplaceAll + "',<" + this.type + ">" + str + "," + this.line + ":" + getCharPositionInLine() + "]";
    }
}
