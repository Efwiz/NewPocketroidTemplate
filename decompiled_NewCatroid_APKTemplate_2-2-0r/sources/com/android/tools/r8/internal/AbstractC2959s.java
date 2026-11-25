package com.android.tools.r8.internal;

import java.nio.charset.Charset;

/* JADX INFO: renamed from: com.android.tools.r8.internal.s, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class AbstractC2959s implements InterfaceC3524zk {
    AbstractC2959s() {
    }

    @Override // com.android.tools.r8.internal.InterfaceC3524zk
    public final InterfaceC3524zk a(boolean z) {
        return a(z ? (byte) 1 : (byte) 0);
    }

    public abstract InterfaceC3524zk a(byte[] bArr, int i);

    @Override // com.android.tools.r8.internal.InterfaceC3524zk
    public final InterfaceC3524zk a(double d) {
        return a(Double.doubleToRawLongBits(d));
    }

    @Override // com.android.tools.r8.internal.InterfaceC3524zk
    public final InterfaceC3524zk a(float f) {
        return a(Float.floatToRawIntBits(f));
    }

    @Override // com.android.tools.r8.internal.InterfaceC3524zk
    public final InterfaceC3524zk a(CharSequence charSequence, Charset charset) {
        return a(charSequence.toString().getBytes(charset));
    }

    @Override // com.android.tools.r8.internal.InterfaceC3524zk
    public InterfaceC3524zk a(byte[] bArr) {
        return a(bArr, bArr.length);
    }
}
