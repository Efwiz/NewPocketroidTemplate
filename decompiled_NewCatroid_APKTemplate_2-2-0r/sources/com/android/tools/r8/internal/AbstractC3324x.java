package com.android.tools.r8.internal;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.x, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3324x implements InterfaceC1050Ho, InterfaceC3162uo, Serializable {
    protected int a;

    protected AbstractC3324x() {
    }

    @Override // com.android.tools.r8.internal.InterfaceC3162uo
    public abstract int b(int i, int i2);

    @Override // com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final boolean containsKey(Object obj) {
        if (obj == null) {
            return false;
        }
        return a(((Integer) obj).intValue());
    }

    @Override // java.util.Map
    public final boolean containsValue(Object obj) {
        if (obj == null) {
            return false;
        }
        return d(((Integer) obj).intValue());
    }

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
        if (obj != null) {
            int iIntValue = ((Integer) obj).intValue();
            if (a(iIntValue)) {
                return Integer.valueOf(get(iIntValue));
            }
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
    /* JADX INFO: renamed from: k, reason: merged with bridge method [inline-methods] */
    public InterfaceC2529mK entrySet() {
        return i();
    }

    @Override // java.util.Map
    public final Object put(Object obj, Object obj2) {
        int iIntValue = ((Integer) obj).intValue();
        boolean zA = a(iIntValue);
        int iB = b(iIntValue, ((Integer) obj2).intValue());
        if (zA) {
            return Integer.valueOf(iB);
        }
        return null;
    }

    public void putAll(Map map) {
        int size = map.size();
        Iterator it = map.entrySet().iterator();
        if (map instanceof InterfaceC1050Ho) {
            while (true) {
                int i = size - 1;
                if (size == 0) {
                    return;
                }
                InterfaceC0990Fo interfaceC0990Fo = (InterfaceC0990Fo) it.next();
                b(interfaceC0990Fo.a(), interfaceC0990Fo.getIntValue());
                size = i;
            }
        } else {
            while (true) {
                int i2 = size - 1;
                if (size == 0) {
                    return;
                }
                Map.Entry entry = (Map.Entry) it.next();
                Integer num = (Integer) entry.getKey();
                Integer num2 = (Integer) entry.getValue();
                int iIntValue = num.intValue();
                a(iIntValue);
                b(iIntValue, num2.intValue());
                size = i2;
            }
        }
    }

    @Override // com.android.tools.r8.internal.InterfaceC3162uo
    public abstract int remove(int i);

    @Override // java.util.Map
    public final Object remove(Object obj) {
        if (obj != null) {
            int iIntValue = ((Integer) obj).intValue();
            boolean zA = a(iIntValue);
            int iRemove = remove(iIntValue);
            if (zA) {
                return Integer.valueOf(iRemove);
            }
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
            InterfaceC0990Fo interfaceC0990Fo = (InterfaceC0990Fo) it.next();
            sb.append(String.valueOf(interfaceC0990Fo.a()));
            sb.append("=>");
            sb.append(String.valueOf(interfaceC0990Fo.getIntValue()));
            size = i;
        }
    }

    public final int e() {
        return this.a;
    }
}
