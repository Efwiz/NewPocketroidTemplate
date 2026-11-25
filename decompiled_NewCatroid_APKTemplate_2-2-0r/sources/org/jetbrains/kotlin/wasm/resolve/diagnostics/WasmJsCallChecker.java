package org.jetbrains.kotlin.wasm.resolve.diagnostics;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorUtilKt;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory0;
import org.jetbrains.kotlin.js.common.IdentifierPolicyKt;
import org.jetbrains.kotlin.js.resolve.diagnostics.ErrorsJs;
import org.jetbrains.kotlin.js.resolve.diagnostics.JsCallChecker;
import org.jetbrains.kotlin.js.resolve.diagnostics.SourceLocationUtilsKt;
import org.jetbrains.kotlin.psi.KtCallExpression;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtValueArgument;
import org.jetbrains.kotlin.psi.KtValueArgumentList;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.TemporaryBindingTrace;
import org.jetbrains.kotlin.resolve.calls.checkers.CallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.CallCheckerContext;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.constants.evaluate.ConstantExpressionEvaluator;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.wasm.util.JsCodeUtilsKt;

/* JADX INFO: compiled from: WasmJsCodeCallsChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J$\u0010\u0005\u001a\u00020\u00062\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/wasm/resolve/diagnostics/WasmJsCallChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "constantExpressionEvaluator", "Lorg/jetbrains/kotlin/resolve/constants/evaluate/ConstantExpressionEvaluator;", "(Lorg/jetbrains/kotlin/resolve/constants/evaluate/ConstantExpressionEvaluator;)V", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "wasm.frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WasmJsCallChecker implements CallChecker {
    private final ConstantExpressionEvaluator constantExpressionEvaluator;

    public WasmJsCallChecker(ConstantExpressionEvaluator constantExpressionEvaluator) {
        Intrinsics.checkNotNullParameter(constantExpressionEvaluator, "constantExpressionEvaluator");
        this.constantExpressionEvaluator = constantExpressionEvaluator;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        KtValueArgument ktValueArgument;
        KtExpression argumentExpression;
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        if (context.isAnnotationContext() || !JsCallChecker.INSTANCE.isJsCall(resolvedCall)) {
            return;
        }
        DeclarationDescriptor ownerDescriptor = context.getScope().getOwnerDescriptor();
        boolean z = ownerDescriptor instanceof FunctionDescriptor;
        if ((!z && !(ownerDescriptor instanceof PropertyDescriptor)) || !DescriptorUtilKt.isTopLevelInPackage(ownerDescriptor)) {
            context.getTrace().report(ErrorsWasm.JSCODE_WRONG_CONTEXT.on(reportOn));
        } else if (z) {
            FunctionDescriptor functionDescriptor = (FunctionDescriptor) ownerDescriptor;
            BindingContext context2 = context.getTrace().getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "getBindingContext(...)");
            if (!JsCodeUtilsKt.hasValidJsCodeBody(functionDescriptor, context2)) {
                context.getTrace().report(ErrorsWasm.JSCODE_WRONG_CONTEXT.on(reportOn));
            } else {
                if (functionDescriptor.isSuspend()) {
                    context.getTrace().report(ErrorsWasm.JSCODE_UNSUPPORTED_FUNCTION_KIND.on(reportOn, "suspend function"));
                }
                if (functionDescriptor.isInline()) {
                    context.getTrace().report(ErrorsWasm.JSCODE_UNSUPPORTED_FUNCTION_KIND.on(reportOn, "inline function"));
                }
                if (functionDescriptor.getExtensionReceiverParameter() != null) {
                    context.getTrace().report(ErrorsWasm.JSCODE_UNSUPPORTED_FUNCTION_KIND.on(reportOn, "function with extension receiver"));
                }
                for (ValueParameterDescriptor valueParameterDescriptor : functionDescriptor.getValueParameters()) {
                    String identifierOrNullIfSpecial = valueParameterDescriptor.getShortName().getIdentifierOrNullIfSpecial();
                    if (identifierOrNullIfSpecial == null || !IdentifierPolicyKt.isValidES5Identifier(identifierOrNullIfSpecial)) {
                        BindingTrace trace = context.getTrace();
                        DiagnosticFactory0<PsiElement> diagnosticFactory0 = ErrorsWasm.JSCODE_INVALID_PARAMETER_NAME;
                        Intrinsics.checkNotNull(valueParameterDescriptor);
                        PsiElement psiElementFindPsi = SourceLocationUtilsKt.findPsi(valueParameterDescriptor);
                        if (psiElementFindPsi == null) {
                            psiElementFindPsi = reportOn;
                        }
                        trace.report(diagnosticFactory0.on(psiElementFindPsi));
                    }
                }
            }
        } else if (ownerDescriptor instanceof PropertyDescriptor) {
            BindingContext context3 = context.getTrace().getContext();
            Intrinsics.checkNotNullExpressionValue(context3, "getBindingContext(...)");
            if (!JsCodeUtilsKt.hasValidJsCodeBody((PropertyDescriptor) ownerDescriptor, context3)) {
                context.getTrace().report(ErrorsWasm.JSCODE_WRONG_CONTEXT.on(reportOn));
            }
        }
        KtElement callElement = resolvedCall.getCall().getCallElement();
        Intrinsics.checkNotNullExpressionValue(callElement, "getCallElement(...)");
        if (callElement instanceof KtCallExpression) {
            KtValueArgumentList valueArgumentList = ((KtCallExpression) callElement).getValueArgumentList();
            List<KtValueArgument> arguments = valueArgumentList != null ? valueArgumentList.getArguments() : null;
            if (arguments == null || (ktValueArgument = (KtValueArgument) CollectionsKt.firstOrNull((List) arguments)) == null || (argumentExpression = ktValueArgument.getArgumentExpression()) == null) {
                return;
            }
            TemporaryBindingTrace temporaryBindingTraceCreate = TemporaryBindingTrace.create(context.getTrace(), "WasmJsCallChecker");
            Intrinsics.checkNotNullExpressionValue(temporaryBindingTraceCreate, "create(...)");
            if (JsCallChecker.INSTANCE.extractStringValue(this.constantExpressionEvaluator.evaluateExpression(argumentExpression, temporaryBindingTraceCreate, TypeUtils.NO_EXPECTED_TYPE)) == null) {
                context.getTrace().report(ErrorsJs.JSCODE_ARGUMENT_SHOULD_BE_CONSTANT.on(argumentExpression));
            } else {
                temporaryBindingTraceCreate.commit();
            }
        }
    }
}
