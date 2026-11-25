package org.jetbrains.kotlin.resolve.calls.checkers;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.calls.components.ArgumentsUtilsKt;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;

/* JADX INFO: compiled from: VarargWrongExecutionOrderChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/checkers/VarargWrongExecutionOrderChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "()V", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class VarargWrongExecutionOrderChecker implements CallChecker {
    public static final VarargWrongExecutionOrderChecker INSTANCE = new VarargWrongExecutionOrderChecker();

    private VarargWrongExecutionOrderChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        int iNextIndex;
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        boolean z = context.getLanguageVersionSettings().getFeatureSupport(LanguageFeature.UseCorrectExecutionOrderForVarargArguments) == LanguageFeature.State.ENABLED;
        CallableDescriptor callableDescriptor = resolvedCall.get$suspendPropertyDescriptor();
        Intrinsics.checkNotNullExpressionValue(callableDescriptor, "getCandidateDescriptor(...)");
        if (DescriptorUtilsKt.isAnnotationConstructor(callableDescriptor) || z) {
            return;
        }
        List<? extends ValueArgument> valueArguments = resolvedCall.getCall().getValueArguments();
        Intrinsics.checkNotNullExpressionValue(valueArguments, "getValueArguments(...)");
        Iterator<? extends ValueArgument> it = valueArguments.iterator();
        int i = 0;
        while (true) {
            if (!it.hasNext()) {
                i = -1;
                break;
            }
            ValueParameterDescriptor parameterForArgument = CallUtilKt.getParameterForArgument(resolvedCall, it.next());
            if (parameterForArgument != null && ArgumentsUtilsKt.isVararg(parameterForArgument)) {
                break;
            } else {
                i++;
            }
        }
        Integer numValueOf = Integer.valueOf(i);
        if (numValueOf.intValue() == -1) {
            numValueOf = null;
        }
        if (numValueOf != null) {
            int iIntValue = numValueOf.intValue();
            ListIterator<? extends ValueArgument> listIterator = valueArguments.listIterator(valueArguments.size());
            while (true) {
                if (!listIterator.hasPrevious()) {
                    iNextIndex = -1;
                    break;
                }
                ValueParameterDescriptor parameterForArgument2 = CallUtilKt.getParameterForArgument(resolvedCall, listIterator.previous());
                if (!(parameterForArgument2 != null && ArgumentsUtilsKt.isVararg(parameterForArgument2))) {
                    iNextIndex = listIterator.nextIndex();
                    break;
                }
            }
            Integer numValueOf2 = Integer.valueOf(iNextIndex);
            Integer num = numValueOf2.intValue() != -1 ? numValueOf2 : null;
            if (num == null || iIntValue > num.intValue()) {
                return;
            }
            ValueArgument valueArgument = valueArguments.get(iIntValue);
            if (valueArgument.isNamed() && (valueArgument instanceof PsiElement)) {
                context.getTrace().report(Errors.CHANGING_ARGUMENTS_EXECUTION_ORDER_FOR_NAMED_VARARGS.on((PsiElement) valueArgument));
            }
        }
    }
}
