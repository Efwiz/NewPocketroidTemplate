package com.android.tools.r8.internal;

import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class N0 implements InterfaceC2100gj, Serializable, Map {
    protected N0() {
    }

    public abstract long a(Object obj, long j);

    @Override // java.util.Map
    public final boolean containsValue(Object obj) {
        if (obj == null) {
            return false;
        }
        long jLongValue = ((Long) obj).longValue();
        C1684bJ c1684bJ = (C1684bJ) this;
        long[] jArr = c1684bJ.b;
        Object[] objArr = c1684bJ.a;
        if (!c1684bJ.d || jArr[c1684bJ.e] != jLongValue) {
            int i = c1684bJ.e;
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

    public abstract InterfaceC2529mK e();

    @Override // java.util.Map
    public final Set entrySet() {
        return e();
    }

    @Override // java.util.Map
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Map)) {
            return false;
        }
        Map map = (Map) obj;
        if (map.size() != ((C1684bJ) this).g) {
            return false;
        }
        return ((AbstractCollection) e()).containsAll(map.entrySet());
    }

    /* JADX WARN: Code duplicated, block: B:16:0x003c A[EDGE_INSN: B:16:0x003c->B:20:0x0049 BREAK  A[LOOP:0: B:14:0x0033->B:25:?]] */
    @Override // java.util.Map
    public final Object get(Object obj) {
        long j;
        C1684bJ c1684bJ = (C1684bJ) this;
        if (!c1684bJ.containsKey(obj)) {
            return null;
        }
        if (obj != null) {
            Object[] objArr = c1684bJ.a;
            int iA = AbstractC3307wk.a(obj.hashCode()) & c1684bJ.c;
            Object obj2 = objArr[iA];
            if (obj2 == null) {
                j = 0;
                break;
            }
            if (obj.equals(obj2)) {
                j = c1684bJ.b[iA];
            } else {
                while (true) {
                    iA = (iA + 1) & c1684bJ.c;
                    Object obj3 = objArr[iA];
                    if (obj3 == null) {
                        j = 0;
                        break;
                    }
                    if (obj.equals(obj3)) {
                        j = c1684bJ.b[iA];
                        break;
                    }
                }
            }
        } else {
            if (!c1684bJ.d) {
                j = 0;
                break;
            }
            j = c1684bJ.b[c1684bJ.e];
        }
        return Long.valueOf(j);
    }

    @Override // java.util.Map
    public final Object put(Object obj, Object obj2) {
        boolean zContainsKey = ((C1684bJ) this).containsKey(obj);
        long jA = a(obj, ((Long) obj2).longValue());
        if (zContainsKey) {
            return Long.valueOf(jA);
        }
        return null;
    }

    @Override // java.util.Map
    public void putAll(Map map) {
        int size = map.size();
        Iterator it = map.entrySet().iterator();
        if (map instanceof N0) {
            while (true) {
                int i = size - 1;
                if (size == 0) {
                    return;
                }
                XI xi = (XI) ((RI) it.next());
                a(xi.getKey(), xi.getLongValue());
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
                ((C1684bJ) this).containsKey(key);
                a(key, l.longValue());
                size = i2;
            }
        }
    }

    @Override // java.util.Map
    public final Object remove(Object obj) {
        boolean zContainsKey = ((C1684bJ) this).containsKey(obj);
        long jD = d(obj);
        if (zContainsKey) {
            return Long.valueOf(jD);
        }
        return null;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("{");
        InterfaceC1912eK it = ((YI) e()).iterator();
        int i = ((C1684bJ) this).g;
        boolean z = true;
        while (true) {
            int i2 = i - 1;
            if (i == 0) {
                sb.append("}");
                return sb.toString();
            }
            if (z) {
                z = false;
            } else {
                sb.append(", ");
            }
            XI xi = (XI) ((RI) it.next());
            if (this == xi.getKey()) {
                sb.append("(this map)");
            } else {
                sb.append(String.valueOf(xi.getKey()));
            }
            sb.append("=>");
            sb.append(String.valueOf(xi.getLongValue()));
            i = i2;
        }
    }
}
