package com.android.tools.r8.internal;

import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.gJ, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2064gJ implements InterfaceC2454lJ, Map.Entry {
    int a;
    final /* synthetic */ C2375kJ b;

    C2064gJ(C2375kJ c2375kJ, int i) {
        this.b = c2375kJ;
        this.a = i;
    }

    @Override // java.util.Map.Entry
    public final boolean equals(Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        C2375kJ c2375kJ = this.b;
        if (!c2375kJ.f.a(c2375kJ.b[this.a], entry.getKey())) {
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
        return this.b.b[this.a];
    }

    @Override // java.util.Map.Entry
    public final Object getValue() {
        return this.b.c[this.a];
    }

    @Override // java.util.Map.Entry
    public final int hashCode() {
        C2375kJ c2375kJ = this.b;
        int iA = c2375kJ.f.a(c2375kJ.b[this.a]);
        Object obj = this.b.c[this.a];
        return iA ^ (obj == null ? 0 : obj.hashCode());
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
