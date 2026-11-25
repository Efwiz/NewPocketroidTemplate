package com.reandroid.dex.model;

import com.reandroid.arsc.item.IntegerReference;
import com.reandroid.dex.data.AnnotationItem;
import com.reandroid.dex.data.FieldDef;
import com.reandroid.dex.id.FieldId;
import com.reandroid.dex.ins.Opcode;
import com.reandroid.dex.key.FieldKey;
import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.dex.value.DexValueBlock;
import com.reandroid.dex.value.DexValueType;
import com.reandroid.dex.value.IntValue;
import com.reandroid.utils.collection.CollectionUtil;
import com.reandroid.utils.collection.ComputeIterator;
import com.reandroid.utils.collection.ExpandIterator;
import com.reandroid.utils.collection.FilterIterator;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes11.dex */
public class DexField extends DexDeclaration {
    private final DexClass dexClass;
    private final FieldDef fieldDef;

    public DexField(DexClass dexClass, FieldDef fieldDef) {
        this.dexClass = dexClass;
        this.fieldDef = fieldDef;
    }

    public String getName() {
        return getId().getName();
    }

    public void setName(String str) {
        getId().setName(str);
    }

    public DexValue getInitialValue() {
        return DexValue.create(this, getDefinition().getStaticInitialValue(true));
    }

    public IntegerReference getStaticIntegerValue() {
        DexValue initialValue;
        if (!isStatic()) {
            return null;
        }
        IntegerReference integerReferenceResolveValueFromStaticConstructor = resolveValueFromStaticConstructor();
        return (integerReferenceResolveValueFromStaticConstructor == null && (initialValue = getInitialValue()) != null && initialValue.is(DexValueType.INT)) ? (IntValue) initialValue.getDexValueBlock() : integerReferenceResolveValueFromStaticConstructor;
    }

    private IntegerReference resolveValueFromStaticConstructor() {
        DexInstruction previousSetter;
        DexMethod staticConstructor = getDexClass().getStaticConstructor();
        if (staticConstructor == null) {
            return null;
        }
        Iterator<DexInstruction> instructions = staticConstructor.getInstructions();
        FieldKey key = getKey();
        while (instructions.hasNext()) {
            DexInstruction next = instructions.next();
            if (key.equals(next.getFieldKey())) {
                if (next.is(Opcode.SPUT) && (previousSetter = next.getPreviousSetter(next.getRegister())) != null) {
                    return previousSetter.getAsIntegerReference();
                }
                return null;
            }
        }
        return null;
    }

    public <T1 extends DexValueBlock<?>> T1 getOrCreateInitialValue(DexValueType<T1> dexValueType) {
        return (T1) getDefinition().getOrCreateStaticValue(dexValueType);
    }

    @Override // com.reandroid.dex.model.DexDeclaration
    public FieldKey getKey() {
        return getId().getKey();
    }

    @Override // com.reandroid.dex.model.DexDeclaration
    public FieldId getId() {
        return getDefinition().getId();
    }

    @Override // com.reandroid.dex.model.DexDeclaration
    public DexClass getDexClass() {
        return this.dexClass;
    }

    @Override // com.reandroid.dex.model.DexDeclaration
    public FieldDef getDefinition() {
        return this.fieldDef;
    }

    @Override // com.reandroid.dex.model.AnnotatedDex
    public Iterator<DexAnnotation> getAnnotations() {
        return ComputeIterator.of(ExpandIterator.of(getDefinition().getAnnotations()), new Function() { // from class: com.reandroid.dex.model.DexField$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m2766lambda$getAnnotations$0$comreandroiddexmodelDexField((AnnotationItem) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$getAnnotations$0$com-reandroid-dex-model-DexField, reason: not valid java name */
    /* synthetic */ DexAnnotation m2766lambda$getAnnotations$0$comreandroiddexmodelDexField(AnnotationItem annotationItem) {
        return DexAnnotation.create(this, annotationItem);
    }

    @Override // com.reandroid.dex.model.AnnotatedDex
    public Iterator<DexAnnotation> getAnnotations(final TypeKey typeKey) {
        return FilterIterator.of(getAnnotations(), new Predicate() { // from class: com.reandroid.dex.model.DexField$$ExternalSyntheticLambda1
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
        return ElementType.FIELD;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return FieldId.equals(getId(), ((DexField) obj).getId());
    }
}
