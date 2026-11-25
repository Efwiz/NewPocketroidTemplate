package com.android.tools.r8.internal;

import java.util.Iterator;
import java.util.Set;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class U10 {
    private final S10 a;
    private final Set b;
    private final Set c;
    private final Set d;

    public U10(S10 s10) {
        Set setC = OU.c();
        this.b = setC;
        this.c = OU.c();
        this.d = OU.c();
        setC.add(s10);
        this.a = s10;
    }

    public final Set a() {
        return this.d;
    }

    public final boolean a(Predicate predicate) {
        Iterator it = this.d.iterator();
        while (it.hasNext()) {
            if (predicate.test((U10) it.next())) {
                return true;
            }
        }
        return false;
    }
}
