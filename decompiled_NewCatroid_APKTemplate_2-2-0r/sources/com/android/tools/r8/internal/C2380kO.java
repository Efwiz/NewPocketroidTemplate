package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.kO, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2380kO extends AbstractC0859Bj {
    private int b;
    private EnumC2460lO c = EnumC2460lO.b;
    private List d = Collections.emptyList();
    private C3125uO e = C3125uO.i();
    private EnumC2534mO f = EnumC2534mO.b;

    private C2380kO() {
    }

    static C2380kO f() {
        return new C2380kO();
    }

    @Override // com.android.tools.r8.internal.A0
    public final B0 c() {
        C2608nO c2608nOG = g();
        if (c2608nOG.a()) {
            return c2608nOG;
        }
        throw new C1659b10();
    }

    public final Object clone() {
        return new C2380kO().a(g());
    }

    public final C2608nO g() {
        C2608nO c2608nO = new C2608nO(this, 0);
        int i = this.b;
        int i2 = (i & 1) == 1 ? 1 : 0;
        c2608nO.c = this.c;
        if ((this.b & 2) == 2) {
            this.d = Collections.unmodifiableList(this.d);
            this.b &= -3;
        }
        c2608nO.d = this.d;
        if ((i & 4) == 4) {
            i2 |= 2;
        }
        c2608nO.e = this.e;
        if ((i & 8) == 8) {
            i2 |= 4;
        }
        c2608nO.f = this.f;
        c2608nO.b = i2;
        return c2608nO;
    }

    @Override // com.android.tools.r8.internal.AbstractC0859Bj
    public final C2380kO a(C2608nO c2608nO) {
        if (c2608nO == C2608nO.h()) {
            return this;
        }
        if (c2608nO.m()) {
            a(c2608nO.j());
        }
        if (!c2608nO.d.isEmpty()) {
            if (this.d.isEmpty()) {
                this.d = c2608nO.d;
                this.b &= -3;
            } else {
                if ((this.b & 2) != 2) {
                    this.d = new ArrayList(this.d);
                    this.b |= 2;
                }
                this.d.addAll(c2608nO.d);
            }
        }
        if (c2608nO.l()) {
            C3125uO c3125uOG = c2608nO.g();
            if ((this.b & 4) != 4 || this.e == C3125uO.i()) {
                this.e = c3125uOG;
            } else {
                this.e = C2987sO.f().a(this.e).a(c3125uOG).g();
            }
            this.b |= 4;
        }
        if (c2608nO.n()) {
            a(c2608nO.k());
        }
        a(e().b(c2608nO.a));
        return this;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x001d  */
    @Override // com.android.tools.r8.internal.A0
    public final A0 a(C2396kb c2396kb, C2867qh c2867qh) throws Throwable {
        C2608nO c2608nO = null;
        try {
            try {
                a((C2608nO) ((C2302jO) C2608nO.j).a(c2396kb, c2867qh));
                return this;
            } catch (C1054Hs e) {
                C2608nO c2608nO2 = (C2608nO) e.a();
                try {
                    throw e;
                } catch (Throwable th) {
                    th = th;
                    c2608nO = c2608nO2;
                    if (c2608nO != null) {
                        a(c2608nO);
                    }
                    throw th;
                }
            }
        } catch (Throwable th2) {
            th = th2;
            if (c2608nO != null) {
                a(c2608nO);
            }
            throw th;
        }
    }

    public final void a(EnumC2460lO enumC2460lO) {
        enumC2460lO.getClass();
        this.b |= 1;
        this.c = enumC2460lO;
    }

    public final void a(C2987sO c2987sO) {
        if ((this.b & 2) != 2) {
            this.d = new ArrayList(this.d);
            this.b |= 2;
        }
        List list = this.d;
        C3125uO c3125uOG = c2987sO.g();
        if (c3125uOG.a()) {
            list.add(c3125uOG);
            return;
        }
        throw new C1659b10();
    }

    public final void a(C3125uO c3125uO) {
        this.e = c3125uO;
        this.b |= 4;
    }

    public final void a(EnumC2534mO enumC2534mO) {
        enumC2534mO.getClass();
        this.b |= 8;
        this.f = enumC2534mO;
    }
}
