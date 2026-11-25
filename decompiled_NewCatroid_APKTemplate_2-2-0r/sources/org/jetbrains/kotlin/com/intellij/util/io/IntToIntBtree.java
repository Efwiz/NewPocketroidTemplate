package org.jetbrains.kotlin.com.intellij.util.io;

import androidx.core.view.ViewCompat;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.function.IntUnaryOperator;
import org.jetbrains.kotlin.com.intellij.util.BitUtil;
import org.jetbrains.kotlin.com.intellij.util.ObjectUtils;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2IntMap;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;

/* JADX INFO: loaded from: classes3.dex */
public final class IntToIntBtree {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private int count;
    private boolean hasZeroKey;
    final int hashPageCapacity;
    private int hashSearchRequests;
    private int hashedPagesCount;
    private int height;
    private final short maxInteriorNodes;
    private final short maxLeafNodes;
    private final short maxLeafNodesInHash;
    private int maxStepsSearchedInHash;
    final int metaDataLeafPageLength;
    private int movedMembersCount;
    private BtreeIndexNodeView myAccessNodeView;
    private Int2IntMap myCachedMappings;
    private final int myCachedMappingsSize;
    private boolean myCanUseLastKey;
    private int myLastGetKey;
    private int myOptimizedInserts;
    final int pageSize;
    private int pagesCount;
    final BtreeRootNode root;
    private final ResizeableMappedFile storage;
    private int totalHashStepsSearched;
    private int zeroKeyValue;

    public interface BtreeDataStorage {
        int persistInt(int i, int i2, boolean z) throws IOException;
    }

    public static abstract class KeyValueProcessor {
        public abstract boolean process(int i, int i2) throws IOException;
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
                objArr[0] = "storageLockContext";
                break;
            case 2:
                objArr[0] = "storage";
                break;
            case 3:
                objArr[0] = "result";
                break;
            case 4:
            case 5:
                objArr[0] = "processor";
                break;
            case 6:
                objArr[0] = "node";
                break;
            default:
                objArr[0] = "file";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/util/io/IntToIntBtree";
        if (i == 2) {
            objArr[2] = "persistVars";
        } else if (i == 3) {
            objArr[2] = "get";
        } else if (i == 4) {
            objArr[2] = "processMappings";
        } else if (i == 5 || i == 6) {
            objArr[2] = "processLeafPages";
        } else {
            objArr[2] = "<init>";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    private void flushCachedMappings() throws IOException {
    }

    static /* synthetic */ int access$1204(IntToIntBtree intToIntBtree) {
        int i = intToIntBtree.hashedPagesCount + 1;
        intToIntBtree.hashedPagesCount = i;
        return i;
    }

    static /* synthetic */ int access$1206(IntToIntBtree intToIntBtree) {
        int i = intToIntBtree.hashedPagesCount - 1;
        intToIntBtree.hashedPagesCount = i;
        return i;
    }

    static /* synthetic */ int access$1312(IntToIntBtree intToIntBtree, int i) {
        int i2 = intToIntBtree.movedMembersCount + i;
        intToIntBtree.movedMembersCount = i2;
        return i2;
    }

    static /* synthetic */ int access$1508(IntToIntBtree intToIntBtree) {
        int i = intToIntBtree.hashSearchRequests;
        intToIntBtree.hashSearchRequests = i + 1;
        return i;
    }

    static /* synthetic */ int access$1712(IntToIntBtree intToIntBtree, int i) {
        int i2 = intToIntBtree.totalHashStepsSearched + i;
        intToIntBtree.totalHashStepsSearched = i2;
        return i2;
    }

    public static int version() {
        return (IOUtil.BYTE_BUFFERS_USE_NATIVE_BYTE_ORDER ? 255 : 0) + 4;
    }

    public IntToIntBtree(int i, Path path, StorageLockContext storageLockContext, boolean z) throws IOException {
        if (path == null) {
            $$$reportNull$$$0(0);
        }
        if (storageLockContext == null) {
            $$$reportNull$$$0(1);
        }
        this.pageSize = i;
        if (z) {
            Files.deleteIfExists(path);
        }
        ResizeableMappedFile resizeableMappedFile = new ResizeableMappedFile(path, i, storageLockContext, 1048576, true, IOUtil.BYTE_BUFFERS_USE_NATIVE_BYTE_ORDER);
        this.storage = resizeableMappedFile;
        resizeableMappedFile.setRoundFactor(i);
        BtreeRootNode btreeRootNode = new BtreeRootNode(this);
        this.root = btreeRootNode;
        if (z) {
            btreeRootNode.setAddress(-1);
        }
        int i2 = (i - 8) / 8;
        short s = (short) (i2 - 1);
        this.maxInteriorNodes = s;
        this.maxLeafNodes = s;
        while (!isPrime(i2)) {
            i2 -= 2;
        }
        this.hashPageCapacity = i2;
        int i3 = (int) (((double) i2) * 0.9d);
        i3 = (i3 & 1) == 1 ? i3 + 1 : i3;
        this.metaDataLeafPageLength = 8;
        this.maxLeafNodesInHash = (short) i3;
        this.myCachedMappings = null;
        this.myCachedMappingsSize = -1;
    }

    private void doAllocateRoot() throws IOException {
        nextPage();
        this.root.setAddress(0);
        this.root.getNodeView().setIndexLeaf(true);
    }

    public int persistVars(BtreeDataStorage btreeDataStorage, boolean z) throws IOException {
        if (btreeDataStorage == null) {
            $$$reportNull$$$0(2);
        }
        int iPersistInt = btreeDataStorage.persistInt(0, this.height | (this.hasZeroKey ? -16777216 : 0), z);
        this.hasZeroKey = (iPersistInt & ViewCompat.MEASURED_STATE_MASK) != 0;
        this.height = iPersistInt & 16777215;
        this.pagesCount = btreeDataStorage.persistInt(4, this.pagesCount, z);
        this.movedMembersCount = btreeDataStorage.persistInt(8, this.movedMembersCount, z);
        this.maxStepsSearchedInHash = btreeDataStorage.persistInt(12, this.maxStepsSearchedInHash, z);
        this.count = btreeDataStorage.persistInt(16, this.count, z);
        this.hashSearchRequests = btreeDataStorage.persistInt(20, this.hashSearchRequests, z);
        this.totalHashStepsSearched = btreeDataStorage.persistInt(24, this.totalHashStepsSearched, z);
        this.hashedPagesCount = btreeDataStorage.persistInt(28, this.hashedPagesCount, z);
        BtreeRootNode btreeRootNode = this.root;
        btreeRootNode.setAddress(btreeDataStorage.persistInt(32, btreeRootNode.address, z));
        this.zeroKeyValue = btreeDataStorage.persistInt(36, this.zeroKeyValue, z);
        return 40;
    }

    private final class BtreeRootNode {
        int address;
        boolean initialized;
        final BtreeIndexNodeView nodeView;

        BtreeRootNode(IntToIntBtree intToIntBtree) {
            this.nodeView = IntToIntBtree.this.new BtreeIndexNodeView(intToIntBtree);
        }

        void setAddress(int i) {
            this.address = i;
            this.initialized = false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void syncWithStore() throws IOException {
            this.nodeView.setAddress(this.address);
            this.initialized = true;
        }

        public BtreeIndexNodeView getNodeView() throws IOException {
            if (!this.initialized) {
                syncWithStore();
            }
            return this.nodeView;
        }
    }

    private static boolean isPrime(int i) {
        if (i % 2 == 0) {
            return false;
        }
        int iSqrt = (int) Math.sqrt(i);
        for (int i2 = 3; i2 <= iSqrt; i2 += 2) {
            if (i % i2 == 0) {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int nextPage() throws IOException {
        int length = (int) this.storage.length();
        this.storage.putInt((this.pageSize + length) - 4, 0);
        this.pagesCount++;
        return length;
    }

    public boolean get(int i, int[] iArr) throws IOException {
        if (iArr == null) {
            $$$reportNull$$$0(3);
        }
        if (i == 0) {
            if (!this.hasZeroKey) {
                return false;
            }
            iArr[0] = this.zeroKeyValue;
            return true;
        }
        if (this.root.address == -1) {
            return false;
        }
        if (this.myAccessNodeView == null) {
            this.myAccessNodeView = new BtreeIndexNodeView(this);
        }
        this.myAccessNodeView.initTraversal(this.root.address);
        int iLocate = this.myAccessNodeView.locate(i, false);
        if (iLocate < 0) {
            this.myCanUseLastKey = true;
            this.myLastGetKey = i;
            return false;
        }
        this.myCanUseLastKey = false;
        iArr[0] = this.myAccessNodeView.addressAt(iLocate);
        return true;
    }

    public void put(int i, int i2) throws IOException {
        if (i == 0) {
            this.hasZeroKey = true;
            this.zeroKeyValue = i2;
            return;
        }
        if (this.myCanUseLastKey) {
            this.myCanUseLastKey = false;
            if (i == this.myLastGetKey && !this.myAccessNodeView.myHasFullPagesAlongPath && this.myAccessNodeView.isValid()) {
                this.myOptimizedInserts++;
                this.count++;
                this.myAccessNodeView.insert(i, i2);
                return;
            }
        }
        doPut(i, i2);
    }

    private void doPut(int i, int i2) throws IOException {
        if (this.root.address == -1) {
            doAllocateRoot();
        }
        if (this.myAccessNodeView == null) {
            this.myAccessNodeView = new BtreeIndexNodeView(this);
        }
        this.myAccessNodeView.initTraversal(this.root.address);
        int iLocate = this.myAccessNodeView.locate(i, true);
        if (iLocate < 0) {
            this.count++;
            this.myAccessNodeView.insert(i, i2);
        } else {
            this.myAccessNodeView.setAddressAt(iLocate, i2);
        }
    }

    void dumpStatistics() throws IOException {
        int childrenCount;
        int i = this.height;
        if (i == 3) {
            childrenCount = this.pagesCount - (this.root.getNodeView().getChildrenCount() + 2);
        } else {
            childrenCount = i == 2 ? this.pagesCount - 1 : 1;
        }
        int i2 = this.hashedPagesCount;
        long j = ((long) i2) * ((long) this.maxLeafNodesInHash);
        long j2 = childrenCount - i2;
        short s = this.maxLeafNodes;
        long j3 = j + (j2 * ((long) s));
        long j4 = ((long) childrenCount) * ((long) s);
        int i3 = this.count;
        int i4 = (int) ((((long) i3) * 100) / j3);
        int i5 = (int) ((((long) i3) * 100) / j4);
        StringBuilder sbAppend = new StringBuilder("pagecount:").append(this.pagesCount).append(", height:").append(this.height).append(", movedMembers:").append(this.movedMembersCount).append(", optimized inserts:").append(this.myOptimizedInserts).append(", hash steps:").append(this.maxStepsSearchedInHash).append(", avg search in hash:");
        int i6 = this.hashSearchRequests;
        IOStatistics.dump(sbAppend.append(i6 != 0 ? this.totalHashStepsSearched / i6 : 0).append(", leaf pages used:").append(i4).append("%, leaf pages used if sorted: ").append(i5).append("%, size:").append(this.storage.length()).toString());
    }

    public void doClose() throws IOException {
        this.myCachedMappings = null;
        this.storage.close();
    }

    public void doFlush() throws IOException {
        flushCachedMappings();
        this.storage.m6720xd71ec63f();
    }

    static void myAssert(boolean z) {
        if (z) {
            return;
        }
        myAssert(true);
    }

    private static class BtreePage {
        protected final IntToIntBtree btree;
        protected int myAddressInBuffer;
        protected DirectBufferWrapper myBufferWrapper;
        protected boolean myHasFullPagesAlongPath;
        protected int address = -1;
        private short myChildrenCount = -1;

        BtreePage(IntToIntBtree intToIntBtree) {
            this.btree = intToIntBtree;
        }

        void setAddress(int i) throws IOException {
            setAddressInternal(i);
            syncWithStore();
        }

        private void setAddressInternal(int i) {
            this.address = i;
        }

        protected void syncWithStore() throws IOException {
            PagedFileStorage pagedFileStorage = this.btree.storage.getPagedFileStorage();
            this.myAddressInBuffer = pagedFileStorage.getOffsetInPage(this.address);
            DirectBufferWrapper byteBuffer = pagedFileStorage.getByteBuffer(this.address, false);
            this.myBufferWrapper = byteBuffer;
            doInitFlags(byteBuffer.getInt(this.myAddressInBuffer));
        }

        protected void doInitFlags(int i) {
            this.myChildrenCount = (short) ((i >>> 8) & 65535);
        }

        protected final void setFlag(int i, boolean z) throws IOException {
            int i2 = i << 24;
            int i3 = this.myBufferWrapper.getInt(this.myAddressInBuffer);
            this.myBufferWrapper.putInt(this.myAddressInBuffer, z ? i2 | i3 : (~i2) & i3);
        }

        protected final short getChildrenCount() {
            return this.myChildrenCount;
        }

        protected final void setChildrenCount(short s) throws IOException {
            this.myChildrenCount = s;
            this.myBufferWrapper.putInt(this.myAddressInBuffer, (s << 8) | (this.myBufferWrapper.getInt(this.myAddressInBuffer) & ViewCompat.MEASURED_STATE_MASK));
        }

        protected final void setNextPage(int i) throws IOException {
            putInt(4, i);
        }

        protected final int getNextPage() {
            return getInt(4);
        }

        protected final int getInt(int i) {
            return this.myBufferWrapper.getInt(this.myAddressInBuffer + i);
        }

        protected final void putInt(int i, int i2) throws IOException {
            this.myBufferWrapper.putInt(this.myAddressInBuffer + i, i2);
        }

        protected final ByteBuffer getBytes(int i, int i2) {
            ByteBuffer byteBufferCopy = this.myBufferWrapper.copy();
            int i3 = i + this.myAddressInBuffer;
            byteBufferCopy.position(i3);
            byteBufferCopy.limit(i3 + i2);
            return byteBufferCopy;
        }

        protected final void putBytes(int i, ByteBuffer byteBuffer) throws IOException {
            this.myBufferWrapper.position(i + this.myAddressInBuffer);
            this.myBufferWrapper.put(byteBuffer);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class BtreeIndexNodeView extends BtreePage {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private boolean isHashedLeaf;
        private boolean isIndexLeaf;

        BtreeIndexNodeView(IntToIntBtree intToIntBtree) {
            super(intToIntBtree);
        }

        private int search(final int i) throws IOException {
            if (isIndexLeaf() && isHashedLeaf()) {
                return hashIndex(i);
            }
            return ObjectUtils.binarySearch(0, getChildrenCount(), new IntUnaryOperator() { // from class: org.jetbrains.kotlin.com.intellij.util.io.IntToIntBtree$BtreeIndexNodeView$$ExternalSyntheticLambda0
                @Override // java.util.function.IntUnaryOperator
                public final int applyAsInt(int i2) {
                    return this.f$0.m6719xae4ce2cf(i, i2);
                }
            });
        }

        /* JADX INFO: renamed from: lambda$search$0$org-jetbrains-kotlin-com-intellij-util-io-IntToIntBtree$BtreeIndexNodeView, reason: not valid java name */
        /* synthetic */ int m6719xae4ce2cf(int i, int i2) {
            return Integer.compare(keyAt(i2), i);
        }

        int addressAt(int i) {
            return getInt(indexToOffset(i));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setAddressAt(int i, int i2) throws IOException {
            putInt(indexToOffset(i), i2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int indexToOffset(int i) {
            return (i * 8) + (isHashedLeaf() ? this.btree.metaDataLeafPageLength : 8);
        }

        private int keyAt(int i) {
            return getInt(indexToOffset(i) + 4);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setKeyAt(int i, int i2) throws IOException {
            putInt(indexToOffset(i) + 4, i2);
        }

        boolean isIndexLeaf() {
            return this.isIndexLeaf;
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.io.IntToIntBtree.BtreePage
        protected void doInitFlags(int i) {
            super.doInitFlags(i);
            int i2 = (i >> 24) & 255;
            this.isHashedLeaf = BitUtil.isSet(i2, 2);
            this.isIndexLeaf = BitUtil.isSet(i2, 1);
        }

        void setIndexLeaf(boolean z) throws IOException {
            this.isIndexLeaf = z;
            setFlag(1, z);
        }

        private boolean isHashedLeaf() {
            return this.isHashedLeaf;
        }

        void setHashedLeaf() throws IOException {
            this.isHashedLeaf = true;
            setFlag(2, true);
        }

        short getMaxChildrenCount() {
            if (isIndexLeaf()) {
                return isHashedLeaf() ? this.btree.maxLeafNodesInHash : this.btree.maxLeafNodes;
            }
            return this.btree.maxInteriorNodes;
        }

        boolean isFull() {
            short childrenCount = getChildrenCount();
            if (!isIndexLeaf()) {
                childrenCount = (short) (childrenCount + 1);
            }
            return childrenCount == getMaxChildrenCount();
        }

        boolean processMappings(KeyValueProcessor keyValueProcessor) throws IOException {
            if (isHashedLeaf()) {
                int iIndexToOffset = this.myAddressInBuffer + indexToOffset(0);
                for (int i = 0; i < this.btree.hashPageCapacity; i++) {
                    int i2 = this.myBufferWrapper.getInt(iIndexToOffset + 4);
                    if (i2 != 0 && !keyValueProcessor.process(i2, this.myBufferWrapper.getInt(iIndexToOffset))) {
                        return false;
                    }
                    iIndexToOffset += 8;
                }
                return true;
            }
            short childrenCount = getChildrenCount();
            for (int i3 = 0; i3 < childrenCount; i3++) {
                if (!keyValueProcessor.process(keyAt(i3), addressAt(i3))) {
                    return false;
                }
            }
            return true;
        }

        public void initTraversal(int i) throws IOException {
            this.myHasFullPagesAlongPath = false;
            setAddress(i);
        }

        public boolean isValid() {
            return !this.myBufferWrapper.isReleased();
        }

        private final class HashLeafData {
            final int[] keys;
            final BtreeIndexNodeView nodeView;
            final Int2IntMap values;

            HashLeafData(BtreeIndexNodeView btreeIndexNodeView, int i) throws IOException {
                this.nodeView = btreeIndexNodeView;
                IntToIntBtree intToIntBtree = btreeIndexNodeView.btree;
                int iIndexToOffset = btreeIndexNodeView.myAddressInBuffer + btreeIndexNodeView.indexToOffset(0);
                this.keys = new int[i];
                this.values = new Int2IntOpenHashMap(i);
                int i2 = 0;
                for (int i3 = 0; i3 < intToIntBtree.hashPageCapacity; i3++) {
                    int i4 = this.nodeView.myBufferWrapper.getInt(iIndexToOffset + 4);
                    if (i4 != 0) {
                        int i5 = this.nodeView.myBufferWrapper.getInt(iIndexToOffset);
                        int[] iArr = this.keys;
                        if (i2 == iArr.length) {
                            throw new CorruptedException(IntToIntBtree.this.storage.getPagedFileStorage().getFile());
                        }
                        iArr[i2] = i4;
                        this.values.put(i4, i5);
                        i2++;
                    }
                    iIndexToOffset += 8;
                }
                Arrays.sort(this.keys);
            }

            /* JADX INFO: Access modifiers changed from: private */
            public void clean() throws IOException {
                IntToIntBtree intToIntBtree = this.nodeView.btree;
                for (int i = 0; i < intToIntBtree.hashPageCapacity; i++) {
                    this.nodeView.setKeyAt(i, 0);
                }
            }
        }

        private int splitNode(int i) throws IOException {
            BtreeIndexNodeView btreeIndexNodeView;
            int iKeyAt;
            int i2;
            boolean zIsIndexLeaf = isIndexLeaf();
            boolean zIsHashedLeaf = isHashedLeaf();
            short childrenCount = getChildrenCount();
            if (i != 0) {
                btreeIndexNodeView = IntToIntBtree.this.new BtreeIndexNodeView(this.btree);
                btreeIndexNodeView.setAddress(i);
            } else {
                btreeIndexNodeView = null;
            }
            short maxChildrenCount = (short) (getMaxChildrenCount() / 2);
            BtreeIndexNodeView btreeIndexNodeView2 = IntToIntBtree.this.new BtreeIndexNodeView(this.btree);
            btreeIndexNodeView2.setAddress(this.btree.nextPage());
            syncWithStore();
            if (btreeIndexNodeView != null) {
                btreeIndexNodeView.syncWithStore();
            }
            this.btree.root.syncWithStore();
            btreeIndexNodeView2.setIndexLeaf(zIsIndexLeaf);
            int nextPage = getNextPage();
            setNextPage(btreeIndexNodeView2.address);
            btreeIndexNodeView2.setNextPage(nextPage);
            if (zIsIndexLeaf && zIsHashedLeaf) {
                HashLeafData hashLeafData = new HashLeafData(this, childrenCount);
                int[] iArr = hashLeafData.keys;
                hashLeafData.clean();
                Int2IntMap int2IntMap = hashLeafData.values;
                int length = iArr.length / 2;
                i2 = iArr[length];
                IntToIntBtree.access$1206(this.btree);
                setChildrenCount((short) 0);
                btreeIndexNodeView2.setChildrenCount((short) 0);
                for (int i3 = 0; i3 < length; i3++) {
                    int i4 = iArr[i3];
                    insert(i4, int2IntMap.get(i4));
                    int i5 = iArr[length + i3];
                    btreeIndexNodeView2.insert(i5, int2IntMap.get(i5));
                }
            } else {
                short s = (short) (childrenCount - maxChildrenCount);
                btreeIndexNodeView2.setChildrenCount(s);
                btreeIndexNodeView2.putBytes(btreeIndexNodeView2.indexToOffset(0), getBytes(indexToOffset(maxChildrenCount), s * 8));
                if (zIsIndexLeaf) {
                    iKeyAt = btreeIndexNodeView2.keyAt(0);
                } else {
                    btreeIndexNodeView2.setAddressAt(s, addressAt(childrenCount));
                    maxChildrenCount = (short) (maxChildrenCount - 1);
                    iKeyAt = keyAt(maxChildrenCount);
                }
                setChildrenCount(maxChildrenCount);
                i2 = iKeyAt;
            }
            if (btreeIndexNodeView == null) {
                int iNextPage = this.btree.nextPage();
                btreeIndexNodeView2.syncWithStore();
                syncWithStore();
                this.btree.root.setAddress(iNextPage);
                BtreeIndexNodeView nodeView = this.btree.root.getNodeView();
                nodeView.setChildrenCount((short) 1);
                nodeView.setKeyAt(0, i2);
                nodeView.setAddressAt(0, -this.address);
                nodeView.setAddressAt(1, -btreeIndexNodeView2.address);
                return iNextPage;
            }
            btreeIndexNodeView.insert(i2, -btreeIndexNodeView2.address);
            return i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int locate(int i, boolean z) throws IOException {
            int i2 = this.btree.height + 1;
            int i3 = 0;
            int i4 = 0;
            while (true) {
                if (isFull()) {
                    if (z) {
                        int iSplitNode = splitNode(i3);
                        if (iSplitNode != 0) {
                            setAddress(iSplitNode);
                        }
                        i4--;
                    } else {
                        this.myHasFullPagesAlongPath = true;
                    }
                }
                int iSearch = search(i);
                i4++;
                if (i4 > i2) {
                    throw new CorruptedException(IntToIntBtree.this.storage.getPagedFileStorage().getFile());
                }
                if (isIndexLeaf()) {
                    this.btree.height = Math.max(this.btree.height, i4);
                    return iSearch;
                }
                int iAddressAt = addressAt(iSearch < 0 ? (-iSearch) - 1 : iSearch + 1);
                int i5 = this.address;
                setAddress(-iAddressAt);
                i3 = i5;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void insert(int i, int i2) throws IOException {
            short childrenCount = getChildrenCount();
            boolean zIsIndexLeaf = isIndexLeaf();
            if (zIsIndexLeaf) {
                if (childrenCount == 0) {
                    setHashedLeaf();
                    IntToIntBtree.access$1204(this.btree);
                }
                if (isHashedLeaf()) {
                    int iHashIndex = hashIndex(i);
                    if (iHashIndex < 0) {
                        iHashIndex = (-iHashIndex) - 1;
                    }
                    setKeyAt(iHashIndex, i);
                    setAddressAt(iHashIndex, i2);
                    setChildrenCount((short) (childrenCount + 1));
                    return;
                }
            }
            int i3 = -search(i);
            int i4 = i3 - 1;
            int i5 = childrenCount + 1;
            setChildrenCount((short) i5);
            int i6 = childrenCount - i4;
            IntToIntBtree.access$1312(this.btree, i6);
            if (zIsIndexLeaf) {
                if (i6 > 5) {
                    putBytes(indexToOffset(i3), getBytes(indexToOffset(i4), i6 * 8));
                } else {
                    for (int i7 = childrenCount - 1; i7 >= i4; i7--) {
                        int i8 = i7 + 1;
                        setKeyAt(i8, keyAt(i7));
                        setAddressAt(i8, addressAt(i7));
                    }
                }
                setKeyAt(i4, i);
                setAddressAt(i4, i2);
                return;
            }
            setAddressAt(i5, addressAt(childrenCount));
            if (i6 > 5) {
                int i9 = i6 - 1;
                if (i9 > 0) {
                    putBytes(indexToOffset(i3 + 1), getBytes(indexToOffset(i3), i9 * 8));
                }
            } else {
                for (int i10 = childrenCount - 1; i10 > i4; i10--) {
                    int i11 = i10 + 1;
                    setKeyAt(i11, keyAt(i10));
                    setAddressAt(i11, addressAt(i10));
                }
            }
            if (i4 < childrenCount) {
                setKeyAt(i3, keyAt(i4));
            }
            setKeyAt(i4, i);
            setAddressAt(i3, i2);
        }

        private int hashIndex(int i) throws IOException {
            int i2 = this.btree.hashPageCapacity;
            int i3 = Integer.MAX_VALUE & i;
            int i4 = i3 % i2;
            int iKeyAt = keyAt(i4);
            IntToIntBtree.access$1508(this.btree);
            int i5 = 0;
            if (iKeyAt != i && iKeyAt != 0) {
                int i6 = (i3 % (i2 - 2)) + 1;
                do {
                    i4 -= i6;
                    if (i4 < 0) {
                        i4 += i2;
                    }
                    iKeyAt = keyAt(i4);
                    i5++;
                    if (i5 > i2) {
                        throw new CorruptedException(IntToIntBtree.this.storage.getPagedFileStorage().getFile());
                    }
                    if (iKeyAt == i) {
                        break;
                    }
                } while (iKeyAt != 0);
            }
            this.btree.maxStepsSearchedInHash = Math.max(this.btree.maxStepsSearchedInHash, i5);
            IntToIntBtree.access$1712(this.btree, i5);
            return iKeyAt == 0 ? (-i4) - 1 : i4;
        }
    }

    public boolean processMappings(KeyValueProcessor keyValueProcessor) throws IOException {
        if (keyValueProcessor == null) {
            $$$reportNull$$$0(4);
        }
        doFlush();
        if (this.hasZeroKey && !keyValueProcessor.process(0, this.zeroKeyValue)) {
            return false;
        }
        if (this.root.address == -1) {
            return true;
        }
        this.root.syncWithStore();
        return processLeafPages(this.root.getNodeView(), keyValueProcessor);
    }

    private boolean processLeafPages(BtreeIndexNodeView btreeIndexNodeView, KeyValueProcessor keyValueProcessor) throws IOException {
        if (keyValueProcessor == null) {
            $$$reportNull$$$0(5);
        }
        if (btreeIndexNodeView == null) {
            $$$reportNull$$$0(6);
        }
        if (btreeIndexNodeView.isIndexLeaf()) {
            return btreeIndexNodeView.processMappings(keyValueProcessor);
        }
        int childrenCount = btreeIndexNodeView.getChildrenCount() + 1;
        int[] iArr = new int[childrenCount];
        for (int i = 0; i < childrenCount; i++) {
            iArr[i] = -btreeIndexNodeView.addressAt(i);
        }
        if (childrenCount > 0) {
            BtreeIndexNodeView btreeIndexNodeView2 = new BtreeIndexNodeView(this);
            for (int i2 = 0; i2 < childrenCount; i2++) {
                btreeIndexNodeView2.setAddress(iArr[i2]);
                if (!processLeafPages(btreeIndexNodeView2, keyValueProcessor)) {
                    return false;
                }
            }
        }
        return true;
    }
}
