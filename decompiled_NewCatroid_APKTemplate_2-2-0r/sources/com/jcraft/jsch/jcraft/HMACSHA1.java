package com.jcraft.jsch.jcraft;

import com.jcraft.jsch.MAC;
import java.security.MessageDigest;
import org.bouncycastle.pqc.jcajce.spec.McElieceCCA2KeyGenParameterSpec;

/* JADX INFO: loaded from: classes10.dex */
public class HMACSHA1 extends HMAC implements MAC {

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private static final String f211name = "hmac-sha1";

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

    public HMACSHA1() {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance(McElieceCCA2KeyGenParameterSpec.SHA1);
        } catch (Exception e) {
            System.err.println(e);
            messageDigest = null;
        }
        setH(messageDigest);
    }

    @Override // com.jcraft.jsch.MAC
    public String getName() {
        return f211name;
    }
}
