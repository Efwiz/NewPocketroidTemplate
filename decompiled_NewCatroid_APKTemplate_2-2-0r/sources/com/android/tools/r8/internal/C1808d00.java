package com.android.tools.r8.internal;

import com.android.tools.r8.AbstractC0403b;
import java.io.IOException;
import java.util.BitSet;

/* JADX INFO: renamed from: com.android.tools.r8.internal.d00, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1808d00 extends EZ {
    C1808d00() {
    }

    @Override // com.android.tools.r8.internal.EZ
    public final void a(C1799cu c1799cu, Object obj) throws IOException {
        BitSet bitSet = (BitSet) obj;
        if (bitSet == null) {
            c1799cu.l();
            return;
        }
        c1799cu.d();
        for (int i = 0; i < bitSet.length(); i++) {
            c1799cu.a(bitSet.get(i) ? 1L : 0L);
        }
        c1799cu.f();
    }

    /* JADX WARN: Code duplicated, block: B:23:0x0052  */
    /* JADX WARN: Code duplicated, block: B:24:0x0054  */
    @Override // com.android.tools.r8.internal.EZ
    public final Object a(C1486Wt c1486Wt) throws IOException {
        boolean zL;
        if (c1486Wt.t() == 9) {
            c1486Wt.q();
            return null;
        }
        BitSet bitSet = new BitSet();
        c1486Wt.c();
        int iT = c1486Wt.t();
        int i = 0;
        while (iT != 2) {
            int iB = AbstractC0403b.b(iT);
            if (iB == 5) {
                String strR = c1486Wt.r();
                try {
                    if (Integer.parseInt(strR) != 0) {
                        zL = true;
                    } else {
                        zL = false;
                    }
                } catch (NumberFormatException unused) {
                    throw new C1542Yt("Error: Expecting: bitset number value (1, 0), Found: " + strR);
                }
            } else if (iB != 6) {
                if (iB != 7) {
                    throw new C1542Yt("Invalid bitset value type: ".concat(AbstractC1568Zt.a(iT)));
                }
                zL = c1486Wt.l();
            } else if (c1486Wt.n() != 0) {
                zL = true;
            } else {
                zL = false;
            }
            if (zL) {
                bitSet.set(i);
            }
            i++;
            iT = c1486Wt.t();
        }
        c1486Wt.g();
        return bitSet;
    }
}
