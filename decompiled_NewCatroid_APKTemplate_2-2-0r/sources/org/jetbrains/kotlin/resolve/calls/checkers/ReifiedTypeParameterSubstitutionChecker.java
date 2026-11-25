package org.jetbrains.kotlin.resolve.calls.checkers;

import java.util.Map;
import kotlin.collections.CollectionsKt;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.KtTypeProjection;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.model.DefinitelyNotNullTypeMarker;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: loaded from: classes6.dex */
public class ReifiedTypeParameterSubstitutionChecker implements CallChecker {
    /* JADX WARN: Code duplicated, block: B:11:0x0021  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "reportOn";
        } else if (i == 2) {
            objArr[0] = "context";
        } else if (i == 3) {
            objArr[0] = "reportErrorOn";
        } else if (i == 4) {
            objArr[0] = "context";
        } else if (i != 5) {
            objArr[0] = "resolvedCall";
        } else {
            objArr[0] = "parameter";
        }
        objArr[1] = "org/jetbrains/kotlin/resolve/calls/checkers/ReifiedTypeParameterSubstitutionChecker";
        if (i == 3 || i == 4) {
            objArr[2] = "checkTypeArgument";
        } else if (i != 5) {
            objArr[2] = "check";
        } else {
            objArr[2] = "isTypeParameterOfKotlinArray";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement psiElement, CallCheckerContext callCheckerContext) {
        if (resolvedCall == null) {
            $$$reportNull$$$0(0);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(1);
        }
        if (callCheckerContext == null) {
            $$$reportNull$$$0(2);
        }
        for (Map.Entry<TypeParameterDescriptor, KotlinType> entry : resolvedCall.getTypeArguments().entrySet()) {
            TypeParameterDescriptor key = entry.getKey();
            KotlinType value = entry.getValue();
            ClassifierDescriptor this$0 = value.getConstructor().getThis$0();
            if (key.isReified() || isTypeParameterOfKotlinArray(key)) {
                KtTypeProjection ktTypeProjection = (KtTypeProjection) CollectionsKt.getOrNull(resolvedCall.getCall().getTypeArguments(), key.getIndex());
                checkTypeArgument(ktTypeProjection != null ? ktTypeProjection : psiElement, callCheckerContext, value, this$0, false);
                if (ktTypeProjection != null && (value instanceof DefinitelyNotNullTypeMarker)) {
                    callCheckerContext.getTrace().report(Errors.DEFINITELY_NON_NULLABLE_AS_REIFIED.on(ktTypeProjection));
                }
            }
        }
    }

    private void checkTypeArgument(PsiElement psiElement, CallCheckerContext callCheckerContext, KotlinType kotlinType, ClassifierDescriptor classifierDescriptor, boolean z) {
        DiagnosticFactory1<PsiElement, TypeParameterDescriptor> diagnosticFactory1;
        if (psiElement == null) {
            $$$reportNull$$$0(3);
        }
        if (callCheckerContext == null) {
            $$$reportNull$$$0(4);
        }
        if (classifierDescriptor instanceof TypeParameterDescriptor) {
            TypeParameterDescriptor typeParameterDescriptor = (TypeParameterDescriptor) classifierDescriptor;
            if (typeParameterDescriptor.isReified()) {
                return;
            }
            if (z) {
                diagnosticFactory1 = (DiagnosticFactory1) Errors.TYPE_PARAMETER_AS_REIFIED_ARRAY.chooseFactory(callCheckerContext.getLanguageVersionSettings());
            } else {
                diagnosticFactory1 = Errors.TYPE_PARAMETER_AS_REIFIED;
            }
            callCheckerContext.getTrace().report(diagnosticFactory1.on(psiElement, typeParameterDescriptor));
            return;
        }
        if (TypeUtilsKt.cannotBeReified(kotlinType)) {
            callCheckerContext.getTrace().report(Errors.REIFIED_TYPE_FORBIDDEN_SUBSTITUTION.on(psiElement, kotlinType));
        } else if ((classifierDescriptor instanceof ClassDescriptor) && KotlinBuiltIns.isNonPrimitiveArray((ClassDescriptor) classifierDescriptor)) {
            KotlinType nullableAnyType = kotlinType.getArguments().get(0).getNullableAnyType();
            checkTypeArgument(psiElement, callCheckerContext, nullableAnyType, nullableAnyType.getConstructor().getThis$0(), true);
        }
    }

    private static boolean isTypeParameterOfKotlinArray(TypeParameterDescriptor typeParameterDescriptor) {
        if (typeParameterDescriptor == null) {
            $$$reportNull$$$0(5);
        }
        DeclarationDescriptor containingDeclaration = typeParameterDescriptor.getContainingModule();
        return (containingDeclaration instanceof ClassDescriptor) && KotlinBuiltIns.isNonPrimitiveArray((ClassDescriptor) containingDeclaration);
    }
}
