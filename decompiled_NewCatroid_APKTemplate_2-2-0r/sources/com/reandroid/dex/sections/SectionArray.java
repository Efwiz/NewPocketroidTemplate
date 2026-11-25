package com.reandroid.dex.sections;

import com.reandroid.arsc.base.Creator;
import com.reandroid.dex.base.BlockListArray;
import com.reandroid.dex.base.DexPositionAlign;
import com.reandroid.dex.base.IntegerPair;
import com.reandroid.dex.base.PositionAlignedItem;
import com.reandroid.dex.base.PositionedItem;
import com.reandroid.dex.common.FullRefresh;
import com.reandroid.dex.common.SectionItem;
import java.util.Iterator;

/* JADX INFO: loaded from: classes11.dex */
public class SectionArray<T extends SectionItem> extends BlockListArray<T> implements FullRefresh {
    public SectionArray(IntegerPair integerPair, Creator<T> creator) {
        super(integerPair, creator);
    }

    @Override // com.reandroid.dex.common.FullRefresh
    public void refreshFull() {
        if (getFirst() instanceof FullRefresh) {
            Iterator<T> it = iterator();
            while (it.hasNext()) {
                ((FullRefresh) it.next()).refreshFull();
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    int updatePositionedItemOffsets(int i) {
        DexPositionAlign positionAlign;
        int count = getCount();
        getCountAndOffset().getFirst().set(count);
        if (!(getFirst() instanceof PositionedItem)) {
            return i;
        }
        DexPositionAlign dexPositionAlign = null;
        for (int i2 = 0; i2 < count; i2++) {
            SectionItem sectionItem = (SectionItem) get(i2);
            if (sectionItem == 0) {
                dexPositionAlign = null;
            } else {
                if (sectionItem instanceof PositionAlignedItem) {
                    positionAlign = ((PositionAlignedItem) sectionItem).getPositionAlign();
                    if (positionAlign != null) {
                        positionAlign.setSize(0);
                    }
                    if (dexPositionAlign != null) {
                        dexPositionAlign.align(i);
                        i += dexPositionAlign.size();
                    }
                } else {
                    positionAlign = null;
                }
                if (i2 == count - 1) {
                    ((PositionedItem) sectionItem).removeLastAlign();
                }
                ((PositionedItem) sectionItem).setPosition(i);
                i += sectionItem.countBytes();
                dexPositionAlign = positionAlign;
            }
        }
        return i;
    }

    @Override // com.reandroid.arsc.base.BlockArray, com.reandroid.arsc.container.BlockList
    public void onPreRemove(T t) {
        super.onPreRemove(t);
        notifyBeforeRemoved(t);
    }

    private void notifyBeforeRemoved(T t) {
        Section<T> parentSection = getParentSection();
        if (parentSection != null) {
            parentSection.onRemoving(t);
        }
    }

    Section<T> getParentSection() {
        return (Section) getParentInstance(Section.class);
    }

    @Override // com.reandroid.arsc.base.BlockArray
    public void clear() {
        clearChildes();
        destroy();
    }
}
