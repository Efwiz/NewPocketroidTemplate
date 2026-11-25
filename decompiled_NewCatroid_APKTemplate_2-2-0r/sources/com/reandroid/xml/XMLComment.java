package com.reandroid.xml;

import com.reandroid.xml.base.Comment;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes11.dex */
public class XMLComment extends XMLNode implements Comment {
    private String text;

    public XMLComment(String str) {
        this();
        setText(str);
    }

    public XMLComment() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.reandroid.xml.XMLNode
    public XMLComment newCopy(XMLNode xMLNode) {
        XMLComment xMLComment = new XMLComment();
        xMLComment.setText(getText());
        return xMLComment;
    }

    @Override // com.reandroid.xml.base.Comment
    public void setText(String str) {
        this.text = str;
    }

    @Override // com.reandroid.xml.base.Comment
    public String getText() {
        return getText(false);
    }

    public String getText(boolean z) {
        String str = this.text;
        return z ? XMLUtil.escapeXmlChars(str) : str;
    }

    @Override // com.reandroid.xml.base.XmlSerializable
    public void serialize(XmlSerializer xmlSerializer) throws IOException {
        xmlSerializer.comment(getText(false));
    }

    @Override // com.reandroid.xml.XMLNode, com.reandroid.xml.base.XmlReader
    public void parse(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        if (xmlPullParser.getEventType() != 9) {
            throw new XmlPullParserException("Invalid event, expecting COMMENT but found " + xmlPullParser.getEventType());
        }
        setText(xmlPullParser.getText());
        xmlPullParser.next();
    }

    @Override // com.reandroid.xml.XMLNode
    void write(Appendable appendable, boolean z, boolean z2) throws IOException {
        appendable.append("<!-- ");
        appendable.append(getText(z2));
        appendable.append(" -->");
    }

    @Override // com.reandroid.xml.XMLNode
    int appendDebugText(Appendable appendable, int i, int i2) throws IOException {
        String text;
        if (i2 >= i || (text = getText()) == null) {
            return i2;
        }
        appendable.append(text);
        return i2 + text.length();
    }
}
