package com.android.tools.r8.internal;

import java.util.Map;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class UR implements OR, Map.Entry {
    int a;
    final /* synthetic */ YR b;

    UR(YR yr, int i) {
        this.b = yr;
        this.a = i;
    }

    @Override // java.util.Map.Entry
    public final boolean equals(Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        return this.b.a[this.a] == entry.getKey() && this.b.b[this.a] == ((Long) entry.getValue()).longValue();
    }

    @Override // java.util.Map.Entry
    public final Object getKey() {
        return this.b.a[this.a];
    }

    @Override // java.util.Map.Entry
    public final Object getValue() {
        return Long.valueOf(this.b.b[this.a]);
    }

    @Override // java.util.Map.Entry
    public final int hashCode() {
        return System.identityHashCode(this.b.a[this.a]) ^ AbstractC3307wk.a(this.b.b[this.a]);
    }

    @Override // java.util.Map.Entry
    public final Object setValue(Object obj) {
        long jLongValue = ((Long) obj).longValue();
        long[] jArr = this.b.b;
        int i = this.a;
        long j = jArr[i];
        jArr[i] = jLongValue;
        return Long.valueOf(j);
    }

    public final String toString() {
        return this.b.a[this.a] + "=>" + this.b.b[this.a];
    }
}
