package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import java.util.ArrayList;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class ZP {
    static final /* synthetic */ boolean a = true;

    public static boolean a(int i) {
        return (i & 1) != 0;
    }

    public static S10 a(AbstractC1305Qs abstractC1305Qs, VP vp) {
        if (!a) {
            C0684j2 c0684j2S2 = abstractC1305Qs.S2();
            if (!c0684j2S2.b(vp.F) && c0684j2S2 != vp.G) {
                throw new AssertionError();
            }
        }
        int iA = com.android.tools.r8.utils.M.a(abstractC1305Qs.W1());
        return ((S10) ((ArrayList) abstractC1305Qs.h1()).get(iA + 1)).m();
    }
}
