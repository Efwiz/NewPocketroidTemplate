package com.android.tools.r8.graph;

import com.android.tools.r8.ProgramResource;
import com.android.tools.r8.dex.B$$ExternalSyntheticLambda1;
import com.android.tools.r8.internal.AbstractC0984Fi;
import com.android.tools.r8.internal.AbstractC1256Pa;
import com.android.tools.r8.internal.AbstractC1899eF;
import com.android.tools.r8.internal.AbstractC2252ii;
import com.android.tools.r8.internal.AbstractC2509m2;
import com.android.tools.r8.internal.C00;
import com.android.tools.r8.internal.C1367Tb;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.internal.CQ;
import com.android.tools.r8.internal.Z8;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;
import com.android.tools.r8.utils.C3970u0;
import com.android.tools.r8.utils.StringDiagnostic;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.zip.CRC32;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.implementation.auxiliary.TypeProxy;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class O3 extends AbstractC1256Pa {
    static final /* synthetic */ boolean F = true;
    private final ArrayList A;
    private final ArrayList B;
    private final HashSet C;
    private boolean D;
    private com.android.tools.r8.synthesis.F E;
    private final Origin c;
    private final I d;
    private final M3 e;
    private final Consumer f;
    private final S3 g;
    private Z8 h;
    private boolean i;
    private C0767v2 j;
    private E k;
    private C0767v2 l;
    private C0800x2 m;
    private C0759u2 n;
    private W4 o;
    private final ArrayList p;
    private final ArrayList q;
    private final ArrayList r;
    private O2 s;
    private final ArrayList t;
    private C0706m3.b u;
    private ArrayList v;
    private ArrayList w;
    private final ArrayList x;
    private final ArrayList y;
    private final HashSet z;

    public O3(Origin origin, I i, byte[] bArr, M3 m3, Consumer consumer) {
        super(589824);
        S3 s3 = new S3();
        this.g = s3;
        this.o = null;
        this.p = new ArrayList();
        this.q = new ArrayList();
        this.r = new ArrayList();
        this.s = null;
        this.t = new ArrayList();
        this.u = C0706m3.b.f();
        this.v = null;
        this.w = null;
        this.x = new ArrayList();
        this.y = new ArrayList();
        this.z = new HashSet();
        this.A = new ArrayList();
        this.B = new ArrayList();
        this.C = new HashSet();
        this.D = false;
        this.E = null;
        this.c = origin;
        this.d = i;
        this.f = consumer;
        s3.a = bArr;
        this.e = m3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ long a(long j, C0729q2 c0729q2) {
        return j;
    }

    private void d(String str) {
        if (!this.e.a.b0() && !C0759u2.a(this.e.a.B0(), str)) {
            throw new C1367Tb("Space characters in SimpleName '" + str + "' are not allowed prior to DEX version 040");
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final void b(String str) {
        if (!F && this.o != null) {
            throw new AssertionError();
        }
        this.p.add(new X4(this.e.f(str)));
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final void c(String str) {
        if (!F && str == null) {
            throw new AssertionError();
        }
        this.q.add(new C0670h5(this.e.f(str)));
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final void a(com.android.tools.r8.internal.T3 t3) {
        com.android.tools.r8.synthesis.F fA = com.android.tools.r8.synthesis.F.a(t3);
        if (fA != null) {
            if (!F && this.E != null) {
                throw new AssertionError();
            }
            this.E = fA;
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final CQ b(String str, String str2, String str3) {
        boolean z = F;
        if (!z && str == null) {
            throw new AssertionError();
        }
        if (!z && str2 == null) {
            throw new AssertionError();
        }
        return new R3(this, str, str2, str3);
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final void a(int i, String str, String str2, String str3) {
        if (str2 != null && str3 != null && C3970u0.a(str2, str, str3) == null && this.h.e(Z8.i)) {
            this.e.a.e.info(new StringDiagnostic(com.android.tools.r8.utils.Y2.b("Malformed inner-class attribute:", "\touterTypeInternal: " + str2, "\tinnerTypeInternal: " + str, "\tinnerName: " + str3), this.c));
        }
        this.t.add(new J3(i, this.e.f(str), str2 == null ? null : this.e.f(str2), str3 != null ? this.e.d(str3) : null));
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final void a(String str, String str2, String str3) {
        O2 o2;
        if (!F && this.s != null) {
            throw new AssertionError();
        }
        C0767v2 c0767v2F = this.e.f(str);
        if (str2 != null && !str2.equals(MethodDescription.TYPE_INITIALIZER_INTERNAL_NAME)) {
            o2 = new O2(this.e.b(c0767v2F, str2, str3));
        } else {
            o2 = new O2(c0767v2F);
        }
        this.s = o2;
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final void a(String str) {
        if (!F && (this.o != null || !this.p.isEmpty())) {
            throw new AssertionError();
        }
        this.o = new W4(this.e.f(str));
    }

    private static String a(E e, String str) {
        return "Illegal class file: " + (e.W() ? "Interface" : "Class") + " " + str;
    }

    private static String a(Z8 z8) {
        return "Class file version " + z8;
    }

    private static String a(E e, String str, Z8 z8, String str2) {
        return a(e, str) + " " + str2 + ". " + a(z8) + ".";
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final void a(int i, int i2, String str, String str2, String str3, String[] strArr) {
        Z8 z8C = Z8.c(i);
        this.h = z8C;
        if (!com.android.tools.r8.utils.A1.P1.e(z8C)) {
            this.i = com.android.tools.r8.utils.C.a(i2);
            this.k = E.g(T3.a(i2));
            this.j = this.e.f(str);
            if (this.k.a(this.h, str.endsWith("/package-info"))) {
                if (str3 == null && !str.equals(TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_INTERNAL_NAME)) {
                    throw new C1367Tb(a(this.k, str, this.h, "is missing a super type"), this.c);
                }
                if (this.k.W() && !Objects.equals(str3, TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_INTERNAL_NAME)) {
                    throw new C1367Tb(a(this.k, str, this.h, "must extend class java.lang.Object. Found: " + str3), this.c);
                }
                d(str);
                if (!F && str3 == null && !str.equals(TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_INTERNAL_NAME)) {
                    throw new AssertionError();
                }
                this.l = str3 == null ? null : this.e.f(str3);
                this.m = this.e.a(strArr);
                if (this.e.a.u1()) {
                    this.u = C0706m3.a(str, str2, this.c, this.e.a(), this.e.a.e);
                    return;
                }
                return;
            }
            E e = this.k;
            throw new C1367Tb(a(e, str, this.h, "has invalid access flags. Found: " + e.toString()), this.c);
        }
        throw new C1367Tb("Unsupported class file version: " + this.h, this.c);
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final void a(String str, String str2) {
        if (str != null) {
            this.n = this.e.d(str);
        }
        if (str2 != null) {
            if (this.v == null) {
                this.v = new ArrayList();
            }
            ArrayList arrayList = this.v;
            B2.j jVar = new B2.j(this.e.d(str2));
            C0722p1 c0722p1 = this.e.a.a;
            arrayList.add(new C0633d0(2, new R0(c0722p1.g5, new C0649f0[]{new C0649f0(c0722p1.c("value"), jVar)})));
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final AbstractC2252ii a(int i, String str, String str2, String str3, Object obj) {
        I i2 = this.d;
        I i3 = I.e;
        if (i2 == i3 && Q2.g(T3.a(i)).j()) {
            return null;
        }
        d(str);
        return new P3(this, i, str, str2, str3, this.d == i3 ? null : obj);
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final AbstractC1899eF a(int i, String str, String str2, String str3, String[] strArr) {
        if (this.d == I.e) {
            C0725p4 c0725p4A = T3.a(i, str);
            if ((c0725p4A.o() && c0725p4A.Y()) || c0725p4A.j()) {
                return null;
            }
        }
        d(str);
        return new Q3(i, str, str2, str3, strArr, this);
    }

    /* JADX WARN: Code duplicated, block: B:22:0x006f  */
    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final void a() {
        Iterator it;
        W wA1;
        ArrayList arrayList = this.w;
        if (arrayList != null) {
            C0633d0 c0633d0A = C0633d0.a(this.j, arrayList, this.e.a.a);
            if (this.v == null) {
                this.v = new ArrayList();
            }
            this.v.add(c0633d0A);
        }
        if (!this.D) {
            C0767v2 c0767v2 = this.e.a.a.l5;
            Iterator it2 = AbstractC0984Fi.a(this.y, this.x).iterator();
            while (true) {
                if (it2.hasNext()) {
                    C0633d0[] c0633d0Arr = ((T0) it2.next()).s0().c;
                    int length = c0633d0Arr.length;
                    int i = 0;
                    while (true) {
                        if (i < length) {
                            if (c0633d0Arr[i].b.a == c0767v2) {
                                it = AbstractC0984Fi.a(this.A, this.B).iterator();
                                while (it.hasNext()) {
                                    wA1 = ((W0) it.next()).a1();
                                    if (wA1 == null && wA1.z0()) {
                                        wA1.v0().I0();
                                    }
                                }
                            } else {
                                i++;
                            }
                        }
                    }
                }
            }
        } else {
            it = AbstractC0984Fi.a(this.A, this.B).iterator();
            while (it.hasNext()) {
                wA1 = ((W0) it.next()).a1();
                if (wA1 == null) {
                }
            }
        }
        if (this.k.X()) {
            this.e.b(this.j, this.d);
            if (this.d == I.c && this.r.size() != this.y.size()) {
                throw new C1367Tb("Records are expected to have one record component per instance field.", null, this.c, Position.UNKNOWN);
            }
        }
        I i2 = this.d;
        C0767v2 c0767v3 = this.j;
        ProgramResource.Kind kind = ProgramResource.Kind.CF;
        Origin origin = this.c;
        E e = this.k;
        C0767v2 c0767v4 = this.l;
        C0800x2 c0800x2 = this.m;
        C0759u2 c0759u2 = this.n;
        W4 w4 = this.o;
        ArrayList arrayList2 = this.p;
        ArrayList arrayList3 = this.q;
        ArrayList arrayList4 = this.r;
        O2 o2 = this.s;
        ArrayList arrayList5 = this.t;
        C0706m3.b bVar = this.u;
        C0658g0 c0658g0A = T3.a(this.v, this.e.a);
        ArrayList arrayList6 = this.x;
        T0[] t0Arr = T0.m;
        T0[] t0Arr2 = (T0[]) arrayList6.toArray(t0Arr);
        T0[] t0Arr3 = (T0[]) this.y.toArray(t0Arr);
        ArrayList arrayList7 = this.A;
        W0[] w0Arr = W0.s;
        AbstractC0727q0 abstractC0727q0A = i2.a(c0767v3, kind, origin, e, c0767v4, c0800x2, c0759u2, w4, arrayList2, arrayList3, arrayList4, o2, arrayList5, bVar, c0658g0A, t0Arr2, t0Arr3, (W0[]) arrayList7.toArray(w0Arr), (W0[]) this.B.toArray(w0Arr), this.e.a.a.i(), a(this.d), this.E);
        this.e.a(abstractC0727q0A, this.d);
        J3 j3D1 = abstractC0727q0A.d1();
        if (j3D1 != null && j3D1.d() != null) {
            if (j3D1.e()) {
                if (!F && j3D1.c() != null) {
                    throw new AssertionError();
                }
                J3 j3 = new J3(j3D1.a(), j3D1.b(), null, null);
                ListIterator<J3> listIterator = abstractC0727q0A.e1().listIterator();
                do {
                    if (!listIterator.hasNext()) {
                        throw new C3405y10();
                    }
                } while (abstractC0727q0A.d != listIterator.next().b());
                listIterator.set(j3);
            } else if (this.s != null) {
                if (!F && !j3D1.f()) {
                    throw new AssertionError();
                }
                throw new C1367Tb(com.android.tools.r8.utils.Y2.b("A member class cannot also be a (non-member) local class at the same time.", "This is likely due to invalid EnclosingMethod and InnerClasses attributes:", this.s.toString(), j3D1.toString()), null, this.c, Position.UNKNOWN);
            }
        }
        if (this.s == null && ((abstractC0727q0A.E1() || abstractC0727q0A.z1()) && Z8.f.e(this.h))) {
            this.e.a.a(abstractC0727q0A.d, abstractC0727q0A.c, this.h);
        }
        if (!abstractC0727q0A.f0()) {
            this.g.b = abstractC0727q0A;
        }
        if (abstractC0727q0A.e0()) {
            C0729q2 c0729q2A0 = abstractC0727q0A.a0();
            c0729q2A0.b(this.h);
            if (this.i) {
                c0729q2A0.i2();
            }
        }
        this.f.accept(abstractC0727q0A);
    }

    private C0729q2.a a(I i) {
        if (this.e.a.H && i == I.c) {
            CRC32 crc32 = new CRC32();
            byte[] bArr = this.g.a;
            crc32.update(bArr, 0, bArr.length);
            final long value = crc32.getValue();
            return new C0729q2.a() { // from class: com.android.tools.r8.graph.O3$$ExternalSyntheticLambda1
                @Override // com.android.tools.r8.graph.C0729q2.a
                public final long a(C0729q2 c0729q2) {
                    return O3.a(value, c0729q2);
                }
            };
        }
        return new B$$ExternalSyntheticLambda1();
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final AbstractC2509m2 a(String str, boolean z) {
        if (this.v == null) {
            this.v = new ArrayList();
        }
        return T3.a(str, z, this.v, this.e, new O3$$ExternalSyntheticLambda0());
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final AbstractC2509m2 a(int i, C00 c00, String str, boolean z) {
        if (this.v == null) {
            this.v = new ArrayList();
        }
        return T3.a(str, z, this.v, this.e, i, c00);
    }
}
