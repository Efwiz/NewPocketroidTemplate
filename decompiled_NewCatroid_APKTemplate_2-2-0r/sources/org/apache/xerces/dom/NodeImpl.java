package org.apache.xerces.dom;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Hashtable;
import javax.xml.XMLConstants;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.UserDataHandler;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;

/* JADX INFO: loaded from: classes13.dex */
public abstract class NodeImpl implements Node, NodeList, EventTarget, Cloneable, Serializable {
    public static final short DOCUMENT_POSITION_CONTAINS = 8;
    public static final short DOCUMENT_POSITION_DISCONNECTED = 1;
    public static final short DOCUMENT_POSITION_FOLLOWING = 4;
    public static final short DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC = 32;
    public static final short DOCUMENT_POSITION_IS_CONTAINED = 16;
    public static final short DOCUMENT_POSITION_PRECEDING = 2;
    public static final short ELEMENT_DEFINITION_NODE = 21;
    protected static final short FIRSTCHILD = 16;
    protected static final short HASSTRING = 128;
    protected static final short ID = 512;
    protected static final short IGNORABLEWS = 64;
    protected static final short NORMALIZED = 256;
    protected static final short OWNED = 8;
    protected static final short READONLY = 1;
    protected static final short SPECIFIED = 32;
    protected static final short SYNCCHILDREN = 4;
    protected static final short SYNCDATA = 2;
    public static final short TREE_POSITION_ANCESTOR = 4;
    public static final short TREE_POSITION_DESCENDANT = 8;
    public static final short TREE_POSITION_DISCONNECTED = 0;
    public static final short TREE_POSITION_EQUIVALENT = 16;
    public static final short TREE_POSITION_FOLLOWING = 2;
    public static final short TREE_POSITION_PRECEDING = 1;
    public static final short TREE_POSITION_SAME_NODE = 32;
    static final long serialVersionUID = -6316591992167219696L;
    protected short flags;
    protected NodeImpl ownerNode;

    public NodeImpl() {
    }

    protected NodeImpl(CoreDocumentImpl coreDocumentImpl) {
        this.ownerNode = coreDocumentImpl;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        if (needsSyncData()) {
            synchronizeData();
        }
        objectOutputStream.defaultWriteObject();
    }

    @Override // org.w3c.dom.events.EventTarget
    public void addEventListener(String str, EventListener eventListener, boolean z) {
        ownerDocument().addEventListener(this, str, eventListener, z);
    }

    @Override // org.w3c.dom.Node
    public Node appendChild(Node node) throws DOMException {
        return insertBefore(node, null);
    }

    protected void changed() {
        ownerDocument().changed();
    }

    protected int changes() {
        return ownerDocument().changes();
    }

    @Override // org.w3c.dom.Node
    public Node cloneNode(boolean z) {
        if (needsSyncData()) {
            synchronizeData();
        }
        try {
            NodeImpl nodeImpl = (NodeImpl) clone();
            nodeImpl.ownerNode = ownerDocument();
            nodeImpl.isOwned(false);
            nodeImpl.isReadOnly(false);
            ownerDocument().callUserDataHandlers(this, nodeImpl, (short) 1);
            return nodeImpl;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("**Internal Error**" + e);
        }
    }

    /*  JADX ERROR: Type inference failed
        jadx.core.utils.exceptions.JadxOverflowException: Type inference error: updates count limit reached with updateSeq = 4211. Try increasing type updates limit count.
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:79)
        */
    @Override // org.w3c.dom.Node
    public short compareDocumentPosition(org.w3c.dom.Node r20) throws org.w3c.dom.DOMException {
        /*
            Method dump skipped, instruction units count: 421
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.xerces.dom.NodeImpl.compareDocumentPosition(org.w3c.dom.Node):short");
    }

    public short compareTreePosition(Node node) {
        Node node2;
        Node node3;
        if (this == node) {
            return (short) 48;
        }
        short nodeType = getNodeType();
        short nodeType2 = node.getNodeType();
        if (nodeType != 6 && nodeType != 12 && nodeType2 != 6 && nodeType2 != 12) {
            Node parentNode = this;
            Node node4 = parentNode;
            int i = 0;
            while (parentNode != null) {
                i++;
                if (parentNode == node) {
                    return (short) 5;
                }
                node4 = parentNode;
                parentNode = parentNode.getParentNode();
            }
            Node parentNode2 = node;
            Node node5 = parentNode2;
            int i2 = 0;
            while (parentNode2 != null) {
                i2++;
                if (parentNode2 == this) {
                    return (short) 10;
                }
                node5 = parentNode2;
                parentNode2 = parentNode2.getParentNode();
            }
            short nodeType3 = node4.getNodeType();
            short nodeType4 = node5.getNodeType();
            Node ownerElement = nodeType3 == 2 ? ((AttrImpl) node4).getOwnerElement() : this;
            if (nodeType4 == 2) {
                node = ((AttrImpl) node5).getOwnerElement();
            }
            if (nodeType3 == 2 && nodeType4 == 2 && ownerElement == node) {
                return (short) 16;
            }
            if (nodeType3 == 2) {
                i = 0;
                for (Node parentNode3 = ownerElement; parentNode3 != null; parentNode3 = parentNode3.getParentNode()) {
                    i++;
                    if (parentNode3 == node) {
                        return (short) 1;
                    }
                    node4 = parentNode3;
                }
            }
            if (nodeType4 == 2) {
                i2 = 0;
                for (Node parentNode4 = node; parentNode4 != null; parentNode4 = parentNode4.getParentNode()) {
                    i2++;
                    if (parentNode4 == ownerElement) {
                        return (short) 2;
                    }
                    node5 = parentNode4;
                }
            }
            if (node4 != node5) {
                return (short) 0;
            }
            int i3 = 0;
            if (i > i2) {
                while (i3 < i - i2) {
                    ownerElement = ownerElement.getParentNode();
                    i3++;
                }
                if (ownerElement == node) {
                    return (short) 1;
                }
            } else {
                while (i3 < i2 - i) {
                    node = node.getParentNode();
                    i3++;
                }
                if (node == ownerElement) {
                    return (short) 2;
                }
            }
            do {
                Node parentNode5 = ownerElement.getParentNode();
                node2 = node;
                node = node.getParentNode();
                node3 = ownerElement;
                ownerElement = parentNode5;
            } while (ownerElement != node);
            for (Node firstChild = ownerElement.getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
                if (firstChild == node2) {
                    return (short) 1;
                }
                if (firstChild == node3) {
                    return (short) 2;
                }
            }
        }
        return (short) 0;
    }

    @Override // org.w3c.dom.events.EventTarget
    public boolean dispatchEvent(Event event) {
        return ownerDocument().dispatchEvent(this, event);
    }

    @Override // org.w3c.dom.Node
    public NamedNodeMap getAttributes() {
        return null;
    }

    @Override // org.w3c.dom.Node
    public String getBaseURI() {
        return null;
    }

    @Override // org.w3c.dom.Node
    public NodeList getChildNodes() {
        return this;
    }

    protected Node getContainer() {
        return null;
    }

    Node getElementAncestor(Node node) {
        do {
            node = node.getParentNode();
            if (node == null) {
                return null;
            }
        } while (node.getNodeType() != 1);
        return node;
    }

    @Override // org.w3c.dom.Node
    public Object getFeature(String str, String str2) {
        if (isSupported(str, str2)) {
            return this;
        }
        return null;
    }

    @Override // org.w3c.dom.Node
    public Node getFirstChild() {
        return null;
    }

    @Override // org.w3c.dom.Node
    public Node getLastChild() {
        return null;
    }

    public int getLength() {
        return 0;
    }

    @Override // org.w3c.dom.Node
    public String getLocalName() {
        return null;
    }

    @Override // org.w3c.dom.Node
    public String getNamespaceURI() {
        return null;
    }

    @Override // org.w3c.dom.Node
    public Node getNextSibling() {
        return null;
    }

    @Override // org.w3c.dom.Node
    public abstract String getNodeName();

    protected int getNodeNumber() {
        return ((CoreDocumentImpl) getOwnerDocument()).getNodeNumber(this);
    }

    @Override // org.w3c.dom.Node
    public abstract short getNodeType();

    @Override // org.w3c.dom.Node
    public String getNodeValue() throws DOMException {
        return null;
    }

    @Override // org.w3c.dom.Node
    public Document getOwnerDocument() {
        return isOwned() ? this.ownerNode.ownerDocument() : (Document) this.ownerNode;
    }

    @Override // org.w3c.dom.Node
    public Node getParentNode() {
        return null;
    }

    @Override // org.w3c.dom.Node
    public String getPrefix() {
        return null;
    }

    @Override // org.w3c.dom.Node
    public Node getPreviousSibling() {
        return null;
    }

    public boolean getReadOnly() {
        if (needsSyncData()) {
            synchronizeData();
        }
        return isReadOnly();
    }

    @Override // org.w3c.dom.Node
    public String getTextContent() throws DOMException {
        return getNodeValue();
    }

    void getTextContent(StringBuffer stringBuffer) throws DOMException {
        String nodeValue = getNodeValue();
        if (nodeValue != null) {
            stringBuffer.append(nodeValue);
        }
    }

    public Object getUserData() {
        return ownerDocument().getUserData(this);
    }

    @Override // org.w3c.dom.Node
    public Object getUserData(String str) {
        return ownerDocument().getUserData(this, str);
    }

    protected Hashtable getUserDataRecord() {
        return ownerDocument().getUserDataRecord(this);
    }

    @Override // org.w3c.dom.Node
    public boolean hasAttributes() {
        return false;
    }

    @Override // org.w3c.dom.Node
    public boolean hasChildNodes() {
        return false;
    }

    final void hasStringValue(boolean z) {
        this.flags = (short) (z ? this.flags | 128 : this.flags & (-129));
    }

    final boolean hasStringValue() {
        return (this.flags & 128) != 0;
    }

    @Override // org.w3c.dom.Node
    public Node insertBefore(Node node, Node node2) throws DOMException {
        throw new DOMException((short) 3, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "HIERARCHY_REQUEST_ERR", null));
    }

    final boolean internalIsIgnorableWhitespace() {
        return (this.flags & 64) != 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.w3c.dom.Node
    public boolean isDefaultNamespace(String str) {
        NodeImpl nodeImpl;
        short nodeType = getNodeType();
        if (nodeType != 1) {
            if (nodeType == 2) {
                if (this.ownerNode.getNodeType() == 1) {
                    return this.ownerNode.isDefaultNamespace(str);
                }
                return false;
            }
            if (nodeType != 6) {
                switch (nodeType) {
                    case 9:
                        Element documentElement = ((Document) this).getDocumentElement();
                        if (documentElement != null) {
                            return documentElement.isDefaultNamespace(str);
                        }
                    case 10:
                    case 11:
                    case 12:
                        return false;
                    default:
                        NodeImpl nodeImpl2 = (NodeImpl) getElementAncestor(this);
                        if (nodeImpl2 != null) {
                            return nodeImpl2.isDefaultNamespace(str);
                        }
                        return false;
                }
            }
            return false;
        }
        String namespaceURI = getNamespaceURI();
        String prefix = getPrefix();
        if (prefix == null || prefix.length() == 0) {
            if (str == null) {
                return namespaceURI == str;
            }
            return str.equals(namespaceURI);
        }
        if (!hasAttributes() || (nodeImpl = (NodeImpl) ((ElementImpl) this).getAttributeNodeNS(XMLConstants.XMLNS_ATTRIBUTE_NS_URI, XMLConstants.XMLNS_ATTRIBUTE)) == null) {
            NodeImpl nodeImpl3 = (NodeImpl) getElementAncestor(this);
            if (nodeImpl3 != null) {
                return nodeImpl3.isDefaultNamespace(str);
            }
            return false;
        }
        String nodeValue = nodeImpl.getNodeValue();
        if (str == null) {
            return namespaceURI == nodeValue;
        }
        return str.equals(nodeValue);
    }

    @Override // org.w3c.dom.Node
    public boolean isEqualNode(Node node) {
        if (node == this) {
            return true;
        }
        if (node.getNodeType() != getNodeType()) {
            return false;
        }
        if (getNodeName() == null) {
            if (node.getNodeName() != null) {
                return false;
            }
        } else if (!getNodeName().equals(node.getNodeName())) {
            return false;
        }
        if (getLocalName() == null) {
            if (node.getLocalName() != null) {
                return false;
            }
        } else if (!getLocalName().equals(node.getLocalName())) {
            return false;
        }
        if (getNamespaceURI() == null) {
            if (node.getNamespaceURI() != null) {
                return false;
            }
        } else if (!getNamespaceURI().equals(node.getNamespaceURI())) {
            return false;
        }
        if (getPrefix() == null) {
            if (node.getPrefix() != null) {
                return false;
            }
        } else if (!getPrefix().equals(node.getPrefix())) {
            return false;
        }
        if (getNodeValue() == null) {
            if (node.getNodeValue() != null) {
                return false;
            }
        } else if (!getNodeValue().equals(node.getNodeValue())) {
            return false;
        }
        return true;
    }

    final void isFirstChild(boolean z) {
        this.flags = (short) (z ? this.flags | 16 : this.flags & (-17));
    }

    final boolean isFirstChild() {
        return (this.flags & 16) != 0;
    }

    final void isIdAttribute(boolean z) {
        this.flags = (short) (z ? this.flags | 512 : this.flags & (-513));
    }

    final boolean isIdAttribute() {
        return (this.flags & 512) != 0;
    }

    final void isIgnorableWhitespace(boolean z) {
        this.flags = (short) (z ? this.flags | 64 : this.flags & (-65));
    }

    final void isNormalized(boolean z) {
        NodeImpl nodeImpl;
        if (!z && isNormalized() && (nodeImpl = this.ownerNode) != null) {
            nodeImpl.isNormalized(false);
        }
        this.flags = (short) (z ? this.flags | 256 : this.flags & (-257));
    }

    final boolean isNormalized() {
        return (this.flags & 256) != 0;
    }

    final void isOwned(boolean z) {
        this.flags = (short) (z ? this.flags | 8 : this.flags & (-9));
    }

    final boolean isOwned() {
        return (this.flags & 8) != 0;
    }

    final void isReadOnly(boolean z) {
        this.flags = (short) (z ? this.flags | 1 : this.flags & (-2));
    }

    final boolean isReadOnly() {
        return (this.flags & 1) != 0;
    }

    @Override // org.w3c.dom.Node
    public boolean isSameNode(Node node) {
        return this == node;
    }

    final void isSpecified(boolean z) {
        this.flags = (short) (z ? this.flags | 32 : this.flags & (-33));
    }

    final boolean isSpecified() {
        return (this.flags & 32) != 0;
    }

    @Override // org.w3c.dom.Node
    public boolean isSupported(String str, String str2) {
        return ownerDocument().getImplementation().hasFeature(str, str2);
    }

    public Node item(int i) {
        return null;
    }

    String lookupNamespacePrefix(String str, ElementImpl elementImpl) {
        String localName;
        String strLookupNamespaceURI;
        String strLookupNamespaceURI2;
        String namespaceURI = getNamespaceURI();
        String prefix = getPrefix();
        if (namespaceURI != null && namespaceURI.equals(str) && prefix != null && (strLookupNamespaceURI2 = elementImpl.lookupNamespaceURI(prefix)) != null && strLookupNamespaceURI2.equals(str)) {
            return prefix;
        }
        if (hasAttributes()) {
            NamedNodeMap attributes = getAttributes();
            int length = attributes.getLength();
            for (int i = 0; i < length; i++) {
                Node nodeItem = attributes.item(i);
                String namespaceURI2 = nodeItem.getNamespaceURI();
                if (namespaceURI2 != null && namespaceURI2.equals(XMLConstants.XMLNS_ATTRIBUTE_NS_URI)) {
                    String prefix2 = nodeItem.getPrefix();
                    String nodeValue = nodeItem.getNodeValue();
                    if ((nodeItem.getNodeName().equals(XMLConstants.XMLNS_ATTRIBUTE) || (prefix2 != null && prefix2.equals(XMLConstants.XMLNS_ATTRIBUTE) && nodeValue.equals(str))) && (strLookupNamespaceURI = elementImpl.lookupNamespaceURI((localName = nodeItem.getLocalName()))) != null && strLookupNamespaceURI.equals(str)) {
                        return localName;
                    }
                }
            }
        }
        NodeImpl nodeImpl = (NodeImpl) getElementAncestor(this);
        if (nodeImpl != null) {
            return nodeImpl.lookupNamespacePrefix(str, elementImpl);
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.w3c.dom.Node
    public String lookupNamespaceURI(String str) {
        short nodeType = getNodeType();
        if (nodeType != 1) {
            if (nodeType == 2) {
                if (this.ownerNode.getNodeType() == 1) {
                    return this.ownerNode.lookupNamespaceURI(str);
                }
                return null;
            }
            if (nodeType != 6) {
                switch (nodeType) {
                    case 9:
                        Element documentElement = ((Document) this).getDocumentElement();
                        if (documentElement != null) {
                            return documentElement.lookupNamespaceURI(str);
                        }
                    case 10:
                    case 11:
                    case 12:
                        return null;
                    default:
                        NodeImpl nodeImpl = (NodeImpl) getElementAncestor(this);
                        if (nodeImpl != null) {
                            return nodeImpl.lookupNamespaceURI(str);
                        }
                        return null;
                }
            }
            return null;
        }
        String namespaceURI = getNamespaceURI();
        String prefix = getPrefix();
        if (namespaceURI != null) {
            if (str == null && prefix == str) {
                return namespaceURI;
            }
            if (prefix != null && prefix.equals(str)) {
                return namespaceURI;
            }
        }
        if (hasAttributes()) {
            NamedNodeMap attributes = getAttributes();
            int length = attributes.getLength();
            for (int i = 0; i < length; i++) {
                Node nodeItem = attributes.item(i);
                String namespaceURI2 = nodeItem.getNamespaceURI();
                if (namespaceURI2 != null && namespaceURI2.equals(XMLConstants.XMLNS_ATTRIBUTE_NS_URI)) {
                    String prefix2 = nodeItem.getPrefix();
                    String nodeValue = nodeItem.getNodeValue();
                    if (str == null && nodeItem.getNodeName().equals(XMLConstants.XMLNS_ATTRIBUTE)) {
                        if (nodeValue.length() > 0) {
                            return nodeValue;
                        }
                        return null;
                    }
                    if (prefix2 != null && prefix2.equals(XMLConstants.XMLNS_ATTRIBUTE) && nodeItem.getLocalName().equals(str)) {
                        if (nodeValue.length() > 0) {
                            return nodeValue;
                        }
                        return null;
                    }
                }
            }
        }
        NodeImpl nodeImpl2 = (NodeImpl) getElementAncestor(this);
        if (nodeImpl2 != null) {
            return nodeImpl2.lookupNamespaceURI(str);
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.w3c.dom.Node
    public String lookupPrefix(String str) {
        if (str == null) {
            return null;
        }
        short nodeType = getNodeType();
        if (nodeType == 1) {
            getNamespaceURI();
            return lookupNamespacePrefix(str, (ElementImpl) this);
        }
        if (nodeType == 2) {
            if (this.ownerNode.getNodeType() == 1) {
                return this.ownerNode.lookupPrefix(str);
            }
            return null;
        }
        if (nodeType != 6) {
            switch (nodeType) {
                case 9:
                    Element documentElement = ((Document) this).getDocumentElement();
                    if (documentElement != null) {
                        return documentElement.lookupPrefix(str);
                    }
                case 10:
                case 11:
                case 12:
                    return null;
                default:
                    NodeImpl nodeImpl = (NodeImpl) getElementAncestor(this);
                    if (nodeImpl != null) {
                        return nodeImpl.lookupPrefix(str);
                    }
                    return null;
            }
        }
        return null;
    }

    public final void needsSyncChildren(boolean z) {
        this.flags = (short) (z ? this.flags | 4 : this.flags & (-5));
    }

    final boolean needsSyncChildren() {
        return (this.flags & 4) != 0;
    }

    final void needsSyncData(boolean z) {
        this.flags = (short) (z ? this.flags | 2 : this.flags & (-3));
    }

    final boolean needsSyncData() {
        return (this.flags & 2) != 0;
    }

    @Override // org.w3c.dom.Node
    public void normalize() {
    }

    CoreDocumentImpl ownerDocument() {
        return isOwned() ? this.ownerNode.ownerDocument() : (CoreDocumentImpl) this.ownerNode;
    }

    NodeImpl parentNode() {
        return null;
    }

    ChildNode previousSibling() {
        return null;
    }

    @Override // org.w3c.dom.Node
    public Node removeChild(Node node) throws DOMException {
        throw new DOMException((short) 8, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NOT_FOUND_ERR", null));
    }

    @Override // org.w3c.dom.events.EventTarget
    public void removeEventListener(String str, EventListener eventListener, boolean z) {
        ownerDocument().removeEventListener(this, str, eventListener, z);
    }

    @Override // org.w3c.dom.Node
    public Node replaceChild(Node node, Node node2) throws DOMException {
        throw new DOMException((short) 3, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "HIERARCHY_REQUEST_ERR", null));
    }

    @Override // org.w3c.dom.Node
    public void setNodeValue(String str) throws DOMException {
    }

    protected void setOwnerDocument(CoreDocumentImpl coreDocumentImpl) {
        if (needsSyncData()) {
            synchronizeData();
        }
        if (isOwned()) {
            return;
        }
        this.ownerNode = coreDocumentImpl;
    }

    @Override // org.w3c.dom.Node
    public void setPrefix(String str) throws DOMException {
        throw new DOMException((short) 14, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NAMESPACE_ERR", null));
    }

    public void setReadOnly(boolean z, boolean z2) {
        if (needsSyncData()) {
            synchronizeData();
        }
        isReadOnly(z);
    }

    @Override // org.w3c.dom.Node
    public void setTextContent(String str) throws DOMException {
        setNodeValue(str);
    }

    @Override // org.w3c.dom.Node
    public Object setUserData(String str, Object obj, UserDataHandler userDataHandler) {
        return ownerDocument().setUserData(this, str, obj, userDataHandler);
    }

    public void setUserData(Object obj) {
        ownerDocument().setUserData(this, obj);
    }

    protected void synchronizeData() {
        needsSyncData(false);
    }

    public String toString() {
        return "[" + getNodeName() + ": " + getNodeValue() + "]";
    }
}
