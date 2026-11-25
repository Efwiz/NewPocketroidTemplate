package com.reandroid.archive.block;

import com.reandroid.archive.block.pad.SchemePadding;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.utils.collection.ArrayCollection;
import com.reandroid.utils.collection.IterableIterator;
import com.reandroid.utils.io.FileUtil;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public class ApkSignatureBlock extends LengthPrefixedList<SignatureInfo> implements Comparator<SignatureInfo> {
    public static final String FILE_EXT = ".sig";

    public ApkSignatureBlock(SignatureFooter signatureFooter) {
        super(true);
        setBottomBlock(signatureFooter);
    }

    public ApkSignatureBlock() {
        this(new SignatureFooter());
    }

    public Iterator<CertificateBlock> getCertificates() {
        return new IterableIterator<SignatureInfo, CertificateBlock>(iterator()) { // from class: com.reandroid.archive.block.ApkSignatureBlock.1
            @Override // com.reandroid.utils.collection.IterableIterator
            public Iterator<CertificateBlock> iterator(SignatureInfo signatureInfo) {
                return signatureInfo.getCertificates();
            }
        };
    }

    public void sortSignatures() {
        sort(this);
    }

    public void updatePadding() {
        SchemePadding orCreateSchemePadding = getOrCreateSchemePadding();
        orCreateSchemePadding.setPadding(0);
        sortSignatures();
        refresh();
        orCreateSchemePadding.setPadding((4096 - (countBytes() % 4096)) % 4096);
        refresh();
    }

    private SchemePadding getOrCreateSchemePadding() {
        SignatureInfo signature = getSignature(SignatureId.PADDING);
        if (signature == null) {
            signature = new SignatureInfo();
            signature.setId(SignatureId.PADDING);
            signature.setSignatureScheme(new SchemePadding());
            add(signature);
        }
        SignatureScheme signatureScheme = signature.getSignatureScheme();
        if (!(signatureScheme instanceof SchemePadding)) {
            signatureScheme = new SchemePadding();
            signature.setSignatureScheme(signatureScheme);
        }
        return (SchemePadding) signatureScheme;
    }

    public SignatureInfo getSignature(SignatureId signatureId) {
        for (SignatureInfo signatureInfo : this) {
            if (signatureInfo.getId().equals(signatureId)) {
                return signatureInfo;
            }
        }
        return null;
    }

    public SignatureFooter getSignatureFooter() {
        return (SignatureFooter) getBottomBlock();
    }

    @Override // com.reandroid.arsc.base.BlockCreator
    public SignatureInfo newInstance() {
        return new SignatureInfo();
    }

    @Override // com.reandroid.archive.block.LengthPrefixedList, com.reandroid.arsc.container.FixedBlockContainer, com.reandroid.arsc.base.BlockContainer
    protected void onRefreshed() {
        SignatureFooter signatureFooter = getSignatureFooter();
        signatureFooter.updateMagic();
        super.onRefreshed();
        signatureFooter.setSignatureSize(getDataSize());
    }

    public void writeRaw(File file) throws IOException {
        refresh();
        OutputStream outputStream = FileUtil.outputStream(file);
        writeBytes(outputStream);
        outputStream.close();
    }

    public List<File> writeSplitRawToDirectory(File file) throws IOException {
        refresh();
        ArrayCollection arrayCollection = new ArrayCollection(size());
        Iterator<SignatureInfo> it = iterator();
        while (it.hasNext()) {
            arrayCollection.add(it.next().writeRawToDirectory(file));
        }
        return arrayCollection;
    }

    public void read(File file) throws IOException {
        super.readBytes(new BlockReader(file));
    }

    public void scanSplitFiles(File file) throws IOException {
        if (!file.isDirectory()) {
            throw new IOException("No such directory");
        }
        File[] fileArrListFiles = file.listFiles(new FileFilter() { // from class: com.reandroid.archive.block.ApkSignatureBlock.2
            @Override // java.io.FileFilter
            public boolean accept(File file2) {
                if (file2.isFile()) {
                    return file2.getName().toLowerCase().endsWith(SignatureId.FILE_EXT_RAW);
                }
                return false;
            }
        });
        if (fileArrListFiles == null) {
            return;
        }
        for (File file2 : fileArrListFiles) {
            addSplitRaw(file2);
        }
        sortSignatures();
    }

    public SignatureInfo addSplitRaw(File file) throws IOException {
        SignatureInfo signatureInfo = new SignatureInfo();
        signatureInfo.read(file);
        add(signatureInfo);
        return signatureInfo;
    }

    @Override // java.util.Comparator
    public int compare(SignatureInfo signatureInfo, SignatureInfo signatureInfo2) {
        return signatureInfo.getId().compareTo(signatureInfo2.getId());
    }
}
