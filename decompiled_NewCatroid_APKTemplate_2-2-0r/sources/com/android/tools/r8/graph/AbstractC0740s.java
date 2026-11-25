package com.android.tools.r8.graph;

/* JADX INFO: renamed from: com.android.tools.r8.graph.s, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC0740s extends AbstractC0594a1 {
    static final /* synthetic */ boolean b = true;
    private volatile int a = -1;

    private void j(int i) {
        int iS0 = s0();
        if (iS0 == -1) {
            iS0 = 0;
        }
        if (b || i == iS0) {
            return;
        }
        throw new AssertionError("Hash code for " + this + " has changed from " + this.a + " to " + iS0);
    }

    protected abstract boolean c(Object obj);

    public final boolean equals(Object obj) {
        return this == obj || c(obj);
    }

    public final int hashCode() {
        int iS0 = this.a;
        if (iS0 == -1) {
            iS0 = s0();
            if (iS0 == -1) {
                iS0 = 0;
            }
            this.a = iS0;
        }
        if (!b) {
            j(iS0);
        }
        return iS0;
    }

    protected abstract int s0();
}
