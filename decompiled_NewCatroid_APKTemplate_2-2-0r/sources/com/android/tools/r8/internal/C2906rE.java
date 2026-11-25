package com.android.tools.r8.internal;

import java.security.MessageDigest;
import java.util.Arrays;

/* JADX INFO: renamed from: com.android.tools.r8.internal.rE, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2906rE extends AbstractC2427l {
    private final MessageDigest b;
    private final int c;
    private boolean d;

    /* synthetic */ C2906rE(MessageDigest messageDigest, int i, int i2) {
        this(messageDigest, i);
    }

    @Override // com.android.tools.r8.internal.InterfaceC3524zk
    public final AbstractC3154uk a() {
        AbstractC3123uM.b("Cannot re-use a Hasher after calling hash() on it", !this.d);
        this.d = true;
        if (this.c == this.b.getDigestLength()) {
            byte[] bArrDigest = this.b.digest();
            int i = AbstractC3154uk.b;
            return new C3081tk(bArrDigest);
        }
        byte[] bArrCopyOf = Arrays.copyOf(this.b.digest(), this.c);
        int i2 = AbstractC3154uk.b;
        return new C3081tk(bArrCopyOf);
    }

    @Override // com.android.tools.r8.internal.AbstractC2427l
    protected final void b(byte b) {
        AbstractC3123uM.b("Cannot re-use a Hasher after calling hash() on it", !this.d);
        this.b.update(b);
    }

    private C2906rE(MessageDigest messageDigest, int i) {
        this.b = messageDigest;
        this.c = i;
    }

    @Override // com.android.tools.r8.internal.AbstractC2427l
    protected final void b(byte[] bArr, int i) {
        AbstractC3123uM.b("Cannot re-use a Hasher after calling hash() on it", !this.d);
        this.b.update(bArr, 0, i);
    }
}
