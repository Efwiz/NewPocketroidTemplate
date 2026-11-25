package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.internal.AbstractC2412ko;
import com.android.tools.r8.internal.C1552Zd;
import com.android.tools.r8.internal.C2409kl;
import com.android.tools.r8.internal.C3030t00;
import com.android.tools.r8.internal.C3332x4;
import com.android.tools.r8.internal.InterfaceC2641no;
import com.android.tools.r8.internal.N3;
import com.android.tools.r8.internal.OU;
import com.android.tools.r8.internal.S10;
import com.android.tools.r8.internal.VH$$ExternalSyntheticLambda0;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.ir.optimize.q, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3579q {
    private final C0702m a;
    private final C2409kl b;
    private final Set c;
    private final Set d = OU.c();
    private boolean e = false;

    public C3579q(C0702m c0702m, C2409kl c2409kl, Set set) {
        this.a = c0702m;
        this.b = c2409kl;
        this.c = set;
    }

    public final void a(List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            S10 s10 = (S10) it.next();
            if (s10.b(new VH$$ExternalSyntheticLambda0()) && !s10.y()) {
                this.d.add(s10.q().w());
            }
        }
    }

    public final Set b() {
        return this.c;
    }

    public final boolean c() {
        return this.e;
    }

    public final C3579q d() {
        return a((Set) null);
    }

    public final void a(N3 n3, InterfaceC2641no interfaceC2641no) {
        if (this.d.remove(n3)) {
            S10 s10N2 = n3.N2();
            S10 s10B = n3.b();
            if (!s10B.w().equals(s10N2.w())) {
                this.c.addAll(s10B.a());
            }
            if (s10B.C()) {
                this.e = true;
            }
            s10B.g(s10N2);
            interfaceC2641no.s();
        }
    }

    public final C3579q a(Set set) {
        if (!this.d.isEmpty()) {
            for (C3332x4 c3332x4 : this.b.d) {
                if (set == null || !set.contains(c3332x4)) {
                    InterfaceC2641no interfaceC2641noA = c3332x4.a(this.b);
                    while (interfaceC2641noA.hasNext()) {
                        AbstractC2412ko next = interfaceC2641noA.next();
                        next.getClass();
                        if (next instanceof N3) {
                            a(next.w(), interfaceC2641noA);
                        }
                    }
                }
            }
        }
        return this;
    }

    public final void a() {
        if (this.c.isEmpty()) {
            return;
        }
        new C3030t00(this.a, false).a((Iterable) this.c);
    }

    public final void a(S10 s10) {
        for (AbstractC2412ko abstractC2412ko : s10.a(C1552Zd.a())) {
            abstractC2412ko.getClass();
            if (abstractC2412ko instanceof N3) {
                N3 n3W = abstractC2412ko.w();
                n3W.O2();
                if (!n3W.L2()) {
                    this.d.add(n3W);
                }
            }
        }
    }
}
