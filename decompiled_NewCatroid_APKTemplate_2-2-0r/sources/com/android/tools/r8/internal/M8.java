package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class M8 extends L8 {
    static final /* synthetic */ boolean d = true;
    private final H8 c;

    M8(L8 l8, int i, K8 k8) {
        super(l8.b + 1, l8);
        this.c = new H8(i, k8);
        if (d) {
            return;
        }
        if (i < 0 || i >= 100000) {
            throw new AssertionError();
        }
    }

    @Override // com.android.tools.r8.internal.L8
    public final H8 a(int i) {
        H8 h8 = this.c;
        return i == h8.a ? h8 : this.a.a(i);
    }

    @Override // com.android.tools.r8.internal.L8
    public final int b() {
        return Math.max(this.c.a, this.a.b());
    }

    public final String toString() {
        String string = this.a.toString();
        H8 h8 = this.c;
        return string + "; write " + h8.a + " := " + h8.d;
    }

    @Override // com.android.tools.r8.internal.L8
    final void a(E8 e8) {
        this.a.a(e8);
        K8[] k8Arr = e8.c;
        H8 h8 = this.c;
        k8Arr[h8.a] = h8.d;
    }
}
