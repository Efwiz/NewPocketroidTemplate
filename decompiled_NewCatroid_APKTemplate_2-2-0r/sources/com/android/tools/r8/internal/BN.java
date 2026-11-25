package com.android.tools.r8.internal;

import java.util.function.ObjIntConsumer;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class BN {
    private final InterfaceC1600aG a;

    private BN(C3222vc c3222vc) {
        this.a = c3222vc;
    }

    public static BN a() {
        return new BN(C3222vc.f());
    }

    public final int b() {
        return this.a.size();
    }

    public final void a(com.android.tools.r8.graph.o5 o5Var) {
        this.a.add(com.android.tools.r8.utils.D2.a().c(o5Var));
    }

    public final void a(final ObjIntConsumer objIntConsumer) {
        this.a.a(new ObjIntConsumer() { // from class: com.android.tools.r8.internal.BN$$ExternalSyntheticLambda0
            @Override // java.util.function.ObjIntConsumer
            public final void accept(Object obj, int i) {
                BN.a(objIntConsumer, (C2173hh) obj, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(ObjIntConsumer objIntConsumer, C2173hh c2173hh, int i) {
        objIntConsumer.accept((com.android.tools.r8.graph.o5) c2173hh.a(), i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(Predicate predicate, C2173hh c2173hh) {
        return predicate.test((com.android.tools.r8.graph.o5) c2173hh.a());
    }

    public final void a(final Predicate predicate) {
        this.a.removeIf(new Predicate() { // from class: com.android.tools.r8.internal.BN$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return BN.a(predicate, (C2173hh) obj);
            }
        });
    }
}
