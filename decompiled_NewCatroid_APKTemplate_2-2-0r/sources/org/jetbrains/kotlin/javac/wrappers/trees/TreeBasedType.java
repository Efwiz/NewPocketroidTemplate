package org.jetbrains.kotlin.javac.wrappers.trees;

import androidx.exifinterface.media.ExifInterface;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.tools.javac.tree.JCTree;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.javac.JavacWrapper;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner;
import org.jetbrains.kotlin.load.java.structure.JavaElement;
import org.jetbrains.kotlin.load.java.structure.JavaType;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: treeBasedTypes.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b&\u0018\u0000 )*\n\b\u0000\u0010\u0001 \u0001*\u00020\u00022\u00020\u00032\u00020\u0004:\u0001)B3\u0012\u0006\u0010\u0005\u001a\u00028\u0000\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u0012\u0006\u0010\r\u001a\u00020\u000e¢\u0006\u0002\u0010\u000fJ\u0013\u0010\u001f\u001a\u00020\u00182\b\u0010 \u001a\u0004\u0018\u00010!H\u0096\u0002J\u0012\u0010\"\u001a\u0004\u0018\u00010\f2\u0006\u0010#\u001a\u00020$H\u0016J\b\u0010%\u001a\u00020&H\u0016J\b\u0010'\u001a\u00020(H\u0016R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\f0\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0014\u0010\r\u001a\u00020\u000eX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0014\u0010\u0017\u001a\u00020\u00188VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0019R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0013\u0010\u0005\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\u001e\u001a\u0004\b\u001c\u0010\u001d¨\u0006*"}, d2 = {"Lorg/jetbrains/kotlin/javac/wrappers/trees/TreeBasedType;", ExifInterface.GPS_DIRECTION_TRUE, "Lcom/sun/tools/javac/tree/JCTree;", "Lorg/jetbrains/kotlin/load/java/structure/JavaType;", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotationOwner;", Constants.TYPE_TREE, "compilationUnit", "Lcom/sun/source/tree/CompilationUnitTree;", "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "allAnnotations", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotation;", "containingElement", "Lorg/jetbrains/kotlin/load/java/structure/JavaElement;", "(Lcom/sun/tools/javac/tree/JCTree;Lcom/sun/source/tree/CompilationUnitTree;Lorg/jetbrains/kotlin/javac/JavacWrapper;Ljava/util/Collection;Lorg/jetbrains/kotlin/load/java/structure/JavaElement;)V", "annotations", "getAnnotations", "()Ljava/util/Collection;", "getCompilationUnit", "()Lcom/sun/source/tree/CompilationUnitTree;", "getContainingElement", "()Lorg/jetbrains/kotlin/load/java/structure/JavaElement;", "isDeprecatedInJavaDoc", "", "()Z", "getJavac", "()Lorg/jetbrains/kotlin/javac/JavacWrapper;", "getTree", "()Lcom/sun/tools/javac/tree/JCTree;", "Lcom/sun/tools/javac/tree/JCTree;", Namer.EQUALS_METHOD_NAME, "other", "", "findAnnotation", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "hashCode", "", "toString", "", "Companion", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class TreeBasedType<T extends JCTree> implements JavaAnnotationOwner, JavaType {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final Collection<JavaAnnotation> allAnnotations;
    private final CompilationUnitTree compilationUnit;
    private final JavaElement containingElement;
    private final JavacWrapper javac;
    private final T tree;

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: isDeprecatedInJavaDoc */
    public boolean getIsDeprecatedInJavaDoc() {
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public TreeBasedType(T tree, CompilationUnitTree compilationUnit, JavacWrapper javac, Collection<? extends JavaAnnotation> allAnnotations, JavaElement containingElement) {
        Intrinsics.checkNotNullParameter(tree, "tree");
        Intrinsics.checkNotNullParameter(compilationUnit, "compilationUnit");
        Intrinsics.checkNotNullParameter(javac, "javac");
        Intrinsics.checkNotNullParameter(allAnnotations, "allAnnotations");
        Intrinsics.checkNotNullParameter(containingElement, "containingElement");
        this.tree = tree;
        this.compilationUnit = compilationUnit;
        this.javac = javac;
        this.allAnnotations = allAnnotations;
        this.containingElement = containingElement;
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

    protected final JavaElement getContainingElement() {
        return this.containingElement;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: getAnnotations */
    public Collection<JavaAnnotation> mo7252getAnnotations() {
        return UtilsKt.filterTypeAnnotations(this.allAnnotations);
    }

    /* JADX INFO: compiled from: treeBasedTypes.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J4\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f2\u0006\u0010\u000e\u001a\u00020\u000f¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/javac/wrappers/trees/TreeBasedType$Companion;", "", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/load/java/structure/JavaType;", Constants.TYPE_TREE, "Lcom/sun/tools/javac/tree/JCTree;", "compilationUnit", "Lcom/sun/source/tree/CompilationUnitTree;", "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "annotations", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotation;", "containingElement", "Lorg/jetbrains/kotlin/load/java/structure/JavaElement;", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final JavaType create(JCTree tree, CompilationUnitTree compilationUnit, JavacWrapper javac, Collection<? extends JavaAnnotation> annotations, JavaElement containingElement) {
            Intrinsics.checkNotNullParameter(tree, "tree");
            Intrinsics.checkNotNullParameter(compilationUnit, "compilationUnit");
            Intrinsics.checkNotNullParameter(javac, "javac");
            Intrinsics.checkNotNullParameter(annotations, "annotations");
            Intrinsics.checkNotNullParameter(containingElement, "containingElement");
            if (tree instanceof JCTree.JCPrimitiveTypeTree) {
                return new TreeBasedPrimitiveType((JCTree.JCPrimitiveTypeTree) tree, compilationUnit, javac, annotations, containingElement);
            }
            if (tree instanceof JCTree.JCArrayTypeTree) {
                return new TreeBasedArrayType((JCTree.JCArrayTypeTree) tree, compilationUnit, javac, annotations, containingElement);
            }
            if (tree instanceof JCTree.JCWildcard) {
                return new TreeBasedWildcardType((JCTree.JCWildcard) tree, compilationUnit, javac, annotations, containingElement);
            }
            if (tree instanceof JCTree.JCTypeApply) {
                return new TreeBasedGenericClassifierType((JCTree.JCTypeApply) tree, compilationUnit, javac, annotations, containingElement);
            }
            if (tree instanceof JCTree.JCAnnotatedType) {
                JCTree.JCAnnotatedType jCAnnotatedType = (JCTree.JCAnnotatedType) tree;
                JCTree.JCExpression jCExpression = jCAnnotatedType.underlyingType;
                Iterable annotations2 = jCAnnotatedType.annotations;
                Intrinsics.checkNotNullExpressionValue(annotations2, "annotations");
                Iterable<JCTree.JCAnnotation> iterable = annotations2;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterable, 10));
                for (JCTree.JCAnnotation jCAnnotation : iterable) {
                    Intrinsics.checkNotNull(jCAnnotation);
                    arrayList.add(new TreeBasedAnnotation(jCAnnotation, compilationUnit, javac, containingElement));
                }
                Intrinsics.checkNotNull(jCExpression);
                return create((JCTree) jCExpression, compilationUnit, javac, arrayList, containingElement);
            }
            if (tree instanceof JCTree.JCExpression) {
                return new TreeBasedNonGenericClassifierType((JCTree.JCExpression) tree, compilationUnit, javac, annotations, containingElement);
            }
            throw new UnsupportedOperationException("Unsupported type: " + tree);
        }
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

    public boolean equals(Object other) {
        TreeBasedType treeBasedType = other instanceof TreeBasedType ? (TreeBasedType) other : null;
        return Intrinsics.areEqual(treeBasedType != null ? treeBasedType.tree : null, this.tree);
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
