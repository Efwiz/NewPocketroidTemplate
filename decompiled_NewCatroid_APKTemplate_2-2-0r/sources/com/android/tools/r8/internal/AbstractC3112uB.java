package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0681j0;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.utils.EnumC3891f;

/* JADX INFO: renamed from: com.android.tools.r8.internal.uB, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3112uB {
    public static void a(AbstractC0681j0 abstractC0681j0, boolean z, EnumC3891f enumC3891f) {
        C0767v2 c0767v2E;
        if (z) {
            return;
        }
        if (enumC3891f.isEqualTo(EnumC3891f.O)) {
            c0767v2E = abstractC0681j0.e.e("Ljava/time/LocalTime;");
        } else if (enumC3891f.isEqualTo(EnumC3891f.R)) {
            c0767v2E = abstractC0681j0.e.e("Ljava/util/concurrent/Flow;");
        } else if (enumC3891f.isEqualTo(EnumC3891f.N)) {
            c0767v2E = abstractC0681j0.e.e("Ljava/util/StringJoiner;");
        } else {
            if (!enumC3891f.isEqualTo(EnumC3891f.T)) {
                abstractC0681j0.d.e.c("Unsupported requiredCompilationAPILevel: " + enumC3891f);
                return;
            }
            c0767v2E = abstractC0681j0.e.e("Ljava/lang/invoke/VarHandle;");
        }
        if (abstractC0681j0.c(c0767v2E) == null) {
            abstractC0681j0.d.e.c("Desugared library requires to be compiled with a library file of API greater or equal to " + enumC3891f + ", but it seems the library file passed is of a lower API.");
        }
    }
}
