package com.android.tools.r8.internal;

import java.io.Serializable;
import java.util.Comparator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ib, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1063Ib extends OK implements Serializable {
    final Comparator a;

    C1063Ib(Comparator comparator) {
        this.a = (Comparator) AbstractC3123uM.a(comparator);
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        return this.a.compare(obj, obj2);
    }

    @Override // java.util.Comparator
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof C1063Ib) {
            return this.a.equals(((C1063Ib) obj).a);
        }
        return false;
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    public final String toString() {
        return this.a.toString();
    }
}
