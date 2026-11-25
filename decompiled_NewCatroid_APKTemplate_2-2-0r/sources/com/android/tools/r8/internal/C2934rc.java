package com.android.tools.r8.internal;

import java.util.Collection;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.rc, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2934rc extends AbstractC1211Ni {
    final /* synthetic */ Set a;

    C2934rc(Set set) {
        this.a = set;
    }

    @Override // com.android.tools.r8.internal.AbstractC1183Mi
    protected final Object a() {
        return this.a;
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean contains(Object obj) {
        boolean zContains;
        if (obj == null) {
            return false;
        }
        Set set = this.a;
        int i = AbstractC3123uM.a;
        set.getClass();
        try {
            zContains = set.contains(obj);
        } catch (ClassCastException | NullPointerException unused) {
            zContains = false;
        }
        return zContains;
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean remove(Object obj) {
        boolean zRemove;
        if (obj == null) {
            return false;
        }
        Set set = this.a;
        int i = AbstractC3123uM.a;
        set.getClass();
        try {
            zRemove = set.remove(obj);
        } catch (ClassCastException | NullPointerException unused) {
            zRemove = false;
        }
        return zRemove;
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean removeAll(Collection collection) {
        int i = AbstractC3123uM.a;
        collection.getClass();
        return OU.a(this, collection);
    }
}
