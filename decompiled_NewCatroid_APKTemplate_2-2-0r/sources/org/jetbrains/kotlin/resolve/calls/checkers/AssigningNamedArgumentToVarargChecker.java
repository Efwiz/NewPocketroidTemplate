package org.jetbrains.kotlin.resolve.calls.checkers;

import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactoryForDeprecation0;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactoryForDeprecation1;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.calls.components.ArgumentsUtilsKt;
import org.jetbrains.kotlin.resolve.calls.context.ResolutionContext;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedValueArgument;
import org.jetbrains.kotlin.resolve.calls.util.CallResolverUtilKt;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: AssigningNamedArgumentToVarargChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J$\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\u0010H\u0002J,\u0010\u0011\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\u00102\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J$\u0010\u0014\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\u00132\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\u0010H\u0002J\f\u0010\u0015\u001a\u00020\u0016*\u00020\rH\u0002¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/checkers/AssigningNamedArgumentToVarargChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "()V", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "checkAssignmentOfSingleElementInAnnotation", "argument", "Lorg/jetbrains/kotlin/psi/ValueArgument;", "argumentExpression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "Lorg/jetbrains/kotlin/resolve/calls/context/ResolutionContext;", "checkAssignmentOfSingleElementInFunction", "parameterDescriptor", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "checkAssignmentOfSingleElementToVararg", "hasSpread", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AssigningNamedArgumentToVarargChecker implements CallChecker {
    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        Map<ValueParameterDescriptor, ResolvedValueArgument> valueArguments = resolvedCall.getValueArguments();
        Intrinsics.checkNotNullExpressionValue(valueArguments, "getValueArguments(...)");
        for (Map.Entry<ValueParameterDescriptor, ResolvedValueArgument> entry : valueArguments.entrySet()) {
            ValueParameterDescriptor key = entry.getKey();
            for (ValueArgument valueArgument : entry.getValue().getArguments()) {
                Intrinsics.checkNotNull(valueArgument);
                Intrinsics.checkNotNull(key);
                checkAssignmentOfSingleElementToVararg(valueArgument, key, context.getResolutionContext());
            }
        }
    }

    private final void checkAssignmentOfSingleElementToVararg(ValueArgument argument, ValueParameterDescriptor parameterDescriptor, ResolutionContext<?> context) {
        KtExpression argumentExpression;
        if (context.languageVersionSettings.supportsFeature(LanguageFeature.AssigningArraysToVarargsInNamedFormInAnnotations) && argument.isNamed() && ArgumentsUtilsKt.isVararg(parameterDescriptor) && (argumentExpression = argument.getArgumentExpression()) != null) {
            if (DescriptorUtilsKt.isParameterOfAnnotation(parameterDescriptor)) {
                checkAssignmentOfSingleElementInAnnotation(argument, argumentExpression, context);
            } else {
                checkAssignmentOfSingleElementInFunction(argument, argumentExpression, context, parameterDescriptor);
            }
        }
    }

    private final void checkAssignmentOfSingleElementInAnnotation(ValueArgument argument, KtExpression argumentExpression, ResolutionContext<?> context) {
        BindingTrace trace = context.trace;
        Intrinsics.checkNotNullExpressionValue(trace, "trace");
        if (CallResolverUtilKt.isArrayOrArrayLiteral(argument, trace)) {
            if (hasSpread(argument)) {
                context.trace.report(Errors.REDUNDANT_SPREAD_OPERATOR_IN_NAMED_FORM_IN_ANNOTATION.on(argumentExpression));
            }
        } else {
            BindingTrace bindingTrace = context.trace;
            DiagnosticFactoryForDeprecation0<KtExpression> diagnosticFactoryForDeprecation0 = Errors.ASSIGNING_SINGLE_ELEMENT_TO_VARARG_IN_NAMED_FORM_ANNOTATION;
            LanguageVersionSettings languageVersionSettings = context.languageVersionSettings;
            Intrinsics.checkNotNullExpressionValue(languageVersionSettings, "languageVersionSettings");
            bindingTrace.report(diagnosticFactoryForDeprecation0.on(languageVersionSettings, argumentExpression));
        }
    }

    private final void checkAssignmentOfSingleElementInFunction(ValueArgument argument, KtExpression argumentExpression, ResolutionContext<?> context, ValueParameterDescriptor parameterDescriptor) {
        if (context.languageVersionSettings.supportsFeature(LanguageFeature.AllowAssigningArrayElementsToVarargsInNamedFormForFunctions)) {
            BindingTrace trace = context.trace;
            Intrinsics.checkNotNullExpressionValue(trace, "trace");
            if (CallResolverUtilKt.isArrayOrArrayLiteral(argument, trace)) {
                if (hasSpread(argument)) {
                    context.trace.report(Errors.REDUNDANT_SPREAD_OPERATOR_IN_NAMED_FORM_IN_FUNCTION.on(argumentExpression));
                    return;
                }
                return;
            }
        }
        if (hasSpread(argument)) {
            return;
        }
        BindingTrace bindingTrace = context.trace;
        DiagnosticFactoryForDeprecation1<KtExpression, KotlinType> diagnosticFactoryForDeprecation1 = Errors.ASSIGNING_SINGLE_ELEMENT_TO_VARARG_IN_NAMED_FORM_FUNCTION;
        LanguageVersionSettings languageVersionSettings = context.languageVersionSettings;
        Intrinsics.checkNotNullExpressionValue(languageVersionSettings, "languageVersionSettings");
        bindingTrace.report(diagnosticFactoryForDeprecation1.on(languageVersionSettings, argumentExpression, parameterDescriptor.getType()));
    }

    private final boolean hasSpread(ValueArgument valueArgument) {
        return valueArgument.getSpreadElement() != null;
    }
}
