package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.shaking.C3733i;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class YL {
    static final /* synthetic */ boolean a = true;

    private static void a(C0702m c0702m, EnumC0928Dk enumC0928Dk, final C1507Xl c1507Xl) {
        AbstractC1639am.a(new UY(c0702m, enumC0928Dk), new CU()).stream().map(new Function() { // from class: com.android.tools.r8.internal.YL$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return new C2278j20((SF) obj);
            }
        }).forEach(new Consumer() { // from class: com.android.tools.r8.internal.YL$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                c1507Xl.a((C2278j20) obj);
            }
        });
    }

    private static void b(C0702m c0702m, EnumC0928Dk enumC0928Dk, C1507Xl c1507Xl) {
        AbstractC1639am.a(new C2295jH(), new HG(), new XG(c0702m, enumC0928Dk, null), new YG(c0702m), new C1990fH(c0702m, enumC0928Dk), new C1753cH(), new C1830dH(), new C2144hH(), new C2221iH(), new C2371kH(c0702m)).stream().map(new YL$$ExternalSyntheticLambda1()).forEach(new YL$$ExternalSyntheticLambda2(c1507Xl));
    }

    private static void c(C0702m c0702m, EnumC0928Dk enumC0928Dk, C1507Xl c1507Xl) {
        AbstractC1639am.a(new C2295jH(), new HG(), new XG(c0702m, enumC0928Dk, null), new C1990fH(c0702m, enumC0928Dk), new C1753cH(), new C1830dH(), new C2144hH(), new C2221iH()).stream().map(new YL$$ExternalSyntheticLambda1()).forEach(new YL$$ExternalSyntheticLambda2(c1507Xl));
    }

    private static void a(List list) {
        int iB = com.android.tools.r8.utils.V1.b(list, new Predicate() { // from class: com.android.tools.r8.internal.YL$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return YL.a((WL) obj);
            }
        });
        if (iB >= 0) {
            for (WL wl : list.subList(iB + 1, list.size())) {
                if (!a && !wl.f()) {
                    throw new AssertionError();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(WL wl) {
        return wl instanceof IK;
    }

    public static List a(C0702m c0702m, InterfaceC2802pl interfaceC2802pl, EnumC0928Dk enumC0928Dk, com.android.tools.r8.shaking.g4 g4Var) {
        if (c0702m.f().h()) {
            C0702m c0702mH0 = c0702m.h0();
            C1507Xl c1507XlG = AbstractC1639am.g();
            C1507Xl c1507XlG2 = AbstractC1639am.g();
            c1507XlG2.a((Object[]) new AbstractC2309jV[]{new H9(c0702mH0), new KG(c0702mH0), new C2062gH(c0702mH0), new PG()});
            if (enumC0928Dk.b()) {
                C0702m<C3733i> c0702mI0 = c0702mH0.i0();
                c1507XlG2.a((Object[]) new AbstractC2309jV[]{new RG(c0702mI0, enumC0928Dk), new C1601aH(c0702mI0, enumC0928Dk), new C2452lH(c0702mI0, enumC0928Dk)});
            }
            if (c0702mH0.Y().O0().g()) {
                if (!a) {
                    b(c0702mH0, enumC0928Dk, c1507XlG2);
                }
            } else {
                C0702m<C3733i> c0702mI1 = c0702mH0.i0();
                c1507XlG2.a((Object[]) new AbstractC2309jV[]{new C2295jH(), new NH(c0702mI1), new HG(), new XG(c0702mI1, enumC0928Dk, g4Var), new YG(c0702mI1), new ZG(c0702mI1), new C1990fH(c0702mI1, enumC0928Dk), new C1753cH(), new C1830dH(), new C2144hH(), new C2221iH(), new C2371kH(c0702mI1)});
            }
            C1507Xl c1507XlA = c1507XlG.a((Iterable) c1507XlG2.a());
            C1507Xl c1507XlG3 = AbstractC1639am.g();
            com.android.tools.r8.graph.H3 h3A = com.android.tools.r8.graph.H3.a(c0702mH0);
            c1507XlG3.a((Object[]) new WL[]{new C3291wU(c0702mH0), new C2758p9(c0702mH0), new LG(), new C3213vU(c0702mH0), new C3438yU(c0702mH0, enumC0928Dk), new C3508zU(c0702mH0), new AU(c0702mH0), new CU(), new UY(c0702mH0, enumC0928Dk), new C2613nT(c0702mH0, enumC0928Dk), new WG(c0702mH0), new C1680bH(c0702mH0, g4Var), new C2601nH(c0702mH0, h3A), new NM(c0702mH0, h3A), new PH(c0702mH0)});
            if (!c0702mH0.Y().O0().g()) {
                C0702m<C3733i> c0702mI2 = c0702mH0.i0();
                c1507XlG3.a((Object[]) new WL[]{new OG(c0702mI2), new SG(c0702mI2)});
            }
            if (enumC0928Dk.b()) {
                C0702m<C3733i> c0702mI3 = c0702mH0.i0();
                c1507XlG3.a((Object[]) new WL[]{new J1(c0702mI3, enumC0928Dk), new FM(c0702mI3, enumC0928Dk), new C2059gF()});
            } else {
                if (!a && !enumC0928Dk.a()) {
                    throw new AssertionError();
                }
                c1507XlG3.a((Object[]) new WL[]{new C2526mH(c0702mH0, enumC0928Dk), new QG(c0702mH0, enumC0928Dk)});
            }
            c1507XlG3.a((Object[]) new WL[]{new VG(c0702mH0), new UG(c0702mH0, enumC0928Dk), new C3264wB(c0702mH0), new IK(c0702mH0, enumC0928Dk)});
            c1507XlG3.a(new C3189vB(c0702mH0));
            if (enumC0928Dk.a()) {
                c1507XlG3.a(new C1901eH(c0702mH0, interfaceC2802pl, enumC0928Dk));
            }
            List<WL> listApply = c0702mH0.Y().n1.t.apply(c1507XlA.a((Iterable) c1507XlG3.a(new C2558mi(c0702mH0, enumC0928Dk)).a()).a());
            if (!a) {
                a(listApply);
            }
            return listApply;
        }
        C0702m c0702mJ0 = c0702m.j0();
        boolean z = a;
        if (!z && !enumC0928Dk.a()) {
            throw new AssertionError();
        }
        C1507Xl c1507XlG4 = AbstractC1639am.g();
        C1507Xl c1507XlA2 = AbstractC1639am.g().a(new H9(c0702mJ0)).a(new GK());
        if (!z) {
            c(c0702mJ0, enumC0928Dk, c1507XlA2);
        }
        C1507Xl c1507XlA3 = c1507XlG4.a((Iterable) c1507XlA2.a());
        C1507Xl c1507XlG5 = AbstractC1639am.g();
        c1507XlG5.a((Object[]) new SF[]{new C2758p9(c0702mJ0), new C3508zU(c0702mJ0), new AU(c0702mJ0), new CU(), new UY(c0702mJ0, enumC0928Dk), new JG(c0702mJ0), new BU(c0702mJ0), new WG(c0702mJ0), new C3189vB(c0702mJ0)});
        if (!z) {
            a(c0702mJ0, enumC0928Dk, c1507XlG5);
        }
        c1507XlG5.a(new C2558mi(c0702mJ0, enumC0928Dk));
        List<WL> listApply2 = c0702mJ0.Y().n1.t.apply(c1507XlA3.a((Iterable) c1507XlG5.a()).a());
        if (!z) {
            a(listApply2);
        }
        return listApply2;
    }
}
