package com.android.tools.r8.utils;

import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
final class U implements V {
    final /* synthetic */ Consumer a;

    U(Consumer consumer) {
        this.a = consumer;
    }

    @Override // com.android.tools.r8.utils.V
    public final V a(String str) {
        this.a.accept(str);
        return this;
    }
}
