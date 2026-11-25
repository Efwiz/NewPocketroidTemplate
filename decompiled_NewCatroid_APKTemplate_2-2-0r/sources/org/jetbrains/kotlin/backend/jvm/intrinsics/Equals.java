package org.jetbrains.kotlin.backend.jvm.intrinsics;

import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.jvm.codegen.BlockInfo;
import org.jetbrains.kotlin.backend.jvm.codegen.BooleanConstant;
import org.jetbrains.kotlin.backend.jvm.codegen.BooleanValue;
import org.jetbrains.kotlin.backend.jvm.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.backend.jvm.codegen.MaterialValue;
import org.jetbrains.kotlin.backend.jvm.codegen.PromisedValue;
import org.jetbrains.kotlin.backend.jvm.codegen.PromisedValueKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.backend.jvm.mapping.IrTypeMappingKt;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.DescriptorAsmUtil;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.expressions.IrClassReference;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetClass;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.types.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: Equals.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u0019B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J \u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J0\u0010\u0014\u001a\u00020\r2\u0006\u0010\u0015\u001a\u00020\t2\u0006\u0010\u0016\u001a\u00020\t2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0018\u0010\u0007\u001a\u00020\b*\u00020\t8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\nR\u0018\u0010\u000b\u001a\u00020\b*\u00020\t8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\n¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/intrinsics/Equals;", "Lorg/jetbrains/kotlin/backend/jvm/intrinsics/IntrinsicMethod;", "operator", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/IElementType;", "(Lcom/intellij/psi/tree/IElementType;)V", "getOperator", "()Lcom/intellij/psi/tree/IElementType;", "isClassValue", "", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpression;)Z", "isEnumValue", "invoke", "Lorg/jetbrains/kotlin/backend/jvm/codegen/PromisedValue;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "codegen", "Lorg/jetbrains/kotlin/backend/jvm/codegen/ExpressionCodegen;", "data", "Lorg/jetbrains/kotlin/backend/jvm/codegen/BlockInfo;", "referenceEquals", "left", "right", "leftType", "Lorg/jetbrains/org/objectweb/asm/Type;", "BooleanNullCheck", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Equals extends IntrinsicMethod {
    private final IElementType operator;

    public Equals(IElementType operator) {
        Intrinsics.checkNotNullParameter(operator, "operator");
        this.operator = operator;
    }

    public final IElementType getOperator() {
        return this.operator;
    }

    /* JADX INFO: compiled from: Equals.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000bH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/intrinsics/Equals$BooleanNullCheck;", "Lorg/jetbrains/kotlin/backend/jvm/codegen/BooleanValue;", "value", "Lorg/jetbrains/kotlin/backend/jvm/codegen/PromisedValue;", "(Lorg/jetbrains/kotlin/backend/jvm/codegen/PromisedValue;)V", "getValue", "()Lorg/jetbrains/kotlin/backend/jvm/codegen/PromisedValue;", "discard", "", "jumpIfFalse", "target", "Lorg/jetbrains/org/objectweb/asm/Label;", "jumpIfTrue", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class BooleanNullCheck extends BooleanValue {
        private final PromisedValue value;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public BooleanNullCheck(PromisedValue value) {
            super(value.getCodegen());
            Intrinsics.checkNotNullParameter(value, "value");
            this.value = value;
        }

        public final PromisedValue getValue() {
            return this.value;
        }

        @Override // org.jetbrains.kotlin.backend.jvm.codegen.BooleanValue
        public void jumpIfFalse(Label target) {
            Intrinsics.checkNotNullParameter(target, "target");
            PromisedValueKt.materialize(this.value);
            Unit unit = Unit.INSTANCE;
            getMv().ifnonnull(target);
        }

        @Override // org.jetbrains.kotlin.backend.jvm.codegen.BooleanValue
        public void jumpIfTrue(Label target) {
            Intrinsics.checkNotNullParameter(target, "target");
            PromisedValueKt.materialize(this.value);
            Unit unit = Unit.INSTANCE;
            getMv().ifnull(target);
        }

        @Override // org.jetbrains.kotlin.backend.jvm.codegen.PromisedValue
        public void discard() {
            this.value.discard();
        }
    }

    @Override // org.jetbrains.kotlin.backend.jvm.intrinsics.IntrinsicMethod
    public PromisedValue invoke(IrFunctionAccessExpression expression, ExpressionCodegen codegen, BlockInfo data) {
        IrClassSymbol classOrNull;
        IrClass owner;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(data, "data");
        List<IrExpression> listReceiverAndArgs = JvmIrUtilsKt.receiverAndArgs(expression);
        IrExpression irExpression = listReceiverAndArgs.get(0);
        IrExpression irExpression2 = listReceiverAndArgs.get(1);
        if (IrUtilsKt.isNullConst(irExpression) || IrUtilsKt.isNullConst(irExpression2)) {
            if (IrUtilsKt.isNullConst(irExpression)) {
                irExpression = irExpression2;
            }
            PromisedValue promisedValue = (PromisedValue) irExpression.accept(codegen, data);
            if (!AsmUtil.isPrimitive(promisedValue.getType()) && ((classOrNull = IrTypesKt.getClassOrNull(irExpression.getType())) == null || (owner = classOrNull.getOwner()) == null || !IrDeclarationsKt.isSingleFieldValueClass(owner) || IrTypeUtilsKt.isNullable(irExpression.getType()))) {
                return new BooleanNullCheck(promisedValue);
            }
            promisedValue.discard();
            return new BooleanConstant(codegen, false);
        }
        Type typeMapTypeAsDeclaration = IrTypeMappingKt.mapTypeAsDeclaration(codegen.getTypeMapper(), irExpression.getType());
        if (Intrinsics.areEqual(expression.getOrigin(), IrStatementOrigin.EQEQEQ.INSTANCE) || Intrinsics.areEqual(expression.getOrigin(), IrStatementOrigin.EXCLEQEQ.INSTANCE)) {
            return referenceEquals(irExpression, irExpression2, typeMapTypeAsDeclaration, codegen, data);
        }
        Type typeMapTypeAsDeclaration2 = IrTypeMappingKt.mapTypeAsDeclaration(codegen.getTypeMapper(), irExpression2.getType());
        if ((AsmUtil.isIntOrLongPrimitive(typeMapTypeAsDeclaration) && !AsmUtil.isPrimitive(typeMapTypeAsDeclaration2)) || (AsmUtil.isIntOrLongPrimitive(typeMapTypeAsDeclaration2) && AsmUtil.isBoxedPrimitiveType(typeMapTypeAsDeclaration))) {
            ExpressionCodegen expressionCodegen = codegen;
            return new PrimitiveToObjectComparison(this.operator, AsmUtil.isIntOrLongPrimitive(typeMapTypeAsDeclaration), PromisedValueKt.materializedAt$default((PromisedValue) irExpression.accept(expressionCodegen, data), typeMapTypeAsDeclaration, irExpression.getType(), false, 4, null), PromisedValueKt.materializedAt$default((PromisedValue) irExpression2.accept(expressionCodegen, data), typeMapTypeAsDeclaration2, irExpression2.getType(), false, 4, null));
        }
        if (AsmUtil.isPrimitive(typeMapTypeAsDeclaration) && Intrinsics.areEqual(typeMapTypeAsDeclaration, typeMapTypeAsDeclaration2)) {
            if (Intrinsics.areEqual(typeMapTypeAsDeclaration, Type.FLOAT_TYPE) || Intrinsics.areEqual(typeMapTypeAsDeclaration, Type.DOUBLE_TYPE)) {
                ExpressionCodegen expressionCodegen2 = codegen;
                return new NonIEEE754FloatComparison(this.operator, PromisedValueKt.materializedAt$default((PromisedValue) irExpression.accept(expressionCodegen2, data), typeMapTypeAsDeclaration, irExpression.getType(), false, 4, null), PromisedValueKt.materializedAt$default((PromisedValue) irExpression2.accept(expressionCodegen2, data), typeMapTypeAsDeclaration2, irExpression2.getType(), false, 4, null));
            }
            return referenceEquals(irExpression, irExpression2, typeMapTypeAsDeclaration, codegen, data);
        }
        if (isEnumValue(irExpression) || isEnumValue(irExpression2)) {
            return referenceEquals(irExpression, irExpression2, typeMapTypeAsDeclaration, codegen, data);
        }
        if (isClassValue(irExpression) && isClassValue(irExpression2)) {
            return new BooleanComparison(this.operator, codegen.generateClassLiteralReference(irExpression, false, true, data), codegen.generateClassLiteralReference(irExpression2, false, true, data));
        }
        ExpressionCodegen expressionCodegen3 = codegen;
        PromisedValue promisedValue2 = (PromisedValue) irExpression.accept(expressionCodegen3, data);
        Type OBJECT_TYPE = AsmTypes.OBJECT_TYPE;
        Intrinsics.checkNotNullExpressionValue(OBJECT_TYPE, "OBJECT_TYPE");
        PromisedValueKt.materializeAt(promisedValue2, OBJECT_TYPE, codegen.getContext().getIrBuiltIns().getAnyNType());
        PromisedValue promisedValue3 = (PromisedValue) irExpression2.accept(expressionCodegen3, data);
        Type OBJECT_TYPE2 = AsmTypes.OBJECT_TYPE;
        Intrinsics.checkNotNullExpressionValue(OBJECT_TYPE2, "OBJECT_TYPE");
        PromisedValueKt.materializeAt(promisedValue3, OBJECT_TYPE2, codegen.getContext().getIrBuiltIns().getAnyNType());
        DescriptorAsmUtil.genAreEqualCall(codegen.getMv());
        Type BOOLEAN_TYPE = Type.BOOLEAN_TYPE;
        Intrinsics.checkNotNullExpressionValue(BOOLEAN_TYPE, "BOOLEAN_TYPE");
        return new MaterialValue(codegen, BOOLEAN_TYPE, codegen.getContext().getIrBuiltIns().getBooleanType());
    }

    private final PromisedValue referenceEquals(IrExpression left, IrExpression right, Type leftType, ExpressionCodegen codegen, BlockInfo data) {
        if (!AsmUtil.isPrimitive(leftType)) {
            leftType = AsmTypes.OBJECT_TYPE;
        }
        if (Intrinsics.areEqual(leftType, Type.INT_TYPE) && (IrUtilsKt.isIntegerConst(left, 0) || IrUtilsKt.isIntegerConst(right, 0))) {
            if (IrUtilsKt.isIntegerConst(left, 0)) {
                left = right;
            }
            PromisedValue promisedValue = (PromisedValue) left.accept(codegen, data);
            Intrinsics.checkNotNull(leftType);
            return new IntegerZeroComparison(PromisedValueKt.materializedAt$default(promisedValue, leftType, left.getType(), false, 4, null));
        }
        ExpressionCodegen expressionCodegen = codegen;
        PromisedValue promisedValue2 = (PromisedValue) left.accept(expressionCodegen, data);
        Intrinsics.checkNotNull(leftType);
        Type type = leftType;
        return new BooleanComparison(this.operator, PromisedValueKt.materializedAt$default(promisedValue2, type, left.getType(), false, 4, null), PromisedValueKt.materializedAt$default((PromisedValue) right.accept(expressionCodegen, data), type, right.getType(), false, 4, null));
    }

    private final boolean isEnumValue(IrExpression irExpression) {
        IrClass owner;
        IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(irExpression.getType());
        if (classOrNull == null || (owner = classOrNull.getOwner()) == null) {
            return false;
        }
        return IrUtilsKt.isEnumClass(owner) || IrUtilsKt.isEnumEntry(owner);
    }

    private final boolean isClassValue(IrExpression irExpression) {
        return (irExpression instanceof IrGetClass) || (irExpression instanceof IrClassReference);
    }
}
