package com.android.tools.r8.internal;

import com.android.dex.DexFormat;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.shaking.C3804u;
import io.netty.util.internal.StringUtil;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.x4, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C3332x4 {
    static final /* synthetic */ boolean p = true;
    private InterfaceC0992Fq a;
    private final ArrayList b = new ArrayList();
    private final ArrayList c = new ArrayList();
    private Set d = null;
    private C3038t6 e = C3038t6.d;
    private LinkedList f = new LinkedList();
    private int g = -1;
    private ArrayList h = new ArrayList();
    private boolean i = false;
    private boolean j = false;
    private final HashMap k = new HashMap();
    private int l = 0;
    private int m = 0;
    private int n = 0;
    private HashMap o = new HashMap();

    private void S() {
        Set set = this.d;
        if (set != null) {
            set.forEach(new Consumer() { // from class: com.android.tools.r8.internal.x4$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.b((InterfaceC3105u4) obj);
                }
            });
        }
    }

    private void T() {
        Set set = this.d;
        if (set != null) {
            set.forEach(new Consumer() { // from class: com.android.tools.r8.internal.x4$$ExternalSyntheticLambda7
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.c((InterfaceC3105u4) obj);
                }
            });
        }
    }

    public static boolean a(C2179hn c2179hn, boolean z) {
        if (!z) {
            return true;
        }
        boolean z2 = p;
        if (!z2 && !c2179hn.d1()) {
            throw new AssertionError();
        }
        if (!z2 && c2179hn.b().z()) {
            throw new AssertionError();
        }
        if (!z2 && c2179hn.b().C()) {
            throw new AssertionError();
        }
        if (z2 || c2179hn.b().h0().stream().allMatch(new Predicate() { // from class: com.android.tools.r8.internal.x4$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((AbstractC2412ko) obj).s2();
            }
        })) {
            return true;
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(InterfaceC3105u4 interfaceC3105u4) {
        ((C0827Af) interfaceC3105u4).e();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c(InterfaceC3105u4 interfaceC3105u4) {
        ((C0827Af) interfaceC3105u4).f();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String j(C3332x4 c3332x4) {
        if (!c(c3332x4)) {
            return "";
        }
        if (!p && !c(c3332x4)) {
            throw new AssertionError();
        }
        int iIndexOf = this.b.indexOf(c3332x4);
        Iterator it = this.e.a().iterator();
        int i = 0;
        while (it.hasNext()) {
            if (((Integer) it.next()).intValue() == iIndexOf) {
                i++;
            }
        }
        if (p || i > 0) {
            return new String(new char[i]).replace(DexFormat.MAGIC_SUFFIX, "*");
        }
        throw new AssertionError();
    }

    public C3332x4 A() {
        if (p || H()) {
            return (C3332x4) com.android.tools.r8.utils.V1.b(this.b);
        }
        throw new AssertionError();
    }

    public final C3332x4 B() {
        if (p || J()) {
            return (C3332x4) this.c.get(0);
        }
        throw new AssertionError();
    }

    public final C3332x4 C() {
        if (p || K()) {
            return (C3332x4) this.b.get(0);
        }
        throw new AssertionError();
    }

    public boolean D() {
        if (p || this.e != null) {
            return !this.e.isEmpty();
        }
        throw new AssertionError();
    }

    public final boolean E() {
        return !this.k.isEmpty();
    }

    public final boolean F() {
        return this.b.size() == 1 && m().M1();
    }

    public final boolean G() {
        return !this.h.isEmpty();
    }

    public final boolean H() {
        return W() == 1;
    }

    public final boolean I() {
        return H() && A().y().size() == 1;
    }

    public final boolean J() {
        return this.c.size() == 1;
    }

    public final boolean K() {
        return this.b.size() == 1;
    }

    public final void L() {
        this.m++;
        this.l++;
    }

    public final boolean M() {
        return this.f.isEmpty();
    }

    public final boolean N() {
        return this.i;
    }

    public final boolean O() {
        return m().u2();
    }

    public final boolean P() {
        return this.j;
    }

    public boolean Q() {
        return this.f.size() == 1 && m().M1();
    }

    public InterfaceC2488lo R() {
        return new C3479z4(this);
    }

    public final int U() {
        return this.e.size();
    }

    public final int V() {
        if (D()) {
            return this.e.c().size();
        }
        return 0;
    }

    public final int W() {
        return D() ? this.b.size() - this.e.c().size() : this.b.size();
    }

    public final void X() {
        if (!D()) {
            this.b.clear();
            return;
        }
        C2732or c2732or = new C2732or(16);
        Set setC = this.e.c();
        for (int i = 0; i < this.b.size(); i++) {
            if (!setC.contains(Integer.valueOf(i))) {
                c2732or.a(i);
            }
        }
        a(c2732or);
    }

    public final void Y() {
        this.i = true;
    }

    public void Z() {
        this.i = true;
    }

    public final int a0() {
        return this.f.size();
    }

    public final String b0() {
        String str;
        String strZ0;
        StringBuilder sb = new StringBuilder();
        sb.append("block ");
        sb.append(this.g);
        sb.append(", pred-counts: " + this.c.size());
        int i = this.m;
        if (i > 0) {
            sb.append(" (" + i + " unfilled)");
        }
        sb.append(", succ-count: " + this.b.size());
        sb.append(", filled: " + this.i);
        sb.append(", sealed: " + this.j);
        sb.append("\npredecessors: ");
        ArrayList<C3332x4> arrayList = this.c;
        Function function = new Function() { // from class: com.android.tools.r8.internal.x4$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C3332x4.g((C3332x4) obj);
            }
        };
        if (arrayList.size() > 0) {
            for (C3332x4 c3332x4 : arrayList) {
                sb.append(c3332x4.w());
                sb.append((String) function.apply(c3332x4));
                sb.append(StringUtil.SPACE);
            }
        } else {
            sb.append('-');
        }
        sb.append("\nsuccessors: ");
        ArrayList<C3332x4> arrayList2 = this.b;
        Function function2 = new Function() { // from class: com.android.tools.r8.internal.x4$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.j((C3332x4) obj);
            }
        };
        if (arrayList2.size() > 0) {
            for (C3332x4 c3332x5 : arrayList2) {
                sb.append(c3332x5.w());
                sb.append((String) function2.apply(c3332x5));
                sb.append(StringUtil.SPACE);
            }
        } else {
            sb.append('-');
        }
        if (this.b.size() > 0) {
            sb.append(" (");
            if (D()) {
                sb.append(this.e.size());
            } else {
                sb.append("no");
            }
            sb.append(" try/catch successors)");
        }
        sb.append('\n');
        ArrayList arrayList3 = this.h;
        if (arrayList3 == null || arrayList3.size() <= 0) {
            sb.append("no phis\n");
        } else {
            for (ML ml : this.h) {
                sb.append(ml.m0());
                if (this.k.values().contains(ml)) {
                    sb.append(" (incomplete)");
                }
                sb.append('\n');
            }
        }
        if (this.a != null) {
            sb.append("locals: ");
            com.android.tools.r8.utils.Y2.a(sb, this.a.c(), ", ", com.android.tools.r8.utils.Y2.a.NONE);
            sb.append('\n');
        }
        int iMax = 0;
        int iMax2 = 0;
        for (AbstractC2412ko abstractC2412ko : this.f) {
            iMax2 = Math.max(iMax2, abstractC2412ko.Z0().length());
            iMax = Math.max(iMax, (int) Math.ceil(Math.log10(abstractC2412ko.W0() + 1)));
        }
        String str2 = null;
        for (AbstractC2412ko abstractC2412ko2 : this.f) {
            if (iMax2 > 0) {
                if (abstractC2412ko2.Z0().equals(str2)) {
                    str = str2;
                    strZ0 = "";
                } else {
                    strZ0 = abstractC2412ko2.Z0();
                    str = strZ0;
                }
                com.android.tools.r8.utils.Y2.a(sb, strZ0, iMax2 + 1);
                sb.append(": ");
                str2 = str;
            }
            com.android.tools.r8.utils.Y2.a(sb, new StringBuilder().append(abstractC2412ko2.W0()).toString(), iMax + 1);
            sb.append(": ");
            sb.append(abstractC2412ko2.toString());
            int i2 = com.android.tools.r8.graph.X.d;
            if (!abstractC2412ko2.S0().isEmpty()) {
                sb.append(" [end: ");
                com.android.tools.r8.utils.Y2.a(sb, abstractC2412ko2.S0(), ", ", com.android.tools.r8.utils.Y2.a.NONE);
                sb.append("]");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public final void c0() {
        if (!p && this.c.size() != 1) {
            throw new AssertionError();
        }
        ((C3332x4) this.c.get(0)).k(this);
        r().clear();
    }

    public final boolean d(C3332x4 c3332x4) {
        for (int size = this.b.size() - W(); size < this.b.size(); size++) {
            if (this.b.get(size) == c3332x4) {
                return true;
            }
        }
        return false;
    }

    public final C3332x4 d0() {
        boolean z = p;
        if (!z && this.c.size() != 1) {
            throw new AssertionError();
        }
        if (!z && ((C3332x4) this.c.get(0)).b.size() != 1) {
            throw new AssertionError();
        }
        C3332x4 c3332x4 = (C3332x4) this.c.get(0);
        c3332x4.s().clear();
        r().clear();
        return c3332x4;
    }

    public final void e() {
        this.h = null;
        this.f.forEach(new Consumer() { // from class: com.android.tools.r8.internal.x4$$ExternalSyntheticLambda12
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                AbstractC2412ko.a((AbstractC2412ko) obj);
            }
        });
    }

    public final void e0() {
        boolean z = p;
        if (!z && this.c.size() != 1) {
            throw new AssertionError();
        }
        if (!z && !((C3332x4) this.c.get(0)).b.contains(this)) {
            throw new AssertionError();
        }
        r().get(0).s().remove(this);
        r().clear();
    }

    public final int f(int i) {
        Iterator it = this.f.iterator();
        while (it.hasNext()) {
            ((AbstractC2412ko) it.next()).b(i);
            i += 2;
        }
        return i;
    }

    public final C3332x4 f0() {
        boolean z = p;
        if (!z && D()) {
            throw new AssertionError();
        }
        if (!z && this.b.size() != 1) {
            throw new AssertionError();
        }
        if (!z && ((C3332x4) this.b.get(0)).c.size() != 1) {
            throw new AssertionError();
        }
        C3332x4 c3332x4 = (C3332x4) this.b.get(0);
        c3332x4.r().clear();
        s().clear();
        return c3332x4;
    }

    public void g(int i) {
        if (!p && i < 0) {
            throw new AssertionError();
        }
        this.g = i;
    }

    public final void g0() {
        boolean z = p;
        if (!z && this.l != this.c.size()) {
            throw new AssertionError();
        }
        if (!z && this.m != 0) {
            throw new AssertionError();
        }
    }

    public final void h(C3332x4 c3332x4) {
        boolean z = p;
        if (!z && this.b.contains(c3332x4)) {
            throw new AssertionError();
        }
        if (!z && c3332x4.c.contains(this)) {
            throw new AssertionError();
        }
        s().add(c3332x4);
        c3332x4.r().add(this);
    }

    public final void i() {
        Iterator it = this.b.iterator();
        while (it.hasNext()) {
            ((C3332x4) it.next()).r().remove(this);
        }
        s().clear();
    }

    public final void k(C3332x4 c3332x4) {
        int iIndexOf = this.b.indexOf(c3332x4);
        if (!p && iIndexOf < 0) {
            throw new AssertionError("removeSuccessor did not find the successor to remove");
        }
        a(new C2732or(new int[]{iIndexOf}, 1, 0));
    }

    public final AbstractC2412ko l() {
        if (!p && !D()) {
            throw new AssertionError();
        }
        C3479z4 c3479z4 = new C3479z4(this.f.size(), this);
        while (c3479z4.hasPrevious()) {
            AbstractC2412ko abstractC2412koPrevious = c3479z4.previous();
            if (abstractC2412koPrevious.g()) {
                return abstractC2412koPrevious;
            }
        }
        return null;
    }

    public AbstractC1954eu m() {
        boolean z = p;
        if (!z && !this.i) {
            throw new AssertionError();
        }
        if (!z) {
            LinkedList linkedList = this.f;
            if (!((AbstractC2412ko) linkedList.get(linkedList.size() - 1)).g2()) {
                throw new AssertionError();
            }
        }
        LinkedList linkedList2 = this.f;
        return ((AbstractC2412ko) linkedList2.get(linkedList2.size() - 1)).j0();
    }

    public final C3038t6 n() {
        if (!D()) {
            return C3038t6.e;
        }
        List listA = this.e.a();
        final ArrayList arrayList = this.b;
        Objects.requireNonNull(arrayList);
        return new C3038t6(this.e.b(), com.android.tools.r8.utils.V1.a((Collection) listA, new Function() { // from class: com.android.tools.r8.internal.x4$$ExternalSyntheticLambda10
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return (C3332x4) arrayList.get(((Integer) obj).intValue());
            }
        }));
    }

    public final C3038t6 o() {
        return this.e;
    }

    public LinkedList<AbstractC2412ko> p() {
        return this.f;
    }

    public final InterfaceC0992Fq q() {
        return this.a;
    }

    public List<C3332x4> r() {
        if (!p) {
            S();
        }
        return this.c;
    }

    public List<C3332x4> s() {
        if (!p) {
            T();
        }
        return this.b;
    }

    public final AbstractC1639am t() {
        C1507Xl c1507XlG = AbstractC1639am.g();
        for (C3332x4 c3332x4 : this.c) {
            if (!c3332x4.c(this)) {
                c1507XlG.a(c3332x4);
            }
        }
        return c1507XlG.a();
    }

    public final String toString() {
        return b0();
    }

    public final List u() {
        if (!D()) {
            return this.b;
        }
        final C1507Xl c1507XlG = AbstractC1639am.g();
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.x4$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                c1507XlG.a((C3332x4) obj);
            }
        };
        for (int size = this.b.size() - W(); size < this.b.size(); size++) {
            consumer.accept((C3332x4) this.b.get(size));
        }
        return c1507XlG.a();
    }

    public final int v() {
        if (p || this.g >= 0) {
            return this.g;
        }
        throw new AssertionError();
    }

    public final String w() {
        int i = this.g;
        return i >= 0 ? new StringBuilder().append(i).toString() : "<unknown>";
    }

    public List<ML> x() {
        return this.h;
    }

    public List<C3332x4> y() {
        return Collections.unmodifiableList(this.c);
    }

    public List<C3332x4> z() {
        return Collections.unmodifiableList(this.b);
    }

    /* JADX WARN: Code duplicated, block: B:61:0x0142  */
    public final void b(C3332x4 c3332x4, C3332x4 c3332x5) {
        if (!p && !this.b.contains(c3332x4)) {
            throw new AssertionError("attempt to replace non-existent successor");
        }
        int i = 0;
        if (!this.b.contains(c3332x5)) {
            while (i < this.b.size()) {
                if (this.b.get(i) == c3332x4) {
                    s().set(i, c3332x5);
                    return;
                }
                i++;
            }
            return;
        }
        int iIndexOf = this.b.indexOf(c3332x4);
        int iIndexOf2 = this.b.indexOf(c3332x5);
        if (D()) {
            ArrayList arrayList = new ArrayList(this.e.a());
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                if (((Integer) arrayList.get(i2)).intValue() == iIndexOf) {
                    arrayList.set(i2, Integer.valueOf(iIndexOf2));
                }
                if (((Integer) arrayList.get(i2)).intValue() > iIndexOf) {
                    arrayList.set(i2, Integer.valueOf(((Integer) arrayList.get(i2)).intValue() - 1));
                }
            }
            this.e = new C3038t6(this.e.b(), arrayList);
        }
        if (m().M1()) {
            if (iIndexOf == this.b.size() - 1 && iIndexOf2 != this.b.size() - 2) {
                a(iIndexOf - 1, iIndexOf2);
            }
        } else if (m().N1()) {
            if (iIndexOf2 >= this.b.size() - 2 && iIndexOf >= this.b.size() - 2) {
                AbstractC2412ko abstractC2412koRemoveLast = p().removeLast();
                for (int size = abstractC2412koRemoveLast.b.size() - 1; size >= 0; size--) {
                    S10 s10 = (S10) abstractC2412koRemoveLast.b.get(size);
                    if (s10.T()) {
                        if (s10.i()) {
                            if (p) {
                            }
                            ZL zl = new ZL(s10);
                            zl.a(this);
                            zl.b(abstractC2412koRemoveLast.Y0());
                            p().addLast(zl);
                        } else {
                            AbstractC2412ko abstractC2412ko = s10.b;
                            abstractC2412ko.getClass();
                            if ((abstractC2412ko instanceof C3044tC) && s10.b.h() == this) {
                                if (!p) {
                                    C3332x4 c3332x4H = s10.b.h();
                                    C3332x4 c3332x6 = this;
                                    while (c3332x6 != c3332x4H) {
                                        if (c3332x6.y().size() == 1) {
                                            C3332x4 c3332x7 = c3332x6.y().get(0);
                                            if (c3332x7.m().M1() && AbstractC2967s4.a(c3332x7) == c3332x6) {
                                                c3332x6 = c3332x7;
                                            }
                                        }
                                        throw new AssertionError();
                                    }
                                }
                                s10.b.h().g(s10.b);
                            } else {
                                if (p && (s10 instanceof C2708oW)) {
                                    throw new AssertionError();
                                }
                                ZL zl2 = new ZL(s10);
                                zl2.a(this);
                                zl2.b(abstractC2412koRemoveLast.Y0());
                                p().addLast(zl2);
                            }
                        }
                    }
                    if (s10.F()) {
                        s10.e(abstractC2412koRemoveLast);
                    }
                }
                C1296Qj c1296Qj = new C1296Qj();
                c1296Qj.a(this);
                c1296Qj.b(abstractC2412koRemoveLast.Y0());
                p().addLast(c1296Qj);
            } else if (iIndexOf >= this.b.size() - 2) {
                a(iIndexOf - 1, iIndexOf2);
            }
        } else if (m().A2()) {
            IY iyK0 = m().K0();
            if (iyK0.K2() == iIndexOf) {
                iyK0.d(iIndexOf2);
            }
            if (iyK0.K2() > iIndexOf) {
                iyK0.d(iyK0.K2() - 1);
            }
            int[] iArrM2 = iyK0.M2();
            while (i < iArrM2.length) {
                if (iArrM2[i] == iIndexOf) {
                    iArrM2[i] = iIndexOf2;
                }
                int i3 = iArrM2[i];
                if (i3 > iIndexOf) {
                    iArrM2[i] = i3 - 1;
                }
                i++;
            }
        } else if (!p && !m().u2() && !m().B2()) {
            throw new AssertionError();
        }
        boolean zRemove = s().remove(c3332x4);
        if (!p && !zRemove) {
            throw new AssertionError();
        }
    }

    public final void c(C3332x4 c3332x4, C3332x4 c3332x5) {
        boolean z = p;
        if (!z && c3332x4 == c3332x5) {
            throw new AssertionError();
        }
        int iIndexOf = this.b.indexOf(c3332x4);
        int iIndexOf2 = this.b.indexOf(c3332x5);
        if (!z && (iIndexOf < 0 || iIndexOf2 < 0)) {
            throw new AssertionError();
        }
        a(iIndexOf, iIndexOf2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Iterator f(AbstractC2412ko abstractC2412ko) {
        return new C3036t4(this, abstractC2412ko);
    }

    public final void d() {
        this.o = null;
        Iterator<ML> it = x().iterator();
        while (it.hasNext()) {
            it.next().j0();
        }
    }

    public final void e(int i) {
        boolean z = p;
        if (!z && i == 0) {
            throw new AssertionError();
        }
        if (!z && c(i)) {
            throw new AssertionError();
        }
        this.n |= i;
        if (!z && !c(i)) {
            throw new AssertionError();
        }
    }

    public final void g() {
        this.m--;
        this.l--;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void f(C3332x4 c3332x4) {
        boolean z = p;
        if (!z && c3332x4.c.size() != 0) {
            throw new AssertionError();
        }
        if (!z && c3332x4.b.size() != 0) {
            throw new AssertionError();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String g(C3332x4 c3332x4) {
        return "";
    }

    public final void a(final C0702m c0702m, final C2433l20 c2433l20) {
        if (!p && !this.f.stream().allMatch(new Predicate() { // from class: com.android.tools.r8.internal.x4$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C3332x4.a(c0702m, c2433l20, (AbstractC2412ko) obj);
            }
        })) {
            throw new AssertionError();
        }
    }

    public final void h() {
        C3257w4 c3257w4 = new C3257w4();
        HashMap map = new HashMap();
        Iterator it = this.h.iterator();
        while (it.hasNext()) {
            ML ml = (ML) it.next();
            C2173hh c2173hhC = c3257w4.c(ml);
            ML ml2 = (ML) map.get(c2173hhC);
            if (ml2 == null) {
                map.put(c2173hhC, ml);
            } else {
                if (ml.s() != ml2.s()) {
                    if (ml2.s() == null) {
                        ml2.a(ml.s());
                    } else if (ml.s() != null) {
                        if (!p && (!ml.A() || !ml2.A())) {
                            throw new AssertionError();
                        }
                    }
                }
                ml.g(ml2);
                Iterator<S10> it2 = ml.k0().iterator();
                while (it2.hasNext()) {
                    it2.next().c(ml);
                }
                it.remove();
            }
        }
    }

    public final void i(C3332x4 c3332x4) {
        for (C3332x4 c3332x5 : a(c3332x4)) {
            c3332x4.s().remove(c3332x5);
            c3332x5.a(c3332x4, (Set) null);
        }
        c3332x4.e = C3038t6.d;
    }

    public final C3479z4 d(int i) {
        return new C3479z4(i, this);
    }

    public final void f() {
        if (D()) {
            boolean z = p;
            if (!z && !m().M1() && !m().B2()) {
                throw new AssertionError();
            }
            C3038t6 c3038t6 = this.e;
            if (!z && c3038t6.b().size() != AbstractC1048Hm.a(c3038t6.b()).size()) {
                throw new AssertionError();
            }
            List listB = c3038t6.b();
            int size = listB.size() - 1;
            for (int i = 0; i < listB.size(); i++) {
                if (!p && ((C0767v2) listB.get(i)).b1().equals("Ljava/lang/Throwable;") && i != size) {
                    throw new AssertionError();
                }
            }
            ArrayList arrayList = new ArrayList(c3038t6.a());
            arrayList.sort(Comparator.naturalOrder());
            int iIntValue = ((Integer) arrayList.get(0)).intValue();
            int iIntValue2 = ((Integer) arrayList.get(arrayList.size() - 1)).intValue();
            boolean z2 = p;
            if (!z2 && iIntValue != 0) {
                throw new AssertionError();
            }
            if (!z2 && iIntValue2 >= arrayList.size()) {
                throw new AssertionError();
            }
            int size2 = z().size();
            int i2 = size2 - 1;
            if (!z2 && iIntValue2 != i2 && iIntValue2 != size2 - 2) {
                throw new AssertionError();
            }
            if (!z2 && iIntValue2 != i2 && m().B2()) {
                throw new AssertionError();
            }
        }
    }

    public final void g(AbstractC2412ko abstractC2412ko) {
        int iIndexOf = this.f.indexOf(abstractC2412ko);
        if (!p && iIndexOf < 0) {
            throw new AssertionError();
        }
        List<Integer> listSingletonList = Collections.singletonList(Integer.valueOf(iIndexOf));
        if (listSingletonList.isEmpty()) {
            return;
        }
        LinkedList linkedList = new LinkedList();
        int iIntValue = 0;
        for (Integer num : listSingletonList) {
            if (!p && num.intValue() < iIntValue) {
                throw new AssertionError();
            }
            linkedList.addAll(this.f.subList(iIntValue, num.intValue()));
            ((AbstractC2412ko) this.f.get(num.intValue())).O0();
            iIntValue = num.intValue() + 1;
        }
        if (iIntValue < this.f.size()) {
            LinkedList linkedList2 = this.f;
            linkedList.addAll(linkedList2.subList(iIntValue, linkedList2.size()));
        }
        if (!p) {
            if (this.f.size() != listSingletonList.size() + linkedList.size()) {
                throw new AssertionError();
            }
        }
        this.f = linkedList;
    }

    public final AbstractC2412ko k() {
        return (AbstractC2412ko) this.f.get(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Iterator e(AbstractC2412ko abstractC2412ko) {
        return new C3479z4(this, abstractC2412ko);
    }

    public final boolean c(int i) {
        if (p || i != 0) {
            return (i & this.n) != 0;
        }
        throw new AssertionError();
    }

    public final C3479z4 d(AbstractC2412ko abstractC2412ko) {
        return new C3479z4(this, abstractC2412ko);
    }

    public final void l(C3332x4 c3332x4) {
        this.e = c3332x4.e;
        c3332x4.e = C3038t6.d;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C0702m c0702m, C2433l20 c2433l20, AbstractC2412ko abstractC2412ko) {
        abstractC2412ko.a(c0702m, c2433l20);
        return true;
    }

    public final boolean e(C3332x4 c3332x4) {
        Set setC = OU.c();
        ArrayDeque arrayDeque = new ArrayDeque();
        arrayDeque.push(this);
        while (!arrayDeque.isEmpty()) {
            C3332x4 c3332x5 = (C3332x4) arrayDeque.pop();
            if (c3332x5 == c3332x4) {
                return true;
            }
            setC.add(c3332x5);
            for (C3332x4 c3332x6 : c3332x5.z()) {
                if (!setC.contains(c3332x6)) {
                    arrayDeque.push(c3332x6);
                }
            }
        }
        return false;
    }

    public final void a(C1303Qq c1303Qq) {
        this.a = c1303Qq;
    }

    public boolean c(C3332x4 c3332x4) {
        if (D()) {
            return this.e.c().contains(Integer.valueOf(this.b.indexOf(c3332x4)));
        }
        return false;
    }

    public final void a(C2409kl c2409kl, AbstractC1954eu abstractC1954eu) {
        InterfaceC2641no interfaceC2641noA = a(c2409kl, p().size());
        interfaceC2641noA.previous();
        interfaceC2641noA.d(abstractC1954eu);
    }

    public final com.android.tools.r8.utils.D3 a(AbstractC3327x1 abstractC3327x1, BiFunction biFunction) {
        com.android.tools.r8.utils.D3 d3B = com.android.tools.r8.utils.D3.b(abstractC3327x1);
        for (C3332x4 c3332x4 : y()) {
            if (!c3332x4.c(this)) {
                d3B = (com.android.tools.r8.utils.D3) biFunction.apply(c3332x4, d3B.c().j());
                if (d3B.f()) {
                    break;
                }
            }
        }
        return d3B;
    }

    public final boolean c(AbstractC2412ko abstractC2412ko) {
        if (!p && abstractC2412ko.h() != this) {
            throw new AssertionError();
        }
        for (AbstractC2412ko abstractC2412ko2 : p()) {
            if (abstractC2412ko2 == abstractC2412ko) {
                return true;
            }
            if (abstractC2412ko2.g()) {
                return false;
            }
        }
        throw new C3405y10();
    }

    public final C3332x4 j() {
        boolean z = false;
        C3332x4 c3332x4A = this;
        C3332x4 c3332x4A2 = c3332x4A;
        while (c3332x4A.Q()) {
            c3332x4A = AbstractC2967s4.a(c3332x4A);
            if (z) {
                c3332x4A2 = AbstractC2967s4.a(c3332x4A2);
            }
            z = !z;
            if (c3332x4A == c3332x4A2) {
                return null;
            }
        }
        return c3332x4A;
    }

    public final com.android.tools.r8.utils.D3 a(BiFunction biFunction) {
        com.android.tools.r8.utils.D3 d3B = com.android.tools.r8.utils.D3.b(null);
        for (int size = this.b.size() - W(); size < this.b.size(); size++) {
            d3B = (com.android.tools.r8.utils.D3) biFunction.apply(this.b.get(size), d3B.c().j());
            if (d3B.f()) {
                break;
            }
        }
        return d3B;
    }

    public final void c() {
        this.e = C3038t6.d;
    }

    public final com.android.tools.r8.utils.D3 a(Object obj, BiFunction biFunction) {
        com.android.tools.r8.utils.D3 d3B = com.android.tools.r8.utils.D3.b(obj);
        for (C3332x4 c3332x4 : y()) {
            if (c3332x4.c(this)) {
                d3B = (com.android.tools.r8.utils.D3) biFunction.apply(c3332x4, d3B.c().j());
                if (d3B.f()) {
                    break;
                }
            }
        }
        return d3B;
    }

    public final com.android.tools.r8.utils.D3 a(com.android.tools.r8.utils.G3 g3, Object obj) {
        int iV = V();
        com.android.tools.r8.utils.D3 d3B = com.android.tools.r8.utils.D3.b(obj);
        for (int i = 0; i < iV; i++) {
            d3B = (com.android.tools.r8.utils.D3) g3.a(this.b.get(i), this.e.j(i), d3B.c().j());
            if (d3B.f()) {
                break;
            }
        }
        return d3B;
    }

    public final void a(InterfaceC3105u4 interfaceC3105u4) {
        if (this.d == null) {
            this.d = Collections.newSetFromMap(new WeakHashMap());
        }
        this.d.add(interfaceC3105u4);
    }

    public final void a(C3332x4 c3332x4, Set set) {
        int iIndexOf = this.c.indexOf(c3332x4);
        if (!p && iIndexOf < 0) {
            throw new AssertionError("removePredecessor did not find the predecessor to remove");
        }
        r().remove(iIndexOf);
        if (this.h != null) {
            Iterator<ML> it = x().iterator();
            while (it.hasNext()) {
                it.next().d(iIndexOf);
            }
            ArrayList arrayList = new ArrayList();
            for (ML ml : x()) {
                if (ml.l0()) {
                    arrayList.add(ml);
                    if (set != null) {
                        set.addAll(ml.a());
                    }
                }
            }
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                ((ML) it2.next()).a((C2102gl) null, set);
            }
        }
    }

    public final void a(int i, int i2) {
        boolean z = p;
        if (!z && i == i2) {
            throw new AssertionError();
        }
        if (D()) {
            ArrayList arrayList = new ArrayList(this.e.a());
            if (!z && arrayList.contains(Integer.valueOf(i)) != arrayList.contains(Integer.valueOf(i2))) {
                throw new AssertionError("Swapping normal successor and catch handler");
            }
            for (int i3 = 0; i3 < arrayList.size(); i3++) {
                if (((Integer) arrayList.get(i3)).intValue() == i) {
                    arrayList.set(i3, Integer.valueOf(i2));
                } else if (((Integer) arrayList.get(i3)).intValue() == i2) {
                    arrayList.set(i3, Integer.valueOf(i));
                }
            }
            this.e = new C3038t6(this.e.b(), arrayList);
        }
        List<C3332x4> listS = s();
        C3332x4 c3332x4 = listS.get(i);
        listS.set(i, listS.get(i2));
        listS.set(i2, c3332x4);
    }

    public final void a(C3332x4 c3332x4, C3332x4 c3332x5) {
        for (int i = 0; i < this.c.size(); i++) {
            if (this.c.get(i) == c3332x4) {
                if (!p) {
                    S();
                }
                r().set(i, c3332x5);
                return;
            }
        }
        if (!p) {
            throw new AssertionError("replaceSuccessor did not find the predecessor to replace");
        }
    }

    public final void a(C2732or c2732or) {
        if (c2732or.isEmpty()) {
            return;
        }
        if (!p) {
            for (int size = c2732or.size() - 1; size > 0; size--) {
                if (Integer.valueOf(c2732or.i(size)).compareTo(Integer.valueOf(c2732or.i(size - 1))) < 0) {
                    throw new AssertionError();
                }
            }
        }
        List<C3332x4> listS = s();
        ArrayList arrayList = new ArrayList(listS);
        listS.clear();
        InterfaceC0841Ar interfaceC0841ArListIterator = c2732or.listIterator(0);
        int i = 0;
        while (interfaceC0841ArListIterator.hasNext()) {
            int iIntValue = Integer.valueOf(((K) interfaceC0841ArListIterator).t()).intValue();
            listS.addAll(arrayList.subList(i, iIntValue));
            i = iIntValue + 1;
        }
        listS.addAll(arrayList.subList(i, arrayList.size()));
        if (D()) {
            List listA = this.e.a();
            List listB = this.e.b();
            int size2 = this.e.size();
            ArrayList arrayList2 = new ArrayList(size2);
            ArrayList arrayList3 = new ArrayList(size2);
            for (int i2 = 0; i2 < listA.size(); i2++) {
                int iIntValue2 = ((Integer) listA.get(i2)).intValue();
                InterfaceC0841Ar interfaceC0841ArListIterator2 = c2732or.listIterator(0);
                int i3 = 0;
                while (true) {
                    if (interfaceC0841ArListIterator2.hasNext()) {
                        int iIntValue3 = Integer.valueOf(((K) interfaceC0841ArListIterator2).t()).intValue();
                        if (iIntValue2 == iIntValue3) {
                            break;
                        } else if (iIntValue2 >= iIntValue3) {
                            i3++;
                        }
                    }
                    arrayList3.add(Integer.valueOf(iIntValue2 - i3));
                    arrayList2.add((C0767v2) listB.get(i2));
                    break;
                }
            }
            if (arrayList3.isEmpty()) {
                this.e = C3038t6.d;
            } else {
                this.e = new C3038t6(arrayList2, arrayList3);
            }
        }
    }

    public final Iterable a(final AbstractC2412ko abstractC2412ko) {
        return new Iterable() { // from class: com.android.tools.r8.internal.x4$$ExternalSyntheticLambda9
            @Override // java.lang.Iterable
            public final Iterator iterator() {
                return this.f$0.e(abstractC2412ko);
            }
        };
    }

    public final void a(C2322jf c2322jf) {
        Iterator it = this.f.iterator();
        while (it.hasNext()) {
            ((AbstractC2412ko) it.next()).a(c2322jf);
        }
    }

    public final void a(int i) {
        boolean z = p;
        if (!z && i == 0) {
            throw new AssertionError();
        }
        this.n &= ~i;
        if (!z && c(i)) {
            throw new AssertionError();
        }
    }

    public final void a(ML ml) {
        this.h.add(ml);
    }

    public void a(AbstractC2412ko abstractC2412ko, C3155ul c3155ul) {
        if (!p && this.i) {
            throw new AssertionError();
        }
        this.f.add(abstractC2412ko);
        c3155ul.a(abstractC2412ko);
        abstractC2412ko.a(this);
    }

    public final void a(C2102gl c2102gl) {
        boolean z = p;
        if (!z && this.i) {
            throw new AssertionError();
        }
        if (!z && this.f.isEmpty()) {
            throw new AssertionError();
        }
        this.i = true;
        this.j = this.m == 0;
        if (!z) {
            m().getClass();
        }
        if (!z && D()) {
            C3479z4 c3479z4 = new C3479z4(this.f.size(), this);
            while (c3479z4.hasPrevious()) {
                AbstractC2412ko abstractC2412koPrevious = c3479z4.previous();
                if (abstractC2412koPrevious.g()) {
                    break;
                } else if (!p && abstractC2412koPrevious.b() != null) {
                    throw new AssertionError();
                }
            }
        }
        for (C3332x4 c3332x4 : this.b) {
            boolean z2 = p;
            if (!z2 && c3332x4.m <= 0) {
                throw new AssertionError();
            }
            int i = c3332x4.m - 1;
            c3332x4.m = i;
            if (i == 0) {
                if (!z2 && c3332x4.l != c3332x4.c.size()) {
                    throw new AssertionError();
                }
                for (Map.Entry entry : c3332x4.k.entrySet()) {
                    int iIntValue = ((Integer) entry.getKey()).intValue();
                    if (iIntValue < 0) {
                        iIntValue = -(iIntValue + 1);
                    }
                    ((ML) entry.getValue()).a(iIntValue, c2102gl);
                }
                c3332x4.j = true;
                c3332x4.k.clear();
            }
        }
    }

    private static void a(ArrayList arrayList) {
        arrayList.forEach(new Consumer() { // from class: com.android.tools.r8.internal.x4$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C3332x4.f((C3332x4) obj);
            }
        });
    }

    private void a(C0767v2 c0767v2) {
        int iIndexOf = this.e.b().indexOf(c0767v2);
        if (iIndexOf >= 0) {
            final int iIntValue = ((Integer) this.e.a().get(iIndexOf)).intValue();
            boolean z = p;
            if (!z && iIntValue < 0) {
                throw new AssertionError();
            }
            this.e = this.e.a(c0767v2);
            if (n().a().stream().noneMatch(new Predicate() { // from class: com.android.tools.r8.internal.x4$$ExternalSyntheticLambda4
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return this.f$0.a(iIntValue, (C3332x4) obj);
                }
            })) {
                s().remove(iIntValue);
            }
            if (z) {
                return;
            }
            f();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean a(int i, C3332x4 c3332x4) {
        return c3332x4 == this.b.get(i);
    }

    public final ArrayList a(C3332x4 c3332x4, C0827Af c0827Af, Set set) {
        boolean z = p;
        if (!z && set == null) {
            throw new AssertionError();
        }
        if (!z && !this.b.contains(c3332x4)) {
            throw new AssertionError();
        }
        if (!z && c3332x4.c.size() != 1) {
            throw new AssertionError();
        }
        if (!z && c3332x4.c.get(0) != this) {
            throw new AssertionError();
        }
        ArrayList arrayList = new ArrayList();
        for (C3332x4 c3332x5 : (List) c0827Af.a(c3332x4, new ArrayList())) {
            set.addAll(c3332x5.b());
            arrayList.add(c3332x5);
        }
        if (!p) {
            a(arrayList);
        }
        return arrayList;
    }

    public final void a(List list, List list2) {
        ArrayList arrayList = new ArrayList(list2.size());
        Iterator it = list2.iterator();
        while (it.hasNext()) {
            C3332x4 c3332x4 = (C3332x4) it.next();
            int iIndexOf = this.b.indexOf(c3332x4);
            if (iIndexOf < 0) {
                iIndexOf = this.b.size();
                h(c3332x4);
            }
            arrayList.add(Integer.valueOf(iIndexOf));
        }
        this.e = new C3038t6(list, arrayList);
    }

    public final void a(C3332x4 c3332x4, C0767v2 c0767v2) {
        int size;
        if (a()) {
            if (D()) {
                if (this.e.b().contains(c0767v2)) {
                    return;
                }
                int iIndexOf = this.b.indexOf(c3332x4);
                if (iIndexOf < 0) {
                    List<C3332x4> listS = s();
                    int size2 = listS.size();
                    int iW = W();
                    if (iW > 0) {
                        size = size2 - iW;
                        listS.add(size, c3332x4);
                    } else {
                        size = listS.size();
                        listS.add(c3332x4);
                    }
                    iIndexOf = size;
                    c3332x4.r().add(this);
                }
                this.e = this.e.a(c0767v2, Integer.valueOf(iIndexOf));
                return;
            }
            if (!p && this.f.stream().filter(new A4$$ExternalSyntheticLambda5()).count() != 1) {
                throw new AssertionError();
            }
            s().add(0, c3332x4);
            c3332x4.r().add(this);
            this.e = new C3038t6(AbstractC1639am.c(c0767v2), AbstractC1639am.c(0));
        }
    }

    public final boolean a(DH dh, AbstractC1476Wj abstractC1476Wj) {
        if (!p && !D()) {
            throw new AssertionError();
        }
        ArrayList arrayList = new ArrayList(this.e.b().size());
        boolean z = false;
        for (C0767v2 c0767v2 : this.e.b()) {
            C0767v2 c0767v2A = dh.a(abstractC1476Wj, c0767v2);
            arrayList.add(c0767v2A);
            z |= c0767v2A != c0767v2;
        }
        if (z) {
            this.e = new C3038t6(arrayList, this.e.a());
        }
        return z;
    }

    public final void a(int i, ML ml, EnumC3182v4 enumC3182v4) {
        if ((enumC3182v4 == EnumC3182v4.c ? (S10) this.o.get(Integer.valueOf(-(i + 1))) : null) != null) {
            i = -(i + 1);
        }
        if (!p && this.k.containsKey(Integer.valueOf(i))) {
            throw new AssertionError();
        }
        this.k.put(Integer.valueOf(i), ml);
    }

    public final S10 a(int i, EnumC3182v4 enumC3182v4) {
        S10 s10 = enumC3182v4 == EnumC3182v4.c ? (S10) this.o.get(Integer.valueOf(-(i + 1))) : null;
        if (s10 != null) {
            if (s10 == S10.o) {
                return null;
            }
            return s10;
        }
        return (S10) this.o.get(Integer.valueOf(i));
    }

    public final void a(int i, S10 s10, EnumC3182v4 enumC3182v4) {
        if ((enumC3182v4 == EnumC3182v4.c ? (S10) this.o.get(Integer.valueOf(-(i + 1))) : null) != null) {
            i = -(i + 1);
        }
        S10 s11 = (S10) this.o.get(Integer.valueOf(i));
        if (s10.i()) {
            s10.l().a(this.o);
        }
        if (!p && i < 0) {
            for (Integer num : this.o.keySet()) {
                if (!p && num.intValue() < 0 && num.intValue() != i) {
                    throw new AssertionError();
                }
            }
        }
        this.o.put(Integer.valueOf(i), s10);
        if (s11 == null || !s11.i() || this.o.values().contains(s11)) {
            return;
        }
        s11.l().b(this.o);
    }

    public final void a(int i, S10 s10, int i2) {
        if (i2 == 2) {
            S10 s11 = (S10) this.o.get(Integer.valueOf(i));
            if (!p && i < 0) {
                for (Integer num : this.o.keySet()) {
                    if (!p && num.intValue() < 0 && num.intValue() != i) {
                        throw new AssertionError();
                    }
                }
            }
            HashMap map = this.o;
            Integer numValueOf = Integer.valueOf(-(i + 1));
            if (s11 == null) {
                s11 = S10.o;
            }
            map.put(numValueOf, s11);
        }
        a(i, s10, EnumC3182v4.a);
    }

    public static C3332x4 a(int i, AbstractC1915eM abstractC1915eM, C3155ul c3155ul, C3332x4 c3332x4) {
        C3332x4 c3332x4A = a(i, abstractC1915eM, c3155ul);
        c3332x4A.s().add(c3332x4);
        return c3332x4A;
    }

    public static C3332x4 a(int i, AbstractC1915eM abstractC1915eM, C3155ul c3155ul) {
        C3332x4 c3332x4 = new C3332x4();
        c3332x4.a(new C1296Qj(), c3155ul);
        c3332x4.a((C2102gl) null);
        c3332x4.g(i);
        c3332x4.k().b(abstractC1915eM);
        return c3332x4;
    }

    public static C3332x4 a(C2409kl c2409kl, AbstractC1915eM abstractC1915eM, C0767v2 c0767v2, C0702m c0702m) {
        AbstractC3472z00 abstractC3472z00A = AbstractC3472z00.a(c0767v2, WH.b(), (C0702m<?>) c0702m);
        C3332x4 c3332x4 = new C3332x4();
        AF af = new AF(new S10(c2409kl.e.a(), abstractC3472z00A, null), c0767v2, c0702m.Y());
        af.b(abstractC1915eM);
        C1621aZ c1621aZ = new C1621aZ(af.a);
        c1621aZ.b(abstractC1915eM);
        c3332x4.a(af, c2409kl.t());
        c3332x4.a(c1621aZ, c2409kl.t());
        c3332x4.a((C2102gl) null);
        c3332x4.g(c2409kl.f.a());
        return c3332x4;
    }

    public boolean a() {
        Iterator it = this.f.iterator();
        while (it.hasNext()) {
            if (((AbstractC2412ko) it.next()).g()) {
                return true;
            }
        }
        return false;
    }

    public InterfaceC2641no a(C2409kl c2409kl) {
        return new A4(c2409kl.t(), this);
    }

    public final A4 a(C3155ul c3155ul) {
        return new A4(c3155ul, this);
    }

    public InterfaceC2641no a(C2409kl c2409kl, int i) {
        return new A4(c2409kl.t(), this, i);
    }

    public final A4 a(C2409kl c2409kl, AbstractC2412ko abstractC2412ko) {
        return new A4(c2409kl.t(), this, abstractC2412ko);
    }

    final C3332x4 a(int i, boolean z) {
        boolean zD = D();
        C3332x4 c3332x4 = new C3332x4();
        c3332x4.g(i);
        c3332x4.s().addAll(this.b);
        Iterator<C3332x4> it = c3332x4.z().iterator();
        while (it.hasNext()) {
            it.next().a(this, c3332x4);
        }
        s().clear();
        c3332x4.e = this.e;
        this.e = C3038t6.d;
        if (z && zD) {
            i(c3332x4);
        }
        h(c3332x4);
        c3332x4.i = true;
        c3332x4.j = true;
        return c3332x4;
    }

    public final void a(C2409kl c2409kl, ListIterator listIterator, C3332x4 c3332x4, com.android.tools.r8.utils.A1 a1) {
        C0767v2 c0767v2;
        AbstractC3472z00 abstractC3472z00A;
        AF af;
        C3038t6 c3038t6 = this.e;
        if (c3038t6 == null || !c3038t6.a(a1.a)) {
            Iterator it = a(c3332x4).iterator();
            while (it.hasNext()) {
                C3332x4 c3332x5 = (C3332x4) it.next();
                List<C3332x4> listR = c3332x5.r();
                AbstractC2412ko abstractC2412koK = c3332x5.k();
                abstractC2412koK.getClass();
                boolean z = abstractC2412koK instanceof AF;
                AbstractC1915eM abstractC1915eMY0 = c3332x5.k().Y0();
                if (z) {
                    AF afO0 = c3332x5.k().o0();
                    abstractC3472z00A = afO0.a();
                    C0767v2 c0767v2J2 = afO0.J2();
                    if (!p && !afO0.S0().isEmpty()) {
                        throw new AssertionError();
                    }
                    c3332x5.p().remove(0);
                    af = afO0;
                    c0767v2 = c0767v2J2;
                } else {
                    c0767v2 = null;
                    abstractC3472z00A = null;
                    af = null;
                }
                ArrayList arrayList = new ArrayList(listR.size());
                ArrayList arrayList2 = new ArrayList(listR.size());
                Iterator<C3332x4> it2 = listR.iterator();
                while (it2.hasNext()) {
                    C3332x4 next = it2.next();
                    if (next.c(c3332x5)) {
                        C3332x4 c3332x6 = new C3332x4();
                        Iterator it3 = it;
                        c3332x6.g(c2409kl.f.a());
                        arrayList.add(c3332x6);
                        if (z) {
                            S10 s10 = new S10(c2409kl.e.a(), abstractC3472z00A, af.j());
                            arrayList2.add(s10);
                            AF af2 = new AF(s10, c0767v2, a1);
                            c3332x6.a(af2, c2409kl.t());
                            af2.b(abstractC1915eMY0);
                        }
                        C1296Qj c1296Qj = new C1296Qj();
                        c1296Qj.b(abstractC1915eMY0);
                        c3332x6.a(c1296Qj, c2409kl.t());
                        c3332x6.a((C2102gl) null);
                        c3332x6.s().add(c3332x5);
                        c3332x6.r().add(next);
                        next.b(c3332x5, c3332x6);
                        if (listIterator == null) {
                            c2409kl.d.add(c3332x6);
                        } else {
                            listIterator.add(c3332x6);
                        }
                        it = it3;
                        it2 = it2;
                        z = z;
                    } else {
                        throw new C1367Tb("Invalid block structure: catch block reachable via non-exceptional flow.");
                    }
                }
                Iterator it4 = it;
                boolean z2 = z;
                listR.clear();
                listR.addAll(arrayList);
                if (z2) {
                    ML ml = new ML(c2409kl.e.a(), c3332x5, abstractC3472z00A, af.j(), ML.a.a);
                    ml.a((List<S10>) arrayList2);
                    af.b().g(ml);
                }
                it = it4;
            }
        }
    }

    private ArrayList a(C3332x4 c3332x4) {
        if (!p && !c3332x4.D()) {
            throw new AssertionError();
        }
        List listA = c3332x4.e.a();
        List listB = c3332x4.e.b();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        if (D()) {
            arrayList2.addAll(this.e.b());
            arrayList3.addAll(this.e.a());
            Iterator it = arrayList3.iterator();
            while (it.hasNext()) {
                int iIntValue = ((Integer) it.next()).intValue();
                C3332x4 c3332x5 = (C3332x4) this.b.get(iIntValue);
                if (!arrayList.contains(c3332x5)) {
                    arrayList.add(c3332x5);
                }
                int iIndexOf = arrayList.indexOf(c3332x5);
                if (!p && iIndexOf != iIntValue) {
                    throw new AssertionError();
                }
            }
        }
        int size = arrayList.size();
        for (int i = 0; i < listA.size(); i++) {
            int iIntValue2 = ((Integer) listA.get(i)).intValue();
            C0767v2 c0767v2 = (C0767v2) listB.get(i);
            if (!arrayList2.contains(c0767v2)) {
                C3332x4 c3332x6 = (C3332x4) c3332x4.b.get(iIntValue2);
                boolean z = p;
                if (!z && c3332x6.y().size() != 1) {
                    throw new AssertionError();
                }
                if (!z && !c3332x6.x().isEmpty()) {
                    throw new AssertionError();
                }
                int iIndexOf2 = arrayList.indexOf(c3332x6);
                if (iIndexOf2 == -1) {
                    arrayList.add(c3332x6);
                    iIndexOf2 = arrayList.size() - 1;
                }
                arrayList2.add(c0767v2);
                arrayList3.add(Integer.valueOf(iIndexOf2));
            }
        }
        List<C3332x4> listS = s();
        ArrayList<C3332x4> arrayList4 = new ArrayList(listS);
        listS.clear();
        ArrayList arrayList5 = new ArrayList();
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            if (i2 < size) {
                if (!p && !((C3332x4) arrayList.get(i2)).y().contains(this)) {
                    throw new AssertionError();
                }
                listS.add((C3332x4) arrayList.get(i2));
            } else {
                if (!p && ((C3332x4) arrayList.get(i2)).y().contains(this)) {
                    throw new AssertionError();
                }
                h((C3332x4) arrayList.get(i2));
                arrayList5.add((C3332x4) arrayList.get(i2));
            }
        }
        this.e = new C3038t6(arrayList2, arrayList3);
        int size2 = listS.size();
        for (C3332x4 c3332x7 : arrayList4) {
            if (!listS.contains(c3332x7)) {
                if (!p && m().B2()) {
                    throw new AssertionError();
                }
                listS.add(c3332x7);
            }
        }
        if (p || listS.size() == size2 || !m().B2()) {
            return arrayList5;
        }
        throw new AssertionError();
    }

    public final void a(com.android.tools.r8.graph.o5 o5Var, C3804u c3804u) {
        Iterator it = this.f.iterator();
        while (it.hasNext()) {
            ((AbstractC2412ko) it.next()).a(c3804u);
            if (c3804u.e().f()) {
                return;
            }
        }
        Iterator it2 = this.e.b().iterator();
        while (it2.hasNext()) {
            c3804u.a((C0767v2) it2.next());
            if (c3804u.e().f()) {
                return;
            }
        }
    }

    public final void b(ArrayList arrayList) {
        Iterator it = this.h.iterator();
        while (it.hasNext()) {
            ((ML) it.next()).a(arrayList);
        }
    }

    public final Iterable b(final AbstractC2412ko abstractC2412ko) {
        return new Iterable() { // from class: com.android.tools.r8.internal.x4$$ExternalSyntheticLambda8
            @Override // java.lang.Iterable
            public final Iterator iterator() {
                return this.f$0.f(abstractC2412ko);
            }
        };
    }

    public final void b(int i) {
        this.m -= i;
        this.l -= i;
    }

    public final void b(ML ml) {
        HashMap map;
        this.h.remove(ml);
        if (!p && (map = this.o) != null && map.containsValue(ml)) {
            throw new AssertionError("Attempt to remove Phi " + ml + " which is present in currentDefinitions");
        }
    }

    public final void b(C0767v2 c0767v2) {
        boolean z = p;
        if (!z && this.c.size() != 1) {
            throw new AssertionError();
        }
        if (!z && this.c.size() != 1) {
            throw new AssertionError();
        }
        C3332x4 c3332x4 = (C3332x4) this.c.get(0);
        if (!z && !c3332x4.n().a().contains(this)) {
            throw new AssertionError();
        }
        Iterator it = c3332x4.n().a().iterator();
        int i = 0;
        while (it.hasNext()) {
            if (((C3332x4) it.next()) == this && (i = i + 1) > 1) {
                ((C3332x4) this.c.get(0)).a(c0767v2);
                return;
            }
        }
        c0();
    }

    public final Set b() {
        Set setC = OU.c();
        for (C3332x4 c3332x4 : this.b) {
            setC.addAll(c3332x4.x());
            c3332x4.a(this, setC);
        }
        s().clear();
        Iterator it = this.c.iterator();
        while (it.hasNext()) {
            ((C3332x4) it.next()).k(this);
        }
        r().clear();
        for (ML ml : x()) {
            setC.addAll(ml.a());
            Iterator<S10> it2 = ml.k0().iterator();
            while (it2.hasNext()) {
                it2.next().c(ml);
            }
        }
        x().clear();
        for (AbstractC2412ko abstractC2412ko : p()) {
            if (abstractC2412ko.b() != null) {
                setC.addAll(abstractC2412ko.b().a());
                abstractC2412ko.b().d();
                abstractC2412ko.f(null);
            }
            Iterator it3 = abstractC2412ko.b.iterator();
            while (it3.hasNext()) {
                ((S10) it3.next()).e(abstractC2412ko);
            }
            Iterator it4 = abstractC2412ko.S0().iterator();
            while (it4.hasNext()) {
                ((S10) it4.next()).d(abstractC2412ko);
            }
        }
        return setC;
    }

    public final EnumC3182v4 b(C3332x4 c3332x4) {
        if (p || this.b.indexOf(c3332x4) >= 0) {
            return c(c3332x4) ? EnumC3182v4.c : EnumC3182v4.b;
        }
        throw new AssertionError();
    }
}
