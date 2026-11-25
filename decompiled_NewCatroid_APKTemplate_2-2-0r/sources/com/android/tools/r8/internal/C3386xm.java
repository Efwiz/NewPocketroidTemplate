package com.android.tools.r8.internal;

import java.util.Collection;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.xm, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3386xm extends AbstractC1241Ol {
    final InterfaceC1600aG a;

    C3386xm(AB ab) {
        this.a = ab;
    }

    public final C3386xm a(Object obj) {
        InterfaceC1600aG interfaceC1600aG = this.a;
        int i = AbstractC3123uM.a;
        obj.getClass();
        interfaceC1600aG.add(obj);
        return this;
    }

    public final AbstractC0836Am a() {
        InterfaceC1600aG interfaceC1600aGA = this.a;
        if (interfaceC1600aGA instanceof AbstractC0836Am) {
            AbstractC0836Am abstractC0836Am = (AbstractC0836Am) interfaceC1600aGA;
            if (!abstractC0836Am.e()) {
                return abstractC0836Am;
            }
        }
        if (!(interfaceC1600aGA instanceof InterfaceC1600aG)) {
            interfaceC1600aGA = AB.a(interfaceC1600aGA);
        }
        Set setEntrySet = interfaceC1600aGA.entrySet();
        if (setEntrySet.isEmpty()) {
            return RS.j;
        }
        return RS.a((Collection) setEntrySet);
    }
}
