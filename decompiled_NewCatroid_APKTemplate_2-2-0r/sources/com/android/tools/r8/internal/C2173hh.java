package com.android.tools.r8.internal;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.Serializable;

/* JADX INFO: renamed from: com.android.tools.r8.internal.hh, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2173hh implements Serializable {
    private final AbstractC2251ih a;
    private final Object b;

    /* synthetic */ C2173hh(AbstractC2251ih abstractC2251ih, Object obj, int i) {
        this(abstractC2251ih, obj);
    }

    public final Object a() {
        return this.b;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof C2173hh)) {
            return false;
        }
        C2173hh c2173hh = (C2173hh) obj;
        if (this.a.equals(c2173hh.a)) {
            return this.a.b(this.b, c2173hh.b);
        }
        return false;
    }

    public final int hashCode() {
        return this.a.b(this.b);
    }

    public final String toString() {
        String strValueOf = String.valueOf(this.a);
        String strValueOf2 = String.valueOf(this.b);
        return new StringBuilder(strValueOf2.length() + strValueOf.length() + 7).append(strValueOf).append(".wrap(").append(strValueOf2).append(Constant.AFTER_QUTO).toString();
    }

    private C2173hh(AbstractC2251ih abstractC2251ih, Object obj) {
        this.a = (AbstractC2251ih) AbstractC3123uM.a(abstractC2251ih);
        this.b = obj;
    }
}
