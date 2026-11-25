package org.apache.xerces.stax.events;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.events.Namespace;

/* JADX INFO: loaded from: classes13.dex */
public final class NamespaceImpl extends AttributeImpl implements Namespace {
    private final String fNamespaceURI;
    private final String fPrefix;

    public NamespaceImpl(String str, String str2, Location location) {
        super(13, makeAttributeQName(str), str2, null, true, location);
        this.fPrefix = str == null ? "" : str;
        this.fNamespaceURI = str2;
    }

    private static QName makeAttributeQName(String str) {
        return (str == null || str.equals("")) ? new QName(XMLConstants.XMLNS_ATTRIBUTE_NS_URI, XMLConstants.XMLNS_ATTRIBUTE, "") : new QName(XMLConstants.XMLNS_ATTRIBUTE_NS_URI, str, XMLConstants.XMLNS_ATTRIBUTE);
    }

    @Override // javax.xml.stream.events.Namespace
    public String getNamespaceURI() {
        return this.fNamespaceURI;
    }

    @Override // javax.xml.stream.events.Namespace
    public String getPrefix() {
        return this.fPrefix;
    }

    @Override // javax.xml.stream.events.Namespace
    public boolean isDefaultNamespaceDeclaration() {
        return this.fPrefix.length() == 0;
    }
}
