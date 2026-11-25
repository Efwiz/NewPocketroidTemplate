package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0722p1;

/* JADX INFO: renamed from: com.android.tools.r8.internal.kI, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2372kI {
    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ B7 a(I7 i7, C0722p1 c0722p1) {
        return new C3481z6(C3481z6.a.Add, EnumC2527mI.a(i7.T().I0()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ B7 b(I7 i7, C0722p1 c0722p1) {
        C0684j2 c0684j2T = i7.T();
        return new I7(184, c0722p1.a(c0722p1.j2, c0684j2T.h, c0684j2T.f), false);
    }

    public static InterfaceC1665b4 c() {
        return new InterfaceC1665b4() { // from class: com.android.tools.r8.internal.kI$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.internal.InterfaceC1665b4
            public final B7 a(I7 i7, C0722p1 c0722p1) {
                return AbstractC2372kI.b(i7, c0722p1);
            }
        };
    }

    public static InterfaceC1665b4 a() {
        return new C2296jI();
    }

    public static InterfaceC1665b4 b() {
        return new InterfaceC1665b4() { // from class: com.android.tools.r8.internal.kI$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.internal.InterfaceC1665b4
            public final B7 a(I7 i7, C0722p1 c0722p1) {
                return AbstractC2372kI.a(i7, c0722p1);
            }
        };
    }
}
