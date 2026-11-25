package com.android.tools.r8.graph;

import com.android.tools.r8.internal.JA;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class D4 {
    static final /* synthetic */ boolean e = true;
    private final Function a;
    private final C0722p1 b;
    private final boolean c;
    private final boolean d;

    private D4(Function function, C0722p1 c0722p1, boolean z, boolean z2) {
        this.a = function;
        this.b = c0722p1;
        this.c = z;
        this.d = z2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ O a(Function function, C0767v2 c0767v2) {
        AbstractC0727q0 abstractC0727q0 = (AbstractC0727q0) function.apply(c0767v2);
        return abstractC0727q0 == null ? M.a() : abstractC0727q0;
    }

    public static D4 b(C0722p1 c0722p1, final Function function) {
        return new D4(new Function() { // from class: com.android.tools.r8.graph.D4$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return D4.a(function, (C0767v2) obj);
            }
        }, c0722p1, false, false);
    }

    final I4 c(AbstractC0727q0 abstractC0727q0, C0684j2 c0684j2) {
        A4 a4 = new A4(this.b, this.a);
        a(c0684j2.H0(), c0684j2.C0(), abstractC0727q0, a4, abstractC0727q0.f, Arrays.asList(abstractC0727q0.g.a));
        boolean z = A4.f;
        return a4.a(abstractC0727q0);
    }

    final AbstractC0748t0 b(AbstractC0727q0 abstractC0727q0, C0684j2 c0684j2) {
        A4 a4 = new A4(this.b, this.a);
        a(c0684j2.H0(), c0684j2.C0(), abstractC0727q0, a4, abstractC0727q0.f, Arrays.asList(abstractC0727q0.g.a));
        return a4.a((AbstractC0727q0) null).c();
    }

    public static D4 a(C0722p1 c0722p1, Function function) {
        return new D4(function, c0722p1, true, true);
    }

    private O a(C0767v2 c0767v2) {
        return (O) this.a.apply(c0767v2);
    }

    public final I4 a(final C0684j2 c0684j2) {
        C0767v2 c0767v2 = c0684j2.e;
        if (c0767v2.M0()) {
            C0737r2 c0737r2H0 = c0684j2.H0();
            C0759u2 c0759u2C0 = c0684j2.C0();
            if (!e && !c0767v2.M0()) {
                throw new AssertionError();
            }
            C0722p1 c0722p1 = this.b;
            if (c0759u2C0 == c0722p1.d0) {
                return E4.b;
            }
            return a(c0722p1.Z1, c0737r2H0, c0759u2C0);
        }
        final F4 f4P = I4.p();
        a(c0767v2).b(new Consumer() { // from class: com.android.tools.r8.graph.D4$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(f4P, c0684j2, (AbstractC0727q0) obj);
            }
        });
        return f4P.a(G4.c, Collections.singletonList(c0767v2));
    }

    public final I4 b(C0767v2 c0767v2, final C0737r2 c0737r2, final C0759u2 c0759u2) {
        if (c0767v2.M0()) {
            return L4.d;
        }
        final F4 f4P = I4.p();
        a(c0767v2).b(new Consumer() { // from class: com.android.tools.r8.graph.D4$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.b(f4P, c0737r2, c0759u2, (AbstractC0727q0) obj);
            }
        });
        return f4P.a(G4.c, Collections.singletonList(c0767v2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(F4 f4, C0684j2 c0684j2, AbstractC0727q0 abstractC0727q0) {
        I4 i4A;
        if (abstractC0727q0.isInterface()) {
            i4A = b(abstractC0727q0, c0684j2.H0(), c0684j2.C0());
        } else {
            i4A = a(abstractC0727q0, c0684j2.H0(), c0684j2.C0());
        }
        f4.a(i4A);
    }

    public final I4 a(C0767v2 c0767v2, final C0737r2 c0737r2, final C0759u2 c0759u2) {
        if (c0767v2.M0()) {
            if (!e && !c0767v2.M0()) {
                throw new AssertionError();
            }
            C0722p1 c0722p1 = this.b;
            if (c0759u2 == c0722p1.d0) {
                return E4.b;
            }
            return a(c0722p1.Z1, c0737r2, c0759u2);
        }
        final F4 f4P = I4.p();
        a(c0767v2).b(new Consumer() { // from class: com.android.tools.r8.graph.D4$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(f4P, c0737r2, c0759u2, (AbstractC0727q0) obj);
            }
        });
        return f4P.a(G4.c, Collections.singletonList(c0767v2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(F4 f4, C0737r2 c0737r2, C0759u2 c0759u2, AbstractC0727q0 abstractC0727q0) {
        if (!abstractC0727q0.isInterface()) {
            f4.a(L4.d);
        } else {
            f4.a(b(abstractC0727q0, c0737r2, c0759u2));
        }
    }

    public final I4 b(final AbstractC0727q0 abstractC0727q0, final C0737r2 c0737r2, final C0759u2 c0759u2) {
        if (!e && !abstractC0727q0.isInterface()) {
            throw new AssertionError();
        }
        W0 w0A = abstractC0727q0.k.a(c0737r2, c0759u2);
        if (w0A != null) {
            return I4.a(abstractC0727q0, abstractC0727q0, w0A);
        }
        final F4 f4P = I4.p();
        a(this.b.Z1).b(new Consumer() { // from class: com.android.tools.r8.graph.D4$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c0737r2, c0759u2, f4P, abstractC0727q0, (AbstractC0727q0) obj);
            }
        });
        return f4P.a(G4.c, Collections.emptySet());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(F4 f4, C0737r2 c0737r2, C0759u2 c0759u2, AbstractC0727q0 abstractC0727q0) {
        if (abstractC0727q0.isInterface()) {
            f4.a(L4.d);
        } else {
            f4.a(a(abstractC0727q0, c0737r2, c0759u2));
        }
    }

    public final I4 a(AbstractC0727q0 abstractC0727q0, C0737r2 c0737r2, C0759u2 c0759u2) {
        if (!e && abstractC0727q0.isInterface()) {
            throw new AssertionError();
        }
        I4 i4A = a(abstractC0727q0, c0737r2, c0759u2, abstractC0727q0);
        if (i4A != null) {
            return i4A;
        }
        A4 a4 = new A4(this.b, this.a);
        a(c0737r2, c0759u2, abstractC0727q0, a4, abstractC0727q0.f, Arrays.asList(abstractC0727q0.g.a));
        return a4.a(abstractC0727q0);
    }

    private I4 a(final AbstractC0727q0 abstractC0727q0, final C0737r2 c0737r2, final C0759u2 c0759u2, final AbstractC0727q0 abstractC0727q1) {
        W0 w0;
        C0722p1 c0722p1 = this.b;
        C0767v2 c0767v2 = abstractC0727q0.d;
        if (c0767v2 != c0722p1.C2 && c0767v2 != c0722p1.B2) {
            w0 = null;
            break loop0;
        }
        Iterator<W0> it = abstractC0727q0.P1().iterator();
        w0 = null;
        loop0: while (true) {
            W0 w1 = w0;
            while (true) {
                if (!it.hasNext()) {
                    break loop0;
                }
                W0 next = it.next();
                if (next.getReference().f == c0759u2) {
                    if (w1 != null) {
                        w0 = null;
                        break loop0;
                    }
                    if (AbstractC0727q0.a(next, c0722p1)) {
                        w0 = next;
                    } else {
                        w1 = next;
                    }
                }
            }
        }
        if (w0 != null) {
            return I4.a(abstractC0727q1, abstractC0727q0, w0);
        }
        W0 w0A = abstractC0727q0.k.a(c0737r2, c0759u2);
        if (w0A != null) {
            if (w0A.L1() && abstractC0727q0 != abstractC0727q1) {
                return new K4(abstractC0727q1, Collections.singletonList(w0A));
            }
            return I4.a(abstractC0727q1, abstractC0727q0, w0A);
        }
        if (abstractC0727q0.f == null) {
            return null;
        }
        final F4 f4P = I4.p();
        a(abstractC0727q0.f).b(new Consumer() { // from class: com.android.tools.r8.graph.D4$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(abstractC0727q0, c0737r2, c0759u2, abstractC0727q1, f4P, (AbstractC0727q0) obj);
            }
        });
        return f4P.a((I4.a) null, Collections.singletonList(abstractC0727q0.f));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(AbstractC0727q0 abstractC0727q0, C0737r2 c0737r2, C0759u2 c0759u2, AbstractC0727q0 abstractC0727q1, F4 f4, AbstractC0727q0 abstractC0727q2) {
        I4 i4A;
        if ((this.c && abstractC0727q0.f0() && !abstractC0727q2.f0()) || (i4A = a(abstractC0727q2, c0737r2, c0759u2, abstractC0727q1)) == null) {
            return;
        }
        f4.a(i4A);
    }

    final ArrayList a(AbstractC0727q0 abstractC0727q0, C0684j2 c0684j2) {
        A4 a4 = new A4(this.b, this.a);
        a(c0684j2.H0(), c0684j2.C0(), abstractC0727q0, a4, abstractC0727q0.f, Arrays.asList(abstractC0727q0.g.a));
        ArrayList arrayList = new ArrayList();
        for (Map.Entry entry : a4.a.entrySet()) {
            W0 w0 = (W0) entry.getValue();
            if (w0 != null && w0.s1()) {
                arrayList.add(entry);
            }
        }
        for (Map.Entry entry2 : a4.b.entrySet()) {
            W0 w1 = (W0) entry2.getValue();
            if (w1 != null && w1.s1()) {
                arrayList.add(entry2);
            }
        }
        return arrayList;
    }

    final I4 a(JA ja, C0684j2 c0684j2) {
        A4 a4 = new A4(this.b, this.a);
        a(c0684j2.H0(), c0684j2.C0(), (AbstractC0727q0) null, a4, this.b.Z1, ja.g);
        return a4.a((AbstractC0727q0) null);
    }

    private void a(C0737r2 c0737r2, C0759u2 c0759u2, final AbstractC0727q0 abstractC0727q0, A4 a4, C0767v2 c0767v2, List list) {
        final C4 c4;
        if (this.d) {
            c4 = new C4(this.a, this.c);
            list.forEach(new Consumer() { // from class: com.android.tools.r8.graph.D4$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    c4.a(abstractC0727q0, (C0767v2) obj);
                }
            });
            if (c0767v2 != null) {
                c4.a(abstractC0727q0, Collections.emptySet(), c0767v2);
            }
        } else {
            c4 = C0819z4.f;
        }
        a(c0737r2, c0759u2, abstractC0727q0, a4, c0767v2, list, c4);
    }

    private void a(final C0737r2 c0737r2, final C0759u2 c0759u2, final AbstractC0727q0 abstractC0727q0, final A4 a4, C0767v2 c0767v2, List list, final C4 c4) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            final C0767v2 c0767v3 = (C0767v2) it.next();
            final O oA = a(c0767v3);
            oA.b(new Consumer() { // from class: com.android.tools.r8.graph.D4$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a(abstractC0727q0, oA, a4, c0767v3, c0737r2, c0759u2, c4, (AbstractC0727q0) obj);
                }
            });
        }
        if (c0767v2 != null) {
            a(c0767v2).b(new Consumer() { // from class: com.android.tools.r8.graph.D4$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a(abstractC0727q0, c0737r2, c0759u2, a4, c4, (AbstractC0727q0) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(AbstractC0727q0 abstractC0727q0, O o, A4 a4, C0767v2 c0767v2, C0737r2 c0737r2, C0759u2 c0759u2, C4 c4, AbstractC0727q0 abstractC0727q1) {
        if (!this.c || abstractC0727q0 == null || !abstractC0727q0.f0() || abstractC0727q1.f0()) {
            if (o.r()) {
                a4.d.add(c0767v2);
            }
            if (!e && !abstractC0727q1.isInterface()) {
                throw new AssertionError();
            }
            W0 w0A = abstractC0727q1.k.a(c0737r2, c0759u2);
            if (w0A != null && !w0A.f.j() && !w0A.f.o()) {
                if (c4.a(abstractC0727q1)) {
                    if (a4.b.containsKey(abstractC0727q1)) {
                        return;
                    }
                    a4.b.put(abstractC0727q1, w0A);
                    return;
                }
                if (a4.a.containsKey(abstractC0727q1)) {
                    return;
                }
                a4.a.put(abstractC0727q1, w0A);
                boolean z = A4.f;
                if (!z && !abstractC0727q1.isInterface()) {
                    throw new AssertionError();
                }
                if (!z && abstractC0727q1.f != a4.e.Z1) {
                    throw new AssertionError();
                }
                for (C0767v2 c0767v3 : abstractC0727q1.g.a) {
                    a4.a(c0767v3);
                }
                return;
            }
            a(c0737r2, c0759u2, abstractC0727q1, a4, abstractC0727q1.f, Arrays.asList(abstractC0727q1.g.a), c4);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(AbstractC0727q0 abstractC0727q0, C0737r2 c0737r2, C0759u2 c0759u2, A4 a4, C4 c4, AbstractC0727q0 abstractC0727q1) {
        if (!this.c || abstractC0727q0 == null || !abstractC0727q0.f0() || abstractC0727q1.f0()) {
            a(c0737r2, c0759u2, abstractC0727q1, a4, abstractC0727q1.f, Arrays.asList(abstractC0727q1.g.a), c4);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0737r2 c0737r2, C0759u2 c0759u2, F4 f4, AbstractC0727q0 abstractC0727q0, AbstractC0727q0 abstractC0727q1) {
        W0 w0A = abstractC0727q1.k.a(c0737r2, c0759u2);
        if (w0A != null && w0A.f.m() && !w0A.f.W()) {
            f4.a(I4.a(abstractC0727q0, abstractC0727q1, w0A));
            return;
        }
        A4 a4 = new A4(this.b, this.a);
        a(c0737r2, c0759u2, abstractC0727q0, a4, abstractC0727q0.f, Arrays.asList(abstractC0727q0.g.a));
        f4.a(a4.a(abstractC0727q0));
    }
}
