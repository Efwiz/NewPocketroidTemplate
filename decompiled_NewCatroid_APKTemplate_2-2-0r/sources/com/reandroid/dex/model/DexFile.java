package com.reandroid.dex.model;

import com.android.dex.DexFormat;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.common.Origin;
import com.reandroid.dex.base.DexException;
import com.reandroid.dex.common.AccessFlag;
import com.reandroid.dex.common.DexUtils;
import com.reandroid.dex.common.FullRefresh;
import com.reandroid.dex.common.SectionItem;
import com.reandroid.dex.data.CodeItem;
import com.reandroid.dex.data.DebugInfo;
import com.reandroid.dex.id.ClassId;
import com.reandroid.dex.id.StringId;
import com.reandroid.dex.id.TypeId;
import com.reandroid.dex.key.Key;
import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.pool.DexSectionPool;
import com.reandroid.dex.sections.DexLayout;
import com.reandroid.dex.sections.Marker;
import com.reandroid.dex.sections.MergeOptions;
import com.reandroid.dex.sections.Section;
import com.reandroid.dex.sections.SectionType;
import com.reandroid.dex.smali.SmaliReader;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.dex.smali.model.SmaliClass;
import com.reandroid.utils.collection.CollectionUtil;
import com.reandroid.utils.collection.ComputeIterator;
import com.reandroid.utils.collection.FilterIterator;
import com.reandroid.utils.collection.IterableIterator;
import com.reandroid.utils.collection.SingleIterator;
import com.reandroid.utils.io.FileByteSource;
import com.reandroid.utils.io.FileIterator;
import com.reandroid.utils.io.FileUtil;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes11.dex */
public class DexFile implements DexClassRepository, Closeable, Iterable<DexClass>, FullRefresh {
    private boolean closed;
    private DexDirectory dexDirectory;
    private final DexLayout dexLayout;

    public DexFile(DexLayout dexLayout) {
        this.dexLayout = dexLayout;
        dexLayout.setTag(this);
    }

    public int getVersion() {
        return getDexLayout().getVersion();
    }

    public void setVersion(int i) {
        getDexLayout().setVersion(i);
    }

    @Override // com.reandroid.dex.model.DexClassRepository
    public int shrink() {
        return getDexLayout().getSectionList().shrink();
    }

    public int clearDuplicateData() {
        return getDexLayout().getSectionList().clearDuplicateData();
    }

    public int clearUnused() {
        return getDexLayout().getSectionList().clearUnused();
    }

    public int clearEmptySections() {
        return getDexLayout().getSectionList().clearEmptySections();
    }

    public void fixDebugLineNumbers() {
        Section section = getSection(SectionType.CODE);
        if (section == null) {
            return;
        }
        Iterator it = section.iterator();
        while (it.hasNext()) {
            DebugInfo debugInfo = ((CodeItem) it.next()).getDebugInfo();
            if (debugInfo != null) {
                debugInfo.getDebugSequence().fixDebugLineNumbers();
            }
        }
    }

    public DexClassRepository getClassRepository() {
        DexDirectory dexDirectory = getDexDirectory();
        return dexDirectory != null ? dexDirectory : this;
    }

    public DexDirectory getDexDirectory() {
        return this.dexDirectory;
    }

    public void setDexDirectory(DexDirectory dexDirectory) {
        this.dexDirectory = dexDirectory;
        DexLayout dexLayout = getDexLayout();
        dexLayout.setTag(this);
        dexLayout.setSimpleName(getSimpleName());
    }

    public Iterator<DexClass> getSubTypes(TypeKey typeKey) {
        return ComputeIterator.of(getSubTypeIds(typeKey), new DexFile$$ExternalSyntheticLambda1(this));
    }

    public Iterator<DexClass> getExtendingClasses(TypeKey typeKey) {
        return ComputeIterator.of(getExtendingClassIds(typeKey), new DexFile$$ExternalSyntheticLambda1(this));
    }

    public Iterator<DexClass> getImplementClasses(TypeKey typeKey) {
        return ComputeIterator.of(getImplementationIds(typeKey), new DexFile$$ExternalSyntheticLambda1(this));
    }

    public Iterator<ClassId> getSubTypeIds(TypeKey typeKey) {
        return getDexLayout().getSubTypes(typeKey);
    }

    public Iterator<ClassId> getExtendingClassIds(TypeKey typeKey) {
        return getDexLayout().getExtendingClassIds(typeKey);
    }

    public Iterator<ClassId> getImplementationIds(TypeKey typeKey) {
        return getDexLayout().getImplementationIds(typeKey);
    }

    public DexClass getOrCreateClass(String str) {
        return getOrCreateClass(new TypeKey(str));
    }

    public DexClass getOrCreateClass(TypeKey typeKey) {
        DexClass dexClassSearch = search(typeKey);
        return dexClassSearch != null ? dexClassSearch : create(getOrCreateClassId(typeKey));
    }

    public DexSource<DexFile> getSource() {
        DexDirectory dexDirectory = getDexDirectory();
        if (dexDirectory != null) {
            return dexDirectory.getDexSourceSet().getSource(this);
        }
        return null;
    }

    public String getSimpleName() {
        return getDexLayout().getSimpleName();
    }

    public void setSimpleName(String str) {
        getDexLayout().setSimpleName(str);
    }

    @Override // java.lang.Iterable
    public Iterator<DexClass> iterator() {
        return getDexClasses();
    }

    /* JADX INFO: renamed from: lambda$removeClasses$0$com-reandroid-dex-model-DexFile, reason: not valid java name */
    /* synthetic */ boolean m2767lambda$removeClasses$0$comreandroiddexmodelDexFile(Predicate predicate, ClassId classId) {
        return predicate.test(create(classId));
    }

    @Override // com.reandroid.dex.model.DexClassRepository
    public boolean removeClasses(final Predicate<? super DexClass> predicate) {
        return getDexLayout().removeEntries(SectionType.CLASS_ID, new Predicate() { // from class: com.reandroid.dex.model.DexFile$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.m2767lambda$removeClasses$0$comreandroiddexmodelDexFile(predicate, (ClassId) obj);
            }
        });
    }

    @Override // com.reandroid.dex.model.DexClassRepository
    public <T1 extends SectionItem> boolean removeEntries(SectionType<T1> sectionType, Predicate<T1> predicate) {
        return getDexLayout().removeEntries(sectionType, predicate);
    }

    @Override // com.reandroid.dex.model.DexClassRepository
    public <T1 extends SectionItem> boolean removeEntriesWithKey(SectionType<T1> sectionType, Predicate<? super Key> predicate) {
        return getDexLayout().removeWithKeys(sectionType, predicate);
    }

    @Override // com.reandroid.dex.model.DexClassRepository
    public <T1 extends SectionItem> boolean removeEntry(SectionType<T1> sectionType, Key key) {
        return getDexLayout().removeWithKey(sectionType, key);
    }

    @Override // com.reandroid.dex.model.DexClassRepository
    public int getDexClassesCount() {
        Section section = getSection(SectionType.CLASS_ID);
        if (section != null) {
            return section.getCount();
        }
        return 0;
    }

    @Override // com.reandroid.dex.model.DexClassRepository
    public DexClass getDexClass(TypeKey typeKey) {
        ClassId classId = (ClassId) getItem(SectionType.CLASS_ID, typeKey);
        if (classId == null) {
            return null;
        }
        return create(classId);
    }

    @Override // com.reandroid.dex.model.DexClassRepository
    public Iterator<DexClass> getDexClasses(Predicate<? super TypeKey> predicate) {
        return ComputeIterator.of(getClassIds(predicate), new DexFile$$ExternalSyntheticLambda1(this));
    }

    @Override // com.reandroid.dex.model.DexClassRepository
    public Iterator<DexClass> getDexClassesCloned(Predicate<? super TypeKey> predicate) {
        return ComputeIterator.of(getClassIdsCloned(predicate), new DexFile$$ExternalSyntheticLambda1(this));
    }

    @Override // com.reandroid.dex.model.DexClassRepository
    public <T1 extends SectionItem> Iterator<Section<T1>> getSections(SectionType<T1> sectionType) {
        return SingleIterator.of(getSection(sectionType));
    }

    @Override // com.reandroid.dex.model.DexClassRepository
    public Iterator<DexClass> searchExtending(TypeKey typeKey) {
        DexDirectory dexDirectory = getDexDirectory();
        if (dexDirectory != null) {
            return dexDirectory.searchExtending(typeKey);
        }
        return getExtendingClasses(typeKey);
    }

    @Override // com.reandroid.dex.model.DexClassRepository
    public Iterator<DexClass> searchImplementations(TypeKey typeKey) {
        DexDirectory dexDirectory = getDexDirectory();
        if (dexDirectory != null) {
            return dexDirectory.searchImplementations(typeKey);
        }
        return getImplementClasses(typeKey);
    }

    public DexClass search(TypeKey typeKey) {
        return getClassRepository().getDexClass(typeKey);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ClassId getOrCreateClassId(TypeKey typeKey) {
        DexSectionPool pool = getDexLayout().get(SectionType.CLASS_ID).getPool();
        ClassId classId = (ClassId) pool.get(typeKey);
        if (classId != null) {
            return classId;
        }
        ClassId classId2 = (ClassId) pool.getOrCreate(typeKey);
        classId2.getOrCreateClassData();
        classId2.setSuperClass(TypeKey.OBJECT);
        classId2.setSourceFile(DexUtils.toSourceFileName(typeKey.getTypeName()));
        classId2.addAccessFlag(AccessFlag.PUBLIC);
        return classId2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public DexClass create(ClassId classId) {
        return new DexClass(this, classId);
    }

    public Marker getOrCreateMarker() {
        Marker marker = (Marker) CollectionUtil.getFirst(getMarkers());
        if (marker != null) {
            return marker;
        }
        Marker markerCreateR8 = Marker.createR8();
        markerCreateR8.setStringId((StringId) getSection(SectionType.STRING_ID).createItem());
        markerCreateR8.save();
        return markerCreateR8;
    }

    public void addMarker(Marker marker) {
        if (marker.getStringId() == null) {
            marker.setStringId((StringId) getSection(SectionType.STRING_ID).createItem());
        }
        marker.save();
    }

    @Override // com.reandroid.dex.model.DexClassRepository
    public Iterator<Marker> getMarkers() {
        return getDexLayout().getMarkers();
    }

    @Override // com.reandroid.dex.common.FullRefresh
    public void refreshFull() throws DexException {
        getDexLayout().refreshFull();
    }

    public void sortSection(SectionType<?>[] sectionTypeArr) {
        refresh();
        getDexLayout().sortSection(sectionTypeArr);
        refresh();
    }

    public void clearPoolMap(SectionType<?> sectionType) {
        getDexLayout().clearPoolMap(sectionType);
    }

    @Override // com.reandroid.dex.model.DexClassRepository
    public void clearPoolMap() {
        getDexLayout().clearPoolMap();
    }

    public void sortStrings() {
        getDexLayout().sortStrings();
    }

    public Iterator<DexInstruction> getDexInstructions() {
        return new IterableIterator<DexClass, DexInstruction>(getDexClasses()) { // from class: com.reandroid.dex.model.DexFile.1
            @Override // com.reandroid.utils.collection.IterableIterator
            public Iterator<DexInstruction> iterator(DexClass dexClass) {
                return dexClass.getDexInstructions();
            }
        };
    }

    public Iterator<DexInstruction> getDexInstructionsCloned() {
        return new IterableIterator<DexClass, DexInstruction>(getDexClassesCloned()) { // from class: com.reandroid.dex.model.DexFile.2
            @Override // com.reandroid.utils.collection.IterableIterator
            public Iterator<DexInstruction> iterator(DexClass dexClass) {
                return dexClass.getDexInstructions();
            }
        };
    }

    public Iterator<ClassId> getClassIds(final Predicate<? super TypeKey> predicate) {
        return FilterIterator.of(getItems(SectionType.CLASS_ID), new Predicate() { // from class: com.reandroid.dex.model.DexFile$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return DexFile.lambda$getClassIds$1(predicate, (ClassId) obj);
            }
        });
    }

    static /* synthetic */ boolean lambda$getClassIds$1(Predicate predicate, ClassId classId) {
        return predicate == null || predicate.test(classId.getKey());
    }

    public Iterator<ClassId> getClassIdsCloned(final Predicate<? super TypeKey> predicate) {
        return FilterIterator.of(getClonedItems(SectionType.CLASS_ID), new Predicate() { // from class: com.reandroid.dex.model.DexFile$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return DexFile.lambda$getClassIdsCloned$2(predicate, (ClassId) obj);
            }
        });
    }

    static /* synthetic */ boolean lambda$getClassIdsCloned$2(Predicate predicate, ClassId classId) {
        return predicate == null || predicate.test(classId.getKey());
    }

    public Iterator<TypeId> getTypes() {
        return getItems(SectionType.TYPE_ID);
    }

    public <T1 extends SectionItem> Section<T1> getSection(SectionType<T1> sectionType) {
        return getDexLayout().get(sectionType);
    }

    @Override // com.reandroid.arsc.base.BlockRefresh
    public void refresh() {
        getDexLayout().refresh();
    }

    public DexLayout getDexLayout() {
        return this.dexLayout;
    }

    public boolean isEmpty() {
        return getDexLayout().isEmpty();
    }

    public int getIndex() {
        DexDirectory dexDirectory = getDexDirectory();
        if (dexDirectory != null) {
            return dexDirectory.indexOf(this);
        }
        return -1;
    }

    public boolean merge(DexClass dexClass) {
        return merge(new DexMergeOptions(true), dexClass);
    }

    public boolean merge(MergeOptions mergeOptions, DexClass dexClass) {
        return merge(mergeOptions, dexClass.getId());
    }

    public boolean merge(ClassId classId) {
        return merge(new DexMergeOptions(true), classId);
    }

    public boolean merge(MergeOptions mergeOptions, ClassId classId) {
        return getDexLayout().merge(mergeOptions, classId);
    }

    public boolean merge(MergeOptions mergeOptions, DexFile dexFile) {
        if (dexFile == null || dexFile.isEmpty()) {
            return false;
        }
        return getDexLayout().merge(mergeOptions, dexFile.getDexLayout());
    }

    public void parseSmaliDirectory(File file) throws IOException {
        requireNotClosed();
        if (!file.isDirectory()) {
            throw new FileNotFoundException("No such directory: " + file);
        }
        FileIterator fileIterator = new FileIterator(file, FileIterator.getExtensionFilter(".smali"));
        FileByteSource fileByteSource = new FileByteSource();
        SmaliReader smaliReader = new SmaliReader(fileByteSource);
        DexLayout dexLayout = getDexLayout();
        while (fileIterator.hasNext()) {
            smaliReader.reset();
            File next = fileIterator.next();
            fileByteSource.setFile(next);
            smaliReader.setOrigin(Origin.createNew(next));
            SmaliClass smaliClass = new SmaliClass();
            smaliClass.parse(smaliReader);
            dexLayout.fromSmali(smaliClass);
        }
        shrink();
    }

    public void parseSmaliFile(File file) throws IOException {
        requireNotClosed();
        fromSmali(SmaliReader.of(file));
    }

    public void fromSmaliAll(SmaliReader smaliReader) throws IOException {
        smaliReader.skipWhitespacesOrComment();
        while (!smaliReader.finished()) {
            fromSmali(smaliReader);
            smaliReader.skipWhitespacesOrComment();
        }
    }

    public DexClass fromSmali(SmaliReader smaliReader) throws IOException {
        requireNotClosed();
        SmaliClass smaliClass = new SmaliClass();
        smaliClass.parse(smaliReader);
        DexClass dexClassFromSmali = fromSmali(smaliClass);
        smaliReader.skipWhitespacesOrComment();
        return dexClassFromSmali;
    }

    public DexClass fromSmali(SmaliClass smaliClass) throws IOException {
        requireNotClosed();
        return create(getDexLayout().fromSmali(smaliClass));
    }

    public byte[] getBytes() {
        if (isClosed()) {
            return null;
        }
        if (isEmpty()) {
            return new byte[0];
        }
        return getDexLayout().getBytes();
    }

    public void write(File file) throws IOException {
        requireNotClosed();
        OutputStream outputStream = FileUtil.outputStream(file);
        write(outputStream);
        outputStream.close();
    }

    public void write(OutputStream outputStream) throws IOException {
        requireNotClosed();
        byte[] bytes = getBytes();
        outputStream.write(bytes, 0, bytes.length);
    }

    public String printSectionInfo() {
        return getDexLayout().getMapList().toString();
    }

    public void writeSmali(SmaliWriter smaliWriter, File file) throws IOException {
        requireNotClosed();
        File file2 = new File(file, buildSmaliDirectoryName());
        Iterator<DexClass> it = iterator();
        while (it.hasNext()) {
            it.next().writeSmali(smaliWriter, file2);
        }
    }

    public String buildSmaliDirectoryName() {
        DexDirectory dexDirectory = getDexDirectory();
        int i = 0;
        if (dexDirectory == null) {
            String simpleName = getSimpleName();
            if (simpleName == null || !simpleName.endsWith(".dex")) {
                return "classes";
            }
            return simpleName.substring(0, simpleName.length() - 4);
        }
        Iterator<DexFile> it = dexDirectory.iterator();
        while (it.hasNext() && it.next() != this) {
            i++;
        }
        if (i == 0) {
            return "classes";
        }
        return "classes" + (i + 1);
    }

    public String getFileName() {
        String simpleName = getSimpleName();
        if (simpleName == null) {
            return buildSmaliDirectoryName() + ".dex";
        }
        return FileUtil.getFileName(simpleName);
    }

    private void requireNotClosed() throws IOException {
        if (isClosed()) {
            throw new IOException("Closed");
        }
    }

    public boolean isClosed() {
        return this.closed;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this.closed) {
            return;
        }
        this.closed = true;
        getDexLayout().clear();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getSimpleName());
        sb.append(", version = ");
        sb.append(getVersion());
        sb.append(", classes = ");
        sb.append(getDexClassesCount());
        List list = CollectionUtil.toList(getMarkers());
        int size = list.size();
        if (size != 0) {
            sb.append(", markers = ");
            sb.append(size);
            if (size > 10) {
                size = 10;
            }
            for (int i = 0; i < size; i++) {
                sb.append('\n');
                sb.append(list.get(i));
            }
        }
        return sb.toString();
    }

    public static DexFile read(byte[] bArr) throws IOException {
        return read(new BlockReader(bArr));
    }

    public static DexFile read(InputStream inputStream) throws IOException {
        return read(new BlockReader(inputStream));
    }

    public static DexFile read(File file) throws IOException {
        return read(new BlockReader(file));
    }

    public static DexFile read(BlockReader blockReader) throws IOException {
        DexLayout dexLayout = new DexLayout();
        dexLayout.readBytes(blockReader);
        blockReader.close();
        return new DexFile(dexLayout);
    }

    public static DexFile readStrings(BlockReader blockReader) throws IOException {
        DexLayout dexLayout = new DexLayout();
        dexLayout.readStrings(blockReader);
        return new DexFile(dexLayout);
    }

    public static DexFile readClassIds(BlockReader blockReader) throws IOException {
        DexLayout dexLayout = new DexLayout();
        dexLayout.readClassIds(blockReader);
        return new DexFile(dexLayout);
    }

    public static DexFile readSections(BlockReader blockReader, Predicate<SectionType<?>> predicate) throws IOException {
        DexLayout dexLayout = new DexLayout();
        dexLayout.readSections(blockReader, predicate);
        return new DexFile(dexLayout);
    }

    public static DexFile readStrings(InputStream inputStream) throws IOException {
        return readStrings(new BlockReader(inputStream));
    }

    public static DexFile readClassIds(InputStream inputStream) throws IOException {
        return readClassIds(new BlockReader(inputStream));
    }

    public static DexFile createDefault() {
        return new DexFile(DexLayout.createDefault());
    }

    public static DexFile findDexFile(ClassId classId) {
        if (classId == null) {
            return null;
        }
        return findDexFile((DexLayout) classId.getParentInstance(DexLayout.class));
    }

    public static DexFile findDexFile(DexLayout dexLayout) {
        if (dexLayout == null) {
            return null;
        }
        Object tag = dexLayout.getTag();
        if (tag instanceof DexFile) {
            return (DexFile) tag;
        }
        return null;
    }

    public static int getDexFileNumber(String str) {
        int iLastIndexOf = str.lastIndexOf(47);
        if (iLastIndexOf < 0) {
            iLastIndexOf = str.lastIndexOf(92);
        }
        if (iLastIndexOf >= 0) {
            str = str.substring(iLastIndexOf + 1);
        }
        if (str.equals(DexFormat.DEX_IN_JAR_NAME)) {
            return 0;
        }
        if (str.startsWith("classes") && str.endsWith(".dex")) {
            try {
                return Integer.parseInt(str.substring("classes".length(), str.length() - ".dex".length()));
            } catch (NumberFormatException unused) {
            }
        }
        return -1;
    }

    public static String getDexName(int i) {
        if (i == 0) {
            return DexFormat.DEX_IN_JAR_NAME;
        }
        if (i == 1) {
            i = 2;
        }
        return "classes" + i + ".dex";
    }
}
