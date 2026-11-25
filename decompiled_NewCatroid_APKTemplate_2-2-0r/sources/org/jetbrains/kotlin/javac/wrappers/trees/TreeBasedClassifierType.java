package org.jetbrains.kotlin.javac.wrappers.trees;

import androidx.exifinterface.media.ExifInterface;
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
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.javac.JavacWrapper;
import org.jetbrains.kotlin.javac.resolve.MockKotlinClassifier;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaClassifier;
import org.jetbrains.kotlin.load.java.structure.JavaClassifierType;
import org.jetbrains.kotlin.load.java.structure.JavaElement;
import org.jetbrains.kotlin.load.java.structure.JavaType;
import org.jetbrains.kotlin.load.java.structure.JavaTypeParameter;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: treeBasedTypes.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000*\n\b\u0000\u0010\u0001 \u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\u00020\u0004B5\b\u0004\u0012\u0006\u0010\u0005\u001a\u00028\u0000\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u0012\u0006\u0010\r\u001a\u00020\u000e¢\u0006\u0002\u0010\u000fR\u001d\u0010\u0010\u001a\u0004\u0018\u00010\u00118VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0016\u001a\u00020\u00178VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u0014\u0010\u001a\u001a\u00020\u00178VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u0019R\u001c\u0010\u001c\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u001e0\u001d8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010 \u0082\u0001\u0002!\"¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/javac/wrappers/trees/TreeBasedClassifierType;", ExifInterface.GPS_DIRECTION_TRUE, "Lcom/sun/tools/javac/tree/JCTree;", "Lorg/jetbrains/kotlin/javac/wrappers/trees/TreeBasedType;", "Lorg/jetbrains/kotlin/load/java/structure/JavaClassifierType;", Constants.TYPE_TREE, "compilationUnit", "Lcom/sun/source/tree/CompilationUnitTree;", "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "allAnnotations", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotation;", "containingElement", "Lorg/jetbrains/kotlin/load/java/structure/JavaElement;", "(Lcom/sun/tools/javac/tree/JCTree;Lcom/sun/source/tree/CompilationUnitTree;Lorg/jetbrains/kotlin/javac/JavacWrapper;Ljava/util/Collection;Lorg/jetbrains/kotlin/load/java/structure/JavaElement;)V", "classifier", "Lorg/jetbrains/kotlin/load/java/structure/JavaClassifier;", "getClassifier", "()Lorg/jetbrains/kotlin/load/java/structure/JavaClassifier;", "classifier$delegate", "Lkotlin/Lazy;", "classifierQualifiedName", "", "getClassifierQualifiedName", "()Ljava/lang/String;", "presentableText", "getPresentableText", "typeArguments", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaType;", "getTypeArguments", "()Ljava/util/List;", "Lorg/jetbrains/kotlin/javac/wrappers/trees/TreeBasedGenericClassifierType;", "Lorg/jetbrains/kotlin/javac/wrappers/trees/TreeBasedNonGenericClassifierType;", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class TreeBasedClassifierType<T extends JCTree> extends TreeBasedType<T> implements JavaClassifierType {

    /* JADX INFO: renamed from: classifier$delegate, reason: from kotlin metadata */
    private final Lazy classifier;

    public /* synthetic */ TreeBasedClassifierType(JCTree jCTree, CompilationUnitTree compilationUnitTree, JavacWrapper javacWrapper, Collection collection, JavaElement javaElement, DefaultConstructorMarker defaultConstructorMarker) {
        this(jCTree, compilationUnitTree, javacWrapper, collection, javaElement);
    }

    private TreeBasedClassifierType(final T t, final CompilationUnitTree compilationUnitTree, final JavacWrapper javacWrapper, Collection<? extends JavaAnnotation> collection, final JavaElement javaElement) {
        super(t, compilationUnitTree, javacWrapper, collection, javaElement);
        this.classifier = LazyKt.lazy(new Function0<JavaClassifier>() { // from class: org.jetbrains.kotlin.javac.wrappers.trees.TreeBasedClassifierType$classifier$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Incorrect types in method signature: (Lorg/jetbrains/kotlin/javac/JavacWrapper;TT;Lcom/sun/source/tree/CompilationUnitTree;Lorg/jetbrains/kotlin/load/java/structure/JavaElement;)V */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final JavaClassifier invoke() {
                return javacWrapper.resolve(t, compilationUnitTree, javaElement);
            }
        });
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClassifierType
    public JavaClassifier getClassifier() {
        return (JavaClassifier) this.classifier.getValue();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClassifierType
    public String getClassifierQualifiedName() {
        FqName fqName;
        JavaClassifier classifier = getClassifier();
        JavaClass javaClass = classifier instanceof JavaClass ? (JavaClass) classifier : null;
        String strAsString = (javaClass == null || (fqName = javaClass.getFqName()) == null) ? null : fqName.asString();
        if (strAsString != null) {
            return strAsString;
        }
        String string = getTree().toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return StringsKt.substringBefore$default(string, "<", (String) null, 2, (Object) null);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClassifierType
    public String getPresentableText() {
        return getClassifierQualifiedName();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClassifierType
    /* JADX INFO: renamed from: getTypeArguments */
    public List<JavaType> mo7270getTypeArguments() {
        List<JavaType> listMo7270getTypeArguments;
        T tree = getTree();
        if (tree instanceof JCTree.JCTypeApply) {
            T clazz = ((JCTree.JCTypeApply) tree).clazz;
            Intrinsics.checkNotNullExpressionValue(clazz, "clazz");
            tree = clazz;
        }
        if (tree instanceof JCTree.JCFieldAccess) {
            TreeBasedType.Companion companion = TreeBasedType.INSTANCE;
            JCTree.JCExpression selected = ((JCTree.JCFieldAccess) tree).selected;
            Intrinsics.checkNotNullExpressionValue(selected, "selected");
            JavaType javaTypeCreate = companion.create((JCTree) selected, getCompilationUnit(), getJavac(), mo7252getAnnotations(), getContainingElement());
            JavaClassifierType javaClassifierType = javaTypeCreate instanceof JavaClassifierType ? (JavaClassifierType) javaTypeCreate : null;
            return (javaClassifierType == null || (listMo7270getTypeArguments = javaClassifierType.mo7270getTypeArguments()) == null) ? CollectionsKt.emptyList() : listMo7270getTypeArguments;
        }
        JavaClassifier classifier = getClassifier();
        JavaClass javaClass = classifier instanceof JavaClass ? (JavaClass) classifier : null;
        if (javaClass == null) {
            return CollectionsKt.emptyList();
        }
        if ((javaClass instanceof MockKotlinClassifier) || javaClass.mo7267isStatic()) {
            return CollectionsKt.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        boolean z = false;
        for (JavaClass outerClass = javaClass.getOuterClass(); outerClass != null && !z; outerClass = outerClass.getOuterClass()) {
            if (outerClass.mo7267isStatic()) {
                z = true;
            }
            arrayList.add(outerClass);
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            List<JavaTypeParameter> typeParameters = ((JavaClass) it.next()).mo7258getTypeParameters();
            ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(typeParameters, 10));
            Iterator<T> it2 = typeParameters.iterator();
            while (it2.hasNext()) {
                arrayList3.add(new TreeBasedTypeParameterType((JavaTypeParameter) it2.next()));
            }
            CollectionsKt.addAll(arrayList2, arrayList3);
        }
        return arrayList2;
    }
}
