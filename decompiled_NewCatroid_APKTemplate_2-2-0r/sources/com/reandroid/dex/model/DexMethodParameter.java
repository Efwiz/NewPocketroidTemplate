package com.reandroid.dex.model;

import com.reandroid.dex.data.AnnotationItem;
import com.reandroid.dex.data.MethodDef;
import com.reandroid.dex.key.Key;
import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.utils.ObjectsUtil;
import com.reandroid.utils.collection.CollectionUtil;
import com.reandroid.utils.collection.ComputeIterator;
import com.reandroid.utils.collection.FilterIterator;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes11.dex */
public class DexMethodParameter extends Dex implements AnnotatedDex {
    private final DexMethod dexMethod;
    private final MethodDef.Parameter parameter;

    public DexMethodParameter(DexMethod dexMethod, MethodDef.Parameter parameter) {
        this.dexMethod = dexMethod;
        this.parameter = parameter;
    }

    @Override // com.reandroid.dex.model.AnnotatedDex
    public Iterator<DexAnnotation> getAnnotations() {
        return ComputeIterator.of(getParameter().getAnnotationItems(), new Function() { // from class: com.reandroid.dex.model.DexMethodParameter$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m2773xf7aa6295((AnnotationItem) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$getAnnotations$0$com-reandroid-dex-model-DexMethodParameter, reason: not valid java name */
    /* synthetic */ DexAnnotation m2773xf7aa6295(AnnotationItem annotationItem) {
        return DexAnnotation.create(this, annotationItem);
    }

    @Override // com.reandroid.dex.model.AnnotatedDex
    public Iterator<DexAnnotation> getAnnotations(final TypeKey typeKey) {
        return FilterIterator.of(getAnnotations(), new Predicate() { // from class: com.reandroid.dex.model.DexMethodParameter$$ExternalSyntheticLambda0
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
        return DexAnnotation.create(this, getParameter().getOrCreateAnnotationItem(typeKey));
    }

    @Override // com.reandroid.dex.model.AnnotatedDex
    public DexAnnotation newAnnotation(TypeKey typeKey) {
        return DexAnnotation.create(this, getParameter().addAnnotationItem(typeKey));
    }

    public String getDebugName() {
        return getParameter().getDebugName();
    }

    public void removeDebugName() {
        getParameter().setDebugName(null);
    }

    public void setDebugName(String str) {
        getParameter().setDebugName(str);
    }

    public void clearAnnotations() {
        getParameter().clearAnnotations();
    }

    public DexClass getTypeClass() {
        return getClassRepository().getDexClass(getType());
    }

    public TypeKey getType() {
        return getParameter().getType();
    }

    public int getIndex() {
        return getParameter().getDefinitionIndex();
    }

    public DexMethod getDexMethod() {
        return this.dexMethod;
    }

    public MethodDef.Parameter getParameter() {
        return this.parameter;
    }

    @Override // com.reandroid.dex.model.Dex
    public boolean uses(Key key) {
        if (ObjectsUtil.equals(getType(), key)) {
            return true;
        }
        Iterator<DexAnnotation> annotations = getAnnotations();
        while (annotations.hasNext()) {
            if (annotations.next().uses(key)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.reandroid.dex.model.Dex
    public DexClassRepository getClassRepository() {
        return getDexMethod().getClassRepository();
    }

    @Override // com.reandroid.dex.model.Dex
    public void removeSelf() {
        getParameter().remove();
    }

    @Override // com.reandroid.dex.model.Dex, com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        getParameter().append(smaliWriter);
    }

    @Override // com.reandroid.dex.model.AnnotatedDex
    public ElementType getElementType() {
        return ElementType.PARAMETER;
    }

    public static DexMethodParameter create(DexMethod dexMethod, MethodDef.Parameter parameter) {
        if (dexMethod == null || parameter == null) {
            return null;
        }
        return new DexMethodParameter(dexMethod, parameter);
    }
}
