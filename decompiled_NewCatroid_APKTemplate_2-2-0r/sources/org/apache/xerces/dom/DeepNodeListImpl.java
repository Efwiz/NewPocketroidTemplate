package org.apache.xerces.dom;

import java.util.ArrayList;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/* JADX INFO: loaded from: classes13.dex */
public class DeepNodeListImpl implements NodeList {
    protected int changes;
    protected boolean enableNS;
    protected ArrayList nodes;
    protected String nsName;
    protected NodeImpl rootNode;
    protected String tagName;

    public DeepNodeListImpl(NodeImpl nodeImpl, String str) {
        this.changes = 0;
        this.enableNS = false;
        this.rootNode = nodeImpl;
        this.tagName = str;
        this.nodes = new ArrayList();
    }

    public DeepNodeListImpl(NodeImpl nodeImpl, String str, String str2) {
        this(nodeImpl, str2);
        this.nsName = (str == null || str.length() == 0) ? null : str;
        this.enableNS = true;
    }

    @Override // org.w3c.dom.NodeList, org.w3c.dom.html.HTMLFormElement
    public int getLength() {
        item(Integer.MAX_VALUE);
        return this.nodes.size();
    }

    @Override // org.w3c.dom.NodeList
    public Node item(int i) {
        Node nodeNextMatchingElementAfter;
        if (this.rootNode.changes() != this.changes) {
            this.nodes = new ArrayList();
            this.changes = this.rootNode.changes();
        }
        int size = this.nodes.size();
        if (i < size) {
            return (Node) this.nodes.get(i);
        }
        if (size == 0) {
            nodeNextMatchingElementAfter = this.rootNode;
        } else {
            nodeNextMatchingElementAfter = (NodeImpl) this.nodes.get(size - 1);
        }
        while (nodeNextMatchingElementAfter != null && i >= this.nodes.size()) {
            nodeNextMatchingElementAfter = nextMatchingElementAfter(nodeNextMatchingElementAfter);
            if (nodeNextMatchingElementAfter != null) {
                this.nodes.add(nodeNextMatchingElementAfter);
            }
        }
        return nodeNextMatchingElementAfter;
    }

    protected Node nextMatchingElementAfter(Node node) {
        Node nextSibling;
        String str;
        String str2;
        while (true) {
            Node nextSibling2 = null;
            if (node == null) {
                return null;
            }
            if (node.hasChildNodes()) {
                node = node.getFirstChild();
            } else if (node == this.rootNode || (nextSibling = node.getNextSibling()) == null) {
                while (node != this.rootNode && (nextSibling2 = node.getNextSibling()) == null) {
                    node = node.getParentNode();
                }
                node = nextSibling2;
            } else {
                node = nextSibling;
            }
            if (node != this.rootNode && node != null && node.getNodeType() == 1) {
                if (this.enableNS) {
                    if (this.tagName.equals("*")) {
                        String str3 = this.nsName;
                        if (str3 != null && str3.equals("*")) {
                            return node;
                        }
                        ElementImpl elementImpl = (ElementImpl) node;
                        if ((this.nsName == null && elementImpl.getNamespaceURI() == null) || ((str = this.nsName) != null && str.equals(elementImpl.getNamespaceURI()))) {
                            return node;
                        }
                    } else {
                        ElementImpl elementImpl2 = (ElementImpl) node;
                        if (elementImpl2.getLocalName() != null && elementImpl2.getLocalName().equals(this.tagName)) {
                            String str4 = this.nsName;
                            if (str4 != null && str4.equals("*")) {
                                return node;
                            }
                            if ((this.nsName == null && elementImpl2.getNamespaceURI() == null) || ((str2 = this.nsName) != null && str2.equals(elementImpl2.getNamespaceURI()))) {
                                return node;
                            }
                        }
                    }
                } else if (this.tagName.equals("*") || ((ElementImpl) node).getTagName().equals(this.tagName)) {
                    return node;
                }
            }
        }
    }
}
