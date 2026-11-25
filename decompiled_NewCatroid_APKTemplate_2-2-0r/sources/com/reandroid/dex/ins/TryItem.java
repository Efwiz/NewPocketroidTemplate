package com.reandroid.dex.ins;

import com.reandroid.arsc.base.Block;
import com.reandroid.arsc.base.BlockCounter;
import com.reandroid.arsc.container.BlockList;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.dex.base.Sle128Item;
import com.reandroid.dex.data.FixedDexContainerWithTool;
import com.reandroid.dex.data.InstructionList;
import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.smali.model.SmaliCodeCatch;
import com.reandroid.dex.smali.model.SmaliCodeCatchAll;
import com.reandroid.dex.smali.model.SmaliCodeTryItem;
import com.reandroid.dex.smali.model.SmaliSet;
import com.reandroid.utils.collection.ArrayCollection;
import com.reandroid.utils.collection.CombiningIterator;
import com.reandroid.utils.collection.EmptyIterator;
import com.reandroid.utils.collection.ExpandIterator;
import com.reandroid.utils.collection.SingleIterator;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Objects;

/* JADX INFO: loaded from: classes11.dex */
public class TryItem extends FixedDexContainerWithTool implements Iterable<Label> {
    private CatchAllHandler catchAllHandler;
    private final BlockList<CatchTypedHandler> catchTypedHandlerList;
    private final HandlerOffsetArray handlerOffsetArray;
    final Sle128Item handlersCount;
    private HandlerOffset mHandlerOffset;

    TryItem getTryItem() {
        return this;
    }

    public boolean isCopy() {
        return false;
    }

    public TryItem(HandlerOffsetArray handlerOffsetArray) {
        super(3);
        this.handlerOffsetArray = handlerOffsetArray;
        Sle128Item sle128Item = new Sle128Item();
        this.handlersCount = sle128Item;
        BlockList<CatchTypedHandler> blockList = new BlockList<>();
        this.catchTypedHandlerList = blockList;
        addChild(0, sle128Item);
        addChild(1, blockList);
    }

    private TryItem() {
        super(0);
        this.handlerOffsetArray = null;
        this.handlersCount = null;
        this.catchTypedHandlerList = null;
    }

    InstructionList getInstructionList() {
        return getTryBlock().getInstructionList();
    }

    TryBlock getTryBlock() {
        return (TryBlock) getParent(TryBlock.class);
    }

    TryItem newCopy() {
        return new Copy(this);
    }

    HandlerOffset getHandlerOffset() {
        HandlerOffset handlerOffset = this.mHandlerOffset;
        if (handlerOffset != null) {
            return handlerOffset;
        }
        HandlerOffset orCreate = getHandlerOffsetArray().getOrCreate(getIndex());
        this.mHandlerOffset = orCreate;
        orCreate.setTryItem(this);
        return orCreate;
    }

    HandlerOffsetArray getHandlerOffsetArray() {
        return this.handlerOffsetArray;
    }

    BlockList<CatchTypedHandler> getCatchTypedHandlerBlockList() {
        return this.catchTypedHandlerList;
    }

    Iterator<CatchTypedHandler> getCatchTypedHandlers() {
        return this.catchTypedHandlerList.iterator();
    }

    void updateCount() {
        Sle128Item sle128Item = this.handlersCount;
        if (sle128Item == null) {
            return;
        }
        int size = this.catchTypedHandlerList.size();
        if (hasCatchAllHandler()) {
            size = -size;
        }
        sle128Item.set(size);
    }

    @Override // java.lang.Iterable
    public Iterator<Label> iterator() {
        return new ExpandIterator(getExceptionHandlers());
    }

    public int getCatchTypedHandlersCount() {
        return getCatchTypedHandlerBlockList().size();
    }

    public Iterator<ExceptionHandler> getExceptionHandlers() {
        Iterator itOf = EmptyIterator.of();
        CatchAllHandler catchAllHandler = getCatchAllHandler();
        if (catchAllHandler != null) {
            itOf = SingleIterator.of(catchAllHandler);
        }
        return new CombiningIterator(getCatchTypedHandlers(), itOf);
    }

    public ExceptionHandler getExceptionHandler(TypeKey typeKey) {
        Iterator<CatchTypedHandler> catchTypedHandlers = getCatchTypedHandlers();
        while (catchTypedHandlers.hasNext()) {
            CatchTypedHandler next = catchTypedHandlers.next();
            if (typeKey.equals(next.getKey())) {
                return next;
            }
        }
        return null;
    }

    public int getStartAddress() {
        return getHandlerOffset().getStartAddress();
    }

    public void setStartAddress(int i) {
        getHandlerOffset().setStartAddress(i);
    }

    public int getCatchCodeUnit() {
        return getHandlerOffset().getCatchCodeUnit();
    }

    public void setCatchCodeUnit(int i) {
        getHandlerOffset().setCatchCodeUnit(i);
    }

    public boolean hasCatchAllHandler() {
        return getCatchAllHandler() != null;
    }

    public CatchAllHandler getCatchAllHandler() {
        return this.catchAllHandler;
    }

    public CatchAllHandler getOrCreateCatchAll() {
        CatchAllHandler catchAllHandler = getCatchAllHandler();
        if (catchAllHandler != null) {
            return catchAllHandler;
        }
        initCatchAllHandler();
        return getCatchAllHandler();
    }

    private CatchAllHandler initCatchAllHandler() {
        CatchAllHandler catchAllHandler = getCatchAllHandler();
        if (catchAllHandler != null) {
            return catchAllHandler;
        }
        CatchAllHandler catchAllHandler2 = new CatchAllHandler();
        addChild(2, catchAllHandler2);
        this.catchAllHandler = catchAllHandler2;
        return catchAllHandler2;
    }

    @Override // com.reandroid.arsc.container.FixedBlockContainer, com.reandroid.arsc.base.BlockContainer
    protected void onRefreshed() {
        super.onRefreshed();
        updateCount();
    }

    @Override // com.reandroid.dex.base.FixedDexContainer, com.reandroid.arsc.base.BlockContainer, com.reandroid.arsc.base.Block
    public void onReadBytes(BlockReader blockReader) throws IOException {
        boolean z;
        int position = blockReader.getPosition();
        blockReader.seek(getHandlerOffsetArray().getItemsStart() + getHandlerOffset().getOffset());
        this.handlersCount.readBytes(blockReader);
        int i = this.handlersCount.get();
        if (i <= 0) {
            i = -i;
            z = true;
        } else {
            z = false;
        }
        BlockList<CatchTypedHandler> catchTypedHandlerBlockList = getCatchTypedHandlerBlockList();
        catchTypedHandlerBlockList.ensureCapacity(i);
        for (int i2 = 0; i2 < i; i2++) {
            CatchTypedHandler catchTypedHandler = new CatchTypedHandler();
            catchTypedHandlerBlockList.add(catchTypedHandler);
            catchTypedHandler.readBytes(blockReader);
        }
        if (z) {
            initCatchAllHandler().readBytes(blockReader);
        }
        if (position > blockReader.getPosition()) {
            blockReader.seek(position);
        }
    }

    @Override // com.reandroid.arsc.base.BlockContainer, com.reandroid.arsc.base.Block
    public void onCountUpTo(BlockCounter blockCounter) {
        if (blockCounter.FOUND) {
            return;
        }
        Block block = blockCounter.END;
        if ((block instanceof Copy) && ((Copy) block).getTryItem() == this) {
            blockCounter.FOUND = true;
        } else {
            super.onCountUpTo(blockCounter);
        }
    }

    public void removeSelf() {
        TryBlock tryBlock = getTryBlock();
        if (tryBlock != null) {
            tryBlock.remove(this);
        }
    }

    public void remove(ExceptionHandler exceptionHandler) {
        BlockList<CatchTypedHandler> blockList;
        if (exceptionHandler == null) {
            return;
        }
        if (exceptionHandler == this.catchAllHandler) {
            exceptionHandler.onRemove();
            this.catchAllHandler = null;
        } else if ((exceptionHandler instanceof CatchTypedHandler) && (blockList = this.catchTypedHandlerList) != null && blockList.contains(exceptionHandler)) {
            this.catchTypedHandlerList.remove((CatchTypedHandler) exceptionHandler);
            exceptionHandler.onRemove();
        }
    }

    public void onRemove() {
        HandlerOffset handlerOffset = this.mHandlerOffset;
        BlockList<CatchTypedHandler> blockList = this.catchTypedHandlerList;
        if (blockList != null) {
            int size = blockList.size();
            for (int i = 0; i < size; i++) {
                CatchTypedHandler catchTypedHandler = (CatchTypedHandler) blockList.get(i);
                catchTypedHandler.onRemove();
                catchTypedHandler.setParent(null);
            }
            blockList.destroy();
        }
        remove(this.catchAllHandler);
        if (handlerOffset != null) {
            this.mHandlerOffset = null;
            handlerOffset.removeSelf();
        }
        setParent(null);
    }

    public void merge(TryItem tryItem) {
        mergeOffset(tryItem);
        mergeHandlers(tryItem);
    }

    void mergeHandlers(TryItem tryItem) {
        BlockList<CatchTypedHandler> catchTypedHandlerBlockList = tryItem.getCatchTypedHandlerBlockList();
        int size = catchTypedHandlerBlockList.size();
        BlockList<CatchTypedHandler> catchTypedHandlerBlockList2 = getCatchTypedHandlerBlockList();
        catchTypedHandlerBlockList2.ensureCapacity(size);
        for (int i = 0; i < size; i++) {
            CatchTypedHandler catchTypedHandler = (CatchTypedHandler) catchTypedHandlerBlockList.get(i);
            CatchTypedHandler catchTypedHandler2 = new CatchTypedHandler();
            catchTypedHandlerBlockList2.add(catchTypedHandler2);
            catchTypedHandler2.merge(catchTypedHandler);
        }
        if (tryItem.hasCatchAllHandler()) {
            initCatchAllHandler().merge(tryItem.getCatchAllHandler());
        }
        updateCount();
    }

    void mergeOffset(TryItem tryItem) {
        HandlerOffset handlerOffset = tryItem.getHandlerOffset();
        HandlerOffset handlerOffset2 = getHandlerOffset();
        handlerOffset2.setCatchCodeUnit(handlerOffset.getCatchCodeUnit());
        handlerOffset2.setStartAddress(handlerOffset.getStartAddress());
    }

    public void fromSmali(SmaliCodeTryItem smaliCodeTryItem) {
        setStartAddress(smaliCodeTryItem.getStartAddress());
        SmaliSet<SmaliCodeCatch> catchSet = smaliCodeTryItem.getCatchSet();
        BlockList<CatchTypedHandler> catchTypedHandlerBlockList = getCatchTypedHandlerBlockList();
        int size = catchSet.size();
        for (int i = 0; i < size; i++) {
            SmaliCodeCatch smaliCodeCatch = (SmaliCodeCatch) catchSet.get(i);
            CatchTypedHandler catchTypedHandler = new CatchTypedHandler();
            catchTypedHandlerBlockList.add(catchTypedHandler);
            catchTypedHandler.fromSmali(smaliCodeCatch);
        }
        SmaliCodeCatchAll catchAll = smaliCodeTryItem.getCatchAll();
        if (catchAll != null) {
            initCatchAllHandler().fromSmali(catchAll);
        }
        updateCount();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        TryItem tryItem = (TryItem) obj;
        return Objects.equals(this.catchTypedHandlerList, tryItem.catchTypedHandlerList) && Objects.equals(this.catchAllHandler, tryItem.catchAllHandler);
    }

    public int hashCode() {
        BlockList<CatchTypedHandler> blockList = this.catchTypedHandlerList;
        int iHashCode = blockList != null ? blockList.hashCode() + 961 : 31;
        int i = iHashCode * 31;
        CatchAllHandler catchAllHandler = this.catchAllHandler;
        return catchAllHandler != null ? (iHashCode * 961) + catchAllHandler.hashCode() : i;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        Iterator<ExceptionHandler> exceptionHandlers = getExceptionHandlers();
        while (exceptionHandlers.hasNext()) {
            if (sb.length() != 0) {
                sb.append('\n');
            }
            sb.append(exceptionHandlers.next());
        }
        return sb.toString();
    }

    static class Copy extends TryItem {
        private CatchAllHandler mCatchAllHandler;
        private CatchAllHandler mOriginalCatchAllHandler;
        private ArrayCollection<CatchTypedHandler> mTypeHandlerList;
        private final TryItem tryItem;

        @Override // com.reandroid.arsc.base.BlockContainer, com.reandroid.arsc.base.Block
        public int countBytes() {
            return 0;
        }

        @Override // com.reandroid.arsc.base.BlockContainer, com.reandroid.arsc.base.Block
        public byte[] getBytes() {
            return null;
        }

        @Override // com.reandroid.dex.ins.TryItem
        public boolean isCopy() {
            return true;
        }

        @Override // com.reandroid.arsc.base.BlockContainer
        protected void onPreRefresh() {
        }

        @Override // com.reandroid.arsc.base.BlockContainer, com.reandroid.arsc.base.Block
        public int onWriteBytes(OutputStream outputStream) throws IOException {
            return 0;
        }

        @Override // com.reandroid.dex.ins.TryItem
        void updateCount() {
        }

        public Copy(TryItem tryItem) {
            super();
            this.tryItem = tryItem;
        }

        @Override // com.reandroid.dex.ins.TryItem
        TryBlock getTryBlock() {
            return this.tryItem.getTryBlock();
        }

        @Override // com.reandroid.dex.ins.TryItem
        TryItem newCopy() {
            return this.tryItem.newCopy();
        }

        @Override // com.reandroid.dex.ins.TryItem
        HandlerOffsetArray getHandlerOffsetArray() {
            return this.tryItem.getHandlerOffsetArray();
        }

        @Override // com.reandroid.dex.ins.TryItem
        Iterator<CatchTypedHandler> getCatchTypedHandlers() {
            return getTypeHandlerList().iterator();
        }

        private ArrayCollection<CatchTypedHandler> getTypeHandlerList() {
            ArrayCollection<CatchTypedHandler> arrayCollection = this.mTypeHandlerList;
            BlockList<CatchTypedHandler> catchTypedHandlerBlockList = getCatchTypedHandlerBlockList();
            if (arrayCollection == null || arrayCollection.size() != catchTypedHandlerBlockList.size()) {
                arrayCollection = new ArrayCollection<>();
                this.mTypeHandlerList = arrayCollection;
                Iterator<T> it = catchTypedHandlerBlockList.iterator();
                while (it.hasNext()) {
                    arrayCollection.add(((CatchTypedHandler) it.next()).newCopy(this));
                }
            }
            return arrayCollection;
        }

        @Override // com.reandroid.dex.ins.TryItem
        BlockList<CatchTypedHandler> getCatchTypedHandlerBlockList() {
            return this.tryItem.getCatchTypedHandlerBlockList();
        }

        @Override // com.reandroid.dex.ins.TryItem
        TryItem getTryItem() {
            return this.tryItem.getTryItem();
        }

        @Override // com.reandroid.dex.ins.TryItem
        public CatchAllHandler getCatchAllHandler() {
            CatchAllHandler catchAllHandler = this.tryItem.getCatchAllHandler();
            if (catchAllHandler != this.mOriginalCatchAllHandler) {
                this.mOriginalCatchAllHandler = catchAllHandler;
                this.mCatchAllHandler = null;
            }
            if (catchAllHandler == null) {
                this.mCatchAllHandler = null;
                return catchAllHandler;
            }
            CatchAllHandler catchAllHandler2 = this.mCatchAllHandler;
            if (catchAllHandler2 != null) {
                return catchAllHandler2;
            }
            CatchAllHandler catchAllHandlerNewCopy = catchAllHandler.newCopy(this);
            this.mCatchAllHandler = catchAllHandlerNewCopy;
            return catchAllHandlerNewCopy;
        }

        @Override // com.reandroid.dex.ins.TryItem
        public CatchAllHandler getOrCreateCatchAll() {
            this.tryItem.getOrCreateCatchAll();
            return getCatchAllHandler();
        }

        @Override // com.reandroid.dex.ins.TryItem, com.reandroid.arsc.container.FixedBlockContainer, com.reandroid.arsc.base.BlockContainer
        protected void onRefreshed() {
            clearCache();
        }

        @Override // com.reandroid.dex.ins.TryItem, com.reandroid.dex.base.FixedDexContainer, com.reandroid.arsc.base.BlockContainer, com.reandroid.arsc.base.Block
        public void onReadBytes(BlockReader blockReader) throws IOException {
            clearCache();
        }

        @Override // com.reandroid.dex.ins.TryItem
        void mergeHandlers(TryItem tryItem) {
            clearCache();
        }

        private void clearCache() {
            this.mTypeHandlerList = null;
            this.mCatchAllHandler = null;
        }
    }
}
