package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Bt, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0869Bt extends ME implements JK {
    private final BitSet F;
    private final HashMap G;
    final BitSet H;

    public C0869Bt(AbstractC1899eF abstractC1899eF, int i, String str, String str2, String[] strArr) {
        this(i, abstractC1899eF, str, str2, strArr);
    }

    @Override // com.android.tools.r8.internal.ME, com.android.tools.r8.internal.AbstractC1899eF
    public final void a(int i, C3263wA c3263wA) {
        super.a(i, c3263wA);
        C3338xA c3338xA = ((C1871du) this.x.b()).g;
        if (i != 168 || this.G.containsKey(c3338xA)) {
            return;
        }
        this.G.put(c3338xA, new BitSet());
    }

    @Override // com.android.tools.r8.internal.ME, com.android.tools.r8.internal.AbstractC1899eF
    public final void c() {
        boolean z;
        boolean z2;
        if (!this.G.isEmpty()) {
            BitSet bitSet = new BitSet();
            BitSet bitSet2 = this.F;
            int i = 0;
            a(0, bitSet2, bitSet);
            do {
                z = false;
                for (C3443yZ c3443yZ : this.y) {
                    int iB = this.x.b(c3443yZ.c);
                    if (!bitSet2.get(iB)) {
                        int iB2 = this.x.b(c3443yZ.a);
                        int iB3 = this.x.b(c3443yZ.b);
                        int iNextSetBit = bitSet2.nextSetBit(iB2);
                        if (iNextSetBit >= iB2 && iNextSetBit < iB3) {
                            a(iB, bitSet2, bitSet);
                            z = true;
                        }
                    }
                }
            } while (z);
            for (Map.Entry entry : this.G.entrySet()) {
                C3338xA c3338xA = (C3338xA) entry.getKey();
                BitSet bitSet3 = (BitSet) entry.getValue();
                a(this.x.b(c3338xA), bitSet3, bitSet);
                do {
                    z2 = false;
                    for (C3443yZ c3443yZ2 : this.y) {
                        int iB4 = this.x.b(c3443yZ2.c);
                        if (!bitSet3.get(iB4)) {
                            int iB5 = this.x.b(c3443yZ2.a);
                            int iB6 = this.x.b(c3443yZ2.b);
                            int iNextSetBit2 = bitSet3.nextSetBit(iB5);
                            if (iNextSetBit2 >= iB5 && iNextSetBit2 < iB6) {
                                a(iB4, bitSet3, bitSet);
                                z2 = true;
                            }
                        }
                    }
                } while (z2);
            }
            LinkedList linkedList = new LinkedList();
            C3338xA c3338xA2 = null;
            linkedList.add(new C0843At(this, null, this.F));
            C0863Bn c0863Bn = new C0863Bn();
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            while (!linkedList.isEmpty()) {
                C0843At c0843At = (C0843At) linkedList.removeFirst();
                C3338xA c3338xA3 = c3338xA2;
                for (int i2 = i; i2 < this.x.size(); i2++) {
                    AbstractC3097u abstractC3097uJ = this.x.j(i2);
                    if (abstractC3097uJ.c() == 8) {
                        C3338xA c3338xA4 = (C3338xA) c0843At.c.get((C3338xA) abstractC3097uJ);
                        if (c3338xA4 != c3338xA3) {
                            c0863Bn.a(c3338xA4);
                            c3338xA3 = c3338xA4;
                        }
                    } else if (c0843At.a(i2) != c0843At) {
                        continue;
                    } else if (abstractC3097uJ.b() == 169) {
                        C3338xA c3338xA5 = c3338xA2;
                        for (C0843At c0843At2 = c0843At; c0843At2 != null; c0843At2 = c0843At2.a) {
                            if (c0843At2.b.get(i2)) {
                                c3338xA5 = c0843At2.d;
                            }
                        }
                        if (c3338xA5 == null) {
                            throw new IllegalArgumentException("Instruction #" + i2 + " is a RET not owned by any subroutine");
                        }
                        c0863Bn.a(new C1871du(167, c3338xA5));
                    } else if (abstractC3097uJ.b() == 168) {
                        C3338xA c3338xA6 = ((C1871du) abstractC3097uJ).g;
                        C0843At c0843At3 = new C0843At(this, c0843At, (BitSet) this.G.get(c3338xA6));
                        C3338xA c3338xA7 = (C3338xA) c0843At3.a(this.x.b(c3338xA6)).c.get(c3338xA6);
                        c0863Bn.a(new C0903Cn(1));
                        c0863Bn.a(new C1871du(167, c3338xA7));
                        c0863Bn.a(c0843At3.d);
                        linkedList.add(c0843At3);
                    } else {
                        c0863Bn.a(abstractC3097uJ.a(c0843At));
                    }
                }
                for (C3443yZ c3443yZ3 : this.y) {
                    C3338xA c3338xA8 = (C3338xA) c0843At.c.get(c3443yZ3.a);
                    C3338xA c3338xA9 = (C3338xA) c0843At.c.get(c3443yZ3.b);
                    if (c3338xA8 != c3338xA9) {
                        C3338xA c3338xA10 = c3443yZ3.c;
                        C3338xA c3338xA11 = (C3338xA) c0843At.a(c0843At.e.x.b(c3338xA10)).c.get(c3338xA10);
                        if (c3338xA8 == null || c3338xA9 == null || c3338xA11 == null) {
                            throw new AssertionError("Internal error!");
                        }
                        arrayList.add(new C3443yZ(c3338xA8, c3338xA9, c3338xA11, c3443yZ3.d));
                    }
                }
                for (CC cc : this.B) {
                    C3338xA c3338xA12 = (C3338xA) c0843At.c.get(cc.d);
                    C3338xA c3338xA13 = (C3338xA) c0843At.c.get(cc.e);
                    if (c3338xA12 != c3338xA13) {
                        arrayList2.add(new CC(cc.a, cc.b, cc.c, c3338xA12, c3338xA13, cc.f));
                        i = 0;
                        c3338xA2 = null;
                    }
                }
            }
            this.x = c0863Bn;
            this.y = arrayList;
            this.B = arrayList2;
        }
        AbstractC1899eF abstractC1899eF = this.i;
        if (abstractC1899eF != null) {
            a(abstractC1899eF);
        }
    }

    protected C0869Bt(int i, AbstractC1899eF abstractC1899eF, String str, String str2, String[] strArr) {
        super(589824, i, str, str2, strArr);
        this.F = new BitSet();
        this.G = new HashMap();
        this.H = new BitSet();
        this.i = abstractC1899eF;
    }

    private void a(int i, BitSet bitSet, BitSet bitSet2) {
        while (i < this.x.size() && !bitSet.get(i)) {
            bitSet.set(i);
            if (bitSet2.get(i)) {
                this.H.set(i);
            }
            bitSet2.set(i);
            AbstractC3097u abstractC3097uJ = this.x.j(i);
            if (abstractC3097uJ.c() == 7 && abstractC3097uJ.b() != 168) {
                a(this.x.b(((C1871du) abstractC3097uJ).g), bitSet, bitSet2);
            } else if (abstractC3097uJ.c() == 11) {
                YY yy = (YY) abstractC3097uJ;
                a(this.x.b(yy.i), bitSet, bitSet2);
                Iterator it = yy.j.iterator();
                while (it.hasNext()) {
                    a(this.x.b((C3338xA) it.next()), bitSet, bitSet2);
                }
            } else if (abstractC3097uJ.c() == 12) {
                C2056gD c2056gD = (C2056gD) abstractC3097uJ;
                a(this.x.b(c2056gD.g), bitSet, bitSet2);
                Iterator it2 = c2056gD.i.iterator();
                while (it2.hasNext()) {
                    a(this.x.b((C3338xA) it2.next()), bitSet, bitSet2);
                }
            }
            int iB = this.x.j(i).b();
            if (iB != 167 && iB != 191) {
                switch (iB) {
                    case 169:
                    case 170:
                    case 171:
                    case 172:
                    case 173:
                    case 174:
                    case 175:
                    case 176:
                    case 177:
                        return;
                    default:
                        i++;
                        break;
                }
            } else {
                return;
            }
        }
    }
}
