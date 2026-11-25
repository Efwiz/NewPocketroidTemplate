package com.reandroid.archive;

import com.reandroid.archive.block.CentralEntryHeader;
import com.reandroid.archive.block.LocalFileHeader;
import com.reandroid.utils.HexUtil;
import com.reandroid.utils.io.FilePermissions;

/* JADX INFO: loaded from: classes10.dex */
public class ArchiveEntry {
    private final LocalFileHeader localFileHeader;

    public ArchiveEntry(LocalFileHeader localFileHeader) {
        this.localFileHeader = localFileHeader;
    }

    public long getDataSize() {
        if (getMethod() != Archive.DEFLATED) {
            return getSize();
        }
        return getCompressedSize();
    }

    public boolean isCompressed() {
        return getMethod() == Archive.DEFLATED;
    }

    public int getMethod() {
        return this.localFileHeader.getMethod();
    }

    public void setMethod(int i) {
        this.localFileHeader.setMethod(i);
        getCentralEntryHeader().setMethod(i);
    }

    public long getSize() {
        return this.localFileHeader.getSize();
    }

    public void setSize(long j) {
        this.localFileHeader.setSize(j);
        getCentralEntryHeader().setSize(j);
    }

    public long getCrc() {
        return this.localFileHeader.getCrc();
    }

    public void setCrc(long j) {
        this.localFileHeader.setCrc(j);
        getCentralEntryHeader().setCrc(j);
    }

    public long getCompressedSize() {
        return this.localFileHeader.getCompressedSize();
    }

    public void setCompressedSize(long j) {
        this.localFileHeader.setCompressedSize(j);
        getCentralEntryHeader().setCompressedSize(j);
    }

    public long getFileOffset() {
        return this.localFileHeader.getFileOffset();
    }

    public String getName() {
        return this.localFileHeader.getFileName();
    }

    public String getSanitizedName() {
        String strSanitizePath = ArchiveUtil.sanitizePath(this.localFileHeader.getFileName());
        return strSanitizePath == null ? ".error_file_path_" + this.localFileHeader.getIndex() : strSanitizePath;
    }

    public void setName(String str) {
        this.localFileHeader.setFileName(str);
        getCentralEntryHeader().setFileName(str);
    }

    public String getComment() {
        return getCentralEntryHeader().getComment();
    }

    public void setComment(String str) {
        getCentralEntryHeader().setComment(str);
    }

    public boolean isFile() {
        return !isDirectory();
    }

    public boolean isDirectory() {
        return getName().endsWith("/");
    }

    public FilePermissions getFilePermissions() {
        return getCentralEntryHeader().getFilePermissions();
    }

    public CentralEntryHeader getCentralEntryHeader() {
        CentralEntryHeader centralEntryHeader = this.localFileHeader.getCentralEntryHeader();
        if (centralEntryHeader != null) {
            return centralEntryHeader;
        }
        CentralEntryHeader centralEntryHeaderFromLocalFileHeader = CentralEntryHeader.fromLocalFileHeader(this.localFileHeader);
        this.localFileHeader.setCentralEntryHeader(centralEntryHeaderFromLocalFileHeader);
        return centralEntryHeaderFromLocalFileHeader;
    }

    public LocalFileHeader getLocalFileHeader() {
        return this.localFileHeader;
    }

    public long getDosTime() {
        return getCentralEntryHeader().getDosTime();
    }

    public void setDosTime(long j) {
        getCentralEntryHeader().setDosTime(j);
        getLocalFileHeader().setDosTime(j);
    }

    public int getIndex() {
        return getCentralEntryHeader().getIndex();
    }

    public String toString() {
        return "[" + getFileOffset() + "] " + getName() + getComment() + HexUtil.toHex(" 0x", getCrc(), 8);
    }
}
