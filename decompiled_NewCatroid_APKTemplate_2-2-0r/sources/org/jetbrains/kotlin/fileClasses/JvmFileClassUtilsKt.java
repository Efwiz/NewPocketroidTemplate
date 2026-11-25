package org.jetbrains.kotlin.fileClasses;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.load.kotlin.JvmPackagePartSource;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberDescriptor;

/* JADX INFO: compiled from: JvmFileClassUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002¨\u0006\u0003"}, d2 = {"isTopLevelInJvmMultifileClass", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "frontend.java"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JvmFileClassUtilsKt {
    public static final boolean isTopLevelInJvmMultifileClass(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        if (!(declarationDescriptor.getDeclarationDescriptor() instanceof PackageFragmentDescriptor)) {
            return false;
        }
        PsiElement psiElementDescriptorToDeclaration = DescriptorToSourceUtils.descriptorToDeclaration(declarationDescriptor);
        if (psiElementDescriptorToDeclaration instanceof KtDeclaration) {
            return JvmFileClassUtilKt.isInsideJvmMultifileClassFile((KtDeclaration) psiElementDescriptorToDeclaration);
        }
        if (declarationDescriptor instanceof DeserializedMemberDescriptor) {
            DeserializedContainerSource containerSource = ((DeserializedMemberDescriptor) declarationDescriptor).getContainerSource();
            if ((containerSource instanceof JvmPackagePartSource) && ((JvmPackagePartSource) containerSource).getFacadeClassName() != null) {
                return true;
            }
        }
        return false;
    }
}
