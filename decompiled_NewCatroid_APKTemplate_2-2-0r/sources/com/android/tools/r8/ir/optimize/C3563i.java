package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.internal.AbstractC2412ko;
import com.android.tools.r8.internal.C1207Nf;
import com.android.tools.r8.internal.L3;
import com.android.tools.r8.internal.M3;
import com.android.tools.r8.internal.OU;
import com.android.tools.r8.internal.S10;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.ir.optimize.i, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3563i {
    private final LinkedHashMap a = new LinkedHashMap();
    private final Set b = OU.c();

    C3563i() {
    }

    private void a(AbstractC2412ko abstractC2412ko, S10 s10, final AbstractC3559g abstractC3559g, Consumer consumer) {
        C3561h c3561h = (C3561h) ((Map) this.a.computeIfAbsent(abstractC2412ko, new Function() { // from class: com.android.tools.r8.ir.optimize.i$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C3563i.a((AbstractC2412ko) obj);
            }
        })).computeIfAbsent(s10, new Function() { // from class: com.android.tools.r8.ir.optimize.i$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C3563i.a(abstractC3559g, (S10) obj);
            }
        });
        consumer.accept(c3561h);
        if ((abstractC3559g instanceof C3567k) && c3561h.a()) {
            this.b.add(s10);
        }
    }

    final void b(AbstractC2412ko abstractC2412ko, S10 s10) {
        a(abstractC2412ko, s10, C3575o.a, new i$$ExternalSyntheticLambda3());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Map a(AbstractC2412ko abstractC2412ko) {
        return new LinkedHashMap();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C3561h a(AbstractC3559g abstractC3559g, S10 s10) {
        return new C3561h(abstractC3559g);
    }

    final void a(AbstractC2412ko abstractC2412ko, S10 s10, final C1207Nf c1207Nf) {
        a(abstractC2412ko, s10, C3567k.a, new Consumer() { // from class: com.android.tools.r8.ir.optimize.i$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C3563i.a(c1207Nf, (C3561h) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(C1207Nf c1207Nf, C3561h c3561h) {
        c3561h.getClass();
        if (!C3561h.d && c1207Nf == null) {
            throw new AssertionError();
        }
        c3561h.b = new L3(c1207Nf);
        if (c1207Nf.o().x()) {
            c3561h.c = M3.a();
        }
    }

    final void a(AbstractC2412ko abstractC2412ko, S10 s10) {
        a(abstractC2412ko, s10, C3567k.a, new i$$ExternalSyntheticLambda3());
    }
}
