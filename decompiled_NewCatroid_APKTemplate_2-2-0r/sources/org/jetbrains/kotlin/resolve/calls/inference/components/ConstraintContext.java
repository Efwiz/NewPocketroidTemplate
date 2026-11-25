package org.jetbrains.kotlin.resolve.calls.inference.components;

import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintKind;
import org.jetbrains.kotlin.resolve.calls.inference.model.OnlyInputTypeConstraintPosition;
import org.jetbrains.kotlin.types.model.TypeVariableMarker;

/* JADX INFO: compiled from: ConstraintInjector.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0010\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B/\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J\u000b\u0010\u0015\u001a\u0004\u0018\u00010\bHÆ\u0003J\t\u0010\u0016\u001a\u00020\nHÆ\u0003J9\u0010\u0017\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\b\u0002\u0010\t\u001a\u00020\nHÆ\u0001J\u0013\u0010\u0018\u001a\u00020\n2\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001a\u001a\u00020\u001bHÖ\u0001J\t\u0010\u001c\u001a\u00020\u001dHÖ\u0001R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0010R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintContext;", "", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintKind;", "derivedFrom", "", "Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;", "inputTypePositionBeforeIncorporation", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/OnlyInputTypeConstraintPosition;", "isNullabilityConstraint", "", "(Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintKind;Ljava/util/Set;Lorg/jetbrains/kotlin/resolve/calls/inference/model/OnlyInputTypeConstraintPosition;Z)V", "getDerivedFrom", "()Ljava/util/Set;", "getInputTypePositionBeforeIncorporation", "()Lorg/jetbrains/kotlin/resolve/calls/inference/model/OnlyInputTypeConstraintPosition;", "()Z", "getKind", "()Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintKind;", "component1", "component2", "component3", "component4", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ConstraintContext {
    private final Set<TypeVariableMarker> derivedFrom;
    private final OnlyInputTypeConstraintPosition inputTypePositionBeforeIncorporation;
    private final boolean isNullabilityConstraint;
    private final ConstraintKind kind;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ ConstraintContext copy$default(ConstraintContext constraintContext, ConstraintKind constraintKind, Set set, OnlyInputTypeConstraintPosition onlyInputTypeConstraintPosition, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            constraintKind = constraintContext.kind;
        }
        if ((i & 2) != 0) {
            set = constraintContext.derivedFrom;
        }
        if ((i & 4) != 0) {
            onlyInputTypeConstraintPosition = constraintContext.inputTypePositionBeforeIncorporation;
        }
        if ((i & 8) != 0) {
            z = constraintContext.isNullabilityConstraint;
        }
        return constraintContext.copy(constraintKind, set, onlyInputTypeConstraintPosition, z);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final ConstraintKind getKind() {
        return this.kind;
    }

    public final Set<TypeVariableMarker> component2() {
        return this.derivedFrom;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final OnlyInputTypeConstraintPosition getInputTypePositionBeforeIncorporation() {
        return this.inputTypePositionBeforeIncorporation;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final boolean getIsNullabilityConstraint() {
        return this.isNullabilityConstraint;
    }

    public final ConstraintContext copy(ConstraintKind kind, Set<? extends TypeVariableMarker> derivedFrom, OnlyInputTypeConstraintPosition inputTypePositionBeforeIncorporation, boolean isNullabilityConstraint) {
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(derivedFrom, "derivedFrom");
        return new ConstraintContext(kind, derivedFrom, inputTypePositionBeforeIncorporation, isNullabilityConstraint);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ConstraintContext)) {
            return false;
        }
        ConstraintContext constraintContext = (ConstraintContext) other;
        return this.kind == constraintContext.kind && Intrinsics.areEqual(this.derivedFrom, constraintContext.derivedFrom) && Intrinsics.areEqual(this.inputTypePositionBeforeIncorporation, constraintContext.inputTypePositionBeforeIncorporation) && this.isNullabilityConstraint == constraintContext.isNullabilityConstraint;
    }

    public int hashCode() {
        int iHashCode = ((this.kind.hashCode() * 31) + this.derivedFrom.hashCode()) * 31;
        OnlyInputTypeConstraintPosition onlyInputTypeConstraintPosition = this.inputTypePositionBeforeIncorporation;
        return ((iHashCode + (onlyInputTypeConstraintPosition == null ? 0 : onlyInputTypeConstraintPosition.hashCode())) * 31) + Boolean.hashCode(this.isNullabilityConstraint);
    }

    public String toString() {
        return "ConstraintContext(kind=" + this.kind + ", derivedFrom=" + this.derivedFrom + ", inputTypePositionBeforeIncorporation=" + this.inputTypePositionBeforeIncorporation + ", isNullabilityConstraint=" + this.isNullabilityConstraint + ')';
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ConstraintContext(ConstraintKind kind, Set<? extends TypeVariableMarker> derivedFrom, OnlyInputTypeConstraintPosition onlyInputTypeConstraintPosition, boolean z) {
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(derivedFrom, "derivedFrom");
        this.kind = kind;
        this.derivedFrom = derivedFrom;
        this.inputTypePositionBeforeIncorporation = onlyInputTypeConstraintPosition;
        this.isNullabilityConstraint = z;
    }

    public /* synthetic */ ConstraintContext(ConstraintKind constraintKind, Set set, OnlyInputTypeConstraintPosition onlyInputTypeConstraintPosition, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(constraintKind, set, (i & 4) != 0 ? null : onlyInputTypeConstraintPosition, z);
    }

    public final ConstraintKind getKind() {
        return this.kind;
    }

    public final Set<TypeVariableMarker> getDerivedFrom() {
        return this.derivedFrom;
    }

    public final OnlyInputTypeConstraintPosition getInputTypePositionBeforeIncorporation() {
        return this.inputTypePositionBeforeIncorporation;
    }

    public final boolean isNullabilityConstraint() {
        return this.isNullabilityConstraint;
    }
}
