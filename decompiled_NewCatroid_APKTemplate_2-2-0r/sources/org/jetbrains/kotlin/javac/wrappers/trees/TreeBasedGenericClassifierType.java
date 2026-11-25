package org.jetbrains.kotlin.javac.wrappers.trees;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.tools.javac.tree.JCTree;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.javac.JavacWrapper;
import org.jetbrains.kotlin.javac.resolve.MockKotlinClassifier;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaClassifier;
import org.jetbrains.kotlin.load.java.structure.JavaElement;
import org.jetbrains.kotlin.load.java.structure.JavaType;
import org.jetbrains.kotlin.load.java.structure.JavaTypeParameter;

/* JADX INFO: compiled from: treeBasedTypes.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B3\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u0012\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rR\u001a\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u001d\u0010\u0010\u001a\u0004\u0018\u00010\u00118VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0016\u001a\u00020\u00178VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0018R\u001c\u0010\u0019\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u001b0\u001a8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001d¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/javac/wrappers/trees/TreeBasedGenericClassifierType;", "Lorg/jetbrains/kotlin/javac/wrappers/trees/TreeBasedClassifierType;", "Lcom/sun/tools/javac/tree/JCTree$JCTypeApply;", Constants.TYPE_TREE, "compilationUnit", "Lcom/sun/source/tree/CompilationUnitTree;", "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "annotations", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotation;", "containingElement", "Lorg/jetbrains/kotlin/load/java/structure/JavaElement;", "(Lcom/sun/tools/javac/tree/JCTree$JCTypeApply;Lcom/sun/source/tree/CompilationUnitTree;Lorg/jetbrains/kotlin/javac/JavacWrapper;Ljava/util/Collection;Lorg/jetbrains/kotlin/load/java/structure/JavaElement;)V", "getAnnotations", "()Ljava/util/Collection;", "classifier", "Lorg/jetbrains/kotlin/load/java/structure/JavaClassifier;", "getClassifier", "()Lorg/jetbrains/kotlin/load/java/structure/JavaClassifier;", "classifier$delegate", "Lkotlin/Lazy;", "isRaw", "", "()Z", "typeArguments", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaType;", "getTypeArguments", "()Ljava/util/List;", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TreeBasedGenericClassifierType extends TreeBasedClassifierType<JCTree.JCTypeApply> {

    /* JADX INFO: renamed from: classifier$delegate, reason: from kotlin metadata */
    private final Lazy classifier;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TreeBasedGenericClassifierType(final JCTree.JCTypeApply tree, final CompilationUnitTree compilationUnit, final JavacWrapper javac, Collection<? extends JavaAnnotation> annotations, final JavaElement containingElement) {
        super((JCTree) tree, compilationUnit, javac, annotations, containingElement, null);
        Intrinsics.checkNotNullParameter(tree, "tree");
        Intrinsics.checkNotNullParameter(compilationUnit, "compilationUnit");
        Intrinsics.checkNotNullParameter(javac, "javac");
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        Intrinsics.checkNotNullParameter(containingElement, "containingElement");
        this.classifier = LazyKt.lazy(new Function0<JavaClassifier>() { // from class: org.jetbrains.kotlin.javac.wrappers.trees.TreeBasedGenericClassifierType$classifier$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final JavaClassifier invoke() {
                JCTree.JCAnnotatedType jCAnnotatedType = tree.clazz;
                if (!(jCAnnotatedType instanceof JCTree.JCAnnotatedType)) {
                    return super/*org.jetbrains.kotlin.javac.wrappers.trees.TreeBasedClassifierType*/.getClassifier();
                }
                JavacWrapper javacWrapper = javac;
                JCTree.JCExpression underlyingType = jCAnnotatedType.underlyingType;
                Intrinsics.checkNotNullExpressionValue(underlyingType, "underlyingType");
                return javacWrapper.resolve((JCTree) underlyingType, compilationUnit, containingElement);
            }
        });
    }

    @Override // org.jetbrains.kotlin.javac.wrappers.trees.TreeBasedClassifierType, org.jetbrains.kotlin.load.java.structure.JavaClassifierType
    public JavaClassifier getClassifier() {
        return (JavaClassifier) this.classifier.getValue();
    }

    @Override // org.jetbrains.kotlin.javac.wrappers.trees.TreeBasedType, org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: getAnnotations */
    public Collection<JavaAnnotation> mo7252getAnnotations() {
        Iterable iterable;
        JCTree.JCAnnotatedType jCAnnotatedType = ((JCTree.JCTypeApply) getTree()).clazz;
        JCTree.JCAnnotatedType jCAnnotatedType2 = jCAnnotatedType instanceof JCTree.JCAnnotatedType ? jCAnnotatedType : null;
        if (jCAnnotatedType2 != null && (iterable = jCAnnotatedType2.annotations) != null) {
            Iterable<JCTree.JCAnnotation> iterable2 = iterable;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterable2, 10));
            for (JCTree.JCAnnotation jCAnnotation : iterable2) {
                Intrinsics.checkNotNull(jCAnnotation);
                arrayList.add(new TreeBasedAnnotation(jCAnnotation, getCompilationUnit(), getJavac(), getContainingElement()));
            }
            List mutableList = CollectionsKt.toMutableList((Collection) arrayList);
            if (mutableList != null) {
                mutableList.addAll(super.mo7252getAnnotations());
                return mutableList;
            }
        }
        return super.mo7252getAnnotations();
    }

    @Override // org.jetbrains.kotlin.javac.wrappers.trees.TreeBasedClassifierType, org.jetbrains.kotlin.load.java.structure.JavaClassifierType
    /* JADX INFO: renamed from: getTypeArguments */
    public List<JavaType> mo7270getTypeArguments() {
        Iterable arguments = ((JCTree.JCTypeApply) getTree()).arguments;
        Intrinsics.checkNotNullExpressionValue(arguments, "arguments");
        Iterable<JCTree> iterable = arguments;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterable, 10));
        for (JCTree jCTree : iterable) {
            TreeBasedType.Companion companion = TreeBasedType.INSTANCE;
            Intrinsics.checkNotNull(jCTree);
            arrayList.add(companion.create(jCTree, getCompilationUnit(), getJavac(), CollectionsKt.emptyList(), getContainingElement()));
        }
        List<JavaType> mutableList = CollectionsKt.toMutableList((Collection) arrayList);
        mutableList.addAll(super.mo7270getTypeArguments());
        return mutableList;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClassifierType
    /* JADX INFO: renamed from: isRaw */
    public boolean mo7271isRaw() {
        List<JavaTypeParameter> typeParameters;
        JavaClassifier classifier = getClassifier();
        if (!(classifier instanceof MockKotlinClassifier)) {
            JavaClassifier classifier2 = getClassifier();
            JavaClass javaClass = classifier2 instanceof JavaClass ? (JavaClass) classifier2 : null;
            if (javaClass == null || (typeParameters = javaClass.mo7258getTypeParameters()) == null || ((JCTree.JCTypeApply) getTree()).arguments.size() != typeParameters.size()) {
                return true;
            }
        } else if (((JCTree.JCTypeApply) getTree()).arguments.size() != ((MockKotlinClassifier) classifier).getTypeParametersNumber()) {
            return true;
        }
        return false;
    }
}
