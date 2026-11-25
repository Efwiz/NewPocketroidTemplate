package org.jetbrains.kotlin.backend.common.lower.loops;

import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.ULong;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.expressions.IrExpression;

/* JADX INFO: compiled from: HeaderInfo.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001Bi\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\t\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\t\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u000e\b\u0002\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0012J\n\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0016J\b\u0010\u001d\u001a\u0004\u0018\u00010\u0000R\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u001b\u0010\u000b\u001a\u00020\t8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0015\u0010\u0016R\u0013\u0010\u0011\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001a¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/loops/ProgressionHeaderInfo;", "Lorg/jetbrains/kotlin/backend/common/lower/loops/NumericHeaderInfo;", "progressionType", "Lorg/jetbrains/kotlin/backend/common/lower/loops/ProgressionType;", "first", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "last", "step", "isLastInclusive", "", "isReversed", "canOverflow", HiAnalyticsConstant.HaKey.BI_KEY_DIRECTION, "Lorg/jetbrains/kotlin/backend/common/lower/loops/ProgressionDirection;", "additionalStatements", "", "Lorg/jetbrains/kotlin/ir/IrStatement;", "originalLastInclusive", "(Lorg/jetbrains/kotlin/backend/common/lower/loops/ProgressionType;Lorg/jetbrains/kotlin/ir/expressions/IrExpression;Lorg/jetbrains/kotlin/ir/expressions/IrExpression;Lorg/jetbrains/kotlin/ir/expressions/IrExpression;ZZLjava/lang/Boolean;Lorg/jetbrains/kotlin/backend/common/lower/loops/ProgressionDirection;Ljava/util/List;Lorg/jetbrains/kotlin/ir/expressions/IrExpression;)V", "getAdditionalStatements", "()Ljava/util/List;", "getCanOverflow", "()Z", "canOverflow$delegate", "Lkotlin/Lazy;", "getOriginalLastInclusive", "()Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "asReversed", "Lorg/jetbrains/kotlin/backend/common/lower/loops/HeaderInfo;", "revertToLastInclusive", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ProgressionHeaderInfo extends NumericHeaderInfo {
    private final List<IrStatement> additionalStatements;

    /* JADX INFO: renamed from: canOverflow$delegate, reason: from kotlin metadata */
    private final Lazy canOverflow;
    private final IrExpression originalLastInclusive;

    public /* synthetic */ ProgressionHeaderInfo(ProgressionType progressionType, IrExpression irExpression, IrExpression irExpression2, IrExpression irExpression3, boolean z, boolean z2, Boolean bool, ProgressionDirection progressionDirection, List list, IrExpression irExpression4, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(progressionType, irExpression, irExpression2, irExpression3, (i & 16) != 0 ? true : z, (i & 32) != 0 ? false : z2, (i & 64) != 0 ? null : bool, progressionDirection, (i & 256) != 0 ? CollectionsKt.emptyList() : list, (i & 512) != 0 ? null : irExpression4);
    }

    public final List<IrStatement> getAdditionalStatements() {
        return this.additionalStatements;
    }

    public final IrExpression getOriginalLastInclusive() {
        return this.originalLastInclusive;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public ProgressionHeaderInfo(final ProgressionType progressionType, IrExpression first, final IrExpression last, final IrExpression step, boolean z, boolean z2, final Boolean bool, final ProgressionDirection direction, List<? extends IrStatement> additionalStatements, IrExpression irExpression) {
        super(progressionType, first, last, step, z, true, z2, direction, null);
        Intrinsics.checkNotNullParameter(progressionType, "progressionType");
        Intrinsics.checkNotNullParameter(first, "first");
        Intrinsics.checkNotNullParameter(last, "last");
        Intrinsics.checkNotNullParameter(step, "step");
        Intrinsics.checkNotNullParameter(direction, "direction");
        Intrinsics.checkNotNullParameter(additionalStatements, "additionalStatements");
        this.additionalStatements = additionalStatements;
        this.originalLastInclusive = irExpression;
        this.canOverflow = LazyKt.lazy(new Function0<Boolean>() { // from class: org.jetbrains.kotlin.backend.common.lower.loops.ProgressionHeaderInfo$canOverflow$2

            /* JADX INFO: compiled from: HeaderInfo.kt */
            @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
            public /* synthetic */ class WhenMappings {
                public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                static {
                    int[] iArr = new int[ProgressionDirection.values().length];
                    try {
                        iArr[ProgressionDirection.DECREASING.ordinal()] = 1;
                    } catch (NoSuchFieldError unused) {
                    }
                    try {
                        iArr[ProgressionDirection.INCREASING.ordinal()] = 2;
                    } catch (NoSuchFieldError unused2) {
                    }
                    $EnumSwitchMapping$0 = iArr;
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            /* JADX WARN: Code duplicated, block: B:41:0x00c4  */
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() {
                Boolean bool2 = bool;
                if (bool2 != null) {
                    return bool2;
                }
                Long constLongValue = UtilsKt.getConstLongValue(step);
                boolean z3 = true;
                if (constLongValue == null) {
                    return true;
                }
                long jLongValue = constLongValue.longValue();
                if (direction == ProgressionDirection.UNKNOWN) {
                    return true;
                }
                if (progressionType instanceof UnsignedProgressionType) {
                    Long constLongValue2 = UtilsKt.getConstLongValue(last);
                    if (constLongValue2 == null) {
                        return true;
                    }
                    long jM3072constructorimpl = ULong.m3072constructorimpl(constLongValue2.longValue());
                    int i = WhenMappings.$EnumSwitchMapping$0[direction.ordinal()];
                    if (i != 1) {
                        if (i == 2) {
                            if (Long.compareUnsigned(jM3072constructorimpl, ULong.m3072constructorimpl(ULong.m3072constructorimpl(progressionType.getMaxValueAsLong()) - ULong.m3072constructorimpl(jLongValue))) <= 0) {
                                z3 = false;
                            }
                        } else {
                            throw new IllegalStateException("Unexpected progression direction".toString());
                        }
                    } else if (Long.compareUnsigned(jM3072constructorimpl, ULong.m3072constructorimpl(ULong.m3072constructorimpl(progressionType.getMinValueAsLong()) - ULong.m3072constructorimpl(jLongValue))) >= 0) {
                        z3 = false;
                    }
                } else {
                    Long constLongValue3 = UtilsKt.getConstLongValue(last);
                    if (constLongValue3 == null) {
                        return true;
                    }
                    long jLongValue2 = constLongValue3.longValue();
                    int i2 = WhenMappings.$EnumSwitchMapping$0[direction.ordinal()];
                    if (i2 != 1) {
                        if (i2 != 2) {
                            throw new IllegalStateException("Unexpected progression direction".toString());
                        }
                        if (jLongValue2 <= progressionType.getMaxValueAsLong() - jLongValue) {
                            z3 = false;
                        }
                    } else if (jLongValue2 >= progressionType.getMinValueAsLong() - jLongValue) {
                        z3 = false;
                    }
                }
                return Boolean.valueOf(z3);
            }
        });
    }

    public final ProgressionHeaderInfo revertToLastInclusive() {
        if (this.originalLastInclusive != null) {
            return new ProgressionHeaderInfo(getProgressionType(), getFirst(), this.originalLastInclusive, getStep(), true, getIsReversed(), Boolean.valueOf(getCanOverflow()), getDirection(), this.additionalStatements, null);
        }
        return null;
    }

    public final boolean getCanOverflow() {
        return ((Boolean) this.canOverflow.getValue()).booleanValue();
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.loops.HeaderInfo
    public HeaderInfo asReversed() {
        if (getIsLastInclusive()) {
            return new ProgressionHeaderInfo(getProgressionType(), getLast(), getFirst(), UtilsKt.negate(getStep()), false, !getIsReversed(), null, getDirection().asReversed(), this.additionalStatements, null, 592, null);
        }
        ProgressionHeaderInfo progressionHeaderInfoRevertToLastInclusive = revertToLastInclusive();
        if (progressionHeaderInfoRevertToLastInclusive != null) {
            return progressionHeaderInfoRevertToLastInclusive.asReversed();
        }
        return null;
    }
}
