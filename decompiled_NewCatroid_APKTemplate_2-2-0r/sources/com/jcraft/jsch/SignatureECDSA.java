package com.jcraft.jsch;

/* JADX INFO: loaded from: classes10.dex */
public interface SignatureECDSA extends Signature {
    void setPrvKey(byte[] bArr) throws Exception;

    void setPubKey(byte[] bArr, byte[] bArr2) throws Exception;
}
