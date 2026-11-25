package com.android.tools.r8.graph;

import com.android.tools.r8.internal.OU;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
class C4 {
    static final /* synthetic */ boolean e = true;
    private final Function a;
    private final boolean b;
    private final IdentityHashMap c = new IdentityHashMap();
    private final Set d = OU.c();

    public static /* synthetic */ IdentityHashMap $r8$lambda$g1bKX7VgjnMTK9HK56GvBI6Fqxo() {
        return new IdentityHashMap();
    }

    public C4(Function function, boolean z) {
        this.a = function;
        this.b = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public void a(final AbstractC0727q0 abstractC0727q0, final Set set, final C0767v2 c0767v2) {
        if (!set.isEmpty() || this.d.add(c0767v2)) {
            final O o = (O) this.a.apply(c0767v2);
            o.b(new Consumer() { // from class: com.android.tools.r8.graph.C4$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a(abstractC0727q0, o, set, c0767v2, (AbstractC0727q0) obj);
                }
            });
        }
    }

    public boolean a(AbstractC0727q0 abstractC0727q0) {
        Map map = (Map) this.c.get(abstractC0727q0);
        if (!e && map == null) {
            throw new AssertionError();
        }
        for (B4 b4 : map.values()) {
            b4.getClass();
            if (b4 != B4.b) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(AbstractC0727q0 abstractC0727q0, C0767v2 c0767v2) {
        a(abstractC0727q0, Collections.emptySet(), c0767v2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(AbstractC0727q0 abstractC0727q0, O o, final Set set, C0767v2 c0767v2, final AbstractC0727q0 abstractC0727q1) {
        if (this.b && abstractC0727q0.f0() && !abstractC0727q1.f0()) {
            return;
        }
        if (o.r()) {
            set = com.android.tools.r8.utils.T2.a((Iterable) set);
            set.add(new B4(c0767v2));
        }
        final Map map = (Map) this.c.computeIfAbsent(abstractC0727q1, com.android.tools.r8.utils.Z1.a(new Supplier() { // from class: com.android.tools.r8.graph.C4$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return C4.$r8$lambda$g1bKX7VgjnMTK9HK56GvBI6Fqxo();
            }
        }));
        set.forEach(new Consumer() { // from class: com.android.tools.r8.graph.C4$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C4.a(map, (B4) obj);
            }
        });
        abstractC0727q1.g.forEach(new Consumer() { // from class: com.android.tools.r8.graph.C4$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(abstractC0727q1, set, (C0767v2) obj);
            }
        });
        C0767v2 c0767v3 = abstractC0727q1.f;
        if (c0767v3 != null) {
            a(abstractC0727q1, set, c0767v3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(Map map, B4 b4) {
        B4 b5;
        B4 b6 = (B4) map.get(b4.a);
        if (b6 == null) {
            map.put(b4.a, b4);
        } else {
            if (b6 == b4 || b6 == (b5 = B4.b)) {
                return;
            }
            map.put(b4.a, b5);
        }
    }
}
