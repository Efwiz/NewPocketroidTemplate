package org.apache.xerces.impl.xs.opti;

/* JADX INFO: loaded from: classes13.dex */
public class NodeImpl extends DefaultNode {
    boolean hidden;
    String localpart;
    short nodeType;
    String prefix;
    String rawname;
    String uri;

    public NodeImpl() {
    }

    public NodeImpl(String str, String str2, String str3, String str4, short s) {
        this.prefix = str;
        this.localpart = str2;
        this.rawname = str3;
        this.uri = str4;
        this.nodeType = s;
    }

    @Override // org.apache.xerces.impl.xs.opti.DefaultNode, org.w3c.dom.Node
    public String getLocalName() {
        return this.localpart;
    }

    @Override // org.apache.xerces.impl.xs.opti.DefaultNode, org.w3c.dom.Node
    public String getNamespaceURI() {
        return this.uri;
    }

    @Override // org.apache.xerces.impl.xs.opti.DefaultNode, org.w3c.dom.Node
    public String getNodeName() {
        return this.rawname;
    }

    @Override // org.apache.xerces.impl.xs.opti.DefaultNode, org.w3c.dom.Node
    public short getNodeType() {
        return this.nodeType;
    }

    @Override // org.apache.xerces.impl.xs.opti.DefaultNode, org.w3c.dom.Node
    public String getPrefix() {
        return this.prefix;
    }

    public boolean getReadOnly() {
        return this.hidden;
    }

    public void setReadOnly(boolean z, boolean z2) {
        this.hidden = z;
    }

    public String toString() {
        return "[" + getNodeName() + ": " + getNodeValue() + "]";
    }
}
