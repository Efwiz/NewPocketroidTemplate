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
import kotlin.text.StringsKt;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.javac.JavacWrapper;
import org.jetbrains.kotlin.load.java.structure.JavaElement;
import org.jetbrains.kotlin.load.java.structure.JavaType;
import org.jetbrains.kotlin.load.java.structure.JavaValueParameter;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: TreeBasedValueParameter.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B%\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\u0012\u0010 \u001a\u0004\u0018\u00010\u000e2\u0006\u0010!\u001a\u00020\"H\u0016R!\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\u00020\u00148VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0015R\u0014\u0010\u0016\u001a\u00020\u00148VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0015R\u0014\u0010\u0017\u001a\u00020\u00148VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0015R\u0014\u0010\u0018\u001a\u00020\u00198VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001bR\u0014\u0010\u001c\u001a\u00020\u001d8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001f¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/javac/wrappers/trees/TreeBasedValueParameter;", "Lorg/jetbrains/kotlin/javac/wrappers/trees/TreeBasedElement;", "Lcom/sun/tools/javac/tree/JCTree$JCVariableDecl;", "Lorg/jetbrains/kotlin/load/java/structure/JavaValueParameter;", Constants.TYPE_TREE, "compilationUnit", "Lcom/sun/source/tree/CompilationUnitTree;", "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "containingElement", "Lorg/jetbrains/kotlin/load/java/structure/JavaElement;", "(Lcom/sun/tools/javac/tree/JCTree$JCVariableDecl;Lcom/sun/source/tree/CompilationUnitTree;Lorg/jetbrains/kotlin/javac/JavacWrapper;Lorg/jetbrains/kotlin/load/java/structure/JavaElement;)V", "annotations", "", "Lorg/jetbrains/kotlin/javac/wrappers/trees/TreeBasedAnnotation;", "getAnnotations", "()Ljava/util/Collection;", "annotations$delegate", "Lkotlin/Lazy;", "isDeprecatedInJavaDoc", "", "()Z", "isFromSource", "isVararg", "name", "Lorg/jetbrains/kotlin/name/Name;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/load/java/structure/JavaType;", "getType", "()Lorg/jetbrains/kotlin/load/java/structure/JavaType;", "findAnnotation", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TreeBasedValueParameter extends TreeBasedElement<JCTree.JCVariableDecl> implements JavaValueParameter {

    /* JADX INFO: renamed from: annotations$delegate, reason: from kotlin metadata */
    private final Lazy annotations;
    private final JavaElement containingElement;

    @Override // org.jetbrains.kotlin.load.java.structure.JavaValueParameter
    public boolean isFromSource() {
        return true;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TreeBasedValueParameter(final JCTree.JCVariableDecl tree, final CompilationUnitTree compilationUnit, final JavacWrapper javac, JavaElement containingElement) {
        super((JCTree) tree, compilationUnit, javac);
        Intrinsics.checkNotNullParameter(tree, "tree");
        Intrinsics.checkNotNullParameter(compilationUnit, "compilationUnit");
        Intrinsics.checkNotNullParameter(javac, "javac");
        Intrinsics.checkNotNullParameter(containingElement, "containingElement");
        this.containingElement = containingElement;
        this.annotations = LazyKt.lazy(new Function0<List<? extends TreeBasedAnnotation>>() { // from class: org.jetbrains.kotlin.javac.wrappers.trees.TreeBasedValueParameter$annotations$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends TreeBasedAnnotation> invoke() {
                Collection<JCTree.JCAnnotation> collectionAnnotations = UtilsKt.annotations(tree);
                CompilationUnitTree compilationUnitTree = compilationUnit;
                JavacWrapper javacWrapper = javac;
                TreeBasedValueParameter treeBasedValueParameter = this;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collectionAnnotations, 10));
                Iterator<T> it = collectionAnnotations.iterator();
                while (it.hasNext()) {
                    arrayList.add(new TreeBasedAnnotation((JCTree.JCAnnotation) it.next(), compilationUnitTree, javacWrapper, treeBasedValueParameter.containingElement));
                }
                return arrayList;
            }
        });
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

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: isDeprecatedInJavaDoc */
    public boolean getIsDeprecatedInJavaDoc() {
        return getJavac().isDeprecatedInJavaDoc(getTree(), getCompilationUnit());
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaValueParameter
    public Name getName() {
        Name nameIdentifier = Name.identifier(getTree().name.toString());
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        return nameIdentifier;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaValueParameter
    public JavaType getType() {
        TreeBasedType.Companion companion = TreeBasedType.INSTANCE;
        JCTree type = getTree().getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        return companion.create(type, getCompilationUnit(), getJavac(), mo7252getAnnotations(), this.containingElement);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaValueParameter
    /* JADX INFO: renamed from: isVararg */
    public boolean getIsVararg() {
        return (getTree().getModifiers().flags & 17179869184L) != 0;
    }
}
