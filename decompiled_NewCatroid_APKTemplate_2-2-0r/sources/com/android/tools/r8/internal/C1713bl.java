package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.bl, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1713bl {
    C3332x4 a = new C3332x4();
    C2879qr b = new C2879qr();
    InterfaceC1277Pr c = new C2879qr();
    C2879qr d = new C2879qr();
    C2879qr e = new C2879qr();

    public final String toString() {
        StringBuilder sbAppend = new StringBuilder("block ").append(this.a.w()).append(" predecessors: ");
        InterfaceC0841Ar it = this.b.iterator();
        String str = "";
        String str2 = "";
        while (it.hasNext()) {
            sbAppend.append(str2).append(Integer.valueOf(((K) it).t()).intValue());
            str2 = ", ";
        }
        InterfaceC0841Ar it2 = this.d.iterator();
        while (it2.hasNext()) {
            sbAppend.append(str2).append('*').append(Integer.valueOf(((K) it2).t()).intValue());
            str2 = ", ";
        }
        sbAppend.append(" successors: ");
        InterfaceC0841Ar it3 = this.c.iterator();
        while (it3.hasNext()) {
            sbAppend.append(str).append(((Integer) it3.next()).intValue());
            str = ", ";
        }
        InterfaceC0841Ar it4 = this.e.iterator();
        while (it4.hasNext()) {
            sbAppend.append(str).append('*').append(Integer.valueOf(((K) it4).t()).intValue());
            str = ", ";
        }
        return sbAppend.toString();
    }
}
