package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.utils.EnumC3891f;
import java.util.HashMap;
import java.util.Iterator;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class IF extends C1185Mk {
    public IF(C0722p1 c0722p1, com.android.tools.r8.utils.O2 o2) {
        super(c0722p1, o2, false, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(InterfaceC2089gZ interfaceC2089gZ) {
    }

    public EF c(com.android.tools.r8.d0 d0Var) {
        return new EF(b(), a(new Consumer() { // from class: com.android.tools.r8.internal.IF$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                IF.b((InterfaceC2089gZ) obj);
            }
        }, b(d0Var)), c("common_flags"), c("library_flags"), c("program_flags"));
    }

    private HashMap c(String str) {
        C2822q2 c2822q2;
        C1297Qk.a aVarA;
        AbstractC1249Ot abstractC1249OtA = a(a(), str);
        HashMap map = new HashMap();
        Iterator it = abstractC1249OtA.c().iterator();
        while (it.hasNext()) {
            C1333Rt c1333RtD = ((AbstractC1249Ot) it.next()).d();
            int iB = a(c1333RtD, "api_level_below_or_equal").b();
            if (c1333RtD.b("api_level_greater_or_equal")) {
                c2822q2 = new C2822q2(EnumC3891f.c(iB), EnumC3891f.c(c1333RtD.a("api_level_greater_or_equal").b()));
            } else {
                c2822q2 = new C2822q2(iB);
            }
            if (map.containsKey(c2822q2)) {
                aVarA = ((C1297Qk) map.get(c2822q2)).b(c(), b());
            } else {
                aVarA = C1297Qk.a(c(), b());
            }
            a(c1333RtD, aVarA);
            map.put(c2822q2, aVarA.a());
        }
        return map;
    }
}
