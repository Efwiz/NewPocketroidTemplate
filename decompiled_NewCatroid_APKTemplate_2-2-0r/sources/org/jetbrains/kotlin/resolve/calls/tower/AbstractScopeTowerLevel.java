package org.jetbrains.kotlin.resolve.calls.tower;

import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilityUtils;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.resolve.calls.smartcasts.SmartCastUtilKt;
import org.jetbrains.kotlin.resolve.calls.util.IsFromStdlibJre7Or8Kt;
import org.jetbrains.kotlin.resolve.descriptorUtil.AnnotationsForResolveUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValueWithSmartCastInfo;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.error.ErrorUtils;
import org.jetbrains.kotlin.utils.CollectionsKt;
import org.jetbrains.kotlin.utils.SmartList;

/* JADX INFO: compiled from: TowerLevels.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b \u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J2\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u00102\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u00122\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0004R\u0014\u0010\u0005\u001a\u00020\u00068DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\u0002\u001a\u00020\u0003X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/AbstractScopeTowerLevel;", "Lorg/jetbrains/kotlin/resolve/calls/tower/ScopeTowerLevel;", "scopeTower", "Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;", "(Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;)V", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "getLocation", "()Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "getScopeTower", "()Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;", "createCandidateDescriptor", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateWithBoundDispatchReceiver;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "dispatchReceiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "specialError", "Lorg/jetbrains/kotlin/resolve/calls/tower/ResolutionDiagnostic;", "dispatchReceiverSmartCastType", "Lorg/jetbrains/kotlin/types/KotlinType;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractScopeTowerLevel implements ScopeTowerLevel {
    private final ImplicitScopeTower scopeTower;

    public AbstractScopeTowerLevel(ImplicitScopeTower scopeTower) {
        Intrinsics.checkNotNullParameter(scopeTower, "scopeTower");
        this.scopeTower = scopeTower;
    }

    protected final ImplicitScopeTower getScopeTower() {
        return this.scopeTower;
    }

    protected final LookupLocation getLocation() {
        return this.scopeTower.getLocation();
    }

    public static /* synthetic */ CandidateWithBoundDispatchReceiver createCandidateDescriptor$default(AbstractScopeTowerLevel abstractScopeTowerLevel, CallableDescriptor callableDescriptor, ReceiverValueWithSmartCastInfo receiverValueWithSmartCastInfo, ResolutionDiagnostic resolutionDiagnostic, KotlinType kotlinType, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: createCandidateDescriptor");
        }
        if ((i & 4) != 0) {
            resolutionDiagnostic = null;
        }
        if ((i & 8) != 0) {
            kotlinType = null;
        }
        return abstractScopeTowerLevel.createCandidateDescriptor(callableDescriptor, receiverValueWithSmartCastInfo, resolutionDiagnostic, kotlinType);
    }

    protected final CandidateWithBoundDispatchReceiver createCandidateDescriptor(CallableDescriptor descriptor, ReceiverValueWithSmartCastInfo dispatchReceiver, ResolutionDiagnostic specialError, KotlinType dispatchReceiverSmartCastType) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        SmartList smartList = new SmartList();
        CollectionsKt.addIfNotNull(smartList, specialError);
        if (ErrorUtils.isError(descriptor)) {
            smartList.add(ErrorDescriptorDiagnostic.INSTANCE);
        } else {
            if (AnnotationsForResolveUtilsKt.hasLowPriorityInOverloadResolution(descriptor) || IsFromStdlibJre7Or8Kt.isLowPriorityFromStdlibJre7Or8(descriptor)) {
                smartList.add(LowPriorityDescriptorDiagnostic.INSTANCE);
            }
            if (dispatchReceiverSmartCastType != null) {
                smartList.add(new UsedSmartCastForDispatchReceiver(dispatchReceiverSmartCastType));
            }
            if (!this.scopeTower.isNewInferenceEnabled()) {
                DeclarationDescriptorWithVisibility declarationDescriptorWithVisibilityFindInvisibleMember = DescriptorVisibilityUtils.findInvisibleMember(SmartCastUtilKt.getReceiverValueWithSmartCast(dispatchReceiver != null ? dispatchReceiver.getReceiverValue() : null, dispatchReceiverSmartCastType), descriptor, this.scopeTower.getLexicalScope().getOwnerDescriptor(), this.scopeTower.getLanguageVersionSettings());
                if (declarationDescriptorWithVisibilityFindInvisibleMember != null) {
                    smartList.add(new VisibilityError(declarationDescriptorWithVisibilityFindInvisibleMember));
                }
            }
        }
        return new CandidateWithBoundDispatchReceiver(dispatchReceiver, descriptor, smartList);
    }
}
