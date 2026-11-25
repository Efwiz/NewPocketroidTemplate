package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0717o2;
import com.android.tools.r8.graph.C0722p1;
import java.util.ArrayList;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class HP {
    static final /* synthetic */ boolean c = true;
    private final ArrayList a = new ArrayList(2);
    private final Q4 b = new Q4();

    HP() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public AbstractC0717o2 a(AbstractC0717o2 abstractC0717o2, C0722p1 c0722p1, GP gp, boolean z) {
        AbstractC0717o2 abstractC0717o3;
        AbstractC0717o2 abstractC0717o4 = null;
        if (gp.b(abstractC0717o2)) {
            if (!c) {
                com.android.tools.r8.utils.O3 o3H = com.android.tools.r8.utils.O3.h(this);
                while (true) {
                    if (!o3H.c()) {
                        abstractC0717o3 = null;
                        break;
                    }
                    HP hp = (HP) o3H.f();
                    abstractC0717o3 = (AbstractC0717o2) hp.b.get(abstractC0717o2);
                    if (abstractC0717o3 != null) {
                        break;
                    }
                    o3H.a((Iterable) hp.a);
                }
                if (abstractC0717o3 != null) {
                    com.android.tools.r8.utils.O3 o3H2 = com.android.tools.r8.utils.O3.h(this);
                    while (o3H2.c()) {
                        HP hp2 = (HP) o3H2.f();
                        AbstractC0717o2 abstractC0717o5 = (AbstractC0717o2) hp2.b.get(abstractC0717o2);
                        if (abstractC0717o5 != null) {
                            abstractC0717o4 = abstractC0717o5;
                            break;
                        }
                        o3H2.a((Iterable) hp2.a);
                    }
                    if (!abstractC0717o2.equals(abstractC0717o4)) {
                        throw new AssertionError();
                    }
                }
            }
            return abstractC0717o2;
        }
        com.android.tools.r8.utils.O3 o3H3 = com.android.tools.r8.utils.O3.h(this);
        while (o3H3.c()) {
            HP hp3 = (HP) o3H3.f();
            AbstractC0717o2 abstractC0717o6 = (AbstractC0717o2) hp3.b.get(abstractC0717o2);
            if (abstractC0717o6 != null) {
                abstractC0717o4 = abstractC0717o6;
                break;
            }
            o3H3.a((Iterable) hp3.a);
        }
        if (abstractC0717o4 != null) {
            return abstractC0717o4;
        }
        AbstractC0717o2 abstractC0717o2A = abstractC0717o2.a(gp.a(abstractC0717o2), c0722p1);
        com.android.tools.r8.utils.O3 o3H4 = com.android.tools.r8.utils.O3.h(this);
        while (o3H4.c()) {
            HP hp4 = (HP) o3H4.f();
            if (hp4.b.containsValue(abstractC0717o2A)) {
                String string = abstractC0717o2.a().toString();
                int i = 1;
                loop4: while (true) {
                    abstractC0717o2A = abstractC0717o2A.a(c0722p1.c(string + "$" + i));
                    i++;
                    com.android.tools.r8.utils.O3 o3H5 = com.android.tools.r8.utils.O3.h(this);
                    while (true) {
                        if (!o3H5.c()) {
                            break;
                        }
                        HP hp5 = (HP) o3H5.f();
                        if (!hp5.b.containsValue(abstractC0717o2A)) {
                            o3H5.a((Iterable) hp5.a);
                        }
                    }
                }
            } else {
                o3H4.a((Iterable) hp4.a);
            }
        }
        if (z) {
            this.b.put(abstractC0717o2, abstractC0717o2A);
        }
        return abstractC0717o2A;
    }
}
