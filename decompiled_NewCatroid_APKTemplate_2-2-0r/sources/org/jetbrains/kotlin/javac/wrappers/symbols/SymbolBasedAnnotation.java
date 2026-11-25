package org.jetbrains.kotlin.javac.wrappers.symbols;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.javac.JavacWrapper;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotationArgument;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaElement;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: SymbolBasedAnnotation.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\n\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0016R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedAnnotation;", "Lorg/jetbrains/kotlin/load/java/structure/JavaElement;", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotation;", "annotationMirror", "Ljavax/lang/model/element/AnnotationMirror;", "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "(Ljavax/lang/model/element/AnnotationMirror;Lorg/jetbrains/kotlin/javac/JavacWrapper;)V", "getAnnotationMirror", "()Ljavax/lang/model/element/AnnotationMirror;", "arguments", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotationArgument;", "getArguments", "()Ljava/util/Collection;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "getClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "getJavac", "()Lorg/jetbrains/kotlin/javac/JavacWrapper;", "resolve", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class SymbolBasedAnnotation implements JavaAnnotation, JavaElement {
    private final AnnotationMirror annotationMirror;
    private final JavacWrapper javac;

    public SymbolBasedAnnotation(AnnotationMirror annotationMirror, JavacWrapper javac) {
        Intrinsics.checkNotNullParameter(annotationMirror, "annotationMirror");
        Intrinsics.checkNotNullParameter(javac, "javac");
        this.annotationMirror = annotationMirror;
        this.javac = javac;
    }

    public final AnnotationMirror getAnnotationMirror() {
        return this.annotationMirror;
    }

    public final JavacWrapper getJavac() {
        return this.javac;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotation
    public Collection<JavaAnnotationArgument> getArguments() {
        Map elementValues = this.annotationMirror.getElementValues();
        Intrinsics.checkNotNullExpressionValue(elementValues, "getElementValues(...)");
        ArrayList arrayList = new ArrayList(elementValues.size());
        for (Map.Entry entry : elementValues.entrySet()) {
            ExecutableElement executableElement = (ExecutableElement) entry.getKey();
            AnnotationValue annotationValue = (AnnotationValue) entry.getValue();
            SymbolBasedAnnotationArgument.Companion companion = SymbolBasedAnnotationArgument.INSTANCE;
            Object value = annotationValue.getValue();
            Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
            Name nameIdentifier = Name.identifier(executableElement.getSimpleName().toString());
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
            arrayList.add(companion.create(value, nameIdentifier, this.javac));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotation
    public ClassId getClassId() {
        TypeElement typeElementAsElement = this.annotationMirror.getAnnotationType().asElement();
        Intrinsics.checkNotNull(typeElementAsElement, "null cannot be cast to non-null type javax.lang.model.element.TypeElement");
        ClassId classIdComputeClassId = UtilsKt.computeClassId(typeElementAsElement);
        Intrinsics.checkNotNull(classIdComputeClassId);
        return classIdComputeClassId;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotation
    public JavaClass resolve() {
        Intrinsics.checkNotNull(this.annotationMirror.getAnnotationType().asElement(), "null cannot be cast to non-null type com.sun.tools.javac.code.Symbol.ClassSymbol");
        return JavacWrapper.findClass$default(this.javac, getClassId(), null, 2, null);
    }
}
