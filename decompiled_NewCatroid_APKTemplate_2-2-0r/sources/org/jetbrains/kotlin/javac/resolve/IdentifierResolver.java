package org.jetbrains.kotlin.javac.resolve;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.Tree;
import com.sun.tools.javac.tree.JCTree;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.javac.JavacWrapper;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaClassifier;
import org.jetbrains.kotlin.load.java.structure.JavaField;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: IdentifierResolver.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/javac/resolve/IdentifierResolver;", "", "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "(Lorg/jetbrains/kotlin/javac/JavacWrapper;)V", "resolve", "Lorg/jetbrains/kotlin/load/java/structure/JavaField;", Constants.TYPE_TREE, "Lcom/sun/source/tree/Tree;", "compilationUnit", "Lcom/sun/source/tree/CompilationUnitTree;", "containingClass", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IdentifierResolver {
    private final JavacWrapper javac;

    public IdentifierResolver(JavacWrapper javac) {
        Intrinsics.checkNotNullParameter(javac, "javac");
        this.javac = javac;
    }

    public final JavaField resolve(Tree tree, CompilationUnitTree compilationUnit, JavaClass containingClass) {
        Intrinsics.checkNotNullParameter(tree, "tree");
        Intrinsics.checkNotNullParameter(compilationUnit, "compilationUnit");
        Intrinsics.checkNotNullParameter(containingClass, "containingClass");
        if (tree instanceof JCTree.JCIdent) {
            Name nameIdentifier = Name.identifier(((JCTree.JCIdent) tree).name.toString());
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
            return new CurrentClassAndInnerFieldScope(this.javac, compilationUnit, null, 4, null).findField(containingClass, nameIdentifier);
        }
        if (!(tree instanceof JCTree.JCFieldAccess)) {
            return null;
        }
        JavacWrapper javacWrapper = this.javac;
        JCTree.JCFieldAccess jCFieldAccess = (JCTree.JCFieldAccess) tree;
        JCTree.JCExpression selected = jCFieldAccess.selected;
        Intrinsics.checkNotNullExpressionValue(selected, "selected");
        JavaClassifier javaClassifierResolve = javacWrapper.resolve((JCTree) selected, compilationUnit, containingClass);
        JavaClass javaClass = javaClassifierResolve instanceof JavaClass ? (JavaClass) javaClassifierResolve : null;
        if (javaClass == null) {
            return null;
        }
        if (javaClass instanceof MockKotlinClassifier) {
            String string = jCFieldAccess.name.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            return ((MockKotlinClassifier) javaClass).findField(string);
        }
        Name nameIdentifier2 = Name.identifier(jCFieldAccess.name.toString());
        Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
        return new CurrentClassAndInnerFieldScope(this.javac, compilationUnit, null).findField(javaClass, nameIdentifier2);
    }
}
