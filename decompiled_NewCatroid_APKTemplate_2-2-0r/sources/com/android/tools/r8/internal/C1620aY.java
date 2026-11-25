package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.utils.InterfaceC3890e3;

/* JADX INFO: renamed from: com.android.tools.r8.internal.aY, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1620aY extends IY {
    static final /* synthetic */ boolean m = true;
    private final C0759u2[] l;

    public C1620aY(S10 s10, C0759u2[] c0759u2Arr, int[] iArr, int i) {
        super(s10, iArr, i);
        this.l = c0759u2Arr;
        if (m) {
            return;
        }
        N2();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int E2() {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int F2() {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int G2() {
        return 62;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final C1620aY I0() {
        return this;
    }

    @Override // com.android.tools.r8.internal.IY
    public final void N2() {
        boolean z = m;
        if (!z) {
            super.N2();
        }
        if (!z && this.l.length < 1) {
            throw new AssertionError();
        }
        if (!z && this.l.length > 65535) {
            throw new AssertionError();
        }
        if (!z && this.l.length != L2()) {
            throw new AssertionError();
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final Object a(AbstractC1855de abstractC1855de) {
        return null;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean b(AbstractC2412ko abstractC2412ko) {
        throw new C3405y10();
    }

    public final C0759u2 f(int i) {
        return this.l[i];
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final String toString() {
        StringBuilder sbAppend = new StringBuilder(super.toString()).append(System.lineSeparator());
        for (int i = 0; i < L2(); i++) {
            sbAppend.append("          \"").append(this.l[i]).append("\" -> ").append(e(i).w()).append(System.lineSeparator());
        }
        return sbAppend.append("          F -> ").append(J2().v()).toString();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean y2() {
        return true;
    }

    public final void a(InterfaceC3890e3 interfaceC3890e3) throws Throwable {
        int i = 0;
        while (true) {
            C0759u2[] c0759u2Arr = this.l;
            if (i >= c0759u2Arr.length) {
                return;
            }
            interfaceC3890e3.accept(c0759u2Arr[i], e(i));
            i++;
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(L6 l6) {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(C2322jf c2322jf) {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(InterfaceC2641no interfaceC2641no, C3340xC c3340xC) {
        throw new C3405y10();
    }
}
