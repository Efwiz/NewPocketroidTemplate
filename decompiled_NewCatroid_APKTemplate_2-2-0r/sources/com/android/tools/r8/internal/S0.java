package com.android.tools.r8.internal;

import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class S0 implements QJ, InterfaceC2100gj, Serializable {
    protected S0() {
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
        if (map.size() != ((OJ) this).j) {
            return false;
        }
        OJ oj = (OJ) ((T0) this);
        if (oj.k == null) {
            oj.k = new LJ(oj, i);
        }
        return ((AbstractCollection) oj.k).containsAll(map.entrySet());
    }

    public abstract Object put(Object obj, Object obj2);

    public void putAll(Map map) {
        int size = map.size();
        Iterator it = map.entrySet().iterator();
        if (map instanceof QJ) {
            while (true) {
                int i = size - 1;
                if (size == 0) {
                    return;
                }
                KJ kj = (KJ) ((PJ) it.next());
                put(kj.getKey(), kj.getValue());
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
        OJ oj = (OJ) ((T0) this);
        int i = 0;
        if (oj.k == null) {
            oj.k = new LJ(oj, i);
        }
        YJ it = ((LJ) oj.k).iterator();
        int i2 = ((OJ) this).j;
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
            KJ kj = (KJ) ((PJ) ((HJ) it).next());
            if (this == kj.getKey()) {
                sb.append("(this map)");
            } else {
                sb.append(String.valueOf(kj.getKey()));
            }
            sb.append("=>");
            if (this == kj.getValue()) {
                sb.append("(this map)");
            } else {
                sb.append(String.valueOf(kj.getValue()));
            }
            i2 = i3;
        }
    }
}
