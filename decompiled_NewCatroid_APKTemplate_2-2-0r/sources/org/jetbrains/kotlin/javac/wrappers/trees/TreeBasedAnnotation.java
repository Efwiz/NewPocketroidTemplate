package org.jetbrains.kotlin.javac.wrappers.trees;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.tools.javac.tree.JCTree;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt;
import org.jetbrains.kotlin.javac.JavaClassWithClassId;
import org.jetbrains.kotlin.javac.JavacWrapper;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotationArgument;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaClassifier;
import org.jetbrains.kotlin.load.java.structure.JavaElement;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: TreeBasedAnnotation.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B%\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\u0001¢\u0006\u0002\u0010\nJ\n\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0016R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\u00138VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\t\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001b¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/javac/wrappers/trees/TreeBasedAnnotation;", "Lorg/jetbrains/kotlin/load/java/structure/JavaElement;", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotation;", "annotation", "Lcom/sun/tools/javac/tree/JCTree$JCAnnotation;", "compilationUnit", "Lcom/sun/source/tree/CompilationUnitTree;", "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "onElement", "(Lcom/sun/tools/javac/tree/JCTree$JCAnnotation;Lcom/sun/source/tree/CompilationUnitTree;Lorg/jetbrains/kotlin/javac/JavacWrapper;Lorg/jetbrains/kotlin/load/java/structure/JavaElement;)V", "getAnnotation", "()Lcom/sun/tools/javac/tree/JCTree$JCAnnotation;", "arguments", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotationArgument;", "getArguments", "()Ljava/util/Collection;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "getClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "getCompilationUnit", "()Lcom/sun/source/tree/CompilationUnitTree;", "getJavac", "()Lorg/jetbrains/kotlin/javac/JavacWrapper;", "getOnElement", "()Lorg/jetbrains/kotlin/load/java/structure/JavaElement;", "resolve", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TreeBasedAnnotation implements JavaAnnotation, JavaElement {
    private final JCTree.JCAnnotation annotation;
    private final CompilationUnitTree compilationUnit;
    private final JavacWrapper javac;
    private final JavaElement onElement;

    public TreeBasedAnnotation(JCTree.JCAnnotation annotation, CompilationUnitTree compilationUnit, JavacWrapper javac, JavaElement onElement) {
        Intrinsics.checkNotNullParameter(annotation, "annotation");
        Intrinsics.checkNotNullParameter(compilationUnit, "compilationUnit");
        Intrinsics.checkNotNullParameter(javac, "javac");
        Intrinsics.checkNotNullParameter(onElement, "onElement");
        this.annotation = annotation;
        this.compilationUnit = compilationUnit;
        this.javac = javac;
        this.onElement = onElement;
    }

    public final JCTree.JCAnnotation getAnnotation() {
        return this.annotation;
    }

    public final CompilationUnitTree getCompilationUnit() {
        return this.compilationUnit;
    }

    public final JavacWrapper getJavac() {
        return this.javac;
    }

    public final JavaElement getOnElement() {
        return this.onElement;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotation
    public Collection<JavaAnnotationArgument> getArguments() {
        return TreeBasedAnnotationKt.createAnnotationArguments(this, this.javac, this.onElement);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotation
    public ClassId getClassId() {
        ClassId classId;
        JavaClass javaClassResolve = resolve();
        JavaClassWithClassId javaClassWithClassId = javaClassResolve instanceof JavaClassWithClassId ? (JavaClassWithClassId) javaClassResolve : null;
        if (javaClassWithClassId != null && (classId = javaClassWithClassId.getClassId()) != null) {
            return classId;
        }
        String string = this.annotation.annotationType.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        ClassId classId2 = ClassId.topLevel(new FqName(StringsKt.substringAfter$default(string, PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT, (String) null, 2, (Object) null)));
        Intrinsics.checkNotNullExpressionValue(classId2, "topLevel(...)");
        return classId2;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotation
    public JavaClass resolve() {
        JavacWrapper javacWrapper = this.javac;
        JCTree annotationType = this.annotation.annotationType;
        Intrinsics.checkNotNullExpressionValue(annotationType, "annotationType");
        JavaClassifier javaClassifierResolve = javacWrapper.resolve(annotationType, this.compilationUnit, this.onElement);
        if (javaClassifierResolve instanceof JavaClass) {
            return (JavaClass) javaClassifierResolve;
        }
        return null;
    }
}
