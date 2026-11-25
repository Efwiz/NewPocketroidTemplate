package org.jetbrains.kotlin.resolve.calls.inference;

import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.resolve.calls.inference.constraintPosition.ConstraintPosition;

/* JADX INFO: compiled from: constraintIncorporation.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0011\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B3\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0010\b\u0002\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\b¢\u0006\u0002\u0010\nJ\t\u0010\u0012\u001a\u00020\u0003HÆ\u0003J\u0011\u0010\u0013\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005HÆ\u0003J\t\u0010\u0014\u001a\u00020\bHÆ\u0003J\t\u0010\u0015\u001a\u00020\bHÆ\u0003J9\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u0010\b\u0002\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u00052\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\bHÆ\u0001J\u0013\u0010\u0017\u001a\u00020\b2\b\u0010\u0018\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0019\u001a\u00020\u001aHÖ\u0001J\t\u0010\u001b\u001a\u00020\u001cHÖ\u0001R\u0019\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\t\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintContext;", "", "position", "Lorg/jetbrains/kotlin/resolve/calls/inference/constraintPosition/ConstraintPosition;", "derivedFrom", "", "Lorg/jetbrains/kotlin/resolve/calls/inference/TypeVariable;", "initial", "", "initialReduction", "(Lorg/jetbrains/kotlin/resolve/calls/inference/constraintPosition/ConstraintPosition;Ljava/util/Set;ZZ)V", "getDerivedFrom", "()Ljava/util/Set;", "getInitial", "()Z", "getInitialReduction", "getPosition", "()Lorg/jetbrains/kotlin/resolve/calls/inference/constraintPosition/ConstraintPosition;", "component1", "component2", "component3", "component4", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ConstraintContext {
    private final Set<TypeVariable> derivedFrom;
    private final boolean initial;
    private final boolean initialReduction;
    private final ConstraintPosition position;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ ConstraintContext copy$default(ConstraintContext constraintContext, ConstraintPosition constraintPosition, Set set, boolean z, boolean z2, int i, Object obj) {
        if ((i & 1) != 0) {
            constraintPosition = constraintContext.position;
        }
        if ((i & 2) != 0) {
            set = constraintContext.derivedFrom;
        }
        if ((i & 4) != 0) {
            z = constraintContext.initial;
        }
        if ((i & 8) != 0) {
            z2 = constraintContext.initialReduction;
        }
        return constraintContext.copy(constraintPosition, set, z, z2);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final ConstraintPosition getPosition() {
        return this.position;
    }

    public final Set<TypeVariable> component2() {
        return this.derivedFrom;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final boolean getInitial() {
        return this.initial;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final boolean getInitialReduction() {
        return this.initialReduction;
    }

    public final ConstraintContext copy(ConstraintPosition position, Set<TypeVariable> derivedFrom, boolean initial, boolean initialReduction) {
        Intrinsics.checkNotNullParameter(position, "position");
        return new ConstraintContext(position, derivedFrom, initial, initialReduction);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ConstraintContext)) {
            return false;
        }
        ConstraintContext constraintContext = (ConstraintContext) other;
        return Intrinsics.areEqual(this.position, constraintContext.position) && Intrinsics.areEqual(this.derivedFrom, constraintContext.derivedFrom) && this.initial == constraintContext.initial && this.initialReduction == constraintContext.initialReduction;
    }

    public int hashCode() {
        int iHashCode = this.position.hashCode() * 31;
        Set<TypeVariable> set = this.derivedFrom;
        return ((((iHashCode + (set == null ? 0 : set.hashCode())) * 31) + Boolean.hashCode(this.initial)) * 31) + Boolean.hashCode(this.initialReduction);
    }

    public String toString() {
        return "ConstraintContext(position=" + this.position + ", derivedFrom=" + this.derivedFrom + ", initial=" + this.initial + ", initialReduction=" + this.initialReduction + ')';
    }

    public ConstraintContext(ConstraintPosition position, Set<TypeVariable> set, boolean z, boolean z2) {
        Intrinsics.checkNotNullParameter(position, "position");
        this.position = position;
        this.derivedFrom = set;
        this.initial = z;
        this.initialReduction = z2;
    }

    public /* synthetic */ ConstraintContext(ConstraintPosition constraintPosition, Set set, boolean z, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(constraintPosition, (i & 2) != 0 ? null : set, (i & 4) != 0 ? false : z, (i & 8) != 0 ? false : z2);
    }

    public final ConstraintPosition getPosition() {
        return this.position;
    }

    public final Set<TypeVariable> getDerivedFrom() {
        return this.derivedFrom;
    }

    public final boolean getInitial() {
        return this.initial;
    }

    public final boolean getInitialReduction() {
        return this.initialReduction;
    }
}
