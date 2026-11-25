package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0754u0;
import com.android.tools.r8.utils.C3879c3;
import com.android.tools.r8.utils.C3912j0;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Dd, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0919Dd extends C3194vG {
    C0919Dd(C0702m c0702m) {
        super(c0702m);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(AbstractC0727q0 abstractC0727q0) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(C3048tG c3048tG) {
        if (c3048tG.e()) {
            this.a.Y().a(c3048tG);
            throw null;
        }
        AbstractC0727q0 abstractC0727q0B = c3048tG.b();
        for (AbstractC0727q0 abstractC0727q0 : c3048tG.c()) {
            if (abstractC0727q0B.e0() || abstractC0727q0.e0()) {
                this.a.f().a(abstractC0727q0B, abstractC0727q0);
                this.a.f().a(abstractC0727q0, abstractC0727q0B);
            }
        }
    }

    public final void c() {
        a(new Consumer() { // from class: com.android.tools.r8.internal.Dd$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.b((C3048tG) obj);
            }
        }, new Consumer() { // from class: com.android.tools.r8.internal.Dd$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.b((AbstractC0727q0) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0702m c0702m, C3048tG c3048tG) {
        if (c3048tG.e()) {
            c0702m.Y().a(c3048tG);
            throw null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0702m c0702m, AbstractC0727q0 abstractC0727q0) {
        c0702m.Y().a(abstractC0727q0);
        throw null;
    }

    public final void a() {
        a(new Consumer() { // from class: com.android.tools.r8.internal.Dd$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C0919Dd.a((C3048tG) obj);
            }
        }, new Consumer() { // from class: com.android.tools.r8.internal.Dd$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C0919Dd.a((AbstractC0727q0) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C3048tG c3048tG) {
        c3048tG.b().Q0();
        c3048tG.c().forEach(new Consumer() { // from class: com.android.tools.r8.internal.Dd$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((AbstractC0727q0) obj).P0();
            }
        });
    }

    public final void a(C0825Ad c0825Ad, ExecutorService executorService) {
        final ArrayList arrayList = new ArrayList();
        a(new Consumer() { // from class: com.android.tools.r8.internal.Dd$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C0919Dd.a(arrayList, (C3048tG) obj);
            }
        }, C3912j0.b());
        final InterfaceC3345xG interfaceC3345xGA = C2845qN.a(c0825Ad.e(), new C0853Bd(c0825Ad));
        C3879c3.a(arrayList, new Consumer() { // from class: com.android.tools.r8.internal.Dd$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(interfaceC3345xGA, (C0754u0) obj);
            }
        }, executorService);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(AbstractC0727q0 abstractC0727q0) {
        this.a.Y().a(abstractC0727q0);
        throw null;
    }

    public static void b(final C0702m c0702m) {
        C3194vG.a(new Consumer() { // from class: com.android.tools.r8.internal.Dd$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C0919Dd.a(c0702m, (C3048tG) obj);
            }
        }, new Consumer() { // from class: com.android.tools.r8.internal.Dd$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C0919Dd.a(c0702m, (AbstractC0727q0) obj);
            }
        }, c0702m);
    }

    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public final void a(final InterfaceC3345xG interfaceC3345xG, C0754u0 c0754u0) {
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.Dd$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(interfaceC3345xG, (com.android.tools.r8.graph.S) obj);
            }
        };
        c0754u0.getClass();
        c0754u0.d(consumer, CM.b());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(List list, C3048tG c3048tG) {
        AbstractC0727q0 abstractC0727q0B = c3048tG.b();
        abstractC0727q0B.getClass();
        if (abstractC0727q0B instanceof C0754u0) {
            list.add(c3048tG.b().m());
        }
        AbstractC2734ot.a(c3048tG.a(), (Collection) list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(InterfaceC3345xG interfaceC3345xG, com.android.tools.r8.graph.S s) {
        C0890Cd c0890Cd = new C0890Cd(this, s, interfaceC3345xG);
        com.android.tools.r8.graph.W wA1 = s.e().a1();
        if (wA1 != null) {
            wA1.a(s, c0890Cd);
        }
    }
}
