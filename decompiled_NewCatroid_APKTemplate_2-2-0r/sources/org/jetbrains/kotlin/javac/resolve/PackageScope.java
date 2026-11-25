package org.jetbrains.kotlin.javac.resolve;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.ExpressionTree;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.javac.JavacWrapper;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaClassifier;

/* JADX INFO: compiled from: ClassifierResolver.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J \u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\r0\u000fH\u0016R\u0014\u0010\u0007\u001a\u00020\u00018VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/javac/resolve/PackageScope;", "Lorg/jetbrains/kotlin/javac/resolve/Scope;", "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "compilationUnit", "Lcom/sun/source/tree/CompilationUnitTree;", "(Lorg/jetbrains/kotlin/javac/JavacWrapper;Lcom/sun/source/tree/CompilationUnitTree;)V", "parent", "getParent", "()Lorg/jetbrains/kotlin/javac/resolve/Scope;", "findClass", "Lorg/jetbrains/kotlin/load/java/structure/JavaClassifier;", "name", "", "pathSegments", "", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class PackageScope extends Scope {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PackageScope(JavacWrapper javac, CompilationUnitTree compilationUnit) {
        super(javac, compilationUnit);
        Intrinsics.checkNotNullParameter(javac, "javac");
        Intrinsics.checkNotNullParameter(compilationUnit, "compilationUnit");
    }

    @Override // org.jetbrains.kotlin.javac.resolve.Scope
    public Scope getParent() {
        return new ImportOnDemandScope(getJavac(), getCompilationUnit());
    }

    @Override // org.jetbrains.kotlin.javac.resolve.Scope
    public JavaClassifier findClass(String name2, List<String> pathSegments) {
        String string;
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(pathSegments, "pathSegments");
        ResolveHelper helper = getHelper();
        ExpressionTree packageName = getCompilationUnit().getPackageName();
        if (packageName == null || (string = packageName.toString()) == null) {
            string = "";
        }
        JavaClass javaClassFindJavaOrKotlinClass = helper.findJavaOrKotlinClass(ClassifierResolverKt.classId(string, name2));
        if (javaClassFindJavaOrKotlinClass != null) {
            return getHelper().getJavaClassFromPathSegments(javaClassFindJavaOrKotlinClass, pathSegments);
        }
        return getParent().findClass(name2, pathSegments);
    }
}
