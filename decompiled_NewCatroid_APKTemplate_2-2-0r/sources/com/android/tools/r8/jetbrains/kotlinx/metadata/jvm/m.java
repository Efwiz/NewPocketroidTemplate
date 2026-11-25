package com.android.tools.r8.jetbrains.kotlinx.metadata.jvm;

import com.android.tools.r8.internal.C2576my;
import com.android.tools.r8.internal.C3321wx;
import com.android.tools.r8.internal.CS;
import com.android.tools.r8.internal.InterfaceC1090Iy;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class m implements InterfaceC1090Iy {
    public static final C2576my b = new C2576my(CS.a(m.class));
    private final m a;

    public m(m mVar) {
        this.a = mVar;
    }

    public final void a() {
        m mVar = this.a;
        if (mVar != null) {
            mVar.a();
        }
    }

    public abstract void a(C3321wx c3321wx);

    public abstract void a(boolean z);

    @Override // com.android.tools.r8.internal.InterfaceC2653ny
    public final C2576my getType() {
        return b;
    }

    public /* synthetic */ m() {
        this(null);
    }
}
