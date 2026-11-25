package org.jetbrains.kotlin.resolve.calls.inference;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintKind;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintPosition;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;

/* JADX INFO: compiled from: ConstraintSystemBuilder.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00000\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a,\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0002\u001a\"\u0010\n\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007\u001a\"\u0010\u000b\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007\u001a,\u0010\f\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0002\u001a\"\u0010\r\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007\u001a+\u0010\u000e\u001a\u00020\u0001*\u00020\u00022\u0019\b\u0004\u0010\u000f\u001a\u0013\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00010\u0010¢\u0006\u0002\b\u0012H\u0086\bø\u0001\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0013"}, d2 = {"addConstraintIfCompatible", "", "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemBuilder;", "lowerType", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "upperType", "position", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintPosition;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintKind;", "addEqualityConstraintIfCompatible", "addSubtypeConstraintIfCompatible", "isConstraintCompatible", "isSubtypeConstraintCompatible", "runTransaction", "runOperations", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemOperation;", "Lkotlin/ExtensionFunctionType;", "resolution.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ConstraintSystemBuilderKt {

    /* JADX INFO: compiled from: ConstraintSystemBuilder.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ConstraintKind.values().length];
            try {
                iArr[ConstraintKind.LOWER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ConstraintKind.UPPER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ConstraintKind.EQUALITY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final boolean runTransaction(ConstraintSystemBuilder constraintSystemBuilder, Function1<? super ConstraintSystemOperation, Boolean> runOperations) {
        Intrinsics.checkNotNullParameter(constraintSystemBuilder, "<this>");
        Intrinsics.checkNotNullParameter(runOperations, "runOperations");
        ConstraintSystemTransaction constraintSystemTransactionPrepareTransaction = constraintSystemBuilder.prepareTransaction();
        if (runOperations.invoke(constraintSystemBuilder).booleanValue()) {
            constraintSystemTransactionPrepareTransaction.closeTransaction();
            return true;
        }
        constraintSystemTransactionPrepareTransaction.rollbackTransaction();
        return false;
    }

    public static final boolean addSubtypeConstraintIfCompatible(ConstraintSystemBuilder constraintSystemBuilder, KotlinTypeMarker lowerType, KotlinTypeMarker upperType, ConstraintPosition position) {
        Intrinsics.checkNotNullParameter(constraintSystemBuilder, "<this>");
        Intrinsics.checkNotNullParameter(lowerType, "lowerType");
        Intrinsics.checkNotNullParameter(upperType, "upperType");
        Intrinsics.checkNotNullParameter(position, "position");
        return addConstraintIfCompatible(constraintSystemBuilder, lowerType, upperType, position, ConstraintKind.LOWER);
    }

    public static final boolean addEqualityConstraintIfCompatible(ConstraintSystemBuilder constraintSystemBuilder, KotlinTypeMarker lowerType, KotlinTypeMarker upperType, ConstraintPosition position) {
        Intrinsics.checkNotNullParameter(constraintSystemBuilder, "<this>");
        Intrinsics.checkNotNullParameter(lowerType, "lowerType");
        Intrinsics.checkNotNullParameter(upperType, "upperType");
        Intrinsics.checkNotNullParameter(position, "position");
        return addConstraintIfCompatible(constraintSystemBuilder, lowerType, upperType, position, ConstraintKind.EQUALITY);
    }

    public static final boolean isSubtypeConstraintCompatible(ConstraintSystemBuilder constraintSystemBuilder, KotlinTypeMarker lowerType, KotlinTypeMarker upperType, ConstraintPosition position) {
        Intrinsics.checkNotNullParameter(constraintSystemBuilder, "<this>");
        Intrinsics.checkNotNullParameter(lowerType, "lowerType");
        Intrinsics.checkNotNullParameter(upperType, "upperType");
        Intrinsics.checkNotNullParameter(position, "position");
        return isConstraintCompatible(constraintSystemBuilder, lowerType, upperType, position, ConstraintKind.LOWER);
    }

    private static final boolean addConstraintIfCompatible(ConstraintSystemBuilder constraintSystemBuilder, KotlinTypeMarker kotlinTypeMarker, KotlinTypeMarker kotlinTypeMarker2, ConstraintPosition constraintPosition, ConstraintKind constraintKind) {
        ConstraintSystemTransaction constraintSystemTransactionPrepareTransaction = constraintSystemBuilder.prepareTransaction();
        ConstraintSystemBuilder constraintSystemBuilder2 = constraintSystemBuilder;
        if (!constraintSystemBuilder2.getHasContradiction()) {
            int i = WhenMappings.$EnumSwitchMapping$0[constraintKind.ordinal()];
            if (i == 1) {
                constraintSystemBuilder2.addSubtypeConstraint(kotlinTypeMarker, kotlinTypeMarker2, constraintPosition);
            } else if (i == 2) {
                constraintSystemBuilder2.addSubtypeConstraint(kotlinTypeMarker2, kotlinTypeMarker, constraintPosition);
            } else if (i == 3) {
                constraintSystemBuilder2.addEqualityConstraint(kotlinTypeMarker, kotlinTypeMarker2, constraintPosition);
            }
        }
        if (!constraintSystemBuilder2.getHasContradiction()) {
            constraintSystemTransactionPrepareTransaction.closeTransaction();
            return true;
        }
        constraintSystemTransactionPrepareTransaction.rollbackTransaction();
        return false;
    }

    private static final boolean isConstraintCompatible(ConstraintSystemBuilder constraintSystemBuilder, KotlinTypeMarker kotlinTypeMarker, KotlinTypeMarker kotlinTypeMarker2, ConstraintPosition constraintPosition, ConstraintKind constraintKind) {
        ConstraintSystemTransaction constraintSystemTransactionPrepareTransaction = constraintSystemBuilder.prepareTransaction();
        ConstraintSystemBuilder constraintSystemBuilder2 = constraintSystemBuilder;
        if (!constraintSystemBuilder2.getHasContradiction()) {
            int i = WhenMappings.$EnumSwitchMapping$0[constraintKind.ordinal()];
            if (i == 1) {
                constraintSystemBuilder2.addSubtypeConstraint(kotlinTypeMarker, kotlinTypeMarker2, constraintPosition);
            } else if (i == 2) {
                constraintSystemBuilder2.addSubtypeConstraint(kotlinTypeMarker2, kotlinTypeMarker, constraintPosition);
            } else if (i == 3) {
                constraintSystemBuilder2.addEqualityConstraint(kotlinTypeMarker, kotlinTypeMarker2, constraintPosition);
            }
        }
        boolean z = !constraintSystemBuilder2.getHasContradiction();
        constraintSystemTransactionPrepareTransaction.rollbackTransaction();
        return z;
    }
}
