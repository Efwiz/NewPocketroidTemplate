package com.reandroid.dex.sections;

import com.reandroid.arsc.base.Creator;
import com.reandroid.arsc.item.IntegerItem;
import com.reandroid.arsc.item.IntegerReference;
import com.reandroid.dex.base.CountedArray;
import com.reandroid.dex.base.DexPositionAlign;
import com.reandroid.dex.base.ParallelIntegerPair;
import com.reandroid.dex.base.ParallelReference;
import com.reandroid.dex.base.PositionAlignedItem;
import com.reandroid.dex.header.DexHeader;
import com.reandroid.utils.collection.ArraySort;
import io.netty.util.internal.StringUtil;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes11.dex */
public class MapList extends SpecialItem implements Iterable<MapItem>, PositionAlignedItem {
    private static final Creator<MapItem> CREATOR = new Creator<MapItem>() { // from class: com.reandroid.dex.sections.MapList.1
        @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockArrayCreator
        public MapItem[] newArrayInstance(int i) {
            return new MapItem[i];
        }

        @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockCreator
        public MapItem newInstance() {
            return new MapItem();
        }
    };
    private final ParallelReference dataSize;
    private final ParallelReference dataStart;
    private final ParallelReference fileSize;
    private final CountedArray<MapItem> itemArray;
    private final DexPositionAlign positionAlign;

    public MapList(IntegerReference integerReference) {
        super(3);
        IntegerItem integerItem = new IntegerItem();
        CountedArray<MapItem> countedArray = new CountedArray<>(integerItem, CREATOR);
        this.itemArray = countedArray;
        DexPositionAlign dexPositionAlign = new DexPositionAlign();
        this.positionAlign = dexPositionAlign;
        addChild(0, dexPositionAlign);
        addChild(1, integerItem);
        addChild(2, countedArray);
        setOffsetReference(integerReference);
        this.fileSize = new ParallelReference(new FileSizeReference());
        this.dataStart = new ParallelReference(new DataStartReference());
        this.dataSize = new ParallelReference(new DataSizeReference());
    }

    @Override // com.reandroid.dex.common.SectionItem
    public SectionType<MapList> getSectionType() {
        return SectionType.MAP_LIST;
    }

    public ParallelReference getFileSize() {
        return this.fileSize;
    }

    public ParallelReference getDataStart() {
        return this.dataStart;
    }

    public ParallelReference getDataSize() {
        return this.dataSize;
    }

    public void sortMapItems(SectionType<?>[] sectionTypeArr) {
        this.itemArray.sort(SectionType.comparator(sectionTypeArr, new MapList$$ExternalSyntheticLambda0()));
    }

    public void linkHeader(DexHeader dexHeader) {
        linkSpecialReference(SectionType.HEADER);
        linkSpecialReference(SectionType.MAP_LIST);
        linkIdTypesHeader(dexHeader);
        getFileSize().setReference2(dexHeader.fileSize);
        getDataSize().setReference2(dexHeader.data.getFirst());
        getDataStart().setReference2(dexHeader.data.getSecond());
    }

    private void linkSpecialReference(SectionType<?> sectionType) {
        MapItem mapItem = get(sectionType);
        ParallelIntegerPair parallelIntegerPair = (ParallelIntegerPair) getSection(sectionType).getItemArray().getCountAndOffset();
        parallelIntegerPair.setReference2(mapItem.getCountAndOffset());
        parallelIntegerPair.refresh();
    }

    private void linkIdTypesHeader(DexHeader dexHeader) {
        Iterator<SectionType<?>> idSectionTypes = SectionType.getIdSectionTypes();
        while (idSectionTypes.hasNext()) {
            MapItem mapItem = get(idSectionTypes.next());
            if (mapItem != null) {
                mapItem.link(dexHeader);
            }
        }
    }

    public MapItem getDataStartItem() {
        while (true) {
            boolean z = false;
            for (MapItem mapItem : this) {
                SectionType<DexHeader> sectionType = mapItem.getSectionType();
                if (!z) {
                    if (sectionType == SectionType.HEADER) {
                        z = true;
                    }
                } else if (sectionType.isDataSection()) {
                    return mapItem;
                }
            }
            return null;
        }
    }

    public void remove(SectionType<?> sectionType) {
        remove(get(sectionType));
    }

    public void remove(MapItem mapItem) {
        if (mapItem == null) {
            return;
        }
        ParallelIntegerPair countAndOffset = mapItem.getCountAndOffset();
        countAndOffset.getFirst().set(0);
        countAndOffset.getSecond().set(0);
        countAndOffset.setReference2(null);
        this.itemArray.remove(mapItem);
        mapItem.setParent(null);
        mapItem.setIndex(-1);
    }

    public MapItem get(SectionType<?> sectionType) {
        for (MapItem mapItem : this) {
            if (sectionType == mapItem.getSectionType()) {
                return mapItem;
            }
        }
        return null;
    }

    public MapItem getOrCreate(SectionType<?> sectionType) {
        if (get(sectionType) != null) {
            return null;
        }
        MapItem mapItemCreateNext = this.itemArray.createNext();
        mapItemCreateNext.setType(sectionType);
        return mapItemCreateNext;
    }

    @Override // java.lang.Iterable
    public Iterator<MapItem> iterator() {
        return this.itemArray.iterator();
    }

    public MapItem[] getReadSorted() {
        List<MapItem> childes = this.itemArray.getChildes();
        MapItem[] mapItemArr = (MapItem[]) childes.toArray(new MapItem[childes.size()]);
        ArraySort.sort(mapItemArr, SectionType.comparator(SectionType.getReadOrderList(), new MapList$$ExternalSyntheticLambda0()));
        return mapItemArr;
    }

    @Override // com.reandroid.dex.common.SectionItemContainer
    protected void onRefreshed() {
        super.onRefreshed();
        getFileSize().refresh();
        getDataStart().refresh();
        getDataSize().refresh();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (MapItem mapItem : this.itemArray) {
            if (i != 0) {
                sb.append('\n');
            }
            if (i < 9) {
                sb.append(StringUtil.SPACE);
            }
            i++;
            sb.append(i);
            sb.append(") ");
            sb.append(mapItem);
        }
        return sb.toString();
    }

    @Override // com.reandroid.dex.base.PositionAlignedItem
    public DexPositionAlign getPositionAlign() {
        return this.positionAlign;
    }

    static class DataStartReference implements IntegerReference {
        private final MapList mapList;

        @Override // com.reandroid.arsc.item.IntegerReference
        public void set(int i) {
        }

        private DataStartReference(MapList mapList) {
            this.mapList = mapList;
        }

        @Override // com.reandroid.arsc.item.IntegerReference
        public int get() {
            MapItem dataStartItem = this.mapList.getDataStartItem();
            if (dataStartItem != null) {
                return dataStartItem.getOffset().get();
            }
            return 0;
        }
    }

    static class DataSizeReference implements IntegerReference {
        private final MapList mapList;

        @Override // com.reandroid.arsc.item.IntegerReference
        public void set(int i) {
        }

        private DataSizeReference(MapList mapList) {
            this.mapList = mapList;
        }

        @Override // com.reandroid.arsc.item.IntegerReference
        public int get() {
            MapItem dataStartItem = this.mapList.getDataStartItem();
            if (dataStartItem != null) {
                return this.mapList.getFileSize().get() - dataStartItem.getOffset().get();
            }
            return 0;
        }
    }

    static class FileSizeReference implements IntegerReference {
        private final MapList mapList;

        @Override // com.reandroid.arsc.item.IntegerReference
        public void set(int i) {
        }

        private FileSizeReference(MapList mapList) {
            this.mapList = mapList;
        }

        @Override // com.reandroid.arsc.item.IntegerReference
        public int get() {
            return this.mapList.getOffset() + this.mapList.countBytes();
        }
    }
}
