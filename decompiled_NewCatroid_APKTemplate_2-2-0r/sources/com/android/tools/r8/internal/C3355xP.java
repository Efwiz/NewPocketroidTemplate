package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;

/* JADX INFO: renamed from: com.android.tools.r8.internal.xP, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3355xP {
    static final /* synthetic */ boolean e = true;
    private final int a;
    private final C3503zP b;
    private OptionalInt c;
    private final List d;

    public C3355xP(int i, C3503zP c3503zP, OptionalInt optionalInt, ArrayList arrayList) {
        this.a = i;
        this.b = c3503zP;
        this.c = optionalInt;
        this.d = arrayList;
    }

    public final int a() {
        if (e || this.c.isPresent()) {
            return this.c.getAsInt();
        }
        throw new AssertionError();
    }

    public final int b() {
        return this.a;
    }

    public final List c() {
        return this.d;
    }

    public final C3503zP d() {
        return this.b;
    }

    public final boolean e() {
        return this.c.isPresent();
    }

    public final String toString() {
        StringBuilder sbAppend = new StringBuilder("ProtoFieldInfo(number=").append(this.a).append(", type=").append(this.b).append(", aux data=").append(this.c).append(", objects=[");
        if (this.d.size() > 0) {
            sbAppend.append(this.d.get(0));
            for (int i = 1; i < this.d.size(); i++) {
                sbAppend.append(", ").append(this.d.get(i));
            }
        }
        return sbAppend.append("])").toString();
    }

    public final boolean b(EP ep) {
        return this.b.a(ep.h());
    }

    public final com.android.tools.r8.graph.m5 c(C0702m c0702m, EP ep) {
        C3503zP c3503zP = this.b;
        c3503zP.getClass();
        LP lpB = c3503zP instanceof OP ? ((PP) ep.e().get(a())).b() : (LP) this.d.get(0);
        if (!e) {
            lpB.getClass();
            if (!(lpB instanceof C2975sC)) {
                throw new AssertionError();
            }
        }
        return ((C0680j) c0702m.f()).c(lpB.a().g()).q();
    }

    public final com.android.tools.r8.graph.m5 b(C0702m c0702m, EP ep) {
        boolean z = e;
        if (!z) {
            C3503zP c3503zP = this.b;
            c3503zP.getClass();
            if (!(c3503zP instanceof OP)) {
                throw new AssertionError();
            }
        }
        AbstractC3433yP abstractC3433yPA = ((PP) ep.e().get(a())).a();
        if (!z) {
            abstractC3433yPA.getClass();
            if (!(abstractC3433yPA instanceof C2975sC)) {
                throw new AssertionError();
            }
        }
        return ((C0680j) c0702m.f()).c(abstractC3433yPA.a().g()).u();
    }

    final void a(int i) {
        if (!e && !this.c.isPresent()) {
            throw new AssertionError();
        }
        this.c = OptionalInt.of(i);
    }

    public final C0767v2 a(AP ap) {
        C3503zP c3503zP = this.b;
        c3503zP.getClass();
        if (c3503zP instanceof OP) {
            C3503zP c3503zPA = ap.a(this.b.a().n() - 51);
            if (!c3503zPA.c() && !c3503zPA.f()) {
                return null;
            }
            LP lp = (LP) this.d.get(0);
            if (!e) {
                lp.getClass();
                if (!(lp instanceof XP)) {
                    throw new AssertionError();
                }
            }
            return lp.d().g();
        }
        if (!this.b.f() && !this.b.c()) {
            if (!this.b.g() && !this.b.d()) {
                return null;
            }
            LP lp2 = (LP) this.d.get(1);
            if (!e) {
                lp2.getClass();
                if (!(lp2 instanceof XP)) {
                    throw new AssertionError();
                }
            }
            return lp2.d().g();
        }
        LP lp3 = (LP) this.d.get(0);
        if (!e) {
            lp3.getClass();
            if (!(lp3 instanceof C2975sC)) {
                throw new AssertionError();
            }
        }
        return lp3.a().g().h;
    }

    public final com.android.tools.r8.graph.m5 a(C0702m c0702m, EP ep) {
        boolean z = e;
        if (!z && !this.b.a(ep.h())) {
            throw new AssertionError();
        }
        int iA = a() / 32;
        if (!z && iA >= ep.j()) {
            throw new AssertionError();
        }
        LP lp = (LP) ep.d().get(iA);
        if (!z) {
            lp.getClass();
            if (!(lp instanceof C2975sC)) {
                throw new AssertionError();
            }
        }
        return ((C0680j) c0702m.f()).c(lp.a().g()).u();
    }

    public final int a(EP ep) {
        if (!e && !this.b.a(ep.h())) {
            throw new AssertionError();
        }
        return (a() % 32) + 1;
    }
}
