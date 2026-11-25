package org.jetbrains.kotlin.com.intellij.util.io;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import org.apache.commons.compress.harmony.pack200.PackingOptions;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.Comparing;
import org.jetbrains.kotlin.com.intellij.openapi.util.ThreadLocalCachedByteArray;
import org.jetbrains.kotlin.com.intellij.openapi.util.io.BufferExposingByteArrayOutputStream;
import org.jetbrains.kotlin.com.intellij.openapi.util.io.ByteArraySequence;
import org.jetbrains.kotlin.com.intellij.util.MathUtil;
import org.jetbrains.kotlin.com.intellij.util.SystemProperties;
import org.jetbrains.kotlin.fir.analysis.collectors.AbstractDiagnosticCollector;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;

/* JADX INFO: loaded from: classes3.dex */
public final class PersistentHashMapValueStorage {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final int BLOCK_SIZE_TO_WRITE_WHEN_SOFT_MAX_RETAINED_LIMIT_IS_HIT = 1024;
    private static final int CACHE_PROBATIONAL_QUEUE_SIZE = 20;
    private static final int CACHE_PROTECTED_QUEUE_SIZE = 10;
    private static final long MAX_RETAINED_LIMIT_WHEN_COMPACTING = 104857600;
    static final long SOFT_MAX_RETAINED_LIMIT = 10485760;
    private static final FileAccessorCache<Path, OutputStream> ourAppendersCache;
    private static final int ourBufferLength = 1024;
    private static final FileAccessorCache<Path, FileChannelWithSizeTracking> ourFileChannelCache;
    private static final FileAccessorCache<Path, RAReader> ourReadersCache;
    private int myChunks;
    private long myChunksBytesAfterRemoval;
    private long myChunksOriginalBytes;
    private long myChunksReadingTime;
    private long myChunksRemovalTime;
    private boolean myCompactionMode;
    private RAReader myCompactionModeReader;
    private final CompressedAppendableFile myCompressedAppendableFile;
    private int myLastReportedChunksCount;
    private final CreationTimeOptions myOptions;
    private final Path myPath;
    private volatile long mySize;
    public static final boolean COMPRESSION_ENABLED = SystemProperties.getBooleanProperty("idea.compression.enabled", true);
    private static final ThreadLocalCachedByteArray myBuffer = new ThreadLocalCachedByteArray();
    private static final boolean ourDumpChunkRemovalTime = SystemProperties.getBooleanProperty("idea.phmp.dump.chunk.removal.time", false);

    private interface RAReader {
        void dispose() throws IOException;

        void get(long j, byte[] bArr, int i, int i2) throws IOException;
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
            default:
                str = "@NotNull method %s.%s must not return null";
                break;
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
                i2 = 3;
                break;
            default:
                i2 = 2;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 14:
                objArr[0] = "path";
                break;
            case 2:
            case 15:
                objArr[0] = "options";
                break;
            case 3:
                objArr[0] = "dataOutputStream";
                break;
            case 4:
            case 6:
                objArr[0] = AbstractDiagnosticCollector.SUPPRESS_ALL_INFOS;
                break;
            case 5:
            case 7:
                objArr[0] = "storage";
                break;
            case 8:
                objArr[0] = "appender";
                break;
            case 9:
                objArr[0] = "result";
                break;
            case 10:
            case 11:
                objArr[0] = "in";
                break;
            case 12:
                objArr[0] = "buffer";
                break;
            case 13:
                objArr[0] = "handle";
                break;
            default:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/io/PersistentHashMapValueStorage";
                break;
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/util/io/PersistentHashMapValueStorage";
                break;
            default:
                objArr[1] = "getOptions";
                break;
        }
        switch (i) {
            case 1:
            case 2:
                objArr[2] = "<init>";
                break;
            case 3:
                objArr[2] = "saveHeader";
                break;
            case 4:
            case 5:
                objArr[2] = "compactValuesWithoutChunks";
                break;
            case 6:
            case 7:
                objArr[2] = "compactValues";
                break;
            case 8:
            case 9:
                objArr[2] = "compactChunks";
                break;
            case 10:
                objArr[2] = "readChunkSize";
                break;
            case 11:
                objArr[2] = "readPrevChunkAddress";
                break;
            case 12:
                objArr[2] = "toDataInputStream";
                break;
            case 13:
                objArr[2] = "toDataOutputStream";
                break;
            case 14:
            case 15:
                objArr[2] = CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME;
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
                throw new IllegalArgumentException(str2);
            default:
                throw new IllegalStateException(str2);
        }
    }

    public static final class CreationTimeOptions {
        private final boolean myCompactChunksWithValueDeserialization;
        private final boolean myHasNoChunks;
        private final IOCancellationCallback myIOCancellationCallback;
        private final boolean myReadOnly;
        private final boolean myUseCompression;
        public static final ThreadLocal<Boolean> READONLY = new ThreadLocal<>();
        public static final ThreadLocal<Boolean> COMPACT_CHUNKS_WITH_VALUE_DESERIALIZATION = new ThreadLocal<>();
        public static final ThreadLocal<Boolean> HAS_NO_CHUNKS = new ThreadLocal<>();
        static final ThreadLocal<Boolean> DO_COMPRESSION = new ThreadLocal<Boolean>() { // from class: org.jetbrains.kotlin.com.intellij.util.io.PersistentHashMapValueStorage.CreationTimeOptions.1
            /* JADX INFO: Access modifiers changed from: protected */
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.lang.ThreadLocal
            public Boolean initialValue() {
                return Boolean.valueOf(PersistentHashMapValueStorage.COMPRESSION_ENABLED);
            }
        };

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "callback", "org/jetbrains/kotlin/com/intellij/util/io/PersistentHashMapValueStorage$CreationTimeOptions", "<init>"));
        }

        private CreationTimeOptions(IOCancellationCallback iOCancellationCallback, boolean z, boolean z2, boolean z3, boolean z4) {
            if (iOCancellationCallback == null) {
                $$$reportNull$$$0(0);
            }
            this.myIOCancellationCallback = iOCancellationCallback;
            this.myReadOnly = z;
            this.myCompactChunksWithValueDeserialization = z2;
            this.myHasNoChunks = z3;
            this.myUseCompression = z4;
        }

        int getVersion() {
            return ((this.myHasNoChunks ? 10 : 0) * 31) + (this.myUseCompression ? 19 : 0);
        }

        boolean isReadOnly() {
            return this.myReadOnly;
        }

        boolean useCompression() {
            return this.myUseCompression;
        }

        CreationTimeOptions setReadOnly() {
            return new CreationTimeOptions(this.myIOCancellationCallback, true, this.myCompactChunksWithValueDeserialization, this.myHasNoChunks, this.myUseCompression);
        }

        static CreationTimeOptions threadLocalOptions() {
            return new CreationTimeOptions(IOCancellationCallbackHolder.INSTANCE.getUsedIoCallback(), READONLY.get() == Boolean.TRUE, COMPACT_CHUNKS_WITH_VALUE_DESERIALIZATION.get() == Boolean.TRUE, HAS_NO_CHUNKS.get() == Boolean.TRUE, DO_COMPRESSION.get() == Boolean.TRUE);
        }
    }

    CreationTimeOptions getOptions() {
        CreationTimeOptions creationTimeOptions = this.myOptions;
        if (creationTimeOptions == null) {
            $$$reportNull$$$0(0);
        }
        return creationTimeOptions;
    }

    static {
        int i = 20;
        ourFileChannelCache = new FileAccessorCache<Path, FileChannelWithSizeTracking>(i, 40) { // from class: org.jetbrains.kotlin.com.intellij.util.io.PersistentHashMapValueStorage.1
            private static /* synthetic */ void $$$reportNull$$$0(int i2) {
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "fileAccessor", "org/jetbrains/kotlin/com/intellij/util/io/PersistentHashMapValueStorage$1", "disposeAccessor"));
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // org.jetbrains.kotlin.com.intellij.util.io.FileAccessorCache
            public FileChannelWithSizeTracking createAccessor(Path path) throws IOException {
                return new FileChannelWithSizeTracking(path);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // org.jetbrains.kotlin.com.intellij.util.io.FileAccessorCache
            public void disposeAccessor(FileChannelWithSizeTracking fileChannelWithSizeTracking) throws Throwable {
                if (fileChannelWithSizeTracking == null) {
                    $$$reportNull$$$0(0);
                }
                fileChannelWithSizeTracking.close();
            }
        };
        int i2 = 10;
        ourAppendersCache = new FileAccessorCache<Path, OutputStream>(i2, i) { // from class: org.jetbrains.kotlin.com.intellij.util.io.PersistentHashMapValueStorage.2
            private static /* synthetic */ void $$$reportNull$$$0(int i3) {
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "fileAccessor", "org/jetbrains/kotlin/com/intellij/util/io/PersistentHashMapValueStorage$2", "disposeAccessor"));
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // org.jetbrains.kotlin.com.intellij.util.io.FileAccessorCache
            public OutputStream createAccessor(Path path) {
                return new BufferedOutputStream(new OutputStreamOverRandomAccessFileCache(path));
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // org.jetbrains.kotlin.com.intellij.util.io.FileAccessorCache
            public void disposeAccessor(OutputStream outputStream) throws IOException {
                if (outputStream == null) {
                    $$$reportNull$$$0(0);
                }
                outputStream.close();
            }
        };
        ourReadersCache = new FileAccessorCache<Path, RAReader>(i2, i) { // from class: org.jetbrains.kotlin.com.intellij.util.io.PersistentHashMapValueStorage.3
            private static /* synthetic */ void $$$reportNull$$$0(int i3) {
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "fileAccessor", "org/jetbrains/kotlin/com/intellij/util/io/PersistentHashMapValueStorage$3", "disposeAccessor"));
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // org.jetbrains.kotlin.com.intellij.util.io.FileAccessorCache
            public RAReader createAccessor(Path path) {
                return new ReaderOverFileChannelCache(path);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // org.jetbrains.kotlin.com.intellij.util.io.FileAccessorCache
            public void disposeAccessor(RAReader rAReader) throws IOException {
                if (rAReader == null) {
                    $$$reportNull$$$0(0);
                }
                rAReader.dispose();
            }
        };
    }

    PersistentHashMapValueStorage(Path path, CreationTimeOptions creationTimeOptions) throws IOException {
        if (path == null) {
            $$$reportNull$$$0(1);
        }
        if (creationTimeOptions == null) {
            $$$reportNull$$$0(2);
        }
        this.myPath = path;
        this.myOptions = creationTimeOptions;
        if (creationTimeOptions.useCompression()) {
            MyCompressedAppendableFile myCompressedAppendableFile = new MyCompressedAppendableFile();
            this.myCompressedAppendableFile = myCompressedAppendableFile;
            this.mySize = myCompressedAppendableFile.length();
        } else {
            this.myCompressedAppendableFile = null;
            this.mySize = Files.exists(path, new LinkOption[0]) ? Files.size(path) : 0L;
        }
    }

    public long appendBytes(ByteArraySequence byteArraySequence, long j) throws IOException {
        return appendBytes(byteArraySequence.getInternalBuffer(), byteArraySequence.getOffset(), byteArraySequence.getLength(), j);
    }

    public long appendBytes(byte[] bArr, int i, int i2, long j) throws IOException {
        if (this.mySize == 0) {
            byte[] bytes = "Header Record For PersistentHashMapValueStorage".getBytes(StandardCharsets.UTF_8);
            doAppendBytes(bytes, 0, bytes.length, 0L);
            FileAccessorCache.Handle<OutputStream> ifCached = ourAppendersCache.getIfCached(this.myPath);
            if (ifCached != null) {
                try {
                    try {
                        IOUtil.syncStream(ifCached.get());
                        ifCached.release();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } catch (Throwable th) {
                    ifCached.release();
                    throw th;
                }
            }
            long size = Files.exists(this.myPath, new LinkOption[0]) ? Files.size(this.myPath) : 0L;
            if (size > this.mySize) {
                Logger.getInstance(getClass().getName()).info("Avoided PSHM corruption due to write failure:" + this.myPath);
                this.mySize = size;
            }
        }
        return doAppendBytes(bArr, i, i2, j);
    }

    void checkAppendsAllowed(int i) {
        if (i != 0 && this.myOptions.myHasNoChunks) {
            throw new AssertionError();
        }
    }

    private long doAppendBytes(byte[] bArr, int i, int i2, long j) throws IOException {
        DataOutputStream dataOutputStream;
        if (allowedToCompactChunks()) {
            if (j != 0 && this.myOptions.myHasNoChunks) {
                throw new AssertionError();
            }
            long j2 = this.mySize;
            if (this.myCompressedAppendableFile != null) {
                dataOutputStream = new DataOutputStream(new OutputStream() { // from class: org.jetbrains.kotlin.com.intellij.util.io.PersistentHashMapValueStorage.4
                    private static /* synthetic */ void $$$reportNull$$$0(int i3) {
                        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "org/jetbrains/kotlin/com/intellij/util/io/PersistentHashMapValueStorage$4", "write"));
                    }

                    @Override // java.io.OutputStream
                    public void write(byte[] bArr2, int i3, int i4) throws IOException {
                        if (bArr2 == null) {
                            $$$reportNull$$$0(0);
                        }
                        PersistentHashMapValueStorage.this.myCompressedAppendableFile.append(bArr2, i3, i4);
                    }

                    @Override // java.io.OutputStream
                    public void write(int i3) throws IOException {
                        write(new byte[]{(byte) (i3 & 255)});
                    }
                });
            } else {
                dataOutputStream = toDataOutputStream(ourAppendersCache.get(this.myPath));
            }
            DataOutputStream dataOutputStream2 = dataOutputStream;
            try {
                saveHeader(i2, j, j2, dataOutputStream2);
                dataOutputStream2.write(bArr, i, i2);
                this.mySize += (long) dataOutputStream2.resetWrittenBytesCount();
                return j2;
            } finally {
                dataOutputStream2.close();
            }
        }
        throw new AssertionError();
    }

    private void saveHeader(int i, long j, long j2, DataOutputStream dataOutputStream) throws IOException {
        if (dataOutputStream == null) {
            $$$reportNull$$$0(3);
        }
        DataInputOutputUtil.writeINT(dataOutputStream, i);
        if (this.myOptions.myHasNoChunks) {
            return;
        }
        if (j2 < j) {
            throw new IOException("writePrevChunkAddress:" + j2 + "," + j + "," + this.myPath);
        }
        long j3 = j2 - j;
        if (j == 0) {
            j3 = 0;
        }
        DataInputOutputUtil.writeLONG(dataOutputStream, j3);
    }

    private long compactValuesWithoutChunks(List<PersistentMapImpl.CompactionRecordInfo> list, PersistentHashMapValueStorage persistentHashMapValueStorage) throws IOException {
        PersistentMapImpl.CompactionRecordInfo compactionRecordInfo;
        if (list == null) {
            $$$reportNull$$$0(4);
        }
        if (persistentHashMapValueStorage == null) {
            $$$reportNull$$$0(5);
        }
        list.sort(new Comparator() { // from class: org.jetbrains.kotlin.com.intellij.util.io.PersistentHashMapValueStorage$$ExternalSyntheticLambda1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return Comparing.compare(((PersistentMapImpl.CompactionRecordInfo) obj).valueAddress, ((PersistentMapImpl.CompactionRecordInfo) obj2).valueAddress);
            }
        });
        int i = 262144;
        byte[] bArr = new byte[262144];
        int i2 = 4096;
        long j = -1;
        int i3 = -1;
        byte[] bArr2 = new byte[4096];
        long j2 = -1;
        int i4 = 0;
        int i5 = 0;
        for (PersistentMapImpl.CompactionRecordInfo compactionRecordInfo2 : list) {
            byte[] bArr3 = bArr2;
            int i6 = (int) (compactionRecordInfo2.valueAddress - j2);
            if (i6 + 5 > i || j2 == j) {
                long j3 = compactionRecordInfo2.valueAddress;
                long j4 = j3 != j ? this.mySize - j3 : this.mySize;
                int i7 = j4 < 262144 ? (int) j4 : i;
                compactionRecordInfo = compactionRecordInfo2;
                this.myCompactionModeReader.get(j3, bArr, 0, i7);
                i6 = (int) (compactionRecordInfo.valueAddress - j3);
                j2 = j3;
                i3 = i7;
            } else {
                compactionRecordInfo = compactionRecordInfo2;
            }
            DataInputStream dataInputStream = toDataInputStream(bArr, i6, i);
            int iAvailable = dataInputStream.available();
            int chunkSize = readChunkSize(dataInputStream);
            readPrevChunkAddress(compactionRecordInfo.valueAddress, dataInputStream);
            int iAvailable2 = (iAvailable - dataInputStream.available()) + i6;
            byte[] bArr4 = chunkSize >= bArr3.length ? new byte[((chunkSize / 4096) + 1) * i2] : bArr3;
            int iMin = Math.min(chunkSize, 262144 - iAvailable2);
            System.arraycopy(bArr, iAvailable2, bArr4, 0, iMin);
            int i8 = iMin;
            int i9 = i3;
            long j5 = j2;
            while (i8 != chunkSize) {
                j5 += (long) i9;
                long j6 = this.mySize - j5;
                int i10 = j6 < 262144 ? (int) j6 : 262144;
                int i11 = i8;
                this.myCompactionModeReader.get(j5, bArr, 0, i10);
                int iMin2 = Math.min(chunkSize - i11, 262144);
                System.arraycopy(bArr, 0, bArr4, i11, iMin2);
                i8 = iMin2 + i11;
                i9 = i10;
            }
            compactionRecordInfo.newValueAddress = persistentHashMapValueStorage.appendBytes(bArr4, 0, chunkSize, 0L);
            i5++;
            i4++;
            i3 = i9;
            j2 = j5;
            i2 = 4096;
            j = -1;
            bArr2 = bArr4;
            i = 262144;
        }
        return ((long) i5) | (((long) i4) << 32);
    }

    /* JADX WARN: Code duplicated, block: B:57:0x0160  */
    /* JADX WARN: Code duplicated, block: B:58:0x016f  */
    /* JADX WARN: Code duplicated, block: B:61:0x0176  */
    /* JADX WARN: Code duplicated, block: B:62:0x0195  */
    long compactValues(List<PersistentMapImpl.CompactionRecordInfo> list, PersistentHashMapValueStorage persistentHashMapValueStorage) throws IOException {
        byte[] bArr;
        int i;
        PriorityQueue priorityQueue;
        byte[] bArr2;
        byte[] bArr3;
        byte[] bArr4;
        int i2;
        long j;
        byte[] bArr5;
        long j2;
        byte[] bArr6;
        byte[] bArr7;
        int i3;
        int i4;
        long j3;
        PriorityQueue priorityQueue2;
        byte[] bArr8;
        if (list == null) {
            $$$reportNull$$$0(6);
        }
        if (persistentHashMapValueStorage == null) {
            $$$reportNull$$$0(7);
        }
        if (this.myOptions.myHasNoChunks) {
            return compactValuesWithoutChunks(list, persistentHashMapValueStorage);
        }
        PriorityQueue priorityQueue3 = new PriorityQueue(list.size(), new Comparator() { // from class: org.jetbrains.kotlin.com.intellij.util.io.PersistentHashMapValueStorage$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return Comparing.compare(((PersistentMapImpl.CompactionRecordInfo) obj2).valueAddress, ((PersistentMapImpl.CompactionRecordInfo) obj).valueAddress);
            }
        });
        priorityQueue3.addAll(list);
        int i5 = 262159;
        byte[] bArr9 = new byte[262159];
        int i6 = 0;
        byte[] bArr10 = new byte[0];
        long j4 = this.mySize;
        long j5 = j4;
        int i7 = (int) (this.mySize - ((this.mySize / 262144) * 262144));
        int i8 = 0;
        int i9 = 0;
        long j6 = 0;
        long length = 0;
        byte[] bArr11 = null;
        while (j5 != 0) {
            long j7 = j5 - ((long) i7);
            long j8 = j7;
            int iSaveAccumulatedDataOnDiskPreservingWriteOrder = i8;
            int i10 = i9;
            this.myCompactionModeReader.get(j7, bArr9, 0, i7);
            byte[] bArr12 = bArr11;
            while (true) {
                if (!priorityQueue3.isEmpty()) {
                    PersistentMapImpl.CompactionRecordInfo compactionRecordInfo = (PersistentMapImpl.CompactionRecordInfo) priorityQueue3.peek();
                    PriorityQueue priorityQueue4 = priorityQueue3;
                    long j9 = j8;
                    if (compactionRecordInfo.valueAddress < j9) {
                        bArr = bArr9;
                        i = i6;
                        j5 = j9;
                        priorityQueue = priorityQueue4;
                        bArr2 = bArr12;
                        if (bArr2 != null) {
                            j6 = j5;
                            break;
                        }
                        int i11 = (int) (j4 - j5);
                        byte[] bArr13 = new byte[i11];
                        System.arraycopy(bArr, i, bArr13, i, i11);
                        bArr2 = bArr13;
                        break;
                    }
                    if (compactionRecordInfo.valueAddress >= j5) {
                        throw new IOException("Value storage is corrupted: value file size:" + this.mySize + ", readStartOffset:" + j9 + ", record address:" + compactionRecordInfo.valueAddress + "; file: " + this.myPath);
                    }
                    int i12 = (int) (compactionRecordInfo.valueAddress - j9);
                    DataInputStream dataInputStream = toDataInputStream(bArr9, i12, i5);
                    if (bArr12 == null || 262144 - i12 >= 15) {
                        bArr3 = bArr12;
                    } else if (j6 != 0) {
                        bArr3 = bArr12;
                        this.myCompactionModeReader.get(j6, bArr9, i7, 15);
                    } else {
                        bArr3 = bArr12;
                        System.arraycopy(bArr3, 0, bArr9, i7, Math.min(bArr3.length, 15));
                    }
                    int iAvailable = dataInputStream.available();
                    int chunkSize = readChunkSize(dataInputStream);
                    long prevChunkAddress = readPrevChunkAddress(compactionRecordInfo.valueAddress, dataInputStream);
                    int iAvailable2 = iAvailable - dataInputStream.available();
                    if (compactionRecordInfo.value != null) {
                        int length2 = compactionRecordInfo.value.length + chunkSize;
                        if (prevChunkAddress == 0) {
                            if (length2 >= bArr10.length) {
                                bArr10 = new byte[length2];
                            }
                            bArr8 = bArr10;
                        } else {
                            byte[] bArr14 = new byte[length2];
                            length += (long) length2;
                            bArr8 = bArr10;
                            bArr10 = bArr14;
                        }
                        bArr4 = bArr8;
                        System.arraycopy(compactionRecordInfo.value, 0, bArr10, chunkSize, compactionRecordInfo.value.length);
                    } else if (prevChunkAddress == 0) {
                        if (chunkSize >= bArr10.length) {
                            bArr10 = new byte[chunkSize];
                        }
                        bArr4 = bArr10;
                    } else {
                        length += (long) chunkSize;
                        bArr4 = bArr10;
                        bArr10 = new byte[chunkSize];
                    }
                    long j10 = prevChunkAddress;
                    long j11 = iAvailable2;
                    int iClamp = MathUtil.clamp((int) (((compactionRecordInfo.valueAddress + j11) + ((long) chunkSize)) - j5), 0, chunkSize);
                    if (iClamp > 0) {
                        if (j6 != 0) {
                            this.myCompactionModeReader.get(j6, bArr10, chunkSize - iClamp, iClamp);
                        } else {
                            chunkSize = chunkSize;
                            j10 = j10;
                            i2 = 0;
                            System.arraycopy(bArr3, Math.max((int) ((compactionRecordInfo.valueAddress + j11) - j5), 0), bArr10, chunkSize - iClamp, iClamp);
                        }
                        j = compactionRecordInfo.valueAddress;
                        checkPreconditions(bArr10, chunkSize);
                        System.arraycopy(bArr9, i12 + iAvailable2, bArr10, i2, chunkSize - iClamp);
                        i10++;
                        priorityQueue4.remove(compactionRecordInfo);
                        if (compactionRecordInfo.value != null) {
                            int length3 = compactionRecordInfo.value.length + chunkSize;
                            length -= (long) compactionRecordInfo.value.length;
                            bArr5 = null;
                            compactionRecordInfo.value = null;
                            chunkSize = length3;
                        } else {
                            bArr5 = null;
                        }
                        j2 = j10;
                        if (j2 == 0) {
                            i3 = i7;
                            bArr6 = bArr5;
                            compactionRecordInfo.newValueAddress = persistentHashMapValueStorage.appendBytes(bArr10, 0, chunkSize, compactionRecordInfo.newValueAddress);
                            iSaveAccumulatedDataOnDiskPreservingWriteOrder++;
                            i4 = i2;
                            j3 = j9;
                            priorityQueue2 = priorityQueue4;
                        } else {
                            bArr6 = bArr5;
                            bArr7 = bArr10;
                            i3 = i7;
                            i4 = i2;
                            if ((length <= SOFT_MAX_RETAINED_LIMIT && bArr7.length > 1024) || length > 104857600) {
                                j3 = j9;
                                j = j;
                                iSaveAccumulatedDataOnDiskPreservingWriteOrder += saveAccumulatedDataOnDiskPreservingWriteOrder(persistentHashMapValueStorage, compactionRecordInfo, j2, bArr7, chunkSize);
                                length -= (long) bArr7.length;
                                priorityQueue3 = priorityQueue4;
                                bArr9 = bArr9;
                            } else {
                                j3 = j9;
                                priorityQueue2 = priorityQueue4;
                                compactionRecordInfo.value = bArr7;
                                compactionRecordInfo.valueAddress = j2;
                                priorityQueue2.add(compactionRecordInfo);
                            }
                            j8 = j3;
                            bArr12 = bArr6;
                            bArr10 = bArr4;
                            j4 = j;
                            i5 = 262159;
                            i6 = i4;
                            i7 = i3;
                            j6 = 0;
                        }
                        priorityQueue3 = priorityQueue2;
                        bArr9 = bArr9;
                        j8 = j3;
                        bArr12 = bArr6;
                        bArr10 = bArr4;
                        j4 = j;
                        i5 = 262159;
                        i6 = i4;
                        i7 = i3;
                        j6 = 0;
                    }
                    i2 = 0;
                    j = compactionRecordInfo.valueAddress;
                    checkPreconditions(bArr10, chunkSize);
                    System.arraycopy(bArr9, i12 + iAvailable2, bArr10, i2, chunkSize - iClamp);
                    i10++;
                    priorityQueue4.remove(compactionRecordInfo);
                    if (compactionRecordInfo.value != null) {
                        int length4 = compactionRecordInfo.value.length + chunkSize;
                        length -= (long) compactionRecordInfo.value.length;
                        bArr5 = null;
                        compactionRecordInfo.value = null;
                        chunkSize = length4;
                    } else {
                        bArr5 = null;
                    }
                    j2 = j10;
                    if (j2 == 0) {
                        i3 = i7;
                        bArr6 = bArr5;
                        compactionRecordInfo.newValueAddress = persistentHashMapValueStorage.appendBytes(bArr10, 0, chunkSize, compactionRecordInfo.newValueAddress);
                        iSaveAccumulatedDataOnDiskPreservingWriteOrder++;
                        i4 = i2;
                        j3 = j9;
                        priorityQueue2 = priorityQueue4;
                    } else {
                        bArr6 = bArr5;
                        bArr7 = bArr10;
                        i3 = i7;
                        i4 = i2;
                        if (length <= SOFT_MAX_RETAINED_LIMIT) {
                            j3 = j9;
                            priorityQueue2 = priorityQueue4;
                            compactionRecordInfo.value = bArr7;
                            compactionRecordInfo.valueAddress = j2;
                            priorityQueue2.add(compactionRecordInfo);
                        } else {
                            j3 = j9;
                            priorityQueue2 = priorityQueue4;
                            compactionRecordInfo.value = bArr7;
                            compactionRecordInfo.valueAddress = j2;
                            priorityQueue2.add(compactionRecordInfo);
                        }
                        j3 = j9;
                        j = j;
                        iSaveAccumulatedDataOnDiskPreservingWriteOrder += saveAccumulatedDataOnDiskPreservingWriteOrder(persistentHashMapValueStorage, compactionRecordInfo, j2, bArr7, chunkSize);
                        length -= (long) bArr7.length;
                        priorityQueue3 = priorityQueue4;
                        bArr9 = bArr9;
                        j8 = j3;
                        bArr12 = bArr6;
                        bArr10 = bArr4;
                        j4 = j;
                        i5 = 262159;
                        i6 = i4;
                        i7 = i3;
                        j6 = 0;
                    }
                    priorityQueue3 = priorityQueue2;
                    bArr9 = bArr9;
                    j8 = j3;
                    bArr12 = bArr6;
                    bArr10 = bArr4;
                    j4 = j;
                    i5 = 262159;
                    i6 = i4;
                    i7 = i3;
                    j6 = 0;
                } else {
                    bArr = bArr9;
                    i = i6;
                    priorityQueue = priorityQueue3;
                    j5 = j8;
                    bArr2 = bArr12;
                    break;
                }
            }
            priorityQueue3 = priorityQueue;
            bArr9 = bArr;
            bArr11 = bArr2;
            i8 = iSaveAccumulatedDataOnDiskPreservingWriteOrder;
            i9 = i10;
            i5 = 262159;
            i6 = i;
            i7 = 262144;
        }
        return ((long) i9) | (((long) i8) << 32);
    }

    private int saveAccumulatedDataOnDiskPreservingWriteOrder(PersistentHashMapValueStorage persistentHashMapValueStorage, PersistentMapImpl.CompactionRecordInfo compactionRecordInfo, long j, byte[] bArr, int i) throws IOException {
        ReadResult bytes = readBytes(j);
        compactionRecordInfo.newValueAddress = persistentHashMapValueStorage.appendBytes(bytes.buffer, 0, bytes.buffer.length, compactionRecordInfo.newValueAddress);
        compactionRecordInfo.newValueAddress = persistentHashMapValueStorage.appendBytes(bArr, 0, i, compactionRecordInfo.newValueAddress);
        compactionRecordInfo.value = null;
        compactionRecordInfo.valueAddress = 0L;
        return 2;
    }

    static class ReadResult {
        final byte[] buffer;
        final int chunksCount;

        ReadResult(byte[] bArr, int i) {
            this.buffer = bArr;
            this.chunksCount = i;
        }
    }

    public ReadResult readBytes(long j) throws IOException {
        FileAccessorCache.Handle<RAReader> handle;
        byte[] bArr;
        forceAppender(this.myPath);
        checkCancellation();
        long jNanoTime = 0;
        long jNanoTime2 = ourDumpChunkRemovalTime ? System.nanoTime() : 0L;
        RAReader readerOverCompressedFile = this.myCompactionModeReader;
        if (this.myCompressedAppendableFile != null) {
            readerOverCompressedFile = new ReaderOverCompressedFile(this.myCompressedAppendableFile);
        }
        byte[] bArr2 = null;
        if (readerOverCompressedFile == null) {
            FileAccessorCache.Handle<RAReader> handle2 = ourReadersCache.get(this.myPath);
            handle = handle2;
            readerOverCompressedFile = handle2.get();
        } else {
            handle = null;
        }
        int i = 0;
        long j2 = j;
        int i2 = 0;
        while (j2 != jNanoTime) {
            if (j2 >= jNanoTime) {
                try {
                    try {
                        if (j2 <= this.mySize) {
                            byte[] buffer = myBuffer.getBuffer(1024);
                            int iMin = (int) Math.min(1024L, this.mySize - j2);
                            long j3 = j2;
                            readerOverCompressedFile.get(j2, buffer, 0, iMin);
                            DataInputStream dataInputStream = toDataInputStream(buffer, i, iMin);
                            int chunkSize = readChunkSize(dataInputStream);
                            long prevChunkAddress = readPrevChunkAddress(j3, dataInputStream);
                            int iAvailable = iMin - dataInputStream.available();
                            int length = (bArr2 != null ? bArr2.length : i) + chunkSize;
                            byte[] bArr3 = new byte[length];
                            if (bArr2 != null) {
                                System.arraycopy(bArr2, i, bArr3, length - bArr2.length, bArr2.length);
                            }
                            checkPreconditions(bArr3, chunkSize);
                            if (chunkSize < 1024 - iAvailable) {
                                System.arraycopy(buffer, iAvailable, bArr3, i, chunkSize);
                                bArr = bArr3;
                            } else {
                                bArr = bArr3;
                                readerOverCompressedFile.get(((long) iAvailable) + j3, bArr, 0, chunkSize);
                            }
                            if (prevChunkAddress >= j3) {
                                throw new CorruptedException(this.myPath);
                            }
                            i2++;
                            jNanoTime = 0;
                            if (prevChunkAddress != 0) {
                                checkCancellation();
                            }
                            if (length > this.mySize && this.myCompressedAppendableFile == null) {
                                throw new CorruptedException(this.myPath);
                            }
                            j2 = prevChunkAddress;
                            bArr2 = bArr;
                            i = 0;
                        }
                    } catch (OutOfMemoryError unused) {
                        throw new CorruptedException(this.myPath);
                    }
                } catch (Throwable th) {
                    if (handle != null) {
                        handle.release();
                    }
                    throw th;
                }
            }
            throw new CorruptedException(this.myPath);
        }
        if (handle != null) {
            handle.release();
        }
        if (i2 > 1) {
            checkCancellation();
            long j4 = this.myChunksReadingTime;
            if (ourDumpChunkRemovalTime) {
                jNanoTime = System.nanoTime();
            }
            this.myChunksReadingTime = j4 + (jNanoTime - jNanoTime2);
            this.myChunks += i2;
            this.myChunksOriginalBytes += (long) bArr2.length;
        }
        return new ReadResult(bArr2, i2);
    }

    private boolean allowedToCompactChunks() {
        return (this.myCompactionMode || this.myOptions.myReadOnly) ? false : true;
    }

    boolean performChunksCompaction(int i) {
        return i > 1 && allowedToCompactChunks();
    }

    long compactChunks(AppendablePersistentMap.ValueDataAppender valueDataAppender, ReadResult readResult) throws IOException {
        long jAppendBytes;
        if (valueDataAppender == null) {
            $$$reportNull$$$0(8);
        }
        if (readResult == null) {
            $$$reportNull$$$0(9);
        }
        checkCancellation();
        boolean z = ourDumpChunkRemovalTime;
        long jNanoTime = z ? System.nanoTime() : 0L;
        if (this.myOptions.myCompactChunksWithValueDeserialization) {
            BufferExposingByteArrayOutputStream bufferExposingByteArrayOutputStream = new BufferExposingByteArrayOutputStream(readResult.buffer.length);
            valueDataAppender.append(new DataOutputStream(bufferExposingByteArrayOutputStream));
            jAppendBytes = appendBytes(bufferExposingByteArrayOutputStream.toByteArraySequence(), 0L);
            this.myChunksBytesAfterRemoval += (long) bufferExposingByteArrayOutputStream.size();
        } else {
            long jAppendBytes2 = appendBytes(ByteArraySequence.create(readResult.buffer), 0L);
            this.myChunksBytesAfterRemoval += (long) readResult.buffer.length;
            jAppendBytes = jAppendBytes2;
        }
        if (z) {
            this.myChunksRemovalTime += System.nanoTime() - jNanoTime;
            int i = this.myChunks;
            if (i - this.myLastReportedChunksCount > 1000) {
                this.myLastReportedChunksCount = i;
                System.out.println(this.myChunks + " chunks were read " + (this.myChunksReadingTime / PackingOptions.SEGMENT_LIMIT) + "ms, bytes: " + this.myChunksOriginalBytes + (this.myChunksOriginalBytes != this.myChunksBytesAfterRemoval ? "->" + this.myChunksBytesAfterRemoval : "") + " compaction:" + (this.myChunksRemovalTime / PackingOptions.SEGMENT_LIMIT) + "ms in " + this.myPath);
            }
        }
        return jAppendBytes;
    }

    private void checkCancellation() {
        this.myOptions.myIOCancellationCallback.checkCancelled();
    }

    private static int readChunkSize(DataInputStream dataInputStream) throws IOException {
        if (dataInputStream == null) {
            $$$reportNull$$$0(10);
        }
        int i = DataInputOutputUtil.readINT(dataInputStream);
        if (i >= 0) {
            return i;
        }
        throw new IOException("Value storage corrupted: negative chunk size: " + i);
    }

    private long readPrevChunkAddress(long j, DataInputStream dataInputStream) throws IOException {
        if (dataInputStream == null) {
            $$$reportNull$$$0(11);
        }
        if (this.myOptions.myHasNoChunks) {
            return 0L;
        }
        long j2 = DataInputOutputUtil.readLONG(dataInputStream);
        if (j2 >= j) {
            throw new IOException("readPrevChunkAddress:" + j + "," + j2 + "," + this.mySize + "," + this.myPath);
        }
        if (j2 != 0) {
            return j - j2;
        }
        return 0L;
    }

    public long getSize() {
        return this.mySize;
    }

    private static void checkPreconditions(byte[] bArr, int i) throws IOException {
        if (i < 0) {
            throw new IOException("Value storage corrupted: negative chunk size");
        }
        if (i > bArr.length) {
            throw new IOException("Value storage corrupted");
        }
    }

    public void force() {
        if (this.myOptions.myReadOnly) {
            return;
        }
        CompressedAppendableFile compressedAppendableFile = this.myCompressedAppendableFile;
        if (compressedAppendableFile != null) {
            compressedAppendableFile.force();
        }
        forceAppender(this.myPath);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Bottom block not found for handler: all -> 0x0015 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static void forceAppender(java.nio.file.Path r2) {
        /*
            org.jetbrains.kotlin.com.intellij.util.io.FileAccessorCache<java.nio.file.Path, java.io.OutputStream> r0 = org.jetbrains.kotlin.com.intellij.util.io.PersistentHashMapValueStorage.ourAppendersCache
            org.jetbrains.kotlin.com.intellij.util.io.FileAccessorCache$Handle r2 = r0.getIfCached(r2)
            if (r2 == 0) goto L22
            java.lang.Object r0 = r2.get()     // Catch: java.lang.Throwable -> L15 java.io.IOException -> L17
            java.io.OutputStream r0 = (java.io.OutputStream) r0     // Catch: java.lang.Throwable -> L15 java.io.IOException -> L17
            r0.flush()     // Catch: java.lang.Throwable -> L15 java.io.IOException -> L17
            r2.release()
            goto L22
        L15:
            r0 = move-exception
            goto L1e
        L17:
            r0 = move-exception
            java.lang.RuntimeException r1 = new java.lang.RuntimeException     // Catch: java.lang.Throwable -> L15
            r1.<init>(r0)     // Catch: java.lang.Throwable -> L15
            throw r1     // Catch: java.lang.Throwable -> L15
        L1e:
            r2.release()
            throw r0
        L22:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jetbrains.kotlin.com.intellij.util.io.PersistentHashMapValueStorage.forceAppender(java.nio.file.Path):void");
    }

    public void dispose() {
        try {
            CompressedAppendableFile compressedAppendableFile = this.myCompressedAppendableFile;
            if (compressedAppendableFile != null) {
                compressedAppendableFile.dispose();
            }
            ourReadersCache.remove(this.myPath);
            ourAppendersCache.remove(this.myPath);
            ourFileChannelCache.remove(this.myPath);
            RAReader rAReader = this.myCompactionModeReader;
            if (rAReader != null) {
                try {
                    rAReader.dispose();
                    this.myCompactionModeReader = null;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (Throwable th) {
            ourReadersCache.remove(this.myPath);
            ourAppendersCache.remove(this.myPath);
            ourFileChannelCache.remove(this.myPath);
            RAReader rAReader2 = this.myCompactionModeReader;
            if (rAReader2 != null) {
                try {
                    rAReader2.dispose();
                    this.myCompactionModeReader = null;
                } catch (IOException e2) {
                    throw new RuntimeException(e2);
                }
            }
            throw th;
        }
    }

    void switchToCompactionMode() throws IOException {
        ourReadersCache.remove(this.myPath);
        ourFileChannelCache.remove(this.myPath);
        if (this.myCompressedAppendableFile != null) {
            this.myCompactionModeReader = new ReaderOverCompressedFile(this.myCompressedAppendableFile);
        } else {
            this.myCompactionModeReader = new FileReader(this.myPath);
        }
        this.myCompactionMode = true;
    }

    private static DataInputStream toDataInputStream(byte[] bArr, int i, int i2) {
        if (bArr == null) {
            $$$reportNull$$$0(12);
        }
        return new DataInputStream(new UnsyncByteArrayInputStream(bArr, i, i2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static DataOutputStream toDataOutputStream(final FileAccessorCache.Handle<OutputStream> handle) {
        if (handle == null) {
            $$$reportNull$$$0(13);
        }
        return new DataOutputStream(handle.get()) { // from class: org.jetbrains.kotlin.com.intellij.util.io.PersistentHashMapValueStorage.5
            @Override // java.io.FilterOutputStream, java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
            public void close() throws IOException {
                super.close();
                handle.close();
            }
        };
    }

    public static PersistentHashMapValueStorage create(Path path, CreationTimeOptions creationTimeOptions) throws IOException {
        if (path == null) {
            $$$reportNull$$$0(14);
        }
        if (creationTimeOptions == null) {
            $$$reportNull$$$0(15);
        }
        return new PersistentHashMapValueStorage(path, creationTimeOptions);
    }

    private static class ReaderOverCompressedFile implements RAReader {
        private final CompressedAppendableFile myCompressedAppendableFile;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "compressedAppendableFile", "org/jetbrains/kotlin/com/intellij/util/io/PersistentHashMapValueStorage$ReaderOverCompressedFile", "<init>"));
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.io.PersistentHashMapValueStorage.RAReader
        public void dispose() {
        }

        ReaderOverCompressedFile(CompressedAppendableFile compressedAppendableFile) {
            if (compressedAppendableFile == null) {
                $$$reportNull$$$0(0);
            }
            this.myCompressedAppendableFile = compressedAppendableFile;
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.io.PersistentHashMapValueStorage.RAReader
        public void get(long j, byte[] bArr, int i, int i2) throws IOException {
            DataInputStream stream = this.myCompressedAppendableFile.getStream(j);
            try {
                stream.readFully(bArr, i, i2);
                if (stream != null) {
                    stream.close();
                }
            } catch (Throwable th) {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
    }

    private static final class ReaderOverFileChannelCache implements RAReader {
        private final Path myPath;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "path", "org/jetbrains/kotlin/com/intellij/util/io/PersistentHashMapValueStorage$ReaderOverFileChannelCache", "<init>"));
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.io.PersistentHashMapValueStorage.RAReader
        public void dispose() {
        }

        private ReaderOverFileChannelCache(Path path) {
            if (path == null) {
                $$$reportNull$$$0(0);
            }
            this.myPath = path;
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.io.PersistentHashMapValueStorage.RAReader
        public void get(long j, byte[] bArr, int i, int i2) throws IOException {
            FileAccessorCache.Handle handle = PersistentHashMapValueStorage.ourFileChannelCache.get(this.myPath);
            try {
                ((FileChannelWithSizeTracking) handle.get()).read(j, bArr, i, i2);
                if (handle != null) {
                    handle.close();
                }
            } catch (Throwable th) {
                if (handle != null) {
                    try {
                        handle.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
    }

    private static final class FileReader implements RAReader {
        private final UnInterruptibleFileChannel myFile;

        private FileReader(Path path) throws IOException {
            this.myFile = new UnInterruptibleFileChannel(path, StandardOpenOption.READ);
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.io.PersistentHashMapValueStorage.RAReader
        public void get(long j, byte[] bArr, int i, int i2) throws IOException {
            this.myFile.read(ByteBuffer.wrap(bArr, i, i2), j);
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.io.PersistentHashMapValueStorage.RAReader
        public void dispose() throws IOException {
            this.myFile.close();
        }
    }

    private static class OutputStreamOverRandomAccessFileCache extends OutputStream {
        private final Path myPath;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "org/jetbrains/kotlin/com/intellij/util/io/PersistentHashMapValueStorage$OutputStreamOverRandomAccessFileCache", "write"));
        }

        OutputStreamOverRandomAccessFileCache(Path path) {
            this.myPath = path;
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr, int i, int i2) throws IOException {
            if (bArr == null) {
                $$$reportNull$$$0(0);
            }
            FileAccessorCache.Handle handle = PersistentHashMapValueStorage.ourFileChannelCache.get(this.myPath);
            FileChannelWithSizeTracking fileChannelWithSizeTracking = (FileChannelWithSizeTracking) handle.get();
            try {
                fileChannelWithSizeTracking.write(fileChannelWithSizeTracking.length(), bArr, i, i2);
            } finally {
                handle.release();
            }
        }

        @Override // java.io.OutputStream
        public void write(int i) throws IOException {
            write(new byte[]{(byte) (i & 255)});
        }
    }

    private class MyCompressedAppendableFile extends CompressedAppendableFile {
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[2];
            objArr[0] = "org/jetbrains/kotlin/com/intellij/util/io/PersistentHashMapValueStorage$MyCompressedAppendableFile";
            if (i == 1) {
                objArr[1] = "getChunkAppendStream";
            } else if (i == 2) {
                objArr[1] = "getChunkLengthAppendStream";
            } else if (i != 3) {
                objArr[1] = "getChunkInputStream";
            } else {
                objArr[1] = "getChunksFile";
            }
            throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", objArr));
        }

        MyCompressedAppendableFile() throws IOException {
            super(PersistentHashMapValueStorage.this.myPath);
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.io.CompressedAppendableFile
        protected InputStream getChunkInputStream(long j, int i) throws IOException {
            PersistentHashMapValueStorage.forceAppender(PersistentHashMapValueStorage.this.myPath);
            FileAccessorCache.Handle handle = PersistentHashMapValueStorage.ourReadersCache.get(PersistentHashMapValueStorage.this.myPath);
            try {
                byte[] bArr = new byte[i];
                ((RAReader) handle.get()).get(j, bArr, 0, i);
                return new ByteArrayInputStream(bArr);
            } finally {
                handle.release();
            }
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.io.CompressedAppendableFile
        protected DataOutputStream getChunkAppendStream() {
            DataOutputStream dataOutputStream = PersistentHashMapValueStorage.toDataOutputStream(PersistentHashMapValueStorage.ourAppendersCache.get(PersistentHashMapValueStorage.this.myPath));
            if (dataOutputStream == null) {
                $$$reportNull$$$0(1);
            }
            return dataOutputStream;
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.io.CompressedAppendableFile
        protected DataOutputStream getChunkLengthAppendStream() {
            DataOutputStream dataOutputStream = PersistentHashMapValueStorage.toDataOutputStream(PersistentHashMapValueStorage.ourAppendersCache.get(getChunkLengthFile()));
            if (dataOutputStream == null) {
                $$$reportNull$$$0(2);
            }
            return dataOutputStream;
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.io.CompressedAppendableFile
        protected Path getChunksFile() {
            Path path = PersistentHashMapValueStorage.this.myPath;
            if (path == null) {
                $$$reportNull$$$0(3);
            }
            return path;
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.io.CompressedAppendableFile
        public synchronized void force() {
            super.force();
            PersistentHashMapValueStorage.forceAppender(getChunkLengthFile());
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.io.CompressedAppendableFile
        public synchronized void dispose() {
            super.dispose();
            PersistentHashMapValueStorage.ourAppendersCache.remove(getChunkLengthFile());
            PersistentHashMapValueStorage.ourFileChannelCache.remove(getChunkLengthFile());
        }
    }

    public boolean isReadOnly() {
        return this.myOptions.myReadOnly;
    }
}
