package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.utils.InterfaceC3885d3;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.oB, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2685oB {
    static final /* synthetic */ boolean e = true;
    private final C0702m a;
    private final Set b = OU.c();
    private final Set c;
    private final IdentityHashMap d;

    public C2685oB(C0702m c0702m, com.android.tools.r8.utils.y3 y3Var) {
        Set setC = OU.c();
        this.c = setC;
        this.d = new IdentityHashMap();
        this.a = c0702m;
        y3Var.a("Register optimizers");
        a(new C2672o5(c0702m));
        a(new T5(c0702m));
        a(new C2455lK(c0702m));
        a(new C3497zK(c0702m));
        a(new C3138uX(c0702m));
        a(new XX(c0702m));
        if (c0702m.q()) {
            a(new C0893Cg(c0702m));
        }
        if (DC.a(c0702m)) {
            a(new DC(c0702m));
        }
        y3Var.c();
        y3Var.a("Initialize final fields", new InterfaceC3885d3() { // from class: com.android.tools.r8.internal.oB$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.utils.InterfaceC3885d3
            public final void a() {
                this.f$0.a();
            }
        });
        if (c0702m.q()) {
            y3Var.a("Initialize opt info");
            C3043tB c3043tB = new C3043tB(c0702m);
            c3043tB.d();
            setC.addAll(c3043tB.a());
            y3Var.c();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        for (com.android.tools.r8.graph.O1 o1 : this.a.a().K4) {
            final Set set = this.b;
            Objects.requireNonNull(set);
            o1.a(new Consumer() { // from class: com.android.tools.r8.internal.oB$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    set.add((com.android.tools.r8.graph.Y0) obj);
                }
            });
        }
    }

    public final boolean a(C0767v2 c0767v2) {
        return this.c.contains(c0767v2);
    }

    private void a(InterfaceC2833qB interfaceC2833qB) {
        C0767v2 type = interfaceC2833qB.getType();
        InterfaceC2833qB interfaceC2833qB2 = (InterfaceC2833qB) this.d.put(type, interfaceC2833qB);
        if (!e && interfaceC2833qB2 != null) {
            throw new AssertionError();
        }
        this.c.add(type);
    }

    public final void a(C2409kl c2409kl, TE te, C1230Ob c1230Ob) {
        AbstractC1305Qs abstractC1305QsB0;
        AbstractC0748t0 abstractC0748t0H;
        InterfaceC2833qB interfaceC2833qB;
        Set setC = OU.c();
        B4 b4S = c2409kl.s();
        Set setC2 = OU.c();
        while (b4S.hasNext()) {
            C3332x4 next = b4S.next();
            if (!setC2.contains(next)) {
                InterfaceC2641no interfaceC2641noA = next.a(c2409kl);
                IdentityHashMap identityHashMap = new IdentityHashMap();
                while (interfaceC2641noA.hasNext()) {
                    AbstractC2412ko next2 = interfaceC2641noA.next();
                    if (next2.Y1() && (abstractC0748t0H = (abstractC1305QsB0 = next2.b0()).h(this.a, c2409kl.h())) != null && (interfaceC2833qB = (InterfaceC2833qB) this.d.get(abstractC0748t0H.t())) != null) {
                        if (abstractC1305QsB0.f1() && !abstractC0748t0H.e().C1() && !abstractC1305QsB0.a(this.a, c2409kl.h(), C2332jo.a)) {
                            interfaceC2641noA.s();
                        } else {
                            interfaceC2833qB.a(c2409kl, b4S, interfaceC2641noA, abstractC1305QsB0, abstractC0748t0H, setC, setC2, (InterfaceC2762pB) identityHashMap.computeIfAbsent(interfaceC2833qB, new Function() { // from class: com.android.tools.r8.internal.oB$$ExternalSyntheticLambda2
                                @Override // java.util.function.Function
                                public final Object apply(Object obj) {
                                    return ((InterfaceC2833qB) obj).a();
                                }
                            }), te, c1230Ob);
                            identityHashMap = identityHashMap;
                        }
                    }
                }
            }
        }
        c2409kl.b((Collection<C3332x4>) setC2);
        if (setC.isEmpty()) {
            return;
        }
        new C3030t00(this.a, false).a((Iterable) setC);
    }

    public final boolean a(com.android.tools.r8.graph.T0 t0) {
        return t0.f.h() && this.b.contains(t0.getReference());
    }
}
