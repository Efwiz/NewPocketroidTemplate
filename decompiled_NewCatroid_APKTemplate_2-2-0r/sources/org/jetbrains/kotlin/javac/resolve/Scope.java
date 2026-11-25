package org.jetbrains.kotlin.javac.resolve;

import com.sun.source.tree.CompilationUnitTree;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.javac.JavacWrapper;
import org.jetbrains.kotlin.load.java.structure.JavaClassifier;

/* JADX INFO: compiled from: ClassifierResolver.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0000\b\"\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J \u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0014\u001a\u00020\u00152\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00150\u0017H&R\u0014\u0010\u0004\u001a\u00020\u0005X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\nX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\u0002\u001a\u00020\u0003X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u0004\u0018\u00010\u0000X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/javac/resolve/Scope;", "", "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "compilationUnit", "Lcom/sun/source/tree/CompilationUnitTree;", "(Lorg/jetbrains/kotlin/javac/JavacWrapper;Lcom/sun/source/tree/CompilationUnitTree;)V", "getCompilationUnit", "()Lcom/sun/source/tree/CompilationUnitTree;", "helper", "Lorg/jetbrains/kotlin/javac/resolve/ResolveHelper;", "getHelper", "()Lorg/jetbrains/kotlin/javac/resolve/ResolveHelper;", "getJavac", "()Lorg/jetbrains/kotlin/javac/JavacWrapper;", "parent", "getParent", "()Lorg/jetbrains/kotlin/javac/resolve/Scope;", "findClass", "Lorg/jetbrains/kotlin/load/java/structure/JavaClassifier;", "name", "", "pathSegments", "", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
abstract class Scope {
    private final CompilationUnitTree compilationUnit;
    private final ResolveHelper helper;
    private final JavacWrapper javac;

    public abstract JavaClassifier findClass(String name2, List<String> pathSegments);

    public abstract Scope getParent();

    public Scope(JavacWrapper javac, CompilationUnitTree compilationUnit) {
        Intrinsics.checkNotNullParameter(javac, "javac");
        Intrinsics.checkNotNullParameter(compilationUnit, "compilationUnit");
        this.javac = javac;
        this.compilationUnit = compilationUnit;
        this.helper = new ResolveHelper(javac, compilationUnit);
    }

    protected final JavacWrapper getJavac() {
        return this.javac;
    }

    protected final CompilationUnitTree getCompilationUnit() {
        return this.compilationUnit;
    }

    protected final ResolveHelper getHelper() {
        return this.helper;
    }
}
