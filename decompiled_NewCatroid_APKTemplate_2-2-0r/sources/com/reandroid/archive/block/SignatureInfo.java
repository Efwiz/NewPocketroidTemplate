package com.reandroid.archive.block;

import com.reandroid.archive.block.pad.SchemePadding;
import com.reandroid.archive.block.stamp.SchemeStampV1;
import com.reandroid.archive.block.stamp.SchemeStampV2;
import com.reandroid.archive.block.v2.SchemeV2;
import com.reandroid.archive.block.v3.SchemeV3;
import com.reandroid.archive.block.v3.SchemeV31;
import com.reandroid.arsc.base.Block;
import com.reandroid.arsc.base.BlockRefresh;
import com.reandroid.arsc.container.SingleBlockContainer;
import com.reandroid.arsc.io.BlockLoad;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.arsc.item.IntegerItem;
import com.reandroid.utils.collection.EmptyIterator;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

/* JADX INFO: loaded from: classes10.dex */
public class SignatureInfo extends LengthPrefixedBlock implements BlockLoad {
    private final IntegerItem idItem;
    private final SingleBlockContainer<SignatureScheme> schemeContainer;

    public SignatureInfo() {
        super(2, true);
        IntegerItem integerItem = new IntegerItem();
        this.idItem = integerItem;
        SingleBlockContainer<SignatureScheme> singleBlockContainer = new SingleBlockContainer<>();
        this.schemeContainer = singleBlockContainer;
        addChild(integerItem);
        addChild(singleBlockContainer);
        integerItem.setBlockLoad(this);
    }

    public Iterator<CertificateBlock> getCertificates() {
        SignatureScheme signatureScheme = getSignatureScheme();
        if (signatureScheme != null) {
            return signatureScheme.getCertificates();
        }
        return EmptyIterator.of();
    }

    public int getIdValue() {
        return this.idItem.get();
    }

    public SignatureId getId() {
        return SignatureId.valueOf(getIdValue());
    }

    public void setId(int i) {
        this.idItem.set(i);
    }

    public void setId(SignatureId signatureId) {
        setId(signatureId == null ? 0 : signatureId.getId());
    }

    public SignatureScheme getSignatureScheme() {
        return (SignatureScheme) this.schemeContainer.getItem();
    }

    public void setSignatureScheme(SignatureScheme signatureScheme) {
        this.schemeContainer.setItem(signatureScheme);
    }

    @Override // com.reandroid.arsc.io.BlockLoad
    public void onBlockLoaded(BlockReader blockReader, Block block) throws IOException {
        if (block == this.idItem) {
            onIdLoaded();
        }
    }

    private void onIdLoaded() {
        BlockRefresh unknownScheme;
        SignatureId id2 = getId();
        if (id2 == SignatureId.V2) {
            unknownScheme = new SchemeV2();
        } else if (id2 == SignatureId.V3) {
            unknownScheme = new SchemeV3();
        } else if (id2 == SignatureId.V31) {
            unknownScheme = new SchemeV31();
        } else if (id2 == SignatureId.STAMP_V1) {
            unknownScheme = new SchemeStampV1();
        } else if (id2 == SignatureId.STAMP_V2) {
            unknownScheme = new SchemeStampV2();
        } else if (id2 == SignatureId.PADDING) {
            unknownScheme = new SchemePadding();
        } else {
            unknownScheme = new UnknownScheme(id2);
        }
        this.schemeContainer.setItem(unknownScheme);
    }

    public void writeRaw(File file) throws IOException {
        File parentFile = file.getParentFile();
        if (parentFile != null && !parentFile.exists()) {
            parentFile.mkdirs();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        writeBytes(fileOutputStream);
        fileOutputStream.close();
    }

    public File writeRawToDirectory(File file) throws IOException {
        File file2 = new File(file, getIndex() + "_" + getId().toFileName());
        writeRaw(file2);
        return file2;
    }

    public void read(File file) throws IOException {
        super.readBytes(new BlockReader(file));
    }

    @Override // com.reandroid.archive.block.LengthPrefixedBlock
    public String toString() {
        return getId() + ", scheme: " + getSignatureScheme();
    }
}
