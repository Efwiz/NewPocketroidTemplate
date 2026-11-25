package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.huawei.hms.ml.language.common.utils.Constant;

/* JADX INFO: renamed from: com.android.tools.r8.internal.En, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0963En implements InterfaceC0989Fn {
    static final /* synthetic */ boolean b = true;
    private final int a;

    C0963En(int i) {
        this.a = i;
    }

    @Override // com.android.tools.r8.internal.InterfaceC0989Fn
    /* JADX INFO: renamed from: a */
    public final InterfaceC0989Fn b(C0702m c0702m, AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2) {
        return this;
    }

    public final int b() {
        return this.a;
    }

    @Override // com.android.tools.r8.internal.InterfaceC0989Fn
    public final C0963En f() {
        return this;
    }

    @Override // com.android.tools.r8.internal.InterfaceC0989Fn
    public final boolean h() {
        return true;
    }

    public final String toString() {
        return "InstanceFieldArgumentInitializationInfo(argumentIndex=" + this.a + Constant.AFTER_QUTO;
    }

    @Override // com.android.tools.r8.internal.InterfaceC0989Fn
    public final InterfaceC0989Fn a(com.android.tools.r8.graph.proto.c cVar) {
        com.android.tools.r8.graph.proto.b bVarA = cVar.a(this.a);
        if (!bVarA.c()) {
            int iB = cVar.b(this.a);
            return iB != this.a ? new C0963En(iB) : this;
        }
        com.android.tools.r8.graph.proto.h hVarA = bVarA.a();
        if (hVarA.h()) {
            return hVarA.f();
        }
        if (b) {
            return C2351k10.b();
        }
        throw new AssertionError();
    }
}
