package org.apache.xerces.dom;

import com.reandroid.apk.ApkUtil;
import javax.xml.XMLConstants;
import org.apache.xerces.impl.dv.xs.XSSimpleTypeDecl;
import org.apache.xerces.impl.xs.XSComplexTypeDecl;
import org.apache.xerces.xni.NamespaceContext;
import org.apache.xerces.xs.XSTypeDefinition;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;

/* JADX INFO: loaded from: classes13.dex */
public class ElementNSImpl extends ElementImpl {
    static final long serialVersionUID = -9142310625494392642L;
    static final String xmlURI = "http://www.w3.org/XML/1998/namespace";
    protected String localName;
    protected String namespaceURI;
    transient XSTypeDefinition type;

    protected ElementNSImpl() {
    }

    protected ElementNSImpl(CoreDocumentImpl coreDocumentImpl, String str) {
        super(coreDocumentImpl, str);
    }

    protected ElementNSImpl(CoreDocumentImpl coreDocumentImpl, String str, String str2) throws DOMException {
        super(coreDocumentImpl, str2);
        setName(str, str2);
    }

    protected ElementNSImpl(CoreDocumentImpl coreDocumentImpl, String str, String str2, String str3) throws DOMException {
        super(coreDocumentImpl, str2);
        this.localName = str3;
        this.namespaceURI = str;
    }

    private void setName(String str, String str2) {
        this.namespaceURI = str;
        if (str != null) {
            this.namespaceURI = str.length() == 0 ? null : str;
        }
        if (str2 == null) {
            throw new DOMException((short) 14, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NAMESPACE_ERR", null));
        }
        int iIndexOf = str2.indexOf(58);
        int iLastIndexOf = str2.lastIndexOf(58);
        this.ownerDocument.checkNamespaceWF(str2, iIndexOf, iLastIndexOf);
        if (iIndexOf >= 0) {
            String strSubstring = str2.substring(0, iIndexOf);
            this.localName = str2.substring(iLastIndexOf + 1);
            if (this.ownerDocument.errorChecking) {
                if (str == null || (strSubstring.equals("xml") && !str.equals(NamespaceContext.XML_URI))) {
                    throw new DOMException((short) 14, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NAMESPACE_ERR", null));
                }
                this.ownerDocument.checkQName(strSubstring, this.localName);
                this.ownerDocument.checkDOMNSErr(strSubstring, str);
                return;
            }
            return;
        }
        this.localName = str2;
        if (this.ownerDocument.errorChecking) {
            this.ownerDocument.checkQName(null, this.localName);
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
        int iIndexOf = this.f468name.indexOf(58);
        if (iIndexOf < 0) {
            return null;
        }
        return this.f468name.substring(0, iIndexOf);
    }

    @Override // org.apache.xerces.dom.ElementImpl, org.w3c.dom.TypeInfo
    public String getTypeName() {
        XSTypeDefinition xSTypeDefinition = this.type;
        if (xSTypeDefinition == null) {
            return null;
        }
        if (xSTypeDefinition instanceof XSSimpleTypeDecl) {
            return ((XSSimpleTypeDecl) xSTypeDefinition).getTypeName();
        }
        if (xSTypeDefinition instanceof XSComplexTypeDecl) {
            return ((XSComplexTypeDecl) xSTypeDefinition).getTypeName();
        }
        return null;
    }

    @Override // org.apache.xerces.dom.ElementImpl, org.w3c.dom.TypeInfo
    public String getTypeNamespace() {
        XSTypeDefinition xSTypeDefinition = this.type;
        if (xSTypeDefinition != null) {
            return xSTypeDefinition.getNamespace();
        }
        return null;
    }

    @Override // org.apache.xerces.dom.ElementImpl
    protected Attr getXMLBaseAttribute() {
        return (Attr) this.attributes.getNamedItemNS("http://www.w3.org/XML/1998/namespace", ApkUtil.DEF_MODULE_NAME);
    }

    @Override // org.apache.xerces.dom.ElementImpl, org.w3c.dom.TypeInfo
    public boolean isDerivedFrom(String str, String str2, int i) {
        if (needsSyncData()) {
            synchronizeData();
        }
        XSTypeDefinition xSTypeDefinition = this.type;
        if (xSTypeDefinition == null) {
            return false;
        }
        if (xSTypeDefinition instanceof XSSimpleTypeDecl) {
            return ((XSSimpleTypeDecl) xSTypeDefinition).isDOMDerivedFrom(str, str2, i);
        }
        if (xSTypeDefinition instanceof XSComplexTypeDecl) {
            return ((XSComplexTypeDecl) xSTypeDefinition).isDOMDerivedFrom(str, str2, i);
        }
        return false;
    }

    void rename(String str, String str2) {
        if (needsSyncData()) {
            synchronizeData();
        }
        this.f468name = str2;
        setName(str, str2);
        reconcileDefaultAttributes();
    }

    @Override // org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public void setPrefix(String str) throws DOMException {
        if (needsSyncData()) {
            synchronizeData();
        }
        if (this.ownerDocument.errorChecking) {
            if (isReadOnly()) {
                throw new DOMException((short) 7, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NO_MODIFICATION_ALLOWED_ERR", null));
            }
            if (str != null && str.length() != 0) {
                if (!CoreDocumentImpl.isXMLName(str, this.ownerDocument.isXML11Version())) {
                    throw new DOMException((short) 5, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INVALID_CHARACTER_ERR", null));
                }
                if (this.namespaceURI == null || str.indexOf(58) >= 0) {
                    throw new DOMException((short) 14, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NAMESPACE_ERR", null));
                }
                if (str.equals("xml") && !this.namespaceURI.equals("http://www.w3.org/XML/1998/namespace")) {
                    throw new DOMException((short) 14, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NAMESPACE_ERR", null));
                }
            }
        }
        this.f468name = (str == null || str.length() == 0) ? this.localName : str + ":" + this.localName;
    }

    public void setType(XSTypeDefinition xSTypeDefinition) {
        this.type = xSTypeDefinition;
    }
}
