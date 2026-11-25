package com.reandroid.archive.block;

import com.reandroid.arsc.io.BlockReader;
import com.reandroid.arsc.item.ByteArray;
import com.reandroid.utils.collection.EmptyIterator;
import java.io.IOException;
import java.util.Iterator;

/* JADX INFO: loaded from: classes10.dex */
public class UnknownScheme extends SignatureScheme {
    private final ByteArray byteArray;

    public UnknownScheme(SignatureId signatureId) {
        super(1, signatureId);
        ByteArray byteArray = new ByteArray();
        this.byteArray = byteArray;
        addChild(byteArray);
    }

    @Override // com.reandroid.arsc.base.BlockContainer, com.reandroid.arsc.base.Block
    public void onReadBytes(BlockReader blockReader) throws IOException {
        this.byteArray.setSize(((int) getSignatureInfo().getDataSize()) - 4);
        super.onReadBytes(blockReader);
    }

    @Override // com.reandroid.archive.block.SignatureScheme
    public Iterator<CertificateBlock> getCertificates() {
        return EmptyIterator.of();
    }
}
