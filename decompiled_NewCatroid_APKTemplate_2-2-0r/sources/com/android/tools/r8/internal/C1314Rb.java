package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0729q2;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Rb, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1314Rb extends AbstractC1176Mb {
    private final AbstractC1287Qb a;
    private final C0729q2 b;
    private final int c;

    private C1314Rb(AbstractC1287Qb abstractC1287Qb, C0729q2 c0729q2, int i) {
        super();
        this.a = abstractC1287Qb;
        this.b = c0729q2;
        this.c = i;
    }

    @Override // com.android.tools.r8.internal.AbstractC1176Mb
    final StringBuilder a(StringBuilder sb) {
        return this.a.a(sb).append('$').append(this.c);
    }

    @Override // com.android.tools.r8.internal.AbstractC1176Mb
    final StringBuilder b(StringBuilder sb) {
        return this.a.b(sb).append('$').append(this.c);
    }

    public final C0729q2 a() {
        return this.b;
    }

    public final String b() {
        return b(new StringBuilder()).toString();
    }
}
