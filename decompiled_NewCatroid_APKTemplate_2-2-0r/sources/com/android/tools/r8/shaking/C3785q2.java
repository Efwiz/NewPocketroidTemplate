package com.android.tools.r8.shaking;

import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.q2, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3785q2 extends S2 {
    private C3785q2(Origin origin, Position position, String str, AbstractC1639am abstractC1639am, C3774o2 c3774o2, C3774o2 c3774o3, boolean z, I2 i2, AbstractC3833y2 abstractC3833y2, AbstractC1639am abstractC1639am2, G3 g3, boolean z2, List list) {
        super(origin, position, str, abstractC1639am, c3774o2, c3774o3, z, i2, abstractC3833y2, abstractC1639am2, g3, z2, list);
    }

    public static C3780p2 G() {
        return new C3780p2();
    }

    @Override // com.android.tools.r8.shaking.S2
    final String F() {
        return "assumemayhavesideeffects";
    }

    @Override // com.android.tools.r8.shaking.S2
    public final boolean p() {
        return true;
    }
}
