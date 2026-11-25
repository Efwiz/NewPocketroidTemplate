package com.android.tools.r8.internal;

import java.util.Map;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final enum KD extends LD {
    KD() {
        super("VALUE", 1, 0);
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return ((Map.Entry) obj).getValue();
    }
}
