package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.fI, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1991fI {
    static final /* synthetic */ boolean b = true;
    private C2879qr a;

    C1991fI() {
        this.a = new C2879qr();
    }

    final C1991fI a(int i) {
        C2879qr c2879qr = this.a;
        if (c2879qr != null) {
            if (!b && c2879qr.size() > 30) {
                throw new AssertionError();
            }
            if (this.a.a(i) && this.a.size() > 30) {
                this.a = null;
            }
        }
        return this;
    }

    C1991fI(C2063gI c2063gI) {
        this.a = new C2879qr(c2063gI.c);
    }

    final void a(C2063gI c2063gI) {
        C2879qr c2879qr = this.a;
        if (c2879qr != null) {
            if (!b && c2879qr.size() > 30) {
                throw new AssertionError();
            }
            if (!this.a.a(c2063gI.c) || this.a.size() <= 30) {
                return;
            }
            this.a = null;
        }
    }

    final B1 a(C1 c1) {
        C2879qr c2879qr = this.a;
        if (c2879qr != null) {
            boolean z = b;
            if (!z && c2879qr.isEmpty()) {
                throw new AssertionError();
            }
            if (!z && this.a.size() > 30) {
                throw new AssertionError();
            }
            if (this.a.size() == 1) {
                return c1.a(this.a.iterator().t());
            }
            return new C2063gI(this.a);
        }
        return C2892r10.F();
    }
}
