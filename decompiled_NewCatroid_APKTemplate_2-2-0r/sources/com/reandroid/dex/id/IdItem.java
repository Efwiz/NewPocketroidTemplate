package com.reandroid.dex.id;

import com.reandroid.arsc.base.BlockRefresh;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.dex.base.BlockListArray;
import com.reandroid.dex.base.FixedSizeBlock;
import com.reandroid.dex.common.EditableItem;
import com.reandroid.dex.common.IdUsageIterator;
import com.reandroid.dex.common.SectionItem;
import com.reandroid.dex.key.Key;
import com.reandroid.dex.key.ModifiableKeyItem;
import com.reandroid.dex.sections.SectionType;
import com.reandroid.dex.smali.SmaliFormat;
import java.io.IOException;
import java.util.Iterator;

/* JADX INFO: loaded from: classes11.dex */
public abstract class IdItem extends SectionItem implements SmaliFormat, BlockRefresh, EditableItem, ModifiableKeyItem, FixedSizeBlock, IdUsageIterator {
    abstract void cacheItems();

    @Override // com.reandroid.dex.common.SectionItem, com.reandroid.dex.key.KeyItem
    public abstract Key getKey();

    @Override // com.reandroid.dex.common.SectionItem
    public abstract SectionType<? extends IdItem> getSectionType();

    public abstract void setKey(Key key);

    public abstract Iterator<IdItem> usedIds();

    IdItem(int i) {
        super(i);
    }

    @Override // com.reandroid.dex.common.SectionItem
    public void removeSelf() {
        BlockListArray blockListArray = (BlockListArray) getParentInstance(BlockListArray.class);
        if (blockListArray != null) {
            blockListArray.remove(this);
            setParent(null);
            setIndex(-1);
        }
    }

    @Override // com.reandroid.dex.common.SectionItem
    public int getIdx() {
        return getIndex();
    }

    @Override // com.reandroid.arsc.item.BlockItem, com.reandroid.arsc.base.Block
    public void onReadBytes(BlockReader blockReader) throws IOException {
        super.onReadBytes(blockReader);
        cacheItems();
    }
}
