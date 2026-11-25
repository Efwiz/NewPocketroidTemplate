package com.android.tools.r8.internal;

import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class J0 implements InterfaceC2840qI, Serializable, Map {
    protected J0() {
    }

    public abstract boolean a(Object obj, boolean z);

    public abstract boolean a(boolean z);

    @Override // com.android.tools.r8.internal.InterfaceC2840qI
    public abstract boolean c(Object obj);

    @Override // java.util.Map
    public final boolean containsValue(Object obj) {
        if (obj == null) {
            return false;
        }
        return a(((Boolean) obj).booleanValue());
    }

    @Override // java.util.Map
    /* JADX INFO: renamed from: e, reason: merged with bridge method [inline-methods] */
    public abstract InterfaceC2529mK keySet();

    @Override // java.util.Map
    public final Set entrySet() {
        return k();
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
        if (map.size() != ((C2769pI) this).size()) {
            return false;
        }
        return ((AbstractCollection) k()).containsAll(map.entrySet());
    }

    @Override // java.util.Map
    public final Object get(Object obj) {
        C2769pI c2769pI = (C2769pI) this;
        if (c2769pI.containsKey(obj)) {
            return Boolean.valueOf(c2769pI.b(obj));
        }
        return null;
    }

    @Override // java.util.Map
    public final int hashCode() {
        int size = ((C2769pI) this).size();
        C2602nI c2602nI = new C2602nI((C2693oI) k());
        int iHashCode = 0;
        while (true) {
            int i = size - 1;
            if (size == 0) {
                return iHashCode;
            }
            iHashCode += ((Map.Entry) c2602nI.next()).hashCode();
            size = i;
        }
    }

    public abstract InterfaceC2529mK k();

    @Override // java.util.Map
    public final Object put(Object obj, Object obj2) {
        boolean zContainsKey = ((C2769pI) this).containsKey(obj);
        boolean zA = a(obj, ((Boolean) obj2).booleanValue());
        if (zContainsKey) {
            return Boolean.valueOf(zA);
        }
        return null;
    }

    @Override // java.util.Map
    public final void putAll(Map map) {
        int size = map.size();
        Iterator it = map.entrySet().iterator();
        if (map instanceof J0) {
            while (true) {
                int i = size - 1;
                if (size == 0) {
                    return;
                }
                I0 i0 = (I0) it.next();
                a(i0.a, i0.b);
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
                Boolean bool = (Boolean) entry.getValue();
                ((C2769pI) this).containsKey(key);
                a(key, bool.booleanValue());
                size = i2;
            }
        }
    }

    @Override // java.util.Map
    public final Object remove(Object obj) {
        boolean zContainsKey = ((C2769pI) this).containsKey(obj);
        boolean zC = c(obj);
        if (zContainsKey) {
            return Boolean.valueOf(zC);
        }
        return null;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("{");
        C2602nI c2602nI = new C2602nI((C2693oI) k());
        int size = ((C2769pI) this).size();
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
            I0 i0 = (I0) c2602nI.next();
            Object obj = i0.a;
            if (this == obj) {
                sb.append("(this map)");
            } else {
                sb.append(String.valueOf(obj));
            }
            sb.append("=>");
            sb.append(String.valueOf(i0.b));
            size = i;
        }
    }
}
