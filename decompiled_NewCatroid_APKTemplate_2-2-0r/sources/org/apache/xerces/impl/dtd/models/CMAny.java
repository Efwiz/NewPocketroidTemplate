package org.apache.xerces.impl.dtd.models;

/* JADX INFO: loaded from: classes13.dex */
public class CMAny extends CMNode {
    private int fPosition;
    private final int fType;
    private final String fURI;

    public CMAny(int i, String str, int i2) {
        super(i);
        this.fType = i;
        this.fURI = str;
        this.fPosition = i2;
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

    final int getPosition() {
        return this.fPosition;
    }

    final int getType() {
        return this.fType;
    }

    final String getURI() {
        return this.fURI;
    }

    @Override // org.apache.xerces.impl.dtd.models.CMNode
    public boolean isNullable() {
        return this.fPosition == -1;
    }

    final void setPosition(int i) {
        this.fPosition = i;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("(##any:uri=");
        stringBuffer.append(this.fURI);
        stringBuffer.append(')');
        if (this.fPosition >= 0) {
            stringBuffer.append(" (Pos:").append(Integer.toString(this.fPosition)).append(')');
        }
        return stringBuffer.toString();
    }
}
