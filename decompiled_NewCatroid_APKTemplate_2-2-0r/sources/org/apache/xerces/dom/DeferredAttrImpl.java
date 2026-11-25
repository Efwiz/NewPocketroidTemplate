package org.apache.xerces.dom;

/* JADX INFO: loaded from: classes13.dex */
public final class DeferredAttrImpl extends AttrImpl implements DeferredNode {
    static final long serialVersionUID = 6903232312469148636L;
    protected transient int fNodeIndex;

    DeferredAttrImpl(DeferredDocumentImpl deferredDocumentImpl, int i) {
        super(deferredDocumentImpl, null);
        this.fNodeIndex = i;
        needsSyncData(true);
        needsSyncChildren(true);
    }

    @Override // org.apache.xerces.dom.DeferredNode
    public int getNodeIndex() {
        return this.fNodeIndex;
    }

    @Override // org.apache.xerces.dom.AttrImpl
    protected void synchronizeChildren() {
        ((DeferredDocumentImpl) ownerDocument()).synchronizeChildren(this, this.fNodeIndex);
    }

    @Override // org.apache.xerces.dom.NodeImpl
    protected void synchronizeData() {
        needsSyncData(false);
        DeferredDocumentImpl deferredDocumentImpl = (DeferredDocumentImpl) ownerDocument();
        this.f465name = deferredDocumentImpl.getNodeName(this.fNodeIndex);
        int nodeExtra = deferredDocumentImpl.getNodeExtra(this.fNodeIndex);
        isSpecified((nodeExtra & 32) != 0);
        isIdAttribute((nodeExtra & 512) != 0);
        this.type = deferredDocumentImpl.getTypeInfo(deferredDocumentImpl.getLastChild(this.fNodeIndex));
    }
}
