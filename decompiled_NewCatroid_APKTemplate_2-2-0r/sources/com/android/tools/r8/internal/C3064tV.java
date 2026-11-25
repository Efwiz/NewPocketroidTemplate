package com.android.tools.r8.internal;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Objects;

/* JADX INFO: renamed from: com.android.tools.r8.internal.tV, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3064tV extends AbstractC2706oV {
    static final /* synthetic */ boolean g = true;
    private final AbstractC3121uK f;

    C3064tV(com.android.tools.r8.graph.Y0 y0, AbstractC3121uK abstractC3121uK) {
        super(y0);
        if (!g && abstractC3121uK.e()) {
            throw new AssertionError();
        }
        this.f = abstractC3121uK;
    }

    @Override // com.android.tools.r8.internal.B1
    public final boolean equals(Object obj) {
        if (obj == null || C3064tV.class != obj.getClass()) {
            return false;
        }
        C3064tV c3064tV = (C3064tV) obj;
        return this.d == c3064tV.d && this.f.equals(c3064tV.f);
    }

    @Override // com.android.tools.r8.internal.B1
    public final int hashCode() {
        return Objects.hash(this.d, this.f);
    }

    @Override // com.android.tools.r8.internal.B1
    public final int s() {
        return this.f.c();
    }

    @Override // com.android.tools.r8.internal.AbstractC2706oV, com.android.tools.r8.internal.B1
    public final AbstractC3121uK t() {
        return this.f;
    }

    @Override // com.android.tools.r8.internal.B1
    public final String toString() {
        return "SingleStatefulFieldValue(" + this.d.r0() + Constant.AFTER_QUTO;
    }

    @Override // com.android.tools.r8.internal.B1
    public final boolean u() {
        return this.f.d();
    }
}
