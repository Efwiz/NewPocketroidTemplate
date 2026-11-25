package com.android.tools.r8.utils.structural;

import com.android.tools.r8.internal.AbstractC3154uk;
import com.android.tools.r8.internal.InterfaceC3524zk;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public final class m implements n {
    private final InterfaceC3524zk a;

    public m(InterfaceC3524zk interfaceC3524zk) {
        this.a = interfaceC3524zk;
    }

    @Override // com.android.tools.r8.utils.structural.n
    public final void a(boolean z) {
        this.a.a(z);
    }

    public final AbstractC3154uk c() {
        return this.a.a();
    }

    @Override // com.android.tools.r8.utils.structural.n
    public final void a(int i) {
        this.a.a(i);
    }

    @Override // com.android.tools.r8.utils.structural.n
    public final void a(float f) {
        this.a.a(f);
    }

    @Override // com.android.tools.r8.utils.structural.n
    public final void a(long j) {
        this.a.a(j);
    }

    @Override // com.android.tools.r8.utils.structural.n
    public final void a(double d) {
        this.a.a(d);
    }

    @Override // com.android.tools.r8.utils.structural.n
    public final void a(byte[] bArr) {
        this.a.a(bArr);
    }

    @Override // com.android.tools.r8.utils.structural.n
    public final String a() {
        return this.a.a().toString();
    }
}
