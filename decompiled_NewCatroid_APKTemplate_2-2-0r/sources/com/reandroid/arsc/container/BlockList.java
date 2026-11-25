package com.reandroid.arsc.container;

import com.reandroid.arsc.base.Block;
import com.reandroid.arsc.base.BlockCounter;
import com.reandroid.arsc.base.BlockRefresh;
import com.reandroid.arsc.base.Creator;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.utils.collection.ArrayCollection;
import com.reandroid.utils.collection.SwapListener;
import com.reandroid.utils.collection.Swappable;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes10.dex */
public class BlockList<T extends Block> extends Block implements BlockRefresh, Swappable {
    private static final BlockList<?> empty_list = new BlockList<Block>() { // from class: com.reandroid.arsc.container.BlockList.3
        @Override // com.reandroid.arsc.container.BlockList
        public int size() {
            return 0;
        }

        @Override // com.reandroid.arsc.container.BlockList
        public boolean add(Block block) {
            throw new IllegalArgumentException("Empty BlockList");
        }

        @Override // com.reandroid.arsc.container.BlockList
        public void add(int i, Block block) {
            throw new IllegalArgumentException("Empty BlockList");
        }

        @Override // com.reandroid.arsc.container.BlockList
        public void ensureCapacity(int i) {
            if (i != 0) {
                throw new IllegalArgumentException("Empty BlockList");
            }
        }

        @Override // com.reandroid.arsc.container.BlockList
        public void setSize(int i) {
            if (i != 0) {
                throw new IllegalArgumentException("Empty BlockList");
            }
        }
    };
    private Creator<? extends T> mCreator;
    private ArrayCollection<T> mItems;

    protected void onPreRefresh() {
    }

    public void onPreRemove(T t) {
    }

    public BlockList(Creator<? extends T> creator) {
        this.mItems = ArrayCollection.empty();
        this.mCreator = creator;
    }

    public BlockList() {
        this(null);
    }

    public Creator<? extends T> getCreator() {
        return this.mCreator;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setCreator(Creator<T> creator) {
        this.mCreator = creator;
        if (this.mItems.isImmutableEmpty()) {
            return;
        }
        updateCreator();
    }

    public void ensureSize(int i) {
        if (i > size()) {
            setSize(i);
        }
    }

    public void setSize(int i) {
        if (i == 0) {
            lockList();
        } else if (this.mCreator != null || i < size()) {
            unlockList();
            this.mItems.setSize(i);
        }
    }

    public void clearTemporarily() {
        this.mItems.clearTemporarily();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setElements(T[] tArr) {
        if (tArr == 0 || tArr.length == 0) {
            lockList();
            return;
        }
        unlockList();
        Creator<? extends T> creator = getCreator();
        int length = tArr.length;
        for (int i = 0; i < length; i++) {
            Object objNewInstanceAt = tArr[i];
            if (objNewInstanceAt == 0 && creator != null) {
                objNewInstanceAt = creator.newInstanceAt(i);
                tArr[i] = objNewInstanceAt;
            }
            onItemCreated(i, objNewInstanceAt);
        }
        this.mItems.setElements(tArr);
        onChanged();
    }

    void onItemCreated(int i, T t) {
        if (t == null) {
            return;
        }
        t.setIndex(i);
        t.setParent(this);
    }

    public T createAt(int i) {
        Creator<? extends T> creator = getCreator();
        ensureSize(i);
        T t = (T) creator.newInstanceAt(i);
        add(i, t);
        return t;
    }

    public T createNext() {
        T t = (T) getCreator().newInstanceAt(size());
        add(t);
        return t;
    }

    public T getFirst() {
        int size = size();
        for (int i = 0; i < size; i++) {
            T t = (T) get(i);
            if (t != null) {
                return t;
            }
        }
        return null;
    }

    public T getLast() {
        for (int size = size() - 1; size >= 0; size--) {
            T t = (T) get(size);
            if (t != null) {
                return t;
            }
        }
        return null;
    }

    public Iterator<T> clonedIterator() {
        return this.mItems.clonedIterator();
    }

    public Iterator<T> clonedIterator(int i) {
        return clonedIterator(i, size() - i);
    }

    public Iterator<T> clonedIterator(int i, int i2) {
        return this.mItems.clonedIterator(i, i2);
    }

    public Iterator<T> arrayIterator() {
        return this.mItems.arrayIterator();
    }

    public Iterator<T> iterator() {
        return this.mItems.iterator();
    }

    public Iterator<T> iterator(int i, int i2) {
        return this.mItems.iterator(i, i2);
    }

    public Iterator<T> iterator(Predicate<? super T> predicate) {
        return this.mItems.iterator(predicate);
    }

    public <T1> Iterator<T1> iterator(Class<T1> cls) {
        return this.mItems.iterator(cls);
    }

    public int countIf(Predicate<? super T> predicate) {
        return this.mItems.count(predicate);
    }

    public int countFromLast(Predicate<? super T> predicate) {
        return this.mItems.countFromLast(predicate);
    }

    public ArrayCollection<T> subListIf(Predicate<? super T> predicate) {
        return this.mItems.subListIf(predicate);
    }

    public void clearChildes() {
        if (this.mItems.isEmpty()) {
            return;
        }
        int size = size();
        for (int i = 0; i < size; i++) {
            remove(size() - 1, false);
        }
        lockList();
        onChanged();
    }

    public void destroy() {
        this.mItems.clear();
        lockList();
        onChanged();
    }

    public boolean sort(Comparator<? super T> comparator) {
        if (size() < 2) {
            return false;
        }
        boolean zSort = this.mItems.sort(comparator, new SwapListener() { // from class: com.reandroid.arsc.container.BlockList$$ExternalSyntheticLambda0
            @Override // com.reandroid.utils.collection.SwapListener
            public final void onSwap(int i, int i2) {
                this.f$0.m2756lambda$sort$0$comreandroidarsccontainerBlockList(i, i2);
            }
        });
        if (zSort) {
            updateIndex();
        }
        return zSort;
    }

    /* JADX INFO: renamed from: lambda$sort$0$com-reandroid-arsc-container-BlockList, reason: not valid java name */
    /* synthetic */ void m2756lambda$sort$0$comreandroidarsccontainerBlockList(int i, int i2) {
        Block block = get(i);
        Block block2 = get(i2);
        if (block != null) {
            block.setIndex(i);
        }
        if (block2 != null) {
            block2.setIndex(i2);
        }
    }

    public boolean sort(Comparator<? super T> comparator, Swappable swappable) {
        if (size() >= 2 && this.mItems.sort(comparator, swappable)) {
            return updateIndex();
        }
        return false;
    }

    public boolean needsSort(Comparator<? super T> comparator) {
        int size;
        if (comparator == null || (size = size()) < 2) {
            return false;
        }
        Block block = get(0);
        int i = 1;
        while (i < size) {
            Block block2 = get(i);
            if (comparator.compare(block, block2) > 0) {
                return true;
            }
            i++;
            block = block2;
        }
        return false;
    }

    public boolean removeAll(Collection<?> collection) {
        return removeAllIndexes(toIndexArray(collection));
    }

    private int[] toIndexArray(Collection<?> collection) {
        Block block;
        int index;
        int size = collection.size();
        int[] iArr = new int[size];
        ArrayCollection<T> arrayCollection = this.mItems;
        int size2 = arrayCollection.size();
        int i = 0;
        for (Object obj : collection) {
            if (obj != null && (index = (block = (Block) obj).getIndex()) >= 0 && index < size2 && block == arrayCollection.get(index)) {
                iArr[i] = index;
                i++;
            }
        }
        while (i < size) {
            iArr[i] = -1;
            i++;
        }
        return iArr;
    }

    public boolean removeAllIndexes(int[] iArr) {
        this.mItems.removeAllIndexes(iArr);
        updateIndex();
        return true;
    }

    public boolean removeIf(Predicate<? super T> predicate) {
        boolean zRemoveIf = this.mItems.removeIf(predicate);
        if (zRemoveIf) {
            updateIndex();
        }
        return zRemoveIf;
    }

    public T remove(int i) {
        return (T) remove(i, true);
    }

    private T remove(int i, boolean z) {
        T tRemove = this.mItems.remove(i);
        if (tRemove == null) {
            return null;
        }
        tRemove.setParent(null);
        tRemove.setIndex(-1);
        if (z) {
            updateIndex(i);
        }
        onChanged();
        return tRemove;
    }

    public boolean remove(T t) {
        if (t == null) {
            return false;
        }
        int iIndexOfFast = this.mItems.indexOfFast(t, t.getIndex());
        if (iIndexOfFast < 0) {
            iIndexOfFast = this.mItems.indexOfFast(t);
        }
        if (iIndexOfFast < 0) {
            return false;
        }
        boolean z = this.mItems.remove(iIndexOfFast) != null;
        if (z) {
            updateIndex(iIndexOfFast);
            t.setIndex(-1);
            t.setParent(null);
        }
        onChanged();
        return z;
    }

    public int indexOf(T t) {
        if (t == null) {
            return -1;
        }
        int iIndexOfFast = this.mItems.indexOfFast(t, t.getIndex());
        return iIndexOfFast < 0 ? this.mItems.indexOfFast(t) : iIndexOfFast;
    }

    protected void notifyPreRemove(T t) {
        if (t == null || t.getParent() != this) {
            return;
        }
        onPreRemove(t);
        t.setIndex(-1);
        t.setParent(null);
    }

    @Override // com.reandroid.utils.collection.Swappable
    public boolean swap(int i, int i2) {
        if (i == i2) {
            return false;
        }
        return swap(get(i), get(i2));
    }

    public boolean swap(T t, T t2) {
        if (t == t2 || t == null || t2 == null) {
            return false;
        }
        int index = t.getIndex();
        int index2 = t2.getIndex();
        this.mItems.swap(index, index2);
        t.setIndex(index2);
        t2.setIndex(index);
        return true;
    }

    public void moveTo(T t, int i) {
        if (i < 0) {
            i = 0;
        }
        int iIndexOfFast = this.mItems.indexOfFast(t, t.getIndex());
        this.mItems.move(t, i);
        updateIndex(iIndexOfFast, i);
    }

    public void set(int i, T t) {
        if (t == null) {
            return;
        }
        unlockList();
        t.setIndex(i);
        t.setParent(this);
        this.mItems.set(i, t);
        onChanged();
    }

    public void addAll(int i, T[] tArr) {
        if (tArr == null || (tArr.length) == 0) {
            return;
        }
        unlockList();
        this.mItems.addAll(i, tArr);
        for (T t : tArr) {
            if (t != null) {
                t.setIndex(i);
                t.setParent(this);
                i++;
            }
        }
        updateIndex(i);
        onChanged();
    }

    public void add(int i, T t) {
        if (t == null) {
            return;
        }
        unlockList();
        t.setIndex(i);
        t.setParent(this);
        this.mItems.add(i, t);
        updateIndex(i);
        onChanged();
    }

    private boolean updateIndex() {
        return updateIndex(0);
    }

    private boolean updateIndex(int i) {
        return updateIndex(i, size());
    }

    private boolean updateIndex(int i, int i2) {
        boolean z = false;
        if (i < 0) {
            i = 0;
        }
        if (i > i2) {
            i2 = i;
            i = i2;
        }
        int i3 = i2 + 1;
        int size = size();
        if (i3 > size) {
            i3 = size;
        }
        List<T> childes = getChildes();
        while (i < i3) {
            T t = childes.get(i);
            if (t.getIndex() != i) {
                t.setIndex(i);
                z = true;
            }
            i++;
        }
        return z;
    }

    public boolean add(T t) {
        if (t == null) {
            return false;
        }
        unlockList();
        t.setIndex(size());
        t.setParent(this);
        boolean zAdd = this.mItems.add(t);
        onChanged();
        return zAdd;
    }

    public T get(int i) {
        if (i >= this.mItems.size() || i < 0) {
            return null;
        }
        return this.mItems.get(i);
    }

    public int getCount() {
        return size();
    }

    public int size() {
        return this.mItems.size();
    }

    public void ensureCapacity(int i) {
        unlockList();
        this.mItems.ensureCapacity(i);
    }

    public void trimToSize() {
        this.mItems.trimToSize();
        if (this.mItems.size() == 0) {
            lockList();
        }
    }

    public boolean contains(Object obj) {
        return this.mItems.contains(obj);
    }

    public boolean containsExact(Object obj) {
        return this.mItems.containsFast(obj);
    }

    public Object[] toArray() {
        return this.mItems.toArray();
    }

    public <T1> T1[] toArray(T1[] t1Arr) {
        return (T1[]) this.mItems.toArray(t1Arr);
    }

    public List<T> getChildes() {
        return this.mItems;
    }

    private void lockList() {
        if (this.mItems.isImmutableEmpty()) {
            return;
        }
        this.mItems = ArrayCollection.empty();
    }

    private void unlockList() {
        if (this.mItems.isImmutableEmpty()) {
            this.mItems = new ArrayCollection<>();
            updateCreator();
            this.mItems.setMonitor(getMonitor());
        }
    }

    protected ArrayCollection.Monitor<T> getMonitor() {
        return (ArrayCollection.Monitor<T>) new ArrayCollection.Monitor<T>() { // from class: com.reandroid.arsc.container.BlockList.1
            @Override // com.reandroid.utils.collection.ArrayCollection.Monitor
            public void onAdd(int i, T t) {
            }

            @Override // com.reandroid.utils.collection.ArrayCollection.Monitor
            public void onRemoved(int i, T t) {
                BlockList.this.notifyPreRemove(t);
            }
        };
    }

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
    private void updateCreator() {
        final Creator<? extends T> creator = getCreator();
        if (creator == null) {
            this.mItems.setInitializer(null);
        } else {
            this.mItems.setInitializer(new ArrayCollection.Initializer<T>() { // from class: com.reandroid.arsc.container.BlockList.2
                /* JADX WARN: Type inference fix 'apply assigned field type' failed
                java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$PrimitiveArg
                	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
                	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
                	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
                	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
                	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
                	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
                	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
                 */
                @Override // com.reandroid.utils.collection.ArrayCollection.Initializer
                public T createNewItem(int i) {
                    T t = (T) creator.newInstanceAt(i);
                    BlockList.this.onItemCreated(i, t);
                    return t;
                }

                @Override // com.reandroid.utils.collection.ArrayCollection.Initializer
                public T[] newArray(int i) {
                    return (T[]) creator.newArrayInstance(i);
                }
            });
        }
    }

    @Override // com.reandroid.arsc.base.BlockRefresh
    public final void refresh() {
        if (isNull()) {
            return;
        }
        trimToSize();
        onPreRefresh();
        refreshChildes();
        onRefreshed();
        onChanged();
    }

    protected void onRefreshed() {
        onChanged();
    }

    public void onChanged() {
        this.mItems.onChanged();
    }

    private void refreshChildes() {
        for (T t : this) {
            if (t instanceof BlockRefresh) {
                ((BlockRefresh) t).refresh();
            }
        }
    }

    @Override // com.reandroid.arsc.base.Block
    public byte[] getBytes() {
        Iterator<T> it = iterator();
        byte[] bArrAddBytes = null;
        while (it.hasNext()) {
            bArrAddBytes = addBytes(bArrAddBytes, it.next().getBytes());
        }
        return bArrAddBytes;
    }

    @Override // com.reandroid.arsc.base.Block
    public int countBytes() {
        int size = size();
        int iCountBytes = 0;
        for (int i = 0; i < size; i++) {
            Block block = get(i);
            if (block != null) {
                iCountBytes += block.countBytes();
            }
        }
        return iCountBytes;
    }

    @Override // com.reandroid.arsc.base.Block
    public void onCountUpTo(BlockCounter blockCounter) {
        if (blockCounter.FOUND) {
            return;
        }
        blockCounter.setCurrent(this);
        if (blockCounter.END == this) {
            blockCounter.FOUND = true;
            return;
        }
        int size = size();
        for (int i = 0; i < size && !blockCounter.FOUND; i++) {
            Block block = get(i);
            if (block != null) {
                block.onCountUpTo(blockCounter);
            }
        }
    }

    public void readChildes(BlockReader blockReader) throws IOException {
        int size = size();
        for (int i = 0; i < size; i++) {
            get(i).readBytes(blockReader);
        }
        onChanged();
    }

    @Override // com.reandroid.arsc.base.Block
    protected int onWriteBytes(OutputStream outputStream) throws IOException {
        int size = size();
        int iWriteBytes = 0;
        for (int i = 0; i < size; i++) {
            Block block = get(i);
            if (block != null) {
                iWriteBytes += block.writeBytes(outputStream);
            }
        }
        return iWriteBytes;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.mItems.equals(((BlockList) obj).mItems);
    }

    public int hashCode() {
        return this.mItems.hashCode();
    }

    public String toString() {
        return "size=" + size();
    }

    public static boolean isImmutableEmpty(Object obj) {
        return empty_list == obj;
    }

    public static <T1 extends Block> BlockList<T1> empty() {
        return (BlockList<T1>) empty_list;
    }
}
