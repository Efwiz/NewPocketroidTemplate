package org.jetbrains.kotlin.javac.resolve;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.tools.javac.tree.JCTree;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.javac.JavacWrapper;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaField;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: IdentifierResolver.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u001a\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0016\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\u0006\u0010\u0013\u001a\u00020\u0012H\u0002R\u0014\u0010\u0007\u001a\u00020\u00018VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/javac/resolve/StaticImportFieldScope;", "Lorg/jetbrains/kotlin/javac/resolve/FieldScope;", "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "compilationUnit", "Lcom/sun/source/tree/CompilationUnitTree;", "(Lorg/jetbrains/kotlin/javac/JavacWrapper;Lcom/sun/source/tree/CompilationUnitTree;)V", "parent", "getParent", "()Lorg/jetbrains/kotlin/javac/resolve/FieldScope;", "findField", "Lorg/jetbrains/kotlin/load/java/structure/JavaField;", "javaClass", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "name", "Lorg/jetbrains/kotlin/name/Name;", "staticImports", "", "", "fieldName", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class StaticImportFieldScope extends FieldScope {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public StaticImportFieldScope(JavacWrapper javac, CompilationUnitTree compilationUnit) {
        super(javac, compilationUnit);
        Intrinsics.checkNotNullParameter(javac, "javac");
        Intrinsics.checkNotNullParameter(compilationUnit, "compilationUnit");
    }

    @Override // org.jetbrains.kotlin.javac.resolve.FieldScope
    public FieldScope getParent() {
        return new StaticImportOnDemandFieldScope(getJavac(), getCompilationUnit());
    }

    @Override // org.jetbrains.kotlin.javac.resolve.FieldScope
    public JavaField findField(JavaClass javaClass, Name name2) {
        Intrinsics.checkNotNullParameter(javaClass, "javaClass");
        Intrinsics.checkNotNullParameter(name2, "name");
        String strAsString = name2.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        Set set = CollectionsKt.toSet(staticImports(strAsString));
        if (set.isEmpty()) {
            set = null;
        }
        if (set == null) {
            return getParent().findField(javaClass, name2);
        }
        String str = (String) CollectionsKt.singleOrNull(set);
        if (str == null) {
            return null;
        }
        JavaClass javaClassFindImport = getHelper().findImport(CollectionsKt.dropLast(StringsKt.split$default((CharSequence) str, new String[]{"."}, false, 0, 6, (Object) null), 1));
        if (javaClassFindImport instanceof MockKotlinClassifier) {
            String strAsString2 = name2.asString();
            Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
            return ((MockKotlinClassifier) javaClassFindImport).findField(strAsString2);
        }
        if (javaClassFindImport != null) {
            return FieldScope.findFieldIncludingSupertypes$default(this, javaClassFindImport, name2, null, 2, null);
        }
        return null;
    }

    private final List<String> staticImports(String fieldName) {
        com.sun.tools.javac.util.Name name2;
        JCTree.JCCompilationUnit compilationUnit = getCompilationUnit();
        Intrinsics.checkNotNull(compilationUnit, "null cannot be cast to non-null type com.sun.tools.javac.tree.JCTree.JCCompilationUnit");
        Iterable imports = compilationUnit.getImports();
        Intrinsics.checkNotNullExpressionValue(imports, "getImports(...)");
        ArrayList arrayList = new ArrayList();
        for (Object obj : imports) {
            if (((JCTree.JCImport) obj).staticImport) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            JCTree.JCFieldAccess qualifiedIdentifier = ((JCTree.JCImport) it.next()).getQualifiedIdentifier();
            JCTree.JCFieldAccess jCFieldAccess = qualifiedIdentifier instanceof JCTree.JCFieldAccess ? qualifiedIdentifier : null;
            String string = Intrinsics.areEqual((jCFieldAccess == null || (name2 = jCFieldAccess.name) == null) ? null : name2.toString(), fieldName) ? jCFieldAccess.toString() : null;
            if (string != null) {
                arrayList2.add(string);
            }
        }
        return arrayList2;
    }
}
