package org.jetbrains.kotlin.javac.resolve;

import com.sun.source.tree.CompilationUnitTree;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.javac.JavacWrapper;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaClassifier;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: ClassifierResolver.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0018\u0010\n\u001a\u0004\u0018\u00010\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rH\u0002J \u0010\u000f\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0010\u001a\u00020\u000e2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rH\u0016R\u0016\u0010\u0007\u001a\u0004\u0018\u00010\u00018VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/javac/resolve/GlobalScope;", "Lorg/jetbrains/kotlin/javac/resolve/Scope;", "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "compilationUnit", "Lcom/sun/source/tree/CompilationUnitTree;", "(Lorg/jetbrains/kotlin/javac/JavacWrapper;Lcom/sun/source/tree/CompilationUnitTree;)V", "parent", "getParent", "()Lorg/jetbrains/kotlin/javac/resolve/Scope;", "findByFqName", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "pathSegments", "", "", "findClass", "name", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class GlobalScope extends Scope {
    @Override // org.jetbrains.kotlin.javac.resolve.Scope
    public Scope getParent() {
        return null;
    }

    @Override // org.jetbrains.kotlin.javac.resolve.Scope
    public /* bridge */ /* synthetic */ JavaClassifier findClass(String str, List list) {
        return findClass(str, (List<String>) list);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GlobalScope(JavacWrapper javac, CompilationUnitTree compilationUnit) {
        super(javac, compilationUnit);
        Intrinsics.checkNotNullParameter(javac, "javac");
        Intrinsics.checkNotNullParameter(compilationUnit, "compilationUnit");
    }

    @Override // org.jetbrains.kotlin.javac.resolve.Scope
    public JavaClass findClass(String name2, List<String> pathSegments) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(pathSegments, "pathSegments");
        JavaClass javaClassFindByFqName = findByFqName(pathSegments);
        if (javaClassFindByFqName != null) {
            return javaClassFindByFqName;
        }
        JavaClass javaClassFindJavaOrKotlinClass = getHelper().findJavaOrKotlinClass(ClassifierResolverKt.classId(CommonClassNames.DEFAULT_PACKAGE, name2));
        if (javaClassFindJavaOrKotlinClass != null) {
            return getHelper().getJavaClassFromPathSegments(javaClassFindJavaOrKotlinClass, pathSegments);
        }
        return null;
    }

    private final JavaClass findByFqName(List<String> pathSegments) {
        List<String> list = pathSegments;
        int i = 0;
        for (Object obj : list) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            if (i != 0) {
                FqName fqNameFindPackage = getHelper().findPackage(CollectionsKt.joinToString$default(CollectionsKt.take(list, i), ".", null, null, 0, null, null, 62, null));
                if (fqNameFindPackage != null) {
                    List<String> listDrop = CollectionsKt.drop(list, i);
                    JavaClass javaClassFindJavaOrKotlinClass = getHelper().findJavaOrKotlinClass(new ClassId(fqNameFindPackage, Name.identifier((String) CollectionsKt.first((List) listDrop))));
                    if (javaClassFindJavaOrKotlinClass != null) {
                        return getHelper().getJavaClassFromPathSegments(javaClassFindJavaOrKotlinClass, listDrop);
                    }
                } else {
                    continue;
                }
            }
            i = i2;
        }
        JavaClass javaClassFindJavaOrKotlinClass2 = getHelper().findJavaOrKotlinClass(ClassifierResolverKt.classId("", (String) CollectionsKt.first((List) pathSegments)));
        if (javaClassFindJavaOrKotlinClass2 != null) {
            return getHelper().getJavaClassFromPathSegments(javaClassFindJavaOrKotlinClass2, pathSegments);
        }
        return null;
    }
}
