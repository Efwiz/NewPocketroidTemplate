package org.jetbrains.kotlin.backend.common.lower.loops;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.ir.Symbols;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;

/* JADX INFO: compiled from: ProgressionType.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\b \u0018\u00002\u00020\u0001BG\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\b\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000b¢\u0006\u0002\u0010\u000fJ\n\u0010\u0013\u001a\u00020\u0014*\u00020\u0014J\n\u0010\u0015\u001a\u00020\u0014*\u00020\u0014R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\f\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/loops/UnsignedProgressionType;", "Lorg/jetbrains/kotlin/backend/common/lower/loops/ProgressionType;", "symbols", "Lorg/jetbrains/kotlin/backend/common/ir/Symbols;", "elementClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "stepClass", "minValueAsLong", "", "maxValueAsLong", "getProgressionLastElementFunction", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "unsignedType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "unsignedConversionFunction", "(Lorg/jetbrains/kotlin/backend/common/ir/Symbols;Lorg/jetbrains/kotlin/ir/declarations/IrClass;Lorg/jetbrains/kotlin/ir/declarations/IrClass;JJLorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;Lorg/jetbrains/kotlin/ir/types/IrType;Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;)V", "unsafeCoerceIntrinsic", "getUnsignedType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "asSigned", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "asUnsigned", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class UnsignedProgressionType extends ProgressionType {
    private final IrSimpleFunctionSymbol unsafeCoerceIntrinsic;
    private final IrSimpleFunctionSymbol unsignedConversionFunction;
    private final IrType unsignedType;

    public final IrType getUnsignedType() {
        return this.unsignedType;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UnsignedProgressionType(Symbols symbols, IrClass elementClass, IrClass stepClass, long j, long j2, IrSimpleFunctionSymbol irSimpleFunctionSymbol, IrType unsignedType, IrSimpleFunctionSymbol unsignedConversionFunction) {
        super(elementClass, stepClass, j, j2, irSimpleFunctionSymbol, null);
        Intrinsics.checkNotNullParameter(symbols, "symbols");
        Intrinsics.checkNotNullParameter(elementClass, "elementClass");
        Intrinsics.checkNotNullParameter(stepClass, "stepClass");
        Intrinsics.checkNotNullParameter(unsignedType, "unsignedType");
        Intrinsics.checkNotNullParameter(unsignedConversionFunction, "unsignedConversionFunction");
        this.unsignedType = unsignedType;
        this.unsignedConversionFunction = unsignedConversionFunction;
        this.unsafeCoerceIntrinsic = symbols.getUnsafeCoerceIntrinsic();
    }

    public final IrExpression asUnsigned(IrExpression irExpression) {
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        IrType type = irExpression.getType();
        if (Intrinsics.areEqual(irExpression.getType(), this.unsignedType)) {
            return irExpression;
        }
        if (this.unsafeCoerceIntrinsic != null) {
            IrCallImpl irCallImpl = new IrCallImpl(irExpression.getStartOffset(), irExpression.getEndOffset(), this.unsignedType, this.unsafeCoerceIntrinsic, 2, 1, null, null, 192, null);
            irCallImpl.putTypeArgument(0, type);
            irCallImpl.putTypeArgument(1, this.unsignedType);
            irCallImpl.putValueArgument(0, irExpression);
            return irCallImpl;
        }
        IrCallImpl irCallImpl2 = new IrCallImpl(irExpression.getStartOffset(), irExpression.getEndOffset(), this.unsignedConversionFunction.getOwner().getReturnType(), this.unsignedConversionFunction, 0, 0, null, null, 192, null);
        irCallImpl2.setExtensionReceiver(irExpression);
        return irCallImpl2;
    }

    public final IrExpression asSigned(IrExpression irExpression) {
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        IrSimpleType defaultType = IrUtilsKt.getDefaultType(getElementClass());
        if (Intrinsics.areEqual(irExpression.getType(), defaultType)) {
            return irExpression;
        }
        if (this.unsafeCoerceIntrinsic != null) {
            IrSimpleType irSimpleType = defaultType;
            IrCallImpl irCallImpl = new IrCallImpl(irExpression.getStartOffset(), irExpression.getEndOffset(), irSimpleType, this.unsafeCoerceIntrinsic, 2, 1, null, null, 192, null);
            irCallImpl.putTypeArgument(0, this.unsignedType);
            irCallImpl.putTypeArgument(1, irSimpleType);
            irCallImpl.putValueArgument(0, irExpression);
            return irCallImpl;
        }
        return asElementType(irExpression);
    }
}
