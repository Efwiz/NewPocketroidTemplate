package org.jetbrains.kotlin.backend.common.lower.inline;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrBlock;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeUtilsKt;

/* JADX INFO: compiled from: FunctionInlining.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000(\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0003\u001a\u00020\u0001*\u00020\u0004\u001a\u0014\u0010\u0005\u001a\u00020\u0001*\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b\u001a\u001a\u0010\t\u001a\u00020\u0001*\u00020\u00042\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b¨\u0006\r"}, d2 = {"isAdaptedFunctionReference", "", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "isBuiltInSuspendCoroutineUninterceptedOrReturn", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "isInlineParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "isTopLevelInPackage", "name", "", "packageName", "ir.backend.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FunctionInliningKt {
    public static /* synthetic */ boolean isInlineParameter$default(IrValueParameter irValueParameter, IrType irType, int i, Object obj) {
        if ((i & 1) != 0) {
            irType = irValueParameter.getType();
        }
        return isInlineParameter(irValueParameter, irType);
    }

    public static final boolean isInlineParameter(IrValueParameter irValueParameter, IrType type) {
        Intrinsics.checkNotNullParameter(irValueParameter, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        return irValueParameter.getIndex() >= 0 && !irValueParameter.getIsNoinline() && !IrTypeUtilsKt.isNullable(type) && (org.jetbrains.kotlin.ir.util.IrTypeUtilsKt.isFunction(type) || org.jetbrains.kotlin.ir.util.IrTypeUtilsKt.isSuspendFunction(type));
    }

    public static final boolean isAdaptedFunctionReference(IrExpression irExpression) {
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        return (irExpression instanceof IrBlock) && Intrinsics.areEqual(((IrBlock) irExpression).getOrigin(), IrStatementOrigin.ADAPTED_FUNCTION_REFERENCE.INSTANCE);
    }

    public static final boolean isTopLevelInPackage(IrFunction irFunction, String name2, String packageName) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        if (!Intrinsics.areEqual(name2, irFunction.getName().asString())) {
            return false;
        }
        IrDeclarationParent parent = irFunction.getParent();
        IrPackageFragment irPackageFragment = parent instanceof IrPackageFragment ? (IrPackageFragment) parent : null;
        if (irPackageFragment == null) {
            return false;
        }
        String strAsString = irPackageFragment.getPackageFqName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return Intrinsics.areEqual(packageName, strAsString);
    }

    public static final boolean isBuiltInSuspendCoroutineUninterceptedOrReturn(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        String strAsString = StandardNames.COROUTINES_INTRINSICS_PACKAGE_FQ_NAME.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return isTopLevelInPackage(irFunction, "suspendCoroutineUninterceptedOrReturn", strAsString);
    }
}
