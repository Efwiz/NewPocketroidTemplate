package com.android.tools.r8.shaking;

import com.android.tools.r8.AbstractC0403b;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;
import java.util.List;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class S3 extends S2 {
    private final int r;

    private S3(Origin origin, Position position, String str, AbstractC1639am abstractC1639am, C3774o2 c3774o2, C3774o2 c3774o3, boolean z, I2 i2, AbstractC3833y2 abstractC3833y2, AbstractC1639am abstractC1639am2, G3 g3, boolean z2, List list, int i) {
        super(origin, position, str, abstractC1639am, c3774o2, c3774o3, z, i2, abstractC3833y2, abstractC1639am2, g3, z2, list);
        this.r = i;
    }

    public static R3 G() {
        return new R3();
    }

    @Override // com.android.tools.r8.shaking.S2
    final String F() {
        int iB = AbstractC0403b.b(this.r);
        if (iB == 0) {
            return "reprocessmethod";
        }
        if (iB == 1) {
            return "neverreprocessmethod";
        }
        throw new C3405y10();
    }

    public final int H() {
        return this.r;
    }

    @Override // com.android.tools.r8.shaking.S2
    public final S3 v() {
        return this;
    }
}
