package com.android.tools.r8.internal;

import com.android.dx.rop.code.RegisterSpec;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0767v2;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class ZK implements Comparable {
    static final /* synthetic */ boolean g = true;
    final List a;
    final List b;
    final ArrayList c = new ArrayList();
    public final C0767v2 d;
    private C0737r2 e;
    final /* synthetic */ C2225iL f;

    ZK(C2225iL c2225iL, List list, ArrayList arrayList, ArrayList arrayList2, C0767v2 c0767v2, int i, int i2) {
        this.f = c2225iL;
        this.a = arrayList;
        this.b = arrayList2;
        this.d = c0767v2;
        while (i < i2) {
            AbstractC2412ko abstractC2412ko = (AbstractC2412ko) list.get(i);
            if (abstractC2412ko.U1() || abstractC2412ko.p2() || abstractC2412ko.n1()) {
                this.c.add(AbstractC1757cL.a(abstractC2412ko));
            } else if (!abstractC2412ko.y1() && !abstractC2412ko.s1() && !g) {
                throw new AssertionError("Unexpected type of instruction in outlining template.");
            }
            i++;
        }
    }

    final int a() {
        return this.a.size();
    }

    public final ZK b(AbstractC1476Wj abstractC1476Wj) {
        if (a(abstractC1476Wj)) {
            return null;
        }
        return this;
    }

    @Override // java.lang.Comparable
    public final int compareTo(Object obj) {
        ZK zk = (ZK) obj;
        if (this == zk) {
            return 0;
        }
        if (this.e == null) {
            this.e = this.f.e.b(this.d, (C0767v2[]) this.a.toArray(C0767v2.g));
        }
        C0737r2 c0737r2 = this.e;
        if (zk.e == null) {
            zk.e = zk.f.e.b(zk.d, (C0767v2[]) zk.a.toArray(C0767v2.g));
        }
        int iCompareTo = c0737r2.compareTo(zk.e);
        if (iCompareTo == 0) {
            boolean z = g;
            if (!z && this.a.size() != zk.a.size()) {
                throw new AssertionError();
            }
            ArrayList arrayList = this.c;
            ArrayList arrayList2 = zk.c;
            int size = arrayList.size() - arrayList2.size();
            if (size != 0) {
                if (z || !equals(zk)) {
                    return size;
                }
                throw new AssertionError();
            }
            for (int i = 0; i < arrayList.size(); i++) {
                AbstractC1757cL abstractC1757cL = (AbstractC1757cL) arrayList.get(i);
                AbstractC1757cL abstractC1757cL2 = (AbstractC1757cL) arrayList2.get(i);
                int iA = abstractC1757cL.a(abstractC1757cL2);
                if (iA != 0) {
                    if (g || !abstractC1757cL.equals(abstractC1757cL2)) {
                        return iA;
                    }
                    throw new AssertionError();
                }
            }
            iCompareTo = this.b.size() - zk.b.size();
            if (iCompareTo == 0) {
                for (int i2 = 0; i2 < this.b.size(); i2++) {
                    int iIntValue = ((Integer) this.b.get(i2)).intValue() - ((Integer) zk.b.get(i2)).intValue();
                    if (iIntValue != 0) {
                        if (g || !equals(zk)) {
                            return iIntValue;
                        }
                        throw new AssertionError();
                    }
                }
                if (g || equals(zk)) {
                    return 0;
                }
                throw new AssertionError();
            }
            if (!g && equals(zk)) {
                throw new AssertionError();
            }
        } else if (!g && equals(zk)) {
            throw new AssertionError();
        }
        return iCompareTo;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof ZK)) {
            return false;
        }
        ZK zk = (ZK) obj;
        ArrayList arrayList = this.c;
        ArrayList arrayList2 = zk.c;
        if (arrayList.size() != arrayList2.size()) {
            return false;
        }
        for (int i = 0; i < arrayList.size(); i++) {
            if (!((AbstractC1757cL) arrayList.get(i)).equals((AbstractC1757cL) arrayList2.get(i))) {
                return false;
            }
        }
        return this.a.equals(zk.a) && this.b.equals(zk.b) && this.d == zk.d;
    }

    public final int hashCode() {
        int size = this.c.size();
        int iHashCode = 0;
        for (int i = 0; i < this.c.size() && i < 5; i++) {
            iHashCode = (iHashCode << 4) + ((AbstractC1757cL) this.c.get(i)).hashCode();
            size = (size * 3) + iHashCode;
        }
        return size;
    }

    public final String toString() {
        int size = this.a.size();
        StringBuilder sb = new StringBuilder();
        sb.append(this.d);
        sb.append(" anOutline");
        com.android.tools.r8.utils.Y2.a(sb, this.a, ", ", com.android.tools.r8.utils.Y2.a.PARENS);
        sb.append("\n");
        int i = 0;
        for (AbstractC1757cL abstractC1757cL : this.c) {
            sb.append(abstractC1757cL.toString());
            com.android.tools.r8.utils.Y2.b(sb, abstractC1757cL.b(), 20);
            if (abstractC1757cL.c()) {
                sb.append(RegisterSpec.PREFIX + size);
                sb.append(" <- ");
            }
            int i2 = 0;
            while (i2 < abstractC1757cL.d()) {
                sb.append(i2 > 0 ? ", " : "");
                sb.append(RegisterSpec.PREFIX);
                int i3 = i + 1;
                int iIntValue = ((Integer) this.b.get(i)).intValue();
                if (iIntValue >= 0) {
                    sb.append(iIntValue);
                } else {
                    sb.append(size);
                }
                i2++;
                i = i3;
            }
            sb.append(abstractC1757cL.a());
            sb.append("\n");
        }
        if (this.d == this.f.e.D1) {
            sb.append("Return-Void");
        } else {
            com.android.tools.r8.utils.Y2.b(sb, "Return", 20);
            sb.append(RegisterSpec.PREFIX + size);
        }
        sb.append("\n");
        sb.append(this.b);
        return sb.toString();
    }

    private boolean a(final AbstractC1476Wj abstractC1476Wj) {
        for (C0767v2 c0767v2 : this.a) {
            if (AbstractC2964s2.a(abstractC1476Wj, c0767v2) != c0767v2) {
                return true;
            }
        }
        if (AbstractC2964s2.a(abstractC1476Wj, this.d) != this.d) {
            return true;
        }
        return AbstractC2734ot.b(this.c, new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.ZK$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return ZK.a(abstractC1476Wj, (AbstractC1757cL) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(AbstractC1476Wj abstractC1476Wj, AbstractC1757cL abstractC1757cL) {
        return abstractC1757cL.a(abstractC1476Wj);
    }
}
