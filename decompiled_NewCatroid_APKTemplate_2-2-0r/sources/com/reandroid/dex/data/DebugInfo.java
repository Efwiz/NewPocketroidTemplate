package com.reandroid.dex.data;

import com.reandroid.arsc.base.Creator;
import com.reandroid.arsc.container.BlockList;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.dex.base.Ule128Item;
import com.reandroid.dex.debug.DebugElement;
import com.reandroid.dex.debug.DebugParameter;
import com.reandroid.dex.debug.DebugSequence;
import com.reandroid.dex.id.IdItem;
import com.reandroid.dex.key.DataKey;
import com.reandroid.dex.key.Key;
import com.reandroid.dex.key.ModifiableKeyItem;
import com.reandroid.dex.sections.SectionType;
import com.reandroid.utils.collection.CombiningIterator;
import com.reandroid.utils.collection.EmptyIterator;
import com.reandroid.utils.collection.IterableIterator;
import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;

/* JADX INFO: loaded from: classes11.dex */
public class DebugInfo extends DataItem implements ModifiableKeyItem {
    private static final Creator<DebugParameter> CREATOR = new Creator<DebugParameter>() { // from class: com.reandroid.dex.data.DebugInfo.2
        @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockArrayCreator
        public DebugParameter[] newArrayInstance(int i) {
            if (i == 0) {
                return DebugInfo.EMPTY;
            }
            return new DebugParameter[i];
        }

        @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockCreator
        public DebugParameter newInstance() {
            return new DebugParameter();
        }
    };
    static final DebugParameter[] EMPTY = new DebugParameter[0];
    private final DataKey<DebugInfo> debugKey;
    private final Ule128Item debugParameterCount;
    private BlockList<DebugParameter> debugParametersArray;
    private final DebugSequence debugSequence;
    private final Ule128Item lineStart;

    public DebugInfo() {
        super(4);
        Ule128Item ule128Item = new Ule128Item(true);
        this.lineStart = ule128Item;
        Ule128Item ule128Item2 = new Ule128Item();
        this.debugParameterCount = ule128Item2;
        DebugSequence debugSequence = new DebugSequence(ule128Item);
        this.debugSequence = debugSequence;
        addChild(0, ule128Item);
        addChild(1, ule128Item2);
        addChild(3, debugSequence);
        this.debugKey = new DataKey<>(this);
    }

    public void removeInvalidElements() {
        DebugSequence debugSequence = getDebugSequence();
        if (debugSequence != null) {
            debugSequence.removeInvalid();
        }
    }

    @Override // com.reandroid.dex.data.DataItem, com.reandroid.dex.common.SectionItem, com.reandroid.dex.key.KeyItem
    public DataKey<DebugInfo> getKey() {
        return this.debugKey;
    }

    @Override // com.reandroid.dex.key.ModifiableKeyItem
    public void setKey(Key key) {
        merge((DebugInfo) ((DataKey) key).getItem());
    }

    @Override // com.reandroid.dex.common.SectionItem
    public SectionType<DebugInfo> getSectionType() {
        return SectionType.DEBUG_INFO;
    }

    public int getParameterCount() {
        BlockList<DebugParameter> blockList = this.debugParametersArray;
        if (blockList != null) {
            return blockList.size();
        }
        return 0;
    }

    public DebugParameter getDebugParameter(int i) {
        BlockList<DebugParameter> blockList = this.debugParametersArray;
        if (blockList != null) {
            return (DebugParameter) blockList.get(i);
        }
        return null;
    }

    public DebugParameter getOrCreateDebugParameter(int i) {
        BlockList<DebugParameter> blockListInitParametersArray = initParametersArray();
        blockListInitParametersArray.ensureSize(i + 1);
        return (DebugParameter) blockListInitParametersArray.get(i);
    }

    public void removeDebugParameter(int i) {
        DebugParameter debugParameter;
        BlockList<DebugParameter> blockList = this.debugParametersArray;
        if (blockList == null || (debugParameter = (DebugParameter) blockList.get(i)) == null) {
            return;
        }
        if (i == blockList.getCount() - 1) {
            blockList.remove(debugParameter);
        } else {
            debugParameter.set(0);
        }
        blockList.refresh();
    }

    public Iterator<DebugParameter> getParameters() {
        BlockList<DebugParameter> blockList = this.debugParametersArray;
        if (blockList != null) {
            return blockList.iterator();
        }
        return EmptyIterator.of();
    }

    public Iterator<DebugElement> getExtraLines() {
        return getDebugSequence().getExtraLines();
    }

    public DebugSequence getDebugSequence() {
        return this.debugSequence;
    }

    @Override // com.reandroid.dex.common.SectionItemContainer, com.reandroid.arsc.item.BlockItem, com.reandroid.arsc.base.Block
    public void onReadBytes(BlockReader blockReader) throws IOException {
        this.lineStart.onReadBytes(blockReader);
        this.debugParameterCount.onReadBytes(blockReader);
        if (this.debugParameterCount.get() > 0) {
            BlockList<DebugParameter> blockListInitParametersArray = initParametersArray();
            blockListInitParametersArray.setSize(this.debugParameterCount.get());
            blockListInitParametersArray.readChildes(blockReader);
        }
        this.debugSequence.onReadBytes(blockReader);
    }

    private BlockList<DebugParameter> initParametersArray() {
        BlockList<DebugParameter> blockList = this.debugParametersArray;
        if (blockList != null) {
            return blockList;
        }
        BlockList<DebugParameter> blockList2 = new BlockList<>();
        this.debugParametersArray = blockList2;
        addChild(2, blockList2);
        blockList2.setCreator(CREATOR);
        return blockList2;
    }

    public void onRemove() {
        BlockList<DebugParameter> blockList = this.debugParametersArray;
        if (blockList != null) {
            this.debugParametersArray = null;
            blockList.clearChildes();
        }
        this.debugSequence.clear();
    }

    @Override // com.reandroid.dex.common.SectionItemContainer
    protected void onPreRefresh() {
        super.onPreRefresh();
        removeInvalidElements();
    }

    @Override // com.reandroid.dex.data.DataItem, com.reandroid.dex.common.SectionItemContainer
    protected void onRefreshed() {
        super.onRefreshed();
        BlockList<DebugParameter> blockList = this.debugParametersArray;
        this.debugParameterCount.set(blockList != null ? blockList.size() : 0);
    }

    @Override // com.reandroid.dex.data.DataItem, com.reandroid.dex.common.IdUsageIterator
    public Iterator<IdItem> usedIds() {
        return CombiningIterator.two(new IterableIterator<DebugParameter, IdItem>(getParameters()) { // from class: com.reandroid.dex.data.DebugInfo.1
            @Override // com.reandroid.utils.collection.IterableIterator
            public Iterator<IdItem> iterator(DebugParameter debugParameter) {
                return debugParameter.usedIds();
            }
        }, getDebugSequence().usedIds());
    }

    public void merge(DebugInfo debugInfo) {
        if (debugInfo == this) {
            return;
        }
        this.lineStart.set(debugInfo.lineStart.get());
        int parameterCount = debugInfo.getParameterCount();
        this.debugParameterCount.set(parameterCount);
        if (parameterCount != 0) {
            BlockList<DebugParameter> blockListInitParametersArray = initParametersArray();
            blockListInitParametersArray.setSize(parameterCount);
            for (int i = 0; i < parameterCount; i++) {
                ((DebugParameter) blockListInitParametersArray.get(i)).merge(debugInfo.getDebugParameter(i));
            }
        }
        getDebugSequence().merge(debugInfo.getDebugSequence());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        DebugInfo debugInfo = (DebugInfo) obj;
        return this.lineStart.get() == this.lineStart.get() && Objects.equals(this.debugParametersArray, debugInfo.debugParametersArray) && Objects.equals(this.debugSequence, debugInfo.debugSequence);
    }

    public int hashCode() {
        int i = this.lineStart.get() + 31;
        BlockList<DebugParameter> blockList = this.debugParametersArray;
        int iHashCode = i * 31;
        if (blockList != null) {
            iHashCode += blockList.hashCode();
        }
        return (iHashCode * 31) + this.debugSequence.hashCode();
    }

    public String toString() {
        return "DebugInfo{lineStart=" + this.lineStart.get() + ", parameterCount=" + this.debugParameterCount.get() + ", sequence=(" + this.debugSequence + ")}";
    }
}
