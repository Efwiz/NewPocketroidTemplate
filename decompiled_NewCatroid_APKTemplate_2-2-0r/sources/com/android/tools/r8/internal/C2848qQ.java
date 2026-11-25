package com.android.tools.r8.internal;

import com.huawei.hms.ml.language.common.utils.Constant;

/* JADX INFO: renamed from: com.android.tools.r8.internal.qQ, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2848qQ extends AbstractC3287wQ {
    static final /* synthetic */ boolean D = true;
    private final com.android.tools.r8.graph.Y0 A;
    private final int B;
    private final com.android.tools.r8.graph.Y0 C;

    public C2848qQ(com.android.tools.r8.graph.Y0 y0) {
        this.A = null;
        this.B = -1;
        this.C = y0;
    }

    @Override // com.android.tools.r8.internal.AbstractC3287wQ
    public final Object a() {
        return C2848qQ.class;
    }

    public final String toString() {
        com.android.tools.r8.graph.Y0 y0 = this.A;
        if (y0 != null) {
            return "MissingInstanceFieldValueForEnumInstance(enum field=" + y0.r0() + ", instance field=" + this.C.r0() + Constant.AFTER_QUTO;
        }
        int i = this.B;
        if (i == -1) {
            return "MissingInstanceFieldValueForEnumInstance(Cannot resolve instance field=" + this.C.r0() + Constant.AFTER_QUTO;
        }
        if (!D && i < 0) {
            throw new AssertionError();
        }
        return "MissingInstanceFieldValueForEnumInstance(ordinal=" + i + ", instance field=" + this.C.r0() + Constant.AFTER_QUTO;
    }

    public C2848qQ(com.android.tools.r8.graph.Y0 y0, com.android.tools.r8.graph.Y0 y1) {
        this.A = y1;
        this.B = -1;
        this.C = y0;
    }

    public C2848qQ(int i, com.android.tools.r8.graph.Y0 y0) {
        this.A = null;
        this.B = i;
        this.C = y0;
    }
}
