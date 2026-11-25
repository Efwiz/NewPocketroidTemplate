package org.jetbrains.kotlin.resolve.calls.inference;

import com.reandroid.arsc.value.CompoundEntry;
import java.util.Collection;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.resolve.calls.inference.constraintPosition.ConstraintPosition;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: TypeBounds.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\bf\u0018\u00002\u00020\u0001:\u0002\u0011\u0012R\u0018\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0012\u0010\u0007\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0016\u0010\u000b\u001a\u0004\u0018\u00010\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0018\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\f0\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0006ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0013À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/TypeBounds;", "", "bounds", "", "Lorg/jetbrains/kotlin/resolve/calls/inference/TypeBounds$Bound;", "getBounds", "()Ljava/util/Collection;", "typeVariable", "Lorg/jetbrains/kotlin/resolve/calls/inference/TypeVariable;", "getTypeVariable", "()Lorg/jetbrains/kotlin/resolve/calls/inference/TypeVariable;", "value", "Lorg/jetbrains/kotlin/types/KotlinType;", "getValue", "()Lorg/jetbrains/kotlin/types/KotlinType;", CompoundEntry.NAME_values, "getValues", "Bound", "BoundKind", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface TypeBounds {

    /* JADX INFO: compiled from: TypeBounds.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/TypeBounds$BoundKind;", "", "(Ljava/lang/String;I)V", "LOWER_BOUND", "EXACT_BOUND", "UPPER_BOUND", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum BoundKind {
        LOWER_BOUND,
        EXACT_BOUND,
        UPPER_BOUND;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<BoundKind> getEntries() {
            return $ENTRIES;
        }
    }

    Collection<Bound> getBounds();

    TypeVariable getTypeVariable();

    Collection<KotlinType> getValues();

    default KotlinType getValue() {
        if (getValues().size() == 1) {
            return (KotlinType) CollectionsKt.first(getValues());
        }
        return null;
    }

    /* JADX INFO: compiled from: TypeBounds.kt */
    @Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\"\n\u0002\b\u000f\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B;\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00030\r¢\u0006\u0002\u0010\u000eJ\u0013\u0010\u001a\u001a\u00020\u000b2\b\u0010\u001b\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u001c\u001a\u00020\u001dH\u0016J\b\u0010\u001e\u001a\u00020\u001fH\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00030\r¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0013R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/TypeBounds$Bound;", "", "typeVariable", "Lorg/jetbrains/kotlin/resolve/calls/inference/TypeVariable;", "constrainingType", "Lorg/jetbrains/kotlin/types/KotlinType;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/resolve/calls/inference/TypeBounds$BoundKind;", "position", "Lorg/jetbrains/kotlin/resolve/calls/inference/constraintPosition/ConstraintPosition;", "isProper", "", "derivedFrom", "", "(Lorg/jetbrains/kotlin/resolve/calls/inference/TypeVariable;Lorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/kotlin/resolve/calls/inference/TypeBounds$BoundKind;Lorg/jetbrains/kotlin/resolve/calls/inference/constraintPosition/ConstraintPosition;ZLjava/util/Set;)V", "getConstrainingType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "getDerivedFrom", "()Ljava/util/Set;", "()Z", "getKind", "()Lorg/jetbrains/kotlin/resolve/calls/inference/TypeBounds$BoundKind;", "getPosition", "()Lorg/jetbrains/kotlin/resolve/calls/inference/constraintPosition/ConstraintPosition;", "getTypeVariable", "()Lorg/jetbrains/kotlin/resolve/calls/inference/TypeVariable;", Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Bound {
        private final KotlinType constrainingType;
        private final Set<TypeVariable> derivedFrom;
        private final boolean isProper;
        private final BoundKind kind;
        private final ConstraintPosition position;
        private final TypeVariable typeVariable;

        public Bound(TypeVariable typeVariable, KotlinType constrainingType, BoundKind kind, ConstraintPosition position, boolean z, Set<TypeVariable> derivedFrom) {
            Intrinsics.checkNotNullParameter(typeVariable, "typeVariable");
            Intrinsics.checkNotNullParameter(constrainingType, "constrainingType");
            Intrinsics.checkNotNullParameter(kind, "kind");
            Intrinsics.checkNotNullParameter(position, "position");
            Intrinsics.checkNotNullParameter(derivedFrom, "derivedFrom");
            this.typeVariable = typeVariable;
            this.constrainingType = constrainingType;
            this.kind = kind;
            this.position = position;
            this.isProper = z;
            this.derivedFrom = derivedFrom;
        }

        public final TypeVariable getTypeVariable() {
            return this.typeVariable;
        }

        public final KotlinType getConstrainingType() {
            return this.constrainingType;
        }

        public final BoundKind getKind() {
            return this.kind;
        }

        public final ConstraintPosition getPosition() {
            return this.position;
        }

        /* JADX INFO: renamed from: isProper, reason: from getter */
        public final boolean getIsProper() {
            return this.isProper;
        }

        public final Set<TypeVariable> getDerivedFrom() {
            return this.derivedFrom;
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || !Intrinsics.areEqual(getClass(), other.getClass())) {
                return false;
            }
            Bound bound = (Bound) other;
            return Intrinsics.areEqual(this.typeVariable, bound.typeVariable) && Intrinsics.areEqual(this.constrainingType, bound.constrainingType) && this.kind == bound.kind && this.position.isStrong() == bound.position.isStrong();
        }

        public int hashCode() {
            return (((((this.typeVariable.hashCode() * 31) + this.constrainingType.hashCode()) * 31) + this.kind.hashCode()) * 31) + (this.position.isStrong() ? 1 : 0);
        }

        public String toString() {
            return "Bound(" + this.constrainingType + ", " + this.kind + ", " + this.position + ", isProper = " + this.isProper + ')';
        }
    }
}
