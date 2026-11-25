package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Td, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1369Td {
    static final /* synthetic */ boolean c = true;
    private final int a;
    private final InterfaceC2415kr b;

    private C1369Td(int i, C2807pq c2807pq) {
        if (!c && i < 0) {
            throw new AssertionError();
        }
        this.a = i;
        this.b = c2807pq;
    }

    public final String a(boolean z) {
        StringBuilder sb = new StringBuilder("params:");
        sb.append(this.a).append('\n');
        InterfaceC2415kr interfaceC2415kr = this.b;
        if (interfaceC2415kr != null) {
            InterfaceC1912eK it = interfaceC2415kr.values().iterator();
            while (it.hasNext()) {
                sb.append((C1470Wd) it.next()).append('\n');
            }
        } else {
            sb.append(" no conversions\n");
        }
        return sb.toString();
    }

    public final String toString() {
        return a(false);
    }
}
