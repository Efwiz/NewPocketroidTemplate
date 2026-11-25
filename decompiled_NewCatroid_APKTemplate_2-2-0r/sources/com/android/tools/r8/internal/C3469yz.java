package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.AbstractC3598b;
import com.android.tools.r8.utils.C3888e1;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.yz, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3469yz implements InterfaceC1894eA {
    private final int a;
    private final List b;
    private final C3111uA c;
    private final C1148Kz d;

    private C3469yz(int i, AbstractC1639am abstractC1639am, C3111uA c3111uA, C1148Kz c1148Kz) {
        this.a = i;
        this.b = abstractC1639am;
        this.c = c3111uA;
        this.d = c1148Kz;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(C1493Wx c1493Wx, com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j jVar) {
        AbstractC0936Ds.c(c1493Wx, "<this>");
        ((C2186hu) c1493Wx.a(AbstractC3598b.a)).b(jVar);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1285Pz
    public final boolean h() {
        return true;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1285Pz
    public final C3469yz i() {
        return this;
    }

    public static C3469yz a(C1493Wx c1493Wx, C0722p1 c0722p1, com.android.tools.r8.utils.O2 o2, boolean z) {
        return new C3469yz(c1493Wx.c(), C3042tA.a(c1493Wx.d(), c0722p1, o2), C3111uA.a(c1493Wx.e()), z ? C1148Kz.a(com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.d.a(c1493Wx), c0722p1) : null);
    }

    final boolean a(C1283Px c1283Px, com.android.tools.r8.graph.W0 w0, C0702m c0702m) {
        final C1493Wx c1493Wx = new C1493Wx(this.a);
        C1148Kz c1148Kz = this.d;
        boolean zA = (c1148Kz != null ? c1148Kz.a(new Consumer() { // from class: com.android.tools.r8.internal.yz$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C3469yz.a(c1493Wx, (com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j) obj);
            }
        }, w0, c0702m) : false) | AbstractC1745cA.a(c0702m, this.b, c1493Wx.d(), new Iz$$ExternalSyntheticLambda13());
        C3111uA c3111uA = this.c;
        ArrayList arrayListE = c1493Wx.e();
        Objects.requireNonNull(arrayListE);
        boolean zA2 = zA | c3111uA.a(new Iz$$ExternalSyntheticLambda2(arrayListE));
        c1283Px.g().add(c1493Wx);
        return zA2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Consumer a(C3042tA c3042tA) {
        Objects.requireNonNull(c3042tA);
        return new Iz$$ExternalSyntheticLambda6(c3042tA);
    }

    @Override // com.android.tools.r8.shaking.InterfaceC3680a0
    public final void a(com.android.tools.r8.graph.Q0 q0) {
        C3888e1.a(this.b, new Function() { // from class: com.android.tools.r8.internal.yz$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C3469yz.a((C3042tA) obj);
            }
        }, (Object) q0);
        C1148Kz c1148Kz = this.d;
        if (c1148Kz != null) {
            c1148Kz.a(q0);
        }
    }
}
