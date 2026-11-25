package org.jetbrains.kotlin.javac.resolve;

import com.sun.source.tree.CompilationUnitTree;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.javac.JavacWrapper;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaClassifier;
import org.jetbrains.kotlin.load.java.structure.JavaTypeParameter;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: ClassifierResolver.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ \u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0011H\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\u00020\u00018VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/javac/resolve/CurrentClassAndInnerScope;", "Lorg/jetbrains/kotlin/javac/resolve/Scope;", "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "compilationUnit", "Lcom/sun/source/tree/CompilationUnitTree;", "containingElement", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "(Lorg/jetbrains/kotlin/javac/JavacWrapper;Lcom/sun/source/tree/CompilationUnitTree;Lorg/jetbrains/kotlin/load/java/structure/JavaClass;)V", "parent", "getParent", "()Lorg/jetbrains/kotlin/javac/resolve/Scope;", "findClass", "Lorg/jetbrains/kotlin/load/java/structure/JavaClassifier;", "name", "", "pathSegments", "", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class CurrentClassAndInnerScope extends Scope {
    private final JavaClass containingElement;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CurrentClassAndInnerScope(JavacWrapper javac, CompilationUnitTree compilationUnit, JavaClass containingElement) {
        super(javac, compilationUnit);
        Intrinsics.checkNotNullParameter(javac, "javac");
        Intrinsics.checkNotNullParameter(compilationUnit, "compilationUnit");
        Intrinsics.checkNotNullParameter(containingElement, "containingElement");
        this.containingElement = containingElement;
    }

    @Override // org.jetbrains.kotlin.javac.resolve.Scope
    public Scope getParent() {
        return new SingleTypeImportScope(getJavac(), getCompilationUnit());
    }

    @Override // org.jetbrains.kotlin.javac.resolve.Scope
    public JavaClassifier findClass(String name2, List<String> pathSegments) {
        Object next;
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(pathSegments, "pathSegments");
        Name nameIdentifier = Name.identifier(name2);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        for (JavaClass outerClass = this.containingElement; outerClass != null; outerClass = outerClass.getOuterClass()) {
            Iterator<T> it = outerClass.mo7258getTypeParameters().iterator();
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
            JavaClass javaClassFindInnerOrNested$default = ResolveHelper.findInnerOrNested$default(getHelper(), outerClass, nameIdentifier, null, 4, null);
            if (javaClassFindInnerOrNested$default != null) {
                return getHelper().getJavaClassFromPathSegments(javaClassFindInnerOrNested$default, pathSegments);
            }
            if (Intrinsics.areEqual(outerClass.getName(), nameIdentifier) && pathSegments.size() == 1) {
                return outerClass;
            }
        }
        return getParent().findClass(name2, pathSegments);
    }
}
