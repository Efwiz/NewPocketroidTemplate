package com.android.tools.r8.internal;

import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Uh, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1422Uh extends AbstractC2252ii {
    public Object c;
    public List d;
    public List e;
    public List f;
    public List g;
    public List h;

    public C1422Uh(Object obj) {
        super(589824);
        this.c = obj;
    }

    @Override // com.android.tools.r8.internal.AbstractC2252ii
    public final void a() {
    }

    @Override // com.android.tools.r8.internal.AbstractC2252ii
    public final AbstractC2509m2 a(String str, boolean z) {
        C2432l2 c2432l2 = new C2432l2(str);
        if (z) {
            this.d = P10.a(this.d, c2432l2);
        } else {
            this.e = P10.a(this.e, c2432l2);
        }
        return c2432l2;
    }

    @Override // com.android.tools.r8.internal.AbstractC2252ii
    public final AbstractC2509m2 a(int i, C00 c00, String str, boolean z) {
        C3251w00 c3251w00 = new C3251w00(i, c00, str);
        if (z) {
            this.f = P10.a(this.f, c3251w00);
        } else {
            this.g = P10.a(this.g, c3251w00);
        }
        return c3251w00;
    }

    @Override // com.android.tools.r8.internal.AbstractC2252ii
    public final void a(T3 t3) {
        this.h = P10.a(this.h, t3);
    }
}
