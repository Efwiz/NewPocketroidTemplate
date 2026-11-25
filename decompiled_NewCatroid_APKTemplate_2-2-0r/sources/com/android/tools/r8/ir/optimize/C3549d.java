package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.AssertionsConfiguration;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.internal.C3405y10;

/* JADX INFO: renamed from: com.android.tools.r8.ir.optimize.d, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3549d {
    static final /* synthetic */ boolean c = true;
    private final AssertionsConfiguration a;
    private final C0759u2 b;

    private C3549d(AssertionsConfiguration assertionsConfiguration, C0722p1 c0722p1) {
        this.a = assertionsConfiguration;
        int i = AbstractC3545c.a[assertionsConfiguration.getScope().ordinal()];
        if (i == 1) {
            if (assertionsConfiguration.getValue().length() == 0) {
                this.b = c0722p1.c("");
                return;
            } else {
                this.b = c0722p1.c("L" + assertionsConfiguration.getValue().replace('.', '/') + "/");
                return;
            }
        }
        if (i == 2) {
            this.b = c0722p1.c("L" + assertionsConfiguration.getValue().replace('.', '/') + ";");
        } else {
            if (i != 3) {
                throw new C3405y10();
            }
            this.b = null;
        }
    }
}
