package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;

/* JADX INFO: renamed from: com.android.tools.r8.internal.vg, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C3226vg {
    static final /* synthetic */ boolean b = true;
    private final AbstractC2178hm a;

    public C3226vg(AbstractC2178hm abstractC2178hm) {
        this.a = abstractC2178hm;
    }

    public final C3150ug a(C0729q2 c0729q2) {
        C3150ug c3150ug = (C3150ug) this.a.get(c0729q2.getType());
        if (b || c3150ug != null) {
            return c3150ug;
        }
        throw new AssertionError();
    }

    public final boolean b(C0729q2 c0729q2) {
        return b(c0729q2.getType());
    }

    public boolean c() {
        return this.a.isEmpty();
    }

    public boolean b(C0767v2 c0767v2) {
        return this.a.containsKey(c0767v2);
    }

    public final boolean c(com.android.tools.r8.graph.Y0 y0) {
        if (b || this.a.containsKey(y0.e)) {
            return ((C3150ug) this.a.get(y0.e)).c.contains(y0);
        }
        throw new AssertionError();
    }

    public final AbstractC3303wg a(C0767v2 c0767v2, com.android.tools.r8.graph.Y0 y0) {
        if (b || this.a.containsKey(c0767v2)) {
            return ((C3150ug) this.a.get(c0767v2)).a(y0);
        }
        throw new AssertionError();
    }

    public final AbstractC1048Hm b() {
        return this.a.keySet();
    }

    public final boolean b(com.android.tools.r8.graph.Y0 y0) {
        return b(y0.e) && ((C3150ug) this.a.get(y0.e)).b.containsKey(y0);
    }

    public final int a(com.android.tools.r8.graph.Y0 y0) {
        if (!b && !this.a.containsKey(y0.e)) {
            throw new AssertionError();
        }
        C3150ug c3150ug = (C3150ug) this.a.get(y0.e);
        if (C3150ug.e || c3150ug.b.containsKey(y0)) {
            return ((Integer) c3150ug.b.get(y0)).intValue();
        }
        throw new AssertionError();
    }

    public final int a(C0767v2 c0767v2) {
        if (!b && !this.a.containsKey(c0767v2)) {
            throw new AssertionError();
        }
        C3150ug c3150ug = (C3150ug) this.a.get(c0767v2);
        if (!C3150ug.e && c3150ug.d == -1) {
            throw new AssertionError();
        }
        return c3150ug.d;
    }

    public final int a() {
        AbstractC2963s10 it = this.a.values().iterator();
        int iMax = 0;
        while (it.hasNext()) {
            int i = ((C3150ug) it.next()).d;
            if (i != -1) {
                if (!C3150ug.e && i == -1) {
                    throw new AssertionError();
                }
                iMax = Math.max(iMax, i);
            }
        }
        return iMax;
    }
}
