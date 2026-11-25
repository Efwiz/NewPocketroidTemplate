package org.jetbrains.kotlin.resolve.calls.inference.model;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.types.AbstractTypeChecker;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeCheckerProviderContext;

/* JADX INFO: compiled from: ConstraintStorage.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a&\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0005\u001a\u0012\u0010\t\u001a\u00020\n*\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0005¨\u0006\f"}, d2 = {"checkConstraint", "", "context", "Lorg/jetbrains/kotlin/types/model/TypeCheckerProviderContext;", "constraintType", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "constraintKind", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintKind;", "resultType", "replaceType", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/Constraint;", "newType", "resolution.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ConstraintStorageKt {

    /* JADX INFO: compiled from: ConstraintStorage.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ConstraintKind.values().length];
            try {
                iArr[ConstraintKind.EQUALITY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ConstraintKind.LOWER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ConstraintKind.UPPER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final boolean checkConstraint(TypeCheckerProviderContext context, KotlinTypeMarker constraintType, ConstraintKind constraintKind, KotlinTypeMarker resultType) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(constraintType, "constraintType");
        Intrinsics.checkNotNullParameter(constraintKind, "constraintKind");
        Intrinsics.checkNotNullParameter(resultType, "resultType");
        AbstractTypeChecker abstractTypeChecker = AbstractTypeChecker.INSTANCE;
        int i = WhenMappings.$EnumSwitchMapping$0[constraintKind.ordinal()];
        if (i == 1) {
            return AbstractTypeChecker.equalTypes$default(abstractTypeChecker, context, constraintType, resultType, false, 8, null);
        }
        if (i == 2) {
            return AbstractTypeChecker.isSubtypeOf$default(abstractTypeChecker, context, constraintType, resultType, false, 8, (Object) null);
        }
        if (i == 3) {
            return AbstractTypeChecker.isSubtypeOf$default(abstractTypeChecker, context, resultType, constraintType, false, 8, (Object) null);
        }
        throw new NoWhenBranchMatchedException();
    }

    public static final Constraint replaceType(Constraint constraint, KotlinTypeMarker newType) {
        Intrinsics.checkNotNullParameter(constraint, "<this>");
        Intrinsics.checkNotNullParameter(newType, "newType");
        return new Constraint(constraint.getKind(), newType, constraint.getPosition(), constraint.getTypeHashCode(), constraint.getDerivedFrom(), constraint.getIsNullabilityConstraint(), constraint.getInputTypePositionBeforeIncorporation());
    }
}
