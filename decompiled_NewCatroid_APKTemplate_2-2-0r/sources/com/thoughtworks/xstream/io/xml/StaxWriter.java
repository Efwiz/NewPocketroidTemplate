package com.thoughtworks.xstream.io.xml;

import com.thoughtworks.xstream.io.StreamException;
import com.thoughtworks.xstream.io.naming.NameCoder;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/xml/StaxWriter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class StaxWriter extends AbstractXmlWriter {
    private boolean namespaceRepairingMode;
    private final XMLStreamWriter out;
    private final QNameMap qnameMap;
    private int tagDepth;
    private final boolean writeEnclosingDocument;

    public StaxWriter(QNameMap qNameMap, XMLStreamWriter xMLStreamWriter) throws XMLStreamException {
        this(qNameMap, xMLStreamWriter, true, true);
    }

    public StaxWriter(QNameMap qNameMap, XMLStreamWriter xMLStreamWriter, NameCoder nameCoder) throws XMLStreamException {
        this(qNameMap, xMLStreamWriter, true, true, nameCoder);
    }

    public StaxWriter(QNameMap qNameMap, XMLStreamWriter xMLStreamWriter, boolean z, boolean z2, NameCoder nameCoder) throws XMLStreamException {
        super(nameCoder);
        this.qnameMap = qNameMap;
        this.out = xMLStreamWriter;
        this.writeEnclosingDocument = z;
        this.namespaceRepairingMode = z2;
        if (z) {
            xMLStreamWriter.writeStartDocument();
        }
    }

    public StaxWriter(QNameMap qNameMap, XMLStreamWriter xMLStreamWriter, boolean z, boolean z2) throws XMLStreamException {
        this(qNameMap, xMLStreamWriter, z, z2, new XmlFriendlyNameCoder());
    }

    public StaxWriter(QNameMap qNameMap, XMLStreamWriter xMLStreamWriter, boolean z, boolean z2, XmlFriendlyReplacer xmlFriendlyReplacer) throws XMLStreamException {
        this(qNameMap, xMLStreamWriter, z, z2, (NameCoder) xmlFriendlyReplacer);
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamWriter
    public void flush() {
        try {
            this.out.flush();
        } catch (XMLStreamException e) {
            throw new StreamException(e);
        }
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamWriter
    public void close() {
        try {
            this.out.close();
        } catch (XMLStreamException e) {
            throw new StreamException(e);
        }
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamWriter
    public void addAttribute(String str, String str2) {
        try {
            this.out.writeAttribute(encodeAttribute(str), str2);
        } catch (XMLStreamException e) {
            throw new StreamException(e);
        }
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamWriter
    public void endNode() {
        try {
            this.tagDepth--;
            this.out.writeEndElement();
            if (this.tagDepth == 0 && this.writeEnclosingDocument) {
                this.out.writeEndDocument();
            }
        } catch (XMLStreamException e) {
            throw new StreamException(e);
        }
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamWriter
    public void setValue(String str) {
        try {
            this.out.writeCharacters(str);
        } catch (XMLStreamException e) {
            throw new StreamException(e);
        }
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamWriter
    public void startNode(String str) {
        String namespaceURI;
        String namespaceURI2;
        try {
            QName qName = this.qnameMap.getQName(encodeNode(str));
            String prefix = qName.getPrefix();
            String namespaceURI3 = qName.getNamespaceURI();
            boolean z = false;
            boolean z2 = prefix != null && prefix.length() > 0;
            boolean z3 = namespaceURI3 != null && namespaceURI3.length() > 0;
            if (z3 && (!z2 ? (namespaceURI = this.out.getNamespaceContext().getNamespaceURI("")) == null || !namespaceURI.equals(namespaceURI3) : (namespaceURI2 = this.out.getNamespaceContext().getNamespaceURI(prefix)) == null || !namespaceURI2.equals(namespaceURI3))) {
                z = true;
            }
            this.out.writeStartElement(prefix, qName.getLocalPart(), namespaceURI3);
            if (z2) {
                this.out.setPrefix(prefix, namespaceURI3);
            } else if (z3 && z) {
                this.out.setDefaultNamespace(namespaceURI3);
            }
            if (z3 && z && !isNamespaceRepairingMode()) {
                if (z2) {
                    this.out.writeNamespace(prefix, namespaceURI3);
                } else {
                    this.out.writeDefaultNamespace(namespaceURI3);
                }
            }
            this.tagDepth++;
        } catch (XMLStreamException e) {
            throw new StreamException(e);
        }
    }

    public boolean isNamespaceRepairingMode() {
        return this.namespaceRepairingMode;
    }

    protected QNameMap getQNameMap() {
        return this.qnameMap;
    }

    protected XMLStreamWriter getXMLStreamWriter() {
        return this.out;
    }
}
