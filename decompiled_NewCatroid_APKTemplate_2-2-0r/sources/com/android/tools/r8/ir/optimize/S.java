package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.internal.AbstractC1305Qs;
import com.android.tools.r8.internal.InterfaceC2101gk;
import java.util.ArrayList;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class S implements InterfaceC2101gk {
    static final /* synthetic */ boolean a = true;

    S() {
    }

    @Override // com.android.tools.r8.internal.InterfaceC2101gk
    public final boolean a(Object obj, Object obj2) {
        AbstractC1305Qs abstractC1305Qs = (AbstractC1305Qs) obj;
        AbstractC1305Qs abstractC1305Qs2 = (AbstractC1305Qs) obj2;
        boolean z = a;
        if (!z && abstractC1305Qs != null && abstractC1305Qs.b().A()) {
            throw new AssertionError();
        }
        if (!z && abstractC1305Qs2 != null && abstractC1305Qs2.b().A()) {
            throw new AssertionError();
        }
        if (abstractC1305Qs != abstractC1305Qs2) {
            if (abstractC1305Qs != null && abstractC1305Qs2 != null && abstractC1305Qs.b(abstractC1305Qs2)) {
                if (((ArrayList) abstractC1305Qs.h1()).equals(abstractC1305Qs2.h1())) {
                }
            }
            return false;
        }
        return true;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2101gk
    public final int a(Object obj) {
        AbstractC1305Qs abstractC1305Qs = (AbstractC1305Qs) obj;
        return ((ArrayList) abstractC1305Qs.h1()).hashCode() + (abstractC1305Qs.S2().hashCode() * 31);
    }
}
