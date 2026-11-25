package com.android.tools.r8.graph;

import com.android.tools.r8.internal.C2627nd;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.utils.C3979v2;
import com.android.tools.r8.utils.EnumC3891f;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.ToIntFunction;

/* JADX INFO: renamed from: com.android.tools.r8.graph.d0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C0633d0 extends AbstractC0594a1 implements com.android.tools.r8.utils.structural.x<C0633d0> {
    public static final C0633d0[] c = new C0633d0[0];
    static final /* synthetic */ boolean d = true;
    public final int a;
    public final R0 b;

    public C0633d0(int i, R0 r0) {
        this.a = i;
        this.b = r0;
    }

    protected static void a(com.android.tools.r8.utils.structural.z zVar) {
        zVar.c(new Function() { // from class: com.android.tools.r8.graph.d0$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0633d0) obj).b;
            }
        }).a(new ToIntFunction() { // from class: com.android.tools.r8.graph.d0$$ExternalSyntheticLambda1
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((C0633d0) obj).a;
            }
        });
    }

    public static ArrayList d(C0633d0 c0633d0, C0722p1 c0722p1) {
        B2 b2A = a(c0722p1.U4, c0633d0);
        if (b2A == null) {
            return null;
        }
        B2.a aVarV0 = b2A.v0();
        ArrayList arrayList = new ArrayList(aVarV0.j1().length);
        for (B2 b2 : aVarV0.j1()) {
            arrayList.add((C0767v2) b2.L0().b);
        }
        return arrayList;
    }

    public static C0767v2 e(C0633d0 c0633d0, C0722p1 c0722p1) {
        B2 b2A = a(c0722p1.X4, c0633d0);
        if (b2A == null) {
            return null;
        }
        return (C0767v2) b2A.L0().j1();
    }

    public static ArrayList f(C0633d0 c0633d0, C0722p1 c0722p1) {
        B2 b2A = a(c0722p1.Y4, c0633d0);
        if (b2A == null) {
            return null;
        }
        B2.a aVarV0 = b2A.v0();
        ArrayList arrayList = new ArrayList(aVarV0.j1().length);
        for (B2 b2 : aVarV0.j1()) {
            arrayList.add((C0767v2) b2.L0().b);
        }
        return arrayList;
    }

    public static ArrayList g(C0633d0 c0633d0, C0722p1 c0722p1) {
        B2 b2A = a(c0722p1.Z4, c0633d0);
        if (b2A == null) {
            return null;
        }
        B2.a aVarV0 = b2A.v0();
        ArrayList arrayList = new ArrayList(aVarV0.j1().length);
        for (B2 b2 : aVarV0.j1()) {
            arrayList.add((C0767v2) b2.L0().b);
        }
        return arrayList;
    }

    public static boolean h(C0633d0 c0633d0, C0722p1 c0722p1) {
        return c0633d0.b.a == c0722p1.R4;
    }

    public static boolean i(C0633d0 c0633d0, C0722p1 c0722p1) {
        return c0633d0.b.a == c0722p1.S4;
    }

    public static boolean j(C0633d0 c0633d0, C0722p1 c0722p1) {
        return c0633d0.b.a == c0722p1.T4;
    }

    public static boolean k(C0633d0 c0633d0, C0722p1 c0722p1) {
        return c0633d0.b.a == c0722p1.U4;
    }

    public static boolean l(C0633d0 c0633d0, C0722p1 c0722p1) {
        return c0633d0.b.a == c0722p1.X4;
    }

    public static boolean m(C0633d0 c0633d0, C0722p1 c0722p1) {
        return c0633d0.b.a == c0722p1.Y4;
    }

    public static boolean n(C0633d0 c0633d0, C0722p1 c0722p1) {
        return c0633d0.b.a == c0722p1.Z4;
    }

    public static boolean o(C0633d0 c0633d0, C0722p1 c0722p1) {
        return c0633d0.t0() == c0722p1.a5;
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.x U() {
        return this;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C0633d0)) {
            return false;
        }
        C0633d0 c0633d0 = (C0633d0) obj;
        return this.a == c0633d0.a && this.b.equals(c0633d0.b);
    }

    public final int hashCode() {
        return (this.b.hashCode() * 3) + this.a;
    }

    @Override // com.android.tools.r8.utils.structural.x
    public com.android.tools.r8.utils.structural.y p() {
        return new d0$$ExternalSyntheticLambda2();
    }

    public C0787w2 s0() {
        return null;
    }

    public C0767v2 t0() {
        return this.b.a;
    }

    public final String toString() {
        return this.a + " " + this.b;
    }

    public int u0() {
        return this.a;
    }

    public boolean v0() {
        return false;
    }

    public static C0684j2 b(C0633d0 c0633d0, C0722p1 c0722p1) {
        B2 b2A = a(c0722p1.S4, c0633d0);
        if (b2A == null) {
            return null;
        }
        return (C0684j2) b2A.F0().b;
    }

    public static C3979v2 c(C0633d0 c0633d0, C0722p1 c0722p1) {
        if (!d && !j(c0633d0, c0722p1)) {
            throw new AssertionError();
        }
        C0649f0[] c0649f0Arr = c0633d0.b.b;
        C3979v2 c3979v2 = new C3979v2();
        for (C0649f0 c0649f0 : c0649f0Arr) {
            if (c0649f0.a != c0722p1.c("name")) {
                if (!d && c0649f0.a != c0722p1.c("accessFlags")) {
                    throw new AssertionError();
                }
                c3979v2.b(Integer.valueOf(c0649f0.b.D0().k1()));
            } else if (c0649f0.b.e1()) {
                c3979v2.a((C0759u2) c0649f0.b.K0().j1());
            }
        }
        return c3979v2;
    }

    public static boolean p(C0633d0 c0633d0, C0722p1 c0722p1) {
        return c0633d0.b.a == c0722p1.W4;
    }

    public void a(C0702m c0702m, com.android.tools.r8.dex.L l) {
        this.b.a(c0702m, l);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.tools.r8.graph.AbstractC0594a1
    public void a(com.android.tools.r8.dex.V v) {
        v.a(this);
    }

    public static boolean a(com.android.tools.r8.utils.A1 a1, C0767v2 c0767v2) {
        if (!a1.v0 && c0767v2 != a1.a.i5) {
            C0759u2 c0759u2D0 = c0767v2.D0();
            C0759u2 c0759u2 = a1.a.l1;
            c0759u2D0.getClass();
            if (!c0759u2D0.a(c0759u2.e)) {
                if (!a1.H0) {
                    return false;
                }
                C0722p1 c0722p1 = a1.a;
                int i = C2627nd.e;
                return c0767v2 == c0722p1.j5 || c0767v2 == c0722p1.k5;
            }
        }
        return true;
    }

    public static C0633d0 b(C0722p1 c0722p1, List list) {
        ArrayList arrayList = new ArrayList(list.size());
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(new B2.k(((C0670h5) it.next()).a()));
        }
        return a(c0722p1.Z4, c0722p1, new B2.a((B2[]) arrayList.toArray(B2.a)));
    }

    public static C0633d0 a(C0722p1 c0722p1, C0767v2 c0767v2) {
        return a(c0722p1.R4, c0722p1, new B2.k(c0767v2));
    }

    public static C0767v2 a(C0633d0 c0633d0, C0722p1 c0722p1) {
        B2 b2A = a(c0722p1.R4, c0633d0);
        if (b2A == null) {
            return null;
        }
        return (C0767v2) b2A.L0().b;
    }

    public static C0633d0 a(C0722p1 c0722p1, C0684j2 c0684j2) {
        return a(c0722p1.S4, c0722p1, new F2(c0684j2));
    }

    public static C0633d0 a(C0722p1 c0722p1, ArrayList arrayList) {
        B2[] b2Arr = new B2[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            b2Arr[i] = new B2.k((C0767v2) arrayList.get(i));
        }
        return a(c0722p1.U4, c0722p1, new B2.a(b2Arr));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v5, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r6v6, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r6v7, types: [java.util.ArrayList] */
    public static ArrayList a(C0767v2 c0767v2, C0633d0 c0633d0, C0722p1 c0722p1, Origin origin) {
        B2.a aVar;
        String string;
        B2 b2A = a(c0722p1.a5, c0633d0, c0722p1.b5);
        B2 b2A2 = a(c0722p1.a5, c0633d0, c0722p1.c5);
        B2 b2A3 = a(c0722p1.a5, c0633d0, c0722p1.d5);
        B2 b2A4 = a(c0722p1.a5, c0633d0, c0722p1.e5);
        B2 b2A5 = a(c0722p1.a5, c0633d0, c0722p1.f5);
        if (b2A == null || b2A2 == null || b2A3 == null || b2A4 == null || b2A5 == null || !(b2A instanceof B2.a) || !(b2A2 instanceof B2.a) || !(b2A3 instanceof B2.a) || !(b2A4 instanceof B2.a) || !(b2A5 instanceof B2.a)) {
            return null;
        }
        B2.a aVarV0 = b2A.v0();
        B2.a aVarV1 = b2A2.v0();
        B2.a aVarV2 = b2A3.v0();
        B2.a aVarV3 = b2A4.v0();
        B2.a aVarV4 = b2A5.v0();
        B2[] b2Arr = aVarV0.b;
        if (b2Arr.length != aVarV1.b.length || b2Arr.length != aVarV2.b.length || b2Arr.length != aVarV3.b.length || b2Arr.length != aVarV4.b.length) {
            return null;
        }
        ArrayList arrayList = new ArrayList(aVarV0.b.length);
        int i = 0;
        while (true) {
            B2[] b2Arr2 = aVarV0.b;
            if (i >= b2Arr2.length) {
                return arrayList;
            }
            B2 b2 = b2Arr2[i];
            B2 b3 = aVarV1.b[i];
            B2 b4 = aVarV2.b[i];
            B2 b5 = aVarV3.b[i];
            B2 b6 = aVarV4.b[i];
            if (!b2.e1() || !b3.f1()) {
                return null;
            }
            b4.getClass();
            boolean z = b4 instanceof A2;
            if (!z && !(b4 instanceof I2)) {
                return null;
            }
            b5.getClass();
            if (!(b5 instanceof B2.a)) {
                return null;
            }
            b6.getClass();
            if (!(b6 instanceof B2.a)) {
                return null;
            }
            B2.a aVarV5 = b5.v0();
            B2.a aVarV6 = b6.v0();
            if (aVarV5.b.length != aVarV6.b.length) {
                return null;
            }
            ?? EmptyList = Collections.emptyList();
            if (aVarV6.b.length > 0) {
                EmptyList = new ArrayList(aVarV6.b.length);
                int i2 = 0;
                while (true) {
                    B2[] b2Arr3 = aVarV6.b;
                    aVar = aVarV0;
                    if (i2 >= b2Arr3.length) {
                        break;
                    }
                    B2 b7 = aVarV5.b[i2];
                    B2 b8 = b2Arr3[i2];
                    if (!b7.R0()) {
                        return null;
                    }
                    b8.getClass();
                    B2.a aVar2 = aVarV1;
                    if (!(b8 instanceof A2)) {
                        return null;
                    }
                    EmptyList.add(new C0633d0(b7.x0().k1(), b8.u0().b));
                    i2++;
                    aVarV0 = aVar;
                    aVarV1 = aVar2;
                }
            } else {
                aVar = aVarV0;
            }
            B2.a aVar3 = aVarV1;
            String string2 = ((C0759u2) b2.K0().j1()).toString();
            if (z) {
                B2.a aVarV7 = b4.u0().b.b[0].b.v0();
                StringBuilder sb = new StringBuilder();
                for (B2 b9 : aVarV7.j1()) {
                    sb.append(((C0759u2) b9.K0().b).toString());
                }
                string = sb.toString();
            } else {
                string = null;
            }
            arrayList.add(new v5(c0722p1.a(c0767v2, (C0767v2) b3.L0().j1(), (C0759u2) b2.K0().j1()), C0706m3.b(string2, string, origin, c0722p1, null), EmptyList));
            i++;
            aVarV4 = aVarV4;
            aVarV0 = aVar;
            aVarV1 = aVar3;
        }
    }

    public static C0633d0 a(B2[] b2Arr, B2[] b2Arr2, C0722p1 c0722p1) {
        if (d || b2Arr.length == b2Arr2.length) {
            return new C0633d0(2, new R0(c0722p1.V4, new C0649f0[]{new C0649f0(c0722p1.c("names"), new B2.a(b2Arr)), new C0649f0(c0722p1.c("accessFlags"), new B2.a(b2Arr2))}));
        }
        throw new AssertionError();
    }

    public static C0633d0 a(C0767v2 c0767v2, ArrayList arrayList, C0722p1 c0722p1) {
        return a(c0722p1.Q4, c0722p1, new A2(new R0(c0767v2, (C0649f0[]) arrayList.toArray(C0649f0.c))));
    }

    public static C0633d0 a(String str, C0722p1 c0722p1) {
        C0767v2 c0767v2 = c0722p1.W4;
        int length = str.length();
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (i < length) {
            int i2 = i + 1;
            if (str.charAt(i) == 'L') {
                while (i2 < length) {
                    char cCharAt = str.charAt(i2);
                    if (cCharAt == ';' || cCharAt == '<') {
                        i2++;
                        break;
                    }
                    i2++;
                }
            } else {
                while (i2 < length && str.charAt(i2) != 'L') {
                    i2++;
                }
            }
            arrayList.add(new B2.j(c0722p1.c(str.substring(i, i2))));
            i = i2;
        }
        return a(c0767v2, c0722p1, new B2.a((B2[]) arrayList.toArray(B2.a)));
    }

    public static C0633d0 a(W4 w4, C0722p1 c0722p1) {
        return a(c0722p1.X4, c0722p1, new B2.k(w4.a()));
    }

    public static C0633d0 a(C0722p1 c0722p1, List list) {
        ArrayList arrayList = new ArrayList(list.size());
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(new B2.k(((X4) it.next()).a()));
        }
        return a(c0722p1.Y4, c0722p1, new B2.a((B2[]) arrayList.toArray(B2.a)));
    }

    public static String a(C0633d0 c0633d0) {
        B2.a aVarV0 = c0633d0.b.b[0].b.v0();
        StringBuilder sb = new StringBuilder();
        for (B2 b2 : aVarV0.j1()) {
            sb.append(((C0759u2) b2.K0().b).toString());
        }
        return sb.toString();
    }

    public static C0633d0 a(B2[] b2Arr, C0722p1 c0722p1) {
        return a(c0722p1.h5, c0722p1, new B2.a(b2Arr));
    }

    private static C0633d0 a(C0767v2 c0767v2, C0722p1 c0722p1, B2 b2) {
        return new C0633d0(2, new R0(c0767v2, new C0649f0[]{new C0649f0(c0722p1.c("value"), b2)}));
    }

    private static B2 a(C0767v2 c0767v2, C0633d0 c0633d0) {
        boolean z = d;
        if (!z && c0633d0.a != 2) {
            throw new AssertionError();
        }
        if (!z && c0633d0.b.a != c0767v2) {
            throw new AssertionError();
        }
        C0649f0[] c0649f0Arr = c0633d0.b.b;
        if (c0649f0Arr.length == 0) {
            return null;
        }
        return c0649f0Arr[0].b;
    }

    private static B2 a(C0767v2 c0767v2, C0633d0 c0633d0, C0759u2 c0759u2) {
        boolean z = d;
        if (!z && c0633d0.a != 2) {
            throw new AssertionError();
        }
        if (!z && c0633d0.t0() != c0767v2) {
            throw new AssertionError();
        }
        for (C0649f0 c0649f0 : c0633d0.b.b) {
            if (c0649f0.a == c0759u2) {
                return c0649f0.b;
            }
        }
        return null;
    }

    public static C0633d0 a(com.android.tools.r8.synthesis.L.b bVar, C0722p1 c0722p1, com.android.tools.r8.androidapi.h hVar) {
        int iD;
        C0759u2 c0759u2C = c0722p1.c(c0722p1.j().a());
        C0649f0 c0649f0 = new C0649f0(c0722p1.i1, B2.g.j(bVar.c()));
        C0649f0 c0649f1 = new C0649f0(c0722p1.j1, new B2.j(c0759u2C));
        if (hVar.J()) {
            iD = -2;
        } else if (hVar.H()) {
            iD = -1;
        } else {
            if (!d && !hVar.y()) {
                throw new AssertionError();
            }
            iD = hVar.b0().a().d();
        }
        return new C0633d0(0, new R0(c0722p1.i5, new C0649f0[]{new C0649f0(c0722p1.k1, B2.g.j(iD)), c0649f0, c0649f1}));
    }

    public static C0626c0 a(C0658g0 c0658g0, C0722p1 c0722p1, com.android.tools.r8.synthesis.D d2, com.android.tools.r8.androidapi.b bVar) {
        com.android.tools.r8.androidapi.h hVarA;
        c0IA c0ia = null;
        if (c0658g0.size() != 1) {
            return null;
        }
        C0633d0 c0633d0 = c0658g0.c[0];
        R0 r0 = c0633d0.b;
        if (r0.a != c0722p1.i5 || r0.b.length != 3) {
            return null;
        }
        boolean z = d;
        if (!z && !c0722p1.i1.e(c0722p1.j1)) {
            throw new AssertionError();
        }
        C0649f0[] c0649f0Arr = c0633d0.b.b;
        C0649f0 c0649f0 = c0649f0Arr[0];
        C0649f0 c0649f1 = c0649f0Arr[1];
        C0649f0 c0649f2 = c0649f0Arr[2];
        if (c0649f1.a != c0722p1.i1 || !c0649f1.b.X0() || c0649f2.a != c0722p1.j1 || !c0649f2.b.e1() || c0649f0.a != c0722p1.k1 || !c0649f0.b.X0() || !d2.b().a().equals(((C0759u2) c0649f2.b.K0().j1()).toString())) {
            return null;
        }
        int iK1 = c0649f0.b.D0().k1();
        if (iK1 == -2) {
            hVarA = com.android.tools.r8.androidapi.h.w();
        } else if (iK1 == -1) {
            hVarA = com.android.tools.r8.androidapi.h.f();
        } else {
            hVarA = bVar.a(EnumC3891f.c(iK1));
        }
        com.android.tools.r8.synthesis.L.b bVarA = d2.b().a(c0649f1.b.D0().k1());
        if (!z && bVarA == d2.b().R && !hVarA.y()) {
            throw new AssertionError();
        }
        return new C0626c0(bVarA, hVarA);
    }

    public final C0633d0 a(Function function) {
        R0 r0 = (R0) function.apply(this.b);
        if (r0 == this.b) {
            return this;
        }
        if (r0 == null) {
            return null;
        }
        return new C0633d0(this.a, r0);
    }
}
