package org.jetbrains.kotlin.descriptors.runtime.components;

import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.descriptors.runtime.structure.ReflectJavaClass;
import org.jetbrains.kotlin.descriptors.runtime.structure.ReflectJavaPackage;
import org.jetbrains.kotlin.load.java.JavaClassFinder;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaPackage;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: ReflectJavaClassFinder.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\u0012\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0016\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\b0\f2\u0006\u0010\t\u001a\u00020\nH\u0016J\u001a\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0006H\u0016J\u0018\u0010\u0012\u001a\n\u0012\u0004\u0012\u00020\u0014\u0018\u00010\u00132\u0006\u0010\u0015\u001a\u00020\u0010H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/runtime/components/ReflectJavaClassFinder;", "Lorg/jetbrains/kotlin/load/java/JavaClassFinder;", "classLoader", "Ljava/lang/ClassLoader;", "(Ljava/lang/ClassLoader;)V", "canComputeKnownClassNamesInPackage", "", "findClass", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "request", "Lorg/jetbrains/kotlin/load/java/JavaClassFinder$Request;", "findClasses", "", "findPackage", "Lorg/jetbrains/kotlin/load/java/structure/JavaPackage;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "mayHaveAnnotations", "knownClassNamesInPackage", "", "", "packageFqName", "descriptors.runtime"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReflectJavaClassFinder implements JavaClassFinder {
    private final ClassLoader classLoader;

    @Override // org.jetbrains.kotlin.load.java.JavaClassFinder
    public boolean canComputeKnownClassNamesInPackage() {
        return false;
    }

    @Override // org.jetbrains.kotlin.load.java.JavaClassFinder
    public Set<String> knownClassNamesInPackage(FqName packageFqName) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        return null;
    }

    public ReflectJavaClassFinder(ClassLoader classLoader) {
        Intrinsics.checkNotNullParameter(classLoader, "classLoader");
        this.classLoader = classLoader;
    }

    @Override // org.jetbrains.kotlin.load.java.JavaClassFinder
    public JavaClass findClass(JavaClassFinder.Request request) {
        Intrinsics.checkNotNullParameter(request, "request");
        ClassId classId = request.getClassId();
        FqName packageFqName = classId.getPackageFqName();
        Intrinsics.checkNotNullExpressionValue(packageFqName, "getPackageFqName(...)");
        String strAsString = classId.getRelativeClassName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        String strReplace$default = StringsKt.replace$default(strAsString, '.', '$', false, 4, (Object) null);
        if (!packageFqName.isRoot()) {
            strReplace$default = packageFqName.asString() + '.' + strReplace$default;
        }
        Class<?> clsTryLoadClass = ReflectJavaClassFinderKt.tryLoadClass(this.classLoader, strReplace$default);
        if (clsTryLoadClass != null) {
            return new ReflectJavaClass(clsTryLoadClass);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.load.java.JavaClassFinder
    public List<JavaClass> findClasses(JavaClassFinder.Request request) {
        Intrinsics.checkNotNullParameter(request, "request");
        return CollectionsKt.listOfNotNull(findClass(request));
    }

    @Override // org.jetbrains.kotlin.load.java.JavaClassFinder
    public JavaPackage findPackage(FqName fqName, boolean mayHaveAnnotations) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        return new ReflectJavaPackage(fqName);
    }
}
