package org.apache.xerces.dom;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.UserDataHandler;

/* JADX INFO: loaded from: classes13.dex */
public abstract class ParentNode extends ChildNode {
    static final long serialVersionUID = 2815829867152120872L;
    protected transient NodeListCache fNodeListCache;
    protected ChildNode firstChild;
    protected CoreDocumentImpl ownerDocument;

    class UserDataRecord implements Serializable {
        private static final long serialVersionUID = 3258126977134310455L;
        Object fData;
        UserDataHandler fHandler;

        UserDataRecord(Object obj, UserDataHandler userDataHandler) {
            this.fData = obj;
            this.fHandler = userDataHandler;
        }
    }

    public ParentNode() {
        this.firstChild = null;
        this.fNodeListCache = null;
    }

    protected ParentNode(CoreDocumentImpl coreDocumentImpl) {
        super(coreDocumentImpl);
        this.firstChild = null;
        this.fNodeListCache = null;
        this.ownerDocument = coreDocumentImpl;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int nodeListGetLength() {
        ChildNode childNode;
        int i = 0;
        if (this.fNodeListCache == null) {
            if (needsSyncChildren()) {
                synchronizeChildren();
            }
            ChildNode childNode2 = this.firstChild;
            if (childNode2 == null) {
                return 0;
            }
            if (childNode2 == lastChild()) {
                return 1;
            }
            this.fNodeListCache = this.ownerDocument.getNodeListCache(this);
        }
        if (this.fNodeListCache.fLength == -1) {
            if (this.fNodeListCache.fChildIndex == -1 || this.fNodeListCache.fChild == null) {
                childNode = this.firstChild;
            } else {
                i = this.fNodeListCache.fChildIndex;
                childNode = this.fNodeListCache.fChild;
            }
            while (childNode != null) {
                i++;
                childNode = childNode.nextSibling;
            }
            this.fNodeListCache.fLength = i;
        }
        return this.fNodeListCache.fLength;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Node nodeListItem(int i) {
        if (this.fNodeListCache == null) {
            if (needsSyncChildren()) {
                synchronizeChildren();
            }
            if (this.firstChild == lastChild()) {
                if (i == 0) {
                    return this.firstChild;
                }
                return null;
            }
            this.fNodeListCache = this.ownerDocument.getNodeListCache(this);
        }
        int i2 = this.fNodeListCache.fChildIndex;
        ChildNode childNodePreviousSibling = this.fNodeListCache.fChild;
        boolean z = false;
        if (i2 == -1 || childNodePreviousSibling == null) {
            if (i < 0) {
                return null;
            }
            childNodePreviousSibling = this.firstChild;
            i2 = 0;
            while (i2 < i && childNodePreviousSibling != null) {
                childNodePreviousSibling = childNodePreviousSibling.nextSibling;
                i2++;
            }
            z = true;
        } else if (i2 < i) {
            while (i2 < i && childNodePreviousSibling != null) {
                i2++;
                childNodePreviousSibling = childNodePreviousSibling.nextSibling;
            }
        } else if (i2 > i) {
            while (i2 > i && childNodePreviousSibling != null) {
                i2--;
                childNodePreviousSibling = childNodePreviousSibling.previousSibling();
            }
        }
        if (z || !(childNodePreviousSibling == this.firstChild || childNodePreviousSibling == lastChild())) {
            this.fNodeListCache.fChildIndex = i2;
            this.fNodeListCache.fChild = childNodePreviousSibling;
        } else {
            this.fNodeListCache.fChildIndex = -1;
            this.fNodeListCache.fChild = null;
            this.ownerDocument.freeNodeListCache(this.fNodeListCache);
        }
        return childNodePreviousSibling;
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

    @Override // org.apache.xerces.dom.ChildNode, org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public Node cloneNode(boolean z) {
        if (needsSyncChildren()) {
            synchronizeChildren();
        }
        ParentNode parentNode = (ParentNode) super.cloneNode(z);
        parentNode.ownerDocument = this.ownerDocument;
        parentNode.firstChild = null;
        parentNode.fNodeListCache = null;
        if (z) {
            for (ChildNode childNode = this.firstChild; childNode != null; childNode = childNode.nextSibling) {
                parentNode.appendChild(childNode.cloneNode(true));
            }
        }
        return parentNode;
    }

    @Override // org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public NodeList getChildNodes() {
        if (needsSyncChildren()) {
            synchronizeChildren();
        }
        return this;
    }

    protected final NodeList getChildNodesUnoptimized() {
        if (needsSyncChildren()) {
            synchronizeChildren();
        }
        return new NodeList() { // from class: org.apache.xerces.dom.ParentNode.1
            @Override // org.w3c.dom.NodeList, org.w3c.dom.html.HTMLFormElement
            public int getLength() {
                return ParentNode.this.nodeListGetLength();
            }

            @Override // org.w3c.dom.NodeList
            public Node item(int i) {
                return ParentNode.this.nodeListItem(i);
            }
        };
    }

    @Override // org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public Node getFirstChild() {
        if (needsSyncChildren()) {
            synchronizeChildren();
        }
        return this.firstChild;
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
        return nodeListGetLength();
    }

    @Override // org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public Document getOwnerDocument() {
        return this.ownerDocument;
    }

    @Override // org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public String getTextContent() throws DOMException {
        Node firstChild = getFirstChild();
        if (firstChild == null) {
            return "";
        }
        if (firstChild.getNextSibling() == null) {
            return hasTextContent(firstChild) ? ((NodeImpl) firstChild).getTextContent() : "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        getTextContent(stringBuffer);
        return stringBuffer.toString();
    }

    @Override // org.apache.xerces.dom.NodeImpl
    void getTextContent(StringBuffer stringBuffer) throws DOMException {
        for (Node firstChild = getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
            if (hasTextContent(firstChild)) {
                ((NodeImpl) firstChild).getTextContent(stringBuffer);
            }
        }
    }

    @Override // org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public boolean hasChildNodes() {
        if (needsSyncChildren()) {
            synchronizeChildren();
        }
        return this.firstChild != null;
    }

    final boolean hasTextContent(Node node) {
        return (node.getNodeType() == 8 || node.getNodeType() == 7 || (node.getNodeType() == 3 && ((TextImpl) node).isIgnorableWhitespace())) ? false : true;
    }

    @Override // org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public Node insertBefore(Node node, Node node2) throws DOMException {
        return internalInsertBefore(node, node2, false);
    }

    Node internalInsertBefore(Node node, Node node2, boolean z) throws DOMException {
        boolean z2 = this.ownerDocument.errorChecking;
        if (node.getNodeType() == 11) {
            if (z2) {
                for (Node firstChild = node.getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
                    if (!this.ownerDocument.isKidOK(this, firstChild)) {
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
            Document ownerDocument = node.getOwnerDocument();
            CoreDocumentImpl coreDocumentImpl = this.ownerDocument;
            if (ownerDocument != coreDocumentImpl && node != coreDocumentImpl) {
                throw new DOMException((short) 4, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "WRONG_DOCUMENT_ERR", null));
            }
            if (!coreDocumentImpl.isKidOK(this, node)) {
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
        this.ownerDocument.insertingNode(this, z);
        ChildNode childNode = (ChildNode) node;
        NodeImpl nodeImplParentNode2 = childNode.parentNode();
        if (nodeImplParentNode2 != null) {
            nodeImplParentNode2.removeChild(childNode);
        }
        ChildNode childNode2 = (ChildNode) node2;
        childNode.ownerNode = this;
        childNode.isOwned(true);
        ChildNode childNode3 = this.firstChild;
        if (childNode3 == null) {
            this.firstChild = childNode;
            childNode.isFirstChild(true);
            childNode.previousSibling = childNode;
        } else if (childNode2 == null) {
            ChildNode childNode4 = childNode3.previousSibling;
            childNode4.nextSibling = childNode;
            childNode.previousSibling = childNode4;
            this.firstChild.previousSibling = childNode;
        } else if (node2 == childNode3) {
            childNode3.isFirstChild(false);
            childNode.nextSibling = this.firstChild;
            childNode.previousSibling = this.firstChild.previousSibling;
            this.firstChild.previousSibling = childNode;
            this.firstChild = childNode;
            childNode.isFirstChild(true);
        } else {
            ChildNode childNode5 = childNode2.previousSibling;
            childNode.nextSibling = childNode2;
            childNode5.nextSibling = childNode;
            childNode2.previousSibling = childNode;
            childNode.previousSibling = childNode5;
        }
        changed();
        NodeListCache nodeListCache = this.fNodeListCache;
        if (nodeListCache != null) {
            if (nodeListCache.fLength != -1) {
                this.fNodeListCache.fLength++;
            }
            if (this.fNodeListCache.fChildIndex != -1) {
                if (this.fNodeListCache.fChild == childNode2) {
                    this.fNodeListCache.fChild = childNode;
                } else {
                    this.fNodeListCache.fChildIndex = -1;
                }
            }
        }
        this.ownerDocument.insertedNode(this, childNode, z);
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
        ChildNode childNodePreviousSibling = childNode.previousSibling();
        NodeListCache nodeListCache = this.fNodeListCache;
        if (nodeListCache != null) {
            if (nodeListCache.fLength != -1) {
                this.fNodeListCache.fLength--;
            }
            if (this.fNodeListCache.fChildIndex != -1) {
                if (this.fNodeListCache.fChild == childNode) {
                    this.fNodeListCache.fChildIndex--;
                    this.fNodeListCache.fChild = childNodePreviousSibling;
                } else {
                    this.fNodeListCache.fChildIndex = -1;
                }
            }
        }
        if (childNode == this.firstChild) {
            childNode.isFirstChild(false);
            ChildNode childNode2 = childNode.nextSibling;
            this.firstChild = childNode2;
            if (childNode2 != null) {
                childNode2.isFirstChild(true);
                this.firstChild.previousSibling = childNode.previousSibling;
            }
        } else {
            ChildNode childNode3 = childNode.previousSibling;
            ChildNode childNode4 = childNode.nextSibling;
            childNode3.nextSibling = childNode4;
            if (childNode4 == null) {
                childNode4 = this.firstChild;
            }
            childNode4.previousSibling = childNode3;
        }
        childNode.ownerNode = coreDocumentImplOwnerDocument;
        childNode.isOwned(false);
        childNode.nextSibling = null;
        childNode.previousSibling = null;
        changed();
        coreDocumentImplOwnerDocument.removedNode(this, z);
        checkNormalizationAfterRemove(childNodePreviousSibling);
        return childNode;
    }

    @Override // org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public boolean isEqualNode(Node node) {
        if (!super.isEqualNode(node)) {
            return false;
        }
        Node firstChild = getFirstChild();
        Node firstChild2 = node.getFirstChild();
        while (firstChild != null && firstChild2 != null) {
            if (!firstChild.isEqualNode(firstChild2)) {
                return false;
            }
            firstChild = firstChild.getNextSibling();
            firstChild2 = firstChild2.getNextSibling();
        }
        return firstChild == firstChild2;
    }

    @Override // org.apache.xerces.dom.NodeImpl, org.w3c.dom.NodeList
    public Node item(int i) {
        return nodeListItem(i);
    }

    final ChildNode lastChild() {
        ChildNode childNode = this.firstChild;
        if (childNode != null) {
            return childNode.previousSibling;
        }
        return null;
    }

    final void lastChild(ChildNode childNode) {
        ChildNode childNode2 = this.firstChild;
        if (childNode2 != null) {
            childNode2.previousSibling = childNode;
        }
    }

    @Override // org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public void normalize() {
        if (isNormalized()) {
            return;
        }
        if (needsSyncChildren()) {
            synchronizeChildren();
        }
        for (ChildNode childNode = this.firstChild; childNode != null; childNode = childNode.nextSibling) {
            childNode.normalize();
        }
        isNormalized(true);
    }

    @Override // org.apache.xerces.dom.NodeImpl
    CoreDocumentImpl ownerDocument() {
        return this.ownerDocument;
    }

    @Override // org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public Node removeChild(Node node) throws DOMException {
        return internalRemoveChild(node, false);
    }

    @Override // org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public Node replaceChild(Node node, Node node2) throws DOMException {
        this.ownerDocument.replacingNode(this);
        internalInsertBefore(node, node2, true);
        if (node != node2) {
            internalRemoveChild(node2, true);
        }
        this.ownerDocument.replacedNode(this);
        return node2;
    }

    @Override // org.apache.xerces.dom.NodeImpl
    protected void setOwnerDocument(CoreDocumentImpl coreDocumentImpl) {
        if (needsSyncChildren()) {
            synchronizeChildren();
        }
        super.setOwnerDocument(coreDocumentImpl);
        this.ownerDocument = coreDocumentImpl;
        for (ChildNode childNode = this.firstChild; childNode != null; childNode = childNode.nextSibling) {
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
            for (ChildNode childNode = this.firstChild; childNode != null; childNode = childNode.nextSibling) {
                if (childNode.getNodeType() != 5) {
                    childNode.setReadOnly(z, true);
                }
            }
        }
    }

    @Override // org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public void setTextContent(String str) throws DOMException {
        while (true) {
            Node firstChild = getFirstChild();
            if (firstChild == null) {
                break;
            } else {
                removeChild(firstChild);
            }
        }
        if (str == null || str.length() == 0) {
            return;
        }
        appendChild(ownerDocument().createTextNode(str));
    }

    protected void synchronizeChildren() {
        needsSyncChildren(false);
    }
}
