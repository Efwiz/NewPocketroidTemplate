package com.android.tools.r8.internal;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.Serializable;

/* JADX INFO: renamed from: com.android.tools.r8.internal.wM, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3275wM implements InterfaceC3205vM, Serializable {
    final InterfaceC3205vM a;

    C3275wM(InterfaceC3205vM interfaceC3205vM) {
        this.a = (InterfaceC3205vM) AbstractC3123uM.a(interfaceC3205vM);
    }

    @Override // com.android.tools.r8.internal.InterfaceC3205vM
    public final boolean apply(Object obj) {
        return !this.a.apply(obj);
    }

    @Override // com.android.tools.r8.internal.InterfaceC3205vM
    public final boolean equals(Object obj) {
        if (obj instanceof C3275wM) {
            return this.a.equals(((C3275wM) obj).a);
        }
        return false;
    }

    public final int hashCode() {
        return ~this.a.hashCode();
    }

    public final String toString() {
        String strValueOf = String.valueOf(this.a);
        return new StringBuilder(strValueOf.length() + 16).append("Predicates.not(").append(strValueOf).append(Constant.AFTER_QUTO).toString();
    }
}
