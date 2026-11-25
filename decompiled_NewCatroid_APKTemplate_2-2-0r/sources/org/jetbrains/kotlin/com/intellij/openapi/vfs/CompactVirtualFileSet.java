package org.jetbrains.kotlin.com.intellij.openapi.vfs;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressManager;
import org.jetbrains.kotlin.com.intellij.openapi.util.ModificationTracker;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.com.intellij.util.containers.IntArrayList;

/* JADX INFO: loaded from: classes3.dex */
@Deprecated
public final class CompactVirtualFileSet extends AbstractSet<VirtualFile> implements VirtualFileSet {
    private BitSet fileIds;
    private boolean frozen;
    private StrippedIntOpenHashSet idSet;
    private final Set<VirtualFile> weirdFiles;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 4) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "fileIds";
                break;
            case 2:
            case 3:
            case 4:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/CompactVirtualFileSet";
                break;
            case 5:
                objArr[0] = "file";
                break;
            case 6:
                objArr[0] = "processor";
                break;
            case 7:
            case 8:
                objArr[0] = "c";
                break;
            case 9:
                objArr[0] = "weirdFiles";
                break;
            default:
                objArr[0] = "files";
                break;
        }
        if (i == 2 || i == 3 || i == 4) {
            objArr[1] = "onlyInternalFileIds";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/CompactVirtualFileSet";
        }
        switch (i) {
            case 2:
            case 3:
            case 4:
                break;
            case 5:
                objArr[2] = ProductAction.ACTION_ADD;
                break;
            case 6:
                objArr[2] = "process";
                break;
            case 7:
                objArr[2] = "retainAll";
                break;
            case 8:
                objArr[2] = "addAll";
                break;
            case 9:
                objArr[2] = "contains";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 4) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public CompactVirtualFileSet() {
        this.weirdFiles = new HashSet();
    }

    CompactVirtualFileSet(Collection<? extends VirtualFile> collection) {
        if (collection == null) {
            $$$reportNull$$$0(0);
        }
        this.weirdFiles = new HashSet();
        addAll(collection);
    }

    public boolean containsId(int i) {
        StrippedIntOpenHashSet strippedIntOpenHashSet = this.idSet;
        if (strippedIntOpenHashSet != null) {
            return strippedIntOpenHashSet.contains(i);
        }
        BitSet bitSet = this.fileIds;
        if (bitSet != null) {
            return bitSet.get(i);
        }
        for (ModificationTracker modificationTracker : this.weirdFiles) {
            if ((modificationTracker instanceof VirtualFileWithId) && ((VirtualFileWithId) modificationTracker).getId() == i) {
                return true;
            }
        }
        return false;
    }

    public int[] onlyInternalFileIds() {
        StrippedIntOpenHashSet strippedIntOpenHashSet = this.idSet;
        if (strippedIntOpenHashSet != null) {
            int[] array = strippedIntOpenHashSet.toArray();
            if (array == null) {
                $$$reportNull$$$0(2);
            }
            return array;
        }
        BitSet bitSet = this.fileIds;
        if (bitSet != null) {
            int[] array2 = bitSet.stream().toArray();
            if (array2 == null) {
                $$$reportNull$$$0(3);
            }
            return array2;
        }
        int[] array3 = this.weirdFiles.stream().filter(new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.openapi.vfs.CompactVirtualFileSet$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return CompactVirtualFileSet.lambda$onlyInternalFileIds$0((VirtualFile) obj);
            }
        }).mapToInt(new ToIntFunction() { // from class: org.jetbrains.kotlin.com.intellij.openapi.vfs.CompactVirtualFileSet$$ExternalSyntheticLambda1
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((VirtualFileWithId) ((VirtualFile) obj)).getId();
            }
        }).toArray();
        if (array3 == null) {
            $$$reportNull$$$0(4);
        }
        return array3;
    }

    static /* synthetic */ boolean lambda$onlyInternalFileIds$0(VirtualFile virtualFile) {
        return virtualFile instanceof VirtualFileWithId;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean contains(Object obj) {
        if (obj instanceof VirtualFileWithId) {
            BitSet bitSet = this.fileIds;
            int id2 = ((VirtualFileWithId) obj).getId();
            if (bitSet != null) {
                return bitSet.get(id2);
            }
            StrippedIntOpenHashSet strippedIntOpenHashSet = this.idSet;
            if (strippedIntOpenHashSet != null) {
                return strippedIntOpenHashSet.contains(id2);
            }
        }
        return this.weirdFiles.contains(obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean add(VirtualFile virtualFile) {
        if (virtualFile == 0) {
            $$$reportNull$$$0(5);
        }
        if (this.frozen) {
            throw new UnsupportedOperationException();
        }
        if (virtualFile instanceof VirtualFileWithId) {
            int id2 = ((VirtualFileWithId) virtualFile).getId();
            BitSet bitSet = this.fileIds;
            StrippedIntOpenHashSet strippedIntOpenHashSet = this.idSet;
            if (bitSet != null) {
                boolean z = !bitSet.get(id2);
                bitSet.set(id2);
                return z;
            }
            if (strippedIntOpenHashSet != null) {
                boolean zAdd = strippedIntOpenHashSet.add(id2);
                if (strippedIntOpenHashSet.size() <= 1000) {
                    return zAdd;
                }
                convertToBitSet();
                return zAdd;
            }
            boolean zAdd2 = this.weirdFiles.add(virtualFile);
            if (this.weirdFiles.size() <= 10) {
                return zAdd2;
            }
            convertToIntSet();
            return zAdd2;
        }
        return this.weirdFiles.add(virtualFile);
    }

    private void convertToIntSet() {
        StrippedIntOpenHashSet strippedIntOpenHashSet = new StrippedIntOpenHashSet(this.weirdFiles.size());
        Iterator<VirtualFile> it = this.weirdFiles.iterator();
        while (it.hasNext()) {
            ModificationTracker modificationTracker = (VirtualFile) it.next();
            if (modificationTracker instanceof VirtualFileWithId) {
                strippedIntOpenHashSet.add(((VirtualFileWithId) modificationTracker).getId());
                it.remove();
            }
        }
        this.idSet = strippedIntOpenHashSet;
    }

    private void convertToBitSet() {
        this.fileIds = new BitSet();
        StrippedIntOpenHashSet.SetIterator it = this.idSet.iterator();
        while (it.hasNext()) {
            this.fileIds.set(it.nextInt());
        }
        this.idSet = null;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean remove(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSet
    public void freeze() {
        this.frozen = true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public int size() {
        BitSet bitSet = this.fileIds;
        StrippedIntOpenHashSet strippedIntOpenHashSet = this.idSet;
        return (bitSet == null ? 0 : bitSet.cardinality()) + (strippedIntOpenHashSet != null ? strippedIntOpenHashSet.size() : 0) + this.weirdFiles.size();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean retainAll(Collection<?> collection) {
        boolean z;
        if (collection == null) {
            $$$reportNull$$$0(7);
        }
        if (this.frozen) {
            throw new IllegalStateException();
        }
        if (collection instanceof CompactVirtualFileSet) {
            CompactVirtualFileSet compactVirtualFileSet = (CompactVirtualFileSet) collection;
            StrippedIntOpenHashSet strippedIntOpenHashSet = compactVirtualFileSet.idSet;
            BitSet bitSet = compactVirtualFileSet.fileIds;
            Set<VirtualFile> set = compactVirtualFileSet.weirdFiles;
            if (this.idSet != null) {
                IntArrayList intArrayList = new IntArrayList();
                StrippedIntOpenHashSet.SetIterator it = this.idSet.iterator();
                z = false;
                while (it.hasNext()) {
                    int iNextInt = it.nextInt();
                    if (!contains(iNextInt, strippedIntOpenHashSet, bitSet, set)) {
                        intArrayList.add(iNextInt);
                        z = true;
                    }
                }
                for (int i : intArrayList.toArray()) {
                    strippedIntOpenHashSet.remove(i);
                }
            } else {
                z = false;
            }
            BitSet bitSet2 = this.fileIds;
            if (bitSet2 != null) {
                for (int i2 : bitSet2.stream().toArray()) {
                    if (!contains(i2, strippedIntOpenHashSet, bitSet, set)) {
                        this.fileIds.set(i2, false);
                        z = true;
                    }
                }
            }
            Iterator<VirtualFile> it2 = this.weirdFiles.iterator();
            while (it2.hasNext()) {
                if (!collection.contains(it2.next())) {
                    it2.remove();
                    z = true;
                }
            }
            return z;
        }
        return super.retainAll(collection);
    }

    /* JADX WARN: Type inference failed for: r5v7, types: [java.util.PrimitiveIterator$OfInt] */
    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean addAll(Collection<? extends VirtualFile> collection) {
        IntStream intStreamStream;
        if (collection == null) {
            $$$reportNull$$$0(8);
        }
        if (this.frozen) {
            throw new IllegalStateException();
        }
        if (collection instanceof CompactVirtualFileSet) {
            CompactVirtualFileSet compactVirtualFileSet = (CompactVirtualFileSet) collection;
            Iterator<VirtualFile> it = compactVirtualFileSet.weirdFiles.iterator();
            boolean z = false;
            while (it.hasNext()) {
                if (add(it.next())) {
                    z = true;
                }
            }
            StrippedIntOpenHashSet strippedIntOpenHashSet = compactVirtualFileSet.idSet;
            if (strippedIntOpenHashSet != null) {
                intStreamStream = Arrays.stream(strippedIntOpenHashSet.toArray());
            } else {
                BitSet bitSet = compactVirtualFileSet.fileIds;
                if (bitSet != null) {
                    intStreamStream = bitSet.stream();
                }
                return z;
            }
            ?? it2 = intStreamStream.iterator();
            while (it2.hasNext()) {
                int iNextInt = it2.nextInt();
                if (this.fileIds == null && this.idSet == null) {
                    convertToIntSet();
                }
                BitSet bitSet2 = this.fileIds;
                if (bitSet2 != null) {
                    z = !bitSet2.get(iNextInt);
                    this.fileIds.set(iNextInt);
                } else {
                    StrippedIntOpenHashSet strippedIntOpenHashSet2 = this.idSet;
                    if (strippedIntOpenHashSet2 != null) {
                        boolean zAdd = strippedIntOpenHashSet2.add(iNextInt);
                        if (this.idSet.size() > 1000) {
                            convertToBitSet();
                        }
                        z = zAdd;
                    } else {
                        throw new IllegalStateException();
                    }
                }
            }
            return z;
        }
        return super.addAll(collection);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public Iterator<VirtualFile> iterator() {
        Iterator it;
        final Iterator itConcatIterators;
        BitSet bitSet = this.fileIds;
        final StrippedIntOpenHashSet strippedIntOpenHashSet = this.idSet;
        final VirtualFileManager virtualFileManager = VirtualFileManager.getInstance();
        if (bitSet == null) {
            it = Collections.emptyIterator();
        } else {
            it = bitSet.stream().mapToObj(new IntFunction() { // from class: org.jetbrains.kotlin.com.intellij.openapi.vfs.CompactVirtualFileSet$$ExternalSyntheticLambda2
                @Override // java.util.function.IntFunction
                public final Object apply(int i) {
                    return CompactVirtualFileSet.lambda$iterator$2(virtualFileManager, i);
                }
            }).iterator();
        }
        if (strippedIntOpenHashSet == null) {
            itConcatIterators = ContainerUtil.concatIterators(it, this.weirdFiles.iterator());
        } else {
            final StrippedIntOpenHashSet.SetIterator it2 = strippedIntOpenHashSet.iterator();
            itConcatIterators = ContainerUtil.concatIterators(it, new Iterator<VirtualFile>() { // from class: org.jetbrains.kotlin.com.intellij.openapi.vfs.CompactVirtualFileSet.1
                int storedId;

                @Override // java.util.Iterator
                public boolean hasNext() {
                    return it2.hasNext();
                }

                @Override // java.util.Iterator
                public VirtualFile next() {
                    int iNextInt = it2.nextInt();
                    this.storedId = iNextInt;
                    ProgressManager.checkCanceled();
                    return virtualFileManager.findFileById(iNextInt);
                }

                @Override // java.util.Iterator
                public void remove() {
                    strippedIntOpenHashSet.remove(this.storedId);
                }
            }, this.weirdFiles.iterator());
        }
        return new Iterator<VirtualFile>() { // from class: org.jetbrains.kotlin.com.intellij.openapi.vfs.CompactVirtualFileSet.2
            boolean hasNext;
            VirtualFile next;

            {
                findNext();
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.hasNext;
            }

            private void findNext() {
                this.hasNext = false;
                while (itConcatIterators.hasNext()) {
                    ProgressManager.checkCanceled();
                    VirtualFile virtualFile = (VirtualFile) itConcatIterators.next();
                    if (virtualFile != null) {
                        this.next = virtualFile;
                        this.hasNext = true;
                        return;
                    }
                }
            }

            @Override // java.util.Iterator
            public VirtualFile next() {
                if (!this.hasNext) {
                    throw new NoSuchElementException();
                }
                VirtualFile virtualFile = this.next;
                findNext();
                return virtualFile;
            }

            @Override // java.util.Iterator
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    static /* synthetic */ VirtualFile lambda$iterator$2(VirtualFileManager virtualFileManager, int i) {
        ProgressManager.checkCanceled();
        return virtualFileManager.findFileById(i);
    }

    private static boolean contains(int i, StrippedIntOpenHashSet strippedIntOpenHashSet, BitSet bitSet, Set<? extends VirtualFile> set) {
        if (set == null) {
            $$$reportNull$$$0(9);
        }
        if (strippedIntOpenHashSet != null && strippedIntOpenHashSet.contains(i)) {
            return true;
        }
        if (bitSet != null && bitSet.get(i)) {
            return true;
        }
        for (ModificationTracker modificationTracker : set) {
            if ((modificationTracker instanceof VirtualFileWithId) && ((VirtualFileWithId) modificationTracker).getId() == i) {
                return true;
            }
        }
        return false;
    }
}
