package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.dh, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1858dh extends AbstractC3121uK {
    static final /* synthetic */ boolean c = true;
    private final AbstractC3121uK[] a;
    private final AbstractC1629ah b;

    public C1858dh(AbstractC3121uK[] abstractC3121uKArr, C0767v2[] c0767v2Arr) {
        boolean z = c;
        if (!z && abstractC3121uKArr.length <= 0) {
            throw new AssertionError();
        }
        if (!z && c0767v2Arr.length != abstractC3121uKArr.length) {
            throw new AssertionError();
        }
        if (!z && !Arrays.stream(abstractC3121uKArr).noneMatch(new Predicate() { // from class: com.android.tools.r8.internal.dh$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Objects.isNull((AbstractC3121uK) obj);
            }
        })) {
            throw new AssertionError();
        }
        this.a = abstractC3121uKArr;
        this.b = AbstractC1629ah.a(c0767v2Arr);
    }

    @Override // com.android.tools.r8.internal.AbstractC3121uK
    public final C1858dh a() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC3121uK
    public final void a(BiConsumer biConsumer) {
    }

    public final AbstractC3121uK b(int i) {
        if (i >= 0) {
            AbstractC3121uK[] abstractC3121uKArr = this.a;
            if (i < abstractC3121uKArr.length) {
                return abstractC3121uKArr[i];
            }
        }
        return C2096gg.f();
    }

    @Override // com.android.tools.r8.internal.AbstractC3121uK
    public final int c() {
        return this.a.length;
    }

    @Override // com.android.tools.r8.internal.AbstractC3121uK
    public final boolean e() {
        return false;
    }

    @Override // com.android.tools.r8.internal.AbstractC3121uK
    public final boolean equals(Object obj) {
        if (C1858dh.class != obj.getClass()) {
            return false;
        }
        C1858dh c1858dh = (C1858dh) obj;
        AbstractC3121uK[] abstractC3121uKArr = this.a;
        int length = abstractC3121uKArr.length;
        AbstractC3121uK[] abstractC3121uKArr2 = c1858dh.a;
        if (length == abstractC3121uKArr2.length && Arrays.equals(abstractC3121uKArr, abstractC3121uKArr2)) {
            return this.b.equals(c1858dh.b);
        }
        return false;
    }

    public final int f() {
        return this.a.length;
    }

    @Override // com.android.tools.r8.internal.AbstractC3121uK
    public final int hashCode() {
        return Arrays.hashCode(this.a);
    }

    @Override // com.android.tools.r8.internal.AbstractC3121uK
    public final B1 a(com.android.tools.r8.graph.T0 t0) {
        return C2892r10.F();
    }

    public final C0767v2 a(int i) {
        if (i < 0 || i >= this.a.length) {
            return null;
        }
        return this.b.a(i);
    }

    C1858dh(AbstractC3121uK[] abstractC3121uKArr, AbstractC1629ah abstractC1629ah) {
        this.a = abstractC3121uKArr;
        this.b = abstractC1629ah;
    }

    @Override // com.android.tools.r8.internal.AbstractC3121uK
    public final AbstractC3121uK a(C0702m c0702m, AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2) {
        AbstractC3121uK[] abstractC3121uKArr = new AbstractC3121uK[this.a.length];
        int i = 0;
        while (true) {
            AbstractC3121uK[] abstractC3121uKArr2 = this.a;
            if (i < abstractC3121uKArr2.length) {
                abstractC3121uKArr[i] = abstractC3121uKArr2[i].a(c0702m, abstractC1476Wj, abstractC1476Wj2);
                i++;
            } else {
                return new C1858dh(abstractC3121uKArr, this.b.a(abstractC1476Wj, abstractC1476Wj2));
            }
        }
    }
}
