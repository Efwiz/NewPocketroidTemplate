package com.reandroid.xml;

import com.reandroid.common.Namespace;
import com.reandroid.utils.collection.CollectionUtil;
import com.reandroid.xml.base.Attribute;
import com.reandroid.xml.base.Comment;
import com.reandroid.xml.base.Document;
import com.reandroid.xml.base.Element;
import com.reandroid.xml.base.Text;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes11.dex */
public class XMLDocument extends XMLNodeTree implements Document<XMLElement> {
    private String encoding;
    private Boolean standalone;

    @Override // com.reandroid.xml.XMLNodeTree, com.reandroid.xml.base.NodeFactory
    public /* bridge */ /* synthetic */ Attribute newAttribute() {
        return super.newAttribute();
    }

    @Override // com.reandroid.xml.XMLNodeTree, com.reandroid.xml.base.NodeFactory
    public /* bridge */ /* synthetic */ Comment newComment() {
        return super.newComment();
    }

    @Override // com.reandroid.xml.XMLNodeTree, com.reandroid.xml.base.NodeFactory
    public /* bridge */ /* synthetic */ Element newElement() {
        return super.newElement();
    }

    @Override // com.reandroid.xml.base.NodeFactory
    public /* bridge */ /* synthetic */ Namespace newNamespace(String str, String str2) {
        return super.newNamespace(str, str2);
    }

    @Override // com.reandroid.xml.XMLNodeTree, com.reandroid.xml.base.NodeFactory
    public /* bridge */ /* synthetic */ Text newText() {
        return super.newText();
    }

    public XMLDocument(String str) {
        this();
        setDocumentElement(new XMLElement(str));
    }

    public XMLDocument() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.reandroid.xml.XMLNode
    public XMLDocument newCopy(XMLNode xMLNode) {
        XMLDocument xMLDocument = new XMLDocument();
        xMLDocument.encoding = this.encoding;
        xMLDocument.standalone = this.standalone;
        Iterator<XMLNode> it = iterator();
        while (it.hasNext()) {
            it.next().newCopy(xMLDocument);
        }
        return xMLDocument;
    }

    @Override // com.reandroid.xml.base.Document
    public XMLElement getDocumentElement() {
        return (XMLElement) CollectionUtil.getFirst(iterator(XMLElement.class));
    }

    @Override // com.reandroid.xml.base.Document
    public void setDocumentElement(XMLElement xMLElement) {
        clear();
        add((XMLNode) xMLElement);
    }

    public void setEncoding(String str) {
        this.encoding = str;
    }

    public void setStandalone(Boolean bool) {
        this.standalone = bool;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.reandroid.xml.XMLNode, com.reandroid.xml.base.XmlReader
    public void parse(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        this.encoding = null;
        this.standalone = null;
        clear();
        int eventType = xmlPullParser.getEventType();
        if (eventType == 0) {
            this.encoding = xmlPullParser.getInputEncoding();
            XMLUtil.ensureStartTag(xmlPullParser);
        } else if (eventType == 3 || eventType == 2) {
            xmlPullParser.next();
        } else if (eventType == 1) {
            return;
        }
        XMLUtil.ensureStartTag(xmlPullParser);
        XMLElement xMLElementNewElement = newElement();
        add((XMLNode) xMLElementNewElement);
        xMLElementNewElement.parse(xmlPullParser);
    }

    public void parseInner(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        this.encoding = null;
        this.standalone = null;
        clear();
        int eventType = xmlPullParser.getEventType();
        if (eventType == 0) {
            this.encoding = xmlPullParser.getInputEncoding();
            eventType = XMLUtil.ensureStartTag(xmlPullParser);
        }
        if (eventType == 1) {
            return;
        }
        if (eventType != 2) {
            throw new XmlPullParserException("Invalid document event: " + eventType);
        }
        xmlPullParser.next();
        parseAll(xmlPullParser);
    }

    private void parseAll(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        int eventType = xmlPullParser.getEventType();
        while (eventType != 3 && eventType != 1) {
            XMLNode xMLNodeCreateChildNode = createChildNode(eventType);
            if (xMLNodeCreateChildNode != null) {
                add(xMLNodeCreateChildNode);
                xMLNodeCreateChildNode.parse(xmlPullParser);
                eventType = xmlPullParser.getEventType();
            } else {
                eventType = xmlPullParser.nextToken();
            }
        }
    }

    @Override // com.reandroid.xml.XMLNodeTree
    void startSerialize(XmlSerializer xmlSerializer) throws IOException {
        String str = this.encoding;
        if (str == null) {
            return;
        }
        xmlSerializer.startDocument(str, this.standalone);
    }

    @Override // com.reandroid.xml.XMLNodeTree
    void endSerialize(XmlSerializer xmlSerializer) {
        if (this.encoding == null) {
            return;
        }
        try {
            xmlSerializer.endDocument();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void appendDocument(Appendable appendable, boolean z) throws IOException {
        if (this.encoding == null || !z) {
            return;
        }
        appendable.append("<?xml version='1.0' encoding='");
        appendable.append(this.encoding);
        appendable.append("'?>");
    }

    @Override // com.reandroid.xml.XMLNode
    void write(Appendable appendable, boolean z, boolean z2) throws IOException {
        appendDocument(appendable, z);
        getDocumentElement().write(appendable, z, z2);
    }

    @Override // com.reandroid.xml.XMLNode
    int appendDebugText(Appendable appendable, int i, int i2) throws IOException {
        if (i2 > i) {
            return i2;
        }
        Iterator<XMLNode> it = iterator();
        while (it.hasNext() && i2 < i) {
            i2 = it.next().appendDebugText(appendable, i, i2);
        }
        return i2;
    }

    public static XMLDocument load(String str) throws XmlPullParserException, IOException {
        XMLDocument xMLDocument = new XMLDocument();
        xMLDocument.parse(XMLFactory.newPullParser(str));
        return xMLDocument;
    }

    public static XMLDocument load(InputStream inputStream) throws XmlPullParserException, IOException {
        XMLDocument xMLDocument = new XMLDocument();
        xMLDocument.parse(XMLFactory.newPullParser(inputStream));
        return xMLDocument;
    }

    public static XMLDocument load(File file) throws XmlPullParserException, IOException {
        XMLDocument xMLDocument = new XMLDocument();
        xMLDocument.parse(XMLFactory.newPullParser(file));
        return xMLDocument;
    }

    XMLNode createChildNode(int i) {
        if (i == 2) {
            return newElement();
        }
        if (XMLText.isTextEvent(i)) {
            return newText();
        }
        return null;
    }
}
