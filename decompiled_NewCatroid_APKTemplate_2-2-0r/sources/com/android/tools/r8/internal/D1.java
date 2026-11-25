package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0719o4;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0725p4;
import com.android.tools.r8.graph.C0729q2;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class D1 {
    static final /* synthetic */ boolean a = true;

    static com.android.tools.r8.graph.o5 a(C0684j2 c0684j2, com.android.tools.r8.graph.m5 m5Var, boolean z) {
        boolean z2 = a;
        if (!z2 && c0684j2.B0() != m5Var.t()) {
            throw new AssertionError();
        }
        if (!z2 && !m5Var.getAccessFlags().j()) {
            throw new AssertionError();
        }
        C0729q2 c0729q2A = m5Var.a();
        com.android.tools.r8.graph.W0.a aVarA = com.android.tools.r8.graph.W0.Y1().a((C0725p4) ((C0719o4) ((C0719o4) ((C0719o4) C0725p4.O().c().c(m5Var.a().isInterface())).f()).g()).a());
        C0857Bh c0857BhA = C0857Bh.b().a(z, new D1$$ExternalSyntheticLambda0(), new D1$$ExternalSyntheticLambda1());
        return new com.android.tools.r8.graph.o5(c0729q2A, aVarA.a((m5Var.getAccessFlags().o() ? c0857BhA.b(m5Var.getReference()) : c0857BhA.a(m5Var.getReference())).a(c0684j2).a()).a(c0684j2).b(m5Var.e().I0()).a(m5Var.e().I0()).a());
    }

    static com.android.tools.r8.graph.o5 b(C0684j2 c0684j2, final com.android.tools.r8.graph.o5 o5Var, C0722p1 c0722p1) {
        boolean z = a;
        if (!z && c0684j2.B0() != o5Var.t()) {
            throw new AssertionError();
        }
        if (!z && o5Var.getAccessFlags().Y()) {
            throw new AssertionError();
        }
        if (!z && !o5Var.getAccessFlags().j()) {
            throw new AssertionError();
        }
        final boolean zIsInterface = o5Var.a().isInterface();
        return new com.android.tools.r8.graph.o5(o5Var.a(), com.android.tools.r8.graph.W0.Y1().a((C0725p4) ((C0719o4) ((C0719o4) ((C0719o4) C0725p4.O().c().c(zIsInterface)).f()).g()).a()).a(C1071Ii.a(c0722p1).d(c0684j2).a(o5Var.getAccessFlags().o(), new Consumer() { // from class: com.android.tools.r8.internal.D1$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                D1.a(o5Var, zIsInterface, (C1071Ii) obj);
            }
        }, new Consumer() { // from class: com.android.tools.r8.internal.D1$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                D1.b(o5Var, zIsInterface, (C1071Ii) obj);
            }
        }).a()).a(c0684j2).b(o5Var.e().I0()).a(o5Var.e().I0()).a());
    }

    static com.android.tools.r8.graph.o5 a(C0684j2 c0684j2, com.android.tools.r8.graph.o5 o5Var, C0722p1 c0722p1) {
        boolean z = a;
        if (!z && c0684j2.B0() != o5Var.t()) {
            throw new AssertionError();
        }
        if (!z && !o5Var.getAccessFlags().Y()) {
            throw new AssertionError();
        }
        if (!z && !o5Var.getAccessFlags().j()) {
            throw new AssertionError();
        }
        if (z || !o5Var.a().isInterface()) {
            return new com.android.tools.r8.graph.o5(o5Var.a(), com.android.tools.r8.graph.W0.Y1().a((C0725p4) ((C0719o4) C0725p4.O().d().g()).a()).a(C1071Ii.a(c0722p1).c(c0684j2).a(o5Var.getReference(), false).a()).a(c0684j2).b(o5Var.e().I0()).a(o5Var.e().Y0()).a());
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(com.android.tools.r8.graph.o5 o5Var, boolean z, C1071Ii c1071Ii) {
        c1071Ii.a(o5Var.getReference(), z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(com.android.tools.r8.graph.o5 o5Var, boolean z, C1071Ii c1071Ii) {
        c1071Ii.b(o5Var.getReference(), z);
    }
}
