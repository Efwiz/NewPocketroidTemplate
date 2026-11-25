package com.android.tools.r8.internal;

import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.mm, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class AbstractC2562mm extends AbstractC1048Hm {
    AbstractC2562mm() {
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        Object obj2 = l().get(entry.getKey());
        return obj2 != null && obj2.equals(entry.getValue());
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl
    final boolean e() {
        return l().o();
    }

    @Override // com.android.tools.r8.internal.AbstractC1048Hm, java.util.Collection, java.util.Set
    public int hashCode() {
        return l().hashCode();
    }

    @Override // com.android.tools.r8.internal.AbstractC1048Hm
    boolean j() {
        AbstractC2178hm abstractC2178hmL = l();
        abstractC2178hmL.getClass();
        return abstractC2178hmL instanceof LS;
    }

    abstract AbstractC2178hm l();

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return l().size();
    }
}
