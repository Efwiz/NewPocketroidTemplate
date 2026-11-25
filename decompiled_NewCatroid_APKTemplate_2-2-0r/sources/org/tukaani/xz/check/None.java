package org.tukaani.xz.check;

/* JADX INFO: loaded from: classes6.dex */
public class None extends Check {
    public None() {
        this.size = 0;
        this.f1078name = "None";
    }

    @Override // org.tukaani.xz.check.Check
    public byte[] finish() {
        return new byte[0];
    }

    @Override // org.tukaani.xz.check.Check
    public void update(byte[] bArr, int i, int i2) {
    }
}
