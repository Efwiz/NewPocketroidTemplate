package com.android.tools.r8.jetbrains.kotlinx.metadata.jvm;

import com.android.tools.r8.internal.AbstractC1872dv;
import com.android.tools.r8.internal.AbstractC2696oL;
import com.android.tools.r8.internal.C1648av;
import com.android.tools.r8.internal.C1659b10;
import com.android.tools.r8.internal.C2772pL;
import com.android.tools.r8.internal.CO;
import java.io.IOException;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class s extends AbstractC2696oL {
    public s() {
        super(new C1648av());
    }

    public final KotlinClassMetadata.FileFacade a(int[] iArr) throws IOException {
        int[] iArr2 = KotlinClassMetadata.b;
        r.a(iArr);
        CO coG = g().g();
        if (!coG.a()) {
            throw new C1659b10();
        }
        C2772pL c2772pLA = AbstractC1872dv.a(coG, f());
        return new KotlinClassMetadata.FileFacade(i.a(2, iArr, (String[]) c2772pLA.a(), (String[]) c2772pLA.b(), null, 0, 48));
    }
}
