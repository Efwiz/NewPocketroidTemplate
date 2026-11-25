package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.graph.C0767v2;
import java.util.ArrayList;

/* JADX INFO: renamed from: com.android.tools.r8.internal.zc, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3516zc extends QY {
    private final C0767v2 c;

    private C3516zc(C0702m c0702m, C0767v2 c0767v2, C0767v2 c0767v3) {
        super(c0702m, c0767v2);
        this.c = c0767v3;
    }

    public static C3516zc a(C0702m c0702m, C0767v2 c0767v2, C0767v2 c0767v3) {
        return new C3516zc(c0702m, c0767v2, c0767v3);
    }

    public final C0753u b() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new X6(this.c, false));
        arrayList.add(new C3109u8(EnumC1812d20.OBJECT));
        return a(arrayList);
    }
}
