package org.jetbrains.kotlin.backend.common.lower.loops;

import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBuilderKt;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrConstKind;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetEnumValue;
import org.jetbrains.kotlin.ir.expressions.IrGetObjectValue;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: Utils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000f\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0005\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\n\n\u0002\b\u0002\u001a\u0014\u0010\u000b\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\f\u001a\u00020\rH\u0000\u001aD\u0010\u000e\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0010\u0012\u0004\u0012\u00020\u00020\u000f*\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00022\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u00142\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u00162\b\b\u0002\u0010\u0017\u001a\u00020\u0001H\u0000\u001aD\u0010\u0018\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0010\u0012\u0004\u0012\u00020\u00020\u000f*\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00022\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u00142\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u00162\b\b\u0002\u0010\u0017\u001a\u00020\u0001H\u0000\u001a\f\u0010\u0019\u001a\u00020\u0002*\u00020\u0002H\u0000\u001a\f\u0010\u001a\u001a\u00020\u0002*\u00020\u0002H\u0000\u001a\u0015\u0010\u001b\u001a\u0004\u0018\u00010\u001c*\u0004\u0018\u00010\u001dH\u0002¢\u0006\u0002\u0010\u001e\u001a\u0015\u0010\u001f\u001a\u0004\u0018\u00010 *\u0004\u0018\u00010\u001dH\u0002¢\u0006\u0002\u0010!\u001a\u0015\u0010\"\u001a\u0004\u0018\u00010#*\u0004\u0018\u00010\u001dH\u0002¢\u0006\u0002\u0010$\u001a\u0015\u0010%\u001a\u0004\u0018\u00010&*\u0004\u0018\u00010\u001dH\u0002¢\u0006\u0002\u0010'\u001a\u0015\u0010(\u001a\u0004\u0018\u00010\b*\u0004\u0018\u00010\u001dH\u0002¢\u0006\u0002\u0010)\u001a\u0015\u0010*\u001a\u0004\u0018\u00010+*\u0004\u0018\u00010\u001dH\u0002¢\u0006\u0002\u0010,\"\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00028@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0018\u0010\u0005\u001a\u00020\u0001*\u00020\u00028@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0004\"\u001a\u0010\u0007\u001a\u0004\u0018\u00010\b*\u00020\u00028@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006-"}, d2 = {"canChangeValueDuringExecution", "", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "getCanChangeValueDuringExecution", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpression;)Z", "canHaveSideEffects", "getCanHaveSideEffects", "constLongValue", "", "getConstLongValue", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpression;)Ljava/lang/Long;", "castIfNecessary", "targetClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "createLoopTemporaryVariableIfNecessary", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "Lorg/jetbrains/kotlin/backend/common/lower/DeclarationIrBuilder;", "expression", "nameHint", "", "irType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "isMutable", "createTemporaryVariableIfNecessary", "decrement", "negate", "toByte", "", "", "(Ljava/lang/Object;)Ljava/lang/Byte;", "toDouble", "", "(Ljava/lang/Object;)Ljava/lang/Double;", "toFloat", "", "(Ljava/lang/Object;)Ljava/lang/Float;", "toInt", "", "(Ljava/lang/Object;)Ljava/lang/Integer;", "toLong", "(Ljava/lang/Object;)Ljava/lang/Long;", "toShort", "", "(Ljava/lang/Object;)Ljava/lang/Short;", "ir.backend.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class UtilsKt {

    /* JADX INFO: compiled from: Utils.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[PrimitiveType.values().length];
            try {
                iArr[PrimitiveType.BYTE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[PrimitiveType.SHORT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[PrimitiveType.INT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[PrimitiveType.LONG.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[PrimitiveType.FLOAT.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[PrimitiveType.DOUBLE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final IrExpression negate(IrExpression irExpression) {
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        IrSimpleFunction irSimpleFunction = null;
        IrConst irConst = irExpression instanceof IrConst ? (IrConst) irExpression : null;
        Object value = irConst != null ? irConst.getValue() : null;
        Number number = value instanceof Number ? (Number) value : null;
        if (number instanceof Integer) {
            return new IrConstImpl(irExpression.getStartOffset(), irExpression.getEndOffset(), irExpression.getType(), IrConstKind.Int.INSTANCE, Integer.valueOf(-number.intValue()));
        }
        if (number instanceof Long) {
            return new IrConstImpl(irExpression.getStartOffset(), irExpression.getEndOffset(), irExpression.getType(), IrConstKind.Long.INSTANCE, Long.valueOf(-number.longValue()));
        }
        if (IrTypePredicatesKt.isNothing(irExpression.getType())) {
            return irExpression;
        }
        IrClass irClass = IrTypesKt.getClass(irExpression.getType());
        Intrinsics.checkNotNull(irClass);
        boolean z = false;
        for (IrSimpleFunction irSimpleFunction2 : IrUtilsKt.getFunctions(irClass)) {
            IrSimpleFunction irSimpleFunction3 = irSimpleFunction2;
            if (Intrinsics.areEqual(irSimpleFunction3.getName(), OperatorNameConventions.UNARY_MINUS) && irSimpleFunction3.getValueParameters().isEmpty()) {
                if (z) {
                    throw new IllegalArgumentException("Sequence contains more than one matching element.");
                }
                z = true;
                irSimpleFunction = irSimpleFunction2;
            }
        }
        if (z) {
            IrSimpleFunction irSimpleFunction4 = irSimpleFunction;
            IrCallImpl irCallImpl = new IrCallImpl(irExpression.getStartOffset(), irExpression.getEndOffset(), irSimpleFunction4.getReturnType(), irSimpleFunction4.getSymbol(), 0, 0, null, null, 192, null);
            irCallImpl.setDispatchReceiver(irExpression);
            return IrUtilsKt.implicitCastIfNeededTo(irCallImpl, irExpression.getType());
        }
        throw new NoSuchElementException("Sequence contains no element matching the predicate.");
    }

    public static final IrExpression decrement(IrExpression irExpression) {
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        IrSimpleFunction irSimpleFunction = null;
        IrConst irConst = irExpression instanceof IrConst ? (IrConst) irExpression : null;
        Object value = irConst != null ? irConst.getValue() : null;
        if (value instanceof Integer) {
            return new IrConstImpl(irExpression.getStartOffset(), irExpression.getEndOffset(), irExpression.getType(), IrConstKind.Int.INSTANCE, Integer.valueOf(((Number) value).intValue() - 1));
        }
        if (value instanceof Long) {
            return new IrConstImpl(irExpression.getStartOffset(), irExpression.getEndOffset(), irExpression.getType(), IrConstKind.Long.INSTANCE, Long.valueOf(((Number) value).longValue() - 1));
        }
        if (value instanceof Character) {
            return new IrConstImpl(irExpression.getStartOffset(), irExpression.getEndOffset(), irExpression.getType(), IrConstKind.Char.INSTANCE, Character.valueOf((char) (((Character) value).charValue() - 1)));
        }
        IrClass irClass = IrTypesKt.getClass(irExpression.getType());
        Intrinsics.checkNotNull(irClass);
        boolean z = false;
        for (IrSimpleFunction irSimpleFunction2 : IrUtilsKt.getFunctions(irClass)) {
            IrSimpleFunction irSimpleFunction3 = irSimpleFunction2;
            if (Intrinsics.areEqual(irSimpleFunction3.getName(), OperatorNameConventions.DEC) && irSimpleFunction3.getValueParameters().isEmpty()) {
                if (z) {
                    throw new IllegalArgumentException("Sequence contains more than one matching element.");
                }
                z = true;
                irSimpleFunction = irSimpleFunction2;
            }
        }
        if (z) {
            IrCallImpl irCallImpl = new IrCallImpl(irExpression.getStartOffset(), irExpression.getEndOffset(), irExpression.getType(), irSimpleFunction.getSymbol(), 0, 0, null, null, 192, null);
            irCallImpl.setDispatchReceiver(irExpression);
            return irCallImpl;
        }
        throw new NoSuchElementException("Sequence contains no element matching the predicate.");
    }

    public static final boolean getCanChangeValueDuringExecution(IrExpression irExpression) {
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        if (irExpression instanceof IrGetValue) {
            if (IrUtilsKt.isImmutable(((IrGetValue) irExpression).getSymbol().getOwner())) {
                return false;
            }
        } else {
            if (irExpression instanceof IrConst ? true : irExpression instanceof IrGetObjectValue ? true : irExpression instanceof IrGetEnumValue) {
                return false;
            }
        }
        return true;
    }

    public static final boolean getCanHaveSideEffects(IrExpression irExpression) {
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        return !IrUtilsKt.isTrivial(irExpression);
    }

    private static final Byte toByte(Object obj) {
        if (obj instanceof Number) {
            return Byte.valueOf(((Number) obj).byteValue());
        }
        if (obj instanceof Character) {
            return Byte.valueOf((byte) ((Character) obj).charValue());
        }
        return null;
    }

    private static final Short toShort(Object obj) {
        if (obj instanceof Number) {
            return Short.valueOf(((Number) obj).shortValue());
        }
        if (obj instanceof Character) {
            return Short.valueOf((short) ((Character) obj).charValue());
        }
        return null;
    }

    private static final Integer toInt(Object obj) {
        if (obj instanceof Number) {
            return Integer.valueOf(((Number) obj).intValue());
        }
        if (obj instanceof Character) {
            return Integer.valueOf(((Character) obj).charValue());
        }
        return null;
    }

    private static final Long toLong(Object obj) {
        if (obj instanceof Number) {
            return Long.valueOf(((Number) obj).longValue());
        }
        if (obj instanceof Character) {
            return Long.valueOf(((Character) obj).charValue());
        }
        return null;
    }

    private static final Float toFloat(Object obj) {
        if (obj instanceof Number) {
            return Float.valueOf(((Number) obj).floatValue());
        }
        if (obj instanceof Character) {
            return Float.valueOf(((Character) obj).charValue());
        }
        return null;
    }

    private static final Double toDouble(Object obj) {
        if (obj instanceof Number) {
            return Double.valueOf(((Number) obj).doubleValue());
        }
        if (obj instanceof Character) {
            return Double.valueOf(((Character) obj).charValue());
        }
        return null;
    }

    public static final Long getConstLongValue(IrExpression irExpression) {
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        if (irExpression instanceof IrConst) {
            return toLong(((IrConst) irExpression).getValue());
        }
        return null;
    }

    public static /* synthetic */ Pair createTemporaryVariableIfNecessary$default(DeclarationIrBuilder declarationIrBuilder, IrExpression irExpression, String str, IrType irType, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            str = null;
        }
        if ((i & 4) != 0) {
            irType = null;
        }
        if ((i & 8) != 0) {
            z = false;
        }
        return createTemporaryVariableIfNecessary(declarationIrBuilder, irExpression, str, irType, z);
    }

    public static final Pair<IrVariable, IrExpression> createTemporaryVariableIfNecessary(DeclarationIrBuilder declarationIrBuilder, IrExpression expression, String str, IrType irType, boolean z) {
        Intrinsics.checkNotNullParameter(declarationIrBuilder, "<this>");
        Intrinsics.checkNotNullParameter(expression, "expression");
        if (getCanHaveSideEffects(expression)) {
            IrVariable irVariableCreateTmpVariable$default = IrBuilderKt.createTmpVariable$default(declarationIrBuilder.getScope(), expression, str, z, (IrDeclarationOrigin) null, irType, 8, (Object) null);
            return new Pair<>(irVariableCreateTmpVariable$default, ExpressionHelpersKt.irGet(declarationIrBuilder, irVariableCreateTmpVariable$default));
        }
        return new Pair<>(null, expression);
    }

    public static /* synthetic */ Pair createLoopTemporaryVariableIfNecessary$default(DeclarationIrBuilder declarationIrBuilder, IrExpression irExpression, String str, IrType irType, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            str = null;
        }
        if ((i & 4) != 0) {
            irType = null;
        }
        if ((i & 8) != 0) {
            z = false;
        }
        return createLoopTemporaryVariableIfNecessary(declarationIrBuilder, irExpression, str, irType, z);
    }

    public static final Pair<IrVariable, IrExpression> createLoopTemporaryVariableIfNecessary(DeclarationIrBuilder declarationIrBuilder, IrExpression expression, String str, IrType irType, boolean z) {
        Intrinsics.checkNotNullParameter(declarationIrBuilder, "<this>");
        Intrinsics.checkNotNullParameter(expression, "expression");
        if (getCanChangeValueDuringExecution(expression)) {
            IrVariable irVariableCreateTmpVariable$default = IrBuilderKt.createTmpVariable$default(declarationIrBuilder.getScope(), expression, str, z, (IrDeclarationOrigin) null, irType, 8, (Object) null);
            return new Pair<>(irVariableCreateTmpVariable$default, ExpressionHelpersKt.irGet(declarationIrBuilder, irVariableCreateTmpVariable$default));
        }
        return new Pair<>(null, expression);
    }

    public static final IrExpression castIfNecessary(IrExpression irExpression, IrClass targetClass) {
        IrExpression irExpressionM7207byte;
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        Intrinsics.checkNotNullParameter(targetClass, "targetClass");
        if (Intrinsics.areEqual(irExpression.getType(), IrUtilsKt.getDefaultType(targetClass)) || IrTypePredicatesKt.isNothing(irExpression.getType())) {
            return irExpression;
        }
        boolean z = false;
        IrSimpleFunction irSimpleFunction = null;
        if ((irExpression instanceof IrConst) && IrTypePredicatesKt.isPrimitiveType$default(IrUtilsKt.getDefaultType(targetClass), false, 1, null)) {
            IrSimpleType defaultType = IrUtilsKt.getDefaultType(targetClass);
            IrSimpleType irSimpleType = defaultType;
            PrimitiveType primitiveType = IrTypePredicatesKt.getPrimitiveType(irSimpleType);
            switch (primitiveType == null ? -1 : WhenMappings.$EnumSwitchMapping$0[primitiveType.ordinal()]) {
                case 1:
                    IrConstImpl.Companion companion = IrConstImpl.INSTANCE;
                    int startOffset = irExpression.getStartOffset();
                    int endOffset = irExpression.getEndOffset();
                    Byte b = toByte(((IrConst) irExpression).getValue());
                    Intrinsics.checkNotNull(b);
                    irExpressionM7207byte = companion.m7207byte(startOffset, endOffset, irSimpleType, b.byteValue());
                    break;
                case 2:
                    IrConstImpl.Companion companion2 = IrConstImpl.INSTANCE;
                    int startOffset2 = irExpression.getStartOffset();
                    int endOffset2 = irExpression.getEndOffset();
                    Short sh = toShort(((IrConst) irExpression).getValue());
                    Intrinsics.checkNotNull(sh);
                    irExpressionM7207byte = companion2.m7213short(startOffset2, endOffset2, irSimpleType, sh.shortValue());
                    break;
                case 3:
                    IrConstImpl.Companion companion3 = IrConstImpl.INSTANCE;
                    int startOffset3 = irExpression.getStartOffset();
                    int endOffset3 = irExpression.getEndOffset();
                    Integer num = toInt(((IrConst) irExpression).getValue());
                    Intrinsics.checkNotNull(num);
                    irExpressionM7207byte = companion3.m7211int(startOffset3, endOffset3, irSimpleType, num.intValue());
                    break;
                case 4:
                    IrConstImpl.Companion companion4 = IrConstImpl.INSTANCE;
                    int startOffset4 = irExpression.getStartOffset();
                    int endOffset4 = irExpression.getEndOffset();
                    Long l = toLong(((IrConst) irExpression).getValue());
                    Intrinsics.checkNotNull(l);
                    irExpressionM7207byte = companion4.m7212long(startOffset4, endOffset4, irSimpleType, l.longValue());
                    break;
                case 5:
                    IrConstImpl.Companion companion5 = IrConstImpl.INSTANCE;
                    int startOffset5 = irExpression.getStartOffset();
                    int endOffset5 = irExpression.getEndOffset();
                    Float f = toFloat(((IrConst) irExpression).getValue());
                    Intrinsics.checkNotNull(f);
                    irExpressionM7207byte = companion5.m7210float(startOffset5, endOffset5, irSimpleType, f.floatValue());
                    break;
                case 6:
                    IrConstImpl.Companion companion6 = IrConstImpl.INSTANCE;
                    int startOffset6 = irExpression.getStartOffset();
                    int endOffset6 = irExpression.getEndOffset();
                    Double d = toDouble(((IrConst) irExpression).getValue());
                    Intrinsics.checkNotNull(d);
                    irExpressionM7207byte = companion6.m7209double(startOffset6, endOffset6, irSimpleType, d.doubleValue());
                    break;
                default:
                    throw new IllegalStateException(("Cannot cast expression of type " + RenderIrElementKt.render$default(irExpression.getType(), (DumpIrTreeOptions) null, 1, (Object) null) + " to " + RenderIrElementKt.render$default(defaultType, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
            }
            return irExpressionM7207byte;
        }
        Name nameIdentifier = Name.identifier("to" + targetClass.getName().asString());
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        IrClass irClass = IrTypesKt.getClass(irExpression.getType());
        if (irClass == null) {
            throw new IllegalStateException(("Has to be a class " + RenderIrElementKt.render$default(irExpression.getType(), (DumpIrTreeOptions) null, 1, (Object) null)).toString());
        }
        for (IrSimpleFunction irSimpleFunction2 : IrUtilsKt.getFunctions(irClass)) {
            IrSimpleFunction irSimpleFunction3 = irSimpleFunction2;
            if (Intrinsics.areEqual(irSimpleFunction3.getName(), nameIdentifier) && irSimpleFunction3.getDispatchReceiverParameter() != null && irSimpleFunction3.getExtensionReceiverParameter() == null && irSimpleFunction3.getValueParameters().isEmpty()) {
                if (z) {
                    throw new IllegalArgumentException("Sequence contains more than one matching element.");
                }
                z = true;
                irSimpleFunction = irSimpleFunction2;
            }
        }
        if (z) {
            IrSimpleFunction irSimpleFunction4 = irSimpleFunction;
            IrCallImpl irCallImpl = new IrCallImpl(irExpression.getStartOffset(), irExpression.getEndOffset(), irSimpleFunction4.getReturnType(), irSimpleFunction4.getSymbol(), 0, 0, null, null, 192, null);
            irCallImpl.setDispatchReceiver(irExpression);
            return irCallImpl;
        }
        throw new NoSuchElementException("Sequence contains no element matching the predicate.");
    }
}
