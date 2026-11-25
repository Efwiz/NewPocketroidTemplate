package org.jetbrains.kotlin.javac.wrappers.trees;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.tools.javac.code.BoundKind;
import com.sun.tools.javac.tree.JCTree;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.javac.JavacWrapper;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.java.structure.JavaElement;
import org.jetbrains.kotlin.load.java.structure.JavaType;
import org.jetbrains.kotlin.load.java.structure.JavaWildcardType;

/* JADX INFO: compiled from: treeBasedTypes.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B3\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u0012\u0006\u0010\f\u001a\u00020\r¢\u0006\u0002\u0010\u000eR\u0016\u0010\u000f\u001a\u0004\u0018\u00010\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0013\u001a\u00020\u00148VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0015¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/javac/wrappers/trees/TreeBasedWildcardType;", "Lorg/jetbrains/kotlin/javac/wrappers/trees/TreeBasedType;", "Lcom/sun/tools/javac/tree/JCTree$JCWildcard;", "Lorg/jetbrains/kotlin/load/java/structure/JavaWildcardType;", Constants.TYPE_TREE, "compilationUnit", "Lcom/sun/source/tree/CompilationUnitTree;", "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "allAnnotations", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotation;", "containingElement", "Lorg/jetbrains/kotlin/load/java/structure/JavaElement;", "(Lcom/sun/tools/javac/tree/JCTree$JCWildcard;Lcom/sun/source/tree/CompilationUnitTree;Lorg/jetbrains/kotlin/javac/JavacWrapper;Ljava/util/Collection;Lorg/jetbrains/kotlin/load/java/structure/JavaElement;)V", "bound", "Lorg/jetbrains/kotlin/load/java/structure/JavaType;", "getBound", "()Lorg/jetbrains/kotlin/load/java/structure/JavaType;", "isExtends", "", "()Z", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TreeBasedWildcardType extends TreeBasedType<JCTree.JCWildcard> implements JavaWildcardType {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TreeBasedWildcardType(JCTree.JCWildcard tree, CompilationUnitTree compilationUnit, JavacWrapper javac, Collection<? extends JavaAnnotation> allAnnotations, JavaElement containingElement) {
        super((JCTree) tree, compilationUnit, javac, allAnnotations, containingElement);
        Intrinsics.checkNotNullParameter(tree, "tree");
        Intrinsics.checkNotNullParameter(compilationUnit, "compilationUnit");
        Intrinsics.checkNotNullParameter(javac, "javac");
        Intrinsics.checkNotNullParameter(allAnnotations, "allAnnotations");
        Intrinsics.checkNotNullParameter(containingElement, "containingElement");
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaWildcardType
    public JavaType getBound() {
        JCTree bound = getTree().getBound();
        if (bound != null) {
            return TreeBasedType.INSTANCE.create(bound, getCompilationUnit(), getJavac(), mo7252getAnnotations(), getContainingElement());
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaWildcardType
    /* JADX INFO: renamed from: isExtends */
    public boolean getIsExtends() {
        return getTree().kind.kind == BoundKind.EXTENDS;
    }
}
