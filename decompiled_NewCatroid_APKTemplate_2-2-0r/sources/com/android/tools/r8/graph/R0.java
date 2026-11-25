package com.android.tools.r8.graph;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class R0 extends AbstractC0594a1 implements com.android.tools.r8.utils.structural.x<R0> {
    static final /* synthetic */ boolean d = true;
    public final C0767v2 a;
    public final C0649f0[] b;
    private int c = 0;

    public R0(C0767v2 c0767v2, C0649f0[] c0649f0Arr) {
        this.a = c0767v2;
        this.b = c0649f0Arr;
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.x U() {
        return this;
    }

    public final void d(Consumer consumer) {
        for (C0649f0 c0649f0 : this.b) {
            consumer.accept(c0649f0);
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof R0)) {
            return false;
        }
        R0 r0 = (R0) obj;
        return r0.a.equals(this.a) && Arrays.equals(r0.b, this.b);
    }

    public final int hashCode() {
        return (this.a.hashCode() * 7) + Arrays.hashCode(this.b);
    }

    public C0649f0 j(int i) {
        return this.b[i];
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.y p() {
        return new com.android.tools.r8.utils.structural.y() { // from class: com.android.tools.r8.graph.R0$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.utils.structural.y
            public final void a(com.android.tools.r8.utils.structural.z zVar) {
                R0.a(zVar);
            }
        };
    }

    public int s0() {
        return this.b.length;
    }

    public final void t0() {
        int i = this.c;
        if (i != 0) {
            if (d) {
                return;
            }
            int iHashCode = hashCode();
            if (i != (iHashCode != 0 ? iHashCode : 1)) {
                throw new AssertionError();
            }
            return;
        }
        Arrays.sort(this.b, new Comparator() { // from class: com.android.tools.r8.graph.R0$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return R0.a((C0649f0) obj, (C0649f0) obj2);
            }
        });
        for (C0649f0 c0649f0 : this.b) {
            c0649f0.b.i1();
        }
        int iHashCode2 = hashCode();
        this.c = iHashCode2 != 0 ? iHashCode2 : 1;
    }

    public final String toString() {
        return "Encoded annotation " + this.a + " " + Arrays.toString(this.b);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.utils.structural.z zVar) {
        zVar.c(new Function() { // from class: com.android.tools.r8.graph.R0$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((R0) obj).a;
            }
        }).d(new Function() { // from class: com.android.tools.r8.graph.R0$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((R0) obj).b;
            }
        });
    }

    public final void a(C0702m c0702m, com.android.tools.r8.dex.L l) {
        C0767v2 c0767v2 = this.a;
        c0767v2.getClass();
        if (l.a(c0767v2)) {
            C0759u2 c0759u2C = c0702m.z().c(c0767v2);
            c0759u2C.getClass();
            l.a(c0759u2C);
        }
        for (C0649f0 c0649f0 : this.b) {
            C0759u2 c0759u2 = c0649f0.a;
            c0759u2.getClass();
            l.a(c0759u2);
            c0649f0.b.a(c0702m, l);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.tools.r8.graph.AbstractC0594a1
    public final void a(com.android.tools.r8.dex.V v) {
        if (!d) {
            throw new AssertionError();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int a(C0649f0 c0649f0, C0649f0 c0649f1) {
        return c0649f0.a.a(c0649f1.a);
    }

    public final R0 a(Function function, Function function2) {
        C0767v2 c0767v2 = (C0767v2) function.apply(this.a);
        C0649f0[] c0649f0Arr = (C0649f0[]) com.android.tools.r8.utils.B.a(this.b, function2, C0649f0.c);
        return (c0767v2 == this.a && c0649f0Arr == this.b) ? this : new R0(c0767v2, c0649f0Arr);
    }
}
