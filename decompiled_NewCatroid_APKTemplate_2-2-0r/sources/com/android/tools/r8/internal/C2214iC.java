package com.android.tools.r8.internal;

import com.android.dx.rop.code.RegisterSpec;
import com.huawei.hms.ml.language.common.utils.Constant;

/* JADX INFO: renamed from: com.android.tools.r8.internal.iC, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2214iC {
    static final /* synthetic */ boolean b = true;
    private final int a;

    private C2214iC(int i) {
        this.a = i;
    }

    public final boolean a() {
        return !(this.a < 0);
    }

    public final boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        return (obj instanceof C2214iC) && this.a == ((C2214iC) obj).a;
    }

    public final int hashCode() {
        return this.a;
    }

    public final String toString() {
        int i = this.a;
        if (i >= 0) {
            if (!b && !a()) {
                throw new AssertionError();
            }
            return RegisterSpec.PREFIX + this.a;
        }
        boolean z = b;
        if (!z && i >= 0) {
            throw new AssertionError();
        }
        int i2 = (Integer.MAX_VALUE & i) >> 16;
        if (!z && i >= 0) {
            throw new AssertionError();
        }
        return "phi(" + i2 + "," + (i & 65535) + Constant.AFTER_QUTO;
    }
}
