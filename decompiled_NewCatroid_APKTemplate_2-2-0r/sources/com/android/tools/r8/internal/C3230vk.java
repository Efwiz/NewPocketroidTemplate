package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.graph.C0767v2;
import java.util.ArrayList;

/* JADX INFO: renamed from: com.android.tools.r8.internal.vk, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3230vk extends QY {
    private final com.android.tools.r8.graph.Y0 c;

    public C3230vk(C0702m c0702m, com.android.tools.r8.graph.Y0 y0, C0767v2 c0767v2) {
        super(c0702m, c0767v2);
        this.c = y0;
    }

    public final C0753u b() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new O7(EnumC1812d20.OBJECT, 0));
        com.android.tools.r8.graph.Y0 y0 = this.c;
        arrayList.add(new C3413y7(y0, y0));
        arrayList.add(new I7(182, this.a.a().g4.f, false));
        arrayList.add(new C3109u8(EnumC1812d20.INT));
        return a(arrayList);
    }
}
