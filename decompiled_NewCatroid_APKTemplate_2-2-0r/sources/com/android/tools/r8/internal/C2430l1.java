package com.android.tools.r8.internal;

import com.gaurav.avnc.vnc.XKeySym;
import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.l1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2430l1 implements YQ {
    protected Object a;
    protected boolean b;

    public C2430l1(Object obj, boolean z) {
        this.a = obj;
        this.b = z;
    }

    @Override // java.util.Map.Entry
    public final boolean equals(Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        return entry.getValue() != null && (entry.getValue() instanceof Boolean) && this.a == entry.getKey() && this.b == ((Boolean) entry.getValue()).booleanValue();
    }

    @Override // com.android.tools.r8.internal.YQ
    public final boolean getBooleanValue() {
        return this.b;
    }

    @Override // java.util.Map.Entry
    public final Object getKey() {
        return this.a;
    }

    @Override // java.util.Map.Entry
    public final Object getValue() {
        return Boolean.valueOf(this.b);
    }

    @Override // java.util.Map.Entry
    public final int hashCode() {
        return System.identityHashCode(this.a) ^ (this.b ? XKeySym.XK_kana_MA : XKeySym.XK_kana_YU);
    }

    @Override // java.util.Map.Entry
    public final Object setValue(Object obj) {
        ((Boolean) obj).booleanValue();
        throw new UnsupportedOperationException();
    }

    public final String toString() {
        return this.a + "->" + this.b;
    }
}
