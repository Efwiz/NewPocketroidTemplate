package org.jetbrains.kotlin.javac.resolve;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fileClasses.JvmFileClassUtilKt;
import org.jetbrains.kotlin.javac.JavacWrapper;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtFile;

/* JADX INFO: compiled from: KotlinClassifiersCache.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001b\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0012\u0010\u0015\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0016\u001a\u00020\nH\u0002J\"\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u000f2\b\u0010\u001a\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0016\u001a\u00020\nJ\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0016\u001a\u00020\nJ\u000e\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u0013R*\u0010\b\u001a\u001e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\tj\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b`\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\r\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\n\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\u0010\u001a\u001e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00040\tj\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u0004`\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0011\u001a\u0012\u0012\u0004\u0012\u00020\u00130\u0012j\b\u0012\u0004\u0012\u00020\u0013`\u0014X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/javac/resolve/KotlinClassifiersCache;", "", "sourceFiles", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "(Ljava/util/Collection;Lorg/jetbrains/kotlin/javac/JavacWrapper;)V", "classifiers", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/name/ClassId;", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "Lkotlin/collections/HashMap;", "kotlinClasses", "", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "kotlinFacadeClasses", "kotlinPackages", "Ljava/util/HashSet;", "Lorg/jetbrains/kotlin/name/FqName;", "Lkotlin/collections/HashSet;", "createClassifier", "classId", "createMockKotlinClassifier", "Lorg/jetbrains/kotlin/javac/resolve/MockKotlinClassifier;", "classifier", "ktFile", "getKotlinClassifier", "hasPackage", "", "packageFqName", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinClassifiersCache {
    private final HashMap<ClassId, JavaClass> classifiers;
    private final JavacWrapper javac;
    private final Map<ClassId, KtClassOrObject> kotlinClasses;
    private final HashMap<ClassId, KtFile> kotlinFacadeClasses;
    private final HashSet<FqName> kotlinPackages;

    public KotlinClassifiersCache(Collection<? extends KtFile> sourceFiles, JavacWrapper javac) {
        Intrinsics.checkNotNullParameter(sourceFiles, "sourceFiles");
        Intrinsics.checkNotNullParameter(javac, "javac");
        this.javac = javac;
        this.kotlinPackages = new HashSet<>();
        this.kotlinFacadeClasses = new HashMap<>();
        ArrayList arrayList = new ArrayList();
        for (KtFile ktFile : sourceFiles) {
            this.kotlinPackages.add(ktFile.getPackageFqName());
            FqName javaFileFacadeFqName = JvmFileClassUtilKt.getJavaFileFacadeFqName(ktFile);
            this.kotlinFacadeClasses.put(new ClassId(javaFileFacadeFqName.parent(), javaFileFacadeFqName.shortName()), ktFile);
            List<KtDeclaration> declarations = ktFile.getDeclarations();
            ArrayList arrayList2 = new ArrayList();
            for (Object obj : declarations) {
                if (obj instanceof KtClassOrObject) {
                    arrayList2.add(obj);
                }
            }
            ArrayList<KtClassOrObject> arrayList3 = arrayList2;
            ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList3, 10));
            for (KtClassOrObject ktClassOrObject : arrayList3) {
                arrayList4.add(TuplesKt.to(KotlinClassifiersCacheKt.computeClassId(ktClassOrObject), ktClassOrObject));
            }
            CollectionsKt.addAll(arrayList, arrayList4);
        }
        this.kotlinClasses = MapsKt.toMap(arrayList);
        this.classifiers = new HashMap<>();
    }

    public final JavaClass getKotlinClassifier(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        JavaClass javaClass = this.classifiers.get(classId);
        return javaClass == null ? createClassifier(classId) : javaClass;
    }

    public final MockKotlinClassifier createMockKotlinClassifier(KtClassOrObject classifier, KtFile ktFile, ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        MockKotlinClassifier mockKotlinClassifier = new MockKotlinClassifier(classId, classifier, ktFile, this, this.javac);
        this.classifiers.put(classId, mockKotlinClassifier);
        return mockKotlinClassifier;
    }

    public final boolean hasPackage(FqName packageFqName) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        return this.kotlinPackages.contains(packageFqName);
    }

    private final JavaClass createClassifier(ClassId classId) {
        MockKotlinClassifier mockKotlinClassifierCreateMockKotlinClassifier;
        KtFile ktFile = this.kotlinFacadeClasses.get(classId);
        if (ktFile != null) {
            return createMockKotlinClassifier(null, ktFile, classId);
        }
        if (classId.isNestedClass()) {
            JavaClass javaClass = this.classifiers.get(classId);
            if (javaClass != null) {
                return javaClass;
            }
            List<Name> listPathSegments = classId.getRelativeClassName().pathSegments();
            Intrinsics.checkNotNullExpressionValue(listPathSegments, "pathSegments(...)");
            List<Name> list = listPathSegments;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(((Name) it.next()).asString());
            }
            ArrayList arrayList2 = arrayList;
            ClassId classId2 = new ClassId(classId.getPackageFqName(), Name.identifier((String) CollectionsKt.first((List) arrayList2)));
            KtClassOrObject ktClassOrObject = this.kotlinClasses.get(classId2);
            if (ktClassOrObject == null || (mockKotlinClassifierCreateMockKotlinClassifier = createMockKotlinClassifier(ktClassOrObject, null, classId2)) == null) {
                return null;
            }
            MockKotlinClassifier mockKotlinClassifierFindInnerClass = mockKotlinClassifierCreateMockKotlinClassifier;
            Iterator it2 = CollectionsKt.drop(arrayList2, 1).iterator();
            while (it2.hasNext()) {
                Name nameIdentifier = Name.identifier((String) it2.next());
                Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                mockKotlinClassifierFindInnerClass = mockKotlinClassifierFindInnerClass.findInnerClass(nameIdentifier);
                if (mockKotlinClassifierFindInnerClass == null) {
                    return null;
                }
            }
            this.classifiers.put(classId, mockKotlinClassifierFindInnerClass);
            return mockKotlinClassifierFindInnerClass;
        }
        KtClassOrObject ktClassOrObject2 = this.kotlinClasses.get(classId);
        if (ktClassOrObject2 == null) {
            return null;
        }
        return createMockKotlinClassifier(ktClassOrObject2, null, classId);
    }
}
