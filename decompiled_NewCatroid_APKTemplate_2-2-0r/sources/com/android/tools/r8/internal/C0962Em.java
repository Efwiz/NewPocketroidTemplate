package com.android.tools.r8.internal;

import java.util.HashSet;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Em, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C0962Em extends AbstractC1016Gm {
    private final HashSet c;

    C0962Em(AbstractC1016Gm abstractC1016Gm) {
        super(abstractC1016Gm);
        this.c = OU.a(this.b);
        for (int i = 0; i < this.b; i++) {
            this.c.add(this.a[i]);
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC1016Gm
    final AbstractC1016Gm a(Object obj) {
        int i = AbstractC3123uM.a;
        obj.getClass();
        if (this.c.add(obj)) {
            b(obj);
        }
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC1016Gm
    final AbstractC1016Gm b() {
        return new C0962Em(this);
    }

    @Override // com.android.tools.r8.internal.AbstractC1016Gm
    final AbstractC1048Hm a() {
        int i = this.b;
        if (i == 0) {
            int i2 = AbstractC1048Hm.c;
            return SS.h;
        }
        if (i != 1) {
            return new C1085It(this.c, AbstractC1639am.b(this.b, this.a));
        }
        Object obj = this.a[0];
        int i3 = AbstractC1048Hm.c;
        return new AV(obj);
    }
}
