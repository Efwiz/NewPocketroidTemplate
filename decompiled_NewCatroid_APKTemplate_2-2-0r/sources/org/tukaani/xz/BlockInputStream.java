package org.tukaani.xz;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import org.tukaani.xz.check.Check;
import org.tukaani.xz.common.DecoderUtil;

/* JADX INFO: loaded from: classes6.dex */
class BlockInputStream extends InputStream {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final Check check;
    private long compressedSizeInHeader;
    private long compressedSizeLimit;
    private InputStream filterChain;
    private final int headerSize;
    private final CountingInputStream inCounted;
    private final DataInputStream inData;
    private long uncompressedSizeInHeader;
    private final boolean verifyCheck;
    private long uncompressedSize = 0;
    private boolean endReached = false;
    private final byte[] tempBuf = new byte[1];

    public BlockInputStream(InputStream inputStream, Check check, boolean z, int i, long j, long j2, ArrayCache arrayCache) throws IndexIndicatorException, IOException {
        this.uncompressedSizeInHeader = -1L;
        this.compressedSizeInHeader = -1L;
        this.check = check;
        this.verifyCheck = z;
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        this.inData = dataInputStream;
        int unsignedByte = dataInputStream.readUnsignedByte();
        if (unsignedByte == 0) {
            throw new IndexIndicatorException();
        }
        int i2 = (unsignedByte + 1) * 4;
        this.headerSize = i2;
        byte[] bArr = new byte[i2];
        bArr[0] = (byte) unsignedByte;
        dataInputStream.readFully(bArr, 1, i2 - 1);
        if (!DecoderUtil.isCRC32Valid(bArr, 0, i2 - 4, i2 - 4)) {
            throw new CorruptedInputException("XZ Block Header is corrupt");
        }
        int i3 = bArr[1];
        if ((i3 & 60) != 0) {
            throw new UnsupportedOptionsException("Unsupported options in XZ Block Header");
        }
        int i4 = i3 & 3;
        int i5 = i4 + 1;
        long[] jArr = new long[i5];
        byte[][] bArr2 = new byte[i5][];
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr, 2, i2 - 6);
        try {
            this.compressedSizeLimit = (9223372036854775804L - ((long) i2)) - ((long) check.getSize());
            if ((bArr[1] & 64) != 0) {
                long jDecodeVLI = DecoderUtil.decodeVLI(byteArrayInputStream);
                this.compressedSizeInHeader = jDecodeVLI;
                if (jDecodeVLI != 0) {
                    try {
                        if (jDecodeVLI <= this.compressedSizeLimit) {
                            this.compressedSizeLimit = jDecodeVLI;
                        }
                    } catch (IOException unused) {
                        throw new CorruptedInputException("XZ Block Header is corrupt");
                    }
                }
                throw new CorruptedInputException();
            }
            if ((bArr[1] & 128) != 0) {
                this.uncompressedSizeInHeader = DecoderUtil.decodeVLI(byteArrayInputStream);
            }
            int i6 = 0;
            while (i6 < i5) {
                jArr[i6] = DecoderUtil.decodeVLI(byteArrayInputStream);
                long jDecodeVLI2 = DecoderUtil.decodeVLI(byteArrayInputStream);
                int i7 = i4;
                if (jDecodeVLI2 > byteArrayInputStream.available()) {
                    throw new CorruptedInputException();
                }
                byte[] bArr3 = new byte[(int) jDecodeVLI2];
                bArr2[i6] = bArr3;
                byteArrayInputStream.read(bArr3);
                i6++;
                i4 = i7;
            }
            int i8 = i4;
            for (int iAvailable = byteArrayInputStream.available(); iAvailable > 0; iAvailable--) {
                if (byteArrayInputStream.read() != 0) {
                    throw new UnsupportedOptionsException("Unsupported options in XZ Block Header");
                }
            }
            if (j != -1) {
                long size = this.headerSize + check.getSize();
                if (size >= j) {
                    throw new CorruptedInputException("XZ Index does not match a Block Header");
                }
                long j3 = j - size;
                if (j3 <= this.compressedSizeLimit) {
                    long j4 = this.compressedSizeInHeader;
                    if (j4 == -1 || j4 == j3) {
                        long j5 = this.uncompressedSizeInHeader;
                        if (j5 != -1 && j5 != j2) {
                            throw new CorruptedInputException("XZ Index does not match a Block Header");
                        }
                        this.compressedSizeLimit = j3;
                        this.compressedSizeInHeader = j3;
                        this.uncompressedSizeInHeader = j2;
                    }
                }
                throw new CorruptedInputException("XZ Index does not match a Block Header");
            }
            FilterDecoder[] filterDecoderArr = new FilterDecoder[i5];
            for (int i9 = 0; i9 < i5; i9++) {
                long j6 = jArr[i9];
                if (j6 == 33) {
                    filterDecoderArr[i9] = new LZMA2Decoder(bArr2[i9]);
                } else if (j6 == 3) {
                    filterDecoderArr[i9] = new DeltaDecoder(bArr2[i9]);
                } else {
                    if (!BCJDecoder.isBCJFilterID(j6)) {
                        throw new UnsupportedOptionsException("Unknown Filter ID " + jArr[i9]);
                    }
                    filterDecoderArr[i9] = new BCJDecoder(jArr[i9], bArr2[i9]);
                }
            }
            RawCoder.validate(filterDecoderArr);
            if (i >= 0) {
                int memoryUsage = 0;
                for (int i10 = 0; i10 < i5; i10++) {
                    memoryUsage += filterDecoderArr[i10].getMemoryUsage();
                }
                if (memoryUsage > i) {
                    throw new MemoryLimitException(memoryUsage, i);
                }
            }
            CountingInputStream countingInputStream = new CountingInputStream(inputStream);
            this.inCounted = countingInputStream;
            this.filterChain = countingInputStream;
            for (int i11 = i8; i11 >= 0; i11--) {
                this.filterChain = filterDecoderArr[i11].getInputStream(this.filterChain, arrayCache);
            }
        } catch (IOException unused2) {
        }
    }

    private void validate() throws IOException {
        long size = this.inCounted.getSize();
        long j = this.compressedSizeInHeader;
        if (j == -1 || j == size) {
            long j2 = this.uncompressedSizeInHeader;
            if (j2 == -1 || j2 == this.uncompressedSize) {
                while (true) {
                    long j3 = 1 + size;
                    if ((size & 3) == 0) {
                        byte[] bArr = new byte[this.check.getSize()];
                        this.inData.readFully(bArr);
                        if (this.verifyCheck && !Arrays.equals(this.check.finish(), bArr)) {
                            throw new CorruptedInputException("Integrity check (" + this.check.getName() + ") does not match");
                        }
                        return;
                    }
                    if (this.inData.readUnsignedByte() != 0) {
                        throw new CorruptedInputException();
                    }
                    size = j3;
                }
            }
        }
        throw new CorruptedInputException();
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        return this.filterChain.available();
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        try {
            this.filterChain.close();
        } catch (IOException unused) {
        }
        this.filterChain = null;
    }

    public long getUncompressedSize() {
        return this.uncompressedSize;
    }

    public long getUnpaddedSize() {
        return ((long) this.headerSize) + this.inCounted.getSize() + ((long) this.check.getSize());
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        if (read(this.tempBuf, 0, 1) == -1) {
            return -1;
        }
        return this.tempBuf[0] & 255;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (this.endReached) {
            return -1;
        }
        int i3 = this.filterChain.read(bArr, i, i2);
        if (i3 > 0) {
            if (this.verifyCheck) {
                this.check.update(bArr, i, i3);
            }
            this.uncompressedSize += (long) i3;
            long size = this.inCounted.getSize();
            if (size >= 0 && size <= this.compressedSizeLimit) {
                long j = this.uncompressedSize;
                if (j >= 0) {
                    long j2 = this.uncompressedSizeInHeader;
                    if (j2 == -1 || j <= j2) {
                        if (i3 < i2 || j == j2) {
                            if (this.filterChain.read() != -1) {
                                throw new CorruptedInputException();
                            }
                            validate();
                            this.endReached = true;
                        }
                    }
                }
            }
            throw new CorruptedInputException();
        }
        if (i3 == -1) {
            validate();
            this.endReached = true;
        }
        return i3;
    }
}
