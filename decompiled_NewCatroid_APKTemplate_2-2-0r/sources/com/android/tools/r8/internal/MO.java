package com.android.tools.r8.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class MO extends AbstractC1045Hj {
    private static final MO e;
    public static InterfaceC3429yL f = new GO();
    private final Y5 a;
    private List b;
    private byte c;
    private int d;

    static {
        MO mo = new MO();
        e = mo;
        mo.b = Collections.emptyList();
    }

    /* synthetic */ MO(C2396kb c2396kb, C2867qh c2867qh, int i) {
        this(c2396kb, c2867qh);
    }

    public static MO g() {
        return e;
    }

    private void h() {
        this.b = Collections.emptyList();
    }

    @Override // com.android.tools.r8.internal.B0
    public final int c() {
        int i = this.d;
        if (i != -1) {
            return i;
        }
        int iA = 0;
        for (int i2 = 0; i2 < this.b.size(); i2++) {
            iA += C2547mb.a(1, (B0) this.b.get(i2));
        }
        int size = this.a.size() + iA;
        this.d = size;
        return size;
    }

    @Override // com.android.tools.r8.internal.B0
    public final A0 d() {
        return HO.f();
    }

    @Override // com.android.tools.r8.internal.B0
    public final A0 e() {
        return HO.f().a(this);
    }

    public final HO i() {
        return HO.f().a(this);
    }

    /* synthetic */ MO(AbstractC0859Bj abstractC0859Bj, int i) {
        this(abstractC0859Bj);
    }

    private MO(AbstractC0859Bj abstractC0859Bj) {
        super(0);
        this.c = (byte) -1;
        this.d = -1;
        this.a = abstractC0859Bj.e();
    }

    public final LO a(int i) {
        return (LO) this.b.get(i);
    }

    @Override // com.android.tools.r8.internal.InterfaceC3046tE
    public final boolean a() {
        byte b = this.c;
        if (b == 1) {
            return true;
        }
        if (b == 0) {
            return false;
        }
        for (int i = 0; i < this.b.size(); i++) {
            if (!a(i).a()) {
                this.c = (byte) 0;
                return false;
            }
        }
        this.c = (byte) 1;
        return true;
    }

    public static HO c(MO mo) {
        return HO.f().a(mo);
    }

    @Override // com.android.tools.r8.internal.B0
    public final void a(C2547mb c2547mb) throws IOException {
        c();
        for (int i = 0; i < this.b.size(); i++) {
            c2547mb.b(1, (B0) this.b.get(i));
        }
        c2547mb.b(this.a);
    }

    private MO() {
        this.c = (byte) -1;
        this.d = -1;
        this.a = Y5.a;
    }

    private MO(C2396kb c2396kb, C2867qh c2867qh) {
        this.c = (byte) -1;
        this.d = -1;
        h();
        X5 x5D = Y5.d();
        C2547mb c2547mbA = C2547mb.a(x5D, 1);
        boolean z = false;
        boolean z2 = false;
        while (!z) {
            try {
                try {
                    try {
                        int iN = c2396kb.n();
                        if (iN != 0) {
                            if (iN != 10) {
                                if (!a(c2396kb, c2547mbA, c2867qh, iN)) {
                                }
                            } else {
                                if (!z2) {
                                    this.b = new ArrayList();
                                    z2 = true;
                                }
                                this.b.add(c2396kb.a((AbstractC1732c1) LO.i, c2867qh));
                            }
                        }
                        z = true;
                    } catch (IOException e2) {
                        throw new C1054Hs(e2.getMessage()).a(this);
                    }
                } catch (C1054Hs e3) {
                    throw e3.a(this);
                }
            } catch (Throwable th) {
                if (z2) {
                    this.b = Collections.unmodifiableList(this.b);
                }
                try {
                    c2547mbA.b();
                } catch (IOException unused) {
                } finally {
                    this.a = x5D.c();
                }
                throw th;
            }
        }
        if (z2) {
            this.b = Collections.unmodifiableList(this.b);
        }
        try {
            c2547mbA.b();
        } catch (IOException unused2) {
        } finally {
            this.a = x5D.c();
        }
    }
}
