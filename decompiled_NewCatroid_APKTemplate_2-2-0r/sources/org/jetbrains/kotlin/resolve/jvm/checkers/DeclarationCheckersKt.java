package org.jetbrains.kotlin.resolve.jvm.checkers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.resolve.DescriptorUtils;

/* JADX INFO: compiled from: declarationCheckers.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0000¨\u0006\u0003"}, d2 = {"isInsideCompanionObjectOfInterface", "", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "frontend.java"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DeclarationCheckersKt {
    public static final boolean isInsideCompanionObjectOfInterface(CallableMemberDescriptor callableMemberDescriptor) {
        Intrinsics.checkNotNullParameter(callableMemberDescriptor, "<this>");
        DeclarationDescriptor containingDeclaration = callableMemberDescriptor.getPackageFragment();
        ClassDescriptor classDescriptor = containingDeclaration instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration : null;
        if (classDescriptor == null || !DescriptorUtils.isCompanionObject(classDescriptor)) {
            return false;
        }
        DeclarationDescriptor packageFragment = classDescriptor.getPackageFragment();
        ClassDescriptor classDescriptor2 = packageFragment instanceof ClassDescriptor ? (ClassDescriptor) packageFragment : null;
        ClassKind kind = classDescriptor2 != null ? classDescriptor2.getKind() : null;
        return kind == ClassKind.INTERFACE || kind == ClassKind.ANNOTATION_CLASS;
    }
}
