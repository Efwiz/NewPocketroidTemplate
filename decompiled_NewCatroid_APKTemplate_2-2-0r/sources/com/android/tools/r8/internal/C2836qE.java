package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.qE, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2836qE implements InterfaceC2765pE {
    static final /* synthetic */ boolean b = true;
    private ArrayList a = new ArrayList();

    public final void a(InterfaceC2765pE interfaceC2765pE) {
        this.a.add(interfaceC2765pE);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2765pE
    public final boolean a(C0767v2 c0767v2) {
        Iterator it = this.a.iterator();
        while (it.hasNext()) {
            if (((InterfaceC2765pE) it.next()).a(c0767v2)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2765pE
    public final void a(C0702m c0702m) {
        for (InterfaceC2765pE interfaceC2765pE : this.a) {
            if (!b) {
                interfaceC2765pE.a(c0702m);
            }
        }
    }
}
