package org.jetbrains.kotlin.fir.resolve.calls;

import java.util.Arrays;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

/* JADX INFO: compiled from: CallKind.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0007\u000b\f\r\u000e\u000f\u0010\u0011B\u001b\b\u0004\u0012\u0012\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003\"\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0006\u0010\t\u001a\u00020\nR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b\u0082\u0001\u0007\u0012\u0013\u0014\u0015\u0016\u0017\u0018¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/CallKind;", "", "resolutionSequence", "", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionStage;", "([Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionStage;)V", "", "getResolutionSequence", "()Ljava/util/List;", "toString", "", "CallableReference", "CustomForIde", "DelegatingConstructorCall", "Function", "SyntheticIdForCallableReferencesResolution", "SyntheticSelect", "VariableAccess", "Lorg/jetbrains/kotlin/fir/resolve/calls/CallKind$CallableReference;", "Lorg/jetbrains/kotlin/fir/resolve/calls/CallKind$CustomForIde;", "Lorg/jetbrains/kotlin/fir/resolve/calls/CallKind$DelegatingConstructorCall;", "Lorg/jetbrains/kotlin/fir/resolve/calls/CallKind$Function;", "Lorg/jetbrains/kotlin/fir/resolve/calls/CallKind$SyntheticIdForCallableReferencesResolution;", "Lorg/jetbrains/kotlin/fir/resolve/calls/CallKind$SyntheticSelect;", "Lorg/jetbrains/kotlin/fir/resolve/calls/CallKind$VariableAccess;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class CallKind {
    private final List<ResolutionStage> resolutionSequence;

    public /* synthetic */ CallKind(ResolutionStage[] resolutionStageArr, DefaultConstructorMarker defaultConstructorMarker) {
        this(resolutionStageArr);
    }

    private CallKind(ResolutionStage... resolutionStageArr) {
        this.resolutionSequence = ArraysKt.toList(resolutionStageArr);
    }

    /* JADX INFO: compiled from: CallKind.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/CallKind$VariableAccess;", "Lorg/jetbrains/kotlin/fir/resolve/calls/CallKind;", "()V", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class VariableAccess extends CallKind {
        public static final VariableAccess INSTANCE = new VariableAccess();

        private VariableAccess() {
            super(new ResolutionStage[]{CheckDeprecatedSinceKotlin.INSTANCE, CheckVisibility.INSTANCE, DiscriminateSynthetics.INSTANCE, CheckExplicitReceiverConsistency.INSTANCE, NoTypeArguments.INSTANCE, CreateFreshTypeVariableSubstitutorStage.INSTANCE, CollectTypeVariableUsagesInfo.INSTANCE, CheckDispatchReceiver.INSTANCE, CheckExtensionReceiver.INSTANCE, CheckContextReceivers.INSTANCE, CheckDslScopeViolation.INSTANCE, CheckLowPriorityInOverloadResolution.INSTANCE, PostponedVariablesInitializerResolutionStage.INSTANCE, ProcessDynamicExtensionAnnotation.INSTANCE, LowerPriorityIfDynamic.INSTANCE, ConstraintSystemForks.INSTANCE, CheckIncompatibleTypeVariableUpperBounds.INSTANCE}, null);
        }
    }

    /* JADX INFO: compiled from: CallKind.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/CallKind$SyntheticSelect;", "Lorg/jetbrains/kotlin/fir/resolve/calls/CallKind;", "()V", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SyntheticSelect extends CallKind {
        public static final SyntheticSelect INSTANCE = new SyntheticSelect();

        private SyntheticSelect() {
            super(new ResolutionStage[]{MapArguments.INSTANCE, NoTypeArguments.INSTANCE, CreateFreshTypeVariableSubstitutorStage.INSTANCE, CollectTypeVariableUsagesInfo.INSTANCE, CheckArguments.INSTANCE, EagerResolveOfCallableReferences.INSTANCE, ConstraintSystemForks.INSTANCE, CheckIncompatibleTypeVariableUpperBounds.INSTANCE}, null);
        }
    }

    /* JADX INFO: compiled from: CallKind.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/CallKind$Function;", "Lorg/jetbrains/kotlin/fir/resolve/calls/CallKind;", "()V", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Function extends CallKind {
        public static final Function INSTANCE = new Function();

        private Function() {
            super(new ResolutionStage[]{CheckDeprecatedSinceKotlin.INSTANCE, CheckVisibility.INSTANCE, DiscriminateSynthetics.INSTANCE, MapArguments.INSTANCE, CheckExplicitReceiverConsistency.INSTANCE, MapTypeArguments.INSTANCE, CreateFreshTypeVariableSubstitutorStage.INSTANCE, CollectTypeVariableUsagesInfo.INSTANCE, CheckDispatchReceiver.INSTANCE, CheckExtensionReceiver.INSTANCE, CheckContextReceivers.INSTANCE, CheckDslScopeViolation.INSTANCE, CheckArguments.INSTANCE, CheckCallModifiers.INSTANCE, EagerResolveOfCallableReferences.INSTANCE, CheckLowPriorityInOverloadResolution.INSTANCE, PostponedVariablesInitializerResolutionStage.INSTANCE, ProcessDynamicExtensionAnnotation.INSTANCE, LowerPriorityIfDynamic.INSTANCE, ConstraintSystemForks.INSTANCE, CheckIncompatibleTypeVariableUpperBounds.INSTANCE}, null);
        }
    }

    /* JADX INFO: compiled from: CallKind.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/CallKind$DelegatingConstructorCall;", "Lorg/jetbrains/kotlin/fir/resolve/calls/CallKind;", "()V", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DelegatingConstructorCall extends CallKind {
        public static final DelegatingConstructorCall INSTANCE = new DelegatingConstructorCall();

        private DelegatingConstructorCall() {
            super(new ResolutionStage[]{CheckDeprecatedSinceKotlin.INSTANCE, CheckVisibility.INSTANCE, MapArguments.INSTANCE, CheckExplicitReceiverConsistency.INSTANCE, MapTypeArguments.INSTANCE, CreateFreshTypeVariableSubstitutorStage.INSTANCE, CollectTypeVariableUsagesInfo.INSTANCE, CheckDispatchReceiver.INSTANCE, CheckExtensionReceiver.INSTANCE, CheckContextReceivers.INSTANCE, CheckDslScopeViolation.INSTANCE, CheckArguments.INSTANCE, EagerResolveOfCallableReferences.INSTANCE, ConstraintSystemForks.INSTANCE, CheckIncompatibleTypeVariableUpperBounds.INSTANCE}, null);
        }
    }

    /* JADX INFO: compiled from: CallKind.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/CallKind$CallableReference;", "Lorg/jetbrains/kotlin/fir/resolve/calls/CallKind;", "()V", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class CallableReference extends CallKind {
        public static final CallableReference INSTANCE = new CallableReference();

        private CallableReference() {
            super(new ResolutionStage[]{CheckDeprecatedSinceKotlin.INSTANCE, CheckVisibility.INSTANCE, DiscriminateSynthetics.INSTANCE, NoTypeArguments.INSTANCE, CreateFreshTypeVariableSubstitutorStage.INSTANCE, CollectTypeVariableUsagesInfo.INSTANCE, CheckDispatchReceiver.INSTANCE, CheckExtensionReceiver.INSTANCE, CheckDslScopeViolation.INSTANCE, CheckCallableReferenceExpectedType.INSTANCE, CheckLowPriorityInOverloadResolution.INSTANCE, CheckIncompatibleTypeVariableUpperBounds.INSTANCE, ProcessDynamicExtensionAnnotation.INSTANCE, LowerPriorityIfDynamic.INSTANCE}, null);
        }
    }

    /* JADX INFO: compiled from: CallKind.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/CallKind$SyntheticIdForCallableReferencesResolution;", "Lorg/jetbrains/kotlin/fir/resolve/calls/CallKind;", "()V", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SyntheticIdForCallableReferencesResolution extends CallKind {
        public static final SyntheticIdForCallableReferencesResolution INSTANCE = new SyntheticIdForCallableReferencesResolution();

        private SyntheticIdForCallableReferencesResolution() {
            super(new ResolutionStage[]{MapArguments.INSTANCE, MapTypeArguments.INSTANCE, CreateFreshTypeVariableSubstitutorStage.INSTANCE, CollectTypeVariableUsagesInfo.INSTANCE, CheckArguments.INSTANCE, EagerResolveOfCallableReferences.INSTANCE, ConstraintSystemForks.INSTANCE, CheckIncompatibleTypeVariableUpperBounds.INSTANCE}, null);
        }
    }

    /* JADX INFO: compiled from: CallKind.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B\u0019\u0012\u0012\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003\"\u00020\u0004¢\u0006\u0002\u0010\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/CallKind$CustomForIde;", "Lorg/jetbrains/kotlin/fir/resolve/calls/CallKind;", "resolutionSequence", "", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionStage;", "([Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionStage;)V", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class CustomForIde extends CallKind {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public CustomForIde(ResolutionStage... resolutionSequence) {
            super((ResolutionStage[]) Arrays.copyOf(resolutionSequence, resolutionSequence.length), null);
            Intrinsics.checkNotNullParameter(resolutionSequence, "resolutionSequence");
        }
    }

    public final List<ResolutionStage> getResolutionSequence() {
        return this.resolutionSequence;
    }

    public final String toString() {
        String simpleName = Reflection.getOrCreateKotlinClass(getClass()).getSimpleName();
        return simpleName == null ? super.toString() : simpleName;
    }
}
