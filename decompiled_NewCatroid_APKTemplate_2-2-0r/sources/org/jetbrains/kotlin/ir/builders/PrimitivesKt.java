package org.jetbrains.kotlin.ir.builders;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.expressions.IrElseBranch;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.IrWhen;
import org.jetbrains.kotlin.ir.expressions.impl.IrBranchImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrElseBranchImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrIfThenElseImpl;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;

/* JADX INFO: compiled from: Primitives.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000D\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\u001a6\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0001\u001a>\u0010\f\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u00012\u0006\u0010\u000e\u001a\u00020\u0001\u001a4\u0010\u000f\u001a\u00020\u0010*\u00020\u00112\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00012\b\b\u0002\u0010\t\u001a\u00020\n\u001a$\u0010\u000f\u001a\u00020\u0010*\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00012\b\b\u0002\u0010\t\u001a\u00020\n\u001a \u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015*\u00020\u00112\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003\u001a\u001a\u0010\u0017\u001a\u00020\u0001*\u00020\u00112\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003\u001a \u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015*\u00020\u00112\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003\u001a\u0012\u0010\u0019\u001a\u00020\u001a*\u00020\u00112\u0006\u0010\u001b\u001a\u00020\u0001\u001a*\u0010\u001c\u001a\u00020\u0001*\u00020\u00112\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u00012\u0006\u0010\u000e\u001a\u00020\u0001\u001a\"\u0010\u001d\u001a\u00020\u0001*\u00020\u00112\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u001e\u001a\u00020\u0001\u001a4\u0010\u001f\u001a\u00020\u0010*\u00020\u00112\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00012\b\b\u0002\u0010\t\u001a\u00020\n\u001a$\u0010\u001f\u001a\u00020\u0010*\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00012\b\b\u0002\u0010\t\u001a\u00020\n\u001a\u001a\u0010 \u001a\u00020\u0010*\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u0001¨\u0006!"}, d2 = {"primitiveOp1", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "startOffset", "", "endOffset", "primitiveOpSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "primitiveOpReturnType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "origin", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "dispatchReceiver", "primitiveOp2", "argument1", "argument2", "andand", "Lorg/jetbrains/kotlin/ir/expressions/IrWhen;", "Lorg/jetbrains/kotlin/ir/builders/IrGeneratorContextInterface;", "a", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "constFalse", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrConstImpl;", "", "constNull", "constTrue", "elseBranch", "Lorg/jetbrains/kotlin/ir/expressions/IrElseBranch;", "elseExpr", "eqeqeq", "equalsNull", "argument", "oror", "whenComma", "ir.tree"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PrimitivesKt {
    public static final IrExpression primitiveOp1(int i, int i2, IrSimpleFunctionSymbol primitiveOpSymbol, IrType primitiveOpReturnType, IrStatementOrigin origin, IrExpression dispatchReceiver) {
        Intrinsics.checkNotNullParameter(primitiveOpSymbol, "primitiveOpSymbol");
        Intrinsics.checkNotNullParameter(primitiveOpReturnType, "primitiveOpReturnType");
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(dispatchReceiver, "dispatchReceiver");
        IrCallImpl irCallImpl = new IrCallImpl(i, i2, primitiveOpReturnType, primitiveOpSymbol, 0, 0, origin, null, 128, null);
        irCallImpl.setDispatchReceiver(dispatchReceiver);
        return irCallImpl;
    }

    public static final IrExpression primitiveOp2(int i, int i2, IrSimpleFunctionSymbol primitiveOpSymbol, IrType primitiveOpReturnType, IrStatementOrigin origin, IrExpression argument1, IrExpression argument2) {
        Intrinsics.checkNotNullParameter(primitiveOpSymbol, "primitiveOpSymbol");
        Intrinsics.checkNotNullParameter(primitiveOpReturnType, "primitiveOpReturnType");
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(argument1, "argument1");
        Intrinsics.checkNotNullParameter(argument2, "argument2");
        IrCallImpl irCallImpl = new IrCallImpl(i, i2, primitiveOpReturnType, primitiveOpSymbol, 0, 2, origin, null, 128, null);
        irCallImpl.putValueArgument(0, argument1);
        irCallImpl.putValueArgument(1, argument2);
        return irCallImpl;
    }

    public static final IrExpression constNull(IrGeneratorContextInterface irGeneratorContextInterface, int i, int i2) {
        Intrinsics.checkNotNullParameter(irGeneratorContextInterface, "<this>");
        return IrConstImpl.INSTANCE.constNull(i, i2, irGeneratorContextInterface.getIrBuiltIns().getNothingNType());
    }

    public static final IrExpression equalsNull(IrGeneratorContextInterface irGeneratorContextInterface, int i, int i2, IrExpression argument) {
        Intrinsics.checkNotNullParameter(irGeneratorContextInterface, "<this>");
        Intrinsics.checkNotNullParameter(argument, "argument");
        return primitiveOp2(i, i2, irGeneratorContextInterface.getIrBuiltIns().getEqeqSymbol(), irGeneratorContextInterface.getIrBuiltIns().getBooleanType(), IrStatementOrigin.EQEQ.INSTANCE, argument, constNull(irGeneratorContextInterface, i, i2));
    }

    public static final IrExpression eqeqeq(IrGeneratorContextInterface irGeneratorContextInterface, int i, int i2, IrExpression argument1, IrExpression argument2) {
        Intrinsics.checkNotNullParameter(irGeneratorContextInterface, "<this>");
        Intrinsics.checkNotNullParameter(argument1, "argument1");
        Intrinsics.checkNotNullParameter(argument2, "argument2");
        return primitiveOp2(i, i2, irGeneratorContextInterface.getIrBuiltIns().getEqeqeqSymbol(), irGeneratorContextInterface.getIrBuiltIns().getBooleanType(), IrStatementOrigin.EQEQEQ.INSTANCE, argument1, argument2);
    }

    public static final IrConstImpl<Boolean> constTrue(IrGeneratorContextInterface irGeneratorContextInterface, int i, int i2) {
        Intrinsics.checkNotNullParameter(irGeneratorContextInterface, "<this>");
        return IrConstImpl.INSTANCE.constTrue(i, i2, irGeneratorContextInterface.getIrBuiltIns().getBooleanType());
    }

    public static final IrConstImpl<Boolean> constFalse(IrGeneratorContextInterface irGeneratorContextInterface, int i, int i2) {
        Intrinsics.checkNotNullParameter(irGeneratorContextInterface, "<this>");
        return IrConstImpl.INSTANCE.constFalse(i, i2, irGeneratorContextInterface.getIrBuiltIns().getBooleanType());
    }

    public static final IrElseBranch elseBranch(IrGeneratorContextInterface irGeneratorContextInterface, IrExpression elseExpr) {
        Intrinsics.checkNotNullParameter(irGeneratorContextInterface, "<this>");
        Intrinsics.checkNotNullParameter(elseExpr, "elseExpr");
        int startOffset = elseExpr.getStartOffset();
        int endOffset = elseExpr.getEndOffset();
        return new IrElseBranchImpl(startOffset, endOffset, constTrue(irGeneratorContextInterface, startOffset, endOffset), elseExpr);
    }

    public static /* synthetic */ IrWhen oror$default(IrGeneratorContextInterface irGeneratorContextInterface, int i, int i2, IrExpression irExpression, IrExpression irExpression2, IrStatementOrigin irStatementOrigin, int i3, Object obj) {
        if ((i3 & 16) != 0) {
            irStatementOrigin = IrStatementOrigin.OROR.INSTANCE;
        }
        return oror(irGeneratorContextInterface, i, i2, irExpression, irExpression2, irStatementOrigin);
    }

    public static final IrWhen oror(IrGeneratorContextInterface irGeneratorContextInterface, int i, int i2, IrExpression a, IrExpression b, IrStatementOrigin origin) {
        Intrinsics.checkNotNullParameter(irGeneratorContextInterface, "<this>");
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        Intrinsics.checkNotNullParameter(origin, "origin");
        IrIfThenElseImpl irIfThenElseImpl = new IrIfThenElseImpl(i, i2, irGeneratorContextInterface.getIrBuiltIns().getBooleanType(), origin);
        irIfThenElseImpl.getBranches().add(new IrBranchImpl(a, constTrue(irGeneratorContextInterface, a.getStartOffset(), a.getEndOffset())));
        irIfThenElseImpl.getBranches().add(elseBranch(irGeneratorContextInterface, b));
        return irIfThenElseImpl;
    }

    public static /* synthetic */ IrWhen oror$default(IrGeneratorContextInterface irGeneratorContextInterface, IrExpression irExpression, IrExpression irExpression2, IrStatementOrigin irStatementOrigin, int i, Object obj) {
        if ((i & 4) != 0) {
            irStatementOrigin = IrStatementOrigin.OROR.INSTANCE;
        }
        return oror(irGeneratorContextInterface, irExpression, irExpression2, irStatementOrigin);
    }

    public static final IrWhen oror(IrGeneratorContextInterface irGeneratorContextInterface, IrExpression a, IrExpression b, IrStatementOrigin origin) {
        Intrinsics.checkNotNullParameter(irGeneratorContextInterface, "<this>");
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        Intrinsics.checkNotNullParameter(origin, "origin");
        return oror(irGeneratorContextInterface, b.getStartOffset(), b.getEndOffset(), a, b, origin);
    }

    public static final IrWhen whenComma(IrGeneratorContextInterface irGeneratorContextInterface, IrExpression a, IrExpression b) {
        Intrinsics.checkNotNullParameter(irGeneratorContextInterface, "<this>");
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        return oror(irGeneratorContextInterface, a, b, IrStatementOrigin.WHEN_COMMA.INSTANCE);
    }

    public static /* synthetic */ IrWhen andand$default(IrGeneratorContextInterface irGeneratorContextInterface, int i, int i2, IrExpression irExpression, IrExpression irExpression2, IrStatementOrigin irStatementOrigin, int i3, Object obj) {
        if ((i3 & 16) != 0) {
            irStatementOrigin = IrStatementOrigin.ANDAND.INSTANCE;
        }
        return andand(irGeneratorContextInterface, i, i2, irExpression, irExpression2, irStatementOrigin);
    }

    public static final IrWhen andand(IrGeneratorContextInterface irGeneratorContextInterface, int i, int i2, IrExpression a, IrExpression b, IrStatementOrigin origin) {
        Intrinsics.checkNotNullParameter(irGeneratorContextInterface, "<this>");
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        Intrinsics.checkNotNullParameter(origin, "origin");
        IrIfThenElseImpl irIfThenElseImpl = new IrIfThenElseImpl(i, i2, irGeneratorContextInterface.getIrBuiltIns().getBooleanType(), origin);
        irIfThenElseImpl.getBranches().add(new IrBranchImpl(a, b));
        irIfThenElseImpl.getBranches().add(elseBranch(irGeneratorContextInterface, constFalse(irGeneratorContextInterface, b.getStartOffset(), b.getEndOffset())));
        return irIfThenElseImpl;
    }

    public static /* synthetic */ IrWhen andand$default(IrGeneratorContextInterface irGeneratorContextInterface, IrExpression irExpression, IrExpression irExpression2, IrStatementOrigin irStatementOrigin, int i, Object obj) {
        if ((i & 4) != 0) {
            irStatementOrigin = IrStatementOrigin.ANDAND.INSTANCE;
        }
        return andand(irGeneratorContextInterface, irExpression, irExpression2, irStatementOrigin);
    }

    public static final IrWhen andand(IrGeneratorContextInterface irGeneratorContextInterface, IrExpression a, IrExpression b, IrStatementOrigin origin) {
        Intrinsics.checkNotNullParameter(irGeneratorContextInterface, "<this>");
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        Intrinsics.checkNotNullParameter(origin, "origin");
        return andand(irGeneratorContextInterface, b.getStartOffset(), b.getEndOffset(), a, b, origin);
    }
}
