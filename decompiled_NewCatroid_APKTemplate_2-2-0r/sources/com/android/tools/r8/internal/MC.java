package com.android.tools.r8.internal;

import java.util.Map;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class MC implements GC, Map.Entry {
    int a;
    final /* synthetic */ QC b;

    MC(QC qc, int i) {
        this.b = qc;
        this.a = i;
    }

    @Override // java.util.Map.Entry
    public final boolean equals(Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        return this.b.a[this.a] == ((Long) entry.getKey()).longValue() && this.b.b[this.a] == entry.getValue();
    }

    @Override // java.util.Map.Entry
    public final Object getKey() {
        return Long.valueOf(this.b.a[this.a]);
    }

    @Override // java.util.Map.Entry
    public final Object getValue() {
        return this.b.b[this.a];
    }

    @Override // java.util.Map.Entry
    public final int hashCode() {
        int iA = AbstractC3307wk.a(this.b.a[this.a]);
        Object obj = this.b.b[this.a];
        return iA ^ (obj == null ? 0 : System.identityHashCode(obj));
    }

    @Override // java.util.Map.Entry
    public final Object setValue(Object obj) {
        Object[] objArr = this.b.b;
        int i = this.a;
        Object obj2 = objArr[i];
        objArr[i] = obj;
        return obj2;
    }

    public final String toString() {
        return this.b.a[this.a] + "=>" + this.b.b[this.a];
    }
}
