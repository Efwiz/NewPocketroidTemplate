package com.android.tools.r8.jetbrains.kotlinx.metadata.jvm;

import com.android.tools.r8.internal.AbstractC3416yA;
import com.android.tools.r8.internal.C1569Zu;
import com.android.tools.r8.internal.C1640an;
import com.android.tools.r8.internal.InterfaceC1423Ui;
import com.android.tools.r8.internal.InterfaceC3115uE;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class q extends AbstractC3416yA implements InterfaceC1423Ui {
    final /* synthetic */ InterfaceC3115uE a;

    q(InterfaceC3115uE interfaceC3115uE) {
        this.a = interfaceC3115uE;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1423Ui
    public final Object a() {
        String[] strArrD1 = this.a.d1();
        if (strArrD1.length == 0) {
            strArrD1 = null;
        }
        if (strArrD1 != null) {
            return C1569Zu.a(strArrD1, this.a.d2());
        }
        throw new C1640an("data1 must not be empty", null);
    }
}
