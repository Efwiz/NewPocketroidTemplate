package com.reandroid.archive.block;

import com.reandroid.archive.Archive;
import com.reandroid.archive.ZipSignature;
import com.reandroid.utils.HexUtil;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/* JADX INFO: loaded from: classes10.dex */
public abstract class CommonHeader extends ZipHeader {
    private static final int OFFSET_platform = 5;
    private static final int OFFSET_versionMadeBy = 4;
    private final GeneralPurposeFlag generalPurposeFlag;
    private String mFileName;
    private long mFileOffset;
    private final int offsetFileName;
    private final int offsetGeneralPurpose;

    static boolean isZip64Value(int i) {
        return i == -1;
    }

    static boolean isZip64Value(long j) {
        return j == 4294967295L || (j & (-4294967296L)) != 0;
    }

    public int getCommentLength() {
        return 0;
    }

    int getZip64BytesLength() {
        return 20;
    }

    int readComment(InputStream inputStream) throws IOException {
        return 0;
    }

    public void setVersionExtract(int i) {
    }

    public CommonHeader(int i, ZipSignature zipSignature, int i2) {
        super(i, zipSignature);
        this.offsetFileName = i;
        this.offsetGeneralPurpose = i2;
        GeneralPurposeFlag generalPurposeFlag = new GeneralPurposeFlag(this, i2);
        this.generalPurposeFlag = generalPurposeFlag;
        generalPurposeFlag.setUtf8(true, false);
        setDosTime(35719201L);
    }

    public long getFileOffset() {
        return this.mFileOffset;
    }

    public void setFileOffset(long j) {
        this.mFileOffset = j;
    }

    public long getDataSize() {
        if (getMethod() == Archive.STORED) {
            return getSize();
        }
        return getCompressedSize();
    }

    public void setDataSize(long j) {
        if (getMethod() == Archive.STORED) {
            setSize(j);
        }
        setCompressedSize(j);
    }

    @Override // com.reandroid.archive.block.ZipHeader
    int readNext(InputStream inputStream) throws IOException {
        int fileName = readFileName(inputStream) + readExtra(inputStream) + readComment(inputStream);
        this.mFileName = null;
        return fileName;
    }

    private int readFileName(InputStream inputStream) throws IOException {
        int fileNameLength = getFileNameLength();
        if (fileNameLength == 0) {
            this.mFileName = "";
            return 0;
        }
        setFileNameLength(fileNameLength);
        int i = inputStream.read(getBytesInternal(), this.offsetFileName, fileNameLength);
        if (i != fileNameLength) {
            throw new IOException("Stream ended before reading file name: read=" + i + ", name length=" + fileNameLength);
        }
        this.mFileName = null;
        return fileNameLength;
    }

    private int readExtra(InputStream inputStream) throws IOException {
        int extraLength = getExtraLength();
        if (extraLength == 0) {
            return 0;
        }
        setExtraLength(extraLength);
        int i = inputStream.read(getBytesInternal(), getOffsetExtra(), extraLength);
        if (i == extraLength) {
            return extraLength;
        }
        throw new IOException("Stream ended before reading extra bytes: read=" + i + ", extra length=" + extraLength);
    }

    public int getVersionMadeBy() {
        return getShortUnsigned(4);
    }

    public void setVersionMadeBy(int i) {
        putShort(4, i);
    }

    public int getPlatform() {
        return getByteUnsigned(5);
    }

    public void setPlatform(int i) {
        getBytesInternal()[5] = (byte) i;
    }

    public GeneralPurposeFlag getGeneralPurposeFlag() {
        return this.generalPurposeFlag;
    }

    public int getMethod() {
        return getShortUnsigned(this.offsetGeneralPurpose + 2);
    }

    public void setMethod(int i) {
        putShort(this.offsetGeneralPurpose + 2, i);
    }

    public long getDosTime() {
        return getIntegerUnsigned(this.offsetGeneralPurpose + 4);
    }

    public void setDosTime(long j) {
        if (j != -1) {
            putInteger(this.offsetGeneralPurpose + 4, j);
        }
    }

    public Date getDate() {
        return Archive.dosToJavaDate(getDosTime());
    }

    public void setDate(Date date) {
        setDosTime(Archive.javaToDosTime(date));
    }

    public void setDate(long j) {
        setDosTime(Archive.javaToDosTime(j));
    }

    public long getCrc() {
        return getIntegerUnsigned(this.offsetGeneralPurpose + 8);
    }

    public void setCrc(long j) {
        putInteger(this.offsetGeneralPurpose + 8, j);
    }

    public long getCompressedSize() {
        return getIntegerUnsigned(getOffsetCompressedSize());
    }

    public void setCompressedSize(long j) {
        putInteger(getOffsetCompressedSize(), j);
    }

    int getOffsetCompressedSize() {
        return this.offsetGeneralPurpose + 12;
    }

    public long getSize() {
        return getIntegerUnsigned(getOffsetSize());
    }

    public void setSize(long j) {
        putInteger(getOffsetSize(), j);
    }

    int getOffsetSize() {
        return this.offsetGeneralPurpose + 16;
    }

    public int getFileNameLength() {
        return getShortUnsigned(this.offsetGeneralPurpose + 20);
    }

    private void setFileNameLength(int i) {
        super.setBytesLength(this.offsetFileName + i + getExtraLength() + getCommentLength(), false);
        putShort(this.offsetGeneralPurpose + 20, i);
    }

    public int getExtraLength() {
        return getShortUnsigned(this.offsetGeneralPurpose + 22);
    }

    public void setExtraLength(int i) {
        super.setBytesLength(this.offsetFileName + getFileNameLength() + i + getCommentLength(), false);
        putShort(this.offsetGeneralPurpose + 22, i);
    }

    public void setZipAlign(int i) {
        if (isZip64()) {
            i += getZip64BytesLength();
        }
        setExtraLength(i);
    }

    public byte[] getExtra() {
        int extraLength = getExtraLength();
        byte[] bArr = new byte[extraLength];
        if (extraLength == 0) {
            return bArr;
        }
        System.arraycopy(getBytesInternal(), getOffsetExtra(), bArr, 0, extraLength);
        return bArr;
    }

    public void setExtra(byte[] bArr) {
        if (bArr == null) {
            bArr = new byte[0];
        }
        int length = bArr.length;
        setExtraLength(length);
        if (length == 0) {
            return;
        }
        putBytes(bArr, 0, getOffsetExtra(), length);
    }

    void ensureZip64() {
        if (getExtraLength() >= getZip64BytesLength()) {
            return;
        }
        setExtraLength(getZip64BytesLength());
    }

    boolean isZip64() {
        return isZip64Value() && getExtraLength() >= getZip64BytesLength();
    }

    boolean isZip64Value() {
        return isZip64Value(getInteger(getOffsetCompressedSize())) || isZip64Value(getInteger(getOffsetCompressedSize()));
    }

    public long getZip64CompressedSize() {
        return getLong(getOffsetZip64CompressedSize());
    }

    public void setZip64CompressedSize(long j) {
        putLong(getOffsetZip64CompressedSize(), j);
    }

    public long getZip64Size() {
        return getLong(getOffsetZip64Size());
    }

    void setZip64Size(long j) {
        putLong(getOffsetZip64Size(), j);
    }

    int getZip64FieldLength() {
        return getShortUnsigned(getOffsetZip64FieldLength());
    }

    void setZip64FieldLength(int i) {
        putShort(getOffsetZip64FieldLength(), i);
    }

    int getZip64FieldHeader() {
        return getShortUnsigned(getOffsetZip64FieldHeader());
    }

    void setZip64FieldHeader(int i) {
        putShort(getOffsetZip64FieldHeader(), i);
    }

    int getOffsetZip64CompressedSize() {
        return getOffsetExtra() + 8;
    }

    private int getOffsetZip64Size() {
        return getOffsetZip64FieldLength() + 2;
    }

    int getOffsetZip64FieldLength() {
        return getOffsetZip64FieldHeader() + 2;
    }

    private int getOffsetZip64FieldHeader() {
        return getOffsetZip64();
    }

    private int getOffsetZip64() {
        return getOffsetExtra();
    }

    int getOffsetComment() {
        return getOffsetExtra() + getExtraLength();
    }

    private int getOffsetExtra() {
        return this.offsetFileName + getFileNameLength();
    }

    public String getFileName() {
        if (this.mFileName == null) {
            this.mFileName = decodeFileName();
        }
        return this.mFileName;
    }

    public void setFileName(String str) {
        if (str == null) {
            str = "";
        }
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        getGeneralPurposeFlag().setUtf8(true, false);
        int length = bytes.length;
        setFileNameLength(length);
        if (length == 0) {
            this.mFileName = str;
        } else {
            System.arraycopy(bytes, 0, getBytesInternal(), this.offsetFileName, length);
            this.mFileName = str;
        }
    }

    public boolean isUtf8() {
        return getGeneralPurposeFlag().getUtf8();
    }

    public boolean hasDataDescriptor() {
        return getGeneralPurposeFlag().hasDataDescriptor();
    }

    public void setHasDataDescriptor(boolean z) {
        getGeneralPurposeFlag().setHasDataDescriptor(z);
    }

    private String decodeFileName() {
        int fileNameLength = getFileNameLength();
        byte[] bytesInternal = getBytesInternal();
        int i = this.offsetFileName;
        int length = bytesInternal.length - i;
        if (length <= 0) {
            return "";
        }
        if (fileNameLength > length) {
            fileNameLength = length;
        }
        return new String(bytesInternal, i, fileNameLength, StandardCharsets.UTF_8);
    }

    public String decodeComment() {
        int extraLength = getExtraLength();
        byte[] bytesInternal = getBytesInternal();
        int offsetExtra = getOffsetExtra();
        int length = bytesInternal.length - offsetExtra;
        if (length <= 0) {
            return "";
        }
        if (extraLength > length) {
            extraLength = length;
        }
        return new String(bytesInternal, offsetExtra, extraLength, StandardCharsets.UTF_8);
    }

    void onUtf8Changed(boolean z) {
        String str = this.mFileName;
        if (str != null) {
            setFileName(str);
        }
    }

    public String toString() {
        if (countBytes() < getMinByteLength()) {
            return "Invalid";
        }
        StringBuilder sb = new StringBuilder("[");
        sb.append(getFileOffset()).append("] ");
        String fileName = getFileName();
        if (fileName.length() > 0) {
            sb.append("name=").append(fileName);
            sb.append(", ");
        }
        sb.append("SIG=").append(getSignature());
        sb.append(", versionMadeBy=").append(HexUtil.toHex4((short) getVersionMadeBy()));
        sb.append(", platform=").append(HexUtil.toHex2((byte) getPlatform()));
        sb.append(", GP={").append(getGeneralPurposeFlag()).append("}, method=");
        sb.append(getMethod());
        sb.append(", date=").append(HexUtil.toHex(getDosTime(), 1));
        sb.append(", crc=").append(HexUtil.toHex8(getCrc()));
        sb.append(", cSize=").append(getCompressedSize());
        sb.append(", size=").append(getSize());
        sb.append(", fileNameLength=").append(getFileNameLength());
        sb.append(", extraLength=").append(getExtraLength());
        return sb.toString();
    }

    public static class GeneralPurposeFlag {
        private final CommonHeader localFileHeader;
        private final int offset;

        public GeneralPurposeFlag(CommonHeader commonHeader, int i) {
            this.localFileHeader = commonHeader;
            this.offset = i;
        }

        public boolean getEncryption() {
            return this.localFileHeader.getBit(this.offset, 0);
        }

        public void setEncryption(boolean z) {
            this.localFileHeader.putBit(this.offset, 0, z);
        }

        public boolean hasDataDescriptor() {
            return this.localFileHeader.getBit(this.offset, 3);
        }

        public void setHasDataDescriptor(boolean z) {
            this.localFileHeader.putBit(this.offset, 3, z);
        }

        public boolean getStrongEncryption() {
            return this.localFileHeader.getBit(this.offset, 6);
        }

        public void setStrongEncryption(boolean z) {
            this.localFileHeader.putBit(this.offset, 6, z);
        }

        public boolean getUtf8() {
            return this.localFileHeader.getBit(this.offset + 1, 3);
        }

        public void setUtf8(boolean z) {
            setUtf8(z, true);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setUtf8(boolean z, boolean z2) {
            boolean utf8 = getUtf8();
            if (utf8 == z) {
                return;
            }
            this.localFileHeader.putBit(this.offset + 1, 3, z);
            if (z2) {
                this.localFileHeader.onUtf8Changed(utf8);
            }
        }

        public int getValue() {
            return this.localFileHeader.getShortUnsigned(this.offset);
        }

        public void setValue(int i) {
            if (i == getValue()) {
                return;
            }
            boolean utf8 = getUtf8();
            this.localFileHeader.putShort(this.offset, i);
            if (utf8 != getUtf8()) {
                this.localFileHeader.onUtf8Changed(utf8);
            }
        }

        public void initDefault() {
            setUtf8(false, false);
        }

        public String toString() {
            return "Enc=" + getEncryption() + ", Descriptor=" + hasDataDescriptor() + ", StrongEnc=" + getStrongEncryption() + ", UTF8=" + getUtf8();
        }
    }
}
