package com.jcraft.jsch;

/* JADX INFO: loaded from: classes10.dex */
public interface Signature {
    void init() throws Exception;

    byte[] sign() throws Exception;

    void update(byte[] bArr) throws Exception;

    boolean verify(byte[] bArr) throws Exception;
}
