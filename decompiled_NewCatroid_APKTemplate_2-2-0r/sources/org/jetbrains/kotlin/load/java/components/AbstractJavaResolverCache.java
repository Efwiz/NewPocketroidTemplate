package org.jetbrains.kotlin.load.java.components;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNamesUtilKt;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.lazy.ResolveSession;
import org.jetbrains.kotlin.resolve.lazy.ResolveSessionUtils;
import org.jetbrains.kotlin.resolve.lazy.descriptors.LazyPackageDescriptor;

/* JADX INFO: compiled from: AbstractJavaResolverCache.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0012\u0010\r\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000e\u001a\u00020\fH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u00068DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/load/java/components/AbstractJavaResolverCache;", "Lorg/jetbrains/kotlin/load/java/components/JavaResolverCache;", "resolveSession", "Lorg/jetbrains/kotlin/resolve/lazy/ResolveSession;", "(Lorg/jetbrains/kotlin/resolve/lazy/ResolveSession;)V", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "getTrace", "()Lorg/jetbrains/kotlin/resolve/BindingTrace;", "findInPackageFragments", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "fullFqName", "Lorg/jetbrains/kotlin/name/FqName;", "getClassResolvedFromSource", "fqName", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractJavaResolverCache implements JavaResolverCache {
    private final ResolveSession resolveSession;

    public AbstractJavaResolverCache(ResolveSession resolveSession) {
        Intrinsics.checkNotNullParameter(resolveSession, "resolveSession");
        this.resolveSession = resolveSession;
    }

    protected final BindingTrace getTrace() {
        BindingTrace trace = this.resolveSession.getTrace();
        Intrinsics.checkNotNullExpressionValue(trace, "<get-trace>(...)");
        return trace;
    }

    @Override // org.jetbrains.kotlin.load.java.components.JavaResolverCache
    public ClassDescriptor getClassResolvedFromSource(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        ClassDescriptor classDescriptor = (ClassDescriptor) getTrace().get(BindingContext.FQNAME_TO_CLASS_DESCRIPTOR, fqName.toUnsafe());
        if (classDescriptor != null) {
            if (classDescriptor.get$isExpect()) {
                classDescriptor = null;
            }
            if (classDescriptor != null) {
                return classDescriptor;
            }
        }
        return findInPackageFragments(fqName);
    }

    private final ClassDescriptor findInPackageFragments(FqName fullFqName) {
        FqName fqNameParent;
        ClassDescriptor classDescriptorFindClassByRelativePath;
        if (fullFqName.isRoot()) {
            fqNameParent = fullFqName;
        } else {
            fqNameParent = fullFqName.parent();
            Intrinsics.checkNotNullExpressionValue(fqNameParent, "parent(...)");
        }
        while (true) {
            LazyPackageDescriptor packageFragment = this.resolveSession.getPackageFragment(fqNameParent);
            if (packageFragment != null && (classDescriptorFindClassByRelativePath = ResolveSessionUtils.findClassByRelativePath(packageFragment.getScope(), FqNamesUtilKt.tail(fullFqName, fqNameParent))) != null) {
                return classDescriptorFindClassByRelativePath;
            }
            if (fqNameParent.isRoot()) {
                return null;
            }
            fqNameParent = fqNameParent.parent();
            Intrinsics.checkNotNullExpressionValue(fqNameParent, "parent(...)");
        }
    }
}
