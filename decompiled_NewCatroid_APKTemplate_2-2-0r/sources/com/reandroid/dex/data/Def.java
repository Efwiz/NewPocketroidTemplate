package com.reandroid.dex.data;

import com.reandroid.arsc.base.Block;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.dex.base.Ule128Item;
import com.reandroid.dex.common.AccessFlag;
import com.reandroid.dex.common.EditableItem;
import com.reandroid.dex.common.HiddenApiFlag;
import com.reandroid.dex.common.IdDefinition;
import com.reandroid.dex.common.IdUsageIterator;
import com.reandroid.dex.common.Modifier;
import com.reandroid.dex.common.SectionTool;
import com.reandroid.dex.id.ClassId;
import com.reandroid.dex.id.IdItem;
import com.reandroid.dex.key.Key;
import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.sections.SectionList;
import com.reandroid.dex.sections.SectionType;
import com.reandroid.dex.smali.SmaliRegion;
import com.reandroid.utils.collection.CollectionUtil;
import com.reandroid.utils.collection.CombiningIterator;
import com.reandroid.utils.collection.EmptyIterator;
import com.reandroid.utils.collection.FilterIterator;
import com.reandroid.utils.collection.MultiMap;
import com.reandroid.utils.collection.SingleIterator;
import java.io.IOException;
import java.util.Iterator;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes11.dex */
public abstract class Def<T extends IdItem> extends FixedDexContainerWithTool implements IdDefinition<T>, EditableItem, Comparable<Def<T>>, SmaliRegion, DefIndex, IdUsageIterator {
    private final Ule128Item accessFlags;
    private HiddenApiFlagValue hiddenApiFlagValue;
    private int mCachedIndex;
    private boolean mCachedIndexUpdated;
    private T mDefId;
    private final Ule128Item relativeId;
    private final SectionType<T> sectionType;

    @Override // com.reandroid.dex.common.EditableItem
    public void editInternal(Block block) {
    }

    public boolean isDirect() {
        return false;
    }

    public Def(int i, SectionType<T> sectionType) {
        super(i + 2);
        this.sectionType = sectionType;
        Ule128Item ule128Item = new Ule128Item(true);
        this.relativeId = ule128Item;
        Ule128Item ule128Item2 = new Ule128Item();
        this.accessFlags = ule128Item2;
        addChild(0, ule128Item);
        addChild(1, ule128Item2);
    }

    @Override // com.reandroid.dex.common.IdDefinition
    public Iterator<? extends Modifier> getModifiers() {
        return CombiningIterator.two(getAccessFlags(), getHiddenApiFlags());
    }

    public Iterator<HiddenApiFlag> getHiddenApiFlags() {
        HiddenApiFlagValue hiddenApiFlagValue = getHiddenApiFlagValue();
        if (hiddenApiFlagValue != null) {
            return hiddenApiFlagValue.getFlags();
        }
        return EmptyIterator.of();
    }

    public HiddenApiFlagValue getHiddenApiFlagValue() {
        return this.hiddenApiFlagValue;
    }

    public void setHiddenApiFlagValue(HiddenApiFlagValue hiddenApiFlagValue) {
        this.hiddenApiFlagValue = hiddenApiFlagValue;
    }

    public void removeSelf() {
        DefArray<Def<T>> parentArray = getParentArray();
        if (parentArray != null) {
            parentArray.remove(this);
        }
    }

    void onRemove() {
        this.mCachedIndexUpdated = true;
        this.mDefId = null;
        this.relativeId.set(0);
    }

    @Override // com.reandroid.dex.data.DefIndex
    public Key getKey() {
        IdItem id2 = getId();
        if (id2 != null) {
            return id2.getKey();
        }
        return null;
    }

    public void setKey(Key key) {
        setItem(key);
    }

    public AnnotationSet getOrCreateAnnotationSet() {
        AnnotationSet annotationSet = (AnnotationSet) CollectionUtil.getFirst(getAnnotations());
        if (annotationSet != null) {
            return annotationSet;
        }
        AnnotationsDirectory orCreateUniqueAnnotationsDirectory = getOrCreateUniqueAnnotationsDirectory();
        AnnotationSet annotationSet2 = (AnnotationSet) orCreateUniqueAnnotationsDirectory.createSectionItem(SectionType.ANNOTATION_SET);
        orCreateUniqueAnnotationsDirectory.addAnnotation(this, annotationSet2);
        return annotationSet2;
    }

    public void addAnnotationSet(AnnotationSet annotationSet) {
        addAnnotationSet(getOrCreateUniqueAnnotationsDirectory(), annotationSet);
    }

    void addAnnotationSet(AnnotationsDirectory annotationsDirectory, AnnotationSet annotationSet) {
        annotationsDirectory.addAnnotation(this, annotationSet);
    }

    public Iterator<AnnotationSet> getAnnotations() {
        return getAnnotations(false);
    }

    public Iterator<AnnotationSet> getAnnotations(boolean z) {
        AnnotationsDirectory annotationsDirectory = getAnnotationsDirectory();
        if (annotationsDirectory == null) {
            return EmptyIterator.of();
        }
        Iterator<AnnotationSet> annotations = annotationsDirectory.getAnnotations(this);
        return (z && annotations.hasNext()) ? FilterIterator.of(annotations, new Predicate() { // from class: com.reandroid.dex.data.Def$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Def.lambda$getAnnotations$0((AnnotationSet) obj);
            }
        }) : annotations;
    }

    static /* synthetic */ boolean lambda$getAnnotations$0(AnnotationSet annotationSet) {
        return !annotationSet.isEmpty();
    }

    public AnnotationsDirectory getAnnotationsDirectory() {
        ClassId classId = getClassId();
        if (classId != null) {
            return classId.getAnnotationsDirectory();
        }
        return null;
    }

    public AnnotationsDirectory getOrCreateUniqueAnnotationsDirectory() {
        ClassId classId = getClassId();
        if (classId != null) {
            return classId.getOrCreateUniqueAnnotationsDirectory();
        }
        return null;
    }

    public AnnotationsDirectory getUniqueAnnotationsDirectory() {
        ClassId classId = getClassId();
        if (classId != null) {
            return classId.getUniqueAnnotationsDirectory();
        }
        return null;
    }

    public ClassId getClassId() {
        TypeKey defining;
        MultiMap pool;
        ClassId classId;
        ClassData classData;
        ClassId classId2;
        DefArray<Def<T>> parentArray = getParentArray();
        if (parentArray != null && (classId2 = parentArray.getClassId()) != null) {
            return classId2;
        }
        SectionList sectionList = getSectionList();
        if (sectionList == null || sectionList.isReading() || (defining = getDefining()) == null || (pool = getPool(SectionType.CLASS_ID)) == null || (classId = (ClassId) pool.get(defining)) == null || (classData = getClassData()) == null) {
            return null;
        }
        classData.setClassId(classId);
        return classId;
    }

    public TypeKey getDefining() {
        Key key = getKey();
        if (key != null) {
            return key.getDeclaring();
        }
        return null;
    }

    public int getRelativeIdValue() {
        return this.relativeId.get();
    }

    @Override // com.reandroid.dex.common.IdDefinition
    public int getAccessFlagsValue() {
        return this.accessFlags.get();
    }

    @Override // com.reandroid.dex.common.IdDefinition
    public void setAccessFlagsValue(int i) {
        this.accessFlags.set(i);
    }

    public boolean isPrivate() {
        return AccessFlag.PRIVATE.isSet(getAccessFlagsValue());
    }

    public boolean isNative() {
        return AccessFlag.NATIVE.isSet(getAccessFlagsValue());
    }

    public boolean isStatic() {
        return AccessFlag.STATIC.isSet(getAccessFlagsValue());
    }

    public boolean isFinal() {
        return AccessFlag.FINAL.isSet(getAccessFlagsValue());
    }

    public boolean isConstructor() {
        return AccessFlag.CONSTRUCTOR.isSet(getAccessFlagsValue());
    }

    @Override // com.reandroid.dex.common.IdDefinition
    public T getId() {
        return this.mDefId;
    }

    void setItem(Key key) {
        IdItem id2 = getId();
        if (id2 == null || !key.equals(id2.getKey())) {
            setItem((IdItem) getOrCreateSection(this.sectionType).getOrCreate(key));
        }
    }

    void setItem(T t) {
        this.mDefId = t;
        updateIndex();
    }

    @Override // com.reandroid.dex.data.DefIndex
    public int getDefinitionIndex() {
        if (!this.mCachedIndexUpdated) {
            this.mCachedIndexUpdated = true;
            this.mCachedIndex = calculateDefinitionIndex();
        }
        return this.mCachedIndex;
    }

    private int calculateDefinitionIndex() {
        Def<T> def;
        DefArray<Def<T>> parentArray = getParentArray();
        if (parentArray != null && (def = parentArray.get(getIndex() - 1)) != null) {
            return getRelativeIdValue() + def.getDefinitionIndex();
        }
        return this.relativeId.get();
    }

    private int getPreviousIdIndex() {
        Def<T> def;
        DefArray<Def<T>> parentArray = getParentArray();
        if (parentArray == null || (def = parentArray.get(getIndex() - 1)) == null) {
            return 0;
        }
        return def.getDefinitionIndex();
    }

    private ClassData getClassData() {
        DefArray<Def<T>> parentArray = getParentArray();
        if (parentArray != null) {
            return parentArray.getClassData();
        }
        return null;
    }

    private DefArray<Def<T>> getParentArray() {
        return (DefArray) getParent();
    }

    @Override // com.reandroid.dex.base.FixedDexContainer, com.reandroid.arsc.base.BlockContainer, com.reandroid.arsc.base.Block
    public void onReadBytes(BlockReader blockReader) throws IOException {
        super.onReadBytes(blockReader);
        cacheItem();
    }

    private void cacheItem() {
        T t = (T) getSectionItem(this.sectionType, getDefinitionIndex());
        this.mDefId = t;
        if (t != null) {
            t.addUsageType(IdItem.USAGE_DEFINITION);
        }
    }

    @Override // com.reandroid.arsc.container.FixedBlockContainer, com.reandroid.arsc.base.BlockContainer
    protected void onRefreshed() {
        super.onRefreshed();
        updateIndex();
    }

    private void updateIndex() {
        resetIndex();
        T t = (T) this.mDefId.getReplace();
        this.mDefId = t;
        this.relativeId.set(t.getIndex() - getPreviousIdIndex());
        t.addUsageType(IdItem.USAGE_DEFINITION);
    }

    void resetIndex() {
        this.mCachedIndexUpdated = false;
    }

    public void replaceKeys(Key key, Key key2) {
        Key key3 = getKey();
        Key keyReplaceKey = key3.replaceKey(key, key2);
        if (key3 != keyReplaceKey) {
            setItem(keyReplaceKey);
        }
    }

    @Override // com.reandroid.dex.common.IdUsageIterator
    public Iterator<IdItem> usedIds() {
        return SingleIterator.of(getId());
    }

    public void merge(Def<?> def) {
        setItem(def.getKey());
        setAccessFlagsValue(def.getAccessFlagsValue());
    }

    @Override // java.lang.Comparable
    public int compareTo(Def<T> def) {
        if (def == null) {
            return -1;
        }
        return SectionTool.compareIdx(getId(), def.getId());
    }
}
