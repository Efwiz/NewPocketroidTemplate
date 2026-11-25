package com.android.tools.r8.graph;

import com.android.tools.r8.ClassConflictResolver;
import com.android.tools.r8.dex.InterfaceC0407b;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.AbstractC2178hm;
import com.android.tools.r8.internal.C1946em;
import com.android.tools.r8.internal.OU;
import com.android.tools.r8.naming.C3605b;
import com.android.tools.r8.utils.C3887e0;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.ArrayList;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* JADX INFO: renamed from: com.android.tools.r8.graph.b4, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C0612b4 extends AbstractC0681j0 {
    static final /* synthetic */ boolean l = true;
    private final com.android.tools.r8.utils.A2 i;
    private final C3887e0 j;
    private final com.android.tools.r8.utils.S1 k;

    /* JADX INFO: renamed from: com.android.tools.r8.graph.b4$a */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a extends AbstractC0674i0 {
        private C3887e0 i;
        private com.android.tools.r8.utils.S1 j;

        a(com.android.tools.r8.utils.A1 a1, com.android.tools.r8.utils.y3 y3Var) {
            super(a1, y3Var);
            this.i = C3887e0.g();
            this.j = com.android.tools.r8.utils.S1.g();
        }

        public final void a(C3887e0 c3887e0) {
            this.i = c3887e0;
        }

        @Override // com.android.tools.r8.graph.AbstractC0674i0
        final AbstractC0674i0 d() {
            return this;
        }

        @Override // com.android.tools.r8.graph.AbstractC0674i0
        /* JADX INFO: renamed from: e, reason: merged with bridge method [inline-methods] */
        public C0612b4 b() {
            com.android.tools.r8.utils.A1 a1 = this.c;
            InterfaceC0407b interfaceC0407bA = a1.g;
            if (interfaceC0407bA == null) {
                interfaceC0407bA = com.android.tools.r8.utils.A2.a((ClassConflictResolver) null, a1.e);
            }
            return new C0612b4(this.d, this.f, com.android.tools.r8.utils.A2.a(c(), interfaceC0407bA), AbstractC1639am.a(this.b), this.i, this.j, this.c, this.g, this.e);
        }

        public final void a(com.android.tools.r8.utils.S1 s1) {
            this.j = s1;
        }

        @Override // com.android.tools.r8.graph.AbstractC0674i0
        public final void b(C0729q2 c0729q2) {
            a(c0729q2);
            this.i.a(c0729q2.d);
            this.j.a(c0729q2.d);
        }

        private a(C0612b4 c0612b4) {
            super(c0612b4);
            this.i = c0612b4.j;
            this.j = c0612b4.k;
        }
    }

    private C0612b4(C3605b c3605b, C0696l0 c0696l0, com.android.tools.r8.utils.A2 a2, AbstractC1639am abstractC1639am, C3887e0 c3887e0, com.android.tools.r8.utils.S1 s1, com.android.tools.r8.utils.A1 a1, C0759u2 c0759u2, com.android.tools.r8.utils.y3 y3Var) {
        super(c3605b, c0696l0, abstractC1639am, a1, c0759u2, y3Var);
        this.i = a2;
        this.j = c3887e0;
        this.k = s1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean g(C0767v2 c0767v2) {
        return true;
    }

    @Override // com.android.tools.r8.graph.AbstractC0681j0
    public final void a(Consumer consumer) {
        this.k.a().forEach(consumer);
    }

    @Override // com.android.tools.r8.graph.AbstractC0681j0
    public final void b(Consumer consumer) {
        this.i.d().forEach(consumer);
    }

    @Override // com.android.tools.r8.graph.AbstractC0681j0, com.android.tools.r8.graph.Q0
    public final AbstractC0727q0 c(C0767v2 c0767v2) {
        com.android.tools.r8.utils.S1 s1;
        C3887e0 c3887e0;
        C3887e0 c3887e1;
        if (!l && !c0767v2.O0()) {
            throw new AssertionError("Cannot lookup definition for type: " + c0767v2);
        }
        if (this.d.I0) {
            com.android.tools.r8.utils.S1 s2 = this.k;
            AbstractC0727q0 abstractC0727q0B = s2 != null ? s2.b(c0767v2) : null;
            if (abstractC0727q0B == null) {
                abstractC0727q0B = this.i.b(c0767v2);
            }
            return (abstractC0727q0B != null || (c3887e1 = this.j) == null) ? abstractC0727q0B : c3887e1.b(c0767v2);
        }
        AbstractC0727q0 abstractC0727q0B2 = this.i.b(c0767v2);
        if (abstractC0727q0B2 == null && (c3887e0 = this.j) != null) {
            abstractC0727q0B2 = c3887e0.b(c0767v2);
        }
        return (abstractC0727q0B2 != null || (s1 = this.k) == null) ? abstractC0727q0B2 : s1.b(c0767v2);
    }

    @Override // com.android.tools.r8.graph.AbstractC0681j0
    public final C0729q2 d(C0767v2 c0767v2) {
        if (l || c0767v2.O0()) {
            return (C0729q2) this.i.b(c0767v2);
        }
        throw new AssertionError("Cannot lookup definition for type: " + c0767v2);
    }

    /* JADX WARN: Code duplicated, block: B:12:0x002e  */
    /* JADX WARN: Code duplicated, block: B:14:0x0032  */
    /* JADX WARN: Code duplicated, block: B:16:0x003f  */
    @Override // com.android.tools.r8.graph.Q0
    public final O f(C0767v2 c0767v2) {
        C3887e0 c3887e0;
        C0754u0 c0754u0;
        Consumer consumer;
        final J j = new J();
        com.android.tools.r8.utils.S1 s1 = this.k;
        if (s1 != null) {
            C0635d2 c0635d2 = (C0635d2) s1.b(c0767v2);
            Consumer consumer2 = new Consumer() { // from class: com.android.tools.r8.graph.b4$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    j.a((C0635d2) obj);
                }
            };
            if (c0635d2 != null) {
                consumer2.accept(c0635d2);
            }
        }
        com.android.tools.r8.utils.A2 a2 = this.i;
        if (a2 != null) {
            C0729q2 c0729q2 = (C0729q2) a2.b(c0767v2);
            Consumer consumer3 = new Consumer() { // from class: com.android.tools.r8.graph.b4$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    j.a((C0729q2) obj);
                }
            };
            if (c0729q2 != null) {
                consumer3.accept(c0729q2);
            } else {
                c3887e0 = this.j;
                if (c3887e0 != null) {
                    c0754u0 = (C0754u0) c3887e0.b(c0767v2);
                    consumer = new Consumer() { // from class: com.android.tools.r8.graph.b4$$ExternalSyntheticLambda2
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            j.a((C0754u0) obj);
                        }
                    };
                    if (c0754u0 != null) {
                        consumer.accept(c0754u0);
                    }
                }
            }
        } else {
            c3887e0 = this.j;
            if (c3887e0 != null) {
                c0754u0 = (C0754u0) c3887e0.b(c0767v2);
                consumer = new Consumer() { // from class: com.android.tools.r8.graph.b4$$ExternalSyntheticLambda2
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        j.a((C0754u0) obj);
                    }
                };
                if (c0754u0 != null) {
                    consumer.accept(c0754u0);
                }
            }
        }
        return j.a();
    }

    @Override // com.android.tools.r8.graph.AbstractC0681j0
    public N2 i() {
        return new N2.a(this).b().b();
    }

    public final C0601a4 j() {
        return new C0601a4(this.k, this.j, this.i, this.d);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.tools.r8.graph.AbstractC0681j0
    /* JADX INFO: renamed from: k, reason: merged with bridge method [inline-methods] */
    public final ArrayList h() {
        this.i.a(new Predicate() { // from class: com.android.tools.r8.graph.b4$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C0612b4.g((C0767v2) obj);
            }
        });
        return this.i.b();
    }

    public final String toString() {
        return "Application (" + this.i + "; " + this.j + "; " + this.k + Constant.AFTER_QUTO;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static AbstractC2178hm a(AbstractC2178hm abstractC2178hm, final Function function, com.android.tools.r8.utils.A1 a1) {
        if (abstractC2178hm == null) {
            return AbstractC2178hm.s();
        }
        final Set setC = OU.c();
        final C1946em c1946emE = AbstractC2178hm.e();
        abstractC2178hm.forEach(new BiConsumer() { // from class: com.android.tools.r8.graph.b4$$ExternalSyntheticLambda4
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C0612b4.a(function, c1946emE, setC, (C0767v2) obj, (AbstractC0727q0) obj2);
            }
        });
        if (!setC.isEmpty()) {
            a(a1, setC);
        }
        return c1946emE.a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Function function, C1946em c1946em, Set set, C0767v2 c0767v2, AbstractC0727q0 abstractC0727q0) {
        AbstractC0727q0 abstractC0727q1 = (AbstractC0727q0) function.apply(c0767v2);
        if (abstractC0727q1 == null) {
            c1946em.a(c0767v2, abstractC0727q0);
        } else if (c0767v2.I0().startsWith("java.")) {
            if (abstractC0727q0.f0() || abstractC0727q1.f0()) {
                set.add(c0767v2);
            }
        }
    }

    @Override // com.android.tools.r8.graph.AbstractC0681j0
    public final AbstractC0674i0 c() {
        return new a();
    }

    private static void a(com.android.tools.r8.utils.A1 a1, Set set) {
        if (a1.d0) {
            return;
        }
        a1.e.c("The following library types, prefixed by java., are present both as library and non library classes: " + ((String) set.stream().sorted().map(new b4$$ExternalSyntheticLambda3()).collect(Collectors.joining(", "))) + ". " + (a1.I0 ? "Non library" : "Library") + " classes will be ignored.");
    }
}
