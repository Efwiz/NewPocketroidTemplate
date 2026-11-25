package com.android.tools.r8.internal;

import java.util.Map;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class HR implements AR, Map.Entry {
    int a;
    final /* synthetic */ MR b;

    HR(MR mr, int i) {
        this.b = mr;
        this.a = i;
    }

    @Override // java.util.Map.Entry
    public final boolean equals(Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        return this.b.b[this.a] == entry.getKey() && this.b.c[this.a] == ((Integer) entry.getValue()).intValue();
    }

    @Override // com.android.tools.r8.internal.AR
    public final int getIntValue() {
        return this.b.c[this.a];
    }

    @Override // java.util.Map.Entry
    public final Object getKey() {
        return this.b.b[this.a];
    }

    @Override // java.util.Map.Entry
    public final Object getValue() {
        return Integer.valueOf(this.b.c[this.a]);
    }

    @Override // java.util.Map.Entry
    public final int hashCode() {
        return System.identityHashCode(this.b.b[this.a]) ^ this.b.c[this.a];
    }

    @Override // java.util.Map.Entry
    public final Object setValue(Object obj) {
        int iIntValue = ((Integer) obj).intValue();
        int[] iArr = this.b.c;
        int i = this.a;
        int i2 = iArr[i];
        iArr[i] = iIntValue;
        return Integer.valueOf(i2);
    }

    public final String toString() {
        return this.b.b[this.a] + "=>" + this.b.c[this.a];
    }
}
