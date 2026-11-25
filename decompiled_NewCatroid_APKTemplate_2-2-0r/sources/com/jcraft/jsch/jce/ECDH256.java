package com.jcraft.jsch.jce;

import com.jcraft.jsch.ECDH;

/* JADX INFO: loaded from: classes10.dex */
public class ECDH256 extends ECDHN implements ECDH {
    public void init() throws Exception {
        super.init(256);
    }
}
