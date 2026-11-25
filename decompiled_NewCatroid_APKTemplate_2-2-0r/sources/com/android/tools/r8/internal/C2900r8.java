package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.r8, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2900r8 {
    public final InterfaceC0992Fq a;
    public final List b;

    C2900r8(C1303Qq c1303Qq, ArrayList arrayList) {
        this.a = c1303Qq;
        this.b = arrayList;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("regs[");
        InterfaceC1912eK it = this.a.c().iterator();
        while (it.hasNext()) {
            InterfaceC0934Dq interfaceC0934Dq = (InterfaceC0934Dq) it.next();
            sb.append(interfaceC0934Dq.a()).append(":").append(interfaceC0934Dq.getValue()).append(", ");
        }
        sb.append("], stack[");
        Iterator it2 = this.b.iterator();
        while (it2.hasNext()) {
            sb.append((P00) it2.next()).append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}
