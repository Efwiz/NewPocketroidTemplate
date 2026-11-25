package com.jcraft.jsch.jce;

import com.jcraft.jsch.Buffer;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.Signature;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

/* JADX INFO: loaded from: classes10.dex */
public class SignatureRSA implements com.jcraft.jsch.SignatureRSA {
    KeyFactory keyFactory;
    Signature signature;

    @Override // com.jcraft.jsch.Signature
    public void init() throws Exception {
        this.signature = Signature.getInstance("SHA1withRSA");
        this.keyFactory = KeyFactory.getInstance("RSA");
    }

    @Override // com.jcraft.jsch.SignatureRSA
    public void setPubKey(byte[] bArr, byte[] bArr2) throws Exception {
        this.signature.initVerify(this.keyFactory.generatePublic(new RSAPublicKeySpec(new BigInteger(bArr2), new BigInteger(bArr))));
    }

    @Override // com.jcraft.jsch.SignatureRSA
    public void setPrvKey(byte[] bArr, byte[] bArr2) throws Exception {
        this.signature.initSign(this.keyFactory.generatePrivate(new RSAPrivateKeySpec(new BigInteger(bArr2), new BigInteger(bArr))));
    }

    @Override // com.jcraft.jsch.Signature
    public byte[] sign() throws Exception {
        return this.signature.sign();
    }

    @Override // com.jcraft.jsch.Signature
    public void update(byte[] bArr) throws Exception {
        this.signature.update(bArr);
    }

    @Override // com.jcraft.jsch.Signature
    public boolean verify(byte[] bArr) throws Exception {
        Buffer buffer = new Buffer(bArr);
        if (new String(buffer.getString()).equals("ssh-rsa")) {
            int i = buffer.getInt();
            byte[] bArr2 = new byte[i];
            System.arraycopy(bArr, buffer.getOffSet(), bArr2, 0, i);
            bArr = bArr2;
        }
        return this.signature.verify(bArr);
    }
}
