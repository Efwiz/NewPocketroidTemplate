package com.android.tools.r8.dex;

import com.android.tools.r8.ByteBufferProvider;
import com.android.tools.r8.ByteDataView;
import com.android.tools.r8.C3601m;
import com.android.tools.r8.DataEntryResource;
import com.android.tools.r8.DataResourceConsumer;
import com.android.tools.r8.DataResourceProvider;
import com.android.tools.r8.DexFilePerClassFileConsumer;
import com.android.tools.r8.DexIndexedConsumer;
import com.android.tools.r8.ExtractMarker$$ExternalSyntheticLambda1;
import com.android.tools.r8.ProgramConsumer;
import com.android.tools.r8.ResourceException;
import com.android.tools.r8.SourceFileEnvironment;
import com.android.tools.r8.StringConsumer;
import com.android.tools.r8.graph.A2;
import com.android.tools.r8.graph.A3;
import com.android.tools.r8.graph.AbstractC0681j0;
import com.android.tools.r8.graph.B2;
import com.android.tools.r8.graph.C0633d0;
import com.android.tools.r8.graph.C0645e5;
import com.android.tools.r8.graph.C0649f0;
import com.android.tools.r8.graph.C0658g0;
import com.android.tools.r8.graph.C0664h;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0706m3;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.I2;
import com.android.tools.r8.graph.J3;
import com.android.tools.r8.graph.O2;
import com.android.tools.r8.graph.R0;
import com.android.tools.r8.graph.T0;
import com.android.tools.r8.graph.W0;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.graph.v5;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.AbstractC3135uW;
import com.android.tools.r8.internal.AbstractC3417yB;
import com.android.tools.r8.internal.C1367Tb;
import com.android.tools.r8.internal.C1525Yd;
import com.android.tools.r8.internal.C2783pW;
import com.android.tools.r8.internal.C3381xh;
import com.android.tools.r8.internal.C3451yh;
import com.android.tools.r8.internal.WP;
import com.android.tools.r8.internal.YR;
import com.android.tools.r8.naming.AbstractC3649o0;
import com.android.tools.r8.naming.J0;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.shaking.N1;
import com.android.tools.r8.utils.A1;
import com.android.tools.r8.utils.AbstractC3972u2;
import com.android.tools.r8.utils.AbstractC3983w1;
import com.android.tools.r8.utils.C3879c3;
import com.android.tools.r8.utils.C3945p;
import com.android.tools.r8.utils.C3967t1;
import com.android.tools.r8.utils.C3970u0;
import com.android.tools.r8.utils.C3971u1;
import com.android.tools.r8.utils.C3988x2;
import com.android.tools.r8.utils.InterfaceC3910i3;
import com.android.tools.r8.utils.InterfaceC3949p3;
import com.android.tools.r8.utils.V1;
import com.android.tools.r8.utils.Y2;
import com.android.tools.r8.utils.w3;
import com.android.tools.r8.utils.y3;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;

/* JADX INFO: renamed from: com.android.tools.r8.dex.k, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C0548k {
    static final /* synthetic */ boolean k = true;
    public final C0702m a;
    public final A1 b;
    private final r c;
    private final Predicate d;
    private final Optional e;
    public Collection f;
    public List g;
    public HashSet h;
    public DexIndexedConsumer i;
    public AbstractC3983w1 j;

    protected C0548k(C0702m c0702m, U u, C3601m c3601m) {
        this.a = c0702m;
        this.b = c0702m.Y();
        this.c = r.a(c0702m);
        this.e = Optional.ofNullable(u);
        this.i = c3601m;
        C0664h c0664hF = c0702m.f();
        Objects.requireNonNull(c0664hF);
        this.d = C3988x2.a(new C0562k$$ExternalSyntheticLambda20(c0664hF));
        this.f = c0702m.a().h();
    }

    public static C0548k a(C0702m<?> c0702m, U u) {
        return a(c0702m, u, (C3601m) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:30:0x00aa  */
    /* JADX WARN: Code duplicated, block: B:31:0x00bc  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v5, types: [com.android.tools.r8.ByteBufferProvider] */
    /* JADX WARN: Type inference failed for: r2v6 */
    /* JADX WARN: Type inference failed for: r2v7 */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.android.tools.r8.dex.k] */
    public y3 b(List list, r0 r0Var) {
        ProgramConsumer programConsumer;
        ?? r2;
        ByteDataView byteDataView;
        y3 y3VarA = y3.a(this.b, "VirtualFile " + r0Var.g());
        if (!r0Var.m()) {
            a(r0Var);
            HashSet hashSet = this.h;
            if (hashSet == null || !hashSet.contains(r0Var)) {
                programConsumer = this.i;
                if (programConsumer == null) {
                    if (r0Var.k() != null) {
                        programConsumer = (DexFilePerClassFileConsumer) this.b.f;
                    } else if (r0Var.f() != null) {
                        programConsumer = r0Var.f().getProgramConsumer();
                        if (!k && !(programConsumer instanceof DexIndexedConsumer)) {
                            throw new AssertionError();
                        }
                        r2 = (DexIndexedConsumer) programConsumer;
                    } else {
                        programConsumer = (DexIndexedConsumer) this.b.f;
                    }
                }
                y3VarA.a("Reindex for lazy strings");
                C0645e5 c0645e5J = r0Var.j();
                c0645e5J.a(list);
                y3VarA.c();
                y3VarA.a("Write bytes");
                E eA = a(c0645e5J, r2, r0Var, y3VarA);
                byteDataView = new ByteDataView(eA.a.c().array(), eA.a.c().arrayOffset(), eA.b);
                y3VarA.c();
                y3VarA.a("Pass bytes to consumer");
                if (programConsumer instanceof DexFilePerClassFileConsumer) {
                    ((DexFilePerClassFileConsumer) programConsumer).accept(r0Var.k(), byteDataView, r0Var.d(), this.b.e);
                } else {
                    ((DexIndexedConsumer) programConsumer).accept(r0Var.g(), byteDataView, r0Var.d(), this.b.e);
                }
                y3VarA.c();
                byteDataView.invalidate();
                r2.releaseByteBuffer(eA.a.c());
            } else {
                programConsumer = this.j;
            }
            r2 = programConsumer;
            y3VarA.a("Reindex for lazy strings");
            C0645e5 c0645e5J2 = r0Var.j();
            c0645e5J2.a(list);
            y3VarA.c();
            y3VarA.a("Write bytes");
            E eA2 = a(c0645e5J2, r2, r0Var, y3VarA);
            byteDataView = new ByteDataView(eA2.a.c().array(), eA2.a.c().arrayOffset(), eA2.b);
            y3VarA.c();
            y3VarA.a("Pass bytes to consumer");
            if (programConsumer instanceof DexFilePerClassFileConsumer) {
                ((DexFilePerClassFileConsumer) programConsumer).accept(r0Var.k(), byteDataView, r0Var.d(), this.b.e);
            } else {
                ((DexIndexedConsumer) programConsumer).accept(r0Var.g(), byteDataView, r0Var.d(), this.b.e);
            }
            y3VarA.c();
            byteDataView.invalidate();
            r2.releaseByteBuffer(eA2.a.c());
        }
        y3VarA.c();
        return y3VarA;
    }

    public void c(ExecutorService executorService) throws ExecutionException, IOException {
        if (!k && this.b.x1 != null) {
            throw new AssertionError();
        }
        a((C3945p) null, executorService);
    }

    public static C0548k a(C0702m c0702m, U u, C3601m c3601m) {
        return c0702m.Y().n1.j ? new C0574l(c0702m, u, c3601m) : new C0548k(c0702m, u, c3601m);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] */
    public final y3 a(List list, r0 r0Var) {
        y3 y3VarA = y3.a(this.b, "VirtualFile " + r0Var.g());
        if (!r0Var.m()) {
            y3VarA.a("Compute object offset mapping");
            r0Var.a(this.a, list.size(), y3VarA, (C0645e5) null);
            y3VarA.c();
            y3VarA.a("Rewrite jumbo strings");
            a(r0Var.j(), r0Var.b(), this.a.f().b());
            y3VarA.c();
        }
        C1525Yd.a(this.a, r0Var);
        y3VarA.c();
        return y3VarA;
    }

    public final r a() {
        return this.c;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(C0759u2 c0759u2, C0729q2 c0729q2) {
        c0729q2.h = c0759u2;
    }

    private List a(ExecutorService executorService) {
        Collection collection;
        AbstractC3135uW abstractC3135uWA;
        g0 j0Var;
        AbstractC3983w1 c3967t1;
        Collection<C0729q2> collectionD = this.a.f().d();
        ArrayList arrayList = new ArrayList();
        if (this.a.Y().t0 && this.a.Y().K0()) {
            ArrayList arrayList2 = new ArrayList(collectionD.size());
            for (C0729q2 c0729q2 : collectionD) {
                com.android.tools.r8.synthesis.D D = this.a.D();
                D.getClass();
                if (D.g(c0729q2.getType())) {
                    arrayList.add(c0729q2);
                } else {
                    arrayList2.add(c0729q2);
                }
            }
            collection = arrayList2;
        } else {
            collection = collectionD;
        }
        A1 a1 = this.b;
        ProgramConsumer programConsumer = a1.f;
        if (programConsumer instanceof DexFilePerClassFileConsumer) {
            j0Var = new i0(this, collection, ((DexFilePerClassFileConsumer) programConsumer).combineSyntheticClassesWithPrimaryClass());
        } else if (!a1.T() && this.b.o1.isEmpty() && this.a.f().f().d() && this.b.L0) {
            j0Var = new m0(this, collection, this.b);
        } else {
            if (this.a.f().h()) {
                abstractC3135uWA = this.a.C();
            } else {
                abstractC3135uWA = AbstractC3135uW.a(this.a);
            }
            j0Var = new j0(this, collection, this.b, executorService, abstractC3135uWA);
        }
        ArrayList arrayListA = j0Var.a();
        if (!arrayList.isEmpty()) {
            HashSet hashSet = new HashSet(new i0(this, arrayList, false).a());
            this.h = hashSet;
            arrayListA.addAll(hashSet);
            A1 a2 = this.b;
            if (a2.f instanceof DexFilePerClassFileConsumer) {
                c3967t1 = new C3971u1(a2.x0(), this.a);
            } else {
                c3967t1 = new C3967t1(a2.x0());
            }
            this.j = c3967t1;
        }
        return arrayListA;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static C0759u2 a(C0722p1 c0722p1, U u) {
        return c0722p1.c(u.toString());
    }

    private void a(List list) {
        Collection<C0729q2> collectionD = this.a.f().d();
        YR yr = new YR(collectionD.size());
        for (C0729q2 c0729q2 : collectionD) {
            yr.a(this.a.z().c(c0729q2.getType()), c0729q2.S1());
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            r0 r0Var = (r0) it.next();
            C0576n c0576n = new C0576n();
            Iterator it2 = r0Var.b().iterator();
            while (it2.hasNext()) {
                C0759u2 c0759u2C = this.a.z().c(((C0729q2) it2.next()).d);
                c0576n.a(c0759u2C.toString(), yr.e(c0759u2C));
            }
            r0Var.b(this.a.a().c(c0576n.b()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public C0759u2 b(J0 j0) {
        if (!k && this.b.K1 == null) {
            throw new AssertionError();
        }
        String str = this.b.K1.get(a(j0));
        final C0759u2 c0759u2C = str == null ? null : this.b.a.c(str);
        this.a.f().d().forEach(new Consumer() { // from class: com.android.tools.r8.dex.k$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C0548k.a(c0759u2C, (C0729q2) obj);
            }
        });
        return c0759u2C;
    }

    private void b() {
        Iterator<C0729q2> it;
        C0633d0 c0633d0;
        String string;
        Iterator<C0729q2> it2;
        Iterator<C0729q2> it3 = this.a.f().d().iterator();
        while (it3.hasNext()) {
            C0729q2 next = it3.next();
            O2 o2B1 = next.b1();
            List<J3> listE1 = next.e1();
            if (o2B1 == null && listE1.isEmpty() && next.a1().a() && !next.D1() && !next.K1()) {
                it = it3;
            } else {
                int i = 2;
                ArrayList arrayList = new ArrayList(listE1.size() + 2);
                if (o2B1 != null) {
                    if (o2B1.b() != null) {
                        arrayList.add(C0633d0.a(this.b.a, o2B1.b()));
                    } else {
                        arrayList.add(C0633d0.a(this.b.a, o2B1.a()));
                    }
                }
                if (listE1.isEmpty()) {
                    it = it3;
                } else {
                    ArrayList arrayList2 = new ArrayList(listE1.size());
                    for (J3 j3 : listE1) {
                        if (next.d == j3.b()) {
                            if (o2B1 == null && (j3.d() == null || j3.e())) {
                                this.b.a(next.d, next.c, next.U1());
                            } else {
                                C0759u2 c0759u2A = this.a.z().a(j3, this.b);
                                int iA = j3.a();
                                C0722p1 c0722p1 = this.b.a;
                                C0767v2 c0767v2 = c0722p1.T4;
                                C0649f0[] c0649f0Arr = new C0649f0[i];
                                it2 = it3;
                                c0649f0Arr[0] = new C0649f0(c0722p1.c("accessFlags"), B2.g.j(iA));
                                c0649f0Arr[1] = new C0649f0(c0722p1.c("name"), c0759u2A == null ? I2.b : new B2.j(c0759u2A));
                                arrayList.add(new C0633d0(2, new R0(c0767v2, c0649f0Arr)));
                                if (j3.d() != null && j3.f()) {
                                    arrayList.add(C0633d0.a(this.b.a, j3.d()));
                                }
                            }
                        } else {
                            it2 = it3;
                            if (next.d == j3.d() && j3.f()) {
                                arrayList2.add(j3.b());
                            }
                        }
                        it3 = it2;
                        i = 2;
                    }
                    it = it3;
                    if (!arrayList2.isEmpty()) {
                        arrayList.add(C0633d0.a(this.b.a, arrayList2));
                    }
                }
                if (next.a1().b()) {
                    C0706m3.b bVarA1 = next.a1();
                    AbstractC3649o0 abstractC3649o0Z = this.a.z();
                    Predicate predicate = this.d;
                    if (bVarA1.a()) {
                        string = null;
                    } else {
                        A3 a3 = new A3(abstractC3649o0Z, predicate);
                        a3.a(bVarA1);
                        string = a3.toString();
                    }
                    arrayList.add(C0633d0.a(string, this.b.a));
                }
                if (this.b.i0) {
                    if (next.G1()) {
                        arrayList.add(C0633d0.a(this.b.a, next.i1()));
                    }
                    if (next.H1()) {
                        arrayList.add(C0633d0.a(next.h1(), this.b.a));
                    }
                }
                if (next.u1() && this.b.a0()) {
                    arrayList.add(C0633d0.b(this.b.a, next.j1()));
                }
                if (next.K1() && this.b.Y()) {
                    C0702m c0702m = this.a;
                    C0633d0[] c0633d0Arr = C0633d0.c;
                    C0722p1 c0722p1A = c0702m.a();
                    int size = next.k1().size();
                    B2.j[] jVarArr = new B2.j[size];
                    B2.k[] kVarArr = new B2.k[size];
                    B2[] b2Arr = new B2[size];
                    B2.a[] aVarArr = new B2.a[size];
                    B2.a[] aVarArr2 = new B2.a[size];
                    int i2 = 0;
                    while (i2 < size) {
                        v5 v5Var = next.k1().get(i2);
                        jVarArr[i2] = new B2.j(c0702m.z().a(v5Var.b()));
                        kVarArr[i2] = new B2.k(v5Var.e());
                        if (v5Var.d().a()) {
                            b2Arr[i2] = I2.b;
                        } else {
                            b2Arr[i2] = new A2(C0633d0.a(v5Var.d().toString(), c0722p1A).b);
                        }
                        int size2 = v5Var.a().size();
                        B2.c[] cVarArr = new B2.c[size2];
                        A2[] a2Arr = new A2[size2];
                        int i3 = size;
                        aVarArr[i2] = new B2.a(cVarArr);
                        aVarArr2[i2] = new B2.a(a2Arr);
                        int i4 = 0;
                        while (i4 < size2) {
                            int i5 = size2;
                            C0633d0 c0633d1 = v5Var.a().get(i4);
                            cVarArr[i4] = B2.c.a((byte) c0633d1.u0());
                            a2Arr[i4] = new A2(c0633d1.b);
                            i4++;
                            size2 = i5;
                            v5Var = v5Var;
                        }
                        i2++;
                        size = i3;
                    }
                    if (c0702m.Y().h0) {
                        c0633d0 = new C0633d0(2, new R0(c0722p1A.a5, new C0649f0[]{new C0649f0(c0722p1A.b5, new B2.a(jVarArr)), new C0649f0(c0722p1A.c5, new B2.a(kVarArr)), new C0649f0(c0722p1A.d5, new B2.a(b2Arr)), new C0649f0(c0722p1A.e5, new B2.a(aVarArr)), new C0649f0(c0722p1A.f5, new B2.a(aVarArr2))}));
                    } else {
                        c0633d0 = new C0633d0(2, new R0(c0722p1A.a5, new C0649f0[]{new C0649f0(c0722p1A.b5, new B2.a(jVarArr)), new C0649f0(c0722p1A.c5, new B2.a(kVarArr))}));
                    }
                    arrayList.add(c0633d0);
                }
                if (!arrayList.isEmpty()) {
                    C0633d0[] c0633d0Arr2 = next.s0().c;
                    C0633d0[] c0633d0Arr3 = (C0633d0[]) arrayList.toArray(C0633d0.c);
                    Object[] objArr = (Object[]) Array.newInstance((Class<?>) C0633d0.class, c0633d0Arr2.length + c0633d0Arr3.length);
                    System.arraycopy(c0633d0Arr2, 0, objArr, 0, c0633d0Arr2.length);
                    System.arraycopy(c0633d0Arr3, 0, objArr, c0633d0Arr2.length, c0633d0Arr3.length);
                    next.a(C0658g0.a((C0633d0[]) objArr));
                }
                next.M0();
                next.N0();
                next.L0();
                next.R0();
                next.S0();
            }
            next.W0().forEach(new Consumer() { // from class: com.android.tools.r8.dex.k$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a((T0) obj);
                }
            });
            next.M1().forEach(new Consumer() { // from class: com.android.tools.r8.dex.k$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a((W0) obj);
                }
            });
            it3 = it;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(List list, com.android.tools.r8.utils.N n, C0722p1 c0722p1, List list2, U u) {
        if (this.b.x1 != null) {
            list.add(new C0541d(u, n, c0722p1));
        } else {
            list2.add(u);
        }
    }

    protected ArrayList a(ExecutorService executorService, List list, final ArrayList arrayList) {
        return C3879c3.a(list, new InterfaceC3910i3() { // from class: com.android.tools.r8.dex.k$$ExternalSyntheticLambda12
            @Override // com.android.tools.r8.utils.InterfaceC3910i3
            public final Object apply(Object obj) {
                return this.f$0.a(arrayList, (r0) obj);
            }
        }, executorService);
    }

    protected void a(ExecutorService executorService, List list, final ArrayList arrayList, y3 y3Var) {
        w3 w3VarA = y3Var.a(C3879c3.a(executorService), "Write files");
        w3VarA.a(C3879c3.a(list, new InterfaceC3910i3() { // from class: com.android.tools.r8.dex.k$$ExternalSyntheticLambda13
            @Override // com.android.tools.r8.utils.InterfaceC3910i3
            public final Object apply(Object obj) {
                return this.f$0.b(arrayList, (r0) obj);
            }
        }, executorService));
        w3VarA.a();
        AbstractC3983w1 abstractC3983w1 = this.j;
        if (abstractC3983w1 != null) {
            abstractC3983w1.a(this.a);
        }
    }

    public final void a(C3945p c3945p, ExecutorService executorService) {
        AbstractC3972u2 abstractC3972u2B;
        Collection collection;
        y3 y3Var = this.a.f().b().c;
        y3Var.a("DexApplication.write");
        com.android.tools.r8.utils.N n = new com.android.tools.r8.utils.N();
        ArrayList<AbstractC0546i> arrayList = new ArrayList();
        a(n, arrayList);
        A1 a1 = this.b;
        if (a1.K1 == null) {
            abstractC3972u2B = AbstractC3972u2.a();
        } else if (a1.x1 == null) {
            b((J0) null);
            abstractC3972u2B = AbstractC3972u2.b();
        } else {
            Collection<C0729q2> collectionD = this.a.f().d();
            HashMap map = new HashMap(collectionD.size());
            for (C0729q2 c0729q2 : collectionD) {
                C0759u2 c0759u2M1 = c0729q2.m1();
                if (c0759u2M1 != null) {
                    map.put(c0729q2.getType(), c0759u2M1);
                    c0729q2.h = null;
                }
            }
            arrayList.add(new C0542e(this, n));
            abstractC3972u2B = AbstractC3972u2.a(map);
        }
        try {
            y3Var.a("Insert Attribute Annotations");
            b();
            y3Var.c();
            if (this.b.Y0()) {
                y3Var.a("Set call-site contexts");
                b(executorService);
                y3Var.c();
            }
            C2783pW.a(this.a);
            y3Var.a("Distribute");
            final List listA = a(executorService);
            y3Var.c();
            if (this.b.H) {
                y3Var.a("Encode checksums");
                a(listA);
                y3Var.c();
            }
            boolean z = k;
            if (!z && (collection = this.f) != null && !collection.isEmpty() && this.a.a().h() == null) {
                throw new AssertionError();
            }
            if (!z) {
                if (!((Boolean) this.a.c(Boolean.TRUE, new Function() { // from class: com.android.tools.r8.dex.k$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return C0548k.a(listA, (WP) obj);
                    }
                })).booleanValue()) {
                    throw new AssertionError();
                }
            }
            y3Var.a("Sort Annotations");
            final C0547j c0547j = new C0547j(this.a.z());
            this.a.f().d().forEach(new Consumer() { // from class: com.android.tools.r8.dex.k$$ExternalSyntheticLambda10
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((C0729q2) obj).b(c0547j);
                }
            });
            y3Var.c();
            w3 w3VarA = y3Var.a(C3879c3.a(executorService), "Pre-write phase");
            w3VarA.a(a(executorService, listA, arrayList));
            w3VarA.a();
            A1 a2 = this.b;
            if (a2.x1 != null) {
                n.a(AbstractC3417yB.a(c3945p, this.a, y3Var, abstractC3972u2B, C1525Yd.a(listA, a2)));
            }
            y3Var.a("Compute lazy strings");
            ArrayList arrayList2 = new ArrayList();
            for (AbstractC0546i abstractC0546i : arrayList) {
                if (!AbstractC0546i.b && abstractC0546i.a) {
                    throw new AssertionError();
                }
                C0759u2 c0759u2A = abstractC0546i.a();
                abstractC0546i.a = true;
                arrayList2.add(c0759u2A);
            }
            y3Var.c();
            a(executorService, listA, arrayList2, y3Var);
            if (this.b.E1 != null && !this.c.a()) {
                if (!k && this.b.C1.d()) {
                    throw new AssertionError();
                }
                this.c.a(this.b);
            }
            this.b.e.a();
            if (!(this.i instanceof C3601m)) {
                a(this.a);
            }
            y3Var.c();
        } catch (Throwable th) {
            y3Var.c();
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Boolean a(List list, final WP wp) {
        Stream stream = list.stream();
        Objects.requireNonNull(wp);
        return Boolean.valueOf(stream.allMatch(new Predicate() { // from class: com.android.tools.r8.dex.k$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return wp.a((r0) obj);
            }
        }));
    }

    private void a(final com.android.tools.r8.utils.N n, final ArrayList arrayList) {
        final ArrayList arrayList2 = new ArrayList();
        Collection collection = this.f;
        if (collection != null) {
            arrayList2.addAll(collection);
        }
        final C0722p1 c0722p1A = this.a.a();
        this.e.ifPresent(new Consumer() { // from class: com.android.tools.r8.dex.k$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(arrayList, n, c0722p1A, arrayList2, (U) obj);
            }
        });
        arrayList2.sort(Comparator.comparing(new ExtractMarker$$ExternalSyntheticLambda1()));
        this.g = V1.a((Collection) arrayList2, new Function() { // from class: com.android.tools.r8.dex.k$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C0548k.a(c0722p1A, (U) obj);
            }
        });
    }

    public static SourceFileEnvironment a(J0 j0) {
        if (j0 == null) {
            return new C0543f();
        }
        return new C0544g(j0);
    }

    public static void a(C0702m c0702m) {
        A1 a1Y = c0702m.Y();
        final com.android.tools.r8.utils.O2 o2 = a1Y.e;
        c0702m.s().a(c0702m);
        StringConsumer stringConsumer = a1Y.A1;
        if (stringConsumer != null) {
            com.android.tools.r8.utils.R0.a(o2, stringConsumer, a1Y.D0().q());
            com.android.tools.r8.utils.R0.a(o2, a1Y.A1);
        }
        StringConsumer stringConsumer2 = a1Y.w1;
        if (stringConsumer2 != null) {
            com.android.tools.r8.utils.R0.a(o2, stringConsumer2, b(c0702m));
            com.android.tools.r8.utils.R0.a(o2, a1Y.w1);
        }
        com.android.tools.r8.naming.K k2 = new com.android.tools.r8.naming.K(c0702m);
        final DataResourceConsumer dataResourceConsumer = a1Y.j;
        if (dataResourceConsumer != null) {
            AbstractC1639am abstractC1639am = c0702m.e().a;
            d0 d0Var = new d0(c0702m);
            HashSet hashSet = new HashSet();
            Iterator<E> it = abstractC1639am.iterator();
            while (it.hasNext()) {
                try {
                    ((DataResourceProvider) it.next()).accept(new C0545h(d0Var, dataResourceConsumer, a1Y, k2, hashSet));
                } catch (ResourceException e) {
                    throw new C1367Tb(e.getMessage(), e);
                }
            }
            if (!c0702m.j().c()) {
                final AbstractC3649o0 abstractC3649o0Z = c0702m.z();
                c0702m.j().a(new BiConsumer() { // from class: com.android.tools.r8.dex.k$$ExternalSyntheticLambda15
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        C0548k.a(abstractC3649o0Z, dataResourceConsumer, o2, (C0767v2) obj, (List) obj2);
                    }
                });
            }
            k2.a().forEach(new Consumer() { // from class: com.android.tools.r8.dex.k$$ExternalSyntheticLambda16
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    dataResourceConsumer.accept((DataEntryResource) obj, o2);
                }
            });
        }
        C3451yh c3451yh = a1Y.k;
        if (c3451yh != null) {
            for (C3381xh c3381xh : c3451yh.a()) {
                d0 d0Var2 = new d0(c0702m);
                DataResourceConsumer dataResourceConsumerA = c3381xh.a();
                Set setB = c3381xh.b();
                HashSet hashSet2 = new HashSet();
                Iterator it2 = setB.iterator();
                while (it2.hasNext()) {
                    try {
                        ((DataResourceProvider) it2.next()).accept(new C0545h(d0Var2, dataResourceConsumerA, a1Y, k2, hashSet2));
                    } catch (ResourceException e2) {
                        throw new C1367Tb(e2.getMessage(), e2);
                    }
                }
            }
        }
    }

    private void b(ExecutorService executorService) {
        C3879c3.a(this.a.f().d(), new Consumer() { // from class: com.android.tools.r8.dex.k$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((C0729q2) obj);
            }
        }, executorService);
    }

    private static String b(final C0702m c0702m) {
        N1 n1F = c0702m.f().f();
        final StringBuilder sb = new StringBuilder();
        ArrayList arrayList = new ArrayList(n1F.g());
        n1F.a(new C0563k$$ExternalSyntheticLambda21(arrayList));
        arrayList.sort(new C0564k$$ExternalSyntheticLambda22());
        arrayList.forEach(new Consumer() { // from class: com.android.tools.r8.dex.k$$ExternalSyntheticLambda23
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C0548k.a(sb, c0702m, (C0767v2) obj);
            }
        });
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(final AbstractC3649o0 abstractC3649o0, DataResourceConsumer dataResourceConsumer, com.android.tools.r8.utils.O2 o2, C0767v2 c0767v2, List list) {
        dataResourceConsumer.accept(DataEntryResource.fromBytes(Y2.a((List<String>) list.stream().map(new Function() { // from class: com.android.tools.r8.dex.k$$ExternalSyntheticLambda17
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return abstractC3649o0.c((C0767v2) obj);
            }
        }).map(new C0559k$$ExternalSyntheticLambda18()).map(new C0560k$$ExternalSyntheticLambda19()).collect(Collectors.toList())).getBytes(), "META-INF/services/" + C3970u0.b(abstractC3649o0.c(c0767v2).toString()), Origin.unknown()), o2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(T0 t0) {
        String string;
        if (t0.T0().a()) {
            return;
        }
        C0633d0[] c0633d0Arr = t0.s0().c;
        C0706m3.e eVarT0 = t0.T0();
        AbstractC3649o0 abstractC3649o0Z = this.a.z();
        Predicate predicate = this.d;
        if (eVarT0.a()) {
            string = null;
        } else {
            A3 a3 = new A3(abstractC3649o0Z, predicate);
            a3.a((C0706m3.i) eVarT0);
            string = a3.toString();
        }
        t0.a(C0658g0.a((C0633d0[]) com.android.tools.r8.utils.B.a(C0633d0.a(string, this.b.a), c0633d0Arr)));
        t0.E();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(W0 w0) {
        String string;
        if (w0.e1().a()) {
            return;
        }
        C0633d0[] c0633d0Arr = w0.s0().c;
        C0706m3.g gVarE1 = w0.e1();
        AbstractC3649o0 abstractC3649o0Z = this.a.z();
        Predicate predicate = this.d;
        if (gVarE1.a()) {
            string = null;
        } else {
            A3 a3 = new A3(abstractC3649o0Z, predicate);
            a3.a(gVarE1);
            string = a3.toString();
        }
        w0.a(C0658g0.a((C0633d0[]) com.android.tools.r8.utils.B.a(C0633d0.a(string, this.b.a), c0633d0Arr)));
        w0.E();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0729q2 c0729q2) {
        c0729q2.g(new Consumer() { // from class: com.android.tools.r8.dex.k$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C0548k.a((o5) obj);
            }
        }, new C0572k$$ExternalSyntheticLambda8());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(o5 o5Var) {
        o5Var.e().a1().u0().c(o5Var);
    }

    protected final void a(final C0645e5 c0645e5, Set set, final AbstractC0681j0 abstractC0681j0) {
        if (!this.b.n1.R) {
            if (!c0645e5.o()) {
                return;
            }
            C0759u2 c0759u2 = abstractC0681j0.g;
            if (c0759u2 != null && c0759u2.a(c0645e5.f()) < 0) {
                return;
            }
        }
        Iterator it = set.iterator();
        while (it.hasNext()) {
            ((C0729q2) it.next()).g(new Consumer() { // from class: com.android.tools.r8.dex.k$$ExternalSyntheticLambda14
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a(c0645e5, abstractC0681j0, (o5) obj);
                }
            }, new C0572k$$ExternalSyntheticLambda8());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C0645e5 c0645e5, AbstractC0681j0 abstractC0681j0, o5 o5Var) {
        o5Var.a(o5Var.e().a1().u0().a(o5Var, c0645e5, abstractC0681j0.e, this.b.n1.R).t(), this.a);
    }

    private E a(C0645e5 c0645e5, ByteBufferProvider byteBufferProvider, r0 r0Var, y3 y3Var) {
        final K k2 = new K(this.a, new C0586x(byteBufferProvider), c0645e5, this.c, r0Var, true);
        y3Var.a("collect", new C0566k$$ExternalSyntheticLambda24(k2));
        return (E) y3Var.a(CompilerOptions.GENERATE, new InterfaceC3949p3() { // from class: com.android.tools.r8.dex.k$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.utils.InterfaceC3949p3
            public final Object get() {
                return k2.b();
            }
        });
    }

    private static String a(C0767v2 c0767v2, AbstractC3649o0 abstractC3649o0) {
        return C3970u0.b(abstractC3649o0.c(c0767v2).toString()).replace('.', '/') + ".class";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(StringBuilder sb, C0702m c0702m, C0767v2 c0767v2) {
        sb.append(a(c0767v2, c0702m.z())).append('\n');
    }

    protected final void a(r0 r0Var) {
        this.b.n1.getClass();
        boolean z = k;
        if (!z && !r0Var.b.i.isEmpty()) {
            throw new AssertionError();
        }
        if (!z && !r0Var.b.j.isEmpty()) {
            throw new AssertionError();
        }
        if (!z && !r0Var.b.k.isEmpty()) {
            throw new AssertionError();
        }
        if (!z && !r0Var.b.l.isEmpty()) {
            throw new AssertionError();
        }
        if (!z && !r0Var.b.m.isEmpty()) {
            throw new AssertionError();
        }
        if (!z && !r0Var.b.n.isEmpty()) {
            throw new AssertionError();
        }
        if (!z && !r0Var.b.o.isEmpty()) {
            throw new AssertionError();
        }
    }
}
