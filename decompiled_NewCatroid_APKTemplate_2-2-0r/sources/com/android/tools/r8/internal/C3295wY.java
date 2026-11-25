package com.android.tools.r8.internal;

import java.util.ArrayList;

/* JADX INFO: renamed from: com.android.tools.r8.internal.wY, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3295wY {
    final C3338xA a;
    final boolean[] b;
    final ArrayList c;

    C3295wY(C3338xA c3338xA, int i, C1871du c1871du) {
        this.a = c3338xA;
        this.b = new boolean[i];
        ArrayList arrayList = new ArrayList();
        this.c = arrayList;
        arrayList.add(c1871du);
    }

    public final boolean a(C3295wY c3295wY) {
        int i = 0;
        boolean z = false;
        while (true) {
            boolean[] zArr = this.b;
            if (i >= zArr.length) {
                break;
            }
            if (c3295wY.b[i] && !zArr[i]) {
                zArr[i] = true;
                z = true;
            }
            i++;
        }
        if (c3295wY.a == this.a) {
            for (int i2 = 0; i2 < c3295wY.c.size(); i2++) {
                C1871du c1871du = (C1871du) c3295wY.c.get(i2);
                if (!this.c.contains(c1871du)) {
                    this.c.add(c1871du);
                    z = true;
                }
            }
        }
        return z;
    }

    C3295wY(C3295wY c3295wY) {
        this.a = c3295wY.a;
        this.b = (boolean[]) c3295wY.b.clone();
        this.c = new ArrayList(c3295wY.c);
    }
}
