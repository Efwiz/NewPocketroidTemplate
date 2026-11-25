package org.apache.html.dom;

import org.apache.xerces.dom.DeepNodeListImpl;
import org.apache.xerces.dom.ElementImpl;
import org.apache.xerces.dom.NodeImpl;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/* JADX INFO: loaded from: classes13.dex */
public class NameNodeListImpl extends DeepNodeListImpl implements NodeList {
    public NameNodeListImpl(NodeImpl nodeImpl, String str) {
        super(nodeImpl, str);
    }

    @Override // org.apache.xerces.dom.DeepNodeListImpl
    protected Node nextMatchingElementAfter(Node node) {
        Node nextSibling;
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
                String attribute = ((ElementImpl) node).getAttribute("name");
                if (attribute.equals("*") || attribute.equals(this.tagName)) {
                    return node;
                }
            }
        }
    }
}
