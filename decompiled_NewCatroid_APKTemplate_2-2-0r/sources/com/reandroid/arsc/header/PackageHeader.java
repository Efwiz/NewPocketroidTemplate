package com.reandroid.arsc.header;

import com.reandroid.arsc.chunk.ChunkType;
import com.reandroid.arsc.container.SingleBlockContainer;
import com.reandroid.arsc.item.FixedLengthString;
import com.reandroid.arsc.item.IntegerItem;

/* JADX INFO: loaded from: classes10.dex */
public class PackageHeader extends HeaderBlock {
    private final IntegerItem packageId;
    private final FixedLengthString packageName;
    private final IntegerItem specStringPoolCount;
    private final IntegerItem specStringPoolOffset;
    private final IntegerItem typeIdOffset;
    private final SingleBlockContainer<IntegerItem> typeIdOffsetContainer;
    private final IntegerItem typeStringPoolCount;
    private final IntegerItem typeStringPoolOffset;

    public PackageHeader() {
        super(ChunkType.PACKAGE.ID);
        IntegerItem integerItem = new IntegerItem();
        this.packageId = integerItem;
        FixedLengthString fixedLengthString = new FixedLengthString(256);
        this.packageName = fixedLengthString;
        IntegerItem integerItem2 = new IntegerItem();
        this.typeStringPoolOffset = integerItem2;
        IntegerItem integerItem3 = new IntegerItem();
        this.typeStringPoolCount = integerItem3;
        IntegerItem integerItem4 = new IntegerItem();
        this.specStringPoolOffset = integerItem4;
        IntegerItem integerItem5 = new IntegerItem();
        this.specStringPoolCount = integerItem5;
        SingleBlockContainer<IntegerItem> singleBlockContainer = new SingleBlockContainer<>();
        this.typeIdOffsetContainer = singleBlockContainer;
        IntegerItem integerItem6 = new IntegerItem();
        this.typeIdOffset = integerItem6;
        singleBlockContainer.setItem(integerItem6);
        addChild(integerItem);
        addChild(fixedLengthString);
        addChild(integerItem2);
        addChild(integerItem3);
        addChild(integerItem4);
        addChild(integerItem5);
        addChild(singleBlockContainer);
    }

    public IntegerItem getPackageId() {
        return this.packageId;
    }

    public FixedLengthString getPackageName() {
        return this.packageName;
    }

    public IntegerItem getTypeStringPoolOffset() {
        return this.typeStringPoolOffset;
    }

    public IntegerItem getTypeStringPoolCount() {
        return this.typeStringPoolCount;
    }

    public IntegerItem getSpecStringPoolOffset() {
        return this.specStringPoolOffset;
    }

    public IntegerItem getSpecStringPoolCount() {
        return this.specStringPoolCount;
    }

    public IntegerItem getTypeIdOffsetItem() {
        return this.typeIdOffset;
    }

    public void setTypeIdOffset(int i) {
        this.typeIdOffset.set(i);
        this.typeIdOffsetContainer.setItem(this.typeIdOffset);
    }

    public int getTypeIdOffset() {
        if (this.typeIdOffset.getParent() == null) {
            this.typeIdOffset.set(0);
        }
        return this.typeIdOffset.get();
    }

    @Override // com.reandroid.arsc.header.HeaderBlock
    void onHeaderSizeLoaded(int i) {
        super.onHeaderSizeLoaded(i);
        if (i < 288) {
            this.typeIdOffset.set(0);
            this.typeIdOffsetContainer.setItem(null);
        }
    }
}
