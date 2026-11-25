package org.jetbrains.kotlin.resolve.calls.inference.model;

import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeVariableMarker;

/* JADX INFO: compiled from: ConstraintStorage.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001BI\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0010¢\u0006\u0002\u0010\u0011J\u0013\u0010\u001f\u001a\u00020\u000e2\b\u0010 \u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010!\u001a\u00020\tH\u0016J\b\u0010\"\u001a\u00020#H\u0016R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0013\u0010\u000f\u001a\u0004\u0018\u00010\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001e¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/model/Constraint;", "", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintKind;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "position", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/IncorporationConstraintPosition;", "typeHashCode", "", "derivedFrom", "", "Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;", "isNullabilityConstraint", "", "inputTypePositionBeforeIncorporation", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/OnlyInputTypeConstraintPosition;", "(Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintKind;Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;Lorg/jetbrains/kotlin/resolve/calls/inference/model/IncorporationConstraintPosition;ILjava/util/Set;ZLorg/jetbrains/kotlin/resolve/calls/inference/model/OnlyInputTypeConstraintPosition;)V", "getDerivedFrom", "()Ljava/util/Set;", "getInputTypePositionBeforeIncorporation", "()Lorg/jetbrains/kotlin/resolve/calls/inference/model/OnlyInputTypeConstraintPosition;", "()Z", "getKind", "()Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintKind;", "getPosition", "()Lorg/jetbrains/kotlin/resolve/calls/inference/model/IncorporationConstraintPosition;", "getType", "()Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "getTypeHashCode", "()I", Namer.EQUALS_METHOD_NAME, "other", "hashCode", "toString", "", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Constraint {
    private final Set<TypeVariableMarker> derivedFrom;
    private final OnlyInputTypeConstraintPosition inputTypePositionBeforeIncorporation;
    private final boolean isNullabilityConstraint;
    private final ConstraintKind kind;
    private final IncorporationConstraintPosition position;
    private final KotlinTypeMarker type;
    private final int typeHashCode;

    /* JADX WARN: Multi-variable type inference failed */
    public Constraint(ConstraintKind kind, KotlinTypeMarker type, IncorporationConstraintPosition position, int i, Set<? extends TypeVariableMarker> derivedFrom, boolean z, OnlyInputTypeConstraintPosition onlyInputTypeConstraintPosition) {
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(position, "position");
        Intrinsics.checkNotNullParameter(derivedFrom, "derivedFrom");
        this.kind = kind;
        this.type = type;
        this.position = position;
        this.typeHashCode = i;
        this.derivedFrom = derivedFrom;
        this.isNullabilityConstraint = z;
        this.inputTypePositionBeforeIncorporation = onlyInputTypeConstraintPosition;
    }

    public final ConstraintKind getKind() {
        return this.kind;
    }

    public final KotlinTypeMarker getType() {
        return this.type;
    }

    public final IncorporationConstraintPosition getPosition() {
        return this.position;
    }

    public /* synthetic */ Constraint(ConstraintKind constraintKind, KotlinTypeMarker kotlinTypeMarker, IncorporationConstraintPosition incorporationConstraintPosition, int i, Set set, boolean z, OnlyInputTypeConstraintPosition onlyInputTypeConstraintPosition, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(constraintKind, kotlinTypeMarker, incorporationConstraintPosition, (i2 & 8) != 0 ? kotlinTypeMarker.hashCode() : i, set, z, (i2 & 64) != 0 ? null : onlyInputTypeConstraintPosition);
    }

    public final int getTypeHashCode() {
        return this.typeHashCode;
    }

    public final Set<TypeVariableMarker> getDerivedFrom() {
        return this.derivedFrom;
    }

    /* JADX INFO: renamed from: isNullabilityConstraint, reason: from getter */
    public final boolean getIsNullabilityConstraint() {
        return this.isNullabilityConstraint;
    }

    public final OnlyInputTypeConstraintPosition getInputTypePositionBeforeIncorporation() {
        return this.inputTypePositionBeforeIncorporation;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!Intrinsics.areEqual(other != null ? other.getClass() : null, getClass())) {
            return false;
        }
        Intrinsics.checkNotNull(other, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.inference.model.Constraint");
        Constraint constraint = (Constraint) other;
        return this.typeHashCode == constraint.typeHashCode && this.kind == constraint.kind && Intrinsics.areEqual(this.position, constraint.position) && Intrinsics.areEqual(this.type, constraint.type);
    }

    public int hashCode() {
        return this.typeHashCode;
    }

    public String toString() {
        return this.kind + '(' + this.type + ") from " + this.position;
    }
}
