package org.jetbrains.kotlin.load.java.lazy;

import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.load.java.JavaTypeQualifiersByElementType;
import org.jetbrains.kotlin.load.java.lazy.types.JavaTypeResolver;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: context.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B'\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u000e\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0007¢\u0006\u0002\u0010\tB'\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u000e\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\b0\u000b¢\u0006\u0002\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001d\u0010\u000f\u001a\u0004\u0018\u00010\b8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0010\u0010\u0011R\u001c\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\b0\u000bX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0016\u001a\u00020\u00178F¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u001a\u001a\u00020\u001b8F¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0011\u0010 \u001a\u00020!¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010#¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/load/java/lazy/LazyJavaResolverContext;", "", "components", "Lorg/jetbrains/kotlin/load/java/lazy/JavaResolverComponents;", "typeParameterResolver", "Lorg/jetbrains/kotlin/load/java/lazy/TypeParameterResolver;", "typeQualifiersComputation", "Lkotlin/Function0;", "Lorg/jetbrains/kotlin/load/java/JavaTypeQualifiersByElementType;", "(Lorg/jetbrains/kotlin/load/java/lazy/JavaResolverComponents;Lorg/jetbrains/kotlin/load/java/lazy/TypeParameterResolver;Lkotlin/jvm/functions/Function0;)V", "delegateForDefaultTypeQualifiers", "Lkotlin/Lazy;", "(Lorg/jetbrains/kotlin/load/java/lazy/JavaResolverComponents;Lorg/jetbrains/kotlin/load/java/lazy/TypeParameterResolver;Lkotlin/Lazy;)V", "getComponents", "()Lorg/jetbrains/kotlin/load/java/lazy/JavaResolverComponents;", "defaultTypeQualifiers", "getDefaultTypeQualifiers", "()Lorg/jetbrains/kotlin/load/java/JavaTypeQualifiersByElementType;", "defaultTypeQualifiers$delegate", "Lkotlin/Lazy;", "getDelegateForDefaultTypeQualifiers$descriptors_jvm", "()Lkotlin/Lazy;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "getModule", "()Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "getStorageManager", "()Lorg/jetbrains/kotlin/storage/StorageManager;", "getTypeParameterResolver", "()Lorg/jetbrains/kotlin/load/java/lazy/TypeParameterResolver;", "typeResolver", "Lorg/jetbrains/kotlin/load/java/lazy/types/JavaTypeResolver;", "getTypeResolver", "()Lorg/jetbrains/kotlin/load/java/lazy/types/JavaTypeResolver;", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LazyJavaResolverContext {
    private final JavaResolverComponents components;

    /* JADX INFO: renamed from: defaultTypeQualifiers$delegate, reason: from kotlin metadata */
    private final Lazy defaultTypeQualifiers;
    private final Lazy<JavaTypeQualifiersByElementType> delegateForDefaultTypeQualifiers;
    private final TypeParameterResolver typeParameterResolver;
    private final JavaTypeResolver typeResolver;

    public LazyJavaResolverContext(JavaResolverComponents components, TypeParameterResolver typeParameterResolver, Lazy<JavaTypeQualifiersByElementType> delegateForDefaultTypeQualifiers) {
        Intrinsics.checkNotNullParameter(components, "components");
        Intrinsics.checkNotNullParameter(typeParameterResolver, "typeParameterResolver");
        Intrinsics.checkNotNullParameter(delegateForDefaultTypeQualifiers, "delegateForDefaultTypeQualifiers");
        this.components = components;
        this.typeParameterResolver = typeParameterResolver;
        this.delegateForDefaultTypeQualifiers = delegateForDefaultTypeQualifiers;
        this.defaultTypeQualifiers = delegateForDefaultTypeQualifiers;
        this.typeResolver = new JavaTypeResolver(this, typeParameterResolver);
    }

    public final JavaResolverComponents getComponents() {
        return this.components;
    }

    public final TypeParameterResolver getTypeParameterResolver() {
        return this.typeParameterResolver;
    }

    public final Lazy<JavaTypeQualifiersByElementType> getDelegateForDefaultTypeQualifiers$descriptors_jvm() {
        return this.delegateForDefaultTypeQualifiers;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public LazyJavaResolverContext(JavaResolverComponents components, TypeParameterResolver typeParameterResolver, Function0<JavaTypeQualifiersByElementType> typeQualifiersComputation) {
        this(components, typeParameterResolver, (Lazy<JavaTypeQualifiersByElementType>) LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) typeQualifiersComputation));
        Intrinsics.checkNotNullParameter(components, "components");
        Intrinsics.checkNotNullParameter(typeParameterResolver, "typeParameterResolver");
        Intrinsics.checkNotNullParameter(typeQualifiersComputation, "typeQualifiersComputation");
    }

    public final JavaTypeQualifiersByElementType getDefaultTypeQualifiers() {
        return (JavaTypeQualifiersByElementType) this.defaultTypeQualifiers.getValue();
    }

    public final JavaTypeResolver getTypeResolver() {
        return this.typeResolver;
    }

    public final StorageManager getStorageManager() {
        return this.components.getStorageManager();
    }

    public final ModuleDescriptor getModule() {
        return this.components.getModule();
    }
}
