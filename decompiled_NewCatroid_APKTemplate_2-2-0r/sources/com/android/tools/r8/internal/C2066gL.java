package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;

/* JADX INFO: renamed from: com.android.tools.r8.internal.gL, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2066gL implements KV {
    static final /* synthetic */ boolean f = true;
    private final ZK a;
    private final C0684j2 b;
    private int c;
    private int d = 0;
    final /* synthetic */ C2225iL e;

    C2066gL(C2225iL c2225iL, ZK zk, C0684j2 c0684j2) {
        this.e = c2225iL;
        this.a = zk;
        this.b = c0684j2;
    }

    @Override // com.android.tools.r8.internal.KV
    public final com.android.tools.r8.graph.X a(int i) {
        return null;
    }

    @Override // com.android.tools.r8.internal.KV
    public final com.android.tools.r8.graph.X a(int i, int i2) {
        return null;
    }

    @Override // com.android.tools.r8.internal.KV
    public final void a(C2102gl c2102gl) {
    }

    @Override // com.android.tools.r8.internal.KV
    public final C3038t6 b(C2102gl c2102gl) {
        return null;
    }

    @Override // com.android.tools.r8.internal.KV
    public final void b() {
    }

    @Override // com.android.tools.r8.internal.KV
    public final com.android.tools.r8.graph.X c(int i) {
        return null;
    }

    @Override // com.android.tools.r8.internal.KV
    public final boolean c() {
        return true;
    }

    @Override // com.android.tools.r8.internal.KV
    public final void clear() {
    }

    @Override // com.android.tools.r8.internal.KV
    public final boolean d() {
        return true;
    }

    @Override // com.android.tools.r8.internal.KV
    public final int e(int i) {
        return i;
    }

    @Override // com.android.tools.r8.internal.KV
    public final boolean f(int i) {
        return true;
    }

    @Override // com.android.tools.r8.internal.KV
    public final int g(int i) {
        return i;
    }

    @Override // com.android.tools.r8.internal.KV
    public final int d(int i) {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.internal.KV
    public final int e() {
        return this.a.c.size() + 1;
    }

    @Override // com.android.tools.r8.internal.KV
    public final AbstractC1915eM f() {
        return ((C1758cM) ((C1758cM) C1835dM.x().a(this.c)).a(this.b)).a();
    }

    @Override // com.android.tools.r8.internal.KV
    public final AbstractC1915eM b(int i) {
        return AbstractC1915eM.v();
    }

    @Override // com.android.tools.r8.internal.KV
    public final void c(C2102gl c2102gl) {
        if (!f && !c2102gl.j().g()) {
            throw new AssertionError();
        }
        for (int i = 0; i < this.a.a.size(); i++) {
            if (((C0767v2) this.a.a.get(i)).N0()) {
                c2102gl.a(i);
            } else {
                c2102gl.a(i, AbstractC3472z00.a((C0767v2) this.a.a.get(i), WH.i(), (C0702m<?>) this.e.d));
            }
        }
    }

    @Override // com.android.tools.r8.internal.KV
    public final int a(int i, C2102gl c2102gl) {
        if (i == this.a.c.size()) {
            return i;
        }
        return -1;
    }

    @Override // com.android.tools.r8.internal.KV
    public final void a(C2102gl c2102gl, int i, int i2, boolean z) {
        throw new C3405y10("Outliner does not support control flow");
    }

    @Override // com.android.tools.r8.internal.KV
    public final void a(C2102gl c2102gl, int i, boolean z) {
        if (i == this.a.c.size()) {
            ZK zk = this.a;
            if (zk.d == this.e.e.D1) {
                c2102gl.b();
                return;
            } else {
                c2102gl.e(zk.a.size());
                return;
            }
        }
        this.c = i;
        this.d = ((AbstractC1757cL) this.a.c.get(i)).a(c2102gl, this.a, this.d);
    }

    @Override // com.android.tools.r8.internal.KV
    public final void a(int i, int i2, int i3, C2102gl c2102gl) {
        throw new C3405y10("Unexpected call to resolveAndBuildSwitch");
    }

    @Override // com.android.tools.r8.internal.KV
    public final void a(int i, int i2, C2102gl c2102gl) {
        throw new C3405y10("Unexpected call to resolveAndBuildNewArrayFilledData");
    }
}
