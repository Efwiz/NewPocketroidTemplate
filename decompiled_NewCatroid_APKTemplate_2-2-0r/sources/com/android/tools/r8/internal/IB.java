package com.android.tools.r8.internal;

import com.huawei.hms.framework.common.ContainerUtils;
import java.util.Map;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class IB implements Map.Entry {
    IB a;
    IB b;
    IB c;
    IB d;
    IB e;
    final Object f;
    Object g;
    int h;

    IB() {
        this.f = null;
        this.e = this;
        this.d = this;
    }

    @Override // java.util.Map.Entry
    public final boolean equals(Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        Object obj2 = this.f;
        if (obj2 == null) {
            if (entry.getKey() != null) {
                return false;
            }
        } else if (!obj2.equals(entry.getKey())) {
            return false;
        }
        Object obj3 = this.g;
        if (obj3 == null) {
            if (entry.getValue() != null) {
                return false;
            }
        } else if (!obj3.equals(entry.getValue())) {
            return false;
        }
        return true;
    }

    @Override // java.util.Map.Entry
    public final Object getKey() {
        return this.f;
    }

    @Override // java.util.Map.Entry
    public final Object getValue() {
        return this.g;
    }

    @Override // java.util.Map.Entry
    public final int hashCode() {
        Object obj = this.f;
        int iHashCode = obj == null ? 0 : obj.hashCode();
        Object obj2 = this.g;
        return iHashCode ^ (obj2 != null ? obj2.hashCode() : 0);
    }

    @Override // java.util.Map.Entry
    public final Object setValue(Object obj) {
        Object obj2 = this.g;
        this.g = obj;
        return obj2;
    }

    public final String toString() {
        return this.f + ContainerUtils.KEY_VALUE_DELIMITER + this.g;
    }

    IB(IB ib, Object obj, IB ib2, IB ib3) {
        this.a = ib;
        this.f = obj;
        this.h = 1;
        this.d = ib2;
        this.e = ib3;
        ib3.d = this;
        ib2.e = this;
    }
}
