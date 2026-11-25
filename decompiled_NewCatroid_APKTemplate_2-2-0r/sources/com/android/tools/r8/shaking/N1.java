package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.InterfaceC0694k5;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.graph.u5;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.OU;
import com.android.tools.r8.ir.optimize.v0$$ExternalSyntheticLambda5;
import com.android.tools.r8.synthesis.C3860s;
import com.android.tools.r8.utils.C3912j0;
import java.util.Collections;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class N1 {
    private static final N1 f = new N1(Collections.emptySet(), Collections.emptySet(), Collections.emptySet(), Collections.emptySet(), false);
    static final /* synthetic */ boolean g = true;
    private final Set a;
    private final Set b;
    private Set c;
    private final Set d;
    private boolean e;

    private N1(Set set, Set set2, Set set3, Set set4, boolean z) {
        this.a = set;
        this.b = set2;
        this.c = set3;
        this.d = set4;
        this.e = z;
        if (g) {
            return;
        }
        Stream stream = set4.stream();
        Objects.requireNonNull(set2);
        if (!stream.noneMatch(new v0$$ExternalSyntheticLambda5(set2))) {
            throw new AssertionError();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean b(com.android.tools.r8.synthesis.D d, C0767v2 c0767v2) {
        return !a(c0767v2, this.b, d);
    }

    public static N1 f() {
        return f;
    }

    public final boolean a(o5 o5Var, AbstractC0744s2 abstractC0744s2, com.android.tools.r8.synthesis.D d) {
        M1 m1A = a(o5Var.getReference(), d);
        if (m1A == M1.d || m1A == M1.c) {
            return true;
        }
        if (m1A == M1.a) {
            return false;
        }
        if (g || m1A == M1.b) {
            return a(abstractC0744s2, d) == M1.b;
        }
        throw new AssertionError();
    }

    public final boolean c(InterfaceC0694k5 interfaceC0694k5, com.android.tools.r8.synthesis.D d) {
        return a(interfaceC0694k5.C(), this.b, d);
    }

    public final boolean d() {
        if (g || !this.b.isEmpty() || this.d.isEmpty()) {
            return this.b.isEmpty() && this.a.isEmpty();
        }
        throw new AssertionError();
    }

    public final boolean e() {
        return this.e;
    }

    public final int g() {
        return this.d.size() + this.b.size() + this.a.size();
    }

    public final M1 b(InterfaceC0694k5 interfaceC0694k5, com.android.tools.r8.synthesis.D d) {
        if (!g && a(interfaceC0694k5.C(), this.a, d)) {
            throw new AssertionError();
        }
        M1 m1A = a(interfaceC0694k5.getReference(), d);
        if (m1A == M1.a) {
            return null;
        }
        return m1A;
    }

    public final void c() {
        this.e = true;
        this.c = OU.c();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(Set set, L1 l1, C0767v2 c0767v2) {
        Objects.requireNonNull(l1);
        N1$$ExternalSyntheticLambda4 n1$$ExternalSyntheticLambda4 = new N1$$ExternalSyntheticLambda4(l1);
        if (set.contains(c0767v2)) {
            return;
        }
        n1$$ExternalSyntheticLambda4.accept(c0767v2);
    }

    public final L1 b() {
        L1 l1 = new L1(this.e);
        l1.a.addAll(this.a);
        l1.b.addAll(this.b);
        l1.c.addAll(this.c);
        l1.d.addAll(this.d);
        return l1;
    }

    public final boolean a(C0767v2 c0767v2) {
        return this.a.contains(c0767v2);
    }

    public final boolean a(InterfaceC0694k5 interfaceC0694k5, com.android.tools.r8.synthesis.D d) {
        return !a(interfaceC0694k5.C(), this.a, d);
    }

    private M1 a(AbstractC0744s2 abstractC0744s2, com.android.tools.r8.synthesis.D d) {
        if (a(abstractC0744s2, this.a, d)) {
            return M1.a;
        }
        if (a(abstractC0744s2, this.b, d)) {
            return M1.b;
        }
        if (this.d.contains(abstractC0744s2.C())) {
            return M1.c;
        }
        return M1.d;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(com.android.tools.r8.synthesis.D d, C0767v2 c0767v2) {
        return !a(c0767v2, this.a, d);
    }

    public final boolean a(C0684j2 c0684j2) {
        if (g || !this.e) {
            return this.c.contains(c0684j2);
        }
        throw new AssertionError("Traced method roots are cleared after mergers has run");
    }

    private static boolean a(AbstractC0744s2 abstractC0744s2, Set set, com.android.tools.r8.synthesis.D d) {
        if (set.isEmpty()) {
            return false;
        }
        C0767v2 c0767v2C = abstractC0744s2.C();
        Iterator<E> it = d.b(c0767v2C).iterator();
        while (it.hasNext()) {
            if (set.contains((C0767v2) it.next())) {
                return true;
            }
        }
        return set.contains(c0767v2C);
    }

    public final boolean a(InterfaceC0694k5 interfaceC0694k5, C0729q2 c0729q2, com.android.tools.r8.synthesis.D d) {
        C0767v2 c0767v2C = interfaceC0694k5.C();
        C0767v2 type = c0729q2.getType();
        M1 m1A = a(c0767v2C, d);
        return m1A == a(type, d) && m1A != M1.a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(L1 l1, AbstractC1476Wj abstractC1476Wj, C0684j2 c0684j2) {
        l1.c.add(abstractC1476Wj.c((AbstractC1476Wj) null, c0684j2));
    }

    public final boolean a(C0702m c0702m, InterfaceC0694k5 interfaceC0694k5, o5 o5Var, final com.android.tools.r8.synthesis.D d) {
        M1 m1A;
        if (interfaceC0694k5.C() == o5Var.t() || (m1A = a(interfaceC0694k5.getReference(), d)) == M1.d || m1A == M1.c) {
            return false;
        }
        if (m1A == M1.a) {
            return K1.a(c0702m, o5Var, new Predicate() { // from class: com.android.tools.r8.shaking.N1$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return this.f$0.a(d, (C0767v2) obj);
                }
            }) != null;
        }
        if (g || m1A == M1.b) {
            return K1.a(c0702m, o5Var, new Predicate() { // from class: com.android.tools.r8.shaking.N1$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return this.f$0.b(d, (C0767v2) obj);
                }
            }) != null;
        }
        throw new AssertionError();
    }

    public final void a(Consumer consumer) {
        Set setC = OU.c();
        this.a.forEach(C3912j0.a(consumer, setC));
        this.b.forEach(C3912j0.a(consumer, setC));
        this.d.forEach(C3912j0.a(consumer, setC));
    }

    public final N1 a(u5 u5Var) {
        if (u5Var.k()) {
            return this;
        }
        final Set setE = u5Var.e();
        final Set setC = OU.c();
        this.a.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.N1$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(setE, setC, (C0767v2) obj);
            }
        });
        final L1 l1A = a();
        this.b.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.N1$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(setE, l1A, (C0767v2) obj);
            }
        });
        this.c.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.N1$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(setE, l1A, (C0684j2) obj);
            }
        });
        this.d.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.N1$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.b(setE, l1A, (C0767v2) obj);
            }
        });
        if (L1.f || l1A.a.isEmpty()) {
            return new N1(setC, l1A.b, l1A.c, l1A.d, l1A.e);
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Set set, Set set2, C0767v2 c0767v2) {
        Objects.requireNonNull(set2);
        N1$$ExternalSyntheticLambda3 n1$$ExternalSyntheticLambda3 = new N1$$ExternalSyntheticLambda3(set2);
        if (set.contains(c0767v2)) {
            return;
        }
        n1$$ExternalSyntheticLambda3.accept(c0767v2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Set set, L1 l1, C0767v2 c0767v2) {
        Objects.requireNonNull(l1);
        N1$$ExternalSyntheticLambda10 n1$$ExternalSyntheticLambda10 = new N1$$ExternalSyntheticLambda10(l1);
        if (set.contains(c0767v2)) {
            return;
        }
        n1$$ExternalSyntheticLambda10.accept(c0767v2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Set set, final L1 l1, final C0684j2 c0684j2) {
        C0767v2 c0767v2B0 = c0684j2.B0();
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.shaking.N1$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                N1.a(l1, c0684j2, (C0767v2) obj);
            }
        };
        if (set.contains(c0767v2B0)) {
            return;
        }
        consumer.accept(c0767v2B0);
    }

    public final N1 a(final com.android.tools.r8.synthesis.D d, final AbstractC1476Wj abstractC1476Wj) {
        final Set setC = OU.c();
        this.a.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.N1$$ExternalSyntheticLambda11
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                N1.a(abstractC1476Wj, setC, (C0767v2) obj);
            }
        });
        final L1 l1A = a();
        this.b.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.N1$$ExternalSyntheticLambda12
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                N1.a(abstractC1476Wj, l1A, (C0767v2) obj);
            }
        });
        this.c.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.N1$$ExternalSyntheticLambda13
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                N1.a(l1A, abstractC1476Wj, (C0684j2) obj);
            }
        });
        this.d.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.N1$$ExternalSyntheticLambda14
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                N1.a(abstractC1476Wj, l1A, d, (C0767v2) obj);
            }
        });
        if (L1.f || l1A.a.isEmpty()) {
            return new N1(setC, l1A.b, l1A.c, l1A.d, l1A.e);
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(AbstractC1476Wj abstractC1476Wj, Set set, C0767v2 c0767v2) {
        Objects.requireNonNull(set);
        com.android.tools.r8.utils.R1.a(abstractC1476Wj, c0767v2, new N1$$ExternalSyntheticLambda3(set));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(AbstractC1476Wj abstractC1476Wj, L1 l1, C0767v2 c0767v2) {
        Objects.requireNonNull(l1);
        com.android.tools.r8.utils.R1.a(abstractC1476Wj, c0767v2, new N1$$ExternalSyntheticLambda10(l1));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(AbstractC1476Wj abstractC1476Wj, final L1 l1, com.android.tools.r8.synthesis.D d, C0767v2 c0767v2) {
        abstractC1476Wj.getClass();
        if (abstractC1476Wj instanceof C3860s) {
            Objects.requireNonNull(l1);
            com.android.tools.r8.utils.R1.a(abstractC1476Wj, c0767v2, new Consumer() { // from class: com.android.tools.r8.shaking.N1$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    l1.b((C0767v2) obj);
                }
            });
        } else if (d.e()) {
            com.android.tools.r8.utils.R1.a(abstractC1476Wj, c0767v2, l1.a(d));
        } else {
            Objects.requireNonNull(l1);
            com.android.tools.r8.utils.R1.a(abstractC1476Wj, c0767v2, new N1$$ExternalSyntheticLambda4(l1));
        }
    }

    public final L1 a() {
        return new L1(this.e);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(L1 l1, C0684j2 c0684j2, C0767v2 c0767v2) {
        l1.c.add(c0684j2);
    }
}
