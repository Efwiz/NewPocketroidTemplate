package com.android.tools.r8.naming;

import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.utils.C3869a3;
import java.util.HashSet;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.naming.c0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class AbstractC3613c0 {
    static final /* synthetic */ boolean d = true;
    private final List a;
    private final HashSet b;
    private final C3869a3.a c;

    AbstractC3613c0(AbstractC1639am abstractC1639am, boolean z) {
        if (!d && abstractC1639am == null) {
            throw new AssertionError();
        }
        this.a = abstractC1639am;
        this.b = new HashSet(abstractC1639am);
        this.c = z ? C3869a3.a.b : C3869a3.a.a;
    }

    final String a(char[] cArr, I i) {
        return cArr + b(cArr, i);
    }

    String b(char[] cArr, I i) {
        String strA;
        do {
            if (i.b() < this.a.size()) {
                strA = (String) this.a.get(i.c());
            } else {
                do {
                    strA = C3869a3.a(i.a(), this.c);
                } while (this.b.contains(strA));
            }
        } while (C3869a3.a.contains(strA));
        return strA;
    }
}
