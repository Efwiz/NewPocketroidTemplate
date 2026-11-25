package org.jetbrains.kotlin.resolve.calls.checkers;

import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory0;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.KtCallableReferenceExpression;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.resolve.OverridingUtil;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExpressionReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;

/* JADX INFO: compiled from: LateinitIntrinsicApplicabilityChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J$\u0010\u0006\u001a\u00020\u00072\n\u0010\b\u001a\u0006\u0012\u0002\b\u00030\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J\u0018\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\f\u001a\u00020\rH\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0005¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/checkers/LateinitIntrinsicApplicabilityChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "isWarningInPre19", "", "(Z)V", "()Z", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "isBackingFieldAccessible", "descriptor", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LateinitIntrinsicApplicabilityChecker implements CallChecker {
    private final boolean isWarningInPre19;

    public LateinitIntrinsicApplicabilityChecker(boolean z) {
        this.isWarningInPre19 = z;
    }

    /* JADX INFO: renamed from: isWarningInPre19, reason: from getter */
    public final boolean getIsWarningInPre19() {
        return this.isWarningInPre19;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        ReceiverParameterDescriptor extensionReceiverParameter;
        Annotations annotations;
        KtExpression expression;
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
        if (Intrinsics.areEqual(resultingDescriptor.getShortName().asString(), "isInitialized") && (extensionReceiverParameter = resultingDescriptor.getExtensionReceiverParameter()) != null && (annotations = extensionReceiverParameter.getAnnotations()) != null && annotations.hasAnnotation(StandardNames.FqNames.accessibleLateinitPropertyLiteral)) {
            ReceiverValue receiverValueMo7305getExtensionReceiver = resolvedCall.getExtensionReceiver();
            ExpressionReceiver expressionReceiver = receiverValueMo7305getExtensionReceiver instanceof ExpressionReceiver ? (ExpressionReceiver) receiverValueMo7305getExtensionReceiver : null;
            KtExpression ktExpressionSafeDeparenthesize = (expressionReceiver == null || (expression = expressionReceiver.getExpression()) == null) ? null : KtPsiUtil.safeDeparenthesize(expression);
            if (!(ktExpressionSafeDeparenthesize instanceof KtCallableReferenceExpression)) {
                context.getTrace().report(((DiagnosticFactory0) check$chooseDiagnostic(this, context, Errors.LATEINIT_INTRINSIC_CALL_ON_NON_LITERAL_WARNING, Errors.LATEINIT_INTRINSIC_CALL_ON_NON_LITERAL)).on(reportOn));
                return;
            }
            KtSimpleNameExpression callableReference = ((KtCallableReferenceExpression) ktExpressionSafeDeparenthesize).getCallableReference();
            BindingContext context2 = context.getTrace().getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "getBindingContext(...)");
            ResolvedCall<? extends CallableDescriptor> resolvedCall2 = CallUtilKt.getResolvedCall(callableReference, context2);
            if (resolvedCall2 == null) {
                return;
            }
            CallableDescriptor resultingDescriptor2 = resolvedCall2.getResultingDescriptor();
            if (!(resultingDescriptor2 instanceof PropertyDescriptor)) {
                throw new IllegalStateException(("Lateinit intrinsic is incorrectly resolved not to a property: " + resultingDescriptor2).toString());
            }
            PropertyDescriptor propertyDescriptor = (PropertyDescriptor) resultingDescriptor2;
            if (!propertyDescriptor.isLateInit()) {
                context.getTrace().report(((DiagnosticFactory0) check$chooseDiagnostic(this, context, Errors.LATEINIT_INTRINSIC_CALL_ON_NON_LATEINIT_WARNING, Errors.LATEINIT_INTRINSIC_CALL_ON_NON_LATEINIT)).on(reportOn));
                return;
            }
            if (!isBackingFieldAccessible(propertyDescriptor, context)) {
                context.getTrace().report(((DiagnosticFactory1) check$chooseDiagnostic(this, context, Errors.LATEINIT_INTRINSIC_CALL_ON_NON_ACCESSIBLE_PROPERTY_WARNING, Errors.LATEINIT_INTRINSIC_CALL_ON_NON_ACCESSIBLE_PROPERTY)).on(reportOn, resultingDescriptor2));
                return;
            }
            DeclarationDescriptor ownerDescriptor = context.getScope().getOwnerDescriptor();
            FunctionDescriptor functionDescriptor = ownerDescriptor instanceof FunctionDescriptor ? (FunctionDescriptor) ownerDescriptor : null;
            if (functionDescriptor == null || !functionDescriptor.isInline()) {
                return;
            }
            context.getTrace().report(((DiagnosticFactory0) check$chooseDiagnostic(this, context, Errors.LATEINIT_INTRINSIC_CALL_IN_INLINE_FUNCTION_WARNING, Errors.LATEINIT_INTRINSIC_CALL_IN_INLINE_FUNCTION)).on(reportOn));
        }
    }

    private static final <T> T check$chooseDiagnostic(LateinitIntrinsicApplicabilityChecker lateinitIntrinsicApplicabilityChecker, CallCheckerContext callCheckerContext, T t, T t2) {
        return (!lateinitIntrinsicApplicabilityChecker.isWarningInPre19 || callCheckerContext.getLanguageVersionSettings().supportsFeature(LanguageFeature.NativeJsProhibitLateinitIsInitializedIntrinsicWithoutPrivateAccess)) ? t2 : t;
    }

    private final boolean isBackingFieldAccessible(PropertyDescriptor descriptor, CallCheckerContext context) {
        KtFile containingFile;
        KtFile containingFile2;
        Set setFilterOutOverridden = OverridingUtil.filterOutOverridden(OverridingUtil.getOverriddenDeclarations(descriptor));
        Intrinsics.checkNotNullExpressionValue(setFilterOutOverridden, "filterOutOverridden(...)");
        CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) CollectionsKt.singleOrNull(setFilterOutOverridden);
        if (callableMemberDescriptor == null || (containingFile = DescriptorToSourceUtils.getContainingFile(callableMemberDescriptor)) == null || (containingFile2 = DescriptorToSourceUtils.getContainingFile(context.getScope().getOwnerDescriptor())) == null || !Intrinsics.areEqual(containingFile, containingFile2)) {
            return false;
        }
        Sequence sequenceGenerateSequence = SequencesKt.generateSequence(context.getScope().getOwnerDescriptor(), AnonymousClass1.INSTANCE);
        DeclarationDescriptor containingDeclaration = callableMemberDescriptor.getContainingModule();
        Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
        return SequencesKt.contains(sequenceGenerateSequence, containingDeclaration);
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.resolve.calls.checkers.LateinitIntrinsicApplicabilityChecker$isBackingFieldAccessible$1, reason: invalid class name */
    /* JADX INFO: compiled from: LateinitIntrinsicApplicabilityChecker.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass1 extends FunctionReference implements Function1<DeclarationDescriptor, DeclarationDescriptor> {
        public static final AnonymousClass1 INSTANCE = new AnonymousClass1();

        AnonymousClass1() {
            super(1);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "getContainingDeclaration()Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "getContainingDeclaration";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(DeclarationDescriptor.class);
        }

        @Override // kotlin.jvm.functions.Function1
        public final DeclarationDescriptor invoke(DeclarationDescriptor p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return p0.getContainingModule();
        }
    }
}
