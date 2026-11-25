package org.jetbrains.kotlin.backend.jvm.lower;

import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.phaser.DumperVerifierKt;
import org.jetbrains.kotlin.backend.common.phaser.NamedCompilerPhase;
import org.jetbrains.kotlin.backend.common.phaser.PhaseBuildersKt;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrBlock;
import org.jetbrains.kotlin.ir.expressions.IrBranch;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrWhen;

/* JADX INFO: compiled from: JvmSafeCallChainFoldingLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000,\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0016\u0010\u0007\u001a\u0004\u0018\u00010\b*\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0000\u001a\u0016\u0010\f\u001a\u0004\u0018\u00010\r*\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0000\"-\u0010\u0000\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001j\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003`\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000e"}, d2 = {"jvmSafeCallFoldingPhase", "Lorg/jetbrains/kotlin/backend/common/phaser/NamedCompilerPhase;", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "Lorg/jetbrains/kotlin/backend/common/phaser/SameTypeNamedCompilerPhase;", "getJvmSafeCallFoldingPhase", "()Lorg/jetbrains/kotlin/backend/common/phaser/NamedCompilerPhase;", "parseElvis", "Lorg/jetbrains/kotlin/backend/jvm/lower/ElvisInfo;", "Lorg/jetbrains/kotlin/ir/expressions/IrBlock;", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "parseSafeCall", "Lorg/jetbrains/kotlin/backend/jvm/lower/SafeCallInfo;", "backend.jvm.lower"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JvmSafeCallChainFoldingLoweringKt {
    private static final NamedCompilerPhase<JvmBackendContext, IrFile> jvmSafeCallFoldingPhase = PhaseBuildersKt.makeIrFilePhase(JvmSafeCallChainFoldingLoweringKt$jvmSafeCallFoldingPhase$1.INSTANCE, "JvmSafeCallChainFoldingLowering", "Fold safe call chains to more compact forms", (248 & 8) != 0 ? SetsKt.emptySet() : null, (248 & 16) != 0 ? SetsKt.emptySet() : null, (248 & 32) != 0 ? SetsKt.emptySet() : null, (248 & 64) != 0 ? SetsKt.emptySet() : null, (248 & 128) != 0 ? SetsKt.setOf((Object[]) new Function3[]{DumperVerifierKt.getDefaultDumper(), DumperVerifierKt.getValidationAction()}) : null);

    public static final NamedCompilerPhase<JvmBackendContext, IrFile> getJvmSafeCallFoldingPhase() {
        return jvmSafeCallFoldingPhase;
    }

    public static final SafeCallInfo parseSafeCall(IrBlock irBlock, IrBuiltIns irBuiltIns) {
        Intrinsics.checkNotNullParameter(irBlock, "<this>");
        Intrinsics.checkNotNullParameter(irBuiltIns, "irBuiltIns");
        if (irBlock.getStatements().size() != 2) {
            return null;
        }
        IrStatement irStatement = irBlock.getStatements().get(0);
        IrVariable irVariable = irStatement instanceof IrVariable ? (IrVariable) irStatement : null;
        if (irVariable == null) {
            return null;
        }
        IrStatement irStatement2 = irBlock.getStatements().get(1);
        IrWhen irWhen = irStatement2 instanceof IrWhen ? (IrWhen) irStatement2 : null;
        if (irWhen == null || irWhen.getBranches().size() != 2) {
            return null;
        }
        IrBranch irBranch = irWhen.getBranches().get(0);
        IrExpression condition = irBranch.getCondition();
        if (!(condition instanceof IrCall)) {
            return null;
        }
        IrCall irCall = (IrCall) condition;
        if (!Intrinsics.areEqual(irCall.getSymbol(), irBuiltIns.getEqeqSymbol())) {
            return null;
        }
        IrExpression valueArgument = irCall.getValueArgument(0);
        if (!(valueArgument instanceof IrGetValue) || !Intrinsics.areEqual(((IrGetValue) valueArgument).getSymbol(), irVariable.getSymbol())) {
            return null;
        }
        IrExpression valueArgument2 = irCall.getValueArgument(1);
        if (!(valueArgument2 instanceof IrConst) || ((IrConst) valueArgument2).getValue() != null) {
            return null;
        }
        IrExpression result = irBranch.getResult();
        if ((result instanceof IrConst) && ((IrConst) result).getValue() == null) {
            return new SafeCallInfo(irBlock, irVariable, irBranch, irWhen.getBranches().get(1));
        }
        return null;
    }

    public static final ElvisInfo parseElvis(IrBlock irBlock, IrBuiltIns irBuiltIns) {
        IrExpression initializer;
        Intrinsics.checkNotNullParameter(irBlock, "<this>");
        Intrinsics.checkNotNullParameter(irBuiltIns, "irBuiltIns");
        if (irBlock.getStatements().size() != 2) {
            return null;
        }
        IrStatement irStatement = irBlock.getStatements().get(0);
        IrVariable irVariable = irStatement instanceof IrVariable ? (IrVariable) irStatement : null;
        if (irVariable == null) {
            return null;
        }
        IrStatement irStatement2 = irBlock.getStatements().get(1);
        IrWhen irWhen = irStatement2 instanceof IrWhen ? (IrWhen) irStatement2 : null;
        if (irWhen == null || irWhen.getBranches().size() != 2 || (initializer = irVariable.getInitializer()) == null) {
            return null;
        }
        IrBranch irBranch = irWhen.getBranches().get(0);
        IrExpression condition = irBranch.getCondition();
        if (!(condition instanceof IrCall)) {
            return null;
        }
        IrCall irCall = (IrCall) condition;
        if (!Intrinsics.areEqual(irCall.getSymbol(), irBuiltIns.getEqeqSymbol())) {
            return null;
        }
        IrExpression valueArgument = irCall.getValueArgument(0);
        if (!(valueArgument instanceof IrGetValue) || !Intrinsics.areEqual(((IrGetValue) valueArgument).getSymbol(), irVariable.getSymbol())) {
            return null;
        }
        IrExpression valueArgument2 = irCall.getValueArgument(1);
        if (!(valueArgument2 instanceof IrConst) || ((IrConst) valueArgument2).getValue() != null) {
            return null;
        }
        IrExpression result = irBranch.getResult();
        IrExpression result2 = irWhen.getBranches().get(1).getResult();
        if ((result2 instanceof IrGetValue) && Intrinsics.areEqual(((IrGetValue) result2).getSymbol(), irVariable.getSymbol())) {
            return new ElvisInfo(irBlock, irVariable, initializer, result);
        }
        return null;
    }
}
