package com.android.tools.r8.dex;

import com.android.tools.r8.ClassFileResourceProvider;
import com.android.tools.r8.ProgramResource;
import com.android.tools.r8.graph.AbstractC0720p;
import com.android.tools.r8.graph.C0612b4;
import com.android.tools.r8.graph.C0633d0;
import com.android.tools.r8.graph.C0689k0;
import com.android.tools.r8.graph.C0696l0;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.M3;
import com.android.tools.r8.graph.T3;
import com.android.tools.r8.internal.C2732or;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.utils.A1;
import com.android.tools.r8.utils.AbstractC3876c0;
import com.android.tools.r8.utils.C3887e0;
import com.android.tools.r8.utils.EnumC3891f;
import com.android.tools.r8.utils.H0;
import com.android.tools.r8.utils.S1;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.dex.a, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C0406a {
    static final /* synthetic */ boolean k = true;
    private final ExecutorService a;
    private final List b;
    private final ConcurrentLinkedQueue c = new ConcurrentLinkedQueue();
    private final ConcurrentLinkedQueue d = new ConcurrentLinkedQueue();
    private final ConcurrentLinkedQueue e = new ConcurrentLinkedQueue();
    private final C0689k0 f;
    private final M3 g;
    private boolean h;
    private boolean i;
    final /* synthetic */ C0408c j;

    C0406a(C0408c c0408c, ExecutorService executorService, ArrayList arrayList) {
        this.j = c0408c;
        C0689k0 c0689k0A = C0696l0.a();
        this.f = c0689k0A;
        this.g = new M3(c0408c.a, c0689k0A);
        this.h = false;
        this.i = false;
        this.a = executorService;
        this.b = arrayList;
    }

    private void b(ArrayList arrayList, final ConcurrentLinkedQueue concurrentLinkedQueue) {
        if (arrayList.isEmpty()) {
            return;
        }
        int i = 1;
        this.i = true;
        ArrayList<B> arrayList2 = new ArrayList(arrayList.size());
        EnumC3891f enumC3891fB0 = this.j.a.B0();
        Iterator it = arrayList.iterator();
        while (true) {
            int i2 = 0;
            if (!it.hasNext()) {
                this.j.a.c(enumC3891fB0);
                Iterator it2 = arrayList2.iterator();
                while (it2.hasNext()) {
                    ((B) it2.next()).l();
                }
                A1 a1 = this.j.a;
                if (a1.v1) {
                    return;
                }
                final AbstractC0720p abstractC0720pA = AbstractC0720p.a(a1);
                if (!this.j.a.n1.j) {
                    for (final B b : arrayList2) {
                        this.b.add(this.a.submit(new Runnable() { // from class: com.android.tools.r8.dex.a$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                C0406a.a(b, concurrentLinkedQueue, abstractC0720pA);
                            }
                        }));
                    }
                    return;
                }
                while (i2 < arrayList2.size()) {
                    B b2 = (B) arrayList2.get(i2);
                    Objects.requireNonNull(concurrentLinkedQueue);
                    b2.a(new a$$ExternalSyntheticLambda1(concurrentLinkedQueue), abstractC0720pA);
                    i2++;
                }
                return;
            }
            C c = new C((ProgramResource) it.next());
            C0408c c0408c = this.j;
            A1 a2 = c0408c.a;
            if (a2.o) {
                if (!a2.n1.j) {
                    H0 h0J = c.j();
                    if (!c0408c.a.n1.j && h0J.b() == A1.Q1) {
                        c0408c.a(h0J);
                        throw null;
                    }
                    if (c0408c.a.B0() == EnumC3891f.b()) {
                        enumC3891fB0 = enumC3891fB0.c(EnumC3891f.a(h0J));
                    } else if (!h0J.d(c0408c.a.B0())) {
                        c0408c.a(h0J);
                        throw null;
                    }
                } else if (!k && c.j() != H0.V41) {
                    throw new AssertionError();
                }
            }
            if (this.j.a.n1.j) {
                C2732or c2732or = new C2732or(16);
                c.k();
                int i3 = 0;
                while (i3 < c.b.d()) {
                    c2732or.a(i3);
                    C c2 = new C(Origin.unknown(), c.b.c().array(), i3);
                    boolean z = k;
                    if (!z && c2.j() != H0.V41) {
                        throw new AssertionError();
                    }
                    if (!z && c.a(i3 + 36) != 116) {
                        throw new AssertionError();
                    }
                    if (!z && c.a(i3 + 112) != i3) {
                        throw new AssertionError();
                    }
                    int iA = c.a(i3 + 104);
                    int iA2 = c.a(i3 + 108);
                    int iA3 = c.a(i3 + 32);
                    if (!z && iA2 != 0) {
                        throw new AssertionError();
                    }
                    if (!z && iA != 0) {
                        throw new AssertionError();
                    }
                    i3 += iA3;
                }
                if (!k && i3 != c.b.d()) {
                    throw new AssertionError();
                }
                B b3 = new B(c, com.android.tools.r8.graph.I.c, this.j.a, c2732or.i(c2732or.size() - i), null);
                while (i2 < c2732or.size() - i) {
                    arrayList2.add(new B(c, com.android.tools.r8.graph.I.c, this.j.a, c2732or.i(i2), b3));
                    i2++;
                    i = 1;
                }
                arrayList2.add(b3);
            } else {
                arrayList2.add(new B(c, com.android.tools.r8.graph.I.c, this.j.a));
            }
            i = 1;
        }
    }

    public final C0696l0 a() {
        return this.f.b(this.i).a(this.h).a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(B b, Queue queue, AbstractC0720p abstractC0720p) {
        Objects.requireNonNull(queue);
        b.a(new a$$ExternalSyntheticLambda1(queue), abstractC0720p);
    }

    private void a(ArrayList arrayList, final ConcurrentLinkedQueue concurrentLinkedQueue) {
        if (arrayList.isEmpty()) {
            return;
        }
        this.h = true;
        final T3 t3 = new T3(this.g, new Consumer() { // from class: com.android.tools.r8.dex.a$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(concurrentLinkedQueue, (C0729q2) obj);
            }
        }, com.android.tools.r8.graph.I.c);
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            final ProgramResource programResource = (ProgramResource) it.next();
            this.b.add(this.a.submit(new Callable() { // from class: com.android.tools.r8.dex.a$$ExternalSyntheticLambda3
                @Override // java.util.concurrent.Callable
                public final Object call() {
                    return C0406a.a(t3, programResource);
                }
            }));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Object a(T3 t3, ProgramResource programResource) {
        t3.getClass();
        t3.b(programResource.getOrigin(), programResource.getBytes());
        return null;
    }

    final void a(C0612b4.a aVar) {
        AbstractC3876c0 abstractC3876c0A;
        Iterator it = this.c.iterator();
        while (it.hasNext()) {
            aVar.a(((C0729q2) it.next()).a0());
        }
        com.android.tools.r8.graph.I i = com.android.tools.r8.graph.I.d;
        ConcurrentLinkedQueue concurrentLinkedQueue = this.d;
        List<ClassFileResourceProvider> listF = this.j.d.f();
        M3 m3 = this.g;
        ArrayList arrayList = new ArrayList();
        if (!concurrentLinkedQueue.isEmpty()) {
            arrayList.add(AbstractC3876c0.a(i, concurrentLinkedQueue));
        }
        Iterator<ClassFileResourceProvider> it2 = listF.iterator();
        while (it2.hasNext()) {
            arrayList.add(AbstractC3876c0.a(i, it2.next(), m3));
        }
        AbstractC3876c0 abstractC3876c0A2 = null;
        if (arrayList.isEmpty()) {
            abstractC3876c0A = null;
        } else {
            abstractC3876c0A = arrayList.size() == 1 ? (AbstractC3876c0) arrayList.get(0) : AbstractC3876c0.a(i, arrayList);
        }
        if (abstractC3876c0A != null) {
            aVar.a(new C3887e0(abstractC3876c0A));
        }
        com.android.tools.r8.graph.I i2 = com.android.tools.r8.graph.I.e;
        ConcurrentLinkedQueue concurrentLinkedQueue2 = this.e;
        List<ClassFileResourceProvider> listI = this.j.d.i();
        M3 m4 = this.g;
        ArrayList arrayList2 = new ArrayList();
        if (!concurrentLinkedQueue2.isEmpty()) {
            arrayList2.add(AbstractC3876c0.a(i2, concurrentLinkedQueue2));
        }
        Iterator<ClassFileResourceProvider> it3 = listI.iterator();
        while (it3.hasNext()) {
            arrayList2.add(AbstractC3876c0.a(i2, it3.next(), m4));
        }
        if (!arrayList2.isEmpty()) {
            abstractC3876c0A2 = arrayList2.size() == 1 ? (AbstractC3876c0) arrayList2.get(0) : AbstractC3876c0.a(i2, arrayList2);
        }
        if (abstractC3876c0A2 != null) {
            aVar.a(new S1(abstractC3876c0A2));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Queue queue, C0729q2 c0729q2) {
        boolean zContains;
        if (c0729q2.e.T()) {
            if (this.j.a.x0) {
                C0633d0 c0633d0A = c0729q2.s0().a(this.j.b.j3);
                if (c0633d0A == null) {
                    zContains = C0633d0.a(this.g.a, c0729q2.getType());
                } else {
                    zContains = c0633d0A.b.toString().contains("RUNTIME");
                }
            } else {
                zContains = true;
            }
            if (!zContains) {
                return;
            }
        }
        queue.add(c0729q2);
    }

    final void b() {
        ArrayList<ProgramResource> arrayListD = this.j.d.d();
        ArrayList arrayList = new ArrayList(arrayListD.size());
        ArrayList arrayList2 = new ArrayList(arrayListD.size());
        for (ProgramResource programResource : arrayListD) {
            if (programResource.getKind() == ProgramResource.Kind.DEX) {
                arrayList.add(programResource);
            } else {
                if (!k && programResource.getKind() != ProgramResource.Kind.CF) {
                    throw new AssertionError();
                }
                arrayList2.add(programResource);
            }
        }
        b(arrayList, this.c);
        a(arrayList2, this.c);
    }
}
