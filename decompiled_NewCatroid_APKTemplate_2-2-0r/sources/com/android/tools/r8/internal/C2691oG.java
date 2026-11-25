package com.android.tools.r8.internal;

import java.util.LinkedList;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.oG, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2691oG implements InterfaceC2600nG {
    private final PO a;
    private final MO b;

    public C2691oG(PO po, MO mo) {
        this.a = po;
        this.b = mo;
    }

    private final C3219vZ d(int i) {
        LinkedList linkedList = new LinkedList();
        LinkedList linkedList2 = new LinkedList();
        boolean z = false;
        while (i != -1) {
            LO loA = this.b.a(i);
            String strA = this.a.a(loA.j());
            KO koH = loA.h();
            AbstractC0936Ds.a(koH);
            int iOrdinal = koH.ordinal();
            if (iOrdinal == 0) {
                linkedList2.addFirst(strA);
            } else if (iOrdinal == 1) {
                linkedList.addFirst(strA);
            } else if (iOrdinal == 2) {
                linkedList2.addFirst(strA);
                z = true;
            }
            i = loA.i();
        }
        return new C3219vZ(linkedList, linkedList2, Boolean.valueOf(z));
    }

    @Override // com.android.tools.r8.internal.InterfaceC2600nG
    public final String a(int i) {
        String strA = this.a.a(i);
        AbstractC0936Ds.b(strA, "strings.getString(index)");
        return strA;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2600nG
    public final boolean b(int i) {
        return ((Boolean) d(i).c()).booleanValue();
    }

    @Override // com.android.tools.r8.internal.InterfaceC2600nG
    public final String c(int i) {
        C3219vZ c3219vZD = d(i);
        List list = (List) c3219vZD.a();
        String strA = AbstractC0823Ab.a((List) c3219vZD.b(), ".", null, null, null, 62);
        return list.isEmpty() ? strA : AbstractC0823Ab.a(list, "/", null, null, null, 62) + '/' + strA;
    }
}
