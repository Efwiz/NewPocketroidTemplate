package com.android.tools.r8.internal;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class OB {
    static final /* synthetic */ boolean c = true;
    private final Map a;
    private final InterfaceC0992Fq b;

    public OB(HashMap map, C1303Qq c1303Qq) {
        boolean z = c;
        if (!z && map.isEmpty()) {
            throw new AssertionError();
        }
        if (!z && c1303Qq.isEmpty()) {
            throw new AssertionError();
        }
        this.a = map;
        this.b = c1303Qq;
    }

    public final void a(BiConsumer biConsumer) {
        this.a.forEach(biConsumer);
    }
}
