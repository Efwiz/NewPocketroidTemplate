package com.reandroid.arsc.header;

import com.reandroid.arsc.chunk.ChunkType;
import com.reandroid.arsc.item.ByteItem;
import com.reandroid.arsc.item.IntegerItem;
import com.reandroid.arsc.item.ShortItem;

/* JADX INFO: loaded from: classes10.dex */
public class StringPoolHeader extends HeaderBlock {
    private final IntegerItem countStrings;
    private final IntegerItem countStyles;
    private final ShortItem flagExtra;
    private final ByteItem flagSorted;
    private final ByteItem flagUtf8;
    private final IntegerItem startStrings;
    private final IntegerItem startStyles;

    public StringPoolHeader() {
        super(ChunkType.STRING.ID);
        IntegerItem integerItem = new IntegerItem();
        this.countStrings = integerItem;
        IntegerItem integerItem2 = new IntegerItem();
        this.countStyles = integerItem2;
        ByteItem byteItem = new ByteItem();
        this.flagSorted = byteItem;
        ByteItem byteItem2 = new ByteItem();
        this.flagUtf8 = byteItem2;
        ShortItem shortItem = new ShortItem();
        this.flagExtra = shortItem;
        IntegerItem integerItem3 = new IntegerItem();
        this.startStrings = integerItem3;
        IntegerItem integerItem4 = new IntegerItem();
        this.startStyles = integerItem4;
        addChild(integerItem);
        addChild(integerItem2);
        addChild(byteItem);
        addChild(byteItem2);
        addChild(shortItem);
        addChild(integerItem3);
        addChild(integerItem4);
    }

    public IntegerItem getCountStrings() {
        return this.countStrings;
    }

    public IntegerItem getCountStyles() {
        return this.countStyles;
    }

    public ByteItem getFlagUtf8() {
        return this.flagUtf8;
    }

    public ByteItem getFlagSorted() {
        return this.flagSorted;
    }

    public ShortItem getFlagExtra() {
        return this.flagExtra;
    }

    public IntegerItem getStartStrings() {
        return this.startStrings;
    }

    public IntegerItem getStartStyles() {
        return this.startStyles;
    }

    public boolean isUtf8() {
        return (getFlagUtf8().get() & 1) != 0;
    }

    public void setUtf8(boolean z) {
        getFlagUtf8().set(z ? (byte) 1 : (byte) 0);
    }

    public boolean isSorted() {
        return (getFlagSorted().get() & 1) != 0;
    }

    public void setSorted(boolean z) {
        getFlagSorted().set(z ? (byte) 1 : (byte) 0);
    }

    @Override // com.reandroid.arsc.header.HeaderBlock
    public String toString() {
        if (getChunkType() != ChunkType.STRING) {
            return super.toString();
        }
        return getClass().getSimpleName() + " {strings=" + getCountStrings() + ", styles=" + getCountStyles() + ", utf8=" + isUtf8() + ", sorted=" + isSorted() + ", flagExtra=" + getFlagExtra().toHex() + ", offset-strings=" + getStartStrings().get() + ", offset-styles=" + getStartStyles().get() + '}';
    }
}
