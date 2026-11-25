package com.reandroid.dex.model;

import com.reandroid.dex.common.AccessFlag;
import com.reandroid.dex.data.AnnotationElement;
import com.reandroid.dex.data.AnnotationItem;
import com.reandroid.dex.data.AnnotationSet;
import com.reandroid.dex.data.AnnotationsDirectory;
import com.reandroid.dex.data.ClassData;
import com.reandroid.dex.data.DataItem;
import com.reandroid.dex.data.FieldDef;
import com.reandroid.dex.data.MethodDef;
import com.reandroid.dex.id.ClassId;
import com.reandroid.dex.id.IdItem;
import com.reandroid.dex.key.FieldKey;
import com.reandroid.dex.key.Key;
import com.reandroid.dex.key.MethodKey;
import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.reference.TypeListReference;
import com.reandroid.dex.smali.SmaliReader;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.dex.smali.model.SmaliField;
import com.reandroid.dex.smali.model.SmaliMethod;
import com.reandroid.dex.value.DexValueBlock;
import com.reandroid.dex.value.DexValueType;
import com.reandroid.dex.value.NullValue;
import com.reandroid.dex.value.StringValue;
import com.reandroid.utils.collection.CollectionUtil;
import com.reandroid.utils.collection.CombiningIterator;
import com.reandroid.utils.collection.ComputeIterator;
import com.reandroid.utils.collection.EmptyIterator;
import com.reandroid.utils.collection.FilterIterator;
import com.reandroid.utils.collection.InstanceIterator;
import com.reandroid.utils.collection.IterableIterator;
import com.reandroid.utils.collection.SingleIterator;
import com.reandroid.utils.collection.UniqueIterator;
import com.reandroid.utils.io.FileUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.annotation.ElementType;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes11.dex */
public class DexClass extends DexDeclaration implements Comparable<DexClass> {
    private final ClassId classId;
    private final DexFile dexFile;

    @Override // com.reandroid.dex.model.DexDeclaration
    public DexClass getDexClass() {
        return this;
    }

    public DexClass(DexFile dexFile, ClassId classId) {
        this.dexFile = dexFile;
        this.classId = classId;
    }

    public boolean usesNative() {
        if (isNative()) {
            return true;
        }
        Iterator<DexMethod> declaredMethods = getDeclaredMethods();
        while (declaredMethods.hasNext()) {
            if (declaredMethods.next().isNative()) {
                return true;
            }
        }
        Iterator<DexField> declaredFields = getDeclaredFields();
        while (declaredFields.hasNext()) {
            if (declaredFields.next().isNative()) {
                return true;
            }
        }
        return false;
    }

    public void replaceKeys(Key key, Key key2) {
        getId().replaceKeys(key, key2);
    }

    public Set<DexClass> getRequired() {
        return getRequired(null);
    }

    public Set<DexClass> getRequired(Predicate<TypeKey> predicate) {
        HashSet hashSet = new HashSet();
        hashSet.add(this);
        searchRequired(predicate, hashSet);
        return hashSet;
    }

    private void searchRequired(Predicate<TypeKey> predicate, Set<DexClass> set) {
        DexClassRepository classRepository = getClassRepository();
        Iterator<TypeKey> itUsedTypes = usedTypes();
        while (itUsedTypes.hasNext()) {
            TypeKey declaring = itUsedTypes.next().getDeclaring();
            if (predicate == null || predicate.test(declaring)) {
                DexClass dexClass = classRepository.getDexClass(declaring);
                if (dexClass != null && !set.contains(dexClass)) {
                    set.add(dexClass);
                    dexClass.searchRequired(predicate, set);
                }
            }
        }
    }

    public Iterator<TypeKey> usedTypes() {
        return InstanceIterator.of(new IterableIterator<Key, Key>(ComputeIterator.of(getId().usedIds(), new Function() { // from class: com.reandroid.dex.model.DexClass$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((IdItem) obj).getKey();
            }
        })) { // from class: com.reandroid.dex.model.DexClass.1
            @Override // com.reandroid.utils.collection.IterableIterator
            public Iterator<Key> iterator(Key key) {
                return key.mentionedKeys();
            }
        }, TypeKey.class);
    }

    public DexMethod getStaticConstructor() {
        return getDeclaredMethod(MethodKey.STATIC_CONSTRUCTOR.changeDeclaring(getDefining()));
    }

    public DexField getField(FieldKey fieldKey) {
        DexField field;
        if (!isAccessibleTo(fieldKey.getDeclaring())) {
            return null;
        }
        DexField declaredField = getDeclaredField(fieldKey);
        if (declaredField != null) {
            return declaredField;
        }
        DexClass superClass = getSuperClass();
        if (superClass != null && (field = superClass.getField(fieldKey)) != null) {
            if (field.isAccessibleTo(getDefining())) {
                return field;
            }
            return null;
        }
        Iterator<DexClass> interfaceClasses = getInterfaceClasses();
        while (interfaceClasses.hasNext()) {
            DexField field2 = interfaceClasses.next().getField(fieldKey);
            if (field2 != null && field2.isAccessibleTo(getDefining())) {
                return field2;
            }
        }
        return null;
    }

    public DexField getDeclaredField(FieldKey fieldKey) {
        Iterator<DexField> declaredFields = getDeclaredFields();
        while (declaredFields.hasNext()) {
            DexField next = declaredFields.next();
            if (fieldKey.equals(next.getKey(), false, true)) {
                return next;
            }
        }
        return null;
    }

    public DexMethod getMethod(MethodKey methodKey) {
        DexMethod declaredMethod = getDeclaredMethod(methodKey);
        if (declaredMethod != null) {
            return declaredMethod;
        }
        Iterator<DexClass> superTypes = getSuperTypes();
        while (superTypes.hasNext()) {
            DexMethod declaredMethod2 = superTypes.next().getDeclaredMethod(methodKey);
            if (declaredMethod2 != null && declaredMethod2.isAccessibleTo(methodKey.getDeclaring())) {
                return declaredMethod2;
            }
        }
        return null;
    }

    public Iterator<DexMethod> getMethods(MethodKey methodKey) {
        return getMethods(methodKey, false);
    }

    public Iterator<DexMethod> getMethods(final MethodKey methodKey, final boolean z) {
        final TypeKey key = getKey();
        return CombiningIterator.two(getDeclaredMethods(methodKey), ComputeIterator.of(getSuperTypes(), new Function() { // from class: com.reandroid.dex.model.DexClass$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return DexClass.lambda$getMethods$0(methodKey, z, key, (DexClass) obj);
            }
        }));
    }

    static /* synthetic */ DexMethod lambda$getMethods$0(MethodKey methodKey, boolean z, TypeKey typeKey, DexClass dexClass) {
        DexMethod declaredMethod = dexClass.getDeclaredMethod(methodKey);
        if (declaredMethod == null || declaredMethod.isPrivate()) {
            return null;
        }
        if (z || declaredMethod.isAccessibleTo(typeKey)) {
            return declaredMethod;
        }
        return null;
    }

    public Iterator<DexMethod> getExtending(final MethodKey methodKey) {
        return CombiningIterator.of(getDeclaredMethod(methodKey), ComputeIterator.of(getExtending(), new Function() { // from class: com.reandroid.dex.model.DexClass$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DexClass) obj).getExtending(methodKey);
            }
        }));
    }

    public Iterator<DexMethod> getImplementations(final MethodKey methodKey) {
        return CombiningIterator.of(getDeclaredMethod(methodKey), ComputeIterator.of(getImplementations(), new Function() { // from class: com.reandroid.dex.model.DexClass$$ExternalSyntheticLambda8
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DexClass) obj).getImplementations(methodKey);
            }
        }));
    }

    public Iterator<MethodKey> getOverridingKeys(MethodKey methodKey) {
        final MethodKey methodKeyChangeDeclaring = methodKey.changeDeclaring(getKey());
        return CombiningIterator.of(CombiningIterator.singleOne(methodKeyChangeDeclaring, SingleIterator.of(getBridgedMethod(methodKey))), ComputeIterator.of(getOverriding(), new Function() { // from class: com.reandroid.dex.model.DexClass$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DexClass) obj).getOverridingKeys(methodKeyChangeDeclaring);
            }
        }));
    }

    private MethodKey getBridgedMethod(MethodKey methodKey) {
        DexMethod bridged;
        DexMethod declaredMethod = getDeclaredMethod(methodKey, false);
        if (declaredMethod == null || (bridged = declaredMethod.getBridged()) == null) {
            return null;
        }
        return bridged.getKey();
    }

    public boolean containsDeclaredMethod(MethodKey methodKey) {
        ClassData classData;
        return (methodKey == null || (classData = getClassData()) == null || classData.getMethod(methodKey) == null) ? false : true;
    }

    public DexMethod getDeclaredMethod(MethodKey methodKey) {
        return getDeclaredMethod(methodKey, false);
    }

    public DexMethod getDeclaredMethod(MethodKey methodKey, boolean z) {
        Iterator<DexMethod> declaredMethods = getDeclaredMethods();
        while (declaredMethods.hasNext()) {
            DexMethod next = declaredMethods.next();
            MethodKey key = next.getKey();
            if (methodKey.equalsNameAndParameters(key) && (z || methodKey.equalsReturnType(key))) {
                return next;
            }
        }
        return null;
    }

    public Iterator<DexMethod> getDeclaredMethods(final MethodKey methodKey) {
        return FilterIterator.of(getDeclaredMethods(), new Predicate() { // from class: com.reandroid.dex.model.DexClass$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return methodKey.equalsNameAndParameters(((DexMethod) obj).getKey());
            }
        });
    }

    public Iterator<DexClass> getOverridingAndSuperTypes() {
        return CombiningIterator.two(getOverriding(), getSuperTypes());
    }

    public Iterator<DexClass> getSuperTypes() {
        return new UniqueIterator(new IterableIterator<DexClass, DexClass>(CombiningIterator.two(SingleIterator.of(getSuperClass()), getInterfaceClasses())) { // from class: com.reandroid.dex.model.DexClass.2
            @Override // com.reandroid.utils.collection.IterableIterator
            public Iterator<DexClass> iterator(DexClass dexClass) {
                return CombiningIterator.two(SingleIterator.of(dexClass), dexClass.getSuperTypes());
            }
        }).exclude(this);
    }

    public Iterator<DexClass> getOverriding() {
        return CombiningIterator.two(getExtending(), getImplementations());
    }

    public Iterator<DexClass> getExtending() {
        return getDexFile().searchExtending(getKey());
    }

    public Iterator<DexClass> getImplementations() {
        return getDexFile().searchImplementations(getKey());
    }

    public DexClass getSuperClass() {
        return search(getSuperClassKey());
    }

    public Iterator<DexField> getDeclaredFields() {
        return CombiningIterator.two(getStaticFields(), getInstanceFields());
    }

    public DexField getOrCreateStaticField(FieldKey fieldKey) {
        return initializeField(getOrCreateStatic(fieldKey));
    }

    public FieldDef getOrCreateStatic(FieldKey fieldKey) {
        return getOrCreateClassData().getOrCreateStatic(fieldKey);
    }

    public Iterator<DexField> getStaticFields() {
        ClassData classData = getClassData();
        if (classData == null) {
            return EmptyIterator.of();
        }
        return ComputeIterator.of(classData.getStaticFields(), new DexClass$$ExternalSyntheticLambda3(this));
    }

    public Iterator<DexField> getInstanceFields() {
        ClassData classData = getClassData();
        if (classData == null) {
            return EmptyIterator.of();
        }
        return ComputeIterator.of(classData.getInstanceFields(), new DexClass$$ExternalSyntheticLambda3(this));
    }

    public DexField getOrCreateInstanceField(FieldKey fieldKey) {
        return initializeField(getOrCreateInstance(fieldKey));
    }

    public FieldDef getOrCreateInstance(FieldKey fieldKey) {
        return getOrCreateClassData().getOrCreateInstance(fieldKey);
    }

    public Iterator<DexMethod> getDeclaredMethods(Predicate<DexMethod> predicate) {
        Iterator<DexMethod> declaredMethods = getDeclaredMethods();
        return predicate == null ? declaredMethods : FilterIterator.of(declaredMethods, predicate);
    }

    public Iterator<DexMethod> getDeclaredMethods() {
        return CombiningIterator.two(getDirectMethods(), getVirtualMethods());
    }

    public Iterator<DexMethod> getDirectMethods() {
        ClassData classData = getClassData();
        if (classData == null) {
            return EmptyIterator.of();
        }
        return ComputeIterator.of(classData.getDirectMethods(), new DexClass$$ExternalSyntheticLambda0(this));
    }

    public Iterator<DexMethod> getVirtualMethods() {
        ClassData classData = getClassData();
        if (classData == null) {
            return EmptyIterator.of();
        }
        return ComputeIterator.of(classData.getVirtualMethods(), new DexClass$$ExternalSyntheticLambda0(this));
    }

    public DexMethod getOrCreateDirectMethod(MethodKey methodKey) {
        return initializeMethod(getOrCreateClassData().getOrCreateDirect(methodKey));
    }

    public DexMethod getOrCreateVirtualMethod(MethodKey methodKey) {
        return initializeMethod(getOrCreateClassData().getOrCreateVirtual(methodKey));
    }

    public DexMethod getOrCreateStaticMethod(MethodKey methodKey) {
        DexMethod orCreateDirectMethod = getOrCreateDirectMethod(methodKey);
        orCreateDirectMethod.addAccessFlag(AccessFlag.STATIC);
        return orCreateDirectMethod;
    }

    DexField initializeField(FieldDef fieldDef) {
        return new DexField(this, fieldDef);
    }

    DexMethod initializeMethod(MethodDef methodDef) {
        return new DexMethod(this, methodDef);
    }

    public boolean isInterface() {
        return AccessFlag.INTERFACE.isSet(getAccessFlagsValue());
    }

    public boolean isEnum() {
        return AccessFlag.ENUM.isSet(getAccessFlagsValue());
    }

    public boolean isAnnotation() {
        return AccessFlag.ANNOTATION.isSet(getAccessFlagsValue());
    }

    public Iterator<DexInstruction> getDexInstructions() {
        return getDexInstructions(null);
    }

    public Iterator<DexInstruction> getDexInstructions(Predicate<DexMethod> predicate) {
        return new IterableIterator<DexMethod, DexInstruction>(getDeclaredMethods(predicate)) { // from class: com.reandroid.dex.model.DexClass.3
            @Override // com.reandroid.utils.collection.IterableIterator
            public Iterator<DexInstruction> iterator(DexMethod dexMethod) {
                return dexMethod.getInstructions();
            }
        };
    }

    public void decode(SmaliWriter smaliWriter, File file) throws IOException {
        File file2 = new File(file, toFilePath());
        File parentFile = file2.getParentFile();
        if (parentFile != null && !parentFile.exists() && !parentFile.mkdirs()) {
            throw new IOException("Failed to create dir: " + parentFile);
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file2);
        smaliWriter.setWriter(new OutputStreamWriter(fileOutputStream));
        append(smaliWriter);
        smaliWriter.close();
        fileOutputStream.close();
    }

    private String toFilePath() {
        String typeName = getDefining().getTypeName();
        return typeName.substring(1, typeName.length() - 1).replace('/', File.separatorChar) + ".smali";
    }

    @Override // com.reandroid.dex.model.DexDeclaration
    public DexFile getDexFile() {
        return this.dexFile;
    }

    @Override // com.reandroid.dex.model.DexDeclaration
    public ClassId getId() {
        return this.classId;
    }

    @Override // com.reandroid.dex.model.DexDeclaration
    public TypeKey getKey() {
        return getId().getKey();
    }

    @Override // com.reandroid.dex.model.DexDeclaration
    public ClassId getDefinition() {
        return getId();
    }

    public TypeKey getSuperClassKey() {
        return getId().getSuperClassKey();
    }

    public void setSuperClass(TypeKey typeKey) {
        getId().setSuperClass(typeKey);
    }

    public String getSourceFileName() {
        return getId().getSourceFileName();
    }

    public void setSourceFile(String str) {
        getId().setSourceFile(str);
    }

    public Iterator<DexClass> getInterfaceClasses() {
        return ComputeIterator.of(getInterfaces(), new Function() { // from class: com.reandroid.dex.model.DexClass$$ExternalSyntheticLambda9
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.search((TypeKey) obj);
            }
        });
    }

    DexClass search(TypeKey typeKey) {
        return getClassRepository().getDexClass(typeKey);
    }

    public boolean containsInterface(TypeKey typeKey) {
        return CollectionUtil.contains(getInterfaces(), typeKey);
    }

    public Iterator<TypeKey> getInterfaces() {
        return getId().getInterfaceKeys();
    }

    public void addInterface(TypeKey typeKey) {
        addInterface(typeKey.getTypeName());
    }

    public void addInterface(String str) {
        getId().getInterfacesReference().add(str);
    }

    public void removeInterface(TypeKey typeKey) {
        if (typeKey != null) {
            removeInterface(typeKey.getTypeName());
        }
    }

    public void removeInterface(String str) {
        TypeListReference interfacesReference = getId().getInterfacesReference();
        interfacesReference.remove(interfacesReference.indexOf(str));
    }

    public void clearInterfaces() {
        getId().getInterfacesReference().setItem((DataItem) null);
    }

    public void clearDebug() {
        Iterator<DexMethod> declaredMethods = getDeclaredMethods();
        while (declaredMethods.hasNext()) {
            declaredMethods.next().clearDebug();
        }
    }

    public void removeAnnotations(Predicate<AnnotationItem> predicate) {
        ClassId id2 = getId();
        AnnotationSet classAnnotations = id2.getClassAnnotations();
        if (classAnnotations == null) {
            return;
        }
        classAnnotations.removeIf(predicate);
        classAnnotations.refresh();
        if (classAnnotations.size() == 0) {
            classAnnotations.removeSelf();
            id2.setClassAnnotations(null);
            AnnotationsDirectory annotationsDirectory = getId().getAnnotationsDirectory();
            if (annotationsDirectory != null && annotationsDirectory.isEmpty()) {
                annotationsDirectory.removeSelf();
                id2.setAnnotationsDirectory(null);
            }
        }
        getId().refresh();
    }

    public void fixDalvikInnerClassName() {
        AnnotationElement element;
        AnnotationItem dalvikInnerClass = getId().getDalvikInnerClass();
        if (dalvikInnerClass == null || (element = dalvikInnerClass.getElement("name")) == null) {
            return;
        }
        DexValueBlock<?> value = element.getValue();
        if (value.is(DexValueType.STRING)) {
            TypeKey key = getKey();
            if (!key.isInnerName()) {
                element.setValue(new NullValue());
            } else {
                ((StringValue) value).setString(key.getSimpleInnerName());
            }
        }
    }

    public Set<Key> fixAccessibility() {
        DexClassRepository classRepository = getClassRepository();
        HashSet hashSet = new HashSet();
        Iterator<Key> itUsedKeys = getId().usedKeys();
        while (itUsedKeys.hasNext()) {
            Key next = itUsedKeys.next();
            if (!hashSet.contains(next) && fixAccessibility(classRepository.getDexDeclaration(next))) {
                hashSet.add(next);
            }
        }
        fixMethodAccessibility(hashSet);
        return hashSet;
    }

    private void fixMethodAccessibility(Set<Key> set) {
        Iterator<DexMethod> declaredMethods = getDeclaredMethods();
        while (declaredMethods.hasNext()) {
            DexMethod next = declaredMethods.next();
            if (!next.isPrivate()) {
                Iterator<DexMethod> methods = getMethods(next.getKey(), true);
                while (methods.hasNext()) {
                    DexMethod next2 = methods.next();
                    if (fixAccessibility(next2)) {
                        set.add(next2.getKey());
                    }
                }
                Iterator<DexMethod> extending = getExtending(next.getKey());
                while (methods.hasNext()) {
                    DexMethod next3 = extending.next();
                    MethodKey key = next3.getKey();
                    if (!set.contains(key) && fixAccessibility(next3)) {
                        set.add(key);
                    }
                }
            }
        }
    }

    private boolean fixAccessibility(DexDeclaration dexDeclaration) {
        if (dexDeclaration == null || dexDeclaration.isPrivate() || dexDeclaration.hasAccessFlag(AccessFlag.CONSTRUCTOR, AccessFlag.STATIC) || dexDeclaration.isAccessibleTo(this)) {
            return false;
        }
        dexDeclaration.addAccessFlag(AccessFlag.PUBLIC);
        return true;
    }

    public TypeKey getDalvikEnclosingClass() {
        Key dalvikEnclosing = getId().getDalvikEnclosing();
        if (dalvikEnclosing != null) {
            return dalvikEnclosing.getDeclaring();
        }
        return null;
    }

    public String getDalvikInnerClassName() {
        DexValue annotationValue = getAnnotationValue(TypeKey.DALVIK_InnerClass, "name");
        if (annotationValue != null) {
            return annotationValue.getString();
        }
        return null;
    }

    public void updateDalvikInnerClassName(String str) {
        DexValue annotationValue = getAnnotationValue(TypeKey.DALVIK_InnerClass, "name");
        if (annotationValue != null) {
            annotationValue.setString(str);
        }
    }

    public void createDalvikInnerClassName(String str) {
        getOrCreateAnnotationValue(TypeKey.DALVIK_InnerClass, "name", DexValueType.STRING).setString(str);
    }

    @Override // com.reandroid.dex.model.AnnotatedDex
    public Iterator<DexAnnotation> getAnnotations() {
        AnnotationSet classAnnotations = getId().getClassAnnotations();
        if (classAnnotations != null) {
            return ComputeIterator.of(classAnnotations.iterator(), new Function() { // from class: com.reandroid.dex.model.DexClass$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return this.f$0.m2762lambda$getAnnotations$5$comreandroiddexmodelDexClass((AnnotationItem) obj);
                }
            });
        }
        return EmptyIterator.of();
    }

    /* JADX INFO: renamed from: lambda$getAnnotations$5$com-reandroid-dex-model-DexClass, reason: not valid java name */
    /* synthetic */ DexAnnotation m2762lambda$getAnnotations$5$comreandroiddexmodelDexClass(AnnotationItem annotationItem) {
        return DexAnnotation.create(this, annotationItem);
    }

    @Override // com.reandroid.dex.model.AnnotatedDex
    public Iterator<DexAnnotation> getAnnotations(TypeKey typeKey) {
        AnnotationSet classAnnotations = getId().getClassAnnotations();
        if (classAnnotations != null) {
            return ComputeIterator.of(classAnnotations.getAll(typeKey), new Function() { // from class: com.reandroid.dex.model.DexClass$$ExternalSyntheticLambda6
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return this.f$0.m2763lambda$getAnnotations$6$comreandroiddexmodelDexClass((AnnotationItem) obj);
                }
            });
        }
        return EmptyIterator.of();
    }

    /* JADX INFO: renamed from: lambda$getAnnotations$6$com-reandroid-dex-model-DexClass, reason: not valid java name */
    /* synthetic */ DexAnnotation m2763lambda$getAnnotations$6$comreandroiddexmodelDexClass(AnnotationItem annotationItem) {
        return DexAnnotation.create(this, annotationItem);
    }

    @Override // com.reandroid.dex.model.AnnotatedDex
    public DexAnnotation getAnnotation(TypeKey typeKey) {
        AnnotationSet classAnnotations = getId().getClassAnnotations();
        if (classAnnotations != null) {
            return DexAnnotation.create(this, classAnnotations.get(typeKey));
        }
        return null;
    }

    @Override // com.reandroid.dex.model.AnnotatedDex
    public DexAnnotation getOrCreateAnnotation(TypeKey typeKey) {
        return DexAnnotation.create(this, getId().getOrCreateClassAnnotations().getOrCreate(typeKey));
    }

    @Override // com.reandroid.dex.model.AnnotatedDex
    public DexAnnotation newAnnotation(TypeKey typeKey) {
        return DexAnnotation.create(this, getId().getOrCreateClassAnnotations().addNewItem(typeKey));
    }

    ClassData getOrCreateClassData() {
        return getId().getOrCreateClassData();
    }

    ClassData getClassData() {
        return getId().getClassData();
    }

    @Override // com.reandroid.dex.model.Dex
    public void removeSelf() {
        getDefinition().removeSelf();
    }

    public void edit() {
        getId().edit();
    }

    @Override // java.lang.Comparable
    public int compareTo(DexClass dexClass) {
        return getKey().compareTo(dexClass.getKey());
    }

    @Override // com.reandroid.dex.model.Dex, com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        getClassData();
        getId().append(smaliWriter);
    }

    public DexMethod parseMethod(SmaliReader smaliReader) throws IOException {
        DexMethod orCreateVirtualMethod;
        SmaliMethod smaliMethod = new SmaliMethod();
        smaliMethod.setDefining(getKey());
        smaliMethod.parse(smaliReader);
        MethodKey key = smaliMethod.getKey();
        if (smaliMethod.isDirect()) {
            orCreateVirtualMethod = getOrCreateDirectMethod(key);
        } else {
            orCreateVirtualMethod = getOrCreateVirtualMethod(key);
        }
        orCreateVirtualMethod.getDefinition().fromSmali(smaliMethod);
        return orCreateVirtualMethod;
    }

    public DexField parseField(SmaliReader smaliReader) throws IOException {
        DexField orCreateStaticField;
        SmaliField smaliField = new SmaliField();
        smaliField.setDefining(getKey());
        smaliField.parse(smaliReader);
        FieldKey key = smaliField.getKey();
        if (smaliField.isInstance()) {
            orCreateStaticField = getOrCreateInstanceField(key);
        } else {
            orCreateStaticField = getOrCreateStaticField(key);
        }
        orCreateStaticField.getDefinition().fromSmali(smaliField);
        return orCreateStaticField;
    }

    public DexField parseInterfaces(SmaliReader smaliReader) throws IOException {
        DexField orCreateStaticField;
        SmaliField smaliField = new SmaliField();
        smaliField.setDefining(getKey());
        smaliField.parse(smaliReader);
        FieldKey key = smaliField.getKey();
        if (smaliField.isInstance()) {
            orCreateStaticField = getOrCreateInstanceField(key);
        } else {
            orCreateStaticField = getOrCreateStaticField(key);
        }
        orCreateStaticField.getDefinition().fromSmali(smaliField);
        return orCreateStaticField;
    }

    public void writeSmali(SmaliWriter smaliWriter, File file) throws IOException {
        File smaliFile = toSmaliFile(file);
        FileUtil.ensureParentDirectory(smaliFile);
        smaliWriter.setWriter(new FileWriter(smaliFile));
        append(smaliWriter);
        smaliWriter.close();
    }

    public File toSmaliFile(File file) {
        return new File(file, buildSmaliPath());
    }

    public String buildSmaliPath() {
        String typeName = getKey().getTypeName();
        return typeName.substring(1, typeName.length() - 1).replace('/', File.separatorChar) + ".smali";
    }

    public String toSmali() throws IOException {
        return SmaliWriter.toString(this);
    }

    public String toSmali(SmaliWriter smaliWriter) throws IOException {
        return SmaliWriter.toString(smaliWriter, this);
    }

    @Override // com.reandroid.dex.model.AnnotatedDex
    public ElementType getElementType() {
        return ElementType.TYPE;
    }

    @Override // com.reandroid.dex.model.DexDeclaration
    public int hashCode() {
        return getKey().hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        DexClass dexClass = (DexClass) obj;
        if (isInSameFile(dexClass)) {
            return getKey().equals(dexClass.getKey());
        }
        return false;
    }

    @Override // com.reandroid.dex.model.DexDeclaration, com.reandroid.dex.model.Dex
    public String toString() {
        return SmaliWriter.toStringSafe(this);
    }
}
