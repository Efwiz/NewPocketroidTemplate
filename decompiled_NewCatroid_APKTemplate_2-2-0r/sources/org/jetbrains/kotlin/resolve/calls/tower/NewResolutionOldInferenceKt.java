package org.jetbrains.kotlin.resolve.calls.tower;

import androidx.core.app.NotificationCompat;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.synthetic.SyntheticMemberDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.calls.context.BasicCallResolutionContext;
import org.jetbrains.kotlin.resolve.calls.context.ResolutionContext;
import org.jetbrains.kotlin.resolve.calls.results.ResolutionStatus;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValue;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValueFactory;
import org.jetbrains.kotlin.resolve.calls.util.FakeCallableDescriptorForObject;
import org.jetbrains.kotlin.resolve.scopes.HierarchicalScope;
import org.jetbrains.kotlin.resolve.scopes.ResolutionScope;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValueWithSmartCastInfo;
import org.jetbrains.kotlin.resolve.scopes.receivers.SuperCallReceiverValue;
import org.jetbrains.kotlin.resolve.scopes.utils.ScopeUtilsKt;
import org.jetbrains.kotlin.utils.addToStdlib.AddToStdlibKt;

/* JADX INFO: compiled from: NewResolutionOldInference.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000j\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0000\u001a0\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0000\u001a6\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 \u001a\f\u0010!\u001a\u00020\u0001*\u00020\u000bH\u0000\u001a\u0016\u0010\u0013\u001a\u00020\u0014*\u0006\u0012\u0002\b\u00030\"2\u0006\u0010\u001b\u001a\u00020\u001c\"\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003¨\u0006#"}, d2 = {"isSuperCall", "", "Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;", "(Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;)Z", "createPreviousResolveError", "Lorg/jetbrains/kotlin/resolve/calls/tower/PreviousResolutionError;", NotificationCompat.CATEGORY_STATUS, "Lorg/jetbrains/kotlin/resolve/calls/results/ResolutionStatus;", "reportResolvedUsingDeprecatedVisibility", "", "call", "Lorg/jetbrains/kotlin/psi/Call;", "candidateDescriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "resultingDescriptor", "diagnostic", "Lorg/jetbrains/kotlin/resolve/calls/tower/ResolvedUsingDeprecatedVisibility;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "transformToReceiverWithSmartCastInfo", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "containingDescriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "dataFlowInfo", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "receiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "dataFlowValueFactory", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValueFactory;", "isCallWithSuperReceiver", "Lorg/jetbrains/kotlin/resolve/calls/context/ResolutionContext;", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class NewResolutionOldInferenceKt {

    /* JADX INFO: compiled from: NewResolutionOldInference.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ResolutionStatus.values().length];
            try {
                iArr[ResolutionStatus.SUCCESS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ResolutionStatus.INCOMPLETE_TYPE_INFERENCE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ResolutionStatus.UNSAFE_CALL_ERROR.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[ResolutionStatus.ARGUMENTS_MAPPING_ERROR.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[ResolutionStatus.RECEIVER_TYPE_ERROR.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final ReceiverValueWithSmartCastInfo transformToReceiverWithSmartCastInfo(ResolutionContext<?> resolutionContext, ReceiverValue receiver) {
        Intrinsics.checkNotNullParameter(resolutionContext, "<this>");
        Intrinsics.checkNotNullParameter(receiver, "receiver");
        DeclarationDescriptor ownerDescriptor = resolutionContext.scope.getOwnerDescriptor();
        BindingContext context = resolutionContext.trace.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
        DataFlowInfo dataFlowInfo = resolutionContext.dataFlowInfo;
        Intrinsics.checkNotNullExpressionValue(dataFlowInfo, "dataFlowInfo");
        LanguageVersionSettings languageVersionSettings = resolutionContext.languageVersionSettings;
        Intrinsics.checkNotNullExpressionValue(languageVersionSettings, "languageVersionSettings");
        DataFlowValueFactory dataFlowValueFactory = resolutionContext.dataFlowValueFactory;
        Intrinsics.checkNotNullExpressionValue(dataFlowValueFactory, "dataFlowValueFactory");
        return transformToReceiverWithSmartCastInfo(ownerDescriptor, context, dataFlowInfo, receiver, languageVersionSettings, dataFlowValueFactory);
    }

    public static final ReceiverValueWithSmartCastInfo transformToReceiverWithSmartCastInfo(DeclarationDescriptor containingDescriptor, BindingContext bindingContext, DataFlowInfo dataFlowInfo, ReceiverValue receiver, LanguageVersionSettings languageVersionSettings, DataFlowValueFactory dataFlowValueFactory) {
        Intrinsics.checkNotNullParameter(containingDescriptor, "containingDescriptor");
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        Intrinsics.checkNotNullParameter(dataFlowInfo, "dataFlowInfo");
        Intrinsics.checkNotNullParameter(receiver, "receiver");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(dataFlowValueFactory, "dataFlowValueFactory");
        DataFlowValue dataFlowValueCreateDataFlowValue = dataFlowValueFactory.createDataFlowValue(receiver, bindingContext, containingDescriptor);
        return new ReceiverValueWithSmartCastInfo(receiver, AddToStdlibKt.compactIfPossible(dataFlowInfo.getCollectedTypes(dataFlowValueCreateDataFlowValue, languageVersionSettings)), dataFlowValueCreateDataFlowValue.getIsStable(), null, 8, null);
    }

    public static final PreviousResolutionError createPreviousResolveError(ResolutionStatus status) {
        CandidateApplicability candidateApplicability;
        Intrinsics.checkNotNullParameter(status, "status");
        int i = WhenMappings.$EnumSwitchMapping$0[status.ordinal()];
        if (i == 1 || i == 2) {
            return null;
        }
        if (i == 3) {
            candidateApplicability = CandidateApplicability.UNSAFE_CALL;
        } else if (i == 4) {
            candidateApplicability = CandidateApplicability.INAPPLICABLE_ARGUMENTS_MAPPING_ERROR;
        } else if (i == 5) {
            candidateApplicability = CandidateApplicability.INAPPLICABLE_WRONG_RECEIVER;
        } else {
            candidateApplicability = CandidateApplicability.INAPPLICABLE;
        }
        return new PreviousResolutionError(candidateApplicability);
    }

    public static final boolean isCallWithSuperReceiver(Call call) {
        Intrinsics.checkNotNullParameter(call, "<this>");
        return call.getExplicitReceiver() instanceof SuperCallReceiverValue;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isSuperCall(BasicCallResolutionContext basicCallResolutionContext) {
        Call call = basicCallResolutionContext.call;
        Intrinsics.checkNotNullExpressionValue(call, "call");
        return isCallWithSuperReceiver(call);
    }

    public static final void reportResolvedUsingDeprecatedVisibility(Call call, CallableDescriptor candidateDescriptor, CallableDescriptor resultingDescriptor, ResolvedUsingDeprecatedVisibility diagnostic, BindingTrace trace) {
        CallableDescriptor samInterface;
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(candidateDescriptor, "candidateDescriptor");
        Intrinsics.checkNotNullParameter(resultingDescriptor, "resultingDescriptor");
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        Intrinsics.checkNotNullParameter(trace, "trace");
        trace.record(BindingContext.DEPRECATED_SHORT_NAME_ACCESS, call.getCalleeExpression());
        if (candidateDescriptor instanceof ClassConstructorDescriptor) {
            samInterface = ((ClassConstructorDescriptor) candidateDescriptor).getContainingModule();
        } else if (candidateDescriptor instanceof FakeCallableDescriptorForObject) {
            samInterface = ((FakeCallableDescriptorForObject) candidateDescriptor).getClassDescriptor();
        } else if (candidateDescriptor instanceof SyntheticMemberDescriptor) {
            samInterface = ((SyntheticMemberDescriptor) candidateDescriptor).getSamInterface();
        } else {
            if (!(candidateDescriptor instanceof PropertyDescriptor ? true : candidateDescriptor instanceof FunctionDescriptor)) {
                StringBuilder sbAppend = new StringBuilder("Unexpected candidate descriptor of resolved call with ResolvedUsingDeprecatedVisibility-diagnostic: ").append(candidateDescriptor).append("\nCall context: ");
                PsiElement parent = call.getCallElement().getLightParent();
                throw new IllegalStateException(sbAppend.append(parent != null ? parent.getText() : null).toString().toString());
            }
            samInterface = candidateDescriptor;
        }
        ResolutionScope baseSourceScope = diagnostic.getBaseSourceScope();
        if (baseSourceScope instanceof HierarchicalScope ? ScopeUtilsKt.canBeResolvedWithoutDeprecation(samInterface, (HierarchicalScope) baseSourceScope, diagnostic.getLookupLocation()) : false) {
            return;
        }
        trace.report(Errors.DEPRECATED_ACCESS_BY_SHORT_NAME.on(call.getCallElement(), resultingDescriptor));
    }
}
