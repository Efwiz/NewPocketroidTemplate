package com.android.tools.r8.graph;

import com.android.tools.r8.internal.AbstractC2178hm;
import com.android.tools.r8.utils.C3887e0;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.graph.a4, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C0601a4 {
    static final /* synthetic */ boolean d = true;
    private final AbstractC2178hm a;
    private final AbstractC2178hm b;
    private final AbstractC2178hm c;

    C0601a4(com.android.tools.r8.utils.S1 s1, C3887e0 c3887e0, com.android.tools.r8.utils.A2 a2, final com.android.tools.r8.utils.A1 a1) {
        AbstractC2178hm abstractC2178hmS;
        AbstractC2178hm abstractC2178hmS2;
        Predicate predicate = new Predicate() { // from class: com.android.tools.r8.graph.a4$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C0601a4.a(a1, (C0767v2) obj);
            }
        };
        if (s1 != null) {
            s1.a(predicate);
            abstractC2178hmS = s1.c();
        } else {
            abstractC2178hmS = AbstractC2178hm.s();
        }
        boolean z = d;
        if (!z && a2 == null) {
            throw new AssertionError();
        }
        if (!z && !a2.f()) {
            throw new AssertionError();
        }
        a2.a(new Predicate() { // from class: com.android.tools.r8.graph.a4$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C0601a4.a((C0767v2) obj);
            }
        });
        final AbstractC2178hm abstractC2178hmC = a2.c();
        if (c3887e0 != null) {
            c3887e0.a(new Predicate() { // from class: com.android.tools.r8.graph.a4$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return C0601a4.b((C0767v2) obj);
                }
            });
            abstractC2178hmS2 = c3887e0.c();
        } else {
            abstractC2178hmS2 = AbstractC2178hm.s();
        }
        if (a1.K0) {
            this.c = abstractC2178hmS;
            this.a = abstractC2178hmC;
            Objects.requireNonNull(abstractC2178hmC);
            this.b = C0612b4.a(abstractC2178hmS2, new Function() { // from class: com.android.tools.r8.graph.a4$$ExternalSyntheticLambda3
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return (AbstractC0727q0) abstractC2178hmC.get((C0767v2) obj);
                }
            }, a1);
            return;
        }
        if (a1.I0) {
            final AbstractC2178hm abstractC2178hmA = C0612b4.a(abstractC2178hmS, new Function() { // from class: com.android.tools.r8.graph.a4$$ExternalSyntheticLambda4
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return C0601a4.c((C0767v2) obj);
                }
            }, a1);
            this.c = abstractC2178hmA;
            Objects.requireNonNull(abstractC2178hmA);
            this.a = C0612b4.a(abstractC2178hmC, new Function() { // from class: com.android.tools.r8.graph.a4$$ExternalSyntheticLambda3
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return (AbstractC0727q0) abstractC2178hmA.get((C0767v2) obj);
                }
            }, a1);
            this.b = C0612b4.a(abstractC2178hmS2, new Function() { // from class: com.android.tools.r8.graph.a4$$ExternalSyntheticLambda5
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return this.f$0.d((C0767v2) obj);
                }
            }, a1);
            return;
        }
        final AbstractC2178hm abstractC2178hmA2 = C0612b4.a(abstractC2178hmC, new Function() { // from class: com.android.tools.r8.graph.a4$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C0601a4.e((C0767v2) obj);
            }
        }, a1);
        this.a = abstractC2178hmA2;
        Objects.requireNonNull(abstractC2178hmA2);
        this.b = C0612b4.a(abstractC2178hmS2, new Function() { // from class: com.android.tools.r8.graph.a4$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return (AbstractC0727q0) abstractC2178hmA2.get((C0767v2) obj);
            }
        }, a1);
        this.c = C0612b4.a(abstractC2178hmS, new Function() { // from class: com.android.tools.r8.graph.a4$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.f((C0767v2) obj);
            }
        }, a1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C0767v2 c0767v2) {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean b(C0767v2 c0767v2) {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ AbstractC0727q0 c(C0767v2 c0767v2) {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ AbstractC0727q0 d(C0767v2 c0767v2) {
        C0635d2 c0635d2 = (C0635d2) this.c.get(c0767v2);
        return c0635d2 != null ? c0635d2 : (AbstractC0727q0) this.a.get(c0767v2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ AbstractC0727q0 e(C0767v2 c0767v2) {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ AbstractC0727q0 f(C0767v2 c0767v2) {
        C0729q2 c0729q2 = (C0729q2) this.a.get(c0767v2);
        return c0729q2 != null ? c0729q2 : (AbstractC0727q0) this.b.get(c0767v2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(com.android.tools.r8.utils.A1 a1, C0767v2 c0767v2) {
        return (a1.D1() && (c0767v2 == a1.j0().B2 || c0767v2 == a1.j0().o5)) ? false : true;
    }

    public final AbstractC2178hm b() {
        return this.c;
    }

    public final AbstractC2178hm c() {
        return this.a;
    }

    public final AbstractC2178hm a() {
        return this.b;
    }
}
