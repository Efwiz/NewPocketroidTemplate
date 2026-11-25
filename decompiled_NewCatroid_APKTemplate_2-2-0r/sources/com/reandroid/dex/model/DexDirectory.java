package com.reandroid.dex.model;

import com.reandroid.archive.ZipEntryMap;
import com.reandroid.dex.common.FullRefresh;
import com.reandroid.dex.common.SectionItem;
import com.reandroid.dex.id.ClassId;
import com.reandroid.dex.id.FieldId;
import com.reandroid.dex.id.MethodId;
import com.reandroid.dex.id.StringId;
import com.reandroid.dex.key.FieldKey;
import com.reandroid.dex.key.Key;
import com.reandroid.dex.key.KeyPair;
import com.reandroid.dex.key.MethodKey;
import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.key.TypeKeyReference;
import com.reandroid.dex.sections.Marker;
import com.reandroid.dex.sections.MergeOptions;
import com.reandroid.dex.sections.Section;
import com.reandroid.dex.sections.SectionArray;
import com.reandroid.dex.sections.SectionType;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.utils.collection.ArrayCollection;
import com.reandroid.utils.collection.CollectionUtil;
import com.reandroid.utils.collection.CombiningIterator;
import com.reandroid.utils.collection.ComputeIterator;
import com.reandroid.utils.collection.EmptyIterator;
import com.reandroid.utils.collection.EmptyList;
import com.reandroid.utils.collection.FilterIterator;
import com.reandroid.utils.collection.IterableIterator;
import com.reandroid.utils.collection.SingleIterator;
import com.reandroid.utils.collection.UniqueIterator;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes11.dex */
public class DexDirectory implements Iterable<DexFile>, Closeable, DexClassRepository, FullRefresh {
    private final DexFileSourceSet dexSourceSet = new DexFileSourceSet();
    private final ArrayCollection<TypeKeyReference> externalTypeKeyReferenceList = new ArrayCollection<>();
    private Object mTag;

    public Object getTag() {
        return this.mTag;
    }

    public void setTag(Object obj) {
        this.mTag = obj;
    }

    public int getVersion() {
        DexFile first = getFirst();
        if (first != null) {
            return first.getVersion();
        }
        return 0;
    }

    public void setVersion(int i) {
        Iterator<DexFile> it = iterator();
        while (it.hasNext()) {
            it.next().setVersion(i);
        }
    }

    @Override // com.reandroid.dex.model.DexClassRepository
    public Iterator<Marker> getMarkers() {
        return new IterableIterator<DexFile, Marker>(iterator()) { // from class: com.reandroid.dex.model.DexDirectory.1
            @Override // com.reandroid.utils.collection.IterableIterator
            public Iterator<Marker> iterator(DexFile dexFile) {
                return dexFile.getMarkers();
            }
        };
    }

    public int mergeAll(MergeOptions mergeOptions, Iterable<DexClass> iterable) {
        return mergeAll(mergeOptions, iterable.iterator());
    }

    public int mergeAll(MergeOptions mergeOptions, Iterator<DexClass> it) {
        int i = 0;
        while (it.hasNext()) {
            if (merge(mergeOptions, it.next())) {
                i++;
            }
        }
        return i;
    }

    public boolean merge(DexClass dexClass) {
        return merge(new DexMergeOptions(), dexClass);
    }

    public boolean merge(MergeOptions mergeOptions, DexClass dexClass) {
        if (dexClass.isInSameDirectory(this)) {
            return false;
        }
        if (containsClass(dexClass.getKey())) {
            mergeOptions.onDuplicate(dexClass.getId());
            return false;
        }
        int mergeStartDexFile = mergeOptions.getMergeStartDexFile();
        boolean z = false;
        while (mergeStartDexFile < size()) {
            if (get(mergeStartDexFile).merge(mergeOptions, dexClass)) {
                if (z) {
                    mergeOptions.setMergeStartDexFile(mergeStartDexFile);
                }
                return true;
            }
            mergeStartDexFile++;
            z = true;
        }
        return false;
    }

    public void merge(DexDirectory dexDirectory) {
        merge(new DexMergeOptions(false), dexDirectory);
    }

    public void merge(MergeOptions mergeOptions, DexDirectory dexDirectory) {
        if (dexDirectory == this) {
            throw new IllegalArgumentException("Cyclic merge");
        }
        int mergeStartDexFile = mergeOptions.getMergeStartDexFile();
        int i = mergeStartDexFile;
        while (true) {
            DexFile dexFile = get(i);
            DexFile lastNonEmpty = dexDirectory.getLastNonEmpty(mergeOptions, 0);
            if (dexFile == null || lastNonEmpty == null) {
                break;
            } else if (!dexFile.merge(mergeOptions, lastNonEmpty)) {
                i++;
            }
        }
        if (i != mergeStartDexFile) {
            mergeOptions.setMergeStartDexFile(i);
        }
        shrink();
        dexDirectory.merge(mergeOptions);
        getDexSourceSet().merge(dexDirectory.getDexSourceSet());
    }

    public void merge() {
        merge(new DexMergeOptions());
    }

    public void merge(MergeOptions mergeOptions) {
        if (size() < 2) {
            return;
        }
        int i = 0;
        while (true) {
            DexFile dexFile = get(i);
            int i2 = i + 1;
            DexFile lastNonEmpty = getLastNonEmpty(mergeOptions, i2);
            if (dexFile == null || lastNonEmpty == null) {
                break;
            } else if (!dexFile.merge(mergeOptions, lastNonEmpty)) {
                i = i2;
            }
        }
        shrink();
    }

    private DexFile getLastNonEmpty(MergeOptions mergeOptions, int i) {
        for (int size = size() - 1; size >= i; size--) {
            DexFile dexFile = get(size);
            if (!mergeOptions.isEmptyDexFile(dexFile.getDexLayout())) {
                return dexFile;
            }
        }
        return null;
    }

    @Override // com.reandroid.dex.model.DexClassRepository
    public int shrink() {
        int iCleanMissingMembers = DalvikUtil.cleanMissingMembers(this);
        Iterator<DexFile> it = iterator();
        while (it.hasNext()) {
            iCleanMissingMembers += it.next().shrink();
        }
        return iCleanMissingMembers;
    }

    public int clearDuplicateData() {
        Iterator<DexFile> it = iterator();
        int iClearDuplicateData = 0;
        while (it.hasNext()) {
            iClearDuplicateData += it.next().clearDuplicateData();
        }
        return iClearDuplicateData;
    }

    public int clearUnused() {
        Iterator<DexFile> it = iterator();
        int iClearUnused = 0;
        while (it.hasNext()) {
            iClearUnused += it.next().clearUnused();
        }
        return iClearUnused;
    }

    public void cleanDuplicateDebugLines() {
        Iterator<DexFile> it = iterator();
        while (it.hasNext()) {
            it.next().fixDebugLineNumbers();
        }
    }

    public Iterator<FieldKey> findEquivalentFields(FieldKey fieldKey) {
        DexClass dexClass = getDexClass(fieldKey.getDeclaring());
        if (dexClass == null) {
            return EmptyIterator.of();
        }
        DexField field = dexClass.getField(fieldKey);
        if (field == null) {
            return EmptyIterator.of();
        }
        DexClass dexClass2 = field.getDexClass();
        final FieldKey key = field.getKey();
        return CombiningIterator.two(SingleIterator.of(key), ComputeIterator.of(getSubTypes(dexClass2.getKey()), new Function() { // from class: com.reandroid.dex.model.DexDirectory$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return DexDirectory.lambda$findEquivalentFields$0(key, (DexClass) obj);
            }
        }));
    }

    static /* synthetic */ FieldKey lambda$findEquivalentFields$0(FieldKey fieldKey, DexClass dexClass) {
        FieldKey fieldKeyChangeDeclaring = fieldKey.changeDeclaring(dexClass.getKey());
        if (fieldKey.equals(dexClass.getField(fieldKeyChangeDeclaring).getKey())) {
            return fieldKeyChangeDeclaring;
        }
        return null;
    }

    public Iterator<DexClass> getSubTypes(final TypeKey typeKey) {
        return new IterableIterator<DexFile, DexClass>(iterator()) { // from class: com.reandroid.dex.model.DexDirectory.2
            @Override // com.reandroid.utils.collection.IterableIterator
            public Iterator<DexClass> iterator(DexFile dexFile) {
                return dexFile.getSubTypes(typeKey);
            }
        };
    }

    public Iterator<DexClass> getImplementClasses(final TypeKey typeKey) {
        return new IterableIterator<DexFile, DexClass>(iterator()) { // from class: com.reandroid.dex.model.DexDirectory.3
            @Override // com.reandroid.utils.collection.IterableIterator
            public Iterator<DexClass> iterator(DexFile dexFile) {
                return dexFile.getImplementClasses(typeKey);
            }
        };
    }

    public void save() throws IOException {
        this.dexSourceSet.saveAll();
    }

    public void save(File file) throws IOException {
        this.dexSourceSet.saveAll(file);
    }

    public Iterator<ClassId> getClassIds() {
        return getItems(SectionType.CLASS_ID);
    }

    public <T1 extends SectionItem> T1 get(SectionType<T1> sectionType, Key key) {
        Iterator<DexFile> it = iterator();
        while (it.hasNext()) {
            T1 t1 = (T1) it.next().getItem(sectionType, key);
            if (t1 != null) {
                return t1;
            }
        }
        return null;
    }

    @Override // com.reandroid.dex.model.DexClassRepository
    public <T1 extends SectionItem> boolean removeEntries(SectionType<T1> sectionType, Predicate<T1> predicate) {
        Iterator<DexFile> itClonedIterator = clonedIterator();
        boolean z = false;
        while (itClonedIterator.hasNext()) {
            if (itClonedIterator.next().removeEntries(sectionType, predicate)) {
                z = true;
            }
        }
        return z;
    }

    @Override // com.reandroid.dex.model.DexClassRepository
    public <T1 extends SectionItem> boolean removeEntry(SectionType<T1> sectionType, Key key) {
        Iterator<DexFile> it = iterator();
        boolean z = false;
        while (it.hasNext()) {
            if (it.next().removeEntry(sectionType, key)) {
                z = true;
            }
        }
        return z;
    }

    @Override // com.reandroid.dex.model.DexClassRepository
    public <T1 extends SectionItem> boolean removeEntriesWithKey(SectionType<T1> sectionType, Predicate<? super Key> predicate) {
        Iterator<DexFile> it = iterator();
        boolean z = false;
        while (it.hasNext()) {
            if (it.next().removeEntriesWithKey(sectionType, predicate)) {
                z = true;
            }
        }
        return z;
    }

    @Override // com.reandroid.dex.model.DexClassRepository
    public boolean removeClasses(Predicate<? super DexClass> predicate) {
        Iterator<DexFile> itClonedIterator = clonedIterator();
        boolean z = false;
        while (itClonedIterator.hasNext()) {
            if (itClonedIterator.next().removeClasses(predicate)) {
                z = true;
            }
        }
        return z;
    }

    @Override // com.reandroid.dex.model.DexClassRepository
    public <T1 extends SectionItem> Iterator<T1> getClonedItems(final SectionType<T1> sectionType) {
        return new IterableIterator<DexFile, T1>(clonedIterator()) { // from class: com.reandroid.dex.model.DexDirectory.4
            @Override // com.reandroid.utils.collection.IterableIterator
            public Iterator<T1> iterator(DexFile dexFile) {
                return dexFile.getClonedItems(sectionType);
            }
        };
    }

    @Override // com.reandroid.dex.model.DexClassRepository
    public int getDexClassesCount() {
        Iterator<DexFile> it = iterator();
        int dexClassesCount = 0;
        while (it.hasNext()) {
            dexClassesCount += it.next().getDexClassesCount();
        }
        return dexClassesCount;
    }

    @Override // com.reandroid.dex.model.DexClassRepository
    public DexClass getDexClass(TypeKey typeKey) {
        Iterator<DexFile> it = iterator();
        while (it.hasNext()) {
            DexClass dexClass = it.next().getDexClass(typeKey);
            if (dexClass != null) {
                return dexClass;
            }
        }
        return null;
    }

    @Override // com.reandroid.dex.model.DexClassRepository
    public Iterator<DexClass> getDexClasses(final Predicate<? super TypeKey> predicate) {
        return new IterableIterator<DexFile, DexClass>(iterator()) { // from class: com.reandroid.dex.model.DexDirectory.5
            @Override // com.reandroid.utils.collection.IterableIterator
            public Iterator<DexClass> iterator(DexFile dexFile) {
                return dexFile.getDexClasses(predicate);
            }
        };
    }

    @Override // com.reandroid.dex.model.DexClassRepository
    public Iterator<DexClass> getDexClassesCloned(final Predicate<? super TypeKey> predicate) {
        return new IterableIterator<DexFile, DexClass>(clonedIterator()) { // from class: com.reandroid.dex.model.DexDirectory.6
            @Override // com.reandroid.utils.collection.IterableIterator
            public Iterator<DexClass> iterator(DexFile dexFile) {
                return dexFile.getDexClassesCloned(predicate);
            }
        };
    }

    @Override // com.reandroid.dex.model.DexClassRepository
    public <T1 extends SectionItem> Iterator<Section<T1>> getSections(final SectionType<T1> sectionType) {
        return new IterableIterator<DexFile, Section<T1>>(iterator()) { // from class: com.reandroid.dex.model.DexDirectory.7
            @Override // com.reandroid.utils.collection.IterableIterator
            public Iterator<Section<T1>> iterator(DexFile dexFile) {
                return dexFile.getSections(sectionType);
            }
        };
    }

    public Iterator<DexInstruction> getDexInstructions() {
        return new IterableIterator<DexFile, DexInstruction>(iterator()) { // from class: com.reandroid.dex.model.DexDirectory.8
            @Override // com.reandroid.utils.collection.IterableIterator
            public Iterator<DexInstruction> iterator(DexFile dexFile) {
                return dexFile.getDexInstructions();
            }
        };
    }

    public Iterator<DexInstruction> getDexInstructionsCloned() {
        return new IterableIterator<DexFile, DexInstruction>(clonedIterator()) { // from class: com.reandroid.dex.model.DexDirectory.9
            @Override // com.reandroid.utils.collection.IterableIterator
            public Iterator<DexInstruction> iterator(DexFile dexFile) {
                return dexFile.getDexInstructionsCloned();
            }
        };
    }

    @Override // java.lang.Iterable
    public Iterator<DexFile> iterator() {
        return this.dexSourceSet.getDexFiles();
    }

    public Iterator<DexFile> clonedIterator() {
        return this.dexSourceSet.getClonedDexFiles();
    }

    public void clearMethodPools() {
        for (DexFile dexFile : this) {
            dexFile.clearPoolMap(SectionType.PROTO_ID);
            dexFile.clearPoolMap(SectionType.METHOD_ID);
        }
    }

    public void clearPoolMap(SectionType<?> sectionType) {
        Iterator<DexFile> it = iterator();
        while (it.hasNext()) {
            it.next().clearPoolMap(sectionType);
        }
    }

    @Override // com.reandroid.dex.model.DexClassRepository
    public void clearPoolMap() {
        Iterator<DexFile> it = iterator();
        while (it.hasNext()) {
            it.next().clearPoolMap();
        }
    }

    public void sortStrings() {
        Iterator<DexFile> it = iterator();
        while (it.hasNext()) {
            it.next().sortStrings();
        }
    }

    @Override // com.reandroid.dex.common.FullRefresh
    public void refreshFull() {
        for (DexFile dexFile : this) {
            dexFile.setDexDirectory(this);
            dexFile.refreshFull();
        }
    }

    @Override // com.reandroid.arsc.base.BlockRefresh
    public void refresh() {
        for (DexFile dexFile : this) {
            dexFile.setDexDirectory(this);
            dexFile.refresh();
        }
    }

    public void updateDexFileList() {
        Iterator<DexFile> it = iterator();
        while (it.hasNext()) {
            it.next().setDexDirectory(this);
        }
    }

    public void addDirectory(File file) throws IOException {
        getDexSourceSet().addAll(file);
        Iterator<DexFile> it = iterator();
        while (it.hasNext()) {
            it.next().setDexDirectory(this);
        }
    }

    public void addApk(ZipEntryMap zipEntryMap) throws IOException {
        addZip(zipEntryMap, "");
    }

    public void addZip(ZipEntryMap zipEntryMap, String str) throws IOException {
        getDexSourceSet().addAll(zipEntryMap, str);
        Iterator<DexFile> it = iterator();
        while (it.hasNext()) {
            it.next().setDexDirectory(this);
        }
    }

    public void addFile(File file) throws IOException {
        DexSource<DexFile> dexSourceAdd = getDexSourceSet().add(file);
        if (file.isFile()) {
            dexSourceAdd.get().setDexDirectory(this);
        }
    }

    public ZipEntryMap getZipEntryMap() {
        return getDexSourceSet().getZipEntryMap();
    }

    public void setZipEntryMap(ZipEntryMap zipEntryMap) {
        getDexSourceSet().setZipEntryMap(zipEntryMap);
    }

    public DexFile createDefault() {
        DexFileSourceSet dexSourceSet = getDexSourceSet();
        if (size() == 0 && dexSourceSet.getZipEntryMap() == null) {
            dexSourceSet.setZipEntryMap(new ZipEntryMap());
        }
        DexSource<DexFile> dexSourceCreateNext = dexSourceSet.createNext();
        DexFile dexFileCreateDefault = DexFile.createDefault();
        dexSourceCreateNext.set(dexFileCreateDefault);
        dexFileCreateDefault.setDexDirectory(this);
        dexFileCreateDefault.setSimpleName(dexSourceCreateNext.toString());
        int version = getVersion();
        if (version != 0) {
            dexFileCreateDefault.setVersion(version);
        }
        return dexFileCreateDefault;
    }

    public DexFileSourceSet getDexSourceSet() {
        return this.dexSourceSet;
    }

    public int rename(TypeKey typeKey, TypeKey typeKey2) {
        if (containsClass(typeKey2)) {
            throw new RuntimeException("Duplicate: " + typeKey + " --> " + typeKey2);
        }
        Iterator<StringId> itRenameTypes = renameTypes(typeKey, typeKey2, true, true);
        int i = 0;
        while (itRenameTypes.hasNext()) {
            itRenameTypes.next();
            i++;
        }
        return i;
    }

    public Iterator<StringId> renameTypes(TypeKey typeKey, TypeKey typeKey2) {
        return renameTypes(typeKey, typeKey2, true, true);
    }

    public Iterator<StringId> renameTypes(TypeKey typeKey, TypeKey typeKey2, boolean z, boolean z2) {
        return renameTypes(new KeyPair<>(typeKey, typeKey2), z, z2);
    }

    public Iterator<StringId> renameTypes(final KeyPair<TypeKey, TypeKey> keyPair, final boolean z, final boolean z2) {
        return FilterIterator.of(getClonedItems(SectionType.STRING_ID), new Predicate() { // from class: com.reandroid.dex.model.DexDirectory$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.m2764lambda$renameTypes$1$comreandroiddexmodelDexDirectory(keyPair, z, z2, (StringId) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$renameTypes$1$com-reandroid-dex-model-DexDirectory, reason: not valid java name */
    /* synthetic */ boolean m2764lambda$renameTypes$1$comreandroiddexmodelDexDirectory(KeyPair keyPair, boolean z, boolean z2, StringId stringId) {
        return renameTypes(stringId, (KeyPair<TypeKey, TypeKey>) keyPair, z, z2);
    }

    public Iterator<StringId> renameTypes(final Iterable<KeyPair<TypeKey, TypeKey>> iterable, final boolean z, final boolean z2) {
        return FilterIterator.of(getClonedItems(SectionType.STRING_ID), new Predicate() { // from class: com.reandroid.dex.model.DexDirectory$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.m2765lambda$renameTypes$2$comreandroiddexmodelDexDirectory(iterable, z, z2, (StringId) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$renameTypes$2$com-reandroid-dex-model-DexDirectory, reason: not valid java name */
    /* synthetic */ boolean m2765lambda$renameTypes$2$comreandroiddexmodelDexDirectory(Iterable iterable, boolean z, boolean z2, StringId stringId) {
        return renameTypes(stringId, (Iterable<KeyPair<TypeKey, TypeKey>>) iterable, z, z2);
    }

    boolean renameTypes(StringId stringId, Iterable<KeyPair<TypeKey, TypeKey>> iterable, boolean z, boolean z2) {
        Iterator<KeyPair<TypeKey, TypeKey>> it = iterable.iterator();
        while (it.hasNext()) {
            if (renameTypes(stringId, it.next(), z, z2)) {
                return true;
            }
        }
        return false;
    }

    boolean renameTypes(StringId stringId, KeyPair<TypeKey, TypeKey> keyPair, boolean z, boolean z2) {
        DexClass dexClass;
        boolean zRenameTypeString = renameTypeString(stringId, keyPair, z, z2);
        if (zRenameTypeString && (dexClass = getDexClass(stringId.getString())) != null) {
            dexClass.fixDalvikInnerClassName();
        }
        return zRenameTypeString;
    }

    private boolean renameTypeString(StringId stringId, KeyPair<TypeKey, TypeKey> keyPair, boolean z, boolean z2) {
        String string = stringId.getString();
        TypeKey typeKey = (TypeKey) keyPair.getFirst();
        TypeKey typeKey2 = (TypeKey) keyPair.getSecond();
        String typeName = typeKey.getTypeName();
        String typeName2 = typeKey2.getTypeName();
        if (typeName.equals(string)) {
            stringId.setString(typeName2);
            return true;
        }
        if (z) {
            String strReplace = typeName.replace(';', '$');
            if (string.startsWith(strReplace)) {
                stringId.setString(typeKey2.getTypeName().replace(';', '$') + string.substring(strReplace.length()));
                return true;
            }
        }
        if (typeKey.getSignatureTypeName().equals(string)) {
            stringId.setString(typeKey2.getSignatureTypeName());
            return true;
        }
        String arrayType = typeKey.getArrayType(1);
        if (arrayType.equals(string)) {
            stringId.setString(typeKey2.getArrayType(1));
            return true;
        }
        if (z) {
            String strReplace2 = arrayType.replace(';', '$');
            if (string.startsWith(strReplace2)) {
                stringId.setString(typeKey2.getArrayType(1).replace(';', '$') + string.substring(strReplace2.length()));
                return true;
            }
        }
        String arrayType2 = typeKey.getArrayType(2);
        if (arrayType2.equals(string)) {
            stringId.setString(typeKey2.getArrayType(2));
            return true;
        }
        if (z) {
            String strReplace3 = arrayType2.replace(';', '$');
            if (string.startsWith(strReplace3)) {
                stringId.setString(typeKey2.getArrayType(2).replace(';', '$') + string.substring(strReplace3.length()));
                return true;
            }
        }
        String arrayType3 = typeKey.getArrayType(3);
        if (arrayType3.equals(string)) {
            stringId.setString(typeKey2.getArrayType(3));
            return true;
        }
        if (z) {
            String strReplace4 = arrayType3.replace(';', '$');
            if (string.startsWith(strReplace4)) {
                stringId.setString(typeKey2.getArrayType(3).replace(';', '$') + string.substring(strReplace4.length()));
                return true;
            }
        }
        if (!z2) {
            return false;
        }
        String sourceName = typeKey.getSourceName();
        if (sourceName.equals(string)) {
            stringId.setString(typeKey2.getSourceName());
            return true;
        }
        if (!z) {
            return false;
        }
        String str = sourceName + "$";
        if (string.startsWith(str)) {
            stringId.setString((typeKey2.getSourceName() + "$") + string.substring(str.length()));
            return true;
        }
        String str2 = str + ".";
        if (!string.startsWith(str2)) {
            return false;
        }
        stringId.setString((typeKey2.getSourceName() + ".") + string.substring(str2.length()));
        return true;
    }

    public List<MethodKey> replace(MethodKey methodKey, String str) {
        List<MethodKey> listRename = rename(methodKey, str);
        if (!listRename.isEmpty()) {
            return listRename;
        }
        List<MethodId> list = CollectionUtil.toList(getItems(SectionType.METHOD_ID, methodKey));
        int size = list.size();
        if (size == 0) {
            return EmptyList.of();
        }
        ArrayCollection arrayCollection = new ArrayCollection(size);
        for (MethodId methodId : list) {
            methodId.setName(str);
            arrayCollection.add(methodId.getKey());
        }
        return arrayCollection;
    }

    public List<MethodKey> rename(MethodKey methodKey, String str) {
        if (containsDeepSearch(methodKey.changeName(str))) {
            return EmptyList.of();
        }
        ArrayCollection<MethodId> arrayCollection = new ArrayCollection();
        arrayCollection.addAll(getMethodIds(methodKey));
        if (arrayCollection.size() == 0) {
            return EmptyList.of();
        }
        MethodKey methodKeyChangeName = methodKey.changeName(str);
        Iterator it = arrayCollection.iterator();
        while (it.hasNext()) {
            if (methodKeyChangeName.equals(((MethodId) it.next()).getKey())) {
                throw new IllegalArgumentException("Duplicate: " + methodKeyChangeName);
            }
        }
        ArrayCollection arrayCollection2 = new ArrayCollection(arrayCollection.size());
        for (MethodId methodId : arrayCollection) {
            methodId.setName(str);
            arrayCollection2.add(methodId.getKey());
        }
        return arrayCollection2;
    }

    public List<FieldKey> replace(FieldKey fieldKey, String str) {
        List<FieldKey> listRename = rename(fieldKey, str);
        if (!listRename.isEmpty()) {
            return listRename;
        }
        List<FieldId> list = CollectionUtil.toList(getItems(SectionType.FIELD_ID, fieldKey));
        int size = list.size();
        if (size == 0) {
            return EmptyList.of();
        }
        ArrayCollection arrayCollection = new ArrayCollection(size);
        for (FieldId fieldId : list) {
            fieldId.setName(str);
            arrayCollection.add(fieldId.getKey());
        }
        return arrayCollection;
    }

    public List<FieldKey> rename(FieldKey fieldKey, String str) {
        ArrayCollection arrayCollectionOf = ArrayCollection.of((Iterator) findEquivalentFields(fieldKey.changeName(str)));
        ArrayCollection<FieldId> arrayCollectionOf2 = ArrayCollection.of(getItems(SectionType.FIELD_ID, fieldKey));
        if (arrayCollectionOf2.isEmpty()) {
            return EmptyList.of();
        }
        if (!arrayCollectionOf.isEmpty()) {
            throw new IllegalArgumentException("Conflicting fields: " + arrayCollectionOf.getFirst());
        }
        FieldKey fieldKeyChangeName = fieldKey.changeName(str);
        Iterator it = arrayCollectionOf2.iterator();
        while (it.hasNext()) {
            if (fieldKeyChangeName.equals(((FieldId) it.next()).getKey())) {
                throw new IllegalArgumentException("Duplicate: " + fieldKeyChangeName);
            }
        }
        ArrayCollection arrayCollection = new ArrayCollection(arrayCollectionOf2.size());
        for (FieldId fieldId : arrayCollectionOf2) {
            fieldId.setName(str);
            arrayCollection.add(fieldId.getKey());
        }
        return arrayCollection;
    }

    public boolean containsDeepSearch(MethodKey methodKey) {
        DexClass dexClass = getDexClass(methodKey.getDeclaring());
        if (dexClass == null) {
            return false;
        }
        if (dexClass.containsDeclaredMethod(methodKey)) {
            return true;
        }
        Iterator<DexClass> overridingAndSuperTypes = dexClass.getOverridingAndSuperTypes();
        while (overridingAndSuperTypes.hasNext()) {
            if (overridingAndSuperTypes.next().containsDeclaredMethod(methodKey)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsDeepSearch(FieldKey fieldKey) {
        DexClass dexClass = getDexClass(fieldKey.getDeclaring());
        if (dexClass == null) {
            return false;
        }
        Iterator<DexClass> overridingAndSuperTypes = dexClass.getOverridingAndSuperTypes();
        while (overridingAndSuperTypes.hasNext()) {
            if (fieldKey.equals(fieldKey.changeDeclaring(overridingAndSuperTypes.next().getKey()))) {
                return true;
            }
        }
        return false;
    }

    @Override // com.reandroid.dex.model.DexClassRepository
    public Iterator<DexClass> searchExtending(final TypeKey typeKey) {
        UniqueIterator uniqueIterator = new UniqueIterator(new IterableIterator<DexFile, DexClass>(iterator()) { // from class: com.reandroid.dex.model.DexDirectory.10
            @Override // com.reandroid.utils.collection.IterableIterator
            public Iterator<DexClass> iterator(DexFile dexFile) {
                return dexFile.getExtendingClasses(typeKey);
            }
        });
        uniqueIterator.exclude(getDexClass(typeKey));
        return uniqueIterator;
    }

    @Override // com.reandroid.dex.model.DexClassRepository
    public Iterator<DexClass> searchImplementations(final TypeKey typeKey) {
        UniqueIterator uniqueIterator = new UniqueIterator(new IterableIterator<DexFile, DexClass>(iterator()) { // from class: com.reandroid.dex.model.DexDirectory.11
            @Override // com.reandroid.utils.collection.IterableIterator
            public Iterator<DexClass> iterator(DexFile dexFile) {
                return dexFile.getImplementClasses(typeKey);
            }
        });
        uniqueIterator.exclude(getDexClass(typeKey));
        return uniqueIterator;
    }

    public int distributeClasses(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("Classes per dex must be greater than zero: " + i);
        }
        int size = size();
        if (size == 0) {
            return 0;
        }
        int dexClassesCount = getDexClassesCount();
        int i2 = dexClassesCount / size;
        while (i2 > i) {
            createDefault();
            int size2 = size();
            if (size2 <= size) {
                throw new IllegalArgumentException("Failed to create next dex");
            }
            i2 = dexClassesCount / size2;
            size = size2;
        }
        int iDistributeClasses = 0;
        for (int i3 = 0; i3 < size; i3++) {
            iDistributeClasses += distributeClasses(get(i3), i2);
        }
        return iDistributeClasses;
    }

    private int distributeClasses(DexFile dexFile, int i) {
        DexDirectory dexDirectory = dexFile.getDexDirectory();
        int iDistributeClasses = 0;
        for (int i2 = 0; i2 < dexDirectory.size(); i2++) {
            iDistributeClasses += distributeClasses(dexFile, dexDirectory.get(i2), i);
        }
        return iDistributeClasses;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private int distributeClasses(DexFile dexFile, DexFile dexFile2, int i) {
        ClassId classId;
        int i2 = 0;
        if (dexFile.getDexLayout() == dexFile2.getDexLayout()) {
            return 0;
        }
        SectionArray itemArray = dexFile.getSection(SectionType.CLASS_ID).getItemArray();
        ClassId classId2 = null;
        while (dexFile.getDexClassesCount() > i && dexFile2.getDexClassesCount() < i && (classId = (ClassId) itemArray.getLast()) != classId2) {
            dexFile2.merge(classId);
            i2++;
            classId2 = classId;
        }
        return i2;
    }

    public DexFile get(int i) {
        return this.dexSourceSet.getDexFile(i);
    }

    public int indexOf(DexFile dexFile) {
        int size = size();
        for (int i = 0; i < size; i++) {
            if (dexFile == get(i)) {
                return i;
            }
        }
        return -1;
    }

    public int size() {
        return this.dexSourceSet.size();
    }

    public DexFile getFirst() {
        DexSource<DexFile> first = this.dexSourceSet.getFirst();
        if (first != null) {
            return first.get();
        }
        return null;
    }

    public DexFile getLast() {
        DexSource<DexFile> last = this.dexSourceSet.getLast();
        if (last != null) {
            return last.get();
        }
        return null;
    }

    @Override // com.reandroid.dex.model.DexClassRepository
    public List<TypeKeyReference> getExternalTypeKeyReferenceList() {
        return this.externalTypeKeyReferenceList;
    }

    public void addExternalTypeKeyReference(TypeKeyReference typeKeyReference) {
        if (typeKeyReference == null || this.externalTypeKeyReferenceList.contains(typeKeyReference)) {
            return;
        }
        this.externalTypeKeyReferenceList.add(typeKeyReference);
    }

    public void clearExternalTypeKeyReferences() {
        this.externalTypeKeyReferenceList.clear();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.dexSourceSet.close();
        clearExternalTypeKeyReferences();
    }

    public void writeSmali(SmaliWriter smaliWriter, File file) throws IOException {
        Iterator<DexFile> it = iterator();
        while (it.hasNext()) {
            it.next().writeSmali(smaliWriter, file);
        }
    }

    public String toString() {
        return "DexFiles = " + size();
    }

    public static DexDirectory fromZip(ZipEntryMap zipEntryMap) throws IOException {
        DexDirectory dexDirectory = new DexDirectory();
        dexDirectory.getDexSourceSet().addAll(zipEntryMap);
        dexDirectory.updateDexFileList();
        return dexDirectory;
    }

    public static DexDirectory fromZip(ZipEntryMap zipEntryMap, String str) throws IOException {
        DexDirectory dexDirectory = new DexDirectory();
        dexDirectory.getDexSourceSet().addAll(zipEntryMap, str);
        dexDirectory.updateDexFileList();
        return dexDirectory;
    }

    public static DexDirectory fromDexFilesDirectory(File file) throws IOException {
        DexDirectory dexDirectory = new DexDirectory();
        dexDirectory.getDexSourceSet().addAll(file);
        dexDirectory.updateDexFileList();
        return dexDirectory;
    }

    public static DexDirectory readStrings(ZipEntryMap zipEntryMap) throws IOException {
        return readStrings(zipEntryMap, null);
    }

    public static DexDirectory readStrings(ZipEntryMap zipEntryMap, String str) throws IOException {
        DexDirectory dexDirectory = new DexDirectory();
        DexFileSourceSet dexSourceSet = dexDirectory.getDexSourceSet();
        dexSourceSet.setReadStringsMode(true);
        dexSourceSet.addAll(zipEntryMap, str);
        dexDirectory.updateDexFileList();
        return dexDirectory;
    }
}
