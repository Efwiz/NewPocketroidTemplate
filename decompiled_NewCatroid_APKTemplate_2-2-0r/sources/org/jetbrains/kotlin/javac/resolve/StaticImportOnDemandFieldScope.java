package org.jetbrains.kotlin.javac.resolve;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.tools.javac.tree.JCTree;
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
import org.jetbrains.kotlin.load.java.structure.JavaField;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: IdentifierResolver.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u001a\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u000e\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011H\u0002R\u0016\u0010\u0007\u001a\u0004\u0018\u00010\u00018VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/javac/resolve/StaticImportOnDemandFieldScope;", "Lorg/jetbrains/kotlin/javac/resolve/FieldScope;", "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "compilationUnit", "Lcom/sun/source/tree/CompilationUnitTree;", "(Lorg/jetbrains/kotlin/javac/JavacWrapper;Lcom/sun/source/tree/CompilationUnitTree;)V", "parent", "getParent", "()Lorg/jetbrains/kotlin/javac/resolve/FieldScope;", "findField", "Lorg/jetbrains/kotlin/load/java/structure/JavaField;", "javaClass", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "name", "Lorg/jetbrains/kotlin/name/Name;", "staticAsteriskImports", "", "", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class StaticImportOnDemandFieldScope extends FieldScope {
    @Override // org.jetbrains.kotlin.javac.resolve.FieldScope
    public FieldScope getParent() {
        return null;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public StaticImportOnDemandFieldScope(JavacWrapper javac, CompilationUnitTree compilationUnit) {
        super(javac, compilationUnit);
        Intrinsics.checkNotNullParameter(javac, "javac");
        Intrinsics.checkNotNullParameter(compilationUnit, "compilationUnit");
    }

    @Override // org.jetbrains.kotlin.javac.resolve.FieldScope
    public JavaField findField(JavaClass javaClass, Name name2) {
        JavaField javaFieldFindFieldIncludingSupertypes$default;
        Intrinsics.checkNotNullParameter(javaClass, "javaClass");
        Intrinsics.checkNotNullParameter(name2, "name");
        HashSet hashSet = new HashSet();
        Iterator<T> it = staticAsteriskImports().iterator();
        while (it.hasNext()) {
            JavaClass javaClassFindImport = getHelper().findImport(StringsKt.split$default((CharSequence) it.next(), new String[]{"."}, false, 0, 6, (Object) null));
            if (javaClassFindImport instanceof MockKotlinClassifier) {
                String strAsString = name2.asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                return ((MockKotlinClassifier) javaClassFindImport).findField(strAsString);
            }
            if (javaClassFindImport != null && (javaFieldFindFieldIncludingSupertypes$default = FieldScope.findFieldIncludingSupertypes$default(this, javaClassFindImport, name2, null, 2, null)) != null) {
                hashSet.add(javaFieldFindFieldIncludingSupertypes$default);
            }
        }
        return (JavaField) CollectionsKt.singleOrNull(hashSet);
    }

    private final List<String> staticAsteriskImports() {
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
            String string = ((JCTree.JCImport) it.next()).getQualifiedIdentifier().toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            String strDropLast = StringsKt.endsWith$default(string, "*", false, 2, (Object) null) ? StringsKt.dropLast(string, 2) : null;
            if (strDropLast != null) {
                arrayList2.add(strDropLast);
            }
        }
        return arrayList2;
    }
}
