package com.android.tools.r8.naming;

import com.android.tools.r8.internal.AbstractC2178hm;
import com.android.tools.r8.internal.AbstractC2963s10;
import com.android.tools.r8.position.Position;
import com.android.tools.r8.utils.InterfaceC3905h3;
import com.android.tools.r8.utils.O2;
import java.util.Arrays;
import java.util.HashMap;

/* JADX INFO: renamed from: com.android.tools.r8.naming.j, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3631j implements InterfaceC3627h {
    private final String a;
    final String b;
    final Position c;
    private final AbstractC2178hm d;
    private final AbstractC2178hm e;
    private final AbstractC2178hm f;

    private C3631j(String str, String str2, Position position, HashMap map, HashMap map2, HashMap map3) {
        this.b = str;
        this.a = str2;
        this.c = position;
        this.d = AbstractC2178hm.a(map);
        this.e = AbstractC2178hm.a(map2);
        this.f = AbstractC2178hm.a(map3);
    }

    static C3629i a(String str, String str2, Position position, O2 o2) {
        return new C3629i(str, str2, position, o2);
    }

    @Override // com.android.tools.r8.naming.InterfaceC3627h
    public final Q a(Q.c cVar) {
        throw null;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C3631j)) {
            return false;
        }
        C3631j c3631j = (C3631j) obj;
        return this.a.equals(c3631j.a) && this.b.equals(c3631j.b) && this.d.equals(c3631j.d) && this.e.equals(c3631j.e) && this.f.equals(c3631j.f);
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.a, this.b, this.d, this.e, this.f});
    }

    public final AbstractC2178hm a() {
        return this.d;
    }

    public final void a(InterfaceC3905h3 interfaceC3905h3) throws Throwable {
        AbstractC2963s10 it = this.f.values().iterator();
        while (it.hasNext()) {
            interfaceC3905h3.accept((Q) it.next());
        }
        AbstractC2963s10 it2 = this.e.values().iterator();
        while (it2.hasNext()) {
            interfaceC3905h3.accept((Q) it2.next());
        }
    }
}
