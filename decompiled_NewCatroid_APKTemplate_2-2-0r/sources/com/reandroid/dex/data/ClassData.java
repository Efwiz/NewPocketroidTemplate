package com.reandroid.dex.data;

import androidx.exifinterface.media.ExifInterface;
import com.reandroid.arsc.base.Block;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.dex.base.Ule128Item;
import com.reandroid.dex.common.AccessFlag;
import com.reandroid.dex.id.ClassId;
import com.reandroid.dex.id.IdItem;
import com.reandroid.dex.ins.Ins;
import com.reandroid.dex.ins.Opcode;
import com.reandroid.dex.key.FieldKey;
import com.reandroid.dex.key.Key;
import com.reandroid.dex.key.MethodKey;
import com.reandroid.dex.sections.SectionType;
import com.reandroid.dex.smali.SmaliFormat;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.dex.smali.model.SmaliClass;
import com.reandroid.dex.smali.model.SmaliField;
import com.reandroid.dex.smali.model.SmaliMethod;
import com.reandroid.utils.CompareUtil;
import com.reandroid.utils.collection.ArrayIterator;
import com.reandroid.utils.collection.CombiningIterator;
import com.reandroid.utils.collection.EmptyIterator;
import com.reandroid.utils.collection.FilterIterator;
import com.reandroid.utils.collection.IterableIterator;
import java.io.IOException;
import java.util.Iterator;
import java.util.function.Predicate;
import name.antonsmirnov.firmata.FormatHelper;
import net.bytebuddy.description.method.MethodDescription;

/* JADX INFO: loaded from: classes11.dex */
public class ClassData extends DataItem implements SmaliFormat {
    private MethodDefArray directMethods;
    private final Ule128Item directMethodsCount;
    private FieldDefArray instanceFields;
    private final Ule128Item instanceFieldsCount;
    private StaticFieldDefArray staticFields;
    private final Ule128Item staticFieldsCount;
    private final Ule128Item virtualMethodCount;
    private MethodDefArray virtualMethods;

    public ClassData() {
        super(8);
        Ule128Item ule128Item = new Ule128Item();
        this.staticFieldsCount = ule128Item;
        Ule128Item ule128Item2 = new Ule128Item();
        this.instanceFieldsCount = ule128Item2;
        Ule128Item ule128Item3 = new Ule128Item();
        this.directMethodsCount = ule128Item3;
        Ule128Item ule128Item4 = new Ule128Item();
        this.virtualMethodCount = ule128Item4;
        addChild(0, ule128Item);
        addChild(1, ule128Item2);
        addChild(2, ule128Item3);
        addChild(3, ule128Item4);
    }

    @Override // com.reandroid.dex.common.SectionItem
    public SectionType<ClassData> getSectionType() {
        return SectionType.CLASS_DATA;
    }

    public void remove(Key key) {
        Def<?> def = get(key);
        if (def != null) {
            def.removeSelf();
        }
    }

    public void removeField(FieldKey fieldKey) {
        FieldDef field = getField(fieldKey);
        if (field != null) {
            field.removeSelf();
        }
    }

    public void removeMethod(MethodKey methodKey) {
        MethodDef method = getMethod(methodKey);
        if (method != null) {
            method.removeSelf();
        }
    }

    public Def<?> get(Key key) {
        if (key instanceof FieldKey) {
            return getField((FieldKey) key);
        }
        if (key instanceof MethodKey) {
            return getMethod((MethodKey) key);
        }
        if (key == null) {
            return null;
        }
        throw new RuntimeException("Unknown key type: " + key.getClass() + ", '" + key + FormatHelper.QUOTE);
    }

    public FieldDef getField(FieldKey fieldKey) {
        FieldDefArray fieldDefArray;
        StaticFieldDefArray staticFieldDefArray = this.staticFields;
        FieldDef fieldDef = staticFieldDefArray != null ? staticFieldDefArray.get(fieldKey) : null;
        return (fieldDef != null || (fieldDefArray = this.instanceFields) == null) ? fieldDef : fieldDefArray.get(fieldKey);
    }

    public MethodDef getMethod(MethodKey methodKey) {
        MethodDefArray methodDefArray;
        MethodDefArray methodDefArray2 = this.directMethods;
        MethodDef methodDef = methodDefArray2 != null ? methodDefArray2.get(methodKey) : null;
        return (methodDef != null || (methodDefArray = this.virtualMethods) == null) ? methodDef : methodDefArray.get(methodKey);
    }

    public FieldDef getOrCreateStatic(FieldKey fieldKey) {
        FieldDef orCreate = initStaticFieldsArray().getOrCreate(fieldKey);
        orCreate.addAccessFlag(AccessFlag.STATIC);
        return orCreate;
    }

    public FieldDef getOrCreateInstance(FieldKey fieldKey) {
        return initInstanceFieldsArray().getOrCreate(fieldKey);
    }

    public void ensureStaticConstructor(String str) {
        MethodKey methodKey = new MethodKey(str, MethodDescription.TYPE_INITIALIZER_INTERNAL_NAME, (String[]) null, ExifInterface.GPS_MEASUREMENT_INTERRUPTED);
        if (getMethod(methodKey) != null) {
            return;
        }
        MethodDef orCreate = initDirectMethodsArray().getOrCreate(methodKey);
        orCreate.addAccessFlag(AccessFlag.STATIC);
        orCreate.addAccessFlag(AccessFlag.CONSTRUCTOR);
        orCreate.getCodeItem().getInstructionList().add(Opcode.RETURN_VOID.newInstance());
    }

    public MethodDef getOrCreateDirect(MethodKey methodKey) {
        return initDirectMethodsArray().getOrCreate(methodKey);
    }

    public MethodDef getOrCreateVirtual(MethodKey methodKey) {
        return initVirtualMethodsArray().getOrCreate(methodKey);
    }

    public Iterator<Ins> getInstructions() {
        return new IterableIterator<MethodDef, Ins>(getMethods()) { // from class: com.reandroid.dex.data.ClassData.1
            @Override // com.reandroid.utils.collection.IterableIterator
            public Iterator<Ins> iterator(MethodDef methodDef) {
                return methodDef.getInstructions();
            }
        };
    }

    public Iterator<FieldDef> getFields() {
        return new CombiningIterator(getStaticFields(), getInstanceFields());
    }

    public Iterator<MethodDef> getMethods() {
        return new CombiningIterator(getDirectMethods(), getVirtualMethods());
    }

    public Iterator<MethodDef> getDirectMethods() {
        MethodDefArray methodDefArray = this.directMethods;
        if (methodDefArray == null) {
            return EmptyIterator.of();
        }
        return methodDefArray.arrayIterator();
    }

    public Iterator<MethodDef> getConstructors() {
        return FilterIterator.of(getDirectMethods(), new Predicate() { // from class: com.reandroid.dex.data.ClassData$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((MethodDef) obj).isConstructor();
            }
        });
    }

    public Iterator<MethodDef> getVirtualMethods() {
        MethodDefArray methodDefArray = this.virtualMethods;
        if (methodDefArray == null) {
            return EmptyIterator.of();
        }
        return methodDefArray.arrayIterator();
    }

    public Iterator<FieldDef> getStaticFields() {
        StaticFieldDefArray staticFieldDefArray = this.staticFields;
        if (staticFieldDefArray == null) {
            return EmptyIterator.of();
        }
        return staticFieldDefArray.arrayIterator();
    }

    public Iterator<FieldDef> getInstanceFields() {
        FieldDefArray fieldDefArray = this.instanceFields;
        if (fieldDefArray == null) {
            return EmptyIterator.of();
        }
        return fieldDefArray.arrayIterator();
    }

    public int getStaticFieldsCount() {
        StaticFieldDefArray staticFieldDefArray = this.staticFields;
        if (staticFieldDefArray == null) {
            return 0;
        }
        return staticFieldDefArray.getCount();
    }

    public int getInstanceFieldsCount() {
        FieldDefArray fieldDefArray = this.instanceFields;
        if (fieldDefArray == null) {
            return 0;
        }
        return fieldDefArray.getCount();
    }

    public int getDirectMethodsCount() {
        MethodDefArray methodDefArray = this.directMethods;
        if (methodDefArray == null) {
            return 0;
        }
        return methodDefArray.getCount();
    }

    public int getVirtualMethodsCount() {
        MethodDefArray methodDefArray = this.virtualMethods;
        if (methodDefArray == null) {
            return 0;
        }
        return methodDefArray.getCount();
    }

    public StaticFieldDefArray getStaticFieldsArray() {
        return this.staticFields;
    }

    public FieldDefArray getInstanceFieldsArray() {
        return this.instanceFields;
    }

    public MethodDefArray getDirectMethodsArray() {
        return this.directMethods;
    }

    public MethodDefArray getVirtualMethodArray() {
        return this.virtualMethods;
    }

    private FieldDefArray initStaticFieldsArray() {
        StaticFieldDefArray staticFieldDefArray = this.staticFields;
        if (staticFieldDefArray != null) {
            return staticFieldDefArray;
        }
        StaticFieldDefArray staticFieldDefArray2 = new StaticFieldDefArray(this.staticFieldsCount);
        this.staticFields = staticFieldDefArray2;
        addChild(4, staticFieldDefArray2);
        return staticFieldDefArray2;
    }

    private FieldDefArray initInstanceFieldsArray() {
        FieldDefArray fieldDefArray = this.instanceFields;
        if (fieldDefArray != null) {
            return fieldDefArray;
        }
        FieldDefArray fieldDefArray2 = new FieldDefArray(this.instanceFieldsCount);
        this.instanceFields = fieldDefArray2;
        addChild(5, fieldDefArray2);
        return fieldDefArray2;
    }

    private MethodDefArray initDirectMethodsArray() {
        MethodDefArray methodDefArray = this.directMethods;
        if (methodDefArray != null) {
            return methodDefArray;
        }
        MethodDefArray methodDefArray2 = new MethodDefArray(this.directMethodsCount);
        this.directMethods = methodDefArray2;
        addChild(6, methodDefArray2);
        return methodDefArray2;
    }

    private MethodDefArray initVirtualMethodsArray() {
        MethodDefArray methodDefArray = this.virtualMethods;
        if (methodDefArray != null) {
            return methodDefArray;
        }
        MethodDefArray methodDefArray2 = new MethodDefArray(this.virtualMethodCount);
        this.virtualMethods = methodDefArray2;
        addChild(7, methodDefArray2);
        return methodDefArray2;
    }

    private Iterator<DefArray<?>> getDefArrays() {
        return ArrayIterator.of(getChildes(), 4, 4);
    }

    public void setClassId(ClassId classId) {
        Iterator<DefArray<?>> defArrays = getDefArrays();
        while (defArrays.hasNext()) {
            defArrays.next().setClassId(classId);
        }
    }

    @Override // com.reandroid.dex.common.SectionItemContainer, com.reandroid.arsc.item.BlockItem, com.reandroid.arsc.base.Block
    public void onReadBytes(BlockReader blockReader) throws IOException {
        super.onReadBytes(blockReader);
        if (this.staticFieldsCount.get() != 0) {
            initStaticFieldsArray().onReadBytes(blockReader);
        }
        if (this.instanceFieldsCount.get() != 0) {
            initInstanceFieldsArray().onReadBytes(blockReader);
        }
        if (this.directMethodsCount.get() != 0) {
            initDirectMethodsArray().onReadBytes(blockReader);
        }
        if (this.virtualMethodCount.get() != 0) {
            initVirtualMethodsArray().onReadBytes(blockReader);
        }
    }

    @Override // com.reandroid.dex.data.DataItem, com.reandroid.dex.common.SectionItemContainer
    protected void onRefreshed() {
        super.onRefreshed();
        Iterator<DefArray<?>> defArrays = getDefArrays();
        while (defArrays.hasNext()) {
            defArrays.next().sort(CompareUtil.getComparableComparator());
        }
    }

    @Override // com.reandroid.dex.data.DataItem, com.reandroid.dex.common.SectionItem
    public void removeSelf() {
        super.removeSelf();
        Iterator<DefArray<?>> defArrays = getDefArrays();
        while (defArrays.hasNext()) {
            defArrays.next().clearChildes();
        }
        setClassId(null);
    }

    public void replaceKeys(Key key, Key key2) {
        Iterator<DefArray<?>> defArrays = getDefArrays();
        while (defArrays.hasNext()) {
            defArrays.next().replaceKeys(key, key2);
        }
    }

    @Override // com.reandroid.dex.common.SectionItem, com.reandroid.dex.common.EditableItem
    public void editInternal(Block block) {
        StaticFieldDefArray staticFieldDefArray = this.staticFields;
        if (staticFieldDefArray != null) {
            staticFieldDefArray.editInternal(block);
        }
        FieldDefArray fieldDefArray = this.instanceFields;
        if (fieldDefArray != null) {
            fieldDefArray.editInternal(block);
        }
        MethodDefArray methodDefArray = this.directMethods;
        if (methodDefArray != null) {
            methodDefArray.editInternal(block);
        }
        MethodDefArray methodDefArray2 = this.virtualMethods;
        if (methodDefArray2 != null) {
            methodDefArray2.editInternal(block);
        }
    }

    @Override // com.reandroid.dex.data.DataItem, com.reandroid.dex.common.IdUsageIterator
    public Iterator<IdItem> usedIds() {
        return new IterableIterator<DefArray<?>, IdItem>(getDefArrays()) { // from class: com.reandroid.dex.data.ClassData.2
            @Override // com.reandroid.utils.collection.IterableIterator
            public Iterator<IdItem> iterator(DefArray<?> defArray) {
                return defArray.usedIds();
            }
        };
    }

    public void merge(ClassData classData) {
        if (classData.getStaticFieldsCount() != 0) {
            initStaticFieldsArray().merge(classData.staticFields);
        }
        if (classData.getInstanceFieldsCount() != 0) {
            initInstanceFieldsArray().merge(classData.instanceFields);
        }
        if (classData.getDirectMethodsCount() != 0) {
            initDirectMethodsArray().merge(classData.directMethods);
        }
        if (classData.getVirtualMethodsCount() != 0) {
            initVirtualMethodsArray().merge(classData.virtualMethods);
        }
    }

    public void fromSmali(SmaliClass smaliClass) throws IOException {
        Iterator<SmaliField> staticFields = smaliClass.getStaticFields();
        if (staticFields.hasNext()) {
            initStaticFieldsArray().fromSmali(staticFields);
        }
        Iterator<SmaliField> instanceFields = smaliClass.getInstanceFields();
        if (instanceFields.hasNext()) {
            initInstanceFieldsArray().fromSmali(instanceFields);
        }
        Iterator<SmaliMethod> directMethods = smaliClass.getDirectMethods();
        if (directMethods.hasNext()) {
            initDirectMethodsArray().fromSmali(directMethods);
        }
        Iterator<SmaliMethod> virtualMethods = smaliClass.getVirtualMethods();
        if (virtualMethods.hasNext()) {
            initVirtualMethodsArray().fromSmali(virtualMethods);
        }
    }

    @Override // com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        smaliWriter.appendOptional(this.staticFields, "static fields");
        smaliWriter.appendOptional(this.instanceFields, "instance fields");
        smaliWriter.appendOptional(this.directMethods, "direct methods");
        smaliWriter.appendOptional(this.virtualMethods, "virtual methods");
    }

    public String toString() {
        return "staticFieldsCount=" + this.staticFieldsCount + ", instanceFieldCount=" + this.instanceFieldsCount + ", directMethodCount=" + this.directMethodsCount + ", virtualMethodCount=" + this.virtualMethodCount;
    }
}
