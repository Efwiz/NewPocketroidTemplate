package com.android.tools.r8.internal;

import java.util.BitSet;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class KU extends S {
    final BitSet c;
    final /* synthetic */ LU d;

    KU(LU lu) {
        this.d = lu;
        this.c = new BitSet(lu.b.size());
    }

    @Override // com.android.tools.r8.internal.S
    protected final Object a() {
        if (this.c.isEmpty()) {
            this.c.set(0, this.d.a);
        } else {
            int iNextSetBit = this.c.nextSetBit(0);
            int iNextClearBit = this.c.nextClearBit(iNextSetBit);
            if (iNextClearBit == this.d.b.size()) {
                b();
                return null;
            }
            int i = (iNextClearBit - iNextSetBit) - 1;
            this.c.set(0, i);
            this.c.clear(i, iNextClearBit);
            this.c.set(iNextClearBit);
        }
        return new JU(this, (BitSet) this.c.clone());
    }
}
