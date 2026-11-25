package com.jcraft.jsch.jce;

/* JADX INFO: loaded from: classes10.dex */
public class HMACSHA512 extends HMAC {
    @Override // com.jcraft.jsch.jce.HMAC, com.jcraft.jsch.MAC
    public /* bridge */ /* synthetic */ void doFinal(byte[] bArr, int i) {
        super.doFinal(bArr, i);
    }

    @Override // com.jcraft.jsch.jce.HMAC, com.jcraft.jsch.MAC
    public /* bridge */ /* synthetic */ int getBlockSize() {
        return super.getBlockSize();
    }

    @Override // com.jcraft.jsch.jce.HMAC, com.jcraft.jsch.MAC
    public /* bridge */ /* synthetic */ String getName() {
        return super.getName();
    }

    @Override // com.jcraft.jsch.jce.HMAC, com.jcraft.jsch.MAC
    public /* bridge */ /* synthetic */ void init(byte[] bArr) throws Exception {
        super.init(bArr);
    }

    @Override // com.jcraft.jsch.jce.HMAC, com.jcraft.jsch.MAC
    public /* bridge */ /* synthetic */ void update(int i) {
        super.update(i);
    }

    @Override // com.jcraft.jsch.jce.HMAC, com.jcraft.jsch.MAC
    public /* bridge */ /* synthetic */ void update(byte[] bArr, int i, int i2) {
        super.update(bArr, i, i2);
    }

    public HMACSHA512() {
        this.f208name = "hmac-sha2-512";
        this.bsize = 64;
        this.algorithm = "HmacSHA512";
    }
}
