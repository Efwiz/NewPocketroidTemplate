package com.android.tools.r8.utils;

import com.android.tools.r8.origin.Origin;
import com.huawei.hms.ml.language.common.utils.Constant;

/* JADX INFO: renamed from: com.android.tools.r8.utils.x1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public final class C3987x1 extends Origin {
    private final String e;

    public C3987x1(String str, Origin origin) {
        super(origin);
        this.e = str;
    }

    @Override // com.android.tools.r8.origin.Origin
    public final String part() {
        return "global(" + this.e + Constant.AFTER_QUTO;
    }
}
