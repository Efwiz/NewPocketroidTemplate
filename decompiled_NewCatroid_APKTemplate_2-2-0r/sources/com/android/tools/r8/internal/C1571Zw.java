package com.android.tools.r8.internal;

import java.util.HashSet;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Zw, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1571Zw {
    public final boolean a;
    public HashSet b;

    private C1571Zw(boolean z) {
        this.b = new HashSet();
        this.a = z;
    }

    public final C1651ax a() {
        if (this.b.isEmpty()) {
            if (this.a) {
                return C1651ax.b;
            }
            throw new C2883qv("Invalid keep options that disallow nothing.");
        }
        if (this.b.size() == C1651ax.a.values().length) {
            if (this.a) {
                throw new C2883qv("Invalid keep options that allow everything.");
            }
            return C1651ax.b;
        }
        if (this.a) {
            return new C1651ax(AbstractC1048Hm.a(this.b));
        }
        C0862Bm c0862BmG = AbstractC1048Hm.g();
        for (C1651ax.a aVar : C1651ax.a.values()) {
            if (!this.b.contains(aVar)) {
                c0862BmG.a(aVar);
            }
        }
        return new C1651ax(c0862BmG.a());
    }
}
