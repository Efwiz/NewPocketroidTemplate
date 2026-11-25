package com.android.tools.r8.graph;

import com.android.tools.r8.internal.AbstractC1008Gg;
import com.android.tools.r8.internal.AbstractC1428Un;
import com.android.tools.r8.internal.AbstractC1617aV;
import com.android.tools.r8.internal.C2005fV;
import com.android.tools.r8.internal.InterfaceC2090ga;
import com.android.tools.r8.internal.M5;
import java.util.BitSet;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class w5 {
    private final com.android.tools.r8.graph.proto.k a;

    public w5(com.android.tools.r8.graph.proto.k kVar) {
        this.a = kVar;
    }

    private BitSet a(BitSet bitSet) {
        if (this.a.a().g() || bitSet == null) {
            return bitSet;
        }
        int length = bitSet.length();
        BitSet bitSet2 = new BitSet(length);
        for (int i = 0; i < length; i++) {
            if (bitSet.get(i)) {
                com.android.tools.r8.graph.proto.b bVarA = this.a.a().a(i);
                if (!bVarA.c() && !(bVarA instanceof com.android.tools.r8.graph.proto.l)) {
                    bitSet2.set(this.a.a().b(i));
                }
            }
        }
        if (bitSet2.isEmpty()) {
            return null;
        }
        return bitSet2;
    }

    public final BitSet b(BitSet bitSet) {
        return a(bitSet);
    }

    public final BitSet c(BitSet bitSet) {
        return a(bitSet);
    }

    public final BitSet d(BitSet bitSet) {
        return a(bitSet);
    }

    public final M5 a(M5 m5) {
        if (this.a.a().g()) {
            return m5;
        }
        return null;
    }

    public final InterfaceC2090ga a(C0702m c0702m, InterfaceC2090ga interfaceC2090ga) {
        return this.a.a().g() ? interfaceC2090ga : interfaceC2090ga.a(this.a.a());
    }

    public final AbstractC1008Gg a(AbstractC1008Gg abstractC1008Gg) {
        return this.a.a().g() ? abstractC1008Gg : abstractC1008Gg.a(this.a.a());
    }

    public final AbstractC1428Un a(C0702m c0702m, AbstractC1428Un abstractC1428Un) {
        return this.a.a().g() ? abstractC1428Un : abstractC1428Un.a(c0702m, this.a.a());
    }

    public final int a(int i) {
        if (this.a.a().g() || i < 0) {
            return i;
        }
        if (this.a.a().a(i).c()) {
            return -1;
        }
        return this.a.a().b(i);
    }

    public final AbstractC1617aV a(C0702m c0702m, AbstractC1617aV abstractC1617aV, C2005fV c2005fV) {
        return this.a.a().g() ? abstractC1617aV : abstractC1617aV.a(c0702m, this.a.a(), c2005fV);
    }

    public final com.android.tools.r8.ir.optimize.info.a a(com.android.tools.r8.ir.optimize.info.b bVar) {
        return this.a.g() ? bVar : bVar.a(this.a);
    }
}
