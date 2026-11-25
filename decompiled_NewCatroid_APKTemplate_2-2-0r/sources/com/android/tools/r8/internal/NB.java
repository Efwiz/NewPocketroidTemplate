package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0594a1;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class NB<V, EV> {
    private static final int p = Float.floatToRawIntBits(0.0f);
    private static final int q = Float.floatToRawIntBits(1.0f);
    private static final int r = Float.floatToRawIntBits(2.0f);
    private static final long s = Double.doubleToRawLongBits(0.0d);
    private static final long t = Double.doubleToRawLongBits(1.0d);
    static final /* synthetic */ boolean u = true;
    private final C0722p1 a;
    private final R5 b;
    private final C2447lC c;
    private final MR d;
    private final ArrayList e;
    private int f;
    private int g;
    private C3155ul h;
    private final TB i;
    private AbstractC1915eM j;
    private AbstractC1915eM k;
    private final C1303Qq l;
    private final HashMap m;
    private final C1303Qq n;
    private int[] o;

    public NB(C0684j2 c0684j2, TB tb, C0722p1 c0722p1) {
        R5 r5 = new R5();
        this.b = r5;
        this.c = new C2447lC(r5);
        this.f = 0;
        this.g = 0;
        this.h = null;
        this.l = new C1303Qq();
        this.m = new HashMap();
        this.n = new C1303Qq();
        this.o = new int[10];
        this.a = c0722p1;
        this.d = new MR();
        this.e = new ArrayList();
        this.i = tb;
        AbstractC1915eM.c cVarA = AbstractC1915eM.c.x().a(0).a(c0684j2).a();
        this.j = cVarA;
        this.k = cVarA;
    }

    private int d() {
        if (!this.j.equals(this.k)) {
            int i = this.g;
            AbstractC1915eM abstractC1915eM = this.j;
            if (!u && !this.e.isEmpty() && ((PB) com.android.tools.r8.utils.V1.b(this.e)).a >= i) {
                throw new AssertionError();
            }
            this.e.add(new PB(i, abstractC1915eM));
            this.k = this.j;
        }
        int i2 = this.g;
        this.g = i2 + 1;
        return i2;
    }

    private void f(int i) {
        C2447lC c2447lC = this.c;
        Objects.requireNonNull(c2447lC);
        C1589a6.a(i, (InterfaceC1741c6) new NB$$ExternalSyntheticLambda0(c2447lC));
    }

    private void g(int i) {
        C2447lC c2447lC = this.c;
        Objects.requireNonNull(c2447lC);
        C1589a6.a(i, (InterfaceC1741c6) new NB$$ExternalSyntheticLambda0(c2447lC));
    }

    public final C0767v2 a(AbstractC3472z00 abstractC3472z00) {
        if (abstractC3472z00.H()) {
            return abstractC3472z00.c().a(this.a);
        }
        if (abstractC3472z00.I()) {
            return abstractC3472z00.d().a(this.a);
        }
        throw new C3405y10("Unexpected type element: " + abstractC3472z00);
    }

    public final void b(AbstractC1915eM abstractC1915eM) {
        boolean z = u;
        if (!z && abstractC1915eM == null) {
            throw new AssertionError();
        }
        if (!z && abstractC1915eM == AbstractC1915eM.u()) {
            throw new AssertionError();
        }
        this.j = abstractC1915eM;
    }

    public NB<V, EV> c(int i) {
        if (-1 > i || i > 5) {
            d();
            C2447lC c2447lC = this.c;
            int i2 = C1589a6.b;
            c2447lC.a(200, 4);
            C2447lC c2447lC2 = this.c;
            Objects.requireNonNull(c2447lC2);
            C1589a6.a(i, (InterfaceC1741c6) new NB$$ExternalSyntheticLambda0(c2447lC2));
        } else {
            d(i + 3);
        }
        return this;
    }

    public final void e(int i) {
        if (!u && this.g + this.f != i) {
            throw new AssertionError();
        }
    }

    public RB<EV> e() {
        if (!u && this.h == null) {
            throw new AssertionError();
        }
        final AbstractC0594a1[] abstractC0594a1Arr = new AbstractC0594a1[this.d.size()];
        this.d.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.NB$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                NB.a(abstractC0594a1Arr, (AbstractC0594a1) obj, (Integer) obj2);
            }
        });
        OB ob = this.m.isEmpty() ? null : new OB(this.m, this.n);
        C3155ul c3155ul = this.h;
        ArrayList arrayList = this.e;
        return new RB<>(c3155ul, abstractC0594a1Arr, (PB[]) arrayList.toArray(new PB[arrayList.size()]), this.f, this.b.a(), this.g, new QB(this.l), ob, this.i.a());
    }

    public final void b(C0767v2 c0767v2) {
        a(Collections.singletonList(c0767v2), Collections.emptyList(), 187);
    }

    public final NB b(int i) {
        if (i == p) {
            return d(11);
        }
        if (i == q) {
            return d(12);
        }
        if (i == r) {
            return d(13);
        }
        d();
        C2447lC c2447lC = this.c;
        int i2 = C1589a6.b;
        c2447lC.a(202, 4);
        C2447lC c2447lC2 = this.c;
        Objects.requireNonNull(c2447lC2);
        C1589a6.a(i, (InterfaceC1741c6) new NB$$ExternalSyntheticLambda0(c2447lC2));
        return this;
    }

    public final void c(C0684j2 c0684j2, ArrayList arrayList, boolean z) {
        a(Collections.singletonList(c0684j2), arrayList, z ? 208 : 207);
    }

    public final void c(S10 s10) {
        a(176, s10);
    }

    public final void a(int i, C3038t6 c3038t6) {
        this.l.a(i, c3038t6);
    }

    public final void c() {
        d(177);
    }

    private void a(AbstractC0594a1 abstractC0594a1) {
        int size = this.d.size();
        Integer num = (Integer) this.d.putIfAbsent(abstractC0594a1, Integer.valueOf(size));
        if (num != null) {
            size = num.intValue();
        }
        if (!u) {
            int i = C1589a6.b;
        }
        C2447lC c2447lC = this.c;
        Objects.requireNonNull(c2447lC);
        C1589a6.a(size, (InterfaceC1741c6) new NB$$ExternalSyntheticLambda0(c2447lC));
    }

    private NB d(int i) {
        d();
        this.c.a(i);
        return this;
    }

    public final void d(S10 s10) {
        a(191, s10);
    }

    public final void a(C3332x4 c3332x4) {
        int iA = this.i.a(c3332x4);
        int i = C1589a6.b;
        d();
        this.c.a(167, 4);
        f(iA);
    }

    public final NB b(long j) {
        if (j == 0) {
            return d(9);
        }
        if (j == 1) {
            return d(10);
        }
        d();
        C2447lC c2447lC = this.c;
        int i = C1589a6.b;
        c2447lC.a(201, 8);
        C2447lC c2447lC2 = this.c;
        Objects.requireNonNull(c2447lC2);
        C1589a6.a(j, new NB$$ExternalSyntheticLambda0(c2447lC2));
        return this;
    }

    public NB<V, EV> a(C3155ul c3155ul) {
        this.h = c3155ul;
        return this;
    }

    public final void a(Object obj, com.android.tools.r8.graph.X x) {
        com.android.tools.r8.graph.X x2 = (com.android.tools.r8.graph.X) this.m.put(obj, x);
        if (!u && x2 != null) {
            throw new AssertionError();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void a(int i, Set set) {
        int size = set.size();
        int[] iArr = new int[size];
        Iterator it = set.iterator();
        for (int i2 = 0; i2 < size; i2++) {
            iArr[i2] = this.i.a().a().a(i, this.i.a(it.next()));
        }
        this.n.a(i, iArr);
    }

    public final void b(C0684j2 c0684j2, ArrayList arrayList, boolean z) {
        a(Collections.singletonList(c0684j2), arrayList, z ? 204 : 184);
    }

    public final void b(C0684j2 c0684j2, ArrayList arrayList) {
        a(Collections.singletonList(c0684j2), arrayList, 182);
    }

    public final void b() {
        d(211);
    }

    public final void b(S10 s10) {
        a(213, s10);
    }

    public final void a(int i) {
        if (!u && this.f != i) {
            throw new AssertionError();
        }
        this.f++;
    }

    public final void a(C0759u2 c0759u2) {
        a(Collections.singletonList(c0759u2), Collections.emptyList(), 18);
    }

    public final void a(C0767v2 c0767v2) {
        a(Collections.singletonList(c0767v2), Collections.emptyList(), 212);
    }

    public final void a(com.android.tools.r8.graph.Y0 y0) {
        a(Collections.singletonList(y0), Collections.emptyList(), 178);
    }

    private NB a(int i, S10 s10) {
        return a(Collections.emptyList(), Collections.singletonList(s10), i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private NB a(List list, List list2, int i) {
        if (!u && list2.size() >= 10) {
            throw new AssertionError();
        }
        int iD = d();
        Iterator it = list.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            i2 += 4;
        }
        for (int i3 = 0; i3 < list2.size(); i3++) {
            int iA = this.i.a().a().a(this.f + iD, this.i.a(list2.get(i3)));
            int i4 = C1589a6.b;
            i2 += 4;
            this.o[i3] = iA;
        }
        this.c.a(i, i2);
        Iterator it2 = list.iterator();
        while (it2.hasNext()) {
            a((AbstractC0594a1) it2.next());
        }
        for (int i5 = 0; i5 < list2.size(); i5++) {
            g(this.o[i5]);
        }
        return this;
    }

    public NB<V, EV> a() {
        return d(1);
    }

    public final NB a(long j) {
        if (j == s) {
            return d(14);
        }
        if (j == t) {
            return d(15);
        }
        d();
        C2447lC c2447lC = this.c;
        int i = C1589a6.b;
        c2447lC.a(203, 8);
        C2447lC c2447lC2 = this.c;
        Objects.requireNonNull(c2447lC2);
        C1589a6.a(j, new NB$$ExternalSyntheticLambda0(c2447lC2));
        return this;
    }

    public final void a(long j, EnumC1812d20 enumC1812d20) {
        int i = MB.a[enumC1812d20.ordinal()];
        if (i == 1) {
            a();
            return;
        }
        if (i == 2) {
            c((int) j);
            return;
        }
        if (i == 3) {
            b((int) j);
        } else if (i == 4) {
            b(j);
        } else {
            if (i != 5) {
                throw new Z00();
            }
            a(j);
        }
    }

    public final void a(EnumC2527mI enumC2527mI, S10 s10, S10 s11) {
        int i;
        switch (MB.b[enumC2527mI.ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
                i = 108;
                break;
            case 5:
                i = 109;
                break;
            case 6:
                i = 110;
                break;
            case 7:
                i = 111;
                break;
            default:
                throw new C3405y10("Unexpected type: " + enumC2527mI);
        }
        a(Collections.emptyList(), AbstractC1639am.a(s10, s11), i);
    }

    public final void a(S10 s10) {
        a(190, s10);
    }

    public final void a(com.android.tools.r8.graph.Y0 y0, S10 s10) {
        a(Collections.singletonList(y0), Collections.singletonList(s10), 180);
    }

    public final void a(com.android.tools.r8.graph.Y0 y0, S10 s10, S10 s11) {
        a(Collections.singletonList(y0), AbstractC1639am.a(s10, s11), 181);
    }

    public final void a(C0684j2 c0684j2, ArrayList arrayList, boolean z) {
        a(Collections.singletonList(c0684j2), arrayList, z ? 206 : 205);
    }

    public final void a(C0684j2 c0684j2, ArrayList arrayList) {
        a(Collections.singletonList(c0684j2), arrayList, 185);
    }

    public final void a(AbstractC1915eM abstractC1915eM) {
        if (!u && this.j != abstractC1915eM) {
            throw new AssertionError();
        }
        d(209);
    }

    public final void a(EnumC1076Il enumC1076Il, EnumC1812d20 enumC1812d20, S10 s10, C3332x4 c3332x4) {
        int i;
        switch (MB.c[enumC1076Il.ordinal()]) {
            case 1:
                i = !enumC1812d20.a() ? 153 : 198;
                break;
            case 2:
                i = 156;
                break;
            case 3:
                i = 157;
                break;
            case 4:
                i = 158;
                break;
            case 5:
                i = 155;
                break;
            case 6:
                i = !enumC1812d20.a() ? 154 : 199;
                break;
            default:
                throw new C3405y10("Unexpected if kind: " + enumC1076Il);
        }
        int iD = d();
        int iA = this.i.a(c3332x4);
        int iA2 = this.i.a().a().a(iD + this.f, this.i.a(s10));
        int i2 = C1589a6.b;
        this.c.a(i, 8);
        f(iA);
        g(iA2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void a(EnumC1076Il enumC1076Il, EnumC1812d20 enumC1812d20, ArrayList arrayList, C3332x4 c3332x4) {
        int i;
        switch (MB.c[enumC1076Il.ordinal()]) {
            case 1:
                i = !enumC1812d20.a() ? 159 : 165;
                break;
            case 2:
                i = 162;
                break;
            case 3:
                i = 163;
                break;
            case 4:
                i = 164;
                break;
            case 5:
                i = 161;
                break;
            case 6:
                i = !enumC1812d20.a() ? 160 : 166;
                break;
            default:
                throw new C3405y10("Unexpected if kind " + enumC1076Il);
        }
        int iD = d();
        int iA = this.i.a(c3332x4);
        int iA2 = this.i.a().a().a(this.f + iD, this.i.a(arrayList.get(0)));
        int iA3 = this.i.a().a().a(iD + this.f, this.i.a(arrayList.get(1)));
        int i2 = C1589a6.b;
        this.c.a(i, 12);
        f(iA);
        g(iA2);
        g(iA3);
    }

    public final void a(AbstractC3472z00 abstractC3472z00, List list) {
        a(Collections.singletonList(a(abstractC3472z00)), list, 210);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(AbstractC0594a1[] abstractC0594a1Arr, AbstractC0594a1 abstractC0594a1, Integer num) {
        abstractC0594a1Arr[num.intValue()] = abstractC0594a1;
    }

    private static int a(EnumC2246ib enumC2246ib, EnumC2527mI enumC2527mI) {
        int i = MB.b[enumC2527mI.ordinal()];
        if (i == 5) {
            return 148;
        }
        if (i == 6) {
            return enumC2246ib == EnumC2246ib.LT ? 149 : 150;
        }
        if (i == 7) {
            return enumC2246ib == EnumC2246ib.LT ? 151 : 152;
        }
        throw new C3405y10("Cmp has unknown type " + enumC2527mI);
    }

    public final void a(EnumC2527mI enumC2527mI, EnumC2246ib enumC2246ib, S10 s10, S10 s11) {
        a(Collections.emptyList(), AbstractC1639am.a(s10, s11), a(enumC2246ib, enumC2527mI));
    }

    public final void a(C3481z6.a aVar, EnumC2527mI enumC2527mI, S10 s10, S10 s11) {
        boolean z = u;
        if (!z && 96 != C3481z6.a(C3481z6.a.Add, EnumC2527mI.INT)) {
            throw new AssertionError();
        }
        if (!z && 115 != C3481z6.a(C3481z6.a.Rem, EnumC2527mI.DOUBLE)) {
            throw new AssertionError();
        }
        a(Collections.emptyList(), AbstractC1639am.a(s10, s11), C3481z6.a(aVar, enumC2527mI));
    }

    public final void a(EnumC3423yF enumC3423yF, S10 s10) {
        a(enumC3423yF == EnumC3423yF.ENTER ? 194 : 195, s10);
    }

    public final void a(C0767v2 c0767v2, S10 s10) {
        a(Collections.singletonList(c0767v2), Collections.singletonList(s10), 188);
    }

    public final void a(EnumC2527mI enumC2527mI, EnumC2527mI enumC2527mI2, S10 s10) {
        int iT = new C1591a8(enumC2527mI, enumC2527mI2).T();
        boolean z = u;
        if (!z && 133 > iT) {
            throw new AssertionError();
        }
        if (!z && iT > 147) {
            throw new AssertionError();
        }
        a(iT, s10);
    }
}
