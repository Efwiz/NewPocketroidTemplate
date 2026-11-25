package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0635d2;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0706m3;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0754u0;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.utils.AbstractC3938n2;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.function.BiConsumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ie, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1066Ie implements InterfaceC2135h8 {
    static final /* synthetic */ boolean d = true;
    private final C0702m a;
    private final C1179Me b;
    private final Map c;

    public C1066Ie(C0702m c0702m) {
        this.a = c0702m;
        this.b = new C1179Me(c0702m);
        this.c = c0702m.Y().C1.n();
    }

    @Override // com.android.tools.r8.internal.InterfaceC2135h8
    public final void a(Collection collection, AbstractC2591n8 abstractC2591n8, ExecutorService executorService) {
        if (!d && this.a.Y().C1.d()) {
            throw new AssertionError();
        }
        a(collection, abstractC2591n8);
    }

    private void a(Collection collection, final InterfaceC1153Le interfaceC1153Le) {
        if (!d && this.a.Y().C1.d()) {
            throw new AssertionError();
        }
        final IdentityHashMap identityHashMap = new IdentityHashMap();
        this.c.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.Ie$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C1066Ie.a(identityHashMap, (C0684j2) obj, (C2404kg) obj2);
            }
        });
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            final C0729q2 c0729q2 = (C0729q2) it.next();
            C0767v2 c0767v2 = c0729q2.f;
            if (c0767v2 == null) {
                if (!d && c0729q2.d != this.a.a().Z1) {
                    throw new AssertionError(c0729q2.d.r0());
                }
            } else {
                final AbstractC0727q0 abstractC0727q0C = this.a.c(c0767v2);
                if (abstractC0727q0C != null && abstractC0727q0C.f0() && abstractC0727q0C.d != this.a.a().Z1) {
                    identityHashMap.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.Ie$$ExternalSyntheticLambda1
                        @Override // java.util.function.BiConsumer
                        public final void accept(Object obj, Object obj2) {
                            this.f$0.a(abstractC0727q0C, interfaceC1153Le, c0729q2, (C0767v2) obj, (List) obj2);
                        }
                    });
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Map map, C0684j2 c0684j2, C2404kg c2404kg) {
        map.putIfAbsent(c0684j2.B0(), new ArrayList(1));
        ((List) map.get(c0684j2.B0())).add(c0684j2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(AbstractC0727q0 abstractC0727q0, InterfaceC1153Le interfaceC1153Le, C0729q2 c0729q2, C0767v2 c0767v2, List list) {
        String str;
        C0635d2 c0635d2D0 = abstractC0727q0.d0();
        Map map = this.c;
        while (c0635d2D0.d != this.a.a().Z1) {
            if (c0635d2D0.d != c0767v2) {
                AbstractC0727q0 abstractC0727q0C = this.a.c(c0635d2D0.f);
                if (abstractC0727q0C != null && !(abstractC0727q0C instanceof C0754u0)) {
                    if (abstractC0727q0C.e0()) {
                        return;
                    } else {
                        c0635d2D0 = abstractC0727q0C.d0();
                    }
                } else {
                    Set setKeySet = map.keySet();
                    AbstractC0727q0 abstractC0727q0C2 = this.a.c(c0635d2D0.f);
                    if (abstractC0727q0C2 == null) {
                        str = "missing";
                    } else if (abstractC0727q0C2 instanceof C0754u0) {
                        str = "a classpath class";
                    } else {
                        if (!d) {
                            throw new AssertionError();
                        }
                        str = "INVALID";
                    }
                    this.a.Y().a(abstractC0727q0C2 == null ? c0635d2D0.c : abstractC0727q0C2.c, c0635d2D0.d, c0635d2D0.f, str, setKeySet);
                    return;
                }
            } else {
                if (this.a.b(c0729q2)) {
                    return;
                }
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    C0684j2 c0684j2 = (C0684j2) it.next();
                    C2404kg c2404kg = (C2404kg) this.c.get(c0684j2);
                    AbstractC0727q0 abstractC0727q0B = this.b.b(c2404kg, interfaceC1153Le);
                    if (!c0729q2.g.a(abstractC0727q0B.d) && !this.a.a().f3.contains(c0729q2.getType())) {
                        c0729q2.c(Collections.singletonList(new C0706m3.c(abstractC0727q0B.d)));
                        interfaceC1153Le.a(c0729q2, abstractC0727q0B);
                        C1179Me c1179Me = this.b;
                        c1179Me.getClass();
                        C0684j2 c0684j2B = c1179Me.b(abstractC0727q0B.d, c2404kg.d());
                        if (!C1179Me.b && abstractC0727q0B.c(c0684j2B) == null) {
                            throw new AssertionError();
                        }
                        if (c0729q2.d(c0684j2) == null) {
                            C0684j2 c0684j2A = this.b.a(c2404kg);
                            boolean z = d;
                            if (!z && (c0684j2A == null || c0684j2A == c0684j2B)) {
                                throw new AssertionError();
                            }
                            AbstractC0748t0 abstractC0748t0C = this.a.g().b(c0684j2B, true).c();
                            if (!z && abstractC0748t0C == null) {
                                throw new AssertionError();
                            }
                            com.android.tools.r8.graph.W0 w0A = com.android.tools.r8.graph.W0.a(abstractC0748t0C, c0729q2, c0684j2A, this.a.a());
                            w0A.a(AbstractC3938n2.a);
                            c0729q2.b(w0A);
                            interfaceC1153Le.a(new com.android.tools.r8.graph.o5(c0729q2, w0A), c2404kg);
                        } else {
                            continue;
                        }
                    }
                }
                return;
            }
        }
    }
}
