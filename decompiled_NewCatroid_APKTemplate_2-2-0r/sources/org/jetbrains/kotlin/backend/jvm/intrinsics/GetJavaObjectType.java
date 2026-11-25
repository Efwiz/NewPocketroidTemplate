package org.jetbrains.kotlin.backend.jvm.intrinsics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.jetbrains.kotlin.backend.jvm.codegen.BlockInfo;
import org.jetbrains.kotlin.backend.jvm.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.backend.jvm.codegen.PromisedValue;
import org.jetbrains.kotlin.backend.jvm.codegen.PromisedValueKt;
import org.jetbrains.kotlin.backend.jvm.mapping.IrTypeMappingKt;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.BaseExpressionCodegenKt;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;
import org.jetbrains.kotlin.ir.expressions.IrClassReference;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetClass;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: GetJavaObjectType.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\"\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/intrinsics/GetJavaObjectType;", "Lorg/jetbrains/kotlin/backend/jvm/intrinsics/IntrinsicMethod;", "()V", "invoke", "Lorg/jetbrains/kotlin/backend/jvm/codegen/PromisedValue;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "codegen", "Lorg/jetbrains/kotlin/backend/jvm/codegen/ExpressionCodegen;", "data", "Lorg/jetbrains/kotlin/backend/jvm/codegen/BlockInfo;", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class GetJavaObjectType extends IntrinsicMethod {
    public static final GetJavaObjectType INSTANCE = new GetJavaObjectType();

    private GetJavaObjectType() {
    }

    @Override // org.jetbrains.kotlin.backend.jvm.intrinsics.IntrinsicMethod
    public PromisedValue invoke(IrFunctionAccessExpression expression, ExpressionCodegen codegen, BlockInfo data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(data, "data");
        IrExpression extensionReceiver = expression.getExtensionReceiver();
        if (extensionReceiver instanceof IrClassReference) {
            IrClassReference irClassReference = (IrClassReference) extensionReceiver;
            if (irClassReference.getSymbol() instanceof IrTypeParameterSymbol) {
                BaseExpressionCodegenKt.putReifiedOperationMarkerIfTypeIsReifiedParameter(codegen, irClassReference.getClassType(), ReifiedTypeInliner.OperationKind.JAVA_CLASS);
            }
            codegen.getMv().aconst(AsmUtil.boxType(IrTypeMappingKt.mapTypeAsDeclaration(codegen.getTypeMapper(), irClassReference.getClassType())));
            return codegen.getOnStack(expression);
        }
        if (!(extensionReceiver instanceof IrGetClass)) {
            return null;
        }
        PromisedValue promisedValue = (PromisedValue) ((IrGetClass) extensionReceiver).getArgument().accept(codegen, data);
        PromisedValueKt.materialize(promisedValue);
        Type type = promisedValue.getType();
        if (Intrinsics.areEqual(type, Type.VOID_TYPE)) {
            codegen.getMv().aconst(AsmTypes.UNIT_TYPE);
        } else if (AsmUtil.isPrimitive(type) || AsmUtil.unboxPrimitiveTypeOrNull(type) != null) {
            AsmUtil.pop(codegen.getMv(), type);
            codegen.getMv().aconst(AsmUtil.boxType(type));
        } else {
            codegen.getMv().invokevirtual(TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_INTERNAL_NAME, "getClass", "()Ljava/lang/Class;", false);
        }
        return codegen.getOnStack(expression);
    }
}
