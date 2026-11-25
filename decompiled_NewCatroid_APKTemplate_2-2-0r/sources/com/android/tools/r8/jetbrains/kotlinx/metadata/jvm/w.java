package com.android.tools.r8.jetbrains.kotlinx.metadata.jvm;

import com.android.tools.r8.internal.AbstractC1872dv;
import com.android.tools.r8.internal.C1648av;
import com.android.tools.r8.internal.C1659b10;
import com.android.tools.r8.internal.C2772pL;
import com.android.tools.r8.internal.C3285wO;
import com.android.tools.r8.internal.C3354xO;
import com.android.tools.r8.internal.RA;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class w extends RA {
    public w() {
        super(new C1648av());
    }

    public final KotlinClassMetadata.SyntheticClass a(int[] iArr) {
        C3354xO c3354xOG;
        int[] iArr2 = KotlinClassMetadata.b;
        r.a(iArr);
        C3285wO c3285wOC = c();
        if (c3285wOC != null) {
            c3354xOG = c3285wOC.g();
            if (!c3354xOG.a()) {
                throw new C1659b10();
            }
        } else {
            c3354xOG = null;
        }
        C2772pL c2772pLA = c3354xOG != null ? AbstractC1872dv.a(c3354xOG, b()) : new C2772pL(new String[0], new String[0]);
        return new KotlinClassMetadata.SyntheticClass(i.a(3, iArr, (String[]) c2772pLA.a(), (String[]) c2772pLA.b(), null, 0, 48));
    }
}
