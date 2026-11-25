package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.at, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1646at extends AbstractC1278Ps {
    static final /* synthetic */ boolean f = true;

    @Override // com.android.tools.r8.internal.Cdo
    public final Cdo a() {
        return this;
    }

    public final C1721bt b() {
        boolean z = f;
        if (!z && this.d == null) {
            throw new AssertionError();
        }
        if (!z && this.c == null) {
            throw new AssertionError();
        }
        if (!z && this.c.F0() != this.d.size()) {
            throw new AssertionError();
        }
        if (z || this.a == null || !this.c.I0().Y0()) {
            return (C1721bt) a(new C1721bt(this.c, this.a, this.d));
        }
        throw new AssertionError();
    }

    public final AbstractC1278Ps a(List list) {
        if (!AbstractC1278Ps.e && list == null) {
            throw new AssertionError();
        }
        this.d = list;
        return (AbstractC1278Ps) a();
    }

    public final AbstractC1278Ps a(C0702m c0702m, T10 t10) {
        return (AbstractC1278Ps) a(t10, AbstractC3472z00.a(this.c.I0(), WH.i(), (C0702m<?>) c0702m), null);
    }

    public final AbstractC1278Ps a(C0684j2 c0684j2) {
        this.c = c0684j2;
        return (AbstractC1278Ps) a();
    }
}
