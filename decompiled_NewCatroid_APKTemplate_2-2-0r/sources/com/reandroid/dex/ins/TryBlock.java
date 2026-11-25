package com.reandroid.dex.ins;

import com.reandroid.arsc.base.Creator;
import com.reandroid.arsc.container.BlockList;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.arsc.item.ByteArray;
import com.reandroid.dex.base.DexPositionAlign;
import com.reandroid.dex.base.Ule128Item;
import com.reandroid.dex.data.CodeItem;
import com.reandroid.dex.data.FixedDexContainerWithTool;
import com.reandroid.dex.data.InstructionList;
import com.reandroid.dex.smali.model.SmaliCodeTryItem;
import com.reandroid.utils.collection.EmptyIterator;
import com.reandroid.utils.collection.ExpandIterator;
import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;

/* JADX INFO: loaded from: classes11.dex */
public class TryBlock extends FixedDexContainerWithTool implements Creator<TryItem>, Iterable<TryItem>, LabelsSet {
    private static final int INDEX_itemArray = 3;
    private static final int INDEX_itemsCount = 1;
    private static final int INDEX_offsetArray = 0;
    private static final int INDEX_positionAlign = 4;
    private static final int INDEX_unknownBytes = 2;
    private final CodeItem codeItem;
    private HandlerOffsetArray handlerOffsetArray;
    private DexPositionAlign positionAlign;
    private BlockList<TryItem> tryItemArray;
    private Ule128Item tryItemsCount;
    private ByteArray unknownBytes;

    public TryBlock(CodeItem codeItem) {
        super(5);
        this.codeItem = codeItem;
    }

    InstructionList getInstructionList() {
        return getCodeItem().getInstructionList();
    }

    private CodeItem getCodeItem() {
        return this.codeItem;
    }

    public int getTryItemCount() {
        if (isNull()) {
            return 0;
        }
        return this.tryItemArray.getCount();
    }

    public int getRealTryItemCount() {
        BlockList<TryItem> blockList = this.tryItemArray;
        if (blockList == null) {
            return 0;
        }
        int size = blockList.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            if (!((TryItem) blockList.get(i2)).isCopy()) {
                i++;
            }
        }
        return i;
    }

    @Override // com.reandroid.dex.ins.LabelsSet
    public Iterator<Label> getLabels() {
        return new ExpandIterator(iterator());
    }

    public TryItem createNext() {
        initialize();
        TryItem tryItemNewInstance = newInstance();
        add(tryItemNewInstance);
        return tryItemNewInstance;
    }

    private void add(TryItem tryItem) {
        BlockList<TryItem> blockList = this.tryItemArray;
        if (blockList != null) {
            blockList.add(tryItem);
            this.handlerOffsetArray.ensureSize(this.tryItemArray.size());
        }
    }

    public TryItem get(int i) {
        BlockList<TryItem> blockList = this.tryItemArray;
        if (blockList != null) {
            return (TryItem) blockList.get(i);
        }
        return null;
    }

    @Override // java.lang.Iterable
    public Iterator<TryItem> iterator() {
        if (isNull()) {
            return EmptyIterator.of();
        }
        return this.tryItemArray.iterator();
    }

    @Override // com.reandroid.arsc.container.FixedBlockContainer, com.reandroid.arsc.base.BlockContainer
    protected void onRefreshed() {
        super.onRefreshed();
        if (isNull()) {
            return;
        }
        updateHandlerOffsets();
        updateCount();
        this.positionAlign.align(this);
    }

    private void updateCount() {
        if (isNull()) {
            return;
        }
        this.tryItemsCount.set(getRealTryItemCount());
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void updateHandlerOffsets() {
        BlockList<TryItem> blockList = this.tryItemArray;
        if (blockList == null) {
            return;
        }
        int iCountBytes = this.tryItemsCount.countBytes();
        HandlerOffsetArray handlerOffsetArray = this.handlerOffsetArray;
        int count = blockList.getCount();
        handlerOffsetArray.setSize(count);
        for (int i = 0; i < count; i++) {
            ((HandlerOffset) handlerOffsetArray.get(i)).setOffset(blockList.countUpTo((TryItem) blockList.get(i)) + iCountBytes);
        }
    }

    private HandlerOffsetArray initHandlersOffset() {
        if (this.handlerOffsetArray == null) {
            HandlerOffsetArray handlerOffsetArray = new HandlerOffsetArray(getCodeItem().getTryCountReference());
            this.handlerOffsetArray = handlerOffsetArray;
            addChild(0, handlerOffsetArray);
        }
        return this.handlerOffsetArray;
    }

    private void initTryItemArray() {
        if (this.tryItemArray != null) {
            return;
        }
        Ule128Item ule128Item = new Ule128Item();
        this.tryItemsCount = ule128Item;
        addChild(1, ule128Item);
        BlockList<TryItem> blockList = new BlockList<>(this);
        this.tryItemArray = blockList;
        addChild(3, blockList);
    }

    @Override // com.reandroid.arsc.base.Block
    public void setNull(boolean z) {
        if (z == isNull()) {
            return;
        }
        if (z) {
            clear();
        } else {
            initialize();
        }
    }

    private void initialize() {
        initHandlersOffset();
        initTryItemArray();
        if (this.positionAlign == null) {
            DexPositionAlign dexPositionAlign = new DexPositionAlign();
            this.positionAlign = dexPositionAlign;
            addChild(4, dexPositionAlign);
        }
    }

    private void clear() {
        HandlerOffsetArray handlerOffsetArray = this.handlerOffsetArray;
        if (handlerOffsetArray != null) {
            handlerOffsetArray.setParent(null);
            this.handlerOffsetArray.setIndex(-1);
            this.handlerOffsetArray = null;
        }
        Ule128Item ule128Item = this.tryItemsCount;
        if (ule128Item != null) {
            ule128Item.setParent(null);
            this.tryItemsCount.setIndex(-1);
            this.tryItemArray = null;
        }
        BlockList<TryItem> blockList = this.tryItemArray;
        if (blockList != null) {
            blockList.clearChildes();
            this.tryItemArray.setParent(null);
            this.tryItemArray.setIndex(-1);
            this.tryItemArray = null;
        }
        DexPositionAlign dexPositionAlign = this.positionAlign;
        if (dexPositionAlign != null) {
            dexPositionAlign.setParent(null);
            this.positionAlign.setIndex(-1);
            this.positionAlign = null;
        }
        addChild(0, null);
        addChild(1, null);
        addChild(2, null);
        addChild(3, null);
        addChild(4, null);
    }

    @Override // com.reandroid.arsc.base.Block
    public boolean isNull() {
        return this.tryItemArray == null;
    }

    public void remove(TryItem tryItem) {
        BlockList<TryItem> blockList = this.tryItemArray;
        if (blockList == null || !blockList.remove(tryItem)) {
            return;
        }
        tryItem.onRemove();
    }

    public void onRemove() {
        BlockList<TryItem> blockList = this.tryItemArray;
        if (blockList != null) {
            this.tryItemArray = null;
            int count = blockList.getCount();
            for (int i = 0; i < count; i++) {
                ((TryItem) blockList.getLast()).onRemove();
            }
            blockList.clearChildes();
        }
        HandlerOffsetArray handlerOffsetArray = this.handlerOffsetArray;
        if (handlerOffsetArray != null) {
            handlerOffsetArray.setSize(0);
            this.handlerOffsetArray = null;
        }
        if (this.positionAlign != null) {
            this.positionAlign = null;
        }
    }

    @Override // com.reandroid.dex.base.FixedDexContainer, com.reandroid.arsc.base.BlockContainer, com.reandroid.arsc.base.Block
    public void onReadBytes(BlockReader blockReader) throws IOException {
        boolean z = getCodeItem().getTryCountReference().get() == 0;
        setNull(z);
        if (z) {
            return;
        }
        this.handlerOffsetArray.onReadBytes(blockReader);
        this.tryItemsCount.onReadBytes(blockReader);
        readUnknownBytes(blockReader);
        this.tryItemArray.setSize(this.handlerOffsetArray.size());
        this.tryItemArray.readChildes(blockReader);
        this.positionAlign.onReadBytes(blockReader);
    }

    private void setUnknownBytes(int i) {
        if (i <= 0 || isNull()) {
            ByteArray byteArray = this.unknownBytes;
            if (byteArray != null) {
                byteArray.setParent(null);
                byteArray.setIndex(-1);
                byteArray.setSize(0);
                this.unknownBytes = null;
                return;
            }
            return;
        }
        ByteArray byteArray2 = this.unknownBytes;
        if (byteArray2 == null) {
            ByteArray byteArray3 = new ByteArray(i);
            this.unknownBytes = byteArray3;
            byteArray3.setParent(this);
            addChild(2, byteArray3);
            return;
        }
        byteArray2.setSize(i);
    }

    private void readUnknownBytes(BlockReader blockReader) throws IOException {
        setUnknownBytes(this.handlerOffsetArray.getMinStart() - this.tryItemsCount.countBytes());
        ByteArray byteArray = this.unknownBytes;
        if (byteArray != null) {
            byteArray.readBytes(blockReader);
        }
    }

    public DexPositionAlign getPositionAlign() {
        return this.positionAlign;
    }

    @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockCreator
    public TryItem newInstance() {
        return new TryItem(initHandlersOffset());
    }

    @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockArrayCreator
    public TryItem[] newArrayInstance(int i) {
        return new TryItem[i];
    }

    @Override // com.reandroid.arsc.base.Creator
    public TryItem newInstanceAt(int i) {
        TryItem tryItemNewCopy;
        BlockList<TryItem> blockList = this.tryItemArray;
        HandlerOffsetArray handlerOffsetArrayInitHandlersOffset = initHandlersOffset();
        if (blockList.size() < 2) {
            return new TryItem(handlerOffsetArrayInitHandlersOffset);
        }
        int iIndexOf = handlerOffsetArrayInitHandlersOffset.indexOf(handlerOffsetArrayInitHandlersOffset.getOffset(i));
        if (iIndexOf < 0 || iIndexOf >= i) {
            tryItemNewCopy = null;
        } else {
            tryItemNewCopy = (TryItem) blockList.get(iIndexOf);
            if (tryItemNewCopy != null) {
                tryItemNewCopy = tryItemNewCopy.newCopy();
            }
        }
        return tryItemNewCopy == null ? new TryItem(handlerOffsetArrayInitHandlersOffset) : tryItemNewCopy;
    }

    public void merge(TryBlock tryBlock) {
        TryItem tryItemNewInstance;
        boolean zIsNull = tryBlock.isNull();
        setNull(zIsNull);
        if (zIsNull) {
            return;
        }
        int tryItemCount = tryBlock.getTryItemCount();
        for (int i = 0; i < tryItemCount; i++) {
            TryItem tryItem = tryBlock.get(i);
            TryItem tryItem2 = tryItem.getTryItem();
            if (tryItem != tryItem2) {
                tryItemNewInstance = get(tryItem2.getIndex()).newCopy();
            } else {
                tryItemNewInstance = newInstance();
            }
            add(tryItemNewInstance);
            tryItemNewInstance.merge(tryItem);
        }
        updateHandlerOffsets();
        updateCount();
    }

    public void fromSmali(SmaliCodeTryItem smaliCodeTryItem) {
        initialize();
        TryItem tryItemNewInstance = newInstance();
        add(tryItemNewInstance);
        initHandlersOffset().setSize(this.tryItemArray.size());
        tryItemNewInstance.fromSmali(smaliCodeTryItem);
        updateHandlerOffsets();
        updateCount();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        TryBlock tryBlock = (TryBlock) obj;
        if (isNull()) {
            return tryBlock.isNull();
        }
        return Objects.equals(this.handlerOffsetArray, tryBlock.handlerOffsetArray) && Objects.equals(this.tryItemArray, tryBlock.tryItemArray);
    }

    public int hashCode() {
        HandlerOffsetArray handlerOffsetArray = this.handlerOffsetArray;
        int iHashCode = handlerOffsetArray != null ? handlerOffsetArray.hashCode() + 31 : 31;
        BlockList<TryItem> blockList = this.tryItemArray;
        int i = iHashCode * 31;
        return blockList != null ? i + blockList.hashCode() : i;
    }

    public String toString() {
        if (isNull()) {
            return "NULL";
        }
        return "tryItems = " + this.tryItemArray.toString() + ", bytes=" + countBytes();
    }
}
