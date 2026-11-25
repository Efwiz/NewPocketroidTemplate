package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.utils.C3970u0;

/* JADX INFO: renamed from: com.android.tools.r8.internal.y0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3402y0 {
    static final AbstractC2178hm b = AbstractC2178hm.e().a("public", 1).a("private", 2).a("protected", 4).a("final", 16).a("abstract", 1024).a("static", 8).a();
    final C0722p1 a;

    protected AbstractC3402y0(C0722p1 c0722p1) {
        this.a = c0722p1;
    }

    C0767v2 a(String str) {
        return this.a.e(C3970u0.H(str));
    }

    protected abstract void a(int i);

    final int a(String[] strArr) {
        int i = 0;
        while (true) {
            AbstractC2178hm abstractC2178hm = b;
            if (!abstractC2178hm.containsKey(strArr[i])) {
                return i;
            }
            a(((Integer) abstractC2178hm.get(strArr[i])).intValue());
            i++;
        }
    }
}
