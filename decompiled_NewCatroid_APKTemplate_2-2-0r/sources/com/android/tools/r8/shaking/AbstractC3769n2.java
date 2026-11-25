package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.AbstractC0614b5;
import com.android.tools.r8.graph.C0606a5;
import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.I4;
import com.android.tools.r8.graph.Y4;
import com.android.tools.r8.internal.C1229Oa;
import com.android.tools.r8.internal.JA;
import java.util.function.BiPredicate;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.n2, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3769n2 {
    public static boolean a(final C0702m c0702m, C1229Oa c1229Oa, final AbstractC0614b5 abstractC0614b5) {
        if (c1229Oa.R() == c0702m.a().Z1 && !c1229Oa.S().f()) {
            return c1229Oa.S().a(new BiPredicate() { // from class: com.android.tools.r8.shaking.n2$$ExternalSyntheticLambda2
                @Override // java.util.function.BiPredicate
                public final boolean test(Object obj, Object obj2) {
                    return AbstractC3769n2.a(c0702m, abstractC0614b5, (C0767v2) obj, (Boolean) obj2);
                }
            });
        }
        C0767v2 c0767v2R = c1229Oa.R();
        if (c0767v2R == c0702m.a().Z1) {
            return true;
        }
        return a(c0702m, c0767v2R, abstractC0614b5);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C0702m c0702m, Y4 y4, C0767v2 c0767v2, Boolean bool) {
        return a(c0702m, c0767v2, y4);
    }

    public static boolean a(C0702m c0702m, C0767v2 c0767v2, C0606a5 c0606a5) {
        if (c0767v2 == c0702m.a().Z1) {
            return true;
        }
        return a(c0702m, c0767v2, (Y4) c0606a5);
    }

    private static boolean a(final C0702m c0702m, C0767v2 c0767v2, final Y4 y4) {
        return ((AbstractC0614b5) y4).a(c0767v2, new Function() { // from class: com.android.tools.r8.shaking.n2$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return AbstractC3769n2.a(y4, c0702m, (C0729q2) obj);
            }
        }, new Function() { // from class: com.android.tools.r8.shaking.n2$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return AbstractC3769n2.a((JA) obj);
            }
        }, c0702m.f()).f();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.tools.r8.utils.D3 a(Y4 y4, C0702m c0702m, C0729q2 c0729q2) {
        if (((AbstractC0614b5) y4).d(c0729q2)) {
            return com.android.tools.r8.utils.D3.d();
        }
        I4.c<?> cVarO = ((C0680j) c0702m.f()).g(c0729q2, c0702m.a().g4.h).o();
        if (cVarO != null && cVarO.d().e0()) {
            return com.android.tools.r8.utils.D3.d();
        }
        return com.android.tools.r8.utils.D3.e();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.tools.r8.utils.D3 a(JA ja) {
        return com.android.tools.r8.utils.D3.e();
    }
}
