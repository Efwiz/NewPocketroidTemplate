package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.shaking.InterfaceC3680a0;
import com.android.tools.r8.utils.C3888e1;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Dz, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0943Dz implements InterfaceC3680a0 {
    private final EnumC2344jy a;
    private final EnumC2269iy b;
    private final List c;
    private final C0915Cz d;

    public C0943Dz(EnumC2344jy enumC2344jy, EnumC2269iy enumC2269iy, AbstractC1639am abstractC1639am, C0915Cz c0915Cz) {
        this.a = enumC2344jy;
        this.b = enumC2269iy;
        this.c = abstractC1639am;
        this.d = c0915Cz;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Consumer a(C0915Cz c0915Cz) {
        Objects.requireNonNull(c0915Cz);
        return new Cz$$ExternalSyntheticLambda2(c0915Cz);
    }

    @Override // com.android.tools.r8.shaking.InterfaceC3680a0
    public final void a(com.android.tools.r8.graph.Q0 q0) {
        C3888e1.a(this.c, new Function() { // from class: com.android.tools.r8.internal.Dz$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C0943Dz.a((C0915Cz) obj);
            }
        }, (Object) q0);
        this.d.a(q0);
    }

    final boolean a(Consumer consumer, C0702m c0702m) {
        final C2036fy c2036fy = (C2036fy) AbstractC1745cA.a(consumer, new C2036fy(this.a, this.b));
        return AbstractC1745cA.a(c0702m, this.c, c2036fy.e(), new Cz$$ExternalSyntheticLambda5()) | this.d.a(new Consumer() { // from class: com.android.tools.r8.internal.Dz$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                c2036fy.a((C2120gy) obj);
            }
        }, c0702m);
    }
}
