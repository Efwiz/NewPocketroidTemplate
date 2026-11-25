package org.apache.xerces.impl.xs.models;

import org.apache.xerces.impl.dtd.models.CMNode;
import org.apache.xerces.impl.dtd.models.CMStateSet;

/* JADX INFO: loaded from: classes13.dex */
public class XSCMLeaf extends CMNode {
    private final Object fLeaf;
    private int fParticleId;
    private int fPosition;

    public XSCMLeaf(int i, Object obj, int i2, int i3) {
        super(i);
        this.fLeaf = obj;
        this.fParticleId = i2;
        this.fPosition = i3;
    }

    @Override // org.apache.xerces.impl.dtd.models.CMNode
    protected void calcFirstPos(CMStateSet cMStateSet) {
        int i = this.fPosition;
        if (i == -1) {
            cMStateSet.zeroBits();
        } else {
            cMStateSet.setBit(i);
        }
    }

    @Override // org.apache.xerces.impl.dtd.models.CMNode
    protected void calcLastPos(CMStateSet cMStateSet) {
        int i = this.fPosition;
        if (i == -1) {
            cMStateSet.zeroBits();
        } else {
            cMStateSet.setBit(i);
        }
    }

    final Object getLeaf() {
        return this.fLeaf;
    }

    final int getParticleId() {
        return this.fParticleId;
    }

    final int getPosition() {
        return this.fPosition;
    }

    @Override // org.apache.xerces.impl.dtd.models.CMNode
    public boolean isNullable() {
        return this.fPosition == -1;
    }

    final void setPosition(int i) {
        this.fPosition = i;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer(this.fLeaf.toString());
        if (this.fPosition >= 0) {
            stringBuffer.append(" (Pos:").append(Integer.toString(this.fPosition)).append(')');
        }
        return stringBuffer.toString();
    }
}
