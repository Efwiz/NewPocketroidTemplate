package org.jetbrains.kotlin.javac.wrappers.trees;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.kotlin.javac.JavacWrapper;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotationArgument;
import org.jetbrains.kotlin.load.java.structure.JavaElement;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: TreeBasedAnnotation.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u00012\u00020\u0002B\u0017\b\u0004\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u0082\u0001\u0005\f\r\u000e\u000f\u0010¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/javac/wrappers/trees/TreeBasedAnnotationArgument;", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotationArgument;", "Lorg/jetbrains/kotlin/load/java/structure/JavaElement;", "name", "Lorg/jetbrains/kotlin/name/Name;", "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "(Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/javac/JavacWrapper;)V", "getJavac", "()Lorg/jetbrains/kotlin/javac/JavacWrapper;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "Lorg/jetbrains/kotlin/javac/wrappers/trees/TreeBasedAnnotationAsAnnotationArgument;", "Lorg/jetbrains/kotlin/javac/wrappers/trees/TreeBasedArrayAnnotationArgument;", "Lorg/jetbrains/kotlin/javac/wrappers/trees/TreeBasedJavaClassObjectAnnotationArgument;", "Lorg/jetbrains/kotlin/javac/wrappers/trees/TreeBasedLiteralAnnotationArgument;", "Lorg/jetbrains/kotlin/javac/wrappers/trees/TreeBasedReferenceAnnotationArgument;", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class TreeBasedAnnotationArgument implements JavaAnnotationArgument, JavaElement {
    private final JavacWrapper javac;
    private final Name name;

    public /* synthetic */ TreeBasedAnnotationArgument(Name name2, JavacWrapper javacWrapper, DefaultConstructorMarker defaultConstructorMarker) {
        this(name2, javacWrapper);
    }

    private TreeBasedAnnotationArgument(Name name2, JavacWrapper javacWrapper) {
        this.name = name2;
        this.javac = javacWrapper;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationArgument
    public Name getName() {
        return this.name;
    }

    public final JavacWrapper getJavac() {
        return this.javac;
    }
}
