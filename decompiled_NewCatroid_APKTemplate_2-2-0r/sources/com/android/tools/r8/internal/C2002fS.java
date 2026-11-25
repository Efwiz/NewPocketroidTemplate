package com.android.tools.r8.internal;

import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.fS, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2002fS implements ZR, Map.Entry {
    int a;
    final /* synthetic */ C2306jS b;

    C2002fS(C2306jS c2306jS, int i) {
        this.b = c2306jS;
        this.a = i;
    }

    @Override // java.util.Map.Entry
    public final boolean equals(Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        return this.b.a[this.a] == entry.getKey() && this.b.b[this.a] == entry.getValue();
    }

    @Override // java.util.Map.Entry
    public final Object getKey() {
        return this.b.a[this.a];
    }

    @Override // java.util.Map.Entry
    public final Object getValue() {
        return this.b.b[this.a];
    }

    @Override // java.util.Map.Entry
    public final int hashCode() {
        int iIdentityHashCode = System.identityHashCode(this.b.a[this.a]);
        Object obj = this.b.b[this.a];
        return iIdentityHashCode ^ (obj == null ? 0 : System.identityHashCode(obj));
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
