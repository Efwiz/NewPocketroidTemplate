package org.jetbrains.kotlin.javac.resolve;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.ImportTree;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.javac.JavacWrapper;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaClassifier;

/* JADX INFO: compiled from: ClassifierResolver.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u000e\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bH\u0002J \u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u000f\u001a\u00020\f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\f0\u000bH\u0016R\u0014\u0010\u0007\u001a\u00020\u00018VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/javac/resolve/ImportOnDemandScope;", "Lorg/jetbrains/kotlin/javac/resolve/Scope;", "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "compilationUnit", "Lcom/sun/source/tree/CompilationUnitTree;", "(Lorg/jetbrains/kotlin/javac/JavacWrapper;Lcom/sun/source/tree/CompilationUnitTree;)V", "parent", "getParent", "()Lorg/jetbrains/kotlin/javac/resolve/Scope;", "asteriskImports", "", "", "findClass", "Lorg/jetbrains/kotlin/load/java/structure/JavaClassifier;", "name", "pathSegments", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class ImportOnDemandScope extends Scope {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ImportOnDemandScope(JavacWrapper javac, CompilationUnitTree compilationUnit) {
        super(javac, compilationUnit);
        Intrinsics.checkNotNullParameter(javac, "javac");
        Intrinsics.checkNotNullParameter(compilationUnit, "compilationUnit");
    }

    @Override // org.jetbrains.kotlin.javac.resolve.Scope
    public Scope getParent() {
        return new GlobalScope(getJavac(), getCompilationUnit());
    }

    @Override // org.jetbrains.kotlin.javac.resolve.Scope
    public JavaClassifier findClass(String name2, List<String> pathSegments) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(pathSegments, "pathSegments");
        List<String> listAsteriskImports = asteriskImports();
        HashSet hashSet = new HashSet();
        Iterator<T> it = listAsteriskImports.iterator();
        while (it.hasNext()) {
            JavaClass javaClassFindImport = getHelper().findImport(StringsKt.split$default((CharSequence) (((String) it.next()) + name2), new String[]{"."}, false, 0, 6, (Object) null));
            if (javaClassFindImport != null) {
                hashSet.add(javaClassFindImport);
            }
        }
        if (hashSet.isEmpty()) {
            hashSet = null;
        }
        HashSet hashSet2 = hashSet;
        if (hashSet2 != null) {
            JavaClass javaClass = (JavaClass) CollectionsKt.singleOrNull(hashSet2);
            return javaClass != null ? getHelper().getJavaClassFromPathSegments(javaClass, pathSegments) : null;
        }
        return getParent().findClass(name2, pathSegments);
    }

    private final List<String> asteriskImports() {
        List imports = getCompilationUnit().getImports();
        Intrinsics.checkNotNullExpressionValue(imports, "getImports(...)");
        ArrayList arrayList = new ArrayList();
        Iterator it = imports.iterator();
        while (it.hasNext()) {
            String string = ((ImportTree) it.next()).getQualifiedIdentifier().toString();
            if (!StringsKt.endsWith$default(string, "*", false, 2, (Object) null)) {
                string = null;
            }
            String strDropLast = string != null ? StringsKt.dropLast(string, 1) : null;
            if (strDropLast != null) {
                arrayList.add(strDropLast);
            }
        }
        return arrayList;
    }
}
