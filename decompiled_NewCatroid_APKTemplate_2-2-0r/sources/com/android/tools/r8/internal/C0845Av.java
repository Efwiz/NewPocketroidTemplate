package com.android.tools.r8.internal;

import java.util.List;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Av, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C0845Av extends AbstractC0939Dv {
    private final Consumer a;

    public C0845Av(Consumer consumer) {
        super();
        this.a = consumer;
    }

    public final Object visitArray(List list, Object obj) {
        list.forEach(this.a);
        return this;
    }
}
