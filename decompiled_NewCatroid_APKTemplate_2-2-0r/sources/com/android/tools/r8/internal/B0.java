package com.android.tools.r8.internal;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class B0 implements InterfaceC3046tE {
    public abstract void a(C2547mb c2547mb);

    public final void a(OutputStream outputStream) throws IOException {
        int iC = c();
        if (iC > 4096) {
            iC = 4096;
        }
        C2547mb c2547mbA = C2547mb.a(outputStream, iC);
        a(c2547mbA);
        c2547mbA.b();
    }

    public abstract int c();

    public abstract A0 d();

    public abstract A0 e();

    public final byte[] f() {
        try {
            byte[] bArr = new byte[c()];
            C2547mb c2547mbA = C2547mb.a(bArr);
            a(c2547mbA);
            c2547mbA.a();
            return bArr;
        } catch (IOException e) {
            throw new RuntimeException("Serializing to a byte array threw an IOException (should never happen).", e);
        }
    }

    public final void a(ByteArrayOutputStream byteArrayOutputStream) throws IOException {
        int iC = c();
        int iB = C2547mb.b(iC) + iC;
        if (iB > 4096) {
            iB = 4096;
        }
        C2547mb c2547mbA = C2547mb.a(byteArrayOutputStream, iB);
        c2547mbA.g(iC);
        a(c2547mbA);
        c2547mbA.b();
    }
}
