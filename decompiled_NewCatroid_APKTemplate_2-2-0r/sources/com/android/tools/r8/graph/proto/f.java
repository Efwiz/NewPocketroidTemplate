package com.android.tools.r8.graph.proto;

import com.android.tools.r8.internal.AbstractC3324x;
import com.android.tools.r8.internal.InterfaceC1050Ho;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class f extends e {
    static final /* synthetic */ boolean b = true;
    private final InterfaceC1050Ho a;

    public f(AbstractC3324x abstractC3324x) {
        if (!b && abstractC3324x.e() != -1) {
            throw new AssertionError();
        }
        this.a = abstractC3324x;
    }

    @Override // com.android.tools.r8.graph.proto.e
    public final int b(int i) {
        int i2 = this.a.get(i);
        return i2 >= 0 ? i2 : i;
    }
}
