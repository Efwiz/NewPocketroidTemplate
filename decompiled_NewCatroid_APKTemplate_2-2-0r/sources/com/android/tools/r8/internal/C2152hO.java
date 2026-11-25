package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.hO, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2152hO extends AbstractC0859Bj {
    private int b;
    private List c = Collections.emptyList();

    private C2152hO() {
    }

    static C2152hO f() {
        return new C2152hO();
    }

    @Override // com.android.tools.r8.internal.A0
    public final B0 c() {
        C2232iO c2232iOG = g();
        if (c2232iOG.a()) {
            return c2232iOG;
        }
        throw new C1659b10();
    }

    public final Object clone() {
        return new C2152hO().a(g());
    }

    public final C2232iO g() {
        C2232iO c2232iO = new C2232iO(this, 0);
        if ((this.b & 1) == 1) {
            this.c = Collections.unmodifiableList(this.c);
            this.b &= -2;
        }
        c2232iO.b = this.c;
        return c2232iO;
    }

    @Override // com.android.tools.r8.internal.AbstractC0859Bj
    public final C2152hO a(C2232iO c2232iO) {
        if (c2232iO == C2232iO.g()) {
            return this;
        }
        if (!c2232iO.b.isEmpty()) {
            if (this.c.isEmpty()) {
                this.c = c2232iO.b;
                this.b &= -2;
            } else {
                if ((this.b & 1) != 1) {
                    this.c = new ArrayList(this.c);
                    this.b |= 1;
                }
                this.c.addAll(c2232iO.b);
            }
        }
        a(e().b(c2232iO.a));
        return this;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x001d  */
    @Override // com.android.tools.r8.internal.A0
    public final A0 a(C2396kb c2396kb, C2867qh c2867qh) throws Throwable {
        C2232iO c2232iO = null;
        try {
            try {
                a((C2232iO) ((C2074gO) C2232iO.f).a(c2396kb, c2867qh));
                return this;
            } catch (C1054Hs e) {
                C2232iO c2232iO2 = (C2232iO) e.a();
                try {
                    throw e;
                } catch (Throwable th) {
                    th = th;
                    c2232iO = c2232iO2;
                    if (c2232iO != null) {
                        a(c2232iO);
                    }
                    throw th;
                }
            }
        } catch (Throwable th2) {
            th = th2;
            if (c2232iO != null) {
                a(c2232iO);
            }
            throw th;
        }
    }

    public final void a(C2380kO c2380kO) {
        if ((this.b & 1) != 1) {
            this.c = new ArrayList(this.c);
            this.b |= 1;
        }
        List list = this.c;
        C2608nO c2608nOG = c2380kO.g();
        if (c2608nOG.a()) {
            list.add(c2608nOG);
            return;
        }
        throw new C1659b10();
    }
}
