package com.android.tools.r8.internal;

import java.util.Iterator;
import java.util.Map;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class G extends E implements InterfaceC0992Fq {
    protected G() {
    }

    @Override // com.android.tools.r8.internal.InterfaceC3087tq
    public abstract boolean a(int i);

    @Override // java.util.Map
    /* JADX INFO: renamed from: e, reason: merged with bridge method [inline-methods] */
    public InterfaceC2529mK entrySet() {
        return c();
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

    public void putAll(Map map) {
        int size = map.size();
        Iterator it = map.entrySet().iterator();
        if (map instanceof InterfaceC0992Fq) {
            while (true) {
                int i = size - 1;
                if (size == 0) {
                    return;
                }
                InterfaceC0934Dq interfaceC0934Dq = (InterfaceC0934Dq) it.next();
                a(interfaceC0934Dq.a(), interfaceC0934Dq.getValue());
                size = i;
            }
        } else {
            while (true) {
                int i2 = size - 1;
                if (size == 0) {
                    return;
                }
                Map.Entry entry = (Map.Entry) it.next();
                put((Integer) entry.getKey(), entry.getValue());
                size = i2;
            }
        }
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
            InterfaceC0934Dq interfaceC0934Dq = (InterfaceC0934Dq) it.next();
            sb.append(String.valueOf(interfaceC0934Dq.a()));
            sb.append("=>");
            if (this == interfaceC0934Dq.getValue()) {
                sb.append("(this map)");
            } else {
                sb.append(String.valueOf(interfaceC0934Dq.getValue()));
            }
            size = i;
        }
    }
}
