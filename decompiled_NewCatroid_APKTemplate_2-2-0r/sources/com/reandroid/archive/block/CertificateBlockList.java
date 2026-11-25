package com.reandroid.archive.block;

/* JADX INFO: loaded from: classes10.dex */
public class CertificateBlockList extends LengthPrefixedList<CertificateBlockV2> {
    public CertificateBlockList() {
        super(false);
    }

    @Override // com.reandroid.arsc.base.BlockCreator
    public CertificateBlockV2 newInstance() {
        return new CertificateBlockV2();
    }
}
