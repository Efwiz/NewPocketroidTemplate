package org.jetbrains.kotlin.backend.jvm.intrinsics;

import com.android.dx.rop.code.RegisterSpec;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.jvm.codegen.ClassCodegen;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.ir.descriptors.IrBasedDescriptorsKt;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodSignature;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: Equals.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J \u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016R\u000e\u0010\u0005\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/intrinsics/Ieee754Equals;", "Lorg/jetbrains/kotlin/backend/jvm/intrinsics/IntrinsicMethod;", "operandType", "Lorg/jetbrains/org/objectweb/asm/Type;", "(Lorg/jetbrains/org/objectweb/asm/Type;)V", "boxedOperandType", "getOperandType", "()Lorg/jetbrains/org/objectweb/asm/Type;", "toCallable", "Lorg/jetbrains/kotlin/backend/jvm/intrinsics/IntrinsicFunction;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "signature", "Lorg/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmMethodSignature;", "classCodegen", "Lorg/jetbrains/kotlin/backend/jvm/codegen/ClassCodegen;", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Ieee754Equals extends IntrinsicMethod {
    private final Type boxedOperandType;
    private final Type operandType;

    public Ieee754Equals(Type operandType) {
        Intrinsics.checkNotNullParameter(operandType, "operandType");
        this.operandType = operandType;
        Type typeBoxType = AsmUtil.boxType(operandType);
        Intrinsics.checkNotNullExpressionValue(typeBoxType, "boxType(...)");
        this.boxedOperandType = typeBoxType;
    }

    public final Type getOperandType() {
        return this.operandType;
    }

    /* JADX INFO: compiled from: Equals.kt */
    @Metadata(d1 = {"\u0000\u001f\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\r"}, d2 = {"org/jetbrains/kotlin/backend/jvm/intrinsics/Ieee754Equals$toCallable$Ieee754AreEqual", "Lorg/jetbrains/kotlin/backend/jvm/intrinsics/IntrinsicFunction;", "left", "Lorg/jetbrains/org/objectweb/asm/Type;", "right", "(Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;Lorg/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmMethodSignature;Lorg/jetbrains/kotlin/backend/jvm/codegen/ClassCodegen;Lorg/jetbrains/org/objectweb/asm/Type;Lorg/jetbrains/org/objectweb/asm/Type;)V", "getLeft", "()Lorg/jetbrains/org/objectweb/asm/Type;", "getRight", "genInvokeInstruction", "", RegisterSpec.PREFIX, "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Ieee754AreEqual extends IntrinsicFunction {
        private final Type left;
        private final Type right;

        public final Type getLeft() {
            return this.left;
        }

        public final Type getRight() {
            return this.right;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Ieee754AreEqual(IrFunctionAccessExpression expression, JvmMethodSignature signature, ClassCodegen classCodegen, Type left, Type right) {
            super(expression, signature, classCodegen, CollectionsKt.listOf((Object[]) new Type[]{left, right}));
            Intrinsics.checkNotNullParameter(expression, "$expression");
            Intrinsics.checkNotNullParameter(signature, "$signature");
            Intrinsics.checkNotNullParameter(classCodegen, "$classCodegen");
            Intrinsics.checkNotNullParameter(left, "left");
            Intrinsics.checkNotNullParameter(right, "right");
            this.left = left;
            this.right = right;
        }

        @Override // org.jetbrains.kotlin.backend.jvm.intrinsics.IntrinsicFunction
        public void genInvokeInstruction(InstructionAdapter v) {
            Intrinsics.checkNotNullParameter(v, "v");
            v.invokestatic("kotlin/jvm/internal/Intrinsics", "areEqual", Type.getMethodDescriptor(Type.BOOLEAN_TYPE, this.left, this.right), false);
        }
    }

    @Override // org.jetbrains.kotlin.backend.jvm.intrinsics.IntrinsicMethod
    public IntrinsicFunction toCallable(IrFunctionAccessExpression expression, JvmMethodSignature signature, ClassCodegen classCodegen) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(classCodegen, "classCodegen");
        IrExpression valueArgument = expression.getValueArgument(0);
        Intrinsics.checkNotNull(valueArgument);
        IrExpression valueArgument2 = expression.getValueArgument(1);
        Intrinsics.checkNotNull(valueArgument2);
        KotlinType irBasedKotlinType$default = IrBasedDescriptorsKt.toIrBasedKotlinType$default(valueArgument.getType(), null, 1, null);
        if (!TypeUtilsKt.isPrimitiveNumberOrNullableType(irBasedKotlinType$default) && !TypeUtilsKt.upperBoundedByPrimitiveNumberOrNullableType(irBasedKotlinType$default)) {
            throw new AssertionError("Should be primitive or nullable primitive type: " + irBasedKotlinType$default);
        }
        KotlinType irBasedKotlinType$default2 = IrBasedDescriptorsKt.toIrBasedKotlinType$default(valueArgument2.getType(), null, 1, null);
        if (!TypeUtilsKt.isPrimitiveNumberOrNullableType(irBasedKotlinType$default2) && !TypeUtilsKt.upperBoundedByPrimitiveNumberOrNullableType(irBasedKotlinType$default2)) {
            throw new AssertionError("Should be primitive or nullable primitive type: " + irBasedKotlinType$default2);
        }
        boolean zIsNullable = KotlinTypeKt.isNullable(irBasedKotlinType$default);
        boolean zIsNullable2 = KotlinTypeKt.isNullable(irBasedKotlinType$default2);
        if (!classCodegen.getContext().getState().getLanguageVersionSettings().supportsFeature(LanguageFeature.ProperIeee754Comparisons) && (JvmIrUtilsKt.isSmartcastFromHigherThanNullable(valueArgument, classCodegen.getContext()) || JvmIrUtilsKt.isSmartcastFromHigherThanNullable(valueArgument2, classCodegen.getContext()))) {
            Type OBJECT_TYPE = AsmTypes.OBJECT_TYPE;
            Intrinsics.checkNotNullExpressionValue(OBJECT_TYPE, "OBJECT_TYPE");
            Type OBJECT_TYPE2 = AsmTypes.OBJECT_TYPE;
            Intrinsics.checkNotNullExpressionValue(OBJECT_TYPE2, "OBJECT_TYPE");
            return new Ieee754AreEqual(expression, signature, classCodegen, OBJECT_TYPE, OBJECT_TYPE2);
        }
        if (!zIsNullable && !zIsNullable2) {
            Type type = this.operandType;
            return new IntrinsicFunction(expression, signature, classCodegen, CollectionsKt.listOf((Object[]) new Type[]{type, type})) { // from class: org.jetbrains.kotlin.backend.jvm.intrinsics.Ieee754Equals.toCallable.1
                @Override // org.jetbrains.kotlin.backend.jvm.intrinsics.IntrinsicFunction
                public void genInvokeInstruction(InstructionAdapter v) {
                    Intrinsics.checkNotNullParameter(v, "v");
                    StackValue.cmp(KtTokens.EQEQ, this.getOperandType(), StackValue.onStack(this.getOperandType()), StackValue.onStack(this.getOperandType())).put(Type.BOOLEAN_TYPE, v);
                }
            };
        }
        if (zIsNullable && !zIsNullable2) {
            return new Ieee754AreEqual(expression, signature, classCodegen, this.boxedOperandType, this.operandType);
        }
        if (!zIsNullable && zIsNullable2) {
            return new Ieee754AreEqual(expression, signature, classCodegen, this.operandType, this.boxedOperandType);
        }
        Type type2 = this.boxedOperandType;
        return new Ieee754AreEqual(expression, signature, classCodegen, type2, type2);
    }
}
