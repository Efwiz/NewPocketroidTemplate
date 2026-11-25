package com.android.tools.r8.dex;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import java.util.HashSet;
import java.util.Iterator;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class M {
    static final /* synthetic */ boolean e = true;
    final HashSet a = new HashSet();
    final HashSet b = new HashSet();
    final HashSet c = new HashSet();
    final /* synthetic */ P d;

    M(P p, N n) {
        this.d = p;
        int size = n.a.size();
        HashSet hashSet = new HashSet();
        Iterator it = n.a.iterator();
        while (it.hasNext()) {
            a((C0729q2) it.next(), hashSet);
        }
        Iterator it2 = hashSet.iterator();
        while (it2.hasNext()) {
            a((C0729q2) it2.next());
        }
        if (e) {
            return;
        }
        if (size != this.b.size() + this.c.size() + this.a.size()) {
            throw new AssertionError();
        }
    }

    private boolean a(C0729q2 c0729q2, HashSet hashSet) {
        boolean z = false;
        if (c0729q2 == null || this.a.contains(c0729q2)) {
            return false;
        }
        if (hashSet.contains(c0729q2) || this.d.b.b().contains(c0729q2)) {
            return true;
        }
        C0702m c0702m = this.d.g;
        C0767v2 c0767v2 = c0729q2.f;
        c0702m.getClass();
        if (a(C0729q2.a(c0767v2 == c0729q2.d ? c0729q2 : c0702m.a(c0767v2)), hashSet)) {
            z = true;
            break;
        }
        C0767v2[] c0767v2Arr = c0729q2.g.a;
        int length = c0767v2Arr.length;
        for (int i = 0; i < length; i++) {
            C0767v2 c0767v3 = c0767v2Arr[i];
            C0702m c0702m2 = this.d.g;
            c0702m2.getClass();
            if (a(C0729q2.a(c0767v3 == c0729q2.d ? c0729q2 : c0702m2.a(c0767v3)), hashSet)) {
                z = true;
                break;
            }
        }
        if (z) {
            hashSet.add(c0729q2);
        } else {
            this.a.add(c0729q2);
        }
        return z;
    }

    private boolean a(C0729q2 c0729q2) {
        boolean z = false;
        if (c0729q2 == null || this.b.contains(c0729q2)) {
            return false;
        }
        if (this.c.contains(c0729q2)) {
            return true;
        }
        if (this.d.b.b().contains(c0729q2)) {
            return false;
        }
        if (this.a.contains(c0729q2)) {
            return true;
        }
        C0702m c0702m = this.d.g;
        C0767v2 c0767v2 = c0729q2.f;
        c0702m.getClass();
        if (a(C0729q2.a(c0767v2 == c0729q2.d ? c0729q2 : c0702m.a(c0767v2)))) {
            z = true;
            break;
        }
        C0767v2[] c0767v2Arr = c0729q2.g.a;
        int length = c0767v2Arr.length;
        for (int i = 0; i < length; i++) {
            C0767v2 c0767v3 = c0767v2Arr[i];
            C0702m c0702m2 = this.d.g;
            c0702m2.getClass();
            if (a(C0729q2.a(c0767v3 == c0729q2.d ? c0729q2 : c0702m2.a(c0767v3)))) {
                z = true;
                break;
            }
        }
        if (z) {
            this.c.add(c0729q2);
        } else {
            this.b.add(c0729q2);
        }
        return z;
    }
}
