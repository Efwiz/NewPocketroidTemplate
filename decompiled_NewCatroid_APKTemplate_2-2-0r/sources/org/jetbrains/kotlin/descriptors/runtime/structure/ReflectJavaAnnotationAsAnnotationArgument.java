package org.jetbrains.kotlin.descriptors.runtime.structure;

import java.lang.annotation.Annotation;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotationAsAnnotationArgument;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: ReflectJavaAnnotationArguments.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u0017\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\b\u0010\b\u001a\u00020\tH\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaAnnotationAsAnnotationArgument;", "Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaAnnotationArgument;", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotationAsAnnotationArgument;", "name", "Lorg/jetbrains/kotlin/name/Name;", "annotation", "", "(Lorg/jetbrains/kotlin/name/Name;Ljava/lang/annotation/Annotation;)V", "getAnnotation", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotation;", "descriptors.runtime"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReflectJavaAnnotationAsAnnotationArgument extends ReflectJavaAnnotationArgument implements JavaAnnotationAsAnnotationArgument {
    private final Annotation annotation;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ReflectJavaAnnotationAsAnnotationArgument(Name name2, Annotation annotation) {
        super(name2, null);
        Intrinsics.checkNotNullParameter(annotation, "annotation");
        this.annotation = annotation;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationAsAnnotationArgument
    public JavaAnnotation getAnnotation() {
        return new ReflectJavaAnnotation(this.annotation);
    }
}
