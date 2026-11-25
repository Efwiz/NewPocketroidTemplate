package com.reandroid.archive.block;

import com.reandroid.archive.ZipSignature;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes10.dex */
public class LocalFileHeader extends CommonHeader {
    private static final int OFFSET_compressed_size = 18;
    private static final int OFFSET_crc = 14;
    private static final int OFFSET_dos_time = 10;
    private static final int OFFSET_extraLength = 28;
    private static final int OFFSET_fileName = 30;
    private static final int OFFSET_fileNameLength = 26;
    private static final int OFFSET_general_purpose = 6;
    private static final int OFFSET_method = 8;
    private static final int OFFSET_platform = 5;
    private static final int OFFSET_signature = 0;
    private static final int OFFSET_size = 22;
    private static final int OFFSET_versionMadeBy = 4;
    private CentralEntryHeader centralEntryHeader;
    private DataDescriptor dataDescriptor;

    public LocalFileHeader() {
        super(30, ZipSignature.LOCAL_FILE, 6);
    }

    public LocalFileHeader(String str) {
        this();
        setFileName(str);
    }

    public CentralEntryHeader getCentralEntryHeader() {
        return this.centralEntryHeader;
    }

    public void setCentralEntryHeader(CentralEntryHeader centralEntryHeader) {
        this.centralEntryHeader = centralEntryHeader;
        if (centralEntryHeader == null) {
            return;
        }
        mergeZeroValues(centralEntryHeader);
    }

    public LocalFileHeader copy() {
        LocalFileHeader localFileHeader = new LocalFileHeader();
        localFileHeader.setSignature(ZipSignature.LOCAL_FILE);
        localFileHeader.setFileName(getFileName());
        localFileHeader.getGeneralPurposeFlag().setValue(getGeneralPurposeFlag().getValue());
        localFileHeader.setCompressedSize(getCompressedSize());
        localFileHeader.setSize(getSize());
        localFileHeader.setCrc(getCrc());
        localFileHeader.setDosTime(getDosTime());
        localFileHeader.setPlatform(getPlatform());
        localFileHeader.setVersionMadeBy(getVersionMadeBy());
        localFileHeader.setMethod(getMethod());
        localFileHeader.updateDataDescriptor();
        return localFileHeader;
    }

    public void mergeZeroValues(CentralEntryHeader centralEntryHeader) {
        if (getFileOffset() == 0) {
            setFileOffset(centralEntryHeader.getFileOffset());
        }
        if (getCrc() == 0) {
            setCrc(centralEntryHeader.getCrc());
        }
        if (getSize() == 0) {
            setSize(centralEntryHeader.getSize());
        }
        if (getCompressedSize() == 0) {
            setCompressedSize(centralEntryHeader.getCompressedSize());
        }
        if (getGeneralPurposeFlag().getValue() == 0) {
            getGeneralPurposeFlag().setValue(centralEntryHeader.getGeneralPurposeFlag().getValue());
        }
    }

    @Override // com.reandroid.archive.block.CommonHeader
    public long getCompressedSize() {
        DataDescriptor dataDescriptor = getDataDescriptor();
        if (dataDescriptor != null) {
            return dataDescriptor.getCompressedSize();
        }
        return getCompressedSizeInternal();
    }

    private long getCompressedSizeInternal() {
        if (isZip64()) {
            return getZip64CompressedSize();
        }
        return getIntegerUnsigned(getOffsetCompressedSize());
    }

    @Override // com.reandroid.archive.block.CommonHeader
    public void setCompressedSize(long j) {
        DataDescriptor dataDescriptor = getDataDescriptor();
        if (dataDescriptor != null) {
            dataDescriptor.setCompressedSize(j);
            setCompressedSizeInternal(0L);
        } else {
            setCompressedSizeInternal(j);
        }
    }

    private void setCompressedSizeInternal(long j) {
        if (isZip64Value() || isZip64Value(j)) {
            ensureZip64();
            putInteger(getOffsetCompressedSize(), -1);
            setZip64CompressedSize(j);
            return;
        }
        putInteger(getOffsetCompressedSize(), j);
    }

    @Override // com.reandroid.archive.block.CommonHeader
    public void setCrc(long j) {
        DataDescriptor dataDescriptor = getDataDescriptor();
        if (dataDescriptor != null) {
            dataDescriptor.setCrc(j);
            setCrcInternal(0L);
        } else {
            setCrcInternal(j);
        }
    }

    private void setCrcInternal(long j) {
        super.setCrc(j);
    }

    @Override // com.reandroid.archive.block.CommonHeader
    public long getSize() {
        DataDescriptor dataDescriptor = getDataDescriptor();
        if (dataDescriptor != null) {
            return dataDescriptor.getSize();
        }
        return getSizeInternal();
    }

    private long getSizeInternal() {
        if (isZip64()) {
            return getZip64Size();
        }
        return getIntegerUnsigned(getOffsetSize());
    }

    @Override // com.reandroid.archive.block.CommonHeader
    public void setSize(long j) {
        DataDescriptor dataDescriptor = getDataDescriptor();
        if (dataDescriptor != null) {
            dataDescriptor.setSize(j);
            setSizeInternal(0L);
        } else {
            setSizeInternal(j);
        }
    }

    private void setSizeInternal(long j) {
        if (isZip64Value() || isZip64Value(j)) {
            ensureZip64();
            putInteger(getOffsetSize(), -1);
            setZip64CompressedSize(j);
            return;
        }
        putInteger(getOffsetSize(), j);
    }

    @Override // com.reandroid.archive.block.CommonHeader
    public long getCrc() {
        DataDescriptor dataDescriptor = getDataDescriptor();
        if (dataDescriptor != null) {
            return dataDescriptor.getCrc();
        }
        return super.getCrc();
    }

    public DataDescriptor getDataDescriptor() {
        return this.dataDescriptor;
    }

    @Override // com.reandroid.archive.block.CommonHeader
    public void setHasDataDescriptor(boolean z) {
        if (z == hasDataDescriptor()) {
            if (z != (getDataDescriptor() == null)) {
                return;
            }
        }
        super.setHasDataDescriptor(z);
        updateDataDescriptor();
    }

    public void updateDataDescriptor() {
        DataDescriptor dataDescriptor = this.dataDescriptor;
        if (hasDataDescriptor()) {
            if (dataDescriptor == null) {
                this.dataDescriptor = DataDescriptor.fromLocalFile(this);
                setCrcInternal(0L);
                setCompressedSizeInternal(0L);
                setSizeInternal(0L);
                return;
            }
            return;
        }
        this.dataDescriptor = null;
        if (dataDescriptor != null) {
            setCrcInternal(dataDescriptor.getCrc());
            setSizeInternal(dataDescriptor.getSize());
            setCompressedSizeInternal(dataDescriptor.getCompressedSize());
        }
    }

    public static LocalFileHeader fromCentralEntryHeader(CentralEntryHeader centralEntryHeader) {
        LocalFileHeader localFileHeader = new LocalFileHeader();
        localFileHeader.setSignature(ZipSignature.LOCAL_FILE);
        localFileHeader.setVersionMadeBy(centralEntryHeader.getVersionMadeBy());
        localFileHeader.getGeneralPurposeFlag().setValue(centralEntryHeader.getGeneralPurposeFlag().getValue());
        localFileHeader.setMethod(centralEntryHeader.getMethod());
        localFileHeader.setDosTime(centralEntryHeader.getDosTime());
        localFileHeader.setCrc(centralEntryHeader.getCrc());
        localFileHeader.setCompressedSize(centralEntryHeader.getCompressedSize());
        localFileHeader.setSize(centralEntryHeader.getSize());
        localFileHeader.setFileName(centralEntryHeader.getFileName());
        localFileHeader.updateDataDescriptor();
        return localFileHeader;
    }

    public static LocalFileHeader read(InputStream inputStream) throws IOException {
        LocalFileHeader localFileHeader = new LocalFileHeader();
        localFileHeader.readBytes(inputStream);
        if (localFileHeader.isValidSignature()) {
            return localFileHeader;
        }
        return null;
    }
}
