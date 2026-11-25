package org.jetbrains.kotlin.js.translate.callTranslator;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.impl.TypeAliasConstructorDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.backend.ast.metadata.SideEffectKind;
import org.jetbrains.kotlin.js.translate.reference.ReferenceTranslator;
import org.jetbrains.kotlin.js.translate.utils.AnnotationsUtils;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.js.translate.utils.TranslationUtils;
import org.jetbrains.kotlin.psi.KtSuperExpression;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExpressionReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;

/* JADX INFO: compiled from: CallInfoExtensions.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000>\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\u001a\u0012\u0010\u0016\u001a\u00020\u0006*\u00020\u00102\u0006\u0010\u0017\u001a\u00020\u0006\u001a\n\u0010\u0018\u001a\u00020\u0019*\u00020\u0010\u001a\n\u0010\u001a\u001a\u00020\u0001*\u00020\u0010\u001a\n\u0010\u001b\u001a\u00020\u001c*\u00020\u000b\u001a\n\u0010\u001d\u001a\u00020\u001c*\u00020\u0010\u001a\n\u0010\u001e\u001a\u00020\u001c*\u00020\u0002\u001a\n\u0010\u001f\u001a\u00020\u001c*\u00020\u0002\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0015\u0010\u0005\u001a\u00020\u0006*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b\"\u0015\u0010\t\u001a\u00020\n*\u00020\u000b8F¢\u0006\u0006\u001a\u0004\b\f\u0010\r\"\u0015\u0010\u000e\u001a\u00020\u000f*\u00020\u00108F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012\"\u0015\u0010\u0013\u001a\u00020\n*\u00020\u00108F¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015¨\u0006 "}, d2 = {"callableDescriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "Lorg/jetbrains/kotlin/js/translate/callTranslator/CallInfo;", "getCallableDescriptor", "(Lorg/jetbrains/kotlin/js/translate/callTranslator/CallInfo;)Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "calleeOwner", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "getCalleeOwner", "(Lorg/jetbrains/kotlin/js/translate/callTranslator/CallInfo;)Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "functionName", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "Lorg/jetbrains/kotlin/js/translate/callTranslator/FunctionCallInfo;", "getFunctionName", "(Lorg/jetbrains/kotlin/js/translate/callTranslator/FunctionCallInfo;)Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "variableDescriptor", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "Lorg/jetbrains/kotlin/js/translate/callTranslator/VariableAccessInfo;", "getVariableDescriptor", "(Lorg/jetbrains/kotlin/js/translate/callTranslator/VariableAccessInfo;)Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "variableName", "getVariableName", "(Lorg/jetbrains/kotlin/js/translate/callTranslator/VariableAccessInfo;)Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "constructAccessExpression", "ref", "getAccessDescriptor", "Lorg/jetbrains/kotlin/descriptors/PropertyAccessorDescriptor;", "getAccessDescriptorIfNeeded", "hasSpreadOperator", "", "isGetAccess", "isNative", "isSuperInvocation", "js.translator"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CallInfoExtensionsKt {
    public static final CallableDescriptor getCallableDescriptor(CallInfo callInfo) {
        Intrinsics.checkNotNullParameter(callInfo, "<this>");
        CallableDescriptor original = callInfo.getResolvedCall().getResultingDescriptor().getOriginal();
        Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
        return original instanceof TypeAliasConstructorDescriptor ? ((TypeAliasConstructorDescriptor) original).getUnderlyingConstructorDescriptor() : original;
    }

    public static final boolean isNative(CallInfo callInfo) {
        Intrinsics.checkNotNullParameter(callInfo, "<this>");
        return AnnotationsUtils.isNativeObject(getCallableDescriptor(callInfo));
    }

    public static final boolean isSuperInvocation(CallInfo callInfo) {
        Intrinsics.checkNotNullParameter(callInfo, "<this>");
        ReceiverValue receiverValueMo7304getDispatchReceiver = callInfo.getResolvedCall().mo7304getDispatchReceiver();
        return (receiverValueMo7304getDispatchReceiver instanceof ExpressionReceiver) && (((ExpressionReceiver) receiverValueMo7304getDispatchReceiver).getExpression() instanceof KtSuperExpression);
    }

    public static final JsExpression getCalleeOwner(CallInfo callInfo) {
        Intrinsics.checkNotNullParameter(callInfo, "<this>");
        DeclarationDescriptor containingDeclaration = callInfo.getResolvedCall().getResultingDescriptor().getPackageFragment();
        Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
        JsExpression jsExpressionTranslateAsValueReference = ReferenceTranslator.translateAsValueReference(containingDeclaration, callInfo.getContext());
        Intrinsics.checkNotNullExpressionValue(jsExpressionTranslateAsValueReference, "translateAsValueReference(...)");
        return jsExpressionTranslateAsValueReference;
    }

    public static final VariableDescriptor getVariableDescriptor(VariableAccessInfo variableAccessInfo) {
        Intrinsics.checkNotNullParameter(variableAccessInfo, "<this>");
        CallableDescriptor callableDescriptor = getCallableDescriptor(variableAccessInfo);
        Intrinsics.checkNotNull(callableDescriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.VariableDescriptor");
        return (VariableDescriptor) callableDescriptor;
    }

    public static final JsName getVariableName(VariableAccessInfo variableAccessInfo) {
        Intrinsics.checkNotNullParameter(variableAccessInfo, "<this>");
        JsName nameForDescriptor = variableAccessInfo.getContext().getNameForDescriptor(getVariableDescriptor(variableAccessInfo));
        Intrinsics.checkNotNullExpressionValue(nameForDescriptor, "getNameForDescriptor(...)");
        return nameForDescriptor;
    }

    public static final boolean isGetAccess(VariableAccessInfo variableAccessInfo) {
        Intrinsics.checkNotNullParameter(variableAccessInfo, "<this>");
        return variableAccessInfo.getValue() == null;
    }

    public static final PropertyAccessorDescriptor getAccessDescriptor(VariableAccessInfo variableAccessInfo) {
        Intrinsics.checkNotNullParameter(variableAccessInfo, "<this>");
        VariableDescriptor variableDescriptor = getVariableDescriptor(variableAccessInfo);
        Intrinsics.checkNotNull(variableDescriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.PropertyDescriptor");
        PropertyDescriptor propertyDescriptor = (PropertyDescriptor) variableDescriptor;
        PropertyAccessorDescriptor getter = isGetAccess(variableAccessInfo) ? propertyDescriptor.getGetter() : propertyDescriptor.getSetter();
        Intrinsics.checkNotNull(getter);
        return getter;
    }

    public static final CallableDescriptor getAccessDescriptorIfNeeded(VariableAccessInfo variableAccessInfo) {
        Intrinsics.checkNotNullParameter(variableAccessInfo, "<this>");
        if ((getVariableDescriptor(variableAccessInfo) instanceof PropertyDescriptor) && (DescriptorUtilsKt.isExtension(getVariableDescriptor(variableAccessInfo)) || TranslationUtils.shouldAccessViaFunctions(getVariableDescriptor(variableAccessInfo)))) {
            return getAccessDescriptor(variableAccessInfo);
        }
        return getVariableDescriptor(variableAccessInfo);
    }

    public static final JsExpression constructAccessExpression(VariableAccessInfo variableAccessInfo, JsExpression ref) {
        Intrinsics.checkNotNullParameter(variableAccessInfo, "<this>");
        Intrinsics.checkNotNullParameter(ref, "ref");
        if (isGetAccess(variableAccessInfo)) {
            return ref;
        }
        MetadataProperties.setSideEffects(ref, SideEffectKind.PURE);
        JsExpression value = variableAccessInfo.getValue();
        Intrinsics.checkNotNull(value);
        JsBinaryOperation jsBinaryOperationAssignment = JsAstUtils.assignment(ref, value);
        Intrinsics.checkNotNull(jsBinaryOperationAssignment);
        return jsBinaryOperationAssignment;
    }

    public static final JsName getFunctionName(FunctionCallInfo functionCallInfo) {
        Intrinsics.checkNotNullParameter(functionCallInfo, "<this>");
        JsName nameForDescriptor = functionCallInfo.getContext().getNameForDescriptor(getCallableDescriptor(functionCallInfo));
        Intrinsics.checkNotNullExpressionValue(nameForDescriptor, "getNameForDescriptor(...)");
        return nameForDescriptor;
    }

    public static final boolean hasSpreadOperator(FunctionCallInfo functionCallInfo) {
        Intrinsics.checkNotNullParameter(functionCallInfo, "<this>");
        return functionCallInfo.getArgumentsInfo().getHasSpreadOperator();
    }
}
