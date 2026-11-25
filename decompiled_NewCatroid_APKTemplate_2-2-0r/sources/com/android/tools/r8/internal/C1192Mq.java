package com.android.tools.r8.internal;

import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Mq, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1192Mq implements InterfaceC0934Dq, Map.Entry {
    int a;
    final /* synthetic */ C1303Qq b;

    C1192Mq(C1303Qq c1303Qq, int i) {
        this.b = c1303Qq;
        this.a = i;
    }

    @Override // com.android.tools.r8.internal.InterfaceC0934Dq
    public final int a() {
        return this.b.b[this.a];
    }

    @Override // java.util.Map.Entry
    public final boolean equals(Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        return this.b.b[this.a] == ((Integer) entry.getKey()).intValue() && this.b.c[this.a] == entry.getValue();
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
        C1303Qq c1303Qq = this.b;
        int[] iArr = c1303Qq.b;
        int i = this.a;
        int i2 = iArr[i];
        Object obj = c1303Qq.c[i];
        return (obj == null ? 0 : System.identityHashCode(obj)) ^ i2;
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
