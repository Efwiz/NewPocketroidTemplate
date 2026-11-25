package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.ir.optimize.C3550d0;
import com.android.tools.r8.naming.AbstractC3649o0;
import com.android.tools.r8.utils.InterfaceC3937n1;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.j7, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C2284j7 extends B7 implements Cloneable {
    public static final C1298Ql d = C1298Ql.a(new InterfaceC3054tM[0]);
    static final /* synthetic */ boolean e = true;
    private final InterfaceC1456Vp b;
    private final Deque c;

    /* JADX INFO: renamed from: com.android.tools.r8.internal.j7$a */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a {
        static final /* synthetic */ boolean e = true;
        private InterfaceC1456Vp a;
        private Deque b;
        private boolean c;
        private boolean d;

        public a() {
            C1298Ql c1298Ql = C2284j7.d;
            this.a = AbstractC1511Xp.a;
            this.b = C2284j7.d;
            this.c = false;
            this.d = false;
        }

        public final a a(int i) {
            if (!e && this.b != C2284j7.d) {
                throw new AssertionError();
            }
            if (i > 0) {
                this.b = new ArrayDeque(i);
            }
            return this;
        }

        public final boolean b(int i) {
            return this.a.a(i);
        }

        public final boolean c() {
            return this.c;
        }

        public final void d() {
            this.c = true;
        }

        public final C2284j7 b() {
            InterfaceC1456Vp interfaceC1456Vp = this.a;
            C1298Ql c1298Ql = C2284j7.d;
            if (interfaceC1456Vp == AbstractC1511Xp.a) {
                this.a = new C2413kp();
            }
            if (this.b == C2284j7.d) {
                this.b = new ArrayDeque();
            }
            return a();
        }

        public final a a(InterfaceC1348Si interfaceC1348Si) {
            boolean z = e;
            if (!z && this.d) {
                throw new AssertionError();
            }
            int size = this.a.size();
            if (!z && interfaceC1348Si.k()) {
                throw new AssertionError();
            }
            InterfaceC1456Vp interfaceC1456Vp = this.a;
            C1298Ql c1298Ql = C2284j7.d;
            if (interfaceC1456Vp == AbstractC1511Xp.a) {
                this.a = new C2413kp();
            }
            AbstractC2440l7.a(size, interfaceC1348Si, (C2413kp) this.a);
            return this;
        }

        public final a a(Consumer consumer) {
            consumer.accept(this);
            return this;
        }

        public final a a(InterfaceC1456Vp interfaceC1456Vp) {
            this.a = interfaceC1456Vp;
            return this;
        }

        public final a a(ArrayDeque arrayDeque) {
            this.b = arrayDeque;
            return this;
        }

        public a a(int i, InterfaceC1348Si interfaceC1348Si) {
            this.d = true;
            if (!e && interfaceC1348Si.k()) {
                throw new AssertionError();
            }
            InterfaceC1456Vp interfaceC1456Vp = this.a;
            C1298Ql c1298Ql = C2284j7.d;
            if (interfaceC1456Vp == AbstractC1511Xp.a) {
                this.a = new C2413kp();
            }
            AbstractC2440l7.a(i, interfaceC1348Si, (C2413kp) this.a);
            return this;
        }

        public C2284j7 a() {
            return new C2284j7(this.a, this.b);
        }

        public a a(InterfaceC3054tM interfaceC3054tM) {
            if (this.b == C2284j7.d) {
                this.b = new ArrayDeque();
            }
            this.b.addLast(interfaceC3054tM);
            return this;
        }
    }

    public C2284j7() {
        this(AbstractC1511Xp.a, d);
    }

    public static a T() {
        return new a();
    }

    @Override // com.android.tools.r8.internal.B7
    public final boolean G() {
        return true;
    }

    /* JADX INFO: renamed from: U, reason: merged with bridge method [inline-methods] */
    public final C2284j7 clone() {
        return new C2284j7(this.b, this.c);
    }

    public final int V() {
        Iterator it = this.c.iterator();
        int iT = 0;
        while (it.hasNext()) {
            iT += ((InterfaceC3054tM) it.next()).t();
        }
        return iT;
    }

    public final InterfaceC1456Vp W() {
        return this.b;
    }

    public final C2413kp X() {
        if (e || (this.b instanceof C2413kp)) {
            return (C2413kp) this.b;
        }
        throw new AssertionError();
    }

    public final ArrayDeque Y() {
        if (e || (this.c instanceof ArrayDeque)) {
            return (ArrayDeque) this.c;
        }
        throw new AssertionError();
    }

    public final Deque Z() {
        return this.c;
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(com.android.tools.r8.utils.structural.o oVar) {
    }

    public final C2284j7 a0() {
        return new C2284j7((InterfaceC1456Vp) new C2413kp(this.b), (Deque) new ArrayDeque(this.c));
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        C2284j7 c2284j7 = (C2284j7) obj;
        return this.b.equals(c2284j7.b) && AbstractC2734ot.a((Iterable) this.c, (Iterable) c2284j7.c);
    }

    public int hashCode() {
        int iHashCode = this.b.hashCode() + 31;
        Iterator it = this.c.iterator();
        while (it.hasNext()) {
            iHashCode = (iHashCode * 31) + ((InterfaceC3054tM) it.next()).hashCode();
        }
        return iHashCode;
    }

    @Override // com.android.tools.r8.internal.B7
    public C2284j7 i() {
        return this;
    }

    @Override // com.android.tools.r8.internal.B7
    public final String toString() {
        return getClass().getSimpleName();
    }

    @Override // com.android.tools.r8.internal.B7
    public final int z() {
        return 207;
    }

    public C2284j7(C2413kp c2413kp) {
        this((InterfaceC1456Vp) c2413kp, (Deque) d);
        if (!e && c2413kp.isEmpty() && c2413kp != AbstractC1511Xp.a) {
            throw new AssertionError("Should use EMPTY_LOCALS instead");
        }
    }

    @Override // com.android.tools.r8.internal.B7
    public final int a(B7 b7, com.android.tools.r8.utils.structural.a aVar, com.android.tools.r8.graph.C c) {
        com.android.tools.r8.graph.C.a(this, b7);
        return 0;
    }

    public final void a(InterfaceC3937n1 interfaceC3937n1) {
        YJ it = this.b.b().iterator();
        while (it.hasNext()) {
            InterfaceC2806pp interfaceC2806pp = (InterfaceC2806pp) it.next();
            interfaceC3937n1.a(interfaceC2806pp.a(), (InterfaceC1348Si) interfaceC2806pp.getValue());
        }
    }

    public C2284j7(C2413kp c2413kp, ArrayDeque arrayDeque) {
        this((InterfaceC1456Vp) c2413kp, (Deque) arrayDeque);
        boolean z = e;
        if (!z && c2413kp.isEmpty() && c2413kp != AbstractC1511Xp.a) {
            throw new AssertionError("Should use EMPTY_LOCALS instead");
        }
        if (!z && arrayDeque.isEmpty() && arrayDeque != d) {
            throw new AssertionError("Should use EMPTY_STACK instead");
        }
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, C0722p1 c0722p1, AbstractC1476Wj abstractC1476Wj, AbstractC2331jn abstractC2331jn, AbstractC3649o0 abstractC3649o0, C2363kB c2363kB, AbstractC1899eF abstractC1899eF) {
        Object[] objArr;
        int i;
        Object[] objArr2;
        int size = this.c.size();
        if (!e && size != this.c.size()) {
            throw new AssertionError();
        }
        int i2 = 0;
        if (size == 0) {
            objArr = null;
        } else {
            Object[] objArr3 = new Object[size];
            Iterator it = this.c.iterator();
            int i3 = 0;
            while (it.hasNext()) {
                objArr3[i3] = ((InterfaceC3054tM) it.next()).a(abstractC1476Wj, abstractC3649o0);
                i3++;
            }
            objArr = objArr3;
        }
        if (this.b.isEmpty()) {
            i = 0;
        } else {
            int iD = this.b.d();
            int i4 = 0;
            i = 0;
            while (i4 <= iD) {
                i++;
                InterfaceC1348Si interfaceC1348Si = (InterfaceC1348Si) this.b.get(i4);
                if (interfaceC1348Si != null && interfaceC1348Si.P()) {
                    i4++;
                }
                i4++;
            }
        }
        if (i == 0) {
            objArr2 = null;
        } else {
            int iD2 = this.b.d();
            objArr2 = new Object[i];
            int i5 = 0;
            while (i2 <= iD2) {
                InterfaceC1348Si interfaceC1348Si2 = (InterfaceC1348Si) this.b.get(i2);
                int i6 = i5 + 1;
                objArr2[i5] = interfaceC1348Si2 == null ? JK.a : interfaceC1348Si2.a(abstractC1476Wj, abstractC3649o0);
                if (interfaceC1348Si2 != null && interfaceC1348Si2.P()) {
                    i2++;
                }
                i2++;
                i5 = i6;
            }
        }
        abstractC1899eF.a(-1, i, objArr2, size, objArr);
    }

    private C2284j7(InterfaceC1456Vp interfaceC1456Vp, Deque deque) {
        boolean z = e;
        if (!z) {
            AbstractC2440l7.a(interfaceC1456Vp);
        }
        if (!z && !deque.stream().allMatch(new Predicate() { // from class: com.android.tools.r8.internal.j7$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Objects.nonNull((InterfaceC3054tM) obj);
            }
        })) {
            throw new AssertionError();
        }
        this.b = interfaceC1456Vp;
        this.c = deque;
    }

    @Override // com.android.tools.r8.internal.B7
    final void a(com.android.tools.r8.graph.J5 j5, ListIterator listIterator) {
        InterfaceC1912eK it = this.b.values().iterator();
        while (it.hasNext()) {
            InterfaceC1348Si interfaceC1348Si = (InterfaceC1348Si) it.next();
            if (!e && interfaceC1348Si.G()) {
                throw new AssertionError();
            }
            if (interfaceC1348Si.N()) {
                j5.f(interfaceC1348Si.j().T());
            } else if (interfaceC1348Si.y()) {
                j5.f(interfaceC1348Si.z().D());
            }
            if (j5.e().f()) {
                return;
            }
        }
        for (InterfaceC1348Si interfaceC1348Si2 : this.c) {
            if (!e && interfaceC1348Si2.G()) {
                throw new AssertionError();
            }
            if (interfaceC1348Si2.N()) {
                j5.f(interfaceC1348Si2.j().T());
            } else if (interfaceC1348Si2.y()) {
                j5.f(interfaceC1348Si2.z().D());
            }
            if (j5.e().f()) {
                return;
            }
        }
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C2757p8 c2757p8) {
        c2757p8.a(this);
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C2102gl c2102gl, N8 n8, A8 a8) {
        a8.a(this);
    }

    @Override // com.android.tools.r8.internal.B7
    public final com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, C0753u c0753u, com.android.tools.r8.graph.o5 o5Var) {
        return com.android.tools.r8.ir.optimize.V.d;
    }

    @Override // com.android.tools.r8.internal.B7
    public final AbstractC2359k7 a(AbstractC2359k7 abstractC2359k7, C0702m c0702m, InterfaceC3107u6 interfaceC3107u6) {
        return abstractC2359k7.a(interfaceC3107u6, this);
    }

    public C2284j7 a(Function<C0767v2, C0767v2> function) {
        InterfaceC3018sr it = this.b.keySet().iterator();
        boolean z = false;
        while (it.hasNext()) {
            InterfaceC1348Si interfaceC1348Si = (InterfaceC1348Si) this.b.get(((Integer) it.next()).intValue());
            z = interfaceC1348Si != interfaceC1348Si.a(function);
            if (z) {
                break;
            }
        }
        if (!z) {
            for (InterfaceC3054tM interfaceC3054tM : this.c) {
                z = interfaceC3054tM != interfaceC3054tM.a((Function) function);
                if (z) {
                    break;
                }
            }
        }
        if (!z) {
            return this;
        }
        a aVarT = T();
        YJ it2 = this.b.b().iterator();
        while (it2.hasNext()) {
            InterfaceC2806pp interfaceC2806pp = (InterfaceC2806pp) it2.next();
            InterfaceC1348Si interfaceC1348Si2 = (InterfaceC1348Si) interfaceC2806pp.getValue();
            if (interfaceC1348Si2.n()) {
                if (e) {
                    continue;
                } else {
                    int iA = interfaceC2806pp.a();
                    if (!a.e && !aVarT.a.a(iA)) {
                        throw new AssertionError();
                    }
                    if (((InterfaceC1348Si) aVarT.a.get(iA)) != interfaceC1348Si2) {
                        throw new AssertionError();
                    }
                }
            } else {
                aVarT.a(interfaceC2806pp.a(), interfaceC1348Si2.a(function));
            }
        }
        for (InterfaceC3054tM interfaceC3054tM2 : this.c) {
            if (!e && interfaceC3054tM2.n()) {
                throw new AssertionError();
            }
            aVarT.a(interfaceC3054tM2.a((Function) function));
        }
        return aVarT.a();
    }
}
