package com.android.tools.r8.internal;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class P0 implements InterfaceC2528mJ, InterfaceC2100gj, Serializable {
    protected Object a;

    protected P0() {
    }

    @Override // com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public abstract void clear();

    @Override // com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public abstract boolean containsKey(Object obj);

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
        return ((R0) this).k().containsAll(map.entrySet());
    }

    @Override // java.util.Map
    public int hashCode() {
        int size = size();
        InterfaceC1912eK it = ((R0) this).k().iterator();
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

    public abstract Object put(Object obj, Object obj2);

    @Override // java.util.Map
    public void putAll(Map map) {
        int size = map.size();
        Iterator it = map.entrySet().iterator();
        if (map instanceof InterfaceC2528mJ) {
            while (true) {
                int i = size - 1;
                if (size == 0) {
                    return;
                }
                InterfaceC2454lJ interfaceC2454lJ = (InterfaceC2454lJ) it.next();
                put(interfaceC2454lJ.getKey(), interfaceC2454lJ.getValue());
                size = i;
            }
        } else {
            while (true) {
                int i2 = size - 1;
                if (size == 0) {
                    return;
                }
                Map.Entry entry = (Map.Entry) it.next();
                put(entry.getKey(), entry.getValue());
                size = i2;
            }
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("{");
        InterfaceC1912eK it = ((R0) this).k().iterator();
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
            InterfaceC2454lJ interfaceC2454lJ = (InterfaceC2454lJ) it.next();
            if (this == interfaceC2454lJ.getKey()) {
                sb.append("(this map)");
            } else {
                sb.append(String.valueOf(interfaceC2454lJ.getKey()));
            }
            sb.append("=>");
            if (this == interfaceC2454lJ.getValue()) {
                sb.append("(this map)");
            } else {
                sb.append(String.valueOf(interfaceC2454lJ.getValue()));
            }
            size = i;
        }
    }
}
