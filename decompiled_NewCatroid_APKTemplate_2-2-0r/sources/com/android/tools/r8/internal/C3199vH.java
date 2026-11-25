package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.shaking.AbstractC3833y2;
import com.android.tools.r8.shaking.C3826x2;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.vH, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3199vH extends AbstractC0892Cf {
    static final /* synthetic */ boolean c = true;
    private final AbstractC1639am a;
    private final IdentityHashMap b = new IdentityHashMap();

    C3199vH(C3826x2 c3826x2) {
        boolean z = c;
        if (!z && c3826x2 == null) {
            throw new AssertionError();
        }
        if (!z && c3826x2.c()) {
            throw new AssertionError();
        }
        this.a = c3826x2.b();
    }

    @Override // com.android.tools.r8.internal.AbstractC0892Cf
    public final boolean a(C0767v2 c0767v2) {
        for (AbstractC3833y2 abstractC3833y2 : this.a) {
            if (abstractC3833y2.a(c0767v2)) {
                a(abstractC3833y2, c0767v2);
                return true;
            }
        }
        return false;
    }

    public final void b(com.android.tools.r8.utils.A1 a1) {
        for (AbstractC3833y2 abstractC3833y2 : this.a) {
            if (!c && !this.b.containsKey(abstractC3833y2) && !a1.n1.o0.contains(abstractC3833y2.toString())) {
                throw new AssertionError("Unexpected unused rule -dontwarn " + abstractC3833y2.toString());
            }
        }
    }

    private void a(AbstractC3833y2 abstractC3833y2, C0767v2 c0767v2) {
        if (com.android.tools.r8.utils.A1.b()) {
            ((Set) this.b.computeIfAbsent(abstractC3833y2, new Function() { // from class: com.android.tools.r8.internal.vH$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return C3199vH.a((AbstractC3833y2) obj);
                }
            })).add(c0767v2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Set a(AbstractC3833y2 abstractC3833y2) {
        return OU.c();
    }

    @Override // com.android.tools.r8.internal.AbstractC0892Cf
    public final void a(com.android.tools.r8.utils.A1 a1) {
        boolean z = c;
        if (!z && !a1.n1.E) {
            a();
        }
        if (z || a1.n1.F) {
            return;
        }
        b(a1);
    }

    public final void a() {
        for (AbstractC3833y2 abstractC3833y2 : this.a) {
            if (!c && abstractC3833y2.d() && ((Set) this.b.getOrDefault(abstractC3833y2, Collections.emptySet())).size() == 1) {
                throw new AssertionError("Unexpected unnecessary wildcard in -dontwarn " + abstractC3833y2.toString() + " (only matches " + ((C0767v2) ((Set) this.b.get(abstractC3833y2)).iterator().next()).L0() + Constant.AFTER_QUTO);
            }
        }
    }
}
