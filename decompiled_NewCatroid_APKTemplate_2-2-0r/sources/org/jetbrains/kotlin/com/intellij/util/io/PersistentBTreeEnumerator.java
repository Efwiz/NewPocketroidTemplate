package org.jetbrains.kotlin.com.intellij.util.io;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtilRt;
import org.jetbrains.kotlin.com.intellij.util.ConcurrencyUtil;
import org.jetbrains.kotlin.com.intellij.util.Processor;
import org.jetbrains.kotlin.com.intellij.util.SystemProperties;
import org.jetbrains.kotlin.com.intellij.util.io.keyStorage.AppendableObjectStorage;

/* JADX INFO: loaded from: classes3.dex */
public class PersistentBTreeEnumerator<Data> extends PersistentEnumeratorBase<Data> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int BTREE_PAGE_SIZE;
    private static final boolean DO_EXPENSIVE_CHECKS = SystemProperties.getBooleanProperty("idea.persistent.enumerator.do.expensive.checks", false);
    protected static final int VERSION;
    private IntToIntBtree myBTree;
    private int myCollisions;
    private int myDataPageOffset;
    private int myDataPageStart;
    private int myDuplicatedValuesPageOffset;
    private int myDuplicatedValuesPageStart;
    private int myExistingKeysEnumerated;
    private boolean myExternalKeysNoMapping;
    private int myFirstPageStart;
    private final boolean myInlineKeysNoMapping;
    private int myLogicalFileLength;
    private final int[] myResultBuf;
    private int myValuesCount;
    private final PersistentEnumeratorWal<Data> myWal;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 9 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 9 ? 3 : 2];
        if (i != 1 && i != 3 && i != 5 && i != 7) {
            switch (i) {
                case 9:
                    objArr[0] = "org/jetbrains/kotlin/com/intellij/util/io/PersistentBTreeEnumerator";
                    break;
                case 10:
                    objArr[0] = "processor";
                    break;
                case 11:
                    objArr[0] = "p";
                    break;
                case 12:
                    objArr[0] = "recordHandler";
                    break;
                default:
                    objArr[0] = "file";
                    break;
            }
        } else {
            objArr[0] = "dataDescriptor";
        }
        if (i != 9) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/io/PersistentBTreeEnumerator";
        } else {
            objArr[1] = "indexFile";
        }
        switch (i) {
            case 8:
                objArr[2] = "indexFile";
                break;
            case 9:
                break;
            case 10:
                objArr[2] = "processAllDataObject";
                break;
            case 11:
                objArr[2] = "traverseAllRecords";
                break;
            case 12:
                objArr[2] = "setRecordHandler";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i == 9) {
            throw new IllegalStateException(str2);
        }
    }

    protected int addrToIndex(int i) {
        return i + 1;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.PersistentEnumeratorBase
    boolean canReEnumerate() {
        return true;
    }

    static /* synthetic */ int access$420(PersistentBTreeEnumerator persistentBTreeEnumerator, int i) {
        int i2 = persistentBTreeEnumerator.myDataPageStart - i;
        persistentBTreeEnumerator.myDataPageStart = i2;
        return i2;
    }

    static /* synthetic */ int access$612(PersistentBTreeEnumerator persistentBTreeEnumerator, int i) {
        int i2 = persistentBTreeEnumerator.myDataPageOffset + i;
        persistentBTreeEnumerator.myDataPageOffset = i2;
        return i2;
    }

    static {
        int intProperty = SystemProperties.getIntProperty("idea.btree.page.size", 32768);
        BTREE_PAGE_SIZE = intProperty;
        VERSION = IntToIntBtree.version() + 8 + intProperty + 4224;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public PersistentBTreeEnumerator(Path path, KeyDescriptor<Data> keyDescriptor, int i, StorageLockContext storageLockContext, int i2, boolean z) throws IOException {
        if (path == null) {
            $$$reportNull$$$0(6);
        }
        if (keyDescriptor == null) {
            $$$reportNull$$$0(7);
        }
        AnonymousClass1 anonymousClass1 = (PersistentEnumeratorWal<Data>) null;
        super(path, new ResizeableMappedFile(path, i, storageLockContext, 1048576, true, IOUtil.BYTE_BUFFERS_USE_NATIVE_BYTE_ORDER), keyDescriptor, i, new PersistentEnumeratorBase.Version(VERSION + i2), new RecordBufferHandler(), false);
        this.myResultBuf = new int[1];
        boolean z2 = keyDescriptor instanceof InlineKeyDescriptor;
        this.myInlineKeysNoMapping = z2;
        this.myExternalKeysNoMapping = true ^ z2;
        try {
            if (this.myBTree == null) {
                try {
                    try {
                        lockStorageWrite();
                        storeVars(false);
                        initBtree(false);
                        storeBTreeVars(false);
                        unlockStorageWrite();
                    } catch (Throwable th) {
                        LOG.info(th);
                        try {
                            close();
                        } catch (Throwable unused) {
                        }
                        throw new PersistentEnumeratorBase.CorruptedException(path);
                    }
                } catch (IOException e) {
                    try {
                        close();
                    } catch (Throwable unused2) {
                    }
                    throw e;
                }
            }
            diagnose();
            this.myWal = (PersistentEnumeratorWal<Data>) (z ? (PersistentEnumeratorWal<Data>) new PersistentEnumeratorWal(keyDescriptor, false, path.resolveSibling(path.getFileName() + ".wal"), ConcurrencyUtil.newSameThreadExecutorService(), true) : anonymousClass1);
        } catch (Throwable th2) {
            unlockStorageWrite();
            throw th2;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void doExpensiveSanityCheck() {
        try {
            LOG.info("Doing self diagnostic for " + this.myFile);
            final ArrayList arrayList = new ArrayList();
            iterateData(new Processor() { // from class: org.jetbrains.kotlin.com.intellij.util.io.PersistentBTreeEnumerator$$ExternalSyntheticLambda1
                @Override // org.jetbrains.kotlin.com.intellij.util.Processor
                public final boolean process(Object obj) {
                    return PersistentBTreeEnumerator.lambda$doExpensiveSanityCheck$0(arrayList, obj);
                }
            });
            for (int i = 0; i < arrayList.size(); i++) {
                try {
                    Object obj = arrayList.get(i);
                    int i2 = i + 1;
                    if (tryEnumerate(obj) != i2) {
                        throw new IOException(this.myFile + " is corrupted");
                    }
                    if (!this.myDataDescriptor.isEqual((Data) valueOf(i2), (Data) obj)) {
                        throw new IOException(this.myFile + " is corrupted");
                    }
                } catch (Exception e) {
                    LOG.error((Throwable) e);
                }
            }
        } catch (Throwable th) {
            LOG.error(th);
        }
    }

    static /* synthetic */ boolean lambda$doExpensiveSanityCheck$0(List list, Object obj) {
        list.add(obj);
        return true;
    }

    public void diagnose() {
        if (!DO_EXPENSIVE_CHECKS || this.myInlineKeysNoMapping) {
            return;
        }
        doExpensiveSanityCheck();
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.PersistentEnumeratorBase
    protected boolean trySelfHeal() {
        if (!SystemProperties.getBooleanProperty("idea.persistent.enumerator.do.self.heal", false)) {
            return false;
        }
        LOG.info("Trying to self-heal " + this.myFile);
        final ArrayList<C1DataWithOffset> arrayList = new ArrayList();
        try {
            doIterateData(new AppendableObjectStorage.StorageObjectProcessor() { // from class: org.jetbrains.kotlin.com.intellij.util.io.PersistentBTreeEnumerator$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.util.io.keyStorage.AppendableObjectStorage.StorageObjectProcessor
                public final boolean process(int i, Object obj) {
                    return this.f$0.m6723x2ae2106e(arrayList, i, obj);
                }
            });
            lockStorageWrite();
            try {
                this.myStorage.clear();
                this.myKeyStorage.clear();
                this.myStorage.ensureSize(4096L);
                markDirty(true);
                putMetaData(0L);
                putMetaData2(0L);
                IntToIntBtree intToIntBtree = this.myBTree;
                if (intToIntBtree != null) {
                    intToIntBtree.doClose();
                }
                setupEmptyFile();
                doFlush();
                unlockStorageWrite();
                for (C1DataWithOffset c1DataWithOffset : arrayList) {
                    int iEnumerate = enumerate(c1DataWithOffset.data);
                    int i = c1DataWithOffset.offset + 1;
                    if (i != iEnumerate) {
                        throw new IOException("Enumeration order has been changed while self-healing, were " + i + " now " + iEnumerate);
                    }
                }
                return true;
            } catch (Throwable th) {
                unlockStorageWrite();
                throw th;
            }
        } catch (Throwable th2) {
            LOG.info(th2);
            return false;
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.com.intellij.util.io.PersistentBTreeEnumerator$1DataWithOffset, reason: invalid class name */
    class C1DataWithOffset {
        final Data data;
        final int offset;

        C1DataWithOffset(Data data, int i) {
            this.data = data;
            this.offset = i;
        }
    }

    /* JADX INFO: renamed from: lambda$trySelfHeal$1$org-jetbrains-kotlin-com-intellij-util-io-PersistentBTreeEnumerator, reason: not valid java name */
    /* synthetic */ boolean m6723x2ae2106e(List list, int i, Object obj) {
        list.add(new C1DataWithOffset(obj, i));
        return true;
    }

    private static Path indexFile(Path path) {
        if (path == null) {
            $$$reportNull$$$0(8);
        }
        Path pathResolveSibling = path.resolveSibling(path.getFileName() + "_i");
        if (pathResolveSibling == null) {
            $$$reportNull$$$0(9);
        }
        return pathResolveSibling;
    }

    private void initBtree(boolean z) throws IOException {
        this.myBTree = new IntToIntBtree(BTREE_PAGE_SIZE, indexFile(this.myFile), this.myStorage.getStorageLockContext(), z);
    }

    private void storeVars(boolean z) throws IOException {
        this.myLogicalFileLength = store(20, this.myLogicalFileLength, z);
        this.myDataPageStart = store(24, this.myDataPageStart, z);
        this.myDataPageOffset = store(28, this.myDataPageOffset, z);
        this.myFirstPageStart = store(32, this.myFirstPageStart, z);
        this.myDuplicatedValuesPageStart = store(36, this.myDuplicatedValuesPageStart, z);
        this.myDuplicatedValuesPageOffset = store(40, this.myDuplicatedValuesPageOffset, z);
        this.myValuesCount = store(44, this.myValuesCount, z);
        this.myCollisions = store(48, this.myCollisions, z);
        this.myExistingKeysEnumerated = store(52, this.myExistingKeysEnumerated, z);
        storeBTreeVars(z);
    }

    private void storeBTreeVars(boolean z) throws IOException {
        IntToIntBtree intToIntBtree = this.myBTree;
        if (intToIntBtree != null) {
            intToIntBtree.persistVars(new IntToIntBtree.BtreeDataStorage() { // from class: org.jetbrains.kotlin.com.intellij.util.io.PersistentBTreeEnumerator.1
                @Override // org.jetbrains.kotlin.com.intellij.util.io.IntToIntBtree.BtreeDataStorage
                public int persistInt(int i, int i2, boolean z2) throws IOException {
                    return PersistentBTreeEnumerator.this.store(i + 56, i2, z2);
                }
            }, z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int store(int i, int i2, boolean z) throws IOException {
        if (z) {
            if (this.myFirstPageStart != -1 && this.myStorage.getInt(i) == i2) {
                return i2;
            }
            this.myStorage.putInt(i, i2);
            return i2;
        }
        return this.myStorage.getInt(i);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.PersistentEnumeratorBase
    protected void setupEmptyFile() throws IOException {
        this.myLogicalFileLength = 128;
        this.myDataPageStart = -1;
        this.myFirstPageStart = -1;
        this.myDuplicatedValuesPageStart = -1;
        initBtree(true);
        storeVars(true);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.PersistentEnumeratorBase
    protected void doClose() throws IOException {
        try {
            super.doClose();
        } finally {
            IntToIntBtree intToIntBtree = this.myBTree;
            if (intToIntBtree != null) {
                intToIntBtree.doClose();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int allocPage() {
        int i = this.myLogicalFileLength;
        this.myLogicalFileLength = (4096 - (i % 4096)) + i;
        return i;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.PersistentEnumeratorBase
    public boolean processAllDataObject(final Processor<? super Data> processor, final PersistentEnumeratorBase.DataFilter dataFilter) throws IOException {
        if (processor == null) {
            $$$reportNull$$$0(10);
        }
        if (this.myInlineKeysNoMapping) {
            return traverseAllRecords(new PersistentEnumeratorBase.RecordsProcessor() { // from class: org.jetbrains.kotlin.com.intellij.util.io.PersistentBTreeEnumerator.2
                @Override // org.jetbrains.kotlin.com.intellij.util.io.PersistentEnumeratorBase.RecordsProcessor
                public boolean process(int i) throws IOException {
                    PersistentEnumeratorBase.DataFilter dataFilter2 = dataFilter;
                    if (dataFilter2 != null && !dataFilter2.accept(i)) {
                        return true;
                    }
                    return processor.process(((InlineKeyDescriptor) PersistentBTreeEnumerator.this.myDataDescriptor).fromInt(getCurrentKey()));
                }
            });
        }
        return super.processAllDataObject(processor, dataFilter);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.PersistentEnumeratorBase
    public boolean traverseAllRecords(final PersistentEnumeratorBase.RecordsProcessor recordsProcessor) throws IOException {
        if (recordsProcessor == null) {
            $$$reportNull$$$0(11);
        }
        getReadLock().lock();
        try {
            lockStorageRead();
            try {
                boolean zProcessMappings = this.myBTree.processMappings(new IntToIntBtree.KeyValueProcessor() { // from class: org.jetbrains.kotlin.com.intellij.util.io.PersistentBTreeEnumerator.3
                    @Override // org.jetbrains.kotlin.com.intellij.util.io.IntToIntBtree.KeyValueProcessor
                    public boolean process(int i, int i2) throws IOException {
                        recordsProcessor.setCurrentKey(i);
                        if (i2 > 0 || PersistentBTreeEnumerator.this.myInlineKeysNoMapping) {
                            return recordsProcessor.process(i2);
                        }
                        int i3 = -i2;
                        while (i3 != 0) {
                            if (!recordsProcessor.process(PersistentBTreeEnumerator.this.myStorage.getInt(i3))) {
                                return false;
                            }
                            i3 = PersistentBTreeEnumerator.this.myStorage.getInt(i3 + 4);
                        }
                        return true;
                    }
                });
                unlockStorageRead();
                getReadLock().unlock();
                return zProcessMappings;
            } catch (Throwable th) {
                unlockStorageRead();
                throw th;
            }
        } catch (Throwable th2) {
            getReadLock().unlock();
            throw th2;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.PersistentEnumeratorBase
    protected int indexToAddr(int i) throws IOException {
        if (this.myExternalKeysNoMapping) {
            IntToIntBtree.myAssert(i > 0);
            return i - 1;
        }
        return this.myStorage.getInt(i);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.PersistentEnumeratorBase
    protected int setupValueId(int i, int i2) throws IOException {
        if (this.myExternalKeysNoMapping) {
            return addrToIndex(i2);
        }
        PersistentEnumeratorBase.RecordBufferHandler<PersistentEnumeratorBase<?>> recordHandler = getRecordHandler();
        byte[] recordBuffer = recordHandler.getRecordBuffer(this);
        int iRecordWriteOffset = recordHandler.recordWriteOffset(this, recordBuffer);
        this.myStorage.ensureSize(recordBuffer.length + iRecordWriteOffset);
        if (!this.myInlineKeysNoMapping) {
            this.myStorage.putInt(iRecordWriteOffset, i2);
        }
        return iRecordWriteOffset;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.PersistentEnumeratorBase
    public void setRecordHandler(PersistentEnumeratorBase.RecordBufferHandler<PersistentEnumeratorBase<?>> recordBufferHandler) {
        if (recordBufferHandler == null) {
            $$$reportNull$$$0(12);
        }
        this.myExternalKeysNoMapping = false;
        super.setRecordHandler(recordBufferHandler);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.PersistentEnumeratorBase
    public Data getValue(int i, int i2) throws IOException {
        if (this.myInlineKeysNoMapping) {
            return (Data) ((InlineKeyDescriptor) this.myDataDescriptor).fromInt(i2);
        }
        return (Data) super.getValue(i, i2);
    }

    long getNonNegativeValue(Data data) throws IOException {
        getReadLock().lock();
        try {
            try {
                lockStorageRead();
                if (this.myBTree.get(((InlineKeyDescriptor) this.myDataDescriptor).toInt(data), this.myResultBuf)) {
                    long jKeyIdToNonNegativeOffset = keyIdToNonNegativeOffset(this.myResultBuf[0]);
                    unlockStorageRead();
                    getReadLock().unlock();
                    return jKeyIdToNonNegativeOffset;
                }
                unlockStorageRead();
                getReadLock().unlock();
                return 0L;
            } catch (Throwable th) {
                unlockStorageRead();
                throw th;
            }
        } catch (Throwable th2) {
            getReadLock().unlock();
            throw th2;
        }
    }

    long keyIdToNonNegativeOffset(int i) throws IOException {
        return i >= 0 ? i : this.myStorage.getLong(-i);
    }

    void putNonNegativeValue(Data data, long j) throws IOException {
        getWriteLock().lock();
        try {
            try {
                lockStorageWrite();
                int i = ((InlineKeyDescriptor) this.myDataDescriptor).toInt(data);
                markDirty(true);
                if (j < 2147483647L) {
                    this.myBTree.put(i, (int) j);
                } else if (this.myBTree.get(i, this.myResultBuf) && this.myResultBuf[0] < 0) {
                    this.myStorage.putLong(-this.myResultBuf[0], j);
                    unlockStorageWrite();
                    getWriteLock().unlock();
                    return;
                } else {
                    int iNextLongValueRecord = nextLongValueRecord();
                    this.myStorage.putLong(iNextLongValueRecord, j);
                    this.myBTree.put(i, -iNextLongValueRecord);
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

    private int nextLongValueRecord() {
        if (this.myDuplicatedValuesPageStart == -1 || this.myDuplicatedValuesPageOffset == 4096) {
            int iAllocPage = allocPage();
            this.myDuplicatedValuesPageStart = iAllocPage;
            int i = iAllocPage % 4096;
            this.myDuplicatedValuesPageOffset = i;
            this.myDuplicatedValuesPageStart = iAllocPage - i;
        }
        int i2 = this.myDuplicatedValuesPageOffset;
        this.myDuplicatedValuesPageOffset = i2 + 8;
        return this.myDuplicatedValuesPageStart + i2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.PersistentEnumeratorBase
    protected int enumerateImpl(Data data, boolean z, boolean z2) throws IOException {
        boolean z3;
        int iNextDuplicatedValueRecord;
        (z ? getReadLock() : getWriteLock()).lock();
        try {
            if (z) {
                lockStorageRead();
            } else {
                lockStorageWrite();
            }
            try {
                int hashCode = this.myDataDescriptor.getHashCode(data);
                boolean z4 = this.myBTree.get(hashCode, this.myResultBuf);
                if (!z4 && z) {
                    if (z) {
                        unlockStorageRead();
                    } else {
                        unlockStorageWrite();
                    }
                    (z ? getReadLock() : getWriteLock()).unlock();
                    return 0;
                }
                int i = z4 ? this.myResultBuf[0] : 0;
                if (!this.myInlineKeysNoMapping) {
                    if (i > 0) {
                        if (!isKeyAtIndex(data, i)) {
                            z3 = false;
                        } else {
                            if (!z2) {
                                this.myExistingKeysEnumerated++;
                                if (z) {
                                    unlockStorageRead();
                                } else {
                                    unlockStorageWrite();
                                }
                                (z ? getReadLock() : getWriteLock()).unlock();
                                return i;
                            }
                            z3 = true;
                        }
                        iNextDuplicatedValueRecord = i;
                    } else if (i < 0) {
                        iNextDuplicatedValueRecord = -i;
                        while (true) {
                            int i2 = this.myStorage.getInt(iNextDuplicatedValueRecord);
                            if (isKeyAtIndex(data, i2)) {
                                if (z2) {
                                    z3 = true;
                                    break;
                                }
                                if (z) {
                                    unlockStorageRead();
                                } else {
                                    unlockStorageWrite();
                                }
                                (z ? getReadLock() : getWriteLock()).unlock();
                                return i2;
                            }
                            int i3 = this.myStorage.getInt(iNextDuplicatedValueRecord + 4);
                            if (i3 == 0) {
                                z3 = false;
                                break;
                            }
                            iNextDuplicatedValueRecord = i3;
                        }
                    } else {
                        z3 = false;
                        iNextDuplicatedValueRecord = 0;
                    }
                    if (z) {
                        if (z) {
                            unlockStorageRead();
                        } else {
                            unlockStorageWrite();
                        }
                        (z ? getReadLock() : getWriteLock()).unlock();
                        return 0;
                    }
                } else if (!z4) {
                    z3 = false;
                    iNextDuplicatedValueRecord = 0;
                } else {
                    if (!z2) {
                        if (z) {
                            unlockStorageRead();
                        } else {
                            unlockStorageWrite();
                        }
                        (z ? getReadLock() : getWriteLock()).unlock();
                        return i;
                    }
                    iNextDuplicatedValueRecord = 0;
                    z3 = true;
                }
                int iWriteData = writeData(data, hashCode);
                this.myValuesCount++;
                PersistentEnumeratorWal<Data> persistentEnumeratorWal = this.myWal;
                if (persistentEnumeratorWal != null) {
                    persistentEnumeratorWal.enumerate(data, iWriteData);
                }
                if (IOStatistics.DEBUG && (this.myValuesCount & 65535) == 0) {
                    IOStatistics.dump("Index " + this.myFile + ", values " + this.myValuesCount + ", existing keys enumerated:" + this.myExistingKeysEnumerated + ", storage size:" + this.myStorage.length());
                    this.myBTree.dumpStatistics();
                }
                if (iNextDuplicatedValueRecord == 0) {
                    this.myBTree.put(hashCode, iWriteData);
                } else if (!z3) {
                    if (i > 0) {
                        iNextDuplicatedValueRecord = nextDuplicatedValueRecord();
                        this.myBTree.put(hashCode, -iNextDuplicatedValueRecord);
                        this.myStorage.putInt(iNextDuplicatedValueRecord, i);
                        this.myCollisions++;
                    }
                    this.myCollisions++;
                    int iNextDuplicatedValueRecord2 = nextDuplicatedValueRecord();
                    this.myStorage.putInt(iNextDuplicatedValueRecord + 4, iNextDuplicatedValueRecord2);
                    this.myStorage.putInt(iNextDuplicatedValueRecord2, iWriteData);
                    this.myStorage.putInt(iNextDuplicatedValueRecord2 + 4, 0);
                } else if (i > 0) {
                    this.myBTree.put(hashCode, iWriteData);
                } else {
                    this.myStorage.putInt(iNextDuplicatedValueRecord, iWriteData);
                }
                if (z) {
                    unlockStorageRead();
                } else {
                    unlockStorageWrite();
                }
                (z ? getReadLock() : getWriteLock()).unlock();
                return iWriteData;
            } catch (Throwable th) {
                if (z) {
                    unlockStorageRead();
                } else {
                    unlockStorageWrite();
                }
                throw th;
            }
        } catch (Throwable th2) {
            (z ? getReadLock() : getWriteLock()).unlock();
            throw th2;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.PersistentEnumeratorBase, org.jetbrains.kotlin.com.intellij.openapi.Forceable
    /* JADX INFO: renamed from: force */
    public void m6720xd71ec63f() throws IOException {
        try {
            super.m6720xd71ec63f();
            if (this.myWal != null) {
            }
        } finally {
            if (this.myWal != null) {
                this.myWal.flush();
            }
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.PersistentEnumeratorBase, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        try {
            super.close();
            if (this.myWal != null) {
            }
        } finally {
            if (this.myWal != null) {
                this.myWal.close();
            }
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.PersistentEnumeratorBase, org.jetbrains.kotlin.com.intellij.util.io.DataEnumerator
    public Data valueOf(int i) throws IOException {
        return (Data) super.valueOf(i);
    }

    private int nextDuplicatedValueRecord() {
        if (this.myDuplicatedValuesPageStart == -1 || this.myDuplicatedValuesPageOffset == 4096) {
            int iAllocPage = allocPage();
            this.myDuplicatedValuesPageStart = iAllocPage;
            int i = iAllocPage % 4096;
            this.myDuplicatedValuesPageOffset = i;
            this.myDuplicatedValuesPageStart = iAllocPage - i;
        }
        int i2 = this.myDuplicatedValuesPageOffset;
        this.myDuplicatedValuesPageOffset = i2 + 8;
        return this.myDuplicatedValuesPageStart + i2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.PersistentEnumeratorBase
    protected void doFlush() throws IOException {
        this.myBTree.doFlush();
        storeVars(true);
        super.doFlush();
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class RecordBufferHandler extends PersistentEnumeratorBase.RecordBufferHandler<PersistentBTreeEnumerator<?>> {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private ThreadLocal<byte[]> myBuffer;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 3 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 3 ? 3 : 2];
            if (i == 1) {
                objArr[0] = "buf";
            } else if (i != 3) {
                objArr[0] = "enumerator";
            } else {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/io/PersistentBTreeEnumerator$RecordBufferHandler";
            }
            if (i != 3) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/util/io/PersistentBTreeEnumerator$RecordBufferHandler";
            } else {
                objArr[1] = "getRecordBuffer";
            }
            if (i == 2) {
                objArr[2] = "getRecordBuffer";
            } else if (i != 3) {
                if (i != 4) {
                    objArr[2] = "recordWriteOffset";
                } else {
                    objArr[2] = "setupRecord";
                }
            }
            String str2 = String.format(str, objArr);
            if (i == 3) {
                throw new IllegalStateException(str2);
            }
        }

        private RecordBufferHandler() {
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jetbrains.kotlin.com.intellij.util.io.PersistentEnumeratorBase.RecordBufferHandler
        public int recordWriteOffset(PersistentBTreeEnumerator<?> persistentBTreeEnumerator, byte[] bArr) throws IOException {
            if (persistentBTreeEnumerator == null) {
                $$$reportNull$$$0(0);
            }
            if (bArr == null) {
                $$$reportNull$$$0(1);
            }
            if (((PersistentBTreeEnumerator) persistentBTreeEnumerator).myFirstPageStart == -1) {
                ((PersistentBTreeEnumerator) persistentBTreeEnumerator).myFirstPageStart = ((PersistentBTreeEnumerator) persistentBTreeEnumerator).myDataPageStart = persistentBTreeEnumerator.allocPage();
                int i = ((PersistentBTreeEnumerator) persistentBTreeEnumerator).myDataPageStart % 4096;
                ((PersistentBTreeEnumerator) persistentBTreeEnumerator).myDataPageOffset = i;
                PersistentBTreeEnumerator.access$420(persistentBTreeEnumerator, i);
            }
            if (((PersistentBTreeEnumerator) persistentBTreeEnumerator).myDataPageOffset + bArr.length + 4 > 4096) {
                int i2 = ((PersistentBTreeEnumerator) persistentBTreeEnumerator).myDataPageStart + 4092;
                ((PersistentBTreeEnumerator) persistentBTreeEnumerator).myDataPageStart = persistentBTreeEnumerator.allocPage();
                persistentBTreeEnumerator.myStorage.putInt(i2, ((PersistentBTreeEnumerator) persistentBTreeEnumerator).myDataPageStart);
                ((PersistentBTreeEnumerator) persistentBTreeEnumerator).myDataPageOffset = 0;
            }
            int i3 = ((PersistentBTreeEnumerator) persistentBTreeEnumerator).myDataPageOffset;
            PersistentBTreeEnumerator.access$612(persistentBTreeEnumerator, bArr.length);
            return i3 + ((PersistentBTreeEnumerator) persistentBTreeEnumerator).myDataPageStart;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jetbrains.kotlin.com.intellij.util.io.PersistentEnumeratorBase.RecordBufferHandler
        public byte[] getRecordBuffer(final PersistentBTreeEnumerator<?> persistentBTreeEnumerator) {
            if (persistentBTreeEnumerator == null) {
                $$$reportNull$$$0(2);
            }
            if (this.myBuffer == null) {
                this.myBuffer = ThreadLocal.withInitial(new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.util.io.PersistentBTreeEnumerator$RecordBufferHandler$$ExternalSyntheticLambda0
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return PersistentBTreeEnumerator.RecordBufferHandler.lambda$getRecordBuffer$0(persistentBTreeEnumerator);
                    }
                });
            }
            byte[] bArr = this.myBuffer.get();
            if (bArr == null) {
                $$$reportNull$$$0(3);
            }
            return bArr;
        }

        static /* synthetic */ byte[] lambda$getRecordBuffer$0(PersistentBTreeEnumerator persistentBTreeEnumerator) {
            return persistentBTreeEnumerator.myInlineKeysNoMapping ? ArrayUtilRt.EMPTY_BYTE_ARRAY : new byte[4];
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.jetbrains.kotlin.com.intellij.util.io.PersistentEnumeratorBase.RecordBufferHandler
        public void setupRecord(PersistentBTreeEnumerator<?> persistentBTreeEnumerator, int i, int i2, byte[] bArr) {
            if (persistentBTreeEnumerator == null) {
                $$$reportNull$$$0(4);
            }
            if (((PersistentBTreeEnumerator) persistentBTreeEnumerator).myInlineKeysNoMapping) {
                return;
            }
            Bits.putInt(bArr, 0, i2);
        }
    }
}
