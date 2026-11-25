package org.jetbrains.kotlin.backend.jvm.intrinsics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.jvm.codegen.BlockInfo;
import org.jetbrains.kotlin.backend.jvm.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.backend.jvm.codegen.MaterialValue;
import org.jetbrains.kotlin.backend.jvm.codegen.PromisedValue;
import org.jetbrains.kotlin.backend.jvm.codegen.PromisedValueKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrTypeUtilsKt;
import org.jetbrains.kotlin.backend.jvm.mapping.IrTypeMapper;
import org.jetbrains.kotlin.codegen.BaseExpressionCodegenKt;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: EnumIntrinsics.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/intrinsics/EnumValueOf;", "Lorg/jetbrains/kotlin/backend/jvm/intrinsics/IntrinsicMethod;", "()V", "invoke", "Lorg/jetbrains/kotlin/backend/jvm/codegen/MaterialValue;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "codegen", "Lorg/jetbrains/kotlin/backend/jvm/codegen/ExpressionCodegen;", "data", "Lorg/jetbrains/kotlin/backend/jvm/codegen/BlockInfo;", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class EnumValueOf extends IntrinsicMethod {
    public static final EnumValueOf INSTANCE = new EnumValueOf();

    private EnumValueOf() {
    }

    @Override // org.jetbrains.kotlin.backend.jvm.intrinsics.IntrinsicMethod
    public MaterialValue invoke(IrFunctionAccessExpression expression, ExpressionCodegen codegen, BlockInfo data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(data, "data");
        IrType typeArgument = expression.getTypeArgument(0);
        Intrinsics.checkNotNull(typeArgument);
        IrExpression valueArgument = expression.getValueArgument(0);
        Intrinsics.checkNotNull(valueArgument);
        PromisedValue promisedValue = (PromisedValue) valueArgument.accept(codegen, data);
        Type JAVA_STRING_TYPE = AsmTypes.JAVA_STRING_TYPE;
        Intrinsics.checkNotNullExpressionValue(JAVA_STRING_TYPE, "JAVA_STRING_TYPE");
        MaterialValue materialValueMaterializedAt$default = PromisedValueKt.materializedAt$default(promisedValue, JAVA_STRING_TYPE, codegen.getContext().getIrBuiltIns().getStringType(), false, 4, null);
        if (JvmIrTypeUtilsKt.isReifiedTypeParameter(typeArgument)) {
            int iEnterTemp = codegen.getFrameMap().enterTemp(materialValueMaterializedAt$default.getType());
            codegen.getMv().store(iEnterTemp, materialValueMaterializedAt$default.getType());
            BaseExpressionCodegenKt.putReifiedOperationMarkerIfTypeIsReifiedParameter(codegen, typeArgument, ReifiedTypeInliner.OperationKind.ENUM_REIFIED);
            codegen.getMv().aconst(null);
            codegen.getMv().load(iEnterTemp, materialValueMaterializedAt$default.getType());
            codegen.getMv().invokestatic("java/lang/Enum", "valueOf", Type.getMethodDescriptor(AsmTypes.ENUM_TYPE, AsmTypes.JAVA_CLASS_TYPE, AsmTypes.JAVA_STRING_TYPE), false);
            codegen.getFrameMap().leaveTemp(materialValueMaterializedAt$default.getType());
            Type ENUM_TYPE = AsmTypes.ENUM_TYPE;
            Intrinsics.checkNotNullExpressionValue(ENUM_TYPE, "ENUM_TYPE");
            return new MaterialValue(codegen, ENUM_TYPE, expression.getType());
        }
        Type typeMapType$default = IrTypeMapper.mapType$default(codegen.getTypeMapper(), typeArgument, null, null, false, 14, null);
        codegen.getMv().invokestatic(typeMapType$default.getInternalName(), "valueOf", Type.getMethodDescriptor(typeMapType$default, AsmTypes.JAVA_STRING_TYPE), false);
        return new MaterialValue(codegen, typeMapType$default, expression.getType());
    }
}
