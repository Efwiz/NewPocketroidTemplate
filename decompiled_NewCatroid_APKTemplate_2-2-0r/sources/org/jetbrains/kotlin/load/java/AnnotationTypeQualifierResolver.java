package org.jetbrains.kotlin.load.java;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.constants.ArrayValue;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.EnumValue;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;

/* JADX INFO: compiled from: AnnotationTypeQualifierResolver.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u001c\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u001a\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u000f*\u00020\u00022\u0006\u0010\u0014\u001a\u00020\u0015H\u0014J\u0016\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00130\u0017*\u0006\u0012\u0002\b\u00030\u0018H\u0002R\u001a\u0010\u0006\u001a\u0004\u0018\u00010\u0007*\u00020\u00028TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0018\u0010\n\u001a\u00020\u000b*\u00020\u00028TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u001e\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00020\u000f*\u00020\u00028TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/load/java/AnnotationTypeQualifierResolver;", "Lorg/jetbrains/kotlin/load/java/AbstractAnnotationTypeQualifierResolver;", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "javaTypeEnhancementState", "Lorg/jetbrains/kotlin/load/java/JavaTypeEnhancementState;", "(Lorg/jetbrains/kotlin/load/java/JavaTypeEnhancementState;)V", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "getFqName", "(Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;)Lorg/jetbrains/kotlin/name/FqName;", ASTExpr.DEFAULT_MAP_KEY_NAME, "", "getKey", "(Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;)Ljava/lang/Object;", "metaAnnotations", "", "getMetaAnnotations", "(Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;)Ljava/lang/Iterable;", "enumArguments", "", "onlyValue", "", "toEnumNames", "", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AnnotationTypeQualifierResolver extends AbstractAnnotationTypeQualifierResolver<AnnotationDescriptor> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AnnotationTypeQualifierResolver(JavaTypeEnhancementState javaTypeEnhancementState) {
        super(javaTypeEnhancementState);
        Intrinsics.checkNotNullParameter(javaTypeEnhancementState, "javaTypeEnhancementState");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.load.java.AbstractAnnotationTypeQualifierResolver
    public Iterable<AnnotationDescriptor> getMetaAnnotations(AnnotationDescriptor annotationDescriptor) {
        Iterable<AnnotationDescriptor> iterableEmptyList;
        Intrinsics.checkNotNullParameter(annotationDescriptor, "<this>");
        ClassDescriptor annotationClass = DescriptorUtilsKt.getAnnotationClass(annotationDescriptor);
        if (annotationClass == null || (iterableEmptyList = annotationClass.getAnnotations()) == null) {
            iterableEmptyList = CollectionsKt.emptyList();
        }
        return iterableEmptyList;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.load.java.AbstractAnnotationTypeQualifierResolver
    public Object getKey(AnnotationDescriptor annotationDescriptor) {
        Intrinsics.checkNotNullParameter(annotationDescriptor, "<this>");
        ClassDescriptor annotationClass = DescriptorUtilsKt.getAnnotationClass(annotationDescriptor);
        Intrinsics.checkNotNull(annotationClass);
        return annotationClass;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.load.java.AbstractAnnotationTypeQualifierResolver
    public FqName getFqName(AnnotationDescriptor annotationDescriptor) {
        Intrinsics.checkNotNullParameter(annotationDescriptor, "<this>");
        return annotationDescriptor.getFqName();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.load.java.AbstractAnnotationTypeQualifierResolver
    public Iterable<String> enumArguments(AnnotationDescriptor annotationDescriptor, boolean z) {
        List<String> enumNames;
        Intrinsics.checkNotNullParameter(annotationDescriptor, "<this>");
        Map<Name, ConstantValue<?>> allValueArguments = annotationDescriptor.getAllValueArguments();
        ArrayList arrayList = new ArrayList();
        for (Map.Entry<Name, ConstantValue<?>> entry : allValueArguments.entrySet()) {
            Name key = entry.getKey();
            ConstantValue<?> value = entry.getValue();
            if (!z || Intrinsics.areEqual(key, JvmAnnotationNames.DEFAULT_ANNOTATION_MEMBER_NAME)) {
                enumNames = toEnumNames(value);
            } else {
                enumNames = CollectionsKt.emptyList();
            }
            CollectionsKt.addAll(arrayList, enumNames);
        }
        return arrayList;
    }

    private final List<String> toEnumNames(ConstantValue<?> constantValue) {
        if (!(constantValue instanceof ArrayValue)) {
            return constantValue instanceof EnumValue ? CollectionsKt.listOf(((EnumValue) constantValue).getEnumEntryName().getIdentifier()) : CollectionsKt.emptyList();
        }
        List<? extends ConstantValue<?>> value = ((ArrayValue) constantValue).getValue();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = value.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, toEnumNames((ConstantValue) it.next()));
        }
        return arrayList;
    }
}
