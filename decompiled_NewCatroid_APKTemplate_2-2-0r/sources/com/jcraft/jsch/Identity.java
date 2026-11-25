package com.jcraft.jsch;

/* JADX INFO: loaded from: classes10.dex */
public interface Identity {
    void clear();

    boolean decrypt();

    String getAlgName();

    String getName();

    byte[] getPublicKeyBlob();

    byte[] getSignature(byte[] bArr);

    boolean isEncrypted();

    boolean setPassphrase(byte[] bArr) throws JSchException;
}
