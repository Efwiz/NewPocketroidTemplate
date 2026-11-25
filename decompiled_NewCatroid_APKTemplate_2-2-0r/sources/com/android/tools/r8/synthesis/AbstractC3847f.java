package com.android.tools.r8.synthesis;

import com.android.tools.r8.ProgramResource;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0658g0;
import com.android.tools.r8.graph.C0706m3;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.C0793w4$$ExternalSyntheticLambda0;
import com.android.tools.r8.graph.C0800x2;
import com.android.tools.r8.graph.T0;
import com.android.tools.r8.graph.W0;
import com.android.tools.r8.origin.Origin;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.synthesis.f, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3847f {
    static final /* synthetic */ boolean s = true;
    private final C0722p1 a;
    private final C0767v2 b;
    private final L.b c;
    private final Origin d;
    private ProgramResource.Kind h;
    private C0767v2 i;
    private boolean e = false;
    private boolean f = true;
    private boolean g = false;
    private C0800x2 j = C0800x2.s0();
    private C0759u2 k = null;
    private boolean l = false;
    private ArrayList m = new ArrayList();
    private ArrayList n = new ArrayList();
    private ArrayList o = new ArrayList();
    private ArrayList p = new ArrayList();
    private ArrayList q = new ArrayList();
    private C0706m3.b r = C0706m3.b.f();

    AbstractC3847f(C0767v2 c0767v2, L.b bVar, C3846e c3846e, C0722p1 c0722p1) {
        this.a = c0722p1;
        this.b = c0767v2;
        this.c = bVar;
        this.d = c3846e.b();
        this.i = c0722p1.Z1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ long a(long j, C0729q2 c0729q2) {
        return j;
    }

    public abstract com.android.tools.r8.graph.I b();

    public final AbstractC3847f b(List list) {
        this.j = list.isEmpty() ? C0800x2.s0() : new C0800x2((C0767v2[]) list.toArray(C0767v2.g));
        return f();
    }

    public final C0722p1 c() {
        return this.a;
    }

    public final L.b d() {
        return this.c;
    }

    public final C0767v2 e() {
        return this.b;
    }

    public abstract AbstractC3847f f();

    public final AbstractC3847f g() {
        this.e = true;
        this.f = false;
        return f();
    }

    public final AbstractC3847f h() {
        g();
        this.g = true;
        return f();
    }

    public final void i() {
        this.l = true;
        f();
    }

    public final void j() {
        this.f = false;
    }

    public final AbstractC3847f a(C0767v2 c0767v2) {
        this.i = c0767v2;
        return f();
    }

    public final AbstractC3847f c(List list) {
        this.m.clear();
        this.m.addAll(list);
        return f();
    }

    public final void a(ProgramResource.Kind kind) {
        this.h = kind;
    }

    public final void a(C0759u2 c0759u2) {
        this.k = c0759u2;
        f();
    }

    public final void b(AbstractCollection abstractCollection) {
        this.p.clear();
        ArrayList arrayList = this.p;
        Objects.requireNonNull(arrayList);
        abstractCollection.forEach(new C0793w4$$ExternalSyntheticLambda0(arrayList));
        f();
    }

    public final AbstractC3847f a(List list) {
        this.n.clear();
        this.n.addAll(list);
        return f();
    }

    public final AbstractC3847f a(AbstractCollection abstractCollection) {
        this.o.clear();
        ArrayList arrayList = this.o;
        Objects.requireNonNull(arrayList);
        abstractCollection.forEach(new C0793w4$$ExternalSyntheticLambda0(arrayList));
        return f();
    }

    public final AbstractC3847f a(Consumer consumer) {
        H h = new H(this);
        consumer.accept(h);
        this.q.add(h);
        return f();
    }

    public final AbstractC0727q0 a() {
        boolean z = this.e;
        int i = z ? 1024 : 0;
        int i2 = this.f ? 16 : 0;
        boolean z2 = this.g;
        int i3 = z2 ? 512 : 0;
        if (!s && z2 && !z) {
            throw new AssertionError();
        }
        com.android.tools.r8.graph.E eK = com.android.tools.r8.graph.E.k(i | i2 | i3 | 4097);
        List listEmptyList = Collections.emptyList();
        List listEmptyList2 = Collections.emptyList();
        List listEmptyList3 = Collections.emptyList();
        List listEmptyList4 = Collections.emptyList();
        Iterator it = this.q.iterator();
        while (it.hasNext()) {
            W0 w0A = ((H) it.next()).a();
            if (w0A.H1()) {
                this.p.add(w0A);
            } else {
                this.o.add(w0A);
            }
        }
        final long jHashCode = (((long) this.n.hashCode()) * 17) + (((long) this.m.hashCode()) * 13) + (((long) this.p.hashCode()) * 11) + (((long) this.o.hashCode()) * 7);
        com.android.tools.r8.graph.I iB = b();
        C0767v2 c0767v2 = this.b;
        ProgramResource.Kind kind = this.h;
        Origin origin = this.d;
        C0767v2 c0767v3 = this.i;
        C0800x2 c0800x2 = this.j;
        C0759u2 c0759u2 = this.k;
        C0706m3.b bVar = this.r;
        C0658g0 c0658g0T0 = C0658g0.t0();
        ArrayList arrayList = this.m;
        T0[] t0Arr = T0.m;
        T0[] t0Arr2 = (T0[]) arrayList.toArray(t0Arr);
        T0[] t0Arr3 = (T0[]) this.n.toArray(t0Arr);
        W0[] w0Arr = W0.s;
        AbstractC0727q0 abstractC0727q0A = iB.a(c0767v2, kind, origin, eK, c0767v3, c0800x2, c0759u2, null, listEmptyList, listEmptyList2, listEmptyList3, null, listEmptyList4, bVar, c0658g0T0, t0Arr2, t0Arr3, w0Arr, w0Arr, this.a.i(), new C0729q2.a() { // from class: com.android.tools.r8.synthesis.f$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.graph.C0729q2.a
            public final long a(C0729q2 c0729q2) {
                return AbstractC3847f.a(jHashCode, c0729q2);
            }
        }, null);
        if (this.l) {
            abstractC0727q0A.Y().m();
        }
        abstractC0727q0A.a((W0[]) this.o.toArray(w0Arr));
        abstractC0727q0A.b((W0[]) this.p.toArray(w0Arr));
        return abstractC0727q0A;
    }
}
