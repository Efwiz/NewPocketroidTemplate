package com.android.tools.r8.internal;

import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class Y implements InterfaceC2100gj, Serializable, Map {
    protected Y() {
    }

    public abstract Object a(long j);

    public abstract Object a(Object obj, long j);

    @Override // com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final boolean containsKey(Object obj) {
        if (obj == null) {
            return false;
        }
        return ((QC) this).b(((Long) obj).longValue());
    }

    @Override // java.util.Map
    public final Set entrySet() {
        QC qc = (QC) this;
        if (qc.h == null) {
            qc.h = new NC(qc, 0);
        }
        return qc.h;
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
        QC qc = (QC) this;
        if (map.size() != qc.g) {
            return false;
        }
        if (qc.h == null) {
            qc.h = new NC(qc, i);
        }
        return ((AbstractCollection) qc.h).containsAll(map.entrySet());
    }

    @Override // java.util.Map
    public final Object get(Object obj) {
        if (obj != null) {
            long jLongValue = ((Long) obj).longValue();
            QC qc = (QC) this;
            if (qc.b(jLongValue)) {
                return qc.c(jLongValue);
            }
        }
        return null;
    }

    @Override // java.util.Map
    public final Object put(Object obj, Object obj2) {
        long jLongValue = ((Long) obj).longValue();
        boolean zB = ((QC) this).b(jLongValue);
        Object objA = a(obj2, jLongValue);
        if (zB) {
            return objA;
        }
        return null;
    }

    public void putAll(Map map) {
        int size = map.size();
        Iterator it = map.entrySet().iterator();
        if (map instanceof Y) {
            while (true) {
                int i = size - 1;
                if (size == 0) {
                    return;
                }
                MC mc = (MC) ((GC) it.next());
                a(mc.getValue(), mc.b.a[mc.a]);
                size = i;
            }
        } else {
            while (true) {
                int i2 = size - 1;
                if (size == 0) {
                    return;
                }
                Map.Entry entry = (Map.Entry) it.next();
                Long l = (Long) entry.getKey();
                Object value = entry.getValue();
                long jLongValue = l.longValue();
                ((QC) this).b(jLongValue);
                a(value, jLongValue);
                size = i2;
            }
        }
    }

    @Override // java.util.Map
    public final Object remove(Object obj) {
        if (obj != null) {
            long jLongValue = ((Long) obj).longValue();
            boolean zB = ((QC) this).b(jLongValue);
            Object objA = a(jLongValue);
            if (zB) {
                return objA;
            }
        }
        return null;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("{");
        QC qc = (QC) this;
        int i = 0;
        if (qc.h == null) {
            qc.h = new NC(qc, i);
        }
        InterfaceC1912eK it = ((NC) qc.h).iterator();
        int i2 = qc.g;
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
            MC mc = (MC) ((GC) ((JC) it).next());
            sb.append(String.valueOf(mc.b.a[mc.a]));
            sb.append("=>");
            if (this == mc.getValue()) {
                sb.append("(this map)");
            } else {
                sb.append(String.valueOf(mc.getValue()));
            }
            i2 = i3;
        }
    }
}
