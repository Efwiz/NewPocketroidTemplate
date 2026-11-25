package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.origin.Origin;
import java.util.IdentityHashMap;

/* JADX INFO: renamed from: com.android.tools.r8.internal.xn, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C3387xn {
    static final /* synthetic */ boolean f = true;
    private final C0702m a;
    private final com.android.tools.r8.graph.o5 b;
    private final C2145hI c;
    private final TE d;
    private final IdentityHashMap e;

    private C3387xn() {
        this.e = new IdentityHashMap();
        this.a = null;
        this.b = null;
        this.c = null;
        this.d = null;
    }

    public static C3387xn b() {
        return new C3310wn();
    }

    public C2409kl a(com.android.tools.r8.graph.o5 o5Var, AbstractC1305Qs abstractC1305Qs) {
        C2409kl c2409klB = b(o5Var, abstractC1305Qs);
        a(abstractC1305Qs, c2409klB);
        return c2409klB;
    }

    public boolean c() {
        if (f || this.e.isEmpty()) {
            return true;
        }
        throw new AssertionError();
    }

    public C2409kl b(com.android.tools.r8.graph.o5 o5Var, AbstractC1305Qs abstractC1305Qs) {
        AbstractC1476Wj abstractC1476WjA;
        com.android.tools.r8.graph.proto.k kVarD;
        C2409kl c2409kl = (C2409kl) this.e.remove(abstractC1305Qs);
        if (c2409kl != null) {
            return c2409kl;
        }
        Origin origin = o5Var.getOrigin();
        com.android.tools.r8.graph.o5 o5Var2 = this.b;
        C0702m c0702m = this.a;
        C2145hI c2145hI = this.c;
        AbstractC1915eM abstractC1915eMA = AbstractC1915eM.a(c0702m, o5Var2, abstractC1305Qs);
        TE te = this.d;
        com.android.tools.r8.graph.W wA1 = o5Var.e().a1();
        AbstractC1476Wj abstractC1476WjE = c0702m.E();
        com.android.tools.r8.graph.proto.k kVarH = com.android.tools.r8.graph.proto.k.h();
        if (te.d(o5Var)) {
            abstractC1476WjA = o5Var.e().a1().a(c0702m);
            kVarD = c0702m.E().d((AbstractC1476Wj) null, o5Var.getReference());
        } else {
            abstractC1476WjA = abstractC1476WjE;
            kVarD = kVarH;
        }
        return wA1.a(o5Var2, o5Var, c0702m, abstractC1476WjA, c2145hI, abstractC1915eMA, origin, kVarD);
    }

    public void a(AbstractC1305Qs abstractC1305Qs, C2409kl c2409kl) {
        C2409kl c2409kl2 = (C2409kl) this.e.put(abstractC1305Qs, c2409kl);
        if (!f && c2409kl2 != null) {
            throw new AssertionError();
        }
    }

    public TE a() {
        return this.d;
    }

    public boolean a(com.android.tools.r8.graph.o5 o5Var) {
        return this.d.d(o5Var);
    }

    public C3387xn(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, C2409kl c2409kl, TE te) {
        this.e = new IdentityHashMap();
        this.a = c0702m;
        this.b = o5Var;
        this.c = c2409kl.e;
        this.d = te;
    }
}
