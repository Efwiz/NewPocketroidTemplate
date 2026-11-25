package com.reandroid.xml;

import com.huawei.hms.framework.common.ContainerUtils;
import com.reandroid.xml.base.Text;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes11.dex */
public class XMLText extends XMLNode implements Text {
    private String text;

    static boolean isTextEvent(int i) {
        return i == 4 || i == 6;
    }

    public XMLText(String str) {
        this.text = str;
    }

    public XMLText() {
        this(null);
    }

    @Override // com.reandroid.xml.XMLNode, com.reandroid.xml.base.Node, com.reandroid.xml.base.Attribute
    public XMLElement getParentNode() {
        return (XMLElement) super.getParentNode();
    }

    @Override // com.reandroid.xml.XMLNode
    XMLNode newCopy(XMLNode xMLNode) {
        if (xMLNode instanceof XMLNodeTree) {
            XMLNodeTree xMLNodeTree = (XMLNodeTree) xMLNode;
            XMLText xMLTextNewText = xMLNodeTree.newText();
            xMLTextNewText.setText(this.text);
            xMLNodeTree.add((XMLNode) xMLTextNewText);
            return xMLTextNewText;
        }
        return new XMLText(this.text);
    }

    @Override // com.reandroid.xml.base.Text
    public String getText() {
        return getText(false);
    }

    public String getText(boolean z) {
        if (z) {
            return XMLUtil.escapeXmlChars(this.text);
        }
        return this.text;
    }

    @Override // com.reandroid.xml.base.Text
    public void setText(String str) {
        this.text = str;
    }

    public void appendText(char c) {
        if (c == 0) {
            return;
        }
        appendText(String.valueOf(c));
    }

    public void appendText(String str) {
        if (str == null) {
            return;
        }
        String str2 = this.text;
        if (str2 == null || str2.length() == 0) {
            this.text = str;
        } else {
            this.text += str;
        }
    }

    private void appendEntityRef(String str) {
        String str2;
        if (str == null) {
            return;
        }
        if (str.equals("lt")) {
            str2 = "<";
        } else if (str.equals("gt")) {
            str2 = ">";
        } else if (str.equals("amp")) {
            str2 = ContainerUtils.FIELD_DELIMITER;
        } else if (!str.equals("quote")) {
            return;
        } else {
            str2 = "\"";
        }
        appendText(str2);
    }

    @Override // com.reandroid.xml.base.XmlSerializable
    public void serialize(XmlSerializer xmlSerializer) throws IOException {
        xmlSerializer.text(getText());
    }

    @Override // com.reandroid.xml.XMLNode, com.reandroid.xml.base.XmlReader
    public void parse(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        int eventType = xmlPullParser.getEventType();
        if (!isTextEvent(eventType)) {
            throw new XmlPullParserException("Not text event");
        }
        while (isTextEvent(eventType)) {
            if (eventType == 4) {
                appendText(xmlPullParser.getText());
            } else if (eventType == 6) {
                appendEntityRef(xmlPullParser.getName());
            }
            eventType = xmlPullParser.next();
        }
    }

    @Override // com.reandroid.xml.XMLNode
    void write(Appendable appendable, boolean z, boolean z2) throws IOException {
        String text = getText(z2);
        if (text != null) {
            appendable.append(text);
        }
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

    boolean isIndent() {
        return isIndentText(getText());
    }

    private static boolean isIndentText(String str) {
        if (str != null && str.length() != 0) {
            if (str.charAt(0) != '\n') {
                return false;
            }
            char[] charArray = str.toCharArray();
            for (int i = 1; i < charArray.length; i++) {
                char c = charArray[i];
                if (c != ' ' && c != '\n') {
                    return false;
                }
            }
        }
        return true;
    }
}
