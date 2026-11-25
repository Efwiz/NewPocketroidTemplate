package org.jetbrains.kotlin.javac.resolve;

import com.sun.source.tree.CompilationUnitTree;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.javac.JavacWrapper;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaClassifier;
import org.jetbrains.kotlin.load.java.structure.JavaClassifierType;
import org.jetbrains.kotlin.load.java.structure.JavaField;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: IdentifierResolver.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\"\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u001a\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H&J0\u0010\u0018\u001a\u0004\u0018\u00010\u0013*\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0018\b\u0002\u0010\u0019\u001a\u0012\u0012\u0004\u0012\u00020\u00150\u001aj\b\u0012\u0004\u0012\u00020\u0015`\u001bH\u0004R\u0014\u0010\u0004\u001a\u00020\u0005X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\nX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\u0002\u001a\u00020\u0003X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u0004\u0018\u00010\u0000X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/javac/resolve/FieldScope;", "", "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "compilationUnit", "Lcom/sun/source/tree/CompilationUnitTree;", "(Lorg/jetbrains/kotlin/javac/JavacWrapper;Lcom/sun/source/tree/CompilationUnitTree;)V", "getCompilationUnit", "()Lcom/sun/source/tree/CompilationUnitTree;", "helper", "Lorg/jetbrains/kotlin/javac/resolve/ResolveHelper;", "getHelper", "()Lorg/jetbrains/kotlin/javac/resolve/ResolveHelper;", "getJavac", "()Lorg/jetbrains/kotlin/javac/JavacWrapper;", "parent", "getParent", "()Lorg/jetbrains/kotlin/javac/resolve/FieldScope;", "findField", "Lorg/jetbrains/kotlin/load/java/structure/JavaField;", "javaClass", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "name", "Lorg/jetbrains/kotlin/name/Name;", "findFieldIncludingSupertypes", "checkedSupertypes", "Ljava/util/HashSet;", "Lkotlin/collections/HashSet;", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
abstract class FieldScope {
    private final CompilationUnitTree compilationUnit;
    private final ResolveHelper helper;
    private final JavacWrapper javac;

    public abstract JavaField findField(JavaClass javaClass, Name name2);

    public abstract FieldScope getParent();

    public FieldScope(JavacWrapper javac, CompilationUnitTree compilationUnit) {
        Intrinsics.checkNotNullParameter(javac, "javac");
        Intrinsics.checkNotNullParameter(compilationUnit, "compilationUnit");
        this.javac = javac;
        this.compilationUnit = compilationUnit;
        this.helper = new ResolveHelper(javac, compilationUnit);
    }

    protected final JavacWrapper getJavac() {
        return this.javac;
    }

    protected final CompilationUnitTree getCompilationUnit() {
        return this.compilationUnit;
    }

    protected final ResolveHelper getHelper() {
        return this.helper;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ JavaField findFieldIncludingSupertypes$default(FieldScope fieldScope, JavaClass javaClass, Name name2, HashSet hashSet, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: findFieldIncludingSupertypes");
        }
        if ((i & 2) != 0) {
            hashSet = new HashSet();
        }
        return fieldScope.findFieldIncludingSupertypes(javaClass, name2, hashSet);
    }

    /* JADX WARN: Code duplicated, block: B:27:0x008e  */
    protected final JavaField findFieldIncludingSupertypes(JavaClass javaClass, Name name2, HashSet<JavaClass> checkedSupertypes) {
        Object next;
        JavaField javaFieldFindFieldIncludingSupertypes;
        Intrinsics.checkNotNullParameter(javaClass, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(checkedSupertypes, "checkedSupertypes");
        Iterator<T> it = javaClass.mo7254getFields().iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!Intrinsics.areEqual(((JavaField) next).getName(), name2));
        JavaField javaField = (JavaField) next;
        if (javaField != null) {
            checkedSupertypes.addAll(ResolveHelperKt.collectAllSupertypes(javaClass));
            return javaField;
        }
        Collection<JavaClassifierType> supertypes = javaClass.getSupertypes();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it2 = supertypes.iterator();
        while (it2.hasNext()) {
            JavaClassifier classifier = ((JavaClassifierType) it2.next()).getClassifier();
            JavaClass javaClass2 = classifier instanceof JavaClass ? (JavaClass) classifier : null;
            if (CollectionsKt.contains(checkedSupertypes, javaClass2)) {
                javaFieldFindFieldIncludingSupertypes = null;
            } else if (javaClass2 instanceof MockKotlinClassifier) {
                String strAsString = name2.asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                javaFieldFindFieldIncludingSupertypes = ((MockKotlinClassifier) javaClass2).findField(strAsString);
            } else if (javaClass2 != null) {
                javaFieldFindFieldIncludingSupertypes = findFieldIncludingSupertypes(javaClass2, name2, checkedSupertypes);
            } else {
                javaFieldFindFieldIncludingSupertypes = null;
            }
            if (javaFieldFindFieldIncludingSupertypes != null) {
                arrayList.add(javaFieldFindFieldIncludingSupertypes);
            }
        }
        return (JavaField) CollectionsKt.singleOrNull((List) arrayList);
    }
}
