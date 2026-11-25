package org.jetbrains.kotlin.resolve.checkers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: ResultClassInReturnTypeChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0002\u001a\f\u0010\u0003\u001a\u00020\u0001*\u00020\u0004H\u0000¨\u0006\u0005"}, d2 = {"isResultClass", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "isResultType", "Lorg/jetbrains/kotlin/types/KotlinType;", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ResultClassInReturnTypeCheckerKt {
    public static final boolean isResultType(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = kotlinType.getConstructor().mo7434getDeclarationDescriptor();
        return classifierDescriptorMo7434getDeclarationDescriptor != null && isResultClass(classifierDescriptorMo7434getDeclarationDescriptor);
    }

    private static final boolean isResultClass(DeclarationDescriptor declarationDescriptor) {
        DeclarationDescriptor moduleDescriptor = declarationDescriptor.getModuleDescriptor();
        return moduleDescriptor != null && (moduleDescriptor instanceof PackageFragmentDescriptor) && Intrinsics.areEqual(((PackageFragmentDescriptor) moduleDescriptor).getFqName(), StandardNames.RESULT_FQ_NAME.parent()) && Intrinsics.areEqual(declarationDescriptor.getName(), StandardNames.RESULT_FQ_NAME.shortName());
    }
}
