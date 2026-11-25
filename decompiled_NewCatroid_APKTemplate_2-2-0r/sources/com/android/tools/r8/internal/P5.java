package com.android.tools.r8.internal;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.Serializable;
import java.util.Arrays;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class P5 extends OK implements Serializable {
    final InterfaceC3010sj a;
    final OK b;

    P5(InterfaceC3010sj interfaceC3010sj, OK ok) {
        this.a = (InterfaceC3010sj) AbstractC3123uM.a(interfaceC3010sj);
        this.b = (OK) AbstractC3123uM.a(ok);
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        return this.b.compare(this.a.apply(obj), this.a.apply(obj2));
    }

    @Override // java.util.Comparator
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof P5)) {
            return false;
        }
        P5 p5 = (P5) obj;
        return this.a.equals(p5.a) && this.b.equals(p5.b);
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.a, this.b});
    }

    public final String toString() {
        String strValueOf = String.valueOf(this.b);
        String strValueOf2 = String.valueOf(this.a);
        return new StringBuilder(strValueOf2.length() + strValueOf.length() + 13).append(strValueOf).append(".onResultOf(").append(strValueOf2).append(Constant.AFTER_QUTO).toString();
    }
}
