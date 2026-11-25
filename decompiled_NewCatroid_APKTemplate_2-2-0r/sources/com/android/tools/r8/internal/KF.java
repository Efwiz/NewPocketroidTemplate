package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0722p1;
import java.util.Iterator;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class KF extends C1672bB {
    public KF(C0722p1 c0722p1, com.android.tools.r8.utils.O2 o2) {
        super(c0722p1, o2, false, 1);
    }

    private C2642np a(String str) {
        AbstractC1249Ot abstractC1249OtA = a(b(), str);
        C2642np c2642np = new C2642np();
        Iterator it = abstractC1249OtA.c().iterator();
        while (it.hasNext()) {
            C1333Rt c1333RtD = ((AbstractC1249Ot) it.next()).d();
            int iB = a(c1333RtD, "api_level_below_or_equal").b();
            C1747cB.a aVarB = c2642np.a(iB) ? ((C1747cB) c2642np.get(iB)).b(a(), d(), c()) : C1747cB.a(a(), d(), c());
            C1672bB.a(c1333RtD, aVarB);
            c2642np.a(iB, aVarB.a());
        }
        return c2642np;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(InterfaceC2089gZ interfaceC2089gZ) {
    }

    public JF c(com.android.tools.r8.d0 d0Var) {
        return new JF(c(), a(new Consumer() { // from class: com.android.tools.r8.internal.KF$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                KF.b((InterfaceC2089gZ) obj);
            }
        }, b(d0Var)), a("common_flags"), a("library_flags"), a("program_flags"));
    }
}
