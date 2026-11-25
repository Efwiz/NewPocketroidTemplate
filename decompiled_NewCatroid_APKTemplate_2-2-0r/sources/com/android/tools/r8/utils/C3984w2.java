package com.android.tools.r8.utils;

import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.OU;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.utils.w2, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public final class C3984w2 {
    private final Set a = OU.c();
    private final ArrayList b = new ArrayList();

    public final void a(C0767v2 c0767v2) {
        this.a.add(c0767v2);
    }

    public final boolean b(C0767v2 c0767v2) {
        if (this.a.contains(c0767v2)) {
            return true;
        }
        Iterator it = this.b.iterator();
        while (it.hasNext()) {
            if (((Predicate) it.next()).test(c0767v2)) {
                return true;
            }
        }
        return false;
    }

    public final void a(Predicate predicate) {
        this.b.add(predicate);
    }

    public static C3984w2 a() {
        return new C3984w2();
    }

    public final C3984w2 a(Function function) {
        C3984w2 c3984w2 = new C3984w2();
        Iterator it = this.a.iterator();
        while (it.hasNext()) {
            c3984w2.a.add(function.apply(it.next()));
        }
        c3984w2.b.addAll(this.b);
        return c3984w2;
    }
}
