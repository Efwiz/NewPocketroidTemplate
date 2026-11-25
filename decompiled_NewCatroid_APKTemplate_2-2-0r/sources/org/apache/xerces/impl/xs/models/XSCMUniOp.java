package org.apache.xerces.impl.xs.models;

import org.apache.xerces.impl.dtd.models.CMNode;
import org.apache.xerces.impl.dtd.models.CMStateSet;

/* JADX INFO: loaded from: classes13.dex */
public class XSCMUniOp extends CMNode {
    private CMNode fChild;

    public XSCMUniOp(int i, CMNode cMNode) {
        super(i);
        if (type() != 5 && type() != 4 && type() != 6) {
            throw new RuntimeException("ImplementationMessages.VAL_UST");
        }
        this.fChild = cMNode;
    }

    @Override // org.apache.xerces.impl.dtd.models.CMNode
    protected void calcFirstPos(CMStateSet cMStateSet) {
        cMStateSet.setTo(this.fChild.firstPos());
    }

    @Override // org.apache.xerces.impl.dtd.models.CMNode
    protected void calcLastPos(CMStateSet cMStateSet) {
        cMStateSet.setTo(this.fChild.lastPos());
    }

    final CMNode getChild() {
        return this.fChild;
    }

    @Override // org.apache.xerces.impl.dtd.models.CMNode
    public boolean isNullable() {
        if (type() == 6) {
            return this.fChild.isNullable();
        }
        return true;
    }
}
