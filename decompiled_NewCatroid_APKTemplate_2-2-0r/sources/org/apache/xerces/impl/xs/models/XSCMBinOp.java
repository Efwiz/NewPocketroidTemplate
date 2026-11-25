package org.apache.xerces.impl.xs.models;

import org.apache.xerces.impl.dtd.models.CMNode;
import org.apache.xerces.impl.dtd.models.CMStateSet;

/* JADX INFO: loaded from: classes13.dex */
public class XSCMBinOp extends CMNode {
    private CMNode fLeftChild;
    private CMNode fRightChild;

    public XSCMBinOp(int i, CMNode cMNode, CMNode cMNode2) {
        super(i);
        if (type() != 101 && type() != 102) {
            throw new RuntimeException("ImplementationMessages.VAL_BST");
        }
        this.fLeftChild = cMNode;
        this.fRightChild = cMNode2;
    }

    @Override // org.apache.xerces.impl.dtd.models.CMNode
    protected void calcFirstPos(CMStateSet cMStateSet) {
        if (type() == 101) {
            cMStateSet.setTo(this.fLeftChild.firstPos());
        } else {
            if (type() != 102) {
                throw new RuntimeException("ImplementationMessages.VAL_BST");
            }
            cMStateSet.setTo(this.fLeftChild.firstPos());
            if (!this.fLeftChild.isNullable()) {
                return;
            }
        }
        cMStateSet.union(this.fRightChild.firstPos());
    }

    @Override // org.apache.xerces.impl.dtd.models.CMNode
    protected void calcLastPos(CMStateSet cMStateSet) {
        CMNode cMNode;
        if (type() == 101) {
            cMStateSet.setTo(this.fLeftChild.lastPos());
            cMNode = this.fRightChild;
        } else {
            if (type() != 102) {
                throw new RuntimeException("ImplementationMessages.VAL_BST");
            }
            cMStateSet.setTo(this.fRightChild.lastPos());
            if (!this.fRightChild.isNullable()) {
                return;
            } else {
                cMNode = this.fLeftChild;
            }
        }
        cMStateSet.union(cMNode.lastPos());
    }

    final CMNode getLeft() {
        return this.fLeftChild;
    }

    final CMNode getRight() {
        return this.fRightChild;
    }

    @Override // org.apache.xerces.impl.dtd.models.CMNode
    public boolean isNullable() {
        if (type() == 101) {
            return this.fLeftChild.isNullable() || this.fRightChild.isNullable();
        }
        if (type() == 102) {
            return this.fLeftChild.isNullable() && this.fRightChild.isNullable();
        }
        throw new RuntimeException("ImplementationMessages.VAL_BST");
    }
}
