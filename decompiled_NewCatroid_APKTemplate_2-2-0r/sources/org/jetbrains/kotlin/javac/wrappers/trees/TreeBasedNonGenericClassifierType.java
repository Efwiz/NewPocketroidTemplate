package org.jetbrains.kotlin.javac.wrappers.trees;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.tools.javac.tree.JCTree;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.javac.JavacWrapper;
import org.jetbrains.kotlin.javac.resolve.MockKotlinClassifier;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaClassifier;
import org.jetbrains.kotlin.load.java.structure.JavaElement;
import org.jetbrains.kotlin.load.java.structure.JavaTypeParameter;

/* JADX INFO: compiled from: treeBasedTypes.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B3\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u0012\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rR\u0014\u0010\u000e\u001a\u00020\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u0010¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/javac/wrappers/trees/TreeBasedNonGenericClassifierType;", "Lorg/jetbrains/kotlin/javac/wrappers/trees/TreeBasedClassifierType;", "Lcom/sun/tools/javac/tree/JCTree$JCExpression;", Constants.TYPE_TREE, "compilationUnit", "Lcom/sun/source/tree/CompilationUnitTree;", "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "annotations", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotation;", "containingElement", "Lorg/jetbrains/kotlin/load/java/structure/JavaElement;", "(Lcom/sun/tools/javac/tree/JCTree$JCExpression;Lcom/sun/source/tree/CompilationUnitTree;Lorg/jetbrains/kotlin/javac/JavacWrapper;Ljava/util/Collection;Lorg/jetbrains/kotlin/load/java/structure/JavaElement;)V", "isRaw", "", "()Z", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TreeBasedNonGenericClassifierType extends TreeBasedClassifierType<JCTree.JCExpression> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TreeBasedNonGenericClassifierType(JCTree.JCExpression tree, CompilationUnitTree compilationUnit, JavacWrapper javac, Collection<? extends JavaAnnotation> annotations, JavaElement containingElement) {
        super((JCTree) tree, compilationUnit, javac, annotations, containingElement, null);
        Intrinsics.checkNotNullParameter(tree, "tree");
        Intrinsics.checkNotNullParameter(compilationUnit, "compilationUnit");
        Intrinsics.checkNotNullParameter(javac, "javac");
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        Intrinsics.checkNotNullParameter(containingElement, "containingElement");
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClassifierType
    /* JADX INFO: renamed from: isRaw */
    public boolean mo7271isRaw() {
        List<JavaTypeParameter> typeParameters;
        JavaClassifier classifier = getClassifier();
        MockKotlinClassifier mockKotlinClassifier = classifier instanceof MockKotlinClassifier ? (MockKotlinClassifier) classifier : null;
        if (mockKotlinClassifier != null) {
            return mockKotlinClassifier.getHasTypeParameters();
        }
        JavaClassifier classifier2 = getClassifier();
        JavaClass javaClass = classifier2 instanceof JavaClass ? (JavaClass) classifier2 : null;
        return (javaClass == null || (typeParameters = javaClass.mo7258getTypeParameters()) == null || typeParameters.isEmpty()) ? false : true;
    }
}
