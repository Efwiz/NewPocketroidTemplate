package com.android.tools.r8.internal;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ew, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1956ew extends AbstractC1116Jv {
    private final String a;
    private C1198Mw b;
    private C1463Vw.a c;

    private C1956ew(String str) {
        this.b = null;
        this.c = null;
        this.a = str;
    }

    final AbstractC2509m2 a(Consumer consumer, String str) {
        if (str.equals("methodAccess")) {
            C1198Mw c1198MwI = C1225Nw.i();
            this.b = c1198MwI;
            return new C1873dw(this.a, c1198MwI);
        }
        if (!str.equals("methodParameters")) {
            return null;
        }
        return new C2266iw(new Consumer() { // from class: com.android.tools.r8.internal.ew$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((List) obj);
            }
        }, this.a);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(List list) {
        AbstractC3024sx abstractC3024sxA;
        if (Arrays.asList(AbstractC2199i2.a).equals(list)) {
            return;
        }
        C1362Sw c1362SwC = AbstractC1437Uw.c();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            if (str.equals("<any>")) {
                abstractC3024sxA = AbstractC3024sx.a();
            } else {
                abstractC3024sxA = AbstractC3024sx.a(AbstractC2497lw.a(str));
            }
            c1362SwC.a(abstractC3024sxA);
        }
        AbstractC1437Uw abstractC1437UwA = c1362SwC.a();
        if (this.c == null) {
            this.c = C1463Vw.k();
        }
        this.c.a(abstractC1437UwA);
    }
}
