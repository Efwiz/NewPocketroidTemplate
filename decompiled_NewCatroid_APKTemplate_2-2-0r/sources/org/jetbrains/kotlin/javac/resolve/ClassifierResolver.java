package org.jetbrains.kotlin.javac.resolve;

import com.koushikdutta.async.http.cache.ResponseCacheMiddleware;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.Tree;
import com.sun.tools.javac.tree.JCTree;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.javac.JavacWrapper;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaClassifier;
import org.jetbrains.kotlin.load.java.structure.JavaElement;
import org.jetbrains.kotlin.load.java.structure.JavaMember;
import org.jetbrains.kotlin.load.java.structure.JavaPackage;
import org.jetbrains.kotlin.load.java.structure.JavaTypeParameter;
import org.jetbrains.kotlin.load.java.structure.JavaTypeParameterListOwner;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: ClassifierResolver.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0007H\u0002J\u0016\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010\u0012\u001a\u00020\u0011H\u0002J\u0010\u0010\u0013\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0007H\u0002J \u0010\u0014\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u000e\u001a\u00020\u00072\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018J\"\u0010\u0019\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u000e\u001a\u00020\u00072\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0002R\u001e\u0010\u0005\u001a\u0012\u0012\u0004\u0012\u00020\u00070\u0006j\b\u0012\u0004\u0012\u00020\u0007`\bX\u0082\u0004¢\u0006\u0002\n\u0000R.\u0010\t\u001a\"\u0012\u0004\u0012\u00020\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\nj\u0010\u0012\u0004\u0012\u00020\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u000b`\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/javac/resolve/ClassifierResolver;", "", "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "(Lorg/jetbrains/kotlin/javac/JavacWrapper;)V", "beingResolved", "Ljava/util/HashSet;", "Lcom/sun/source/tree/Tree;", "Lkotlin/collections/HashSet;", ResponseCacheMiddleware.CACHE, "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/load/java/structure/JavaClassifier;", "Lkotlin/collections/HashMap;", "", Constants.TYPE_TREE, "pathSegments", "", "", "path", "removeBeingResolved", "resolve", "unit", "Lcom/sun/source/tree/CompilationUnitTree;", "containingElement", "Lorg/jetbrains/kotlin/load/java/structure/JavaElement;", "tryToResolve", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ClassifierResolver {
    private final HashSet<Tree> beingResolved;
    private final HashMap<Tree, JavaClassifier> cache;
    private final JavacWrapper javac;

    public ClassifierResolver(JavacWrapper javac) {
        Intrinsics.checkNotNullParameter(javac, "javac");
        this.javac = javac;
        this.cache = new HashMap<>();
        this.beingResolved = new HashSet<>();
    }

    public final JavaClassifier resolve(Tree tree, CompilationUnitTree unit, JavaElement containingElement) {
        Intrinsics.checkNotNullParameter(tree, "tree");
        Intrinsics.checkNotNullParameter(unit, "unit");
        Intrinsics.checkNotNullParameter(containingElement, "containingElement");
        JavaClassifier javaClassifier = this.cache.get(tree);
        if (javaClassifier != null) {
            return javaClassifier;
        }
        if (this.beingResolved.contains(tree)) {
            return null;
        }
        beingResolved(tree);
        JavaClassifier javaClassifierTryToResolve = tryToResolve(tree, unit, containingElement);
        this.cache.put(tree, javaClassifierTryToResolve);
        removeBeingResolved(tree);
        return javaClassifierTryToResolve;
    }

    private final void beingResolved(Tree tree) {
        if (tree instanceof JCTree.JCTypeApply) {
            JCTree.JCExpression clazz = ((JCTree.JCTypeApply) tree).clazz;
            Intrinsics.checkNotNullExpressionValue(clazz, "clazz");
            beingResolved((Tree) clazz);
        }
        if (tree instanceof JCTree.JCFieldAccess) {
            this.beingResolved.add(tree);
            JCTree.JCExpression selected = ((JCTree.JCFieldAccess) tree).selected;
            Intrinsics.checkNotNullExpressionValue(selected, "selected");
            beingResolved((Tree) selected);
            return;
        }
        this.beingResolved.add(tree);
    }

    private final void removeBeingResolved(Tree tree) {
        if (tree instanceof JCTree.JCTypeApply) {
            JCTree.JCExpression clazz = ((JCTree.JCTypeApply) tree).clazz;
            Intrinsics.checkNotNullExpressionValue(clazz, "clazz");
            beingResolved((Tree) clazz);
        }
        if (tree instanceof JCTree.JCFieldAccess) {
            this.beingResolved.remove(tree);
            JCTree.JCExpression selected = ((JCTree.JCFieldAccess) tree).selected;
            Intrinsics.checkNotNullExpressionValue(selected, "selected");
            beingResolved((Tree) selected);
            return;
        }
        this.beingResolved.remove(tree);
    }

    private final List<String> pathSegments(String path) {
        ArrayList arrayList = new ArrayList();
        StringBuilder sb = new StringBuilder();
        String str = path;
        int i = 0;
        for (int i2 = 0; i2 < str.length(); i2++) {
            char cCharAt = str.charAt(i2);
            if (cCharAt == '<') {
                i++;
            } else if (cCharAt == '>') {
                i--;
            } else if (cCharAt == '.') {
                if (i == 0) {
                    arrayList.add(sb.toString());
                    sb.setLength(0);
                }
            } else if (cCharAt != '@' && i == 0) {
                sb.append(cCharAt);
            }
        }
        arrayList.add(sb.toString());
        return arrayList;
    }

    private final JavaClassifier tryToResolve(Tree tree, CompilationUnitTree unit, JavaElement containingElement) {
        JavaClass javaClassMo7272getContainingClass;
        Object next;
        List<String> listPathSegments = pathSegments(tree.toString());
        if (containingElement instanceof JavaClass) {
            javaClassMo7272getContainingClass = (JavaClass) containingElement;
        } else if (containingElement instanceof JavaTypeParameterListOwner) {
            String str = (String) CollectionsKt.singleOrNull((List) listPathSegments);
            if (str != null) {
                Name nameIdentifier = Name.identifier(str);
                Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                Iterator<T> it = ((JavaTypeParameterListOwner) containingElement).mo7258getTypeParameters().iterator();
                do {
                    if (!it.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it.next();
                } while (!Intrinsics.areEqual(((JavaTypeParameter) next).getName(), nameIdentifier));
                JavaTypeParameter javaTypeParameter = (JavaTypeParameter) next;
                if (javaTypeParameter != null) {
                    return javaTypeParameter;
                }
            }
            Intrinsics.checkNotNull(containingElement, "null cannot be cast to non-null type org.jetbrains.kotlin.load.java.structure.JavaMember");
            javaClassMo7272getContainingClass = ((JavaMember) containingElement).getContainingClass();
        } else {
            if (containingElement instanceof JavaPackage) {
                return new SingleTypeImportScope(this.javac, unit).findClass((String) CollectionsKt.first((List) listPathSegments), listPathSegments);
            }
            throw new UnsupportedOperationException();
        }
        return new CurrentClassAndInnerScope(this.javac, unit, javaClassMo7272getContainingClass).findClass((String) CollectionsKt.first((List) listPathSegments), listPathSegments);
    }
}
