package org.apache.xerces.dom;

import org.apache.xerces.xni.NamespaceContext;
import org.apache.xerces.xs.XSTypeDefinition;
import org.w3c.dom.NamedNodeMap;

/* JADX INFO: loaded from: classes13.dex */
public class DeferredElementNSImpl extends ElementNSImpl implements DeferredNode {
    static final long serialVersionUID = -5001885145370927385L;
    protected transient int fNodeIndex;

    DeferredElementNSImpl(DeferredDocumentImpl deferredDocumentImpl, int i) {
        super(deferredDocumentImpl, null);
        this.fNodeIndex = i;
        needsSyncChildren(true);
    }

    @Override // org.apache.xerces.dom.DeferredNode
    public final int getNodeIndex() {
        return this.fNodeIndex;
    }

    @Override // org.apache.xerces.dom.ParentNode
    protected final void synchronizeChildren() {
        ((DeferredDocumentImpl) ownerDocument()).synchronizeChildren(this, this.fNodeIndex);
    }

    @Override // org.apache.xerces.dom.ElementImpl, org.apache.xerces.dom.NodeImpl
    protected final void synchronizeData() {
        boolean z = false;
        needsSyncData(false);
        DeferredDocumentImpl deferredDocumentImpl = (DeferredDocumentImpl) this.ownerDocument;
        boolean z2 = deferredDocumentImpl.mutationEvents;
        deferredDocumentImpl.mutationEvents = false;
        this.f468name = deferredDocumentImpl.getNodeName(this.fNodeIndex);
        int iIndexOf = this.f468name.indexOf(58);
        this.localName = iIndexOf < 0 ? this.f468name : this.f468name.substring(iIndexOf + 1);
        this.namespaceURI = deferredDocumentImpl.getNodeURI(this.fNodeIndex);
        this.type = (XSTypeDefinition) deferredDocumentImpl.getTypeInfo(this.fNodeIndex);
        setupDefaultAttributes();
        int nodeExtra = deferredDocumentImpl.getNodeExtra(this.fNodeIndex);
        if (nodeExtra != -1) {
            NamedNodeMap attributes = getAttributes();
            do {
                AttrImpl attrImpl = (AttrImpl) deferredDocumentImpl.getNodeObject(nodeExtra);
                if (attrImpl.getSpecified() || (!z && (attrImpl.getNamespaceURI() == null || attrImpl.getNamespaceURI() == NamespaceContext.XMLNS_URI || attrImpl.getName().indexOf(58) >= 0))) {
                    attributes.setNamedItem(attrImpl);
                } else {
                    attributes.setNamedItemNS(attrImpl);
                    z = true;
                }
                nodeExtra = deferredDocumentImpl.getPrevSibling(nodeExtra);
            } while (nodeExtra != -1);
        }
        deferredDocumentImpl.mutationEvents = z2;
    }
}
