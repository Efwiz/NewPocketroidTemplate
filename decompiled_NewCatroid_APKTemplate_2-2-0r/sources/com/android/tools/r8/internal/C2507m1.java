package com.android.tools.r8.internal;

import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.m1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2507m1 implements AR {
    protected Object a;
    protected int b;

    public C2507m1(int i, Object obj) {
        this.a = obj;
        this.b = i;
    }

    @Override // java.util.Map.Entry
    public final boolean equals(Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        return entry.getValue() != null && (entry.getValue() instanceof Integer) && this.a == entry.getKey() && this.b == ((Integer) entry.getValue()).intValue();
    }

    @Override // com.android.tools.r8.internal.AR
    public final int getIntValue() {
        return this.b;
    }

    @Override // java.util.Map.Entry
    public final Object getKey() {
        return this.a;
    }

    @Override // java.util.Map.Entry
    public final Object getValue() {
        return Integer.valueOf(this.b);
    }

    @Override // java.util.Map.Entry
    public final int hashCode() {
        return System.identityHashCode(this.a) ^ this.b;
    }

    @Override // java.util.Map.Entry
    public final Object setValue(Object obj) {
        ((Integer) obj).intValue();
        throw new UnsupportedOperationException();
    }

    public final String toString() {
        return this.a + "->" + this.b;
    }
}
