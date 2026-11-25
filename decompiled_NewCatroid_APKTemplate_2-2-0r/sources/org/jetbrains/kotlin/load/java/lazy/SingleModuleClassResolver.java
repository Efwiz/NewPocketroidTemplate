package org.jetbrains.kotlin.load.java.lazy;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.javax.inject.Inject;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.resolve.jvm.JavaDescriptorResolver;

/* JADX INFO: compiled from: ModuleClassResolver.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016R$\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004@GX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\t¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/load/java/lazy/SingleModuleClassResolver;", "Lorg/jetbrains/kotlin/load/java/lazy/ModuleClassResolver;", "()V", "<set-?>", "Lorg/jetbrains/kotlin/resolve/jvm/JavaDescriptorResolver;", "resolver", "getResolver", "()Lorg/jetbrains/kotlin/resolve/jvm/JavaDescriptorResolver;", "setResolver", "(Lorg/jetbrains/kotlin/resolve/jvm/JavaDescriptorResolver;)V", "resolveClass", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "javaClass", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SingleModuleClassResolver implements ModuleClassResolver {
    public JavaDescriptorResolver resolver;

    @Override // org.jetbrains.kotlin.load.java.lazy.ModuleClassResolver
    public ClassDescriptor resolveClass(JavaClass javaClass) {
        Intrinsics.checkNotNullParameter(javaClass, "javaClass");
        return getResolver().resolveClass(javaClass);
    }

    public final JavaDescriptorResolver getResolver() {
        JavaDescriptorResolver javaDescriptorResolver = this.resolver;
        if (javaDescriptorResolver != null) {
            return javaDescriptorResolver;
        }
        Intrinsics.throwUninitializedPropertyAccessException("resolver");
        return null;
    }

    @Inject
    public final void setResolver(JavaDescriptorResolver javaDescriptorResolver) {
        Intrinsics.checkNotNullParameter(javaDescriptorResolver, "<set-?>");
        this.resolver = javaDescriptorResolver;
    }
}
