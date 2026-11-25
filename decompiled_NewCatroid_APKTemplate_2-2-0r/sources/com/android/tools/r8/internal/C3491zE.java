package com.android.tools.r8.internal;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.LinkedHashSet;

/* JADX INFO: renamed from: com.android.tools.r8.internal.zE, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C3491zE {
    private final Collection a;

    private C3491zE(Collection collection) {
        this.a = collection;
    }

    public Collection a() {
        return this.a;
    }

    public final byte[] b() {
        return com.android.tools.r8.utils.Y2.a(";", this.a).getBytes(StandardCharsets.UTF_8);
    }

    public static C3491zE a(LinkedHashSet linkedHashSet) {
        return new C3491zE(linkedHashSet);
    }
}
