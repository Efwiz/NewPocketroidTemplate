package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.nn, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2640nn {
    public static Set a(C0702m c0702m, C2409kl c2409kl) {
        C0827Af c0827Af = new C0827Af(c2409kl, 2);
        C2563mn c2563mn = new C2563mn(c0702m, c2409kl.h());
        for (C3332x4 c3332x4 : c0827Af.c()) {
            if (!c3332x4.D()) {
                Iterator<AbstractC2412ko> it = c3332x4.p().iterator();
                while (it.hasNext()) {
                    it.next().a(c2563mn);
                }
            }
        }
        return Collections.unmodifiableSet(c2563mn.c);
    }
}
