package org.jetbrains.kotlin.javac.wrappers.trees;

import androidx.exifinterface.media.ExifInterface;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.tools.javac.tree.JCTree;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.javac.JavacWrapper;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.structure.JavaElement;

/* JADX INFO: compiled from: TreeBasedElement.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b&\u0018\u0000*\n\b\u0000\u0010\u0001 \u0001*\u00020\u00022\u00020\u0003B\u001d\u0012\u0006\u0010\u0004\u001a\u00028\u0000\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0096\u0002J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\b\u0010\u0017\u001a\u00020\u0018H\u0016R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0013\u0010\u0004\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\u0010\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/javac/wrappers/trees/TreeBasedElement;", ExifInterface.GPS_DIRECTION_TRUE, "Lcom/sun/tools/javac/tree/JCTree;", "Lorg/jetbrains/kotlin/load/java/structure/JavaElement;", Constants.TYPE_TREE, "compilationUnit", "Lcom/sun/source/tree/CompilationUnitTree;", "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "(Lcom/sun/tools/javac/tree/JCTree;Lcom/sun/source/tree/CompilationUnitTree;Lorg/jetbrains/kotlin/javac/JavacWrapper;)V", "getCompilationUnit", "()Lcom/sun/source/tree/CompilationUnitTree;", "getJavac", "()Lorg/jetbrains/kotlin/javac/JavacWrapper;", "getTree", "()Lcom/sun/tools/javac/tree/JCTree;", "Lcom/sun/tools/javac/tree/JCTree;", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class TreeBasedElement<T extends JCTree> implements JavaElement {
    private final CompilationUnitTree compilationUnit;
    private final JavacWrapper javac;
    private final T tree;

    public TreeBasedElement(T tree, CompilationUnitTree compilationUnit, JavacWrapper javac) {
        Intrinsics.checkNotNullParameter(tree, "tree");
        Intrinsics.checkNotNullParameter(compilationUnit, "compilationUnit");
        Intrinsics.checkNotNullParameter(javac, "javac");
        this.tree = tree;
        this.compilationUnit = compilationUnit;
        this.javac = javac;
    }

    public final T getTree() {
        return this.tree;
    }

    public final CompilationUnitTree getCompilationUnit() {
        return this.compilationUnit;
    }

    public final JavacWrapper getJavac() {
        return this.javac;
    }

    public boolean equals(Object other) {
        TreeBasedElement treeBasedElement = other instanceof TreeBasedElement ? (TreeBasedElement) other : null;
        return Intrinsics.areEqual(treeBasedElement != null ? treeBasedElement.tree : null, this.tree);
    }

    public int hashCode() {
        return this.tree.hashCode();
    }

    public String toString() {
        String string = this.tree.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }
}
