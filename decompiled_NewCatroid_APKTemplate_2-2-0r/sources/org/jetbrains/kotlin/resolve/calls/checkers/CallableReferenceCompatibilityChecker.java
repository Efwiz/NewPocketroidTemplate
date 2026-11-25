package org.jetbrains.kotlin.resolve.calls.checkers;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtCallableReferenceExpression;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedValueArgument;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;

/* JADX INFO: compiled from: CallableReferenceCompatibilityChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/checkers/CallableReferenceCompatibilityChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "()V", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CallableReferenceCompatibilityChecker implements CallChecker {
    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        LanguageFeature languageFeature = LanguageFeature.TypeInferenceOnGenericsForCallableReferences;
        if (context.getLanguageVersionSettings().supportsFeature(languageFeature)) {
            return;
        }
        Map<ValueParameterDescriptor, ResolvedValueArgument> valueArguments = resolvedCall.getValueArguments();
        Intrinsics.checkNotNullExpressionValue(valueArguments, "getValueArguments(...)");
        Iterator<Map.Entry<ValueParameterDescriptor, ResolvedValueArgument>> it = valueArguments.entrySet().iterator();
        while (it.hasNext()) {
            Iterator<ValueArgument> it2 = it.next().getValue().getArguments().iterator();
            while (it2.hasNext()) {
                KtExpression argumentExpression = it2.next().getArgumentExpression();
                KtCallableReferenceExpression ktCallableReferenceExpression = argumentExpression instanceof KtCallableReferenceExpression ? (KtCallableReferenceExpression) argumentExpression : null;
                if (ktCallableReferenceExpression != null) {
                    KtSimpleNameExpression callableReference = ktCallableReferenceExpression.getCallableReference();
                    BindingContext context2 = context.getTrace().getContext();
                    Intrinsics.checkNotNullExpressionValue(context2, "getBindingContext(...)");
                    ResolvedCall<? extends CallableDescriptor> resolvedCall2 = CallUtilKt.getResolvedCall(callableReference, context2);
                    if (resolvedCall2 != null) {
                        Call call = resolvedCall2.getCall();
                        Intrinsics.checkNotNullExpressionValue(call, "getCall(...)");
                        if (CallUtilKt.isCallableReference(call)) {
                            List<TypeParameterDescriptor> typeParameters = resolvedCall2.get$suspendPropertyDescriptor().getTypeParameters();
                            Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
                            if (!typeParameters.isEmpty()) {
                                context.getTrace().report(Errors.UNSUPPORTED_FEATURE.on(ktCallableReferenceExpression, TuplesKt.to(languageFeature, context.getLanguageVersionSettings())));
                            }
                        }
                    }
                }
            }
        }
    }
}
