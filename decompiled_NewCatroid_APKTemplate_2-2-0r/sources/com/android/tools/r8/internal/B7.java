package com.android.tools.r8.internal;

import com.android.tools.r8.dex.code.InterfaceC0419c;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.ir.optimize.C3550d0;
import com.android.tools.r8.naming.AbstractC3649o0;
import java.util.ListIterator;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class B7 implements InterfaceC0419c {
    static final /* synthetic */ boolean a = true;

    public M7 A() {
        return null;
    }

    public boolean B() {
        return false;
    }

    public boolean C() {
        return false;
    }

    public boolean D() {
        return this instanceof C3413y7;
    }

    public boolean E() {
        return false;
    }

    public boolean F() {
        return this instanceof C3482z7;
    }

    public boolean G() {
        return false;
    }

    public boolean H() {
        return false;
    }

    public boolean I() {
        return false;
    }

    public boolean J() {
        return false;
    }

    public boolean K() {
        return false;
    }

    public boolean L() {
        return false;
    }

    public boolean M() {
        return false;
    }

    public boolean N() {
        return this instanceof C2899r7;
    }

    public boolean O() {
        return this instanceof M7;
    }

    public boolean P() {
        return this instanceof C2051g8;
    }

    public boolean Q() {
        return this instanceof C3109u8;
    }

    public boolean R() {
        return this instanceof V8;
    }

    public boolean S() {
        return false;
    }

    public abstract int a(B7 b7, com.android.tools.r8.utils.structural.a aVar, com.android.tools.r8.graph.C c);

    public D6 a() {
        return null;
    }

    public abstract AbstractC2359k7 a(AbstractC2359k7 abstractC2359k7, C0702m c0702m, InterfaceC3107u6 interfaceC3107u6);

    public abstract com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, C0753u c0753u, com.android.tools.r8.graph.o5 o5Var);

    void a(com.android.tools.r8.graph.J5 j5, ListIterator listIterator) {
    }

    public abstract void a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, C0722p1 c0722p1, AbstractC1476Wj abstractC1476Wj, AbstractC2331jn abstractC2331jn, AbstractC3649o0 abstractC3649o0, C2363kB c2363kB, AbstractC1899eF abstractC1899eF);

    public abstract void a(C2102gl c2102gl, N8 n8, A8 a8);

    public abstract void a(C2757p8 c2757p8);

    public abstract void a(com.android.tools.r8.utils.structural.o oVar);

    public M6 b() {
        return null;
    }

    public X6 c() {
        return null;
    }

    public Y6 d() {
        return null;
    }

    public C1891e7 e() {
        return null;
    }

    public C2134h7 f() {
        return null;
    }

    @Override // com.android.tools.r8.internal.InterfaceC3174v
    public final boolean g() {
        return x();
    }

    public AbstractC2206i7 h() {
        return null;
    }

    public C2284j7 i() {
        return null;
    }

    public C3482z7 j() {
        return null;
    }

    public A7 k() {
        return null;
    }

    public I7 l() {
        return null;
    }

    @Override // com.android.tools.r8.dex.code.InterfaceC0419c
    public final B7 m() {
        return this;
    }

    public J7 n() {
        return null;
    }

    @Override // com.android.tools.r8.dex.code.InterfaceC0419c
    public final com.android.tools.r8.dex.code.B1 o() {
        return null;
    }

    public L7 p() {
        return null;
    }

    public M7 q() {
        return null;
    }

    public O7 r() {
        return null;
    }

    public C2051g8 s() {
        return null;
    }

    public C2830q8 t() {
        return null;
    }

    public String toString() {
        C2757p8 c2757p8 = new C2757p8();
        a(c2757p8);
        return c2757p8.toString();
    }

    public P8 u() {
        return null;
    }

    public R8 v() {
        return null;
    }

    public X8 w() {
        return null;
    }

    public boolean x() {
        return this instanceof A6;
    }

    public boolean y() {
        return !(this instanceof C2284j7);
    }

    public abstract int z();

    public final void a(com.android.tools.r8.graph.J5 j5, com.android.tools.r8.graph.o5 o5Var, ListIterator listIterator) {
        a(j5, listIterator);
    }

    public final void a(com.android.tools.r8.graph.J5 j5, com.android.tools.r8.graph.S s, ListIterator listIterator) {
        a(j5, listIterator);
    }

    public final void a(final Consumer consumer, B7 b7) {
        a(new BiFunction() { // from class: com.android.tools.r8.internal.B7$$ExternalSyntheticLambda0
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return B7.a(consumer, (B7) obj, obj2);
            }
        }, b7, (AbstractC2359k7) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.tools.r8.utils.D3 a(Consumer consumer, B7 b7, Object obj) {
        consumer.accept(b7);
        return com.android.tools.r8.utils.D3.e();
    }

    public com.android.tools.r8.utils.D3 a(BiFunction biFunction, B7 b7, AbstractC2359k7 abstractC2359k7) {
        boolean z = a;
        if (!z && M()) {
            throw new AssertionError();
        }
        if (b7 != null) {
            return (com.android.tools.r8.utils.D3) biFunction.apply(b7, abstractC2359k7);
        }
        if (!z && !(this instanceof M7)) {
            throw new AssertionError();
        }
        return com.android.tools.r8.utils.D3.b(abstractC2359k7);
    }
}
