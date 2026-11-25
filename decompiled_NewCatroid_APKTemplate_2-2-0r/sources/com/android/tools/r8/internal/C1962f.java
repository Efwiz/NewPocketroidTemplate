package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.graph.C0767v2;
import java.util.ArrayList;

/* JADX INFO: renamed from: com.android.tools.r8.internal.f, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1962f extends QY {
    private final C0684j2 c;
    private final boolean d;
    private final C0684j2 e;
    private final C0684j2[] f;
    private final int g;
    private final com.android.tools.r8.graph.Y0 h;

    public C1962f(C0702m c0702m, C0767v2 c0767v2, C0684j2 c0684j2, boolean z, C0684j2 c0684j3, C0684j2[] c0684j2Arr) {
        super(c0702m, c0767v2);
        this.c = c0684j2;
        this.d = z;
        this.e = c0684j3;
        this.f = c0684j2Arr;
        this.g = a(z);
        this.h = null;
    }

    private static int a(boolean z) {
        return z ? 185 : 182;
    }

    public final C0753u b() {
        ArrayList arrayList = new ArrayList();
        boolean z = this.g == 184;
        if (!z) {
            arrayList.add(new O7(EnumC1812d20.OBJECT, 0));
            if (this.h != null) {
                com.android.tools.r8.graph.Y0 y0 = this.h;
                arrayList.add(new C3413y7(y0, y0));
            }
        }
        int iA = com.android.tools.r8.utils.M.a(!z);
        for (int i = 0; i < this.c.F0(); i++) {
            C0767v2 c0767v2K = this.c.k(i);
            EnumC1812d20 enumC1812d20 = EnumC1812d20.OBJECT;
            EnumC1812d20 enumC1812d20A = EnumC1812d20.a((char) c0767v2K.e.e[0]);
            arrayList.add(new O7(enumC1812d20A, iA));
            if (this.f[i] != null) {
                arrayList.add(new I7(184, this.f[i], false));
            }
            iA += enumC1812d20A.b() ? 2 : 1;
        }
        arrayList.add(new I7(this.g, this.c, this.d));
        if (this.e != null) {
            arrayList.add(new I7(184, this.e, false));
        }
        if (this.c.I0().Y0()) {
            arrayList.add(new C3186v8());
        } else {
            C0767v2 c0767v2I0 = this.c.I0();
            EnumC1812d20 enumC1812d21 = EnumC1812d20.OBJECT;
            arrayList.add(new C3109u8(EnumC1812d20.a((char) c0767v2I0.e.e[0])));
        }
        return a(arrayList);
    }

    public C1962f(C0702m c0702m, C0767v2 c0767v2, C0684j2 c0684j2, boolean z, C0684j2 c0684j3, C0684j2[] c0684j2Arr, int i) {
        super(c0702m, c0767v2);
        this.c = c0684j2;
        this.d = z;
        this.e = c0684j3;
        this.f = c0684j2Arr;
        this.g = i;
        this.h = null;
    }

    public C1962f(C0702m c0702m, C0767v2 c0767v2, C0684j2 c0684j2, boolean z, C0684j2 c0684j3, C0684j2[] c0684j2Arr, com.android.tools.r8.graph.Y0 y0) {
        super(c0702m, c0767v2);
        this.c = c0684j2;
        this.d = z;
        this.e = c0684j3;
        this.f = c0684j2Arr;
        this.g = a(z);
        this.h = y0;
    }
}
