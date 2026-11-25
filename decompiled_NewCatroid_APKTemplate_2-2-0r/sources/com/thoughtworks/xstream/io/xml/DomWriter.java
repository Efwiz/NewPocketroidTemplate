package com.thoughtworks.xstream.io.xml;

import com.thoughtworks.xstream.io.naming.NameCoder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/xml/DomWriter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class DomWriter extends AbstractDocumentWriter {
    private final Document document;
    private boolean hasRootElement;

    public DomWriter(Document document) {
        this(document, new XmlFriendlyNameCoder());
    }

    public DomWriter(Element element) {
        this(element, new XmlFriendlyNameCoder());
    }

    public DomWriter(Document document, NameCoder nameCoder) {
        this(document.getDocumentElement(), document, nameCoder);
    }

    public DomWriter(Element element, Document document, NameCoder nameCoder) {
        super(element, nameCoder);
        this.document = document;
        this.hasRootElement = document.getDocumentElement() != null;
    }

    public DomWriter(Element element, NameCoder nameCoder) {
        this(element, element.getOwnerDocument(), nameCoder);
    }

    public DomWriter(Document document, XmlFriendlyReplacer xmlFriendlyReplacer) {
        this(document.getDocumentElement(), document, (NameCoder) xmlFriendlyReplacer);
    }

    public DomWriter(Element element, Document document, XmlFriendlyReplacer xmlFriendlyReplacer) {
        this(element, document, (NameCoder) xmlFriendlyReplacer);
    }

    public DomWriter(Element element, XmlFriendlyReplacer xmlFriendlyReplacer) {
        this(element, element.getOwnerDocument(), (NameCoder) xmlFriendlyReplacer);
    }

    @Override // com.thoughtworks.xstream.io.xml.AbstractDocumentWriter
    protected Object createNode(String str) {
        Element elementCreateElement = this.document.createElement(encodeNode(str));
        if (top() != null) {
            top().appendChild(elementCreateElement);
        } else if (!this.hasRootElement) {
            this.document.appendChild(elementCreateElement);
            this.hasRootElement = true;
        }
        return elementCreateElement;
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamWriter
    public void addAttribute(String str, String str2) {
        top().setAttribute(encodeAttribute(str), str2);
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamWriter
    public void setValue(String str) {
        top().appendChild(this.document.createTextNode(str));
    }

    private Element top() {
        return (Element) getCurrent();
    }
}
