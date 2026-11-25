package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0699l2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0725p4;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.EnumC0691k2;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.zA, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3485zA extends GA {
    static final /* synthetic */ boolean j = true;
    private final C0702m f;
    private final C0684j2 g;
    private final boolean h;
    private final EnumC0691k2 i;

    private C3485zA(C0684j2 c0684j2, boolean z, EnumC0691k2 enumC0691k2, C0684j2 c0684j3, boolean z2, C0702m c0702m) {
        super(c0684j3, EnumC1953et.e, z2);
        this.f = c0702m;
        this.g = c0684j2;
        this.h = z;
        this.i = enumC0691k2;
    }

    static C3485zA a(C0699l2 c0699l2, com.android.tools.r8.graph.o5 o5Var, C0684j2 c0684j2, C0702m c0702m) {
        return new C3485zA(c0699l2.u0(), c0699l2.f, c0699l2.d, c0684j2, o5Var.a().isInterface(), c0702m);
    }

    @Override // com.android.tools.r8.internal.GA
    public final C0684j2 b() {
        return this.g;
    }

    @Override // com.android.tools.r8.internal.GA
    final com.android.tools.r8.graph.o5 a(InterfaceC1011Gi interfaceC1011Gi, Consumer consumer) {
        C0729q2 c0729q2B = this.f.b(this.a.e);
        boolean z = j;
        if (!z && c0729q2B == null) {
            throw new AssertionError();
        }
        com.android.tools.r8.graph.o5 o5VarF = c0729q2B.f(this.a);
        if (o5VarF != null) {
            if (!z && !o5VarF.getAccessFlags().q()) {
                throw new AssertionError();
            }
            if (!z && !o5VarF.getAccessFlags().m()) {
                throw new AssertionError();
            }
            if (z || o5VarF.getAccessFlags().o()) {
                return o5VarF;
            }
            throw new AssertionError();
        }
        com.android.tools.r8.graph.W0.a aVarA = com.android.tools.r8.graph.W0.Y1().a(this.a).a(C0725p4.Q());
        C0684j2 c0684j2 = this.g;
        boolean z2 = this.h;
        EnumC0691k2 enumC0691k2 = this.i;
        C1071Ii c1071IiD = C1071Ii.a(this.f.a()).d(this.a);
        switch (enumC0691k2.ordinal()) {
            case 4:
                c1071IiD.b(c0684j2, z2);
                break;
            case 5:
            case 8:
                c1071IiD.d(c0684j2, z2);
                break;
            case 6:
                c1071IiD.a(c0684j2);
                break;
            case 7:
                c1071IiD.a(c0684j2, z2);
                break;
            default:
                throw new C3405y10();
        }
        com.android.tools.r8.graph.o5 o5Var = new com.android.tools.r8.graph.o5(c0729q2B, aVarA.a(c1071IiD.a()).c().a());
        c0729q2B.a(o5Var.e());
        consumer.accept(o5Var);
        return o5Var;
    }
}
