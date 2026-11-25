package com.android.tools.r8.shaking;

import com.android.tools.r8.internal.OU;
import java.util.Set;
import java.util.function.BiConsumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class J {
    private final Set a = OU.c();
    private final BiConsumer b;
    final /* synthetic */ L c;

    J(L l, BiConsumer biConsumer) {
        this.c = l;
        this.b = biConsumer;
    }
}
