package org.apache.xerces.stax.events;

import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Namespace;
import javax.xml.stream.events.StartElement;
import org.apache.xerces.stax.DefaultNamespaceContext;

/* JADX INFO: loaded from: classes13.dex */
public final class StartElementImpl extends ElementImpl implements StartElement {
    private static final Comparator QNAME_COMPARATOR = new Comparator() { // from class: org.apache.xerces.stax.events.StartElementImpl.1
        @Override // java.util.Comparator
        public int compare(Object obj, Object obj2) {
            if (obj.equals(obj2)) {
                return 0;
            }
            return ((QName) obj).toString().compareTo(((QName) obj2).toString());
        }
    };
    private final Map fAttributes;
    private final NamespaceContext fNamespaceContext;

    public StartElementImpl(QName qName, Iterator it, Iterator it2, NamespaceContext namespaceContext, Location location) {
        super(qName, true, it2, location);
        if (it == null || !it.hasNext()) {
            this.fAttributes = Collections.EMPTY_MAP;
        } else {
            this.fAttributes = new TreeMap(QNAME_COMPARATOR);
            do {
                Attribute attribute = (Attribute) it.next();
                this.fAttributes.put(attribute.getName(), attribute);
            } while (it.hasNext());
        }
        this.fNamespaceContext = namespaceContext == null ? DefaultNamespaceContext.getInstance() : namespaceContext;
    }

    @Override // javax.xml.stream.events.StartElement
    public Attribute getAttributeByName(QName qName) {
        return (Attribute) this.fAttributes.get(qName);
    }

    @Override // javax.xml.stream.events.StartElement
    public Iterator getAttributes() {
        return createImmutableIterator(this.fAttributes.values().iterator());
    }

    @Override // javax.xml.stream.events.StartElement
    public NamespaceContext getNamespaceContext() {
        return this.fNamespaceContext;
    }

    @Override // javax.xml.stream.events.StartElement
    public String getNamespaceURI(String str) {
        return this.fNamespaceContext.getNamespaceURI(str);
    }

    @Override // javax.xml.stream.events.XMLEvent
    public void writeAsEncodedUnicode(Writer writer) throws XMLStreamException {
        try {
            writer.write(60);
            QName name2 = getName();
            String prefix = name2.getPrefix();
            if (prefix != null && prefix.length() > 0) {
                writer.write(prefix);
                writer.write(58);
            }
            writer.write(name2.getLocalPart());
            Iterator namespaces = getNamespaces();
            while (namespaces.hasNext()) {
                Namespace namespace = (Namespace) namespaces.next();
                writer.write(32);
                namespace.writeAsEncodedUnicode(writer);
            }
            Iterator attributes = getAttributes();
            while (attributes.hasNext()) {
                Attribute attribute = (Attribute) attributes.next();
                writer.write(32);
                attribute.writeAsEncodedUnicode(writer);
            }
            writer.write(62);
        } catch (IOException e) {
            throw new XMLStreamException(e);
        }
    }
}
