package org.apache.xerces.dom;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.w3c.dom.TypeInfo;

/* JADX INFO: loaded from: classes13.dex */
public class AttrImpl extends NodeImpl implements Attr, TypeInfo {
    static final String DTD_URI = "http://www.w3.org/TR/REC-xml";
    static final long serialVersionUID = 7277707688218972102L;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    protected String f465name;
    transient Object type;
    protected Object value;

    protected AttrImpl() {
        this.value = null;
    }

    protected AttrImpl(CoreDocumentImpl coreDocumentImpl, String str) {
        super(coreDocumentImpl);
        this.value = null;
        this.f465name = str;
        isSpecified(true);
        hasStringValue(true);
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        needsSyncChildren(false);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        if (needsSyncChildren()) {
            synchronizeChildren();
        }
        objectOutputStream.defaultWriteObject();
    }

    void checkNormalizationAfterInsert(ChildNode childNode) {
        if (childNode.getNodeType() == 3) {
            ChildNode childNodePreviousSibling = childNode.previousSibling();
            ChildNode childNode2 = childNode.nextSibling;
            if ((childNodePreviousSibling == null || childNodePreviousSibling.getNodeType() != 3) && (childNode2 == null || childNode2.getNodeType() != 3)) {
                return;
            }
        } else if (childNode.isNormalized()) {
            return;
        }
        isNormalized(false);
    }

    void checkNormalizationAfterRemove(ChildNode childNode) {
        ChildNode childNode2;
        if (childNode == null || childNode.getNodeType() != 3 || (childNode2 = childNode.nextSibling) == null || childNode2.getNodeType() != 3) {
            return;
        }
        isNormalized(false);
    }

    @Override // org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public Node cloneNode(boolean z) {
        if (needsSyncChildren()) {
            synchronizeChildren();
        }
        AttrImpl attrImpl = (AttrImpl) super.cloneNode(z);
        if (!attrImpl.hasStringValue()) {
            attrImpl.value = null;
            for (Node nextSibling = (Node) this.value; nextSibling != null; nextSibling = nextSibling.getNextSibling()) {
                attrImpl.appendChild(nextSibling.cloneNode(true));
            }
        }
        attrImpl.isSpecified(true);
        return attrImpl;
    }

    @Override // org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public NodeList getChildNodes() {
        if (needsSyncChildren()) {
            synchronizeChildren();
        }
        return this;
    }

    public Element getElement() {
        return (Element) (isOwned() ? this.ownerNode : null);
    }

    @Override // org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public Node getFirstChild() {
        if (needsSyncChildren()) {
            synchronizeChildren();
        }
        makeChildNode();
        return (Node) this.value;
    }

    @Override // org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public Node getLastChild() {
        if (needsSyncChildren()) {
            synchronizeChildren();
        }
        return lastChild();
    }

    @Override // org.apache.xerces.dom.NodeImpl, org.w3c.dom.NodeList, org.w3c.dom.html.HTMLFormElement
    public int getLength() {
        if (hasStringValue()) {
            return 1;
        }
        int i = 0;
        for (ChildNode childNode = (ChildNode) this.value; childNode != null; childNode = childNode.nextSibling) {
            i++;
        }
        return i;
    }

    @Override // org.w3c.dom.Attr
    public String getName() {
        if (needsSyncData()) {
            synchronizeData();
        }
        return this.f465name;
    }

    @Override // org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public String getNodeName() {
        if (needsSyncData()) {
            synchronizeData();
        }
        return this.f465name;
    }

    @Override // org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public short getNodeType() {
        return (short) 2;
    }

    @Override // org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public String getNodeValue() {
        return getValue();
    }

    @Override // org.w3c.dom.Attr
    public Element getOwnerElement() {
        return (Element) (isOwned() ? this.ownerNode : null);
    }

    @Override // org.w3c.dom.Attr
    public TypeInfo getSchemaTypeInfo() {
        return this;
    }

    @Override // org.w3c.dom.Attr
    public boolean getSpecified() {
        if (needsSyncData()) {
            synchronizeData();
        }
        return isSpecified();
    }

    @Override // org.w3c.dom.TypeInfo
    public String getTypeName() {
        return (String) this.type;
    }

    @Override // org.w3c.dom.TypeInfo
    public String getTypeNamespace() {
        if (this.type != null) {
            return "http://www.w3.org/TR/REC-xml";
        }
        return null;
    }

    @Override // org.w3c.dom.Attr
    public String getValue() {
        String nodeValue;
        if (needsSyncData()) {
            synchronizeData();
        }
        if (needsSyncChildren()) {
            synchronizeChildren();
        }
        if (this.value == null) {
            return "";
        }
        if (hasStringValue()) {
            return (String) this.value;
        }
        ChildNode childNode = (ChildNode) this.value;
        String entityRefValue = childNode.getNodeType() == 5 ? ((EntityReferenceImpl) childNode).getEntityRefValue() : childNode.getNodeValue();
        ChildNode childNode2 = childNode.nextSibling;
        if (childNode2 == null || entityRefValue == null) {
            return entityRefValue == null ? "" : entityRefValue;
        }
        StringBuffer stringBuffer = new StringBuffer(entityRefValue);
        while (childNode2 != null) {
            if (childNode2.getNodeType() == 5) {
                nodeValue = ((EntityReferenceImpl) childNode2).getEntityRefValue();
                if (nodeValue == null) {
                    return "";
                }
            } else {
                nodeValue = childNode2.getNodeValue();
            }
            stringBuffer.append(nodeValue);
            childNode2 = childNode2.nextSibling;
        }
        return stringBuffer.toString();
    }

    @Override // org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public boolean hasChildNodes() {
        if (needsSyncChildren()) {
            synchronizeChildren();
        }
        return this.value != null;
    }

    @Override // org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public Node insertBefore(Node node, Node node2) throws DOMException {
        return internalInsertBefore(node, node2, false);
    }

    Node internalInsertBefore(Node node, Node node2, boolean z) throws DOMException {
        CoreDocumentImpl coreDocumentImplOwnerDocument = ownerDocument();
        boolean z2 = coreDocumentImplOwnerDocument.errorChecking;
        if (node.getNodeType() == 11) {
            if (z2) {
                for (Node firstChild = node.getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
                    if (!coreDocumentImplOwnerDocument.isKidOK(this, firstChild)) {
                        throw new DOMException((short) 3, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "HIERARCHY_REQUEST_ERR", null));
                    }
                }
            }
            while (node.hasChildNodes()) {
                insertBefore(node.getFirstChild(), node2);
            }
            return node;
        }
        if (node == node2) {
            Node nextSibling = node2.getNextSibling();
            removeChild(node);
            insertBefore(node, nextSibling);
            return node;
        }
        if (needsSyncChildren()) {
            synchronizeChildren();
        }
        if (z2) {
            if (isReadOnly()) {
                throw new DOMException((short) 7, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NO_MODIFICATION_ALLOWED_ERR", null));
            }
            if (node.getOwnerDocument() != coreDocumentImplOwnerDocument) {
                throw new DOMException((short) 4, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "WRONG_DOCUMENT_ERR", null));
            }
            if (!coreDocumentImplOwnerDocument.isKidOK(this, node)) {
                throw new DOMException((short) 3, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "HIERARCHY_REQUEST_ERR", null));
            }
            if (node2 != null && node2.getParentNode() != this) {
                throw new DOMException((short) 8, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NOT_FOUND_ERR", null));
            }
            NodeImpl nodeImplParentNode = this;
            boolean z3 = true;
            while (z3 && nodeImplParentNode != null) {
                z3 = node != nodeImplParentNode;
                nodeImplParentNode = nodeImplParentNode.parentNode();
            }
            if (!z3) {
                throw new DOMException((short) 3, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "HIERARCHY_REQUEST_ERR", null));
            }
        }
        makeChildNode();
        coreDocumentImplOwnerDocument.insertingNode(this, z);
        ChildNode childNode = (ChildNode) node;
        NodeImpl nodeImplParentNode2 = childNode.parentNode();
        if (nodeImplParentNode2 != null) {
            nodeImplParentNode2.removeChild(childNode);
        }
        ChildNode childNode2 = (ChildNode) node2;
        childNode.ownerNode = this;
        childNode.isOwned(true);
        ChildNode childNode3 = (ChildNode) this.value;
        if (childNode3 == null) {
            this.value = childNode;
            childNode.isFirstChild(true);
            childNode.previousSibling = childNode;
        } else if (childNode2 == null) {
            ChildNode childNode4 = childNode3.previousSibling;
            childNode4.nextSibling = childNode;
            childNode.previousSibling = childNode4;
            childNode3.previousSibling = childNode;
        } else if (node2 == childNode3) {
            childNode3.isFirstChild(false);
            childNode.nextSibling = childNode3;
            childNode.previousSibling = childNode3.previousSibling;
            childNode3.previousSibling = childNode;
            this.value = childNode;
            childNode.isFirstChild(true);
        } else {
            ChildNode childNode5 = childNode2.previousSibling;
            childNode.nextSibling = childNode2;
            childNode5.nextSibling = childNode;
            childNode2.previousSibling = childNode;
            childNode.previousSibling = childNode5;
        }
        changed();
        coreDocumentImplOwnerDocument.insertedNode(this, childNode, z);
        checkNormalizationAfterInsert(childNode);
        return node;
    }

    Node internalRemoveChild(Node node, boolean z) throws DOMException {
        CoreDocumentImpl coreDocumentImplOwnerDocument = ownerDocument();
        if (coreDocumentImplOwnerDocument.errorChecking) {
            if (isReadOnly()) {
                throw new DOMException((short) 7, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NO_MODIFICATION_ALLOWED_ERR", null));
            }
            if (node != null && node.getParentNode() != this) {
                throw new DOMException((short) 8, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NOT_FOUND_ERR", null));
            }
        }
        ChildNode childNode = (ChildNode) node;
        coreDocumentImplOwnerDocument.removingNode(this, childNode, z);
        if (childNode == this.value) {
            childNode.isFirstChild(false);
            ChildNode childNode2 = childNode.nextSibling;
            this.value = childNode2;
            if (childNode2 != null) {
                childNode2.isFirstChild(true);
                childNode2.previousSibling = childNode.previousSibling;
            }
        } else {
            ChildNode childNode3 = childNode.previousSibling;
            ChildNode childNode4 = childNode.nextSibling;
            childNode3.nextSibling = childNode4;
            if (childNode4 == null) {
                childNode4 = (ChildNode) this.value;
            }
            childNode4.previousSibling = childNode3;
        }
        ChildNode childNodePreviousSibling = childNode.previousSibling();
        childNode.ownerNode = coreDocumentImplOwnerDocument;
        childNode.isOwned(false);
        childNode.nextSibling = null;
        childNode.previousSibling = null;
        changed();
        coreDocumentImplOwnerDocument.removedNode(this, z);
        checkNormalizationAfterRemove(childNodePreviousSibling);
        return childNode;
    }

    @Override // org.w3c.dom.TypeInfo
    public boolean isDerivedFrom(String str, String str2, int i) {
        return false;
    }

    @Override // org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public boolean isEqualNode(Node node) {
        return super.isEqualNode(node);
    }

    @Override // org.w3c.dom.Attr
    public boolean isId() {
        return isIdAttribute();
    }

    @Override // org.apache.xerces.dom.NodeImpl, org.w3c.dom.NodeList
    public Node item(int i) {
        if (hasStringValue()) {
            if (i != 0 || this.value == null) {
                return null;
            }
            makeChildNode();
            return (Node) this.value;
        }
        if (i < 0) {
            return null;
        }
        ChildNode childNode = (ChildNode) this.value;
        for (int i2 = 0; i2 < i && childNode != null; i2++) {
            childNode = childNode.nextSibling;
        }
        return childNode;
    }

    final ChildNode lastChild() {
        makeChildNode();
        Object obj = this.value;
        if (obj != null) {
            return ((ChildNode) obj).previousSibling;
        }
        return null;
    }

    final void lastChild(ChildNode childNode) {
        Object obj = this.value;
        if (obj != null) {
            ((ChildNode) obj).previousSibling = childNode;
        }
    }

    protected void makeChildNode() {
        if (hasStringValue()) {
            if (this.value != null) {
                TextImpl textImpl = (TextImpl) ownerDocument().createTextNode((String) this.value);
                this.value = textImpl;
                textImpl.isFirstChild(true);
                textImpl.previousSibling = textImpl;
                textImpl.ownerNode = this;
                textImpl.isOwned(true);
            }
            hasStringValue(false);
        }
    }

    @Override // org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public void normalize() {
        if (isNormalized() || hasStringValue()) {
            return;
        }
        Node node = (ChildNode) this.value;
        while (node != null) {
            Node nextSibling = node.getNextSibling();
            if (node.getNodeType() == 3) {
                if (nextSibling != null && nextSibling.getNodeType() == 3) {
                    ((Text) node).appendData(nextSibling.getNodeValue());
                    removeChild(nextSibling);
                } else if (node.getNodeValue() == null || node.getNodeValue().length() == 0) {
                    removeChild(node);
                }
            }
            node = nextSibling;
        }
        isNormalized(true);
    }

    @Override // org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public Node removeChild(Node node) throws DOMException {
        if (hasStringValue()) {
            throw new DOMException((short) 8, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NOT_FOUND_ERR", null));
        }
        return internalRemoveChild(node, false);
    }

    void rename(String str) {
        if (needsSyncData()) {
            synchronizeData();
        }
        this.f465name = str;
    }

    @Override // org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public Node replaceChild(Node node, Node node2) throws DOMException {
        makeChildNode();
        CoreDocumentImpl coreDocumentImplOwnerDocument = ownerDocument();
        coreDocumentImplOwnerDocument.replacingNode(this);
        internalInsertBefore(node, node2, true);
        if (node != node2) {
            internalRemoveChild(node2, true);
        }
        coreDocumentImplOwnerDocument.replacedNode(this);
        return node2;
    }

    public void setIdAttribute(boolean z) {
        if (needsSyncData()) {
            synchronizeData();
        }
        isIdAttribute(z);
    }

    @Override // org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public void setNodeValue(String str) throws DOMException {
        setValue(str);
    }

    @Override // org.apache.xerces.dom.NodeImpl
    protected void setOwnerDocument(CoreDocumentImpl coreDocumentImpl) {
        if (needsSyncChildren()) {
            synchronizeChildren();
        }
        super.setOwnerDocument(coreDocumentImpl);
        if (hasStringValue()) {
            return;
        }
        for (ChildNode childNode = (ChildNode) this.value; childNode != null; childNode = childNode.nextSibling) {
            childNode.setOwnerDocument(coreDocumentImpl);
        }
    }

    @Override // org.apache.xerces.dom.NodeImpl
    public void setReadOnly(boolean z, boolean z2) {
        super.setReadOnly(z, z2);
        if (z2) {
            if (needsSyncChildren()) {
                synchronizeChildren();
            }
            if (hasStringValue()) {
                return;
            }
            for (ChildNode childNode = (ChildNode) this.value; childNode != null; childNode = childNode.nextSibling) {
                if (childNode.getNodeType() != 5) {
                    childNode.setReadOnly(z, true);
                }
            }
        }
    }

    public void setSpecified(boolean z) {
        if (needsSyncData()) {
            synchronizeData();
        }
        isSpecified(z);
    }

    public void setType(Object obj) {
        this.type = obj;
    }

    @Override // org.w3c.dom.Attr
    public void setValue(String str) {
        String value;
        TextImpl textImpl;
        String value2;
        CoreDocumentImpl coreDocumentImplOwnerDocument = ownerDocument();
        if (coreDocumentImplOwnerDocument.errorChecking && isReadOnly()) {
            throw new DOMException((short) 7, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NO_MODIFICATION_ALLOWED_ERR", null));
        }
        Element ownerElement = getOwnerElement();
        if (needsSyncData()) {
            synchronizeData();
        }
        if (needsSyncChildren()) {
            synchronizeChildren();
        }
        if (this.value != null) {
            if (coreDocumentImplOwnerDocument.getMutationEvents()) {
                if (!hasStringValue()) {
                    value = getValue();
                    while (true) {
                        Object obj = this.value;
                        if (obj == null) {
                            break;
                        } else {
                            internalRemoveChild((Node) obj, true);
                        }
                    }
                } else {
                    Object obj2 = this.value;
                    value = (String) obj2;
                    textImpl = (TextImpl) coreDocumentImplOwnerDocument.createTextNode((String) obj2);
                    this.value = textImpl;
                    textImpl.isFirstChild(true);
                    textImpl.previousSibling = textImpl;
                    textImpl.ownerNode = this;
                    textImpl.isOwned(true);
                    hasStringValue(false);
                    internalRemoveChild(textImpl, true);
                }
                if (isIdAttribute() && ownerElement != null) {
                    coreDocumentImplOwnerDocument.removeIdentifier(value);
                }
            } else {
                if (hasStringValue()) {
                    value2 = (String) this.value;
                } else {
                    value2 = getValue();
                    ChildNode childNode = (ChildNode) this.value;
                    childNode.previousSibling = null;
                    childNode.isFirstChild(false);
                    childNode.ownerNode = coreDocumentImplOwnerDocument;
                }
                value = value2;
                this.value = null;
                needsSyncChildren(false);
            }
            textImpl = null;
            if (isIdAttribute()) {
                coreDocumentImplOwnerDocument.removeIdentifier(value);
            }
        } else {
            value = "";
            textImpl = null;
        }
        isSpecified(true);
        if (coreDocumentImplOwnerDocument.getMutationEvents()) {
            if (textImpl == null) {
                textImpl = (TextImpl) coreDocumentImplOwnerDocument.createTextNode(str);
            } else {
                textImpl.data = str;
            }
            internalInsertBefore(textImpl, null, true);
            hasStringValue(false);
            coreDocumentImplOwnerDocument.modifiedAttrValue(this, value);
        } else {
            this.value = str;
            hasStringValue(true);
            changed();
        }
        if (!isIdAttribute() || ownerElement == null) {
            return;
        }
        coreDocumentImplOwnerDocument.putIdentifier(str, ownerElement);
    }

    protected void synchronizeChildren() {
        needsSyncChildren(false);
    }

    @Override // org.apache.xerces.dom.NodeImpl
    public String toString() {
        return getName() + "=\"" + getValue() + "\"";
    }
}
