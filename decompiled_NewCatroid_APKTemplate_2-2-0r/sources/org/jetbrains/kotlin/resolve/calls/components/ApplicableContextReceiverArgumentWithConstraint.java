package org.jetbrains.kotlin.resolve.calls.components;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintPosition;
import org.jetbrains.kotlin.resolve.calls.model.SimpleKotlinCallArgument;
import org.jetbrains.kotlin.types.UnwrappedType;

/* JADX INFO: compiled from: ResolutionParts.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0014\u001a\u00020\bHÆ\u0003J1\u0010\u0015\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\bHÆ\u0001J\u0013\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0019\u001a\u00020\u001aHÖ\u0001J\t\u0010\u001b\u001a\u00020\u001cHÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/components/ApplicableContextReceiverArgumentWithConstraint;", "", "argument", "Lorg/jetbrains/kotlin/resolve/calls/model/SimpleKotlinCallArgument;", "argumentType", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "expectedType", "position", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintPosition;", "(Lorg/jetbrains/kotlin/resolve/calls/model/SimpleKotlinCallArgument;Lorg/jetbrains/kotlin/types/UnwrappedType;Lorg/jetbrains/kotlin/types/UnwrappedType;Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintPosition;)V", "getArgument", "()Lorg/jetbrains/kotlin/resolve/calls/model/SimpleKotlinCallArgument;", "getArgumentType", "()Lorg/jetbrains/kotlin/types/UnwrappedType;", "getExpectedType", "getPosition", "()Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintPosition;", "component1", "component2", "component3", "component4", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
final /* data */ class ApplicableContextReceiverArgumentWithConstraint {
    private final SimpleKotlinCallArgument argument;
    private final UnwrappedType argumentType;
    private final UnwrappedType expectedType;
    private final ConstraintPosition position;

    public static /* synthetic */ ApplicableContextReceiverArgumentWithConstraint copy$default(ApplicableContextReceiverArgumentWithConstraint applicableContextReceiverArgumentWithConstraint, SimpleKotlinCallArgument simpleKotlinCallArgument, UnwrappedType unwrappedType, UnwrappedType unwrappedType2, ConstraintPosition constraintPosition, int i, Object obj) {
        if ((i & 1) != 0) {
            simpleKotlinCallArgument = applicableContextReceiverArgumentWithConstraint.argument;
        }
        if ((i & 2) != 0) {
            unwrappedType = applicableContextReceiverArgumentWithConstraint.argumentType;
        }
        if ((i & 4) != 0) {
            unwrappedType2 = applicableContextReceiverArgumentWithConstraint.expectedType;
        }
        if ((i & 8) != 0) {
            constraintPosition = applicableContextReceiverArgumentWithConstraint.position;
        }
        return applicableContextReceiverArgumentWithConstraint.copy(simpleKotlinCallArgument, unwrappedType, unwrappedType2, constraintPosition);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final SimpleKotlinCallArgument getArgument() {
        return this.argument;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final UnwrappedType getArgumentType() {
        return this.argumentType;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final UnwrappedType getExpectedType() {
        return this.expectedType;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final ConstraintPosition getPosition() {
        return this.position;
    }

    public final ApplicableContextReceiverArgumentWithConstraint copy(SimpleKotlinCallArgument argument, UnwrappedType argumentType, UnwrappedType expectedType, ConstraintPosition position) {
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(argumentType, "argumentType");
        Intrinsics.checkNotNullParameter(expectedType, "expectedType");
        Intrinsics.checkNotNullParameter(position, "position");
        return new ApplicableContextReceiverArgumentWithConstraint(argument, argumentType, expectedType, position);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ApplicableContextReceiverArgumentWithConstraint)) {
            return false;
        }
        ApplicableContextReceiverArgumentWithConstraint applicableContextReceiverArgumentWithConstraint = (ApplicableContextReceiverArgumentWithConstraint) other;
        return Intrinsics.areEqual(this.argument, applicableContextReceiverArgumentWithConstraint.argument) && Intrinsics.areEqual(this.argumentType, applicableContextReceiverArgumentWithConstraint.argumentType) && Intrinsics.areEqual(this.expectedType, applicableContextReceiverArgumentWithConstraint.expectedType) && Intrinsics.areEqual(this.position, applicableContextReceiverArgumentWithConstraint.position);
    }

    public int hashCode() {
        return (((((this.argument.hashCode() * 31) + this.argumentType.hashCode()) * 31) + this.expectedType.hashCode()) * 31) + this.position.hashCode();
    }

    public String toString() {
        return "ApplicableContextReceiverArgumentWithConstraint(argument=" + this.argument + ", argumentType=" + this.argumentType + ", expectedType=" + this.expectedType + ", position=" + this.position + ')';
    }

    public ApplicableContextReceiverArgumentWithConstraint(SimpleKotlinCallArgument argument, UnwrappedType argumentType, UnwrappedType expectedType, ConstraintPosition position) {
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(argumentType, "argumentType");
        Intrinsics.checkNotNullParameter(expectedType, "expectedType");
        Intrinsics.checkNotNullParameter(position, "position");
        this.argument = argument;
        this.argumentType = argumentType;
        this.expectedType = expectedType;
        this.position = position;
    }

    public final SimpleKotlinCallArgument getArgument() {
        return this.argument;
    }

    public final UnwrappedType getArgumentType() {
        return this.argumentType;
    }

    public final UnwrappedType getExpectedType() {
        return this.expectedType;
    }

    public final ConstraintPosition getPosition() {
        return this.position;
    }
}
