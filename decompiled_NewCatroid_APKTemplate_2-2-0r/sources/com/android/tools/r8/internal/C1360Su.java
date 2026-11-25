package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Su, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1360Su extends AbstractC0859Bj {
    private int b;
    private List c = Collections.emptyList();
    private List d = Collections.emptyList();

    private C1360Su() {
    }

    static C1360Su f() {
        return new C1360Su();
    }

    @Override // com.android.tools.r8.internal.A0
    public final B0 c() {
        C1516Xu c1516XuG = g();
        if (c1516XuG.a()) {
            return c1516XuG;
        }
        throw new C1659b10();
    }

    public final Object clone() {
        return new C1360Su().a(g());
    }

    public final C1516Xu g() {
        C1516Xu c1516Xu = new C1516Xu(this, 0);
        if ((this.b & 1) == 1) {
            this.c = Collections.unmodifiableList(this.c);
            this.b &= -2;
        }
        c1516Xu.b = this.c;
        if ((this.b & 2) == 2) {
            this.d = Collections.unmodifiableList(this.d);
            this.b &= -3;
        }
        c1516Xu.c = this.d;
        return c1516Xu;
    }

    @Override // com.android.tools.r8.internal.AbstractC0859Bj
    public final C1360Su a(C1516Xu c1516Xu) {
        if (c1516Xu == C1516Xu.g()) {
            return this;
        }
        if (!c1516Xu.b.isEmpty()) {
            if (this.c.isEmpty()) {
                this.c = c1516Xu.b;
                this.b &= -2;
            } else {
                if ((this.b & 1) != 1) {
                    this.c = new ArrayList(this.c);
                    this.b |= 1;
                }
                this.c.addAll(c1516Xu.b);
            }
        }
        if (!c1516Xu.c.isEmpty()) {
            if (this.d.isEmpty()) {
                this.d = c1516Xu.c;
                this.b &= -3;
            } else {
                if ((this.b & 2) != 2) {
                    this.d = new ArrayList(this.d);
                    this.b |= 2;
                }
                this.d.addAll(c1516Xu.c);
            }
        }
        a(e().b(c1516Xu.a));
        return this;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x001d  */
    @Override // com.android.tools.r8.internal.A0
    public final A0 a(C2396kb c2396kb, C2867qh c2867qh) throws Throwable {
        C1516Xu c1516Xu = null;
        try {
            try {
                a((C1516Xu) ((C1334Ru) C1516Xu.h).a(c2396kb, c2867qh));
                return this;
            } catch (C1054Hs e) {
                C1516Xu c1516Xu2 = (C1516Xu) e.a();
                try {
                    throw e;
                } catch (Throwable th) {
                    th = th;
                    c1516Xu = c1516Xu2;
                    if (c1516Xu != null) {
                        a(c1516Xu);
                    }
                    throw th;
                }
            }
        } catch (Throwable th2) {
            th = th2;
            if (c1516Xu != null) {
                a(c1516Xu);
            }
            throw th;
        }
    }

    public final void a(ArrayList arrayList) {
        if ((this.b & 1) != 1) {
            this.c = new ArrayList(this.c);
            this.b |= 1;
        }
        A0.a(arrayList, this.c);
    }

    public final void a(LinkedHashSet linkedHashSet) {
        if ((this.b & 2) != 2) {
            this.d = new ArrayList(this.d);
            this.b |= 2;
        }
        A0.a(linkedHashSet, this.d);
    }
}
