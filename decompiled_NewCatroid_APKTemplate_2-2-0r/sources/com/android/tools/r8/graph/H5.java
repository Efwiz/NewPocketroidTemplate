package com.android.tools.r8.graph;

import com.android.tools.r8.ProgramResource;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.utils.C3912j0;
import com.android.tools.r8.utils.C3970u0;
import java.util.ArrayList;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class H5 {
    static final /* synthetic */ boolean e = true;
    private final C0702m a;
    private final C0722p1 b;
    private final IdentityHashMap c = new IdentityHashMap();
    private final IdentityHashMap d;

    public H5(C0702m c0702m) {
        new IdentityHashMap();
        this.d = new IdentityHashMap();
        this.a = c0702m;
        this.b = c0702m.a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(Y0 y0) {
        Y0 y0A = a(y0);
        if (y0 != y0A) {
            a(y0, y0A);
        }
    }

    public final void a() {
        if (this.a.f().i()) {
            C3733i c3733iO = this.a.f().o();
            c3733iO.t().forEach(new Consumer() { // from class: com.android.tools.r8.graph.H5$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.b((Y0) obj);
                }
            });
            c3733iO.u().forEach(new Consumer() { // from class: com.android.tools.r8.graph.H5$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.b((C0684j2) obj);
                }
            });
        }
    }

    public abstract void a(Y0 y0, Y0 y1);

    public abstract void a(C0684j2 c0684j2, C0684j2 c0684j3);

    public abstract void a(C0767v2 c0767v2, C0767v2 c0767v3);

    public abstract C0767v2 b(C0767v2 c0767v2);

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(C0684j2 c0684j2) {
        C0684j2 c0684j2A = a(c0684j2);
        if (c0684j2 != c0684j2A) {
            a(c0684j2, c0684j2A);
        }
    }

    public W0 a(W0 w0, W0 w1) {
        a(w0.getReference(), w1.getReference());
        return w1;
    }

    public final ArrayList a(Collection collection) {
        ArrayList arrayList = new ArrayList();
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            final C0729q2 c0729q2 = (C0729q2) it.next();
            arrayList.add((C0729q2) this.c.computeIfAbsent(c0729q2.getType(), new Function() { // from class: com.android.tools.r8.graph.H5$$ExternalSyntheticLambda4
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return this.f$0.a(c0729q2, (C0767v2) obj);
                }
            }));
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ C0729q2 a(C0729q2 c0729q2, C0767v2 c0767v2) {
        return a(c0729q2);
    }

    /* JADX WARN: Code duplicated, block: B:40:0x00c2  */
    /* JADX WARN: Code duplicated, block: B:41:0x00c7  */
    /* JADX WARN: Code duplicated, block: B:44:0x00db  */
    /* JADX WARN: Code duplicated, block: B:46:0x00f9  */
    /* JADX WARN: Code duplicated, block: B:47:0x00fc  */
    /* JADX WARN: Code duplicated, block: B:51:0x010b  */
    /* JADX WARN: Code duplicated, block: B:55:0x0119  */
    /* JADX WARN: Code duplicated, block: B:56:0x0122  */
    /* JADX WARN: Code duplicated, block: B:59:0x0136  */
    /* JADX WARN: Code duplicated, block: B:61:0x0160  */
    /* JADX WARN: Code duplicated, block: B:62:0x0163  */
    /* JADX WARN: Code duplicated, block: B:66:0x017a  */
    /* JADX WARN: Code duplicated, block: B:70:0x01e5  */
    /* JADX WARN: Code duplicated, block: B:73:0x01f2  */
    /* JADX WARN: Code duplicated, block: B:76:0x01fb  */
    /* JADX WARN: Code duplicated, block: B:79:0x020c  */
    private C0729q2 a(final C0729q2 c0729q2) {
        C0729q2 c0729q3;
        List<X4> list;
        List list2;
        List<C0670h5> listJ1;
        ArrayList arrayList;
        Iterator<C0670h5> it;
        boolean z;
        List<C0670h5> list3;
        List list4;
        List list5;
        C0767v2 c0767v2A;
        C0767v2 c0767v2A2;
        boolean z2;
        List<v5> listK1;
        ArrayList arrayList2;
        Iterator<v5> it2;
        boolean z3;
        List<v5> list6;
        C0759u2 c0759u2;
        W4 w4;
        List list7;
        List list8;
        Y0 y0B;
        Y0 y0A;
        boolean z4;
        C0767v2 c0767v2A3 = a(c0729q2.getType());
        ProgramResource.Kind kindW1 = c0729q2.W1();
        Origin origin = c0729q2.c;
        E accessFlags = c0729q2.getAccessFlags();
        C0767v2 c0767v2 = c0729q2.f;
        C0767v2 c0767v2A4 = c0767v2 == null ? null : a(c0767v2);
        C0800x2 c0800x2 = c0729q2.g;
        C0767v2[] c0767v2Arr = c0800x2.a;
        int length = c0767v2Arr.length;
        C0767v2[] c0767v2Arr2 = new C0767v2[length];
        int i = 0;
        boolean z5 = false;
        while (true) {
            boolean z6 = true;
            if (i >= length) {
                break;
            }
            C0767v2 c0767v3 = c0767v2Arr[i];
            C0767v2 c0767v2A5 = a(c0767v3);
            c0767v2Arr2[i] = c0767v2A5;
            if (c0767v2A5 == c0767v3) {
                z6 = false;
            }
            z5 |= z6;
            i++;
        }
        if (z5) {
            c0767v2Arr = c0767v2Arr2;
        }
        if (c0767v2Arr != c0800x2.a) {
            c0800x2 = new C0800x2(c0767v2Arr);
        }
        C0800x2 c0800x3 = c0800x2;
        C0759u2 c0759u2M1 = c0729q2.m1();
        W4 w4H1 = c0729q2.h1();
        W4 w5 = w4H1 != null ? new W4(a(w4H1.a())) : null;
        List<X4> listI1 = c0729q2.i1();
        if (!listI1.isEmpty()) {
            ArrayList arrayList3 = new ArrayList(listI1.size());
            Iterator<X4> it3 = listI1.iterator();
            boolean z7 = false;
            while (it3.hasNext()) {
                C0767v2 c0767v2A6 = it3.next().a();
                List<X4> list9 = listI1;
                C0767v2 c0767v2A7 = a(c0767v2A6);
                Iterator<X4> it4 = it3;
                arrayList3.add(new X4(c0767v2A7));
                z7 |= c0767v2A7 != c0767v2A6;
                listI1 = list9;
                it3 = it4;
            }
            list = listI1;
            if (z7) {
                list2 = arrayList3;
            }
            listJ1 = c0729q2.j1();
            if (listJ1.isEmpty()) {
                arrayList = new ArrayList(listJ1.size());
                it = listJ1.iterator();
                z = false;
                while (it.hasNext()) {
                    List<C0670h5> list10 = listJ1;
                    c0767v2A = it.next().a();
                    Iterator<C0670h5> it5 = it;
                    c0767v2A2 = a(c0767v2A);
                    List list11 = list2;
                    arrayList.add(new C0670h5(c0767v2A2));
                    if (c0767v2A2 != c0767v2A) {
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    z |= z2;
                    it = it5;
                    listJ1 = list10;
                    list2 = list11;
                }
                list3 = listJ1;
                list4 = list2;
                if (z) {
                    list5 = arrayList;
                }
                listK1 = c0729q2.k1();
                if (listK1.isEmpty()) {
                    arrayList2 = new ArrayList(listK1.size());
                    it2 = listK1.iterator();
                    z3 = false;
                    while (it2.hasNext()) {
                        v5 next = it2.next();
                        List<v5> list12 = listK1;
                        y0B = next.b();
                        Iterator<v5> it6 = it2;
                        y0A = a(y0B);
                        List list13 = list5;
                        W4 w6 = w5;
                        C0759u2 c0759u3 = c0759u2M1;
                        arrayList2.add(new v5(y0A, next.d(), next.a()));
                        if (y0A != y0B) {
                            z4 = true;
                        } else {
                            z4 = false;
                        }
                        z3 |= z4;
                        listK1 = list12;
                        it2 = it6;
                        list5 = list13;
                        w5 = w6;
                        c0759u2M1 = c0759u3;
                    }
                    list6 = listK1;
                    c0759u2 = c0759u2M1;
                    w4 = w5;
                    list7 = list5;
                    if (z3) {
                        list8 = arrayList2;
                    }
                    O2 o2A = a(c0729q2.b1());
                    List listA = a((List) c0729q2.e1());
                    C0706m3.b bVarA1 = c0729q2.a1();
                    C0658g0 c0658g0S0 = c0729q2.s0();
                    T0[] t0Arr = T0.m;
                    c0729q3 = new C0729q2(c0767v2A3, kindW1, origin, accessFlags, c0767v2A4, c0800x3, c0759u2, w4, list4, list7, list8, o2A, listA, bVarA1, c0658g0S0, t0Arr, t0Arr, new C0792w4.a() { // from class: com.android.tools.r8.graph.H5$$ExternalSyntheticLambda3
                        @Override // com.android.tools.r8.graph.C0792w4.a
                        public final C0792w4 a(AbstractC0727q0 abstractC0727q0) {
                            return this.f$0.a(c0729q2, abstractC0727q0);
                        }
                    }, this.b.i(), c0729q2.T1());
                    c0729q3.j.a(a(C3912j0.b(), c0729q2.w1()));
                    c0729q3.j.b(a(C3912j0.b(), c0729q2.N1()));
                    if (c0729q2.Z1()) {
                        c0729q3.b(c0729q2.U1());
                    }
                    if (c0729q2.e2()) {
                        c0729q3.i2();
                    }
                    if (c0729q2.V1() != null) {
                        c0729q3.a(c0729q2.V1());
                    }
                    if (c0729q3.getType() != c0729q2.getType()) {
                        a(c0729q2.getType(), c0729q3.getType());
                    }
                    return c0729q3;
                }
                list6 = listK1;
                c0759u2 = c0759u2M1;
                w4 = w5;
                list7 = list5;
                list8 = list6;
                O2 o2A2 = a(c0729q2.b1());
                List listA2 = a((List) c0729q2.e1());
                C0706m3.b bVarA2 = c0729q2.a1();
                C0658g0 c0658g0S1 = c0729q2.s0();
                T0[] t0Arr2 = T0.m;
                c0729q3 = new C0729q2(c0767v2A3, kindW1, origin, accessFlags, c0767v2A4, c0800x3, c0759u2, w4, list4, list7, list8, o2A2, listA2, bVarA2, c0658g0S1, t0Arr2, t0Arr2, new C0792w4.a() { // from class: com.android.tools.r8.graph.H5$$ExternalSyntheticLambda3
                    @Override // com.android.tools.r8.graph.C0792w4.a
                    public final C0792w4 a(AbstractC0727q0 abstractC0727q0) {
                        return this.f$0.a(c0729q2, abstractC0727q0);
                    }
                }, this.b.i(), c0729q2.T1());
                c0729q3.j.a(a(C3912j0.b(), c0729q2.w1()));
                c0729q3.j.b(a(C3912j0.b(), c0729q2.N1()));
                if (c0729q2.Z1()) {
                    c0729q3.b(c0729q2.U1());
                }
                if (c0729q2.e2()) {
                    c0729q3.i2();
                }
                if (c0729q2.V1() != null) {
                    c0729q3.a(c0729q2.V1());
                }
                if (c0729q3.getType() != c0729q2.getType()) {
                    a(c0729q2.getType(), c0729q3.getType());
                }
                return c0729q3;
            }
            list3 = listJ1;
            list4 = list2;
            list5 = list3;
            listK1 = c0729q2.k1();
            if (listK1.isEmpty()) {
                arrayList2 = new ArrayList(listK1.size());
                it2 = listK1.iterator();
                z3 = false;
                while (it2.hasNext()) {
                    v5 next2 = it2.next();
                    List<v5> list14 = listK1;
                    y0B = next2.b();
                    Iterator<v5> it7 = it2;
                    y0A = a(y0B);
                    List list15 = list5;
                    W4 w7 = w5;
                    C0759u2 c0759u4 = c0759u2M1;
                    arrayList2.add(new v5(y0A, next2.d(), next2.a()));
                    if (y0A != y0B) {
                        z4 = true;
                    } else {
                        z4 = false;
                    }
                    z3 |= z4;
                    listK1 = list14;
                    it2 = it7;
                    list5 = list15;
                    w5 = w7;
                    c0759u2M1 = c0759u4;
                }
                list6 = listK1;
                c0759u2 = c0759u2M1;
                w4 = w5;
                list7 = list5;
                if (z3) {
                    list8 = arrayList2;
                }
                O2 o2A3 = a(c0729q2.b1());
                List listA3 = a((List) c0729q2.e1());
                C0706m3.b bVarA3 = c0729q2.a1();
                C0658g0 c0658g0S2 = c0729q2.s0();
                T0[] t0Arr3 = T0.m;
                c0729q3 = new C0729q2(c0767v2A3, kindW1, origin, accessFlags, c0767v2A4, c0800x3, c0759u2, w4, list4, list7, list8, o2A3, listA3, bVarA3, c0658g0S2, t0Arr3, t0Arr3, new C0792w4.a() { // from class: com.android.tools.r8.graph.H5$$ExternalSyntheticLambda3
                    @Override // com.android.tools.r8.graph.C0792w4.a
                    public final C0792w4 a(AbstractC0727q0 abstractC0727q0) {
                        return this.f$0.a(c0729q2, abstractC0727q0);
                    }
                }, this.b.i(), c0729q2.T1());
                c0729q3.j.a(a(C3912j0.b(), c0729q2.w1()));
                c0729q3.j.b(a(C3912j0.b(), c0729q2.N1()));
                if (c0729q2.Z1()) {
                    c0729q3.b(c0729q2.U1());
                }
                if (c0729q2.e2()) {
                    c0729q3.i2();
                }
                if (c0729q2.V1() != null) {
                    c0729q3.a(c0729q2.V1());
                }
                if (c0729q3.getType() != c0729q2.getType()) {
                    a(c0729q2.getType(), c0729q3.getType());
                }
                return c0729q3;
            }
            list6 = listK1;
            c0759u2 = c0759u2M1;
            w4 = w5;
            list7 = list5;
            list8 = list6;
            O2 o2A4 = a(c0729q2.b1());
            List listA4 = a((List) c0729q2.e1());
            C0706m3.b bVarA4 = c0729q2.a1();
            C0658g0 c0658g0S3 = c0729q2.s0();
            T0[] t0Arr4 = T0.m;
            c0729q3 = new C0729q2(c0767v2A3, kindW1, origin, accessFlags, c0767v2A4, c0800x3, c0759u2, w4, list4, list7, list8, o2A4, listA4, bVarA4, c0658g0S3, t0Arr4, t0Arr4, new C0792w4.a() { // from class: com.android.tools.r8.graph.H5$$ExternalSyntheticLambda3
                @Override // com.android.tools.r8.graph.C0792w4.a
                public final C0792w4 a(AbstractC0727q0 abstractC0727q0) {
                    return this.f$0.a(c0729q2, abstractC0727q0);
                }
            }, this.b.i(), c0729q2.T1());
            c0729q3.j.a(a(C3912j0.b(), c0729q2.w1()));
            c0729q3.j.b(a(C3912j0.b(), c0729q2.N1()));
            if (c0729q2.Z1()) {
                c0729q3.b(c0729q2.U1());
            }
            if (c0729q2.e2()) {
                c0729q3.i2();
            }
            if (c0729q2.V1() != null) {
                c0729q3.a(c0729q2.V1());
            }
            if (c0729q3.getType() != c0729q2.getType()) {
                a(c0729q2.getType(), c0729q3.getType());
            }
            return c0729q3;
        }
        list = listI1;
        list2 = list;
        listJ1 = c0729q2.j1();
        if (listJ1.isEmpty()) {
            arrayList = new ArrayList(listJ1.size());
            it = listJ1.iterator();
            z = false;
            while (it.hasNext()) {
                List<C0670h5> list16 = listJ1;
                c0767v2A = it.next().a();
                Iterator<C0670h5> it8 = it;
                c0767v2A2 = a(c0767v2A);
                List list17 = list2;
                arrayList.add(new C0670h5(c0767v2A2));
                if (c0767v2A2 != c0767v2A) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                z |= z2;
                it = it8;
                listJ1 = list16;
                list2 = list17;
            }
            list3 = listJ1;
            list4 = list2;
            if (z) {
                list5 = arrayList;
            }
            listK1 = c0729q2.k1();
            if (listK1.isEmpty()) {
                arrayList2 = new ArrayList(listK1.size());
                it2 = listK1.iterator();
                z3 = false;
                while (it2.hasNext()) {
                    v5 next3 = it2.next();
                    List<v5> list18 = listK1;
                    y0B = next3.b();
                    Iterator<v5> it9 = it2;
                    y0A = a(y0B);
                    List list19 = list5;
                    W4 w8 = w5;
                    C0759u2 c0759u5 = c0759u2M1;
                    arrayList2.add(new v5(y0A, next3.d(), next3.a()));
                    if (y0A != y0B) {
                        z4 = true;
                    } else {
                        z4 = false;
                    }
                    z3 |= z4;
                    listK1 = list18;
                    it2 = it9;
                    list5 = list19;
                    w5 = w8;
                    c0759u2M1 = c0759u5;
                }
                list6 = listK1;
                c0759u2 = c0759u2M1;
                w4 = w5;
                list7 = list5;
                if (z3) {
                    list8 = arrayList2;
                }
                O2 o2A5 = a(c0729q2.b1());
                List listA5 = a((List) c0729q2.e1());
                C0706m3.b bVarA5 = c0729q2.a1();
                C0658g0 c0658g0S4 = c0729q2.s0();
                T0[] t0Arr5 = T0.m;
                c0729q3 = new C0729q2(c0767v2A3, kindW1, origin, accessFlags, c0767v2A4, c0800x3, c0759u2, w4, list4, list7, list8, o2A5, listA5, bVarA5, c0658g0S4, t0Arr5, t0Arr5, new C0792w4.a() { // from class: com.android.tools.r8.graph.H5$$ExternalSyntheticLambda3
                    @Override // com.android.tools.r8.graph.C0792w4.a
                    public final C0792w4 a(AbstractC0727q0 abstractC0727q0) {
                        return this.f$0.a(c0729q2, abstractC0727q0);
                    }
                }, this.b.i(), c0729q2.T1());
                c0729q3.j.a(a(C3912j0.b(), c0729q2.w1()));
                c0729q3.j.b(a(C3912j0.b(), c0729q2.N1()));
                if (c0729q2.Z1()) {
                    c0729q3.b(c0729q2.U1());
                }
                if (c0729q2.e2()) {
                    c0729q3.i2();
                }
                if (c0729q2.V1() != null) {
                    c0729q3.a(c0729q2.V1());
                }
                if (c0729q3.getType() != c0729q2.getType()) {
                    a(c0729q2.getType(), c0729q3.getType());
                }
                return c0729q3;
            }
            list6 = listK1;
            c0759u2 = c0759u2M1;
            w4 = w5;
            list7 = list5;
            list8 = list6;
            O2 o2A6 = a(c0729q2.b1());
            List listA6 = a((List) c0729q2.e1());
            C0706m3.b bVarA6 = c0729q2.a1();
            C0658g0 c0658g0S5 = c0729q2.s0();
            T0[] t0Arr6 = T0.m;
            c0729q3 = new C0729q2(c0767v2A3, kindW1, origin, accessFlags, c0767v2A4, c0800x3, c0759u2, w4, list4, list7, list8, o2A6, listA6, bVarA6, c0658g0S5, t0Arr6, t0Arr6, new C0792w4.a() { // from class: com.android.tools.r8.graph.H5$$ExternalSyntheticLambda3
                @Override // com.android.tools.r8.graph.C0792w4.a
                public final C0792w4 a(AbstractC0727q0 abstractC0727q0) {
                    return this.f$0.a(c0729q2, abstractC0727q0);
                }
            }, this.b.i(), c0729q2.T1());
            c0729q3.j.a(a(C3912j0.b(), c0729q2.w1()));
            c0729q3.j.b(a(C3912j0.b(), c0729q2.N1()));
            if (c0729q2.Z1()) {
                c0729q3.b(c0729q2.U1());
            }
            if (c0729q2.e2()) {
                c0729q3.i2();
            }
            if (c0729q2.V1() != null) {
                c0729q3.a(c0729q2.V1());
            }
            if (c0729q3.getType() != c0729q2.getType()) {
                a(c0729q2.getType(), c0729q3.getType());
            }
            return c0729q3;
        }
        list3 = listJ1;
        list4 = list2;
        list5 = list3;
        listK1 = c0729q2.k1();
        if (listK1.isEmpty()) {
            arrayList2 = new ArrayList(listK1.size());
            it2 = listK1.iterator();
            z3 = false;
            while (it2.hasNext()) {
                v5 next4 = it2.next();
                List<v5> list110 = listK1;
                y0B = next4.b();
                Iterator<v5> it10 = it2;
                y0A = a(y0B);
                List list111 = list5;
                W4 w9 = w5;
                C0759u2 c0759u6 = c0759u2M1;
                arrayList2.add(new v5(y0A, next4.d(), next4.a()));
                if (y0A != y0B) {
                    z4 = true;
                } else {
                    z4 = false;
                }
                z3 |= z4;
                listK1 = list110;
                it2 = it10;
                list5 = list111;
                w5 = w9;
                c0759u2M1 = c0759u6;
            }
            list6 = listK1;
            c0759u2 = c0759u2M1;
            w4 = w5;
            list7 = list5;
            if (z3) {
                list8 = arrayList2;
            }
            O2 o2A7 = a(c0729q2.b1());
            List listA7 = a((List) c0729q2.e1());
            C0706m3.b bVarA7 = c0729q2.a1();
            C0658g0 c0658g0S6 = c0729q2.s0();
            T0[] t0Arr7 = T0.m;
            c0729q3 = new C0729q2(c0767v2A3, kindW1, origin, accessFlags, c0767v2A4, c0800x3, c0759u2, w4, list4, list7, list8, o2A7, listA7, bVarA7, c0658g0S6, t0Arr7, t0Arr7, new C0792w4.a() { // from class: com.android.tools.r8.graph.H5$$ExternalSyntheticLambda3
                @Override // com.android.tools.r8.graph.C0792w4.a
                public final C0792w4 a(AbstractC0727q0 abstractC0727q0) {
                    return this.f$0.a(c0729q2, abstractC0727q0);
                }
            }, this.b.i(), c0729q2.T1());
            c0729q3.j.a(a(C3912j0.b(), c0729q2.w1()));
            c0729q3.j.b(a(C3912j0.b(), c0729q2.N1()));
            if (c0729q2.Z1()) {
                c0729q3.b(c0729q2.U1());
            }
            if (c0729q2.e2()) {
                c0729q3.i2();
            }
            if (c0729q2.V1() != null) {
                c0729q3.a(c0729q2.V1());
            }
            if (c0729q3.getType() != c0729q2.getType()) {
                a(c0729q2.getType(), c0729q3.getType());
            }
            return c0729q3;
        }
        list6 = listK1;
        c0759u2 = c0759u2M1;
        w4 = w5;
        list7 = list5;
        list8 = list6;
        O2 o2A8 = a(c0729q2.b1());
        List listA8 = a((List) c0729q2.e1());
        C0706m3.b bVarA8 = c0729q2.a1();
        C0658g0 c0658g0S7 = c0729q2.s0();
        T0[] t0Arr8 = T0.m;
        c0729q3 = new C0729q2(c0767v2A3, kindW1, origin, accessFlags, c0767v2A4, c0800x3, c0759u2, w4, list4, list7, list8, o2A8, listA8, bVarA8, c0658g0S7, t0Arr8, t0Arr8, new C0792w4.a() { // from class: com.android.tools.r8.graph.H5$$ExternalSyntheticLambda3
            @Override // com.android.tools.r8.graph.C0792w4.a
            public final C0792w4 a(AbstractC0727q0 abstractC0727q0) {
                return this.f$0.a(c0729q2, abstractC0727q0);
            }
        }, this.b.i(), c0729q2.T1());
        c0729q3.j.a(a(C3912j0.b(), c0729q2.w1()));
        c0729q3.j.b(a(C3912j0.b(), c0729q2.N1()));
        if (c0729q2.Z1()) {
            c0729q3.b(c0729q2.U1());
        }
        if (c0729q2.e2()) {
            c0729q3.i2();
        }
        if (c0729q2.V1() != null) {
            c0729q3.a(c0729q2.V1());
        }
        if (c0729q3.getType() != c0729q2.getType()) {
            a(c0729q2.getType(), c0729q3.getType());
        }
        return c0729q3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ C0792w4 a(C0729q2 c0729q2, AbstractC0727q0 abstractC0727q0) {
        return c0729q2.Y().a(abstractC0727q0, new Function() { // from class: com.android.tools.r8.graph.H5$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a((W0) obj);
            }
        });
    }

    protected final O2 a(O2 o2) {
        if (o2 == null) {
            return null;
        }
        C0767v2 c0767v2A = o2.a();
        if (c0767v2A != null) {
            C0767v2 c0767v2A2 = a(c0767v2A);
            return c0767v2A2 != c0767v2A ? new O2(c0767v2A2) : o2;
        }
        C0684j2 c0684j2B = o2.b();
        if (!e && c0684j2B == null) {
            throw new AssertionError();
        }
        C0684j2 c0684j2A = a(o2.b());
        return c0684j2A != c0684j2B ? new O2(c0684j2A) : o2;
    }

    public final T0[] a(Consumer consumer, List list) {
        if (list == null) {
            return T0.m;
        }
        T0[] t0Arr = new T0[list.size()];
        for (int i = 0; i < list.size(); i++) {
            T0 t0 = (T0) list.get(i);
            Y0 reference = t0.getReference();
            Y0 y0A = a(reference);
            if (y0A != reference) {
                T0 t0A = t0.a(this.a, y0A, consumer);
                a(t0.getReference(), t0A.getReference());
                t0 = t0A;
            }
            t0Arr[i] = t0;
        }
        return t0Arr;
    }

    public Y0 a(Y0 y0) {
        C0767v2 c0767v2A = a(y0.h);
        return this.b.a(a(y0.e), c0767v2A, y0.f);
    }

    protected final List a(List list) {
        if (list.isEmpty()) {
            return list;
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        boolean z = false;
        while (it.hasNext()) {
            J3 j3 = (J3) it.next();
            C0767v2 c0767v2B = j3.b();
            C0767v2 c0767v2A = c0767v2B != null ? a(c0767v2B) : null;
            C0767v2 c0767v2D = j3.d();
            C0767v2 c0767v2A2 = c0767v2D != null ? a(c0767v2D) : null;
            C0759u2 c0759u2C = j3.c();
            boolean z2 = (c0767v2A == c0767v2B && c0767v2A2 == c0767v2D) ? false : true;
            if (z2 && c0767v2B != null && c0767v2D != null && j3.c() != null) {
                String strA = C3970u0.a(c0767v2A2.b1(), c0767v2A.b1());
                if (strA != null) {
                    c0759u2C = this.b.c(strA);
                } else {
                    boolean z3 = e;
                    if (!z3 && this.a.Y().o1()) {
                        throw new AssertionError();
                    }
                    if (!z3 && this.a.f().d(c0767v2A2) != null) {
                        throw new AssertionError();
                    }
                }
            }
            arrayList.add(new J3(j3.a(), c0767v2A, c0767v2A2, c0759u2C));
            z |= z2;
        }
        return z ? arrayList : list;
    }

    public final W0 a(W0 w0) {
        C0684j2 reference = w0.getReference();
        C0684j2 c0684j2A = a(reference);
        return c0684j2A != reference ? a(w0, w0.a(c0684j2A)) : w0;
    }

    public C0684j2 a(C0684j2 c0684j2) {
        C0722p1 c0722p1 = this.b;
        C0767v2 c0767v2A = a(c0684j2.e);
        C0737r2 c0737r2 = c0684j2.h;
        C0737r2 c0737r2B = (C0737r2) this.d.get(c0737r2);
        if (c0737r2B == null) {
            C0767v2 c0767v2A2 = a(c0737r2.e);
            C0767v2[] c0767v2Arr = c0737r2.f.a;
            int length = c0767v2Arr.length;
            C0767v2[] c0767v2Arr2 = new C0767v2[length];
            boolean z = false;
            for (int i = 0; i < length; i++) {
                C0767v2 c0767v2 = c0767v2Arr[i];
                C0767v2 c0767v2A3 = a(c0767v2);
                c0767v2Arr2[i] = c0767v2A3;
                z |= c0767v2A3 != c0767v2;
            }
            if (z) {
                c0767v2Arr = c0767v2Arr2;
            }
            c0737r2B = this.b.b(c0767v2A2, c0767v2Arr);
            this.d.put(c0737r2, c0737r2B);
        }
        return c0722p1.a(c0767v2A, c0737r2B, c0684j2.f);
    }

    public C0767v2 a(C0767v2 c0767v2) {
        if (!c0767v2.M0()) {
            return c0767v2.O0() ? b(c0767v2) : c0767v2;
        }
        C0767v2 c0767v2B = c0767v2.b(this.b);
        C0767v2 c0767v2A = a(c0767v2B);
        return c0767v2B == c0767v2A ? c0767v2 : c0767v2.a(this.b, c0767v2A);
    }

    public final AbstractC0717o2 a(AbstractC0717o2 abstractC0717o2) {
        C0737r2 c0737r2B = abstractC0717o2.b();
        C0737r2 c0737r2B2 = (C0737r2) this.d.get(c0737r2B);
        if (c0737r2B2 == null) {
            C0767v2 c0767v2A = a(c0737r2B.e);
            C0767v2[] c0767v2Arr = c0737r2B.f.a;
            int length = c0767v2Arr.length;
            C0767v2[] c0767v2Arr2 = new C0767v2[length];
            boolean z = false;
            for (int i = 0; i < length; i++) {
                C0767v2 c0767v2 = c0767v2Arr[i];
                C0767v2 c0767v2A2 = a(c0767v2);
                c0767v2Arr2[i] = c0767v2A2;
                z |= c0767v2A2 != c0767v2;
            }
            if (z) {
                c0767v2Arr = c0767v2Arr2;
            }
            c0737r2B2 = this.b.b(c0767v2A, c0767v2Arr);
            this.d.put(c0737r2B, c0737r2B2);
        }
        return new C0711n2(c0737r2B2, abstractC0717o2.a());
    }
}
