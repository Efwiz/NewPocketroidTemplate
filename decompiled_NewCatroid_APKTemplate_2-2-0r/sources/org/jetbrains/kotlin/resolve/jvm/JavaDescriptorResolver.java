package org.jetbrains.kotlin.resolve.jvm;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.load.java.components.JavaResolverCache;
import org.jetbrains.kotlin.load.java.lazy.LazyJavaPackageFragmentProvider;
import org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaPackageFragment;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.LightClassOriginKind;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;

/* JADX INFO: compiled from: JavaDescriptorResolver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\fR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/JavaDescriptorResolver;", "", "packageFragmentProvider", "Lorg/jetbrains/kotlin/load/java/lazy/LazyJavaPackageFragmentProvider;", "javaResolverCache", "Lorg/jetbrains/kotlin/load/java/components/JavaResolverCache;", "(Lorg/jetbrains/kotlin/load/java/lazy/LazyJavaPackageFragmentProvider;Lorg/jetbrains/kotlin/load/java/components/JavaResolverCache;)V", "getPackageFragmentProvider", "()Lorg/jetbrains/kotlin/load/java/lazy/LazyJavaPackageFragmentProvider;", "resolveClass", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "javaClass", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaDescriptorResolver {
    private final JavaResolverCache javaResolverCache;
    private final LazyJavaPackageFragmentProvider packageFragmentProvider;

    public JavaDescriptorResolver(LazyJavaPackageFragmentProvider packageFragmentProvider, JavaResolverCache javaResolverCache) {
        Intrinsics.checkNotNullParameter(packageFragmentProvider, "packageFragmentProvider");
        Intrinsics.checkNotNullParameter(javaResolverCache, "javaResolverCache");
        this.packageFragmentProvider = packageFragmentProvider;
        this.javaResolverCache = javaResolverCache;
    }

    public final LazyJavaPackageFragmentProvider getPackageFragmentProvider() {
        return this.packageFragmentProvider;
    }

    public final ClassDescriptor resolveClass(JavaClass javaClass) {
        Intrinsics.checkNotNullParameter(javaClass, "javaClass");
        FqName fqName = javaClass.getFqName();
        if (fqName != null && javaClass.getLightClassOriginKind() == LightClassOriginKind.SOURCE) {
            return this.javaResolverCache.getClassResolvedFromSource(fqName);
        }
        JavaClass javaClassMo7256getOuterClass = javaClass.mo7256getOuterClass();
        if (javaClassMo7256getOuterClass != null) {
            ClassDescriptor classDescriptorResolveClass = resolveClass(javaClassMo7256getOuterClass);
            MemberScope unsubstitutedInnerClassesScope = classDescriptorResolveClass != null ? classDescriptorResolveClass.getUnsubstitutedInnerClassesScope() : null;
            ClassifierDescriptor contributedClassifier = unsubstitutedInnerClassesScope != null ? unsubstitutedInnerClassesScope.mo7465getContributedClassifier(javaClass.getName(), NoLookupLocation.FROM_JAVA_LOADER) : null;
            if (contributedClassifier instanceof ClassDescriptor) {
                return (ClassDescriptor) contributedClassifier;
            }
            return null;
        }
        if (fqName == null) {
            return null;
        }
        LazyJavaPackageFragmentProvider lazyJavaPackageFragmentProvider = this.packageFragmentProvider;
        FqName fqNameParent = fqName.parent();
        Intrinsics.checkNotNullExpressionValue(fqNameParent, "parent(...)");
        LazyJavaPackageFragment lazyJavaPackageFragment = (LazyJavaPackageFragment) CollectionsKt.firstOrNull((List) lazyJavaPackageFragmentProvider.getPackageFragments(fqNameParent));
        if (lazyJavaPackageFragment != null) {
            return lazyJavaPackageFragment.findClassifierByJavaClass$descriptors_jvm(javaClass);
        }
        return null;
    }
}
