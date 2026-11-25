package com.android.tools.r8.graph;

import com.android.tools.r8.utils.InterfaceC3900g3;
import java.io.UTFDataFormatException;
import java.util.NoSuchElementException;

/* JADX INFO: renamed from: com.android.tools.r8.graph.t2, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C0750t2 implements InterfaceC3900g3 {
    static final /* synthetic */ boolean c = true;
    private int a = 0;
    final /* synthetic */ C0759u2 b;

    C0750t2(C0759u2 c0759u2) {
        this.b = c0759u2;
    }

    @Override // com.android.tools.r8.utils.InterfaceC3900g3
    public final char a() throws UTFDataFormatException {
        byte b;
        int i;
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        byte[] bArr = this.b.e;
        int i2 = this.a;
        int i3 = i2 + 1;
        this.a = i3;
        char c2 = (char) (bArr[i2] & 255);
        if (!c && c2 == 0) {
            throw new AssertionError();
        }
        if (c2 < 128) {
            return c2;
        }
        if ((c2 & 224) == 192) {
            this.a = i2 + 2;
            b = bArr[i3];
            if ((b & 192) != 128) {
                throw new UTFDataFormatException("bad second byte");
            }
            i = (c2 & 31) << 6;
        } else {
            if ((c2 & 240) != 224) {
                throw new UTFDataFormatException("bad byte");
            }
            byte b2 = bArr[i3];
            this.a = i2 + 3;
            b = bArr[i2 + 2];
            if ((b2 & 192) != 128 || (b & 192) != 128) {
                throw new UTFDataFormatException("bad second or third byte");
            }
            i = ((c2 & 15) << 12) | ((b2 & 63) << 6);
        }
        return (char) ((b & 63) | i);
    }

    @Override // com.android.tools.r8.utils.InterfaceC3900g3
    public final boolean hasNext() {
        int i = this.a;
        byte[] bArr = this.b.e;
        return i < bArr.length && (bArr[i] & 255) != 0;
    }
}
