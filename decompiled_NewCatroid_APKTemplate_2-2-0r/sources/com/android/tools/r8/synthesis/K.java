package com.android.tools.r8.synthesis;

import com.android.tools.r8.internal.C3405y10;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class K {
    private int a;
    private ArrayList b;

    private K() {
        this.a = 1;
        this.b = new ArrayList();
    }

    private L.b a(L.b bVar) {
        this.b.add(bVar);
        if (this.b.size() == bVar.c()) {
            return bVar;
        }
        throw new C3405y10("Invalid synthetic kind id: " + bVar.c());
    }

    private int d() {
        int i = this.a;
        this.a = i + 1;
        return i;
    }

    final L.b b(String str) {
        return a(new M(d(), str, true));
    }

    final L.b c(String str) {
        return a(new M(d(), str, false));
    }

    final L.b e(String str) {
        return a(new O(d(), str, true));
    }

    final L.b b() {
        return a(new N(d(), "", false));
    }

    final List c() {
        ArrayList arrayList = this.b;
        this.b = null;
        return arrayList;
    }

    final L.b d(String str) {
        return a(new O(d(), str, false));
    }

    final L.b a(String str) {
        return a(new N(d(), str, false));
    }

    final L.b a() {
        return a(new N(d(), "", true));
    }
}
