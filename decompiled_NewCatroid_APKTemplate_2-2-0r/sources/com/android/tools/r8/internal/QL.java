package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class QL extends TB {
    static final /* synthetic */ boolean d = true;
    private final AbstractC1748cC a;
    private final MR b;
    private final MR c;

    private QL() {
        this.a = AbstractC1748cC.a();
        this.b = new MR();
        this.c = new MR();
    }

    @Override // com.android.tools.r8.internal.TB
    public final Object a(Object obj, int i) {
        this.b.a(i, (S10) obj);
        return Integer.valueOf(i);
    }

    @Override // com.android.tools.r8.internal.TB
    public final boolean b() {
        return true;
    }

    @Override // com.android.tools.r8.internal.TB
    public final boolean b(Object obj, int i) {
        S10 s10 = (S10) obj;
        if (d || i == this.b.a(s10)) {
            return true;
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.TB
    public final Object a(Object obj) {
        return Integer.valueOf(this.b.a((S10) obj));
    }

    @Override // com.android.tools.r8.internal.TB
    public final void a(C3332x4 c3332x4, int i) {
        if (!d && this.c.containsKey(c3332x4)) {
            throw new AssertionError();
        }
        this.c.a(i, c3332x4);
    }

    @Override // com.android.tools.r8.internal.TB
    public final int a(C3332x4 c3332x4) {
        if (d || this.c.containsKey(c3332x4)) {
            return this.c.a(c3332x4);
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.TB
    public final AbstractC2366kC a() {
        return new PL(this);
    }
}
