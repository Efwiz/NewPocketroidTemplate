package org.tukaani.xz.check;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.bouncycastle.pqc.jcajce.spec.McElieceCCA2KeyGenParameterSpec;

/* JADX INFO: loaded from: classes6.dex */
public class SHA256 extends Check {
    private final MessageDigest sha256;

    public SHA256() throws NoSuchAlgorithmException {
        this.size = 32;
        this.f1078name = McElieceCCA2KeyGenParameterSpec.SHA256;
        this.sha256 = MessageDigest.getInstance(McElieceCCA2KeyGenParameterSpec.SHA256);
    }

    @Override // org.tukaani.xz.check.Check
    public byte[] finish() {
        byte[] bArrDigest = this.sha256.digest();
        this.sha256.reset();
        return bArrDigest;
    }

    @Override // org.tukaani.xz.check.Check
    public void update(byte[] bArr, int i, int i2) {
        this.sha256.update(bArr, i, i2);
    }
}
