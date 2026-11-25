package com.android.tools.r8.internal;

import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.q1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2820q1 implements InterfaceC2100gj, Serializable, Map {
    protected AbstractC2820q1() {
    }

    @Override // java.util.Map
    public final Set entrySet() {
        C2306jS c2306jS = (C2306jS) this;
        if (c2306jS.h == null) {
            c2306jS.h = new C2078gS(c2306jS, 0);
        }
        return c2306jS.h;
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
        C2306jS c2306jS = (C2306jS) this;
        if (map.size() != c2306jS.g) {
            return false;
        }
        if (c2306jS.h == null) {
            c2306jS.h = new C2078gS(c2306jS, i);
        }
        return ((AbstractCollection) c2306jS.h).containsAll(map.entrySet());
    }

    public abstract Object put(Object obj, Object obj2);

    public void putAll(Map map) {
        int size = map.size();
        Iterator it = map.entrySet().iterator();
        if (map instanceof AbstractC2820q1) {
            while (true) {
                int i = size - 1;
                if (size == 0) {
                    return;
                }
                C2002fS c2002fS = (C2002fS) ((ZR) it.next());
                put(c2002fS.getKey(), c2002fS.getValue());
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
        C2306jS c2306jS = (C2306jS) this;
        int i = 0;
        if (c2306jS.h == null) {
            c2306jS.h = new C2078gS(c2306jS, i);
        }
        InterfaceC1912eK it = ((C2078gS) c2306jS.h).iterator();
        int i2 = c2306jS.g;
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
            C2002fS c2002fS = (C2002fS) ((ZR) ((C1765cS) it).next());
            if (this == c2002fS.getKey()) {
                sb.append("(this map)");
            } else {
                sb.append(String.valueOf(c2002fS.getKey()));
            }
            sb.append("=>");
            if (this == c2002fS.getValue()) {
                sb.append("(this map)");
            } else {
                sb.append(String.valueOf(c2002fS.getValue()));
            }
            i2 = i3;
        }
    }
}
