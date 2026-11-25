package org.jetbrains.kotlin.backend.jvm.intrinsics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.jetbrains.kotlin.backend.jvm.JvmLoweredDeclarationOrigin;
import org.jetbrains.kotlin.backend.jvm.codegen.BlockInfo;
import org.jetbrains.kotlin.backend.jvm.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.backend.jvm.codegen.MaterialValue;
import org.jetbrains.kotlin.backend.jvm.codegen.PromisedValue;
import org.jetbrains.kotlin.backend.jvm.codegen.PromisedValueKt;
import org.jetbrains.kotlin.backend.jvm.mapping.IrTypeMapper;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.DescriptorAsmUtil;
import org.jetbrains.kotlin.config.JvmTarget;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: HashCode.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/intrinsics/HashCode;", "Lorg/jetbrains/kotlin/backend/jvm/intrinsics/IntrinsicMethod;", "()V", "invoke", "Lorg/jetbrains/kotlin/backend/jvm/codegen/MaterialValue;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "codegen", "Lorg/jetbrains/kotlin/backend/jvm/codegen/ExpressionCodegen;", "data", "Lorg/jetbrains/kotlin/backend/jvm/codegen/BlockInfo;", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class HashCode extends IntrinsicMethod {
    public static final HashCode INSTANCE = new HashCode();

    private HashCode() {
    }

    @Override // org.jetbrains.kotlin.backend.jvm.intrinsics.IntrinsicMethod
    public MaterialValue invoke(IrFunctionAccessExpression expression, ExpressionCodegen codegen, BlockInfo data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(data, "data");
        IrExpression dispatchReceiver = expression.getDispatchReceiver();
        if (dispatchReceiver == null) {
            throw new IllegalStateException(("No receiver for hashCode: " + RenderIrElementKt.render$default(expression, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
        }
        IrType type = dispatchReceiver.getType();
        Type typeMapType$default = IrTypeMapper.mapType$default(codegen.getTypeMapper(), type, null, null, false, 14, null);
        MaterialValue materialValueMaterialized = PromisedValueKt.materialized((PromisedValue) dispatchReceiver.accept(codegen, data));
        Type type2 = materialValueMaterialized.getType();
        JvmTarget target = codegen.getContext().getConfig().getTarget();
        if (Intrinsics.areEqual(codegen.getIrFunction().getOrigin(), JvmLoweredDeclarationOrigin.INLINE_CLASS_GENERATED_IMPL_METHOD.INSTANCE) || Intrinsics.areEqual(codegen.getIrFunction().getOrigin(), JvmLoweredDeclarationOrigin.MULTI_FIELD_VALUE_CLASS_GENERATED_IMPL_METHOD.INSTANCE) || Intrinsics.areEqual(codegen.getIrFunction().getOrigin(), IrDeclarationOrigin.GENERATED_DATA_CLASS_MEMBER.INSTANCE) || Intrinsics.areEqual(codegen.getIrFunction().getOrigin(), IrDeclarationOrigin.GENERATED_MULTI_FIELD_VALUE_CLASS_MEMBER.INSTANCE)) {
            DescriptorAsmUtil.genHashCode(codegen.getMv(), codegen.getMv(), type2, target);
        } else if (AsmUtil.isPrimitive(typeMapType$default)) {
            Type typeBoxPrimitiveType = AsmUtil.boxPrimitiveType(typeMapType$default);
            if (typeBoxPrimitiveType == null) {
                throw new AssertionError("Primitive type expected: " + typeMapType$default);
            }
            Intrinsics.checkNotNull(typeBoxPrimitiveType);
            PromisedValueKt.materializeAt(materialValueMaterialized, typeMapType$default, type);
            codegen.getMv().visitMethodInsn(184, typeBoxPrimitiveType.getInternalName(), "hashCode", Type.getMethodDescriptor(Type.INT_TYPE, typeMapType$default), false);
        } else {
            PromisedValueKt.materializeAtBoxed(materialValueMaterialized, type);
            codegen.getMv().visitMethodInsn(182, TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_INTERNAL_NAME, "hashCode", "()I", false);
        }
        Type INT_TYPE = Type.INT_TYPE;
        Intrinsics.checkNotNullExpressionValue(INT_TYPE, "INT_TYPE");
        return new MaterialValue(codegen, INT_TYPE, codegen.getContext().getIrBuiltIns().getIntType());
    }
}
