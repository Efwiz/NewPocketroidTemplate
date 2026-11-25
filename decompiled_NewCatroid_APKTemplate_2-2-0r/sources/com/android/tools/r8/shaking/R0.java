package com.android.tools.r8.shaking;

import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;
import java.util.List;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class R0 extends S2 {
    private final Q0 r;

    protected R0(Origin origin, Position position, String str, List list, C3774o2 c3774o2, C3774o2 c3774o3, boolean z, I2 i2, AbstractC3833y2 abstractC3833y2, List list2, G3 g3, boolean z2, List list3, Q0 q0) {
        super(origin, position, str, list, c3774o2, c3774o3, z, i2, abstractC3833y2, list2, g3, z2, list3);
        this.r = q0;
    }

    public static P0 G() {
        return new P0();
    }

    @Override // com.android.tools.r8.shaking.S2
    final String F() {
        int iOrdinal = this.r.ordinal();
        if (iOrdinal == 0) {
            return "alwaysinline";
        }
        if (iOrdinal == 1) {
            return "neverinline";
        }
        if (iOrdinal == 2) {
            return "neverclassinlinemethod";
        }
        if (iOrdinal == 3) {
            return "neversinglecaller";
        }
        throw new C3405y10("Unknown inline type " + this.r);
    }

    public final Q0 H() {
        return this.r;
    }
}
