package com.thoughtworks.xstream.io.xml;

import com.thoughtworks.xstream.io.naming.NameCoder;
import java.util.List;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/xml/JDom2Reader.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class JDom2Reader extends AbstractDocumentReader {
    private Element currentElement;

    public JDom2Reader(Element element) {
        super(element);
    }

    public JDom2Reader(Document document) {
        super(document.getRootElement());
    }

    public JDom2Reader(Element element, NameCoder nameCoder) {
        super(element, nameCoder);
    }

    public JDom2Reader(Document document, NameCoder nameCoder) {
        super(document.getRootElement(), nameCoder);
    }

    @Override // com.thoughtworks.xstream.io.xml.AbstractDocumentReader
    protected void reassignCurrentElement(Object obj) {
        this.currentElement = (Element) obj;
    }

    @Override // com.thoughtworks.xstream.io.xml.AbstractDocumentReader
    protected Object getParent() {
        return this.currentElement.getParentElement();
    }

    @Override // com.thoughtworks.xstream.io.xml.AbstractDocumentReader
    protected Object getChild(int i) {
        return this.currentElement.getChildren().get(i);
    }

    @Override // com.thoughtworks.xstream.io.xml.AbstractDocumentReader
    protected int getChildCount() {
        return this.currentElement.getChildren().size();
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamReader
    public String getNodeName() {
        return decodeNode(this.currentElement.getName());
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamReader
    public String getValue() {
        return this.currentElement.getText();
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamReader
    public String getAttribute(String str) {
        return this.currentElement.getAttributeValue(encodeAttribute(str));
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamReader
    public String getAttribute(int i) {
        return ((Attribute) this.currentElement.getAttributes().get(i)).getValue();
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamReader
    public int getAttributeCount() {
        return this.currentElement.getAttributes().size();
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamReader
    public String getAttributeName(int i) {
        return decodeAttribute(((Attribute) this.currentElement.getAttributes().get(i)).getQualifiedName());
    }

    @Override // com.thoughtworks.xstream.io.AbstractReader, com.thoughtworks.xstream.io.ExtendedHierarchicalStreamReader
    public String peekNextChild() {
        List children = this.currentElement.getChildren();
        if (children == null || children.isEmpty()) {
            return null;
        }
        return decodeNode(((Element) children.get(0)).getName());
    }
}
