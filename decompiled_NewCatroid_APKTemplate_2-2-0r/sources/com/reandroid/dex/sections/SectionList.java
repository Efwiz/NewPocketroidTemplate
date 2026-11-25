package com.reandroid.dex.sections;

import com.reandroid.arsc.base.Block;
import com.reandroid.arsc.base.OffsetSupplier;
import com.reandroid.arsc.container.BlockList;
import com.reandroid.arsc.container.FixedBlockContainer;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.arsc.item.IntegerReference;
import com.reandroid.arsc.item.NumberIntegerReference;
import com.reandroid.common.ArraySupplier;
import com.reandroid.dex.common.FullRefresh;
import com.reandroid.dex.common.SectionItem;
import com.reandroid.dex.common.SectionTool;
import com.reandroid.dex.header.DexHeader;
import com.reandroid.dex.id.ClassId;
import com.reandroid.dex.id.IdItem;
import com.reandroid.dex.key.FieldKey;
import com.reandroid.dex.key.Key;
import com.reandroid.dex.key.MethodKey;
import com.reandroid.dex.key.ProtoKey;
import com.reandroid.dex.key.StringKey;
import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.key.TypeListKey;
import com.reandroid.dex.smali.model.SmaliClass;
import com.reandroid.utils.collection.ArraySupplierIterator;
import com.reandroid.utils.collection.CollectionUtil;
import com.reandroid.utils.collection.CombiningIterator;
import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import name.antonsmirnov.firmata.FormatHelper;

/* JADX INFO: loaded from: classes11.dex */
public class SectionList extends FixedBlockContainer implements SectionTool, OffsetSupplier, Iterable<Section<?>>, ArraySupplier<Section<?>>, FullRefresh {
    private final IntegerReference baseOffset;
    private final BlockList<DataSection<?>> dataSectionList;
    private final DexHeader dexHeader;
    private final Section<DexHeader> dexHeaderSection;
    private final BlockList<IdSection<?>> idSectionList;
    private boolean mReading;
    private final MapList mapList;
    private final Section<MapList> mapListSection;
    private final Map<SectionType<?>, Section<?>> typeMap;

    @Override // com.reandroid.dex.common.SectionTool
    public SectionList getSectionList() {
        return this;
    }

    public SectionList() {
        super(4);
        NumberIntegerReference numberIntegerReference = new NumberIntegerReference();
        this.baseOffset = numberIntegerReference;
        BlockList<IdSection<?>> blockList = new BlockList<>();
        this.idSectionList = blockList;
        BlockList<DataSection<?>> blockList2 = new BlockList<>();
        this.dataSectionList = blockList2;
        Section sectionCreateSpecialSection = SectionType.HEADER.createSpecialSection(numberIntegerReference);
        DexHeader dexHeader = new DexHeader(numberIntegerReference);
        sectionCreateSpecialSection.add(dexHeader);
        this.dexHeaderSection = sectionCreateSpecialSection;
        Section sectionCreateSpecialSection2 = SectionType.MAP_LIST.createSpecialSection(dexHeader.map);
        MapList mapList = new MapList(dexHeader.map);
        sectionCreateSpecialSection2.add(mapList);
        this.mapListSection = sectionCreateSpecialSection2;
        HashMap map = new HashMap();
        this.typeMap = map;
        addChild(0, sectionCreateSpecialSection);
        addChild(1, blockList);
        addChild(2, blockList2);
        addChild(3, sectionCreateSpecialSection2);
        this.dexHeader = dexHeader;
        this.mapList = mapList;
        map.put(SectionType.HEADER, sectionCreateSpecialSection);
        map.put(SectionType.MAP_LIST, sectionCreateSpecialSection2);
    }

    public int shrink() {
        int iClearUnused = 0;
        while (true) {
            int iClearUnused2 = clearUnused();
            if (iClearUnused2 == 0) {
                break;
            }
            iClearUnused += iClearUnused2;
        }
        while (true) {
            int iClearDuplicateData = clearDuplicateData();
            if (iClearDuplicateData != 0) {
                iClearUnused = iClearUnused + iClearDuplicateData + clearUnused();
            } else {
                return iClearUnused + clearEmptySections();
            }
        }
    }

    public int clearDuplicateData() {
        refresh();
        int i = 0;
        for (SectionType<?> sectionType : SectionType.getRemoveOrderList()) {
            Section section = getSection(sectionType);
            if (section != null) {
                int iClearDuplicates = section.getPool().clearDuplicates();
                i += iClearDuplicates;
                if (iClearDuplicates != 0) {
                    section.refresh();
                }
            }
        }
        if (i != 0) {
            refresh();
        }
        return i;
    }

    public int clearUnused() {
        clearUsageTypes();
        refresh();
        int iClearUnused = 0;
        for (SectionType<?> sectionType : SectionType.getRemoveOrderList()) {
            Section section = getSection(sectionType);
            if (section != null) {
                iClearUnused += section.clearUnused();
            }
        }
        return iClearUnused;
    }

    public int clearEmptySections() {
        Iterator it = CollectionUtil.toList(getSections()).iterator();
        int i = 0;
        while (it.hasNext()) {
            if (((Section) it.next()).removeIfEmpty()) {
                i++;
            }
        }
        return i;
    }

    private void clearUsageTypes() {
        Iterator<Section<?>> sections = getSections();
        while (sections.hasNext()) {
            sections.next().clearUsageTypes();
        }
    }

    public void updateHeader() {
        Block block = (Block) getParentInstance(DexLayout.class);
        if (block == null) {
            block = this;
        }
        this.dexHeader.updateHeaderInternal(block);
    }

    @Override // com.reandroid.arsc.container.FixedBlockContainer, com.reandroid.arsc.base.BlockContainer
    protected void onRefreshed() {
        super.onRefreshed();
        this.mapList.refresh();
    }

    @Override // com.reandroid.arsc.base.BlockContainer, com.reandroid.arsc.base.Block
    public void onReadBytes(BlockReader blockReader) throws IOException {
        readSections(blockReader, null);
    }

    void readSections(BlockReader blockReader, Predicate<SectionType<?>> predicate) throws IOException {
        this.mReading = true;
        readSpecialSections(blockReader);
        readBody(blockReader, predicate);
        this.mReading = false;
    }

    private void readSpecialSections(BlockReader blockReader) throws IOException {
        getSection(SectionType.HEADER).readBytes(blockReader);
        getSection(SectionType.MAP_LIST).readBytes(blockReader);
    }

    private void readBody(BlockReader blockReader, Predicate<SectionType<?>> predicate) throws IOException {
        for (MapItem mapItem : this.mapList.getReadSorted()) {
            if (mapItem != null && (predicate == null || predicate.test(mapItem.getSectionType()))) {
                loadSection(mapItem, blockReader);
            }
        }
        this.idSectionList.trimToSize();
        this.dataSectionList.trimToSize();
        this.idSectionList.sort(getOffsetComparator());
        this.dataSectionList.sort(getOffsetComparator());
        this.mapList.linkHeader(this.dexHeader);
    }

    private void loadSection(MapItem mapItem, BlockReader blockReader) throws IOException {
        Section sectionCreateNewSection;
        if (mapItem == null) {
            return;
        }
        if (this.typeMap.containsKey(mapItem.getSectionType()) || (sectionCreateNewSection = mapItem.createNewSection()) == null) {
            return;
        }
        add(sectionCreateNewSection);
        sectionCreateNewSection.readBytes(blockReader);
    }

    @Override // com.reandroid.dex.common.SectionTool
    public boolean isReading() {
        return this.mReading;
    }

    public <T1 extends SectionItem> Section<T1> add(Section<T1> section) {
        if (section instanceof IdSection) {
            this.idSectionList.add((IdSection) section);
        } else {
            this.dataSectionList.add((DataSection) section);
        }
        this.typeMap.put((SectionType<?>) section.getSectionType(), (Section<?>) section);
        return section;
    }

    public DexHeader getHeader() {
        return this.dexHeader;
    }

    public MapList getMapList() {
        return this.mapList;
    }

    public void remove(Section<?> section) {
        if (section == null) {
            return;
        }
        SectionType<T> sectionType = section.getSectionType();
        if (this.typeMap.remove(sectionType) != section) {
            return;
        }
        section.onRemove(this);
        if (sectionType.isDataSection()) {
            this.dataSectionList.remove((DataSection) section);
        } else if (sectionType.isIdSection()) {
            this.idSectionList.remove((IdSection) section);
        }
    }

    public void clear() {
        Iterator<Section<?>> sections = getSections();
        while (sections.hasNext()) {
            sections.next().onRemove(this);
        }
        this.idSectionList.clearChildes();
        this.dataSectionList.clearChildes();
        this.typeMap.clear();
    }

    public void clearPoolMap(SectionType<?> sectionType) {
        Section section = getSection(sectionType);
        if (section != null) {
            section.clearPoolMap();
        }
    }

    public void clearPoolMap() {
        Iterator<Section<?>> it = iterator();
        while (it.hasNext()) {
            it.next().clearPoolMap();
        }
    }

    public void sortSection(SectionType<?>[] sectionTypeArr) {
        this.idSectionList.sort(SectionType.comparator(sectionTypeArr, new Function() { // from class: com.reandroid.dex.sections.SectionList$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((IdSection) obj).getSectionType();
            }
        }));
        this.dataSectionList.sort(SectionType.comparator(sectionTypeArr, new Function() { // from class: com.reandroid.dex.sections.SectionList$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DataSection) obj).getSectionType();
            }
        }));
        this.mapList.sortMapItems(sectionTypeArr);
    }

    @Override // com.reandroid.dex.common.FullRefresh
    public void refreshFull() {
        for (SectionType<?> sectionType : SectionType.getSortSectionsOrder()) {
            Section section = getSection(sectionType);
            if (section != null) {
                section.refreshFull();
            }
        }
        clearUnused();
        clearDuplicateData();
    }

    public boolean sortStrings() {
        Section section = getSection(SectionType.STRING_DATA);
        boolean zSort = section != null ? section.sort() : false;
        if (sortItems(SectionType.STRING_ID)) {
            zSort = true;
        }
        if (sortItems(SectionType.TYPE_ID)) {
            zSort = true;
        }
        if (sortItems(SectionType.PROTO_ID)) {
            zSort = true;
        }
        if (sortItems(SectionType.FIELD_ID)) {
            zSort = true;
        }
        if (sortItems(SectionType.METHOD_ID)) {
            zSort = true;
        }
        if (sortItems(SectionType.CLASS_ID)) {
            return true;
        }
        return zSort;
    }

    private boolean sortItems(SectionType<?> sectionType) {
        Section section = getSection(sectionType);
        if (section != null) {
            return section.sort();
        }
        return false;
    }

    public <T1 extends SectionItem> T1 getLoaded(SectionType<T1> sectionType, Key key) {
        Section<T1> section = getSection(sectionType);
        if (section != null) {
            return (T1) section.getSectionItem(key);
        }
        return null;
    }

    @Override // com.reandroid.dex.common.SectionTool
    public <T1 extends SectionItem> Section<T1> getSection(SectionType<T1> sectionType) {
        return (Section) this.typeMap.get(sectionType);
    }

    @Override // com.reandroid.dex.common.SectionTool
    public <T1 extends SectionItem> Section<T1> getOrCreateSection(SectionType<T1> sectionType) {
        Section<T1> section = getSection(sectionType);
        if (section != null) {
            return section;
        }
        if (sectionType == SectionType.MAP_LIST || sectionType == SectionType.HEADER) {
            return null;
        }
        MapItem orCreate = getMapList().getOrCreate(sectionType);
        Section<T1> sectionCreateNewSection = orCreate.createNewSection();
        add(sectionCreateNewSection);
        sortSection(SectionType.getR8Order());
        orCreate.link(getHeader());
        return sectionCreateNewSection;
    }

    public int indexOf(Section<?> section) {
        if (section == this.dexHeaderSection) {
            return 0;
        }
        if (section == this.mapListSection) {
            return getCount() - 1;
        }
        if (section == this.idSectionList.get(section.getIndex())) {
            return section.getIndex() + 1;
        }
        if (section == this.dataSectionList.get(section.getIndex())) {
            return this.idSectionList.size() + 1 + section.getIndex();
        }
        return -1;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.reandroid.common.ArraySupplier
    public Section<?> get(int i) {
        if (i == 0) {
            return this.dexHeaderSection;
        }
        if (i == getCount() - 1) {
            return this.mapListSection;
        }
        if (i <= this.idSectionList.size()) {
            return (Section) this.idSectionList.get(i - 1);
        }
        return (Section) this.dataSectionList.get((i - 1) - this.idSectionList.size());
    }

    public boolean contains(Key key) {
        if (key == null) {
            return false;
        }
        if (key instanceof StringKey) {
            return contains(SectionType.STRING_ID, key);
        }
        if (key instanceof TypeKey) {
            return contains(SectionType.TYPE_ID, key);
        }
        if (key instanceof FieldKey) {
            return contains(SectionType.FIELD_ID, key);
        }
        if (key instanceof ProtoKey) {
            return contains(SectionType.PROTO_ID, key);
        }
        if (key instanceof MethodKey) {
            return contains(SectionType.METHOD_ID, key);
        }
        if (key instanceof TypeListKey) {
            return contains(SectionType.TYPE_LIST, key);
        }
        throw new IllegalArgumentException("Unknown key type: " + key.getClass() + ", '" + key + FormatHelper.QUOTE);
    }

    private boolean contains(SectionType<?> sectionType, Key key) {
        Section section = getSection(sectionType);
        if (section != null) {
            return section.contains(key);
        }
        return false;
    }

    public void keyChangedInternal(SectionItem sectionItem, SectionType<?> sectionType, Key key) {
        ClassId classId;
        Section section = getSection(sectionType);
        if (section == null) {
            return;
        }
        section.keyChanged(sectionItem, key);
        if (sectionType != SectionType.TYPE_ID || (classId = (ClassId) getLoaded(SectionType.CLASS_ID, key)) == null) {
            return;
        }
        classId.getKey();
    }

    public Iterator<Section<?>> getSections() {
        return new CombiningIterator(getIdSections(), getDataSections());
    }

    public Iterator<IdSection<?>> getIdSections() {
        return this.idSectionList.iterator();
    }

    public Iterator<DataSection<?>> getDataSections() {
        return this.dataSectionList.iterator();
    }

    @Override // com.reandroid.common.CountSupplier
    public int getCount() {
        return this.idSectionList.size() + 2 + this.dataSectionList.size();
    }

    @Override // java.lang.Iterable
    public Iterator<Section<?>> iterator() {
        return ArraySupplierIterator.of(this);
    }

    @Override // com.reandroid.arsc.base.OffsetSupplier, com.reandroid.dex.base.DexArraySupplier
    public IntegerReference getOffsetReference() {
        return this.baseOffset;
    }

    private boolean canAddAll(Collection<IdItem> collection) {
        Iterator<IdSection<?>> idSections = getIdSections();
        while (idSections.hasNext()) {
            if (!idSections.next().canAddAll(collection, 200)) {
                return false;
            }
        }
        return true;
    }

    public boolean merge(MergeOptions mergeOptions, ClassId classId) {
        if (classId == null) {
            mergeOptions.onMergeError((DexLayout) getParentInstance(DexLayout.class), classId, "Null class id");
            return false;
        }
        if (classId.getParent() == null) {
            mergeOptions.onMergeError((DexLayout) getParentInstance(DexLayout.class), classId, "Destroyed class id");
            return false;
        }
        if (classId.getParent(SectionList.class) == this) {
            mergeOptions.onMergeError((DexLayout) getParentInstance(DexLayout.class), classId, "Class id is on same section");
            return false;
        }
        if (mergeOptions.skipMerging(classId, classId.getKey())) {
            return false;
        }
        if (contains(SectionType.CLASS_ID, classId.getKey())) {
            mergeOptions.onDuplicate(classId);
            return false;
        }
        if (!canAddAll(classId.listUsedIds())) {
            mergeOptions.onDexFull((DexLayout) getParentInstance(DexLayout.class), classId);
            return false;
        }
        ((ClassId) getOrCreateSection(SectionType.CLASS_ID).getOrCreate(classId.getKey())).merge(classId);
        if (mergeOptions.relocateClass()) {
            classId.removeSelf();
        }
        mergeOptions.onMergeSuccess(classId, classId.getKey());
        return true;
    }

    public boolean merge(MergeOptions mergeOptions, SectionList sectionList) {
        boolean z = false;
        if (sectionList == this) {
            mergeOptions.onMergeError((DexLayout) getParentInstance(DexLayout.class), sectionList, "Can not merge with self");
            return false;
        }
        if (sectionList.getParent() == null) {
            mergeOptions.onMergeError((DexLayout) getParentInstance(DexLayout.class), sectionList, "Destroyed section list");
            return false;
        }
        Section section = sectionList.getSection(SectionType.CLASS_ID);
        if (section != null && section.getCount() != 0) {
            Section orCreateSection = getOrCreateSection(SectionType.CLASS_ID);
            SectionArray itemArray = section.getItemArray();
            int size = itemArray.size() - 1;
            boolean z2 = false;
            while (true) {
                if (size < 0) {
                    z = true;
                    break;
                }
                ClassId classId = (ClassId) itemArray.get(size);
                TypeKey key = classId.getKey();
                if (!mergeOptions.skipMerging(classId, key)) {
                    if (orCreateSection.contains(key)) {
                        mergeOptions.onDuplicate(classId);
                    } else {
                        if (!canAddAll(classId.listUsedIds())) {
                            mergeOptions.onDexFull((DexLayout) getParentInstance(DexLayout.class), classId);
                            break;
                        }
                        ((ClassId) orCreateSection.getOrCreate(classId.getKey())).merge(classId);
                        mergeOptions.onMergeSuccess(classId, key);
                        if (mergeOptions.relocateClass()) {
                            classId.removeSelf();
                        }
                        z2 = true;
                    }
                }
                size--;
            }
            if (section.getCount() == 0) {
                ((DexLayout) section.getSectionList().getParentInstance(DexLayout.class)).clear();
            }
            if (z2) {
                refresh();
                sortStrings();
                refresh();
            }
        }
        return z;
    }

    public ClassId fromSmali(SmaliClass smaliClass) throws IOException {
        ClassId classId = (ClassId) getOrCreateSectionItem(SectionType.CLASS_ID, smaliClass.getKey());
        classId.fromSmali(smaliClass);
        return classId;
    }

    private static <T1 extends Section<?>> Comparator<T1> getOffsetComparator() {
        return new Comparator() { // from class: com.reandroid.dex.sections.SectionList$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return SectionList.lambda$getOffsetComparator$0((Section) obj, (Section) obj2);
            }
        };
    }

    static /* synthetic */ int lambda$getOffsetComparator$0(Section section, Section section2) {
        if (section == section2) {
            return 0;
        }
        if (section == null) {
            return 1;
        }
        return section.compareOffset(section2);
    }
}
