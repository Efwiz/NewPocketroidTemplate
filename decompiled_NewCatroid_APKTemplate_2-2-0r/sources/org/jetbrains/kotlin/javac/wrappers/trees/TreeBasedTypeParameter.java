package org.jetbrains.kotlin.javac.wrappers.trees;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.tools.javac.tree.JCTree;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.javac.JavacWrapper;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.java.structure.JavaClassifierType;
import org.jetbrains.kotlin.load.java.structure.JavaElement;
import org.jetbrains.kotlin.load.java.structure.JavaType;
import org.jetbrains.kotlin.load.java.structure.JavaTypeParameter;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: TreeBasedTypeParameter.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B%\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\u0013\u0010\u001e\u001a\u00020\u00142\b\u0010\u001f\u001a\u0004\u0018\u00010 H\u0096\u0002J\u0012\u0010!\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\"\u001a\u00020#H\u0016J\b\u0010$\u001a\u00020%H\u0016R!\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\u00020\u00148VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0015R\u0014\u0010\u0016\u001a\u00020\u00148VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0015R\u0014\u0010\u0017\u001a\u00020\u00188VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001aR\u001a\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u0010¨\u0006&"}, d2 = {"Lorg/jetbrains/kotlin/javac/wrappers/trees/TreeBasedTypeParameter;", "Lorg/jetbrains/kotlin/javac/wrappers/trees/TreeBasedElement;", "Lcom/sun/tools/javac/tree/JCTree$JCTypeParameter;", "Lorg/jetbrains/kotlin/load/java/structure/JavaTypeParameter;", Constants.TYPE_TREE, "compilationUnit", "Lcom/sun/source/tree/CompilationUnitTree;", "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "containingElement", "Lorg/jetbrains/kotlin/load/java/structure/JavaElement;", "(Lcom/sun/tools/javac/tree/JCTree$JCTypeParameter;Lcom/sun/source/tree/CompilationUnitTree;Lorg/jetbrains/kotlin/javac/JavacWrapper;Lorg/jetbrains/kotlin/load/java/structure/JavaElement;)V", "annotations", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotation;", "getAnnotations", "()Ljava/util/Collection;", "annotations$delegate", "Lkotlin/Lazy;", "isDeprecatedInJavaDoc", "", "()Z", "isFromSource", "name", "Lorg/jetbrains/kotlin/name/Name;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "upperBounds", "Lorg/jetbrains/kotlin/load/java/structure/JavaClassifierType;", "getUpperBounds", Namer.EQUALS_METHOD_NAME, "other", "", "findAnnotation", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "hashCode", "", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TreeBasedTypeParameter extends TreeBasedElement<JCTree.JCTypeParameter> implements JavaTypeParameter {

    /* JADX INFO: renamed from: annotations$delegate, reason: from kotlin metadata */
    private final Lazy annotations;
    private final JavaElement containingElement;

    @Override // org.jetbrains.kotlin.load.java.structure.JavaNamedElement
    public boolean isFromSource() {
        return true;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TreeBasedTypeParameter(final JCTree.JCTypeParameter tree, final CompilationUnitTree compilationUnit, final JavacWrapper javac, JavaElement containingElement) {
        super((JCTree) tree, compilationUnit, javac);
        Intrinsics.checkNotNullParameter(tree, "tree");
        Intrinsics.checkNotNullParameter(compilationUnit, "compilationUnit");
        Intrinsics.checkNotNullParameter(javac, "javac");
        Intrinsics.checkNotNullParameter(containingElement, "containingElement");
        this.containingElement = containingElement;
        this.annotations = LazyKt.lazy(new Function0<List<? extends TreeBasedAnnotation>>() { // from class: org.jetbrains.kotlin.javac.wrappers.trees.TreeBasedTypeParameter$annotations$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends TreeBasedAnnotation> invoke() {
                Collection<JCTree.JCAnnotation> collectionAnnotations = UtilsKt.annotations(tree);
                CompilationUnitTree compilationUnitTree = compilationUnit;
                JavacWrapper javacWrapper = javac;
                TreeBasedTypeParameter treeBasedTypeParameter = this;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collectionAnnotations, 10));
                Iterator<T> it = collectionAnnotations.iterator();
                while (it.hasNext()) {
                    arrayList.add(new TreeBasedAnnotation((JCTree.JCAnnotation) it.next(), compilationUnitTree, javacWrapper, treeBasedTypeParameter.containingElement));
                }
                return arrayList;
            }
        });
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaNamedElement
    public Name getName() {
        Name nameIdentifier = Name.identifier(getTree().name.toString());
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        return nameIdentifier;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: getAnnotations */
    public Collection<JavaAnnotation> mo7252getAnnotations() {
        return (Collection) this.annotations.getValue();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: findAnnotation */
    public JavaAnnotation mo7251findAnnotation(FqName fqName) {
        Object obj;
        Object next;
        ClassId classId;
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Iterator<T> it = mo7252getAnnotations().iterator();
        do {
            obj = null;
            if (it.hasNext()) {
                next = it.next();
                classId = ((JavaAnnotation) next).getClassId();
            }
            return (JavaAnnotation) obj;
        } while (!Intrinsics.areEqual(classId != null ? classId.asSingleFqName() : null, fqName));
        obj = next;
        return (JavaAnnotation) obj;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: isDeprecatedInJavaDoc */
    public boolean getIsDeprecatedInJavaDoc() {
        return getJavac().isDeprecatedInJavaDoc(getTree(), getCompilationUnit());
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaTypeParameter
    public Collection<JavaClassifierType> getUpperBounds() {
        Iterable<JCTree> bounds = getTree().bounds;
        Intrinsics.checkNotNullExpressionValue(bounds, "bounds");
        ArrayList arrayList = new ArrayList();
        for (JCTree jCTree : bounds) {
            TreeBasedType.Companion companion = TreeBasedType.INSTANCE;
            Intrinsics.checkNotNull(jCTree);
            JavaType javaTypeCreate = companion.create(jCTree, getCompilationUnit(), getJavac(), CollectionsKt.emptyList(), this.containingElement);
            JavaClassifierType javaClassifierType = javaTypeCreate instanceof JavaClassifierType ? (JavaClassifierType) javaTypeCreate : null;
            if (javaClassifierType != null) {
                arrayList.add(javaClassifierType);
            }
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.javac.wrappers.trees.TreeBasedElement
    public boolean equals(Object other) {
        if (!(other instanceof TreeBasedTypeParameter)) {
            return false;
        }
        TreeBasedTypeParameter treeBasedTypeParameter = (TreeBasedTypeParameter) other;
        return Intrinsics.areEqual(treeBasedTypeParameter.getName(), getName()) && Intrinsics.areEqual(treeBasedTypeParameter.getUpperBounds(), getUpperBounds());
    }

    @Override // org.jetbrains.kotlin.javac.wrappers.trees.TreeBasedElement
    public int hashCode() {
        int iHashCode = getName().hashCode();
        Iterator<T> it = getUpperBounds().iterator();
        while (it.hasNext()) {
            iHashCode = (iHashCode * 37) + ((JavaClassifierType) it.next()).hashCode();
        }
        return iHashCode;
    }
}
