package org.jetbrains.kotlin.backend.jvm.intrinsics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.jvm.codegen.BlockInfo;
import org.jetbrains.kotlin.backend.jvm.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.backend.jvm.codegen.PromisedValue;
import org.jetbrains.kotlin.backend.jvm.codegen.PromisedValueKt;
import org.jetbrains.kotlin.backend.jvm.mapping.IrTypeMappingKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.expressions.IrClassReference;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetClass;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: GetJavaPrimitiveType.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\"\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\f\u0010\u000f\u001a\u00020\u0010*\u00020\u0011H\u0002J\f\u0010\u0012\u001a\u00020\u0010*\u00020\u000eH\u0002¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/intrinsics/GetJavaPrimitiveType;", "Lorg/jetbrains/kotlin/backend/jvm/intrinsics/IntrinsicMethod;", "()V", "invoke", "Lorg/jetbrains/kotlin/backend/jvm/codegen/PromisedValue;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "codegen", "Lorg/jetbrains/kotlin/backend/jvm/codegen/ExpressionCodegen;", "data", "Lorg/jetbrains/kotlin/backend/jvm/codegen/BlockInfo;", "putPrimitiveType", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/org/objectweb/asm/Type;", "isInlineClassType", "", "Lorg/jetbrains/kotlin/ir/types/IrType;", "isVoidOrPrimitiveWrapper", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class GetJavaPrimitiveType extends IntrinsicMethod {
    public static final GetJavaPrimitiveType INSTANCE = new GetJavaPrimitiveType();

    private GetJavaPrimitiveType() {
    }

    @Override // org.jetbrains.kotlin.backend.jvm.intrinsics.IntrinsicMethod
    public PromisedValue invoke(IrFunctionAccessExpression expression, ExpressionCodegen codegen, BlockInfo data) {
        IrType classType;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(data, "data");
        IrExpression extensionReceiver = expression.getExtensionReceiver();
        if (extensionReceiver == null) {
            return null;
        }
        boolean z = extensionReceiver instanceof IrGetClass;
        if (z) {
            classType = ((IrGetClass) extensionReceiver).getArgument().getType();
        } else {
            if (!(extensionReceiver instanceof IrClassReference)) {
                return null;
            }
            classType = ((IrClassReference) extensionReceiver).getClassType();
        }
        if (IrTypeUtilsKt.isTypeParameter(classType)) {
            return null;
        }
        Type typeMapTypeAsDeclaration = IrTypeMappingKt.mapTypeAsDeclaration(codegen.getTypeMapper(), classType);
        boolean z2 = false;
        if (IrTypePredicatesKt.isPrimitiveType$default(classType, false, 1, null) || IrTypePredicatesKt.isNullablePrimitiveType(classType) || (!isInlineClassType(classType) && isVoidOrPrimitiveWrapper(typeMapTypeAsDeclaration))) {
            z2 = true;
        }
        if (!z) {
            if (extensionReceiver instanceof IrClassReference) {
                if (!z2) {
                    codegen.getMv().aconst(null);
                } else {
                    putPrimitiveType(codegen, typeMapTypeAsDeclaration);
                }
                return codegen.getOnStack(expression);
            }
            throw new AssertionError("IrGetClass or IrClassReference expected: " + RenderIrElementKt.render$default(extensionReceiver, (DumpIrTreeOptions) null, 1, (Object) null));
        }
        if (!z2) {
            return null;
        }
        PromisedValue promisedValue = (PromisedValue) ((IrGetClass) extensionReceiver).getArgument().accept(codegen, data);
        PromisedValueKt.materialize(promisedValue);
        AsmUtil.pop(codegen.getMv(), promisedValue.getType());
        putPrimitiveType(codegen, typeMapTypeAsDeclaration);
        return codegen.getOnStack(expression);
    }

    private final void putPrimitiveType(ExpressionCodegen codegen, Type type) {
        codegen.getMv().getstatic(AsmUtil.boxType(type).getInternalName(), "TYPE", "Ljava/lang/Class;");
    }

    private final boolean isInlineClassType(IrType irType) {
        IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(irType);
        if (classOrNull == null) {
            return false;
        }
        return IrDeclarationsKt.isSingleFieldValueClass(classOrNull.getOwner());
    }

    private final boolean isVoidOrPrimitiveWrapper(Type type) {
        return Intrinsics.areEqual(type, AsmTypes.VOID_WRAPPER_TYPE) || AsmUtil.unboxPrimitiveTypeOrNull(type) != null;
    }
}
