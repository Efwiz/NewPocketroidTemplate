package com.android.tools.r8.shaking;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.d1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3704d1 extends AbstractC3740j1 {
    private static final C3704d1 i;
    private static final C3704d1 j;
    static final /* synthetic */ boolean k = true;
    private final boolean h;

    private C3704d1(C3691b1 c3691b1) {
        super(c3691b1);
        this.h = c3691b1.o();
    }

    public static C3704d1 i() {
        return j;
    }

    public static C3698c1 p() {
        return j.o();
    }

    public static C3704d1 q() {
        return i;
    }

    final C3691b1 j() {
        return new C3691b1(this);
    }

    final boolean k() {
        return this.h;
    }

    public final boolean l() {
        return equals(j);
    }

    public final boolean m() {
        return this.h;
    }

    public final boolean n() {
        return equals(i);
    }

    public final C3698c1 o() {
        if (k || !equals(i)) {
            return new C3698c1(this);
        }
        throw new AssertionError();
    }

    static {
        C3692b1IA c3692b1IA = null;
        i = (C3704d1) new C3691b1().m().a();
        C3691b1 c3691b1 = (C3691b1) new C3691b1().l();
        c3691b1.i = true;
        j = (C3704d1) c3691b1.a();
    }
}
