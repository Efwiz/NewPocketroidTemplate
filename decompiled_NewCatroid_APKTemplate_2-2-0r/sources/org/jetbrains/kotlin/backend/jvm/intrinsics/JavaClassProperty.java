package org.jetbrains.kotlin.backend.jvm.intrinsics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.jetbrains.kotlin.backend.jvm.codegen.BlockInfo;
import org.jetbrains.kotlin.backend.jvm.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.backend.jvm.codegen.PromisedValue;
import org.jetbrains.kotlin.backend.jvm.codegen.PromisedValueKt;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: JavaClassProperty.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0004H\u0002J\u0016\u0010\u000e\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u0010¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/intrinsics/JavaClassProperty;", "Lorg/jetbrains/kotlin/backend/jvm/intrinsics/IntrinsicMethod;", "()V", "invoke", "Lorg/jetbrains/kotlin/backend/jvm/codegen/PromisedValue;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "codegen", "Lorg/jetbrains/kotlin/backend/jvm/codegen/ExpressionCodegen;", "data", "Lorg/jetbrains/kotlin/backend/jvm/codegen/BlockInfo;", "invokeGetClass", "", "value", "invokeWith", "wrapPrimitives", "", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaClassProperty extends IntrinsicMethod {
    public static final JavaClassProperty INSTANCE = new JavaClassProperty();

    private JavaClassProperty() {
    }

    private final void invokeGetClass(PromisedValue value) {
        value.getMv().invokevirtual(TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_INTERNAL_NAME, "getClass", "()Ljava/lang/Class;", false);
    }

    public final void invokeWith(PromisedValue value, boolean wrapPrimitives) {
        IrClass owner;
        Intrinsics.checkNotNullParameter(value, "value");
        if (Intrinsics.areEqual(value.getType(), Type.VOID_TYPE)) {
            Type UNIT_TYPE = AsmTypes.UNIT_TYPE;
            Intrinsics.checkNotNullExpressionValue(UNIT_TYPE, "UNIT_TYPE");
            invokeGetClass(PromisedValueKt.materializedAt$default(value, UNIT_TYPE, value.getCodegen().getContext().getIrBuiltIns().getUnitType(), false, 4, null));
            return;
        }
        IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(value.getIrType());
        if (classOrNull != null && (owner = classOrNull.getOwner()) != null && IrDeclarationsKt.isSingleFieldValueClass(owner)) {
            invokeGetClass(PromisedValueKt.materializedAtBoxed(value, value.getIrType()));
            return;
        }
        if (AsmUtil.isPrimitive(value.getType())) {
            value.discard();
            if (wrapPrimitives) {
                value.getMv().aconst(AsmUtil.boxType(value.getType()));
                return;
            } else {
                value.getMv().getstatic(AsmUtil.boxType(value.getType()).getInternalName(), "TYPE", "Ljava/lang/Class;");
                return;
            }
        }
        invokeGetClass(PromisedValueKt.materialized(value));
    }

    @Override // org.jetbrains.kotlin.backend.jvm.intrinsics.IntrinsicMethod
    public PromisedValue invoke(IrFunctionAccessExpression expression, ExpressionCodegen codegen, BlockInfo data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(data, "data");
        IrExpression extensionReceiver = expression.getExtensionReceiver();
        Intrinsics.checkNotNull(extensionReceiver);
        invokeWith((PromisedValue) extensionReceiver.accept(codegen, data), false);
        return codegen.getOnStack(expression);
    }
}
