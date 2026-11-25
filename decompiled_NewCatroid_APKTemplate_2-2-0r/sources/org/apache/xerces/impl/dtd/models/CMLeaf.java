package org.apache.xerces.impl.dtd.models;

import org.apache.xerces.xni.QName;

/* JADX INFO: loaded from: classes13.dex */
public class CMLeaf extends CMNode {
    private final QName fElement;
    private int fPosition;

    public CMLeaf(QName qName) {
        super(0);
        QName qName2 = new QName();
        this.fElement = qName2;
        this.fPosition = -1;
        qName2.setValues(qName);
    }

    public CMLeaf(QName qName, int i) {
        super(0);
        QName qName2 = new QName();
        this.fElement = qName2;
        this.fPosition = -1;
        qName2.setValues(qName);
        this.fPosition = i;
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

    final QName getElement() {
        return this.fElement;
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
        StringBuffer stringBuffer = new StringBuffer(this.fElement.toString());
        stringBuffer.append(" (");
        stringBuffer.append(this.fElement.uri);
        stringBuffer.append(',');
        stringBuffer.append(this.fElement.localpart);
        stringBuffer.append(')');
        if (this.fPosition >= 0) {
            stringBuffer.append(" (Pos:").append(Integer.toString(this.fPosition)).append(')');
        }
        return stringBuffer.toString();
    }
}
