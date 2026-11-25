package com.android.tools.r8.graph;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.graph.o0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C0715o0 {
    static final /* synthetic */ boolean c = true;
    private ByteArrayOutputStream a;
    private ObjectOutputStream b;

    private C0715o0(C0721p0 c0721p0) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0759u2 c0759u2) throws IOException {
        this.b.writeInt(c0759u2.d);
        this.b.write(c0759u2.e);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0737r2 c0737r2) throws IOException {
        a(c0737r2.d);
        a(c0737r2.e.e);
        C0767v2[] c0767v2Arr = c0737r2.f.a;
        this.b.writeInt(c0767v2Arr.length);
        for (C0767v2 c0767v2 : c0767v2Arr) {
            a(c0767v2.e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(List list) throws IOException {
        this.b.writeInt(list.size());
        Iterator it = list.iterator();
        while (it.hasNext()) {
            B2 b2 = (B2) it.next();
            this.b.writeByte(b2.N0().a());
            switch (b2.N0().ordinal()) {
                case 3:
                    this.b.writeInt(b2.D0().b);
                    break;
                case 4:
                    this.b.writeLong(b2.E0().b);
                    break;
                case 5:
                    this.b.writeFloat(b2.C0().b);
                    break;
                case 6:
                    this.b.writeDouble(b2.z0().b);
                    break;
                case 7:
                    a((C0737r2) b2.H0().b);
                    break;
                case 8:
                    C0699l2 c0699l2 = (C0699l2) b2.G0().b;
                    this.b.writeShort(c0699l2.d.a());
                    if (c0699l2.d.b()) {
                        Y0 y0T0 = c0699l2.t0();
                        a(y0T0.e.e);
                        a(y0T0.h.e);
                        a(y0T0.f);
                    } else {
                        C0684j2 c0684j2U0 = c0699l2.u0();
                        a(c0684j2U0.e.e);
                        a(c0684j2U0.h);
                        a(c0684j2U0.f);
                    }
                    break;
                case 9:
                    a((C0759u2) b2.K0().b);
                    break;
                case 10:
                    a(((C0767v2) b2.L0().b).e);
                    break;
                default:
                    if (!c) {
                        throw new AssertionError();
                    }
                    break;
                    break;
            }
        }
    }
}
