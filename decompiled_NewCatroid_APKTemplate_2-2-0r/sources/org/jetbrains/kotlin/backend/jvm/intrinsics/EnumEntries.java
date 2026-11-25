package org.jetbrains.kotlin.backend.jvm.intrinsics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.jvm.codegen.BlockInfo;
import org.jetbrains.kotlin.backend.jvm.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.backend.jvm.codegen.IrCodegenUtilsKt;
import org.jetbrains.kotlin.backend.jvm.codegen.MaterialValue;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrTypeUtilsKt;
import org.jetbrains.kotlin.backend.jvm.mapping.IrTypeMapper;
import org.jetbrains.kotlin.codegen.BaseExpressionCodegenKt;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.tree.FieldInsnNode;

/* JADX INFO: compiled from: EnumIntrinsics.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/intrinsics/EnumEntries;", "Lorg/jetbrains/kotlin/backend/jvm/intrinsics/IntrinsicMethod;", "()V", "invoke", "Lorg/jetbrains/kotlin/backend/jvm/codegen/MaterialValue;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "codegen", "Lorg/jetbrains/kotlin/backend/jvm/codegen/ExpressionCodegen;", "data", "Lorg/jetbrains/kotlin/backend/jvm/codegen/BlockInfo;", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class EnumEntries extends IntrinsicMethod {
    public static final EnumEntries INSTANCE = new EnumEntries();

    private EnumEntries() {
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
            codegen.getMv().aconst(null);
            codegen.getMv().checkcast(AsmTypes.ENUM_ENTRIES);
            Type ENUM_ENTRIES = AsmTypes.ENUM_ENTRIES;
            Intrinsics.checkNotNullExpressionValue(ENUM_ENTRIES, "ENUM_ENTRIES");
            return new MaterialValue(codegen, ENUM_ENTRIES, expression.getType());
        }
        FieldInsnNode fieldInsnNodeGenerateExternalEntriesForEnumTypeIfNeeded = IrCodegenUtilsKt.generateExternalEntriesForEnumTypeIfNeeded(typeArgument, codegen.getClassCodegen());
        if (fieldInsnNodeGenerateExternalEntriesForEnumTypeIfNeeded != null) {
            codegen.getMv().visitFieldInsn(fieldInsnNodeGenerateExternalEntriesForEnumTypeIfNeeded.getOpcode(), fieldInsnNodeGenerateExternalEntriesForEnumTypeIfNeeded.owner, fieldInsnNodeGenerateExternalEntriesForEnumTypeIfNeeded.f1010name, fieldInsnNodeGenerateExternalEntriesForEnumTypeIfNeeded.desc);
        } else {
            codegen.getMv().invokestatic(IrTypeMapper.mapType$default(codegen.getTypeMapper(), typeArgument, null, null, false, 14, null).getInternalName(), "getEntries", Type.getMethodDescriptor(AsmTypes.ENUM_ENTRIES, new Type[0]), false);
        }
        Type ENUM_ENTRIES2 = AsmTypes.ENUM_ENTRIES;
        Intrinsics.checkNotNullExpressionValue(ENUM_ENTRIES2, "ENUM_ENTRIES");
        return new MaterialValue(codegen, ENUM_ENTRIES2, expression.getType());
    }
}
