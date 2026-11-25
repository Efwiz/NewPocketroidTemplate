package org.jetbrains.kotlin.com.intellij.util.io;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.jetbrains.kotlin.com.intellij.openapi.Forceable;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable;
import org.jetbrains.kotlin.com.intellij.openapi.util.io.FileUtil;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.Processor;
import org.jetbrains.kotlin.com.intellij.util.SystemProperties;
import org.jetbrains.kotlin.com.intellij.util.io.keyStorage.AppendableObjectStorage;
import org.jetbrains.kotlin.com.intellij.util.io.keyStorage.AppendableStorageBackedByResizableMappedFile;
import org.jetbrains.kotlin.com.intellij.util.io.keyStorage.InlinedKeyStorage;
import org.jetbrains.kotlin.com.intellij.util.io.keyStorage.NoDataException;

/* JADX INFO: loaded from: classes3.dex */
public abstract class PersistentEnumeratorBase<Data> implements Forceable, DataEnumeratorEx<Data>, Closeable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    protected static final Logger LOG = Logger.getInstance((Class<?>) PersistentEnumeratorBase.class);
    protected static final boolean USE_RW_LOCK = SystemProperties.getBooleanProperty("idea.persistent.data.use.read.write.lock", false);
    private boolean myClosed;
    private boolean myCorrupted;
    final KeyDescriptor<Data> myDataDescriptor;
    private boolean myDirty;
    private volatile boolean myDirtyStatusUpdateInProgress;
    private final boolean myDoCaching;
    protected final Path myFile;
    protected final AppendableObjectStorage<Data> myKeyStorage;
    private final ReentrantReadWriteLock myLock;
    private Flushable myMarkCleanCallback;
    private RecordBufferHandler<PersistentEnumeratorBase<?>> myRecordHandler;
    protected final ResizeableMappedFile myStorage;
    private final Version myVersion;

    public interface DataFilter {
        boolean accept(int i) throws IOException;
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 5 || i == 6 || i == 7 || i == 11) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 5 || i == 6 || i == 7 || i == 11) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "storage";
                break;
            case 2:
                objArr[0] = "dataDescriptor";
                break;
            case 3:
                objArr[0] = "version";
                break;
            case 4:
                objArr[0] = "recordBufferHandler";
                break;
            case 5:
            case 6:
            case 7:
            case 11:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/io/PersistentEnumeratorBase";
                break;
            case 8:
                objArr[0] = "recordHandler";
                break;
            case 9:
                objArr[0] = "markCleanCallback";
                break;
            case 10:
            case 12:
            case 13:
                objArr[0] = "processor";
                break;
            default:
                objArr[0] = "file";
                break;
        }
        if (i == 5) {
            objArr[1] = "getWriteLock";
        } else if (i == 6) {
            objArr[1] = "getReadLock";
        } else if (i == 7) {
            objArr[1] = "getRecordHandler";
        } else if (i != 11) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/io/PersistentEnumeratorBase";
        } else {
            objArr[1] = "getAllDataObjects";
        }
        switch (i) {
            case 5:
            case 6:
            case 7:
            case 11:
                break;
            case 8:
                objArr[2] = "setRecordHandler";
                break;
            case 9:
                objArr[2] = "setMarkCleanCallback";
                break;
            case 10:
                objArr[2] = "processAllDataObject";
                break;
            case 12:
                objArr[2] = "iterateData";
                break;
            case 13:
                objArr[2] = "doIterateData";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 5 && i != 6 && i != 7 && i != 11) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    boolean canReEnumerate() {
        return false;
    }

    protected abstract int enumerateImpl(Data data, boolean z, boolean z2) throws IOException;

    protected abstract int indexToAddr(int i) throws IOException;

    protected abstract void setupEmptyFile() throws IOException;

    public abstract boolean traverseAllRecords(RecordsProcessor recordsProcessor) throws IOException;

    protected boolean trySelfHeal() {
        return false;
    }

    public static class Version {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final int correctlyClosedMagic;
        private final int dirtyMagic;

        public Version(int i) {
            this(i + 247118589, -1161946761);
        }

        private Version(int i, int i2) {
            this.correctlyClosedMagic = i;
            this.dirtyMagic = i2;
        }
    }

    static abstract class RecordBufferHandler<T extends PersistentEnumeratorBase<?>> {
        abstract byte[] getRecordBuffer(T t);

        abstract int recordWriteOffset(T t, byte[] bArr) throws IOException;

        abstract void setupRecord(T t, int i, int i2, byte[] bArr);

        RecordBufferHandler() {
        }
    }

    @Deprecated
    public static class CorruptedException extends org.jetbrains.kotlin.com.intellij.util.io.CorruptedException {
        public CorruptedException(Path path) {
            super("PersistentEnumerator storage corrupted " + path);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public PersistentEnumeratorBase(Path path, ResizeableMappedFile resizeableMappedFile, KeyDescriptor<Data> keyDescriptor, int i, Version version, RecordBufferHandler<? extends PersistentEnumeratorBase<?>> recordBufferHandler, boolean z) throws IOException {
        int i2;
        if (path == null) {
            $$$reportNull$$$0(0);
        }
        if (resizeableMappedFile == null) {
            $$$reportNull$$$0(1);
        }
        if (keyDescriptor == null) {
            $$$reportNull$$$0(2);
        }
        if (version == null) {
            $$$reportNull$$$0(3);
        }
        if (recordBufferHandler == 0) {
            $$$reportNull$$$0(4);
        }
        this.myLock = new ReentrantReadWriteLock(true);
        this.myDataDescriptor = keyDescriptor;
        this.myFile = path;
        this.myVersion = version;
        this.myRecordHandler = recordBufferHandler;
        this.myDoCaching = z;
        if (!Files.exists(path, new LinkOption[0])) {
            if (path.getFileSystem().isReadOnly()) {
                throw new IOException(path + " in " + path.getFileSystem() + " is not exist");
            }
            FileUtil.delete(keyStreamFile());
            if (!FileUtil.createIfDoesntExist(path.toFile())) {
                throw new IOException("Cannot create empty file: " + path);
            }
        }
        this.myStorage = resizeableMappedFile;
        lockStorageWrite();
        try {
            if (resizeableMappedFile.length() == 0) {
                try {
                    try {
                        try {
                            markDirty(true);
                            putMetaData(0L);
                            putMetaData2(0L);
                            setupEmptyFile();
                            doFlush();
                        } catch (RuntimeException e) {
                            LOG.info(e);
                            this.myStorage.close();
                            if (e.getCause() instanceof IOException) {
                                throw ((IOException) e.getCause());
                            }
                            throw e;
                        }
                    } catch (IOException e2) {
                        LOG.info(e2);
                        this.myStorage.close();
                        throw e2;
                    }
                } catch (Exception e3) {
                    LOG.info(e3);
                    this.myStorage.close();
                    throw new CorruptedException(path);
                }
            } else {
                try {
                    i2 = resizeableMappedFile.getInt(0L);
                } catch (Exception e4) {
                    LOG.info(e4);
                    i2 = this.myVersion.dirtyMagic;
                }
                if (i2 != this.myVersion.correctlyClosedMagic) {
                    this.myStorage.close();
                    if (i2 == this.myVersion.dirtyMagic) {
                        throw new CorruptedException(path);
                    }
                    throw new VersionUpdatedException(path);
                }
            }
            unlockStorageWrite();
            if (keyDescriptor instanceof InlineKeyDescriptor) {
                this.myKeyStorage = new InlinedKeyStorage((InlineKeyDescriptor) keyDescriptor);
                return;
            }
            try {
                this.myKeyStorage = new AppendableStorageBackedByResizableMappedFile(keyStreamFile(), i, this.myStorage.getStorageLockContext(), 1048576, false, keyDescriptor);
            } catch (Throwable th) {
                LOG.info(th);
                this.myStorage.close();
                throw new CorruptedException(path);
            }
        } catch (Throwable th2) {
            unlockStorageWrite();
            throw th2;
        }
    }

    protected Lock getWriteLock() {
        ReentrantReadWriteLock.WriteLock writeLock = this.myLock.writeLock();
        if (writeLock == null) {
            $$$reportNull$$$0(5);
        }
        return writeLock;
    }

    protected Lock getReadLock() {
        Lock lock = USE_RW_LOCK ? this.myLock.readLock() : this.myLock.writeLock();
        if (lock == null) {
            $$$reportNull$$$0(6);
        }
        return lock;
    }

    void lockStorageRead() {
        this.myStorage.lockRead();
    }

    void unlockStorageRead() {
        this.myStorage.unlockRead();
    }

    void lockStorageWrite() {
        this.myStorage.lockWrite();
    }

    void unlockStorageWrite() {
        this.myStorage.unlockWrite();
    }

    final RecordBufferHandler<PersistentEnumeratorBase<?>> getRecordHandler() {
        RecordBufferHandler<PersistentEnumeratorBase<?>> recordBufferHandler = this.myRecordHandler;
        if (recordBufferHandler == null) {
            $$$reportNull$$$0(7);
        }
        return recordBufferHandler;
    }

    public void setRecordHandler(RecordBufferHandler<PersistentEnumeratorBase<?>> recordBufferHandler) {
        if (recordBufferHandler == null) {
            $$$reportNull$$$0(8);
        }
        this.myRecordHandler = recordBufferHandler;
    }

    void setMarkCleanCallback(Flushable flushable) {
        if (flushable == null) {
            $$$reportNull$$$0(9);
        }
        this.myMarkCleanCallback = flushable;
    }

    public Data getValue(int i, int i2) throws IOException {
        return valueOf(i);
    }

    public int tryEnumerate(Data data) throws IOException {
        return doEnumerate(data, true, false);
    }

    private int doEnumerate(final Data data, final boolean z, final boolean z2) throws IOException {
        int cachedId;
        if (this.myDoCaching && !z2 && (cachedId = PersistentEnumeratorCache.getCachedId(data, this)) != 0) {
            return cachedId;
        }
        int iIntValue = ((Integer) catchCorruption(new ThrowableComputable() { // from class: org.jetbrains.kotlin.com.intellij.util.io.PersistentEnumeratorBase$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable
            public final Object compute() {
                return this.f$0.m6724xbeb62402(data, z, z2);
            }
        })).intValue();
        if (this.myDoCaching && iIntValue != 0) {
            PersistentEnumeratorCache.cacheId(data, iIntValue, this);
        }
        return iIntValue;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: renamed from: lambda$doEnumerate$0$org-jetbrains-kotlin-com-intellij-util-io-PersistentEnumeratorBase, reason: not valid java name */
    /* synthetic */ Integer m6724xbeb62402(Object obj, boolean z, boolean z2) throws IOException {
        return Integer.valueOf(enumerateImpl(obj, z, z2));
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.DataEnumerator
    public int enumerate(Data data) throws IOException {
        return doEnumerate(data, false, false);
    }

    protected void putMetaData(long j) throws IOException {
        lockStorageWrite();
        try {
            if (this.myStorage.length() < 12 || getMetaData() != j) {
                this.myStorage.putLong(4L, j);
            }
        } finally {
            unlockStorageWrite();
        }
    }

    protected long getMetaData() throws IOException {
        lockStorageRead();
        try {
            return this.myStorage.getLong(4L);
        } finally {
            unlockStorageRead();
        }
    }

    void putMetaData2(long j) throws IOException {
        lockStorageWrite();
        try {
            if (this.myStorage.length() < 20 || getMetaData2() != j) {
                this.myStorage.putLong(12L, j);
            }
        } finally {
            unlockStorageWrite();
        }
    }

    long getMetaData2() throws IOException {
        lockStorageRead();
        try {
            return this.myStorage.getLong(12L);
        } finally {
            unlockStorageRead();
        }
    }

    public boolean processAllDataObject(final Processor<? super Data> processor, final DataFilter dataFilter) throws IOException {
        if (processor == null) {
            $$$reportNull$$$0(10);
        }
        return traverseAllRecords(new RecordsProcessor() { // from class: org.jetbrains.kotlin.com.intellij.util.io.PersistentEnumeratorBase.1
            @Override // org.jetbrains.kotlin.com.intellij.util.io.PersistentEnumeratorBase.RecordsProcessor
            public boolean process(int i) throws IOException {
                DataFilter dataFilter2 = dataFilter;
                if (dataFilter2 == null || dataFilter2.accept(i)) {
                    return processor.process(PersistentEnumeratorBase.this.valueOf(i));
                }
                return true;
            }
        });
    }

    public static abstract class RecordsProcessor {
        private int myKey;

        public abstract boolean process(int i) throws IOException;

        void setCurrentKey(int i) {
            this.myKey = i;
        }

        int getCurrentKey() {
            return this.myKey;
        }
    }

    protected boolean isKeyAtIndex(Data data, int i) throws IOException {
        if (this.myKeyStorage instanceof InlinedKeyStorage) {
            return false;
        }
        if (this.myKeyStorage.checkBytesAreTheSame(indexToAddr(i), data)) {
            return true;
        }
        KeyDescriptor<Data> keyDescriptor = this.myDataDescriptor;
        if (keyDescriptor instanceof DifferentSerializableBytesImplyNonEqualityPolicy) {
            return false;
        }
        return keyDescriptor.isEqual(valueOf(i), data);
    }

    protected int writeData(Data data, int i) {
        try {
            markDirty(true);
            return setupValueId(i, doWriteData(data));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected int doWriteData(Data data) throws IOException {
        return this.myKeyStorage.append(data);
    }

    protected int setupValueId(int i, int i2) throws IOException {
        byte[] recordBuffer = this.myRecordHandler.getRecordBuffer(this);
        this.myRecordHandler.setupRecord(this, i, i2, recordBuffer);
        int iRecordWriteOffset = this.myRecordHandler.recordWriteOffset(this, recordBuffer);
        this.myStorage.put(iRecordWriteOffset, recordBuffer, 0, recordBuffer.length);
        return iRecordWriteOffset;
    }

    public boolean iterateData(final Processor<? super Data> processor) throws IOException {
        if (processor == null) {
            $$$reportNull$$$0(12);
        }
        return doIterateData(new AppendableObjectStorage.StorageObjectProcessor() { // from class: org.jetbrains.kotlin.com.intellij.util.io.PersistentEnumeratorBase$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.util.io.keyStorage.AppendableObjectStorage.StorageObjectProcessor
            public final boolean process(int i, Object obj) {
                return processor.process(obj);
            }
        });
    }

    protected boolean doIterateData(AppendableObjectStorage.StorageObjectProcessor<? super Data> storageObjectProcessor) throws IOException {
        if (storageObjectProcessor == null) {
            $$$reportNull$$$0(13);
        }
        lockStorageWrite();
        try {
            this.myKeyStorage.m6720xd71ec63f();
            return this.myKeyStorage.processAll(storageObjectProcessor);
        } finally {
            unlockStorageWrite();
        }
    }

    private Path keyStreamFile() {
        return this.myFile.resolveSibling(this.myFile.getFileName() + ".keystream");
    }

    public Data valueOf(final int i) throws IOException {
        if (i <= 0) {
            return null;
        }
        return (Data) catchCorruption(new ThrowableComputable() { // from class: org.jetbrains.kotlin.com.intellij.util.io.PersistentEnumeratorBase$$ExternalSyntheticLambda2
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable
            public final Object compute() {
                return this.f$0.m6725x1a35ff91(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: findValueFor, reason: merged with bridge method [inline-methods] */
    public Data m6725x1a35ff91(int i) throws IOException {
        lockStorageRead();
        try {
            return this.myKeyStorage.read(indexToAddr(i));
        } finally {
            unlockStorageRead();
        }
    }

    int reEnumerate(Data data) throws IOException {
        if (!canReEnumerate()) {
            throw new IncorrectOperationException();
        }
        return doEnumerate(data, false, true);
    }

    public void close() throws IOException {
        getWriteLock().lock();
        try {
            lockStorageWrite();
            try {
                if (!this.myClosed) {
                    this.myClosed = true;
                    doClose();
                }
                unlockStorageWrite();
                getWriteLock().unlock();
            } catch (Throwable th) {
                unlockStorageWrite();
                throw th;
            }
        } catch (Throwable th2) {
            getWriteLock().unlock();
            throw th2;
        }
    }

    protected void doClose() throws IOException {
        IOCancellationCallbackHolder.interactWithUI();
        try {
            m6720xd71ec63f();
            this.myKeyStorage.close();
        } finally {
            this.myStorage.close();
        }
    }

    public boolean isClosed() {
        getReadLock().lock();
        try {
            return this.myClosed;
        } finally {
            getReadLock().unlock();
        }
    }

    public boolean isDirty() {
        lockStorageRead();
        try {
            return this.myDirty;
        } finally {
            unlockStorageRead();
        }
    }

    public boolean isCorrupted() {
        lockStorageRead();
        try {
            return this.myCorrupted;
        } finally {
            unlockStorageRead();
        }
    }

    protected void doFlush() throws IOException {
        markDirty(false);
        this.myStorage.m6720xd71ec63f();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.Forceable
    /* JADX INFO: renamed from: force */
    public void m6720xd71ec63f() {
        getWriteLock().lock();
        try {
            lockStorageWrite();
            try {
                try {
                    this.myKeyStorage.m6720xd71ec63f();
                    if (this.myStorage.isDirty() || isDirty()) {
                        doFlush();
                    }
                    unlockStorageWrite();
                    getWriteLock().unlock();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } catch (Throwable th) {
                unlockStorageWrite();
                throw th;
            }
        } catch (Throwable th2) {
            getWriteLock().unlock();
            throw th2;
        }
    }

    protected final void markDirty(boolean z) throws IOException {
        if (z && this.myDirty && !this.myDirtyStatusUpdateInProgress) {
            return;
        }
        lockStorageWrite();
        try {
            if (this.myDirty) {
                if (!z) {
                    this.myDirtyStatusUpdateInProgress = true;
                    Flushable flushable = this.myMarkCleanCallback;
                    if (flushable != null) {
                        flushable.flush();
                    }
                    if (!this.myCorrupted) {
                        this.myStorage.putInt(0L, this.myVersion.correctlyClosedMagic);
                        this.myDirty = false;
                    }
                    this.myDirtyStatusUpdateInProgress = false;
                }
            } else if (z) {
                this.myDirtyStatusUpdateInProgress = true;
                this.myStorage.putInt(0L, this.myVersion.dirtyMagic);
                this.myDirtyStatusUpdateInProgress = false;
                this.myDirty = true;
            }
        } finally {
            unlockStorageWrite();
        }
    }

    protected void markCorrupted() {
        lockStorageWrite();
        try {
            if (!this.myCorrupted) {
                this.myCorrupted = true;
                Logger logger = LOG;
                if (logger.isDebugEnabled()) {
                    logger.debug("Marking corrupted:" + this.myFile, new Throwable());
                }
                try {
                    markDirty(true);
                    m6720xd71ec63f();
                } catch (IOException unused) {
                }
            }
        } finally {
            unlockStorageWrite();
        }
    }

    protected <V> V catchCorruption(ThrowableComputable<V, IOException> throwableComputable) throws IOException {
        if (isCorrupted()) {
            throw new CorruptedException(this.myFile);
        }
        try {
            return throwableComputable.compute();
        } catch (Throwable th) {
            try {
                if ((th instanceof NoDataException) || !trySelfHeal()) {
                    throw th;
                }
                return throwableComputable.compute();
            } catch (NoDataException unused) {
                return null;
            } catch (IOException e) {
                markCorrupted();
                throw e;
            } catch (Throwable th2) {
                markCorrupted();
                throw new RuntimeException(th2);
            }
        }
    }
}
