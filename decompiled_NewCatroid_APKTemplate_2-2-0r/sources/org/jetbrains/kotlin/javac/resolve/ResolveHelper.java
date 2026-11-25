package org.jetbrains.kotlin.javac.resolve;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.ExpressionTree;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.javac.JavaClassWithClassId;
import org.jetbrains.kotlin.javac.JavacWrapper;
import org.jetbrains.kotlin.load.java.JavaDescriptorVisibilities;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaClassifier;
import org.jetbrains.kotlin.load.java.structure.JavaClassifierType;
import org.jetbrains.kotlin.load.java.structure.JavaPackage;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: ResolveHelper.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u0004\u0018\u00010\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nJ2\u0010\f\u001a\u0004\u0018\u00010\b2\u0006\u0010\r\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u000f2\u0018\b\u0002\u0010\u0010\u001a\u0012\u0012\u0004\u0012\u00020\b0\u0011j\b\u0012\u0004\u0012\u00020\b`\u0012J\u0010\u0010\u0013\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0014\u001a\u00020\u0015J\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u0018\u001a\u00020\u000bJ\u001e\u0010\u0019\u001a\u0004\u0018\u00010\b2\u0006\u0010\r\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nJ\u001c\u0010\f\u001a\u0004\u0018\u00010\b*\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0002J\u0016\u0010\u001a\u001a\u0004\u0018\u00010\b*\u00020\b2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/javac/resolve/ResolveHelper;", "", "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "compilationUnit", "Lcom/sun/source/tree/CompilationUnitTree;", "(Lorg/jetbrains/kotlin/javac/JavacWrapper;Lcom/sun/source/tree/CompilationUnitTree;)V", "findImport", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "pathSegments", "", "", "findInnerOrNested", "javaClass", "name", "Lorg/jetbrains/kotlin/name/Name;", "checkedSupertypes", "Ljava/util/HashSet;", "Lkotlin/collections/HashSet;", "findJavaOrKotlinClass", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "findPackage", "Lorg/jetbrains/kotlin/name/FqName;", "packageName", "getJavaClassFromPathSegments", "findVisibleInnerOrNestedClass", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ResolveHelper {
    private final CompilationUnitTree compilationUnit;
    private final JavacWrapper javac;

    public ResolveHelper(JavacWrapper javac, CompilationUnitTree compilationUnit) {
        Intrinsics.checkNotNullParameter(javac, "javac");
        Intrinsics.checkNotNullParameter(compilationUnit, "compilationUnit");
        this.javac = javac;
        this.compilationUnit = compilationUnit;
    }

    public final JavaClass getJavaClassFromPathSegments(JavaClass javaClass, List<String> pathSegments) {
        Intrinsics.checkNotNullParameter(javaClass, "javaClass");
        Intrinsics.checkNotNullParameter(pathSegments, "pathSegments");
        return pathSegments.size() == 1 ? javaClass : findInnerOrNested(javaClass, CollectionsKt.drop(pathSegments, 1));
    }

    public final JavaClass findImport(List<String> pathSegments) {
        Intrinsics.checkNotNullParameter(pathSegments, "pathSegments");
        int i = 0;
        for (Object obj : pathSegments) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            if (i == CollectionsKt.getLastIndex(pathSegments)) {
                return null;
            }
            FqName fqNameFindPackage = findPackage(CollectionsKt.joinToString$default(CollectionsKt.dropLast(pathSegments, i2), ".", null, null, 0, null, null, 62, null));
            if (fqNameFindPackage != null) {
                List<String> listTakeLast = CollectionsKt.takeLast(pathSegments, i2);
                JavaClass javaClassFindJavaOrKotlinClass = findJavaOrKotlinClass(new ClassId(fqNameFindPackage, Name.identifier((String) CollectionsKt.first((List) listTakeLast))));
                if (javaClassFindJavaOrKotlinClass != null) {
                    return getJavaClassFromPathSegments(javaClassFindJavaOrKotlinClass, listTakeLast);
                }
                return null;
            }
            i = i2;
        }
        return null;
    }

    public final JavaClass findJavaOrKotlinClass(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        JavaClass javaClassFindClass$default = JavacWrapper.findClass$default(this.javac, classId, null, 2, null);
        return javaClassFindClass$default == null ? this.javac.getKotlinClassifier(classId) : javaClassFindClass$default;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ JavaClass findInnerOrNested$default(ResolveHelper resolveHelper, JavaClass javaClass, Name name2, HashSet hashSet, int i, Object obj) {
        if ((i & 4) != 0) {
            hashSet = new HashSet();
        }
        return resolveHelper.findInnerOrNested(javaClass, name2, hashSet);
    }

    public final JavaClass findInnerOrNested(JavaClass javaClass, Name name2, HashSet<JavaClass> checkedSupertypes) {
        Intrinsics.checkNotNullParameter(javaClass, "javaClass");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(checkedSupertypes, "checkedSupertypes");
        JavaClass javaClassFindVisibleInnerOrNestedClass = findVisibleInnerOrNestedClass(javaClass, name2);
        if (javaClassFindVisibleInnerOrNestedClass != null) {
            checkedSupertypes.addAll(ResolveHelperKt.collectAllSupertypes(javaClass));
            return javaClassFindVisibleInnerOrNestedClass;
        }
        Collection<JavaClassifierType> supertypes = javaClass.getSupertypes();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = supertypes.iterator();
        while (it.hasNext()) {
            JavaClassifier classifier = ((JavaClassifierType) it.next()).getClassifier();
            JavaClass javaClassFindInnerOrNested = null;
            JavaClass javaClass2 = classifier instanceof JavaClass ? (JavaClass) classifier : null;
            if (javaClass2 != null && !checkedSupertypes.contains(javaClass2)) {
                javaClassFindInnerOrNested = findInnerOrNested(javaClass2, name2, checkedSupertypes);
            }
            if (javaClassFindInnerOrNested != null) {
                arrayList.add(javaClassFindInnerOrNested);
            }
        }
        return (JavaClass) CollectionsKt.singleOrNull((List) arrayList);
    }

    public final FqName findPackage(String packageName) {
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        FqName fqName = !StringsKt.isBlank(packageName) ? new FqName(packageName) : FqName.ROOT;
        JavacWrapper javacWrapper = this.javac;
        Intrinsics.checkNotNull(fqName);
        FqName fqNameHasKotlinPackage = javacWrapper.hasKotlinPackage(fqName);
        if (fqNameHasKotlinPackage != null) {
            return fqNameHasKotlinPackage;
        }
        JavaPackage javaPackageFindPackage$default = JavacWrapper.findPackage$default(this.javac, fqName, null, 2, null);
        if (javaPackageFindPackage$default != null) {
            return javaPackageFindPackage$default.getOriginalFqName();
        }
        return null;
    }

    private final JavaClass findVisibleInnerOrNestedClass(JavaClass javaClass, Name name2) {
        String string;
        FqName packageFqName;
        JavaClassWithClassId javaClassWithClassIdFindInnerClass = javaClass.findInnerClass(name2);
        if (javaClassWithClassIdFindInnerClass == null) {
            return null;
        }
        Visibility visibility = javaClassWithClassIdFindInnerClass.getVisibility();
        if (Intrinsics.areEqual(visibility, DescriptorVisibilities.PRIVATE)) {
            javaClassWithClassIdFindInnerClass = null;
        } else if (Intrinsics.areEqual(visibility, JavaDescriptorVisibilities.PACKAGE_VISIBILITY)) {
            JavaClassWithClassId javaClassWithClassId = javaClassWithClassIdFindInnerClass instanceof JavaClassWithClassId ? (JavaClassWithClassId) javaClassWithClassIdFindInnerClass : null;
            ClassId classId = javaClassWithClassId != null ? javaClassWithClassId.getClassId() : null;
            String strAsString = (classId == null || (packageFqName = classId.getPackageFqName()) == null) ? null : packageFqName.asString();
            ExpressionTree packageName = this.compilationUnit.getPackageName();
            if (packageName == null || (string = packageName.toString()) == null) {
                string = "";
            }
            javaClassWithClassIdFindInnerClass = Intrinsics.areEqual(strAsString, string) ? (JavaClassWithClassId) javaClassWithClassIdFindInnerClass : null;
        }
        return javaClassWithClassIdFindInnerClass;
    }

    private final JavaClass findInnerOrNested(JavaClass javaClass, List<String> list) {
        Iterator<T> it = list.iterator();
        JavaClass javaClassFindInnerOrNested$default = javaClass;
        while (it.hasNext()) {
            Name nameIdentifier = Name.identifier((String) it.next());
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
            javaClassFindInnerOrNested$default = findInnerOrNested$default(this, javaClassFindInnerOrNested$default, nameIdentifier, null, 4, null);
            if (javaClassFindInnerOrNested$default == null) {
                return null;
            }
        }
        return javaClassFindInnerOrNested$default;
    }
}
