package com.android.tools.r8.internal;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class LU extends AbstractSet {
    final /* synthetic */ int a;
    final /* synthetic */ AbstractC2178hm b;

    LU(int i, AbstractC2178hm abstractC2178hm) {
        this.a = i;
        this.b = abstractC2178hm;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        if (!(obj instanceof Set)) {
            return false;
        }
        Set set = (Set) obj;
        return set.size() == this.a && this.b.keySet().containsAll(set);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Iterator iterator() {
        return new KU(this);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return AbstractC1112Jr.a(this.b.size(), this.a);
    }

    @Override // java.util.AbstractCollection
    public final String toString() {
        String strValueOf = String.valueOf(this.b.keySet());
        return new StringBuilder(strValueOf.length() + 32).append("Sets.combinations(").append(strValueOf).append(", ").append(this.a).append(Constant.AFTER_QUTO).toString();
    }
}
