package com.android.tools.r8.shaking;

import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.utils.EnumC3891f;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class X2 extends Origin {
    final /* synthetic */ EnumC3891f e;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    X2(Origin origin, EnumC3891f enumC3891f) {
        super(origin);
        this.e = enumC3891f;
    }

    @Override // com.android.tools.r8.origin.Origin
    public final String part() {
        return "<SYNTHESIZED_FROM_API_LEVEL_" + this.e.d() + ">";
    }
}
