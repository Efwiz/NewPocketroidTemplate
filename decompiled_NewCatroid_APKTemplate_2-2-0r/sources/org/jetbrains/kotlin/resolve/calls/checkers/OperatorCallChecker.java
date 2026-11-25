package org.jetbrains.kotlin.resolve.calls.checkers;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticSink;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtArrayAccessExpression;
import org.jetbrains.kotlin.psi.KtDestructuringDeclarationEntry;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtOperationReferenceExpression;
import org.jetbrains.kotlin.psi.KtTypeProjection;
import org.jetbrains.kotlin.resolve.calls.CallTransformer;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.VariableAsFunctionResolvedCall;
import org.jetbrains.kotlin.resolve.calls.tasks.DynamicCallsKt;
import org.jetbrains.kotlin.resolve.calls.util.CallResolverUtilKt;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.error.ErrorUtils;

/* JADX INFO: compiled from: OperatorCallChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u000b2\u00020\u0001:\u0001\u000bB\u0005¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/checkers/OperatorCallChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "()V", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "Companion", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class OperatorCallChecker implements CallChecker {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
        FunctionDescriptor functionDescriptor = resultingDescriptor instanceof FunctionDescriptor ? (FunctionDescriptor) resultingDescriptor : null;
        if (functionDescriptor == null) {
            return;
        }
        Companion companion = INSTANCE;
        if (companion.checkNotErrorOrDynamic(functionDescriptor)) {
            KtExpression calleeExpression = resolvedCall.getCall().getCalleeExpression();
            KtExpression callElement = calleeExpression != null ? calleeExpression : resolvedCall.getCall().getCallElement();
            Intrinsics.checkNotNull(callElement);
            Call call = resolvedCall.getCall();
            Intrinsics.checkNotNullExpressionValue(call, "getCall(...)");
            if ((resolvedCall instanceof VariableAsFunctionResolvedCall) && (call instanceof CallTransformer.CallForImplicitInvoke)) {
                CallTransformer.CallForImplicitInvoke callForImplicitInvoke = (CallTransformer.CallForImplicitInvoke) call;
                if (callForImplicitInvoke.itIsVariableAsFunctionCall) {
                    Call outerCall = callForImplicitInvoke.getOuterCall();
                    Intrinsics.checkNotNullExpressionValue(outerCall, "getOuterCall(...)");
                    if (CallResolverUtilKt.isConventionCall(outerCall)) {
                        DeclarationDescriptor declarationDescriptor = functionDescriptor.getContainingModule();
                        Intrinsics.checkNotNullExpressionValue(declarationDescriptor, "getContainingDeclaration(...)");
                        String strAsString = DescriptorUtilsKt.getFqNameUnsafe(declarationDescriptor).asString();
                        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                        context.getTrace().report(Errors.PROPERTY_AS_OPERATOR.on(reportOn, functionDescriptor, strAsString));
                    } else if (companion.isWrongCallWithExplicitTypeArguments((VariableAsFunctionResolvedCall) resolvedCall, outerCall)) {
                        context.getTrace().report(Errors.TYPE_ARGUMENTS_NOT_ALLOWED.on((KtElement) reportOn, "on implicit invoke call"));
                    }
                }
            }
            if ((call.getCallElement() instanceof KtDestructuringDeclarationEntry) || (call instanceof CallTransformer.CallForImplicitInvoke)) {
                if (functionDescriptor.isOperator()) {
                    return;
                }
                companion.report(reportOn, functionDescriptor, context.getTrace());
                return;
            }
            boolean z = (callElement instanceof KtOperationReferenceExpression) && ((KtOperationReferenceExpression) callElement).isConventionOperator();
            if (z) {
                OperatorCallCheckerKt.checkModConvention(functionDescriptor, context.getLanguageVersionSettings(), context.getTrace(), reportOn);
            }
            if ((z || (callElement instanceof KtArrayAccessExpression)) && !functionDescriptor.isOperator()) {
                companion.report(reportOn, functionDescriptor, context.getTrace());
            }
        }
    }

    /* JADX INFO: compiled from: OperatorCallChecker.kt */
    @Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0018\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u001e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u0012¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/checkers/OperatorCallChecker$Companion;", "", "()V", "checkNotErrorOrDynamic", "", "functionDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "isWrongCallWithExplicitTypeArguments", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/VariableAsFunctionResolvedCall;", "outerCall", "Lorg/jetbrains/kotlin/psi/Call;", "report", "", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "descriptor", "sink", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticSink;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final void report(PsiElement reportOn, FunctionDescriptor descriptor, DiagnosticSink sink) {
            Intrinsics.checkNotNullParameter(reportOn, "reportOn");
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            Intrinsics.checkNotNullParameter(sink, "sink");
            if (checkNotErrorOrDynamic(descriptor)) {
                DeclarationDescriptor declarationDescriptor = descriptor.getContainingModule();
                Intrinsics.checkNotNullExpressionValue(declarationDescriptor, "getContainingDeclaration(...)");
                String strAsString = DescriptorUtilsKt.getFqNameUnsafe(declarationDescriptor).asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                sink.report(Errors.OPERATOR_MODIFIER_REQUIRED.on(reportOn, descriptor, strAsString));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean checkNotErrorOrDynamic(FunctionDescriptor functionDescriptor) {
            FunctionDescriptor functionDescriptor2 = functionDescriptor;
            return (DynamicCallsKt.isDynamic(functionDescriptor2) || ErrorUtils.isError(functionDescriptor2)) ? false : true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean isWrongCallWithExplicitTypeArguments(VariableAsFunctionResolvedCall resolvedCall, Call outerCall) {
            List<KtTypeProjection> typeArguments = outerCall.getTypeArguments();
            Intrinsics.checkNotNullExpressionValue(typeArguments, "getTypeArguments(...)");
            if (!typeArguments.isEmpty()) {
                List<TypeParameterDescriptor> typeParameters = ((FunctionDescriptor) resolvedCall.getFunctionCall().get$suspendPropertyDescriptor()).getTypeParameters();
                Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
                if (!typeParameters.isEmpty()) {
                    List<TypeParameterDescriptor> typeParameters2 = ((VariableDescriptor) resolvedCall.getVariableCall().get$suspendPropertyDescriptor()).getTypeParameters();
                    Intrinsics.checkNotNullExpressionValue(typeParameters2, "getTypeParameters(...)");
                    if (!typeParameters2.isEmpty()) {
                        return true;
                    }
                }
            }
            return false;
        }
    }
}
