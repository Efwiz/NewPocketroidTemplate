package org.jetbrains.kotlin.contracts.parsing;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.ExpressionValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedValueArgument;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: PsiContractsUtils.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\u001a\u0014\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0002\u001a\u0012\u0010\u0005\u001a\u0004\u0018\u00010\u0006*\u0006\u0012\u0002\b\u00030\u0007H\u0000\u001a\n\u0010\b\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\t\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\n\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u000b\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\f\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\r\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u000e\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u000f\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0010\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0011\u001a\u00020\u0001*\u00020\u0002¨\u0006\u0012"}, d2 = {"equalsDslDescriptor", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "dslName", "Lorg/jetbrains/kotlin/name/Name;", "firstArgumentAsExpressionOrNull", "Lorg/jetbrains/kotlin/psi/KtExpression;", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "isCallsInPlaceEffectDescriptor", "isContractCallDescriptor", "isEffectDescriptor", "isEqualsDescriptor", "isFromContractDsl", "isImpliesCallDescriptor", "isInvocationKindEnum", "isReturnsEffectDescriptor", "isReturnsNotNullDescriptor", "isReturnsWildcardDescriptor", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PsiContractsUtilsKt {
    public static final boolean isFromContractDsl(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        return declarationDescriptor.getAnnotations().hasAnnotation(ContractsDslNames.INSTANCE.getCONTRACTS_DSL_ANNOTATION_FQN());
    }

    public static final boolean isContractCallDescriptor(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        return equalsDslDescriptor(declarationDescriptor, ContractsDslNames.INSTANCE.getCONTRACT());
    }

    public static final boolean isImpliesCallDescriptor(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        return equalsDslDescriptor(declarationDescriptor, ContractsDslNames.INSTANCE.getIMPLIES());
    }

    public static final boolean isReturnsEffectDescriptor(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        return equalsDslDescriptor(declarationDescriptor, ContractsDslNames.INSTANCE.getRETURNS());
    }

    public static final boolean isReturnsNotNullDescriptor(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        return equalsDslDescriptor(declarationDescriptor, ContractsDslNames.INSTANCE.getRETURNS_NOT_NULL());
    }

    public static final boolean isReturnsWildcardDescriptor(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        return equalsDslDescriptor(declarationDescriptor, ContractsDslNames.INSTANCE.getRETURNS()) && (declarationDescriptor instanceof FunctionDescriptor) && ((FunctionDescriptor) declarationDescriptor).getValueParameters().isEmpty();
    }

    public static final boolean isEffectDescriptor(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        return equalsDslDescriptor(declarationDescriptor, ContractsDslNames.INSTANCE.getEFFECT());
    }

    public static final boolean isCallsInPlaceEffectDescriptor(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        return equalsDslDescriptor(declarationDescriptor, ContractsDslNames.INSTANCE.getCALLS_IN_PLACE());
    }

    public static final boolean isInvocationKindEnum(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        return equalsDslDescriptor(declarationDescriptor, ContractsDslNames.INSTANCE.getINVOCATION_KIND_ENUM());
    }

    public static final boolean isEqualsDescriptor(DeclarationDescriptor declarationDescriptor) {
        KotlinType returnType;
        KotlinType type;
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        if (declarationDescriptor instanceof FunctionDescriptor) {
            FunctionDescriptor functionDescriptor = (FunctionDescriptor) declarationDescriptor;
            if (Intrinsics.areEqual(functionDescriptor.getShortName(), Name.identifier(Namer.EQUALS_METHOD_NAME)) && functionDescriptor.mo6753getDispatchReceiverParameter() != null && (returnType = functionDescriptor.getReturnType()) != null && TypeUtilsKt.isBoolean(returnType)) {
                List<ValueParameterDescriptor> valueParameters = functionDescriptor.getValueParameters();
                Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
                ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) CollectionsKt.singleOrNull((List) valueParameters);
                if (valueParameterDescriptor != null && (type = valueParameterDescriptor.getType()) != null && TypeUtilsKt.isNullableAny(type)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static final KtExpression firstArgumentAsExpressionOrNull(ResolvedCall<?> resolvedCall) {
        ValueArgument valueArgument;
        Intrinsics.checkNotNullParameter(resolvedCall, "<this>");
        List<ResolvedValueArgument> valueArgumentsByIndex = resolvedCall.getValueArgumentsByIndex();
        ResolvedValueArgument resolvedValueArgument = valueArgumentsByIndex != null ? (ResolvedValueArgument) CollectionsKt.firstOrNull((List) valueArgumentsByIndex) : null;
        ExpressionValueArgument expressionValueArgument = resolvedValueArgument instanceof ExpressionValueArgument ? (ExpressionValueArgument) resolvedValueArgument : null;
        if (expressionValueArgument == null || (valueArgument = expressionValueArgument.getValueArgument()) == null) {
            return null;
        }
        return valueArgument.getArgumentExpression();
    }

    private static final boolean equalsDslDescriptor(DeclarationDescriptor declarationDescriptor, Name name2) {
        return Intrinsics.areEqual(declarationDescriptor.getShortName(), name2) && isFromContractDsl(declarationDescriptor);
    }
}
