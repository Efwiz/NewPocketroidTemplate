package com.reandroid.arsc.item;

import com.reandroid.arsc.chunk.SpecBlock;
import com.reandroid.utils.HexUtil;

/* JADX INFO: loaded from: classes10.dex */
public class SpecFlag extends IndirectItem<SpecFlagsArray> {
    private static final int OFFSET_FLAG = 3;

    public SpecFlag(SpecFlagsArray specFlagsArray, int i) {
        super(specFlagsArray, i);
    }

    public byte getFlagByte() {
        return getBlockItem().getBytesInternal()[getOffset() + 3];
    }

    public void setFlagByte(byte b) {
        getBlockItem().getBytesInternal()[getOffset() + 3] = b;
    }

    public void addFlagByte(byte b) {
        setFlagByte((byte) ((b & 255) | (getFlagByte() & 255)));
    }

    public void addFlag(SpecBlock.Flag flag) {
        addFlagByte(flag.getFlag());
    }

    public void setPublic() {
        addFlag(SpecBlock.Flag.SPEC_PUBLIC);
    }

    public boolean isPublic() {
        return SpecBlock.Flag.isPublic(getFlagByte());
    }

    public int getInteger() {
        return BlockItem.getInteger(getBlockItem().getBytesInternal(), getOffset());
    }

    public void setInteger(int i) {
        if (i == getInteger()) {
            return;
        }
        BlockItem.putInteger(getBlockItem().getBytesInternal(), getOffset(), i);
        getBlockItem().onBytesChanged();
    }

    public String toString() {
        if (getFlagByte() != 0) {
            return SpecBlock.Flag.toString(getFlagByte());
        }
        int integer = getInteger();
        if (integer != 0) {
            return HexUtil.toHex8(integer);
        }
        return "";
    }
}
