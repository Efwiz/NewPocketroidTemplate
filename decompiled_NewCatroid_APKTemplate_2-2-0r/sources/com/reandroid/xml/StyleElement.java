package com.reandroid.xml;

import com.reandroid.utils.collection.IndexIterator;
import com.reandroid.utils.collection.SizedSupplier;
import com.reandroid.xml.base.Attribute;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes11.dex */
public class StyleElement extends XMLElement implements StyleNode, Span {
    @Override // com.reandroid.xml.XMLElement, com.reandroid.xml.XMLNodeTree, com.reandroid.xml.base.NodeFactory
    public XMLComment newComment() {
        return null;
    }

    @Override // com.reandroid.xml.Span
    public StyleElement toElement() {
        return this;
    }

    public StyleElement(String str) {
        super(str);
    }

    public StyleElement() {
        this("");
    }

    @Override // com.reandroid.xml.XMLElement
    public StyleElement getParentElement() {
        return (StyleElement) super.getParentElement();
    }

    void copyFrom(XMLElement xMLElement) {
        setName(xMLElement.getName());
        Iterator<? extends XMLAttribute> attributes = xMLElement.getAttributes();
        while (attributes.hasNext()) {
            addAttribute(new StyleAttribute(attributes.next()));
        }
        for (XMLNode xMLNode : xMLElement) {
            if (xMLNode instanceof XMLElement) {
                StyleElement styleElementNewElement = newElement();
                add((XMLNode) styleElementNewElement);
                styleElementNewElement.copyFrom((XMLElement) xMLNode);
            } else if (xMLNode instanceof XMLText) {
                add((XMLNode) new StyleText(((XMLText) xMLNode).getText()));
            }
        }
    }

    @Override // com.reandroid.xml.XMLElement, com.reandroid.xml.base.Element
    public StyleAttribute getAttributeAt(int i) {
        return (StyleAttribute) super.getAttributeAt(i);
    }

    public void addAttribute(StyleAttribute styleAttribute) {
        super.addAttribute((Attribute) styleAttribute);
    }

    public void addAttributes(Collection<StyleAttribute> collection) {
        if (collection != null) {
            Iterator<StyleAttribute> it = collection.iterator();
            while (it.hasNext()) {
                addAttribute(it.next());
            }
        }
    }

    @Override // com.reandroid.xml.XMLElement
    public StyleAttribute getAttribute(String str) {
        return (StyleAttribute) super.getAttribute(str);
    }

    public void addElement(StyleElement styleElement) {
        add((XMLNode) styleElement);
    }

    @Override // com.reandroid.xml.XMLElement
    public Iterator<StyleElement> getElements() {
        return iterator(StyleElement.class);
    }

    @Override // com.reandroid.xml.XMLElement, com.reandroid.xml.base.Element
    public Iterator<StyleAttribute> getAttributes() {
        return new IndexIterator(new SizedSupplier<StyleAttribute>() { // from class: com.reandroid.xml.StyleElement.1
            @Override // com.reandroid.utils.collection.SizedItem
            public int size() {
                return StyleElement.this.getAttributeCount();
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.reandroid.utils.collection.SizedSupplier
            public StyleAttribute get(int i) {
                return StyleElement.this.getAttributeAt(i);
            }
        });
    }

    public String getTagString() {
        return getTagName() + getSpanAttributes();
    }

    @Override // com.reandroid.xml.Span
    public String getTagName() {
        return getName();
    }

    @Override // com.reandroid.xml.Span
    public int getFirstChar() {
        XMLNode next;
        Iterator<XMLNode> itRecursiveNodes = ((XMLNodeTree) getRootParentNode()).recursiveNodes();
        int textLength = 0;
        while (itRecursiveNodes.hasNext() && (next = itRecursiveNodes.next()) != this) {
            textLength += next.getTextLength();
        }
        return textLength;
    }

    @Override // com.reandroid.xml.Span
    public int getLastChar() {
        int firstChar = getFirstChar() + getLength();
        return firstChar != 0 ? firstChar - 1 : firstChar;
    }

    @Override // com.reandroid.xml.Span
    public int getSpanOrder() {
        XMLNode next;
        Iterator<XMLNode> itRecursiveNodes = ((XMLNodeTree) getRootParentNode()).recursiveNodes();
        int i = 0;
        while (itRecursiveNodes.hasNext() && (next = itRecursiveNodes.next()) != this) {
            if (next instanceof StyleElement) {
                i++;
            }
        }
        return i;
    }

    @Override // com.reandroid.xml.Span
    public String getSpanAttributes() {
        if (getAttributeCount() == 0) {
            return "";
        }
        StringWriter stringWriter = new StringWriter();
        try {
            appendAttributes(stringWriter, false, false);
            stringWriter.flush();
            stringWriter.close();
        } catch (IOException unused) {
        }
        return stringWriter.toString();
    }

    @Override // com.reandroid.xml.XMLNode
    int getLength() {
        Iterator<XMLNode> it = iterator();
        int length = 0;
        while (it.hasNext()) {
            length += it.next().getLength();
        }
        return length;
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

    @Override // com.reandroid.xml.StyleNode
    public StyleNode getParentStyle() {
        return (StyleNode) getParentNode();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.reandroid.xml.StyleNode
    public void addStyleNode(StyleNode styleNode) {
        add((XMLNode) styleNode);
    }

    @Override // com.reandroid.xml.XMLElement, com.reandroid.xml.XMLNodeTree
    void startSerialize(XmlSerializer xmlSerializer) throws IOException {
        xmlSerializer.startTag(null, getName());
        Iterator<StyleAttribute> attributes = getAttributes();
        while (attributes.hasNext()) {
            attributes.next().serialize(xmlSerializer);
        }
    }

    @Override // com.reandroid.xml.XMLElement, com.reandroid.xml.XMLNodeTree
    void endSerialize(XmlSerializer xmlSerializer) throws IOException {
        xmlSerializer.endTag(null, getName());
    }

    @Override // com.reandroid.xml.XMLElement, com.reandroid.xml.XMLNode, com.reandroid.xml.base.XmlReader
    public void parse(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        if (xmlPullParser.getEventType() != 2) {
            throw new XmlPullParserException("Not START_TAG event");
        }
        setName(xmlPullParser.getName());
        int attributeCount = xmlPullParser.getAttributeCount();
        for (int i = 0; i < attributeCount; i++) {
            addAttribute(new StyleAttribute(xmlPullParser.getAttributeName(i), xmlPullParser.getAttributeValue(i)));
        }
        int next = xmlPullParser.next();
        while (next != 3 && next != 1) {
            if (next == 2) {
                StyleElement styleElement = new StyleElement();
                addElement(styleElement);
                styleElement.parse(xmlPullParser);
            } else if (XMLText.isTextEvent(next)) {
                StyleText styleTextNewText = newText();
                add((XMLNode) styleTextNewText);
                styleTextNewText.parse(xmlPullParser);
            } else {
                xmlPullParser.next();
            }
            next = xmlPullParser.getEventType();
        }
        if (xmlPullParser.getEventType() == 3) {
            xmlPullParser.next();
        }
    }

    @Override // com.reandroid.xml.XMLElement, com.reandroid.xml.base.Element, com.reandroid.xml.base.NodeFactory
    public StyleElement newElement() {
        return new StyleElement();
    }

    @Override // com.reandroid.xml.XMLElement, com.reandroid.xml.base.NodeFactory
    public StyleText newText() {
        return new StyleText();
    }

    @Override // com.reandroid.xml.XMLElement, com.reandroid.xml.base.NodeFactory
    public StyleAttribute newAttribute() {
        return new StyleAttribute();
    }

    @Override // com.reandroid.xml.XMLNode
    public String toString() {
        return "[" + getFirstChar() + ", " + getLastChar() + "] " + getTagString();
    }
}
