package com.android.tools.r8.internal;

import com.gaurav.avnc.vnc.XKeySym;
import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.iR, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2235iR implements YQ, Map.Entry {
    int a;
    final /* synthetic */ C2537mR b;

    C2235iR(C2537mR c2537mR, int i) {
        this.b = c2537mR;
        this.a = i;
    }

    @Override // java.util.Map.Entry
    public final boolean equals(Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        return this.b.a[this.a] == entry.getKey() && this.b.b[this.a] == ((Boolean) entry.getValue()).booleanValue();
    }

    @Override // com.android.tools.r8.internal.YQ
    public final boolean getBooleanValue() {
        return this.b.b[this.a];
    }

    @Override // java.util.Map.Entry
    public final Object getKey() {
        return this.b.a[this.a];
    }

    @Override // java.util.Map.Entry
    public final Object getValue() {
        return Boolean.valueOf(this.b.b[this.a]);
    }

    @Override // java.util.Map.Entry
    public final int hashCode() {
        return System.identityHashCode(this.b.a[this.a]) ^ (this.b.b[this.a] ? XKeySym.XK_kana_MA : XKeySym.XK_kana_YU);
    }

    @Override // java.util.Map.Entry
    public final Object setValue(Object obj) {
        boolean zBooleanValue = ((Boolean) obj).booleanValue();
        boolean[] zArr = this.b.b;
        int i = this.a;
        boolean z = zArr[i];
        zArr[i] = zBooleanValue;
        return Boolean.valueOf(z);
    }

    public final String toString() {
        return this.b.a[this.a] + "=>" + this.b.b[this.a];
    }
}
