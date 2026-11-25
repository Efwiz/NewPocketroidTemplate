package org.bouncycastle.dvcs;

import org.bouncycastle.asn1.dvcs.Data;

/* JADX INFO: loaded from: classes13.dex */
public abstract class DVCSRequestData {
    protected Data data;

    protected DVCSRequestData(Data data) {
        this.data = data;
    }

    public Data toASN1Structure() {
        return this.data;
    }
}
