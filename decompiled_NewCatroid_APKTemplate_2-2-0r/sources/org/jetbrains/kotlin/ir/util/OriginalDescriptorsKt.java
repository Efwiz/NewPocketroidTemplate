package org.jetbrains.kotlin.ir.util;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.ir.descriptors.IrBasedTypeParameterDescriptor;

/* JADX INFO: compiled from: originalDescriptors.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00018F¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"originalTypeParameter", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "getOriginalTypeParameter", "(Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;)Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "ir.tree"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class OriginalDescriptorsKt {
    public static final TypeParameterDescriptor getOriginalTypeParameter(TypeParameterDescriptor typeParameterDescriptor) {
        TypeParameterDescriptor typeParameterDescriptor2;
        Intrinsics.checkNotNullParameter(typeParameterDescriptor, "<this>");
        if (typeParameterDescriptor instanceof IrBasedTypeParameterDescriptor) {
            return ((IrBasedTypeParameterDescriptor) typeParameterDescriptor).getOriginal();
        }
        DeclarationDescriptor original = typeParameterDescriptor.getModuleDescriptor().getOriginal();
        Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
        if (original instanceof ClassifierDescriptorWithTypeParameters) {
            typeParameterDescriptor2 = ((ClassifierDescriptorWithTypeParameters) original).getDeclaredTypeParameters().get(typeParameterDescriptor.getIndex());
        } else if (original instanceof CallableDescriptor) {
            typeParameterDescriptor2 = ((CallableDescriptor) original).getTypeParameters().get(typeParameterDescriptor.getIndex());
        } else {
            throw new AssertionError("Unexpected type parameter container: " + original);
        }
        Intrinsics.checkNotNull(typeParameterDescriptor2);
        return typeParameterDescriptor2;
    }
}
