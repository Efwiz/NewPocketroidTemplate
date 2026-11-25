package com.android.tools.r8.naming;

import com.android.tools.r8.position.Position;
import com.android.tools.r8.utils.C3970u0;
import com.android.tools.r8.utils.O2;
import java.util.HashMap;
import java.util.HashSet;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class R0 extends AbstractC3665u0 {
    final HashMap a;
    final HashSet b;
    private final O2 c;

    private R0(O2 o2) {
        this.a = new HashMap();
        this.b = new HashSet();
        this.c = o2;
    }

    @Override // com.android.tools.r8.naming.AbstractC3665u0
    final AbstractC3665u0 a(com.android.tools.r8.naming.mappinginformation.b bVar) {
        return this;
    }

    @Override // com.android.tools.r8.naming.AbstractC3665u0
    final void a(String str) {
    }

    @Override // com.android.tools.r8.naming.AbstractC3665u0
    final AbstractC3625g a(String str, String str2, Position position) {
        String strH = C3970u0.H(str2);
        String strH2 = C3970u0.H(str);
        this.b.add(strH2);
        C3629i c3629iA = C3631j.a(strH2, strH, position, this.c);
        if (!str.startsWith("R8$$REMOVED$$CLASS$$") && this.a.put(strH, c3629iA) != null) {
            this.c.error(w0.a(strH, position));
        }
        return c3629iA;
    }
}
