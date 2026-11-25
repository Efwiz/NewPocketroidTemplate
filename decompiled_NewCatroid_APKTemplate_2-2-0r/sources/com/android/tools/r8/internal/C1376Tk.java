package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Tk, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1376Tk {
    static final /* synthetic */ boolean c = true;
    private final C0680j a;
    private final Set b = OU.c();

    public C1376Tk(C0680j c0680j) {
        this.a = c0680j;
    }

    public final void a(final C1297Qk c1297Qk, final C2522mD c2522mD, BiConsumer biConsumer) {
        c1297Qk.q().forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.Tk$$ExternalSyntheticLambda12
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(c2522mD, (com.android.tools.r8.graph.Y0) obj, (com.android.tools.r8.graph.Y0) obj2);
            }
        });
        c1297Qk.d().forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.Tk$$ExternalSyntheticLambda13
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.c(c2522mD, (C0684j2) obj, (C0767v2) obj2);
            }
        });
        c1297Qk.p().forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.Tk$$ExternalSyntheticLambda14
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.d(c2522mD, (C0684j2) obj, (C0767v2) obj2);
            }
        });
        c1297Qk.n().forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.Tk$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.b(c2522mD, c1297Qk, (C0684j2) obj, (C0767v2) obj2);
            }
        });
        c1297Qk.o().forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.Tk$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.b(c2522mD, (C0684j2) obj, (C0684j2) obj2);
            }
        });
        c1297Qk.m().forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.Tk$$ExternalSyntheticLambda3
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.b(c2522mD, c1297Qk, (C0684j2) obj, (C0684j2) obj2);
            }
        });
        biConsumer.accept("Cannot retarget missing references: ", this.b);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public void d(final C2522mD c2522mD, C0684j2 c0684j2, final C0767v2 c0767v2) {
        a(c0684j2, new Consumer() { // from class: com.android.tools.r8.internal.Tk$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.c(c2522mD, c0767v2, (com.android.tools.r8.graph.W0) obj);
            }
        }, new Consumer() { // from class: com.android.tools.r8.internal.Tk$$ExternalSyntheticLambda10
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.d(c2522mD, c0767v2, (com.android.tools.r8.graph.W0) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public void c(final C2522mD c2522mD, C0767v2 c0767v2, com.android.tools.r8.graph.W0 w0) {
        C0680j c0680j = this.a;
        Objects.requireNonNull(c2522mD);
        BiConsumer biConsumer = new BiConsumer() { // from class: com.android.tools.r8.internal.Tk$$ExternalSyntheticLambda8
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                c2522mD.d((C0684j2) obj, (C0684j2) obj2);
            }
        };
        C0684j2 reference = w0.getReference();
        biConsumer.accept(reference, reference.b(c0767v2, c0680j.a()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C2522mD c2522mD, com.android.tools.r8.graph.Y0 y0, com.android.tools.r8.graph.Y0 y1) {
        if (this.a.c(y0.e).a(y0) == null) {
            this.b.add(y0);
        } else {
            c2522mD.a(y0, y1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void c(C2522mD c2522mD, C0684j2 c0684j2, C0767v2 c0767v2) {
        AbstractC0727q0 abstractC0727q0C = this.a.c(c0684j2.e);
        C0684j2 c0684j2A = c0684j2.a(this.a.a().a(c0767v2, c0684j2.H0().f), this.a.a());
        com.android.tools.r8.graph.W0 w0C = abstractC0727q0C.c(c0684j2A);
        if (w0C == null) {
            this.b.add(c0684j2A);
        } else {
            if (w0C.E0()) {
                this.a.b().d.e.c("Static method " + w0C + " is flagged as having a covariant return type, which is not possible.");
                return;
            }
            if (a(abstractC0727q0C, w0C)) {
                this.a.b().d.e.c("Covariant retargeting of non final method " + w0C + " which could lead to invalid runtime execution in overrides.");
            }
            c2522mD.a(c0684j2, c0684j2A);
        }
    }

    private void a(C0684j2 c0684j2, Consumer consumer, Consumer consumer2) {
        AbstractC0727q0 abstractC0727q0C = this.a.c(c0684j2.e);
        com.android.tools.r8.graph.W0 w0C = abstractC0727q0C.c(c0684j2);
        if (w0C == null) {
            this.b.add(c0684j2);
        } else {
            if (w0C.E0()) {
                consumer.accept(w0C);
                return;
            }
            if (a(abstractC0727q0C, w0C)) {
                this.a.b().d.e.c("Retargeting non final method " + w0C + " which could lead to invalid runtime execution in overrides.");
            }
            consumer2.accept(w0C);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void b(final C2522mD c2522mD, final C0684j2 c0684j2, final C0684j2 c0684j3) {
        a(c0684j2, new Consumer() { // from class: com.android.tools.r8.internal.Tk$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                c2522mD.d(c0684j2, c0684j3);
            }
        }, new Consumer() { // from class: com.android.tools.r8.internal.Tk$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                c2522mD.c(c0684j2, c0684j3);
            }
        });
    }

    private void a(C0684j2 c0684j2, Consumer consumer) {
        AbstractC0727q0 abstractC0727q0C = this.a.c(c0684j2.e);
        com.android.tools.r8.graph.W0 w0C = abstractC0727q0C.c(c0684j2);
        if (w0C == null) {
            this.b.add(c0684j2);
        } else {
            if (w0C.E0()) {
                this.a.b().d.e.a("Cannot generate emulated dispatch for static method " + w0C);
                return;
            }
            if (!a(abstractC0727q0C, w0C)) {
                this.a.b().d.e.c("Generating (seemingly unnecessary) emulated dispatch for final method " + w0C);
            }
            consumer.accept(w0C);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void b(final C2522mD c2522mD, final C1297Qk c1297Qk, C0684j2 c0684j2, final C0767v2 c0767v2) {
        a(c0684j2, new Consumer() { // from class: com.android.tools.r8.internal.Tk$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c2522mD, c1297Qk, c0767v2, (com.android.tools.r8.graph.W0) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void b(final C2522mD c2522mD, final C1297Qk c1297Qk, C0684j2 c0684j2, final C0684j2 c0684j3) {
        a(c0684j2, new Consumer() { // from class: com.android.tools.r8.internal.Tk$$ExternalSyntheticLambda11
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c2522mD, c1297Qk, c0684j3, (com.android.tools.r8.graph.W0) obj);
            }
        });
    }

    private static boolean a(AbstractC0727q0 abstractC0727q0, com.android.tools.r8.graph.W0 w0) {
        if (c || !w0.E0()) {
            return (abstractC0727q0.e.h() || w0.z1()) ? false : true;
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C2522mD c2522mD, C1297Qk c1297Qk, C0684j2 c0684j2, com.android.tools.r8.graph.W0 w0) {
        if (a(w0, this.a, c1297Qk)) {
            c2522mD.b(w0.getReference(), c0684j2);
            return;
        }
        C2630ne c2630ne = new C2630ne(c0684j2, null);
        c2522mD.a(w0.getReference(), new C2404kg(new C2630ne(w0.getReference(), EnumC2687oD.RETARGET_INTERFACE), new C2630ne(w0.getReference(), EnumC2687oD.RETARGET_CLASS), c2630ne, new LinkedHashMap()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C2522mD c2522mD, C1297Qk c1297Qk, C0767v2 c0767v2, com.android.tools.r8.graph.W0 w0) {
        C0684j2 c0684j2A = this.a.a().a(c0767v2, this.a.a().c(w0.getReference()), w0.K0());
        if (a(w0, this.a, c1297Qk)) {
            c2522mD.b(w0.getReference(), c0684j2A);
            return;
        }
        C2630ne c2630ne = new C2630ne(c0684j2A, null);
        c2522mD.a(w0.getReference(), new C2404kg(new C2630ne(w0.getReference(), EnumC2687oD.RETARGET_INTERFACE), new C2630ne(w0.getReference(), EnumC2687oD.RETARGET_CLASS), c2630ne, new LinkedHashMap()));
    }

    private static boolean a(com.android.tools.r8.graph.W0 w0, C0680j c0680j, C1297Qk c1297Qk) {
        final Map<C0767v2, C0767v2> mapI = c1297Qk.i();
        if (mapI.isEmpty()) {
            return false;
        }
        final C0684j2 reference = w0.getReference();
        AbstractC0727q0 abstractC0727q0C = c0680j.c(w0.J0());
        if (c || !mapI.containsKey(abstractC0727q0C.getType())) {
            return c0680j.a(abstractC0727q0C, new com.android.tools.r8.utils.G3() { // from class: com.android.tools.r8.internal.Tk$$ExternalSyntheticLambda5
                @Override // com.android.tools.r8.utils.G3
                public final Object a(Object obj, Object obj2, Object obj3) {
                    return C1376Tk.a(mapI, reference, (C0767v2) obj, (AbstractC0727q0) obj2, (Boolean) obj3);
                }
            }).f();
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static com.android.tools.r8.utils.D3 a(Map map, C0684j2 c0684j2, C0767v2 c0767v2, AbstractC0727q0 abstractC0727q0, Boolean bool) {
        return com.android.tools.r8.utils.D3.a(!(abstractC0727q0.isInterface() && map.containsKey(abstractC0727q0.getType()) && abstractC0727q0.c(c0684j2) != null));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void d(final C2522mD c2522mD, C0767v2 c0767v2, final com.android.tools.r8.graph.W0 w0) {
        C0680j c0680j = this.a;
        BiConsumer biConsumer = new BiConsumer() { // from class: com.android.tools.r8.internal.Tk$$ExternalSyntheticLambda4
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(c2522mD, w0, (C0684j2) obj, (C0684j2) obj2);
            }
        };
        C0684j2 reference = w0.getReference();
        biConsumer.accept(reference, reference.b(c0767v2, c0680j.a()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C2522mD c2522mD, com.android.tools.r8.graph.W0 w0, C0684j2 c0684j2, C0684j2 c0684j3) {
        C0767v2 c0767v2J0 = w0.J0();
        C0722p1 c0722p1A = this.a.a();
        c2522mD.c(c0684j2, c0722p1A.a(c0684j3.e, c0722p1A.a(c0684j3.h, c0767v2J0), c0684j3.f));
    }
}
