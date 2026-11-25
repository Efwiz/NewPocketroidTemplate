package com.android.tools.r8.shaking;

import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.s, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3792s extends S2 {
    private final r r;

    protected C3792s(Origin origin, Position position, String str, List list, C3774o2 c3774o2, C3774o2 c3774o3, boolean z, I2 i2, AbstractC3833y2 abstractC3833y2, List list2, G3 g3, boolean z2, List list3, r rVar) {
        super(origin, position, str, list, c3774o2, c3774o3, z, i2, abstractC3833y2, list2, g3, z2, list3);
        this.r = rVar;
    }

    public static C3782q G() {
        return new C3782q();
    }

    @Override // com.android.tools.r8.shaking.S2
    final String F() {
        int iOrdinal = this.r.ordinal();
        if (iOrdinal == 0) {
            return "alwaysclassinline";
        }
        if (iOrdinal == 1) {
            return "neverclassinline";
        }
        throw new C3405y10("Unknown class inline type " + this.r);
    }

    public final r H() {
        return this.r;
    }

    @Override // com.android.tools.r8.shaking.S2
    public final C3792s q() {
        return this;
    }
}
