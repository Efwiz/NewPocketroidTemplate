package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.InterfaceC0694k5;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.w3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3256w3 {
    static final /* synthetic */ boolean a = true;

    public static void a(C0702m c0702m, EnumC3181v3... enumC3181v3Arr) {
        if (c0702m.Y().q0().d()) {
            C2434l3 c2434l3D = c0702m.s().a().d();
            if (a) {
                return;
            }
            HashSet hashSetA = OU.a(enumC3181v3Arr.length);
            Collections.addAll(hashSetA, enumC3181v3Arr);
            a(c0702m, c2434l3D, hashSetA);
        }
    }

    private static void a(C0702m c0702m, C2434l3 c2434l3, HashSet hashSet) {
        if (!a && c0702m.D().d()) {
            throw new AssertionError();
        }
        ArrayList arrayList = new ArrayList();
        for (C0729q2 c0729q2 : c0702m.f().e()) {
            if (!c0702m.M().a(c0729q2.getType()) && (!c0702m.L() || !c0702m.g0().d(c0729q2.getType()))) {
                C3226vg c3226vgE0 = c0702m.e0();
                c3226vgE0.getClass();
                if (!c3226vgE0.b(c0729q2.getType())) {
                    if (!c2434l3.a(c0729q2.getType())) {
                        a(c0702m, c0729q2, hashSet, arrayList);
                    }
                    for (com.android.tools.r8.graph.o5 o5Var : AbstractC0984Fi.a(c0729q2.R1(), c0729q2.k2())) {
                        if (!c2434l3.a(o5Var.getReference())) {
                            a(c0702m, o5Var, hashSet, arrayList);
                        }
                    }
                }
            }
        }
        if (arrayList.isEmpty()) {
            return;
        }
        String strA = com.android.tools.r8.utils.Y2.a(System.lineSeparator(), arrayList, new Function() { // from class: com.android.tools.r8.internal.w3$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((AbstractC0744s2) obj).q0();
            }
        });
        if (!a) {
            throw new AssertionError(strA);
        }
    }

    private static void a(C0702m c0702m, InterfaceC0694k5 interfaceC0694k5, HashSet hashSet, ArrayList arrayList) {
        if (hashSet.contains(EnumC3181v3.a)) {
            C0767v2 c0767v2C = interfaceC0694k5.C();
            com.android.tools.r8.synthesis.D D = c0702m.D();
            if (D.i(c0767v2C) && (D.a(c0767v2C, new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.w3$$ExternalSyntheticLambda0
                @Override // com.android.tools.r8.synthesis.C
                public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                    return l.z;
                }
            }) || D.a(c0767v2C, new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.w3$$ExternalSyntheticLambda1
                @Override // com.android.tools.r8.synthesis.C
                public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                    return l.i;
                }
            }) || D.a(c0767v2C, new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.w3$$ExternalSyntheticLambda2
                @Override // com.android.tools.r8.synthesis.C
                public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                    return l.j;
                }
            }))) {
                return;
            }
        }
        arrayList.add(interfaceC0694k5.getReference());
    }
}
