package com.android.tools.r8.graph;

import com.android.tools.r8.ClassFileConsumer;
import com.android.tools.r8.DesugarGraphConsumer;
import com.android.tools.r8.ProgramResource;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.internal.AbstractC2509m2;
import com.android.tools.r8.internal.C00;
import com.android.tools.r8.internal.C1031Ha;
import com.android.tools.r8.internal.C1367Tb;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;
import com.android.tools.r8.shaking.C3718f3;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import net.bytebuddy.description.method.MethodDescription;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class T3<T extends AbstractC0727q0> {
    private static final byte[] d = ByteBuffer.allocate(4).putInt(-889275714).array();
    static final /* synthetic */ boolean e = true;
    private final M3 a;
    private final Consumer b;
    private final I c;

    public T3(M3 m3, Consumer<T> consumer, I<T> i) {
        this.a = m3;
        this.b = consumer;
        this.c = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int a(int i) {
        return i & (-393217);
    }

    public void b(final Origin origin, final byte[] bArr) {
        com.android.tools.r8.utils.R0.a(origin, Position.UNKNOWN, new Runnable() { // from class: com.android.tools.r8.graph.T3$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.a(origin, bArr);
            }
        });
    }

    public final void a(ProgramResource programResource) {
        b(programResource.getOrigin(), programResource.getBytes());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:28:0x0068  */
    public void a(Origin origin, byte[] bArr) {
        int i;
        com.android.tools.r8.internal.T3[] t3Arr;
        DesugarGraphConsumer desugarGraphConsumer;
        if (bArr.length < d.length) {
            throw new C1367Tb("Invalid empty classfile", null, origin, Position.UNKNOWN);
        }
        int i2 = 0;
        while (true) {
            byte[] bArr2 = d;
            if (i2 < bArr2.length) {
                if (bArr[i2] != bArr2[i2]) {
                    throw new C1367Tb("Invalid classfile header", null, origin, Position.UNKNOWN);
                }
                i2++;
            } else {
                if (this.c == I.c && this.a.a.A0.b() && (desugarGraphConsumer = this.a.a.H1) != null) {
                    desugarGraphConsumer.acceptProgramNode(origin);
                }
                C1031Ha c1031Ha = new C1031Ha(bArr, 0);
                if (this.a.a.D0() != null) {
                    C3718f3 c3718f3H = this.a.a.D0().h();
                    if (c3718f3H.a || c3718f3H.k || c3718f3H.j) {
                        i = 5;
                    } else {
                        i = 7;
                    }
                } else {
                    i = 5;
                }
                int i3 = this.c == I.c ? i : 7;
                O3 o3 = new O3(origin, this.c, bArr, this.a, this.b);
                if (this.c == I.c) {
                    t3Arr = new com.android.tools.r8.internal.T3[]{com.android.tools.r8.synthesis.F.a(this.a.a.a.j())};
                } else {
                    t3Arr = new com.android.tools.r8.internal.T3[0];
                }
                c1031Ha.a(o3, t3Arr, i3);
                if (c1031Ha.b() <= 2 || c1031Ha.a(2) <= 0) {
                    return;
                }
                try {
                    Object objA = c1031Ha.a(2, new char[c1031Ha.c()]);
                    if (objA instanceof String) {
                        this.a.a.a.a((String) objA);
                        return;
                    }
                    return;
                } catch (IllegalArgumentException unused) {
                    return;
                }
            }
        }
    }

    public static C0725p4 a(int i, String str) {
        return C0725p4.a(a(i), str.equals("<init>") || str.equals(MethodDescription.TYPE_INITIALIZER_INTERNAL_NAME));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static AbstractC2509m2 a(final String str, boolean z, final List list, final M3 m3, final BiFunction biFunction) {
        if (!e && biFunction == null) {
            throw new AssertionError();
        }
        if (!z) {
            if (!C0633d0.a(m3.a, m3.e(str))) {
                return null;
            }
        }
        final int i = z ? 1 : 0;
        return new N3(m3, new BiConsumer() { // from class: com.android.tools.r8.graph.T3$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                T3.a(list, biFunction, i, str, m3, (List) obj, (List) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(List list, BiFunction biFunction, int i, String str, M3 m3, List list2, List list3) {
        list.add((C0633d0) biFunction.apply(Integer.valueOf(i), a(str, list2, list3, m3)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static AbstractC2509m2 a(String str, boolean z, List list, M3 m3, final int i, final C00 c00) {
        if (!e && list == null) {
            throw new AssertionError();
        }
        if (m3.a.f instanceof ClassFileConsumer) {
            return a(str, z, list, m3, new BiFunction() { // from class: com.android.tools.r8.graph.T3$$ExternalSyntheticLambda2
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return T3.a(i, c00, (Integer) obj, (R0) obj2);
                }
            });
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C0633d0 a(int i, C00 c00, Integer num, R0 r0) {
        return new C0787w2(num.intValue(), r0, i, c00);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static R0 a(String str, List list, List list2, M3 m3) {
        if (!e && ((list != null || !list2.isEmpty()) && (list == null || list.isEmpty() || list.size() != list2.size()))) {
            throw new AssertionError();
        }
        C0649f0[] c0649f0Arr = new C0649f0[list2.size()];
        for (int i = 0; i < list2.size(); i++) {
            c0649f0Arr[i] = new C0649f0((C0759u2) list.get(i), (B2) list2.get(i));
        }
        return new R0(m3.e(str), c0649f0Arr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static C0658g0 a(List list, com.android.tools.r8.utils.A1 a1) {
        C0767v2 c0767v2A;
        if (list != null && !list.isEmpty()) {
            if (a1.Y0() && (c0767v2A = C0658g0.a(list)) != null) {
                throw new C1367Tb("Multiple annotations of type `" + c0767v2A.r0() + "`");
            }
            return C0658g0.a((C0633d0[]) list.toArray(C0633d0.c));
        }
        return C0658g0.t0();
    }
}
