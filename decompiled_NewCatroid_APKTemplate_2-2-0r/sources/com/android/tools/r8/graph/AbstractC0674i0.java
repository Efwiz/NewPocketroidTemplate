package com.android.tools.r8.graph;

import com.android.tools.r8.DataResourceProvider;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.naming.C3605b;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.graph.i0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC0674i0 {
    static final /* synthetic */ boolean h = true;
    private final ArrayList a;
    final ArrayList b;
    public final com.android.tools.r8.utils.A1 c;
    C3605b d;
    final com.android.tools.r8.utils.y3 e;
    C0696l0 f;
    C0759u2 g;

    public AbstractC0674i0(com.android.tools.r8.utils.A1 a1, com.android.tools.r8.utils.y3 y3Var) {
        this.a = new ArrayList();
        this.b = new ArrayList();
        this.c = a1;
        C0722p1 c0722p1 = a1.a;
        this.e = y3Var;
        new ArrayList();
    }

    public N2.a a() {
        return null;
    }

    public abstract AbstractC0681j0 b();

    public abstract void b(C0729q2 c0729q2);

    public final List c() {
        return this.a;
    }

    abstract AbstractC0674i0 d();

    public final void a(C0696l0 c0696l0) {
        this.f = c0696l0;
    }

    public final synchronized void a(C3605b c3605b) {
        if (!h && this.d != null) {
            throw new AssertionError();
        }
        this.d = c3605b;
        d();
    }

    public final synchronized AbstractC0674i0 a(Predicate predicate) {
        this.a.removeIf(predicate);
        return d();
    }

    public final synchronized AbstractC0674i0 a(AbstractCollection abstractCollection) {
        if (!h && abstractCollection == null) {
            throw new AssertionError();
        }
        this.a.clear();
        this.a.addAll(abstractCollection);
        final C0689k0 c0689k0A = C0696l0.a();
        c0689k0A.b(this.f.g());
        c0689k0A.a(this.f.f());
        this.a.forEach(new Consumer() { // from class: com.android.tools.r8.graph.i0$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c0689k0A, (C0729q2) obj);
            }
        });
        this.f = c0689k0A.a();
        return d();
    }

    public AbstractC0674i0(AbstractC0681j0 abstractC0681j0) {
        ArrayList arrayList = new ArrayList();
        this.a = arrayList;
        ArrayList arrayList2 = new ArrayList();
        this.b = arrayList2;
        this.f = abstractC0681j0.f;
        arrayList.addAll(abstractC0681j0.h());
        arrayList2.addAll(abstractC0681j0.a);
        this.d = abstractC0681j0.g();
        this.e = abstractC0681j0.c;
        this.g = abstractC0681j0.g;
        this.c = abstractC0681j0.d;
        new ArrayList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C0689k0 c0689k0, C0729q2 c0729q2) {
        C0767v2 type = c0729q2.getType();
        if (this.f.c().contains(type)) {
            c0689k0.b(type);
        }
        if (this.f.d().contains(type)) {
            c0689k0.c(type);
        }
        if (this.f.b().contains(type)) {
            c0689k0.a(type);
        }
    }

    public final synchronized void a(DataResourceProvider dataResourceProvider) {
        this.b.add(dataResourceProvider);
    }

    public final synchronized AbstractC0674i0 a(C0759u2 c0759u2) {
        this.g = c0759u2;
        return d();
    }

    public final synchronized void a(C0729q2 c0729q2) {
        this.a.add(c0729q2);
        d();
    }

    public final synchronized AbstractC0674i0 a(AbstractC1639am abstractC1639am) {
        this.a.addAll(abstractC1639am);
        return d();
    }
}
