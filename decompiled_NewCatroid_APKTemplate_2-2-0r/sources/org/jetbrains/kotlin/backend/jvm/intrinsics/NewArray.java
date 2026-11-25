package org.jetbrains.kotlin.backend.jvm.intrinsics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.jvm.codegen.BlockInfo;
import org.jetbrains.kotlin.backend.jvm.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.backend.jvm.codegen.PromisedValue;
import org.jetbrains.kotlin.backend.jvm.mapping.IrTypeMapper;
import org.jetbrains.kotlin.codegen.BaseExpressionCodegenKt;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypeUtilsKt;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: NewArray.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/intrinsics/NewArray;", "Lorg/jetbrains/kotlin/backend/jvm/intrinsics/IntrinsicMethod;", "()V", "invoke", "Lorg/jetbrains/kotlin/backend/jvm/codegen/PromisedValue;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "codegen", "Lorg/jetbrains/kotlin/backend/jvm/codegen/ExpressionCodegen;", "data", "Lorg/jetbrains/kotlin/backend/jvm/codegen/BlockInfo;", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NewArray extends IntrinsicMethod {
    public static final NewArray INSTANCE = new NewArray();

    private NewArray() {
    }

    @Override // org.jetbrains.kotlin.backend.jvm.intrinsics.IntrinsicMethod
    public PromisedValue invoke(IrFunctionAccessExpression expression, ExpressionCodegen codegen, BlockInfo data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(data, "data");
        IrExpression valueArgument = expression.getValueArgument(0);
        Intrinsics.checkNotNull(valueArgument);
        Type INT_TYPE = Type.INT_TYPE;
        Intrinsics.checkNotNullExpressionValue(INT_TYPE, "INT_TYPE");
        codegen.gen(valueArgument, INT_TYPE, codegen.getContext().getIrBuiltIns().getIntType(), data);
        IrType arrayElementType = IrTypeUtilsKt.getArrayElementType(expression.getType(), codegen.getContext().getIrBuiltIns());
        if (IrTypePredicatesKt.isArray(expression.getType()) || IrTypePredicatesKt.isNullableArray(expression.getType())) {
            BaseExpressionCodegenKt.putReifiedOperationMarkerIfTypeIsReifiedParameter(codegen, arrayElementType, ReifiedTypeInliner.OperationKind.NEW_ARRAY);
            codegen.getMv().newarray(codegen.getTypeMapper().boxType(arrayElementType));
        } else {
            codegen.getMv().newarray(IrTypeMapper.mapType$default(codegen.getTypeMapper(), arrayElementType, null, null, false, 14, null));
        }
        return codegen.getOnStack(expression);
    }
}
