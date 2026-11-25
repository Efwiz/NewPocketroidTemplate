package com.reandroid.archive.block;

import com.reandroid.archive.ZipSignature;
import com.reandroid.utils.HexUtil;
import com.reandroid.utils.io.FilePermissions;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;

/* JADX INFO: loaded from: classes10.dex */
public class CentralEntryHeader extends CommonHeader {
    private static final int OFFSET_commentLength = 32;
    private static final int OFFSET_compressed_size = 20;
    private static final int OFFSET_crc = 16;
    private static final int OFFSET_diskStart = 34;
    private static final int OFFSET_dos_date = 14;
    private static final int OFFSET_dos_time = 12;
    private static final int OFFSET_externalFileAttributes = 38;
    private static final int OFFSET_extraLength = 30;
    private static final int OFFSET_fileName = 46;
    private static final int OFFSET_fileNameLength = 28;
    private static final int OFFSET_general_purpose = 8;
    private static final int OFFSET_internalFileAttributes = 36;
    private static final int OFFSET_localRelativeOffset = 42;
    private static final int OFFSET_method = 10;
    private static final int OFFSET_signature = 0;
    private static final int OFFSET_size = 24;
    private static final int OFFSET_versionExtract = 6;
    private static final int OFFSET_versionMadeBy = 4;
    private String mComment;

    @Override // com.reandroid.archive.block.CommonHeader
    int getZip64BytesLength() {
        return 12;
    }

    public CentralEntryHeader() {
        super(46, ZipSignature.CENTRAL_FILE, 8);
        setFilePermissionsValue(TarArchiveEntry.DEFAULT_FILE_MODE);
    }

    @Override // com.reandroid.archive.block.CommonHeader
    boolean isZip64Value() {
        return isZip64Value(getInteger(42));
    }

    private int getOffsetZip64LocalRelativeOffset() {
        return getOffsetZip64FieldLength() + 2;
    }

    @Override // com.reandroid.archive.block.CommonHeader
    int readComment(InputStream inputStream) throws IOException {
        int commentLength = getCommentLength();
        if (commentLength == 0) {
            this.mComment = "";
            return 0;
        }
        setCommentLength(commentLength);
        int i = inputStream.read(getBytesInternal(), getOffsetComment(), commentLength);
        if (i != commentLength) {
            throw new IOException("Stream ended before reading comment: read=" + i + ", name length=" + commentLength);
        }
        this.mComment = null;
        return commentLength;
    }

    public int getVersionExtract() {
        return getShortUnsigned(6);
    }

    @Override // com.reandroid.archive.block.CommonHeader
    public void setVersionExtract(int i) {
        putShort(6, i);
    }

    public String getComment() {
        if (this.mComment == null) {
            this.mComment = decodeComment();
        }
        return this.mComment;
    }

    public void setComment(String str) {
        if (str == null) {
            str = "";
        }
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        int length = bytes.length;
        setCommentLength(length);
        if (length == 0) {
            this.mComment = str;
        } else {
            System.arraycopy(bytes, 0, getBytesInternal(), getOffsetComment(), length);
            this.mComment = str;
        }
    }

    @Override // com.reandroid.archive.block.CommonHeader
    public int getCommentLength() {
        return getShortUnsigned(32);
    }

    public void setCommentLength(int i) {
        setBytesLength(getOffsetComment() + i, false);
        putShort(32, i);
    }

    public long getLocalRelativeOffset() {
        if (isZip64()) {
            return getLong(getOffsetZip64LocalRelativeOffset());
        }
        return getIntegerUnsigned(42);
    }

    public void setLocalRelativeOffset(long j) {
        if (isZip64Value() || isZip64Value(j)) {
            ensureZip64();
            putInteger(42, -1);
            putLong(getOffsetZip64LocalRelativeOffset(), j);
            return;
        }
        putInteger(42, j);
    }

    public int getInternalFileAttributes() {
        return getShortUnsigned(36);
    }

    public void setInternalFileAttributes(int i) {
        putShort(36, i);
    }

    public int getExternalFileAttributes() {
        return getInteger(38);
    }

    public void setExternalFileAttributes(int i) {
        putInteger(38, i);
    }

    public int getFileAttributesId() {
        return getShortUnsigned(38);
    }

    public void setFileAttributesId(int i) {
        putShort(38, i);
    }

    public int getFilePermissionsValue() {
        return getShortUnsigned(40);
    }

    public void setFilePermissionsValue(int i) {
        putShort(40, i);
    }

    public FilePermissions getFilePermissions() {
        return new FilePermissions() { // from class: com.reandroid.archive.block.CentralEntryHeader.1
            @Override // com.reandroid.utils.io.FilePermissions
            public int get() {
                return CentralEntryHeader.this.getFilePermissionsValue();
            }

            @Override // com.reandroid.utils.io.FilePermissions
            public void set(int i) {
                CentralEntryHeader.this.setFilePermissionsValue(i);
            }
        };
    }

    @Override // com.reandroid.archive.block.CommonHeader
    void onUtf8Changed(boolean z) {
        String str = this.mComment;
        if (str != null) {
            setComment(str);
        }
    }

    public boolean matches(LocalFileHeader localFileHeader) {
        return localFileHeader != null && getCrc() == localFileHeader.getCrc() && Objects.equals(getFileName(), localFileHeader.getFileName());
    }

    @Override // com.reandroid.archive.block.CommonHeader
    public String toString() {
        boolean z;
        if (countBytes() < getMinByteLength()) {
            return "Invalid";
        }
        StringBuilder sb = new StringBuilder("[");
        sb.append(getFileOffset()).append(']');
        String fileName = getFileName();
        if (fileName.length() > 0) {
            sb.append("name=").append(fileName);
            z = true;
        } else {
            z = false;
        }
        String comment = getComment();
        if (comment.length() > 0) {
            if (z) {
                sb.append(", ");
            }
            sb.append("comment=").append(comment);
            z = true;
        }
        if (z) {
            sb.append(", ");
        }
        sb.append("SIG=").append(getSignature());
        sb.append(", versionMadeBy=").append(HexUtil.toHex4((short) getVersionMadeBy()));
        sb.append(", versionExtract=").append(HexUtil.toHex4((short) getVersionExtract()));
        sb.append(", GP={").append(getGeneralPurposeFlag()).append("}, method=");
        sb.append(getMethod());
        sb.append(", date=").append(HexUtil.toHex(getDosTime(), 1));
        sb.append(", crc=").append(HexUtil.toHex8(getCrc()));
        sb.append(", cSize=").append(getCompressedSize());
        sb.append(", size=").append(getSize());
        sb.append(", fileNameLength=").append(getFileNameLength());
        sb.append(", extraLength=").append(getExtraLength());
        sb.append(", commentLength=").append(getCommentLength());
        sb.append(", offset=").append(getLocalRelativeOffset());
        sb.append(", internalAttr=").append(getInternalFileAttributes());
        sb.append(", externalAttr=").append(HexUtil.toHex8(getExternalFileAttributes()));
        sb.append(", attrId=").append(getFileAttributesId());
        sb.append(", permissions=").append(getFilePermissions());
        return sb.toString();
    }

    public static CentralEntryHeader fromLocalFileHeader(LocalFileHeader localFileHeader) {
        CentralEntryHeader centralEntryHeader = new CentralEntryHeader();
        centralEntryHeader.setSignature(ZipSignature.CENTRAL_FILE);
        centralEntryHeader.setVersionMadeBy(localFileHeader.getVersionMadeBy());
        centralEntryHeader.setLocalRelativeOffset(localFileHeader.getFileOffset() - ((long) localFileHeader.countBytes()));
        centralEntryHeader.getGeneralPurposeFlag().setValue(localFileHeader.getGeneralPurposeFlag().getValue());
        centralEntryHeader.setMethod(localFileHeader.getMethod());
        centralEntryHeader.setDosTime(localFileHeader.getDosTime());
        centralEntryHeader.setCrc(localFileHeader.getCrc());
        centralEntryHeader.setCompressedSize(localFileHeader.getCompressedSize());
        centralEntryHeader.setSize(localFileHeader.getSize());
        centralEntryHeader.setFileName(localFileHeader.getFileName());
        return centralEntryHeader;
    }
}
