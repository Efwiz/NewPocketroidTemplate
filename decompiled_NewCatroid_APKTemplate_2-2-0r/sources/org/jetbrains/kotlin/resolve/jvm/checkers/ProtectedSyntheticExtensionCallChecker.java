package org.jetbrains.kotlin.resolve.jvm.checkers;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilityUtils;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory3;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtQualifiedExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.calls.checkers.CallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.CallCheckerContext;
import org.jetbrains.kotlin.resolve.calls.context.CallPosition;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValueFactory;
import org.jetbrains.kotlin.resolve.calls.smartcasts.SmartCastUtilKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.synthetic.SyntheticJavaPropertyDescriptor;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: ProtectedSyntheticExtensionCallChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016JD\u0010\u000b\u001a&\u0012\u0004\u0012\u00020\r\u0012\u001c\u0012\u001a\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u000f0\u000e0\f2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/checkers/ProtectedSyntheticExtensionCallChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "()V", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "computeSuitableDescriptorAndError", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory3;", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "descriptor", "Lorg/jetbrains/kotlin/synthetic/SyntheticJavaPropertyDescriptor;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ProtectedSyntheticExtensionCallChecker implements CallChecker {
    public static final ProtectedSyntheticExtensionCallChecker INSTANCE = new ProtectedSyntheticExtensionCallChecker();

    private ProtectedSyntheticExtensionCallChecker() {
    }

    /* JADX WARN: Code duplicated, block: B:13:0x002e  */
    private final Pair<FunctionDescriptor, DiagnosticFactory3<PsiElement, DeclarationDescriptor, DescriptorVisibility, DeclarationDescriptor>> computeSuitableDescriptorAndError(SyntheticJavaPropertyDescriptor descriptor, PsiElement reportOn, CallCheckerContext context) {
        boolean z;
        CallPosition callPosition = context.getResolutionContext().callPosition;
        Intrinsics.checkNotNullExpressionValue(callPosition, "callPosition");
        if (callPosition instanceof CallPosition.PropertyAssignment) {
            KtExpression leftPart = ((CallPosition.PropertyAssignment) callPosition).getLeftPart();
            KtQualifiedExpression ktQualifiedExpression = leftPart instanceof KtQualifiedExpression ? (KtQualifiedExpression) leftPart : null;
            if (Intrinsics.areEqual(ktQualifiedExpression != null ? ktQualifiedExpression.getSelectorExpression() : null, reportOn)) {
                z = true;
            } else {
                z = false;
            }
        } else {
            z = false;
        }
        FunctionDescriptor getMethod = descriptor.getGetMethod();
        FunctionDescriptor setMethod = descriptor.getSetMethod();
        boolean z2 = context.getLanguageVersionSettings().supportsFeature(LanguageFeature.ImproveReportingDiagnosticsOnProtectedMembersOfBaseClass) && z;
        if (z2 && setMethod != null) {
            getMethod = setMethod;
        }
        return TuplesKt.to(getMethod, (!z2 || setMethod == null) ? Errors.INVISIBLE_MEMBER : Errors.INVISIBLE_SETTER);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
        if (resultingDescriptor instanceof SyntheticJavaPropertyDescriptor) {
            SyntheticJavaPropertyDescriptor syntheticJavaPropertyDescriptor = (SyntheticJavaPropertyDescriptor) resultingDescriptor;
            Pair<FunctionDescriptor, DiagnosticFactory3<PsiElement, DeclarationDescriptor, DescriptorVisibility, DeclarationDescriptor>> pairComputeSuitableDescriptorAndError = computeSuitableDescriptorAndError(syntheticJavaPropertyDescriptor, reportOn, context);
            FunctionDescriptor functionDescriptorComponent1 = pairComputeSuitableDescriptorAndError.component1();
            DiagnosticFactory3<PsiElement, DeclarationDescriptor, DescriptorVisibility, DeclarationDescriptor> diagnosticFactory3Component2 = pairComputeSuitableDescriptorAndError.component2();
            DeclarationDescriptor ownerDescriptor = context.getScope().getOwnerDescriptor();
            if (DescriptorVisibilityUtils.isVisibleIgnoringReceiver(resultingDescriptor, ownerDescriptor, context.getLanguageVersionSettings())) {
                if (resolvedCall.getDispatchReceiver() == null || resolvedCall.getExtensionReceiver() != null) {
                    ReceiverValue extensionReceiver = resolvedCall.getExtensionReceiver();
                    Intrinsics.checkNotNull(extensionReceiver, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue");
                    List listListOf = CollectionsKt.listOf(extensionReceiver.getType());
                    DataFlowInfo dataFlowInfo = context.getDataFlowInfo();
                    DataFlowValueFactory dataFlowValueFactory = context.getDataFlowValueFactory();
                    BindingContext context2 = context.getTrace().getContext();
                    Intrinsics.checkNotNullExpressionValue(context2, "getBindingContext(...)");
                    List listPlus = CollectionsKt.plus((Collection) listListOf, (Iterable) dataFlowInfo.getStableTypes(dataFlowValueFactory.createDataFlowValue(extensionReceiver, context2, context.getScope().getOwnerDescriptor()), context.getLanguageVersionSettings()));
                    if (!(listPlus instanceof Collection) || !listPlus.isEmpty()) {
                        Iterator it = listPlus.iterator();
                        while (it.hasNext()) {
                            if (DescriptorVisibilityUtils.isVisible(SmartCastUtilKt.getReceiverValueWithSmartCast(null, (KotlinType) it.next()), functionDescriptorComponent1, ownerDescriptor, context.getLanguageVersionSettings())) {
                                return;
                            }
                        }
                    }
                    context.getTrace().report(diagnosticFactory3Component2.on(reportOn, resultingDescriptor, syntheticJavaPropertyDescriptor.getVisibilityImpl(), ownerDescriptor));
                }
            }
        }
    }
}
