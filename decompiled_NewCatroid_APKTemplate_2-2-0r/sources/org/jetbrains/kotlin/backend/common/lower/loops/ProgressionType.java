package org.jetbrains.kotlin.backend.common.lower.loops;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.ir.Symbols;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeUtilsKt;

/* JADX INFO: compiled from: ProgressionType.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000 \u00182\u00020\u0001:\u0001\u0018B1\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\nJ\n\u0010\u0013\u001a\u00020\u0014*\u00020\u0014J\n\u0010\u0015\u001a\u00020\u0014*\u00020\u0014J\f\u0010\u0016\u001a\u00020\u0014*\u00020\u0017H&R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0013\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0007\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0010R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\f\u0082\u0001\u0004\u0019\u001a\u001b\u001c¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/loops/ProgressionType;", "", "elementClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "stepClass", "minValueAsLong", "", "maxValueAsLong", "getProgressionLastElementFunction", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;Lorg/jetbrains/kotlin/ir/declarations/IrClass;JJLorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;)V", "getElementClass", "()Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getGetProgressionLastElementFunction", "()Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "getMaxValueAsLong", "()J", "getMinValueAsLong", "getStepClass", "asElementType", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "asStepType", "zeroStepExpression", "Lorg/jetbrains/kotlin/backend/common/lower/DeclarationIrBuilder;", "Companion", "Lorg/jetbrains/kotlin/backend/common/lower/loops/CharProgressionType;", "Lorg/jetbrains/kotlin/backend/common/lower/loops/IntProgressionType;", "Lorg/jetbrains/kotlin/backend/common/lower/loops/LongProgressionType;", "Lorg/jetbrains/kotlin/backend/common/lower/loops/UnsignedProgressionType;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ProgressionType {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final IrClass elementClass;
    private final IrSimpleFunctionSymbol getProgressionLastElementFunction;
    private final long maxValueAsLong;
    private final long minValueAsLong;
    private final IrClass stepClass;

    public /* synthetic */ ProgressionType(IrClass irClass, IrClass irClass2, long j, long j2, IrSimpleFunctionSymbol irSimpleFunctionSymbol, DefaultConstructorMarker defaultConstructorMarker) {
        this(irClass, irClass2, j, j2, irSimpleFunctionSymbol);
    }

    public abstract IrExpression zeroStepExpression(DeclarationIrBuilder declarationIrBuilder);

    private ProgressionType(IrClass irClass, IrClass irClass2, long j, long j2, IrSimpleFunctionSymbol irSimpleFunctionSymbol) {
        this.elementClass = irClass;
        this.stepClass = irClass2;
        this.minValueAsLong = j;
        this.maxValueAsLong = j2;
        this.getProgressionLastElementFunction = irSimpleFunctionSymbol;
    }

    public final IrClass getElementClass() {
        return this.elementClass;
    }

    public final IrClass getStepClass() {
        return this.stepClass;
    }

    public final long getMinValueAsLong() {
        return this.minValueAsLong;
    }

    public final long getMaxValueAsLong() {
        return this.maxValueAsLong;
    }

    public final IrSimpleFunctionSymbol getGetProgressionLastElementFunction() {
        return this.getProgressionLastElementFunction;
    }

    public final IrExpression asElementType(IrExpression irExpression) {
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        return UtilsKt.castIfNecessary(irExpression, this.elementClass);
    }

    public final IrExpression asStepType(IrExpression irExpression) {
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        return UtilsKt.castIfNecessary(irExpression, this.stepClass);
    }

    /* JADX INFO: compiled from: ProgressionType.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/loops/ProgressionType$Companion;", "", "()V", "fromIrType", "Lorg/jetbrains/kotlin/backend/common/lower/loops/ProgressionType;", "irType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "symbols", "Lorg/jetbrains/kotlin/backend/common/ir/Symbols;", "allowUnsignedBounds", "", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final ProgressionType fromIrType(IrType irType, Symbols symbols, boolean allowUnsignedBounds) {
            Intrinsics.checkNotNullParameter(irType, "irType");
            Intrinsics.checkNotNullParameter(symbols, "symbols");
            if (IrTypeUtilsKt.isSubtypeOfClass(irType, symbols.getCharProgression())) {
                return new CharProgressionType(symbols);
            }
            if (IrTypeUtilsKt.isSubtypeOfClass(irType, symbols.getIntProgression())) {
                return new IntProgressionType(symbols);
            }
            if (IrTypeUtilsKt.isSubtypeOfClass(irType, symbols.getLongProgression())) {
                return new LongProgressionType(symbols);
            }
            if (symbols.getUIntProgression() != null && IrTypeUtilsKt.isSubtypeOfClass(irType, symbols.getUIntProgression())) {
                return new UIntProgressionType(symbols, allowUnsignedBounds);
            }
            if (symbols.getULongProgression() == null || !IrTypeUtilsKt.isSubtypeOfClass(irType, symbols.getULongProgression())) {
                return null;
            }
            return new ULongProgressionType(symbols, allowUnsignedBounds);
        }
    }
}
