package com.jcraft.jsch.jcraft;

/* JADX INFO: loaded from: classes10.dex */
public class HMACSHA196 extends HMACSHA1 {
    private static final int BSIZE = 12;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private static final String f212name = "hmac-sha1-96";
    private final byte[] _buf16 = new byte[20];

    @Override // com.jcraft.jsch.jcraft.HMACSHA1, com.jcraft.jsch.jcraft.HMAC, com.jcraft.jsch.MAC
    public int getBlockSize() {
        return 12;
    }

    @Override // com.jcraft.jsch.jcraft.HMACSHA1, com.jcraft.jsch.jcraft.HMAC, com.jcraft.jsch.MAC
    public void doFinal(byte[] bArr, int i) {
        super.doFinal(this._buf16, 0);
        System.arraycopy(this._buf16, 0, bArr, i, 12);
    }

    @Override // com.jcraft.jsch.jcraft.HMACSHA1, com.jcraft.jsch.MAC
    public String getName() {
        return f212name;
    }
}
