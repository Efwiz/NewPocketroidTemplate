package com.android.tools.r8.dex;

import com.android.tools.r8.FeatureSplit;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.internal.AbstractC3533zt;
import com.android.tools.r8.utils.C3932m1;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class p0 {
    static final /* synthetic */ boolean h = true;
    private final List a;
    private final ArrayList b;
    private final C0702m c;
    private final C3932m1 d;
    private Iterator e;
    private Iterator f;
    private FeatureSplit g;

    p0(List list, List list2, C0702m c0702m, C3932m1 c3932m1) {
        this.a = list;
        this.b = new ArrayList(list2);
        this.c = c0702m;
        this.d = c3932m1;
        if (list2.size() > 0) {
            this.g = ((r0) list2.get(0)).f();
        }
        c();
    }

    final r0 a(Predicate predicate) {
        r0 r0VarA;
        do {
            if (this.f.hasNext()) {
                r0VarA = b();
            } else {
                r0VarA = a();
                this.e = AbstractC3533zt.a((List) this.b);
            }
            if (r0VarA.m()) {
                if (h || predicate.test(r0VarA)) {
                    return r0VarA;
                }
                throw new AssertionError();
            }
        } while (!predicate.test(r0VarA));
        return r0VarA;
    }

    final r0 b() {
        return (r0) this.f.next();
    }

    final void c() {
        this.e = AbstractC3533zt.a((List) this.b);
        d();
    }

    final void d() {
        this.f = AbstractC3533zt.a(this.b.size(), this.e);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public r0 a() {
        r0 r0Var = new r0(this.d.b(), this.c, this.g);
        this.a.add(r0Var);
        this.b.add(r0Var);
        return r0Var;
    }
}
