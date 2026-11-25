package com.android.tools.r8.graph;

import com.android.tools.r8.internal.OU;
import com.android.tools.r8.naming.AbstractC3649o0;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/* JADX INFO: renamed from: com.android.tools.r8.graph.g0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C0658g0 extends AbstractC0740s implements com.android.tools.r8.utils.structural.x<C0658g0> {
    public static final C0658g0[] e = new C0658g0[0];
    private static final C0658g0 f = new C0658g0();
    static final /* synthetic */ boolean g = true;
    public final C0633d0[] c;
    private int d;

    private C0658g0() {
        this.d = 0;
        this.c = C0633d0.c;
    }

    public static C0767v2 b(C0633d0[] c0633d0Arr) {
        return a(Arrays.asList(c0633d0Arr));
    }

    public static C0658g0 t0() {
        return f;
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.x U() {
        return this;
    }

    @Override // com.android.tools.r8.graph.AbstractC0740s
    public final boolean c(Object obj) {
        if (obj instanceof C0658g0) {
            return Arrays.equals(this.c, ((C0658g0) obj).c);
        }
        return false;
    }

    public final void forEach(Consumer consumer) {
        for (C0633d0 c0633d0 : this.c) {
            consumer.accept(c0633d0);
        }
    }

    public boolean isEmpty() {
        return this.c.length == 0;
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.y p() {
        return new com.android.tools.r8.utils.structural.y() { // from class: com.android.tools.r8.graph.g0$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.utils.structural.y
            public final void a(com.android.tools.r8.utils.structural.z zVar) {
                C0658g0.a(zVar);
            }
        };
    }

    @Override // com.android.tools.r8.graph.AbstractC0740s
    public final int s0() {
        return Arrays.hashCode(this.c);
    }

    public int size() {
        return this.c.length;
    }

    public final Stream stream() {
        return Arrays.stream(this.c);
    }

    public final String toString() {
        return Arrays.toString(this.c);
    }

    public C0633d0 u0() {
        return this.c[0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.utils.structural.z zVar) {
        zVar.d(new Function() { // from class: com.android.tools.r8.graph.g0$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0658g0) obj).c;
            }
        });
    }

    public final C0658g0 b(C0767v2 c0767v2) {
        int i = 0;
        for (C0633d0 c0633d0 : this.c) {
            if (c0633d0.b.a == c0767v2) {
                C0633d0[] c0633d0Arr = this.c;
                int length = c0633d0Arr.length - 1;
                C0633d0[] c0633d0Arr2 = new C0633d0[length];
                System.arraycopy(c0633d0Arr, 0, c0633d0Arr2, 0, i);
                if (i < length) {
                    System.arraycopy(this.c, i + 1, c0633d0Arr2, i, length - i);
                }
                return a(c0633d0Arr2);
            }
            i++;
        }
        return this;
    }

    public static C0658g0 a(C0633d0[] c0633d0Arr) {
        int i = com.android.tools.r8.utils.B.b;
        return c0633d0Arr.length == 0 ? t0() : new C0658g0(c0633d0Arr);
    }

    public static C0767v2 a(List list) {
        Set setC = OU.c();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            C0633d0 c0633d0 = (C0633d0) it.next();
            if (!g && c0633d0.v0()) {
                throw new AssertionError();
            }
            if (!setC.add(c0633d0.b.a)) {
                return c0633d0.b.a;
            }
        }
        return null;
    }

    private C0658g0(C0633d0[] c0633d0Arr) {
        this.d = 0;
        if (!g && com.android.tools.r8.utils.B.a(c0633d0Arr)) {
            throw new AssertionError();
        }
        this.c = c0633d0Arr;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.tools.r8.graph.AbstractC0594a1
    public final void a(com.android.tools.r8.dex.V v) {
        v.a(this);
        AbstractC0594a1.a(v, this.c);
    }

    public final void a(final AbstractC3649o0 abstractC3649o0) {
        int i = this.d;
        if (i != 0) {
            if (g) {
                return;
            }
            int iHashCode = hashCode();
            if (iHashCode == 0) {
                iHashCode = 1;
            }
            if (i != iHashCode) {
                throw new AssertionError();
            }
            return;
        }
        Arrays.sort(this.c, new Comparator() { // from class: com.android.tools.r8.graph.g0$$ExternalSyntheticLambda3
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return C0658g0.a(abstractC3649o0, (C0633d0) obj, (C0633d0) obj2);
            }
        });
        for (C0633d0 c0633d0 : this.c) {
            c0633d0.b.t0();
        }
        this.d = hashCode();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int a(AbstractC3649o0 abstractC3649o0, C0633d0 c0633d0, C0633d0 c0633d1) {
        return c0633d0.b.a.a((V4) c0633d1.b.a, abstractC3649o0);
    }

    public final C0633d0 a(C0767v2 c0767v2) {
        for (C0633d0 c0633d0 : this.c) {
            if (c0633d0.b.a == c0767v2) {
                return c0633d0;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C0633d0 a(Predicate predicate, C0633d0 c0633d0) {
        if (predicate.test(c0633d0)) {
            return null;
        }
        return c0633d0;
    }

    public final C0658g0 a(final Predicate predicate) {
        return a(new Function() { // from class: com.android.tools.r8.graph.g0$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C0658g0.a(predicate, (C0633d0) obj);
            }
        });
    }

    public final C0658g0 a(Function function) {
        C0633d0[] c0633d0Arr;
        if (!isEmpty() && (c0633d0Arr = (C0633d0[]) com.android.tools.r8.utils.B.a(this.c, function, C0633d0.c)) != this.c) {
            return a(c0633d0Arr);
        }
        return this;
    }

    public final C0658g0 a(C0722p1 c0722p1) {
        C0633d0[] c0633d0Arr = null;
        int i = 0;
        while (true) {
            C0633d0[] c0633d0Arr2 = this.c;
            if (i >= c0633d0Arr2.length) {
                return c0633d0Arr == null ? this : a(c0633d0Arr);
            }
            C0633d0 c0633d0 = c0633d0Arr2[i];
            R0 r0 = c0633d0.b;
            if (r0.a == c0722p1.V4) {
                boolean z = g;
                if (!z && c0633d0.a != 2) {
                    throw new AssertionError();
                }
                if (!z && r0.b.length != 2) {
                    throw new AssertionError();
                }
                if (!z && !r0.b[0].a.toString().equals("names")) {
                    throw new AssertionError();
                }
                if (!z && !c0633d0.b.b[1].a.toString().equals("accessFlags")) {
                    throw new AssertionError();
                }
                B2.a aVarV0 = c0633d0.b.b[0].b.v0();
                B2.a aVarV1 = c0633d0.b.b[1].b.v0();
                if (!z && (aVarV0 == null || aVarV1 == null)) {
                    throw new AssertionError();
                }
                if (!z && aVarV0.j1().length != aVarV1.j1().length) {
                    throw new AssertionError();
                }
                if (c0633d0Arr == null) {
                    C0633d0[] c0633d0Arr3 = this.c;
                    C0633d0[] c0633d0Arr4 = new C0633d0[c0633d0Arr3.length];
                    System.arraycopy(c0633d0Arr3, 0, c0633d0Arr4, 0, i);
                    c0633d0Arr = c0633d0Arr4;
                }
                B2[] b2Arr = new B2[aVarV0.j1().length + 1];
                b2Arr[0] = new B2.j(c0722p1.c("_this"));
                System.arraycopy(aVarV0.j1(), 0, b2Arr, 1, aVarV0.j1().length);
                B2[] b2Arr2 = new B2[aVarV1.j1().length + 1];
                b2Arr2[0] = B2.g.j(0);
                System.arraycopy(aVarV1.j1(), 0, b2Arr2, 1, aVarV1.j1().length);
                c0633d0Arr[i] = C0633d0.a(b2Arr, b2Arr2, c0722p1);
            } else if (c0633d0Arr != null) {
                c0633d0Arr[i] = c0633d0;
            }
            i++;
        }
    }
}
