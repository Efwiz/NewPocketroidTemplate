package com.android.tools.r8.graph;

import com.android.tools.r8.utils.C3988x2;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

/* JADX INFO: renamed from: com.android.tools.r8.graph.g5, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C0663g5 extends AbstractC0594a1 implements com.android.tools.r8.utils.structural.x<C0663g5> {
    private static final C0663g5 c = new C0663g5();
    static final /* synthetic */ boolean d = true;
    private final C0658g0[] a;
    private final int b;

    private C0663g5() {
        this.a = C0658g0.e;
        this.b = 0;
    }

    public static C0663g5 t0() {
        return c;
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.x U() {
        return this;
    }

    public final void d(Consumer consumer) {
        for (C0658g0 c0658g0 : this.a) {
            for (C0633d0 c0633d0 : c0658g0.c) {
                consumer.accept(c0633d0);
            }
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof C0663g5) {
            return Arrays.equals(this.a, ((C0663g5) obj).a);
        }
        return false;
    }

    public final int hashCode() {
        return Arrays.hashCode(this.a);
    }

    public boolean isEmpty() {
        return this.a.length == 0;
    }

    public C0658g0 j(int i) {
        if (!d && i < 0) {
            throw new AssertionError();
        }
        int i2 = i - this.b;
        if (i2 >= 0) {
            C0658g0[] c0658g0Arr = this.a;
            if (i2 < c0658g0Arr.length) {
                return c0658g0Arr[i2];
            }
        }
        return C0658g0.t0();
    }

    public final boolean k(int i) {
        if (d || i >= 0) {
            return i < this.b;
        }
        throw new AssertionError();
    }

    public final C0663g5 l(int i) {
        if (this == c || i == size()) {
            return this;
        }
        if (i >= size()) {
            C0658g0[] c0658g0Arr = this.a;
            return new C0663g5(c0658g0Arr, i - c0658g0Arr.length);
        }
        C0658g0[] c0658g0Arr2 = new C0658g0[i];
        System.arraycopy(this.a, 0, c0658g0Arr2, 0, i);
        return new C0663g5(c0658g0Arr2, 0);
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.y p() {
        return new com.android.tools.r8.utils.structural.y() { // from class: com.android.tools.r8.graph.g5$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.utils.structural.y
            public final void a(com.android.tools.r8.utils.structural.z zVar) {
                C0663g5.a(zVar);
            }
        };
    }

    public final int s0() {
        return this.a.length;
    }

    public int size() {
        return this.b + this.a.length;
    }

    public final C0658g0[] u0() {
        return this.a;
    }

    public final C0663g5 v0() {
        if (isEmpty()) {
            return this;
        }
        C0658g0[] c0658g0Arr = new C0658g0[size() + 1];
        System.arraycopy(this.a, 0, c0658g0Arr, 1, size());
        c0658g0Arr[0] = C0658g0.t0();
        return new C0663g5(c0658g0Arr, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.utils.structural.z zVar) {
        zVar.d(new Function() { // from class: com.android.tools.r8.graph.g5$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0663g5) obj).a;
            }
        }).a(new ToIntFunction() { // from class: com.android.tools.r8.graph.g5$$ExternalSyntheticLambda2
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((C0663g5) obj).b;
            }
        });
    }

    public static C0663g5 a(C0658g0[] c0658g0Arr, int i) {
        int i2 = com.android.tools.r8.utils.B.b;
        if (c0658g0Arr.length != 0 && !a(c0658g0Arr)) {
            return new C0663g5(c0658g0Arr, i);
        }
        return c;
    }

    private C0663g5(C0658g0[] c0658g0Arr, int i) {
        boolean z = d;
        if (!z && c0658g0Arr == null) {
            throw new AssertionError();
        }
        if (!z && c0658g0Arr.length <= 0) {
            throw new AssertionError();
        }
        if (!z && a(c0658g0Arr)) {
            throw new AssertionError();
        }
        this.a = c0658g0Arr;
        this.b = i;
    }

    private static boolean a(C0658g0[] c0658g0Arr) {
        for (C0658g0 c0658g0 : c0658g0Arr) {
            if (!c0658g0.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public final void a(C0702m c0702m, com.android.tools.r8.dex.L l) {
        for (C0658g0 c0658g0 : this.a) {
            for (C0633d0 c0633d0 : c0658g0.c) {
                c0633d0.a(c0702m, l);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.tools.r8.graph.AbstractC0594a1
    public final void a(com.android.tools.r8.dex.V v) {
        AbstractC0594a1.a(v, this.a);
        v.a(this);
    }

    public final C0663g5 a(Predicate predicate) {
        C0658g0[] c0658g0Arr = null;
        boolean z = true;
        int i = 0;
        while (true) {
            C0658g0[] c0658g0Arr2 = this.a;
            if (i >= c0658g0Arr2.length) {
                break;
            }
            C0658g0 c0658g0 = c0658g0Arr2[i];
            c0658g0.getClass();
            C0658g0 c0658g0A = c0658g0.a(C3988x2.a(predicate));
            C0658g0[] c0658g0Arr3 = this.a;
            if (c0658g0A != c0658g0Arr3[i]) {
                if (c0658g0Arr == null) {
                    c0658g0Arr = (C0658g0[]) c0658g0Arr3.clone();
                }
                c0658g0Arr[i] = c0658g0A;
            }
            if (!c0658g0A.isEmpty()) {
                z = false;
            }
            i++;
        }
        if (c0658g0Arr == null) {
            return this;
        }
        if (z) {
            return c;
        }
        return new C0663g5(c0658g0Arr, this.b);
    }

    public final C0663g5 a(final Function function) {
        C0658g0[] c0658g0Arr;
        if (!isEmpty() && (c0658g0Arr = (C0658g0[]) com.android.tools.r8.utils.B.a(this.a, new Function() { // from class: com.android.tools.r8.graph.g5$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C0663g5.a(function, (C0658g0) obj);
            }
        }, C0658g0.e)) != this.a) {
            return a(c0658g0Arr, this.b);
        }
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C0658g0 a(Function function, C0658g0 c0658g0) {
        return c0658g0.a(function);
    }
}
