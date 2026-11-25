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
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.javac.JavacWrapper;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaMember;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: TreeBasedMember.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\b&\u0018\u0000*\n\b\u0000\u0010\u0001 \u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\u00020\u0004B%\u0012\u0006\u0010\u0005\u001a\u00028\u0000\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\u0012\u0010\u001a\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u001b\u001a\u00020\u001cH\u0016R!\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0016\u001a\u00020\u00178VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0018R\u0014\u0010\u0019\u001a\u00020\u00178VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u0018¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/javac/wrappers/trees/TreeBasedMember;", ExifInterface.GPS_DIRECTION_TRUE, "Lcom/sun/tools/javac/tree/JCTree;", "Lorg/jetbrains/kotlin/javac/wrappers/trees/TreeBasedElement;", "Lorg/jetbrains/kotlin/load/java/structure/JavaMember;", Constants.TYPE_TREE, "compilationUnit", "Lcom/sun/source/tree/CompilationUnitTree;", "containingClass", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "(Lcom/sun/tools/javac/tree/JCTree;Lcom/sun/source/tree/CompilationUnitTree;Lorg/jetbrains/kotlin/load/java/structure/JavaClass;Lorg/jetbrains/kotlin/javac/JavacWrapper;)V", "annotations", "", "Lorg/jetbrains/kotlin/javac/wrappers/trees/TreeBasedAnnotation;", "getAnnotations", "()Ljava/util/Collection;", "annotations$delegate", "Lkotlin/Lazy;", "getContainingClass", "()Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "isDeprecatedInJavaDoc", "", "()Z", "isFromSource", "findAnnotation", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class TreeBasedMember<T extends JCTree> extends TreeBasedElement<T> implements JavaMember {

    /* JADX INFO: renamed from: annotations$delegate, reason: from kotlin metadata */
    private final Lazy annotations;
    private final JavaClass containingClass;

    @Override // org.jetbrains.kotlin.load.java.structure.JavaNamedElement
    public boolean isFromSource() {
        return true;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaMember
    public JavaClass getContainingClass() {
        return this.containingClass;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TreeBasedMember(final T tree, final CompilationUnitTree compilationUnit, JavaClass containingClass, final JavacWrapper javac) {
        super(tree, compilationUnit, javac);
        Intrinsics.checkNotNullParameter(tree, "tree");
        Intrinsics.checkNotNullParameter(compilationUnit, "compilationUnit");
        Intrinsics.checkNotNullParameter(containingClass, "containingClass");
        Intrinsics.checkNotNullParameter(javac, "javac");
        this.containingClass = containingClass;
        this.annotations = LazyKt.lazy(new Function0<List<? extends TreeBasedAnnotation>>() { // from class: org.jetbrains.kotlin.javac.wrappers.trees.TreeBasedMember$annotations$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Incorrect types in method signature: (TT;Lcom/sun/source/tree/CompilationUnitTree;Lorg/jetbrains/kotlin/javac/JavacWrapper;Lorg/jetbrains/kotlin/javac/wrappers/trees/TreeBasedMember<+TT;>;)V */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends TreeBasedAnnotation> invoke() {
                Collection<JCTree.JCAnnotation> collectionAnnotations = UtilsKt.annotations(tree);
                CompilationUnitTree compilationUnitTree = compilationUnit;
                JavacWrapper javacWrapper = javac;
                JavaMember javaMember = this;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collectionAnnotations, 10));
                Iterator<T> it = collectionAnnotations.iterator();
                while (it.hasNext()) {
                    arrayList.add(new TreeBasedAnnotation((JCTree.JCAnnotation) it.next(), compilationUnitTree, javacWrapper, javaMember.getContainingClass()));
                }
                return arrayList;
            }
        });
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: isDeprecatedInJavaDoc */
    public boolean getIsDeprecatedInJavaDoc() {
        return getJavac().isDeprecatedInJavaDoc(getTree(), getCompilationUnit());
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: getAnnotations */
    public Collection<TreeBasedAnnotation> mo7252getAnnotations() {
        return (Collection) this.annotations.getValue();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: findAnnotation */
    public TreeBasedAnnotation mo7251findAnnotation(FqName fqName) {
        Object obj;
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Collection<TreeBasedAnnotation> collectionMo7252getAnnotations = mo7252getAnnotations();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = collectionMo7252getAnnotations.iterator();
        while (true) {
            obj = null;
            if (!it.hasNext()) {
                break;
            }
            Object next = it.next();
            String string = ((TreeBasedAnnotation) next).getAnnotation().annotationType.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            String strAsString = fqName.shortName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            if (StringsKt.endsWith$default(string, strAsString, false, 2, (Object) null)) {
                arrayList.add(next);
            }
        }
        for (Object obj2 : arrayList) {
            if (Intrinsics.areEqual(((TreeBasedAnnotation) obj2).getClassId().asSingleFqName(), fqName)) {
                obj = obj2;
                break;
            }
        }
        return (TreeBasedAnnotation) obj;
    }
}
