package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.KotlinClassMetadata;
import com.android.tools.r8.utils.C3888e1;
import com.android.tools.r8.utils.C3979v2;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.nz, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2654nz implements InterfaceC2743oz {
    private final int a;
    private final String b;
    private final boolean c;
    private final String d;
    private final List e;
    private final C0875Bz f;
    private final List g;
    private final List h;
    private final List i;
    private final List j;
    private final List k;
    private final C3111uA l;
    private final C2973sA m;
    private final String n;
    private final C1255Oz o;
    private final int[] p;
    private final String q;
    private final C2759pA r;
    private final int s;
    private final String t;
    private final List u;
    private final C1466Vz v;

    private C2654nz(int i, String str, boolean z, String str2, C0875Bz c0875Bz, AbstractC1639am abstractC1639am, AbstractC1639am abstractC1639am2, AbstractC1639am abstractC1639am3, AbstractC1639am abstractC1639am4, AbstractC1639am abstractC1639am5, List list, C3111uA c3111uA, C2973sA c2973sA, String str3, C1255Oz c1255Oz, int[] iArr, String str4, C2759pA c2759pA, C1466Vz c1466Vz, int i2, String str5, List list2) {
        this.a = i;
        this.b = str;
        this.c = z;
        this.d = str2;
        this.f = c0875Bz;
        this.g = abstractC1639am;
        this.e = abstractC1639am2;
        this.h = abstractC1639am3;
        this.i = abstractC1639am4;
        this.j = abstractC1639am5;
        this.k = list;
        this.l = c3111uA;
        this.m = c2973sA;
        this.n = str3;
        this.o = c1255Oz;
        this.p = iArr;
        this.q = str4;
        this.r = c2759pA;
        this.v = c1466Vz;
        this.s = i2;
        this.t = str5;
        this.u = list2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(C1283Px c1283Px, String str) {
        if (str != null) {
            AbstractC0936Ds.c(c1283Px, "<this>");
            AbstractC2263iu.a(c1283Px).c(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Consumer b(C2973sA c2973sA) {
        Objects.requireNonNull(c2973sA);
        return new Kz$$ExternalSyntheticLambda1(c2973sA);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2743oz
    public final String m() {
        return this.n;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2743oz
    public final int[] t() {
        return this.p;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Consumer b(C2759pA c2759pA) {
        Objects.requireNonNull(c2759pA);
        return new Iz$$ExternalSyntheticLambda0(c2759pA);
    }

    public static C2654nz a(KotlinClassMetadata.Class r36, String str, int[] iArr, AbstractC0727q0 abstractC0727q0, C0702m c0702m, Consumer consumer) {
        com.android.tools.r8.graph.W0 w0;
        final C0722p1 c0722p1A = c0702m.a();
        final com.android.tools.r8.utils.O2 o2 = c0702m.Y().e;
        C1283Px c1283PxB = r36.b();
        C1201Mz c1201MzA = C1201Mz.a(r36, c0702m.Y());
        HashMap map = new HashMap();
        for (com.android.tools.r8.graph.T0 t0 : abstractC0727q0.W0()) {
            com.android.tools.r8.graph.Y0 reference = t0.getReference();
            map.put(new com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.e(reference.f.toString(), reference.h.b1()).a(), t0);
        }
        HashMap map2 = new HashMap();
        for (com.android.tools.r8.graph.W0 w1 : abstractC0727q0.M1()) {
            map2.put(AbstractC1745cA.a(w1.getReference()).a(), w1);
        }
        C1507Xl c1507XlG = AbstractC1639am.g();
        C1466Vz c1466Vz = new C1466Vz(c0702m);
        int i = 0;
        for (C1493Wx c1493Wx : c1283PxB.g()) {
            int i2 = i + 1;
            C3469yz c3469yzA = C3469yz.a(c1493Wx, c0722p1A, o2, c1201MzA.a(i));
            com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j jVarA = com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.d.a(c1493Wx);
            if (jVarA != null && (w0 = (com.android.tools.r8.graph.W0) map2.get(jVarA.a())) != null) {
                w0.a(c3469yzA);
                c1466Vz.a(w0.getReference());
            } else {
                c1507XlG.a(c3469yzA);
            }
            i = i2;
        }
        C0875Bz c0875BzA = C0875Bz.a(c1283PxB, map2, map, c0722p1A, o2, consumer, c1201MzA, c1466Vz);
        String strA = com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.d.a(c1283PxB);
        C2973sA c2973sAA = strA != null ? C2973sA.a(strA, c0722p1A, strA) : null;
        boolean z = c1283PxB.c.equals(AbstractC1745cA.a(abstractC0727q0, abstractC0727q0.getType().b1())) || (c2973sAA != null && c1283PxB.c.equals(c2973sAA.v()));
        int iJ = c1283PxB.j();
        String str2 = c1283PxB.c;
        String strD = com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.d.d(c1283PxB);
        AbstractC1639am abstractC1639amA = C2832qA.a(c1283PxB.q(), c0722p1A, o2);
        AbstractC1639am abstractC1639amA2 = c1507XlG.a();
        List<C0942Dy> listP = c1283PxB.p();
        C1507Xl c1507XlG2 = AbstractC1639am.g();
        Iterator<C0942Dy> it = listP.iterator();
        while (it.hasNext()) {
            c1507XlG2.a(C2759pA.a(c0722p1A, o2, it.next()));
        }
        AbstractC1639am abstractC1639amA3 = c1507XlG2.a();
        List<String> listO = c1283PxB.o();
        C1507Xl c1507XlG3 = AbstractC1639am.g();
        for (String str3 : listO) {
            c1507XlG3.a(C2973sA.a(str3.replace('.', '$'), c0722p1A, str3));
        }
        AbstractC1639am abstractC1639amA4 = c1507XlG3.a();
        AbstractC1639am abstractC1639amA5 = a(abstractC0727q0, c1283PxB.n(), c0722p1A);
        List listA = a(c1283PxB, abstractC0727q0);
        C3111uA c3111uAA = C3111uA.a(c1283PxB.r());
        C1255Oz c1255OzA = C1255Oz.a(com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.d.c(c1283PxB), c0722p1A, o2);
        String strK = c1283PxB.k();
        C2759pA c2759pAA = C2759pA.a(c0722p1A, o2, c1283PxB.l());
        int iB = com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.d.b(c1283PxB);
        String strF = c1283PxB.f();
        if (strF != null) {
            for (com.android.tools.r8.graph.T0 t1 : abstractC0727q0.W0()) {
                if (t1.getReference().f.toString().equals(strF)) {
                    t1.a(new C3400xz(strF));
                }
            }
            o2.warning(C1339Rz.a(abstractC0727q0, strF));
        }
        return new C2654nz(iJ, str2, z, strD, c0875BzA, abstractC1639amA, abstractC1639amA2, abstractC1639amA3, abstractC1639amA4, abstractC1639amA5, listA, c3111uAA, c2973sAA, str, c1255OzA, iArr, strK, c2759pAA, c1466Vz, iB, strF, com.android.tools.r8.utils.V1.a((Collection) c1283PxB.h(), new Function() { // from class: com.android.tools.r8.internal.nz$$ExternalSyntheticLambda8
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C2759pA.a(c0722p1A, o2, (C0942Dy) obj);
            }
        }));
    }

    private static AbstractC1639am a(AbstractC0727q0 abstractC0727q0, List list, C0722p1 c0722p1) {
        C1507Xl c1507XlG = AbstractC1639am.g();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            c1507XlG.a(C2973sA.a(abstractC0727q0.d.a1() + "$" + str, c0722p1, str));
        }
        return c1507XlG.a();
    }

    private static List a(C1283Px c1283Px, AbstractC0727q0 abstractC0727q0) {
        List<String> listI = c1283Px.i();
        if (listI.isEmpty()) {
            return listI;
        }
        final Collection collectionA = listI.size() < 16 ? listI : OU.a(listI);
        abstractC0727q0.W0().forEach(new Consumer() { // from class: com.android.tools.r8.internal.nz$$ExternalSyntheticLambda10
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2654nz.a(collectionA, (com.android.tools.r8.graph.T0) obj);
            }
        });
        return listI;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Collection collection, com.android.tools.r8.graph.T0 t0) {
        String string = t0.K0().toString();
        if (collection.contains(string)) {
            t0.a(new C0975Ez(string));
        }
    }

    /* JADX WARN: Code duplicated, block: B:12:0x0064  */
    @Override // com.android.tools.r8.internal.InterfaceC2743oz
    public final C3979v2 a(C0702m c0702m, AbstractC0727q0 abstractC0727q0) {
        String strA;
        final C1283Px c1283Px = new C1283Px();
        c1283Px.a(this.a);
        C0759u2 c0759u2 = abstractC0727q0.d.e;
        C0759u2 c0759u2C = c0702m.z().c(abstractC0727q0.d);
        boolean z = true;
        boolean zA = !c0759u2.equals(c0759u2C);
        if (!this.c) {
            c1283Px.b(this.b);
        } else {
            C2973sA c2973sA = this.m;
            if (c2973sA == null || !this.b.equals(c2973sA.v())) {
                strA = null;
            } else {
                com.android.tools.r8.utils.N n = new com.android.tools.r8.utils.N();
                this.m.a(new Jz$$ExternalSyntheticLambda0(n), c0702m, (String) null);
                if (n.c()) {
                    strA = "." + ((String) n.a());
                } else {
                    strA = null;
                }
            }
            if (strA == null) {
                strA = AbstractC1745cA.a(abstractC0727q0, c0759u2C.toString());
            }
            c1283Px.b(strA);
            zA |= !this.b.equals(strA);
        }
        boolean z2 = false;
        int i = 0;
        for (com.android.tools.r8.graph.T0 t0 : abstractC0727q0.W0()) {
            InterfaceC1001Fz interfaceC1001FzV0 = t0.V0();
            if (interfaceC1001FzV0.e()) {
                zA |= interfaceC1001FzV0.f().a(c1283Px, t0.getReference(), c0702m.z());
                z2 = true;
            } else if (interfaceC1001FzV0.j()) {
                C0975Ez c0975EzK = interfaceC1001FzV0.k();
                zA |= c0975EzK.a(c1283Px, t0.getReference(), c0702m.z());
                if (i >= this.k.size() || !((String) this.k.get(i)).equals(c0975EzK.u())) {
                    zA = true;
                }
                i++;
            }
        }
        if (!z2 && this.t != null) {
            zA = true;
        }
        if (i < this.k.size()) {
            zA = true;
        }
        Iterator it = this.e.iterator();
        while (it.hasNext()) {
            zA |= ((C3469yz) it.next()).a(c1283Px, null, c0702m);
        }
        C1466Vz c1466Vz = new C1466Vz(c0702m);
        for (com.android.tools.r8.graph.W0 w0 : abstractC0727q0.M1()) {
            if (w0.f1().h()) {
                zA |= w0.f1().i().a(c1283Px, w0, c0702m);
                c1466Vz.a(w0.getReference());
            }
        }
        C0875Bz c0875Bz = this.f;
        List listC = c1283Px.c();
        Objects.requireNonNull(listC);
        hA$$ExternalSyntheticLambda0 ha__externalsyntheticlambda0 = new hA$$ExternalSyntheticLambda0(listC);
        List listA = c1283Px.a();
        Objects.requireNonNull(listA);
        hA$$ExternalSyntheticLambda1 ha__externalsyntheticlambda1 = new hA$$ExternalSyntheticLambda1(listA);
        List listB = c1283Px.b();
        Objects.requireNonNull(listB);
        boolean zA2 = zA | c0875Bz.a(ha__externalsyntheticlambda0, ha__externalsyntheticlambda1, new hA$$ExternalSyntheticLambda2(listB), abstractC0727q0, c0702m, c1466Vz) | AbstractC1745cA.a(c0702m, this.g, c1283Px.q(), new Iz$$ExternalSyntheticLambda14());
        final List<C0942Dy> listP = c1283Px.p();
        for (C2759pA c2759pA : this.h) {
            if (abstractC0727q0.getType() != c2759pA.a(c0702m.E())) {
                Objects.requireNonNull(listP);
                zA2 |= c2759pA.a(new Consumer() { // from class: com.android.tools.r8.internal.nz$$ExternalSyntheticLambda12
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        listP.add((C0942Dy) obj);
                    }
                }, c0702m);
            } else {
                zA2 = true;
            }
        }
        List<String> listN = c1283Px.n();
        for (C2973sA c2973sA2 : this.j) {
            com.android.tools.r8.utils.N n2 = new com.android.tools.r8.utils.N();
            boolean zA3 = c2973sA2.a(new Jz$$ExternalSyntheticLambda0(n2), c0702m, (String) null);
            if (n2.c()) {
                if (zA3) {
                    String str = (String) n2.a();
                    listN.add(str.substring(str.lastIndexOf(36) + 1));
                } else {
                    listN.add(c2973sA2.u());
                }
            }
            zA2 |= zA3;
        }
        final List<String> listO = c1283Px.o();
        Iterator it2 = this.i.iterator();
        while (it2.hasNext()) {
            zA2 |= ((C2973sA) it2.next()).a(new Consumer() { // from class: com.android.tools.r8.internal.nz$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C2654nz.a(listO, (String) obj);
                }
            }, c0702m, (String) null);
        }
        C3111uA c3111uA = this.l;
        ArrayList arrayListR = c1283Px.r();
        Objects.requireNonNull(arrayListR);
        boolean zA4 = c3111uA.a(new Iz$$ExternalSyntheticLambda2(arrayListR)) | zA2;
        String str2 = this.q;
        if (str2 != null && this.r != null) {
            c1283Px.a(str2);
            zA4 |= this.r.a(new Consumer() { // from class: com.android.tools.r8.internal.nz$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    c1283Px.a((C0942Dy) obj);
                }
            }, c0702m);
        }
        boolean zA5 = zA4 | AbstractC1745cA.a(c0702m, this.u, c1283Px.h(), new Cz$$ExternalSyntheticLambda4());
        com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.d.a(c1283Px, this.s);
        com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.d.a(c1283Px, this.d);
        C2973sA c2973sA3 = this.m;
        if (c2973sA3 != null) {
            zA5 |= c2973sA3.a(new Consumer() { // from class: com.android.tools.r8.internal.nz$$ExternalSyntheticLambda11
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C2654nz.a(c1283Px, (String) obj);
                }
            }, c0702m, (String) null);
        }
        C1255Oz c1255Oz = this.o;
        ArrayList arrayListC = com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.d.c(c1283Px);
        Objects.requireNonNull(arrayListC);
        boolean zA6 = zA5 | c1255Oz.a(new hA$$ExternalSyntheticLambda1(arrayListC), c0702m);
        int[] iArr = KotlinClassMetadata.b;
        InterfaceC3115uE interfaceC3115uEA = com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.r.a(c1283Px, AbstractC1745cA.a()).a();
        if (!zA6 && this.v.a(c1466Vz, c0702m)) {
            z = false;
        }
        return C3979v2.a(interfaceC3115uEA, Boolean.valueOf(z));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(List list, String str) {
        if (str != null) {
            list.add(str.replace('$', '.'));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Consumer a(final C3469yz c3469yz) {
        Objects.requireNonNull(c3469yz);
        return new Consumer() { // from class: com.android.tools.r8.internal.nz$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                c3469yz.a((com.android.tools.r8.graph.Q0) obj);
            }
        };
    }

    @Override // com.android.tools.r8.shaking.InterfaceC3680a0
    public final void a(com.android.tools.r8.graph.Q0 q0) {
        C3888e1.a(this.e, new Function() { // from class: com.android.tools.r8.internal.nz$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C2654nz.a((C3469yz) obj);
            }
        }, (Object) q0);
        this.f.a(q0);
        C3888e1.a(this.g, new Function() { // from class: com.android.tools.r8.internal.nz$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C2654nz.a((C2832qA) obj);
            }
        }, (Object) q0);
        C3888e1.a(this.h, new Function() { // from class: com.android.tools.r8.internal.nz$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C2654nz.a((C2759pA) obj);
            }
        }, (Object) q0);
        C3888e1.a(this.i, new Function() { // from class: com.android.tools.r8.internal.nz$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C2654nz.a((C2973sA) obj);
            }
        }, (Object) q0);
        C3888e1.a(this.j, new Function() { // from class: com.android.tools.r8.internal.nz$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C2654nz.b((C2973sA) obj);
            }
        }, (Object) q0);
        C3888e1.a(this.u, new Function() { // from class: com.android.tools.r8.internal.nz$$ExternalSyntheticLambda9
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C2654nz.b((C2759pA) obj);
            }
        }, (Object) q0);
        this.o.a(q0);
        C2973sA c2973sA = this.m;
        if (c2973sA != null) {
            c2973sA.a(q0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Consumer a(C2832qA c2832qA) {
        Objects.requireNonNull(c2832qA);
        return new Iz$$ExternalSyntheticLambda11(c2832qA);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Consumer a(C2759pA c2759pA) {
        Objects.requireNonNull(c2759pA);
        return new Iz$$ExternalSyntheticLambda0(c2759pA);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Consumer a(C2973sA c2973sA) {
        Objects.requireNonNull(c2973sA);
        return new Kz$$ExternalSyntheticLambda1(c2973sA);
    }
}
