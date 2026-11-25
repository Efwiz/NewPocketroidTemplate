package com.android.tools.r8.ir.regalloc;

import com.android.tools.r8.internal.AbstractC2412ko;
import com.android.tools.r8.internal.AbstractC3472z00;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class g implements Comparable<g> {
    static final /* synthetic */ boolean e = true;
    final AbstractC3472z00 a;
    final int b;
    final int c;
    final AbstractC2412ko d;

    public g(int i, int i2, AbstractC3472z00 abstractC3472z00) {
        this.b = i;
        this.c = i2;
        this.d = null;
        this.a = abstractC3472z00;
    }

    public final boolean a(HashMap map, TreeSet treeSet) {
        Iterator it = treeSet.iterator();
        while (it.hasNext()) {
            g gVar = (g) it.next();
            int i = gVar.c;
            if (i != Integer.MIN_VALUE && gVar != this) {
                int iIntValue = ((Integer) map.get(Integer.valueOf(i))).intValue();
                if ((this.a.M() && this.b + 1 == iIntValue) || this.b == iIntValue) {
                    return true;
                }
                if (gVar.a.M()) {
                    int iIntValue2 = ((Integer) map.get(Integer.valueOf(gVar.c))).intValue() + 1;
                    if ((this.a.M() && this.b + 1 == iIntValue2) || this.b == iIntValue2) {
                        return true;
                    }
                } else {
                    continue;
                }
            }
        }
        return false;
    }

    @Override // java.lang.Comparable
    public final int compareTo(g gVar) {
        g gVar2 = gVar;
        int i = this.c - gVar2.c;
        if (i != 0) {
            return i;
        }
        int i2 = this.b - gVar2.b;
        if (i2 != 0) {
            return i2;
        }
        if (this.a.H() != gVar2.a.H()) {
            return Boolean.compare(this.a.H(), gVar2.a.H());
        }
        if (this.a.M() != gVar2.a.M()) {
            return Boolean.compare(this.a.M(), gVar2.a.M());
        }
        if (this.a.I() != gVar2.a.I()) {
            return Boolean.compare(this.a.I(), gVar2.a.I());
        }
        AbstractC2412ko abstractC2412ko = this.d;
        if (abstractC2412ko == null) {
            return gVar2.d != null ? -1 : 0;
        }
        if (gVar2.d == null) {
            return 1;
        }
        return abstractC2412ko.W0() - gVar2.d.W0();
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof g)) {
            return false;
        }
        g gVar = (g) obj;
        return gVar.c == this.c && gVar.b == this.b && gVar.a == this.a && gVar.d == this.d;
    }

    public final int hashCode() {
        int iHashCode = (this.a.hashCode() * 5) + (this.b * 3) + this.c;
        AbstractC2412ko abstractC2412ko = this.d;
        return iHashCode + (abstractC2412ko == null ? 0 : abstractC2412ko.hashCode());
    }

    public g(int i, AbstractC3472z00 abstractC3472z00, AbstractC2412ko abstractC2412ko) {
        if (!e && !abstractC2412ko.r2()) {
            throw new AssertionError();
        }
        this.b = i;
        this.c = Integer.MIN_VALUE;
        this.d = abstractC2412ko;
        this.a = abstractC3472z00;
    }
}
