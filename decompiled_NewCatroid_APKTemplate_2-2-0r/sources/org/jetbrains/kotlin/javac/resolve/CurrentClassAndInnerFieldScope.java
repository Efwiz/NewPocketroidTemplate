package org.jetbrains.kotlin.javac.resolve;

import com.sun.source.tree.CompilationUnitTree;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.javac.JavacWrapper;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaField;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: IdentifierResolver.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\b\u0002\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0001¢\u0006\u0002\u0010\u0007J\u001a\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0012\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\r0\u0011*\u00020\rH\u0002R\u0016\u0010\u0006\u001a\u0004\u0018\u00010\u0001X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/javac/resolve/CurrentClassAndInnerFieldScope;", "Lorg/jetbrains/kotlin/javac/resolve/FieldScope;", "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "compilationUnit", "Lcom/sun/source/tree/CompilationUnitTree;", "parent", "(Lorg/jetbrains/kotlin/javac/JavacWrapper;Lcom/sun/source/tree/CompilationUnitTree;Lorg/jetbrains/kotlin/javac/resolve/FieldScope;)V", "getParent", "()Lorg/jetbrains/kotlin/javac/resolve/FieldScope;", "findField", "Lorg/jetbrains/kotlin/load/java/structure/JavaField;", "javaClass", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "name", "Lorg/jetbrains/kotlin/name/Name;", "enclosingClasses", "", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class CurrentClassAndInnerFieldScope extends FieldScope {
    private final FieldScope parent;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CurrentClassAndInnerFieldScope(JavacWrapper javac, CompilationUnitTree compilationUnit, FieldScope fieldScope) {
        super(javac, compilationUnit);
        Intrinsics.checkNotNullParameter(javac, "javac");
        Intrinsics.checkNotNullParameter(compilationUnit, "compilationUnit");
        this.parent = fieldScope;
    }

    public /* synthetic */ CurrentClassAndInnerFieldScope(JavacWrapper javacWrapper, CompilationUnitTree compilationUnitTree, StaticImportFieldScope staticImportFieldScope, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(javacWrapper, compilationUnitTree, (i & 4) != 0 ? new StaticImportFieldScope(javacWrapper, compilationUnitTree) : staticImportFieldScope);
    }

    @Override // org.jetbrains.kotlin.javac.resolve.FieldScope
    public FieldScope getParent() {
        return this.parent;
    }

    @Override // org.jetbrains.kotlin.javac.resolve.FieldScope
    public JavaField findField(JavaClass javaClass, Name name2) {
        Intrinsics.checkNotNullParameter(javaClass, "javaClass");
        Intrinsics.checkNotNullParameter(name2, "name");
        Iterator<T> it = enclosingClasses(javaClass).iterator();
        while (it.hasNext()) {
            JavaField javaFieldFindFieldIncludingSupertypes$default = FieldScope.findFieldIncludingSupertypes$default(this, (JavaClass) it.next(), name2, null, 2, null);
            if (javaFieldFindFieldIncludingSupertypes$default != null) {
                return javaFieldFindFieldIncludingSupertypes$default;
            }
        }
        FieldScope parent = getParent();
        if (parent != null) {
            return parent.findField(javaClass, name2);
        }
        return null;
    }

    private final List<JavaClass> enclosingClasses(JavaClass javaClass) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(javaClass);
        JavaClass outerClass = javaClass.getOuterClass();
        if (outerClass != null) {
            arrayList.addAll(enclosingClasses(outerClass));
        }
        return arrayList;
    }
}
