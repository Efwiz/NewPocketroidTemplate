package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.internal.DH;
import java.util.Objects;
import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class g4 {
    private final Set a;
    private final Set b;
    private final Set c;

    public g4(Set set, Set set2, Set set3) {
        this.a = set;
        this.b = set2;
        this.c = set3;
    }

    public final boolean a(C0729q2 c0729q2) {
        return this.a.contains(c0729q2.d) || this.b.contains(c0729q2.d) || this.c.contains(c0729q2.d);
    }

    public final g4 a(DH dh) {
        Set set = this.a;
        Objects.requireNonNull(dh);
        return new g4(com.android.tools.r8.utils.T2.a(set, new g4$$ExternalSyntheticLambda0(dh)), com.android.tools.r8.utils.T2.a(this.b, new g4$$ExternalSyntheticLambda0(dh)), com.android.tools.r8.utils.T2.a(this.c, new g4$$ExternalSyntheticLambda0(dh)));
    }
}
