package com.reandroid.dex.ins;

import com.reandroid.arsc.base.Creator;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.arsc.item.IntegerReference;
import com.reandroid.dex.base.CountedList;
import com.reandroid.utils.StringsUtil;
import java.io.IOException;
import java.util.Iterator;

/* JADX INFO: loaded from: classes11.dex */
public class HandlerOffsetArray extends CountedList<HandlerOffset> {
    private static final Creator<HandlerOffset> CREATOR = new Creator<HandlerOffset>() { // from class: com.reandroid.dex.ins.HandlerOffsetArray.1
        @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockArrayCreator
        public HandlerOffset[] newArrayInstance(int i) {
            return new HandlerOffset[i];
        }

        @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockCreator
        public HandlerOffset newInstance() {
            return new HandlerOffset();
        }
    };
    private int itemsStart;

    public HandlerOffsetArray(IntegerReference integerReference) {
        super(integerReference, CREATOR);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public HandlerOffset getOrCreate(int i) {
        ensureSize(i + 1);
        return (HandlerOffset) get(i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    int getOffset(int i) {
        HandlerOffset handlerOffset = (HandlerOffset) get(i);
        if (handlerOffset != null) {
            return handlerOffset.getOffset();
        }
        return -1;
    }

    int indexOf(int i) {
        if (i < 0) {
            return -1;
        }
        int size = size();
        for (int i2 = 0; i2 < size; i2++) {
            if (i == getOffset(i2)) {
                return i2;
            }
        }
        return -1;
    }

    int getItemsStart() {
        return this.itemsStart;
    }

    int getMinStart() {
        int size = size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            int offset = getOffset(i2);
            if (i2 == 0 || offset < i) {
                i = offset;
            }
        }
        return i;
    }

    @Override // com.reandroid.dex.base.CountedList, com.reandroid.arsc.base.Block
    public void onReadBytes(BlockReader blockReader) throws IOException {
        super.onReadBytes(blockReader);
        this.itemsStart = blockReader.getPosition();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void merge(HandlerOffsetArray handlerOffsetArray) {
        if (handlerOffsetArray == this) {
            return;
        }
        int size = handlerOffsetArray.size();
        setSize(size);
        for (int i = 0; i < size; i++) {
            ((HandlerOffset) get(i)).merge((HandlerOffset) handlerOffsetArray.get(i));
        }
    }

    @Override // com.reandroid.arsc.container.BlockList
    public String toString() {
        return "size = " + size() + '[' + StringsUtil.join((Iterator<?>) iterator(), (Object) ", ") + ']';
    }
}
