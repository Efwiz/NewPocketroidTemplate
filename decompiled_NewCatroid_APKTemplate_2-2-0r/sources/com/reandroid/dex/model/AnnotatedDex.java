package com.reandroid.dex.model;

import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.value.DexValueType;
import java.lang.annotation.ElementType;
import java.util.Iterator;

/* JADX INFO: loaded from: classes11.dex */
public interface AnnotatedDex {
    DexAnnotation getAnnotation(TypeKey typeKey);

    Iterator<DexAnnotation> getAnnotations();

    Iterator<DexAnnotation> getAnnotations(TypeKey typeKey);

    ElementType getElementType();

    DexAnnotation getOrCreateAnnotation(TypeKey typeKey);

    DexAnnotation newAnnotation(TypeKey typeKey);

    default DexAnnotationElement getAnnotationElement(TypeKey typeKey, String str) {
        DexAnnotation annotation = getAnnotation(typeKey);
        if (annotation != null) {
            return annotation.get(str);
        }
        return null;
    }

    default DexAnnotationElement getOrCreateAnnotationElement(TypeKey typeKey, String str) {
        DexAnnotationElement annotationElement = getAnnotationElement(typeKey, str);
        return annotationElement != null ? annotationElement : getOrCreateAnnotation(typeKey).getOrCreate(str);
    }

    default DexValue getAnnotationValue(TypeKey typeKey, String str) {
        DexAnnotationElement annotationElement = getAnnotationElement(typeKey, str);
        if (annotationElement != null) {
            return annotationElement.getValue();
        }
        return null;
    }

    default DexValue getOrCreateAnnotationValue(TypeKey typeKey, String str, DexValueType<?> dexValueType) {
        return getOrCreateAnnotationElement(typeKey, str).getOrCreateValue(dexValueType);
    }
}
