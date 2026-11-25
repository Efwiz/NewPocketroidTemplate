package org.apache.xerces.dom;

import javax.xml.XMLConstants;
import org.apache.xerces.impl.dv.xs.XSSimpleTypeDecl;
import org.apache.xerces.xni.NamespaceContext;
import org.w3c.dom.DOMException;

/* JADX INFO: loaded from: classes13.dex */
public class AttrNSImpl extends AttrImpl {
    static final long serialVersionUID = -781906615369795414L;
    static final String xmlURI = "http://www.w3.org/XML/1998/namespace";
    static final String xmlnsURI = "http://www.w3.org/2000/xmlns/";
    protected String localName;
    protected String namespaceURI;

    public AttrNSImpl() {
    }

    protected AttrNSImpl(CoreDocumentImpl coreDocumentImpl, String str) {
        super(coreDocumentImpl, str);
    }

    protected AttrNSImpl(CoreDocumentImpl coreDocumentImpl, String str, String str2) {
        super(coreDocumentImpl, str2);
        setName(str, str2);
    }

    public AttrNSImpl(CoreDocumentImpl coreDocumentImpl, String str, String str2, String str3) {
        super(coreDocumentImpl, str2);
        this.localName = str3;
        this.namespaceURI = str;
    }

    private void setName(String str, String str2) {
        CoreDocumentImpl coreDocumentImplOwnerDocument = ownerDocument();
        this.namespaceURI = str;
        if (str != null) {
            this.namespaceURI = str.length() == 0 ? null : str;
        }
        int iIndexOf = str2.indexOf(58);
        int iLastIndexOf = str2.lastIndexOf(58);
        coreDocumentImplOwnerDocument.checkNamespaceWF(str2, iIndexOf, iLastIndexOf);
        if (iIndexOf >= 0) {
            String strSubstring = str2.substring(0, iIndexOf);
            String strSubstring2 = str2.substring(iLastIndexOf + 1);
            this.localName = strSubstring2;
            coreDocumentImplOwnerDocument.checkQName(strSubstring, strSubstring2);
            coreDocumentImplOwnerDocument.checkDOMNSErr(strSubstring, str);
            return;
        }
        this.localName = str2;
        if (coreDocumentImplOwnerDocument.errorChecking) {
            coreDocumentImplOwnerDocument.checkQName(null, this.localName);
            if ((str2.equals(XMLConstants.XMLNS_ATTRIBUTE) && (str == null || !str.equals(NamespaceContext.XMLNS_URI))) || (str != null && str.equals(NamespaceContext.XMLNS_URI) && !str2.equals(XMLConstants.XMLNS_ATTRIBUTE))) {
                throw new DOMException((short) 14, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NAMESPACE_ERR", null));
            }
        }
    }

    @Override // org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public String getLocalName() {
        if (needsSyncData()) {
            synchronizeData();
        }
        return this.localName;
    }

    @Override // org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public String getNamespaceURI() {
        if (needsSyncData()) {
            synchronizeData();
        }
        return this.namespaceURI;
    }

    @Override // org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public String getPrefix() {
        if (needsSyncData()) {
            synchronizeData();
        }
        int iIndexOf = this.f465name.indexOf(58);
        if (iIndexOf < 0) {
            return null;
        }
        return this.f465name.substring(0, iIndexOf);
    }

    @Override // org.apache.xerces.dom.AttrImpl, org.w3c.dom.TypeInfo
    public String getTypeName() {
        if (this.type != null) {
            return this.type instanceof XSSimpleTypeDecl ? ((XSSimpleTypeDecl) this.type).getName() : (String) this.type;
        }
        return null;
    }

    @Override // org.apache.xerces.dom.AttrImpl, org.w3c.dom.TypeInfo
    public String getTypeNamespace() {
        if (this.type != null) {
            return this.type instanceof XSSimpleTypeDecl ? ((XSSimpleTypeDecl) this.type).getNamespace() : "http://www.w3.org/TR/REC-xml";
        }
        return null;
    }

    @Override // org.apache.xerces.dom.AttrImpl, org.w3c.dom.TypeInfo
    public boolean isDerivedFrom(String str, String str2, int i) {
        if (this.type == null || !(this.type instanceof XSSimpleTypeDecl)) {
            return false;
        }
        return ((XSSimpleTypeDecl) this.type).isDOMDerivedFrom(str, str2, i);
    }

    void rename(String str, String str2) {
        if (needsSyncData()) {
            synchronizeData();
        }
        this.f465name = str2;
        setName(str, str2);
    }

    @Override // org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public void setPrefix(String str) throws DOMException {
        if (needsSyncData()) {
            synchronizeData();
        }
        if (ownerDocument().errorChecking) {
            if (isReadOnly()) {
                throw new DOMException((short) 7, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NO_MODIFICATION_ALLOWED_ERR", null));
            }
            if (str != null && str.length() != 0) {
                if (!CoreDocumentImpl.isXMLName(str, ownerDocument().isXML11Version())) {
                    throw new DOMException((short) 5, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INVALID_CHARACTER_ERR", null));
                }
                if (this.namespaceURI == null || str.indexOf(58) >= 0) {
                    throw new DOMException((short) 14, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NAMESPACE_ERR", null));
                }
                if (str.equals(XMLConstants.XMLNS_ATTRIBUTE)) {
                    if (!this.namespaceURI.equals("http://www.w3.org/2000/xmlns/")) {
                        throw new DOMException((short) 14, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NAMESPACE_ERR", null));
                    }
                } else if (str.equals("xml")) {
                    if (!this.namespaceURI.equals("http://www.w3.org/XML/1998/namespace")) {
                        throw new DOMException((short) 14, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NAMESPACE_ERR", null));
                    }
                } else if (this.f465name.equals(XMLConstants.XMLNS_ATTRIBUTE)) {
                    throw new DOMException((short) 14, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NAMESPACE_ERR", null));
                }
            }
        }
        this.f465name = (str == null || str.length() == 0) ? this.localName : str + ":" + this.localName;
    }
}
