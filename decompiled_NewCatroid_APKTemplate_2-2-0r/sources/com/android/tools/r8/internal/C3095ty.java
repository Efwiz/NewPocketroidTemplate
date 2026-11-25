package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ty, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3095ty extends AbstractC3172uy {
    public C2815py b;

    public final void a(C2815py c2815py) {
        AbstractC0936Ds.c(c2815py, "<set-?>");
        this.b = c2815py;
    }

    public final AbstractC3025sy a(int i, String str) {
        AbstractC0936Ds.c(str, "name");
        C2815py c2815py = new C2815py(i, str);
        a(c2815py);
        return c2815py;
    }
}
