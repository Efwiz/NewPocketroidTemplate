package org.jetbrains.kotlin.fir.resolve.calls;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.apache.commons.compress.archivers.zip.UnixStat;

/* JADX INFO: compiled from: CallKind.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b&\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B}\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003\u0012\b\b\u0002\u0010\n\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\f\u001a\u00020\u0003\u0012\b\b\u0002\u0010\r\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0003¢\u0006\u0002\u0010\u000fJ\u0006\u0010)\u001a\u00020*R\u001a\u0010\b\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001a\u0010\r\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0011\"\u0004\b\u0015\u0010\u0013R\u0011\u0010\u000e\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0011R\u001a\u0010\u0006\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0011\"\u0004\b\u0018\u0010\u0013R\u001a\u0010\u0005\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0011\"\u0004\b\u001a\u0010\u0013R\u001a\u0010\u0007\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u0011\"\u0004\b\u001c\u0010\u0013R\u001a\u0010\t\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u0011\"\u0004\b\u001e\u0010\u0013R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\u0011\"\u0004\b \u0010\u0013R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\u0011\"\u0004\b\"\u0010\u0013R\u001a\u0010\n\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010\u0011\"\u0004\b$\u0010\u0013R\u001a\u0010\u000b\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010\u0011\"\u0004\b&\u0010\u0013R\u001a\u0010\f\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010\u0011\"\u0004\b(\u0010\u0013¨\u0006+"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionSequenceBuilder;", "", "checkVisibility", "", "discriminateSynthetics", "checkExplicitReceiverConsistency", "checkDispatchReceiver", "checkExtensionReceiver", "checkArguments", "checkLowPriorityInOverloadResolution", "initializePostponedVariables", "mapTypeArguments", "resolveCallableReferenceArguments", "checkCallableReferenceExpectedType", "checkContextReceivers", "(ZZZZZZZZZZZZ)V", "getCheckArguments", "()Z", "setCheckArguments", "(Z)V", "getCheckCallableReferenceExpectedType", "setCheckCallableReferenceExpectedType", "getCheckContextReceivers", "getCheckDispatchReceiver", "setCheckDispatchReceiver", "getCheckExplicitReceiverConsistency", "setCheckExplicitReceiverConsistency", "getCheckExtensionReceiver", "setCheckExtensionReceiver", "getCheckLowPriorityInOverloadResolution", "setCheckLowPriorityInOverloadResolution", "getCheckVisibility", "setCheckVisibility", "getDiscriminateSynthetics", "setDiscriminateSynthetics", "getInitializePostponedVariables", "setInitializePostponedVariables", "getMapTypeArguments", "setMapTypeArguments", "getResolveCallableReferenceArguments", "setResolveCallableReferenceArguments", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/fir/resolve/calls/CallKind;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ResolutionSequenceBuilder {
    private boolean checkArguments;
    private boolean checkCallableReferenceExpectedType;
    private final boolean checkContextReceivers;
    private boolean checkDispatchReceiver;
    private boolean checkExplicitReceiverConsistency;
    private boolean checkExtensionReceiver;
    private boolean checkLowPriorityInOverloadResolution;
    private boolean checkVisibility;
    private boolean discriminateSynthetics;
    private boolean initializePostponedVariables;
    private boolean mapTypeArguments;
    private boolean resolveCallableReferenceArguments;

    public ResolutionSequenceBuilder() {
        this(false, false, false, false, false, false, false, false, false, false, false, false, UnixStat.PERM_MASK, null);
    }

    public ResolutionSequenceBuilder(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, boolean z9, boolean z10, boolean z11, boolean z12) {
        this.checkVisibility = z;
        this.discriminateSynthetics = z2;
        this.checkExplicitReceiverConsistency = z3;
        this.checkDispatchReceiver = z4;
        this.checkExtensionReceiver = z5;
        this.checkArguments = z6;
        this.checkLowPriorityInOverloadResolution = z7;
        this.initializePostponedVariables = z8;
        this.mapTypeArguments = z9;
        this.resolveCallableReferenceArguments = z10;
        this.checkCallableReferenceExpectedType = z11;
        this.checkContextReceivers = z12;
    }

    public /* synthetic */ ResolutionSequenceBuilder(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, boolean z9, boolean z10, boolean z11, boolean z12, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? false : z, (i & 2) != 0 ? false : z2, (i & 4) != 0 ? false : z3, (i & 8) != 0 ? false : z4, (i & 16) != 0 ? false : z5, (i & 32) != 0 ? false : z6, (i & 64) != 0 ? false : z7, (i & 128) != 0 ? false : z8, (i & 256) != 0 ? false : z9, (i & 512) != 0 ? false : z10, (i & 1024) != 0 ? false : z11, (i & 2048) == 0 ? z12 : false);
    }

    public final boolean getCheckVisibility() {
        return this.checkVisibility;
    }

    public final void setCheckVisibility(boolean z) {
        this.checkVisibility = z;
    }

    public final boolean getDiscriminateSynthetics() {
        return this.discriminateSynthetics;
    }

    public final void setDiscriminateSynthetics(boolean z) {
        this.discriminateSynthetics = z;
    }

    public final boolean getCheckExplicitReceiverConsistency() {
        return this.checkExplicitReceiverConsistency;
    }

    public final void setCheckExplicitReceiverConsistency(boolean z) {
        this.checkExplicitReceiverConsistency = z;
    }

    public final boolean getCheckDispatchReceiver() {
        return this.checkDispatchReceiver;
    }

    public final void setCheckDispatchReceiver(boolean z) {
        this.checkDispatchReceiver = z;
    }

    public final boolean getCheckExtensionReceiver() {
        return this.checkExtensionReceiver;
    }

    public final void setCheckExtensionReceiver(boolean z) {
        this.checkExtensionReceiver = z;
    }

    public final boolean getCheckArguments() {
        return this.checkArguments;
    }

    public final void setCheckArguments(boolean z) {
        this.checkArguments = z;
    }

    public final boolean getCheckLowPriorityInOverloadResolution() {
        return this.checkLowPriorityInOverloadResolution;
    }

    public final void setCheckLowPriorityInOverloadResolution(boolean z) {
        this.checkLowPriorityInOverloadResolution = z;
    }

    public final boolean getInitializePostponedVariables() {
        return this.initializePostponedVariables;
    }

    public final void setInitializePostponedVariables(boolean z) {
        this.initializePostponedVariables = z;
    }

    public final boolean getMapTypeArguments() {
        return this.mapTypeArguments;
    }

    public final void setMapTypeArguments(boolean z) {
        this.mapTypeArguments = z;
    }

    public final boolean getResolveCallableReferenceArguments() {
        return this.resolveCallableReferenceArguments;
    }

    public final void setResolveCallableReferenceArguments(boolean z) {
        this.resolveCallableReferenceArguments = z;
    }

    public final boolean getCheckCallableReferenceExpectedType() {
        return this.checkCallableReferenceExpectedType;
    }

    public final void setCheckCallableReferenceExpectedType(boolean z) {
        this.checkCallableReferenceExpectedType = z;
    }

    public final boolean getCheckContextReceivers() {
        return this.checkContextReceivers;
    }

    public final CallKind build() {
        ArrayList arrayList = new ArrayList();
        if (this.checkVisibility) {
            arrayList.add(CheckVisibility.INSTANCE);
        }
        if (this.discriminateSynthetics) {
            arrayList.add(DiscriminateSynthetics.INSTANCE);
        }
        if (this.checkArguments) {
            arrayList.add(MapArguments.INSTANCE);
        }
        if (this.checkExplicitReceiverConsistency) {
            arrayList.add(CheckExplicitReceiverConsistency.INSTANCE);
        }
        arrayList.add(this.mapTypeArguments ? MapTypeArguments.INSTANCE : NoTypeArguments.INSTANCE);
        if (this.checkArguments || this.checkDispatchReceiver || this.checkExtensionReceiver) {
            arrayList.add(CreateFreshTypeVariableSubstitutorStage.INSTANCE);
        }
        if (this.checkDispatchReceiver) {
            arrayList.add(CheckDispatchReceiver.INSTANCE);
        }
        if (this.checkExtensionReceiver) {
            arrayList.add(CheckExtensionReceiver.INSTANCE);
        }
        if (this.checkArguments) {
            arrayList.add(CheckArguments.INSTANCE);
        }
        if (this.checkContextReceivers) {
            arrayList.add(CheckContextReceivers.INSTANCE);
        }
        if (this.resolveCallableReferenceArguments) {
            arrayList.add(EagerResolveOfCallableReferences.INSTANCE);
        }
        if (this.checkLowPriorityInOverloadResolution) {
            arrayList.add(CheckLowPriorityInOverloadResolution.INSTANCE);
        }
        if (this.initializePostponedVariables) {
            arrayList.add(PostponedVariablesInitializerResolutionStage.INSTANCE);
        }
        if (this.checkCallableReferenceExpectedType) {
            arrayList.add(CheckCallableReferenceExpectedType.INSTANCE);
        }
        ResolutionStage[] resolutionStageArr = (ResolutionStage[]) arrayList.toArray(new ResolutionStage[0]);
        return new CallKind.CustomForIde((ResolutionStage[]) Arrays.copyOf(resolutionStageArr, resolutionStageArr.length));
    }
}
