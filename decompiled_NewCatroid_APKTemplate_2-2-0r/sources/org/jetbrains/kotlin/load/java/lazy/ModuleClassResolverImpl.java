package org.jetbrains.kotlin.load.java.lazy;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.resolve.jvm.JavaDescriptorResolver;

/* JADX INFO: compiled from: ModuleClassResolver.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0019\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\u0010\u0006J\u0012\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\u0004H\u0016R\u001a\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/load/java/lazy/ModuleClassResolverImpl;", "Lorg/jetbrains/kotlin/load/java/lazy/ModuleClassResolver;", "descriptorResolverByJavaClass", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "Lorg/jetbrains/kotlin/resolve/jvm/JavaDescriptorResolver;", "(Lkotlin/jvm/functions/Function1;)V", "resolveClass", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "javaClass", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ModuleClassResolverImpl implements ModuleClassResolver {
    private final Function1<JavaClass, JavaDescriptorResolver> descriptorResolverByJavaClass;

    /* JADX WARN: Multi-variable type inference failed */
    public ModuleClassResolverImpl(Function1<? super JavaClass, JavaDescriptorResolver> descriptorResolverByJavaClass) {
        Intrinsics.checkNotNullParameter(descriptorResolverByJavaClass, "descriptorResolverByJavaClass");
        this.descriptorResolverByJavaClass = descriptorResolverByJavaClass;
    }

    @Override // org.jetbrains.kotlin.load.java.lazy.ModuleClassResolver
    public ClassDescriptor resolveClass(JavaClass javaClass) {
        Intrinsics.checkNotNullParameter(javaClass, "javaClass");
        return this.descriptorResolverByJavaClass.invoke(javaClass).resolveClass(javaClass);
    }
}
