package com.reandroid.dex.sections;

import com.reandroid.arsc.base.Block;
import com.reandroid.arsc.item.IndirectInteger;
import com.reandroid.arsc.item.IntegerReference;
import com.reandroid.dex.base.DexBlockItem;
import com.reandroid.dex.base.IndirectIntegerPair;
import com.reandroid.dex.base.ParallelIntegerPair;
import com.reandroid.dex.base.ParallelReference;
import com.reandroid.dex.common.SectionItem;
import com.reandroid.dex.header.CountAndOffset;
import com.reandroid.dex.header.DexHeader;
import com.reandroid.utils.HexUtil;
import io.netty.util.internal.StringUtil;

/* JADX INFO: loaded from: classes11.dex */
public class MapItem extends DexBlockItem {
    public static final int SIZE = 12;
    private final ParallelIntegerPair countAndOffset;
    private final IndirectInteger type;

    public MapItem() {
        super(12);
        this.type = new IndirectInteger(this, 0);
        this.countAndOffset = new ParallelIntegerPair(new IndirectIntegerPair(this, 4));
    }

    public void link(DexHeader dexHeader) {
        CountAndOffset countAndOffset = dexHeader.get(getSectionType());
        if (countAndOffset != null) {
            getCountAndOffset().setReference2(countAndOffset);
        }
    }

    public ParallelIntegerPair getCountAndOffset() {
        return this.countAndOffset;
    }

    public <T1 extends SectionItem> SectionType<T1> getSectionType() {
        return SectionType.get(getType().get());
    }

    public <T1 extends SectionItem> Section<T1> createNewSection() {
        SectionType<T1> sectionType = getSectionType();
        if (sectionType == null) {
            System.err.println("Unknown section: " + toString());
            return null;
        }
        if (sectionType.getCreator() == null) {
            System.err.println("Unimplemented section: " + toString());
            return null;
        }
        Block parent = (Block) getParent(SectionList.class);
        if (parent != null) {
            parent = parent.getParent();
        }
        if (parent == null) {
            parent = (Block) getParent(MapList.class);
        }
        if (parent == null) {
            parent = (Block) getParent(DexLayout.class);
        }
        if (parent == null) {
            parent = getParent();
        }
        Section<T1> section = (Section<T1>) sectionType.createSection(getCountAndOffset());
        section.setParent(parent);
        return section;
    }

    public void setType(SectionType<?> sectionType) {
        getType().set(sectionType.getType());
    }

    public IntegerReference getType() {
        return this.type;
    }

    public ParallelReference getCount() {
        return this.countAndOffset.getFirst();
    }

    public int getCountValue() {
        return getCount().get();
    }

    public void setCount(int i) {
        getCount().set(i);
    }

    public ParallelReference getOffset() {
        return this.countAndOffset.getSecond();
    }

    public int getOffsetValue() {
        return getOffset().get();
    }

    public void setOffset(int i) {
        getOffset().set(i);
    }

    public String toString() {
        String name2;
        StringBuilder sb = new StringBuilder();
        SectionType sectionType = getSectionType();
        if (sectionType == null) {
            name2 = HexUtil.toHex("UNKNOWN_", getType().get(), 1);
        } else {
            name2 = sectionType.getName();
        }
        sb.append(name2);
        sb.append(StringUtil.SPACE);
        int length = 24 - name2.length();
        for (int i = 0; i < length; i++) {
            sb.append('-');
        }
        ParallelIntegerPair countAndOffset = getCountAndOffset();
        sb.append("[");
        String string = Integer.toString(countAndOffset.getFirst().get());
        sb.append(string);
        int length2 = 6 - string.length();
        for (int i2 = 0; i2 < length2; i2++) {
            sb.append(StringUtil.SPACE);
        }
        sb.append(',');
        String string2 = Integer.toString(countAndOffset.getSecond().get());
        int length3 = 8 - string2.length();
        for (int i3 = 0; i3 < length3; i3++) {
            sb.append(StringUtil.SPACE);
        }
        sb.append(string2);
        sb.append(']');
        return sb.toString();
    }
}
