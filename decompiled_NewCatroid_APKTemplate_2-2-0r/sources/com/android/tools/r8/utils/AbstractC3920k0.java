package com.android.tools.r8.utils;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0635d2;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0719o4;
import com.android.tools.r8.graph.C0725p4;
import com.android.tools.r8.internal.AbstractC2863qd;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.utils.k0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class AbstractC3920k0 {
    public static void a(final C0702m c0702m) {
        AbstractC2863qd.a(c0702m.a(), new Consumer() { // from class: com.android.tools.r8.utils.k0$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                AbstractC3920k0.a(c0702m, (C0684j2) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(C0702m c0702m, C0684j2 c0684j2) {
        AbstractC0727q0 abstractC0727q0D = c0702m.f().d(c0684j2.B0());
        int i = C0635d2.u;
        C0635d2 c0635d2D0 = abstractC0727q0D != null ? abstractC0727q0D.d0() : null;
        if (c0635d2D0 != null && c0635d2D0.c(c0684j2) == null) {
            c0635d2D0.b(com.android.tools.r8.graph.W0.S0().a(c0684j2).a((C0725p4) ((C0719o4) C0725p4.O().c(true)).a()).b(com.android.tools.r8.androidapi.h.w()).a());
        }
    }
}
