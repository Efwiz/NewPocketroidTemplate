package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.C0664h;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.utils.InterfaceC3905h3;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class CH extends AbstractC3135uW {
    static final /* synthetic */ boolean c = true;
    private final Set a;
    private final Map b;

    public CH(LinkedHashMap linkedHashMap) {
        if (!c && linkedHashMap.isEmpty()) {
            throw new AssertionError();
        }
        this.a = com.android.tools.r8.utils.T2.a(com.android.tools.r8.utils.T2.a(linkedHashMap.keySet(), new Function() { // from class: com.android.tools.r8.internal.CH$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((AbstractC0744s2) obj).C();
            }
        }));
        this.b = com.android.tools.r8.utils.Z1.a(linkedHashMap);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1882e1
    public final boolean a(C0767v2 c0767v2) {
        return this.b.containsKey(c0767v2);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1882e1
    public final InterfaceC2197i1 b(C0684j2 c0684j2) {
        return (C3510zW) this.b.get(c0684j2);
    }

    @Override // com.android.tools.r8.internal.AbstractC3135uW
    public final boolean c() {
        return false;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1882e1
    public final boolean a(C0684j2 c0684j2) {
        return this.b.containsKey(c0684j2);
    }

    @Override // com.android.tools.r8.internal.AbstractC3135uW
    public final boolean b(C0767v2 c0767v2) {
        return this.a.contains(c0767v2);
    }

    @Override // com.android.tools.r8.internal.AbstractC3135uW
    public final AbstractC3135uW c(C0702m c0702m) {
        final C0664h c0664hF = c0702m.f();
        return a(new BiConsumer() { // from class: com.android.tools.r8.internal.CH$$ExternalSyntheticLambda10
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                CH.a(c0664hF, (C3362xW) obj, (AbstractC3135uW.a) obj2);
            }
        }, new BiConsumer() { // from class: com.android.tools.r8.internal.CH$$ExternalSyntheticLambda11
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                CH.a(c0664hF, (C3510zW) obj, (AbstractC3135uW.a) obj2);
            }
        });
    }

    @Override // com.android.tools.r8.internal.AbstractC3135uW
    public final void a(InterfaceC3905h3 interfaceC3905h3) throws Exception {
        a(interfaceC3905h3, interfaceC3905h3);
    }

    @Override // com.android.tools.r8.internal.AbstractC3135uW
    public final AbstractC3135uW b(final C0702m c0702m) {
        return a(new BiConsumer() { // from class: com.android.tools.r8.internal.CH$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) throws Exception {
                CH.a((C3362xW) obj, (AbstractC3135uW.a) obj2, c0702m);
            }
        }, new BiConsumer() { // from class: com.android.tools.r8.internal.CH$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) throws Exception {
                CH.a((C3510zW) obj, (AbstractC3135uW.a) obj2, c0702m);
            }
        });
    }

    @Override // com.android.tools.r8.internal.InterfaceC1882e1
    public final void a(InterfaceC3905h3 interfaceC3905h3, InterfaceC3905h3 interfaceC3905h4) throws Exception {
        Iterator it = this.b.values().iterator();
        while (it.hasNext()) {
            ((CW) it.next()).a(interfaceC3905h3, interfaceC3905h4);
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC3135uW
    public final AbstractC3135uW a(final AbstractC1476Wj abstractC1476Wj) {
        return a(new BiConsumer() { // from class: com.android.tools.r8.internal.CH$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                CH.a(abstractC1476Wj, (C3362xW) obj, (AbstractC3135uW.a) obj2);
            }
        }, new BiConsumer() { // from class: com.android.tools.r8.internal.CH$$ExternalSyntheticLambda5
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                CH.a(abstractC1476Wj, (C3510zW) obj, (AbstractC3135uW.a) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(AbstractC1476Wj abstractC1476Wj, C3362xW c3362xW, AbstractC3135uW.a aVar) {
        C3293wW c3293wWD = C3362xW.d();
        C0767v2 c0767v2E = c3362xW.e();
        abstractC1476Wj.getClass();
        aVar.a(c3293wWD.a(abstractC1476Wj.a(AbstractC1476Wj.e(), c0767v2E)).build());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(AbstractC1476Wj abstractC1476Wj, C3510zW c3510zW, AbstractC3135uW.a aVar) {
        aVar.a(C3510zW.d().b(abstractC1476Wj.c((AbstractC1476Wj) null, c3510zW.c())).d());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(CW cw, final AbstractC3135uW.a aVar, final C0702m c0702m) throws Exception {
        InterfaceC3905h3 interfaceC3905h3 = new InterfaceC3905h3() { // from class: com.android.tools.r8.internal.CH$$ExternalSyntheticLambda3
            @Override // com.android.tools.r8.utils.InterfaceC3905h3
            public final void accept(Object obj) {
                CH.a(aVar, c0702m, (C3362xW) obj);
            }
        };
        Objects.requireNonNull(aVar);
        cw.a(interfaceC3905h3, new InterfaceC3905h3() { // from class: com.android.tools.r8.internal.CH$$ExternalSyntheticLambda4
            @Override // com.android.tools.r8.utils.InterfaceC3905h3
            public final void accept(Object obj) {
                aVar.a((C3510zW) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(AbstractC3135uW.a aVar, C0702m c0702m, C3362xW c3362xW) {
        C0729q2 c0729q2D = c0702m.e().d(c3362xW.e());
        if (c0729q2D != null) {
            int iC = aVar.c();
            aVar.a(C3362xW.d().a(c0729q2D.getType()).build());
            if (aVar.c() > iC) {
                a(c0729q2D, aVar, c0702m);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(AbstractC3135uW.a aVar, C0702m c0702m, C0767v2 c0767v2) {
        C0729q2 c0729q2D = c0702m.e().d(c0767v2);
        if (c0729q2D != null) {
            int iC = aVar.c();
            aVar.a(C3362xW.d().a(c0729q2D.getType()).build());
            if (aVar.c() > iC) {
                a(c0729q2D, aVar, c0702m);
            }
        }
    }

    private static void a(C0729q2 c0729q2, final AbstractC3135uW.a aVar, final C0702m c0702m) {
        c0729q2.d(new Consumer() { // from class: com.android.tools.r8.internal.CH$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                CH.a(aVar, c0702m, (C0767v2) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(C0664h c0664h, C3362xW c3362xW, AbstractC3135uW.a aVar) {
        if (c0664h.d(c3362xW.e()) != null) {
            aVar.a(c3362xW);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(C0664h c0664h, C3510zW c3510zW, AbstractC3135uW.a aVar) {
        if (c3510zW.c().c(c0664h.d(c3510zW.c().B0())) != null) {
            aVar.a(c3510zW);
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC3135uW
    public final AbstractC3135uW a(final com.android.tools.r8.graph.u5 u5Var) {
        return a(new BiConsumer() { // from class: com.android.tools.r8.internal.CH$$ExternalSyntheticLambda8
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                CH.a(u5Var, (C3362xW) obj, (AbstractC3135uW.a) obj2);
            }
        }, new BiConsumer() { // from class: com.android.tools.r8.internal.CH$$ExternalSyntheticLambda9
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                CH.a(u5Var, (C3510zW) obj, (AbstractC3135uW.a) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(com.android.tools.r8.graph.u5 u5Var, C3362xW c3362xW, AbstractC3135uW.a aVar) {
        if (u5Var.a(c3362xW.e())) {
            return;
        }
        aVar.a(c3362xW);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(com.android.tools.r8.graph.u5 u5Var, C3510zW c3510zW, AbstractC3135uW.a aVar) {
        if (u5Var.a(c3510zW.c())) {
            return;
        }
        aVar.a(c3510zW);
    }

    private AbstractC3135uW a(final BiConsumer biConsumer, final BiConsumer biConsumer2) throws Exception {
        final AbstractC3135uW.a aVar = new AbstractC3135uW.a(this.b.size());
        a(new InterfaceC3905h3() { // from class: com.android.tools.r8.internal.CH$$ExternalSyntheticLambda12
            @Override // com.android.tools.r8.utils.InterfaceC3905h3
            public final void accept(Object obj) {
                biConsumer.accept((C3362xW) obj, aVar);
            }
        }, new InterfaceC3905h3() { // from class: com.android.tools.r8.internal.CH$$ExternalSyntheticLambda13
            @Override // com.android.tools.r8.utils.InterfaceC3905h3
            public final void accept(Object obj) {
                biConsumer2.accept((C3510zW) obj, aVar);
            }
        });
        return aVar.build();
    }
}
