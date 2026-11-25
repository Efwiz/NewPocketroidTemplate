package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0725p4;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0754u0;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.synthesis.AbstractC3847f;
import com.android.tools.r8.synthesis.C3851i;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Me, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1179Me {
    static final /* synthetic */ boolean b = true;
    private final C0702m a;

    public C1179Me(C0702m c0702m) {
        this.a = c0702m;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C3851i c3851i) {
    }

    final C0684j2 b(C0767v2 c0767v2, C2630ne c2630ne) {
        boolean z = b;
        if (!z) {
            com.android.tools.r8.synthesis.L.b bVarA = new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.Me$$ExternalSyntheticLambda16
                @Override // com.android.tools.r8.synthesis.C
                public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                    return l.n;
                }
            }.a(this.a.D().b());
            if (!z && !c2630ne.a(this.a).equals(bVarA)) {
                throw new AssertionError();
            }
        }
        return this.a.a().a(c0767v2, c2630ne.e(), c2630ne.d());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.tools.r8.synthesis.L.b c(C2630ne c2630ne, com.android.tools.r8.synthesis.L l) {
        return c2630ne.a(this.a);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.tools.r8.synthesis.L.b d(C2630ne c2630ne, com.android.tools.r8.synthesis.L l) {
        return c2630ne.a(this.a);
    }

    public final C0684j2 a(final C0684j2 c0684j2, final C0684j2 c0684j3, InterfaceC1098Je interfaceC1098Je, C1230Ob c1230Ob) {
        com.android.tools.r8.graph.o5 o5VarB = this.a.D().b(new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.Me$$ExternalSyntheticLambda11
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                return l.N;
            }
        }, c1230Ob.a(), this.a, new Consumer() { // from class: com.android.tools.r8.internal.Me$$ExternalSyntheticLambda15
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c0684j2, c0684j3, (com.android.tools.r8.synthesis.H) obj);
            }
        });
        interfaceC1098Je.m(o5VarB, c1230Ob.b());
        return o5VarB.getReference();
    }

    public final void b(final C2404kg c2404kg, InterfaceC1126Ke interfaceC1126Ke) {
        boolean z = b;
        if (!z && interfaceC1126Ke == null) {
            throw new AssertionError();
        }
        if (!z && !this.a.Y().C1.d()) {
            throw new AssertionError();
        }
        final C2630ne c2630neB = c2404kg.b();
        AbstractC0727q0 abstractC0727q0A = this.a.a(c2630neB.a());
        final C0729q2 c0729q2A = a(c2404kg, interfaceC1126Ke);
        this.a.D().a(new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.Me$$ExternalSyntheticLambda4
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                return this.f$0.d(c2630neB, l);
            }
        }, abstractC0727q0A, this.a, new Consumer() { // from class: com.android.tools.r8.internal.Me$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c0729q2A, c2404kg, (com.android.tools.r8.synthesis.P) obj);
            }
        }, new Me$$ExternalSyntheticLambda3(interfaceC1126Ke));
    }

    public final AbstractC0727q0 b(final C2404kg c2404kg, InterfaceC1098Je interfaceC1098Je) {
        boolean z = b;
        if (!z && interfaceC1098Je == null) {
            throw new AssertionError();
        }
        final C2630ne c2630neD = c2404kg.d();
        AbstractC0727q0 abstractC0727q0A = this.a.a(c2630neD.a());
        if (this.a.Y().C1.d()) {
            return this.a.D().a(new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.Me$$ExternalSyntheticLambda8
                @Override // com.android.tools.r8.synthesis.C
                public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                    return this.f$0.b(c2630neD, l);
                }
            }, abstractC0727q0A, this.a);
        }
        com.android.tools.r8.graph.T tN = abstractC0727q0A.N();
        if (!z && tN == null) {
            throw new AssertionError();
        }
        com.android.tools.r8.synthesis.D D = this.a.D();
        com.android.tools.r8.synthesis.C c = new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.Me$$ExternalSyntheticLambda9
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                return l.n;
            }
        };
        C0702m c0702m = this.a;
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.Me$$ExternalSyntheticLambda10
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c2404kg, (C3851i) obj);
            }
        };
        Objects.requireNonNull(interfaceC1098Je);
        return D.a(c, tN, c0702m, consumer, new Me$$ExternalSyntheticLambda19(interfaceC1098Je));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final C0684j2 c0684j2, final C0684j2 c0684j3, com.android.tools.r8.synthesis.H h) {
        com.android.tools.r8.synthesis.H hA = h.a(C0725p4.Q());
        C0722p1 c0722p1A = this.a.a();
        c0722p1A.getClass();
        hA.a(c0722p1A.a(c0684j2.h, c0684j2.e)).a(new com.android.tools.r8.synthesis.G() { // from class: com.android.tools.r8.internal.Me$$ExternalSyntheticLambda21
            @Override // com.android.tools.r8.synthesis.G
            public final com.android.tools.r8.graph.W a(C0684j2 c0684j4) {
                return this.f$0.a(c0684j3, c0684j2, c0684j4);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.tools.r8.graph.W a(C0684j2 c0684j2, C0684j2 c0684j3, C0684j2 c0684j4) {
        return C1071Ii.a(this.a.a()).d(c0684j2, false).b(c0684j3).c().a();
    }

    public final C0684j2 a(C0684j2 c0684j2, InterfaceC1098Je interfaceC1098Je) {
        AbstractC0727q0 abstractC0727q0C = this.a.c(c0684j2.B0());
        if (abstractC0727q0C != null && !(abstractC0727q0C instanceof C0754u0)) {
            if (b || abstractC0727q0C.c(c0684j2) != null) {
                return c0684j2;
            }
            throw new AssertionError();
        }
        boolean z = b;
        if (!z && interfaceC1098Je == null) {
            throw new AssertionError();
        }
        com.android.tools.r8.synthesis.D D = this.a.D();
        C0759u2 c0759u2C0 = c0684j2.C0();
        C0737r2 c0737r2H0 = c0684j2.H0();
        com.android.tools.r8.synthesis.C c = new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.Me$$ExternalSyntheticLambda17
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                return l.g;
            }
        };
        C0767v2 c0767v2B0 = c0684j2.B0();
        C0702m c0702m = this.a;
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.Me$$ExternalSyntheticLambda18
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1179Me.a((C3851i) obj);
            }
        };
        Objects.requireNonNull(interfaceC1098Je);
        com.android.tools.r8.graph.S sA = D.a(c0759u2C0, c0737r2H0, c, c0767v2B0, c0702m, consumer, new Me$$ExternalSyntheticLambda19(interfaceC1098Je), new Consumer() { // from class: com.android.tools.r8.internal.Me$$ExternalSyntheticLambda20
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1179Me.a((com.android.tools.r8.synthesis.H) obj);
            }
        });
        if (z || sA.getReference() == c0684j2) {
            return c0684j2;
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.tools.r8.synthesis.L.b b(C2630ne c2630ne, com.android.tools.r8.synthesis.L l) {
        return c2630ne.a(this.a);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(com.android.tools.r8.synthesis.H h) {
        h.a(C0725p4.Q()).a((com.android.tools.r8.synthesis.G) null);
    }

    final C0684j2 a(C2404kg c2404kg) {
        if (b || c2404kg.c().a(this.a) == null) {
            return c2404kg.c().c();
        }
        throw new AssertionError();
    }

    private C0684j2 a(C0767v2 c0767v2, C2630ne c2630ne) {
        boolean z = b;
        if (!z) {
            com.android.tools.r8.synthesis.L.b bVarA = new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.Me$$ExternalSyntheticLambda7
                @Override // com.android.tools.r8.synthesis.C
                public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                    return l.m;
                }
            }.a(this.a.D().b());
            if (!z && !c2630ne.a(this.a).equals(bVarA)) {
                throw new AssertionError();
            }
        }
        return this.a.a().a(c0767v2, this.a.a().c(c2630ne.c()), c2630ne.d());
    }

    public final C0684j2 a(final C2404kg c2404kg, final InterfaceC1098Je interfaceC1098Je) {
        boolean z = b;
        if (!z && interfaceC1098Je == null) {
            throw new AssertionError();
        }
        final C2630ne c2630neB = c2404kg.b();
        AbstractC0727q0 abstractC0727q0A = this.a.a(c2630neB.a());
        if (this.a.Y().C1.d()) {
            C0729q2 c0729q2A = this.a.D().a(new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.Me$$ExternalSyntheticLambda12
                @Override // com.android.tools.r8.synthesis.C
                public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                    return this.f$0.a(c2630neB, l);
                }
            }, abstractC0727q0A, this.a);
            C0684j2 c0684j2A = a(c0729q2A.d, c2630neB);
            if (z || c0729q2A.c(c0684j2A) != null) {
                return c0684j2A;
            }
            throw new AssertionError();
        }
        final AbstractC0727q0 abstractC0727q0B = b(c2404kg, interfaceC1098Je);
        com.android.tools.r8.graph.T tN = abstractC0727q0A.N();
        if (!z && tN == null) {
            throw new AssertionError();
        }
        com.android.tools.r8.synthesis.D D = this.a.D();
        com.android.tools.r8.synthesis.C c = new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.Me$$ExternalSyntheticLambda13
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                return l.m;
            }
        };
        C0702m c0702m = this.a;
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.Me$$ExternalSyntheticLambda14
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(abstractC0727q0B, c2404kg, interfaceC1098Je, (C3851i) obj);
            }
        };
        Objects.requireNonNull(interfaceC1098Je);
        C0754u0 c0754u0A = D.a(c, tN, c0702m, consumer, new Me$$ExternalSyntheticLambda19(interfaceC1098Je));
        C0684j2 c0684j2A2 = a(c0754u0A.d, c2630neB);
        if (z || c0754u0A.c(c0684j2A2) != null) {
            return c0684j2A2;
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.tools.r8.synthesis.L.b a(C2630ne c2630ne, com.android.tools.r8.synthesis.L l) {
        return c2630ne.a(this.a);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(AbstractC0727q0 abstractC0727q0, C2404kg c2404kg, com.android.tools.r8.synthesis.P p) {
        a(p, abstractC0727q0, c2404kg, (InterfaceC1098Je) null);
    }

    public final C0729q2 a(final C2404kg c2404kg, InterfaceC1126Ke interfaceC1126Ke) {
        boolean z = b;
        if (!z && !this.a.Y().C1.d()) {
            throw new AssertionError();
        }
        if (!z && interfaceC1126Ke == null) {
            throw new AssertionError();
        }
        final C2630ne c2630neD = c2404kg.d();
        AbstractC0727q0 abstractC0727q0A = this.a.a(c2630neD.a());
        com.android.tools.r8.synthesis.D D = this.a.D();
        com.android.tools.r8.synthesis.C c = new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.Me$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                return this.f$0.c(c2630neD, l);
            }
        };
        C0702m c0702m = this.a;
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.Me$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c2404kg, (com.android.tools.r8.synthesis.P) obj);
            }
        };
        Objects.requireNonNull(interfaceC1126Ke);
        return D.a(c, abstractC0727q0A, c0702m, consumer, new Me$$ExternalSyntheticLambda3(interfaceC1126Ke));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final AbstractC3847f abstractC3847f, final C2404kg c2404kg) {
        abstractC3847f.h().a(new Consumer() { // from class: com.android.tools.r8.internal.Me$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(abstractC3847f, c2404kg, (com.android.tools.r8.synthesis.H) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(AbstractC3847f abstractC3847f, C2404kg c2404kg, com.android.tools.r8.synthesis.H h) {
        C0684j2 c0684j2B = b(abstractC3847f.e(), c2404kg.d());
        h.a(c0684j2B.C0()).a(c0684j2B.H0()).b().a(C0725p4.b(5121, false));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final AbstractC3847f abstractC3847f, final AbstractC0727q0 abstractC0727q0, final C2404kg c2404kg, final InterfaceC1098Je interfaceC1098Je) {
        abstractC3847f.a(new Consumer() { // from class: com.android.tools.r8.internal.Me$$ExternalSyntheticLambda22
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(abstractC3847f, c2404kg, abstractC0727q0, interfaceC1098Je, (com.android.tools.r8.synthesis.H) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(AbstractC3847f abstractC3847f, final C2404kg c2404kg, final AbstractC0727q0 abstractC0727q0, final InterfaceC1098Je interfaceC1098Je, com.android.tools.r8.synthesis.H h) {
        C0684j2 c0684j2A = a(abstractC3847f.e(), c2404kg.b());
        h.a(c0684j2A.C0()).a(c0684j2A.H0()).a(C0725p4.Q()).b().a(new com.android.tools.r8.synthesis.G() { // from class: com.android.tools.r8.internal.Me$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.synthesis.G
            public final com.android.tools.r8.graph.W a(C0684j2 c0684j2) {
                return this.f$0.a(c2404kg, abstractC0727q0, interfaceC1098Je, c0684j2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.tools.r8.graph.W a(C2404kg c2404kg, AbstractC0727q0 abstractC0727q0, InterfaceC1098Je interfaceC1098Je, C0684j2 c0684j2) {
        if (!this.a.Y().C1.d()) {
            return null;
        }
        C0684j2 c0684j2A = a(a(c2404kg), interfaceC1098Je);
        C0684j2 c0684j2B = b(abstractC0727q0.d, c2404kg.d());
        boolean z = b;
        if (!z && abstractC0727q0.c(c0684j2B) == null) {
            throw new AssertionError();
        }
        if (z || c2404kg.a().isEmpty()) {
            return new C2323jg(c0684j2.B0(), c0684j2A, c0684j2B, new LinkedHashMap(), this.a).b();
        }
        throw new AssertionError();
    }
}
