package com.android.tools.r8.errors;

import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.C1507Xl;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class d {
    private final C1507Xl a = AbstractC1639am.g();

    public final d a(ArrayList arrayList) {
        arrayList.sort(Comparator.comparing(new d$$ExternalSyntheticLambda0()));
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            this.a.a("Enum " + ((C0729q2) it.next()).o1() + " was not unboxed.");
        }
        return this;
    }

    public final CheckEnumUnboxedDiagnostic a() {
        return new CheckEnumUnboxedDiagnostic(this.a.a());
    }
}
