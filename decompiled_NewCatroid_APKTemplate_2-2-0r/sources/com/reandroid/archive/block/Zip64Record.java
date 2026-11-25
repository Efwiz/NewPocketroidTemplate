package com.reandroid.archive.block;

import com.reandroid.archive.ZipSignature;

/* JADX INFO: loaded from: classes10.dex */
public class Zip64Record extends ZipHeader {
    public static final int MAX_LENGTH = 56;
    public static final int MIN_LENGTH = 56;
    private static final int OFFSET_diskCD = 20;
    private static final int OFFSET_diskNumber = 16;
    private static final int OFFSET_numberOfCDRecords = 24;
    private static final int OFFSET_offsetOfCentralDirectory = 48;
    private static final int OFFSET_sizeOfCD = 40;
    private static final int OFFSET_sizeOfEOCDR = 4;
    private static final int OFFSET_totalCDRecords = 32;
    private static final int OFFSET_versionCreator = 12;
    private static final int OFFSET_versionViewer = 14;

    public Zip64Record() {
        super(56, ZipSignature.ZIP64_RECORD);
    }

    public long getSizeOfEOCDR() {
        return getLong(4);
    }

    public void setSizeOfEOCDR(long j) {
        putLong(4, j);
    }

    public int getVersionCreator() {
        return getShortUnsigned(12);
    }

    public void setVersionCreator(int i) {
        putShort(12, i);
    }

    public int getVersionViewer() {
        return getShortUnsigned(12);
    }

    public void setVersionViewer(int i) {
        putShort(14, i);
    }

    public int getDiskNumber() {
        return getInteger(16);
    }

    public void setDiskNumber(int i) {
        putInteger(16, i);
    }

    public int getDiskCD() {
        return getInteger(20);
    }

    public void setDiskCD(int i) {
        putInteger(20, i);
    }

    public long getNumberOfCDRecords() {
        return getLong(24);
    }

    public void setNumberOfCDRecords(long j) {
        putLong(24, j);
    }

    public long getTotalCDRecords() {
        return getLong(32);
    }

    public void setTotalCDRecords(long j) {
        putLong(32, j);
    }

    public long getSizeOfCD() {
        return getLong(40);
    }

    public void setSizeOfCD(long j) {
        putLong(40, j);
    }

    public long getOffsetOfCentralDirectory() {
        return getLong(48);
    }

    public void setOffsetOfCentralDirectory(long j) {
        putLong(48, j);
    }

    public String toString() {
        if (countBytes() < getMinByteLength()) {
            return "Invalid";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(getSignature());
        sb.append(", EOCDR=").append(getSizeOfEOCDR());
        sb.append(", creator=").append(getVersionCreator());
        sb.append(", viewer=").append(getVersionViewer());
        sb.append(", disk number=").append(getDiskNumber());
        sb.append(", disk CD=").append(getDiskCD());
        sb.append(", noOf CDR=").append(getNumberOfCDRecords());
        sb.append(", total rec=").append(getTotalCDRecords());
        sb.append(", size of CD=").append(getSizeOfCD());
        sb.append(", offset of CD=").append(getOffsetOfCentralDirectory());
        return sb.toString();
    }

    public static Zip64Record newZip64Record() {
        Zip64Record zip64Record = new Zip64Record();
        zip64Record.setSignature(ZipSignature.ZIP64_RECORD);
        zip64Record.setSizeOfEOCDR(44L);
        zip64Record.setVersionCreator(45);
        zip64Record.setVersionViewer(45);
        return zip64Record;
    }
}
