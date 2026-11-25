package com.reandroid.dex.model;

import com.reandroid.dex.common.AnnotationVisibility;
import com.reandroid.dex.data.AnnotationElement;
import com.reandroid.dex.data.AnnotationItem;
import com.reandroid.dex.key.Key;
import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.utils.collection.ComputeIterator;
import java.io.IOException;
import java.util.Iterator;
import java.util.function.Function;

/* JADX INFO: loaded from: classes11.dex */
public class DexAnnotation extends Dex implements Iterable<DexAnnotationElement> {
    private final AnnotationItem annotationItem;
    private final Dex declaring;

    public DexAnnotation(Dex dex, AnnotationItem annotationItem) {
        this.declaring = dex;
        this.annotationItem = annotationItem;
    }

    public TypeKey getType() {
        return getAnnotationItem().getTypeKey();
    }

    public void setType(TypeKey typeKey) {
        getAnnotationItem().setType(typeKey);
    }

    @Override // com.reandroid.dex.model.Dex
    public boolean uses(Key key) {
        return getAnnotationItem().uses(key);
    }

    public boolean contains(String str) {
        return getAnnotationItem().containsName(str);
    }

    public DexAnnotationElement get(String str) {
        return DexAnnotationElement.create(this, getAnnotationItem().getElement(str));
    }

    public DexAnnotationElement get(int i) {
        return DexAnnotationElement.create(this, getAnnotationItem().getElement(i));
    }

    public int size() {
        return getAnnotationItem().getElementsCount();
    }

    @Override // java.lang.Iterable
    public Iterator<DexAnnotationElement> iterator() {
        return ComputeIterator.of(getAnnotationItem().iterator(), new Function() { // from class: com.reandroid.dex.model.DexAnnotation$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m2761lambda$iterator$0$comreandroiddexmodelDexAnnotation((AnnotationElement) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$iterator$0$com-reandroid-dex-model-DexAnnotation, reason: not valid java name */
    /* synthetic */ DexAnnotationElement m2761lambda$iterator$0$comreandroiddexmodelDexAnnotation(AnnotationElement annotationElement) {
        return DexAnnotationElement.create(this, annotationElement);
    }

    public Iterator<DexAnnotationElement> clonedIterator() {
        return ComputeIterator.of(getAnnotationItem().clonedIterator(), new Function() { // from class: com.reandroid.dex.model.DexAnnotation$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m2760lambda$clonedIterator$1$comreandroiddexmodelDexAnnotation((AnnotationElement) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$clonedIterator$1$com-reandroid-dex-model-DexAnnotation, reason: not valid java name */
    /* synthetic */ DexAnnotationElement m2760lambda$clonedIterator$1$comreandroiddexmodelDexAnnotation(AnnotationElement annotationElement) {
        return DexAnnotationElement.create(this, annotationElement);
    }

    public AnnotationVisibility getVisibility() {
        return getAnnotationItem().getVisibility();
    }

    public void setVisibility(AnnotationVisibility annotationVisibility) {
        getAnnotationItem().setVisibility(annotationVisibility);
    }

    public DexAnnotationElement getOrCreate(String str) {
        return DexAnnotationElement.create(this, getAnnotationItem().getOrCreateElement(str));
    }

    @Override // com.reandroid.dex.model.Dex
    public void removeSelf() {
        getAnnotationItem().removeSelf();
    }

    public AnnotationItem getAnnotationItem() {
        return this.annotationItem;
    }

    public Dex getDeclaring() {
        return this.declaring;
    }

    @Override // com.reandroid.dex.model.Dex
    public DexClassRepository getClassRepository() {
        return getDeclaring().getClassRepository();
    }

    @Override // com.reandroid.dex.model.Dex, com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        getAnnotationItem().append(smaliWriter);
    }

    public static DexAnnotation create(Dex dex, AnnotationItem annotationItem) {
        if (dex == null || annotationItem == null) {
            return null;
        }
        return new DexAnnotation(dex, annotationItem);
    }
}
