package com.android.tools.r8.graph;

import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.internal.CM;
import com.android.tools.r8.shaking.C3704d1;
import com.android.tools.r8.shaking.C3762m1;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/* JADX INFO: renamed from: com.android.tools.r8.graph.w3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C0791w3 {
    static final /* synthetic */ boolean d = true;
    private final C0702m a;
    private final int b;
    private final C0738r3 c;

    /* JADX INFO: renamed from: com.android.tools.r8.graph.w3$a */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public enum a {
        INVALID_SUPER_TYPE,
        /* JADX INFO: Fake field, exist only in values array */
        INVALID_INTERFACE_TYPE,
        INVALID_INTERFACE_COUNT,
        INVALID_APPLICATION_COUNT,
        INVALID_TYPE_VARIABLE_UNDEFINED,
        VALID;

        static final /* synthetic */ boolean g = true;

        public final String a() {
            int i = AbstractC0751t3.a[ordinal()];
            if (i == 1) {
                return "The applied generic arguments have different count than the expected formals";
            }
            if (i == 2) {
                return "The generic signature has a different number of interfaces than the class";
            }
            if (i == 3) {
                return "The generic super type is not the same as the class super type";
            }
            if (i == 4) {
                return "A type variable is not in scope";
            }
            if (g || this == VALID) {
                throw new C3405y10("Should not throw an error for a valid signature");
            }
            throw new AssertionError();
        }

        public final boolean b() {
            return this != VALID;
        }
    }

    private C0791w3(C0702m c0702m, C0738r3 c0738r3, int i) {
        this.a = c0702m;
        this.c = c0738r3;
        this.b = i;
        c0702m.Y();
    }

    public static C0791w3 a(C0702m<?> c0702m, C0738r3 c0738r3) {
        return new C0791w3(c0702m, c0738r3, 2);
    }

    public static C0791w3 b(C0702m<?> c0702m, C0738r3 c0738r3) {
        return new C0791w3(c0702m, c0738r3, 1);
    }

    public a a(Collection<C0729q2> collection) {
        this.a.Y().getClass();
        if (!this.a.Y().a1()) {
            return a.VALID;
        }
        a aVar = a.VALID;
        Iterator<C0729q2> it = collection.iterator();
        while (it.hasNext()) {
            a aVarA = a(it.next());
            if (!aVar.b()) {
                aVar = aVarA;
            }
        }
        return aVar;
    }

    public a a(final C0729q2 c0729q2) {
        a aVarA;
        this.a.Y().getClass();
        if (!this.a.Y().a1()) {
            return a.VALID;
        }
        final C0738r3.a aVarA2 = this.c.a(this.a, c0729q2.d, CM.a());
        C0702m c0702m = this.a;
        int i = this.b;
        final C0760u3 c0760u3 = new C0760u3(c0702m, i, c0729q2);
        C0706m3.b bVar = c0729q2.r;
        if (!bVar.a()) {
            Iterator<C0706m3.f> it = bVar.a.iterator();
            do {
                if (it.hasNext()) {
                    C0706m3.f next = it.next();
                    aVarA = c0760u3.a(next.b, aVarA2);
                    if (!aVarA.b()) {
                        Iterator<C0706m3.e> it2 = next.c.iterator();
                        do {
                            if (it2.hasNext()) {
                                aVarA = c0760u3.a(it2.next(), aVarA2);
                            } else {
                                aVarA = a.VALID;
                                break;
                            }
                        } while (aVarA == a.VALID);
                    }
                } else {
                    aVarA = a.VALID;
                    break;
                }
            } while (!aVarA.b());
            if (!aVarA.b()) {
                if ((c0729q2.f != c0702m.a().Z1 || !bVar.b.a()) && c0729q2.f != bVar.b.b) {
                    if (!C0760u3.d && !AbstractC0768v3.a(i)) {
                        throw new AssertionError("Super type inconsistency in generic signature");
                    }
                    aVarA = a.INVALID_SUPER_TYPE;
                } else {
                    aVarA = c0760u3.a(bVar.b.r(), c0729q2.f, aVarA2);
                    if (!aVarA.b()) {
                        List<C0706m3.c> list = bVar.c;
                        if (c0729q2.g.size() != list.size()) {
                            if (!C0760u3.d && !AbstractC0768v3.a(i)) {
                                throw new AssertionError();
                            }
                            aVarA = a.INVALID_INTERFACE_COUNT;
                        } else {
                            C0767v2[] c0767v2Arr = c0729q2.g.a;
                            int i2 = 0;
                            while (true) {
                                if (i2 < c0767v2Arr.length) {
                                    aVarA = c0760u3.a(list.get(i2).r(), c0767v2Arr[i2], aVarA2);
                                    if (aVarA.b()) {
                                        break;
                                    }
                                    i2++;
                                } else {
                                    aVarA = a.VALID;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        } else {
            aVarA = a.VALID;
        }
        if (aVarA.b() && AbstractC0768v3.a(this.b)) {
            com.android.tools.r8.shaking.Y0 y0A = this.a.v().a(c0729q2);
            if (this.a.f().i() && !y0A.d((com.android.tools.r8.shaking.K0) this.a.Y())) {
                if (!d && y0A.d(this.a.Y())) {
                    throw new AssertionError();
                }
                this.a.Y().e.info(F3.a(c0729q2.a1().toString(), c0729q2.o1(), c0729q2.c, aVarA));
            }
            c0729q2.r = C0706m3.b.f();
        }
        for (final W0 w0 : c0729q2.M1()) {
            Objects.requireNonNull(w0);
            a aVarA3 = a(new Supplier() { // from class: com.android.tools.r8.graph.w3$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final Object get() {
                    return w0.e1();
                }
            }, new Function() { // from class: com.android.tools.r8.graph.w3$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return C0791w3.a(c0760u3, aVarA2, (C0706m3.g) obj);
                }
            }, new Consumer() { // from class: com.android.tools.r8.graph.w3$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a(w0, c0729q2, (C0791w3.a) obj);
                }
            });
            if (!aVarA.b()) {
                aVarA = aVarA3;
            }
        }
        for (final T0 t0 : c0729q2.W0()) {
            Objects.requireNonNull(t0);
            a aVarA4 = a(new Supplier() { // from class: com.android.tools.r8.graph.w3$$ExternalSyntheticLambda3
                @Override // java.util.function.Supplier
                public final Object get() {
                    return t0.T0();
                }
            }, new Function() { // from class: com.android.tools.r8.graph.w3$$ExternalSyntheticLambda4
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return C0791w3.a(c0760u3, aVarA2, (C0706m3.e) obj);
                }
            }, new Consumer() { // from class: com.android.tools.r8.graph.w3$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a(t0, c0729q2, (C0791w3.a) obj);
                }
            });
            if (!aVarA.b()) {
                aVarA = aVarA4;
            }
        }
        return aVarA;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ a a(C0760u3 c0760u3, C0738r3.a aVar, C0706m3.g gVar) {
        return c0760u3.a(gVar, aVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(W0 w0, C0729q2 c0729q2, a aVar) {
        C3762m1 c3762m1A = this.a.v().a(c0729q2, w0);
        if (this.a.f().i() && !c3762m1A.d((com.android.tools.r8.shaking.K0) this.a.Y())) {
            if (!d && c3762m1A.d(this.a.Y())) {
                throw new AssertionError();
            }
            this.a.Y().e.info(F3.c(w0.e1().toString(), w0.r0(), c0729q2.c, aVar));
        }
        w0.E();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(T0 t0, C0729q2 c0729q2, a aVar) {
        C3704d1 c3704d1A = this.a.v().a(t0, c0729q2);
        if (this.a.f().i() && !c3704d1A.d((com.android.tools.r8.shaking.K0) this.a.Y())) {
            if (!d && c3704d1A.d(this.a.Y())) {
                throw new AssertionError();
            }
            this.a.Y().e.info(F3.b(t0.T0().toString(), t0.r0(), c0729q2.c, aVar));
        }
        t0.E();
    }

    private a a(Supplier supplier, Function function, Consumer consumer) {
        C0706m3.d dVar = (C0706m3.d) supplier.get();
        if (!dVar.a()) {
            a aVar = (a) function.apply(dVar);
            if (!d) {
                aVar.getClass();
                if (aVar != a.VALID && !AbstractC0768v3.a(this.b)) {
                    throw new AssertionError();
                }
            }
            if (aVar.b() && AbstractC0768v3.a(this.b)) {
                consumer.accept(aVar);
            }
            return aVar;
        }
        return a.VALID;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static a a(C0760u3 c0760u3, C0738r3.a aVar, C0706m3.e eVar) {
        c0760u3.b.r.getClass();
        return c0760u3.a(eVar, aVar);
    }
}
