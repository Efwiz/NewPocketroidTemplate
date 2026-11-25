package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ks, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1141Ks extends AbstractC1278Ps {
    @Override // com.android.tools.r8.internal.Cdo
    public final Cdo a() {
        return this;
    }

    public final C1166Ls b() {
        return (C1166Ls) a(new C1166Ls(this.c, this.a, this.d, false));
    }

    public final AbstractC1278Ps a(List list) {
        if (!AbstractC1278Ps.e && list == null) {
            throw new AssertionError();
        }
        this.d = list;
        return (AbstractC1278Ps) a();
    }

    public final AbstractC1278Ps a(C0684j2 c0684j2) {
        this.c = c0684j2;
        return (AbstractC1278Ps) a();
    }
}
