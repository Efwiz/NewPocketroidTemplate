package com.android.tools.r8.internal;

import com.android.tools.r8.SourceFileProvider;
import com.android.tools.r8.graph.C0772w0;
import java.util.Iterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.pM, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2773pM {
    public static boolean a(com.android.tools.r8.graph.W0 w0, com.android.tools.r8.utils.A1 a1) {
        com.android.tools.r8.graph.W wA1 = w0.a1();
        if (wA1 == null) {
            return false;
        }
        if (!wA1.B0()) {
            if (!wA1.z0()) {
                return false;
            }
            Iterator<B7> it = wA1.K().I0().iterator();
            while (it.hasNext()) {
                if (it.next() instanceof C2051g8) {
                    return true;
                }
            }
            return false;
        }
        C0772w0 c0772w0P = wA1.P();
        SourceFileProvider sourceFileProvider = a1.K1;
        if (sourceFileProvider != null && sourceFileProvider.allowDiscardingSourceFile()) {
            com.android.tools.r8.graph.J0 j0H0 = c0772w0P.H0();
            if (j0H0 == null) {
                return false;
            }
            if (!j0H0.z0()) {
                for (com.android.tools.r8.graph.B0 b0 : j0H0.t0().g) {
                    if (!(b0 instanceof com.android.tools.r8.graph.B0.b)) {
                    }
                }
                return false;
            }
        }
        return true;
    }
}
