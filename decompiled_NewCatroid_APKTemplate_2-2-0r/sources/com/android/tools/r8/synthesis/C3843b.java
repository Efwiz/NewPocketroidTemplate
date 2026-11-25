package com.android.tools.r8.synthesis;

import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.AbstractC1048Hm;
import com.android.tools.r8.internal.AbstractC2178hm;
import com.android.tools.r8.internal.C0862Bm;
import com.android.tools.r8.utils.T2;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.synthesis.b, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3843b {
    static final /* synthetic */ boolean f = true;
    private final C3844c a;
    private IdentityHashMap b = null;
    private IdentityHashMap c = null;
    private C0862Bm d = null;
    private IdentityHashMap e = null;

    public C3843b(C3844c c3844c) {
        this.a = c3844c;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ List c(C0767v2 c0767v2) {
        return new ArrayList();
    }

    public final C3843b a(Q q) {
        return a(new S(q.f(), q.d(), ((C0729q2) q.d).getType()));
    }

    final C3843b b() {
        if (this.b != null) {
            if (this.d == null) {
                this.d = AbstractC1048Hm.g();
            }
            this.d.a(this.b.keySet());
        }
        if (this.c != null) {
            if (this.d == null) {
                this.d = AbstractC1048Hm.g();
            }
            this.d.a(this.c.keySet());
        }
        return this;
    }

    public final void a(AbstractC3855m abstractC3855m) {
        if (abstractC3855m.h()) {
            abstractC3855m.c().a(new Consumer() { // from class: com.android.tools.r8.synthesis.b$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a((I) obj);
                }
            }, new Consumer() { // from class: com.android.tools.r8.synthesis.b$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a((Q) obj);
                }
            });
        }
    }

    public final C3843b a(S s) {
        if (this.b == null) {
            this.b = new IdentityHashMap();
        }
        ((List) this.b.computeIfAbsent(s.d, new Function() { // from class: com.android.tools.r8.synthesis.b$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C3843b.a((C0767v2) obj);
            }
        })).add(s);
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ List a(C0767v2 c0767v2) {
        return new ArrayList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Set b(C0767v2 c0767v2) {
        return T2.b(new C0767v2[0]);
    }

    public final C3843b a(I i) {
        return a(i.j());
    }

    public final C3843b a(J j) {
        if (this.c == null) {
            this.c = new IdentityHashMap();
        }
        ((List) this.c.computeIfAbsent(j.d.e, new Function() { // from class: com.android.tools.r8.synthesis.b$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C3843b.c((C0767v2) obj);
            }
        })).add(j);
        return this;
    }

    public final void a(InterfaceC3862u interfaceC3862u) {
        if (interfaceC3862u.isEmpty()) {
            return;
        }
        if (this.e == null) {
            this.e = new IdentityHashMap();
        }
        interfaceC3862u.forEach(new BiConsumer() { // from class: com.android.tools.r8.synthesis.b$$ExternalSyntheticLambda4
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a((C0767v2) obj, (Set) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C0767v2 c0767v2, Set set) {
        ((Set) this.e.computeIfAbsent(c0767v2, new Function() { // from class: com.android.tools.r8.synthesis.b$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C3843b.b((C0767v2) obj);
            }
        })).addAll(set);
    }

    public final C3844c a() {
        AbstractC2178hm abstractC2178hmB;
        IdentityHashMap identityHashMap = this.b;
        if (identityHashMap == null && this.c == null && this.e == null) {
            if (f || this.d == null) {
                return this.a;
            }
            throw new AssertionError();
        }
        AbstractC2178hm abstractC2178hmA = C3844c.a(identityHashMap, this.a.c);
        AbstractC2178hm abstractC2178hmA2 = C3844c.a(this.c, this.a.b);
        C0862Bm c0862Bm = this.d;
        AbstractC1048Hm abstractC1048HmA = c0862Bm == null ? this.a.e : c0862Bm.a();
        IdentityHashMap identityHashMap2 = this.e;
        if (identityHashMap2 == null) {
            abstractC2178hmB = this.a.d;
        } else {
            abstractC2178hmB = C3844c.b(identityHashMap2, this.a.d);
        }
        return new C3844c(this.a.a, abstractC2178hmA2, abstractC2178hmA, abstractC2178hmB, abstractC1048HmA);
    }
}
