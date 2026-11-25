package org.jetbrains.kotlin.resolve.calls.inference.model;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: ConstraintPositionAndErrors.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000e\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\u0011\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u0012\u001a\u00020\u00052\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001J\b\u0010\u0017\u001a\u00020\u0018H\u0016R\u0011\u0010\u0007\u001a\u00020\u00018F¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0004\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/model/IncorporationConstraintPosition;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintPosition;", "initialConstraint", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/InitialConstraint;", "isFromDeclaredUpperBound", "", "(Lorg/jetbrains/kotlin/resolve/calls/inference/model/InitialConstraint;Z)V", TypedValues.TransitionType.S_FROM, "getFrom", "()Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintPosition;", "getInitialConstraint", "()Lorg/jetbrains/kotlin/resolve/calls/inference/model/InitialConstraint;", "()Z", "setFromDeclaredUpperBound", "(Z)V", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "", "toString", "", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class IncorporationConstraintPosition extends ConstraintPosition {
    private final InitialConstraint initialConstraint;
    private boolean isFromDeclaredUpperBound;

    public static /* synthetic */ IncorporationConstraintPosition copy$default(IncorporationConstraintPosition incorporationConstraintPosition, InitialConstraint initialConstraint, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            initialConstraint = incorporationConstraintPosition.initialConstraint;
        }
        if ((i & 2) != 0) {
            z = incorporationConstraintPosition.isFromDeclaredUpperBound;
        }
        return incorporationConstraintPosition.copy(initialConstraint, z);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final InitialConstraint getInitialConstraint() {
        return this.initialConstraint;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final boolean getIsFromDeclaredUpperBound() {
        return this.isFromDeclaredUpperBound;
    }

    public final IncorporationConstraintPosition copy(InitialConstraint initialConstraint, boolean isFromDeclaredUpperBound) {
        Intrinsics.checkNotNullParameter(initialConstraint, "initialConstraint");
        return new IncorporationConstraintPosition(initialConstraint, isFromDeclaredUpperBound);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof IncorporationConstraintPosition)) {
            return false;
        }
        IncorporationConstraintPosition incorporationConstraintPosition = (IncorporationConstraintPosition) other;
        return Intrinsics.areEqual(this.initialConstraint, incorporationConstraintPosition.initialConstraint) && this.isFromDeclaredUpperBound == incorporationConstraintPosition.isFromDeclaredUpperBound;
    }

    public int hashCode() {
        return (this.initialConstraint.hashCode() * 31) + Boolean.hashCode(this.isFromDeclaredUpperBound);
    }

    public /* synthetic */ IncorporationConstraintPosition(InitialConstraint initialConstraint, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(initialConstraint, (i & 2) != 0 ? false : z);
    }

    public final InitialConstraint getInitialConstraint() {
        return this.initialConstraint;
    }

    public final boolean isFromDeclaredUpperBound() {
        return this.isFromDeclaredUpperBound;
    }

    public final void setFromDeclaredUpperBound(boolean z) {
        this.isFromDeclaredUpperBound = z;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IncorporationConstraintPosition(InitialConstraint initialConstraint, boolean z) {
        super(null);
        Intrinsics.checkNotNullParameter(initialConstraint, "initialConstraint");
        this.initialConstraint = initialConstraint;
        this.isFromDeclaredUpperBound = z;
    }

    public final ConstraintPosition getFrom() {
        return this.initialConstraint.getPosition();
    }

    public String toString() {
        return "Incorporate " + this.initialConstraint + " from position " + getFrom();
    }
}
