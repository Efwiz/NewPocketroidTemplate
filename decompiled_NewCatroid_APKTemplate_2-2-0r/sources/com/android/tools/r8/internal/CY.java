package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0767v2;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class CY {
    static final /* synthetic */ boolean b = true;
    IdentityHashMap a = new IdentityHashMap();

    CY() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static FY a(AbstractC0727q0 abstractC0727q0, C0767v2 c0767v2) {
        return new FY(abstractC0727q0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static FY b(AbstractC0727q0 abstractC0727q0, C0767v2 c0767v2) {
        return new FY(abstractC0727q0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.utils.F3 f3, FY fy) {
        f3.a(fy.a, fy.d.values(), fy.c.values());
    }

    final void b(final BiConsumer biConsumer) {
        this.a.values().forEach(new Consumer() { // from class: com.android.tools.r8.internal.CY$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((FY) obj).b(biConsumer);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(Map map, C0767v2 c0767v2, FY fy) {
        map.put(c0767v2, new EY.d(fy.a, fy.b, C1188Mm.a(fy.c), C1188Mm.a(fy.d), fy.e, fy.f));
    }

    final HashMap b(AbstractC0727q0 abstractC0727q0) {
        FY fy = (FY) this.a.get(abstractC0727q0.getType());
        if (b || fy != null) {
            return fy.e;
        }
        throw new AssertionError();
    }

    final DY a(C0767v2 c0767v2) {
        FY fy = (FY) this.a.get(c0767v2);
        if (b || fy != null) {
            return fy.b;
        }
        throw new AssertionError();
    }

    final void a(final com.android.tools.r8.utils.F3 f3) {
        this.a.values().forEach(new Consumer() { // from class: com.android.tools.r8.internal.CY$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                CY.a(f3, (FY) obj);
            }
        });
    }

    final void a(final BiConsumer biConsumer) {
        this.a.values().forEach(new Consumer() { // from class: com.android.tools.r8.internal.CY$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((FY) obj).a(biConsumer);
            }
        });
    }

    final void a(final AbstractC0727q0 abstractC0727q0, com.android.tools.r8.graph.W0 w0) {
        FY fy = (FY) this.a.computeIfAbsent(abstractC0727q0.d, new Function() { // from class: com.android.tools.r8.internal.CY$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return CY.b(abstractC0727q0, (C0767v2) obj);
            }
        });
        if (!FY.g) {
            fy.getClass();
            if (w0.J0() != fy.a.d) {
                throw new AssertionError();
            }
        }
        fy.c.put(w0.getReference(), w0);
    }

    final void a(final AbstractC0727q0 abstractC0727q0, com.android.tools.r8.graph.T0 t0) {
        FY fy = (FY) this.a.computeIfAbsent(abstractC0727q0.d, new Function() { // from class: com.android.tools.r8.internal.CY$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return CY.a(abstractC0727q0, (C0767v2) obj);
            }
        });
        if (!FY.g) {
            fy.getClass();
            if (t0.J0() != fy.a.d) {
                throw new AssertionError();
            }
        }
        fy.d.put(t0.getReference(), t0);
    }

    final void a(C0767v2 c0767v2, DY dy) {
        DY dy2;
        FY fy = (FY) this.a.get(c0767v2);
        if (!b && fy == null) {
            throw new AssertionError();
        }
        fy.getClass();
        boolean z = FY.g;
        if (!z && dy == null) {
            throw new AssertionError();
        }
        if (!z && (dy2 = fy.b) != null && dy != dy2) {
            throw new AssertionError();
        }
        fy.b = dy;
    }

    final void a(C0684j2 c0684j2, EY.c cVar) {
        FY fy = (FY) this.a.get(c0684j2.B0());
        if (!b && fy == null) {
            throw new AssertionError();
        }
        fy.a(c0684j2, cVar);
    }

    final void a(com.android.tools.r8.graph.Y0 y0, EY.a aVar) {
        FY fy = (FY) this.a.get(y0.B0());
        if (!b && fy == null) {
            throw new AssertionError();
        }
        fy.a(y0, aVar);
    }

    public final HashMap a(AbstractC0727q0 abstractC0727q0) {
        FY fy = (FY) this.a.get(abstractC0727q0.getType());
        if (b || fy != null) {
            return fy.f;
        }
        throw new AssertionError();
    }

    final EY a() {
        final IdentityHashMap identityHashMap = new IdentityHashMap();
        this.a.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.CY$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                CY.a(identityHashMap, (C0767v2) obj, (FY) obj2);
            }
        });
        return new EY(C1188Mm.a(identityHashMap));
    }
}
