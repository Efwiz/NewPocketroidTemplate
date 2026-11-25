package org.jetbrains.kotlin.com.intellij.util;

import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.commons.compress.harmony.pack200.PackingOptions;
import org.jetbrains.kotlin.com.intellij.openapi.util.ThreadLocalCachedByteArray;
import org.jetbrains.kotlin.com.intellij.util.io.DataInputOutputUtil;
import org.jetbrains.kotlin.net.jpountz.lz4.LZ4Compressor;
import org.jetbrains.kotlin.net.jpountz.lz4.LZ4Factory;
import org.jetbrains.kotlin.net.jpountz.lz4.LZ4FastDecompressor;

/* JADX INFO: loaded from: classes3.dex */
public final class CompressionUtil {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final ThreadLocalCachedByteArray spareBufferLocal = new ThreadLocalCachedByteArray();
    private static final AtomicInteger myCompressionRequests = new AtomicInteger();
    private static final AtomicLong myCompressionTime = new AtomicLong();
    private static final AtomicInteger myDecompressionRequests = new AtomicInteger();
    private static final AtomicLong myDecompressionTime = new AtomicLong();
    private static final AtomicLong myDecompressedSize = new AtomicLong();
    private static final AtomicLong mySizeBeforeCompression = new AtomicLong();
    private static final AtomicLong mySizeAfterCompression = new AtomicLong();
    public static final boolean DUMP_COMPRESSION_STATS = SystemProperties.getBooleanProperty("idea.dump.compression.stats", false);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 5:
            case 7:
            case 8:
            case 10:
            case 11:
            case 12:
            case 14:
                str = "@NotNull method %s.%s must not return null";
                break;
            case 6:
            case 9:
            case 13:
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 5:
            case 7:
            case 8:
            case 10:
            case 11:
            case 12:
            case 14:
                i2 = 2;
                break;
            case 6:
            case 9:
            case 13:
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 3:
                objArr[0] = "bytes";
                break;
            case 2:
            default:
                objArr[0] = "out";
                break;
            case 4:
            case 6:
                objArr[0] = "in";
                break;
            case 5:
            case 7:
            case 8:
            case 10:
            case 11:
            case 12:
            case 14:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/CompressionUtil";
                break;
            case 9:
                objArr[0] = "string";
                break;
            case 13:
                objArr[0] = "compressed";
                break;
        }
        switch (i) {
            case 5:
                objArr[1] = "readCompressedWithoutOriginalBufferLength";
                break;
            case 6:
            case 9:
            case 13:
            default:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/util/CompressionUtil";
                break;
            case 7:
            case 8:
                objArr[1] = "readCompressed";
                break;
            case 10:
            case 11:
            case 12:
                objArr[1] = "compressStringRawBytes";
                break;
            case 14:
                objArr[1] = "uncompressStringRawBytes";
                break;
        }
        switch (i) {
            case 2:
            case 3:
                objArr[2] = "writeCompressedWithoutOriginalBufferLength";
                break;
            case 4:
                objArr[2] = "readCompressedWithoutOriginalBufferLength";
                break;
            case 5:
            case 7:
            case 8:
            case 10:
            case 11:
            case 12:
            case 14:
                break;
            case 6:
                objArr[2] = "readCompressed";
                break;
            case 9:
                objArr[2] = "compressStringRawBytes";
                break;
            case 13:
                objArr[2] = "uncompressStringRawBytes";
                break;
            default:
                objArr[2] = "writeCompressed";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 5:
            case 7:
            case 8:
            case 10:
            case 11:
            case 12:
            case 14:
                throw new IllegalStateException(str2);
            case 6:
            case 9:
            case 13:
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    public static int writeCompressed(DataOutput dataOutput, byte[] bArr, int i, int i2) throws IOException {
        if (dataOutput == null) {
            $$$reportNull$$$0(0);
        }
        if (bArr == null) {
            $$$reportNull$$$0(1);
        }
        if (i2 > 64) {
            LZ4Compressor lZ4CompressorCompressor = compressor();
            byte[] buffer = spareBufferLocal.getBuffer(lZ4CompressorCompressor.maxCompressedLength(i2));
            int iCompress = lZ4CompressorCompressor.compress(bArr, i, i2, buffer, 0);
            if (iCompress < i2) {
                DataInputOutputUtil.writeINT(dataOutput, -iCompress);
                DataInputOutputUtil.writeINT(dataOutput, i2 - iCompress);
                dataOutput.write(buffer, 0, iCompress);
                return iCompress;
            }
        }
        DataInputOutputUtil.writeINT(dataOutput, i2);
        dataOutput.write(bArr, i, i2);
        return i2;
    }

    public static int writeCompressedWithoutOriginalBufferLength(DataOutput dataOutput, byte[] bArr, int i) throws IOException {
        if (dataOutput == null) {
            $$$reportNull$$$0(2);
        }
        if (bArr == null) {
            $$$reportNull$$$0(3);
        }
        boolean z = DUMP_COMPRESSION_STATS;
        long jNanoTime = z ? System.nanoTime() : 0L;
        LZ4Compressor lZ4CompressorCompressor = compressor();
        byte[] buffer = spareBufferLocal.getBuffer(lZ4CompressorCompressor.maxCompressedLength(i));
        int iCompress = lZ4CompressorCompressor.compress(bArr, 0, i, buffer, 0);
        long jNanoTime2 = (z ? System.nanoTime() : 0L) - jNanoTime;
        AtomicLong atomicLong = mySizeAfterCompression;
        atomicLong.addAndGet(iCompress);
        AtomicLong atomicLong2 = mySizeBeforeCompression;
        atomicLong2.addAndGet(i);
        int iIncrementAndGet = myCompressionRequests.incrementAndGet();
        long jAddAndGet = myCompressionTime.addAndGet(jNanoTime2);
        if (z && (iIncrementAndGet & 8191) == 0) {
            System.out.println("Compressed " + iIncrementAndGet + " times, size:" + atomicLong2 + "->" + atomicLong + " for " + (jAddAndGet / PackingOptions.SEGMENT_LIMIT) + LanguageCodeUtil.MS);
        }
        DataInputOutputUtil.writeINT(dataOutput, iCompress);
        dataOutput.write(buffer, 0, iCompress);
        return iCompress;
    }

    private static LZ4Compressor compressor() {
        return LZ4Factory.fastestJavaInstance().fastCompressor();
    }

    public static byte[] readCompressedWithoutOriginalBufferLength(DataInput dataInput, int i) throws IOException {
        if (dataInput == null) {
            $$$reportNull$$$0(4);
        }
        int i2 = DataInputOutputUtil.readINT(dataInput);
        byte[] buffer = spareBufferLocal.getBuffer(i2);
        dataInput.readFully(buffer, 0, i2);
        int iIncrementAndGet = myDecompressionRequests.incrementAndGet();
        boolean z = DUMP_COMPRESSION_STATS;
        long jNanoTime = z ? System.nanoTime() : 0L;
        byte[] bArrDecompress = decompressor().decompress(buffer, 0, i);
        long jNanoTime2 = (z ? System.nanoTime() : 0L) - jNanoTime;
        long jAddAndGet = myDecompressedSize.addAndGet(i2);
        long jAddAndGet2 = myDecompressionTime.addAndGet(jNanoTime2);
        if (z && (iIncrementAndGet & 8191) == 0) {
            System.out.println("Decompressed " + iIncrementAndGet + " times, size: " + jAddAndGet + " for " + (jAddAndGet2 / PackingOptions.SEGMENT_LIMIT) + LanguageCodeUtil.MS);
        }
        if (bArrDecompress == null) {
            $$$reportNull$$$0(5);
        }
        return bArrDecompress;
    }

    private static LZ4FastDecompressor decompressor() {
        return LZ4Factory.fastestJavaInstance().fastDecompressor();
    }

    public static byte[] readCompressed(DataInput dataInput) throws IOException {
        if (dataInput == null) {
            $$$reportNull$$$0(6);
        }
        int i = DataInputOutputUtil.readINT(dataInput);
        if (i < 0) {
            int i2 = -i;
            byte[] buffer = spareBufferLocal.getBuffer(i2);
            int i3 = DataInputOutputUtil.readINT(dataInput) + i2;
            dataInput.readFully(buffer, 0, i2);
            byte[] bArr = new byte[i3];
            decompressor().decompress(buffer, 0, bArr, 0, i3);
            return bArr;
        }
        byte[] bArr2 = new byte[i];
        dataInput.readFully(bArr2);
        return bArr2;
    }
}
