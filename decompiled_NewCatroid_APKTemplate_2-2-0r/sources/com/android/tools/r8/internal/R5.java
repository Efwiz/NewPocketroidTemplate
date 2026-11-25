package com.android.tools.r8.internal;

import java.io.ByteArrayOutputStream;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class R5 implements InterfaceC1741c6 {
    static final /* synthetic */ boolean b = true;
    private final ByteArrayOutputStream a = new ByteArrayOutputStream();

    @Override // com.android.tools.r8.internal.InterfaceC1741c6
    public final void a(int i) {
        if (!b) {
            int i2 = C1589a6.b;
            if (i < 0 || i > 255) {
                throw new AssertionError();
            }
        }
        this.a.write(i);
    }

    public final byte[] a() {
        return this.a.toByteArray();
    }
}
