package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class KB extends ZB {
    static final /* synthetic */ boolean n = true;
    private final C0702m c;
    private final RB d;
    private final SB e;
    private final C2145hI f;
    private final C2145hI g;
    private final C1303Qq h;
    private C3332x4 i;
    private int j;
    private AbstractC1915eM k;
    private PB l;
    private int m;

    public KB(RB rb, C0684j2 c0684j2, C0702m c0702m, SB sb) {
        super(rb);
        this.f = new C2145hI();
        this.g = new C2145hI();
        this.h = new C1303Qq();
        this.i = null;
        this.j = 0;
        this.l = null;
        this.m = 0;
        this.c = c0702m;
        this.d = rb;
        this.e = sb;
        this.k = AbstractC1915eM.c.x().a(0).a(c0684j2).a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C3332x4 a(Integer num) {
        return new C3332x4();
    }

    private void h() {
        if (this.i != null) {
            if (!n && this.h.a(this.j)) {
                throw new AssertionError();
            }
            return;
        }
        this.i = (C3332x4) this.h.computeIfAbsent(Integer.valueOf(this.j), new Function() { // from class: com.android.tools.r8.internal.KB$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return KB.a((Integer) obj);
            }
        });
        QB qbG = this.d.g();
        C3038t6 c3038t6 = (C3038t6) qbG.a.get(this.j);
        if (c3038t6 != null) {
            List listA = com.android.tools.r8.utils.V1.a((Collection) c3038t6.a(), new Function() { // from class: com.android.tools.r8.internal.KB$$ExternalSyntheticLambda4
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return this.f$0.c(((Integer) obj).intValue());
                }
            });
            final C3332x4 c3332x4 = this.i;
            Objects.requireNonNull(c3332x4);
            listA.forEach(new Consumer() { // from class: com.android.tools.r8.internal.KB$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    c3332x4.h((C3332x4) obj);
                }
            });
            this.i.a(c3038t6.b(), listA);
        }
    }

    @Override // com.android.tools.r8.internal.ZB
    public final int b() {
        return this.d.a() + this.j;
    }

    public final C3332x4 c(int i) {
        return (C3332x4) this.h.computeIfAbsent(Integer.valueOf(i), new Function() { // from class: com.android.tools.r8.internal.KB$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.b((Integer) obj);
            }
        });
    }

    @Override // com.android.tools.r8.internal.ZB
    public final void d(EnumC2527mI enumC2527mI, Object obj, Object obj2) {
        a((AbstractC2412ko) new US(enumC2527mI, a((AbstractC3472z00) AbstractC3472z00.k()), f(obj), f(obj2)));
    }

    @Override // com.android.tools.r8.internal.ZB
    public final void e() {
        b(this.j + 1);
    }

    public final S10 f(Object obj) {
        return this.e.a(obj, this.d.f());
    }

    @Override // com.android.tools.r8.internal.ZB
    public final void g() {
        a((AbstractC2412ko) new C1616aU());
        this.i = null;
    }

    public final void i() {
        if (this.d.b() == null) {
            return;
        }
        this.d.b().a(new BiConsumer() { // from class: com.android.tools.r8.internal.KB$$ExternalSyntheticLambda6
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(obj, (com.android.tools.r8.graph.X) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Object obj, com.android.tools.r8.graph.X x) {
        S10 s10F = f(obj);
        if (!s10F.A()) {
            s10F.a(x);
        }
        if (!n && s10F.s() != x) {
            throw new AssertionError();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ C3332x4 b(Integer num) {
        C3332x4 c3332x4 = new C3332x4();
        c3332x4.g(this.g.a());
        return c3332x4;
    }

    @Override // com.android.tools.r8.internal.ZB
    public final void d() {
        a((AbstractC2412ko) new C1343Sd());
    }

    @Override // com.android.tools.r8.internal.ZB
    public final void e(EnumC2527mI enumC2527mI, Object obj, Object obj2) {
        a((AbstractC2412ko) new C3218vY(enumC2527mI, a((AbstractC3472z00) AbstractC3472z00.k()), f(obj), f(obj2)));
    }

    public final ML b(AbstractC3472z00 abstractC3472z00) {
        PB pb;
        final int i = this.j;
        int iA = this.d.a() + i;
        SB sb = this.e;
        IntFunction intFunction = new IntFunction() { // from class: com.android.tools.r8.internal.KB$$ExternalSyntheticLambda1
            @Override // java.util.function.IntFunction
            public final Object apply(int i2) {
                return this.f$0.a(i, i2);
            }
        };
        RB rb = this.d;
        Objects.requireNonNull(rb);
        ML mlA = sb.a(iA, intFunction, abstractC3472z00, new KB$$ExternalSyntheticLambda0(rb), this.d.f());
        PB pb2 = this.l;
        if (pb2 != null && pb2.a <= this.j) {
            this.k = pb2.b;
            if (this.m < this.d.e().length) {
                PB[] pbArrE = this.d.e();
                int i2 = this.m;
                this.m = i2 + 1;
                pb = pbArrE[i2];
            } else {
                pb = null;
            }
            this.l = pb;
        }
        this.j++;
        return mlA;
    }

    @Override // com.android.tools.r8.internal.ZB
    public final void c() {
        a((AbstractC2412ko) new C0978Fc(a((AbstractC3472z00) AbstractC3472z00.m()), 0L));
    }

    @Override // com.android.tools.r8.internal.ZB
    public final void d(Object obj) {
        a((AbstractC2412ko) new C3344xF(EnumC3423yF.EXIT, f(obj)));
    }

    @Override // com.android.tools.r8.internal.ZB
    public final void e(Object obj) {
        a((AbstractC2412ko) new C1621aZ(f(obj)));
        this.i = null;
    }

    @Override // com.android.tools.r8.internal.ZB
    public final void c(EnumC2527mI enumC2527mI, Object obj, Object obj2) {
        a((AbstractC2412ko) new CF(enumC2527mI, a((AbstractC3472z00) AbstractC3472z00.k()), f(obj), f(obj2)));
    }

    public final C2409kl a(com.android.tools.r8.graph.o5 o5Var) {
        LinkedList linkedList = new LinkedList();
        C2732or c2732or = new C2732or(this.h.keySet());
        c2732or.sort(new Comparator() { // from class: com.android.tools.r8.internal.KB$$ExternalSyntheticLambda2
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return Integer.compare(((Integer) obj).intValue(), ((Integer) obj2).intValue());
            }
        });
        for (int i = 0; i < c2732or.size(); i++) {
            C3332x4 c3332x4 = (C3332x4) this.h.get(c2732or.i(i));
            c3332x4.Y();
            linkedList.add(c3332x4);
            for (AbstractC2412ko abstractC2412ko : c3332x4.p()) {
                if (abstractC2412ko.d1() && !abstractC2412ko.m1() && !(abstractC2412ko instanceof AF) && abstractC2412ko.f1()) {
                    abstractC2412ko.f(null);
                }
            }
        }
        for (int i2 = 0; i2 < this.j; i2++) {
            this.f.a();
        }
        return new C2409kl(this.c.Y(), o5Var, AbstractC1915eM.v(), linkedList, this.f, this.g, this.d.d(), o5Var.getOrigin(), new CE.a(this.c.Y()));
    }

    @Override // com.android.tools.r8.internal.ZB
    public final void c(C0684j2 c0684j2, ArrayList arrayList, boolean z) {
        a((AbstractC2412ko) new C1798ct(c0684j2, c0684j2.I0().Y0() ? null : a(c0684j2.I0().c(this.c)), a(arrayList), z));
    }

    @Override // com.android.tools.r8.internal.ZB
    public final void c(C0684j2 c0684j2, ArrayList arrayList) {
        a((AbstractC2412ko) new C2115gt(c0684j2, c0684j2.I0().Y0() ? null : a(c0684j2.I0().c(this.c)), a(arrayList)));
    }

    @Override // com.android.tools.r8.internal.ZB
    public final void c(Object obj) {
        a((AbstractC2412ko) new C3344xF(EnumC3423yF.ENTER, f(obj)));
    }

    @Override // com.android.tools.r8.internal.ZB
    public final void b(EnumC2527mI enumC2527mI, Object obj, Object obj2) {
        a((AbstractC2412ko) new C3379xf(enumC2527mI, a((AbstractC3472z00) AbstractC3472z00.k()), f(obj), f(obj2)));
    }

    @Override // com.android.tools.r8.internal.ZB
    public final void b(int i) {
        C3332x4 c3332x4C = c(i);
        a((AbstractC2412ko) new C1296Qj());
        this.i.h(c3332x4C);
        this.i = null;
    }

    @Override // com.android.tools.r8.internal.ZB
    public final void b(C0684j2 c0684j2, ArrayList arrayList, boolean z) {
        a((AbstractC2412ko) new C1721bt(c0684j2, c0684j2.I0().Y0() ? null : a(c0684j2.I0().c(this.c)), a(arrayList), z));
    }

    @Override // com.android.tools.r8.internal.ZB
    public final void b(C0767v2 c0767v2) {
        a((AbstractC2412ko) new DG(c0767v2, a(AbstractC3472z00.a(c0767v2, WH.b(), (C0702m<?>) this.c))));
    }

    public final ArrayList a(ArrayList arrayList) {
        ArrayList arrayList2 = new ArrayList(arrayList.size());
        for (int i = 0; i < arrayList.size(); i++) {
            arrayList2.add(f(arrayList.get(i)));
        }
        return arrayList2;
    }

    @Override // com.android.tools.r8.internal.ZB
    public final void b(Object obj) {
        S10 s10F = f(obj);
        S10 s10A = a((AbstractC3472z00) AbstractC3472z00.f());
        s10A.a(s10A.s().b.c(this.c));
        a((AbstractC2412ko) new C1289Qd(s10A, s10F));
    }

    private void a(AbstractC2412ko abstractC2412ko) {
        PB pb;
        int iA = this.d.a() + this.j;
        h();
        PB pb2 = this.l;
        if (pb2 != null && pb2.a <= this.j) {
            this.k = pb2.b;
            if (this.m < this.d.e().length) {
                PB[] pbArrE = this.d.e();
                int i = this.m;
                this.m = i + 1;
                pb = pbArrE[i];
            } else {
                pb = null;
            }
            this.l = pb;
        }
        this.j++;
        abstractC2412ko.b(this.k);
        this.i.p().add(abstractC2412ko);
        abstractC2412ko.a(this.i);
        int[] iArrK = this.d.k(iA);
        if (iArrK != null) {
            for (int i2 : iArrK) {
                f(this.d.e(i2, iA)).a(abstractC2412ko);
            }
        }
    }

    public final S10 a(AbstractC3472z00 abstractC3472z00) {
        int iA = this.d.a() + this.j;
        SB sb = this.e;
        RB rb = this.d;
        Objects.requireNonNull(rb);
        return sb.a(iA, abstractC3472z00, new KB$$ExternalSyntheticLambda0(rb));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public C3332x4 a(int i, int i2) {
        if (i2 >= i) {
            h();
            return this.i;
        }
        if (n || this.h.a(i2)) {
            return c(i2);
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public C3406y2 a(int i, C0767v2 c0767v2) {
        AbstractC3472z00 abstractC3472z00C = c0767v2.c(this.c);
        SB sb = this.e;
        RB rb = this.d;
        Objects.requireNonNull(rb);
        C3406y2 c3406y2 = new C3406y2(sb.a(i, abstractC3472z00C, new KB$$ExternalSyntheticLambda0(rb)), i, c0767v2.N0());
        boolean z = n;
        if (!z && this.i == null) {
            throw new AssertionError();
        }
        if (!z) {
            AbstractC1915eM abstractC1915eM = this.k;
            abstractC1915eM.getClass();
            if (!(abstractC1915eM instanceof AbstractC1915eM.c)) {
                throw new AssertionError();
            }
        }
        c3406y2.b(this.k);
        this.i.p().add(c3406y2);
        c3406y2.a(this.i);
        return c3406y2;
    }

    @Override // com.android.tools.r8.internal.ZB
    public final void a(int i) {
        a((AbstractC2412ko) new C0978Fc(a((AbstractC3472z00) AbstractC3472z00.k()), i));
    }

    @Override // com.android.tools.r8.internal.ZB
    public final void a(EnumC2527mI enumC2527mI, Object obj, Object obj2) {
        a((AbstractC2412ko) new H1(enumC2527mI, a((AbstractC3472z00) AbstractC3472z00.k()), f(obj), f(obj2)));
    }

    @Override // com.android.tools.r8.internal.ZB
    public final void a(C0759u2 c0759u2) {
        C0702m c0702m = this.c;
        int i = AbstractC3472z00.b;
        a((AbstractC2412ko) new C1033Hc(a((AbstractC3472z00) AbstractC3472z00.a(c0702m.a().X1, WH.i(), (C0702m<?>) c0702m).b()), c0759u2));
    }

    @Override // com.android.tools.r8.internal.ZB
    public final void a(EnumC2527mI enumC2527mI, EnumC2527mI enumC2527mI2, Object obj) {
        a((AbstractC2412ko) new C1831dI(enumC2527mI, enumC2527mI2, a(enumC2527mI2.a(this.c.a()).c(this.c)), f(obj)));
    }

    @Override // com.android.tools.r8.internal.ZB
    public final void a(EnumC1076Il enumC1076Il, int i, Object obj) {
        C3332x4 c3332x4C = c(i);
        a((AbstractC2412ko) new C1015Gl(enumC1076Il, f(obj)));
        this.i.h(c3332x4C);
        this.i.h(c(this.j));
        this.i = null;
    }

    @Override // com.android.tools.r8.internal.ZB
    public final void a(C0684j2 c0684j2, ArrayList arrayList, boolean z) {
        a((AbstractC2412ko) new C1166Ls(c0684j2, c0684j2.I0().Y0() ? null : a(c0684j2.I0().c(this.c)), a(arrayList), z));
    }

    @Override // com.android.tools.r8.internal.ZB
    public final void a(C0684j2 c0684j2, ArrayList arrayList) {
        a((AbstractC2412ko) new C1248Os(c0684j2, c0684j2.I0().Y0() ? null : a(c0684j2.I0().c(this.c)), a(arrayList)));
    }

    @Override // com.android.tools.r8.internal.ZB
    public final void a(com.android.tools.r8.graph.Y0 y0) {
        a((AbstractC2412ko) new QW(y0, a(AbstractC3472z00.a(y0.getType(), WH.i(), (C0702m<?>) this.c))));
    }

    @Override // com.android.tools.r8.internal.ZB
    public final void a(com.android.tools.r8.graph.Y0 y0, Object obj) {
        a((AbstractC2412ko) new C1189Mn(y0, a(AbstractC3472z00.a(y0.getType(), WH.i(), (C0702m<?>) this.c)), f(obj)));
    }

    @Override // com.android.tools.r8.internal.ZB
    public final void a(com.android.tools.r8.graph.Y0 y0, Object obj, Object obj2) {
        a((AbstractC2412ko) new C1793co(y0, f(obj), f(obj2)));
    }

    @Override // com.android.tools.r8.internal.ZB
    public final void a(C0767v2 c0767v2, Object obj) {
        a((AbstractC2412ko) new AG(a(c0767v2.c(this.c)), f(obj), c0767v2));
    }

    @Override // com.android.tools.r8.internal.ZB
    public final void a(Object obj) {
        a((AbstractC2412ko) new Z2(a((AbstractC3472z00) AbstractC3472z00.k()), f(obj)));
    }

    @Override // com.android.tools.r8.internal.ZB
    public final void a(C0767v2 c0767v2, ArrayList arrayList) {
        ML mlB = b(c0767v2.c(this.c));
        ArrayList arrayList2 = new ArrayList(arrayList.size());
        for (int i = 0; i < arrayList.size(); i++) {
            arrayList2.add(f(arrayList.get(i)));
        }
        mlB.a((List<S10>) arrayList2);
    }

    @Override // com.android.tools.r8.internal.ZB
    public final void a(C0767v2 c0767v2) {
        a((AbstractC2412ko) new AF(a(c0767v2.c(this.c)), c0767v2, this.c.Y()));
    }

    @Override // com.android.tools.r8.internal.ZB
    public final void a(int i, Object obj, Object obj2) {
        EnumC2527mI enumC2527mI;
        EnumC2246ib enumC2246ib;
        switch (i) {
            case 148:
                enumC2527mI = EnumC2527mI.LONG;
                enumC2246ib = EnumC2246ib.NONE;
                break;
            case 149:
                enumC2527mI = EnumC2527mI.FLOAT;
                enumC2246ib = EnumC2246ib.LT;
                break;
            case 150:
                enumC2527mI = EnumC2527mI.FLOAT;
                enumC2246ib = EnumC2246ib.GT;
                break;
            case 151:
                enumC2527mI = EnumC2527mI.DOUBLE;
                enumC2246ib = EnumC2246ib.LT;
                break;
            case 152:
                enumC2527mI = EnumC2527mI.DOUBLE;
                enumC2246ib = EnumC2246ib.GT;
                break;
            default:
                throw new C3405y10("Unexpected cmp opcode: " + i);
        }
        a((AbstractC2412ko) new C2318jb(enumC2527mI, enumC2246ib, a((AbstractC3472z00) AbstractC3472z00.k()), f(obj), f(obj2)));
    }
}
