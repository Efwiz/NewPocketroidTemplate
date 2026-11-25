package org.jetbrains.kotlin.load.java.lazy;

import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaTypeParameterDescriptor;
import org.jetbrains.kotlin.load.java.structure.JavaTypeParameter;
import org.jetbrains.kotlin.load.java.structure.JavaTypeParameterListOwner;
import org.jetbrains.kotlin.storage.MemoizedFunctionToNullable;
import org.jetbrains.kotlin.utils.CollectionsKt;

/* JADX INFO: compiled from: resolvers.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0012\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0013\u001a\u00020\rH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e0\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\t0\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/load/java/lazy/LazyJavaTypeParameterResolver;", "Lorg/jetbrains/kotlin/load/java/lazy/TypeParameterResolver;", "c", "Lorg/jetbrains/kotlin/load/java/lazy/LazyJavaResolverContext;", "containingDeclaration", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "typeParameterOwner", "Lorg/jetbrains/kotlin/load/java/structure/JavaTypeParameterListOwner;", "typeParametersIndexOffset", "", "(Lorg/jetbrains/kotlin/load/java/lazy/LazyJavaResolverContext;Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;Lorg/jetbrains/kotlin/load/java/structure/JavaTypeParameterListOwner;I)V", "resolve", "Lorg/jetbrains/kotlin/storage/MemoizedFunctionToNullable;", "Lorg/jetbrains/kotlin/load/java/structure/JavaTypeParameter;", "Lorg/jetbrains/kotlin/load/java/lazy/descriptors/LazyJavaTypeParameterDescriptor;", "typeParameters", "", "resolveTypeParameter", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "javaTypeParameter", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LazyJavaTypeParameterResolver implements TypeParameterResolver {
    private final LazyJavaResolverContext c;
    private final DeclarationDescriptor containingDeclaration;
    private final MemoizedFunctionToNullable<JavaTypeParameter, LazyJavaTypeParameterDescriptor> resolve;
    private final Map<JavaTypeParameter, Integer> typeParameters;
    private final int typeParametersIndexOffset;

    public LazyJavaTypeParameterResolver(LazyJavaResolverContext c, DeclarationDescriptor containingDeclaration, JavaTypeParameterListOwner typeParameterOwner, int i) {
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
        Intrinsics.checkNotNullParameter(typeParameterOwner, "typeParameterOwner");
        this.c = c;
        this.containingDeclaration = containingDeclaration;
        this.typeParametersIndexOffset = i;
        this.typeParameters = CollectionsKt.mapToIndex(typeParameterOwner.mo7258getTypeParameters());
        this.resolve = c.getStorageManager().createMemoizedFunctionWithNullableValues(new Function1<JavaTypeParameter, LazyJavaTypeParameterDescriptor>() { // from class: org.jetbrains.kotlin.load.java.lazy.LazyJavaTypeParameterResolver$resolve$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final LazyJavaTypeParameterDescriptor invoke(JavaTypeParameter typeParameter) {
                Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
                Integer num = (Integer) this.this$0.typeParameters.get(typeParameter);
                if (num == null) {
                    return null;
                }
                LazyJavaTypeParameterResolver lazyJavaTypeParameterResolver = this.this$0;
                return new LazyJavaTypeParameterDescriptor(ContextKt.copyWithNewDefaultTypeQualifiers(ContextKt.child(lazyJavaTypeParameterResolver.c, lazyJavaTypeParameterResolver), lazyJavaTypeParameterResolver.containingDeclaration.getAnnotations()), typeParameter, lazyJavaTypeParameterResolver.typeParametersIndexOffset + num.intValue(), lazyJavaTypeParameterResolver.containingDeclaration);
            }
        });
    }

    @Override // org.jetbrains.kotlin.load.java.lazy.TypeParameterResolver
    public TypeParameterDescriptor resolveTypeParameter(JavaTypeParameter javaTypeParameter) {
        Intrinsics.checkNotNullParameter(javaTypeParameter, "javaTypeParameter");
        LazyJavaTypeParameterDescriptor lazyJavaTypeParameterDescriptorInvoke = this.resolve.invoke(javaTypeParameter);
        return lazyJavaTypeParameterDescriptorInvoke != null ? lazyJavaTypeParameterDescriptorInvoke : this.c.getTypeParameterResolver().resolveTypeParameter(javaTypeParameter);
    }
}
