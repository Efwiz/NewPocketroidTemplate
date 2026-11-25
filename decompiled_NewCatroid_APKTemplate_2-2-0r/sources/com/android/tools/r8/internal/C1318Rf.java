package com.android.tools.r8.internal;

import java.util.BitSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Rf, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1318Rf {
    static final /* synthetic */ boolean f = true;
    private final com.android.tools.r8.graph.o5 a;
    private final int b;
    private final Set c = OU.c();
    private final Set d = OU.c();
    private boolean e;

    C1318Rf(com.android.tools.r8.graph.o5 o5Var, int i) {
        this.a = o5Var;
        this.b = i;
    }

    final void a(C1318Rf c1318Rf) {
        if (this.d.add(c1318Rf)) {
            c1318Rf.c.add(this);
        } else if (!f && !c1318Rf.c.contains(this)) {
            throw new AssertionError();
        }
    }

    public final int b() {
        return this.b;
    }

    public final com.android.tools.r8.graph.o5 c() {
        return this.a;
    }

    final Set d() {
        return this.c;
    }

    final Set e() {
        return this.d;
    }

    final boolean f() {
        if (this.a.e().B1() && this.b == 0) {
            return false;
        }
        return this.a.B().i().b(this.b).f().h();
    }

    final boolean g() {
        return this.e;
    }

    final void h() {
        this.d.removeIf(new Predicate() { // from class: com.android.tools.r8.internal.Rf$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.b((C1318Rf) obj);
            }
        });
    }

    final void i() {
        this.e = true;
    }

    final void j() {
        if (!this.a.B().v()) {
            BitSet bitSet = new BitSet(this.a.e().h1());
            bitSet.set(this.b);
            com.android.tools.r8.ir.optimize.info.n.a().a(this.a, bitSet);
        } else {
            com.android.tools.r8.ir.optimize.info.r rVarA = com.android.tools.r8.ir.optimize.info.n.a();
            com.android.tools.r8.graph.o5 o5Var = this.a;
            Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.Rf$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a((BitSet) obj);
                }
            };
            rVarA.getClass();
            com.android.tools.r8.ir.optimize.info.r.a(o5Var, consumer);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean b(C1318Rf c1318Rf) {
        if (!c1318Rf.a.B().v() || !c1318Rf.a.B().t().get(c1318Rf.b)) {
            return false;
        }
        boolean zRemove = c1318Rf.c.remove(this);
        if (f || zRemove) {
            return true;
        }
        throw new AssertionError();
    }

    final void a() {
        Iterator it = this.d.iterator();
        while (it.hasNext()) {
            boolean zRemove = ((C1318Rf) it.next()).c.remove(this);
            if (!f && !zRemove) {
                throw new AssertionError();
            }
        }
        this.d.clear();
        Iterator it2 = this.c.iterator();
        while (it2.hasNext()) {
            boolean zRemove2 = ((C1318Rf) it2.next()).d.remove(this);
            if (!f && !zRemove2) {
                throw new AssertionError();
            }
        }
        this.c.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(BitSet bitSet) {
        bitSet.set(this.b);
    }
}
