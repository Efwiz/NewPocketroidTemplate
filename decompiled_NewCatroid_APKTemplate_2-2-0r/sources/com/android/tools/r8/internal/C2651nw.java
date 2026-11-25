package com.android.tools.r8.internal;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.bytebuddy.description.method.MethodDescription;

/* JADX INFO: renamed from: com.android.tools.r8.internal.nw, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C2651nw implements JK {
    static final /* synthetic */ boolean h = true;

    /* JADX INFO: renamed from: com.android.tools.r8.internal.nw$a */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public interface a {
        a a(String str);

        a a(String str, String str2);

        void a();

        void a(String str, Object obj);

        void a(String str, String str2, String str3);

        int b();
    }

    public static void a(C2736ov c2736ov, final C1549Za c1549Za) {
        BiFunction biFunction = new BiFunction() { // from class: com.android.tools.r8.internal.nw$$ExternalSyntheticLambda2
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return c1549Za.a((String) obj, ((Boolean) obj2).booleanValue());
            }
        };
        C2651nw c2651nw = new C2651nw();
        AbstractC2509m2 abstractC2509m2 = (AbstractC2509m2) biFunction.apply(C2043g2.a.b, Boolean.FALSE);
        c2651nw.a(abstractC2509m2, c2736ov.e());
        c2651nw.a(abstractC2509m2, c2736ov.c());
        abstractC2509m2.a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static AbstractC2509m2 a(BiFunction biFunction, String str, Boolean bool) {
        a aVar = (a) biFunction.apply(str, bool);
        if (aVar == null) {
            return null;
        }
        return new C2573mw(aVar.b(), aVar);
    }

    public static void a(C2736ov c2736ov, final BiFunction<String, Boolean, a> biFunction) {
        BiFunction biFunction2 = new BiFunction() { // from class: com.android.tools.r8.internal.nw$$ExternalSyntheticLambda1
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return C2651nw.a(biFunction, (String) obj, (Boolean) obj2);
            }
        };
        C2651nw c2651nw = new C2651nw();
        AbstractC2509m2 abstractC2509m2 = (AbstractC2509m2) biFunction2.apply(C2043g2.a.b, Boolean.FALSE);
        c2651nw.a(abstractC2509m2, c2736ov.e());
        c2651nw.a(abstractC2509m2, c2736ov.c());
        abstractC2509m2.a();
    }

    private void a(AbstractC2509m2 abstractC2509m2, AbstractC2190hx abstractC2190hx) {
        if (abstractC2190hx.c()) {
            return;
        }
        final AbstractC2509m2 abstractC2509m2A = abstractC2509m2.a("preconditions");
        final String str = null;
        abstractC2190hx.a(new Consumer() { // from class: com.android.tools.r8.internal.nw$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(abstractC2509m2A, str, (C2572mv) obj);
            }
        });
        abstractC2509m2A.a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(AbstractC2509m2 abstractC2509m2, String str, C2572mv c2572mv) {
        AbstractC2509m2 abstractC2509m2A = abstractC2509m2.a(str, AbstractC1967f2.a);
        if (!c2572mv.b().c()) {
            a(abstractC2509m2A, c2572mv.b().b());
            return;
        }
        throw new Y00();
    }

    private void a(AbstractC2509m2 abstractC2509m2, C2649nv c2649nv) {
        if (!h && c2649nv.b()) {
            throw new AssertionError();
        }
        final AbstractC2509m2 abstractC2509m2A = abstractC2509m2.a("consequences");
        final String str = null;
        c2649nv.a(new Consumer() { // from class: com.android.tools.r8.internal.nw$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(abstractC2509m2A, str, (C2814px) obj);
            }
        });
        abstractC2509m2A.a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(AbstractC2509m2 abstractC2509m2, String str, C2814px c2814px) {
        AbstractC2509m2 abstractC2509m2A = abstractC2509m2.a(str, C2043g2.e.a);
        if (c2814px.c().c()) {
            if (!c2814px.b().c()) {
                a(abstractC2509m2A, c2814px.b().b());
                return;
            }
            throw new Y00();
        }
        throw new Y00();
    }

    private static void a(AbstractC2509m2 abstractC2509m2, C0846Aw c0846Aw) {
        if (!c0846Aw.a(new Predicate() { // from class: com.android.tools.r8.internal.nw$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C2651nw.a((String) obj);
            }
        })) {
            AbstractC2496lv abstractC2496lvD = c0846Aw.d();
            if (!abstractC2496lvD.d()) {
                C2424kx c2424kxB = abstractC2496lvD.b();
                if (c2424kxB.f()) {
                    abstractC2509m2.a("classConstant", CZ.f(c2424kxB.b()));
                    if (c0846Aw.e().d()) {
                        a(c0846Aw.g(), abstractC2509m2);
                        abstractC2509m2.a();
                        return;
                    }
                    throw new Y00();
                }
                throw new Y00();
            }
            throw new Y00();
        }
        throw new Y00();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(String str) {
        throw new Y00();
    }

    private static void a(AbstractC1171Lw abstractC1171Lw, AbstractC2509m2 abstractC2509m2) {
        if (abstractC1171Lw.i()) {
            return;
        }
        if (!abstractC1171Lw.e()) {
            if (abstractC1171Lw.h()) {
                C1463Vw c1463VwC = abstractC1171Lw.c();
                C1282Pw c1282PwB = c1463VwC.m().b();
                if (c1282PwB != null) {
                    abstractC2509m2.a("methodName", (Object) c1282PwB.e());
                    if (c1463VwC.l().f()) {
                        if (!c1463VwC.o().c() && ((!c1282PwB.e().equals("<init>") && !c1282PwB.e().equals(MethodDescription.TYPE_INITIALIZER_INTERNAL_NAME)) || !c1463VwC.o().d())) {
                            throw new Y00();
                        }
                        if (!c1463VwC.n().d()) {
                            throw new Y00();
                        }
                        return;
                    }
                    throw new Y00();
                }
                throw new Y00();
            }
            if (abstractC1171Lw.f()) {
                C3319ww c3319wwB = abstractC1171Lw.b();
                C3170uw c3170uwB = c3319wwB.m().b();
                if (c3170uwB != null) {
                    abstractC2509m2.a("fieldName", (Object) c3170uwB.d());
                    if (c3319wwB.l().f()) {
                        if (!c3319wwB.n().c()) {
                            throw new Y00();
                        }
                        return;
                    }
                    throw new Y00();
                }
                throw new Y00();
            }
            throw new C2883qv("Unexpected member pattern: " + abstractC1171Lw);
        }
        throw new Y00();
    }
}
