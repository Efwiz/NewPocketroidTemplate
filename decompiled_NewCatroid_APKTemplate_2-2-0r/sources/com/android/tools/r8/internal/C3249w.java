package com.android.tools.r8.internal;

import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.w, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3249w implements InterfaceC0990Fo {
    protected int a;
    protected int b;

    public C3249w(int i, int i2) {
        this.a = i;
        this.b = i2;
    }

    @Override // com.android.tools.r8.internal.InterfaceC0990Fo
    public final int a() {
        return this.a;
    }

    @Override // java.util.Map.Entry
    public final boolean equals(Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        return entry.getKey() != null && (entry.getKey() instanceof Integer) && entry.getValue() != null && (entry.getValue() instanceof Integer) && this.a == ((Integer) entry.getKey()).intValue() && this.b == ((Integer) entry.getValue()).intValue();
    }

    @Override // com.android.tools.r8.internal.InterfaceC0990Fo
    public final int getIntValue() {
        return this.b;
    }

    @Override // java.util.Map.Entry
    public final Object getKey() {
        return Integer.valueOf(this.a);
    }

    @Override // java.util.Map.Entry
    public final Object getValue() {
        return Integer.valueOf(this.b);
    }

    @Override // java.util.Map.Entry
    public final int hashCode() {
        return this.a ^ this.b;
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
