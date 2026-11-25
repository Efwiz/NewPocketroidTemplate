package org.apache.xerces.dom;

/* JADX INFO: loaded from: classes13.dex */
public class DeferredDocumentTypeImpl extends DocumentTypeImpl implements DeferredNode {
    static final long serialVersionUID = -2172579663227313509L;
    protected transient int fNodeIndex;

    DeferredDocumentTypeImpl(DeferredDocumentImpl deferredDocumentImpl, int i) {
        super(deferredDocumentImpl, null);
        this.fNodeIndex = i;
        needsSyncData(true);
        needsSyncChildren(true);
    }

    @Override // org.apache.xerces.dom.DeferredNode
    public int getNodeIndex() {
        return this.fNodeIndex;
    }

    /* JADX WARN: Code duplicated, block: B:21:0x0068  */
    @Override // org.apache.xerces.dom.ParentNode
    protected void synchronizeChildren() {
        NamedNodeMapImpl namedNodeMapImpl;
        boolean mutationEvents = ownerDocument().getMutationEvents();
        ownerDocument().setMutationEvents(false);
        needsSyncChildren(false);
        DeferredDocumentImpl deferredDocumentImpl = (DeferredDocumentImpl) this.ownerDocument;
        this.entities = new NamedNodeMapImpl(this);
        this.notations = new NamedNodeMapImpl(this);
        this.elements = new NamedNodeMapImpl(this);
        DeferredNode deferredNode = null;
        for (int lastChild = deferredDocumentImpl.getLastChild(this.fNodeIndex); lastChild != -1; lastChild = deferredDocumentImpl.getPrevSibling(lastChild)) {
            DeferredNode nodeObject = deferredDocumentImpl.getNodeObject(lastChild);
            short nodeType = nodeObject.getNodeType();
            if (nodeType != 1) {
                if (nodeType == 6) {
                    namedNodeMapImpl = this.entities;
                } else if (nodeType == 12) {
                    namedNodeMapImpl = this.notations;
                } else if (nodeType != 21) {
                    System.out.println("DeferredDocumentTypeImpl#synchronizeInfo: node.getNodeType() = " + ((int) nodeObject.getNodeType()) + ", class = " + nodeObject.getClass().getName());
                } else {
                    namedNodeMapImpl = this.elements;
                }
                namedNodeMapImpl.setNamedItem(nodeObject);
            } else if (((DocumentImpl) getOwnerDocument()).allowGrammarAccess) {
                insertBefore(nodeObject, deferredNode);
                deferredNode = nodeObject;
            } else {
                System.out.println("DeferredDocumentTypeImpl#synchronizeInfo: node.getNodeType() = " + ((int) nodeObject.getNodeType()) + ", class = " + nodeObject.getClass().getName());
            }
        }
        ownerDocument().setMutationEvents(mutationEvents);
        setReadOnly(true, false);
    }

    @Override // org.apache.xerces.dom.NodeImpl
    protected void synchronizeData() {
        needsSyncData(false);
        DeferredDocumentImpl deferredDocumentImpl = (DeferredDocumentImpl) this.ownerDocument;
        this.f466name = deferredDocumentImpl.getNodeName(this.fNodeIndex);
        this.publicID = deferredDocumentImpl.getNodeValue(this.fNodeIndex);
        this.systemID = deferredDocumentImpl.getNodeURI(this.fNodeIndex);
        this.internalSubset = deferredDocumentImpl.getNodeValue(deferredDocumentImpl.getNodeExtra(this.fNodeIndex));
    }
}
