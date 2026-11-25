package org.jetbrains.kotlin.javac.wrappers.trees;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.tools.javac.tree.JCTree;
import java.util.Collection;
import javax.lang.model.type.TypeKind;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.javac.JavacWrapper;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.java.structure.JavaElement;
import org.jetbrains.kotlin.load.java.structure.JavaPrimitiveType;
import org.jetbrains.kotlin.resolve.jvm.JvmPrimitiveType;

/* JADX INFO: compiled from: treeBasedTypes.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B3\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u0012\u0006\u0010\f\u001a\u00020\r¢\u0006\u0002\u0010\u000eR\u0016\u0010\u000f\u001a\u0004\u0018\u00010\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/javac/wrappers/trees/TreeBasedPrimitiveType;", "Lorg/jetbrains/kotlin/javac/wrappers/trees/TreeBasedType;", "Lcom/sun/tools/javac/tree/JCTree$JCPrimitiveTypeTree;", "Lorg/jetbrains/kotlin/load/java/structure/JavaPrimitiveType;", Constants.TYPE_TREE, "compilationUnit", "Lcom/sun/source/tree/CompilationUnitTree;", "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "allAnnotations", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotation;", "containingElement", "Lorg/jetbrains/kotlin/load/java/structure/JavaElement;", "(Lcom/sun/tools/javac/tree/JCTree$JCPrimitiveTypeTree;Lcom/sun/source/tree/CompilationUnitTree;Lorg/jetbrains/kotlin/javac/JavacWrapper;Ljava/util/Collection;Lorg/jetbrains/kotlin/load/java/structure/JavaElement;)V", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/builtins/PrimitiveType;", "getType", "()Lorg/jetbrains/kotlin/builtins/PrimitiveType;", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TreeBasedPrimitiveType extends TreeBasedType<JCTree.JCPrimitiveTypeTree> implements JavaPrimitiveType {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TreeBasedPrimitiveType(JCTree.JCPrimitiveTypeTree tree, CompilationUnitTree compilationUnit, JavacWrapper javac, Collection<? extends JavaAnnotation> allAnnotations, JavaElement containingElement) {
        super((JCTree) tree, compilationUnit, javac, allAnnotations, containingElement);
        Intrinsics.checkNotNullParameter(tree, "tree");
        Intrinsics.checkNotNullParameter(compilationUnit, "compilationUnit");
        Intrinsics.checkNotNullParameter(javac, "javac");
        Intrinsics.checkNotNullParameter(allAnnotations, "allAnnotations");
        Intrinsics.checkNotNullParameter(containingElement, "containingElement");
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaPrimitiveType
    public PrimitiveType getType() {
        if (getTree().getPrimitiveTypeKind() == TypeKind.VOID) {
            return null;
        }
        return JvmPrimitiveType.get(getTree().toString()).getPrimitiveType();
    }
}
