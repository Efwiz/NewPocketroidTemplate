package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.k7, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2359k7 extends AbstractC3327x1 {
    static final /* synthetic */ boolean a = true;

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ AbstractC2359k7 a(AbstractC2359k7 abstractC2359k7, InterfaceC3054tM interfaceC3054tM) {
        return abstractC2359k7;
    }

    public static C3258w5 g() {
        return C3258w5.k();
    }

    public abstract AbstractC2359k7 a(int i, InterfaceC1348Si interfaceC1348Si, InterfaceC3107u6 interfaceC3107u6);

    public abstract AbstractC2359k7 a(C0684j2 c0684j2, InterfaceC3107u6 interfaceC3107u6);

    public abstract AbstractC2359k7 a(C0702m c0702m, C0767v2 c0767v2, InterfaceC3107u6 interfaceC3107u6, BiFunction biFunction);

    public abstract AbstractC2359k7 a(C0702m c0702m, InterfaceC3107u6 interfaceC3107u6, int i, EnumC1812d20 enumC1812d20, BiFunction biFunction);

    public abstract AbstractC2359k7 a(C0702m c0702m, InterfaceC3107u6 interfaceC3107u6, C0767v2... c0767v2Arr);

    public abstract AbstractC2359k7 a(AbstractC1579a10 abstractC1579a10, C0767v2 c0767v2);

    public abstract AbstractC2359k7 a(InterfaceC3107u6 interfaceC3107u6, C0767v2 c0767v2);

    public abstract AbstractC2359k7 a(InterfaceC3107u6 interfaceC3107u6, C2284j7 c2284j7);

    public abstract AbstractC2359k7 a(InterfaceC3107u6 interfaceC3107u6, InterfaceC3054tM interfaceC3054tM);

    public abstract AbstractC2359k7 a(InterfaceC3107u6 interfaceC3107u6, AbstractC3472z00 abstractC3472z00);

    public abstract AbstractC2359k7 a(BiFunction biFunction);

    @Override // com.android.tools.r8.internal.InterfaceC2998sZ
    public final AbstractC3327x1 a() {
        return this;
    }

    public abstract AbstractC2359k7 b(InterfaceC3107u6 interfaceC3107u6, C0767v2 c0767v2);

    public abstract AbstractC2359k7 b(InterfaceC3107u6 interfaceC3107u6, C2284j7 c2284j7);

    public abstract AbstractC2359k7 c(InterfaceC3107u6 interfaceC3107u6, C2284j7 c2284j7);

    public final AbstractC2359k7 c(final BiFunction biFunction) {
        return a(new BiFunction() { // from class: com.android.tools.r8.internal.k7$$ExternalSyntheticLambda0
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return AbstractC2359k7.b(biFunction, (AbstractC2359k7) obj, (InterfaceC3054tM) obj2);
            }
        });
    }

    public C1523Yb e() {
        return null;
    }

    public C2325jh f() {
        return null;
    }

    public abstract AbstractC2359k7 h();

    public abstract AbstractC2359k7 i();

    public final AbstractC2359k7 j() {
        return c(new BiFunction() { // from class: com.android.tools.r8.internal.k7$$ExternalSyntheticLambda3
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return AbstractC2359k7.a((AbstractC2359k7) obj, (InterfaceC3054tM) obj2);
            }
        });
    }

    public final AbstractC2359k7 b(final BiFunction biFunction) {
        return a(new BiFunction() { // from class: com.android.tools.r8.internal.k7$$ExternalSyntheticLambda5
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return AbstractC2359k7.a(biFunction, (AbstractC2359k7) obj, (InterfaceC3054tM) obj2);
            }
        });
    }

    @Override // com.android.tools.r8.internal.AbstractC3327x1
    public final boolean a(C0702m c0702m, AbstractC3327x1 abstractC3327x1) {
        AbstractC2359k7 abstractC2359k7 = (AbstractC2359k7) abstractC3327x1;
        if (this == abstractC2359k7) {
            return true;
        }
        if (!a && !c0702m.f().h()) {
            throw new AssertionError();
        }
        return equals(a(c0702m.h0(), abstractC2359k7, UnaryOperator.identity()));
    }

    public final AbstractC2359k7 b(C0702m c0702m, final C0767v2 c0767v2, final InterfaceC3107u6 interfaceC3107u6, final BiFunction biFunction) {
        final H6 h6C = interfaceC3107u6.c();
        final C0722p1 c0722p1A = c0702m.a();
        return a(new BiFunction() { // from class: com.android.tools.r8.internal.k7$$ExternalSyntheticLambda8
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return AbstractC2359k7.a(h6C, c0722p1A, interfaceC3107u6, c0767v2, biFunction, (AbstractC2359k7) obj, (InterfaceC3054tM) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static AbstractC2359k7 b(BiFunction biFunction, AbstractC2359k7 abstractC2359k7, InterfaceC3054tM interfaceC3054tM) {
        if (interfaceC3054tM.e()) {
            return (AbstractC2359k7) biFunction.apply(abstractC2359k7, interfaceC3054tM);
        }
        return a(C2325jh.c(interfaceC3054tM), C2325jh.d(InterfaceC1348Si.B()), "on stack");
    }

    public static C2325jh a(String str) {
        return new C2325jh(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ AbstractC2359k7 b(com.android.tools.r8.utils.G3 g3, InterfaceC3054tM interfaceC3054tM, AbstractC2359k7 abstractC2359k7, InterfaceC3054tM interfaceC3054tM2) {
        return (AbstractC2359k7) g3.a(abstractC2359k7, interfaceC3054tM2, interfaceC3054tM);
    }

    public static C2325jh a(InterfaceC1348Si interfaceC1348Si, EnumC1812d20 enumC1812d20, int i) {
        return a(C2325jh.c(interfaceC1348Si), C2325jh.b(enumC1812d20), "at local index " + i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ AbstractC2359k7 b(final com.android.tools.r8.utils.G3 g3, AbstractC2359k7 abstractC2359k7, final InterfaceC3054tM interfaceC3054tM) {
        return abstractC2359k7.c(new BiFunction() { // from class: com.android.tools.r8.internal.k7$$ExternalSyntheticLambda6
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return AbstractC2359k7.b(g3, interfaceC3054tM, (AbstractC2359k7) obj, (InterfaceC3054tM) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ InterfaceC1348Si b(InterfaceC1348Si interfaceC1348Si) {
        return interfaceC1348Si.e() ? InterfaceC1348Si.B() : InterfaceC1348Si.Q();
    }

    public static C2325jh a(InterfaceC1348Si interfaceC1348Si, InterfaceC2728on interfaceC2728on) {
        return a(C2325jh.c(interfaceC1348Si), C2325jh.d(interfaceC2728on), "on stack");
    }

    public static C2325jh a(InterfaceC1348Si interfaceC1348Si) {
        return a(C2325jh.c(interfaceC1348Si), "an array type", "on stack");
    }

    private static C2325jh a(String str, String str2, String str3) {
        return new C2325jh("Expected " + str2 + " " + str3 + ", but was " + str);
    }

    public final AbstractC2359k7 a(C0702m c0702m, InterfaceC3107u6 interfaceC3107u6, C0767v2 c0767v2) {
        return a(c0702m, c0767v2, interfaceC3107u6, new D8$$ExternalSyntheticLambda18());
    }

    public final AbstractC2359k7 a(C0702m c0702m, InterfaceC3107u6 interfaceC3107u6, EnumC1812d20 enumC1812d20) {
        return a(c0702m, enumC1812d20.a(c0702m.a()), interfaceC3107u6, new D8$$ExternalSyntheticLambda18());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static AbstractC2359k7 a(BiFunction biFunction, AbstractC2359k7 abstractC2359k7, InterfaceC3054tM interfaceC3054tM) {
        if (interfaceC3054tM.u()) {
            return (AbstractC2359k7) biFunction.apply(abstractC2359k7, interfaceC3054tM);
        }
        return a(C2325jh.c(interfaceC3054tM), C2325jh.b(EnumC1812d20.OBJECT), "on stack");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static AbstractC2359k7 a(H6 h6, C0722p1 c0722p1, InterfaceC3107u6 interfaceC3107u6, C0767v2 c0767v2, BiFunction biFunction, AbstractC2359k7 abstractC2359k7, InterfaceC3054tM interfaceC3054tM) {
        if (interfaceC3054tM.u() && h6.b(interfaceC3054tM.a(c0722p1, interfaceC3107u6.e().B0()), c0767v2)) {
            return (AbstractC2359k7) biFunction.apply(abstractC2359k7, interfaceC3054tM);
        }
        return a(C2325jh.c(interfaceC3054tM), C2325jh.b(c0767v2), "on stack");
    }

    public final AbstractC2359k7 a(final com.android.tools.r8.utils.G3 g3) {
        return c(new BiFunction() { // from class: com.android.tools.r8.internal.k7$$ExternalSyntheticLambda2
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return AbstractC2359k7.b(g3, (AbstractC2359k7) obj, (InterfaceC3054tM) obj2);
            }
        });
    }

    public final AbstractC2359k7 a(final BiFunction biFunction, final BiFunction biFunction2) {
        return a(new BiFunction() { // from class: com.android.tools.r8.internal.k7$$ExternalSyntheticLambda4
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return AbstractC2359k7.a(biFunction, biFunction2, (AbstractC2359k7) obj, (InterfaceC3054tM) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ AbstractC2359k7 a(BiFunction biFunction, BiFunction biFunction2, AbstractC2359k7 abstractC2359k7, InterfaceC3054tM interfaceC3054tM) {
        return (AbstractC2359k7) (interfaceC3054tM.e() ? biFunction.apply(abstractC2359k7, interfaceC3054tM) : biFunction2.apply(abstractC2359k7, interfaceC3054tM));
    }

    public final AbstractC2359k7 a(final com.android.tools.r8.utils.G3 g3, BiFunction biFunction) {
        return a(new BiFunction() { // from class: com.android.tools.r8.internal.k7$$ExternalSyntheticLambda1
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return AbstractC2359k7.a(g3, (AbstractC2359k7) obj, (InterfaceC3054tM) obj2);
            }
        }, biFunction);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ AbstractC2359k7 a(com.android.tools.r8.utils.G3 g3, InterfaceC3054tM interfaceC3054tM, AbstractC2359k7 abstractC2359k7, InterfaceC3054tM interfaceC3054tM2) {
        return (AbstractC2359k7) g3.a(abstractC2359k7, interfaceC3054tM2, interfaceC3054tM);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ AbstractC2359k7 a(final com.android.tools.r8.utils.G3 g3, AbstractC2359k7 abstractC2359k7, final InterfaceC3054tM interfaceC3054tM) {
        return abstractC2359k7.c(new BiFunction() { // from class: com.android.tools.r8.internal.k7$$ExternalSyntheticLambda9
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return AbstractC2359k7.a(g3, interfaceC3054tM, (AbstractC2359k7) obj, (InterfaceC3054tM) obj2);
            }
        });
    }

    public final AbstractC2359k7 a(int i, XM xm, C0702m c0702m, InterfaceC3107u6 interfaceC3107u6) {
        if (!a) {
            xm.getClass();
            if (!(xm instanceof C1484Wr) && !(xm instanceof C0858Bi) && !(xm instanceof C1984fD) && !(xm instanceof C0981Ff)) {
                throw new AssertionError();
            }
        }
        return a(i, InterfaceC1348Si.a(xm.a(c0702m.a())), interfaceC3107u6);
    }

    @Override // com.android.tools.r8.internal.AbstractC3327x1
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public final AbstractC2359k7 b(C0702m c0702m, AbstractC2359k7 abstractC2359k7) {
        if (!a && !c0702m.f().h()) {
            throw new AssertionError();
        }
        return a(c0702m.h0(), abstractC2359k7, new UnaryOperator() { // from class: com.android.tools.r8.internal.k7$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return AbstractC2359k7.b((InterfaceC1348Si) obj);
            }
        });
    }

    public final AbstractC2359k7 a(C0702m c0702m, AbstractC2359k7 abstractC2359k7, UnaryOperator unaryOperator) {
        abstractC2359k7.getClass();
        if ((abstractC2359k7 instanceof C3258w5) || (this instanceof C2325jh)) {
            return this;
        }
        if ((this instanceof C3258w5) || (abstractC2359k7 instanceof C2325jh)) {
            return abstractC2359k7;
        }
        boolean z = a;
        if (!z && !(this instanceof C1523Yb)) {
            throw new AssertionError();
        }
        if (!z && !(abstractC2359k7 instanceof C1523Yb)) {
            throw new AssertionError();
        }
        return e().a(c0702m, abstractC2359k7.e(), unaryOperator);
    }
}
