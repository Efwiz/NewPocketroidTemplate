package com.android.tools.r8.dex;

import com.android.tools.r8.ByteDataView;
import com.android.tools.r8.C3601m;
import com.android.tools.r8.DexFilePerClassFileConsumer;
import com.android.tools.r8.DexIndexedConsumer;
import com.android.tools.r8.graph.C0645e5;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.internal.C1525Yd;
import com.android.tools.r8.internal.OU;
import com.android.tools.r8.utils.A1;
import com.android.tools.r8.utils.C3879c3;
import com.android.tools.r8.utils.InterfaceC3910i3;
import com.android.tools.r8.utils.InterfaceC3949p3;
import com.android.tools.r8.utils.V1;
import com.android.tools.r8.utils.w3;
import com.android.tools.r8.utils.y3;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;

/* JADX INFO: renamed from: com.android.tools.r8.dex.l, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C0574l extends C0548k {
    static final /* synthetic */ boolean l = true;

    protected C0574l(C0702m c0702m, U u, C3601m c3601m) {
        super(c0702m, u, c3601m);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(r0 r0Var) {
        boolean z = l;
        if (!z && r0Var.k() != null) {
            throw new AssertionError();
        }
        if (!z && r0Var.f() != null) {
            throw new AssertionError();
        }
    }

    @Override // com.android.tools.r8.dex.C0548k
    protected final ArrayList a(ExecutorService executorService, List list, final ArrayList arrayList) {
        final r0 r0Var = (r0) list.get(list.size() - 1);
        List listSubList = list.subList(0, list.size() - 1);
        Iterator it = listSubList.iterator();
        while (it.hasNext()) {
            r0Var.b.a(((r0) it.next()).b.a());
        }
        ArrayList arrayList2 = new ArrayList(list.size());
        arrayList2.add(a(arrayList, r0Var));
        arrayList2.addAll(C3879c3.a(listSubList, new InterfaceC3910i3() { // from class: com.android.tools.r8.dex.l$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.utils.InterfaceC3910i3
            public final Object apply(Object obj) {
                return this.f$0.a(arrayList, r0Var, (r0) obj);
            }
        }, executorService));
        return arrayList2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ y3 a(List list, r0 r0Var, r0 r0Var2) {
        return a(r0Var2, list, r0Var.j());
    }

    private y3 a(r0 r0Var, List list, C0645e5 c0645e5) {
        y3 y3VarA = y3.a(this.b, "VirtualFile " + r0Var.g());
        if (!r0Var.m()) {
            y3VarA.a("Compute object offset mapping");
            r0Var.a(this.a, list.size(), y3VarA, c0645e5);
            y3VarA.c();
            y3VarA.a("Rewrite jumbo strings");
            a(r0Var.j(), r0Var.b(), this.a.f().b());
            y3VarA.c();
        }
        C1525Yd.a(this.a, r0Var);
        y3VarA.c();
        return y3VarA;
    }

    @Override // com.android.tools.r8.dex.C0548k
    protected final void a(ExecutorService executorService, List list, ArrayList arrayList, y3 y3Var) {
        w3 w3VarA = y3Var.a(C3879c3.a(executorService), "Write files");
        boolean z = l;
        if (!z && this.j != null) {
            throw new AssertionError();
        }
        if (!z && this.i != null) {
            throw new AssertionError();
        }
        list.forEach(new Consumer() { // from class: com.android.tools.r8.dex.l$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C0574l.b((r0) obj);
            }
        });
        DexIndexedConsumer dexIndexedConsumerT0 = this.b.t0();
        C0586x c0586x = new C0586x(this.b.t0());
        boolean z2 = false;
        ArrayList arrayList2 = new ArrayList();
        ArrayList<F> arrayList3 = new ArrayList();
        byte[] bArr = new byte[0];
        int iA = 0;
        int i = 0;
        while (i < list.size()) {
            r0 r0Var = (r0) list.get(i);
            y3 y3VarA = y3.a(this.b, "VirtualFile " + r0Var.g());
            boolean z3 = l;
            if (!z3 && arrayList.size() != 0) {
                throw new AssertionError();
            }
            if (!r0Var.m()) {
                boolean z4 = i == list.size() - 1 ? true : z2;
                if (!z3 && r0Var.m()) {
                    throw new AssertionError();
                }
                if (!z3 && !com.android.tools.r8.utils.J.a(4, iA)) {
                    throw new AssertionError();
                }
                a(r0Var);
                y3VarA.a("Reindex for lazy strings");
                C0645e5 c0645e5J = r0Var.j();
                c0645e5J.a(arrayList);
                y3VarA.c();
                y3VarA.a("Write bytes");
                int i2 = iA;
                F fA = a(c0645e5J, c0586x, r0Var, y3VarA, iA, z4);
                y3VarA.c();
                if (A1.b()) {
                    byte[] bArrA = c0586x.a();
                    for (int i3 = 0; i3 < i2; i3++) {
                        if (!l && bArr[i3] != bArrA[i3]) {
                            throw new AssertionError();
                        }
                    }
                    bArr = new byte[fA.b().a()];
                    for (int i4 = 0; i4 < fA.b().a(); i4++) {
                        bArr[i4] = bArrA[i4];
                    }
                }
                iA = fA.b().a();
                if (!l && !com.android.tools.r8.utils.J.a(4, iA)) {
                    throw new AssertionError();
                }
                arrayList3.add(fA);
                y3VarA.c();
                arrayList2.add(y3VarA);
            }
            i++;
            z2 = false;
        }
        w3VarA.a(arrayList2);
        w3VarA.a();
        if (arrayList3.isEmpty()) {
            return;
        }
        F f = (F) V1.b(arrayList3);
        int size = f.a().c().j().size();
        int i5 = f.b().b;
        for (F f2 : arrayList3) {
            if (f2 != f) {
                c0586x.e(f2.b().a + 56);
                c0586x.f(size);
                c0586x.f(i5);
                c0586x.e(f2.b().b());
                ArrayList arrayListA = f2.b().a(f2.a(), f2.b().a, size, i5, f.b().c());
                int iB = c0586x.b();
                Iterator it = arrayListA.iterator();
                int iA2 = 0;
                while (it.hasNext()) {
                    iA2 += ((H) it.next()).a(c0586x);
                }
                if (!l && iB != iA2) {
                    throw new AssertionError();
                }
                K kA = f2.a();
                G gB = f2.b();
                kA.getClass();
                K.b(gB, c0586x);
                K kA2 = f2.a();
                G gB2 = f2.b();
                kA2.getClass();
                K.a(gB2, c0586x);
            } else {
                c0586x.e(f2.b().b());
                ArrayList arrayListA2 = f2.b().a(f2.a(), f2.b().a, size, i5, f.b().c());
                int iB2 = c0586x.b();
                Iterator it2 = arrayListA2.iterator();
                int iA3 = 0;
                while (it2.hasNext()) {
                    iA3 += ((H) it2.next()).a(c0586x);
                }
                if (!l && iB2 != iA3) {
                    throw new AssertionError();
                }
                K kA3 = f2.a();
                G gB3 = f2.b();
                kA3.getClass();
                K.b(gB3, c0586x);
                K kA4 = f2.a();
                G gB4 = f2.b();
                kA4.getClass();
                K.a(gB4, c0586x);
            }
        }
        C0581s c0581sD = c0586x.d();
        ByteDataView byteDataView = new ByteDataView(c0581sD.a(), c0581sD.b(), ((F) arrayList3.get(arrayList3.size() - 1)).b().a());
        if (dexIndexedConsumerT0 instanceof DexFilePerClassFileConsumer) {
            if (!l) {
                throw new AssertionError();
            }
        } else {
            dexIndexedConsumerT0.accept(0, byteDataView, OU.c(), this.b.e);
        }
    }

    protected final F a(C0645e5 c0645e5, C0586x c0586x, r0 r0Var, y3 y3Var, final int i, boolean z) {
        final K k = new K(this.a, c0586x, c0645e5, a(), r0Var, z);
        y3Var.a("collect", new C0566k$$ExternalSyntheticLambda24(k));
        return (F) y3Var.a(CompilerOptions.GENERATE, new InterfaceC3949p3() { // from class: com.android.tools.r8.dex.l$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.utils.InterfaceC3949p3
            public final Object get() {
                return C0574l.a(k, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ F a(K k, int i) {
        return k.a(i, 2);
    }
}
