package com.android.tools.r8.internal;

import com.android.tools.r8.DataResourceConsumer;
import java.util.HashSet;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.xh, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3381xh {
    private final Set a;
    private final DataResourceConsumer b;

    public C3381xh(HashSet hashSet, DataResourceConsumer dataResourceConsumer) {
        this.a = hashSet;
        this.b = dataResourceConsumer;
    }

    public final DataResourceConsumer a() {
        return this.b;
    }

    public final Set b() {
        return this.a;
    }
}
