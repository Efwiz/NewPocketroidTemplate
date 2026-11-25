package com.android.tools.r8.naming;

import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import java.util.Arrays;

/* JADX INFO: renamed from: com.android.tools.r8.naming.n0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3647n0 extends AbstractC3649o0 {
    static final /* synthetic */ boolean c = true;
    private final C0722p1 b;

    protected AbstractC3647n0(C0722p1 c0722p1) {
        this.b = c0722p1;
    }

    @Override // com.android.tools.r8.naming.AbstractC3649o0
    public final C0759u2 c(C0767v2 c0767v2) {
        if (c0767v2.W0() || c0767v2.Y0() || c0767v2.U0()) {
            return c0767v2.D0();
        }
        if (!c0767v2.M0()) {
            if (c || c0767v2.O0()) {
                return b(c0767v2);
            }
            throw new AssertionError();
        }
        C0759u2 c0759u2C = c(c0767v2.b(this.b));
        int iG0 = c0767v2.G0();
        C0722p1 c0722p1 = this.b;
        byte[] bArr = new byte[c0759u2C.e.length + iG0];
        Arrays.fill(bArr, 0, iG0, (byte) 91);
        byte[] bArr2 = c0759u2C.e;
        System.arraycopy(bArr2, 0, bArr, iG0, bArr2.length);
        return c0722p1.b(c0759u2C.d + iG0, bArr);
    }
}
