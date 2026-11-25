package com.android.tools.r8.internal;

import com.huawei.hms.framework.common.ContainerUtils;
import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.x0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class AbstractC3325x0 implements Map.Entry {
    AbstractC3325x0() {
    }

    @Override // java.util.Map.Entry
    public final boolean equals(Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        return AbstractC3428yK.a(getKey(), entry.getKey()) && AbstractC3428yK.a(getValue(), entry.getValue());
    }

    @Override // java.util.Map.Entry
    public final int hashCode() {
        Object key = getKey();
        Object value = getValue();
        return (key == null ? 0 : key.hashCode()) ^ (value != null ? value.hashCode() : 0);
    }

    @Override // java.util.Map.Entry
    public Object setValue(Object obj) {
        throw new UnsupportedOperationException();
    }

    public final String toString() {
        String strValueOf = String.valueOf(getKey());
        String strValueOf2 = String.valueOf(getValue());
        return new StringBuilder(strValueOf2.length() + strValueOf.length() + 1).append(strValueOf).append(ContainerUtils.KEY_VALUE_DELIMITER).append(strValueOf2).toString();
    }
}
