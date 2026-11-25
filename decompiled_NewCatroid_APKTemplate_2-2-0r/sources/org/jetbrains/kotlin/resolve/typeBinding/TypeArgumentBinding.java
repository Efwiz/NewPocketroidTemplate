package org.jetbrains.kotlin.resolve.typeBinding;

import kotlin.Metadata;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.types.TypeProjection;

/* JADX INFO: compiled from: TypeBinding.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u0000*\n\b\u0000\u0010\u0001 \u0001*\u00020\u00022\u00020\u0003R\u0018\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0012\u0010\b\u001a\u00020\tX¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u0004\u0018\u00010\rX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0010À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/typeBinding/TypeArgumentBinding;", "P", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "", "binding", "Lorg/jetbrains/kotlin/resolve/typeBinding/TypeBinding;", "getBinding", "()Lorg/jetbrains/kotlin/resolve/typeBinding/TypeBinding;", "projection", "Lorg/jetbrains/kotlin/types/TypeProjection;", "getProjection", "()Lorg/jetbrains/kotlin/types/TypeProjection;", "typeParameter", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "getTypeParameter", "()Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface TypeArgumentBinding<P extends PsiElement> {
    TypeBinding<P> getBinding();

    TypeProjection getProjection();

    TypeParameterDescriptor getTypeParameter();
}
