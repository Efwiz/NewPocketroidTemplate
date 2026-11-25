package org.apache.xerces.dom;

import org.apache.xerces.util.URI;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.ElementTraversal;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.w3c.dom.TypeInfo;

/* JADX INFO: loaded from: classes13.dex */
public class ElementImpl extends ParentNode implements Element, ElementTraversal, TypeInfo {
    static final long serialVersionUID = 3717253516652722278L;
    protected AttributeMap attributes;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    protected String f468name;

    protected ElementImpl() {
    }

    public ElementImpl(CoreDocumentImpl coreDocumentImpl, String str) {
        super(coreDocumentImpl);
        this.f468name = str;
        needsSyncData(true);
    }

    private Element getFirstElementChild(Node node) {
        Node firstChild = node;
        loop0: while (firstChild != null) {
            if (firstChild.getNodeType() != 1) {
                Node parentNode = firstChild;
                firstChild = firstChild.getFirstChild();
                while (firstChild == null && node != parentNode) {
                    firstChild = parentNode.getNextSibling();
                    if (firstChild == null && ((parentNode = parentNode.getParentNode()) == null || node == parentNode)) {
                        break loop0;
                    }
                }
            } else {
                return (Element) firstChild;
            }
        }
        return null;
    }

    private Element getLastElementChild(Node node) {
        Node lastChild = node;
        loop0: while (lastChild != null) {
            if (lastChild.getNodeType() != 1) {
                Node parentNode = lastChild;
                lastChild = lastChild.getLastChild();
                while (lastChild == null && node != parentNode) {
                    lastChild = parentNode.getPreviousSibling();
                    if (lastChild == null && ((parentNode = parentNode.getParentNode()) == null || node == parentNode)) {
                        break loop0;
                    }
                }
            } else {
                return (Element) lastChild;
            }
        }
        return null;
    }

    private Node getNextLogicalSibling(Node node) {
        Node nextSibling = node.getNextSibling();
        if (nextSibling == null) {
            do {
                node = node.getParentNode();
                if (node == null || node.getNodeType() != 5) {
                    break;
                }
                nextSibling = node.getNextSibling();
            } while (nextSibling == null);
        }
        return nextSibling;
    }

    private Node getPreviousLogicalSibling(Node node) {
        Node previousSibling = node.getPreviousSibling();
        if (previousSibling == null) {
            do {
                node = node.getParentNode();
                if (node == null || node.getNodeType() != 5) {
                    break;
                }
                previousSibling = node.getPreviousSibling();
            } while (previousSibling == null);
        }
        return previousSibling;
    }

    @Override // org.apache.xerces.dom.ParentNode, org.apache.xerces.dom.ChildNode, org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public Node cloneNode(boolean z) {
        ElementImpl elementImpl = (ElementImpl) super.cloneNode(z);
        AttributeMap attributeMap = this.attributes;
        if (attributeMap != null) {
            elementImpl.attributes = (AttributeMap) attributeMap.cloneMap(elementImpl);
        }
        return elementImpl;
    }

    public String getAttribute(String str) {
        Attr attr;
        if (needsSyncData()) {
            synchronizeData();
        }
        AttributeMap attributeMap = this.attributes;
        return (attributeMap == null || (attr = (Attr) attributeMap.getNamedItem(str)) == null) ? "" : attr.getValue();
    }

    public String getAttributeNS(String str, String str2) {
        Attr attr;
        if (needsSyncData()) {
            synchronizeData();
        }
        AttributeMap attributeMap = this.attributes;
        return (attributeMap == null || (attr = (Attr) attributeMap.getNamedItemNS(str, str2)) == null) ? "" : attr.getValue();
    }

    public Attr getAttributeNode(String str) {
        if (needsSyncData()) {
            synchronizeData();
        }
        AttributeMap attributeMap = this.attributes;
        if (attributeMap == null) {
            return null;
        }
        return (Attr) attributeMap.getNamedItem(str);
    }

    public Attr getAttributeNodeNS(String str, String str2) {
        if (needsSyncData()) {
            synchronizeData();
        }
        AttributeMap attributeMap = this.attributes;
        if (attributeMap == null) {
            return null;
        }
        return (Attr) attributeMap.getNamedItemNS(str, str2);
    }

    @Override // org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public NamedNodeMap getAttributes() {
        if (needsSyncData()) {
            synchronizeData();
        }
        if (this.attributes == null) {
            this.attributes = new AttributeMap(this, null);
        }
        return this.attributes;
    }

    @Override // org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public String getBaseURI() {
        Attr xMLBaseAttribute;
        if (needsSyncData()) {
            synchronizeData();
        }
        if (this.attributes != null && (xMLBaseAttribute = getXMLBaseAttribute()) != null) {
            String nodeValue = xMLBaseAttribute.getNodeValue();
            if (nodeValue.length() != 0) {
                try {
                    URI uri = new URI(nodeValue, true);
                    if (uri.isAbsoluteURI()) {
                        return uri.toString();
                    }
                    String baseURI = this.ownerNode != null ? this.ownerNode.getBaseURI() : null;
                    if (baseURI != null) {
                        uri.absolutize(new URI(baseURI));
                        return uri.toString();
                    }
                    return null;
                } catch (URI.MalformedURIException unused) {
                }
            }
        }
        if (this.ownerNode != null) {
            return this.ownerNode.getBaseURI();
        }
        return null;
    }

    @Override // org.w3c.dom.ElementTraversal
    public final int getChildElementCount() {
        int i = 0;
        for (Element firstElementChild = getFirstElementChild(); firstElementChild != null; firstElementChild = ((ElementImpl) firstElementChild).getNextElementSibling()) {
            i++;
        }
        return i;
    }

    protected NamedNodeMapImpl getDefaultAttributes() {
        ElementDefinitionImpl elementDefinitionImpl;
        DocumentTypeImpl documentTypeImpl = (DocumentTypeImpl) this.ownerDocument.getDoctype();
        if (documentTypeImpl == null || (elementDefinitionImpl = (ElementDefinitionImpl) documentTypeImpl.getElements().getNamedItem(getNodeName())) == null) {
            return null;
        }
        return (NamedNodeMapImpl) elementDefinitionImpl.getAttributes();
    }

    public NodeList getElementsByTagName(String str) {
        return new DeepNodeListImpl(this, str);
    }

    public NodeList getElementsByTagNameNS(String str, String str2) {
        return new DeepNodeListImpl(this, str, str2);
    }

    @Override // org.w3c.dom.ElementTraversal
    public final Element getFirstElementChild() {
        Element firstElementChild;
        for (Node firstChild = getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
            short nodeType = firstChild.getNodeType();
            if (nodeType == 1) {
                return (Element) firstChild;
            }
            if (nodeType == 5 && (firstElementChild = getFirstElementChild(firstChild)) != null) {
                return firstElementChild;
            }
        }
        return null;
    }

    @Override // org.w3c.dom.ElementTraversal
    public final Element getLastElementChild() {
        Element lastElementChild;
        for (Node lastChild = getLastChild(); lastChild != null; lastChild = lastChild.getPreviousSibling()) {
            short nodeType = lastChild.getNodeType();
            if (nodeType == 1) {
                return (Element) lastChild;
            }
            if (nodeType == 5 && (lastElementChild = getLastElementChild(lastChild)) != null) {
                return lastElementChild;
            }
        }
        return null;
    }

    @Override // org.w3c.dom.ElementTraversal
    public final Element getNextElementSibling() {
        Element firstElementChild;
        Node nextLogicalSibling = getNextLogicalSibling(this);
        while (nextLogicalSibling != null) {
            short nodeType = nextLogicalSibling.getNodeType();
            if (nodeType == 1) {
                return (Element) nextLogicalSibling;
            }
            if (nodeType == 5 && (firstElementChild = getFirstElementChild(nextLogicalSibling)) != null) {
                return firstElementChild;
            }
            nextLogicalSibling = getNextLogicalSibling(nextLogicalSibling);
        }
        return null;
    }

    @Override // org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public String getNodeName() {
        if (needsSyncData()) {
            synchronizeData();
        }
        return this.f468name;
    }

    @Override // org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public short getNodeType() {
        return (short) 1;
    }

    @Override // org.w3c.dom.ElementTraversal
    public final Element getPreviousElementSibling() {
        Element lastElementChild;
        Node previousLogicalSibling = getPreviousLogicalSibling(this);
        while (previousLogicalSibling != null) {
            short nodeType = previousLogicalSibling.getNodeType();
            if (nodeType == 1) {
                return (Element) previousLogicalSibling;
            }
            if (nodeType == 5 && (lastElementChild = getLastElementChild(previousLogicalSibling)) != null) {
                return lastElementChild;
            }
            previousLogicalSibling = getPreviousLogicalSibling(previousLogicalSibling);
        }
        return null;
    }

    @Override // org.w3c.dom.Element
    public TypeInfo getSchemaTypeInfo() {
        if (needsSyncData()) {
            synchronizeData();
        }
        return this;
    }

    @Override // org.w3c.dom.Element
    public String getTagName() {
        if (needsSyncData()) {
            synchronizeData();
        }
        return this.f468name;
    }

    @Override // org.w3c.dom.TypeInfo
    public String getTypeName() {
        return null;
    }

    @Override // org.w3c.dom.TypeInfo
    public String getTypeNamespace() {
        return null;
    }

    protected Attr getXMLBaseAttribute() {
        return (Attr) this.attributes.getNamedItem("xml:base");
    }

    protected int getXercesAttribute(String str, String str2) {
        if (needsSyncData()) {
            synchronizeData();
        }
        AttributeMap attributeMap = this.attributes;
        if (attributeMap == null) {
            return -1;
        }
        return attributeMap.getNamedItemIndex(str, str2);
    }

    @Override // org.w3c.dom.Element
    public boolean hasAttribute(String str) {
        return getAttributeNode(str) != null;
    }

    @Override // org.w3c.dom.Element
    public boolean hasAttributeNS(String str, String str2) {
        return getAttributeNodeNS(str, str2) != null;
    }

    @Override // org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public boolean hasAttributes() {
        if (needsSyncData()) {
            synchronizeData();
        }
        AttributeMap attributeMap = this.attributes;
        return (attributeMap == null || attributeMap.getLength() == 0) ? false : true;
    }

    @Override // org.w3c.dom.TypeInfo
    public boolean isDerivedFrom(String str, String str2, int i) {
        return false;
    }

    @Override // org.apache.xerces.dom.ParentNode, org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public boolean isEqualNode(Node node) {
        if (!super.isEqualNode(node)) {
            return false;
        }
        boolean zHasAttributes = hasAttributes();
        Element element = (Element) node;
        if (zHasAttributes != element.hasAttributes()) {
            return false;
        }
        if (!zHasAttributes) {
            return true;
        }
        NamedNodeMap attributes = getAttributes();
        NamedNodeMap attributes2 = element.getAttributes();
        int length = attributes.getLength();
        if (length != attributes2.getLength()) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            Node nodeItem = attributes.item(i);
            if (nodeItem.getLocalName() == null) {
                Node namedItem = attributes2.getNamedItem(nodeItem.getNodeName());
                if (namedItem == null || !((NodeImpl) nodeItem).isEqualNode(namedItem)) {
                    return false;
                }
            } else {
                Node namedItemNS = attributes2.getNamedItemNS(nodeItem.getNamespaceURI(), nodeItem.getLocalName());
                if (namedItemNS == null || !((NodeImpl) nodeItem).isEqualNode(namedItemNS)) {
                    return false;
                }
            }
        }
        return true;
    }

    void moveSpecifiedAttributes(ElementImpl elementImpl) {
        if (needsSyncData()) {
            synchronizeData();
        }
        if (elementImpl.hasAttributes()) {
            if (this.attributes == null) {
                this.attributes = new AttributeMap(this, null);
            }
            this.attributes.moveSpecifiedAttributes(elementImpl.attributes);
        }
    }

    @Override // org.apache.xerces.dom.ParentNode, org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public void normalize() {
        if (isNormalized()) {
            return;
        }
        if (needsSyncChildren()) {
            synchronizeChildren();
        }
        ChildNode childNode = this.firstChild;
        while (childNode != null) {
            ChildNode childNode2 = childNode.nextSibling;
            if (childNode.getNodeType() == 3) {
                if (childNode2 != null && childNode2.getNodeType() == 3) {
                    ((Text) childNode).appendData(childNode2.getNodeValue());
                    removeChild(childNode2);
                } else if (childNode.getNodeValue() == null || childNode.getNodeValue().length() == 0) {
                    removeChild(childNode);
                }
            } else if (childNode.getNodeType() == 1) {
                childNode.normalize();
            }
            childNode = childNode2;
        }
        if (this.attributes != null) {
            for (int i = 0; i < this.attributes.getLength(); i++) {
                this.attributes.item(i).normalize();
            }
        }
        isNormalized(true);
    }

    protected void reconcileDefaultAttributes() {
        if (this.attributes != null) {
            this.attributes.reconcileDefaults(getDefaultAttributes());
        }
    }

    @Override // org.w3c.dom.Element
    public void removeAttribute(String str) {
        if (this.ownerDocument.errorChecking && isReadOnly()) {
            throw new DOMException((short) 7, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NO_MODIFICATION_ALLOWED_ERR", null));
        }
        if (needsSyncData()) {
            synchronizeData();
        }
        AttributeMap attributeMap = this.attributes;
        if (attributeMap == null) {
            return;
        }
        attributeMap.safeRemoveNamedItem(str);
    }

    @Override // org.w3c.dom.Element
    public void removeAttributeNS(String str, String str2) {
        if (this.ownerDocument.errorChecking && isReadOnly()) {
            throw new DOMException((short) 7, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NO_MODIFICATION_ALLOWED_ERR", null));
        }
        if (needsSyncData()) {
            synchronizeData();
        }
        AttributeMap attributeMap = this.attributes;
        if (attributeMap == null) {
            return;
        }
        attributeMap.safeRemoveNamedItemNS(str, str2);
    }

    @Override // org.w3c.dom.Element
    public Attr removeAttributeNode(Attr attr) throws DOMException {
        if (this.ownerDocument.errorChecking && isReadOnly()) {
            throw new DOMException((short) 7, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NO_MODIFICATION_ALLOWED_ERR", null));
        }
        if (needsSyncData()) {
            synchronizeData();
        }
        AttributeMap attributeMap = this.attributes;
        if (attributeMap != null) {
            return (Attr) attributeMap.removeItem(attr, true);
        }
        throw new DOMException((short) 8, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NOT_FOUND_ERR", null));
    }

    void rename(String str) {
        if (needsSyncData()) {
            synchronizeData();
        }
        if (this.ownerDocument.errorChecking) {
            if (str.indexOf(58) != -1) {
                throw new DOMException((short) 14, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NAMESPACE_ERR", null));
            }
            if (!CoreDocumentImpl.isXMLName(str, this.ownerDocument.isXML11Version())) {
                throw new DOMException((short) 5, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INVALID_CHARACTER_ERR", null));
            }
        }
        this.f468name = str;
        reconcileDefaultAttributes();
    }

    @Override // org.w3c.dom.Element
    public void setAttribute(String str, String str2) {
        if (this.ownerDocument.errorChecking && isReadOnly()) {
            throw new DOMException((short) 7, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NO_MODIFICATION_ALLOWED_ERR", null));
        }
        if (needsSyncData()) {
            synchronizeData();
        }
        Attr attributeNode = getAttributeNode(str);
        if (attributeNode != null) {
            attributeNode.setNodeValue(str2);
            return;
        }
        Attr attrCreateAttribute = getOwnerDocument().createAttribute(str);
        if (this.attributes == null) {
            this.attributes = new AttributeMap(this, null);
        }
        attrCreateAttribute.setNodeValue(str2);
        this.attributes.setNamedItem(attrCreateAttribute);
    }

    @Override // org.w3c.dom.Element
    public void setAttributeNS(String str, String str2, String str3) {
        String strSubstring;
        String strSubstring2;
        if (this.ownerDocument.errorChecking && isReadOnly()) {
            throw new DOMException((short) 7, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NO_MODIFICATION_ALLOWED_ERR", null));
        }
        if (needsSyncData()) {
            synchronizeData();
        }
        int iIndexOf = str2.indexOf(58);
        if (iIndexOf < 0) {
            strSubstring2 = str2;
            strSubstring = null;
        } else {
            strSubstring = str2.substring(0, iIndexOf);
            strSubstring2 = str2.substring(iIndexOf + 1);
        }
        Attr attributeNodeNS = getAttributeNodeNS(str, strSubstring2);
        if (attributeNodeNS == null) {
            Attr attrCreateAttributeNS = getOwnerDocument().createAttributeNS(str, str2);
            if (this.attributes == null) {
                this.attributes = new AttributeMap(this, null);
            }
            attrCreateAttributeNS.setNodeValue(str3);
            this.attributes.setNamedItemNS(attrCreateAttributeNS);
            return;
        }
        if (attributeNodeNS instanceof AttrNSImpl) {
            AttrNSImpl attrNSImpl = (AttrNSImpl) attributeNodeNS;
            if (strSubstring != null) {
                strSubstring2 = strSubstring + ":" + strSubstring2;
            }
            attrNSImpl.f465name = strSubstring2;
        } else {
            attributeNodeNS = ((CoreDocumentImpl) getOwnerDocument()).createAttributeNS(str, str2, strSubstring2);
            this.attributes.setNamedItemNS(attributeNodeNS);
        }
        attributeNodeNS.setNodeValue(str3);
    }

    @Override // org.w3c.dom.Element
    public Attr setAttributeNode(Attr attr) throws DOMException {
        if (needsSyncData()) {
            synchronizeData();
        }
        if (this.ownerDocument.errorChecking) {
            if (isReadOnly()) {
                throw new DOMException((short) 7, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NO_MODIFICATION_ALLOWED_ERR", null));
            }
            if (attr.getOwnerDocument() != this.ownerDocument) {
                throw new DOMException((short) 4, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "WRONG_DOCUMENT_ERR", null));
            }
        }
        if (this.attributes == null) {
            this.attributes = new AttributeMap(this, null);
        }
        return (Attr) this.attributes.setNamedItem(attr);
    }

    @Override // org.w3c.dom.Element
    public Attr setAttributeNodeNS(Attr attr) throws DOMException {
        if (needsSyncData()) {
            synchronizeData();
        }
        if (this.ownerDocument.errorChecking) {
            if (isReadOnly()) {
                throw new DOMException((short) 7, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NO_MODIFICATION_ALLOWED_ERR", null));
            }
            if (attr.getOwnerDocument() != this.ownerDocument) {
                throw new DOMException((short) 4, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "WRONG_DOCUMENT_ERR", null));
            }
        }
        if (this.attributes == null) {
            this.attributes = new AttributeMap(this, null);
        }
        return (Attr) this.attributes.setNamedItemNS(attr);
    }

    @Override // org.w3c.dom.Element
    public void setIdAttribute(String str, boolean z) {
        if (needsSyncData()) {
            synchronizeData();
        }
        Attr attributeNode = getAttributeNode(str);
        if (attributeNode == null) {
            throw new DOMException((short) 8, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NOT_FOUND_ERR", null));
        }
        if (this.ownerDocument.errorChecking) {
            if (isReadOnly()) {
                throw new DOMException((short) 7, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NO_MODIFICATION_ALLOWED_ERR", null));
            }
            if (attributeNode.getOwnerElement() != this) {
                throw new DOMException((short) 8, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NOT_FOUND_ERR", null));
            }
        }
        ((AttrImpl) attributeNode).isIdAttribute(z);
        if (z) {
            this.ownerDocument.putIdentifier(attributeNode.getValue(), this);
        } else {
            this.ownerDocument.removeIdentifier(attributeNode.getValue());
        }
    }

    @Override // org.w3c.dom.Element
    public void setIdAttributeNS(String str, String str2, boolean z) {
        if (needsSyncData()) {
            synchronizeData();
        }
        Attr attributeNodeNS = getAttributeNodeNS(str, str2);
        if (attributeNodeNS == null) {
            throw new DOMException((short) 8, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NOT_FOUND_ERR", null));
        }
        if (this.ownerDocument.errorChecking) {
            if (isReadOnly()) {
                throw new DOMException((short) 7, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NO_MODIFICATION_ALLOWED_ERR", null));
            }
            if (attributeNodeNS.getOwnerElement() != this) {
                throw new DOMException((short) 8, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NOT_FOUND_ERR", null));
            }
        }
        ((AttrImpl) attributeNodeNS).isIdAttribute(z);
        CoreDocumentImpl coreDocumentImpl = this.ownerDocument;
        String value = attributeNodeNS.getValue();
        if (z) {
            coreDocumentImpl.putIdentifier(value, this);
        } else {
            coreDocumentImpl.removeIdentifier(value);
        }
    }

    @Override // org.w3c.dom.Element
    public void setIdAttributeNode(Attr attr, boolean z) {
        if (needsSyncData()) {
            synchronizeData();
        }
        if (this.ownerDocument.errorChecking) {
            if (isReadOnly()) {
                throw new DOMException((short) 7, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NO_MODIFICATION_ALLOWED_ERR", null));
            }
            if (attr.getOwnerElement() != this) {
                throw new DOMException((short) 8, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NOT_FOUND_ERR", null));
            }
        }
        ((AttrImpl) attr).isIdAttribute(z);
        if (z) {
            this.ownerDocument.putIdentifier(attr.getValue(), this);
        } else {
            this.ownerDocument.removeIdentifier(attr.getValue());
        }
    }

    @Override // org.apache.xerces.dom.ParentNode, org.apache.xerces.dom.NodeImpl
    protected void setOwnerDocument(CoreDocumentImpl coreDocumentImpl) {
        super.setOwnerDocument(coreDocumentImpl);
        AttributeMap attributeMap = this.attributes;
        if (attributeMap != null) {
            attributeMap.setOwnerDocument(coreDocumentImpl);
        }
    }

    @Override // org.apache.xerces.dom.ParentNode, org.apache.xerces.dom.NodeImpl
    public void setReadOnly(boolean z, boolean z2) {
        super.setReadOnly(z, z2);
        AttributeMap attributeMap = this.attributes;
        if (attributeMap != null) {
            attributeMap.setReadOnly(z, true);
        }
    }

    protected int setXercesAttributeNode(Attr attr) {
        if (needsSyncData()) {
            synchronizeData();
        }
        if (this.attributes == null) {
            this.attributes = new AttributeMap(this, null);
        }
        return this.attributes.addItem(attr);
    }

    protected void setupDefaultAttributes() {
        NamedNodeMapImpl defaultAttributes = getDefaultAttributes();
        if (defaultAttributes != null) {
            this.attributes = new AttributeMap(this, defaultAttributes);
        }
    }

    @Override // org.apache.xerces.dom.NodeImpl
    protected void synchronizeData() {
        needsSyncData(false);
        boolean mutationEvents = this.ownerDocument.getMutationEvents();
        this.ownerDocument.setMutationEvents(false);
        setupDefaultAttributes();
        this.ownerDocument.setMutationEvents(mutationEvents);
    }
}
