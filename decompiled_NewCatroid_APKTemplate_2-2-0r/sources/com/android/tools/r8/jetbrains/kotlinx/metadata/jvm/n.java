package com.android.tools.r8.jetbrains.kotlinx.metadata.jvm;

import com.android.tools.r8.internal.C2576my;
import com.android.tools.r8.internal.C3321wx;
import com.android.tools.r8.internal.CS;
import com.android.tools.r8.internal.InterfaceC1173Ly;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class n implements InterfaceC1173Ly {
    public static final C2576my b = new C2576my(CS.a(n.class));
    private final n a;

    public n(n nVar) {
        this.a = nVar;
    }

    public final void a() {
        n nVar = this.a;
        if (nVar != null) {
            nVar.a();
        }
    }

    public abstract void a(C3321wx c3321wx);

    @Override // com.android.tools.r8.internal.InterfaceC2653ny
    public final C2576my getType() {
        return b;
    }

    public /* synthetic */ n() {
        this(null);
    }
}
