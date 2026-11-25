package com.android.tools.r8.internal;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.Serializable;

/* JADX INFO: renamed from: com.android.tools.r8.internal.hG, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2143hG extends r implements Serializable {
    static final InterfaceC3384xk b = new C2143hG(0);
    private final int a;

    static {
        new C2143hG(AbstractC0860Bk.a);
    }

    C2143hG(int i) {
        this.a = i;
    }

    @Override // com.android.tools.r8.internal.InterfaceC3384xk
    public final InterfaceC3524zk a() {
        return new C2061gG(this.a);
    }

    public final boolean equals(Object obj) {
        return (obj instanceof C2143hG) && this.a == ((C2143hG) obj).a;
    }

    public final int hashCode() {
        return C2143hG.class.hashCode() ^ this.a;
    }

    public final String toString() {
        return new StringBuilder(32).append("Hashing.murmur3_128(").append(this.a).append(Constant.AFTER_QUTO).toString();
    }
}
