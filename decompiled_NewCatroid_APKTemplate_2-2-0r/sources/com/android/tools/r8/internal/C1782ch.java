package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0767v2;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ch, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1782ch extends AbstractC1629ah {
    static final /* synthetic */ boolean b = true;
    private final C0767v2[] a;

    C1782ch(C0767v2[] c0767v2Arr) {
        if (!b && !Arrays.stream(c0767v2Arr).noneMatch(new Predicate() { // from class: com.android.tools.r8.internal.ch$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Objects.isNull((C0767v2) obj);
            }
        })) {
            throw new AssertionError();
        }
        this.a = c0767v2Arr;
    }

    @Override // com.android.tools.r8.internal.AbstractC1629ah
    final C0767v2 a(int i) {
        if (b || (i >= 0 && i < this.a.length)) {
            return this.a[i];
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.AbstractC1629ah
    public final boolean equals(Object obj) {
        if (obj instanceof C1782ch) {
            return Arrays.equals(this.a, ((C1782ch) obj).a);
        }
        return false;
    }

    public final int hashCode() {
        return Arrays.hashCode(this.a);
    }

    @Override // com.android.tools.r8.internal.AbstractC1629ah
    final AbstractC1629ah a(final AbstractC1476Wj abstractC1476Wj, final AbstractC1476Wj abstractC1476Wj2) {
        return AbstractC1629ah.a((C0767v2[]) com.android.tools.r8.utils.B.a(this.a, new Function() { // from class: com.android.tools.r8.internal.ch$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C1782ch.a(abstractC1476Wj, abstractC1476Wj2, (C0767v2) obj);
            }
        }, C0767v2.g));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C0767v2 a(AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2, C0767v2 c0767v2) {
        C0767v2 c0767v2A = abstractC1476Wj.a(abstractC1476Wj2, c0767v2);
        if (b || c0767v2A.O0()) {
            return c0767v2A;
        }
        throw new AssertionError();
    }
}
