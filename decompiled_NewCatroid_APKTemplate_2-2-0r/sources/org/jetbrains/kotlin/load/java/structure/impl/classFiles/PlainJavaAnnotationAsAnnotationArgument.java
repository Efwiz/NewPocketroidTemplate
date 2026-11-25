package org.jetbrains.kotlin.load.java.structure.impl.classFiles;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotationAsAnnotationArgument;

/* JADX INFO: compiled from: Annotations.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\u00020\u0002B\u0017\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\b\u0010\b\u001a\u00020\u0006H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/PlainJavaAnnotationAsAnnotationArgument;", "Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/PlainJavaAnnotationArgument;", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotationAsAnnotationArgument;", "name", "", "annotation", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotation;", "(Ljava/lang/String;Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotation;)V", "getAnnotation", "resolution.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PlainJavaAnnotationAsAnnotationArgument extends PlainJavaAnnotationArgument implements JavaAnnotationAsAnnotationArgument {
    private final JavaAnnotation annotation;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PlainJavaAnnotationAsAnnotationArgument(String str, JavaAnnotation annotation) {
        super(str, null);
        Intrinsics.checkNotNullParameter(annotation, "annotation");
        this.annotation = annotation;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationAsAnnotationArgument
    public JavaAnnotation getAnnotation() {
        return this.annotation;
    }
}
