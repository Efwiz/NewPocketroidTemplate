package com.android.tools.r8.internal;

import com.android.tools.r8.dex.C0572k$$ExternalSyntheticLambda8;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.C0635d2;
import com.android.tools.r8.graph.C0664h;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0725p4;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.C0772w0;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.synthesis.C3842a;
import com.android.tools.r8.utils.C3879c3;
import com.android.tools.r8.utils.EnumC3891f;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

/* JADX INFO: renamed from: com.android.tools.r8.internal.t2, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3033t2 {
    static final /* synthetic */ boolean g = true;
    private final C0702m a;
    private final ConcurrentHashMap b = new ConcurrentHashMap();
    private final Set c = OU.a();
    private final Set d = OU.a();
    private final com.android.tools.r8.androidapi.b e;
    private final InterfaceC3179v2 f;

    public C3033t2(C0702m c0702m) {
        this.a = c0702m;
        this.e = c0702m.d();
        this.f = InterfaceC3179v2.a(c0702m);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.tools.r8.graph.W a(com.android.tools.r8.graph.D5 d5, C0684j2 c0684j2) {
        return d5;
    }

    public final void a(ExecutorService executorService) {
        if (this.a.Y().Y0() && this.a.Y().a().d) {
            List listA = com.android.tools.r8.utils.V1.a(this.a.f().d(), new Predicate() { // from class: com.android.tools.r8.internal.t2$$ExternalSyntheticLambda4
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return ((C0729q2) obj).f2();
                }
            });
            if (listA.size() > 2) {
                C3879c3.a(listA, new Consumer() { // from class: com.android.tools.r8.internal.t2$$ExternalSyntheticLambda5
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f$0.b((C0729q2) obj);
                    }
                }, executorService);
            } else {
                listA.forEach(new Consumer() { // from class: com.android.tools.r8.internal.t2$$ExternalSyntheticLambda5
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f$0.b((C0729q2) obj);
                    }
                });
            }
        }
        if (!this.c.isEmpty()) {
            this.c.forEach(new Consumer() { // from class: com.android.tools.r8.internal.t2$$ExternalSyntheticLambda6
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a((C0635d2) obj);
                }
            });
            C3842a c3842aA = this.a.D().a(this.a.f().b());
            if (this.a.f().i()) {
                C0702m<C3733i> c0702mI0 = this.a.i0();
                c0702mI0.d(((C3733i) c0702mI0.f()).b(c3842aA));
            } else if (this.a.f().h()) {
                this.a.h0().d(this.a.f().n().a(c3842aA));
            } else {
                this.a.j0().d(new C0664h(this.a.f().g().a(this.a.e()), this.a.f().f()));
            }
        }
        this.f.b(this.a);
    }

    public final void b(final C0729q2 c0729q2) {
        if (!g && !c0729q2.f2()) {
            throw new AssertionError();
        }
        if (a(c0729q2)) {
            return;
        }
        if (this.a.Y().B0().d(EnumC3891f.L)) {
            c0729q2.H0().forEach(new Consumer() { // from class: com.android.tools.r8.internal.t2$$ExternalSyntheticLambda13
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.b(c0729q2, (C0767v2) obj);
                }
            });
        }
        c0729q2.g(new Consumer() { // from class: com.android.tools.r8.internal.t2$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c0729q2, (com.android.tools.r8.graph.o5) obj);
            }
        }, new C0572k$$ExternalSyntheticLambda8());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C0635d2 c0635d2) {
        a(c0635d2, com.android.tools.r8.graph.D5.a(this.a.a().q3), this.f);
    }

    private boolean a(C0729q2 c0729q2) {
        com.android.tools.r8.synthesis.D D = this.a.D();
        return D.a(c0729q2.getType(), new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.t2$$ExternalSyntheticLambda2
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                return l.R;
            }
        }) || D.a(c0729q2.getType(), new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.t2$$ExternalSyntheticLambda3
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                return l.S;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0729q2 c0729q2, com.android.tools.r8.graph.o5 o5Var) {
        com.android.tools.r8.graph.W wA1 = o5Var.e().a1();
        if (wA1.B0()) {
            for (C0772w0.b bVar : wA1.P().h) {
                for (C0772w0.b.a aVar : bVar.a) {
                    b(c0729q2, AbstractC2964s2.a(this.a.E(), aVar.getType()));
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void b(final C0729q2 c0729q2, C0767v2 c0767v2) {
        if (!c0767v2.O0() || c0767v2 == this.a.a().Z1) {
            return;
        }
        C0759u2 c0759u2D0 = c0767v2.D0();
        C0759u2 c0759u2 = this.a.a().G;
        c0759u2D0.getClass();
        if (c0759u2D0.a(c0759u2.e)) {
            return;
        }
        com.android.tools.r8.utils.O3.a(this.d, c0767v2).a(new BiConsumer() { // from class: com.android.tools.r8.internal.t2$$ExternalSyntheticLambda12
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(c0729q2, (C0767v2) obj, (com.android.tools.r8.utils.O3) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C0729q2 c0729q2, C0767v2 c0767v2, com.android.tools.r8.utils.O3 o3) {
        AbstractC0727q0 abstractC0727q0C = this.a.c(c0767v2);
        if (abstractC0727q0C == null || !abstractC0727q0C.f0()) {
            return;
        }
        com.android.tools.r8.androidapi.h hVarA = this.e.a(abstractC0727q0C.d, com.android.tools.r8.androidapi.h.f());
        if (hVarA.a(this.a.o()) && hVarA.y()) {
            o3.a(abstractC0727q0C.H0());
            this.c.add(abstractC0727q0C.d0());
            ((Set) this.b.computeIfAbsent(abstractC0727q0C.d0(), com.android.tools.r8.utils.Z1.a(new IP$$ExternalSyntheticLambda3()))).add(c0729q2);
        }
    }

    private void a(final C0635d2 c0635d2, final com.android.tools.r8.graph.D5 d5, final InterfaceC3179v2 interfaceC3179v2) {
        final C0722p1 c0722p1A = this.a.a();
        C0767v2 type = c0635d2.getType();
        if (type != this.a.a().Z1) {
            C0759u2 c0759u2D0 = type.D0();
            C0759u2 c0759u2 = this.a.a().G;
            c0759u2D0.getClass();
            if (c0759u2D0.a(c0759u2.e) || this.a.Y().C1.b((AbstractC0744s2) c0635d2.getType())) {
                return;
            }
            Set set = (Set) this.b.get(c0635d2);
            if (set != null) {
                C0729q2 c0729q2A = this.a.f().g().a(new Supplier() { // from class: com.android.tools.r8.internal.t2$$ExternalSyntheticLambda8
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return C3033t2.a();
                    }
                }, new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.t2$$ExternalSyntheticLambda9
                    @Override // com.android.tools.r8.synthesis.C
                    public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                        return l.c;
                    }
                }, c0635d2.getType(), set, this.a, new Consumer() { // from class: com.android.tools.r8.internal.t2$$ExternalSyntheticLambda10
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        C3033t2.a(c0635d2, c0722p1A, d5, (com.android.tools.r8.synthesis.P) obj);
                    }
                }, new Consumer() { // from class: com.android.tools.r8.internal.t2$$ExternalSyntheticLambda11
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        interfaceC3179v2.a((C0729q2) obj, c0635d2);
                    }
                });
                if (interfaceC3179v2.isEmpty()) {
                    return;
                }
                Iterator it = set.iterator();
                while (it.hasNext()) {
                    interfaceC3179v2.a(c0729q2A, c0635d2, (C0729q2) it.next());
                }
                return;
            }
            throw new C3405y10("Attempt to create a global synthetic with no contexts");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C2142hF a() {
        return new C2142hF("API stubbing");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(C0635d2 c0635d2, final C0722p1 c0722p1, final com.android.tools.r8.graph.D5 d5, com.android.tools.r8.synthesis.P p) {
        ((com.android.tools.r8.synthesis.P) ((com.android.tools.r8.synthesis.P) p.a(c0635d2.n1())).b(Arrays.asList(c0635d2.f1().a))).a(new Consumer() { // from class: com.android.tools.r8.internal.t2$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C3033t2.a(c0722p1, d5, (com.android.tools.r8.synthesis.H) obj);
            }
        });
        if (c0635d2.isInterface()) {
            p.h();
        }
        if (c0635d2.e.h()) {
            return;
        }
        p.j();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(C0722p1 c0722p1, final com.android.tools.r8.graph.D5 d5, com.android.tools.r8.synthesis.H h) {
        h.a(c0722p1.Z0).a(c0722p1.b(c0722p1.D1, new C0767v2[0])).a(C0725p4.b(4104, true)).a(new com.android.tools.r8.synthesis.G() { // from class: com.android.tools.r8.internal.t2$$ExternalSyntheticLambda7
            @Override // com.android.tools.r8.synthesis.G
            public final com.android.tools.r8.graph.W a(C0684j2 c0684j2) {
                return C3033t2.a(d5, c0684j2);
            }
        });
    }
}
