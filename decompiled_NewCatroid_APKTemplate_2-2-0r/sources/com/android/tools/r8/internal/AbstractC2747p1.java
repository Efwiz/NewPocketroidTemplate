package com.android.tools.r8.internal;

import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.p1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2747p1 implements InterfaceC2100gj, Serializable, Map {
    protected AbstractC2747p1() {
    }

    public abstract long a(Object obj, long j);

    @Override // java.util.Map
    public final boolean containsValue(Object obj) {
        if (obj == null) {
            return false;
        }
        long jLongValue = ((Long) obj).longValue();
        YR yr = (YR) this;
        long[] jArr = yr.b;
        Object[] objArr = yr.a;
        if (!yr.d || jArr[yr.e] != jLongValue) {
            int i = yr.e;
            while (true) {
                int i2 = i - 1;
                if (i == 0) {
                    return false;
                }
                if (objArr[i2] != null && jArr[i2] == jLongValue) {
                    break;
                }
                i = i2;
            }
        }
        return true;
    }

    public abstract long d(Object obj);

    @Override // java.util.Map
    public final Set entrySet() {
        YR yr = (YR) this;
        if (yr.h == null) {
            yr.h = new VR(yr, 0);
        }
        return yr.h;
    }

    @Override // java.util.Map
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        int i = 0;
        if (!(obj instanceof Map)) {
            return false;
        }
        Map map = (Map) obj;
        YR yr = (YR) this;
        if (map.size() != yr.g) {
            return false;
        }
        if (yr.h == null) {
            yr.h = new VR(yr, i);
        }
        return ((AbstractCollection) yr.h).containsAll(map.entrySet());
    }

    @Override // java.util.Map
    public final Object get(Object obj) {
        YR yr = (YR) this;
        if (yr.containsKey(obj)) {
            return Long.valueOf(yr.e(obj));
        }
        return null;
    }

    @Override // java.util.Map
    public final Object put(Object obj, Object obj2) {
        boolean zContainsKey = ((YR) this).containsKey(obj);
        long jA = a(obj, ((Long) obj2).longValue());
        if (zContainsKey) {
            return Long.valueOf(jA);
        }
        return null;
    }

    public void putAll(Map map) {
        int size = map.size();
        Iterator it = map.entrySet().iterator();
        if (map instanceof AbstractC2747p1) {
            while (true) {
                int i = size - 1;
                if (size == 0) {
                    return;
                }
                UR ur = (UR) ((OR) it.next());
                a(ur.getKey(), ur.b.b[ur.a]);
                size = i;
            }
        } else {
            while (true) {
                int i2 = size - 1;
                if (size == 0) {
                    return;
                }
                Map.Entry entry = (Map.Entry) it.next();
                Object key = entry.getKey();
                Long l = (Long) entry.getValue();
                ((YR) this).containsKey(key);
                a(key, l.longValue());
                size = i2;
            }
        }
    }

    @Override // java.util.Map
    public final Object remove(Object obj) {
        boolean zContainsKey = ((YR) this).containsKey(obj);
        long jD = d(obj);
        if (zContainsKey) {
            return Long.valueOf(jD);
        }
        return null;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("{");
        YR yr = (YR) this;
        int i = 0;
        if (yr.h == null) {
            yr.h = new VR(yr, i);
        }
        InterfaceC1912eK it = ((VR) yr.h).iterator();
        int i2 = yr.g;
        boolean z = true;
        while (true) {
            int i3 = i2 - 1;
            if (i2 == 0) {
                sb.append("}");
                return sb.toString();
            }
            if (z) {
                z = false;
            } else {
                sb.append(", ");
            }
            UR ur = (UR) ((OR) ((RR) it).next());
            if (this == ur.getKey()) {
                sb.append("(this map)");
            } else {
                sb.append(String.valueOf(ur.getKey()));
            }
            sb.append("=>");
            sb.append(String.valueOf(ur.b.b[ur.a]));
            i2 = i3;
        }
    }
}
