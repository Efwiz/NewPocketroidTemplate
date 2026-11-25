package com.thoughtworks.xstream.io.xml;

import com.thoughtworks.xstream.io.naming.NameCoder;
import org.dom4j.Branch;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/xml/Dom4JWriter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class Dom4JWriter extends AbstractDocumentWriter {
    private final DocumentFactory documentFactory;

    public Dom4JWriter(Branch branch, DocumentFactory documentFactory, NameCoder nameCoder) {
        super(branch, nameCoder);
        this.documentFactory = documentFactory;
    }

    public Dom4JWriter(DocumentFactory documentFactory, NameCoder nameCoder) {
        this((Branch) null, documentFactory, nameCoder);
    }

    public Dom4JWriter(Branch branch, NameCoder nameCoder) {
        this(branch, new DocumentFactory(), nameCoder);
    }

    public Dom4JWriter(Branch branch, DocumentFactory documentFactory, XmlFriendlyReplacer xmlFriendlyReplacer) {
        this(branch, documentFactory, (NameCoder) xmlFriendlyReplacer);
    }

    public Dom4JWriter(DocumentFactory documentFactory, XmlFriendlyReplacer xmlFriendlyReplacer) {
        this((Branch) null, documentFactory, (NameCoder) xmlFriendlyReplacer);
    }

    public Dom4JWriter(DocumentFactory documentFactory) {
        this(documentFactory, new XmlFriendlyNameCoder());
    }

    public Dom4JWriter(Branch branch, XmlFriendlyReplacer xmlFriendlyReplacer) {
        this(branch, new DocumentFactory(), (NameCoder) xmlFriendlyReplacer);
    }

    public Dom4JWriter(Branch branch) {
        this(branch, new DocumentFactory(), new XmlFriendlyNameCoder());
    }

    public Dom4JWriter() {
        this(new DocumentFactory(), new XmlFriendlyNameCoder());
    }

    @Override // com.thoughtworks.xstream.io.xml.AbstractDocumentWriter
    protected Object createNode(String str) {
        Element elementCreateElement = this.documentFactory.createElement(encodeNode(str));
        if (top() != null) {
            top().add(elementCreateElement);
        }
        return elementCreateElement;
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamWriter
    public void setValue(String str) {
        top().setText(str);
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamWriter
    public void addAttribute(String str, String str2) {
        top().addAttribute(encodeAttribute(str), str2);
    }

    private Branch top() {
        return (Branch) getCurrent();
    }
}
