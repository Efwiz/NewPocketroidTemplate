package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class IY extends AbstractC1954eu {
    static final /* synthetic */ boolean k = true;
    private final int[] i;
    private int j;

    public IY(S10 s10, int[] iArr, int i) {
        super(s10);
        this.i = iArr;
        this.j = i;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean A2() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC1954eu
    public final C3332x4 J2() {
        return h().z().get(this.j);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final IY K0() {
        return this;
    }

    public final int K2() {
        return this.j;
    }

    public int L2() {
        return this.i.length;
    }

    public final int[] M2() {
        return this.i;
    }

    public void N2() {
        for (int i = 0; i < L2(); i++) {
            if (!k && this.i[i] == this.j) {
                throw new AssertionError();
            }
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC1954eu
    public final void b(C3332x4 c3332x4) {
        h().s().set(this.j, c3332x4);
    }

    public final int c(int i) {
        return this.i[i];
    }

    public final void d(int i) {
        this.j = i;
    }

    public final C3332x4 e(int i) {
        return h().z().get(this.i[i]);
    }

    public final S10 value() {
        return (S10) this.b.get(0);
    }
}
