package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.checker.SimpleClassicTypeSystemContext;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;

/* JADX INFO: compiled from: typeAnnotationCollector.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J\u0012\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00020\u0005*\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/codegen/PsiTypeAnnotationCollector;", "Lorg/jetbrains/kotlin/codegen/TypeAnnotationCollector;", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "()V", "extractAnnotations", "", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PsiTypeAnnotationCollector extends TypeAnnotationCollector<AnnotationDescriptor> {
    public PsiTypeAnnotationCollector() {
        super(SimpleClassicTypeSystemContext.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.codegen.TypeAnnotationCollector
    public List<AnnotationDescriptor> extractAnnotations(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        if (!(kotlinTypeMarker instanceof KotlinType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        Annotations annotations = ((KotlinType) kotlinTypeMarker).getAnnotations();
        ArrayList arrayList = new ArrayList();
        for (AnnotationDescriptor annotationDescriptor : annotations) {
            if (isCompiledToJvm8OrHigher(DescriptorUtilsKt.getAnnotationClass(annotationDescriptor))) {
                arrayList.add(annotationDescriptor);
            }
        }
        return arrayList;
    }
}
