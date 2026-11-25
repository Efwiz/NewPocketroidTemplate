package com.android.tools.r8.internal;

import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ao, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C0838Ao implements InterfaceC0990Fo, Map.Entry {
    int a;
    final /* synthetic */ C0964Eo b;

    C0838Ao(C0964Eo c0964Eo, int i) {
        this.b = c0964Eo;
        this.a = i;
    }

    @Override // com.android.tools.r8.internal.InterfaceC0990Fo
    public final int a() {
        return this.b.b[this.a];
    }

    @Override // java.util.Map.Entry
    public final boolean equals(Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        return this.b.b[this.a] == ((Integer) entry.getKey()).intValue() && this.b.c[this.a] == ((Integer) entry.getValue()).intValue();
    }

    @Override // com.android.tools.r8.internal.InterfaceC0990Fo
    public final int getIntValue() {
        return this.b.c[this.a];
    }

    @Override // java.util.Map.Entry
    public final Object getKey() {
        return Integer.valueOf(this.b.b[this.a]);
    }

    @Override // java.util.Map.Entry
    public final Object getValue() {
        return Integer.valueOf(this.b.c[this.a]);
    }

    @Override // java.util.Map.Entry
    public final int hashCode() {
        C0964Eo c0964Eo = this.b;
        int[] iArr = c0964Eo.b;
        int i = this.a;
        return c0964Eo.c[i] ^ iArr[i];
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
