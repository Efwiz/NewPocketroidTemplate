package org.jetbrains.kotlin.com.intellij.util.io;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.File;
import java.io.Flushable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.eclipse.jgit.storage.pack.PackConfig;
import org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.LowMemoryWatcher;
import org.jetbrains.kotlin.com.intellij.openapi.util.ThreadLocalCachedValue;
import org.jetbrains.kotlin.com.intellij.openapi.util.io.BufferExposingByteArrayOutputStream;
import org.jetbrains.kotlin.com.intellij.openapi.util.io.FileUtil;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtilRt;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.Processor;
import org.jetbrains.kotlin.com.intellij.util.SystemProperties;
import org.jetbrains.kotlin.com.intellij.util.containers.LimitedPool;
import org.jetbrains.kotlin.com.intellij.util.containers.SLRUCache;

/* JADX INFO: loaded from: classes3.dex */
public class PersistentMapImpl<Key, Value> implements PersistentMapBase<Key, Value> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final String DATA_FILE_EXTENSION;
    private static final int INITIAL_INDEX_SIZE;
    private static final Logger LOG = Logger.getInstance((Class<?>) PersistentMapImpl.class);
    private static final boolean myDoTrace = SystemProperties.getBooleanProperty("idea.trace.persistent.map", false);
    private static final ThreadLocalCachedValue<AppendStream> ourFlyweightAppenderStream;
    private int largeKeys;
    private final SLRUCache<Key, BufferExposingByteArrayOutputStream> myAppendCache;
    private final LowMemoryWatcher myAppendCacheFlusher;
    private final boolean myCanReEnumerate;
    private final boolean myCompactOnClose;
    private final boolean myDirectlyStoreLongFileOffsetMode;
    private final PersistentEnumeratorBase<Key> myEnumerator;
    private boolean myIntAddressForNewRecord;
    private final boolean myIntMapping;
    private final boolean myIsReadOnly;
    private final KeyDescriptor<Key> myKeyDescriptor;
    private int myLargeIndexWatermarkId;
    private long myLiveAndGarbageKeysCounter;
    private final ReentrantReadWriteLock myLock;
    private final int myParentValueRefOffset;
    private int myReadCompactionGarbageSize;
    private final Path myStorageFile;
    private final LimitedPool<BufferExposingByteArrayOutputStream> myStreamPool;
    private final DataExternalizer<Value> myValueExternalizer;
    private PersistentHashMapValueStorage myValueStorage;
    private final PersistentMapWal<Key, Value> myWal;
    private int requests;
    private int smallKeys;
    private int transformedKeys;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 6 || i == 8 || i == 16 || i == 3 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 6 || i == 8 || i == 16 || i == 3 || i == 4) ? 2 : 3];
        switch (i) {
            case 1:
            case 3:
            case 4:
            case 6:
            case 8:
            case 16:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/io/PersistentMapImpl";
                break;
            case 2:
                objArr[0] = "options";
                break;
            case 5:
            case 7:
                objArr[0] = "file";
                break;
            case 9:
            case 10:
                objArr[0] = "appender";
                break;
            case 11:
            case 12:
                objArr[0] = "processor";
                break;
            case 13:
                objArr[0] = "fileFromDirectory";
                break;
            case 14:
                objArr[0] = "newStorage";
                break;
            case 15:
                objArr[0] = "map";
                break;
            default:
                objArr[0] = "builder";
                break;
        }
        if (i == 1) {
            objArr[1] = "getValuesExternalizer";
        } else if (i == 6) {
            objArr[1] = "checkDataFiles";
        } else if (i == 8) {
            objArr[1] = "getDataFile";
        } else if (i == 16) {
            objArr[1] = "unwrap";
        } else if (i == 3) {
            objArr[1] = "getWriteLock";
        } else if (i != 4) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/io/PersistentMapImpl";
        } else {
            objArr[1] = "getReadLock";
        }
        switch (i) {
            case 1:
            case 3:
            case 4:
            case 6:
            case 8:
            case 16:
                break;
            case 2:
                objArr[2] = "modifyVersionDependingOnOptions";
                break;
            case 5:
                objArr[2] = "checkDataFiles";
                break;
            case 7:
                objArr[2] = "getDataFile";
                break;
            case 9:
                objArr[2] = "appendData";
                break;
            case 10:
                objArr[2] = "doAppendData";
                break;
            case 11:
                objArr[2] = "processKeys";
                break;
            case 12:
                objArr[2] = "processExistingKeys";
                break;
            case 13:
                objArr[2] = "getFilesInDirectoryWithNameStartingWith";
                break;
            case 14:
                objArr[2] = "newCompact";
                break;
            case 15:
                objArr[2] = "unwrap";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 6 && i != 8 && i != 16 && i != 3 && i != 4) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    static /* synthetic */ long access$714(PersistentMapImpl persistentMapImpl, long j) {
        long j2 = persistentMapImpl.myLiveAndGarbageKeysCounter + j;
        persistentMapImpl.myLiveAndGarbageKeysCounter = j2;
        return j2;
    }

    static {
        String property = System.getProperty("idea.initialIndexSize");
        INITIAL_INDEX_SIZE = property == null ? 4096 : Integer.valueOf(property).intValue();
        DATA_FILE_EXTENSION = PersistentHashMap.DATA_FILE_EXTENSION;
        ourFlyweightAppenderStream = new ThreadLocalCachedValue<AppendStream>() { // from class: org.jetbrains.kotlin.com.intellij.util.io.PersistentMapImpl.3
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ThreadLocalCachedValue
            public AppendStream create() {
                return new AppendStream();
            }
        };
    }

    private static final class AppendStream extends DataOutputStream {
        private AppendStream() {
            super(null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setOut(BufferExposingByteArrayOutputStream bufferExposingByteArrayOutputStream) {
            this.out = bufferExposingByteArrayOutputStream;
        }
    }

    private boolean canUseIntAddressForNewRecord(long j) {
        return this.myCanReEnumerate && j + 1 < 2147483647L;
    }

    public PersistentMapImpl(PersistentMapBuilder<Key, Value> persistentMapBuilder) throws IOException {
        boolean z = false;
        if (persistentMapBuilder == null) {
            $$$reportNull$$$0(0);
        }
        this.myLock = new ReentrantReadWriteLock();
        this.myStreamPool = new LimitedPool<>(10, new LimitedPool.ObjectFactory<BufferExposingByteArrayOutputStream>() { // from class: org.jetbrains.kotlin.com.intellij.util.io.PersistentMapImpl.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "appendStream", "org/jetbrains/kotlin/com/intellij/util/io/PersistentMapImpl$1", "cleanup"));
            }

            @Override // org.jetbrains.kotlin.com.intellij.util.containers.LimitedPool.ObjectFactory
            public BufferExposingByteArrayOutputStream create() {
                return new BufferExposingByteArrayOutputStream();
            }

            @Override // org.jetbrains.kotlin.com.intellij.util.containers.LimitedPool.ObjectFactory
            public void cleanup(BufferExposingByteArrayOutputStream bufferExposingByteArrayOutputStream) {
                if (bufferExposingByteArrayOutputStream == null) {
                    $$$reportNull$$$0(0);
                }
                bufferExposingByteArrayOutputStream.reset();
            }
        });
        Path file = persistentMapBuilder.getFile();
        KeyDescriptor<Key> keyDescriptor = persistentMapBuilder.getKeyDescriptor();
        DataExternalizer<Value> valueExternalizer = persistentMapBuilder.getValueExternalizer();
        int initialSize = persistentMapBuilder.getInitialSize(INITIAL_INDEX_SIZE);
        int version = persistentMapBuilder.getVersion(0);
        StorageLockContext lockContext = persistentMapBuilder.getLockContext();
        this.myCompactOnClose = persistentMapBuilder.getCompactOnClose(false);
        PersistentHashMapValueStorage.CreationTimeOptions creationTimeOptionsThreadLocalOptions = PersistentHashMapValueStorage.CreationTimeOptions.threadLocalOptions();
        boolean readOnly = persistentMapBuilder.getReadOnly(false);
        this.myIsReadOnly = readOnly;
        PersistentHashMapValueStorage.CreationTimeOptions readOnly2 = readOnly ? creationTimeOptionsThreadLocalOptions.setReadOnly() : creationTimeOptionsThreadLocalOptions;
        PersistentEnumeratorBase<Key> persistentEnumeratorBaseCreateDefaultEnumerator = PersistentEnumerator.createDefaultEnumerator(checkDataFiles(file), keyDescriptor, initialSize, lockContext, modifyVersionDependingOnOptions(version, readOnly2));
        this.myEnumerator = persistentEnumeratorBaseCreateDefaultEnumerator;
        this.myStorageFile = file;
        this.myKeyDescriptor = keyDescriptor;
        LowMemoryWatcher lowMemoryWatcherRegister = null;
        this.myWal = persistentMapBuilder.isEnableWal() ? new PersistentMapWal<>(keyDescriptor, valueExternalizer, readOnly2.useCompression(), file.resolveSibling(file.getFileName().toString() + ".wal"), persistentMapBuilder.getWalExecutor(), true) : null;
        PersistentEnumeratorBase.RecordBufferHandler<PersistentEnumeratorBase<?>> recordHandler = persistentEnumeratorBaseCreateDefaultEnumerator.getRecordHandler();
        this.myParentValueRefOffset = recordHandler.getRecordBuffer(persistentEnumeratorBaseCreateDefaultEnumerator).length;
        boolean z2 = (valueExternalizer instanceof IntInlineKeyDescriptor) && persistentMapBuilder.getInlineValues(false);
        this.myIntMapping = z2;
        if ((keyDescriptor instanceof InlineKeyDescriptor) && (persistentEnumeratorBaseCreateDefaultEnumerator instanceof PersistentBTreeEnumerator)) {
            z = true;
        }
        this.myDirectlyStoreLongFileOffsetMode = z;
        persistentEnumeratorBaseCreateDefaultEnumerator.setRecordHandler(new MyEnumeratorRecordHandler(this, recordHandler));
        persistentEnumeratorBaseCreateDefaultEnumerator.setMarkCleanCallback(new Flushable() { // from class: org.jetbrains.kotlin.com.intellij.util.io.PersistentMapImpl$$ExternalSyntheticLambda3
            @Override // java.io.Flushable
            public final void flush() throws IOException {
                this.f$0.m6726xeacacb36();
            }
        });
        if (myDoTrace) {
            LOG.info("Opened " + file);
        }
        try {
            this.myValueExternalizer = valueExternalizer;
            this.myValueStorage = z2 ? null : new PersistentHashMapValueStorage(getDataFile(file), readOnly2);
            this.myAppendCache = z2 ? null : createAppendCache(keyDescriptor);
            if (!z2) {
                lowMemoryWatcherRegister = LowMemoryWatcher.register(new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.util.io.PersistentMapImpl$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m6727xa5406bb7();
                    }
                });
            }
            this.myAppendCacheFlusher = lowMemoryWatcherRegister;
            this.myLiveAndGarbageKeysCounter = persistentEnumeratorBaseCreateDefaultEnumerator.getMetaData();
            long metaData2 = persistentEnumeratorBaseCreateDefaultEnumerator.getMetaData2();
            this.myLargeIndexWatermarkId = (int) metaData2;
            this.myReadCompactionGarbageSize = (int) (metaData2 >>> 32);
            this.myCanReEnumerate = persistentEnumeratorBaseCreateDefaultEnumerator.canReEnumerate();
            if (readOnly2.isReadOnly() || !makesSenseToCompact()) {
                return;
            }
            compact();
        } catch (IOException e) {
            try {
                close(true);
            } catch (Throwable unused) {
            }
            throw e;
        } catch (Throwable th) {
            LOG.error(th);
            try {
                close(true);
            } catch (Throwable unused2) {
            }
            throw new CorruptedException(file);
        }
    }

    /* JADX INFO: renamed from: lambda$new$0$org-jetbrains-kotlin-com-intellij-util-io-PersistentMapImpl, reason: not valid java name */
    /* synthetic */ void m6726xeacacb36() throws IOException {
        this.myEnumerator.putMetaData(this.myLiveAndGarbageKeysCounter);
        this.myEnumerator.putMetaData2(((long) this.myLargeIndexWatermarkId) | (((long) this.myReadCompactionGarbageSize) << 32));
    }

    /* JADX INFO: renamed from: lambda$new$1$org-jetbrains-kotlin-com-intellij-util-io-PersistentMapImpl, reason: not valid java name */
    /* synthetic */ void m6727xa5406bb7() {
        try {
            force();
        } catch (IOException e) {
            LOG.error((Throwable) e);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.PersistentMapBase
    public DataExternalizer<Value> getValuesExternalizer() {
        DataExternalizer<Value> dataExternalizer = this.myValueExternalizer;
        if (dataExternalizer == null) {
            $$$reportNull$$$0(1);
        }
        return dataExternalizer;
    }

    private static int modifyVersionDependingOnOptions(int i, PersistentHashMapValueStorage.CreationTimeOptions creationTimeOptions) {
        if (creationTimeOptions == null) {
            $$$reportNull$$$0(2);
        }
        return i + creationTimeOptions.getVersion();
    }

    private SLRUCache<Key, BufferExposingByteArrayOutputStream> createAppendCache(KeyDescriptor<Key> keyDescriptor) {
        return new SLRUCache<Key, BufferExposingByteArrayOutputStream>(16384, 4096, keyDescriptor) { // from class: org.jetbrains.kotlin.com.intellij.util.io.PersistentMapImpl.2
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                String str = i != 1 ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                Object[] objArr = new Object[i != 1 ? 2 : 3];
                if (i != 1) {
                    objArr[0] = "org/jetbrains/kotlin/com/intellij/util/io/PersistentMapImpl$2";
                } else {
                    objArr[0] = "bytes";
                }
                if (i != 1) {
                    objArr[1] = "createValue";
                } else {
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/util/io/PersistentMapImpl$2";
                }
                if (i == 1) {
                    objArr[2] = "onDropFromCache";
                }
                String str2 = String.format(str, objArr);
                if (i == 1) {
                    throw new IllegalArgumentException(str2);
                }
            }

            @Override // org.jetbrains.kotlin.com.intellij.util.containers.SLRUCache
            public BufferExposingByteArrayOutputStream createValue(Key key) {
                BufferExposingByteArrayOutputStream bufferExposingByteArrayOutputStream = (BufferExposingByteArrayOutputStream) PersistentMapImpl.this.myStreamPool.alloc();
                if (bufferExposingByteArrayOutputStream == null) {
                    $$$reportNull$$$0(0);
                }
                return bufferExposingByteArrayOutputStream;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // org.jetbrains.kotlin.com.intellij.util.containers.SLRUMap
            public void onDropFromCache(Key key, BufferExposingByteArrayOutputStream bufferExposingByteArrayOutputStream) {
                int iEnumerate;
                long valueId;
                if (bufferExposingByteArrayOutputStream == null) {
                    $$$reportNull$$$0(1);
                }
                PersistentMapImpl.this.myEnumerator.lockStorageWrite();
                try {
                    try {
                        if (PersistentMapImpl.this.myDirectlyStoreLongFileOffsetMode) {
                            valueId = ((PersistentBTreeEnumerator) PersistentMapImpl.this.myEnumerator).getNonNegativeValue(key);
                            iEnumerate = -1;
                        } else {
                            iEnumerate = PersistentMapImpl.this.enumerate(key);
                            valueId = PersistentMapImpl.this.readValueId(iEnumerate);
                        }
                        int i = iEnumerate;
                        long jAppendBytes = PersistentMapImpl.this.myValueStorage.appendBytes(bufferExposingByteArrayOutputStream.toByteArraySequence(), valueId);
                        if (PersistentMapImpl.this.myDirectlyStoreLongFileOffsetMode) {
                            ((PersistentBTreeEnumerator) PersistentMapImpl.this.myEnumerator).putNonNegativeValue(key, jAppendBytes);
                        } else {
                            PersistentMapImpl.this.updateValueId(i, jAppendBytes, valueId, key, 0);
                        }
                        if (valueId == 0) {
                            PersistentMapImpl.access$714(PersistentMapImpl.this, 4294967296L);
                        }
                        if (bufferExposingByteArrayOutputStream.getInternalBuffer().length <= 4096) {
                            PersistentMapImpl.this.myStreamPool.recycle(bufferExposingByteArrayOutputStream);
                        }
                        PersistentMapImpl.this.myEnumerator.unlockStorageWrite();
                    } catch (IOException e) {
                        PersistentMapImpl.this.markCorrupted();
                        throw new RuntimeException(e);
                    }
                } catch (Throwable th) {
                    PersistentMapImpl.this.myEnumerator.unlockStorageWrite();
                    throw th;
                }
            }
        };
    }

    protected Lock getWriteLock() {
        ReentrantReadWriteLock.WriteLock writeLock = this.myLock.writeLock();
        if (writeLock == null) {
            $$$reportNull$$$0(3);
        }
        return writeLock;
    }

    protected Lock getReadLock() {
        Lock lock = PersistentEnumeratorBase.USE_RW_LOCK ? this.myLock.readLock() : this.myLock.writeLock();
        if (lock == null) {
            $$$reportNull$$$0(4);
        }
        return lock;
    }

    private static boolean doNewCompact() {
        return System.getProperty("idea.persistent.hash.map.oldcompact") == null;
    }

    private boolean forceNewCompact() {
        return System.getProperty("idea.persistent.hash.map.newcompact") != null && ((int) this.myLiveAndGarbageKeysCounter) > 0;
    }

    public Path getBaseFile() {
        return this.myEnumerator.myFile;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.PersistentMapBase
    public void closeAndDelete() {
        Path baseFile = getBaseFile();
        try {
            close(true);
        } catch (IOException unused) {
        }
        IOUtil.deleteAllFilesStartingWith(baseFile.toFile());
        try {
            PersistentMapWal<Key, Value> persistentMapWal = this.myWal;
            if (persistentMapWal != null) {
                persistentMapWal.closeAndDelete();
            }
        } catch (IOException unused2) {
        }
    }

    public boolean makesSenseToCompact() {
        if (!isCompactionSupported()) {
            return false;
        }
        long size = this.myValueStorage.getSize();
        if (size <= 5242880) {
            return false;
        }
        long j = this.myLiveAndGarbageKeysCounter;
        int i = (int) (j / 4294967296L);
        int i2 = (int) j;
        if (size > PackConfig.DEFAULT_DELTA_CACHE_SIZE && forceNewCompact()) {
            return true;
        }
        if (i2 < 50) {
            return false;
        }
        return i2 > i || (size / ((long) (i + i2))) * ((long) i2) > Math.max(PackConfig.DEFAULT_MINSIZE_PREVENT_RACY_PACK, size / 4) || ((long) this.myReadCompactionGarbageSize) > size / 2;
    }

    private static Path checkDataFiles(Path path) {
        if (path == null) {
            $$$reportNull$$$0(5);
        }
        if (!Files.exists(path, new LinkOption[0])) {
            IOUtil.deleteAllFilesStartingWith(getDataFile(path).toFile());
        }
        if (path == null) {
            $$$reportNull$$$0(6);
        }
        return path;
    }

    static Path getDataFile(Path path) {
        if (path == null) {
            $$$reportNull$$$0(7);
        }
        Path pathResolveSibling = path.resolveSibling(path.getFileName() + DATA_FILE_EXTENSION);
        if (pathResolveSibling == null) {
            $$$reportNull$$$0(8);
        }
        return pathResolveSibling;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.PersistentMapBase
    public final void put(Key key, Value value) throws IOException {
        if (this.myIsReadOnly) {
            throw new IncorrectOperationException();
        }
        PersistentMapWal<Key, Value> persistentMapWal = this.myWal;
        if (persistentMapWal != null) {
            persistentMapWal.put(key, value);
        }
        getWriteLock().lock();
        try {
            try {
                doPut(key, value);
                getWriteLock().unlock();
            } catch (IOException e) {
                markCorrupted();
                throw e;
            }
        } catch (Throwable th) {
            getWriteLock().unlock();
            throw th;
        }
    }

    /* JADX WARN: Code duplicated, block: B:21:0x0090 A[Catch: all -> 0x00a8, TryCatch #0 {all -> 0x00a8, blocks: (B:7:0x0032, B:9:0x003f, B:11:0x0043, B:14:0x0057, B:21:0x0090, B:22:0x0098, B:15:0x0067, B:17:0x006f, B:18:0x0081), top: B:28:0x0032 }] */
    /* JADX WARN: Code duplicated, block: B:22:0x0098 A[Catch: all -> 0x00a8, TRY_LEAVE, TryCatch #0 {all -> 0x00a8, blocks: (B:7:0x0032, B:9:0x003f, B:11:0x0043, B:14:0x0057, B:21:0x0090, B:22:0x0098, B:15:0x0067, B:17:0x006f, B:18:0x0081), top: B:28:0x0032 }] */
    /* JADX WARN: Multi-variable type inference failed */
    protected void doPut(Key key, Value value) throws IOException {
        long jAppendBytes;
        long valueId;
        if (this.myIntMapping) {
            jAppendBytes = -1;
        } else {
            BufferExposingByteArrayOutputStream bufferExposingByteArrayOutputStream = new BufferExposingByteArrayOutputStream();
            AppendStream value2 = ourFlyweightAppenderStream.getValue();
            value2.setOut(bufferExposingByteArrayOutputStream);
            this.myValueExternalizer.save(value2, value);
            value2.setOut(null);
            jAppendBytes = this.myValueStorage.appendBytes(bufferExposingByteArrayOutputStream.toByteArraySequence(), 0L);
        }
        long j = jAppendBytes;
        this.myEnumerator.lockStorageWrite();
        try {
            this.myEnumerator.markDirty(true);
            flushAppendCache(key);
            if (this.myDirectlyStoreLongFileOffsetMode) {
                if (this.myIntMapping) {
                    ((PersistentBTreeEnumerator) this.myEnumerator).putNonNegativeValue(key, ((Integer) value).intValue());
                    return;
                }
                valueId = ((PersistentBTreeEnumerator) this.myEnumerator).getNonNegativeValue(key);
                ((PersistentBTreeEnumerator) this.myEnumerator).putNonNegativeValue(key, j);
                if (valueId != 0) {
                    this.myLiveAndGarbageKeysCounter++;
                } else {
                    this.myLiveAndGarbageKeysCounter += 4294967296L;
                }
            }
            int iEnumerate = enumerate(key);
            if (this.myIntMapping) {
                this.myEnumerator.myStorage.putInt(iEnumerate + this.myParentValueRefOffset, ((Integer) value).intValue());
                return;
            }
            valueId = readValueId(iEnumerate);
            updateValueId(iEnumerate, j, valueId, key, 0);
            if (valueId != 0) {
                this.myLiveAndGarbageKeysCounter++;
            } else {
                this.myLiveAndGarbageKeysCounter += 4294967296L;
            }
        } finally {
            this.myEnumerator.unlockStorageWrite();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int enumerate(Key key) throws IOException {
        if (this.myIsReadOnly) {
            throw new IncorrectOperationException();
        }
        getWriteLock().lock();
        try {
            this.myIntAddressForNewRecord = canUseIntAddressForNewRecord(this.myValueStorage.getSize());
            return this.myEnumerator.enumerate(key);
        } finally {
            getWriteLock().unlock();
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.PersistentMapBase
    public final void appendData(Key key, AppendablePersistentMap.ValueDataAppender valueDataAppender) throws IOException {
        if (valueDataAppender == null) {
            $$$reportNull$$$0(9);
        }
        if (this.myIsReadOnly) {
            throw new IncorrectOperationException();
        }
        PersistentMapWal<Key, Value> persistentMapWal = this.myWal;
        if (persistentMapWal != null) {
            persistentMapWal.appendData(key, valueDataAppender);
        }
        getWriteLock().lock();
        try {
            try {
                doAppendData(key, valueDataAppender);
                getWriteLock().unlock();
            } catch (IOException e) {
                markCorrupted();
                throw e;
            }
        } catch (Throwable th) {
            getWriteLock().unlock();
            throw th;
        }
    }

    private void doAppendData(Key key, AppendablePersistentMap.ValueDataAppender valueDataAppender) throws IOException {
        if (valueDataAppender == null) {
            $$$reportNull$$$0(10);
        }
        this.myEnumerator.markDirty(true);
        AppendStream value = ourFlyweightAppenderStream.getValue();
        BufferExposingByteArrayOutputStream bufferExposingByteArrayOutputStream = this.myAppendCache.get(key);
        value.setOut(bufferExposingByteArrayOutputStream);
        this.myValueStorage.checkAppendsAllowed(bufferExposingByteArrayOutputStream.size());
        valueDataAppender.append(value);
        value.setOut(null);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.PersistentMapBase
    public final boolean processKeys(Processor<? super Key> processor) throws IOException {
        if (processor == null) {
            $$$reportNull$$$0(11);
        }
        getReadLock().lock();
        try {
            try {
                flushAppendCache();
                boolean zIterateData = this.myEnumerator.iterateData(processor);
                getReadLock().unlock();
                return zIterateData;
            } catch (IOException e) {
                markCorrupted();
                throw e;
            }
        } catch (Throwable th) {
            getReadLock().unlock();
            throw th;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.PersistentMapBase
    public boolean isClosed() {
        return this.myEnumerator.isClosed();
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.PersistentMapBase
    public boolean isDirty() {
        return this.myEnumerator.isDirty();
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.PersistentMapBase
    public void markDirty() throws IOException {
        getWriteLock().lock();
        try {
            this.myEnumerator.markDirty(true);
        } finally {
            getWriteLock().unlock();
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.PersistentMapBase
    public final boolean processExistingKeys(Processor<? super Key> processor) throws IOException {
        if (processor == null) {
            $$$reportNull$$$0(12);
        }
        getReadLock().lock();
        try {
            try {
                flushAppendCache();
                boolean zProcessAllDataObject = this.myEnumerator.processAllDataObject(processor, new PersistentEnumeratorBase.DataFilter() { // from class: org.jetbrains.kotlin.com.intellij.util.io.PersistentMapImpl.4
                    @Override // org.jetbrains.kotlin.com.intellij.util.io.PersistentEnumeratorBase.DataFilter
                    public boolean accept(int i) throws IOException {
                        return PersistentMapImpl.this.readValueId(i) != 0;
                    }
                });
                getReadLock().unlock();
                return zProcessAllDataObject;
            } catch (IOException e) {
                markCorrupted();
                throw e;
            }
        } catch (Throwable th) {
            getReadLock().unlock();
            throw th;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.PersistentMapBase
    public final Value get(Key key) throws IOException {
        getReadLock().lock();
        try {
            try {
                Value valueDoGet = doGet(key);
                getReadLock().unlock();
                return valueDoGet;
            } catch (IOException e) {
                markCorrupted();
                throw e;
            }
        } catch (Throwable th) {
            getReadLock().unlock();
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void markCorrupted() {
        if (this.myStorageFile.getFileSystem().isReadOnly()) {
            return;
        }
        try {
            this.myEnumerator.markCorrupted();
        } catch (Exception e) {
            LOG.warn(e);
        }
    }

    /* JADX WARN: Code duplicated, block: B:24:0x0057  */
    /* JADX WARN: Code duplicated, block: B:28:0x0081  */
    /* JADX WARN: Code duplicated, block: B:31:0x009b A[Catch: all -> 0x00be, TryCatch #3 {all -> 0x00be, blocks: (B:29:0x0091, B:31:0x009b, B:33:0x00a9, B:32:0x00a3), top: B:53:0x0091 }] */
    /* JADX WARN: Code duplicated, block: B:32:0x00a3 A[Catch: all -> 0x00be, TryCatch #3 {all -> 0x00be, blocks: (B:29:0x0091, B:31:0x009b, B:33:0x00a9, B:32:0x00a3), top: B:53:0x0091 }] */
    protected Value doGet(Key key) throws IOException {
        int iTryEnumerate;
        long valueId;
        Value value;
        int i;
        long j;
        PersistentHashMapValueStorage.ReadResult bytes;
        DataInputStream dataInputStream;
        final Value value2;
        long jCompactChunks;
        flushAppendCache(key);
        this.myEnumerator.lockStorageRead();
        try {
            if (this.myDirectlyStoreLongFileOffsetMode) {
                valueId = ((PersistentBTreeEnumerator) this.myEnumerator).getNonNegativeValue(key);
                if (this.myIntMapping) {
                    value = (Value) Integer.valueOf((int) valueId);
                    return value;
                }
                iTryEnumerate = -1;
                i = iTryEnumerate;
                j = valueId;
                if (j != 0) {
                    this.myEnumerator.unlockStorageRead();
                    bytes = this.myValueStorage.readBytes(j);
                    dataInputStream = new DataInputStream(new UnsyncByteArrayInputStream(bytes.buffer));
                    try {
                        value2 = this.myValueExternalizer.read(dataInputStream);
                        dataInputStream.close();
                        if (this.myValueStorage.performChunksCompaction(bytes.chunksCount)) {
                            jCompactChunks = this.myValueStorage.compactChunks(new AppendablePersistentMap.ValueDataAppender() { // from class: org.jetbrains.kotlin.com.intellij.util.io.PersistentMapImpl.5
                                private static /* synthetic */ void $$$reportNull$$$0(int i2) {
                                    throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "out", "org/jetbrains/kotlin/com/intellij/util/io/PersistentMapImpl$5", "append"));
                                }

                                @Override // org.jetbrains.kotlin.com.intellij.util.io.AppendablePersistentMap.ValueDataAppender
                                public void append(DataOutput dataOutput) throws IOException {
                                    if (dataOutput == null) {
                                        $$$reportNull$$$0(0);
                                    }
                                    PersistentMapImpl.this.myValueExternalizer.save(dataOutput, value2);
                                }
                            }, bytes);
                            this.myEnumerator.lockStorageWrite();
                            try {
                                this.myEnumerator.markDirty(true);
                                if (this.myDirectlyStoreLongFileOffsetMode) {
                                    ((PersistentBTreeEnumerator) this.myEnumerator).putNonNegativeValue(key, jCompactChunks);
                                } else {
                                    updateValueId(i, jCompactChunks, j, key, 0);
                                }
                                this.myLiveAndGarbageKeysCounter++;
                                this.myReadCompactionGarbageSize += bytes.buffer.length;
                            } finally {
                                this.myEnumerator.unlockStorageWrite();
                            }
                        }
                        return value2;
                    } catch (Throwable th) {
                        try {
                            dataInputStream.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                }
            } else {
                iTryEnumerate = this.myEnumerator.tryEnumerate(key);
                if (iTryEnumerate != 0) {
                    if (this.myIntMapping) {
                        value = (Value) Integer.valueOf(this.myEnumerator.myStorage.getInt(iTryEnumerate + this.myParentValueRefOffset));
                        return value;
                    }
                    valueId = readValueId(iTryEnumerate);
                    i = iTryEnumerate;
                    j = valueId;
                    if (j != 0) {
                        this.myEnumerator.unlockStorageRead();
                        bytes = this.myValueStorage.readBytes(j);
                        dataInputStream = new DataInputStream(new UnsyncByteArrayInputStream(bytes.buffer));
                        value2 = this.myValueExternalizer.read(dataInputStream);
                        dataInputStream.close();
                        if (this.myValueStorage.performChunksCompaction(bytes.chunksCount)) {
                            jCompactChunks = this.myValueStorage.compactChunks(new AppendablePersistentMap.ValueDataAppender() { // from class: org.jetbrains.kotlin.com.intellij.util.io.PersistentMapImpl.5
                                private static /* synthetic */ void $$$reportNull$$$0(int i2) {
                                    throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "out", "org/jetbrains/kotlin/com/intellij/util/io/PersistentMapImpl$5", "append"));
                                }

                                @Override // org.jetbrains.kotlin.com.intellij.util.io.AppendablePersistentMap.ValueDataAppender
                                public void append(DataOutput dataOutput) throws IOException {
                                    if (dataOutput == null) {
                                        $$$reportNull$$$0(0);
                                    }
                                    PersistentMapImpl.this.myValueExternalizer.save(dataOutput, value2);
                                }
                            }, bytes);
                            this.myEnumerator.lockStorageWrite();
                            this.myEnumerator.markDirty(true);
                            if (this.myDirectlyStoreLongFileOffsetMode) {
                                ((PersistentBTreeEnumerator) this.myEnumerator).putNonNegativeValue(key, jCompactChunks);
                            } else {
                                updateValueId(i, jCompactChunks, j, key, 0);
                            }
                            this.myLiveAndGarbageKeysCounter++;
                            this.myReadCompactionGarbageSize += bytes.buffer.length;
                        }
                        return value2;
                    }
                }
            }
            return null;
        } finally {
            this.myEnumerator.unlockStorageRead();
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.PersistentMapBase
    public final boolean containsKey(Key key) throws IOException {
        getReadLock().lock();
        try {
            return doContainsMapping(key);
        } finally {
            getReadLock().unlock();
        }
    }

    /* JADX WARN: Code duplicated, block: B:9:0x001d  */
    private boolean doContainsMapping(Key key) throws IOException {
        flushAppendCache(key);
        this.myEnumerator.lockStorageRead();
        try {
            boolean z = true;
            if (!this.myDirectlyStoreLongFileOffsetMode) {
                int iTryEnumerate = this.myEnumerator.tryEnumerate(key);
                if (iTryEnumerate == 0) {
                    return false;
                }
                if (!this.myIntMapping && readValueId(iTryEnumerate) == 0) {
                    z = false;
                }
            } else if (((PersistentBTreeEnumerator) this.myEnumerator).getNonNegativeValue(key) == 0) {
                z = false;
            }
            return z;
        } finally {
            this.myEnumerator.unlockStorageRead();
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.PersistentMapBase
    public final void remove(Key key) throws IOException {
        if (this.myIsReadOnly) {
            throw new IncorrectOperationException();
        }
        PersistentMapWal<Key, Value> persistentMapWal = this.myWal;
        if (persistentMapWal != null) {
            persistentMapWal.remove(key);
        }
        getWriteLock().lock();
        try {
            doRemove(key);
        } finally {
            getWriteLock().unlock();
        }
    }

    protected void doRemove(Key key) throws IOException {
        long nonNegativeValue;
        this.myEnumerator.lockStorageWrite();
        try {
            flushAppendCache(key);
            if (this.myDirectlyStoreLongFileOffsetMode) {
                nonNegativeValue = ((PersistentBTreeEnumerator) this.myEnumerator).getNonNegativeValue(key);
                if (nonNegativeValue != 0) {
                    ((PersistentBTreeEnumerator) this.myEnumerator).putNonNegativeValue(key, 0L);
                }
            } else {
                int iTryEnumerate = this.myEnumerator.tryEnumerate(key);
                if (iTryEnumerate == 0) {
                    return;
                }
                this.myEnumerator.markDirty(true);
                long valueId = readValueId(iTryEnumerate);
                updateValueId(iTryEnumerate, 0L, valueId, key, 0);
                nonNegativeValue = valueId;
            }
            if (nonNegativeValue != 0) {
                this.myLiveAndGarbageKeysCounter -= 4294967295L;
            }
        } finally {
            this.myEnumerator.unlockStorageWrite();
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.PersistentMapBase
    public final void force() throws IOException {
        if (this.myIsReadOnly) {
            return;
        }
        if (myDoTrace) {
            LOG.info("Forcing " + this.myStorageFile);
        }
        PersistentMapWal<Key, Value> persistentMapWal = this.myWal;
        if (persistentMapWal != null) {
            persistentMapWal.flush();
        }
        getWriteLock().lock();
        try {
            doForce();
        } finally {
            getWriteLock().unlock();
        }
    }

    protected void doForce() {
        this.myEnumerator.lockStorageWrite();
        try {
            try {
                clearAppenderCaches();
                this.myEnumerator.m6720xd71ec63f();
                this.myEnumerator.unlockStorageWrite();
            } catch (Throwable th) {
                this.myEnumerator.m6720xd71ec63f();
                throw th;
            }
        } catch (Throwable th2) {
            this.myEnumerator.unlockStorageWrite();
            throw th2;
        }
    }

    private void clearAppenderCaches() {
        if (this.myIntMapping) {
            return;
        }
        flushAppendCache();
        this.myValueStorage.force();
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.PersistentMapBase
    public final void close() throws IOException {
        close(false);
    }

    private void close(boolean z) throws IOException {
        if (myDoTrace) {
            LOG.info("Closed " + this.myStorageFile);
        }
        getWriteLock().lock();
        try {
            if (!isClosed()) {
                PersistentMapWal<Key, Value> persistentMapWal = this.myWal;
                if (persistentMapWal != null) {
                    persistentMapWal.close();
                }
                if (!z) {
                    try {
                        if (this.myCompactOnClose && isCompactionSupported()) {
                            compact();
                        }
                    } catch (Throwable th) {
                        doClose();
                        throw th;
                    }
                }
                doClose();
                getWriteLock().unlock();
                return;
            }
            getWriteLock().unlock();
        } catch (Throwable th2) {
            getWriteLock().unlock();
            throw th2;
        }
    }

    private void doClose() throws IOException {
        this.myEnumerator.lockStorageWrite();
        try {
            try {
                try {
                    LowMemoryWatcher lowMemoryWatcher = this.myAppendCacheFlusher;
                    if (lowMemoryWatcher != null) {
                        lowMemoryWatcher.stop();
                    }
                    flushAppendCache();
                    PersistentHashMapValueStorage persistentHashMapValueStorage = this.myValueStorage;
                    if (persistentHashMapValueStorage != null) {
                        try {
                            persistentHashMapValueStorage.dispose();
                        } catch (Throwable th) {
                            this.myEnumerator.close();
                            throw th;
                        }
                    }
                    this.myEnumerator.close();
                    this.myEnumerator.unlockStorageWrite();
                } catch (Throwable th2) {
                    PersistentHashMapValueStorage persistentHashMapValueStorage2 = this.myValueStorage;
                    if (persistentHashMapValueStorage2 != null) {
                        try {
                            persistentHashMapValueStorage2.dispose();
                        } finally {
                            this.myEnumerator.close();
                        }
                    }
                    throw th2;
                }
            } catch (RuntimeException e) {
                Throwable cause = e.getCause();
                if (!(cause instanceof IOException)) {
                    throw e;
                }
                throw ((IOException) cause);
            }
        } catch (Throwable th3) {
            this.myEnumerator.unlockStorageWrite();
            throw th3;
        }
    }

    static final class CompactionRecordInfo {
        final int address;
        final int key;
        long newValueAddress;
        byte[] value;
        long valueAddress;

        CompactionRecordInfo(int i, long j, int i2) {
            this.key = i;
            this.address = i2;
            this.valueAddress = j;
        }
    }

    public void compact() throws IOException {
        if (!isCompactionSupported()) {
            throw new IncorrectOperationException();
        }
        getWriteLock().lock();
        try {
            force();
            Logger logger = LOG;
            logger.info("Compacting " + this.myEnumerator.myFile);
            logger.info("Live keys:" + ((int) (this.myLiveAndGarbageKeysCounter / 4294967296L)) + ", dead keys:" + ((int) this.myLiveAndGarbageKeysCounter) + ", read compaction size:" + this.myReadCompactionGarbageSize);
            long jCurrentTimeMillis = System.currentTimeMillis();
            Path dataFile = getDataFile(this.myEnumerator.myFile);
            File[] filesInDirectoryWithNameStartingWith = getFilesInDirectoryWithNameStartingWith(dataFile);
            Path pathResolveSibling = dataFile.resolveSibling(dataFile.getFileName() + ".new");
            PersistentHashMapValueStorage.CreationTimeOptions options = this.myValueStorage.getOptions();
            final PersistentHashMapValueStorage persistentHashMapValueStorage = new PersistentHashMapValueStorage(pathResolveSibling, options);
            this.myValueStorage.switchToCompactionMode();
            this.myEnumerator.markDirty(true);
            long size = this.myValueStorage.getSize();
            this.myLiveAndGarbageKeysCounter = 0L;
            int i = 0;
            this.myReadCompactionGarbageSize = 0;
            try {
                if (doNewCompact()) {
                    newCompact(persistentHashMapValueStorage);
                } else {
                    this.myEnumerator.traverseAllRecords(new PersistentEnumeratorBase.RecordsProcessor() { // from class: org.jetbrains.kotlin.com.intellij.util.io.PersistentMapImpl.6
                        @Override // org.jetbrains.kotlin.com.intellij.util.io.PersistentEnumeratorBase.RecordsProcessor
                        public boolean process(int i2) throws IOException {
                            long valueId = PersistentMapImpl.this.readValueId(i2);
                            if (valueId == 0) {
                                return true;
                            }
                            PersistentHashMapValueStorage.ReadResult bytes = PersistentMapImpl.this.myValueStorage.readBytes(valueId);
                            PersistentMapImpl.this.updateValueId(i2, persistentHashMapValueStorage.appendBytes(bytes.buffer, 0, bytes.buffer.length, 0L), valueId, null, getCurrentKey());
                            PersistentMapImpl.access$714(PersistentMapImpl.this, 4294967296L);
                            return true;
                        }
                    });
                }
                persistentHashMapValueStorage.dispose();
                this.myValueStorage.dispose();
                for (File file : filesInDirectoryWithNameStartingWith) {
                }
                long size2 = persistentHashMapValueStorage.getSize();
                File[] filesInDirectoryWithNameStartingWith2 = getFilesInDirectoryWithNameStartingWith(pathResolveSibling);
                File file2 = pathResolveSibling.getParent().toFile();
                String string = pathResolveSibling.getFileName().toString();
                String string2 = dataFile.getFileName().toString();
                int length = filesInDirectoryWithNameStartingWith2.length;
                while (i < length) {
                    File file3 = filesInDirectoryWithNameStartingWith2[i];
                    FileUtil.rename(file3, new File(file2, StringUtil.replace(file3.getName(), string, string2)));
                    i++;
                    filesInDirectoryWithNameStartingWith2 = filesInDirectoryWithNameStartingWith2;
                    string = string;
                }
                this.myValueStorage = new PersistentHashMapValueStorage(dataFile, options);
                Logger logger2 = LOG;
                logger2.info("Compacted " + this.myEnumerator.myFile + ":" + size + " bytes into " + size2 + " bytes in " + (System.currentTimeMillis() - jCurrentTimeMillis) + "ms.");
                this.myEnumerator.putMetaData(this.myLiveAndGarbageKeysCounter);
                this.myEnumerator.putMetaData2(this.myLargeIndexWatermarkId);
                if (myDoTrace) {
                    logger2.assertTrue(this.myEnumerator.isDirty());
                }
                getWriteLock().unlock();
            } catch (Throwable th) {
                persistentHashMapValueStorage.dispose();
                throw th;
            }
        } catch (Throwable th2) {
            getWriteLock().unlock();
            throw th2;
        }
    }

    public boolean isCompactionSupported() {
        return (this.myIsReadOnly || this.myIntMapping) ? false : true;
    }

    private void flushAppendCache(Key key) {
        SLRUCache<Key, BufferExposingByteArrayOutputStream> sLRUCache = this.myAppendCache;
        if (sLRUCache != null) {
            sLRUCache.remove(key);
        }
    }

    private void flushAppendCache() {
        SLRUCache<Key, BufferExposingByteArrayOutputStream> sLRUCache = this.myAppendCache;
        if (sLRUCache != null) {
            sLRUCache.clear();
        }
    }

    private static File[] getFilesInDirectoryWithNameStartingWith(Path path) throws IOException {
        if (path == null) {
            $$$reportNull$$$0(13);
        }
        Path parent = path.getParent();
        if (parent == null) {
            return ArrayUtil.EMPTY_FILE_ARRAY;
        }
        final Path fileName = path.getFileName();
        Stream<Path> list = Files.list(parent);
        try {
            File[] fileArr = (File[]) list.filter(new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.util.io.PersistentMapImpl$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return ((Path) obj).getFileName().toString().startsWith(fileName.toString());
                }
            }).map(new Function() { // from class: org.jetbrains.kotlin.com.intellij.util.io.PersistentMapImpl$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((Path) obj).toFile();
                }
            }).toArray(new IntFunction() { // from class: org.jetbrains.kotlin.com.intellij.util.io.PersistentMapImpl$$ExternalSyntheticLambda2
                @Override // java.util.function.IntFunction
                public final Object apply(int i) {
                    return PersistentMapImpl.lambda$getFilesInDirectoryWithNameStartingWith$4(i);
                }
            });
            if (list != null) {
                list.close();
            }
            return fileArr;
        } catch (Throwable th) {
            if (list != null) {
                try {
                    list.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    static /* synthetic */ File[] lambda$getFilesInDirectoryWithNameStartingWith$4(int i) {
        return new File[i];
    }

    private void newCompact(PersistentHashMapValueStorage persistentHashMapValueStorage) throws IOException {
        long jCompactValues;
        if (persistentHashMapValueStorage == null) {
            $$$reportNull$$$0(14);
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        final ArrayList<CompactionRecordInfo> arrayList = new ArrayList(10000);
        this.myEnumerator.traverseAllRecords(new PersistentEnumeratorBase.RecordsProcessor() { // from class: org.jetbrains.kotlin.com.intellij.util.io.PersistentMapImpl.7
            @Override // org.jetbrains.kotlin.com.intellij.util.io.PersistentEnumeratorBase.RecordsProcessor
            public boolean process(int i) throws IOException {
                long valueId = PersistentMapImpl.this.readValueId(i);
                if (valueId == 0) {
                    return true;
                }
                arrayList.add(new CompactionRecordInfo(getCurrentKey(), valueId, i));
                return true;
            }
        });
        Logger logger = LOG;
        logger.info("Loaded mappings:" + (System.currentTimeMillis() - jCurrentTimeMillis) + "ms, keys:" + arrayList.size());
        long jCurrentTimeMillis2 = System.currentTimeMillis();
        if (arrayList.isEmpty()) {
            jCompactValues = 0;
        } else {
            try {
                jCompactValues = this.myValueStorage.compactValues(arrayList, persistentHashMapValueStorage);
            } catch (IOException e) {
                throw e;
            } catch (Throwable th) {
                throw new IOException("Compaction failed", th);
            }
        }
        logger.info("Compacted values for:" + (System.currentTimeMillis() - jCurrentTimeMillis2) + "ms fragments:" + ((int) jCompactValues) + ", new fragments:" + (jCompactValues >> 32));
        long jCurrentTimeMillis3 = System.currentTimeMillis();
        try {
            this.myEnumerator.lockStorageWrite();
            for (CompactionRecordInfo compactionRecordInfo : arrayList) {
                updateValueId(compactionRecordInfo.address, compactionRecordInfo.newValueAddress, compactionRecordInfo.valueAddress, null, compactionRecordInfo.key);
                this.myLiveAndGarbageKeysCounter += 4294967296L;
            }
            this.myEnumerator.unlockStorageWrite();
            LOG.info("Updated mappings:" + (System.currentTimeMillis() - jCurrentTimeMillis3) + " ms");
        } catch (Throwable th2) {
            this.myEnumerator.unlockStorageWrite();
            throw th2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public long readValueId(int i) throws IOException {
        if (this.myDirectlyStoreLongFileOffsetMode) {
            return ((PersistentBTreeEnumerator) this.myEnumerator).keyIdToNonNegativeOffset(i);
        }
        long j = this.myEnumerator.myStorage.getInt(this.myParentValueRefOffset + i);
        if (j == 0 || j == -1) {
            return 0L;
        }
        if (j < 0) {
            return (-j) - 1;
        }
        return ((j << 32) + (((long) this.myEnumerator.myStorage.getInt(i + this.myParentValueRefOffset + 4)) & 4294967295L)) & (-4611686018427387905L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public void updateValueId(int i, long j, long j2, Key key, int i2) throws IOException {
        if (this.myDirectlyStoreLongFileOffsetMode) {
            ((PersistentBTreeEnumerator) this.myEnumerator).putNonNegativeValue(((InlineKeyDescriptor) this.myKeyDescriptor).fromInt(i2), j);
            return;
        }
        boolean z = false;
        boolean z2 = j2 == 0;
        if (z2) {
            this.requests++;
        }
        if (!this.myCanReEnumerate) {
            z = true;
        } else if (canUseIntAddressForNewRecord(j)) {
            this.myEnumerator.myStorage.putInt(this.myParentValueRefOffset + i, -((int) (1 + j)));
            if (z2) {
                this.smallKeys++;
            }
        } else {
            int i3 = this.myLargeIndexWatermarkId;
            if ((i < i3 || i3 == 0) && (z2 || canUseIntAddressForNewRecord(j2))) {
                this.myIntAddressForNewRecord = false;
                PersistentEnumeratorBase<Key> persistentEnumeratorBase = this.myEnumerator;
                if (key == null) {
                    key = (Key) persistentEnumeratorBase.getValue(i, i2);
                }
                i = persistentEnumeratorBase.reEnumerate(key);
                this.transformedKeys++;
                if (this.myLargeIndexWatermarkId == 0) {
                    this.myLargeIndexWatermarkId = i;
                }
            }
            z = true;
        }
        if (z) {
            long j3 = j | 4611686018427387904L;
            this.myEnumerator.myStorage.putInt(this.myParentValueRefOffset + i, (int) (j3 >>> 32));
            this.myEnumerator.myStorage.putInt(i + this.myParentValueRefOffset + 4, (int) j3);
            if (z2) {
                this.largeKeys++;
            }
        }
        if (z2 && IOStatistics.DEBUG && (this.requests & 65535) == 0) {
            IOStatistics.dump("small:" + this.smallKeys + ", large:" + this.largeKeys + ", transformed:" + this.transformedKeys + ",@" + getBaseFile());
        }
    }

    public String toString() {
        return getClass().getName() + PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT + Integer.toHexString(hashCode()) + ": " + this.myStorageFile;
    }

    /* JADX INFO: Access modifiers changed from: private */
    class MyEnumeratorRecordHandler extends PersistentEnumeratorBase.RecordBufferHandler<PersistentEnumeratorBase<?>> {
        private final ThreadLocal<byte[]> myRecordBuffer;
        private final PersistentEnumeratorBase.RecordBufferHandler<PersistentEnumeratorBase<?>> myRecordHandler;
        private final ThreadLocal<byte[]> mySmallRecordBuffer;
        final /* synthetic */ PersistentMapImpl this$0;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 1 ? 3 : 2];
            if (i == 1) {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/io/PersistentMapImpl$MyEnumeratorRecordHandler";
            } else if (i != 2) {
                objArr[0] = "recordHandler";
            } else {
                objArr[0] = "buf";
            }
            if (i != 1) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/util/io/PersistentMapImpl$MyEnumeratorRecordHandler";
            } else {
                objArr[1] = "getRecordBuffer";
            }
            if (i != 1) {
                if (i != 2) {
                    objArr[2] = "<init>";
                } else {
                    objArr[2] = "setupRecord";
                }
            }
            String str2 = String.format(str, objArr);
            if (i == 1) {
                throw new IllegalStateException(str2);
            }
        }

        MyEnumeratorRecordHandler(PersistentMapImpl persistentMapImpl, PersistentEnumeratorBase.RecordBufferHandler<PersistentEnumeratorBase<?>> recordBufferHandler) {
            if (recordBufferHandler == null) {
                $$$reportNull$$$0(0);
            }
            this.this$0 = persistentMapImpl;
            this.myRecordHandler = recordBufferHandler;
            this.myRecordBuffer = ThreadLocal.withInitial(new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.util.io.PersistentMapImpl$MyEnumeratorRecordHandler$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.m6728x66fb18fb();
                }
            });
            this.mySmallRecordBuffer = ThreadLocal.withInitial(new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.util.io.PersistentMapImpl$MyEnumeratorRecordHandler$$ExternalSyntheticLambda1
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.m6729xd5822a3c();
                }
            });
        }

        /* JADX INFO: renamed from: lambda$new$0$org-jetbrains-kotlin-com-intellij-util-io-PersistentMapImpl$MyEnumeratorRecordHandler, reason: not valid java name */
        /* synthetic */ byte[] m6728x66fb18fb() {
            return this.this$0.myDirectlyStoreLongFileOffsetMode ? ArrayUtilRt.EMPTY_BYTE_ARRAY : new byte[this.this$0.myParentValueRefOffset + 8];
        }

        /* JADX INFO: renamed from: lambda$new$1$org-jetbrains-kotlin-com-intellij-util-io-PersistentMapImpl$MyEnumeratorRecordHandler, reason: not valid java name */
        /* synthetic */ byte[] m6729xd5822a3c() {
            return this.this$0.myDirectlyStoreLongFileOffsetMode ? ArrayUtilRt.EMPTY_BYTE_ARRAY : new byte[this.this$0.myParentValueRefOffset + 4];
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.io.PersistentEnumeratorBase.RecordBufferHandler
        int recordWriteOffset(PersistentEnumeratorBase<?> persistentEnumeratorBase, byte[] bArr) throws IOException {
            return this.myRecordHandler.recordWriteOffset(persistentEnumeratorBase, bArr);
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.io.PersistentEnumeratorBase.RecordBufferHandler
        byte[] getRecordBuffer(PersistentEnumeratorBase<?> persistentEnumeratorBase) {
            byte[] bArr = (this.this$0.myIntAddressForNewRecord ? this.mySmallRecordBuffer : this.myRecordBuffer).get();
            if (bArr == null) {
                $$$reportNull$$$0(1);
            }
            return bArr;
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.io.PersistentEnumeratorBase.RecordBufferHandler
        void setupRecord(PersistentEnumeratorBase persistentEnumeratorBase, int i, int i2, byte[] bArr) {
            if (bArr == null) {
                $$$reportNull$$$0(2);
            }
            this.myRecordHandler.setupRecord(persistentEnumeratorBase, i, i2, bArr);
            for (int i3 = this.this$0.myParentValueRefOffset; i3 < bArr.length; i3++) {
                bArr[i3] = 0;
            }
        }
    }
}
