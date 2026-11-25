package com.reandroid.dex.sections;

import com.reandroid.arsc.container.FixedBlockContainer;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.common.BytesOutputStream;
import com.reandroid.dex.base.DexException;
import com.reandroid.dex.common.DexUtils;
import com.reandroid.dex.common.FullRefresh;
import com.reandroid.dex.common.SectionItem;
import com.reandroid.dex.header.Checksum;
import com.reandroid.dex.header.DexHeader;
import com.reandroid.dex.id.ClassId;
import com.reandroid.dex.id.StringId;
import com.reandroid.dex.key.Key;
import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.smali.model.SmaliClass;
import com.reandroid.utils.collection.CombiningIterator;
import com.reandroid.utils.collection.EmptyIterator;
import com.reandroid.utils.collection.IterableIterator;
import com.reandroid.utils.collection.MultiMap;
import com.reandroid.utils.collection.SingleIterator;
import com.reandroid.utils.io.FileUtil;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes11.dex */
public class DexLayout extends FixedBlockContainer implements FullRefresh {
    private final MultiMap<TypeKey, ClassId> extendingClassMap;
    private final MultiMap<TypeKey, ClassId> interfaceMap;
    private String mSimpleName;
    private Object mTag;
    private final SectionList sectionList;

    public DexLayout() {
        super(1);
        SectionList sectionList = new SectionList();
        this.sectionList = sectionList;
        addChild(0, sectionList);
        this.extendingClassMap = new MultiMap<>();
        this.interfaceMap = new MultiMap<>();
    }

    public int getVersion() {
        return getHeader().getVersion();
    }

    public void setVersion(int i) {
        getHeader().setVersion(i);
    }

    public Iterator<Marker> getMarkers() {
        return Marker.parse((Section<StringId>) get(SectionType.STRING_ID));
    }

    public Iterator<ClassId> getSubTypes(TypeKey typeKey) {
        return new IterableIterator<ClassId, ClassId>(CombiningIterator.two(getExtendingClassIds(typeKey), getImplementationIds(typeKey))) { // from class: com.reandroid.dex.sections.DexLayout.1
            @Override // com.reandroid.utils.collection.IterableIterator
            public Iterator<ClassId> iterator(ClassId classId) {
                return CombiningIterator.two(SingleIterator.of(classId), DexLayout.this.getSubTypes(classId.getKey()));
            }
        };
    }

    public Iterator<ClassId> getExtendingClassIds(TypeKey typeKey) {
        if (this.extendingClassMap.size() == 0) {
            loadExtendingClassMap();
        }
        return this.extendingClassMap.getAll(typeKey);
    }

    public Iterator<ClassId> getImplementationIds(TypeKey typeKey) {
        if (this.interfaceMap.size() == 0) {
            loadInterfacesMap();
        }
        return this.interfaceMap.getAll(typeKey);
    }

    public void clear() {
        this.extendingClassMap.clear();
        this.interfaceMap.clear();
        getSectionList().clear();
    }

    private void loadExtendingClassMap() {
        MultiMap<TypeKey, ClassId> multiMap = this.extendingClassMap;
        multiMap.clear();
        Section<ClassId> section = getSectionList().getSection(SectionType.CLASS_ID);
        if (section == null) {
            return;
        }
        multiMap.setInitialSize(section.getCount());
        for (ClassId classId : section) {
            TypeKey superClassKey = classId.getSuperClassKey();
            if (!DexUtils.isJavaFramework(superClassKey.getTypeName())) {
                multiMap.put(superClassKey, classId);
            }
        }
    }

    private void loadInterfacesMap() {
        MultiMap<TypeKey, ClassId> multiMap = this.interfaceMap;
        multiMap.clear();
        Section<ClassId> section = getSectionList().getSection(SectionType.CLASS_ID);
        if (section == null) {
            return;
        }
        for (ClassId classId : section) {
            Iterator<TypeKey> interfaceKeys = classId.getInterfaceKeys();
            while (interfaceKeys.hasNext()) {
                TypeKey next = interfaceKeys.next();
                if (!DexUtils.isJavaFramework(next.getTypeName())) {
                    multiMap.put(next, classId);
                }
            }
        }
    }

    public Iterator<StringId> getStrings() {
        return getItems(SectionType.STRING_ID);
    }

    public <T1 extends SectionItem> Iterator<T1> getClonedItems(SectionType<T1> sectionType) {
        Section<T1> section = getSectionList().getSection(sectionType);
        if (section != null) {
            return (Iterator<T1>) section.clonedIterator();
        }
        return EmptyIterator.of();
    }

    public <T1 extends SectionItem> Iterator<T1> getItems(SectionType<T1> sectionType) {
        Section<T1> section = getSectionList().getSection(sectionType);
        if (section != null) {
            return (Iterator<T1>) section.iterator();
        }
        return EmptyIterator.of();
    }

    @Override // com.reandroid.dex.common.FullRefresh
    public void refreshFull() throws DexException {
        getSectionList().refreshFull();
        Checksum checksum = getHeader().checksum;
        int value = checksum.getValue();
        int i = 0;
        while (i < 10) {
            refresh();
            int value2 = checksum.getValue();
            if (value == value2) {
                return;
            }
            i++;
            value = value2;
        }
        throw new DexException("Failed to refresh trials = " + i);
    }

    public void sortSection(SectionType<?>[] sectionTypeArr) {
        refresh();
        getSectionList().sortSection(sectionTypeArr);
        refresh();
    }

    public void clearPoolMap(SectionType<?> sectionType) {
        getSectionList().clearPoolMap(sectionType);
    }

    public void clearPoolMap() {
        this.extendingClassMap.clear();
        this.interfaceMap.clear();
        getSectionList().clearPoolMap();
    }

    public void sortStrings() {
        getSectionList().sortStrings();
    }

    public <T1 extends SectionItem> Iterator<T1> getAll(SectionType<T1> sectionType, Key key) {
        Section<T1> section = get(sectionType);
        if (section != null) {
            return (Iterator<T1>) section.getAll(key);
        }
        return EmptyIterator.of();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T1 extends SectionItem> boolean removeEntries(SectionType<T1> sectionType, Predicate<T1> predicate) {
        Section<T1> section = get(sectionType);
        if (section != null) {
            return section.removeEntries(predicate);
        }
        return false;
    }

    public <T1 extends SectionItem> boolean removeWithKeys(SectionType<T1> sectionType, Predicate<? super Key> predicate) {
        Section<T1> section = get(sectionType);
        if (section != null) {
            return section.removeWithKeys(predicate);
        }
        return false;
    }

    public <T1 extends SectionItem> boolean removeWithKey(SectionType<T1> sectionType, Key key) {
        Section<T1> section = get(sectionType);
        if (section != null) {
            return section.remove(key);
        }
        return false;
    }

    public <T1 extends SectionItem> T1 get(SectionType<T1> sectionType, Key key) {
        Section<T1> section = get(sectionType);
        if (section != null) {
            return (T1) section.getSectionItem(key);
        }
        return null;
    }

    public <T1 extends SectionItem> Section<T1> get(SectionType<T1> sectionType) {
        return getSectionList().getSection(sectionType);
    }

    public DexHeader getHeader() {
        return getSectionList().getHeader();
    }

    public SectionList getSectionList() {
        return this.sectionList;
    }

    public MapList getMapList() {
        return getSectionList().getMapList();
    }

    @Override // com.reandroid.arsc.base.BlockContainer
    protected void onPreRefresh() {
        this.sectionList.refresh();
        this.interfaceMap.clear();
        this.extendingClassMap.clear();
    }

    @Override // com.reandroid.arsc.container.FixedBlockContainer, com.reandroid.arsc.base.BlockContainer
    protected void onRefreshed() {
        this.sectionList.updateHeader();
    }

    public boolean isEmpty() {
        Section section = get(SectionType.CLASS_ID);
        return section == null || section.getCount() == 0;
    }

    public boolean merge(MergeOptions mergeOptions, ClassId classId) {
        return getSectionList().merge(mergeOptions, classId);
    }

    public boolean merge(MergeOptions mergeOptions, DexLayout dexLayout) {
        if (dexLayout == this) {
            mergeOptions.onMergeError(this, getSectionList(), "Can not merge dex file to self");
            return false;
        }
        return getSectionList().merge(mergeOptions, dexLayout.getSectionList());
    }

    public ClassId fromSmali(SmaliClass smaliClass) throws IOException {
        return getSectionList().fromSmali(smaliClass);
    }

    @Override // com.reandroid.arsc.base.BlockContainer, com.reandroid.arsc.base.Block
    public byte[] getBytes() {
        BytesOutputStream bytesOutputStream = new BytesOutputStream(getHeader().fileSize.get());
        try {
            writeBytes(bytesOutputStream);
            bytesOutputStream.close();
        } catch (IOException unused) {
        }
        return bytesOutputStream.toByteArray();
    }

    public void read(byte[] bArr) throws IOException {
        BlockReader blockReader = new BlockReader(bArr);
        readBytes(blockReader);
        blockReader.close();
    }

    public void read(InputStream inputStream) throws IOException {
        BlockReader blockReader;
        if (inputStream instanceof BlockReader) {
            blockReader = (BlockReader) inputStream;
        } else {
            blockReader = new BlockReader(inputStream);
        }
        readBytes(blockReader);
        blockReader.close();
    }

    public void readStrings(InputStream inputStream) throws IOException {
        BlockReader blockReader;
        if (inputStream instanceof BlockReader) {
            blockReader = (BlockReader) inputStream;
        } else {
            blockReader = new BlockReader(inputStream);
        }
        readStrings(blockReader);
    }

    public void readStrings(BlockReader blockReader) throws IOException {
        readSections(blockReader, new Predicate() { // from class: com.reandroid.dex.sections.DexLayout$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return DexLayout.lambda$readStrings$0((SectionType) obj);
            }
        });
    }

    static /* synthetic */ boolean lambda$readStrings$0(SectionType sectionType) {
        return sectionType == SectionType.STRING_ID || sectionType == SectionType.STRING_DATA;
    }

    public void readClassIds(BlockReader blockReader) throws IOException {
        readSections(blockReader, new Predicate() { // from class: com.reandroid.dex.sections.DexLayout$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return DexLayout.lambda$readClassIds$1((SectionType) obj);
            }
        });
    }

    static /* synthetic */ boolean lambda$readClassIds$1(SectionType sectionType) {
        return sectionType == SectionType.STRING_ID || sectionType == SectionType.STRING_DATA || sectionType == SectionType.TYPE_ID || sectionType == SectionType.CLASS_ID;
    }

    public void readSections(BlockReader blockReader, Predicate<SectionType<?>> predicate) throws IOException {
        getSectionList().readSections(blockReader, predicate);
        blockReader.close();
    }

    public void read(File file) throws IOException {
        BlockReader blockReader = new BlockReader(file);
        readBytes(blockReader);
        blockReader.close();
    }

    public void write(File file) throws IOException {
        OutputStream outputStream = FileUtil.outputStream(file);
        writeBytes(outputStream);
        outputStream.close();
    }

    public Object getTag() {
        return this.mTag;
    }

    public void setTag(Object obj) {
        this.mTag = obj;
    }

    public String getSimpleName() {
        return this.mSimpleName;
    }

    public void setSimpleName(String str) {
        this.mSimpleName = str;
    }

    public static DexLayout createDefault() {
        DexLayout dexLayout = new DexLayout();
        SectionList sectionList = dexLayout.getSectionList();
        MapList mapList = sectionList.getMapList();
        mapList.getOrCreate(SectionType.HEADER);
        mapList.getOrCreate(SectionType.MAP_LIST);
        for (SectionType<?> sectionType : SectionType.getR8Order()) {
            sectionList.getOrCreateSection(sectionType);
        }
        sectionList.getMapList().linkHeader(sectionList.getHeader());
        return dexLayout;
    }

    public static boolean isDexFile(File file) {
        if (file == null || !file.isFile()) {
            return false;
        }
        DexHeader header = null;
        try {
            InputStream inputStream = FileUtil.inputStream(file);
            header = DexHeader.readHeader(inputStream);
            inputStream.close();
        } catch (IOException unused) {
        }
        return isDexFile(header);
    }

    public static boolean isDexFile(InputStream inputStream) {
        DexHeader header;
        try {
            header = DexHeader.readHeader(inputStream);
            try {
                inputStream.close();
            } catch (IOException unused) {
            }
        } catch (IOException unused2) {
            header = null;
        }
        return isDexFile(header);
    }

    private static boolean isDexFile(DexHeader dexHeader) {
        int version;
        return dexHeader != null && !dexHeader.magic.isDefault() && (version = dexHeader.getVersion()) > 0 && version < 1000;
    }
}
