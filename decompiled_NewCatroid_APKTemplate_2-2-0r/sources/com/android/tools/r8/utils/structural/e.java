package com.android.tools.r8.utils.structural;

import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.naming.AbstractC3649o0;
import java.util.function.ToIntFunction;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public class e extends d {
    private final ToIntFunction c;

    public e(AbstractC3649o0 abstractC3649o0, ToIntFunction toIntFunction) {
        super(abstractC3649o0);
        this.c = toIntFunction;
    }

    @Override // com.android.tools.r8.utils.structural.c, com.android.tools.r8.utils.structural.a
    public final int a(C0759u2 c0759u2, C0759u2 c0759u3) {
        if (c0759u2 == c0759u3) {
            return 0;
        }
        return Integer.compare(this.c.applyAsInt(c0759u2), this.c.applyAsInt(c0759u3));
    }
}
