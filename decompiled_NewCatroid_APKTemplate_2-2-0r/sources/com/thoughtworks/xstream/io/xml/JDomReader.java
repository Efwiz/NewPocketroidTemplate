package com.thoughtworks.xstream.io.xml;

import com.thoughtworks.xstream.io.naming.NameCoder;
import java.util.List;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/xml/JDomReader.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class JDomReader extends AbstractDocumentReader {
    private Element currentElement;

    public JDomReader(Element element) {
        super(element);
    }

    public JDomReader(Document document) {
        super(document.getRootElement());
    }

    public JDomReader(Element element, NameCoder nameCoder) {
        super(element, nameCoder);
    }

    public JDomReader(Document document, NameCoder nameCoder) {
        super(document.getRootElement(), nameCoder);
    }

    public JDomReader(Element element, XmlFriendlyReplacer xmlFriendlyReplacer) {
        this(element, (NameCoder) xmlFriendlyReplacer);
    }

    public JDomReader(Document document, XmlFriendlyReplacer xmlFriendlyReplacer) {
        this(document.getRootElement(), (NameCoder) xmlFriendlyReplacer);
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
