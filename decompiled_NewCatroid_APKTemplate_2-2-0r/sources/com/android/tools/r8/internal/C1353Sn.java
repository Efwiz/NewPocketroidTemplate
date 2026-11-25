package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0800x2;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Sn, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1353Sn {
    private final Map a;
    private final Map b;
    private final C0684j2 c;
    private final List d;
    private final C0800x2 e;

    C1353Sn(LinkedHashMap linkedHashMap, LinkedHashMap linkedHashMap2, C0684j2 c0684j2, List list, C0800x2 c0800x2) {
        this.a = linkedHashMap;
        this.b = linkedHashMap2;
        this.c = c0684j2;
        this.d = list;
        this.e = c0800x2;
    }

    public final C1508Xm a(C0684j2 c0684j2, C0684j2 c0684j3, C2688oE c2688oE, boolean z, int i) {
        return new C1508Xm(z ? c2688oE.a() : null, i, c0684j2, c0684j3, this.a, this.b, this.c, this.d);
    }

    public final boolean equals(Object obj) {
        if (obj == null || C1353Sn.class != obj.getClass()) {
            return false;
        }
        C1353Sn c1353Sn = (C1353Sn) obj;
        return this.a.equals(c1353Sn.a) && this.b.equals(c1353Sn.b) && this.c == c1353Sn.c && this.d.equals(c1353Sn.d);
    }

    public final int hashCode() {
        return Objects.hash(this.a, this.b, this.c, this.d, this.e);
    }
}
