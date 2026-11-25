package org.apache.xerces.util;

import org.apache.xerces.xni.parser.XMLInputSource;
import org.w3c.dom.Node;

/* JADX INFO: loaded from: classes13.dex */
public final class DOMInputSource extends XMLInputSource {
    private Node fNode;

    public DOMInputSource() {
        this(null);
    }

    public DOMInputSource(Node node) {
        super(null, getSystemIdFromNode(node), null);
        this.fNode = node;
    }

    public DOMInputSource(Node node, String str) {
        super(null, str, null);
        this.fNode = node;
    }

    private static String getSystemIdFromNode(Node node) {
        if (node != null) {
            try {
                return node.getBaseURI();
            } catch (Exception | NoSuchMethodError unused) {
            }
        }
        return null;
    }

    public Node getNode() {
        return this.fNode;
    }

    public void setNode(Node node) {
        this.fNode = node;
    }
}
