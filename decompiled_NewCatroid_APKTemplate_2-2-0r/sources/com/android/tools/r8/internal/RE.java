package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Objects;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class RE {
    private final C0684j2 a;
    private final int b;

    public RE(int i, C0684j2 c0684j2) {
        this.a = c0684j2;
        this.b = i;
    }

    public final int a() {
        return this.b;
    }

    public final C0684j2 b() {
        return this.a;
    }

    public final boolean equals(Object obj) {
        if (obj == null || RE.class != obj.getClass()) {
            return false;
        }
        RE re = (RE) obj;
        return this.a == re.a && this.b == re.b;
    }

    public final int hashCode() {
        return Objects.hash(this.a, Integer.valueOf(this.b));
    }

    public final String toString() {
        return "MethodParameter(" + this.a + ", " + this.b + Constant.AFTER_QUTO;
    }
}
