package com.android.tools.r8.jetbrains.kotlinx.metadata.jvm;

import com.android.tools.r8.internal.AbstractC0936Ds;
import com.android.tools.r8.internal.AbstractC2129h3;
import com.android.tools.r8.internal.C1283Px;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class r {
    public /* synthetic */ r(int i) {
        this();
    }

    public static final void a(int[] iArr) {
        int i;
        int[] iArr2 = KotlinClassMetadata.b;
        if (iArr.length < 2 || (i = iArr[0]) < 1 || (i <= 1 && iArr[1] < 4)) {
            throw new IllegalArgumentException(("This version of kotlinx-metadata-jvm doesn't support writing Kotlin metadata of version earlier than 1.4. Please change the version from " + AbstractC2129h3.b(iArr) + " to at least [1, 4].").toString());
        }
    }

    private r() {
    }

    public static KotlinClassMetadata.Class a(C1283Px c1283Px, int[] iArr) {
        AbstractC0936Ds.c(iArr, "metadataVersion");
        p pVar = new p();
        c1283Px.a(pVar);
        return pVar.a(iArr);
    }
}
