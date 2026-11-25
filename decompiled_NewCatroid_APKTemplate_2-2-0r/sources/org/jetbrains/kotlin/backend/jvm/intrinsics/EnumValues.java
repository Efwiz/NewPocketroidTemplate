package org.jetbrains.kotlin.backend.jvm.intrinsics;

import com.reandroid.arsc.value.CompoundEntry;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.jvm.codegen.BlockInfo;
import org.jetbrains.kotlin.backend.jvm.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.backend.jvm.codegen.MaterialValue;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrTypeUtilsKt;
import org.jetbrains.kotlin.backend.jvm.mapping.IrTypeMapper;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.BaseExpressionCodegenKt;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: EnumIntrinsics.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/intrinsics/EnumValues;", "Lorg/jetbrains/kotlin/backend/jvm/intrinsics/IntrinsicMethod;", "()V", "ENUM_ARRAY_TYPE", "Lorg/jetbrains/org/objectweb/asm/Type;", "invoke", "Lorg/jetbrains/kotlin/backend/jvm/codegen/MaterialValue;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "codegen", "Lorg/jetbrains/kotlin/backend/jvm/codegen/ExpressionCodegen;", "data", "Lorg/jetbrains/kotlin/backend/jvm/codegen/BlockInfo;", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class EnumValues extends IntrinsicMethod {
    private static final Type ENUM_ARRAY_TYPE;
    public static final EnumValues INSTANCE = new EnumValues();

    private EnumValues() {
    }

    @Override // org.jetbrains.kotlin.backend.jvm.intrinsics.IntrinsicMethod
    public MaterialValue invoke(IrFunctionAccessExpression expression, ExpressionCodegen codegen, BlockInfo data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(data, "data");
        IrType typeArgument = expression.getTypeArgument(0);
        Intrinsics.checkNotNull(typeArgument);
        if (JvmIrTypeUtilsKt.isReifiedTypeParameter(typeArgument)) {
            BaseExpressionCodegenKt.putReifiedOperationMarkerIfTypeIsReifiedParameter(codegen, typeArgument, ReifiedTypeInliner.OperationKind.ENUM_REIFIED);
            codegen.getMv().iconst(0);
            codegen.getMv().newarray(AsmTypes.ENUM_TYPE);
            return new MaterialValue(codegen, ENUM_ARRAY_TYPE, expression.getType());
        }
        Type typeMapType$default = IrTypeMapper.mapType$default(codegen.getTypeMapper(), typeArgument, null, null, false, 14, null);
        Type arrayType = AsmUtil.getArrayType(typeMapType$default);
        Intrinsics.checkNotNullExpressionValue(arrayType, "getArrayType(...)");
        codegen.getMv().invokestatic(typeMapType$default.getInternalName(), CompoundEntry.NAME_values, Type.getMethodDescriptor(arrayType, new Type[0]), false);
        return new MaterialValue(codegen, arrayType, expression.getType());
    }

    static {
        Type arrayType = AsmUtil.getArrayType(AsmTypes.ENUM_TYPE);
        Intrinsics.checkNotNullExpressionValue(arrayType, "getArrayType(...)");
        ENUM_ARRAY_TYPE = arrayType;
    }
}
