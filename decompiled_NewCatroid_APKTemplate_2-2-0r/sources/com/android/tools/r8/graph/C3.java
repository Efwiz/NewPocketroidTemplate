package com.android.tools.r8.graph;

import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.CM;
import com.android.tools.r8.shaking.C3733i;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C3 {
    private final C0722p1 a;
    private final Predicate b;
    private final Function c;
    private final C0729q2 d;
    private final C0706m3.c e;
    private final Predicate f;

    public C3(C0702m c0702m, C0729q2 c0729q2, Predicate predicate) {
        Predicate predicateA;
        C0722p1 c0722p1A = c0702m.a();
        if (c0702m.f().i()) {
            C3733i c3733iO = c0702m.f().o();
            Objects.requireNonNull(c3733iO);
            predicateA = new C3$$ExternalSyntheticLambda0(c3733iO);
        } else {
            predicateA = CM.a();
        }
        AbstractC1476Wj abstractC1476WjE = c0702m.E();
        Objects.requireNonNull(abstractC1476WjE);
        this(c0722p1A, predicateA, new C3$$ExternalSyntheticLambda1(abstractC1476WjE), c0729q2, predicate);
    }

    public final C0706m3.b a(C0706m3.b bVar) {
        if (bVar.a()) {
            return bVar;
        }
        C0706m3.b bVarA = bVar.a(new B3(this));
        return (bVarA.a.isEmpty() && bVarA.c.isEmpty() && bVarA.b.b == this.a.Z1) ? C0706m3.b.f() : bVarA;
    }

    public C3(C0722p1 c0722p1, Predicate<C0767v2> predicate, Function<C0767v2, C0767v2> function, C0729q2 c0729q2, Predicate<C0767v2> predicate2) {
        this.a = c0722p1;
        this.b = predicate;
        this.c = function;
        this.d = c0729q2;
        this.f = predicate2;
        this.e = new C0706m3.c(c0722p1.Z1, C0706m3.a());
    }

    public C0706m3.e a(C0706m3.e eVar) {
        if (eVar.a()) {
            return eVar;
        }
        C0706m3.e eVarC = new B3(this).c(eVar);
        return eVarC == null ? C0706m3.e.p() : eVarC;
    }

    public C0706m3.g a(C0706m3.g gVar) {
        return !gVar.a() ? gVar.a(new B3(this)) : gVar;
    }
}
