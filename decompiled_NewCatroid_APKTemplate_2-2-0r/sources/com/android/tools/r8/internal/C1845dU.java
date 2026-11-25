package com.android.tools.r8.internal;

import java.io.Serializable;

/* JADX INFO: renamed from: com.android.tools.r8.internal.dU, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1845dU extends OK implements Serializable {
    final OK a;

    C1845dU(OK ok) {
        this.a = (OK) AbstractC3123uM.a(ok);
    }

    @Override // com.android.tools.r8.internal.OK
    public final OK a() {
        return this.a;
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        return this.a.compare(obj2, obj);
    }

    @Override // java.util.Comparator
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof C1845dU) {
            return this.a.equals(((C1845dU) obj).a);
        }
        return false;
    }

    public final int hashCode() {
        return -this.a.hashCode();
    }

    public final String toString() {
        String strValueOf = String.valueOf(this.a);
        return new StringBuilder(strValueOf.length() + 10).append(strValueOf).append(".reverse()").toString();
    }
}
