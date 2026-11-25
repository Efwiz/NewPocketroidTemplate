package com.android.tools.r8.graph.proto;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.W0;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.AbstractC1915eM;
import com.android.tools.r8.internal.AbstractC2412ko;
import com.android.tools.r8.internal.AbstractC3078th;
import com.android.tools.r8.internal.AbstractC3292wV;
import com.android.tools.r8.internal.C2409kl;
import com.android.tools.r8.internal.H2;
import com.android.tools.r8.internal.InterfaceC3176v00;
import com.android.tools.r8.utils.M;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class k {
    private static final k d = new k();
    static final /* synthetic */ boolean e = true;
    public static final /* synthetic */ int f = 0;
    private final List a;
    private final c b;
    private final l c;

    private k() {
        this.a = Collections.emptyList();
        this.c = null;
        this.b = c.b();
    }

    public static k a(List list, l lVar, c cVar) {
        return (list.isEmpty() && lVar == null && cVar.g()) ? d : new k(list, lVar, cVar);
    }

    public static k h() {
        return d;
    }

    public final List b() {
        return this.a;
    }

    public final l c() {
        return this.c;
    }

    public final boolean d() {
        l lVar = this.c;
        return lVar != null && lVar.j();
    }

    public final boolean e() {
        return !this.a.isEmpty();
    }

    public final boolean equals(Object obj) {
        if (obj == null || k.class != obj.getClass()) {
            return false;
        }
        k kVar = (k) obj;
        return this.a.equals(kVar.a) && Objects.equals(this.c, kVar.c) && this.b.equals(kVar.b);
    }

    public final boolean f() {
        return this.c != null;
    }

    public final boolean g() {
        return this.a.isEmpty() && this.c == null && this.b.g();
    }

    public final int hashCode() {
        return Objects.hash(this.a, this.c, this.b);
    }

    public final int i() {
        return this.a.size();
    }

    public final boolean j() {
        return f() || this.a.size() > 0 || this.b.d() || this.b.h() > 0;
    }

    public final k a(k kVar) {
        l lVarA;
        if (g()) {
            return kVar;
        }
        if (kVar.g()) {
            return this;
        }
        AbstractC1639am abstractC1639amA = AbstractC1639am.g().a((Iterable) this.a).a((Iterable) kVar.a).a();
        if (f()) {
            lVarA = this.c;
            lVarA.getClass();
            if (kVar.f()) {
                lVarA = lVarA.a(kVar.c());
            }
        } else {
            lVarA = kVar.c;
        }
        return new k(abstractC1639amA, lVarA, this.b.a(kVar.b));
    }

    private k(List list, l lVar, c cVar) {
        boolean z = e;
        if (!z && cVar == null) {
            throw new AssertionError();
        }
        this.a = list;
        this.c = lVar;
        this.b = cVar;
        if (!z && g()) {
            throw new AssertionError();
        }
    }

    public final Consumer a(W0 w0) {
        return this.b.a(w0);
    }

    public final c a() {
        return this.b;
    }

    public final AbstractC2412ko a(C0702m c0702m, C2409kl c2409kl, AbstractC1915eM abstractC1915eM, InterfaceC3176v00 interfaceC3176v00) {
        boolean z = e;
        if (!z && this.c == null) {
            throw new AssertionError();
        }
        if (!z && !this.c.l()) {
            throw new AssertionError();
        }
        AbstractC3292wV abstractC3292wVI = this.c.i();
        abstractC3292wVI.getClass();
        AbstractC2412ko abstractC2412koA = abstractC3292wVI.a(c0702m, c2409kl.h(), c2409kl.e, interfaceC3176v00);
        abstractC2412koA.b(abstractC1915eM);
        return abstractC2412koA;
    }

    public final void a(C0702m c0702m, o5 o5Var, AbstractC1476Wj abstractC1476Wj) {
        AbstractC3292wV abstractC3292wVC = this.c.i().b(c0702m, c0702m.E(), abstractC1476Wj);
        if (!e && !abstractC3292wVC.b(c0702m, o5Var)) {
            throw new AssertionError();
        }
    }

    public final C0684j2 a(C0722p1 c0722p1, o5 o5Var) {
        C0737r2 c0737r2B;
        if (g()) {
            return o5Var.getReference();
        }
        if (g()) {
            c0737r2B = o5Var.F();
        } else {
            l lVar = this.c;
            C0767v2 c0767v2G = lVar != null ? lVar.g() : o5Var.G();
            C0767v2[] c0767v2Arr = o5Var.D().a;
            if (!g()) {
                int length = c0767v2Arr.length;
                c cVar = this.b;
                C0767v2[] c0767v2Arr2 = new C0767v2[this.a.size() + (length - (cVar.h() - M.a(o5Var.e().B1() && cVar.a(0).c())))];
                int iD1 = o5Var.e().d1();
                int i = 0;
                for (int i2 = 0; i2 < c0767v2Arr.length; i2++) {
                    b bVarA = this.b.a(i2 + iD1);
                    bVarA.getClass();
                    if (bVarA instanceof a) {
                        c0767v2Arr2[i] = c0767v2Arr[i2];
                        i++;
                    } else if (bVarA instanceof l) {
                        l lVarB = bVarA.b();
                        if (!e && c0767v2Arr[i2] != lVarB.h()) {
                            throw new AssertionError();
                        }
                        c0767v2Arr2[i] = lVarB.g();
                        i++;
                    } else {
                        continue;
                    }
                }
                Iterator it = this.a.iterator();
                while (it.hasNext()) {
                    c0767v2Arr2[i] = ((AbstractC3078th) it.next()).a(c0722p1);
                    i++;
                }
                c0767v2Arr = c0767v2Arr2;
            }
            c0737r2B = c0722p1.b(c0767v2G, c0767v2Arr);
        }
        C0684j2 reference = o5Var.getReference();
        return c0722p1.a(reference.e, c0737r2B, reference.f);
    }

    public final k a(C0702m c0702m, H2 h2, AbstractC1476Wj abstractC1476Wj) {
        c cVarA = this.b.a(c0702m, h2, abstractC1476Wj);
        l lVarB = f() ? this.c.a(c0702m, h2, abstractC1476Wj) : null;
        return (cVarA == this.b && lVarB == this.c) ? this : new k(this.a, lVarB, cVarA);
    }

    public final k a(l lVar) {
        return Objects.equals(this.c, lVar) ? this : new k(this.a, lVar, this.b);
    }

    public final k a(List list) {
        if (list.isEmpty()) {
            return this;
        }
        ArrayList arrayList = new ArrayList(list.size() + this.a.size());
        arrayList.addAll(this.a);
        arrayList.addAll(list);
        return new k(arrayList, this.c, this.b);
    }
}
