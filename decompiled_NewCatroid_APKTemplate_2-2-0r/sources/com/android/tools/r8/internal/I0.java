package com.android.tools.r8.internal;

import com.gaurav.avnc.vnc.XKeySym;
import java.util.Map;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class I0 implements Map.Entry {
    protected Object a;
    protected boolean b;

    public I0(Object obj, boolean z) {
        this.a = obj;
        this.b = z;
    }

    @Override // java.util.Map.Entry
    public final boolean equals(Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        if (entry.getValue() == null || !(entry.getValue() instanceof Boolean)) {
            return false;
        }
        Object obj2 = this.a;
        if (obj2 == null) {
            if (entry.getKey() != null) {
                return false;
            }
        } else if (!obj2.equals(entry.getKey())) {
            return false;
        }
        return this.b == ((Boolean) entry.getValue()).booleanValue();
    }

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
        Object obj = this.a;
        return (obj == null ? 0 : obj.hashCode()) ^ (this.b ? XKeySym.XK_kana_MA : XKeySym.XK_kana_YU);
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
