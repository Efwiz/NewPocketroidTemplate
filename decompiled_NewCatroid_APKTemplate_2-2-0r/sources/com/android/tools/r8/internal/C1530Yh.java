package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.shaking.C3733i;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Yh, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C1530Yh {
    private final C0702m a;

    C1530Yh(C0702m c0702m) {
        this.a = c0702m;
    }

    public final void a(AbstractC1182Mh abstractC1182Mh, com.android.tools.r8.graph.m5 m5Var, C2133h6.a aVar) {
        if (!abstractC1182Mh.L1() && a(abstractC1182Mh.M().b(), m5Var)) {
            aVar.a(abstractC1182Mh, new Consumer() { // from class: com.android.tools.r8.internal.Yh$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((C1817d6) obj).b();
                }
            });
        }
    }

    private boolean a(S10 s10, com.android.tools.r8.graph.m5 m5Var) {
        com.android.tools.r8.graph.m5 m5VarU;
        com.android.tools.r8.utils.O3 o3D = com.android.tools.r8.utils.O3.d((Iterable) s10.h0());
        if (s10.z() || s10.C()) {
            return false;
        }
        o3D.a((Iterable) s10.h0());
        boolean z = false;
        while (o3D.c()) {
            AbstractC2412ko abstractC2412ko = (AbstractC2412ko) o3D.f();
            if (!abstractC2412ko.n1() && !abstractC2412ko.i2() && !abstractC2412ko.C2()) {
                if (abstractC2412ko.L1()) {
                    InterfaceC1474Wh interfaceC1474WhO = abstractC2412ko.O();
                    com.android.tools.r8.graph.Y0 field = interfaceC1474WhO.getField();
                    com.android.tools.r8.graph.Y0 reference = m5Var.getReference();
                    field.getClass();
                    if (reference.f == field.f && reference.h == field.h && interfaceC1474WhO.c() == m5Var.getAccessFlags().o() && (m5VarU = ((C3733i) this.a.f()).c(field).u()) != null && m5VarU.e() == m5Var.e() && m5VarU.a() == m5Var.a()) {
                        z = true;
                    }
                }
                return false;
            }
            S10 s10B = abstractC2412ko.b();
            if (s10B.z() || s10B.C()) {
                return false;
            }
            o3D.a((Iterable) s10B.h0());
        }
        return z;
    }
}
