package com.android.tools.r8.naming;

import com.android.tools.r8.SourceFileProvider;
import com.android.tools.r8.shaking.L2;
import com.android.tools.r8.utils.A1;
import java.util.Objects;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class U0 {
    public static SourceFileProvider a(SourceFileProvider sourceFileProvider, L2 l2, A1 a1) {
        String strU;
        if (sourceFileProvider != null) {
            return sourceFileProvider;
        }
        boolean z = l2.h().a;
        boolean z2 = true;
        if (!z) {
            Objects.requireNonNull(a1.j0());
            return new T0("SourceFile", true);
        }
        if (a1.S0) {
            if (!a1.k1() || (strU = a1.D0().u()) == null) {
                return null;
            }
            if (!strU.isEmpty()) {
                Objects.requireNonNull(a1.j0());
                if (!"SourceFile".equals(strU)) {
                    z2 = false;
                }
            }
            return new T0(strU, z2);
        }
        String strU2 = a1.D0().u();
        if (strU2 != null) {
            if (!strU2.isEmpty()) {
                Objects.requireNonNull(a1.j0());
                if (!"SourceFile".equals(strU2)) {
                    z2 = false;
                }
            }
            return new T0(strU2, z2);
        }
        if (!a1.k1() && !a1.l1()) {
            return null;
        }
        Objects.requireNonNull(a1.j0());
        return new T0("SourceFile", true);
    }
}
