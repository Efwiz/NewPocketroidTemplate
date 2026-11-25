package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.shaking.InterfaceC3680a0;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Jz, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1120Jz implements InterfaceC3680a0 {
    private final C2973sA a;
    private final String b;

    private C1120Jz(C2973sA c2973sA, String str) {
        this.b = str;
        this.a = c2973sA;
    }

    public static C1120Jz a(com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.e eVar, C0722p1 c0722p1) {
        if (eVar == null) {
            return null;
        }
        String strC = eVar.c();
        String strB = eVar.b();
        return new C1120Jz(C2973sA.b(strB, c0722p1, strB), strC);
    }

    /* JADX WARN: Code duplicated, block: B:7:0x002a  */
    final boolean a(Consumer consumer, com.android.tools.r8.graph.T0 t0, C0702m c0702m) {
        boolean z;
        String string;
        String str = this.b;
        if (t0 != null) {
            String string2 = t0.getReference().f.toString();
            string = c0702m.z().a(t0.getReference()).toString();
            if (string2.equals(string)) {
                z = false;
                string = str;
            } else {
                z = true;
            }
        } else {
            z = false;
            string = str;
        }
        String strB1 = c0702m.a().Z1.b1();
        com.android.tools.r8.utils.N n = new com.android.tools.r8.utils.N();
        boolean zB = this.a.b(new Jz$$ExternalSyntheticLambda0(n), c0702m, strB1) | z;
        consumer.accept(new com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.e(string, (String) n.a()));
        return zB;
    }

    @Override // com.android.tools.r8.shaking.InterfaceC3680a0
    public final void a(com.android.tools.r8.graph.Q0 q0) {
        this.a.a(q0);
    }
}
