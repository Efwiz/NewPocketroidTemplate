package com.android.tools.r8.synthesis;

import com.android.tools.r8.internal.C1031Ha;
import com.android.tools.r8.internal.C1667b6;
import com.android.tools.r8.internal.T3;
import java.nio.charset.StandardCharsets;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class E extends T3 {
    static final /* synthetic */ boolean g = true;
    private final L.b d;
    private final String e;
    private final L f;

    public E(L.b bVar, String str, L l) {
        super("com.android.tools.r8.SynthesizedClassV2");
        this.d = bVar;
        this.e = str;
        this.f = l;
    }

    @Override // com.android.tools.r8.internal.T3
    protected final T3 a(C1031Ha c1031Ha, int i, int i2) {
        short sF = c1031Ha.f(i);
        int iF = c1031Ha.f(i + 2);
        int i3 = i + 4;
        byte[] bArr = new byte[iF];
        int i4 = 0;
        while (i4 < iF) {
            bArr[i4] = (byte) c1031Ha.c(i3);
            i4++;
            i3++;
        }
        if (g || sF >= 0) {
            return new E(this.f.a(sF), new String(bArr, StandardCharsets.UTF_8), this.f);
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.T3
    protected final C1667b6 a() {
        if (!g && (this.d.c() < 0 || this.d.c() > 32767)) {
            throw new AssertionError();
        }
        C1667b6 c1667b6 = new C1667b6();
        c1667b6.d(this.d.c());
        byte[] bytes = this.e.getBytes(StandardCharsets.UTF_8);
        c1667b6.d(bytes.length);
        c1667b6.a(bytes, 0, bytes.length);
        return c1667b6;
    }
}
