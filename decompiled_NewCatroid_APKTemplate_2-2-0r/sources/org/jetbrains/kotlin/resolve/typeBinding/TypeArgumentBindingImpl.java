package org.jetbrains.kotlin.resolve.typeBinding;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.types.TypeProjection;

/* JADX INFO: compiled from: TypeBinding.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\b\u0002\u0018\u0000*\n\b\u0000\u0010\u0001 \u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B%\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\t¢\u0006\u0002\u0010\nR\u001a\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0016\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/resolve/typeBinding/TypeArgumentBindingImpl;", "P", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "Lorg/jetbrains/kotlin/resolve/typeBinding/TypeArgumentBinding;", "projection", "Lorg/jetbrains/kotlin/types/TypeProjection;", "typeParameter", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "binding", "Lorg/jetbrains/kotlin/resolve/typeBinding/TypeBinding;", "(Lorg/jetbrains/kotlin/types/TypeProjection;Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;Lorg/jetbrains/kotlin/resolve/typeBinding/TypeBinding;)V", "getBinding", "()Lorg/jetbrains/kotlin/resolve/typeBinding/TypeBinding;", "getProjection", "()Lorg/jetbrains/kotlin/types/TypeProjection;", "getTypeParameter", "()Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class TypeArgumentBindingImpl<P extends PsiElement> implements TypeArgumentBinding<P> {
    private final TypeBinding<P> binding;
    private final TypeProjection projection;
    private final TypeParameterDescriptor typeParameter;

    /* JADX WARN: Multi-variable type inference failed */
    public TypeArgumentBindingImpl(TypeProjection projection, TypeParameterDescriptor typeParameterDescriptor, TypeBinding<? extends P> binding) {
        Intrinsics.checkNotNullParameter(projection, "projection");
        Intrinsics.checkNotNullParameter(binding, "binding");
        this.projection = projection;
        this.typeParameter = typeParameterDescriptor;
        this.binding = binding;
    }

    @Override // org.jetbrains.kotlin.resolve.typeBinding.TypeArgumentBinding
    public TypeProjection getProjection() {
        return this.projection;
    }

    @Override // org.jetbrains.kotlin.resolve.typeBinding.TypeArgumentBinding
    public TypeParameterDescriptor getTypeParameter() {
        return this.typeParameter;
    }

    @Override // org.jetbrains.kotlin.resolve.typeBinding.TypeArgumentBinding
    public TypeBinding<P> getBinding() {
        return this.binding;
    }
}
