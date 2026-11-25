package com.android.tools.r8.internal;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class L0 implements GI, InterfaceC3119uI, Serializable {
    protected int a;

    protected L0() {
    }

    @Override // com.android.tools.r8.internal.InterfaceC3119uI
    public abstract int a(int i, Object obj);

    @Override // java.util.Map
    public final boolean containsValue(Object obj) {
        if (obj == null) {
            return false;
        }
        return d(((Integer) obj).intValue());
    }

    public abstract int d(Object obj);

    public abstract boolean d(int i);

    public final void e(int i) {
        this.a = i;
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
        if (map.size() != size()) {
            return false;
        }
        return entrySet().containsAll(map.entrySet());
    }

    @Override // java.util.Map
    public final Object get(Object obj) {
        if (containsKey(obj)) {
            return Integer.valueOf(a(obj));
        }
        return null;
    }

    public int hashCode() {
        int size = size();
        InterfaceC1912eK it = entrySet().iterator();
        int iHashCode = 0;
        while (true) {
            int i = size - 1;
            if (size == 0) {
                return iHashCode;
            }
            iHashCode += ((Map.Entry) it.next()).hashCode();
            size = i;
        }
    }

    @Override // java.util.Map
    public final Object put(Object obj, Object obj2) {
        boolean zContainsKey = containsKey(obj);
        int iA = a(((Integer) obj2).intValue(), obj);
        if (zContainsKey) {
            return Integer.valueOf(iA);
        }
        return null;
    }

    public void putAll(Map map) {
        int size = map.size();
        Iterator it = map.entrySet().iterator();
        if (map instanceof GI) {
            while (true) {
                int i = size - 1;
                if (size == 0) {
                    return;
                }
                EI ei = (EI) it.next();
                a(ei.getIntValue(), ei.getKey());
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
                Integer num = (Integer) entry.getValue();
                containsKey(key);
                a(num.intValue(), key);
                size = i2;
            }
        }
    }

    @Override // java.util.Map
    public final Object remove(Object obj) {
        boolean zContainsKey = containsKey(obj);
        int iD = d(obj);
        if (zContainsKey) {
            return Integer.valueOf(iD);
        }
        return null;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("{");
        InterfaceC1912eK it = entrySet().iterator();
        int size = size();
        boolean z = true;
        while (true) {
            int i = size - 1;
            if (size == 0) {
                sb.append("}");
                return sb.toString();
            }
            if (z) {
                z = false;
            } else {
                sb.append(", ");
            }
            EI ei = (EI) it.next();
            if (this == ei.getKey()) {
                sb.append("(this map)");
            } else {
                sb.append(String.valueOf(ei.getKey()));
            }
            sb.append("=>");
            sb.append(String.valueOf(ei.getIntValue()));
            size = i;
        }
    }

    @Override // java.util.Map
    /* JADX INFO: renamed from: e, reason: merged with bridge method [inline-methods] */
    public InterfaceC2529mK entrySet() {
        return j();
    }
}
