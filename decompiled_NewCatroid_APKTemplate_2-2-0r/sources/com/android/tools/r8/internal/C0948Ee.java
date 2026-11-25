package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0725p4;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0754u0;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.synthesis.C3851i;
import java.util.Objects;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ee, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0948Ee {
    static final /* synthetic */ boolean c = true;
    private final C0702m a;
    private final C0722p1 b;

    public C0948Ee(C0702m c0702m) {
        this.a = c0702m;
        this.b = c0702m.a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.tools.r8.graph.W a(C0684j2 c0684j2) {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.tools.r8.graph.W b(C0684j2 c0684j2) {
        return null;
    }

    private void a(com.android.tools.r8.synthesis.H h, C0767v2 c0767v2, C0767v2 c0767v3, com.android.tools.r8.synthesis.G g) {
        h.a(this.b.j0).a(this.b.b(c0767v3, c0767v2)).a(C0725p4.a(4105, false)).b().a(g);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(final C0767v2 c0767v2, final C0767v2 c0767v3, final Iterable iterable, com.android.tools.r8.synthesis.H h) {
        a(h, c0767v2, c0767v3, new com.android.tools.r8.synthesis.G() { // from class: com.android.tools.r8.internal.Ee$$ExternalSyntheticLambda14
            @Override // com.android.tools.r8.synthesis.G
            public final com.android.tools.r8.graph.W a(C0684j2 c0684j2) {
                return this.f$0.b(iterable, c0767v2, c0767v3, c0684j2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.tools.r8.graph.W b(Iterable iterable, C0767v2 c0767v2, C0767v2 c0767v3, C0684j2 c0684j2) {
        return new ZH(this.a, c0684j2.B0(), iterable, c0767v2, c0767v3).b();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(C0767v2 c0767v2, C0767v2 c0767v3, com.android.tools.r8.synthesis.H h) {
        a(h, c0767v2, c0767v3, new com.android.tools.r8.synthesis.G() { // from class: com.android.tools.r8.internal.Ee$$ExternalSyntheticLambda15
            @Override // com.android.tools.r8.synthesis.G
            public final com.android.tools.r8.graph.W a(C0684j2 c0684j2) {
                return C0948Ee.b(c0684j2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void b(final Iterable iterable, final C0767v2 c0767v2, final C0767v2 c0767v3, com.android.tools.r8.synthesis.P p) {
        ((com.android.tools.r8.synthesis.P) p.a(new Consumer() { // from class: com.android.tools.r8.internal.Ee$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c0767v2, c0767v3, iterable, (com.android.tools.r8.synthesis.H) obj);
            }
        })).a(new Consumer() { // from class: com.android.tools.r8.internal.Ee$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.b(c0767v3, c0767v2, iterable, (com.android.tools.r8.synthesis.H) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(final C0767v2 c0767v2, final C0767v2 c0767v3, final Iterable iterable, com.android.tools.r8.synthesis.H h) {
        a(h, c0767v2, c0767v3, new com.android.tools.r8.synthesis.G() { // from class: com.android.tools.r8.internal.Ee$$ExternalSyntheticLambda12
            @Override // com.android.tools.r8.synthesis.G
            public final com.android.tools.r8.graph.W a(C0684j2 c0684j2) {
                return this.f$0.a(iterable, c0767v2, c0767v3, c0684j2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.tools.r8.graph.W a(Iterable iterable, C0767v2 c0767v2, C0767v2 c0767v3, C0684j2 c0684j2) {
        return new ZH(this.a, c0684j2.B0(), iterable, c0767v2, c0767v3).b();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void b(final C0767v2 c0767v2, final C0767v2 c0767v3, C3851i c3851i) {
        ((C3851i) c3851i.a(new Consumer() { // from class: com.android.tools.r8.internal.Ee$$ExternalSyntheticLambda16
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c0767v2, c0767v3, (com.android.tools.r8.synthesis.H) obj);
            }
        })).a(new Consumer() { // from class: com.android.tools.r8.internal.Ee$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.b(c0767v3, c0767v2, (com.android.tools.r8.synthesis.H) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C0767v2 c0767v2, C0767v2 c0767v3, com.android.tools.r8.synthesis.H h) {
        a(h, c0767v2, c0767v3, new com.android.tools.r8.synthesis.G() { // from class: com.android.tools.r8.internal.Ee$$ExternalSyntheticLambda13
            @Override // com.android.tools.r8.synthesis.G
            public final com.android.tools.r8.graph.W a(C0684j2 c0684j2) {
                return C0948Ee.a(c0684j2);
            }
        });
    }

    final C0684j2 a(AbstractC0727q0 abstractC0727q0, C0767v2 c0767v2, C0767v2 c0767v3, InterfaceC1398Ue interfaceC1398Ue) {
        AbstractC0727q0 abstractC0727q0A = a(abstractC0727q0, interfaceC1398Ue);
        C0722p1 c0722p1 = this.b;
        C0684j2 c0684j2A = c0722p1.a(abstractC0727q0A.d, c0722p1.b(c0767v3, c0767v2), this.b.j0);
        if (c || abstractC0727q0A.b(c0684j2A) != null) {
            return c0684j2A;
        }
        throw new AssertionError();
    }

    final C0684j2 a(AbstractC0727q0 abstractC0727q0, C0767v2 c0767v2, C0767v2 c0767v3) {
        C0729q2 c0729q2A = this.a.D().a(new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.Ee$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                return l.u;
            }
        }, abstractC0727q0, this.a);
        C0722p1 c0722p1 = this.b;
        C0684j2 c0684j2A = c0722p1.a(c0729q2A.d, c0722p1.b(c0767v3, c0767v2), this.b.j0);
        if (c || c0729q2A.f(c0684j2A) != null) {
            return c0684j2A;
        }
        throw new AssertionError();
    }

    final void a(AbstractC0727q0 abstractC0727q0, final S6 s6) {
        boolean z = c;
        if (!z && s6 == null) {
            throw new AssertionError();
        }
        if (!z && !abstractC0727q0.e0()) {
            throw new AssertionError();
        }
        final C0767v2 c0767v2 = abstractC0727q0.d;
        final C0767v2 c0767v2A = C3378xe.a(this.a, c0767v2);
        if (!z && !this.a.Y().C1.d()) {
            throw new AssertionError();
        }
        C0729q2 c0729q2A0 = abstractC0727q0.a0();
        final Iterable iterableC = AbstractC2734ot.c(c0729q2A0.N1(), new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.Ee$$ExternalSyntheticLambda8
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return ((com.android.tools.r8.graph.T0) obj).b1();
            }
        });
        com.android.tools.r8.synthesis.D D = this.a.D();
        com.android.tools.r8.synthesis.C c2 = new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.Ee$$ExternalSyntheticLambda9
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                return l.u;
            }
        };
        C0702m c0702m = this.a;
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.Ee$$ExternalSyntheticLambda10
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.b(iterableC, c0767v2, c0767v2A, (com.android.tools.r8.synthesis.P) obj);
            }
        };
        Objects.requireNonNull(s6);
        D.a(c2, c0729q2A0, c0702m, consumer, new Consumer() { // from class: com.android.tools.r8.internal.Ee$$ExternalSyntheticLambda11
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                s6.e((C0729q2) obj);
            }
        });
    }

    private AbstractC0727q0 a(AbstractC0727q0 abstractC0727q0, final InterfaceC1398Ue interfaceC1398Ue) {
        if (!c && interfaceC1398Ue == null) {
            throw new AssertionError();
        }
        if (abstractC0727q0.e0()) {
            return this.a.D().a(new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.Ee$$ExternalSyntheticLambda4
                @Override // com.android.tools.r8.synthesis.C
                public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                    return l.u;
                }
            }, abstractC0727q0, this.a);
        }
        final C0767v2 c0767v2 = abstractC0727q0.d;
        final C0767v2 c0767v2A = C3378xe.a(this.a, c0767v2);
        com.android.tools.r8.synthesis.D D = this.a.D();
        com.android.tools.r8.synthesis.C c2 = new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.Ee$$ExternalSyntheticLambda5
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                return l.u;
            }
        };
        com.android.tools.r8.graph.T tN = abstractC0727q0.N();
        C0702m c0702m = this.a;
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.Ee$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.b(c0767v2, c0767v2A, (C3851i) obj);
            }
        };
        Objects.requireNonNull(interfaceC1398Ue);
        return D.a(c2, tN, c0702m, consumer, new Consumer() { // from class: com.android.tools.r8.internal.Ee$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                interfaceC1398Ue.e((C0754u0) obj);
            }
        });
    }
}
