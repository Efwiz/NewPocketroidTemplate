package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.lC, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2447lC {
    static final /* synthetic */ boolean c = true;
    private final InterfaceC1741c6 a;
    private int b = 0;

    public C2447lC(R5 r5) {
        this.a = r5;
    }

    public final void a(int i) {
        boolean z = c;
        if (!z) {
            if (!XB.a && i < 1) {
                throw new AssertionError();
            }
            if (i > 15 && i != 177 && i != 209 && i != 211) {
                throw new AssertionError();
            }
        }
        if (!z && this.b != 0) {
            throw new AssertionError();
        }
        InterfaceC1741c6 interfaceC1741c6 = this.a;
        if (!C1589a6.a && (i < 0 || i > 255)) {
            throw new AssertionError();
        }
        interfaceC1741c6.a(i & 255);
    }

    public final void b(int i) {
        if (!c && this.b <= 0) {
            throw new AssertionError();
        }
        this.b--;
        InterfaceC1741c6 interfaceC1741c6 = this.a;
        if (!C1589a6.a && (i < 0 || i > 255)) {
            throw new AssertionError();
        }
        interfaceC1741c6.a(i & 255);
    }

    public final void a(int i, int i2) {
        if (!c && this.b != 0) {
            throw new AssertionError();
        }
        InterfaceC1741c6 interfaceC1741c6 = this.a;
        boolean z = C1589a6.a;
        if (!z && (i < 0 || i > 255)) {
            throw new AssertionError();
        }
        interfaceC1741c6.a(i & 255);
        InterfaceC1741c6 interfaceC1741c7 = this.a;
        if (!z && (i2 < 0 || i2 > 255)) {
            throw new AssertionError();
        }
        interfaceC1741c7.a(i2 & 255);
        this.b = i2;
    }
}
