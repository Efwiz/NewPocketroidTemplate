package com.android.tools.r8.internal;

import com.android.tools.r8.ClassFileConsumer;
import com.android.tools.r8.graph.C0702m;
import java.util.ArrayList;

/* JADX INFO: renamed from: com.android.tools.r8.internal.oM, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface InterfaceC2697oM {
    static InterfaceC2697oM a(C0702m c0702m) {
        return c0702m.Y().f instanceof ClassFileConsumer ? new P9(c0702m) : new C2300jM(c0702m);
    }

    ArrayList a(com.android.tools.r8.graph.o5 o5Var, C2067gM c2067gM, boolean z, boolean z2, int i);

    void a();
}
