package org.apache.xerces.impl.dtd;

import org.apache.xerces.impl.msg.XMLMessageFormatter;
import org.apache.xerces.util.XMLSymbols;
import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.NamespaceContext;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLAttributes;
import org.apache.xerces.xni.XNIException;

/* JADX INFO: loaded from: classes13.dex */
public class XML11NSDTDValidator extends XML11DTDValidator {
    private final QName fAttributeQName = new QName();

    @Override // org.apache.xerces.impl.dtd.XMLDTDValidator
    protected void endNamespaceScope(QName qName, Augmentations augmentations, boolean z) throws XNIException {
        String str = qName.prefix != null ? qName.prefix : XMLSymbols.EMPTY_STRING;
        qName.uri = this.fNamespaceContext.getURI(str);
        if (qName.uri != null) {
            qName.prefix = str;
        }
        if (this.fDocumentHandler != null && !z) {
            this.fDocumentHandler.endElement(qName, augmentations);
        }
        this.fNamespaceContext.popContext();
    }

    @Override // org.apache.xerces.impl.dtd.XMLDTDValidator
    protected final void startNamespaceScope(QName qName, XMLAttributes xMLAttributes, Augmentations augmentations) throws XNIException {
        this.fNamespaceContext.pushContext();
        if (qName.prefix == XMLSymbols.PREFIX_XMLNS) {
            this.fErrorReporter.reportError(XMLMessageFormatter.XMLNS_DOMAIN, "ElementXMLNSPrefix", new Object[]{qName.rawname}, (short) 2);
        }
        int length = xMLAttributes.getLength();
        for (int i = 0; i < length; i++) {
            String localName = xMLAttributes.getLocalName(i);
            String prefix = xMLAttributes.getPrefix(i);
            if (prefix == XMLSymbols.PREFIX_XMLNS || (prefix == XMLSymbols.EMPTY_STRING && localName == XMLSymbols.PREFIX_XMLNS)) {
                String strAddSymbol = this.fSymbolTable.addSymbol(xMLAttributes.getValue(i));
                if (prefix == XMLSymbols.PREFIX_XMLNS && localName == XMLSymbols.PREFIX_XMLNS) {
                    this.fErrorReporter.reportError(XMLMessageFormatter.XMLNS_DOMAIN, "CantBindXMLNS", new Object[]{xMLAttributes.getQName(i)}, (short) 2);
                }
                if (strAddSymbol == NamespaceContext.XMLNS_URI) {
                    this.fErrorReporter.reportError(XMLMessageFormatter.XMLNS_DOMAIN, "CantBindXMLNS", new Object[]{xMLAttributes.getQName(i)}, (short) 2);
                }
                if (localName == XMLSymbols.PREFIX_XML) {
                    if (strAddSymbol != NamespaceContext.XML_URI) {
                        this.fErrorReporter.reportError(XMLMessageFormatter.XMLNS_DOMAIN, "CantBindXML", new Object[]{xMLAttributes.getQName(i)}, (short) 2);
                    }
                } else if (strAddSymbol == NamespaceContext.XML_URI) {
                    this.fErrorReporter.reportError(XMLMessageFormatter.XMLNS_DOMAIN, "CantBindXML", new Object[]{xMLAttributes.getQName(i)}, (short) 2);
                }
                if (localName == XMLSymbols.PREFIX_XMLNS) {
                    localName = XMLSymbols.EMPTY_STRING;
                }
                NamespaceContext namespaceContext = this.fNamespaceContext;
                if (strAddSymbol.length() == 0) {
                    strAddSymbol = null;
                }
                namespaceContext.declarePrefix(localName, strAddSymbol);
            }
        }
        qName.uri = this.fNamespaceContext.getURI(qName.prefix != null ? qName.prefix : XMLSymbols.EMPTY_STRING);
        if (qName.prefix == null && qName.uri != null) {
            qName.prefix = XMLSymbols.EMPTY_STRING;
        }
        if (qName.prefix != null && qName.uri == null) {
            this.fErrorReporter.reportError(XMLMessageFormatter.XMLNS_DOMAIN, "ElementPrefixUnbound", new Object[]{qName.prefix, qName.rawname}, (short) 2);
        }
        for (int i2 = 0; i2 < length; i2++) {
            xMLAttributes.getName(i2, this.fAttributeQName);
            String str = this.fAttributeQName.prefix != null ? this.fAttributeQName.prefix : XMLSymbols.EMPTY_STRING;
            String str2 = this.fAttributeQName.rawname;
            if (str2 == XMLSymbols.PREFIX_XMLNS) {
                this.fAttributeQName.uri = this.fNamespaceContext.getURI(XMLSymbols.PREFIX_XMLNS);
            } else {
                if (str != XMLSymbols.EMPTY_STRING) {
                    this.fAttributeQName.uri = this.fNamespaceContext.getURI(str);
                    if (this.fAttributeQName.uri == null) {
                        this.fErrorReporter.reportError(XMLMessageFormatter.XMLNS_DOMAIN, "AttributePrefixUnbound", new Object[]{qName.rawname, str2, str}, (short) 2);
                    }
                }
            }
            xMLAttributes.setName(i2, this.fAttributeQName);
        }
        int length2 = xMLAttributes.getLength();
        for (int i3 = 0; i3 < length2 - 1; i3++) {
            String uri = xMLAttributes.getURI(i3);
            if (uri != null && uri != NamespaceContext.XMLNS_URI) {
                String localName2 = xMLAttributes.getLocalName(i3);
                for (int i4 = i3 + 1; i4 < length2; i4++) {
                    String localName3 = xMLAttributes.getLocalName(i4);
                    String uri2 = xMLAttributes.getURI(i4);
                    if (localName2 == localName3 && uri == uri2) {
                        this.fErrorReporter.reportError(XMLMessageFormatter.XMLNS_DOMAIN, "AttributeNSNotUnique", new Object[]{qName.rawname, localName2, uri}, (short) 2);
                    }
                }
            }
        }
    }
}
