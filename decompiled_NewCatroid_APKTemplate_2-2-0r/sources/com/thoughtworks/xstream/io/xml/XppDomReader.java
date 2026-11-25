package com.thoughtworks.xstream.io.xml;

import com.thoughtworks.xstream.io.naming.NameCoder;
import com.thoughtworks.xstream.io.xml.xppdom.XppDom;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/xml/XppDomReader.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class XppDomReader extends AbstractDocumentReader {
    private XppDom currentElement;

    public XppDomReader(XppDom xppDom) {
        super(xppDom);
    }

    public XppDomReader(XppDom xppDom, NameCoder nameCoder) {
        super(xppDom, nameCoder);
    }

    public XppDomReader(XppDom xppDom, XmlFriendlyReplacer xmlFriendlyReplacer) {
        this(xppDom, (NameCoder) xmlFriendlyReplacer);
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamReader
    public String getNodeName() {
        return decodeNode(this.currentElement.getName());
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamReader
    public String getValue() {
        String value;
        try {
            value = this.currentElement.getValue();
        } catch (Exception unused) {
            value = null;
        }
        return value == null ? "" : value;
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamReader
    public String getAttribute(String str) {
        return this.currentElement.getAttribute(encodeAttribute(str));
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamReader
    public String getAttribute(int i) {
        XppDom xppDom = this.currentElement;
        return xppDom.getAttribute(xppDom.getAttributeNames()[i]);
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamReader
    public int getAttributeCount() {
        return this.currentElement.getAttributeNames().length;
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamReader
    public String getAttributeName(int i) {
        return decodeAttribute(this.currentElement.getAttributeNames()[i]);
    }

    @Override // com.thoughtworks.xstream.io.xml.AbstractDocumentReader
    protected Object getParent() {
        return this.currentElement.getParent();
    }

    @Override // com.thoughtworks.xstream.io.xml.AbstractDocumentReader
    protected Object getChild(int i) {
        return this.currentElement.getChild(i);
    }

    @Override // com.thoughtworks.xstream.io.xml.AbstractDocumentReader
    protected int getChildCount() {
        return this.currentElement.getChildCount();
    }

    @Override // com.thoughtworks.xstream.io.xml.AbstractDocumentReader
    protected void reassignCurrentElement(Object obj) {
        this.currentElement = (XppDom) obj;
    }

    @Override // com.thoughtworks.xstream.io.AbstractReader, com.thoughtworks.xstream.io.ExtendedHierarchicalStreamReader
    public String peekNextChild() {
        if (this.currentElement.getChildCount() == 0) {
            return null;
        }
        return decodeNode(this.currentElement.getChild(0).getName());
    }
}
