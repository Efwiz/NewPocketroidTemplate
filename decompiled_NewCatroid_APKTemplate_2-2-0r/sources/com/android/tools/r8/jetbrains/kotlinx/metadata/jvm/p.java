package com.android.tools.r8.jetbrains.kotlinx.metadata.jvm;

import com.android.tools.r8.internal.AbstractC0936Ds;
import com.android.tools.r8.internal.AbstractC1623ab;
import com.android.tools.r8.internal.AbstractC1872dv;
import com.android.tools.r8.internal.C1648av;
import com.android.tools.r8.internal.C1659b10;
import com.android.tools.r8.internal.C1761cO;
import com.android.tools.r8.internal.C2772pL;
import java.io.IOException;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class p extends AbstractC1623ab {
    public p() {
        super(new C1648av());
    }

    public final KotlinClassMetadata.Class a(int[] iArr) throws IOException {
        AbstractC0936Ds.c(iArr, "metadataVersion");
        int[] iArr2 = KotlinClassMetadata.b;
        r.a(iArr);
        C1761cO c1761cOG = g().g();
        if (!c1761cOG.a()) {
            throw new C1659b10();
        }
        C2772pL c2772pLA = AbstractC1872dv.a(c1761cOG, f());
        return new KotlinClassMetadata.Class(i.a(1, iArr, (String[]) c2772pLA.a(), (String[]) c2772pLA.b(), null, 0, 48));
    }
}
