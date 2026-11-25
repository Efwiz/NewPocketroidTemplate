package com.android.tools.r8.internal;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: renamed from: com.android.tools.r8.internal.c1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC1732c1 implements InterfaceC3429yL {
    static {
        int i = C2867qh.b;
    }

    public final B0 a(InputStream inputStream, C2867qh c2867qh) throws C1054Hs {
        C2396kb c2396kbA = C2396kb.a(inputStream);
        B0 b0 = (B0) a(c2396kbA, c2867qh);
        try {
            c2396kbA.a(0);
            if (b0 == null || b0.a()) {
                return b0;
            }
            throw new C1054Hs(new C1659b10().getMessage()).a(b0);
        } catch (C1054Hs e) {
            throw e.a(b0);
        }
    }

    public final B0 a(ByteArrayInputStream byteArrayInputStream, C2867qh c2867qh) throws C1054Hs {
        B0 b0;
        try {
            int i = byteArrayInputStream.read();
            if (i == -1) {
                b0 = null;
            } else {
                if ((i & 128) != 0) {
                    i &= 127;
                    int i2 = 7;
                    while (true) {
                        if (i2 < 32) {
                            int i3 = byteArrayInputStream.read();
                            if (i3 == -1) {
                                throw C1054Hs.b();
                            }
                            i |= (i3 & 127) << i2;
                            if ((i3 & 128) == 0) {
                                break;
                            }
                            i2 += 7;
                        } else {
                            while (true) {
                                if (i2 < 64) {
                                    int i4 = byteArrayInputStream.read();
                                    if (i4 == -1) {
                                        throw C1054Hs.b();
                                    }
                                    if ((i4 & 128) == 0) {
                                        break;
                                    }
                                    i2 += 7;
                                } else {
                                    throw new C1054Hs("CodedInputStream encountered a malformed varint.");
                                }
                            }
                        }
                    }
                }
                C2396kb c2396kbA = C2396kb.a(new C3471z0(i, byteArrayInputStream));
                B0 b1 = (B0) a(c2396kbA, c2867qh);
                try {
                    c2396kbA.a(0);
                    b0 = b1;
                } catch (C1054Hs e) {
                    throw e.a(b1);
                }
            }
            if (b0 == null || b0.a()) {
                return b0;
            }
            throw new C1054Hs(new C1659b10().getMessage()).a(b0);
        } catch (IOException e2) {
            throw new C1054Hs(e2.getMessage());
        }
    }
}
