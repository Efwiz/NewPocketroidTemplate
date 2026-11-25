package com.android.tools.r8.internal;

import java.util.Collection;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Nl, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC1214Nl extends AbstractC2178hm implements G4 {
    AbstractC1214Nl() {
    }

    @Override // com.android.tools.r8.internal.G4
    public final Object a(Object obj, Object obj2) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.tools.r8.internal.AbstractC2178hm
    final AbstractC1271Pl m() {
        throw new AssertionError("should never be called");
    }

    @Override // com.android.tools.r8.internal.AbstractC2178hm
    /* JADX INFO: renamed from: t */
    public final AbstractC1271Pl values() {
        return g().keySet();
    }

    @Override // com.android.tools.r8.internal.G4
    /* JADX INFO: renamed from: u, reason: merged with bridge method [inline-methods] */
    public abstract AbstractC1214Nl g();

    @Override // com.android.tools.r8.internal.AbstractC2178hm, java.util.Map, java.util.SortedMap
    public final Collection values() {
        return g().keySet();
    }

    @Override // com.android.tools.r8.internal.AbstractC2178hm, java.util.Map, java.util.SortedMap
    public final Set values() {
        return g().keySet();
    }
}
