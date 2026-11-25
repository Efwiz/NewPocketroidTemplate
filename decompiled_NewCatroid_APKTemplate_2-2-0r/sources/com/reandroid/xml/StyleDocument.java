package com.reandroid.xml;

import com.reandroid.utils.collection.InstanceIterator;
import com.reandroid.utils.io.IOUtil;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes11.dex */
public class StyleDocument extends XMLDocument implements SpanSet<StyleElement>, StyleNode, Comparable<StyleDocument> {
    private static final XmlPullParser PARSER = XMLFactory.newPullParser();

    @Override // com.reandroid.xml.StyleNode
    public StyleNode getParentStyle() {
        return null;
    }

    @Override // com.reandroid.xml.XMLDocument, com.reandroid.xml.XMLNodeTree, com.reandroid.xml.base.NodeFactory
    public XMLComment newComment() {
        return null;
    }

    public boolean hasElements() {
        return getElements().hasNext();
    }

    public Iterator<StyleElement> getElements() {
        return iterator(StyleElement.class);
    }

    @Override // com.reandroid.xml.SpanSet
    public Iterator<StyleElement> getSpans() {
        return InstanceIterator.of(recursiveNodes(), StyleElement.class);
    }

    public Iterator<StyleText> getStyleTexts() {
        return InstanceIterator.of(recursiveNodes(), StyleText.class);
    }

    @Override // com.reandroid.xml.StyleNode
    public void appendChar(char c) {
        StyleText styleText;
        if (c == 0) {
            return;
        }
        XMLNode last = getLast();
        if (last instanceof StyleText) {
            styleText = (StyleText) last;
        } else {
            styleText = new StyleText();
            add((XMLNode) styleText);
        }
        styleText.appendChar(c);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.reandroid.xml.StyleNode
    public void addStyleNode(StyleNode styleNode) {
        add((XMLNode) styleNode);
    }

    public String getXml() {
        return toText(true, false);
    }

    public String getXml(boolean z) {
        return toText(true, z);
    }

    public String getHtml() {
        return getText(false, false);
    }

    public String getText(boolean z, boolean z2) {
        return toText(z, z2);
    }

    public String getStyledString() {
        StringWriter stringWriter = new StringWriter();
        try {
            writeStyledText(stringWriter);
            stringWriter.flush();
            stringWriter.close();
        } catch (IOException unused) {
        }
        return stringWriter.toString();
    }

    void writeStyledText(Appendable appendable) throws IOException {
        for (XMLNode xMLNode : this) {
            if (xMLNode instanceof StyleText) {
                ((StyleText) xMLNode).writeStyledText(appendable);
            } else if (xMLNode instanceof StyleElement) {
                ((StyleElement) xMLNode).writeStyledText(appendable);
            }
        }
    }

    public void parseString(String str) throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParser = PARSER;
        xmlPullParser.setInput(new StringReader("<parser>" + str + "</parser>"));
        parseInner(xmlPullParser);
        IOUtil.close(xmlPullParser);
    }

    @Override // com.reandroid.xml.XMLDocument, com.reandroid.xml.XMLNode
    void write(Appendable appendable, boolean z, boolean z2) throws IOException {
        appendDocument(appendable, z);
        appendChildes(iterator(), appendable, z, z2);
    }

    private void appendChildes(Iterator<XMLNode> it, Appendable appendable, boolean z, boolean z2) throws IOException {
        while (it.hasNext()) {
            it.next().write(appendable, z, z2);
        }
    }

    @Override // java.lang.Comparable
    public int compareTo(StyleDocument styleDocument) {
        if (styleDocument == null) {
            return 0;
        }
        return getStyledString().compareTo(styleDocument.getStyledString());
    }

    public int hashCode() {
        return getXml().hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof StyleDocument) {
            return getXml().equals(((StyleDocument) obj).getXml());
        }
        return false;
    }

    @Override // com.reandroid.xml.XMLDocument, com.reandroid.xml.XMLNodeTree, com.reandroid.xml.base.NodeFactory
    public StyleElement newElement() {
        return new StyleElement();
    }

    @Override // com.reandroid.xml.XMLDocument, com.reandroid.xml.XMLNodeTree, com.reandroid.xml.base.NodeFactory
    public StyleText newText() {
        return new StyleText();
    }

    @Override // com.reandroid.xml.XMLDocument, com.reandroid.xml.XMLNodeTree, com.reandroid.xml.base.NodeFactory
    public StyleAttribute newAttribute() {
        return new StyleAttribute();
    }

    public static StyleDocument parseNext(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        StyleDocument styleDocument = new StyleDocument();
        styleDocument.parseInner(xmlPullParser);
        return styleDocument;
    }

    public static StyleDocument copyInner(XMLElement xMLElement) {
        StyleDocument styleDocument = new StyleDocument();
        for (XMLNode xMLNode : xMLElement) {
            if (xMLNode instanceof XMLElement) {
                StyleElement styleElement = new StyleElement();
                styleDocument.add((XMLNode) styleElement);
                styleElement.copyFrom((XMLElement) xMLNode);
            } else if (xMLNode instanceof XMLText) {
                styleDocument.add((XMLNode) new StyleText(((XMLText) xMLNode).getText()));
            }
        }
        return styleDocument;
    }

    public static StyleDocument parseStyledString(String str) throws XmlPullParserException, IOException {
        StyleDocument styleDocument = new StyleDocument();
        styleDocument.parseString(str);
        return styleDocument;
    }
}
