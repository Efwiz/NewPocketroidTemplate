package org.jetbrains.kotlin.com.intellij.util.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.jetbrains.kotlin.com.intellij.openapi.util.LowMemoryWatcher;
import org.jetbrains.kotlin.com.intellij.openapi.util.io.BufferExposingByteArrayOutputStream;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtilRt;
import org.jetbrains.kotlin.com.intellij.util.CompressionUtil;
import org.jetbrains.kotlin.com.intellij.util.Consumer;
import org.jetbrains.kotlin.com.intellij.util.SystemProperties;
import org.jetbrains.kotlin.com.intellij.util.containers.SLRUMap;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongArrayList;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.org.jline.reader.LineReader;

/* JADX INFO: loaded from: classes3.dex */
public class CompressedAppendableFile {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static int ourFilesCount;
    private final int myAppendBufferLength;
    private final Path myBaseFile;
    private int myBufferPosition;
    private short[] myChunkLengthTable;
    private long[] myChunkOffsetTable;
    private int myChunkTableLength;
    private final LongArrayList myCompressedChunksFileOffsets;
    private final int myCount;
    private boolean myDirty;
    private long myFileLength;
    private byte[] myNextChunkBuffer;
    private long myUncompressedFileLength;
    private static final boolean doDebug = SystemProperties.getBooleanProperty("idea.compressed.file.self.check", false);
    public static final int PAGE_LENGTH = SystemProperties.getIntProperty("idea.compressed.file.page.length", 32768);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[2];
        objArr[0] = "org/jetbrains/kotlin/com/intellij/util/io/CompressedAppendableFile";
        switch (i) {
            case 1:
            case 2:
                objArr[1] = "loadChunk";
                break;
            case 3:
                objArr[1] = "reallocShortTable";
                break;
            case 4:
                objArr[1] = "decompress";
                break;
            case 5:
                objArr[1] = "getChunksFile";
                break;
            case 6:
                objArr[1] = "getIncompleteChunkFile";
                break;
            default:
                objArr[1] = "getChunkLengthFile";
                break;
        }
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", objArr));
    }

    public CompressedAppendableFile(Path path) throws IOException {
        this(path, 32768);
    }

    private CompressedAppendableFile(Path path, int i) throws IOException {
        this.myCompressedChunksFileOffsets = doDebug ? new LongArrayList() : null;
        this.myUncompressedFileLength = -1L;
        int i2 = ourFilesCount;
        ourFilesCount = i2 + 1;
        this.myCount = i2;
        this.myBaseFile = path;
        this.myAppendBufferLength = i;
        Path parent = getChunksFile().getParent();
        if (Files.exists(parent, new LinkOption[0])) {
            return;
        }
        Files.createDirectories(parent, new FileAttribute[0]);
    }

    public synchronized DataInputStream getStream(long j) throws IOException {
        initChunkLengthTable();
        loadAppendBuffer();
        return new DataInputStream(new SegmentedChunkInputStream(j, this.myChunkTableLength, this.myNextChunkBuffer, this.myBufferPosition));
    }

    protected Path getChunkLengthFile() {
        Path pathResolveSibling = this.myBaseFile.resolveSibling(this.myBaseFile.getFileName() + ".s");
        if (pathResolveSibling == null) {
            $$$reportNull$$$0(0);
        }
        return pathResolveSibling;
    }

    private synchronized void initChunkLengthTable() throws IOException {
        try {
            if (this.myChunkLengthTable != null) {
                return;
            }
            Path chunkLengthFile = getChunkLengthFile();
            if (Files.exists(chunkLengthFile, new LinkOption[0])) {
                DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(new LimitedInputStream(Files.newInputStream(chunkLengthFile, new OpenOption[0]), (int) Files.size(chunkLengthFile)) { // from class: org.jetbrains.kotlin.com.intellij.util.io.CompressedAppendableFile.1
                    @Override // org.jetbrains.kotlin.com.intellij.util.io.LimitedInputStream, java.io.FilterInputStream, java.io.InputStream
                    public int available() {
                        return remainingLimit();
                    }
                }, 32768));
                try {
                    short[] sArrReallocShortTable = new short[(int) (Files.size(chunkLengthFile) / 2)];
                    int i = 0;
                    long j = 0;
                    while (dataInputStream.available() != 0) {
                        int i2 = DataInputOutputUtil.readINT(dataInputStream);
                        j += (long) i2;
                        if (i == sArrReallocShortTable.length) {
                            sArrReallocShortTable = reallocShortTable(sArrReallocShortTable);
                        }
                        int i3 = i + 1;
                        sArrReallocShortTable[i] = (short) i2;
                        if (doDebug) {
                            this.myCompressedChunksFileOffsets.add(j);
                        }
                        i = i3;
                    }
                    this.myChunkLengthTable = sArrReallocShortTable;
                    this.myChunkTableLength = i;
                    int i4 = 32;
                    if (i >= 32) {
                        int i5 = i / 32;
                        long[] jArr = new long[i5];
                        int i6 = 0;
                        long j2 = 0;
                        while (i6 < i5) {
                            int i7 = i6 * 32;
                            int i8 = 0;
                            while (i8 < i4) {
                                j2 += (long) (sArrReallocShortTable[i7 + i8] & 65535);
                                i8++;
                                i5 = i5;
                                i4 = 32;
                            }
                            jArr[i6] = j2;
                            i6++;
                            i5 = i5;
                            i4 = 32;
                        }
                        this.myChunkOffsetTable = jArr;
                        if (doDebug) {
                            for (int i9 = 0; i9 < i; i9++) {
                                calcOffsetOfPage(i9);
                            }
                        }
                    } else {
                        this.myChunkOffsetTable = ArrayUtil.EMPTY_LONG_ARRAY;
                    }
                    this.myFileLength = calcOffsetOfPage(this.myChunkTableLength - 1);
                    dataInputStream.close();
                } catch (Throwable th) {
                    try {
                        dataInputStream.close();
                        throw th;
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                        throw th;
                    }
                }
            } else {
                this.myChunkLengthTable = ArrayUtilRt.EMPTY_SHORT_ARRAY;
                this.myChunkTableLength = 0;
                this.myChunkOffsetTable = ArrayUtil.EMPTY_LONG_ARRAY;
                this.myFileLength = 0L;
            }
            if (this.myUncompressedFileLength == -1) {
                long size = Files.exists(getIncompleteChunkFile(), new LinkOption[0]) ? Files.size(getIncompleteChunkFile()) : 0L;
                long j3 = (((long) this.myChunkTableLength) * ((long) this.myAppendBufferLength)) + size;
                this.myUncompressedFileLength = j3;
                if (j3 != this.myFileLength + size && CompressionUtil.DUMP_COMPRESSION_STATS) {
                    System.out.println(this.myUncompressedFileLength + "->" + (this.myFileLength + size) + " for " + this.myBaseFile);
                }
            }
        } catch (Throwable th3) {
            throw th3;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized byte[] loadChunk(int i) throws IOException {
        try {
            try {
                if (this.myChunkLengthTable == null) {
                    initChunkLengthTable();
                }
                DataInputStream chunkStream = getChunkStream(i);
                try {
                    if (chunkStream.available() > 0) {
                        byte[] bArrDecompress = decompress(chunkStream);
                        int length = bArrDecompress.length;
                        if (chunkStream != null) {
                            chunkStream.close();
                        }
                        if (bArrDecompress == null) {
                            $$$reportNull$$$0(1);
                        }
                        return bArrDecompress;
                    }
                    if (chunkStream != null) {
                        chunkStream.close();
                    }
                    byte[] bArr = ArrayUtilRt.EMPTY_BYTE_ARRAY;
                    if (bArr == null) {
                        $$$reportNull$$$0(2);
                    }
                    return bArr;
                } catch (Throwable th) {
                    if (chunkStream != null) {
                        try {
                            chunkStream.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                throw th3;
            }
        } catch (AssertionError | RuntimeException e) {
            throw new IOException(e);
        }
    }

    private DataInputStream getChunkStream(int i) throws IOException {
        int i2;
        long jCalcOffsetOfPage;
        long jCalcOffsetOfPage2 = i < this.myChunkTableLength ? calcOffsetOfPage(i) : this.myFileLength;
        if (i > 0) {
            jCalcOffsetOfPage = calcOffsetOfPage(i - 1);
            i2 = (int) (jCalcOffsetOfPage2 - jCalcOffsetOfPage);
        } else {
            i2 = (int) jCalcOffsetOfPage2;
            jCalcOffsetOfPage = 0;
        }
        return new DataInputStream(getChunkInputStream(jCalcOffsetOfPage, i2));
    }

    private long calcOffsetOfPage(int i) {
        int i2 = i + 1;
        int i3 = i2 / 32;
        long j = i3 > 0 ? this.myChunkOffsetTable[i3 - 1] : 0L;
        int i4 = i3 * 32;
        int i5 = i2 % 32;
        for (int i6 = 0; i6 < i5; i6++) {
            j += (long) (this.myChunkLengthTable[i4 + i6] & 65535);
        }
        return j;
    }

    protected InputStream getChunkInputStream(long j, int i) throws IOException {
        InputStream inputStreamNewInputStream = Files.newInputStream(getChunksFile(), new OpenOption[0]);
        long j2 = j;
        while (j2 > 0) {
            long jSkip = inputStreamNewInputStream.skip(j2);
            if (jSkip == 0) {
                throw new EOFException("Unable to skip " + j + " bytes: end-of-file reached");
            }
            j2 -= jSkip;
        }
        return new BufferedInputStream(new LimitedInputStream(inputStreamNewInputStream, i) { // from class: org.jetbrains.kotlin.com.intellij.util.io.CompressedAppendableFile.2
            @Override // org.jetbrains.kotlin.com.intellij.util.io.LimitedInputStream, java.io.FilterInputStream, java.io.InputStream
            public int available() {
                return remainingLimit();
            }
        }, i);
    }

    public synchronized void append(byte[] bArr, int i, int i2) throws IOException {
        if (i2 == 0) {
            return;
        }
        if (this.myNextChunkBuffer == null) {
            loadAppendBuffer();
        }
        byte[] bArr2 = this.myNextChunkBuffer;
        if (bArr2.length != this.myAppendBufferLength) {
            int i3 = this.myBufferPosition;
            if (i3 + i2 >= bArr2.length) {
                int iCalcBufferSize = calcBufferSize(i3 + i2);
                byte[] bArr3 = this.myNextChunkBuffer;
                if (iCalcBufferSize != bArr3.length) {
                    this.myNextChunkBuffer = Arrays.copyOf(bArr3, iCalcBufferSize);
                }
            }
        }
        int i4 = i2;
        while (i4 > 0) {
            int iMin = Math.min(this.myNextChunkBuffer.length - this.myBufferPosition, i4);
            System.arraycopy(bArr, i, this.myNextChunkBuffer, this.myBufferPosition, iMin);
            this.myBufferPosition += iMin;
            i += iMin;
            i4 -= iMin;
            saveNextChunkIfNeeded();
        }
        if (this.myUncompressedFileLength == -1) {
            length();
        }
        this.myUncompressedFileLength += (long) i2;
        this.myDirty = true;
    }

    private synchronized void loadAppendBuffer() throws IOException {
        int i;
        if (this.myNextChunkBuffer != null) {
            return;
        }
        Path incompleteChunkFile = getIncompleteChunkFile();
        int i2 = 0;
        if (Files.exists(incompleteChunkFile, new LinkOption[0])) {
            int size = (int) Files.size(incompleteChunkFile);
            this.myBufferPosition = size;
            this.myNextChunkBuffer = new byte[calcBufferSize(size)];
            InputStream inputStreamNewInputStream = Files.newInputStream(incompleteChunkFile, new OpenOption[0]);
            while (true) {
                try {
                    int i3 = this.myBufferPosition;
                    if (i2 >= i3 || (i = inputStreamNewInputStream.read(this.myNextChunkBuffer, i2, i3 - i2)) < 0) {
                        break;
                    } else {
                        i2 += i;
                    }
                } catch (Throwable th) {
                    if (inputStreamNewInputStream != null) {
                        try {
                            inputStreamNewInputStream.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            }
            if (inputStreamNewInputStream != null) {
                inputStreamNewInputStream.close();
            }
        } else {
            this.myBufferPosition = 0;
            this.myNextChunkBuffer = new byte[1024];
        }
    }

    private int calcBufferSize(int i) {
        return Math.min(this.myAppendBufferLength, Integer.highestOneBit(Math.max(1023, i)) << 1);
    }

    private void saveNextChunkIfNeeded() throws IOException {
        if (this.myBufferPosition == this.myNextChunkBuffer.length) {
            BufferExposingByteArrayOutputStream bufferExposingByteArrayOutputStream = new BufferExposingByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(bufferExposingByteArrayOutputStream);
            compress(dataOutputStream, this.myNextChunkBuffer);
            dataOutputStream.close();
            saveChunk(bufferExposingByteArrayOutputStream);
            this.myBufferPosition = 0;
            initChunkLengthTable();
            long size = this.myFileLength + ((long) bufferExposingByteArrayOutputStream.size());
            this.myFileLength = size;
            if (doDebug) {
                this.myCompressedChunksFileOffsets.add(size);
            }
            short[] sArr = this.myChunkLengthTable;
            if (sArr.length == this.myChunkTableLength) {
                this.myChunkLengthTable = reallocShortTable(sArr);
            }
            short[] sArr2 = this.myChunkLengthTable;
            int i = this.myChunkTableLength;
            this.myChunkTableLength = i + 1;
            sArr2[i] = (short) bufferExposingByteArrayOutputStream.size();
            int i2 = this.myChunkTableLength / 32;
            long[] jArr = this.myChunkOffsetTable;
            if (i2 > jArr.length) {
                long[] jArr2 = new long[jArr.length + 1];
                System.arraycopy(jArr, 0, jArr2, 0, jArr.length);
                jArr2[this.myChunkOffsetTable.length] = this.myFileLength;
                this.myChunkOffsetTable = jArr2;
            }
            int i3 = this.myAppendBufferLength;
            byte[] bArr = new byte[i3];
            System.arraycopy(this.myNextChunkBuffer, 0, bArr, 0, i3);
            FileChunkReadCache.ourDecompressedCache.put(this, this.myChunkTableLength - 1, bArr);
        }
    }

    private static short[] reallocShortTable(short[] sArr) {
        short[] sArrRealloc = ArrayUtil.realloc(sArr, Math.max((sArr.length * 8) / 5, sArr.length + 1));
        if (sArrRealloc == null) {
            $$$reportNull$$$0(3);
        }
        return sArrRealloc;
    }

    protected int compress(DataOutputStream dataOutputStream, byte[] bArr) throws IOException {
        return CompressionUtil.writeCompressedWithoutOriginalBufferLength(dataOutputStream, bArr, this.myAppendBufferLength);
    }

    protected byte[] decompress(DataInputStream dataInputStream) throws IOException {
        byte[] compressedWithoutOriginalBufferLength = CompressionUtil.readCompressedWithoutOriginalBufferLength(dataInputStream, this.myAppendBufferLength);
        if (compressedWithoutOriginalBufferLength == null) {
            $$$reportNull$$$0(4);
        }
        return compressedWithoutOriginalBufferLength;
    }

    private void saveChunk(BufferExposingByteArrayOutputStream bufferExposingByteArrayOutputStream) throws IOException {
        DataOutputStream chunkAppendStream = getChunkAppendStream();
        try {
            chunkAppendStream.write(bufferExposingByteArrayOutputStream.getInternalBuffer(), 0, bufferExposingByteArrayOutputStream.size());
            if (chunkAppendStream != null) {
                chunkAppendStream.close();
            }
            DataOutputStream chunkLengthAppendStream = getChunkLengthAppendStream();
            try {
                DataInputOutputUtil.writeINT(chunkLengthAppendStream, bufferExposingByteArrayOutputStream.size());
                if (chunkLengthAppendStream != null) {
                    chunkLengthAppendStream.close();
                }
            } catch (Throwable th) {
                if (chunkLengthAppendStream != null) {
                    try {
                        chunkLengthAppendStream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (Throwable th3) {
            if (chunkAppendStream != null) {
                try {
                    chunkAppendStream.close();
                } catch (Throwable th4) {
                    th3.addSuppressed(th4);
                }
            }
            throw th3;
        }
    }

    protected DataOutputStream getChunkLengthAppendStream() throws IOException {
        return new DataOutputStream(new BufferedOutputStream(new FileOutputStream(getChunkLengthFile().toFile(), true)));
    }

    protected DataOutputStream getChunkAppendStream() throws IOException {
        return new DataOutputStream(new BufferedOutputStream(new FileOutputStream(getChunksFile().toFile(), true)));
    }

    protected Path getChunksFile() {
        Path pathResolveSibling = this.myBaseFile.resolveSibling(this.myBaseFile.getFileName() + ".a");
        if (pathResolveSibling == null) {
            $$$reportNull$$$0(5);
        }
        return pathResolveSibling;
    }

    private void saveIncompleteChunk() {
        if (this.myNextChunkBuffer == null || !this.myDirty) {
            return;
        }
        Path incompleteChunkFile = getIncompleteChunkFile();
        try {
            saveNextChunkIfNeeded();
            if (this.myBufferPosition != 0) {
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(Files.newOutputStream(incompleteChunkFile, StandardOpenOption.CREATE));
                try {
                    bufferedOutputStream.write(this.myNextChunkBuffer, 0, this.myBufferPosition);
                    bufferedOutputStream.close();
                } catch (Throwable th) {
                    try {
                        bufferedOutputStream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } else if (Files.exists(incompleteChunkFile, new LinkOption[0])) {
                Files.delete(incompleteChunkFile);
            }
            this.myDirty = false;
        } catch (NoSuchFileException e) {
            Path parent = incompleteChunkFile.getParent();
            if (!Files.exists(parent, new LinkOption[0])) {
                try {
                    Files.createDirectories(parent, new FileAttribute[0]);
                    saveIncompleteChunk();
                    return;
                } catch (IOException unused) {
                    throw new RuntimeException("Failed to write: " + incompleteChunkFile, e);
                }
            }
            throw new RuntimeException(e);
        } catch (IOException e2) {
            throw new RuntimeException(e2);
        }
    }

    private Path getIncompleteChunkFile() {
        Path pathResolveSibling = this.myBaseFile.resolveSibling(this.myBaseFile.getFileName() + ".at");
        if (pathResolveSibling == null) {
            $$$reportNull$$$0(6);
        }
        return pathResolveSibling;
    }

    public synchronized void force() {
        saveIncompleteChunk();
    }

    public synchronized void dispose() {
        force();
        FileChunkReadCache.ourDecompressedCache.clear(this);
    }

    public synchronized long length() {
        if (this.myUncompressedFileLength == -1 && this.myChunkLengthTable == null) {
            try {
                initChunkLengthTable();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return this.myUncompressedFileLength;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class FileChunkReadCache {
        private static final FileChunkReadCache ourDecompressedCache = new FileChunkReadCache();
        private final SLRUMap<FileChunkKey<CompressedAppendableFile>, byte[]> myMap = new SLRUMap<>(64, 64);

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 2 ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
            Object[] objArr = new Object[i != 2 ? 2 : 3];
            if (i != 2) {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/io/CompressedAppendableFile$FileChunkReadCache";
            } else {
                objArr[0] = "file";
            }
            if (i != 2) {
                objArr[1] = "get";
            } else {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/util/io/CompressedAppendableFile$FileChunkReadCache";
            }
            if (i == 2) {
                objArr[2] = LineReader.CLEAR;
            }
            String str2 = String.format(str, objArr);
            if (i == 2) {
                throw new IllegalArgumentException(str2);
            }
        }

        private FileChunkReadCache() {
        }

        static {
            LowMemoryWatcher.register(new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.util.io.CompressedAppendableFile$FileChunkReadCache$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    CompressedAppendableFile.FileChunkReadCache.ourDecompressedCache.clear();
                }
            });
        }

        byte[] get(CompressedAppendableFile compressedAppendableFile, int i) throws IOException {
            synchronized (this) {
                long j = i;
                byte[] bArr = this.myMap.get(new FileChunkKey<>(compressedAppendableFile, j));
                if (bArr != null) {
                    if (bArr == null) {
                        $$$reportNull$$$0(0);
                    }
                    return bArr;
                }
                byte[] bArrLoadChunk = compressedAppendableFile.loadChunk(i);
                put(compressedAppendableFile, j, bArrLoadChunk);
                if (bArrLoadChunk == null) {
                    $$$reportNull$$$0(1);
                }
                return bArrLoadChunk;
            }
        }

        void put(CompressedAppendableFile compressedAppendableFile, long j, byte[] bArr) {
            synchronized (this) {
                this.myMap.put(new FileChunkKey<>(compressedAppendableFile, j), bArr);
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public void clear() {
            synchronized (this) {
                this.myMap.clear();
            }
        }

        void clear(final CompressedAppendableFile compressedAppendableFile) {
            if (compressedAppendableFile == null) {
                $$$reportNull$$$0(2);
            }
            final HashSet hashSet = new HashSet();
            synchronized (this) {
                this.myMap.iterateKeys(new Consumer() { // from class: org.jetbrains.kotlin.com.intellij.util.io.CompressedAppendableFile$FileChunkReadCache$$ExternalSyntheticLambda0
                    @Override // org.jetbrains.kotlin.com.intellij.util.Consumer
                    public final void consume(Object obj) {
                        CompressedAppendableFile.FileChunkReadCache.lambda$clear$1(compressedAppendableFile, hashSet, (FileChunkKey) obj);
                    }
                });
                Iterator it = hashSet.iterator();
                while (it.hasNext()) {
                    this.myMap.remove((FileChunkKey) it.next());
                }
            }
        }

        static /* synthetic */ void lambda$clear$1(CompressedAppendableFile compressedAppendableFile, Set set, FileChunkKey fileChunkKey) {
            if (fileChunkKey.getOwner() == compressedAppendableFile) {
                set.add(fileChunkKey);
            }
        }
    }

    private class SegmentedChunkInputStream extends InputStream {
        private InputStream bytesFromCompressedBlock;
        private InputStream bytesFromTempAppendBlock;
        private final int myBufferPositionSnapshot;
        private final int myChunkLengthTableSnapshotLength;
        private int myCurrentPageNumber;
        private final byte[] myNextChunkBufferSnapshot;
        private int myPageOffset;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "org/jetbrains/kotlin/com/intellij/util/io/CompressedAppendableFile$SegmentedChunkInputStream", "read"));
        }

        SegmentedChunkInputStream(long j, int i, byte[] bArr, int i2) {
            this.myChunkLengthTableSnapshotLength = i;
            this.myNextChunkBufferSnapshot = bArr;
            this.myBufferPositionSnapshot = i2;
            this.myCurrentPageNumber = (int) (j / ((long) CompressedAppendableFile.this.myAppendBufferLength));
            this.myPageOffset = (int) (j % ((long) CompressedAppendableFile.this.myAppendBufferLength));
        }

        @Override // java.io.InputStream
        public int read(byte[] bArr, int i, int i2) throws IOException {
            int i3;
            if (bArr == null) {
                $$$reportNull$$$0(0);
            }
            if (this.bytesFromCompressedBlock == null) {
                byte[] bArr2 = this.myCurrentPageNumber < this.myChunkLengthTableSnapshotLength ? FileChunkReadCache.ourDecompressedCache.get(CompressedAppendableFile.this, this.myCurrentPageNumber) : ArrayUtilRt.EMPTY_BYTE_ARRAY;
                this.bytesFromCompressedBlock = new ByteArrayInputStream(bArr2, this.myPageOffset, bArr2.length);
            }
            if (this.bytesFromCompressedBlock.available() > 0) {
                i3 = this.bytesFromCompressedBlock.read(bArr, i, i2);
                int i4 = this.myPageOffset + i3;
                this.myPageOffset = i4;
                if (i4 == CompressedAppendableFile.this.myAppendBufferLength) {
                    this.myCurrentPageNumber++;
                    this.myPageOffset = 0;
                }
                if (i3 == i2) {
                    return i3;
                }
            } else {
                i3 = 0;
            }
            while (this.myCurrentPageNumber < this.myChunkLengthTableSnapshotLength) {
                byte[] bArr3 = FileChunkReadCache.ourDecompressedCache.get(CompressedAppendableFile.this, this.myCurrentPageNumber);
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr3, 0, bArr3.length);
                this.bytesFromCompressedBlock = byteArrayInputStream;
                int i5 = byteArrayInputStream.read(bArr, i + i3, i2 - i3);
                int i6 = this.myPageOffset + i5;
                this.myPageOffset = i6;
                if (i6 == CompressedAppendableFile.this.myAppendBufferLength) {
                    this.myCurrentPageNumber++;
                    this.myPageOffset = 0;
                }
                i3 += i5;
                if (i3 == i2) {
                    return i3;
                }
            }
            if (this.bytesFromTempAppendBlock == null) {
                this.bytesFromTempAppendBlock = new ByteArrayInputStream(this.myNextChunkBufferSnapshot, this.myPageOffset, this.myBufferPositionSnapshot);
            }
            return i3 + this.bytesFromTempAppendBlock.read(bArr, i + i3, i2 - i3);
        }

        @Override // java.io.InputStream
        public int read() throws IOException {
            byte[] bArr = {0};
            if (read(bArr) == -1) {
                return -1;
            }
            return bArr[0] & 255;
        }
    }

    public int hashCode() {
        return this.myCount;
    }
}
