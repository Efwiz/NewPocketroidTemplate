package com.android.tools.r8.jetbrains.kotlin;

import com.android.tools.r8.AbstractC0403b;
import com.android.tools.r8.internal.AbstractC0908Cs;
import com.android.tools.r8.internal.C2692oH;
import com.android.tools.r8.internal.InterfaceC1423Ui;
import com.android.tools.r8.internal.L10;
import com.android.tools.r8.internal.UA;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class a extends b {
    public static UA a(InterfaceC1423Ui interfaceC1423Ui) {
        AbstractC0908Cs.a(2, "mode");
        int iB = AbstractC0403b.b(2);
        if (iB == 0) {
            return new c(interfaceC1423Ui, null);
        }
        if (iB == 1) {
            return new SafePublicationLazyImpl(interfaceC1423Ui);
        }
        if (iB == 2) {
            return new L10(interfaceC1423Ui);
        }
        throw new C2692oH();
    }

    public static UA b(InterfaceC1423Ui interfaceC1423Ui) {
        return new c(interfaceC1423Ui, null);
    }
}
