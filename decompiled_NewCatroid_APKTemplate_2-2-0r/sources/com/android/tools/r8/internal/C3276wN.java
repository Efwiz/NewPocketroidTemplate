package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0725p4;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.utils.StringDiagnostic;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.wN, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3276wN implements N6 {
    static final /* synthetic */ boolean c = true;
    private final C0702m a;
    private final C1566Zr b;

    public C3276wN(C0702m c0702m) {
        this.a = c0702m;
        this.b = new C1566Zr(c0702m);
    }

    private void a(final C0729q2 c0729q2, final C2556mg c2556mg, final InterfaceC2721og interfaceC2721og) {
        com.android.tools.r8.synthesis.D D = this.a.D();
        com.android.tools.r8.synthesis.C c2 = new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.wN$$ExternalSyntheticLambda6
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                return l.l;
            }
        };
        C0702m c0702m = this.a;
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.wN$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c0729q2, c2556mg, interfaceC2721og, (com.android.tools.r8.synthesis.P) obj);
            }
        };
        Objects.requireNonNull(interfaceC2721og);
        D.a(c2, c0729q2, c0702m, consumer, new Consumer() { // from class: com.android.tools.r8.internal.wN$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                interfaceC2721og.d((C0729q2) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public void a(C0729q2 c0729q2, final C2556mg c2556mg, final InterfaceC2721og interfaceC2721og, final com.android.tools.r8.synthesis.P p) {
        c0729q2.h(new Consumer() { // from class: com.android.tools.r8.internal.wN$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(p, c2556mg, interfaceC2721og, (com.android.tools.r8.graph.o5) obj);
            }
        }, new Predicate() { // from class: com.android.tools.r8.internal.wN$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C3276wN.a(c2556mg, (com.android.tools.r8.graph.W0) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C2556mg c2556mg, com.android.tools.r8.graph.W0 w0) {
        return c2556mg.a().containsKey(w0.getReference());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(final com.android.tools.r8.synthesis.P p, final C2556mg c2556mg, final InterfaceC2721og interfaceC2721og, final com.android.tools.r8.graph.o5 o5Var) {
        p.a(new Consumer() { // from class: com.android.tools.r8.internal.wN$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(o5Var, c2556mg, p, interfaceC2721og, (com.android.tools.r8.synthesis.H) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(com.android.tools.r8.graph.o5 o5Var, C2556mg c2556mg, com.android.tools.r8.synthesis.P p, InterfaceC2721og interfaceC2721og, com.android.tools.r8.synthesis.H h) {
        a(o5Var, (C2404kg) c2556mg.a().get(o5Var.getReference()), p.e(), h, interfaceC2721og);
    }

    private void a(com.android.tools.r8.graph.o5 o5Var, C2404kg c2404kg, C0767v2 c0767v2, com.android.tools.r8.synthesis.H h, InterfaceC2721og interfaceC2721og) {
        if (!c && o5Var.e().E0()) {
            throw new AssertionError();
        }
        final C0684j2 c0684j2A = this.b.a(c0767v2, c2404kg.b());
        final C0684j2 c0684j2A2 = this.b.a(c2404kg.d());
        final C0684j2 c0684j2A3 = this.b.a(c2404kg.c(), C1797cs.a);
        final LinkedHashMap linkedHashMapA = a(c2404kg, interfaceC2721og);
        h.a(c0684j2A.C0()).a(c0684j2A.H0()).a(C0725p4.Q()).a(new com.android.tools.r8.synthesis.G() { // from class: com.android.tools.r8.internal.wN$$ExternalSyntheticLambda2
            @Override // com.android.tools.r8.synthesis.G
            public final com.android.tools.r8.graph.W a(C0684j2 c0684j2) {
                return this.f$0.a(c0684j2A, c0684j2A3, c0684j2A2, linkedHashMapA, c0684j2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.tools.r8.graph.W a(C0684j2 c0684j2, C0684j2 c0684j3, C0684j2 c0684j4, LinkedHashMap linkedHashMap, C0684j2 c0684j5) {
        return new C2323jg(c0684j2.B0(), c0684j3, c0684j4, linkedHashMap, this.a).b();
    }

    private LinkedHashMap a(C2404kg c2404kg, final InterfaceC2721og interfaceC2721og) {
        final LinkedHashMap linkedHashMap = new LinkedHashMap();
        c2404kg.a().forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.wN$$ExternalSyntheticLambda4
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(linkedHashMap, interfaceC2721og, (C0767v2) obj, (C2630ne) obj2);
            }
        });
        return linkedHashMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(LinkedHashMap linkedHashMap, InterfaceC2721og interfaceC2721og, C0767v2 c0767v2, C2630ne c2630ne) {
        linkedHashMap.put(c0767v2, this.b.a(c2630ne, C1797cs.a));
    }

    @Override // com.android.tools.r8.internal.N6
    public final String a() {
        return "$emulatedInterface";
    }

    @Override // com.android.tools.r8.internal.N6
    public final void a(C1150Lb c1150Lb, final S6 s6) {
        if (!c && !this.a.Y().C1.d()) {
            throw new AssertionError();
        }
        this.a.Y().C1.m().forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.wN$$ExternalSyntheticLambda5
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(s6, (C0767v2) obj, (C2556mg) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(S6 s6, C0767v2 c0767v2, C2556mg c2556mg) {
        AbstractC0727q0 abstractC0727q0C = this.a.c(c0767v2);
        if (abstractC0727q0C != null && abstractC0727q0C.e0()) {
            C0729q2 c0729q2A0 = abstractC0727q0C.a0();
            if (!c && c0729q2A0 == null) {
                throw new AssertionError();
            }
            if (this.a.b(c0729q2A0) || c2556mg.a().isEmpty()) {
                return;
            }
            a(c0729q2A0, c2556mg, s6);
            return;
        }
        a(c0767v2);
    }

    private void a(C0767v2 c0767v2) {
        this.a.Y().e.warning(new StringDiagnostic("Cannot emulate interface " + c0767v2.F0() + " because the interface is missing."));
    }
}
