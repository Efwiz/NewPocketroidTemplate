package com.jcraft.jsch.jcraft;

import com.jcraft.jsch.MAC;
import java.security.MessageDigest;

/* JADX INFO: loaded from: classes10.dex */
public class HMACMD5 extends HMAC implements MAC {

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private static final String f209name = "hmac-md5";

    @Override // com.jcraft.jsch.jcraft.HMAC, com.jcraft.jsch.MAC
    public /* bridge */ /* synthetic */ void doFinal(byte[] bArr, int i) {
        super.doFinal(bArr, i);
    }

    @Override // com.jcraft.jsch.jcraft.HMAC, com.jcraft.jsch.MAC
    public /* bridge */ /* synthetic */ int getBlockSize() {
        return super.getBlockSize();
    }

    @Override // com.jcraft.jsch.jcraft.HMAC, com.jcraft.jsch.MAC
    public /* bridge */ /* synthetic */ void init(byte[] bArr) throws Exception {
        super.init(bArr);
    }

    @Override // com.jcraft.jsch.jcraft.HMAC, com.jcraft.jsch.MAC
    public /* bridge */ /* synthetic */ void update(int i) {
        super.update(i);
    }

    @Override // com.jcraft.jsch.jcraft.HMAC, com.jcraft.jsch.MAC
    public /* bridge */ /* synthetic */ void update(byte[] bArr, int i, int i2) {
        super.update(bArr, i, i2);
    }

    public HMACMD5() {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.err.println(e);
            messageDigest = null;
        }
        setH(messageDigest);
    }

    @Override // com.jcraft.jsch.MAC
    public String getName() {
        return f209name;
    }
}
