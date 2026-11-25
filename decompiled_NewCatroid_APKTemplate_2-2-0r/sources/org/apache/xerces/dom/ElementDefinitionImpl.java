package org.apache.xerces.dom;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/* JADX INFO: loaded from: classes13.dex */
public class ElementDefinitionImpl extends ParentNode {
    static final long serialVersionUID = -8373890672670022714L;
    protected NamedNodeMapImpl attributes;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    protected String f467name;

    public ElementDefinitionImpl(CoreDocumentImpl coreDocumentImpl, String str) {
        super(coreDocumentImpl);
        this.f467name = str;
        this.attributes = new NamedNodeMapImpl(coreDocumentImpl);
    }

    @Override // org.apache.xerces.dom.ParentNode, org.apache.xerces.dom.ChildNode, org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public Node cloneNode(boolean z) {
        ElementDefinitionImpl elementDefinitionImpl = (ElementDefinitionImpl) super.cloneNode(z);
        elementDefinitionImpl.attributes = this.attributes.cloneMap(elementDefinitionImpl);
        return elementDefinitionImpl;
    }

    @Override // org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public NamedNodeMap getAttributes() {
        if (needsSyncChildren()) {
            synchronizeChildren();
        }
        return this.attributes;
    }

    @Override // org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public String getNodeName() {
        if (needsSyncData()) {
            synchronizeData();
        }
        return this.f467name;
    }

    @Override // org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public short getNodeType() {
        return (short) 21;
    }
}
