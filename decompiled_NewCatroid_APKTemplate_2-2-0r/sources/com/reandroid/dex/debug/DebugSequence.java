package com.reandroid.dex.debug;

import com.reandroid.arsc.base.Block;
import com.reandroid.arsc.container.BlockList;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.arsc.item.IntegerReference;
import com.reandroid.dex.base.FixedDexContainer;
import com.reandroid.dex.data.DebugInfo;
import com.reandroid.dex.data.InstructionList;
import com.reandroid.dex.id.IdItem;
import com.reandroid.dex.smali.model.SmaliCodeSet;
import com.reandroid.dex.smali.model.SmaliDebugElement;
import com.reandroid.utils.collection.ComputeIterator;
import com.reandroid.utils.collection.FilterIterator;
import com.reandroid.utils.collection.IterableIterator;
import java.io.IOException;
import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes11.dex */
public class DebugSequence extends FixedDexContainer implements Iterable<DebugElement> {
    private BlockList<DebugElement> elementList;
    private final IntegerReference lineStart;

    public DebugSequence(IntegerReference integerReference) {
        super(2);
        this.lineStart = integerReference;
        addChild(1, DebugEndSequence.INSTANCE);
    }

    public <T1 extends DebugElement> T1 getOrCreateAtAddress(DebugElementType<T1> debugElementType, int i) {
        Iterator<T1> it = iterator(debugElementType);
        T1 t1 = null;
        while (it.hasNext()) {
            T1 next = it.next();
            int targetAddress = next.getTargetAddress();
            if (targetAddress == i) {
                return next;
            }
            if (targetAddress > i) {
                break;
            }
            t1 = next;
        }
        T1 t2 = (T1) createAtPosition(debugElementType, t1 != null ? t1.getIndex() + 1 : 0);
        t2.setTargetAddress(i);
        return t2;
    }

    static /* synthetic */ boolean lambda$getAtAddress$0(int i, DebugElement debugElement) {
        return i == debugElement.getTargetAddress();
    }

    public Iterator<DebugElement> getAtAddress(final int i) {
        return FilterIterator.of(iterator(), new Predicate() { // from class: com.reandroid.dex.debug.DebugSequence$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return DebugSequence.lambda$getAtAddress$0(i, (DebugElement) obj);
            }
        });
    }

    public void removeInvalid() {
        for (int size = size() - 1; size >= 0; size--) {
            DebugElement debugElement = get(size);
            if (!debugElement.isValid()) {
                remove(debugElement);
            }
        }
    }

    public int getLineStart() {
        return this.lineStart.get();
    }

    public void setLineStart(int i) {
        if (i == getLineStart()) {
            return;
        }
        setLineStartInternal(i);
        cacheValues();
    }

    void setLineStartInternal(int i) {
        this.lineStart.set(i);
    }

    public Iterator<DebugElement> getExtraLines() {
        return new FilterIterator(iterator(), new Predicate() { // from class: com.reandroid.dex.debug.DebugSequence$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return DebugSequence.lambda$getExtraLines$1((DebugElement) obj);
            }
        });
    }

    static /* synthetic */ boolean lambda$getExtraLines$1(DebugElement debugElement) {
        return !(debugElement instanceof DebugAdvance);
    }

    public void fixDebugLineNumbers() {
        removeAll(new Predicate<DebugElement>() { // from class: com.reandroid.dex.debug.DebugSequence.1
            DebugElement previous = null;

            @Override // java.util.function.Predicate
            public boolean test(DebugElement debugElement) {
                if (debugElement.getElementType() != DebugElementType.LINE_NUMBER) {
                    return false;
                }
                DebugElement debugElement2 = this.previous;
                if (debugElement2 != null && debugElement2.getTargetAddress() == debugElement.getTargetAddress()) {
                    this.previous = debugElement;
                    return true;
                }
                this.previous = debugElement;
                return false;
            }
        });
    }

    static /* synthetic */ boolean lambda$removeInvalid$2(InstructionList instructionList, DebugElement debugElement) {
        return debugElement.getElementType() == DebugElementType.LINE_NUMBER && instructionList.getAtAddress(debugElement.getTargetAddress()) == null;
    }

    public boolean removeInvalid(final InstructionList instructionList) {
        return removeAll(new Predicate() { // from class: com.reandroid.dex.debug.DebugSequence$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return DebugSequence.lambda$removeInvalid$2(instructionList, (DebugElement) obj);
            }
        });
    }

    public boolean removeAll(Predicate<? super DebugElement> predicate) {
        Iterator itOf = FilterIterator.of(clonedIterator(), predicate);
        boolean z = false;
        while (itOf.hasNext()) {
            if (removeInternal((DebugElement) itOf.next())) {
                z = true;
            }
        }
        if (z) {
            updateValues();
        }
        return z;
    }

    public boolean remove(DebugElement debugElement) {
        if (debugElement == null || debugElement.getParent(getClass()) != this) {
            return false;
        }
        boolean zRemoveInternal = removeInternal(debugElement);
        if (zRemoveInternal) {
            updateValues();
        }
        return zRemoveInternal;
    }

    private boolean removeInternal(DebugElement debugElement) {
        debugElement.onPreRemove(this);
        boolean zRemove = getElementList().remove(debugElement);
        if (zRemove) {
            debugElement.setParent(null);
            debugElement.setIndex(-1);
        }
        return zRemove;
    }

    public <T1 extends DebugElement> T1 createAtPosition(DebugElementType<T1> debugElementType, int i) {
        T1 t1 = (T1) debugElementType.newInstance();
        add(i, t1);
        return t1;
    }

    public <T1 extends DebugElement> T1 createNext(DebugElementType<T1> debugElementType) {
        if (debugElementType == DebugElementType.END_SEQUENCE) {
            return DebugEndSequence.INSTANCE;
        }
        T1 t1 = (T1) debugElementType.newInstance();
        add(t1);
        return t1;
    }

    @Override // com.reandroid.dex.base.FixedDexContainer, com.reandroid.arsc.base.BlockContainer, com.reandroid.arsc.base.Block
    public void onReadBytes(BlockReader blockReader) throws IOException {
        int position = blockReader.getPosition();
        int i = 0;
        while (blockReader.read() != 0) {
            i++;
        }
        if (i == 0) {
            return;
        }
        blockReader.seek(position);
        BlockList<DebugElement> blockListUnlockElementList = unlockElementList();
        unlockElementList().ensureCapacity(i);
        DebugElementType<?> next = readNext(blockReader);
        while (!next.is(DebugElementType.END_SEQUENCE)) {
            next = readNext(blockReader);
        }
        blockListUnlockElementList.trimToSize();
        cacheValues();
    }

    private DebugElementType<?> readNext(BlockReader blockReader) throws IOException {
        Block blockNewInstance;
        DebugElementType<?> flag = DebugElementType.readFlag(blockReader);
        if (flag == DebugElementType.END_SEQUENCE) {
            blockNewInstance = DebugEndSequence.INSTANCE;
        } else {
            blockNewInstance = flag.newInstance();
            unlockElementList().add(blockNewInstance);
        }
        blockNewInstance.readBytes(blockReader);
        return flag;
    }

    private void cacheValues() {
        DebugElement debugElement = null;
        for (DebugElement debugElement2 : this) {
            debugElement2.cacheValues(this, debugElement);
            debugElement = debugElement2;
        }
    }

    private void updateValues() {
        Iterator<DebugElement> itClonedIterator = clonedIterator();
        DebugElement debugElement = null;
        while (itClonedIterator.hasNext()) {
            DebugElement next = itClonedIterator.next();
            next.updateValues(this, debugElement);
            debugElement = next;
        }
    }

    public DebugElement get(int i) {
        return (DebugElement) getElementList().get(i);
    }

    public void add(int i, DebugElement debugElement) {
        unlockElementList().add(i, debugElement);
    }

    public <T1 extends DebugElement> Iterator<T1> iterator(final DebugElementType<T1> debugElementType) {
        return ComputeIterator.of(iterator(), new Function() { // from class: com.reandroid.dex.debug.DebugSequence$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return DebugSequence.lambda$iterator$3(debugElementType, (DebugElement) obj);
            }
        });
    }

    static /* synthetic */ DebugElement lambda$iterator$3(DebugElementType debugElementType, DebugElement debugElement) {
        if (debugElement.getElementType() == debugElementType) {
            return debugElement;
        }
        return null;
    }

    public int size() {
        return getElementList().getCount();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    @Override // java.lang.Iterable
    public Iterator<DebugElement> iterator() {
        return getElementList().iterator();
    }

    public Iterator<DebugElement> clonedIterator() {
        return getElementList().clonedIterator();
    }

    public boolean add(DebugElement debugElement) {
        if (debugElement == null || debugElement.getClass() == DebugEndSequence.class) {
            return false;
        }
        return unlockElementList().add(debugElement);
    }

    public void clear() {
        getElementList().clearChildes();
    }

    public boolean isRemoved() {
        DebugInfo debugInfo = (DebugInfo) getParentInstance(DebugInfo.class);
        if (debugInfo != null) {
            return debugInfo.isRemoved();
        }
        return true;
    }

    public Iterator<IdItem> usedIds() {
        return new IterableIterator<DebugElement, IdItem>(iterator()) { // from class: com.reandroid.dex.debug.DebugSequence.2
            @Override // com.reandroid.utils.collection.IterableIterator
            public Iterator<IdItem> iterator(DebugElement debugElement) {
                return debugElement.usedIds();
            }
        };
    }

    private BlockList<DebugElement> unlockElementList() {
        BlockList<DebugElement> blockList = this.elementList;
        if (blockList != null && !BlockList.isImmutableEmpty(blockList)) {
            return blockList;
        }
        BlockList<DebugElement> blockList2 = new BlockList<>();
        this.elementList = blockList2;
        addChild(0, blockList2);
        return blockList2;
    }

    private BlockList<DebugElement> getElementList() {
        BlockList<DebugElement> blockList = this.elementList;
        return (blockList == null || isRemoved()) ? BlockList.empty() : blockList;
    }

    public void merge(DebugSequence debugSequence) {
        this.lineStart.set(debugSequence.lineStart.get());
        int size = debugSequence.size();
        if (size == 0) {
            return;
        }
        unlockElementList().ensureCapacity(size);
        for (int i = 0; i < size; i++) {
            DebugElement debugElement = debugSequence.get(i);
            createNext(debugElement.getElementType()).merge(debugElement);
        }
        cacheValues();
        getElementList().trimToSize();
    }

    public void fromSmali(SmaliCodeSet smaliCodeSet) throws IOException {
        Iterator<SmaliDebugElement> debugElements = smaliCodeSet.getDebugElements();
        while (debugElements.hasNext()) {
            SmaliDebugElement next = debugElements.next();
            createNext(next.getDebugElementType()).fromSmali(next);
        }
    }

    public int hashCode() {
        return getElementList().hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return getElementList().equals(((DebugSequence) obj).getElementList());
    }

    public String toString() {
        return "start=" + this.lineStart + ", elements=" + getElementList();
    }
}
