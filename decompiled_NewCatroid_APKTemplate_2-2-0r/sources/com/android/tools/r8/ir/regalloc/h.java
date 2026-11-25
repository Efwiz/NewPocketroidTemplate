package com.android.tools.r8.ir.regalloc;

import com.android.tools.r8.internal.AbstractC0824Ac;
import com.android.tools.r8.internal.AbstractC1915eM;
import com.android.tools.r8.internal.AbstractC2412ko;
import com.android.tools.r8.internal.AbstractC3472z00;
import com.android.tools.r8.internal.C0978Fc;
import com.android.tools.r8.internal.C2799pi;
import com.android.tools.r8.internal.C2879qr;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.internal.C3406y2;
import com.android.tools.r8.internal.C3492zF;
import com.android.tools.r8.internal.InterfaceC2641no;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeSet;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class h {
    static final /* synthetic */ boolean g = true;
    private final TreeSet a;
    private final HashMap b;
    private int c;
    private final InterfaceC2641no d;
    private final AbstractC1915eM e;
    private final int f;

    public h(InterfaceC2641no interfaceC2641no, int i, AbstractC1915eM abstractC1915eM) {
        this.a = new TreeSet();
        this.b = new HashMap();
        this.c = 0;
        this.d = interfaceC2641no;
        this.f = i;
        this.e = abstractC1915eM;
    }

    public void a(g gVar) {
        this.a.add(gVar);
        int i = gVar.c;
        if (i != Integer.MIN_VALUE) {
            this.b.put(Integer.valueOf(i), Integer.valueOf(gVar.c));
        }
        this.b.put(Integer.valueOf(gVar.b), Integer.valueOf(gVar.b));
    }

    /* JADX WARN: Code duplicated, block: B:129:0x0166 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:131:0x0150 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:54:0x0141  */
    /* JADX WARN: Code duplicated, block: B:58:0x0156  */
    public void b() {
        AbstractC2412ko c3492zF;
        AbstractC2412ko c3492zF2;
        Integer numValueOf;
        int i;
        Iterator it;
        g gVar;
        if (!g) {
            C2879qr c2879qr = new C2879qr(this.a.size());
            Iterator it2 = this.a.iterator();
            while (it2.hasNext()) {
                boolean zA = c2879qr.a(((g) it2.next()).b);
                if (!g && !zA) {
                    throw new AssertionError();
                }
            }
        }
        LinkedList linkedList = new LinkedList();
        Iterator it3 = this.a.iterator();
        while (it3.hasNext()) {
            g gVar2 = (g) it3.next();
            if (!gVar2.a(this.b, this.a)) {
                linkedList.addLast(gVar2);
                it3.remove();
            }
        }
        while (true) {
            if (linkedList.isEmpty() && this.a.isEmpty()) {
                return;
            }
            while (!linkedList.isEmpty()) {
                g gVar3 = (g) linkedList.removeFirst();
                boolean z = g;
                if (!z) {
                    if (gVar3.a(this.b, this.a)) {
                        throw new AssertionError();
                    }
                }
                AbstractC2412ko abstractC2412ko = gVar3.d;
                if (abstractC2412ko != null) {
                    if (abstractC2412ko.m1()) {
                        C3406y2 c3406y2Q = gVar3.d.q();
                        c3492zF2 = new C3492zF(new C2799pi(gVar3.b, c3406y2Q.a()), new C2799pi(c3406y2Q.b().r().i(), c3406y2Q.a()));
                    } else {
                        if (!z && !gVar3.d.r2()) {
                            throw new AssertionError();
                        }
                        AbstractC0824Ac abstractC0824AcX0 = gVar3.d.X0();
                        if (!abstractC0824AcX0.B1()) {
                            throw new C3405y10("Unexpected definition");
                        }
                        c3492zF = new C0978Fc(new C2799pi(gVar3.b, gVar3.d.a()), abstractC0824AcX0.E().P2());
                    }
                    c3492zF2.b(this.e);
                    this.d.add(c3492zF2);
                    numValueOf = Integer.valueOf(gVar3.b);
                    i = gVar3.c;
                    if (i != Integer.MIN_VALUE) {
                        this.b.put(Integer.valueOf(i), numValueOf);
                    }
                    it = this.a.iterator();
                    while (it.hasNext()) {
                        gVar = (g) it.next();
                        if (!gVar.a(this.b, this.a)) {
                            linkedList.addLast(gVar);
                            it.remove();
                        }
                    }
                } else {
                    c3492zF = new C3492zF(new C2799pi(gVar3.b, gVar3.a), new C2799pi(((Integer) this.b.get(Integer.valueOf(gVar3.c))).intValue(), gVar3.a));
                }
                c3492zF2 = c3492zF;
                c3492zF2.b(this.e);
                this.d.add(c3492zF2);
                numValueOf = Integer.valueOf(gVar3.b);
                i = gVar3.c;
                if (i != Integer.MIN_VALUE) {
                    this.b.put(Integer.valueOf(i), numValueOf);
                }
                it = this.a.iterator();
                while (it.hasNext()) {
                    gVar = (g) it.next();
                    if (!gVar.a(this.b, this.a)) {
                        linkedList.addLast(gVar);
                        it.remove();
                    }
                }
            }
            if (!this.a.isEmpty()) {
                Iterator it4 = this.a.iterator();
                g gVar4 = null;
                while (it4.hasNext()) {
                    gVar4 = (g) it4.next();
                    if (!gVar4.a.M()) {
                        break;
                    }
                }
                it4.remove();
                int i2 = gVar4.b;
                AbstractC3472z00 abstractC3472z00 = gVar4.a;
                ArrayList<g> arrayList = new ArrayList();
                if (!g && i2 == Integer.MIN_VALUE) {
                    throw new AssertionError();
                }
                for (g gVar5 : this.a) {
                    int i3 = gVar5.c;
                    if (i3 != Integer.MIN_VALUE) {
                        int iIntValue = ((Integer) this.b.get(Integer.valueOf(i3))).intValue();
                        if (iIntValue == i2) {
                            arrayList.add(gVar5);
                        } else if (gVar5.a.M() && iIntValue + 1 == i2) {
                            arrayList.add(gVar5);
                        } else if (abstractC3472z00.M() && iIntValue - 1 == i2) {
                            arrayList.add(gVar5);
                        }
                    }
                }
                if (!g && arrayList.size() <= 0) {
                    throw new AssertionError();
                }
                for (g gVar6 : arrayList) {
                    C3492zF c3492zF3 = new C3492zF(new C2799pi(this.f + this.c, gVar6.a), new C2799pi(((Integer) this.b.get(Integer.valueOf(gVar6.c))).intValue(), gVar6.a));
                    c3492zF3.b(this.e);
                    this.d.add(c3492zF3);
                    this.b.put(Integer.valueOf(gVar6.c), Integer.valueOf(this.f + this.c));
                    this.c = gVar6.a.O() + this.c;
                }
                linkedList.addLast(gVar4);
            }
        }
    }

    public final int a() {
        return this.c;
    }

    public h(InterfaceC2641no interfaceC2641no, int i) {
        this(interfaceC2641no, i, AbstractC1915eM.u());
    }
}
