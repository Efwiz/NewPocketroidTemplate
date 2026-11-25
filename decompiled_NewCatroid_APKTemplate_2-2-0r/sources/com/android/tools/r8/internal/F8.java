package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class F8 extends L8 {
    static final /* synthetic */ boolean d = true;
    private final int c;

    F8(L8 l8) {
        super(l8.b + 1, l8);
        int iD = l8.d() - 1;
        this.c = iD;
        if (!d && iD < 0) {
            throw new AssertionError();
        }
    }

    @Override // com.android.tools.r8.internal.L8
    public final H8 b(int i) {
        if (d || i < this.c) {
            return this.a.b(i);
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.L8
    public final H8 c() {
        return this.a.b(this.c - 1);
    }

    @Override // com.android.tools.r8.internal.L8
    public final int d() {
        return this.c;
    }

    public final String toString() {
        return this.a.toString() + "; pop";
    }
}
