package com.android.tools.r8.internal;

import com.android.dx.rop.code.RegisterSpec;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.utils.AbstractC3962s0;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class ML extends S10 implements InterfaceC2729oo {
    static final /* synthetic */ boolean v = true;
    private final C3332x4 q;
    private final ArrayList r;
    private a s;
    private boolean t;
    private ArrayList u;

    /* JADX WARN: $VALUES field not found */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static final class a {
        public static final a a = new a("NORMAL", 0);
        public static final a b = new a("DEBUG", 1);

        private a(String str, int i) {
            super(str, i);
        }
    }

    public ML(int i, C3332x4 c3332x4, AbstractC3472z00 abstractC3472z00, com.android.tools.r8.graph.X x, a aVar) {
        super(i, abstractC3472z00, x);
        this.r = new ArrayList();
        this.u = new ArrayList();
        this.q = c3332x4;
        this.s = aVar;
        c3332x4.a(this);
    }

    @Override // com.android.tools.r8.internal.S10
    public final boolean L() {
        return false;
    }

    @Override // com.android.tools.r8.internal.S10
    public final boolean T() {
        if (!v) {
            c(OU.c());
        }
        return this.t;
    }

    @Override // com.android.tools.r8.internal.S10
    public final void X() {
        this.s = a.a;
    }

    @Override // com.android.tools.r8.internal.S10
    public final boolean Y() {
        return !T();
    }

    @Override // com.android.tools.r8.internal.S10
    public final AbstractC3472z00 a(C0702m c0702m) {
        Set setC = OU.c();
        setC.add(this);
        ArrayDeque arrayDequeA = AbstractC3962s0.a(this);
        while (!arrayDequeA.isEmpty()) {
            ML ml = (ML) arrayDequeA.removeFirst();
            if (!v && !setC.contains(ml)) {
                throw new AssertionError();
            }
            Iterator<S10> it = ml.k0().iterator();
            while (it.hasNext()) {
                ML mlL = it.next().m().l();
                if (mlL != null && setC.add(mlL)) {
                    arrayDequeA.addLast(mlL);
                }
            }
        }
        Set setC2 = OU.c();
        AbstractC3472z00 abstractC3472z00F = AbstractC3472z00.f();
        Iterator it2 = setC.iterator();
        while (it2.hasNext()) {
            for (S10 s10 : ((ML) it2.next()).k0()) {
                if (!s10.m().i() && setC2.add(s10)) {
                    abstractC3472z00F = abstractC3472z00F.a(c0702m, s10.a(c0702m));
                }
            }
        }
        return (w().I() && w().x()) ? abstractC3472z00F.d().P() : abstractC3472z00F;
    }

    @Override // com.android.tools.r8.internal.S10
    public final boolean b(Predicate predicate) {
        return false;
    }

    public final S10 c(int i) {
        return (S10) this.r.get(i);
    }

    public final void d(int i) {
        ((S10) this.r.get(i)).c(this);
        this.r.remove(i);
    }

    @Override // com.android.tools.r8.internal.S10, com.android.tools.r8.internal.InterfaceC2729oo
    public final C3332x4 h() {
        return this.q;
    }

    @Override // com.android.tools.r8.internal.S10, com.android.tools.r8.internal.InterfaceC2729oo
    public final boolean i() {
        return true;
    }

    public final void j0() {
        this.u = null;
    }

    public List<S10> k0() {
        return this.r;
    }

    @Override // com.android.tools.r8.internal.S10, com.android.tools.r8.internal.InterfaceC2729oo
    public final ML l() {
        return this;
    }

    public final boolean l0() {
        S10 s10 = null;
        for (S10 s11 : this.r) {
            if (s11 != s10 && s11 != this) {
                if (s10 != null) {
                    return false;
                }
                s10 = s11;
            }
        }
        return true;
    }

    public final String m0() {
        StringBuilder sb = new StringBuilder();
        sb.append(RegisterSpec.PREFIX);
        sb.append(this.a);
        if (A()) {
            sb.append("(").append(s()).append(Constant.AFTER_QUTO);
        }
        sb.append(" <- phi");
        com.android.tools.r8.utils.Y2.a(sb, com.android.tools.r8.utils.V1.a((Collection) this.r, new Function() { // from class: com.android.tools.r8.internal.ML$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((S10) obj).toString();
            }
        }));
        sb.append(" : ").append(w());
        return sb.toString();
    }

    public final void n0() {
        if (!v && E()) {
            throw new AssertionError();
        }
        Iterator<S10> it = k0().iterator();
        while (it.hasNext()) {
            it.next().c(this);
        }
        this.q.b(this);
    }

    public final boolean o0() {
        return a((C2102gl) null, (Set) null);
    }

    public final void p0() {
        this.t = true;
    }

    private void b(EnumC1968f20 enumC1968f20) {
        if (a(enumC1968f20) != null) {
            return;
        }
        throw new C0994Fs("Type information in locals-table is inconsistent. Cannot constrain type: " + this.n + " for value: " + this + " by constraint " + enumC1968f20 + ".");
    }

    private void c(final Set set) {
        set.add(this);
        this.r.forEach(new Consumer() { // from class: com.android.tools.r8.internal.ML$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(set, (S10) obj);
            }
        });
    }

    public final void h(S10 s10) {
        this.r.add(s10);
        s10.a(this);
    }

    public AbstractC3472z00 c(C0702m c0702m) {
        AbstractC3472z00 abstractC3472z00F = AbstractC3472z00.f();
        Iterator<S10> it = k0().iterator();
        while (it.hasNext()) {
            abstractC3472z00F = abstractC3472z00F.a(c0702m, it.next().w());
        }
        return abstractC3472z00F;
    }

    public final void b(Map map) {
        this.u.remove(map);
    }

    @Override // com.android.tools.r8.internal.S10
    public final void a(EnumC1968f20 enumC1968f20, C0684j2 c0684j2, Origin origin, com.android.tools.r8.utils.O2 o2) {
        if (this.s == a.b) {
            b(enumC1968f20);
        }
        super.a(enumC1968f20, c0684j2, origin, o2);
    }

    public final void a(int i, C2102gl c2102gl) {
        if (!v && !this.r.isEmpty()) {
            throw new AssertionError();
        }
        if (this.q.y().size() != 0) {
            AbstractC3472z00 abstractC3472z00 = this.n;
            abstractC3472z00.getClass();
            EnumC1968f20 enumC1968f20A = abstractC3472z00 instanceof G5 ? EnumC1968f20.OBJECT : EnumC1968f20.a(abstractC3472z00);
            ArrayList<S10> arrayList = new ArrayList(this.q.y().size());
            for (C3332x4 c3332x4 : this.q.y()) {
                arrayList.add(c2102gl.a(i, c3332x4.b(this.q), c3332x4, this.s, enumC1968f20A));
            }
            if (this.s == a.b) {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    AbstractC3472z00 abstractC3472z00W = ((S10) it.next()).w();
                    abstractC3472z00W.getClass();
                    b(abstractC3472z00W instanceof G5 ? EnumC1968f20.OBJECT : EnumC1968f20.a(abstractC3472z00W));
                }
            }
            for (S10 s10 : arrayList) {
                c2102gl.a(s10, enumC1968f20A);
                h(s10);
            }
            a(c2102gl, (Set) null);
            return;
        }
        throw new C1367Tb("Undefined value encountered during compilation. This is typically caused by invalid dex input that uses a register that is not defined on all control-flow paths leading to the use.");
    }

    public void a(List<S10> list) {
        a((List) list, true);
    }

    public final void a(List list, boolean z) {
        if (!v && !this.r.isEmpty()) {
            throw new AssertionError();
        }
        if (list.size() != 0) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                h((S10) it.next());
            }
            if (z) {
                o0();
                return;
            }
            return;
        }
        throw new C1367Tb("Undefined value encountered during compilation. This is typically caused by invalid dex input that uses a register that is not defined on all control-flow paths leading to the use.");
    }

    public final void a(ArrayList arrayList) {
        if (arrayList.isEmpty()) {
            return;
        }
        ArrayList arrayList2 = new ArrayList(this.r);
        this.r.clear();
        Iterator it = arrayList.iterator();
        int i = 0;
        while (it.hasNext()) {
            int iIntValue = ((Integer) it.next()).intValue();
            this.r.addAll(arrayList2.subList(i, iIntValue));
            ((S10) arrayList2.get(iIntValue)).c(this);
            i = iIntValue + 1;
        }
        this.r.addAll(arrayList2.subList(i, arrayList2.size()));
    }

    public final void a(int i, S10 s10) {
        S10 s11 = (S10) this.r.get(i);
        this.r.set(i, s10);
        s10.a(this);
        s11.c(this);
    }

    public final void a(S10 s10, S10 s11) {
        for (int i = 0; i < this.r.size(); i++) {
            if (this.r.get(i) == s10) {
                this.r.set(i, s11);
                s11.a(this);
            }
        }
    }

    public final boolean a(C2102gl c2102gl, Set set) {
        S10 s10 = null;
        for (S10 s11 : this.r) {
            if (s11 != s10 && s11 != this) {
                if (s10 != null) {
                    if (v || !l0()) {
                        return false;
                    }
                    throw new AssertionError();
                }
                s10 = s11;
            }
        }
        boolean z = v;
        if (!z && !l0()) {
            throw new AssertionError();
        }
        if (s10 == null) {
            return false;
        }
        if (s() != s10.s() && s() != null) {
            if (s10.s() == null) {
                s10.a(s());
            } else {
                if (z || (A() && s10.A())) {
                    return false;
                }
                throw new AssertionError();
            }
        }
        if (c2102gl != null && this.n.G()) {
            AbstractC3472z00 abstractC3472z00 = this.n;
            abstractC3472z00.getClass();
            if (!(abstractC3472z00 instanceof G5)) {
                c2102gl.a(s10, EnumC1968f20.a(this.n));
            }
        }
        if (set != null) {
            set.addAll(a());
        }
        Iterator it = this.r.iterator();
        while (it.hasNext()) {
            ((S10) it.next()).c(this);
        }
        ArrayList<Map> arrayList = this.u;
        if (arrayList != null) {
            for (Map map : arrayList) {
                for (Map.Entry entry : map.entrySet()) {
                    if (entry.getValue() == this) {
                        entry.setValue(s10);
                        if (s10.i()) {
                            s10.l().u.add(map);
                        }
                    }
                }
            }
        }
        Set setG0 = g0();
        g(s10);
        Iterator it2 = setG0.iterator();
        while (it2.hasNext()) {
            ((ML) it2.next()).a(c2102gl, set);
        }
        this.q.b(this);
        return true;
    }

    public final void a(Map map) {
        this.u.add(map);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Set set, S10 s10) {
        if (s10.i()) {
            if (v || set.contains(s10)) {
                return;
            }
            s10.l().c(set);
            return;
        }
        if (!v && s10.T() != this.t) {
            throw new AssertionError();
        }
    }

    public C0767v2 a(C0702m c0702m, Q00 q00) {
        if (!v && !EnumC1812d20.a(this.n).a()) {
            throw new AssertionError();
        }
        HashSet hashSet = new HashSet(this.r.size());
        Iterator it = this.r.iterator();
        while (it.hasNext()) {
            C0767v2 c0767v2A = q00.a((S10) it.next());
            if (c0767v2A != null) {
                hashSet.add(c0767v2A);
            }
        }
        return q00.a(hashSet);
    }
}
