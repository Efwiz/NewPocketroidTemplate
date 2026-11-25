package com.android.tools.r8.errors;

import com.android.tools.r8.graph.InterfaceC0694k5;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.C1507Xl;
import com.android.tools.r8.shaking.D4;
import com.android.tools.r8.shaking.M0;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class c {
    private C1507Xl a = AbstractC1639am.g();

    public final c a(ArrayList arrayList, M0 m0, D4 d4) {
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            InterfaceC0694k5 interfaceC0694k5 = (InterfaceC0694k5) it.next();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            d4.a(m0.a(interfaceC0694k5.getReference()), new PrintStream(byteArrayOutputStream));
            this.a.a("Item " + interfaceC0694k5.getReference().r0() + " was not discarded.\n" + byteArrayOutputStream.toString());
        }
        return this;
    }

    public final CheckDiscardDiagnostic a() {
        return new CheckDiscardDiagnostic(this.a.a());
    }
}
