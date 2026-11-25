package com.android.tools.r8.internal;

import java.util.Map;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class F implements InterfaceC0934Dq {
    protected int a;
    protected Object b;

    public F(int i, Object obj) {
        this.a = i;
        this.b = obj;
    }

    @Override // com.android.tools.r8.internal.InterfaceC0934Dq
    public final int a() {
        return this.a;
    }

    @Override // java.util.Map.Entry
    public boolean equals(Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        return entry.getKey() != null && (entry.getKey() instanceof Integer) && this.a == ((Integer) entry.getKey()).intValue() && this.b == entry.getValue();
    }

    @Override // java.util.Map.Entry
    public final Object getKey() {
        return Integer.valueOf(this.a);
    }

    @Override // java.util.Map.Entry
    public final Object getValue() {
        return this.b;
    }

    @Override // java.util.Map.Entry
    public int hashCode() {
        int i = this.a;
        Object obj = this.b;
        return i ^ (obj == null ? 0 : System.identityHashCode(obj));
    }

    @Override // java.util.Map.Entry
    public Object setValue(Object obj) {
        throw new UnsupportedOperationException();
    }

    public String toString() {
        return this.a + "->" + this.b;
    }
}
