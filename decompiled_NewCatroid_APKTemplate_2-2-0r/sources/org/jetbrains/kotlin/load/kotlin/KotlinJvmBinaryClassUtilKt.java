package org.jetbrains.kotlin.load.kotlin;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaPackageFragment;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedCallableMemberDescriptor;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedClassDescriptor;

/* JADX INFO: compiled from: kotlinJvmBinaryClassUtil.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002¨\u0006\u0003"}, d2 = {"getContainingKotlinJvmBinaryClass", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass;", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "frontend.java"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KotlinJvmBinaryClassUtilKt {
    public static final KotlinJvmBinaryClass getContainingKotlinJvmBinaryClass(CallableDescriptor callableDescriptor) {
        Intrinsics.checkNotNullParameter(callableDescriptor, "<this>");
        if (!(callableDescriptor instanceof DeserializedCallableMemberDescriptor)) {
            return null;
        }
        DeclarationDescriptor containingDeclaration = ((DeserializedCallableMemberDescriptor) callableDescriptor).getContainingDeclaration();
        Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
        if (containingDeclaration instanceof DeserializedClassDescriptor) {
            SourceElement source = ((DeserializedClassDescriptor) containingDeclaration).getSource();
            KotlinJvmBinarySourceElement kotlinJvmBinarySourceElement = source instanceof KotlinJvmBinarySourceElement ? (KotlinJvmBinarySourceElement) source : null;
            if (kotlinJvmBinarySourceElement != null) {
                return kotlinJvmBinarySourceElement.getBinaryClass();
            }
            return null;
        }
        if (!(containingDeclaration instanceof LazyJavaPackageFragment)) {
            return null;
        }
        SourceElement source2 = ((LazyJavaPackageFragment) containingDeclaration).getSource();
        KotlinJvmBinaryPackageSourceElement kotlinJvmBinaryPackageSourceElement = source2 instanceof KotlinJvmBinaryPackageSourceElement ? (KotlinJvmBinaryPackageSourceElement) source2 : null;
        if (kotlinJvmBinaryPackageSourceElement != null) {
            return kotlinJvmBinaryPackageSourceElement.getRepresentativeBinaryClass();
        }
        return null;
    }
}
