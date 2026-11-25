package com.android.tools.r8.internal;

import com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.AbstractC3598b;

/* JADX INFO: renamed from: com.android.tools.r8.internal.hu, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2186hu extends AbstractC3598b implements InterfaceC1519Xx {
    private com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j b;

    public C2186hu() {
        super(0);
    }

    public final void a(InterfaceC2653ny interfaceC2653ny) {
        InterfaceC1546Yx interfaceC1546Yx = (InterfaceC1546Yx) interfaceC2653ny;
        if (!(interfaceC1546Yx instanceof AbstractC3598b)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        ((AbstractC3598b) interfaceC1546Yx).a(this.b);
    }

    public final void b(com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j jVar) {
        this.b = jVar;
    }

    public final com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j a() {
        return this.b;
    }

    @Override // com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.AbstractC3598b
    public final void a(com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j jVar) {
        this.b = jVar;
    }
}
