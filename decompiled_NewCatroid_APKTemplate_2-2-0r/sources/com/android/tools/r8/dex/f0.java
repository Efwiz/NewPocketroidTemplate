package com.android.tools.r8.dex;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC3135uW;
import com.android.tools.r8.internal.BB;
import com.android.tools.r8.internal.C2363kB;
import com.android.tools.r8.internal.C3362xW;
import com.android.tools.r8.internal.C3510zW;
import com.android.tools.r8.internal.CN;
import com.android.tools.r8.utils.InterfaceC3905h3;
import com.android.tools.r8.utils.Z1;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class f0 extends C0584v {
    private final AbstractC3135uW e;
    private final LinkedHashSet f;
    private final LinkedHashSet g;
    private final LinkedHashSet h;
    private final LinkedHashSet i;
    private final LinkedHashSet j;
    private final BB k;
    private final LinkedHashSet l;
    private final LinkedHashSet m;
    private final LinkedHashSet n;

    public f0(C0702m c0702m, I i, AbstractC3135uW abstractC3135uW, r0 r0Var) {
        super(c0702m, i);
        this.e = abstractC3135uW;
        this.f = new LinkedHashSet(i.f().size());
        this.g = new LinkedHashSet(i.c().size());
        this.h = new LinkedHashSet(i.e().size());
        this.i = new LinkedHashSet(i.d().size());
        this.j = new LinkedHashSet(i.g().size());
        this.k = CN.k(i.h().size());
        this.l = new LinkedHashSet(i.i().size());
        this.m = new LinkedHashSet(i.j().size());
        this.n = new LinkedHashSet(i.k().size());
        a(r0Var);
    }

    private void a(final r0 r0Var) {
        final IdentityHashMap identityHashMapA = Z1.a(new com.android.tools.r8.utils.F() { // from class: com.android.tools.r8.dex.f0$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.utils.F
            public final void forEach(BiConsumer biConsumer) {
                f0.a(r0Var, biConsumer);
            }
        }, r0Var.b().size());
        final C2363kB c2363kB = new C2363kB(this.b, 0);
        final e0 e0Var = new e0(this);
        this.e.a(new InterfaceC3905h3() { // from class: com.android.tools.r8.dex.f0$$ExternalSyntheticLambda2
            @Override // com.android.tools.r8.utils.InterfaceC3905h3
            public final void accept(Object obj) {
                this.f$0.b(e0Var, identityHashMapA, (C3362xW) obj);
            }
        }, new InterfaceC3905h3() { // from class: com.android.tools.r8.dex.f0$$ExternalSyntheticLambda3
            @Override // com.android.tools.r8.utils.InterfaceC3905h3
            public final void accept(Object obj) {
                this.f$0.a(e0Var, identityHashMapA, c2363kB, (C3510zW) obj);
            }
        });
    }

    @Override // com.android.tools.r8.dex.C0584v, com.android.tools.r8.dex.W
    public final Collection c() {
        LinkedHashSet linkedHashSet = this.h;
        linkedHashSet.addAll(this.c.e());
        return linkedHashSet;
    }

    @Override // com.android.tools.r8.dex.C0584v, com.android.tools.r8.dex.W
    public final Collection d() {
        LinkedHashSet linkedHashSet = this.i;
        linkedHashSet.addAll(this.c.d());
        return linkedHashSet;
    }

    @Override // com.android.tools.r8.dex.C0584v, com.android.tools.r8.dex.W
    public final Collection e() {
        LinkedHashSet linkedHashSet = this.j;
        linkedHashSet.addAll(this.c.g());
        return linkedHashSet;
    }

    @Override // com.android.tools.r8.dex.C0584v, com.android.tools.r8.dex.W
    public final Collection f() {
        BB bb = this.k;
        bb.addAll(super.f());
        return bb;
    }

    @Override // com.android.tools.r8.dex.C0584v, com.android.tools.r8.dex.W
    public final Collection g() {
        LinkedHashSet linkedHashSet = this.l;
        linkedHashSet.addAll(this.c.i());
        return linkedHashSet;
    }

    @Override // com.android.tools.r8.dex.C0584v, com.android.tools.r8.dex.W
    public final Collection h() {
        LinkedHashSet linkedHashSet = this.m;
        linkedHashSet.addAll(this.c.j());
        return linkedHashSet;
    }

    @Override // com.android.tools.r8.dex.C0584v, com.android.tools.r8.dex.W
    public final Collection i() {
        LinkedHashSet linkedHashSet = this.n;
        linkedHashSet.addAll(this.c.k());
        return linkedHashSet;
    }

    @Override // com.android.tools.r8.dex.C0584v, com.android.tools.r8.dex.W
    public final Collection b() {
        LinkedHashSet linkedHashSet = this.f;
        linkedHashSet.addAll(this.c.f());
        return linkedHashSet;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(BiConsumer biConsumer, C0729q2 c0729q2) {
        biConsumer.accept(c0729q2.getType(), c0729q2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(r0 r0Var, final BiConsumer biConsumer) {
        r0Var.b().forEach(new Consumer() { // from class: com.android.tools.r8.dex.f0$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                f0.a(biConsumer, (C0729q2) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void b(final e0 e0Var, Map map, C3362xW c3362xW) {
        C0729q2 c0729q2 = (C0729q2) map.get(c3362xW.e());
        if (c0729q2 != null) {
            e0Var.a(c0729q2);
            C0767v2 type = c0729q2.getType();
            C0702m c0702m = this.b;
            type.getClass();
            C0759u2 c0759u2C = c0702m.z().c(type);
            c0759u2C.getClass();
            e0Var.a(c0759u2C);
            c0729q2.d(new Consumer() { // from class: com.android.tools.r8.dex.f0$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a(e0Var, (C0767v2) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(e0 e0Var, C0767v2 c0767v2) {
        C0702m c0702m = this.b;
        c0767v2.getClass();
        e0Var.getClass();
        C0759u2 c0759u2C = c0702m.z().c(c0767v2);
        c0759u2C.getClass();
        e0Var.a(c0759u2C);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(e0 e0Var, Map map, C2363kB c2363kB, C3510zW c3510zW) {
        C0684j2 c0684j2C = c3510zW.c();
        o5 o5VarA = c0684j2C.a((C0729q2) map.get(c0684j2C.B0()));
        if (o5VarA != null) {
            c0684j2C.a(this.b, e0Var);
            if (o5VarA.e().q1()) {
                e0Var.a.k.add(o5VarA);
                o5VarA.e().a1().u0().a(this.b, e0Var, c2363kB, o5VarA);
            }
        }
    }

    @Override // com.android.tools.r8.dex.C0584v, com.android.tools.r8.dex.W
    public final Collection a() {
        LinkedHashSet linkedHashSet = this.g;
        linkedHashSet.addAll(this.c.c());
        return linkedHashSet;
    }
}
