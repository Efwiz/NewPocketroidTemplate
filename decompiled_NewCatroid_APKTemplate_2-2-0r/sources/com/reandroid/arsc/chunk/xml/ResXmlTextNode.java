package com.reandroid.arsc.chunk.xml;

import com.huawei.hms.framework.common.ContainerUtils;
import com.reandroid.arsc.coder.XmlSanitizer;
import com.reandroid.arsc.refactor.ResourceMergeOption;
import com.reandroid.json.JSONObject;
import com.reandroid.xml.XMLNode;
import com.reandroid.xml.XMLText;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import name.antonsmirnov.firmata.FormatHelper;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes10.dex */
public class ResXmlTextNode extends ResXmlNode {
    public static final String NAME_text = "text";
    private String mIndentText;
    private final ResXmlText resXmlText;

    static boolean isTextEvent(int i) {
        return i == 4 || i == 6;
    }

    public ResXmlTextNode(ResXmlText resXmlText) {
        super(1);
        this.resXmlText = resXmlText;
        addChild(0, resXmlText);
    }

    public ResXmlTextNode() {
        this(new ResXmlText());
    }

    void makeIndent(int i) {
        if (!isIndent()) {
            throw new IllegalArgumentException("Not indent text: '" + getText() + FormatHelper.QUOTE);
        }
        if (i < 2) {
            setText("\n");
            return;
        }
        char[] cArr = new char[i];
        cArr[0] = '\n';
        for (int i2 = 1; i2 < i; i2++) {
            cArr[i2] = StringUtil.SPACE;
        }
        setText(new String(cArr));
    }

    public boolean isIndent() {
        return isIndent(getText());
    }

    ResXmlText getResXmlText() {
        return this.resXmlText;
    }

    public int getLineNumber() {
        return getResXmlText().getLineNumber();
    }

    @Override // com.reandroid.arsc.chunk.xml.ResXmlNode
    int autoSetLineNumber(int i) {
        int i2;
        String text = getText();
        if (isIndent(text) && isNextElement()) {
            i2 = i;
            i++;
        } else {
            i2 = i;
            for (char c : text.toCharArray()) {
                if (c == '\n') {
                    i2++;
                }
            }
        }
        setLineNumber(i);
        return i2;
    }

    private boolean isNextElement() {
        ResXmlElement parentResXmlElement = getParentResXmlElement();
        if (parentResXmlElement != null) {
            return parentResXmlElement.get(getIndex() + 1) instanceof ResXmlElement;
        }
        return false;
    }

    public String getComment() {
        return getResXmlText().getComment();
    }

    @Override // com.reandroid.arsc.chunk.xml.ResXmlNode
    public int getDepth() {
        ResXmlElement parentResXmlElement = getParentResXmlElement();
        if (parentResXmlElement != null) {
            return parentResXmlElement.getDepth() + 1;
        }
        return 0;
    }

    @Override // com.reandroid.arsc.chunk.xml.ResXmlNode
    void addEvents(ParserEventList parserEventList) {
        String comment = getComment();
        if (comment != null) {
            parserEventList.add(new ParserEvent(9, this, comment, false));
        }
        parserEventList.add(new ParserEvent(4, this));
    }

    public ResXmlElement getParentResXmlElement() {
        return getResXmlText().getParentResXmlElement();
    }

    public void setLineNumber(int i) {
        getResXmlText().setLineNumber(i);
    }

    public String getText() {
        return getResXmlText().getText();
    }

    public void setText(String str) {
        getResXmlText().setText(str);
        this.mIndentText = null;
    }

    public void append(String str) {
        String text = getText();
        if (text == null || text.length() == 0) {
            text = this.mIndentText;
        }
        if (text == null && isIndent(str)) {
            this.mIndentText = str;
            return;
        }
        if (text != null) {
            str = text + str;
        }
        setText(str);
    }

    @Override // com.reandroid.arsc.base.Block
    public boolean isNull() {
        return getResXmlText().isNull();
    }

    @Override // com.reandroid.arsc.chunk.xml.ResXmlNode
    void onRemoved() {
        getResXmlText().onRemoved();
    }

    @Override // com.reandroid.arsc.chunk.xml.ResXmlNode
    void linkStringReferences() {
        getResXmlText().linkStringReferences();
    }

    @Override // com.reandroid.arsc.chunk.xml.ResXmlNode
    public void serialize(XmlSerializer xmlSerializer, boolean z) throws IOException {
        if (isNull()) {
            return;
        }
        xmlSerializer.text(getText());
    }

    @Override // com.reandroid.arsc.chunk.xml.ResXmlNode
    public void parse(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        String strUnEscapeUnQuote;
        setLineNumber(xmlPullParser.getLineNumber());
        int eventType = xmlPullParser.getEventType();
        if (eventType == 6) {
            strUnEscapeUnQuote = decodeEntityRef(xmlPullParser.getText());
        } else if (eventType == 4) {
            strUnEscapeUnQuote = XmlSanitizer.unEscapeUnQuote(xmlPullParser.getText());
        } else {
            throw new XmlPullParserException("Invalid text event: " + eventType + ", " + xmlPullParser.getPositionDescription());
        }
        append(strUnEscapeUnQuote);
    }

    @Override // com.reandroid.arsc.chunk.xml.ResXmlNode
    public XMLNode toXml(boolean z) {
        return new XMLText(getText());
    }

    public void mergeWithName(ResourceMergeOption resourceMergeOption, ResXmlTextNode resXmlTextNode) {
        setText(resXmlTextNode.getText());
    }

    @Override // com.reandroid.json.JSONConvert
    public JSONObject toJson() {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(ResXmlNode.NAME_node_type, "text");
        jSONObject.put("text", getText());
        return jSONObject;
    }

    @Override // com.reandroid.json.JSONConvert
    public void fromJson(JSONObject jSONObject) {
        setText(jSONObject.optString("text", null));
        setLineNumber(getParentResXmlElement().getStartLineNumber());
    }

    public XMLText decodeToXml() {
        XMLText xMLText = new XMLText(XmlSanitizer.escapeSpecialCharacter(getText()));
        xMLText.setLineNumber(getLineNumber());
        return xMLText;
    }

    public String toString() {
        return "line = " + getLineNumber() + ", \"" + getText() + "\"";
    }

    private static String decodeEntityRef(String str) {
        if (str == null) {
            return "";
        }
        if (str.equals("lt")) {
            return "<";
        }
        if (str.equals("gt")) {
            return ">";
        }
        if (str.equals("amp")) {
            return ContainerUtils.FIELD_DELIMITER;
        }
        if (str.equals("quote")) {
            return "\"";
        }
        return ContainerUtils.FIELD_DELIMITER + str + ";";
    }

    private static boolean isIndent(String str) {
        if (str != null && str.length() != 0) {
            char[] charArray = str.toCharArray();
            if (charArray[0] != '\n') {
                return false;
            }
            for (int i = 1; i < charArray.length; i++) {
                if (charArray[i] != ' ') {
                    return false;
                }
            }
        }
        return true;
    }
}
