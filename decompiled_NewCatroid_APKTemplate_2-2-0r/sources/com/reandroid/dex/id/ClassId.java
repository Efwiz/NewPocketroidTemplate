package com.reandroid.dex.id;

import com.reandroid.arsc.base.Block;
import com.reandroid.arsc.item.IndirectInteger;
import com.reandroid.dex.base.UsageMarker;
import com.reandroid.dex.common.AccessFlag;
import com.reandroid.dex.common.AnnotationVisibility;
import com.reandroid.dex.common.IdDefinition;
import com.reandroid.dex.common.Modifier;
import com.reandroid.dex.common.SectionTool;
import com.reandroid.dex.data.AnnotationElement;
import com.reandroid.dex.data.AnnotationItem;
import com.reandroid.dex.data.AnnotationSet;
import com.reandroid.dex.data.AnnotationsDirectory;
import com.reandroid.dex.data.ClassData;
import com.reandroid.dex.data.EncodedArray;
import com.reandroid.dex.data.TypeList;
import com.reandroid.dex.key.Key;
import com.reandroid.dex.key.StringKey;
import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.key.TypeListKey;
import com.reandroid.dex.reference.DataItemIndirectReference;
import com.reandroid.dex.reference.TypeListReference;
import com.reandroid.dex.sections.SectionType;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.dex.smali.model.SmaliClass;
import com.reandroid.dex.value.ArrayValue;
import com.reandroid.dex.value.DexValueBlock;
import com.reandroid.dex.value.DexValueType;
import com.reandroid.dex.value.IntValue;
import com.reandroid.dex.value.MethodIdValue;
import com.reandroid.dex.value.StringValue;
import com.reandroid.dex.value.TypeValue;
import com.reandroid.utils.StringsUtil;
import com.reandroid.utils.collection.ArrayCollection;
import com.reandroid.utils.collection.CombiningIterator;
import com.reandroid.utils.collection.ComputeIterator;
import com.reandroid.utils.collection.EmptyIterator;
import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Function;

/* JADX INFO: loaded from: classes11.dex */
public class ClassId extends IdItem implements IdDefinition<TypeId>, Comparable<ClassId> {
    private static final int SIZE = 32;
    private final IndirectInteger accessFlagValue;
    private final DataItemIndirectReference<AnnotationsDirectory> annotationsDirectory;
    private final DataItemIndirectReference<ClassData> classData;
    private final ClassTypeId classTypeId;
    private final TypeListReference interfaces;
    private final SourceFile sourceFile;
    private final DataItemIndirectReference<EncodedArray> staticValues;
    private final SuperClassId superClassId;

    @Override // com.reandroid.dex.common.SectionItem, com.reandroid.dex.base.UsageMarker
    public void clearUsageType() {
    }

    public ClassId() {
        super(32);
        this.classTypeId = new ClassTypeId(this, 0);
        this.accessFlagValue = new IndirectInteger(this, 4);
        this.superClassId = new SuperClassId(this, 8);
        this.interfaces = new TypeListReference(this, 12, USAGE_INTERFACE);
        this.sourceFile = new SourceFile(this, 16);
        this.annotationsDirectory = new DataItemIndirectReference<>(SectionType.ANNOTATION_DIRECTORY, this, 20, UsageMarker.USAGE_DEFINITION);
        this.classData = new DataItemIndirectReference<>(SectionType.CLASS_DATA, this, 24, UsageMarker.USAGE_DEFINITION);
        this.staticValues = new DataItemIndirectReference<>(SectionType.ENCODED_ARRAY, this, 28, UsageMarker.USAGE_STATIC_VALUES);
        addUsageType(UsageMarker.USAGE_DEFINITION);
    }

    @Override // com.reandroid.dex.common.EditableItem
    public void edit() {
        editInternal(this);
    }

    @Override // com.reandroid.dex.common.SectionItem, com.reandroid.dex.common.EditableItem
    public void editInternal(Block block) {
        this.annotationsDirectory.editInternal(this);
        this.classData.editInternal(this);
        this.staticValues.editInternal(this);
    }

    @Override // com.reandroid.dex.id.IdItem, com.reandroid.dex.common.SectionItem
    public SectionType<ClassId> getSectionType() {
        return SectionType.CLASS_ID;
    }

    @Override // com.reandroid.dex.id.IdItem, com.reandroid.dex.common.SectionItem, com.reandroid.dex.key.KeyItem
    public TypeKey getKey() {
        return (TypeKey) checkKey(TypeKey.create(getName()));
    }

    @Override // com.reandroid.dex.id.IdItem, com.reandroid.dex.key.ModifiableKeyItem
    public void setKey(Key key) {
        TypeKey key2 = getKey();
        if (Objects.equals(key2, key)) {
            return;
        }
        this.classTypeId.setItem(key);
        keyChanged(key2);
    }

    public String getName() {
        TypeId id2 = getId();
        if (id2 != null) {
            return id2.getName();
        }
        return null;
    }

    public void setName(String str) {
        setKey(new TypeKey(str));
    }

    public ClassTypeId getClassTypeId() {
        return this.classTypeId;
    }

    @Override // com.reandroid.dex.common.IdDefinition
    public TypeId getId() {
        return (TypeId) getClassTypeId().getItem();
    }

    @Override // com.reandroid.dex.common.IdDefinition
    public int getAccessFlagsValue() {
        return this.accessFlagValue.get();
    }

    @Override // com.reandroid.dex.common.IdDefinition
    public Iterator<? extends Modifier> getAccessFlags() {
        return AccessFlag.valuesOfClass(getAccessFlagsValue());
    }

    @Override // com.reandroid.dex.common.IdDefinition
    public void setAccessFlagsValue(int i) {
        this.accessFlagValue.set(i);
    }

    public void setId(TypeId typeId) {
        this.classTypeId.setItem(typeId);
    }

    public SuperClassId getSuperClassId() {
        return this.superClassId;
    }

    public TypeId getSuperClassType() {
        return (TypeId) getSuperClassId().getItem();
    }

    public TypeKey getSuperClassKey() {
        return getSuperClassId().getKey();
    }

    public void setSuperClass(TypeKey typeKey) {
        this.superClassId.setItem(typeKey);
    }

    public void setSuperClass(String str) {
        this.superClassId.setItem(new TypeKey(str));
    }

    public SourceFile getSourceFile() {
        return this.sourceFile;
    }

    public String getSourceFileName() {
        return getSourceFile().getString();
    }

    public void setSourceFile(String str) {
        getSourceFile().setString(str);
    }

    public Iterator<TypeKey> getInstanceKeys() {
        return CombiningIterator.singleOne(getSuperClassKey(), getInterfaceKeys());
    }

    public Iterator<TypeKey> getInterfaceKeys() {
        return getInterfacesReference().getTypeKeys();
    }

    public TypeList getInterfaceTypeList() {
        return (TypeList) this.interfaces.getItem();
    }

    public TypeListReference getInterfacesReference() {
        return this.interfaces;
    }

    public void setInterfaces(TypeListKey typeListKey) {
        this.interfaces.setItem(typeListKey);
    }

    public Key getDalvikEnclosing() {
        TypeValue dalvikEnclosingClass = getDalvikEnclosingClass();
        if (dalvikEnclosingClass != null) {
            return dalvikEnclosingClass.getKey();
        }
        MethodIdValue dalvikEnclosingMethodId = getDalvikEnclosingMethodId();
        if (dalvikEnclosingMethodId != null) {
            return dalvikEnclosingMethodId.getKey();
        }
        return null;
    }

    public MethodIdValue getDalvikEnclosingMethodId() {
        AnnotationItem dalvikEnclosingMethod = getDalvikEnclosingMethod();
        if (dalvikEnclosingMethod == null) {
            return null;
        }
        DexValueBlock<?> elementValue = dalvikEnclosingMethod.getElementValue(Key.DALVIK_value);
        if (elementValue instanceof MethodIdValue) {
            return (MethodIdValue) elementValue;
        }
        return null;
    }

    public Iterator<TypeKey> getMemberClassKeys() {
        return ComputeIterator.of(getDalvikMemberClasses(), new Function() { // from class: com.reandroid.dex.id.ClassId$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((TypeValue) obj).getKey();
            }
        });
    }

    public Iterator<TypeValue> getDalvikMemberClasses() {
        AnnotationItem dalvikMemberClass = getDalvikMemberClass();
        if (dalvikMemberClass != null) {
            DexValueBlock<?> elementValue = dalvikMemberClass.getElementValue(Key.DALVIK_value);
            if (elementValue instanceof ArrayValue) {
                return ((ArrayValue) elementValue).iterator(TypeValue.class);
            }
        }
        return EmptyIterator.of();
    }

    public AnnotationItem getDalvikEnclosingMethod() {
        return getClassAnnotation(TypeKey.DALVIK_EnclosingMethod);
    }

    public AnnotationItem getDalvikMemberClass() {
        return getClassAnnotation(TypeKey.DALVIK_MemberClass);
    }

    public AnnotationItem getDalvikInnerClass() {
        AnnotationSet classAnnotations = getClassAnnotations();
        if (classAnnotations == null) {
            return null;
        }
        return classAnnotations.get(TypeKey.DALVIK_InnerClass);
    }

    public AnnotationItem getOrCreateDalvikInnerClass() {
        TypeKey key = getKey();
        String str = null;
        if (key == null) {
            return null;
        }
        String simpleInnerName = key.getSimpleInnerName();
        if (!AccessFlag.SYNTHETIC.isSet(getAccessFlagsValue()) && !simpleInnerName.equals(key.getSimpleName()) && !StringsUtil.isDigits(simpleInnerName)) {
            str = simpleInnerName;
        }
        return getOrCreateDalvikInnerClass(getAccessFlagsValue(), str);
    }

    public AnnotationItem getOrCreateDalvikInnerClass(int i, String str) {
        AnnotationItem orCreate = getOrCreateClassAnnotations().getOrCreate(TypeKey.DALVIK_InnerClass);
        orCreate.setVisibility(AnnotationVisibility.SYSTEM);
        ((IntValue) orCreate.getOrCreateElement(Key.DALVIK_accessFlags).getOrCreateValue(DexValueType.INT)).set(i);
        AnnotationElement orCreateElement = orCreate.getOrCreateElement(Key.DALVIK_name);
        if (str != null) {
            ((StringValue) orCreateElement.getOrCreateValue(DexValueType.STRING)).setItem(new StringKey(str));
        } else {
            orCreateElement.getOrCreateValue(DexValueType.NULL);
        }
        return orCreate;
    }

    public TypeValue getOrCreateDalvikEnclosingClass() {
        TypeKey key = getKey();
        if (key == null) {
            return null;
        }
        TypeKey enclosingClass = key.getEnclosingClass();
        if (key.equals(enclosingClass)) {
            return null;
        }
        return getOrCreateDalvikEnclosingClass(enclosingClass);
    }

    public TypeValue getOrCreateDalvikEnclosingClass(TypeKey typeKey) {
        if (typeKey == null) {
            return null;
        }
        AnnotationItem orCreate = getOrCreateClassAnnotations().getOrCreate(TypeKey.DALVIK_EnclosingClass);
        orCreate.setVisibility(AnnotationVisibility.SYSTEM);
        TypeValue typeValue = (TypeValue) orCreate.getOrCreateElement(Key.DALVIK_value).getOrCreateValue(DexValueType.TYPE);
        typeValue.setKey(typeKey);
        return typeValue;
    }

    public TypeValue getDalvikEnclosingClass() {
        AnnotationItem dalvikEnclosingClassAnnotation = getDalvikEnclosingClassAnnotation();
        if (dalvikEnclosingClassAnnotation == null) {
            return null;
        }
        DexValueBlock<?> value = dalvikEnclosingClassAnnotation.getElement(Key.DALVIK_value).getValue();
        if (value instanceof TypeValue) {
            return (TypeValue) value;
        }
        return null;
    }

    public AnnotationItem getDalvikEnclosingClassAnnotation() {
        AnnotationSet classAnnotations = getClassAnnotations();
        if (classAnnotations != null) {
            return classAnnotations.get(TypeKey.DALVIK_EnclosingClass);
        }
        return null;
    }

    public AnnotationSet getOrCreateClassAnnotations() {
        return getOrCreateAnnotationsDirectory().getOrCreateClassAnnotations();
    }

    public AnnotationItem getClassAnnotation(TypeKey typeKey) {
        AnnotationSet classAnnotations = getClassAnnotations();
        if (classAnnotations != null) {
            return classAnnotations.get(typeKey);
        }
        return null;
    }

    public Iterator<AnnotationItem> getClassAnnotations(TypeKey typeKey) {
        AnnotationSet classAnnotations = getClassAnnotations();
        if (classAnnotations != null) {
            return classAnnotations.getAll(typeKey);
        }
        return EmptyIterator.of();
    }

    public AnnotationSet getClassAnnotations() {
        AnnotationsDirectory annotationsDirectory = getAnnotationsDirectory();
        if (annotationsDirectory != null) {
            return annotationsDirectory.getClassAnnotations();
        }
        return null;
    }

    public void setClassAnnotations(AnnotationSet annotationSet) {
        AnnotationsDirectory annotationsDirectory = getAnnotationsDirectory();
        if (annotationsDirectory != null) {
            annotationsDirectory.setClassAnnotations(annotationSet);
        }
    }

    public AnnotationsDirectory getOrCreateAnnotationsDirectory() {
        AnnotationsDirectory annotationsDirectory = (AnnotationsDirectory) this.annotationsDirectory.getOrCreate();
        annotationsDirectory.addUniqueUser(this);
        return annotationsDirectory;
    }

    public AnnotationsDirectory getUniqueAnnotationsDirectory() {
        return (AnnotationsDirectory) this.annotationsDirectory.getUniqueItem(this);
    }

    public AnnotationsDirectory getOrCreateUniqueAnnotationsDirectory() {
        return (AnnotationsDirectory) this.annotationsDirectory.getOrCreateUniqueItem(this);
    }

    public AnnotationsDirectory getAnnotationsDirectory() {
        return (AnnotationsDirectory) this.annotationsDirectory.getItem();
    }

    public void setAnnotationsDirectory(AnnotationsDirectory annotationsDirectory) {
        this.annotationsDirectory.setItem(annotationsDirectory);
    }

    public ClassData getOrCreateClassData() {
        ClassData classData = getClassData();
        if (classData != null) {
            return classData;
        }
        ClassData classData2 = (ClassData) getSection(SectionType.CLASS_DATA).createItem();
        setClassData(classData2);
        return classData2;
    }

    public ClassData getClassData() {
        ClassData classData = (ClassData) this.classData.getItem();
        linkClassData(classData);
        return classData;
    }

    public void setClassData(ClassData classData) {
        this.classData.setItem(classData);
        linkClassData(classData);
    }

    public EncodedArray getStaticValues() {
        return (EncodedArray) this.staticValues.getItem();
    }

    public EncodedArray getOrCreateUniqueStaticValues() {
        return (EncodedArray) this.staticValues.getOrCreateUniqueItem(this);
    }

    public EncodedArray getUniqueStaticValues() {
        return (EncodedArray) this.staticValues.getUniqueItem(this);
    }

    public DexValueBlock<?> getStaticValue(int i) {
        EncodedArray staticValues = getStaticValues();
        if (staticValues != null) {
            return staticValues.get(i);
        }
        return null;
    }

    public <T1 extends DexValueBlock<?>> T1 getOrCreateStaticValue(DexValueType<T1> dexValueType, int i) {
        return (T1) getOrCreateUniqueStaticValues().getOrCreate(dexValueType, i);
    }

    public void setStaticValues(EncodedArray encodedArray) {
        this.staticValues.setItem(encodedArray);
    }

    @Override // com.reandroid.arsc.base.BlockRefresh
    public void refresh() {
        this.annotationsDirectory.addUniqueUser(this);
        this.classData.addUniqueUser(this);
        this.staticValues.addUniqueUser(this);
        this.classTypeId.refresh();
        this.superClassId.refresh();
        this.interfaces.refresh();
        this.sourceFile.refresh();
        this.annotationsDirectory.refresh();
        this.classData.refresh();
        this.staticValues.refresh();
    }

    @Override // com.reandroid.dex.id.IdItem
    void cacheItems() {
        this.classTypeId.pullItem();
        this.superClassId.pullItem();
        this.interfaces.pullItem();
        this.sourceFile.pullItem();
        this.annotationsDirectory.pullItem();
        this.classData.pullItem();
        this.staticValues.pullItem();
        this.annotationsDirectory.addUniqueUser(this);
        this.classData.addUniqueUser(this);
        this.staticValues.addUniqueUser(this);
        linkClassData((ClassData) this.classData.getItem());
    }

    private void linkClassData(ClassData classData) {
        if (classData != null) {
            classData.setClassId(this);
        }
    }

    @Override // com.reandroid.dex.id.IdItem, com.reandroid.dex.common.SectionItem
    public void removeSelf() {
        super.removeSelf();
        this.classTypeId.unlink();
        this.superClassId.unlink();
        this.sourceFile.unlink();
        this.classData.unlink();
        this.annotationsDirectory.unlink();
        this.staticValues.unlink();
    }

    public void replaceKeys(Key key, Key key2) {
        this.classTypeId.replaceKeys(key, key2);
        this.superClassId.replaceKeys(key, key2);
        if (getAnnotationsDirectory() != null) {
            getUniqueAnnotationsDirectory().replaceKeys(key, key2);
        }
        this.interfaces.replaceKeys(key, key2);
        ClassData classData = getClassData();
        if (classData != null) {
            classData.replaceKeys(key, key2);
        }
    }

    @Override // com.reandroid.dex.id.IdItem, com.reandroid.dex.common.IdUsageIterator
    public Iterator<IdItem> usedIds() {
        return listUsedIds().iterator();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ArrayCollection<IdItem> listUsedIds() {
        ArrayCollection<IdItem> arrayCollection = new ArrayCollection<>(200);
        arrayCollection.add(this.classTypeId.getItem());
        arrayCollection.add(this.superClassId.getItem());
        arrayCollection.add(this.sourceFile.getItem());
        arrayCollection.addAll(this.interfaces.iterator());
        AnnotationsDirectory annotationsDirectory = getAnnotationsDirectory();
        if (annotationsDirectory != null) {
            arrayCollection.addAll(annotationsDirectory.usedIds());
        }
        ClassData classData = getClassData();
        if (classData != null) {
            arrayCollection.addAll(classData.usedIds());
        }
        EncodedArray staticValues = getStaticValues();
        if (staticValues != null) {
            arrayCollection.addAll(staticValues.usedIds());
        }
        int size = arrayCollection.size();
        for (int i = 0; i < size; i++) {
            arrayCollection.addAll(arrayCollection.get(i).usedIds());
        }
        return arrayCollection;
    }

    public void merge(ClassId classId) {
        if (classId == this) {
            return;
        }
        this.accessFlagValue.set(classId.accessFlagValue.get());
        this.superClassId.setItem(classId.superClassId.getKey());
        this.sourceFile.setItem(classId.sourceFile.getKey());
        this.interfaces.setItem(classId.interfaces.getKey());
        this.annotationsDirectory.setItem(classId.annotationsDirectory.getKey());
        EncodedArray staticValues = classId.getStaticValues();
        if (staticValues != null) {
            ((EncodedArray) this.staticValues.getOrCreate()).merge(staticValues);
        }
        ClassData classData = classId.getClassData();
        if (classData != null) {
            getOrCreateClassData().merge(classData);
        }
    }

    public void fromSmali(SmaliClass smaliClass) throws IOException {
        setKey(smaliClass.getKey());
        setAccessFlagsValue(smaliClass.getAccessFlagsValue());
        setSuperClass(smaliClass.getSuperClass());
        setSourceFile(smaliClass.getSourceFileName());
        setInterfaces(smaliClass.getInterfacesKey());
        if (smaliClass.hasClassData()) {
            getOrCreateClassData().fromSmali(smaliClass);
        }
        if (smaliClass.hasAnnotation()) {
            getOrCreateClassAnnotations().fromSmali(smaliClass.getAnnotation());
        }
    }

    @Override // com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        getClassTypeId().append(smaliWriter);
        getSuperClassId().append(smaliWriter);
        getSourceFile().append(smaliWriter);
        smaliWriter.newLine();
        TypeList interfaceTypeList = getInterfaceTypeList();
        if (interfaceTypeList != null) {
            interfaceTypeList.appendInterfaces(smaliWriter);
        }
        AnnotationSet classAnnotations = getClassAnnotations();
        if (classAnnotations != null) {
            smaliWriter.newLine();
            smaliWriter.newLine();
            smaliWriter.appendComment("annotations");
            classAnnotations.append(smaliWriter);
            smaliWriter.newLine();
        }
        ClassData classData = getClassData();
        if (classData != null) {
            classData.append(smaliWriter);
        } else {
            smaliWriter.appendComment("Null class data: " + this.classData.get());
        }
    }

    @Override // java.lang.Comparable
    public int compareTo(ClassId classId) {
        if (classId == null) {
            return -1;
        }
        if (classId == this) {
            return 0;
        }
        return SectionTool.compareIdx(getId(), classId.getId());
    }

    public String toString() {
        if (isReading()) {
            return ".class " + getKey();
        }
        return SmaliWriter.toStringSafe(this);
    }
}
