package com.android.tools.r8.utils.structural;

import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.naming.AbstractC3649o0;
import java.util.function.ToIntFunction;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public class g extends e {
    private final ToIntFunction d;

    public g(AbstractC3649o0 abstractC3649o0, ToIntFunction toIntFunction, ToIntFunction toIntFunction2) {
        super(abstractC3649o0, toIntFunction);
        this.d = toIntFunction2;
    }

    @Override // com.android.tools.r8.utils.structural.d, com.android.tools.r8.utils.structural.a
    public final int a(C0767v2 c0767v2, C0767v2 c0767v3) {
        if (c0767v2 == c0767v3) {
            return 0;
        }
        return Integer.compare(this.d.applyAsInt(c0767v2), this.d.applyAsInt(c0767v3));
    }
}
