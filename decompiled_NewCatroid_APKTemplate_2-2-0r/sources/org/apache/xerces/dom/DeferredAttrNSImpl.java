package org.apache.xerces.dom;

/* JADX INFO: loaded from: classes13.dex */
public final class DeferredAttrNSImpl extends AttrNSImpl implements DeferredNode {
    static final long serialVersionUID = 6074924934945957154L;
    protected transient int fNodeIndex;

    DeferredAttrNSImpl(DeferredDocumentImpl deferredDocumentImpl, int i) {
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
        int iIndexOf = this.f465name.indexOf(58);
        this.localName = iIndexOf < 0 ? this.f465name : this.f465name.substring(iIndexOf + 1);
        int nodeExtra = deferredDocumentImpl.getNodeExtra(this.fNodeIndex);
        isSpecified((nodeExtra & 32) != 0);
        isIdAttribute((nodeExtra & 512) != 0);
        this.namespaceURI = deferredDocumentImpl.getNodeURI(this.fNodeIndex);
        this.type = deferredDocumentImpl.getTypeInfo(deferredDocumentImpl.getLastChild(this.fNodeIndex));
    }
}
