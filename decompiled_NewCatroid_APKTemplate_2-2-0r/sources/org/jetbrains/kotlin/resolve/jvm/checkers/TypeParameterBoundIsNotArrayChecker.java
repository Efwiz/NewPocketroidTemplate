package org.jetbrains.kotlin.resolve.jvm.checkers;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.resolve.checkers.DeclarationChecker;
import org.jetbrains.kotlin.resolve.checkers.DeclarationCheckerContext;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.ErrorsJvm;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: declarationCheckers.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/checkers/TypeParameterBoundIsNotArrayChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TypeParameterBoundIsNotArrayChecker implements DeclarationChecker {
    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        List<TypeParameterDescriptor> typeParameters;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        CallableDescriptor callableDescriptor = descriptor instanceof CallableDescriptor ? (CallableDescriptor) descriptor : null;
        if (callableDescriptor == null || (typeParameters = callableDescriptor.getTypeParameters()) == null) {
            ClassDescriptor classDescriptor = descriptor instanceof ClassDescriptor ? (ClassDescriptor) descriptor : null;
            List<TypeParameterDescriptor> declaredTypeParameters = classDescriptor != null ? classDescriptor.getDeclaredTypeParameters() : null;
            if (declaredTypeParameters == null) {
                return;
            } else {
                typeParameters = declaredTypeParameters;
            }
        }
        for (TypeParameterDescriptor typeParameterDescriptor : typeParameters) {
            List<KotlinType> upperBounds = typeParameterDescriptor.getUpperBounds();
            Intrinsics.checkNotNullExpressionValue(upperBounds, "getUpperBounds(...)");
            List<KotlinType> list = upperBounds;
            if (!(list instanceof Collection) || !list.isEmpty()) {
                for (KotlinType kotlinType : list) {
                    if (KotlinBuiltIns.isArray(kotlinType) || KotlinBuiltIns.isPrimitiveArray(kotlinType)) {
                        Intrinsics.checkNotNull(typeParameterDescriptor);
                        KtDeclaration ktDeclarationDescriptorToDeclaration = DescriptorToSourceUtils.descriptorToDeclaration(typeParameterDescriptor);
                        if (ktDeclarationDescriptorToDeclaration == null) {
                            ktDeclarationDescriptorToDeclaration = declaration;
                        }
                        context.getTrace().report(ErrorsJvm.UPPER_BOUND_CANNOT_BE_ARRAY.on(ktDeclarationDescriptorToDeclaration));
                        break;
                    }
                }
            }
        }
    }
}
