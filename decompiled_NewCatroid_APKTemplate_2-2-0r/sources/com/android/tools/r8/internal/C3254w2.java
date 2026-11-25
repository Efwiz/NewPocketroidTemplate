package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.w2, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3254w2 extends AbstractC2094ge {
    static final /* synthetic */ boolean j = true;
    private final M4 f;
    private final C3011sk g;
    private final C3011sk h;
    private final IdentityHashMap i;

    public C3254w2(C0702m c0702m) {
        super(c0702m.a(), AbstractC1476Wj.e());
        this.f = M4.d();
        this.g = C3011sk.l();
        this.h = C3011sk.l();
        this.i = new IdentityHashMap();
        for (C0729q2 c0729q2 : ((C0680j) c0702m.f()).d()) {
            if (c0702m.E().d(c0729q2.getType()) == c0729q2.getType()) {
                a(c0702m, c0729q2);
                Iterator<com.android.tools.r8.graph.T0> it = c0729q2.W0().iterator();
                while (it.hasNext()) {
                    com.android.tools.r8.graph.Y0 reference = it.next().getReference();
                    com.android.tools.r8.graph.Y0 y0A = c0702m.E().a(reference);
                    if (y0A != reference) {
                        com.android.tools.r8.graph.Y0 y0 = (com.android.tools.r8.graph.Y0) this.g.a(reference, y0A);
                        if (!j && y0 != null) {
                            throw new AssertionError();
                        }
                    }
                }
                Iterator<com.android.tools.r8.graph.W0> it2 = c0729q2.M1().iterator();
                while (it2.hasNext()) {
                    C0684j2 reference2 = it2.next().getReference();
                    C0684j2 c0684j2B = c0702m.E().b(reference2);
                    C0684j2 c0684j2 = (C0684j2) this.h.g().get(c0684j2B);
                    if (c0684j2 == null) {
                        this.h.put(reference2, c0684j2B);
                    } else if (c0702m.E().c(c0684j2B) == c0684j2) {
                        this.i.put(reference2, c0684j2B);
                    } else {
                        this.h.a(reference2, c0684j2B);
                        this.i.put(c0684j2, c0684j2B);
                    }
                }
            }
        }
        com.android.tools.r8.utils.Z1.a(this.h);
        com.android.tools.r8.utils.Z1.a(this.i);
    }

    private void a(C0702m c0702m, C0729q2 c0729q2) {
        final C0767v2 type = c0729q2.getType();
        ArrayList arrayListA = AbstractC2763pC.a(c0702m.E().b(type));
        if (arrayListA.size() == 1 && arrayListA.get(0) == type) {
            return;
        }
        arrayListA.forEach(new Consumer() { // from class: com.android.tools.r8.internal.w2$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(type, (C0767v2) obj);
            }
        });
        this.f.b(type, c0702m.E().a(type));
    }

    @Override // com.android.tools.r8.internal.AbstractC2094ge, com.android.tools.r8.internal.AbstractC1476Wj
    public final Iterable b(C0767v2 c0767v2) {
        Set setA = this.f.a(c0767v2);
        return setA.isEmpty() ? AbstractC1639am.c(c0767v2) : setA;
    }

    @Override // com.android.tools.r8.internal.AbstractC2094ge, com.android.tools.r8.internal.AbstractC1476Wj
    public final com.android.tools.r8.graph.proto.k d(AbstractC1476Wj abstractC1476Wj, C0684j2 c0684j2) {
        return AbstractC1476Wj.e().d(abstractC1476Wj, c0684j2);
    }

    @Override // com.android.tools.r8.internal.AbstractC2094ge, com.android.tools.r8.internal.DH
    public final C0767v2 e(C0767v2 c0767v2) {
        return (C0767v2) this.f.getOrDefault(c0767v2, c0767v2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.tools.r8.internal.AbstractC2094ge, com.android.tools.r8.internal.DH
    public final C0684j2 f(C0684j2 c0684j2) {
        return this.i.containsKey(c0684j2) ? (C0684j2) this.i.get(c0684j2) : (C0684j2) this.h.getOrDefault(c0684j2, c0684j2);
    }

    @Override // com.android.tools.r8.internal.AbstractC2094ge, com.android.tools.r8.internal.AbstractC1476Wj
    public final boolean g() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2094ge
    public final C0684j2 h(C0684j2 c0684j2) {
        return (C0684j2) this.h.g().getOrDefault(c0684j2, c0684j2);
    }

    @Override // com.android.tools.r8.internal.AbstractC2094ge, com.android.tools.r8.internal.AbstractC1476Wj
    /* JADX INFO: renamed from: b */
    public final C0684j2 c(AbstractC1476Wj abstractC1476Wj, C0684j2 c0684j2) {
        return this != abstractC1476Wj ? (C0684j2) this.h.g().getOrDefault(c0684j2, c0684j2) : c0684j2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C0767v2 c0767v2, C0767v2 c0767v3) {
        if (!j && this.f.containsKey(c0767v3)) {
            throw new AssertionError();
        }
        this.f.a(c0767v3, c0767v2);
    }

    @Override // com.android.tools.r8.internal.AbstractC2094ge, com.android.tools.r8.internal.AbstractC1476Wj
    public final C0767v2 a(C0767v2 c0767v2) {
        Object objC = this.f.c(c0767v2);
        Object obj = c0767v2;
        if (objC != null) {
            obj = objC;
        }
        return (C0767v2) obj;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.tools.r8.internal.AbstractC2094ge, com.android.tools.r8.internal.AbstractC1476Wj
    public final com.android.tools.r8.graph.Y0 a(com.android.tools.r8.graph.Y0 y0) {
        return (com.android.tools.r8.graph.Y0) this.g.getOrDefault(y0, y0);
    }

    @Override // com.android.tools.r8.internal.AbstractC2094ge, com.android.tools.r8.internal.AbstractC1476Wj
    /* JADX INFO: renamed from: a */
    public final com.android.tools.r8.graph.Y0 b(AbstractC1476Wj abstractC1476Wj, com.android.tools.r8.graph.Y0 y0) {
        return this == abstractC1476Wj ? y0 : (com.android.tools.r8.graph.Y0) this.g.g().getOrDefault(y0, y0);
    }
}
