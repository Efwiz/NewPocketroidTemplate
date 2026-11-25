package org.apache.xerces.dom;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import org.w3c.dom.DOMException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/* JADX INFO: loaded from: classes13.dex */
public class NamedNodeMapImpl implements NamedNodeMap, Serializable {
    protected static final short CHANGED = 2;
    protected static final short HASDEFAULTS = 4;
    protected static final short READONLY = 1;
    static final long serialVersionUID = -7039242451046758020L;
    protected short flags;
    protected List nodes;
    protected NodeImpl ownerNode;

    protected NamedNodeMapImpl(NodeImpl nodeImpl) {
        this.ownerNode = nodeImpl;
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        if (this.nodes != null) {
            this.nodes = new ArrayList((Vector) this.nodes);
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        List list = this.nodes;
        if (list != null) {
            try {
                this.nodes = new Vector(list);
            } finally {
                this.nodes = list;
            }
        }
        objectOutputStream.defaultWriteObject();
    }

    protected int addItem(Node node) {
        int iFindNamePoint = findNamePoint(node.getNamespaceURI(), node.getLocalName());
        if (iFindNamePoint >= 0) {
            this.nodes.set(iFindNamePoint, node);
        } else {
            iFindNamePoint = findNamePoint(node.getNodeName(), 0);
            if (iFindNamePoint < 0) {
                iFindNamePoint = (-1) - iFindNamePoint;
                if (this.nodes == null) {
                    this.nodes = new ArrayList(5);
                }
            }
            this.nodes.add(iFindNamePoint, node);
        }
        return iFindNamePoint;
    }

    final void changed(boolean z) {
        this.flags = (short) (z ? this.flags | 2 : this.flags & (-3));
    }

    final boolean changed() {
        return (this.flags & 2) != 0;
    }

    protected void cloneContent(NamedNodeMapImpl namedNodeMapImpl) {
        int size;
        List list = namedNodeMapImpl.nodes;
        if (list == null || (size = list.size()) == 0) {
            return;
        }
        List list2 = this.nodes;
        if (list2 == null) {
            this.nodes = new ArrayList(size);
        } else {
            list2.clear();
        }
        for (int i = 0; i < size; i++) {
            NodeImpl nodeImpl = (NodeImpl) namedNodeMapImpl.nodes.get(i);
            NodeImpl nodeImpl2 = (NodeImpl) nodeImpl.cloneNode(true);
            nodeImpl2.isSpecified(nodeImpl.isSpecified());
            this.nodes.add(nodeImpl2);
        }
    }

    protected ArrayList cloneMap(ArrayList arrayList) {
        if (arrayList == null) {
            arrayList = new ArrayList(5);
        }
        arrayList.clear();
        List list = this.nodes;
        if (list != null) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                arrayList.add(this.nodes.get(i));
            }
        }
        return arrayList;
    }

    public NamedNodeMapImpl cloneMap(NodeImpl nodeImpl) {
        NamedNodeMapImpl namedNodeMapImpl = new NamedNodeMapImpl(nodeImpl);
        namedNodeMapImpl.cloneContent(this);
        return namedNodeMapImpl;
    }

    protected int findNamePoint(String str, int i) {
        List list = this.nodes;
        int i2 = 0;
        if (list != null) {
            int size = list.size() - 1;
            while (i <= size) {
                i2 = (i + size) / 2;
                int iCompareTo = str.compareTo(((Node) this.nodes.get(i2)).getNodeName());
                if (iCompareTo == 0) {
                    return i2;
                }
                if (iCompareTo < 0) {
                    size = i2 - 1;
                } else {
                    i = i2 + 1;
                }
            }
            if (i > i2) {
                i2 = i;
            }
        }
        return (-1) - i2;
    }

    protected int findNamePoint(String str, String str2) {
        List list = this.nodes;
        if (list == null || str2 == null) {
            return -1;
        }
        int size = list.size();
        for (int i = 0; i < size; i++) {
            NodeImpl nodeImpl = (NodeImpl) this.nodes.get(i);
            String namespaceURI = nodeImpl.getNamespaceURI();
            String localName = nodeImpl.getLocalName();
            if (str == null) {
                if (namespaceURI == null && (str2.equals(localName) || (localName == null && str2.equals(nodeImpl.getNodeName())))) {
                    return i;
                }
            } else if (str.equals(namespaceURI) && str2.equals(localName)) {
                return i;
            }
        }
        return -1;
    }

    protected Object getItem(int i) {
        List list = this.nodes;
        if (list != null) {
            return list.get(i);
        }
        return null;
    }

    @Override // org.w3c.dom.NamedNodeMap
    public int getLength() {
        List list = this.nodes;
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override // org.w3c.dom.NamedNodeMap
    public Node getNamedItem(String str) {
        int iFindNamePoint = findNamePoint(str, 0);
        if (iFindNamePoint < 0) {
            return null;
        }
        return (Node) this.nodes.get(iFindNamePoint);
    }

    protected int getNamedItemIndex(String str, String str2) {
        return findNamePoint(str, str2);
    }

    @Override // org.w3c.dom.NamedNodeMap
    public Node getNamedItemNS(String str, String str2) {
        int iFindNamePoint = findNamePoint(str, str2);
        if (iFindNamePoint < 0) {
            return null;
        }
        return (Node) this.nodes.get(iFindNamePoint);
    }

    boolean getReadOnly() {
        return isReadOnly();
    }

    final void hasDefaults(boolean z) {
        this.flags = (short) (z ? this.flags | 4 : this.flags & (-5));
    }

    final boolean hasDefaults() {
        return (this.flags & 4) != 0;
    }

    final void isReadOnly(boolean z) {
        this.flags = (short) (z ? this.flags | 1 : this.flags & (-2));
    }

    final boolean isReadOnly() {
        return (this.flags & 1) != 0;
    }

    @Override // org.w3c.dom.NamedNodeMap
    public Node item(int i) {
        List list = this.nodes;
        if (list == null || i >= list.size()) {
            return null;
        }
        return (Node) this.nodes.get(i);
    }

    protected boolean precedes(Node node, Node node2) {
        List list = this.nodes;
        if (list != null) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                Node node3 = (Node) this.nodes.get(i);
                if (node3 == node) {
                    return true;
                }
                if (node3 == node2) {
                    return false;
                }
            }
        }
        return false;
    }

    public void removeAll() {
        List list = this.nodes;
        if (list != null) {
            list.clear();
        }
    }

    protected void removeItem(int i) {
        List list = this.nodes;
        if (list == null || i >= list.size()) {
            return;
        }
        this.nodes.remove(i);
    }

    @Override // org.w3c.dom.NamedNodeMap
    public Node removeNamedItem(String str) throws DOMException {
        if (isReadOnly()) {
            throw new DOMException((short) 7, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NO_MODIFICATION_ALLOWED_ERR", null));
        }
        int iFindNamePoint = findNamePoint(str, 0);
        if (iFindNamePoint < 0) {
            throw new DOMException((short) 8, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NOT_FOUND_ERR", null));
        }
        NodeImpl nodeImpl = (NodeImpl) this.nodes.get(iFindNamePoint);
        this.nodes.remove(iFindNamePoint);
        return nodeImpl;
    }

    @Override // org.w3c.dom.NamedNodeMap
    public Node removeNamedItemNS(String str, String str2) throws DOMException {
        if (isReadOnly()) {
            throw new DOMException((short) 7, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NO_MODIFICATION_ALLOWED_ERR", null));
        }
        int iFindNamePoint = findNamePoint(str, str2);
        if (iFindNamePoint < 0) {
            throw new DOMException((short) 8, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NOT_FOUND_ERR", null));
        }
        NodeImpl nodeImpl = (NodeImpl) this.nodes.get(iFindNamePoint);
        this.nodes.remove(iFindNamePoint);
        return nodeImpl;
    }

    @Override // org.w3c.dom.NamedNodeMap
    public Node setNamedItem(Node node) throws DOMException {
        CoreDocumentImpl coreDocumentImplOwnerDocument = this.ownerNode.ownerDocument();
        if (coreDocumentImplOwnerDocument.errorChecking) {
            if (isReadOnly()) {
                throw new DOMException((short) 7, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NO_MODIFICATION_ALLOWED_ERR", null));
            }
            if (node.getOwnerDocument() != coreDocumentImplOwnerDocument) {
                throw new DOMException((short) 4, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "WRONG_DOCUMENT_ERR", null));
            }
        }
        int iFindNamePoint = findNamePoint(node.getNodeName(), 0);
        if (iFindNamePoint >= 0) {
            NodeImpl nodeImpl = (NodeImpl) this.nodes.get(iFindNamePoint);
            this.nodes.set(iFindNamePoint, node);
            return nodeImpl;
        }
        int i = (-1) - iFindNamePoint;
        if (this.nodes == null) {
            this.nodes = new ArrayList(5);
        }
        this.nodes.add(i, node);
        return null;
    }

    @Override // org.w3c.dom.NamedNodeMap
    public Node setNamedItemNS(Node node) throws DOMException {
        CoreDocumentImpl coreDocumentImplOwnerDocument = this.ownerNode.ownerDocument();
        NodeImpl nodeImpl = null;
        if (coreDocumentImplOwnerDocument.errorChecking) {
            if (isReadOnly()) {
                throw new DOMException((short) 7, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NO_MODIFICATION_ALLOWED_ERR", null));
            }
            if (node.getOwnerDocument() != coreDocumentImplOwnerDocument) {
                throw new DOMException((short) 4, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "WRONG_DOCUMENT_ERR", null));
            }
        }
        int iFindNamePoint = findNamePoint(node.getNamespaceURI(), node.getLocalName());
        if (iFindNamePoint >= 0) {
            NodeImpl nodeImpl2 = (NodeImpl) this.nodes.get(iFindNamePoint);
            this.nodes.set(iFindNamePoint, node);
            return nodeImpl2;
        }
        int iFindNamePoint2 = findNamePoint(node.getNodeName(), 0);
        if (iFindNamePoint2 >= 0) {
            nodeImpl = (NodeImpl) this.nodes.get(iFindNamePoint2);
        } else {
            iFindNamePoint2 = (-1) - iFindNamePoint2;
            if (this.nodes == null) {
                this.nodes = new ArrayList(5);
            }
        }
        this.nodes.add(iFindNamePoint2, node);
        return nodeImpl;
    }

    protected void setOwnerDocument(CoreDocumentImpl coreDocumentImpl) {
        List list = this.nodes;
        if (list != null) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                ((NodeImpl) item(i)).setOwnerDocument(coreDocumentImpl);
            }
        }
    }

    void setReadOnly(boolean z, boolean z2) {
        List list;
        isReadOnly(z);
        if (!z2 || (list = this.nodes) == null) {
            return;
        }
        for (int size = list.size() - 1; size >= 0; size--) {
            ((NodeImpl) this.nodes.get(size)).setReadOnly(z, z2);
        }
    }
}
