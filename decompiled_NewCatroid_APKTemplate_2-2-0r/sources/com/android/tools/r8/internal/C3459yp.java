package com.android.tools.r8.internal;

import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.yp, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3459yp implements InterfaceC2806pp, Map.Entry {
    int a;
    final /* synthetic */ C0905Cp b;

    C3459yp(C0905Cp c0905Cp, int i) {
        this.b = c0905Cp;
        this.a = i;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2806pp
    public final int a() {
        return this.b.b[this.a];
    }

    @Override // java.util.Map.Entry
    public final boolean equals(Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        if (this.b.b[this.a] != ((Integer) entry.getKey()).intValue()) {
            return false;
        }
        Object obj2 = this.b.c[this.a];
        Object value = entry.getValue();
        if (obj2 == null) {
            if (value != null) {
                return false;
            }
        } else if (!obj2.equals(value)) {
            return false;
        }
        return true;
    }

    @Override // java.util.Map.Entry
    public final Object getKey() {
        return Integer.valueOf(this.b.b[this.a]);
    }

    @Override // java.util.Map.Entry
    public final Object getValue() {
        return this.b.c[this.a];
    }

    @Override // java.util.Map.Entry
    public final int hashCode() {
        C0905Cp c0905Cp = this.b;
        int[] iArr = c0905Cp.b;
        int i = this.a;
        int i2 = iArr[i];
        Object obj = c0905Cp.c[i];
        return (obj == null ? 0 : obj.hashCode()) ^ i2;
    }

    @Override // java.util.Map.Entry
    public final Object setValue(Object obj) {
        Object[] objArr = this.b.c;
        int i = this.a;
        Object obj2 = objArr[i];
        objArr[i] = obj;
        return obj2;
    }

    public final String toString() {
        return this.b.b[this.a] + "=>" + this.b.c[this.a];
    }
}
