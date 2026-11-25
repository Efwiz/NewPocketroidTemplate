package org.apache.xerces.impl.dtd.models;

/* JADX INFO: loaded from: classes13.dex */
public abstract class CMNode {
    private final int fType;
    private CMStateSet fFirstPos = null;
    private CMStateSet fFollowPos = null;
    private CMStateSet fLastPos = null;
    private int fMaxStates = -1;
    private boolean fCompactedForUPA = false;

    public CMNode(int i) {
        this.fType = i;
    }

    protected abstract void calcFirstPos(CMStateSet cMStateSet);

    protected abstract void calcLastPos(CMStateSet cMStateSet);

    public final CMStateSet firstPos() {
        if (this.fFirstPos == null) {
            CMStateSet cMStateSet = new CMStateSet(this.fMaxStates);
            this.fFirstPos = cMStateSet;
            calcFirstPos(cMStateSet);
        }
        return this.fFirstPos;
    }

    public boolean isCompactedForUPA() {
        return this.fCompactedForUPA;
    }

    public abstract boolean isNullable();

    public final CMStateSet lastPos() {
        if (this.fLastPos == null) {
            CMStateSet cMStateSet = new CMStateSet(this.fMaxStates);
            this.fLastPos = cMStateSet;
            calcLastPos(cMStateSet);
        }
        return this.fLastPos;
    }

    final void setFollowPos(CMStateSet cMStateSet) {
        this.fFollowPos = cMStateSet;
    }

    public void setIsCompactUPAModel(boolean z) {
        this.fCompactedForUPA = z;
    }

    public final void setMaxStates(int i) {
        this.fMaxStates = i;
    }

    public final int type() {
        return this.fType;
    }
}
