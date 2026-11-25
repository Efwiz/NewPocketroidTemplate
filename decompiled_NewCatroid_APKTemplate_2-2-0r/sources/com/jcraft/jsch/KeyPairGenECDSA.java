package com.jcraft.jsch;

/* JADX INFO: loaded from: classes10.dex */
public interface KeyPairGenECDSA {
    byte[] getD();

    byte[] getR();

    byte[] getS();

    void init(int i) throws Exception;
}
