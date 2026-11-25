package com.android.tools.r8.internal;

import java.util.Collection;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.gm, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class AbstractC2107gm extends AbstractC2178hm {
    AbstractC2107gm() {
    }

    @Override // com.android.tools.r8.internal.AbstractC2178hm, java.util.Map, java.util.SortedMap
    public final /* bridge */ /* synthetic */ Set entrySet() {
        return entrySet();
    }

    @Override // com.android.tools.r8.internal.AbstractC2178hm
    final AbstractC1048Hm k() {
        return new C2023fm(this);
    }

    @Override // com.android.tools.r8.internal.AbstractC2178hm, java.util.Map, java.util.SortedMap
    public final /* bridge */ /* synthetic */ Set keySet() {
        return keySet();
    }

    @Override // com.android.tools.r8.internal.AbstractC2178hm
    final AbstractC1048Hm l() {
        return new C2638nm(this);
    }

    @Override // com.android.tools.r8.internal.AbstractC2178hm
    final AbstractC1271Pl m() {
        return new C2872qm(this);
    }

    abstract AbstractC2963s10 u();

    @Override // com.android.tools.r8.internal.AbstractC2178hm, java.util.Map, java.util.SortedMap
    public final /* bridge */ /* synthetic */ Collection values() {
        return values();
    }
}
