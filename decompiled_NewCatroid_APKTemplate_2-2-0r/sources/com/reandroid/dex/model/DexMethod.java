package com.reandroid.dex.model;

import com.reandroid.dex.common.AccessFlag;
import com.reandroid.dex.common.Register;
import com.reandroid.dex.common.RegistersTable;
import com.reandroid.dex.data.AnnotationItem;
import com.reandroid.dex.data.CodeItem;
import com.reandroid.dex.data.InstructionList;
import com.reandroid.dex.data.MethodDef;
import com.reandroid.dex.id.MethodId;
import com.reandroid.dex.ins.Ins;
import com.reandroid.dex.ins.Opcode;
import com.reandroid.dex.ins.TryBlock;
import com.reandroid.dex.key.Key;
import com.reandroid.dex.key.MethodKey;
import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.smali.SmaliReader;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.dex.smali.model.SmaliInstruction;
import com.reandroid.utils.collection.CollectionUtil;
import com.reandroid.utils.collection.CombiningIterator;
import com.reandroid.utils.collection.ComputeIterator;
import com.reandroid.utils.collection.EmptyIterator;
import com.reandroid.utils.collection.EmptyList;
import com.reandroid.utils.collection.ExpandIterator;
import com.reandroid.utils.collection.FilterIterator;
import com.reandroid.utils.collection.MergingIterator;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes11.dex */
public class DexMethod extends DexDeclaration {
    private final DexClass dexClass;
    private int mEditIndex;
    private final MethodDef methodDef;

    public DexMethod(DexClass dexClass, MethodDef methodDef) {
        this.dexClass = dexClass;
        this.methodDef = methodDef;
    }

    public DexMethod getDeclared() {
        DexMethod method;
        DexClass superClass = getDexClass().getSuperClass();
        if (superClass != null && (method = superClass.getMethod(getKey())) != null) {
            return method.getDeclared();
        }
        Iterator<DexClass> interfaceClasses = getDexClass().getInterfaceClasses();
        while (interfaceClasses.hasNext()) {
            DexMethod method2 = interfaceClasses.next().getMethod(getKey());
            if (method2 != null) {
                return method2.getDeclared();
            }
        }
        return this;
    }

    public Iterator<DexMethod> getSuperMethods() {
        final MethodKey key = getKey();
        return ComputeIterator.of(getDexClass().getSuperTypes(), new Function() { // from class: com.reandroid.dex.model.DexMethod$$ExternalSyntheticLambda8
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DexClass) obj).getDeclaredMethod(key);
            }
        });
    }

    public Iterator<DexMethod> getOverriding() {
        return CombiningIterator.two(getExtending(), getImplementations());
    }

    public DexMethod getBridged() {
        if (!isBridge()) {
            return null;
        }
        Iterator<DexInstruction> instructions = getInstructions();
        MethodKey methodKey = null;
        while (instructions.hasNext()) {
            Key key = instructions.next().getKey();
            if (key instanceof MethodKey) {
                MethodKey methodKey2 = (MethodKey) key;
                if (methodKey != null) {
                    return null;
                }
                methodKey = methodKey2;
            }
        }
        if (methodKey != null && getDefining().equals(methodKey.getDeclaring()) && getName().equals(methodKey.getName())) {
            return getDexClass().getDeclaredMethod(methodKey, false);
        }
        return null;
    }

    public Iterator<DexMethod> getExtending() {
        return new MergingIterator(ComputeIterator.of(getDexClass().getExtending(), new Function() { // from class: com.reandroid.dex.model.DexMethod$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m2769lambda$getExtending$1$comreandroiddexmodelDexMethod((DexClass) obj);
            }
        }));
    }

    /* JADX INFO: renamed from: lambda$getExtending$1$com-reandroid-dex-model-DexMethod, reason: not valid java name */
    /* synthetic */ Iterator m2769lambda$getExtending$1$comreandroiddexmodelDexMethod(DexClass dexClass) {
        return dexClass.getExtending(getKey());
    }

    public Iterator<DexMethod> getImplementations() {
        return new MergingIterator(ComputeIterator.of(getDexClass().getImplementations(), new Function() { // from class: com.reandroid.dex.model.DexMethod$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m2770lambda$getImplementations$2$comreandroiddexmodelDexMethod((DexClass) obj);
            }
        }));
    }

    /* JADX INFO: renamed from: lambda$getImplementations$2$com-reandroid-dex-model-DexMethod, reason: not valid java name */
    /* synthetic */ Iterator m2770lambda$getImplementations$2$comreandroiddexmodelDexMethod(DexClass dexClass) {
        return dexClass.getImplementations(getKey());
    }

    public Iterator<MethodKey> getOverridingKeys() {
        return new MergingIterator(ComputeIterator.of(getDexClass().getOverriding(), new Function() { // from class: com.reandroid.dex.model.DexMethod$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m2771lambda$getOverridingKeys$3$comreandroiddexmodelDexMethod((DexClass) obj);
            }
        }));
    }

    /* JADX INFO: renamed from: lambda$getOverridingKeys$3$com-reandroid-dex-model-DexMethod, reason: not valid java name */
    /* synthetic */ Iterator m2771lambda$getOverridingKeys$3$comreandroiddexmodelDexMethod(DexClass dexClass) {
        return dexClass.getOverridingKeys(getKey());
    }

    public String getName() {
        return getDefinition().getName();
    }

    public void setName(String str) {
        getDefinition().setName(str);
    }

    @Override // com.reandroid.dex.model.AnnotatedDex
    public Iterator<DexAnnotation> getAnnotations() {
        return ComputeIterator.of(ExpandIterator.of(getDefinition().getAnnotations()), new Function() { // from class: com.reandroid.dex.model.DexMethod$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m2768lambda$getAnnotations$4$comreandroiddexmodelDexMethod((AnnotationItem) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$getAnnotations$4$com-reandroid-dex-model-DexMethod, reason: not valid java name */
    /* synthetic */ DexAnnotation m2768lambda$getAnnotations$4$comreandroiddexmodelDexMethod(AnnotationItem annotationItem) {
        return DexAnnotation.create(this, annotationItem);
    }

    @Override // com.reandroid.dex.model.AnnotatedDex
    public Iterator<DexAnnotation> getAnnotations(final TypeKey typeKey) {
        return FilterIterator.of(getAnnotations(), new Predicate() { // from class: com.reandroid.dex.model.DexMethod$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return typeKey.equals(((DexAnnotation) obj).getType());
            }
        });
    }

    @Override // com.reandroid.dex.model.AnnotatedDex
    public DexAnnotation getAnnotation(TypeKey typeKey) {
        return (DexAnnotation) CollectionUtil.getFirst(getAnnotations(typeKey));
    }

    @Override // com.reandroid.dex.model.AnnotatedDex
    public DexAnnotation getOrCreateAnnotation(TypeKey typeKey) {
        return DexAnnotation.create(this, getDefinition().getOrCreateAnnotationSet().getOrCreate(typeKey));
    }

    @Override // com.reandroid.dex.model.AnnotatedDex
    public DexAnnotation newAnnotation(TypeKey typeKey) {
        return DexAnnotation.create(this, getDefinition().getOrCreateAnnotationSet().addNewItem(typeKey));
    }

    static /* synthetic */ boolean lambda$getInstructions$6(Opcode opcode, Ins ins) {
        return ins.getOpcode() == opcode;
    }

    public Iterator<DexInstruction> getInstructions(final Opcode<?> opcode) {
        return getInstructions(new Predicate() { // from class: com.reandroid.dex.model.DexMethod$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return DexMethod.lambda$getInstructions$6(opcode, (Ins) obj);
            }
        });
    }

    public Iterator<DexInstruction> getInstructions(Predicate<? super Ins> predicate) {
        return ComputeIterator.of(FilterIterator.of(getDefinition().getInstructions(), predicate), new Function() { // from class: com.reandroid.dex.model.DexMethod$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.create((Ins) obj);
            }
        });
    }

    public Iterator<DexInstruction> getInstructions() {
        return DexInstruction.create(this, getDefinition().getInstructions());
    }

    int getEditIndex() {
        return this.mEditIndex;
    }

    void setEditIndex(int i) {
        this.mEditIndex = i;
    }

    public void clearCode() {
        getDefinition().clearCode();
    }

    public void clearDebug() {
        getDefinition().clearDebug();
    }

    public Iterator<DexTry> getDexTry() {
        TryBlock tryBlock = getDefinition().getTryBlock();
        if (tryBlock == null) {
            return EmptyIterator.of();
        }
        return DexTry.create(this, tryBlock.iterator());
    }

    public DexTry createDexTry() {
        return DexTry.create(this, getDefinition().getOrCreateTryBlock().createNext());
    }

    public DexInstruction getInstruction(int i) {
        return create(getDefinition().getInstruction(i));
    }

    public DexInstruction getInstructionAt(int i) {
        return create(getDefinition().getInstructionAt(i));
    }

    public DexInstruction addInstruction(Opcode<?> opcode) {
        return create(getDefinition().getOrCreateInstructionList().createNext(opcode));
    }

    public DexInstruction parseInstruction(String str) throws IOException {
        return parseInstruction(SmaliReader.of(str));
    }

    public DexInstruction parseInstruction(SmaliReader smaliReader) throws IOException {
        return parseInstruction(getInstructionsCount(), smaliReader);
    }

    public DexInstruction parseInstruction(int i, SmaliReader smaliReader) throws IOException {
        SmaliInstruction smaliInstruction = new SmaliInstruction();
        smaliInstruction.parse(smaliReader);
        Ins insCreateAt = getDefinition().getOrCreateInstructionList().createAt(i, smaliInstruction.getOpcode());
        insCreateAt.fromSmali(smaliInstruction);
        return create(insCreateAt);
    }

    public DexInstruction createInstruction(int i, Opcode<?> opcode) {
        return create(getDefinition().getOrCreateInstructionList().createAt(i, opcode));
    }

    public int getInstructionsCount() {
        return getDefinition().getInstructionsCount();
    }

    public RegistersTable getRegistersTable() {
        return getDefinition().getCodeItem();
    }

    public RegistersTable getOrCreateRegistersTable() {
        return getDefinition().getOrCreateCodeItem();
    }

    public List<Register> getLocalFreeRegisters(int i) {
        InstructionList instructionList = getInstructionList();
        if (instructionList != null) {
            return instructionList.getLocalFreeRegisters(i);
        }
        return EmptyList.of();
    }

    public void ensureLocalRegistersCount(int i) {
        if (i == 0) {
            return;
        }
        RegistersTable registersTable = getRegistersTable();
        if (registersTable == null || i > registersTable.getLocalRegistersCount()) {
            getOrCreateRegistersTable().ensureLocalRegistersCount(i);
        }
    }

    public int refreshParameterRegistersCount() {
        RegistersTable registersTable = getRegistersTable();
        if (registersTable == null) {
            return 0;
        }
        int parameterRegistersCount = getKey().getParameterRegistersCount();
        if (!isStatic()) {
            parameterRegistersCount++;
        }
        int localRegistersCount = registersTable.getLocalRegistersCount();
        registersTable.setParameterRegistersCount(parameterRegistersCount);
        registersTable.setRegistersCount(localRegistersCount + parameterRegistersCount);
        return parameterRegistersCount;
    }

    private InstructionList getInstructionList() {
        return getDefinition().getInstructionList();
    }

    public int getLocalRegistersCount() {
        RegistersTable registersTable = getRegistersTable();
        if (registersTable != null) {
            return registersTable.getLocalRegistersCount();
        }
        return 0;
    }

    public void setParameterRegistersCount(int i) {
        CodeItem orCreateCodeItem = getDefinition().getOrCreateCodeItem();
        if (orCreateCodeItem != null) {
            orCreateCodeItem.setParameterRegistersCount(i);
        }
    }

    public void setLocalRegistersCount(int i) {
        CodeItem orCreateCodeItem = getDefinition().getOrCreateCodeItem();
        if (orCreateCodeItem != null) {
            orCreateCodeItem.setRegistersCount(orCreateCodeItem.getParameterRegistersCount() + i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public DexInstruction create(Ins ins) {
        return DexInstruction.create(this, ins);
    }

    @Override // com.reandroid.dex.model.DexDeclaration
    public MethodKey getKey() {
        return getId().getKey();
    }

    @Override // com.reandroid.dex.model.DexDeclaration
    public MethodId getId() {
        return getDefinition().getId();
    }

    @Override // com.reandroid.dex.model.DexDeclaration
    public DexClass getDexClass() {
        return this.dexClass;
    }

    @Override // com.reandroid.dex.model.DexDeclaration
    public MethodDef getDefinition() {
        return this.methodDef;
    }

    public boolean isConstructor() {
        return AccessFlag.CONSTRUCTOR.isSet(getAccessFlagsValue());
    }

    public boolean isBridge() {
        return AccessFlag.BRIDGE.isSet(getAccessFlagsValue());
    }

    public boolean isDirect() {
        return isConstructor() || isPrivate() || isStatic();
    }

    public boolean isVirtual() {
        return !isDirect();
    }

    public Iterator<DexMethodParameter> getParameters() {
        return ComputeIterator.of(getDefinition().getParameters(), new Function() { // from class: com.reandroid.dex.model.DexMethod$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m2772lambda$getParameters$7$comreandroiddexmodelDexMethod((MethodDef.Parameter) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$getParameters$7$com-reandroid-dex-model-DexMethod, reason: not valid java name */
    /* synthetic */ DexMethodParameter m2772lambda$getParameters$7$comreandroiddexmodelDexMethod(MethodDef.Parameter parameter) {
        return DexMethodParameter.create(this, parameter);
    }

    @Override // com.reandroid.dex.model.Dex
    public void removeSelf() {
        getDefinition().removeSelf();
    }

    @Override // com.reandroid.dex.model.Dex, com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        getDefinition().append(smaliWriter);
    }

    @Override // com.reandroid.dex.model.AnnotatedDex
    public ElementType getElementType() {
        return ElementType.METHOD;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return MethodId.equals(true, getId(), ((DexMethod) obj).getId());
    }
}
