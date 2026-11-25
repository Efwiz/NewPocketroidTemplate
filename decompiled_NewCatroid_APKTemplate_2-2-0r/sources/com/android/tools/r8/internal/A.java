package com.android.tools.r8.internal;

import java.util.Map;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class A implements InterfaceC2806pp {
    protected int a;
    protected Object b;

    public A(int i, Object obj) {
        this.a = i;
        this.b = obj;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2806pp
    public final int a() {
        return this.a;
    }

    @Override // java.util.Map.Entry
    public boolean equals(Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        if (entry.getKey() == null || !(entry.getKey() instanceof Integer) || this.a != ((Integer) entry.getKey()).intValue()) {
            return false;
        }
        Object obj2 = this.b;
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
        return i ^ (obj == null ? 0 : obj.hashCode());
    }

    @Override // java.util.Map.Entry
    public Object setValue(Object obj) {
        throw new UnsupportedOperationException();
    }

    public String toString() {
        return this.a + "->" + this.b;
    }
}
