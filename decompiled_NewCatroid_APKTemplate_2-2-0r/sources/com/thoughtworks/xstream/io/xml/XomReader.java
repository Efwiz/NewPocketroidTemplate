package com.thoughtworks.xstream.io.xml;

import com.thoughtworks.xstream.io.naming.NameCoder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.Text;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/xml/XomReader.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class XomReader extends AbstractDocumentReader {
    private Element currentElement;

    public XomReader(Element element) {
        super(element);
    }

    public XomReader(Document document) {
        super(document.getRootElement());
    }

    public XomReader(Element element, NameCoder nameCoder) {
        super(element, nameCoder);
    }

    public XomReader(Document document, NameCoder nameCoder) {
        super(document.getRootElement(), nameCoder);
    }

    public XomReader(Element element, XmlFriendlyReplacer xmlFriendlyReplacer) {
        this(element, (NameCoder) xmlFriendlyReplacer);
    }

    public XomReader(Document document, XmlFriendlyReplacer xmlFriendlyReplacer) {
        this(document.getRootElement(), (NameCoder) xmlFriendlyReplacer);
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamReader
    public String getNodeName() {
        return decodeNode(this.currentElement.getLocalName());
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamReader
    public String getValue() {
        StringBuffer stringBuffer = new StringBuffer();
        int childCount = this.currentElement.getChildCount();
        for (int i = 0; i < childCount; i++) {
            Text child = this.currentElement.getChild(i);
            if (child instanceof Text) {
                stringBuffer.append(child.getValue());
            }
        }
        return stringBuffer.toString();
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamReader
    public String getAttribute(String str) {
        return this.currentElement.getAttributeValue(encodeAttribute(str));
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamReader
    public String getAttribute(int i) {
        return this.currentElement.getAttribute(i).getValue();
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamReader
    public int getAttributeCount() {
        return this.currentElement.getAttributeCount();
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamReader
    public String getAttributeName(int i) {
        return decodeAttribute(this.currentElement.getAttribute(i).getQualifiedName());
    }

    @Override // com.thoughtworks.xstream.io.xml.AbstractDocumentReader
    protected int getChildCount() {
        return this.currentElement.getChildElements().size();
    }

    @Override // com.thoughtworks.xstream.io.xml.AbstractDocumentReader
    protected Object getParent() {
        return this.currentElement.getParent();
    }

    @Override // com.thoughtworks.xstream.io.xml.AbstractDocumentReader
    protected Object getChild(int i) {
        return this.currentElement.getChildElements().get(i);
    }

    @Override // com.thoughtworks.xstream.io.xml.AbstractDocumentReader
    protected void reassignCurrentElement(Object obj) {
        this.currentElement = (Element) obj;
    }

    @Override // com.thoughtworks.xstream.io.AbstractReader, com.thoughtworks.xstream.io.ExtendedHierarchicalStreamReader
    public String peekNextChild() {
        Elements childElements = this.currentElement.getChildElements();
        if (childElements == null || childElements.size() == 0) {
            return null;
        }
        return decodeNode(childElements.get(0).getLocalName());
    }
}
