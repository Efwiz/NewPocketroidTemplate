package com.topjohnwu.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* JADX INFO: loaded from: classes11.dex */
public class ZipAdjust {
    public static int unsigned(short s) {
        return s & 65535;
    }

    public static long unsigned(int i) {
        return ((long) i) & 4294967295L;
    }

    public static short unsigned(byte b) {
        return (short) (b & 255);
    }

    public static void adjust(File file, File file2) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            try {
                adjust(randomAccessFile, fileOutputStream);
                fileOutputStream.close();
                randomAccessFile.close();
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    try {
                        fileOutputStream.close();
                    } catch (Throwable th3) {
                        th.addSuppressed(th3);
                    }
                    throw th2;
                }
            }
        } catch (Throwable th4) {
            try {
                throw th4;
            } catch (Throwable th5) {
                try {
                    randomAccessFile.close();
                } catch (Throwable th6) {
                    th4.addSuppressed(th6);
                }
                throw th5;
            }
        }
    }

    public static void adjust(RandomAccessFile randomAccessFile, OutputStream outputStream) throws IOException {
        CentralFooter centralFooter = new CentralFooter(randomAccessFile);
        long jUnsigned = unsigned(centralFooter.centralDirectoryOffset);
        int iUnsigned = unsigned(centralFooter.numEntries);
        CentralHeader[] centralHeaderArr = new CentralHeader[iUnsigned];
        int i = 0;
        for (int i2 = 0; i2 < iUnsigned; i2++) {
            randomAccessFile.seek(jUnsigned);
            centralHeaderArr[i2] = new CentralHeader(randomAccessFile);
            jUnsigned = randomAccessFile.getFilePointer();
            randomAccessFile.seek(unsigned(centralHeaderArr[i2].localHeaderOffset));
            LocalHeader localHeader = new LocalHeader(randomAccessFile);
            centralHeaderArr[i2].localHeaderOffset = i;
            CentralHeader centralHeader = centralHeaderArr[i2];
            centralHeader.flags = (short) (centralHeader.flags & (-9));
            localHeader.flags = centralHeaderArr[i2].flags;
            localHeader.crc32 = centralHeaderArr[i2].crc32;
            localHeader.compressedSize = centralHeaderArr[i2].compressedSize;
            localHeader.uncompressedSize = centralHeaderArr[i2].uncompressedSize;
            localHeader.fileNameLength = centralHeaderArr[i2].fileNameLength;
            localHeader.filename = centralHeaderArr[i2].filename;
            int iWrite = i + localHeader.write(outputStream);
            long jUnsigned2 = unsigned(localHeader.compressedSize);
            i = (int) (((long) iWrite) + jUnsigned2);
            byte[] bArr = new byte[4096];
            while (true) {
                int i3 = randomAccessFile.read(bArr, 0, jUnsigned2 < ((long) 4096) ? (int) jUnsigned2 : 4096);
                if (i3 > 0) {
                    outputStream.write(bArr, 0, i3);
                    jUnsigned2 -= (long) i3;
                }
            }
        }
        centralFooter.centralDirectoryOffset = i;
        int iWrite2 = 0;
        for (int i4 = 0; i4 < iUnsigned; i4++) {
            iWrite2 += centralHeaderArr[i4].write(outputStream);
        }
        centralFooter.centralDirectorySize = iWrite2;
        centralFooter.write(outputStream);
    }

    public static class CentralFooter {
        static final int MAGIC = 101010256;
        short centralDirectoryDiskNumber;
        int centralDirectoryOffset;
        int centralDirectorySize;
        short diskNumber;
        short numEntries;
        short numEntriesThisDisk;
        int signature;
        short zipCommentLength;

        CentralFooter(RandomAccessFile randomAccessFile) throws IOException {
            byte[] bArr = new byte[22];
            long length = randomAccessFile.length();
            long j = 4;
            while (true) {
                length -= j;
                if (length < 0) {
                    return;
                }
                randomAccessFile.seek(length);
                randomAccessFile.read(bArr, 0, 4);
                ByteBuffer byteBufferOrder = ByteBuffer.wrap(bArr).order(ByteOrder.LITTLE_ENDIAN);
                int i = byteBufferOrder.getInt();
                this.signature = i;
                if (i == MAGIC) {
                    randomAccessFile.read(bArr, 4, 18);
                    this.diskNumber = byteBufferOrder.getShort();
                    this.centralDirectoryDiskNumber = byteBufferOrder.getShort();
                    this.numEntriesThisDisk = byteBufferOrder.getShort();
                    this.numEntries = byteBufferOrder.getShort();
                    this.centralDirectorySize = byteBufferOrder.getInt();
                    this.centralDirectoryOffset = byteBufferOrder.getInt();
                    this.zipCommentLength = byteBufferOrder.getShort();
                    return;
                }
                j = 1;
            }
        }

        int write(OutputStream outputStream) throws IOException {
            byte[] bArr = new byte[22];
            ByteBuffer byteBufferOrder = ByteBuffer.wrap(bArr).order(ByteOrder.LITTLE_ENDIAN);
            byteBufferOrder.putInt(this.signature);
            byteBufferOrder.putShort(this.diskNumber);
            byteBufferOrder.putShort(this.centralDirectoryDiskNumber);
            byteBufferOrder.putShort(this.numEntriesThisDisk);
            byteBufferOrder.putShort(this.numEntries);
            byteBufferOrder.putInt(this.centralDirectorySize);
            byteBufferOrder.putInt(this.centralDirectoryOffset);
            byteBufferOrder.putShort((short) 0);
            outputStream.write(bArr);
            return 22;
        }
    }

    static class CentralHeader {
        static final int MAGIC = 33639248;
        int compressedSize;
        short compressionMethod;
        int crc32;
        short diskNumberStart;
        int externalFileAttributes;
        short extraFieldLength;
        short fileCommentLength;
        short fileNameLength;
        byte[] filename;
        short flags;
        short internalFileAttributes;
        short lastModFileDate;
        short lastModFileTime;
        int localHeaderOffset;
        int signature;
        int uncompressedSize;
        short versionMadeBy;
        short versionNeededToExtract;

        CentralHeader(RandomAccessFile randomAccessFile) throws IOException {
            byte[] bArr = new byte[46];
            randomAccessFile.read(bArr);
            ByteBuffer byteBufferOrder = ByteBuffer.wrap(bArr).order(ByteOrder.LITTLE_ENDIAN);
            int i = byteBufferOrder.getInt();
            this.signature = i;
            if (i != MAGIC) {
                throw new IOException();
            }
            this.versionMadeBy = byteBufferOrder.getShort();
            this.versionNeededToExtract = byteBufferOrder.getShort();
            this.flags = byteBufferOrder.getShort();
            this.compressionMethod = byteBufferOrder.getShort();
            this.lastModFileTime = byteBufferOrder.getShort();
            this.lastModFileDate = byteBufferOrder.getShort();
            this.crc32 = byteBufferOrder.getInt();
            this.compressedSize = byteBufferOrder.getInt();
            this.uncompressedSize = byteBufferOrder.getInt();
            this.fileNameLength = byteBufferOrder.getShort();
            this.extraFieldLength = byteBufferOrder.getShort();
            this.fileCommentLength = byteBufferOrder.getShort();
            this.diskNumberStart = byteBufferOrder.getShort();
            this.internalFileAttributes = byteBufferOrder.getShort();
            this.externalFileAttributes = byteBufferOrder.getInt();
            this.localHeaderOffset = byteBufferOrder.getInt();
            byte[] bArr2 = new byte[ZipAdjust.unsigned(this.fileNameLength)];
            this.filename = bArr2;
            randomAccessFile.read(bArr2);
            randomAccessFile.skipBytes(ZipAdjust.unsigned(this.extraFieldLength) + ZipAdjust.unsigned(this.fileCommentLength));
        }

        int write(OutputStream outputStream) throws IOException {
            byte[] bArr = new byte[46];
            ByteBuffer byteBufferOrder = ByteBuffer.wrap(bArr).order(ByteOrder.LITTLE_ENDIAN);
            byteBufferOrder.putInt(this.signature);
            byteBufferOrder.putShort(this.versionMadeBy);
            byteBufferOrder.putShort(this.versionNeededToExtract);
            byteBufferOrder.putShort(this.flags);
            byteBufferOrder.putShort(this.compressionMethod);
            byteBufferOrder.putShort(this.lastModFileTime);
            byteBufferOrder.putShort(this.lastModFileDate);
            byteBufferOrder.putInt(this.crc32);
            byteBufferOrder.putInt(this.compressedSize);
            byteBufferOrder.putInt(this.uncompressedSize);
            byteBufferOrder.putShort(this.fileNameLength);
            byteBufferOrder.putShort((short) 0);
            byteBufferOrder.putShort((short) 0);
            byteBufferOrder.putShort(this.diskNumberStart);
            byteBufferOrder.putShort(this.internalFileAttributes);
            byteBufferOrder.putInt(this.externalFileAttributes);
            byteBufferOrder.putInt(this.localHeaderOffset);
            outputStream.write(bArr);
            outputStream.write(this.filename);
            return 46 + this.filename.length;
        }
    }

    static class LocalHeader {
        static final int MAGIC = 67324752;
        int compressedSize;
        short compressionMethod;
        int crc32;
        short extraFieldLength;
        short fileNameLength;
        byte[] filename;
        short flags;
        short lastModFileDate;
        short lastModFileTime;
        int signature;
        int uncompressedSize;
        short versionNeededToExtract;

        LocalHeader(RandomAccessFile randomAccessFile) throws IOException {
            byte[] bArr = new byte[30];
            randomAccessFile.read(bArr);
            ByteBuffer byteBufferOrder = ByteBuffer.wrap(bArr).order(ByteOrder.LITTLE_ENDIAN);
            int i = byteBufferOrder.getInt();
            this.signature = i;
            if (i != MAGIC) {
                throw new IOException();
            }
            this.versionNeededToExtract = byteBufferOrder.getShort();
            this.flags = byteBufferOrder.getShort();
            this.compressionMethod = byteBufferOrder.getShort();
            this.lastModFileTime = byteBufferOrder.getShort();
            this.lastModFileDate = byteBufferOrder.getShort();
            this.crc32 = byteBufferOrder.getInt();
            this.compressedSize = byteBufferOrder.getInt();
            this.uncompressedSize = byteBufferOrder.getInt();
            this.fileNameLength = byteBufferOrder.getShort();
            this.extraFieldLength = byteBufferOrder.getShort();
            randomAccessFile.skipBytes(ZipAdjust.unsigned(this.fileNameLength) + ZipAdjust.unsigned(this.extraFieldLength));
        }

        int write(OutputStream outputStream) throws IOException {
            byte[] bArr = new byte[30];
            ByteBuffer byteBufferOrder = ByteBuffer.wrap(bArr).order(ByteOrder.LITTLE_ENDIAN);
            byteBufferOrder.putInt(this.signature);
            byteBufferOrder.putShort(this.versionNeededToExtract);
            byteBufferOrder.putShort(this.flags);
            byteBufferOrder.putShort(this.compressionMethod);
            byteBufferOrder.putShort(this.lastModFileTime);
            byteBufferOrder.putShort(this.lastModFileDate);
            byteBufferOrder.putInt(this.crc32);
            byteBufferOrder.putInt(this.compressedSize);
            byteBufferOrder.putInt(this.uncompressedSize);
            byteBufferOrder.putShort(this.fileNameLength);
            byteBufferOrder.putShort((short) 0);
            outputStream.write(bArr);
            outputStream.write(this.filename);
            return 30 + this.filename.length;
        }
    }
}
