package com.android.tools.r8.graph;

import com.android.tools.r8.internal.AbstractC2509m2;
import com.android.tools.r8.internal.C00;
import com.android.tools.r8.internal.CQ;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class R3 extends CQ {
    static final /* synthetic */ boolean f = true;
    private final O3 b;
    private final Y0 c;
    private final C0706m3.e d;
    private ArrayList e = null;

    public R3(O3 o3, String str, String str2, String str3) {
        this.c = o3.e.a(o3.j, str, str2);
        this.b = o3;
        this.d = o3.e.a.u1() ? C0706m3.b(str, str3, o3.c, o3.e.a(), o3.e.a.e) : C0706m3.e.p();
    }

    @Override // com.android.tools.r8.internal.CQ
    public final AbstractC2509m2 a(int i, C00 c00, String str, boolean z) {
        return null;
    }

    @Override // com.android.tools.r8.internal.CQ
    public final AbstractC2509m2 a(String str, boolean z) {
        if (this.e == null) {
            this.e = new ArrayList();
        }
        return T3.a(str, z, this.e, this.b.e, new O3$$ExternalSyntheticLambda0());
    }

    @Override // com.android.tools.r8.internal.CQ
    public final void a() {
        List listEmptyList;
        ArrayList arrayList;
        if (!f && (arrayList = this.e) != null && arrayList.isEmpty()) {
            throw new AssertionError();
        }
        ArrayList arrayList2 = this.b.r;
        Y0 y0 = this.c;
        C0706m3.e eVar = this.d;
        ArrayList arrayList3 = this.e;
        if (arrayList3 != null && !arrayList3.isEmpty()) {
            listEmptyList = this.e;
        } else {
            listEmptyList = Collections.emptyList();
        }
        arrayList2.add(new v5(y0, eVar, listEmptyList));
    }
}
