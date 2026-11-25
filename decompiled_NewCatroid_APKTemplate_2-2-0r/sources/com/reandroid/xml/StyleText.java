package com.reandroid.xml;

import java.io.IOException;

/* JADX INFO: loaded from: classes11.dex */
public class StyleText extends XMLText implements StyleNode {
    @Override // com.reandroid.xml.XMLText
    boolean isIndent() {
        return false;
    }

    public StyleText(String str) {
        super(str);
    }

    public StyleText() {
        this("");
    }

    void writeStyledText(Appendable appendable) throws IOException {
        appendable.append(getText(false));
    }

    @Override // com.reandroid.xml.XMLNode
    public int getLength() {
        return getTextLength();
    }

    @Override // com.reandroid.xml.XMLNode
    public int getTextLength() {
        String text = getText();
        if (text != null) {
            return text.length();
        }
        return 0;
    }

    @Override // com.reandroid.xml.StyleNode
    public void appendChar(char c) {
        if (c == 0) {
            return;
        }
        appendText(c);
    }

    @Override // com.reandroid.xml.StyleNode
    public StyleNode getParentStyle() {
        return (StyleNode) getParentNode();
    }

    @Override // com.reandroid.xml.StyleNode
    public void addStyleNode(StyleNode styleNode) {
        throw new IllegalArgumentException("Text can't add node");
    }
}
