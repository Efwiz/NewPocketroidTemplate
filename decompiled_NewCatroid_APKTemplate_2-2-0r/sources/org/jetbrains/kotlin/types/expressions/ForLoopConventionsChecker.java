package org.jetbrains.kotlin.types.expressions;

import java.util.Collections;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.DiagnosticSink;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.calls.checkers.OperatorCallChecker;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResults;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExpressionReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.TransientReceiver;
import org.jetbrains.kotlin.types.DynamicTypesKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.error.ErrorUtils;
import org.jetbrains.kotlin.util.OperatorNameConventions;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;

/* JADX INFO: loaded from: classes6.dex */
public class ForLoopConventionsChecker {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final KotlinBuiltIns builtIns;
    private final FakeCallResolver fakeCallResolver;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
                objArr[0] = "fakeCallResolver";
                break;
            case 2:
                objArr[0] = "loopRange";
                break;
            case 3:
            case 4:
                objArr[0] = "context";
                break;
            case 5:
                objArr[0] = "loopRangeExpression";
                break;
            case 6:
                objArr[0] = "iteratorType";
                break;
            case 7:
                objArr[0] = "name";
                break;
            case 8:
                objArr[0] = "ambiguity";
                break;
            case 9:
                objArr[0] = "missing";
                break;
            case 10:
                objArr[0] = "noneApplicable";
                break;
            case 11:
                objArr[0] = "resolvedCallKey";
                break;
            default:
                objArr[0] = "builtIns";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/types/expressions/ForLoopConventionsChecker";
        switch (i) {
            case 2:
            case 3:
                objArr[2] = "checkIterableConvention";
                break;
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                objArr[2] = "checkConventionForIterator";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public ForLoopConventionsChecker(KotlinBuiltIns kotlinBuiltIns, FakeCallResolver fakeCallResolver) {
        if (kotlinBuiltIns == null) {
            $$$reportNull$$$0(0);
        }
        if (fakeCallResolver == null) {
            $$$reportNull$$$0(1);
        }
        this.builtIns = kotlinBuiltIns;
        this.fakeCallResolver = fakeCallResolver;
    }

    public KotlinType checkIterableConvention(ExpressionReceiver expressionReceiver, ExpressionTypingContext expressionTypingContext) {
        if (expressionReceiver == null) {
            $$$reportNull$$$0(2);
        }
        if (expressionTypingContext == null) {
            $$$reportNull$$$0(3);
        }
        KtExpression expression = expressionReceiver.getExpression();
        OverloadResolutionResults<FunctionDescriptor> overloadResolutionResultsResolveFakeCall = this.fakeCallResolver.resolveFakeCall(expressionTypingContext, expressionReceiver, OperatorNameConventions.ITERATOR, expression, expression, FakeCallKind.ITERATOR, Collections.emptyList());
        if (!overloadResolutionResultsResolveFakeCall.isSuccess()) {
            return null;
        }
        ResolvedCall<D> resolvedCallMo7436getResultingCall = overloadResolutionResultsResolveFakeCall.mo7436getResultingCall();
        expressionTypingContext.trace.record(BindingContext.LOOP_RANGE_ITERATOR_RESOLVED_CALL, expression, resolvedCallMo7436getResultingCall);
        FunctionDescriptor functionDescriptor = (FunctionDescriptor) resolvedCallMo7436getResultingCall.getResultingDescriptor();
        checkIfOperatorModifierPresent(expression, functionDescriptor, expressionTypingContext.trace);
        KotlinType returnType = functionDescriptor.getReturnType();
        KotlinType kotlinTypeCheckConventionForIterator = checkConventionForIterator(expressionTypingContext, expression, returnType, OperatorNameConventions.HAS_NEXT, Errors.HAS_NEXT_FUNCTION_AMBIGUITY, Errors.HAS_NEXT_MISSING, Errors.HAS_NEXT_FUNCTION_NONE_APPLICABLE, BindingContext.LOOP_RANGE_HAS_NEXT_RESOLVED_CALL);
        if (kotlinTypeCheckConventionForIterator != null && !this.builtIns.isBooleanOrSubtype(kotlinTypeCheckConventionForIterator)) {
            expressionTypingContext.trace.report(Errors.HAS_NEXT_FUNCTION_TYPE_MISMATCH.on(expression, kotlinTypeCheckConventionForIterator));
        }
        return checkConventionForIterator(expressionTypingContext, expression, returnType, OperatorNameConventions.NEXT, Errors.NEXT_AMBIGUITY, Errors.NEXT_MISSING, Errors.NEXT_NONE_APPLICABLE, BindingContext.LOOP_RANGE_NEXT_RESOLVED_CALL);
    }

    private static void checkIfOperatorModifierPresent(KtExpression ktExpression, FunctionDescriptor functionDescriptor, DiagnosticSink diagnosticSink) {
        if (ErrorUtils.isError(functionDescriptor)) {
            return;
        }
        ReceiverParameterDescriptor extensionReceiverParameter = functionDescriptor.getExtensionReceiverParameter();
        if ((extensionReceiverParameter == null || !DynamicTypesKt.isDynamic(extensionReceiverParameter.getType())) && !functionDescriptor.isOperator()) {
            OperatorCallChecker.INSTANCE.report(ktExpression, functionDescriptor, diagnosticSink);
        }
    }

    private KotlinType checkConventionForIterator(ExpressionTypingContext expressionTypingContext, KtExpression ktExpression, KotlinType kotlinType, Name name2, DiagnosticFactory1<KtExpression, KotlinType> diagnosticFactory1, DiagnosticFactory1<KtExpression, KotlinType> diagnosticFactory2, DiagnosticFactory1<KtExpression, KotlinType> diagnosticFactory3, WritableSlice<KtExpression, ResolvedCall<FunctionDescriptor>> writableSlice) {
        if (expressionTypingContext == null) {
            $$$reportNull$$$0(4);
        }
        if (ktExpression == null) {
            $$$reportNull$$$0(5);
        }
        if (kotlinType == null) {
            $$$reportNull$$$0(6);
        }
        if (name2 == null) {
            $$$reportNull$$$0(7);
        }
        if (diagnosticFactory1 == null) {
            $$$reportNull$$$0(8);
        }
        if (diagnosticFactory2 == null) {
            $$$reportNull$$$0(9);
        }
        if (diagnosticFactory3 == null) {
            $$$reportNull$$$0(10);
        }
        if (writableSlice == null) {
            $$$reportNull$$$0(11);
        }
        OverloadResolutionResults<FunctionDescriptor> overloadResolutionResultsResolveFakeCall = this.fakeCallResolver.resolveFakeCall(expressionTypingContext, new TransientReceiver(kotlinType), name2, ktExpression, ktExpression, FakeCallKind.OTHER, Collections.emptyList());
        if (overloadResolutionResultsResolveFakeCall.isAmbiguity()) {
            expressionTypingContext.trace.report(diagnosticFactory1.on(ktExpression, kotlinType));
            return null;
        }
        if (overloadResolutionResultsResolveFakeCall.isNothing()) {
            expressionTypingContext.trace.report(diagnosticFactory2.on(ktExpression, kotlinType));
            return null;
        }
        if (!overloadResolutionResultsResolveFakeCall.isSuccess()) {
            expressionTypingContext.trace.report(diagnosticFactory3.on(ktExpression, kotlinType));
            return null;
        }
        ResolvedCall<D> resolvedCallMo7436getResultingCall = overloadResolutionResultsResolveFakeCall.mo7436getResultingCall();
        expressionTypingContext.trace.record(writableSlice, ktExpression, resolvedCallMo7436getResultingCall);
        FunctionDescriptor functionDescriptor = (FunctionDescriptor) resolvedCallMo7436getResultingCall.getResultingDescriptor();
        checkIfOperatorModifierPresent(ktExpression, functionDescriptor, expressionTypingContext.trace);
        return functionDescriptor.getReturnType();
    }
}
